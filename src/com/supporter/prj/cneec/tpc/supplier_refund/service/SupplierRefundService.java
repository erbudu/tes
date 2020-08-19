package com.supporter.prj.cneec.tpc.supplier_refund.service;


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

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.entity.PrjContractSettlement;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.service.PrjContractSettlementService;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractTable;
import com.supporter.prj.cneec.tpc.prj_contract_table.service.PrjContractTableService;
import com.supporter.prj.cneec.tpc.register_project.dao.RegisterProjectDao;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.supplier_refund.dao.SupplierRefundDao;
import com.supporter.prj.cneec.tpc.supplier_refund.dao.SupplierRefundDetailDao;
import com.supporter.prj.cneec.tpc.supplier_refund.entity.SupplierRefund;
import com.supporter.prj.cneec.tpc.supplier_refund.entity.SupplierRefundDetail;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: SupplierRefundService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2017-11-22
 * @version: V1.0
 */
@Service
public class SupplierRefundService {

	@Autowired
	private SupplierRefundDao supplierRefundDao;	
	@Autowired
	private SupplierRefundDetailDao  detailDao;
	@Autowired
	private RegisterProjectDao registerProjectDao;
	@Autowired
	private PrjContractTableService contractSercice;
	@Autowired
	private PrjContractSettlementService contractSettlementService;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, SupplierRefund.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param supplierRefund
	 */
	public void getAuthCanExecute(UserProfile userProfile, SupplierRefund supplierRefund) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, SupplierRefund.MODULE_ID, supplierRefund.getRefundId(), supplierRefund);
	}

	/**
	 * 获取供应商退款对象集合
	 * @param user
	 * @param jqGrid
	 * @param supplierRefund
	 * @return
	 */
	public List<SupplierRefund> getGrid(UserProfile user, JqGrid jqGrid, SupplierRefund supplierRefund) {
		String authFilter = getAuthFilter(user);
		return this.supplierRefundDao.findPage(jqGrid, supplierRefund, authFilter);
	}

	/**
	 * 获取单个供应商退款对象
	 * @param refundId
	 * @return
	 */
	public SupplierRefund get(String refundId) {
		return this.supplierRefundDao.get(refundId);
	}

	/**
	 * 新建供应商退款对象
	 * @param user
	 * @return
	 */
	public SupplierRefund newSupplierRefund(UserProfile user) {
		SupplierRefund supplierRefund = new SupplierRefund();
		loadSupplierRefund(supplierRefund, user);
		supplierRefund.setSwfStatus(SupplierRefund.DRAFT);
		return supplierRefund;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public SupplierRefund loadSupplierRefund(SupplierRefund supplierRefund, UserProfile user) {
		supplierRefund.setCreatedBy(user.getName());
		supplierRefund.setCreatedById(user.getPersonId());
		supplierRefund.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		supplierRefund.setModifiedBy(user.getName());
		supplierRefund.setModifiedById(user.getPersonId());
		supplierRefund.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			supplierRefund.setDeptName(dept.getName());
			supplierRefund.setDeptId(dept.getDeptId());
		}
		// 设置状态
		supplierRefund.setSwfStatus(SupplierRefund.DRAFT);
		return supplierRefund;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param refundId
	 * @param user
	 * @return
	 */
	@Transactional(TransManager.APP)
	public SupplierRefund initEditOrViewPage(String refundId, Map<String, Object> valueMap, UserProfile user) {
		SupplierRefund supplierRefund = null;
		if (StringUtils.isNotBlank(refundId)) {
			supplierRefund = this.supplierRefundDao.get(refundId);
		} else {
			refundId = UUIDHex.newId();
		}
		
		if (supplierRefund == null) {
			supplierRefund = newSupplierRefund(user);
			supplierRefund.setRefundId(refundId);
			supplierRefund.setAdd(true);
			
			// 根据合同ID取到合同信息 并赋值
			String contractId = (String) valueMap.get("contractId");
			String settlementId = (String) valueMap.get("settlementId");
			if (StringUtils.isNotBlank(contractId) && StringUtils.isNotBlank(settlementId)) {
				// 改成采购合同的对象
				PrjContractTable contract = this.contractSercice.get(contractId);
				if (contract != null) {
					supplierRefund.setProjectId(contract.getProjectId());
					supplierRefund.setProjectName(contract.getProjectName());
					supplierRefund.setContractId(contractId);
					supplierRefund.setContractNo(contract.getContractNo());
					supplierRefund.setContractName(contract.getContractName());
					
					RegisterProject prj = registerProjectDao.get(contract.getProjectId());
					if (prj != null) {
						supplierRefund.setPrjManagerId(prj.getProjectChargeId());
						supplierRefund.setPrjManager(prj.getProjectCharge());
					}
					
				}
				PrjContractSettlement contractSettlement = this.contractSettlementService.get(settlementId);
				if (contractSettlement != null) {
					supplierRefund.setReturnSlipId(settlementId);
					supplierRefund.setReturnSlipNo(contractSettlement.getSettlementNo());
				}
			}	
			saveOrUpdate(user, supplierRefund);
		}
		
		return supplierRefund;
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param supplierRefund
	 * @return
	 */
	@Transactional(TransManager.APP)
	public SupplierRefund saveOrUpdate(UserProfile user, SupplierRefund supplierRefund) {
		if (supplierRefund.getAdd()) {
			supplierRefund.setAdd(false);
			this.supplierRefundDao.save(supplierRefund);
			this.contractSettlementService.updateRefundStatus(supplierRefund.getReturnSlipId(), PrjContractSettlement.REFUND_LOCK);
		} else {
			// 设置更新时间
			supplierRefund.setModifiedBy(user.getName());
			supplierRefund.setModifiedById(user.getPersonId());
			supplierRefund.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			// 删除从表
			deleteDetails(supplierRefund.getDelDetailIds());
			this.supplierRefundDao.update(supplierRefund);
		}
		// 保存或更新从表
		List<SupplierRefundDetail> detailList = supplierRefund.getDetailList();
		if (CollectionUtils.isNotEmpty(detailList)) {
			saveOrUpdateDetailList(supplierRefund.getRefundId(), detailList);
		}
		return supplierRefund;
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
	private void saveOrUpdateDetailList(String refundId, List<SupplierRefundDetail> detailList) {
		for (SupplierRefundDetail detail : detailList) {
			if (detail == null) {
				continue;
			}
			
			detail.setRefundId(refundId);
			if (detail.getAdd() || StringUtils.isBlank(detail.getDetailId())) {
				detail.setAdd(false);
				this.detailDao.save(detail);
			} else {
				this.detailDao.update(detail);
			}
		}
		
	}

	/**
	 * 保存提交
	 * @param user
	 * @param supplierRefund
	 * @return
	 */
	@Transactional(TransManager.APP)
	public SupplierRefund commit(UserProfile user, SupplierRefund supplierRefund) {
		if (StringUtils.isBlank(supplierRefund.getRefundNo())) {
			// 设置退款单号
			supplierRefund.setRefundNo(generatorRefundNo());
		} 
		// 改成采购合同的对象
		PrjContractTable contract = this.contractSercice.get(supplierRefund.getContractId());
		if (contract != null) {
			RegisterProject prj = registerProjectDao.get(contract.getProjectId());
			if (prj != null) {
				supplierRefund.setPrjManagerId(prj.getProjectChargeId());
				supplierRefund.setPrjManager(prj.getProjectCharge());
			}
		}
		saveOrUpdate(user, supplierRefund);
		
		return supplierRefund;
	}

	/**
	 * 启动流程后操作
	 * 
	 * @param supplierRefund
	 */
	@Transactional(TransManager.APP)
	public void startProc(SupplierRefund supplierRefund) {
		// 将退款写入收款单收款明细在途
		List<SupplierRefundDetail> detailList = this.detailDao.findBy("refundId", supplierRefund.getRefundId());
		for (SupplierRefundDetail detail : detailList) {
			this.contractSettlementService.addOnwayAmountForDetail(detail.getRefundClauseId(), detail.getRefundAmount());
		}
		// 将收款单退款状态改为退款中
		this.contractSettlementService.updateRefundStatus(supplierRefund.getReturnSlipId(), PrjContractSettlement.REFUNDING);
	}

	/**
	 * 流程中止后操作
	 * 
	 * @param supplierRefund
	 */
	@Transactional(TransManager.APP)
	public void abortProc(SupplierRefund supplierRefund) {
		// 将退款移除收款单收款明细在途
		List<SupplierRefundDetail> detailList = this.detailDao.getListByRefundId(supplierRefund.getRefundId());
		for (SupplierRefundDetail detail : detailList) {
			this.contractSettlementService.removeOnwayAmountForDetail(detail.getRefundClauseId(), detail.getRefundAmount());
		}
		// 将收款单退款状态改为退款锁定中
		this.contractSettlementService.updateRefundStatus(supplierRefund.getReturnSlipId(), PrjContractSettlement.REFUND_LOCK);
	}

	/**
	 * 流程审批完成后操作
	 * 
	 * @param supplierRefund
	 */
	@Transactional(TransManager.APP)
	public void completeExam(SupplierRefund supplierRefund) {
		// 将退款移除收款单收款明细在途
		List<SupplierRefundDetail> detailList = this.detailDao.getListByRefundId(supplierRefund.getRefundId());
		for (SupplierRefundDetail detail : detailList) {
			this.contractSettlementService.transferToRealSettlementAmountForDetail(detail.getRefundClauseId(), detail.getRefundAmount());
		}
		// 将收款单退款状态改为部分/全部退款完成
		this.contractSettlementService.updateRefundStatus(supplierRefund.getReturnSlipId(), PrjContractSettlement.REFUNDED);
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param refundIds
	 */
	@Transactional(TransManager.APP)
	public void delete(UserProfile user, String refundIds) {
		if (StringUtils.isNotBlank(refundIds)) {
			SupplierRefund supplierRefund;
			for (String refundId : refundIds.split(",")) {
				supplierRefund = this.get(refundId);
				if (supplierRefund == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, supplierRefund);
				//更改付款单状态
				this.contractSettlementService.updateRefundStatus(supplierRefund.getReturnSlipId(), PrjContractSettlement.REFUND);
				this.supplierRefundDao.delete(supplierRefund);
				//删除子表
				List<SupplierRefundDetail> list = detailDao.getListByRefundId(refundId);
				if(list!=null&&list.size()>0){
					detailDao.delete(list);
				}
			}
		}
	}

	/**
	 * 更新对象
	 * @param useSeal
	 * @return
	 */
	@Transactional(TransManager.APP)
	public SupplierRefund update(SupplierRefund supplierRefund) {
		this.supplierRefundDao.update(supplierRefund);
		return supplierRefund;
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
		Integer count = this.supplierRefundDao.find(params, likeSearhNames, null).size();
		String NoEnd = String.format("%03d", count + 1);
		refundNo = NoHead + NoEnd;
		return refundNo;
	}

	/**
	 * 获取明细列表
	 * @param user
	 * @param jqGrid
	 * @param refundId
	 */
	public List<SupplierRefundDetail> getDetailGrid(UserProfile user, JqGrid jqGrid, String refundId) {
		return detailDao.findPage(jqGrid, refundId);
	}

	/**
	 * 采购合同付款单列表
	 * @param contractId
	 * @param userProfile
	 * @return
	 */
	public Map<String, String> getSettlementCodeTable(String contractId, UserProfile userProfile) {
		Map<String, String> map = this.contractSettlementService.getRefundMap(null, contractId);
		return map;
	}

	/**
	 * 采购合同付款单列表
	 * @param settlementId
	 * @return
	 */
	public Map<String, String> getSettlementDetailCodeTable(String settlementId) {
		Map<String, String> map = this.contractSettlementService.getRefundDetailMap(settlementId);
		return map;
	}

}
