package com.supporter.prj.cneec.tpc.contract_refund.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.collection_confirmation.entity.CollectionConfirmation;
import com.supporter.prj.cneec.tpc.collection_confirmation.service.CollectionConfirmationService;
import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.contract_refund.dao.ContractRefundDao;
import com.supporter.prj.cneec.tpc.contract_refund.dao.ContractRefundDetailDao;
import com.supporter.prj.cneec.tpc.contract_refund.entity.ContractRefund;
import com.supporter.prj.cneec.tpc.contract_refund.entity.ContractRefundDetail;
import com.supporter.prj.cneec.tpc.contract_refund.util.ContractRefundConstant;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractTable;
import com.supporter.prj.cneec.tpc.prj_contract_table.service.PrjContractTableService;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: ContractRefundService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2017-11-24
 * @version: V1.0
 */
@Service
public class ContractRefundService {

	@Autowired
	private ContractRefundDao contractRefundDao;
	@Autowired
	private ContractRefundDetailDao detailDao;

	@Autowired
	private PrjContractTableService contractSercice;
	@Autowired
	private CollectionConfirmationService collectionConfirmationService;
	@Autowired
	private RegisterProjectService registerProjectService;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, ContractRefund.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param contractRefund
	 */
	public void getAuthCanExecute(UserProfile userProfile, ContractRefund contractRefund) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, ContractRefund.MODULE_ID, contractRefund.getRefundId(), contractRefund);
	}

	/**
	 * 获取销售合同退款对象集合
	 * 
	 * @param user
	 * @param jqGrid
	 * @param contractRefund
	 * @return
	 */
	public List<ContractRefund> getGrid(UserProfile user, JqGrid jqGrid, ContractRefund contractRefund) {
		String authFilter = getAuthFilter(user);
		return this.contractRefundDao.findPage(jqGrid, contractRefund, authFilter);
	}

	/**
	 * 获取单个销售合同退款对象
	 * 
	 * @param refundId
	 * @return
	 */
	public ContractRefund get(String refundId) {
		return this.contractRefundDao.get(refundId);
	}

	/**
	 * 新建销售合同退款对象
	 * 
	 * @param user
	 * @return
	 */
	public ContractRefund newContractRefund(UserProfile user) {
		ContractRefund contractRefund = new ContractRefund();
		contractRefund.setCreatedBy(user.getName());
		contractRefund.setCreatedById(user.getPersonId());
		contractRefund.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		contractRefund.setModifiedBy(user.getName());
		contractRefund.setModifiedById(user.getPersonId());
		contractRefund.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			contractRefund.setDeptName(dept.getName());
			contractRefund.setDeptId(dept.getDeptId());
		}
		contractRefund.setSwfStatus(ContractRefund.DRAFT);
		return contractRefund;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * 
	 * @param refundId
	 * @param user
	 * @return
	 */
	@Transactional(TransManager.APP)
	public ContractRefund initEditOrViewPage(String refundId, Map<String, Object> valueMap, UserProfile user) {
		ContractRefund contractRefund = null;
		if (StringUtils.isNotBlank(refundId)) {
			contractRefund = this.contractRefundDao.get(refundId);
		} else {
			refundId = UUIDHex.newId();
		}
		
		if (contractRefund == null) {
			contractRefund = newContractRefund(user);
			contractRefund.setRefundId(refundId);
			contractRefund.setAdd(true);
			
			// 根据合同ID取到合同信息 并赋值
			String contractId = (String) valueMap.get("contractId");
			String collectionId = (String) valueMap.get("collectionId");
			if (StringUtils.isNotBlank(contractId) && StringUtils.isNotBlank(collectionId)) {
				PrjContractTable contract = this.contractSercice.get(contractId);
				if (contract != null) {
					contractRefund.setProjectId(contract.getProjectId());
					contractRefund.setProjectName(contract.getProjectName());
					contractRefund.setContractId(contractId);
					contractRefund.setContractNo(contract.getContractNo());
					contractRefund.setContractName(contract.getContractName());
				}
				// 根据收款ID取到收款记录 并回填赋值
				CollectionConfirmation confirmation = this.collectionConfirmationService.get(collectionId);
				if (confirmation != null) {
					contractRefund.setReturnSlipId(collectionId);
					contractRefund.setReturnSlipNo(confirmation.getCollectionNo());
					contractRefund.setBankAccount(confirmation.getBankAccount());
					contractRefund.setDepositBank(confirmation.getBank());
					contractRefund.setCollectionUnit(confirmation.getCompanyName());
					contractRefund.setBankCode(confirmation.getBankCode());
					if (StringUtils.isBlank(contractRefund.getBankCode())) {
						contractRefund.setIsAbroad(true);
					} else {
						contractRefund.setIsAbroad(false);
					}
					contractRefund.setRemitProvince(confirmation.getRemitProvince());
					contractRefund.setRemitProvinceId(confirmation.getRemitProvinceId());
					contractRefund.setRemitCity(confirmation.getRemitCity());
					contractRefund.setRemitCityId(confirmation.getRemitCityId());
				}
			}
			
			saveOrUpdate(user, contractRefund);
		}
		
		return contractRefund;
	}

	/**
	 * 保存或修改对象
	 * 
	 * @param user
	 * @param contractRefund
	 * @return
	 */
	@Transactional(TransManager.APP)
	public ContractRefund saveOrUpdate(UserProfile user, ContractRefund contractRefund) {
		if (contractRefund.getAdd()) {
			contractRefund.setAdd(false);
			this.contractRefundDao.save(contractRefund);
			this.collectionConfirmationService.updateRefundStatus(contractRefund.getReturnSlipId(), CollectionConfirmation.REFUND_LOCK);
		} else {
			// 设置更新时间
			contractRefund.setModifiedBy(user.getName());
			contractRefund.setModifiedById(user.getPersonId());
			contractRefund.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			// 删除从表
			deleteDetails(contractRefund.getDelDetailIds());
			this.contractRefundDao.update(contractRefund);
		}
		// 保存或更新从表
		List<ContractRefundDetail> detailList = contractRefund.getDetailList();
		if (CollectionUtils.isNotEmpty(detailList)) {
			saveOrUpdateDetailList(contractRefund.getRefundId(), detailList);
		}
		
		return contractRefund;
	}
	/**
	 * 删除子表
	 * @param delDetailIds
	 */
	@Transactional(TransManager.APP)
	public void deleteDetails(String delDetailIds) {
		if (StringUtils.isNotBlank(delDetailIds)) {
			for (String detailId : delDetailIds.split(",")) {
				this.detailDao.delete(detailId);
			}
		}
	}

	/**
	 * 保存或更新子表
	 * @param refundId
	 * @param detailList
	 */
	private void saveOrUpdateDetailList(String refundId, List<ContractRefundDetail> detailList) {
		for (ContractRefundDetail detail : detailList) {
			if (detail == null) {
				continue;
			}
			
			detail.setRefundId(refundId);
			if (StringUtils.isBlank(detail.getDetailId()) || detail.getAdd()) {
				detail.setAdd(false);
				this.detailDao.save(detail);
			} else {
				this.detailDao.update(detail);
			}
		}
		
	}

	/**
	 * 保存提交
	 * 
	 * @param user
	 * @param contractRefund
	 * @return
	 */
	@Transactional(TransManager.APP)
	public ContractRefund commit(UserProfile user, ContractRefund contractRefund) {
		if (StringUtils.isBlank(contractRefund.getRefundNo())) {
			// 设置退款单号
			contractRefund.setRefundNo(generatorRefundNo());
		}
		saveOrUpdate(user, contractRefund);
		commitExcute(contractRefund);
		// 记录日志
		
		return contractRefund;
	}

	@Transactional(TransManager.APP)
	public void commitExcute(ContractRefund contractRefund) {
		String projectId = contractRefund.getProjectId();
		// 设置项目经理
		if (StringUtils.isBlank(contractRefund.getPrjManagerId())) {
			RegisterProject project = this.registerProjectService.get(projectId);
			contractRefund.setPrjManagerId(project.getProjectChargeId());
			contractRefund.setPrjManager(project.getProjectCharge());
			this.update(contractRefund);
		}
	}

	/**
	 * 启动流程后操作
	 * 
	 * @param contractRefund
	 */
	@Transactional(TransManager.APP)
	public void startProc(ContractRefund contractRefund) {
		// 将退款写入收款单收款明细在途
		List<ContractRefundDetail> detailList = this.detailDao.getListByRefundId(contractRefund.getRefundId());
		for (ContractRefundDetail detail : detailList) {
			if (detail == null) {
				continue;
			}
			this.collectionConfirmationService.addOnwayAmountForDetail(detail.getRefundClauseId(), detail.getRefundAmount());
		}
		// 将收款单退款状态改为退款中
		this.collectionConfirmationService.updateRefundStatus(contractRefund.getReturnSlipId(), CollectionConfirmation.REFUNDING);
	}

	/**
	 * 流程中止后操作
	 * 
	 * @param contractRefund
	 */
	@Transactional(TransManager.APP)
	public void abortProc(ContractRefund contractRefund) {
		// 将退款移除收款单收款明细在途
		List<ContractRefundDetail> detailList = this.detailDao.getListByRefundId(contractRefund.getRefundId());
		for (ContractRefundDetail detail : detailList) {
			if (detail == null) {
				continue;
			}
			this.collectionConfirmationService.removeOnwayAmountForDetail(detail.getRefundClauseId(), detail.getRefundAmount());
		}
		// 将收款单退款状态改为退款锁定中
		this.collectionConfirmationService.updateRefundStatus(contractRefund.getReturnSlipId(), CollectionConfirmation.REFUND_LOCK);
	}

	/**
	 * 流程审批完成后操作
	 * 
	 * @param contractRefund
	 */
	@Transactional(TransManager.APP)
	public void completeExam(ContractRefund contractRefund) {
		// 将退款移除收款单收款明细在途
		List<ContractRefundDetail> detailList = this.detailDao.getListByRefundId(contractRefund.getRefundId());
		for (ContractRefundDetail detail : detailList) {
			if (detail == null) {
				continue;
			}
			this.collectionConfirmationService.transferToRealCollectionAmountForDetail(detail.getRefundClauseId(), detail.getRefundAmount());
		}
		// 将收款单退款状态改为部分/全部退款完成
		this.collectionConfirmationService.updateRefundStatus(contractRefund.getReturnSlipId(), CollectionConfirmation.REFUNDED);
	}

	/**
	 * 批量删除对象
	 * 
	 * @param user
	 * @param refundIds
	 */
	@Transactional(TransManager.APP)
	public void delete(UserProfile user, String refundIds) {
		if (StringUtils.isNotBlank(refundIds)) {
			for (String refundId : refundIds.split(",")) {
				ContractRefund contractRefund = this.get(refundId);
				if (contractRefund == null) continue;
				
				// 权限验证
				this.getAuthCanExecute(user, contractRefund);
				//更改收款业务单状态
				this.collectionConfirmationService.updateRefundStatus(contractRefund.getReturnSlipId(), CollectionConfirmation.REFUND);
				this.contractRefundDao.delete(contractRefund);
				//删除子表
				List<ContractRefundDetail> list = detailDao.getListByRefundId(refundId);
				if(list!=null&&list.size()>0){
					detailDao.delete(list);
				}
			}
		}
	}

	/**
	 * 更新对象
	 * 
	 * @param useSeal
	 * @return
	 */
	@Transactional(TransManager.APP)
	public ContractRefund update(ContractRefund contractRefund) {
		this.contractRefundDao.update(contractRefund);
		return contractRefund;
	}

	/**
	 * 生成退款单号
	 * @return
	 */
	public synchronized String generatorRefundNo() {
		String refundNo;
		String NoHead = CommonUtil.format(new Date(), "yyyyMM");
		// 过滤条件
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("refundNo", NoHead + "%");
		List<String> likeSearhNames = new ArrayList<String>();
		likeSearhNames.add("refundNo");
		Integer count = this.contractRefundDao.find(params, likeSearhNames, null).size();
		String NoEnd = String.format("%03d", count + 1);
		refundNo = NoHead + NoEnd;
		return refundNo;
	}

	/**
	 * 境内外退款
	 * @param refundId
	 * @return
	 */
	public List<CheckBoxVO> getIsAbroadList(String refundId) {
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		String choose = "";// 不设置默认
		if (refundId.length() > 0) {
			ContractRefund contractRefund = this.get(refundId);
			if (contractRefund != null) {
				choose = contractRefund.getIsAbroad() ? "true" : "false";
			}
		}
		Map<String, String> map = ContractRefundConstant.getIsAbroadMap();
		for (String key : map.keySet()) {
			CheckBoxVO vo = new CheckBoxVO("isAbroad_" + key, "isAbroad", key, map.get(key), key.equals(choose));
			list.add(vo);
		}
		return list;
	}

	/**
	 * 销售合同收款确认单列表
	 * @param contractId
	 * @param userProfile
	 * @return
	 */
	public Map<String, String> getCollectionCodeTable(String contractId, UserProfile userProfile) {
		Map<String, String> map = this.collectionConfirmationService.getRefundMap(null, contractId);
		return map;
	}

	/**
	 * 销售合同收款确认单收款明细列表
	 * @param collectionId
	 * @return
	 */
	public Map<String, String> getCollectionDetailCodeTable(String collectionId) {
		Map<String, String> map = this.collectionConfirmationService.getRefundDetailMap(collectionId);
		return map;
	}

	/**
	 * 获取退款明细列表
	 * @param user
	 * @param jqGrid
	 * @param refundId
	 */
	public List<ContractRefundDetail> getDetailGrid(UserProfile user, JqGrid jqGrid, String refundId) {
		return detailDao.findPage(jqGrid, refundId);
	}

}
