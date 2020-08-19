package com.supporter.prj.cneec.tpc.contract_change.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.benefit_budget.constant.BenefitBudgetItemConstant;
import com.supporter.prj.cneec.tpc.collection_confirmation.entity.CollectionDetail;
import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.contract_change.dao.ContractChangeCollectionTermsDao;
import com.supporter.prj.cneec.tpc.contract_change.dao.ContractChangeContractAmountDao;
import com.supporter.prj.cneec.tpc.contract_change.dao.ContractChangeDao;
import com.supporter.prj.cneec.tpc.contract_change.dao.ContractChangeGoodsChildDao;
import com.supporter.prj.cneec.tpc.contract_change.dao.ContractChangeGoodsDao;
import com.supporter.prj.cneec.tpc.contract_change.dao.ContractOrderDao;
import com.supporter.prj.cneec.tpc.contract_change.dao.ContractSealDao;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChange;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChangeCollectionTerms;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChangeContractAmount;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChangeGoods;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChangeGoodsChild;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractOrder;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractSeal;
import com.supporter.prj.cneec.tpc.contract_change.util.ContractChangeConstant;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.dao.ContractSignDeptItem1Dao;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.dao.ContractSignDeptItem2Dao;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptItem1;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptItem2;
import com.supporter.prj.cneec.tpc.contract_stamp_tax_amount.service.ContractStampTaxAmountService;
import com.supporter.prj.cneec.tpc.order_change.entity.ContractChOrder;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderChangeCollectionTerms;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderChangeContractAmount;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.dao.PrjContractSettlementRecDao;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.entity.PrjContractSettlementRec;
import com.supporter.prj.cneec.tpc.prj_contract_table.dao.PrjContractAmountDao;
import com.supporter.prj.cneec.tpc.prj_contract_table.dao.PrjContractCollectionTermsDao;
import com.supporter.prj.cneec.tpc.prj_contract_table.dao.PrjContractGoodsDao;
import com.supporter.prj.cneec.tpc.prj_contract_table.dao.PrjContractTableDao;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractAmount;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractCollectionTerms;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractGoods;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractTable;
import com.supporter.prj.cneec.tpc.prj_contract_table.service.PrjContractTableService;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.cneec.tpc.util.TpcBudgetUtil;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileManageService;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.eip.todo.entity.Todo;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.account.entity.Account;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: ContractChangeService
 * @Description: 业务操作类
 */
@Service
@Transactional(TransManager.APP)
public class ContractChangeService {
	@Autowired
	private ContractSealDao contractSealDao;
	@Autowired
	private ContractChangeDao contractChangeDao;  
	@Autowired
	private ContractChangeContractAmountDao amountDao;
	@Autowired
	private ContractChangeGoodsDao goodsDao;
	@Autowired
	private ContractChangeGoodsChildDao goodsChildDao;
	@Autowired
	private ContractChangeCollectionTermsDao termsDao;
	@Autowired
	private PrjContractTableDao prjContractTableDao;
	@Autowired
	private PrjContractTableService prjContractTableService;
	@Autowired
	private PrjContractAmountDao prjContractAmountDao;
	@Autowired
	private PrjContractGoodsDao prjContractGoodsDao;
	@Autowired
	private ContractSignDeptItem1Dao contractSignDeptItem1Dao;// 采购合同明细
	@Autowired
	private PrjContractSettlementRecDao prjContractSettlementRecDao;
	@Autowired
	private PrjContractCollectionTermsDao prjContractCollectionTermsDao;
	@Autowired
	private ContractOrderDao contractOrderDao;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private FileManageService fileManageService;
	@Autowired
	private ContractStampTaxAmountService contractStampTaxAmountService;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, ContractChange.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param contractChange
	 */
//	public void getAuthCanExecute(UserProfile userProfile, ContractOrder contractOrder) {
//		// 权限验证
//		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, ContractChange.MODULE_ID, contractOrder.getChId(), contractOrder);
//	}

	/**
	 * 获取采购合同变更对象集合
	 * @param user
	 * @param jqGrid
	 * @param contractChange
	 * @return
	 */
	public List<ContractChange> getGrid(UserProfile user, JqGrid jqGrid,ContractChange contractChange, String contractId) {
		String authFilter = getAuthFilter(user);
		return this.contractChangeDao.findPage(jqGrid, contractChange, contractId, authFilter);
	}

	/**
	 * 获取合同额集合
	 * @param user
	 * @param jqGrid
	 * @param changeId
	 * @return
	 */
	public List<ContractChangeContractAmount> getContractAmountGrid(UserProfile user, JqGrid jqGrid, String chId) {
		return this.amountDao.findPage(jqGrid, chId);
	}

	/**
	 * 获取货物及服务明细集合
	 * @param user
	 * @param jqGrid
	 * @param changeId
	 * @return
	 */
	public List<ContractChangeGoods> getGoodsGrid(UserProfile user, JqGrid jqGrid, String chId) {
		return this.goodsDao.findPage(jqGrid, chId);
	}

	public List<ContractChangeGoods> getGoodsGridSingle(UserProfile user, JqGrid jqGrid, String goodsId) {
		return this.goodsDao.getGoodsGridSingle(jqGrid, goodsId);
	}
	
	public List<ContractChangeGoodsChild> getGoodsChildGrid(UserProfile user, JqGrid jqGrid, String goodsId) {
		return this.goodsChildDao.findPage(jqGrid, goodsId);
	}
	/**
	 * 获取收款条件集合
	 * @param user
	 * @param jqGrid
	 * @param changeId
	 * @return
	 */
	public List<ContractChangeCollectionTerms> getCollectionTermsGrid(UserProfile user, JqGrid jqGrid, String chId) {
		return this.termsDao.findPage(jqGrid, chId);
	}

	/**
	 * 获取单个采购合同变更对象
	 * @param changeId
	 * @return
	 */
	public ContractChange get(String changeId) {
		return this.contractChangeDao.get(changeId);
	}

	/**
	 * 新建采购合同变更对象
	 * @param user
	 * @return
	 */
	public ContractOrder newContractChange(UserProfile user) {
		ContractOrder contractOrder = new ContractOrder();
		loadContractOrder(contractOrder, user);
		contractOrder.setSwfStatus(ContractOrder.DRAFT);
		return contractOrder;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public ContractOrder loadContractOrder(ContractOrder contractOrder, UserProfile user) {
		contractOrder.setCreatedBy(user.getName());
		contractOrder.setCreatedById(user.getPersonId());
		contractOrder.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		contractOrder.setModifiedBy(user.getName());
		contractOrder.setModifiedById(user.getPersonId());
		contractOrder.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			contractOrder.setDeptName(dept.getName());
			contractOrder.setDeptId(dept.getDeptId());
		}
		// 设置状态
		contractOrder.setSwfStatus(ContractChange.DRAFT);
		return contractOrder;
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param contractOrder
	 * @param valueMap
	 * @return
	 */
	public ContractOrder saveOrUpdate(UserProfile user, ContractOrder contractOrder, Map<String, Object> valueMap) {
		if (contractOrder.getAdd()) {
			// 装配基础信息
			loadContractOrder(contractOrder, user);
			// 设置流水号
//			contractChange.setSerialNumber(generatorSerialNumber());
			this.contractOrderDao.save(contractOrder);
		} else {
			// 设置更新时间
			contractOrder.setModifiedBy(user.getName());
			contractOrder.setModifiedById(user.getPersonId());
			contractOrder.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.contractOrderDao.update(contractOrder);
		
		// 保存或更新合同额
		List<ContractChangeContractAmount> contractAmountList = contractOrder.getContractAmountList();
		if(CollectionUtils.isNotEmpty(contractAmountList)){
			saveOrUpdateContractAmountList(contractOrder.getChId(), contractAmountList);
		}
		// 删除合同额
		deleteContractAmount(contractOrder.getDelAmountIds());
		// 保存或更新货物及服务明细
		List<ContractChangeGoods> goodsList = contractOrder.getGoodsList();
		if(CollectionUtils.isNotEmpty(goodsList)){
			saveOrUpdateGoodsList(contractOrder.getChId(), goodsList);
		}
		// 删除货物及服务明细
		deleteGoods(contractOrder.getDelGoodsIds());
		// 保存或更新收款条件
		List<ContractChangeCollectionTerms> collectionTermsList = contractOrder.getCollectionTermsList();
		if(CollectionUtils.isNotEmpty(collectionTermsList)){
			saveOrUpdateCollectionTermsList(contractOrder.getChId(), collectionTermsList);
		}
		// 删除收款条件
	     deleteCollectionTerms(contractOrder.getDelTermsIds());
		}
		return contractOrder;
	}

	/**
	 * 删除合同额
	 * @param delAmountIds
	 */
	public void deleteContractAmount(String delAmountIds) {
		if (StringUtils.isNotBlank(delAmountIds)) {
			for (String amountId : delAmountIds.split(",")) {
				// 根据ID字段删除（删除主键方法有问题）
				this.amountDao.deleteByProperty("amountId", amountId);
			}
		}
	}

	/**
	 * 保存或更新合同额
	 * @param changeId
	 * @param contractAmountList
	 */
	public void saveOrUpdateContractAmountList(String chId, List<ContractChangeContractAmount> contractAmountList) {
		for (ContractChangeContractAmount amount : contractAmountList) {
			amount.setChId(chId);
			if (StringUtils.isBlank(amount.getAmountId()) || amount.getAdd()) {
				this.amountDao.save(amount);
			} else {
				this.amountDao.update(amount);
			}
		}
	}

	/**
	 * 删除货物及服务明细
	 * @param delGoodsIds
	 */
	public void deleteGoods(String delGoodsIds) {
		if (StringUtils.isNotBlank(delGoodsIds)) {
			for (String goodsId : delGoodsIds.split(",")) {
				this.goodsDao.deleteByProperty("goodsId", goodsId);
			}
		}
	}

	/**
	 * 保存或更新货物及服务明细
	 * @param changeId
	 * @param goodsList
	 */
	public void saveOrUpdateGoodsList(String chId, List<ContractChangeGoods> goodsList) {
		for (ContractChangeGoods goods : goodsList) {
			goods.setChId(chId);
			goods.setCurrency(TpcConstant.getCommonCurrencyMap().get(goods.getCurrencyId()));
			if (StringUtils.isBlank(goods.getGoodsId()) || goods.getAdd()) {
				this.goodsDao.save(goods);
			} else {
				this.goodsDao.update(goods);
			}
		}
	}

	/**
	 * 删除收款条件
	 * @param delTermsIds
	 */
	public void deleteCollectionTerms(String delTermsIds) {
		if (StringUtils.isNotBlank(delTermsIds)) {
			for (String termsId : delTermsIds.split(",")) {
				this.termsDao.deleteByProperty("termsId", termsId);
			}
		}
	}

	/**
	 * 保存或更新收款条件
	 * @param changeId
	 * @param collectionTermsList
	 */
	public void saveOrUpdateCollectionTermsList(String chId, List<ContractChangeCollectionTerms> collectionTermsList) {
		for (ContractChangeCollectionTerms terms : collectionTermsList) {
			terms.setChId(chId);
			if (StringUtils.isBlank(terms.getTermsId()) || terms.getAdd()) {
				this.termsDao.save(terms);
			} else {
				this.termsDao.update(terms);
			}
		}
	}

	/**
	 * 保存提交
	 * @param user
	 * @param contractChange
	 * @param valueMap
	 * @return
	 */
//	public ContractChange commit(UserProfile user, ContractChange contractChange) {
//		saveChangeOrder(user, contractChange);
//		// 记录日志
//		String logMessage = MessageFormat.format(LogConstant.PUBLISH_LOG_MESSAGE_CONTRACTCHANGE, "(" + contractChange.getContractNo() + ")" + contractChange.getContractName());
//		EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS_CONTRACTCHANGE).info(user, LogConstant.PUBLISH_LOG_ACTION_CONTRACTCHANGE, logMessage, contractChange, null);
//		return contractChange;
//	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param changeIds
	 */
	public void delete(UserProfile user, String changeIds) {
		ContractChange contractChange = contractChangeDao.get(changeIds);
		contractChangeDao.delete(contractChange);
	}
//	public void delete(UserProfile user, String changeIds) {
//		if (StringUtils.isNotBlank(changeIds)) {
//			ContractChange contractChange;
//			for (String changeId : changeIds.split(",")) {
//				contractChange = this.contractChangeDao.get(changeId);
//				if (contractChange == null) continue;
//				// 权限验证
//				this.getAuthCanExecute(user, contractChange);
//				// 修改合同状态为原始状态
//				PrjContractTable contract = this.prjContractTableService.get(contractChange.getContractId());
//				this.prjContractTableService.updateToChange(contract, false, user);
//				this.contractChangeDao.delete(contractChange);
//				this.amountDao.deleteByProperty("changeId", changeId);
//				this.goodsDao.deleteByProperty("changeId", changeId);
//				this.termsDao.deleteByProperty("changeId", changeId);
//			}
//		}
//	}

	/**
	 * 根据ID获取付款金额
	 * @param orderId
	 * @return
	 */
	public Map<String, Double> getPayPlanAmounts(String contractId) {
		return this.termsDao.getPayPlanAmounts(contractId);
	}

	/**
	 * 流程更新对象
	 * @param DerivativeChange
	 * @return
	 */
	public ContractChange update(ContractChange contractChange) {
		if (contractChange==null){
			return contractChange;
		}
		this.contractChangeDao.update(contractChange);
		return contractChange;
	}
	
	/**
	 * 流程更新对象
	 * @param Seal
	 * @return
	 */
	public ContractSeal update(ContractSeal contractSeal) {
		if (contractSeal==null){
			return contractSeal;
		}
		this.contractSealDao.update(contractSeal);
		return contractSeal;
	}
	/**
	 * 流程更新对象
	 * @param 
	 * @return
	 */
	public ContractOrder update(ContractOrder contractOrder) {
		if (contractOrder==null){
			return contractOrder;
		}
		this.contractOrderDao.update(contractOrder);
		return contractOrder;
	}
	
	/**
	 * 调整性质单/复选框
	 * @param projectId
	 * @return
	 */
	public List<CheckBoxVO> getAdjustmentNatureList(String changeId,String chType) {
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		String[] arr = new String[] {};
		if (StringUtils.isNotBlank(changeId)) {
			ContractChange contractChange = this.get(changeId);
			if (contractChange != null) {
				arr = CommonUtil.trim(contractChange.getAdjustmentNatureId()).split(",");
			}
		}
		if(StringUtils.isNotBlank(chType)) {
			Map<Integer, String> map = ContractChangeConstant.getAdjustmentNatureMap(Integer.parseInt(chType));
			for (Integer key : map.keySet()) {
				CheckBoxVO vo = new CheckBoxVO("adjustmentNatureId_" + key, "adjustmentNatureId", key.toString(), map.get(key), existByLoop(arr, key.toString()));
				for (String string : arr) {
					if(string.equals(String.valueOf(key))) {
						vo.setChecked(true);
					}
				}
				list.add(vo);
			}
		}
		return list;
	}

	/**
	 * 是否有协议
	 * @param changeId
	 * @return
	 */
//	public List<CheckBoxVO> getHasProtocolList(String changeId) {
//		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
//		String choose = "";// 不设置默认
//		if (changeId.length() > 0) {
//			ContractChange contractChange = this.get(changeId);
//			if (contractChange != null) {
//				choose = contractChange.isHasProtocol() ? "true" : "false";
//			}
//		}
//		Map<String, String> map = ContractChangeConstant.getHasProtocolMap();
//		for (String key : map.keySet()) {
//			CheckBoxVO vo = new CheckBoxVO("hasProtocol_" + key, "hasProtocol", key, map.get(key), key.equals(choose));
//			vo.setChecked(true);
//			list.add(vo);
//		}
//		return list;
//	}

	/**
	 * 判断字符串数组是否包含特定值
	 * @param arr
	 * @param tarVal
	 * @return
	 */
	public static boolean existByLoop(String[] arr, String tarVal) {
		for (String s : arr) {
			if (s.equals(tarVal)) return true;
		}
		return false;
	}

	/** 以下是选择采购合同变更方法 **/

	public ListPage<ContractChange> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<ContractChange> listPage = new ListPage<ContractChange>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.contractChangeDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}

	/**
	 * 保存或更新
	 * 
	 */
	@Transactional(TransManager.APP)
	public ContractChange saveChangeOrder(UserProfile user,  ContractChange contractChange) {
		if (user != null) {
			contractChange.setModifiedBy(user.getName());
			contractChange.setModifiedById(user.getPersonId());
		}
		//更新调整性质
		Map<Integer, String> map = ContractChangeConstant.getAdjustmentNatureMap(contractChange.getChType());
		String adjustmentNatureIds = contractChange.getAdjustmentNatureId();
		if(StringUtils.isNotBlank(adjustmentNatureIds)) {
			String adjustmentNature = "";
			for (String adjustmentNatureId : adjustmentNatureIds.split(",")) {
				adjustmentNature += map.get(CommonUtil.parseInt(adjustmentNatureId))+",";
			}
			if(adjustmentNature.length()>0) {
				adjustmentNature = adjustmentNature.substring(0,adjustmentNature.length()-1);
				contractChange.setAdjustmentNature(adjustmentNature);
			}
		}
		if (contractChange.getIsNew()) {
			if (user != null) {
				contractChange.setCreatedBy(user.getName());
				contractChange.setCreatedById(user.getPersonId());
				Dept dept = user.getDept();
				if (dept != null) {
					contractChange.setDeptId(dept.getDeptId());
					contractChange.setDeptName(dept.getName());
				}
			}
			contractChange.setCreatedDate(new Date());
			contractChange.setIsNew(false);
			contractChangeDao.save(contractChange);
		} else { // 设置更新时间
			contractChange.setModifiedDate(new Date());
			contractChangeDao.update(contractChange);
		}
		return contractChange;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 */
	public ContractChange initChangeOrder(String changeId, String contractId, int chType, UserProfile user) {
		ContractChange contractChange = null;
		if (StringUtils.isNotBlank(changeId)) {
			contractChange = (ContractChange) this.contractChangeDao.get(changeId);
		}
		
		if (contractChange == null) {
			contractChange = new ContractChange();
			if (StringUtils.isBlank(changeId)) {
				changeId = UUIDHex.newId();
			} 
			contractChange.setChangeId(changeId);
			contractChange.setChType(chType);
			contractChange.setIsNew(true);
			contractChange.setSwfStatus(ContractChange.DRAFT);
			
			PrjContractTable contract = prjContractTableService.get(contractId);
			if (contract != null) {
				contractChange.setContractId(contract.getContractId());
				contractChange.setContractName(contract.getContractName());
				contractChange.setContractNo(contract.getContractNo());
				contractChange.setProjectId(contract.getProjectId());
				contractChange.setProjectName(contract.getProjectName());
				contractChange.setDeptId(contract.getDeptId());
				contractChange.setDeptName(contract.getDeptName());
				contractChange.setCreatedDate(new Date());
				contractChange.setPurchaseTypeCode(contract.getPurchaseTypeCode());
				contractChange.setPurchaseType(contract.getPurchaseType());
			    System.out.println("1111"+contract.getPurchaseTypeCode());
				if(!CommonUtil.isNullOrEmpty(contract.getContractAmountList())) {
					double amount = 0 ;
					for (PrjContractAmount om : contract.getContractAmountList()) {
						amount += om.getRmbAmount();
					}
					contractChange.setOnlineRmbAmount(amount);
				}
				/*if(!CommonUtil.isNullOrEmpty(contract.getContractAmountList())) {
					contractChange.setOnlineRmbAmount(contract.getContractAmountList().get(0).getRmbAmount());
				}*/
			}
			contractChange.setCreatedBy(user.getName());		
			contractChange.setCreatedById(user.getPersonId());	
		} else {
			contractChange.setIsNew(false);
			contractChange.setModifiedById(user.getName());
			contractChange.setModifiedBy(user.getPersonId());
		}
		
		return contractChange;
	}
    //变更单审批查看
	public ContractChange initOrder(String changeId) {
		return contractChangeDao.get(changeId);
	}
	//用印审批查看
	public ContractSeal initSeal(String sealId) {
		return contractSealDao.get(sealId);
	}
	
	//用印审批查看
	public ContractSeal getContractSealByChangeId(String changeId) {
		return contractSealDao.getSealByChangeId(changeId);
	}
	//合同导航查看
		public ContractOrder getContractOrderByChangeId(String changeId) {
			return contractOrderDao.getOrderByChangeId(changeId);
		}
	//合同审批查看
	public ContractOrder initContract(String chId) {
		return contractOrderDao.get(chId);
	}
	
	public ContractSeal initSealPage(String changeId, UserProfile user) {
		ContractSeal contractSeal = null;
		if (StringUtils.isNotBlank(changeId)) {
			contractSeal = contractSealDao.getSealByChangeId(changeId);
			if (contractSeal != null) {
				contractSeal.setModifiedById(user.getName());
				contractSeal.setModifiedBy(user.getPersonId());
			} else {
				ContractChange change  = contractChangeDao.get(changeId);
				int chType = change.getChType();
				if(change != null) {
					contractSeal = new ContractSeal();
					contractSeal.setSealId(UUIDHex.newId());
					contractSeal.setProjectId(change.getProjectId());
					contractSeal.setProjectName(change.getProjectName());
					contractSeal.setContractId(change.getContractId());
					contractSeal.setContractNo(change.getContractNo());
					contractSeal.setContractName(change.getContractName());
					contractSeal.setDeptId(change.getDeptId());
					contractSeal.setDeptName(change.getDeptName());
					contractSeal.setCreatedDate(new Date());
					//用印初始化生成协议编号//生成新的合同编号
					if(chType == 1) {
					String businessNo;
					String NoHead = change.getContractNo();
					String Con = "e";
					String NoCon = NoHead+Con;
					Integer count = this.contractOrderDao.getBusinessNoIndex(NoCon);
					String NoEnd = String.format("%03d", count);
					businessNo = NoCon + NoEnd;
					contractSeal.setBusinessNo(businessNo);
					}else if(chType == 2){
						int hasProtocol = change.getHasProtocol();
						if(hasProtocol == 1) {
							String businessNo;
							String NoHead = change.getContractNo();
							String Con = "e";
							String NoCon = NoHead+Con;
							Integer count = this.contractOrderDao.getBusinessNoIndex(NoCon);
							String NoEnd = String.format("%03d", count);
							businessNo = NoCon + NoEnd;
							contractSeal.setBusinessNo(businessNo);	
						}
					}
				}
				contractSeal.setChangeId(changeId);
				contractSeal.setSwfStatus(ContractSeal.DRAFT);
				contractSeal.setIsNew(true);
				contractSeal.setCreatedBy(user.getName());		
				contractSeal.setCreatedById(user.getPersonId());	
//				if(user.getDept() != null){
//					contractSeal.setCreatedBy(user.getDept().getName());
//					contractSeal.setCreatedById(user.getDeptId());
//				}
			}
		}
		return contractSeal;
	}
	//保存用印
	public ContractSeal saveChangeSeal(UserProfile user, ContractSeal contractSeal) {
		if (user != null) {
			contractSeal.setModifiedBy(user.getName());
			contractSeal.setModifiedById(user.getPersonId());
		}
		if (contractSeal.getIsNew()) {
			if (user != null) {
				contractSeal.setCreatedBy(user.getName());
				contractSeal.setCreatedById(user.getPersonId());
				Dept dept = user.getDept();
				if (dept != null) {
					contractSeal.setDeptId(dept.getDeptId());
					contractSeal.setDeptName(dept.getName());
				}
			}
			contractSeal.setCreatedDate(new Date());
			contractSeal.setIsNew(false);
			contractSealDao.save(contractSeal);
		} else { // 设置更新时间
			contractSeal.setModifiedDate(new Date());
			contractSealDao.update(contractSeal);
		}
		return contractSeal;
	}

	public ContractSeal getContractSealBySealId(String sealId) {
		return this.contractSealDao.get(sealId);
	}
	public ContractOrder getContractOrderByChId(String chId) {
		return this.contractOrderDao.get(chId);
	}
	/**
	 * 为编辑或查看页面初始化对象
	 * @param changeId
	 * @param user
	 * @return
	 */
	public ContractOrder initEditOrViewPage(String changeId, UserProfile user) {
		ContractOrder contractOrder = null;
		if (StringUtils.isNotBlank(changeId)) {
			contractOrder = this.contractOrderDao.getOrderByChangeId(changeId);
			if (contractOrder != null) {
				contractOrder.setModifiedById(user.getName());
				contractOrder.setModifiedBy(user.getPersonId());
			} else {
				ContractChange change = (ContractChange) this.contractChangeDao.get(changeId);

				String contractId = change.getContractId();

				PrjContractTable contract = (PrjContractTable) this.prjContractTableDao.get(contractId);
				if (contract == null)
					return null;
				try {
					contractOrder = new ContractOrder();
					org.apache.commons.beanutils.BeanUtils.copyProperties(contractOrder, contract);

					contractOrder.setChId(UUIDHex.newId());
					contractOrder.setChangeId(changeId);

					contractOrder.setSwfStatus(Integer.valueOf(0));

					contractOrder.setCreatedById(user.getPersonId());
					contractOrder.setCreatedBy(user.getName());
					contractOrder.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd"));
					contractOrder.setTelephone(change.getTelephone());
					contractOrder.setBusinessNo(change.getBusinessNo());
					this.contractOrderDao.save(contractOrder);

					// 设置合同额
					List<PrjContractAmount> amountList = prjContractAmountDao.getByContractId(contractId);
					if (amountList != null) {
						for (PrjContractAmount amount : amountList) {
							if (amount == null) {
								continue;
							}
							ContractChangeContractAmount changeAmount = new ContractChangeContractAmount();
							BeanUtils.copyProperties(changeAmount, amount);
							changeAmount.setAmountId(com.supporter.util.UUIDHex.newId());
							changeAmount.setAmountOldId(amount.getAmountId());
							changeAmount.setChId(contractOrder.getChId());
							// 保存
							amountDao.save(changeAmount);
						}
					}

					// 设置货物及服务明细
					List<PrjContractGoods> goodsList = prjContractGoodsDao.getByContractId(contractId);
					if (goodsList != null) {
						for (PrjContractGoods goods : goodsList) {
							if (goods == null) {
								continue;
							}
							ContractChangeGoods changeGoods = new ContractChangeGoods();
							BeanUtils.copyProperties(changeGoods, goods);
							changeGoods.setGoodsId(com.supporter.util.UUIDHex.newId());
							changeGoods.setGoodsOldId(goods.getGoodsId());
							changeGoods.setChId(contractOrder.getChId());
							// 保存
							goodsDao.save(changeGoods);
						}
					}

					// 设置收款条件
					List<PrjContractCollectionTerms> termsList = prjContractCollectionTermsDao
							.getByContractId(contractId);
					if (termsList != null) {
						for (PrjContractCollectionTerms terms : termsList) {
							if (terms == null) {
								continue;
							}
							ContractChangeCollectionTerms changeterms = new ContractChangeCollectionTerms();
							BeanUtils.copyProperties(changeterms, terms);
							changeterms.setTermsId(com.supporter.util.UUIDHex.newId());
							changeterms.setTermsOldId(terms.getTermsId());
							changeterms.setChId(contractOrder.getChId());
							// 保存
							termsDao.save(changeterms);
						}
					}

					File("TPCPRJCONTAB", "file", contract.getContractId(), "", contractOrder.getChId(), "", "file",
							user);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				return contractOrder;
			}
		}
		return contractOrder;
	}
	  public void File(String moduleName, String busiType, String oneLevelId, String twoLevelId, String newOneId, String newTwoId, String newType, UserProfile user)
	  {
	  List<FileUpload> list =  fileUploadService.getList(moduleName, busiType, oneLevelId, twoLevelId);
	    if ((list != null) && (list.size() > 0))
	      for (FileUpload fileUpload : list) {
	        if (fileUpload == null) {
	          continue;
	        }
	        FileUpload file = new FileUpload();
	        try {
	          String newFileId = UUIDHex.newId();
	          org.apache.commons.beanutils.BeanUtils.copyProperties(file, fileUpload);
	          file.setModuleName(ContractOrder.MODULE_ID);
	          file.setCreatedDate(fileUpload.getCreatedDate());
	          file.setFileId(newFileId);
	          file.setOneLevelId(newOneId);
	          file.setTwoLevelId(newTwoId);
	          file.setBusiType(newType);
	          file.setFileSize(Integer.valueOf(fileUpload.getFileSize()));
	          this.fileUploadService.save(file);
	          String pathOld = this.fileManageService.getStoragePath(fileUpload.getTenantNo(), fileUpload.getModuleName(), fileUpload.getBusiType(), fileUpload.getCreatedDate());
	          String root = pathOld + File.separator;
	          File sourceFile = new File(root, fileUpload.getFileId());

	          String path = this.fileManageService.getStoragePath(file.getTenantNo(), file.getModuleName(), file.getBusiType(), file.getCreatedDate());

	          File fileM = new File(path);
	          fileM.mkdirs();

	          File newFile = new File(path, newFileId);

	          copy(sourceFile, newFile);
	        }
	        catch (IllegalAccessException e)
	        {
	          e.printStackTrace();
	        }
	        catch (InvocationTargetException e) {
	          e.printStackTrace();
	        }
	      }
	  }

		public void copy(File src, File dst) {
			try {
				InputStream in = null;
				OutputStream out = null;
				try {
					in = new BufferedInputStream(new FileInputStream(src), 16 * 1024);
					out = new BufferedOutputStream(new FileOutputStream(dst), 16 * 1024);
					byte[] buffer = new byte[16 * 1024];
					while (in.read(buffer) > 0) {
						out.write(buffer);
					}
				} finally {
					if (null != in) {
						in.close();
					}
					if (null != out) {
						out.close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	public String checkOrderChange(String contractId) {
		return this.contractChangeDao.checkOrderChange(contractId);
	}

	public boolean checkContractChOrder(String changeId) {
		return this.contractOrderDao.checkContractChOrder(changeId);
	}
	
	/**
	 * 检查预算可用金额
	 * @param contractChange
	 * @return true 有可用预算，false 无可用预算
	 */
	public synchronized String checkBudgetAvailableAmount(ContractChange contractChange){
		//增减类型
		String changeTypeId = contractChange.getChangeTypeId();
		if(changeTypeId.endsWith("ADD")){
			String projectId = contractChange.getProjectId();
			//增加金额
			double changeAmount = contractChange.getChangeAmount();
			// 采购合同扣除预算即为采购合同总金额下货款（服务款）金额
			String budgetId = BenefitBudgetItemConstant.SUMMARY_PURCHASE_PAYMENT;
			//可用预算
			Double budgetAvailableAmoun = TpcBudgetUtil.getBudgetAvailableAmount(projectId, budgetId);
			//可用预算大于增加金额
			if(budgetAvailableAmoun >= changeAmount){
				return "true";
			}else{
				return budgetAvailableAmoun.toString();
			}
		}else{
			return "true";
		}
	}
	
	/**
	 * 流程提交后调用(合同调整单及变更单提交)
	 * @param user
	 * @param contractChange
	 */
	public synchronized void WsfSubmitAferExcuteByContractChange(UserProfile user,ContractChange contractChange){
		String changeTypeId = contractChange.getChangeTypeId();
		String projectId = contractChange.getProjectId();
		// 采购合同扣除预算即为采购合同总金额下货款（服务款）金额
		String budgetId = BenefitBudgetItemConstant.SUMMARY_PURCHASE_PAYMENT;
		if(changeTypeId.endsWith("ADD")){
			double amount = contractChange.getChangeAmount();
			this.addOnwayBudgetAmount(user, projectId, budgetId, amount);
		}else{
			//如果变更金额不变或是减额不用处理
		}
	}
	
	/**
	 * 添加在途金额(提交调整单或时提交变更后执行)
	 *
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void addOnwayBudgetAmount(UserProfile user, String projectId, String budgetId, double amount) {
		TpcBudgetUtil.addOnwayBudgetAmount(TpcBudgetUtil.TPC_CONTRACT_CHANGE, user, projectId, budgetId, amount);
	}
	
	/**
	 * 流程中止后调用(合同调整单及变更单流程中止调用)
	 * @param user
	 * @param contractChange
	 */
	public synchronized void WsfAbortAferExcuteByContractChange(ContractChange contractChange){
		//增减类型
		String changeTypeId = contractChange.getChangeTypeId();
		String projectId = contractChange.getProjectId();
		// 采购合同扣除预算即为采购合同总金额下货款（服务款）金额
		String budgetId = BenefitBudgetItemConstant.SUMMARY_PURCHASE_PAYMENT;
		if(changeTypeId.endsWith("ADD")){
			double amount = contractChange.getChangeAmount();
			Person person = EIPService.getEmpService().getEmp(contractChange.getCreatedById());
			Account account = EIPService.getAccountService().getDefaultAccount(person);
			UserProfile user = EIPService.getLogonService().getUserProfile(account);
			this.removeOnwayBudgetAmount(user, projectId, budgetId, amount);
		}else{
			//如果变更金额不变或是减额不用处理
		}
	}
	/**
	 * 解锁在途金额
	 *
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void removeOnwayBudgetAmount(UserProfile user, String projectId, String budgetId, double amount) {
		TpcBudgetUtil.removeOnwayBudgetAmount(TpcBudgetUtil.TPC_CONTRACT_CHANGE, user, projectId, budgetId, amount);
	}
	
	/**
	 * 合同变更上线审批完成后，调整预算项目预算的差值。
	 * @param derivativeConChange
	 */
	public void adjustPrjBenefitBudget(ContractOrder contractOrder){
		//需要调整的预算金额
		double adjustPrjBenfiBudgetAmount  = getAdjustPrjBenfiBudgetAmount(contractOrder);
		//adjustPrjBenfiBudgetAmount 不等于零才需要调整金额.
		if(adjustPrjBenfiBudgetAmount != 0 ){
			//项目ID
			String projectId = contractOrder.getProjectId();
			// 采购合同扣除预算即为采购合同总金额下货款（服务款）金额
			String budgetId = BenefitBudgetItemConstant.SUMMARY_PURCHASE_PAYMENT;
			// 在途金额转执行
			Person person = EIPService.getEmpService().getEmp(contractOrder.getCreatedById());
			Account account = EIPService.getAccountService().getDefaultAccount(person);
			UserProfile user = EIPService.getLogonService().getUserProfile(account);
			//注意衍生合同变更申请时如果金额时正数要锁在途。
			if(adjustPrjBenfiBudgetAmount > 0){
				//转执行
				this.transferToExecute(user, projectId, budgetId, adjustPrjBenfiBudgetAmount);
			}else{
				//负数时取绝对值.
				double  adjustPrjBenfiBudgetAmount_ABS= Math.abs(adjustPrjBenfiBudgetAmount);
				//扣减执行值（注意：如何合同变更额度调减不需要锁预算，合同上线完成后，直接扣减执行预算并增加可用预算即可）
				this.removeExecuteBudgetAmount(user, projectId, budgetId, adjustPrjBenfiBudgetAmount_ABS);
			}
		}
	}
	
	/**
	 * 合同上线完成后扣减执行预算。
	 *
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void removeExecuteBudgetAmount(UserProfile user, String projectId, String budgetId, double amount) {
		TpcBudgetUtil.removeExecuteBudgetAmount(TpcBudgetUtil.TPC_CONTRACT_CHANGE_ONLINE, user, projectId, budgetId, amount);
	}
	
	/**
	 * 本次变更需要调整预算的差额。 
	 * 如果返回金额为正数：解锁途金额，转执行。
	 * 如何返回金额为负数：直接扣减预算执行数值。
	 * @param derivativeConChange
	 * @return
	 */
	public double getAdjustPrjBenfiBudgetAmount(ContractOrder contractOrder){
		String contractId = contractOrder.getContractId();
		PrjContractTable prjContractTable = prjContractTableService.get(contractId);
		//衍生合同折合人民币金额。
		double  prjContractTableRmbAmount= prjContractTable.getContractRmbAmount();
		//衍生合同变更后折合人民币金额
		double  conChangeRbmAmount= contractOrder.getContractRmbAmount();
		//如果两个金额不相等代表合同金额发生的变更，则需要调整预算
		if(prjContractTableRmbAmount != conChangeRbmAmount){
			return conChangeRbmAmount - prjContractTableRmbAmount;
		}else{
			return 0;
		}
	}
	
	/**
	 * 合同变更上线完成金额转执行
	 *
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void transferToExecute(UserProfile user, String projectId, String budgetId, double amount) {
		TpcBudgetUtil.transferToExecute(TpcBudgetUtil.TPC_CONTRACT_CHANGE_ONLINE, user, projectId, budgetId, amount);
	}
	
	public void saveFirstDetail(ContractChangeGoods entity) {
		if (entity == null) {
			return;
		}
		String goodsId = entity.getGoodsId();
		//
		ContractChangeGoods dbGoods = this.goodsDao.get(goodsId);
		if(dbGoods!=null) {
			ModuleUtils.copyPropertiesExcludeNullProperty(entity, dbGoods);
			//更新币别中文
			dbGoods.setCurrency(TpcConstant.getCommonCurrencyMap().get(dbGoods.getCurrencyId()));
			this.goodsDao.update(dbGoods);
		}else {
			//更新币别中文
			entity.setCurrency(TpcConstant.getCommonCurrencyMap().get(entity.getCurrencyId()));
			this.goodsDao.save(entity);
		}
		
	}
	public void saveTwoDetail(ContractChangeGoods entity) {
		if (entity == null) {
			return;
		}
		goodsDao.update(entity);
		List<ContractChangeGoodsChild> child = entity.getGooodsChild();
		if(CollectionUtils.isNotEmpty(child)) {
			saveOrUpdateGoodsChildList(entity.getGoodsId(), child);
		}
		deleteGoodsTerms(entity.getDelTermsIds());
	}
	
	public void saveOrUpdateGoodsChildList(String goodsId, List<ContractChangeGoodsChild> goodsChildList) {
		for (ContractChangeGoodsChild goods : goodsChildList) {
			goods.setGoodsId(goodsId);
			goods.setCurrency(TpcConstant.getCommonCurrencyMap().get(goods.getCurrencyId()));
			if (StringUtils.isBlank(goods.getGoodsId()) || goods.getAdd()) {
				this.goodsChildDao.save(goods);
			} else {
				this.goodsChildDao.update(goods);
			}
		}
	}
	
	public void deleteGoodsTerms(String delTermsIds) {
		if (StringUtils.isNotBlank(delTermsIds)) {
			for (String termsId : delTermsIds.split(",")) {
				this.goodsChildDao.deleteByProperty("childId", termsId);
			}
		}
	}
	//流程结束操作
		public void change(UserProfile user, ContractOrder contractChOrder) {
	        //变更合同主键chId
			String chId = contractChOrder.getChId();
			//覆盖原合同online的主键是contractId
			String contractId = contractChOrder.getContractId();
			PrjContractTable prjContractTable = prjContractTableService.get(contractId);
			// 复制合同上线信息至合同正式表
			try {
				//创建印花税
				this.contractStampTaxAmountService.completedContractChange(contractChOrder, prjContractTable);
				
				BeanUtils.copyProperties(prjContractTable, contractChOrder);
				this.prjContractTableDao.update(prjContractTable);
				//金额
				// 原合同old
				List<PrjContractAmount> oldAmountList = prjContractAmountDao.findBy("contractId", contractId);
				// 变更合同new
				List<ContractChangeContractAmount> newAmountList = amountDao.findBy("chId", chId);
				int newAmountSize = newAmountList.size();
				for (int i = 0; i < newAmountSize; i++) {
					String[] ignores = { "amountId", "chId" };
					ContractChangeContractAmount newContent = newAmountList.get(i);
					PrjContractAmount oldContent = findByRecId(oldAmountList, newContent.getAmountOldId());
					if (oldContent != null) {
						// 更新
						com.supporter.prj.pm.util.BeanUtils.copyProperties(newContent, oldContent, ignores);
						prjContractAmountDao.update(oldContent);
						// 从原表中删除
						oldAmountList.remove(oldContent);
					} else {
						// 新增
						oldContent = new PrjContractAmount();
						com.supporter.prj.pm.util.BeanUtils.copyProperties(newContent, oldContent, ignores);
						oldContent.setAmountId(null);
						oldContent.setContractId(contractId);
						prjContractAmountDao.save(oldContent);
					}
				}
				if (oldAmountList.size() > 0) {
					prjContractAmountDao.delete(oldAmountList);
				}
				//terms表增删改
				//原合同old
				List<PrjContractCollectionTerms> oldTermsList = prjContractCollectionTermsDao.findBy("contractId", contractId);
				//变更合同new
				List<ContractChangeCollectionTerms> newTermsList = termsDao.findBy("chId", chId);
				int newSize = newTermsList.size();
				for (int i = 0; i < newSize; i++) {
					String[] ignores = {"termsId", "chId"};
					ContractChangeCollectionTerms newTerms = newTermsList.get(i);
					PrjContractCollectionTerms oldTerms = findBySiteId(oldTermsList,newTerms.getTermsOldId());
					if(oldTerms != null) {
						//更新
						com.supporter.prj.pm.util.BeanUtils.copyProperties(newTerms, oldTerms, ignores);
						prjContractCollectionTermsDao.update(oldTerms);
						//从原表中删除
						oldTermsList.remove(oldTerms);
					}else {
						//新增
						oldTerms = new PrjContractCollectionTerms();
						com.supporter.prj.pm.util.BeanUtils.copyProperties(newTerms, oldTerms, ignores);
						oldTerms.setContractId(contractId);
						prjContractCollectionTermsDao.save(oldTerms);
					}
					
				}
				if (oldTermsList.size() > 0) {
					prjContractCollectionTermsDao.delete(oldTermsList);
				}
				
				//Goods
				// 原合同old
				List<PrjContractGoods> oldGoodsList = prjContractGoodsDao.findBy("contractId", contractId);
				// 变更合同new
				List<ContractChangeGoods> newGoodsList = goodsDao.findBy("chId", chId);
				int newGoodsSize = newGoodsList.size();
				for (int i = 0; i < newGoodsSize; i++) {
					String[] ignores = {"goodsId","chId"};
					ContractChangeGoods newGoods = newGoodsList.get(i);
					PrjContractGoods oldGoods = findByGoodsId(oldGoodsList, newGoods.getGoodsOldId());
					if (oldGoods != null) {
						// 更新
						com.supporter.prj.pm.util.BeanUtils.copyProperties(newGoods, oldGoods, ignores);
						prjContractGoodsDao.update(oldGoods);
						// 从原表中删除
						oldGoodsList.remove(oldGoods);
					} else {
						// 新增
						oldGoods = new PrjContractGoods();
						com.supporter.prj.pm.util.BeanUtils.copyProperties(newGoods, oldGoods, ignores);
						oldGoods.setGoodsId(null);
						oldGoods.setContractId(contractId);
						prjContractGoodsDao.save(oldGoods);
					}
				}
				if (oldGoodsList.size() > 0) {
					prjContractGoodsDao.delete(oldGoodsList);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		private PrjContractCollectionTerms findBySiteId(List<PrjContractCollectionTerms> oldTermsList, String termsOldId) {
			if (StringUtils.isNotBlank(termsOldId)) {
			int len = oldTermsList.size();
			for (int i = 0; i < len; i++) {
				PrjContractCollectionTerms oldSite = oldTermsList.get(i);
				if (termsOldId.equals(oldSite.getTermsId())) {
					return oldSite;
				}
			}
			}
			return null;
		}
		
		private PrjContractAmount findByRecId(List<PrjContractAmount> oldAmountList, String amountOldId) {
			if (StringUtils.isNotBlank(amountOldId)) {
			int len = oldAmountList.size();
			for (int i = 0; i < len; i++) {
				PrjContractAmount oldContent = oldAmountList.get(i);
				if (amountOldId.equals(oldContent.getAmountId())) {
					return oldContent;
				}
			}
			}
			return null;
		}
		
		private PrjContractGoods findByGoodsId(List<PrjContractGoods> oldGoodsList, String GoodsOldId) {
			if (StringUtils.isNotBlank(GoodsOldId)) {
			int len = oldGoodsList.size();
			for (int i = 0; i < len; i++) {
				PrjContractGoods oldGoods = oldGoodsList.get(i);
				if (GoodsOldId.equals(oldGoods.getGoodsId())) {
					return oldGoods;
				}
			}
			}
			return null;
		}

		public ContractSeal saveStamp(String sealId, String stampPersonId, String stampPerson, Date stampDate) {
			// TODO Auto-generated method stub
			ContractSeal contractSeal= (ContractSeal) this.contractSealDao.get(sealId);
			contractSeal.setStampDate(stampDate);
			contractSeal.setStampPerson(stampPerson);
			contractSeal.setStampPersonId(stampPersonId);
			this.contractSealDao.update(contractSeal);
			return contractSeal;
		}

		public void callPaper(String chId) {
			if (StringUtils.isNotBlank(chId)) {
				ContractOrder contractChOrder = contractOrderDao.get(chId);	
				if(contractChOrder != null && StringUtils.isNotBlank(contractChOrder.getCreatedById())) {
					Message messageCreat =new Todo();
					messageCreat.setPersonId(contractChOrder.getCreatedById());
					messageCreat.setEventTitle("请及时送达纸质版合同：" + contractChOrder.getContractName());
					messageCreat.setNotifyTime(new Date());
					messageCreat.setWebPageURL("/tpc/contract_change/contract_callPaperView.html?chId="
							+ CommonUtil.trim(chId));
					messageCreat.setMessageType(ITodo.MsgType.CC);
					messageCreat.setRelatedRecordTable(EIPService.getWebappService().getWebappName());
					EIPService.getBMSService().addMessage(messageCreat);
				}
			}else {
				System.out.println("ContractOrder 未找到经办人！");
			}
		}

	/**
	 * 保存更新明细集合
	 * 
	 * @param valueMap
	 */
	public String saveOrUpdateItem(Map<String, Object> valueMap) {
		if (valueMap == null || valueMap.isEmpty()) {
			return "";
		}
		String itemIds = (String)valueMap.get("itemIds");
		String chId = (String)valueMap.get("chId");
		if(StringUtils.isNotBlank(itemIds)) {
			for (String itemId : itemIds.split(",")) {
				ContractSignDeptItem1 rebuildItem = this.contractSignDeptItem1Dao.get(itemId);
				ContractChangeGoods goods = new ContractChangeGoods();
				goods.setChId(chId);
				goods.setItemName(rebuildItem.getItemName());
				goods.setSpecification(rebuildItem.getSpecification());
				goods.setNum(rebuildItem.getNum());
				goods.setUnit(rebuildItem.getUnit());
				goods.setHsCode(rebuildItem.getHsCode());
				goods.setCountry(rebuildItem.getCountry());
				goods.setCountryId(rebuildItem.getCountryId());
				goods.setManufacturer(rebuildItem.getManufacturer());
				goods.setCurrency(rebuildItem.getCurrency());
				goods.setCurrencyId(rebuildItem.getCurrencyId());
				goods.setAmount(rebuildItem.getAmount());
				goods.setRmbAmount(rebuildItem.getRmbAmount());
				goods.setTaxRebateRate(rebuildItem.getTaxRebateRate());
				this.goodsDao.save(goods);
				return goods.getGoodsId();
			}
		}
		return "";
	}
	//判断采购合同付款条款能不能被删除
	public String canDelTent(String termsId) {
		//通过termsId获取变更付款内容的termsOldId
		ContractChangeCollectionTerms termsC = termsDao.get(termsId);
		String termsOldId = termsC.getTermsOldId();
		//通过termsOldId获取付款条款列表
		List<PrjContractSettlementRec> materialContentList = prjContractSettlementRecDao.findBy("paymentTermsId",termsOldId);
		if(materialContentList != null) {
			for(PrjContractSettlementRec maList : materialContentList) {
				if(maList.getPaymentTermsId().equals(termsOldId)) {
					return "materError";
				}
			}
		}
		return "success";
	}

	public boolean saveDetail(ContractOrder order) {
		List<ContractChangeContractAmount> contractAmountList = order.getContractAmountList();
		// 保存或更新合同额
		if(CollectionUtils.isNotEmpty(contractAmountList)){
			for (ContractChangeContractAmount amount : contractAmountList) {
				amount.setCurrency(TpcConstant.getCommonCurrencyMap().get(amount.getCurrencyId()));
				if (StringUtils.isBlank(amount.getAmountId()) || amount.getAdd()) {
					this.amountDao.save(amount);
				} else {
					this.amountDao.update(amount);
				}
			}
			return true;
		}
		return false;
	}
		
}

