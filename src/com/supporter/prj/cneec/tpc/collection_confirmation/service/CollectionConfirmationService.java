package com.supporter.prj.cneec.tpc.collection_confirmation.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.collection_confirmation.dao.CollectionConfirmationDao;
import com.supporter.prj.cneec.tpc.collection_confirmation.dao.CollectionDetailDao;
import com.supporter.prj.cneec.tpc.collection_confirmation.entity.CollectionConfirmation;
import com.supporter.prj.cneec.tpc.collection_confirmation.entity.CollectionDetail;
import com.supporter.prj.cneec.tpc.collection_confirmation.util.CollectionConfirmationConstant;
import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.custom.dao.CustomPayaccountDao;
import com.supporter.prj.cneec.tpc.custom.entity.CustomPayaccount;
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
 * @Title: CollectionConfirmationService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2017-11-17
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class CollectionConfirmationService {

	@Autowired
	private CollectionConfirmationDao collectionConfirmationDao;
	@Autowired
	private CollectionDetailDao collectionDetailDao;

	@Autowired
	private RegisterProjectService registerProjectService;
	@Autowired
	private CustomPayaccountDao customPayaccountDao;
	@Autowired
	private PrjContractTableService contractsercice;
	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, CollectionConfirmation.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param collectionConfirmation
	 */
	public void getAuthCanExecute(UserProfile userProfile, CollectionConfirmation collectionConfirmation) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, CollectionConfirmation.MODULE_ID, collectionConfirmation.getCollectionId(), collectionConfirmation);
	}

	/**
	 * 获取收款确认对象集合
	 * @param user
	 * @param jqGrid
	 * @param collectionConfirmation
	 * @return
	 */
	public List<CollectionConfirmation> getGrid(UserProfile user, JqGrid jqGrid, CollectionConfirmation collectionConfirmation) {
		String authFilter = getAuthFilter(user);
		return this.collectionConfirmationDao.findPage(jqGrid, collectionConfirmation, authFilter);
	}

	/**
	 * 获取从表集合
	 * @param user
	 * @param jqGrid
	 * @param collectionId
	 * @return
	 */
	public List<CollectionDetail> getDetailGrid(UserProfile user, JqGrid jqGrid, String collectionId) {
		return this.collectionDetailDao.findPage(jqGrid, collectionId);
	}

	/**
	 * 获取单个收款确认对象
	 * @param collectionId
	 * @return
	 */
	public CollectionConfirmation get(String collectionId) {
		return this.collectionConfirmationDao.get(collectionId);
	}

	/**
	 * 获取可退款收款确认单LIST
	 * 
	 * @param projectId
	 * @param contractId 销售合同ID
	 * @return
	 */
	public List<CollectionConfirmation> getRefundList(String projectId, String contractId) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(projectId)) {
			params.put("projectId", projectId);
		}
		if (StringUtils.isNotBlank(contractId)) {
			params.put("businessPreposeId", contractId);
		}
		// 必须已完成
		params.put("swfStatus", CollectionConfirmation.COMPLETED);
		// 未退款、退款中、部分退款完成
		params.put("refundStatus", new Integer[] { CollectionConfirmation.REFUND, CollectionConfirmation.REFUNDING, CollectionConfirmation.REFUNDED_PARTIAL });
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		orders.put("createdDate", true);
		return this.collectionConfirmationDao.queryByParam(params, null, orders);
	}

	/**
	 * 获取可退款收款确认单MAP
	 * @return
	 */
	public Map<String, String> getRefundMap(String projectId, String contractId) {
		Map<String, String> detailMap = new HashMap<String, String>();
		List<CollectionConfirmation> list = this.getRefundList(projectId, contractId);
		for (CollectionConfirmation entity : list) {
			detailMap.put(entity.getCollectionId(), "（" + entity.getCollectionNo() + "）-" + entity.getBusinessPreposeRecord());
		}
		return detailMap;
	}

	/**
	 * 从表LIST
	 * @param collectionId
	 * @return
	 */
	public List<CollectionDetail> getRefundDetailList(String collectionId) {
		List<CollectionDetail> list = new ArrayList<CollectionDetail>();
		List<CollectionDetail> detailList = this.collectionDetailDao.findBy("collectionId", collectionId);
		for (CollectionDetail detail : detailList) {
			if (detail.canRefund()) {
				list.add(detail);
			}
		}
		return list;
	}

	/**
	 * 从表Map
	 * @param collectionId
	 * @return
	 */
	public Map<String, String> getRefundDetailMap(String collectionId) {
		Map<String, String> detailMap = new HashMap<String, String>();
		List<CollectionDetail> detailList = this.getRefundDetailList(collectionId);
		for (CollectionDetail detail : detailList) {
			detailMap.put(detail.getDetailId(), detail.getCollectionClause());
		}
		return detailMap;
	}

	/**
	 * 新建收款确认对象
	 * @param user
	 * @return
	 */
	public CollectionConfirmation newCollectionConfirmation(UserProfile user) {
		CollectionConfirmation collectionConfirmation = new CollectionConfirmation();
		loadCollectionConfirmation(collectionConfirmation, user);
		// 设置为销售合同收款业务
		String code = CollectionConfirmationConstant.ORDER_COLLECTION;
		String business = CollectionConfirmationConstant.getCollectionBusinessMap().get(code);
		collectionConfirmation.setCollectionBusinessCode(code);
		collectionConfirmation.setCollectionBusiness(business);
		collectionConfirmation.setSwfStatus(CollectionConfirmation.DRAFT);
		collectionConfirmation.setIsOtherPay(false);
		return collectionConfirmation;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public CollectionConfirmation loadCollectionConfirmation(CollectionConfirmation collectionConfirmation, UserProfile user) {
		collectionConfirmation.setCreatedBy(user.getName());
		collectionConfirmation.setCreatedById(user.getPersonId());
		collectionConfirmation.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		collectionConfirmation.setModifiedBy(user.getName());
		collectionConfirmation.setModifiedById(user.getPersonId());
		collectionConfirmation.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			collectionConfirmation.setDeptName(dept.getName());
			collectionConfirmation.setDeptId(dept.getDeptId());
		}
		// 设置状态
		collectionConfirmation.setSwfStatus(CollectionConfirmation.DRAFT);
		return collectionConfirmation;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param collectionId
	 * @param user
	 * @return
	 */
	public CollectionConfirmation initEditOrViewPage(String collectionId, Map<String, Object> valueMap, UserProfile user) {
		CollectionConfirmation collectionConfirmation;
		if (StringUtils.isBlank(collectionId)) {
			collectionConfirmation = newCollectionConfirmation(user);
			collectionConfirmation.setCollectionId(UUIDHex.newId());
			if (valueMap.containsKey("contractId")) {
				String contractId = (String) valueMap.get("contractId");
				PrjContractTable contract = this.contractsercice.get(contractId);
				collectionConfirmation.setProjectId(contract.getProjectId());
				collectionConfirmation.setProjectName(contract.getProjectName());
				collectionConfirmation.setBusinessPreposeId(contract.getContractId());
				collectionConfirmation.setBusinessPreposeRecord("（" + contract.getContractNo() + "）" + contract.getContractName());
			}
			collectionConfirmation.setIsOtherPay(false);
			collectionConfirmation.setAdd(true);			
		} else {
			collectionConfirmation = (CollectionConfirmation) this.collectionConfirmationDao.get(collectionId);
			collectionConfirmation.setAdd(false);
		}
		return collectionConfirmation;
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param collectionConfirmation
	 * @param valueMap
	 * @return
	 */
	public CollectionConfirmation saveOrUpdate(UserProfile user, CollectionConfirmation collectionConfirmation, Map<String, Object> valueMap) {
		if (collectionConfirmation.getAdd()) {
			// 装配基础信息
			loadCollectionConfirmation(collectionConfirmation, user);
			// 设置收款单号
			collectionConfirmation.setCollectionNo(generatorCollectionNo());
			this.collectionConfirmationDao.save(collectionConfirmation);
		} else {
			// 设置更新时间
			collectionConfirmation.setModifiedBy(user.getName());
			collectionConfirmation.setModifiedById(user.getPersonId());
			collectionConfirmation.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			// 删除从表
			deleteDetails(collectionConfirmation.getDelDetailIds());
			this.collectionConfirmationDao.update(collectionConfirmation);
		}
		// 保存或更新从表
		List<CollectionDetail> detailList = collectionConfirmation.getDetailList();
		if (CollectionUtils.isNotEmpty(detailList)) {
			saveOrUpdateDetailList(collectionConfirmation.getCollectionId(), detailList);
		}
		return collectionConfirmation;
	}

	/**
	 * 保存从表
	 * @param collectionId
	 * @param detailList
	 */
	public void saveOrUpdateDetailList(String collectionId, List<CollectionDetail> detailList) {
		for (CollectionDetail detail : detailList) {
			detail.setCollectionId(collectionId);
			if (StringUtils.isBlank(detail.getDetailId()) || detail.getAdd()) {
				this.collectionDetailDao.save(detail);
			} else {
				this.collectionDetailDao.update(detail);
			}
		}
	}

	/**
	 * 保存提交
	 * @param user
	 * @param collectionConfirmation
	 * @param valueMap
	 * @return
	 */
	public CollectionConfirmation commit(UserProfile user, CollectionConfirmation collectionConfirmation, Map<String, Object> valueMap) {
		saveOrUpdate(user, collectionConfirmation, valueMap);
		// 设置项目经理
		String projectId = collectionConfirmation.getProjectId();
		RegisterProject project = this.registerProjectService.get(projectId);
		collectionConfirmation.setPrjManagerId(project.getProjectChargeId());
		collectionConfirmation.setPrjManager(project.getProjectCharge());
		this.update(collectionConfirmation);
		// 记录日志
		//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{EditApplication : " + collectionConfirmation + "}", null, null);
		return collectionConfirmation;
	}

	/**
	 * 提交流程需处理操作
	 */
	public void startProc(CollectionConfirmation collectionConfirmation) {
		// 将收款金额写入合同在途
		List<CollectionDetail> detailList = this.collectionDetailDao.findBy("collectionId", collectionConfirmation.getCollectionId());
		for (CollectionDetail detail : detailList) {
			this.contractsercice.addOnwayAmountForTerms(detail.getCollectionClauseId(), detail.getCollectionAmount());
		}
	}

	/**
	 * 中止流程处理操作
	 * @param collectionConfirmation
	 */
	public void abortProc(CollectionConfirmation collectionConfirmation) {
		// 将收款金额移除在途
		List<CollectionDetail> detailList = this.collectionDetailDao.findBy("collectionId", collectionConfirmation.getCollectionId());
		for (CollectionDetail detail : detailList) {
			this.contractsercice.removeOnwayAmountForTerms(detail.getCollectionClauseId(), detail.getCollectionAmount());
		}
	}

	/**
	 * 审批完成执行操作
	 * @param collectionConfirmation
	 */
	public void completeExam(CollectionConfirmation collectionConfirmation) {
		// 将收款明细金额转为实际收款金额并将合同金额增加实际收款
		List<CollectionDetail> detailList = this.collectionDetailDao.findBy("collectionId", collectionConfirmation.getCollectionId());
		for (CollectionDetail detail : detailList) {
			detail.setRealCollectionAmount(detail.getCollectionAmount());
			this.collectionDetailDao.update(detail);
			this.contractsercice.transferToRealReceiveAmountForTerms(detail.getCollectionClauseId(), detail.getCollectionAmount());
		}
	}

	/**
	 * 退款处理修改收款确认单退款状态
	 */
	public void updateRefundStatus(String collectionId, int refundStatus) {
		CollectionConfirmation collectionConfirmation = this.get(collectionId);
		if (collectionConfirmation != null) {
			// 1.若是退款删除置为草稿时需判断收款单是否已有退款；2.若是退款确认完成需判断收款单是否全部退款
			if (refundStatus == CollectionConfirmation.DRAFT || refundStatus == CollectionConfirmation.REFUNDED) {
				boolean isDraft = refundStatus == CollectionConfirmation.DRAFT;
				int clearSize = 0;
				List<CollectionDetail> detailList = this.collectionDetailDao.findBy("collectionId", collectionId);
				for (CollectionDetail detail : detailList) {
					// 1.置为草稿时判断实际收款金额是否等于收款金额；2.置为完成时判断实际收款金额是否等于0
					double judgeAmount = isDraft ? detail.getCollectionAmount() : 0;
					if (detail.getRealCollectionAmount() == judgeAmount) {
						clearSize++;
					}
				}
				// 1.有若干收款明细实际收款金额不等于收款金额，即已经有部分退款
				// 2.有若干收款明细实际收款金额非0，即仍有未全部退款
				if (clearSize < detailList.size()) {
					refundStatus = CollectionConfirmation.REFUNDED_PARTIAL;
				}
			}
			collectionConfirmation.setRefundStatus(refundStatus);
			this.collectionConfirmationDao.update(collectionConfirmation);
		}
	}

	/**
	 * 退款时：
	 * 给收款明细增加在途金额
	 * @param detailId
	 * @param amount
	 */
	public void addOnwayAmountForDetail(String detailId, double amount) {
		CollectionDetail entity = this.collectionDetailDao.get(detailId);
		if (entity != null) {
			entity.setOnwayAmount(BigDecimal.valueOf(entity.getOnwayAmount()).add(BigDecimal.valueOf(amount)).doubleValue());
			this.collectionDetailDao.update(entity);
		}
	}

	/**
	 * 退款时：
	 * 给收款明细移除在途金额
	 * @param detailId
	 * @param amount
	 */
	public void removeOnwayAmountForDetail(String detailId, double amount) {
		CollectionDetail entity = this.collectionDetailDao.get(detailId);
		if (entity != null) {
			entity.setOnwayAmount(BigDecimal.valueOf(entity.getOnwayAmount()).subtract(BigDecimal.valueOf(amount)).doubleValue());
			this.collectionDetailDao.update(entity);
		}
	}

	/**
	 * 退款时：
	 * 给收款明细将在途转为实际收款金额(实际收款-在途=新的实际收款金额)
	 * @param detailId
	 * @param amount
	 */
	public void transferToRealCollectionAmountForDetail(String detailId, double amount) {
		CollectionDetail entity = this.collectionDetailDao.get(detailId);
		if (entity != null) {
			entity.setOnwayAmount(BigDecimal.valueOf(entity.getOnwayAmount()).subtract(BigDecimal.valueOf(amount)).doubleValue());
			entity.setRealCollectionAmount(BigDecimal.valueOf(entity.getRealCollectionAmount()).subtract(BigDecimal.valueOf(amount)).doubleValue());
			this.collectionDetailDao.update(entity);
			// 更新合同实际收款
			this.contractsercice.transferToRealReceiveAmountForTermsByRefund(entity.getCollectionClauseId(), amount);
		}
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param collectionIds
	 */
	public void delete(UserProfile user, String collectionIds) {
		if (StringUtils.isNotBlank(collectionIds)) {
			CollectionConfirmation confirmation;
			for (String collectionId : collectionIds.split(",")) {
				confirmation = this.get(collectionId);
				if(confirmation == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, confirmation);
				this.collectionConfirmationDao.delete(confirmation);
				this.collectionDetailDao.deleteByProperty("collectionId", collectionId);
			}
		}
	}

	/**
	 * 批量删除对象
	 * @param detailIds
	 */
	public void deleteDetails(String detailIds) {
		if (StringUtils.isNotBlank(detailIds)) {
			for (String detailId : detailIds.split(",")) {
				this.collectionDetailDao.delete(detailId);
			}
		}
	}

	/**
	 * 更新对象
	 * @param useSeal
	 * @return
	 */
	public CollectionConfirmation update(CollectionConfirmation collectionConfirmation) {
		this.collectionConfirmationDao.update(collectionConfirmation);
		return collectionConfirmation;
	}

	/**
	 * 生成收款单号
	 * @return
	 */
	public synchronized String generatorCollectionNo() {
		String collectionNo;
		String NoHead = CommonUtil.format(new Date(), "yyyyMM");
		// 过滤条件
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("collectionNo", NoHead + "%");
		List<String> likeSearhNames = new ArrayList<String>();
		likeSearhNames.add("collectionNo");
		Integer count = this.collectionConfirmationDao.find(params, likeSearhNames, null).size();
		String NoEnd = String.format("%03d", count + 1);
		collectionNo = NoHead + NoEnd;
		return collectionNo;
	}

	/**
	 * 获取收款业务
	 * @param collectionId
	 * @return
	 */
	public List<CheckBoxVO> getCollectionBusinessList(String collectionId) {
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		String choose = "";// 不设置默认
		if (collectionId.length() > 0) {
			CollectionConfirmation collectionConfirmation = this.get(collectionId);
			if (collectionConfirmation != null) {
				choose = collectionConfirmation.getCollectionBusinessCode();
			}
		}
		Map<String, String> map = CollectionConfirmationConstant.getCollectionBusinessMap();
		for (String key : map.keySet()) {
			CheckBoxVO vo = new CheckBoxVO("collectionBusinessCode_" + key, "collectionBusinessCode", key, map.get(key), key.equals(choose));
			list.add(vo);
		}
		return list;
	}

	/**
	 * 获取销售合同中可用收款条款MAP
	 * @return
	 */
	public Map<String, String> getCollectionClauseMap(String contractId) {
		Map<String, String> map = this.contractsercice.getPrjContractCollectionTermsMap(contractId);
		return map;
	}

	/**
	 * 根据合同取客户付款信息 （非第三方）
	 * @param businessPreposeId
	 * @return
	 */
	public CustomPayaccount changeBusiness(String businessPreposeId) {
		if(StringUtils.isNotBlank(businessPreposeId)) {
			PrjContractTable contract = contractsercice.getPrjContractTableByContractId(businessPreposeId);
			if(contract!=null) {
				String customId = contract.getCustomerId();
				List<CustomPayaccount> list = customPayaccountDao.findNotOtherPay(customId, "1");
				if(list!=null&&list.size()>0) {
					return list.get(0);
				}
			}
		}
		return new CustomPayaccount();
	}

	/**
	 * 取第三方账号信息
	 * @param payaccountId
	 * @return
	 */
	public CustomPayaccount changeAccount(String payaccountId) {
		if(StringUtils.isNotBlank(payaccountId)) {
			return customPayaccountDao.get(payaccountId);
		}
		return new CustomPayaccount();
	}

	/**
	 * 获取第三方银行账户码表
	 * @param businessPreposeId
	 * @return
	 */
	public Map<String, String> getPayAccountCodeTable(String businessPreposeId) {
		Map <String,String > map = new HashMap<String, String>();
		if(StringUtils.isNotBlank(businessPreposeId)) {
			PrjContractTable contract = contractsercice.getPrjContractTableByContractId(businessPreposeId);
			if(contract!=null) {
				String customId = contract.getCustomerId();
				List<CustomPayaccount> list = customPayaccountDao.findOtherPay(customId);
				if(list!=null&&list.size()>0) {
					for (CustomPayaccount customPayaccount : list) {
						map.put(customPayaccount.getPayaccountId(),customPayaccount.getCompanyname());
					}
				}
			}
		}
		return map;
	}

	/**
	 *获取收款明细
	 * @param detailId
	 * @return
	 */
	public CollectionDetail getDetail(String detailId) {
		CollectionDetail entity = collectionDetailDao.get(detailId);
		return entity;
	}
	
	/**
	 * 根据销售合同ID获取第三方单位名称
	 * @param contractId
	 * @return
	 */
	public List<String> getCompanyNames(String contractId){
		List<String> companyNames = new ArrayList<String>();
		List<CollectionConfirmation> list = this.collectionConfirmationDao.getCollectionConfirmationByContractId(contractId);
		if (list != null) {
			for (CollectionConfirmation cc : list) {
				String companyName = cc.getCompanyName();
				if (!companyNames.contains(companyName) && StringUtils.isNotBlank(companyName)) {
					companyNames.add(companyName);
				}
			}
		}
		return companyNames;
	}

}
