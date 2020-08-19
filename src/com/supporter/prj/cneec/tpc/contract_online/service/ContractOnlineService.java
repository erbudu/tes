package com.supporter.prj.cneec.tpc.contract_online.service;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.benefit_budget.constant.BenefitBudgetItemConstant;
import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.constant.LogConstant;
import com.supporter.prj.cneec.tpc.contract_online.dao.ContractCollectionTermsDao;
import com.supporter.prj.cneec.tpc.contract_online.dao.ContractContractAmountDao;
import com.supporter.prj.cneec.tpc.contract_online.dao.ContractGoodsDao;
import com.supporter.prj.cneec.tpc.contract_online.dao.ContractOnlineDao;
import com.supporter.prj.cneec.tpc.contract_online.entity.ContractCollectionTerms;
import com.supporter.prj.cneec.tpc.contract_online.entity.ContractContractAmount;
import com.supporter.prj.cneec.tpc.contract_online.entity.ContractGoods;
import com.supporter.prj.cneec.tpc.contract_online.entity.ContractOnline;
import com.supporter.prj.cneec.tpc.contract_online_prepare.entity.ContractOnlinePrepare;
import com.supporter.prj.cneec.tpc.contract_online_prepare.service.ContractOnlinePrepareService;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignAmount;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignItem2;
import com.supporter.prj.cneec.tpc.contract_sign_review.service.ContractSignReviewService;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptAmount;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptItem2;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.service.ContractSignDeptReviewService;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.util.ContractSignReviewUtil;
import com.supporter.prj.cneec.tpc.prj_contract_table.service.PrjContractTableService;
import com.supporter.prj.cneec.tpc.record_filing_manager.dao.RecordFilingManagerDao;
import com.supporter.prj.cneec.tpc.record_filing_manager.entity.RecordFilingManager;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.TpcBudgetUtil;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.account.entity.Account;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: ContractOnlineService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2017-11-06
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class ContractOnlineService {

	@Autowired
	private ContractOnlineDao contractOnlineDao;
	@Autowired
	private ContractContractAmountDao contractAmountDao;
	@Autowired
	private ContractGoodsDao goodsDao;
	@Autowired
	private ContractCollectionTermsDao collectionTermsDao;

	@Autowired
	private ContractOnlinePrepareService contractOnlinePrepareService;
	@Autowired
	private RecordFilingManagerDao filingManagerDao;
	@Autowired
	private ContractSignDeptReviewService contractSignDeptReviewService;
	@Autowired
	private ContractSignReviewService contractSignReviewService;
	@Autowired
	private PrjContractTableService contractTableService;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, ContractOnline.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param contractOnline
	 */
	public void getAuthCanExecute(UserProfile userProfile, ContractOnline contractOnline) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, ContractOnline.MODULE_ID, contractOnline.getContractId(), contractOnline);
	}

	/**
	 * 获取销售合同上线对象集合
	 * @param user
	 * @param jqGrid
	 * @param contractOnline
	 * @return
	 */
	public List<ContractOnline> getGrid(UserProfile user, JqGrid jqGrid, ContractOnline contractOnline) {
		String authFilter = getAuthFilter(user);
		return this.contractOnlineDao.findPage(jqGrid, contractOnline, authFilter);
	}

	/**
	 * 获取合同额集合
	 * @param user
	 * @param jqGrid
	 * @param contractId
	 * @return
	 */
	public List<ContractContractAmount> getContractAmountGrid(UserProfile user, JqGrid jqGrid, String contractId) {
		return this.contractAmountDao.findPage(jqGrid, contractId);
	}

	/**
	 * 获取货物及服务明细集合
	 * @param user
	 * @param jqGrid
	 * @param contractId
	 * @return
	 */
	public List<ContractGoods> getGoodsGrid(UserProfile user, JqGrid jqGrid, String contractId) {
		return this.goodsDao.findPage(jqGrid, contractId);
	}

	/**
	 * 获取收款条件集合
	 * @param user
	 * @param jqGrid
	 * @param contractId
	 * @return
	 */
	public List<ContractCollectionTerms> getCollectionTermsGrid(UserProfile user, JqGrid jqGrid, String contractId) {
		return this.collectionTermsDao.findPage(jqGrid, contractId);
	}

	/**
	 * 获取单个销售合同上线对象
	 * @param contractId
	 * @return
	 */
	public ContractOnline get(String contractId) {
		ContractOnline contractOnline = this.contractOnlineDao.get(contractId);
		List<ContractContractAmount> contractAmountList = this.contractAmountDao.findBy("contractId", contractId);
		List<ContractGoods> goodsList = this.goodsDao.findBy("contractId", contractId);
		List<ContractCollectionTerms> collectionTermsList = this.collectionTermsDao.findBy("contractId", contractId);
		if (contractOnline != null) {
			contractOnline.setContractAmountList(contractAmountList);
			contractOnline.setGoodsList(goodsList);
			contractOnline.setCollectionTermsList(collectionTermsList);
		}
		return contractOnline;
	}

	/**
	 * 币别
	 * @param orderId
	 * @return
	 */
	public List<ContractContractAmount> getContractAmountList(String contractId) {
		if (StringUtils.isNotBlank(contractId)) {
			contractId = contractId.split(",")[0];
		}
		return this.contractAmountDao.findBy("contractId", contractId);
	}

	public Map<String, String> getContractCurrencyMap(String contractId) {
		Map<String, String> amountMap = new HashMap<String, String>();
		List<ContractContractAmount> amountList = this.getContractAmountList(contractId);
		if (amountList != null) {
			for (ContractContractAmount amount : amountList) {
				amountMap.put(amount.getCurrencyId(), amount.getCurrency());
			}
		}
		return amountMap;
	}

	/**
	 * 新建销售合同上线对象
	 * @param user
	 * @return
	 */
	public ContractOnline newContractOnline(UserProfile user) {
		ContractOnline contractOnline = new ContractOnline();
		loadContractOnline(contractOnline, user);
		contractOnline.setSwfStatus(ContractOnline.DRAFT);
		return contractOnline;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public ContractOnline loadContractOnline(ContractOnline contractOnline, UserProfile user) {
		contractOnline.setCreatedBy(user.getName());
		contractOnline.setCreatedById(user.getPersonId());
		contractOnline.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		contractOnline.setModifiedBy(user.getName());
		contractOnline.setModifiedById(user.getPersonId());
		contractOnline.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			contractOnline.setDeptName(dept.getName());
			contractOnline.setDeptId(dept.getDeptId());
		}
		// 设置状态
		contractOnline.setSwfStatus(ContractOnline.DRAFT);
		return contractOnline;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param contractId
	 * @param user
	 * @return
	 */
	public ContractOnline initEditOrViewPage(String contractId, UserProfile user) {
		ContractOnline contractOnline;
		if (StringUtils.isBlank(contractId)) {
			contractOnline = newContractOnline(user);
			contractOnline.setContractId(UUIDHex.newId());
			contractOnline.setAdd(true);
		} else {
			contractOnline = (ContractOnline) this.contractOnlineDao.get(contractId);
			contractOnline.setAdd(false);
		}
		return contractOnline;
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param contractOnline
	 * @param valueMap
	 * @return
	 */
	public ContractOnline saveOrUpdate(UserProfile user, ContractOnline contractOnline, Map<String, Object> valueMap) {
		if (contractOnline.getAdd()) {
			// 装配基础信息
			loadContractOnline(contractOnline, user);
			this.contractOnlineDao.save(contractOnline);
			//保存备案合同附件
			this.saveFile(contractOnline ,user);
			updatePrepareProcess(contractOnline);
		} else {
			// 设置更新时间
			contractOnline.setModifiedBy(user.getName());
			contractOnline.setModifiedById(user.getPersonId());
			contractOnline.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			// 删除合同额
			deleteContractAmount(contractOnline.getDelAmountIds());
			// 删除货物及服务明细
			deleteGoods(contractOnline.getDelGoodsIds());
			// 删除收款条件
			deleteCollectionTerms(contractOnline.getDelTermsIds());
			this.contractOnlineDao.update(contractOnline);
		}
		// 保存或更新合同额
		List<ContractContractAmount> contractAmountList = contractOnline.getContractAmountList();
		if(CollectionUtils.isNotEmpty(contractAmountList)){
			saveOrUpdateContractAmountList(contractOnline.getContractId(), contractAmountList);
		}
		// 保存或更新货物及服务明细
		List<ContractGoods> goodsList = contractOnline.getGoodsList();
		if(CollectionUtils.isNotEmpty(goodsList)){
			saveOrUpdateGoodsList(contractOnline.getContractId(), goodsList);
		}
		// 保存或更新收款条件
		List<ContractCollectionTerms> collectionTermsList = contractOnline.getCollectionTermsList();
		if(CollectionUtils.isNotEmpty(collectionTermsList)){
			saveOrUpdateCollectionTermsList(contractOnline.getContractId(), collectionTermsList);
		}
		return contractOnline;
	}

	/**
	 * 拷贝备案附件
	 * @param contractOnline
	 * @param user
	 */
	private void saveFile(ContractOnline contractOnline, UserProfile user) {
		RecordFilingManager recordFiling = this.filingManagerDao.findUniqueResult("preformId", contractOnline.getInforId());
		if (recordFiling != null) {
			List<IFile> fileList = EIPService.getFileUploadService().getFileList(RecordFilingManager.MODULE_ID, RecordFilingManager.BUSI_TYPE_CONTRACT,
					recordFiling.getRecordFilingId());
			for (int i = 0; i < fileList.size(); i++) {
				File file = EIPService.getFileUploadService().getFile(fileList.get(i).getFileId());
				String fileName = fileList.get(i).getFileName();
				EIPService.getFileUploadService().saveFile(file, ContractOnline.MODULE_ID, ContractOnline.BUSI_TYPE, fileName, i, user, contractOnline.getContractId());
			}
		}
	}

	/**
	 * 删除合同额
	 * @param delAmountIds
	 */
	public void deleteContractAmount(String delAmountIds) {
		if (StringUtils.isNotBlank(delAmountIds)) {
			for (String amountId : delAmountIds.split(",")) {
				// 根据ID字段删除
				this.contractAmountDao.deleteByProperty("amountId", amountId);
			}
		}
	}

	/**
	 * 保存或更新合同额
	 * @param contractId
	 * @param contractAmountList
	 */
	public void saveOrUpdateContractAmountList(String contractId, List<ContractContractAmount> contractAmountList) {
		for (ContractContractAmount amount : contractAmountList) {
			amount.setContractId(contractId);
			if (StringUtils.isBlank(amount.getAmountId()) || amount.getAdd()) {
				this.contractAmountDao.save(amount);
			} else {
				this.contractAmountDao.update(amount);
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
	 * @param contractId
	 * @param goodsList
	 */
	public void saveOrUpdateGoodsList(String contractId, List<ContractGoods> goodsList) {
		for (ContractGoods goods : goodsList) {
			goods.setContractId(contractId);
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
				this.collectionTermsDao.deleteByProperty("termsId", termsId);
			}
		}
	}

	/**
	 * 保存或更新收款条件
	 * @param contractId
	 * @param collectionTermsList
	 */
	public void saveOrUpdateCollectionTermsList(String contractId, List<ContractCollectionTerms> collectionTermsList) {
		//先清除已有的明细
		List<ContractCollectionTerms> oldTerms = this.collectionTermsDao.getTermsByContractId(contractId);
		if (oldTerms != null && oldTerms.size() > 0) {
			this.collectionTermsDao.delete(oldTerms);
		}
		//保存现有的明细
		for (ContractCollectionTerms terms : collectionTermsList) {
			terms.setContractId(contractId);
			this.collectionTermsDao.save(terms);
		}
	}

	/**
	 * 保存提交
	 * @param user
	 * @param contractOnline
	 * @param valueMap
	 * @return
	 */
	public ContractOnline commit(UserProfile user, ContractOnline contractOnline, Map<String, Object> valueMap) {
		saveOrUpdate(user, contractOnline, valueMap);
		// 记录日志
		String logMessage = MessageFormat.format(LogConstant.PUBLISH_LOG_MESSAGE_CONTRACTONLINE, "(" + contractOnline.getContractNo() + ")" + contractOnline.getContractName());
		EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS_CONTRACTONLINE).info(user, LogConstant.PUBLISH_LOG_ACTION_CONTRACTONLINE, logMessage, contractOnline, null);
		return contractOnline;
	}


	/**
	 * 审批完成操作
	 * @param contractOnline
	 */
	public void completeExam(ContractOnline contractOnline) {
		// 将合同信息录入合同正式表中
		contractTableService.createTpcPrjContractTableByContractOnline(contractOnline);
		updatePrepareFinish(contractOnline);

		// 在途金额转执行
		Person person = EIPService.getEmpService().getEmp(contractOnline.getCreatedById());
		Account account = EIPService.getAccountService().getDefaultAccount(person);
		UserProfile user = EIPService.getLogonService().getUserProfile(account);
		String projectId = contractOnline.getProjectId();
		// 采购合同扣除预算即为采购合同总金额下货款（服务款）金额
		String budgetId = BenefitBudgetItemConstant.SUMMARY_PURCHASE_PAYMENT;
		List<ContractContractAmount> contractAmounts = contractOnline.getContractAmountList();
		if (contractAmounts == null || contractAmounts.size() == 0) {
			contractAmounts = this.contractAmountDao.findBy("contractId", contractOnline.getContractId());
		}
		for (ContractContractAmount contractAmount : contractAmounts) {
			this.transferToExecute(user, projectId, budgetId, contractAmount.getRmbAmount());
		}
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param contractIds
	 */
	public void delete(UserProfile user, String contractIds) {
		if (StringUtils.isNotBlank(contractIds)) {
			ContractOnline contractOnline;
			for (String contractId : contractIds.split(",")) {
				contractOnline = this.get(contractId);
				if (contractOnline == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, contractOnline);
				updatePrepareDraft(contractOnline);
				this.contractOnlineDao.delete(contractOnline);
				this.contractAmountDao.deleteByProperty("contractId", contractId);
				this.goodsDao.deleteByProperty("contractId", contractId);
				this.collectionTermsDao.deleteByProperty("contractId", contractId);
			}
		}
	}

	/**
	 * 根据ID获取付款金额
	 * @param orderId
	 * @return
	 */
	public Map<String, Double> getPayPlanAmounts(String contractId) {
		return this.collectionTermsDao.getPayPlanAmounts(contractId);
	}

	/**
	 * 流程更新对象
	 * @param useSeal
	 * @return
	 */
	public ContractOnline update(ContractOnline contractOnline) {
		this.contractOnlineDao.update(contractOnline);
		return contractOnline;
	}

	public ContractOnline initContractOnlineByInforId(String inforId, Map<String, Object> valueMap, UserProfile user) {
		ContractOnline contractOnline = this.contractOnlineDao.findUniqueResult("inforId", inforId);
		if (contractOnline == null) {
			ContractOnlinePrepare prepare = this.contractOnlinePrepareService.findUniqueResult("inforId", inforId);
			contractOnline = newContractOnline(user);
			String contractId = UUIDHex.newId();
			contractOnline.setContractId(contractId);
			contractOnline.setSwfStatus(ContractOnline.DRAFT);
			contractOnline.setAdd(true);
			contractOnline.setProjectId(prepare.getProjectId());
			contractOnline.setProjectName(prepare.getProjectName());
			contractOnline.setContractName(prepare.getContractName());
			contractOnline.setContractPartyId(prepare.getCustomerId());
			contractOnline.setContractParty(prepare.getContractParty());
			contractOnline.setContractRmbAmount(prepare.getTotalRmbAmount());
			contractOnline.setPurchaseTypeCode(prepare.getPurchaseTypeCode());
			contractOnline.setPurchaseType(prepare.getPurchaseType());
			contractOnline.setInforId(inforId);
			RecordFilingManager recordFiling = this.filingManagerDao.findUniqueResult("preformId", inforId);
			if (recordFiling != null) {
				contractOnline.setFilingId(recordFiling.getRecordFilingId());
				contractOnline.setFilingNo(recordFiling.getRecordFilingNo());
				contractOnline.setContractNo(recordFiling.getBusinessNo());
			}
			try {
				List<ContractContractAmount> contractAmountList = new ArrayList<ContractContractAmount>();
				List<ContractGoods> contractGoodsList = new ArrayList<ContractGoods>();
				ContractContractAmount contractAmount;
				ContractGoods contractGoods;
				// 根据不同评审类型取值
				if (prepare.isCompanyReview()) {
					List<ContractSignAmount> amountList = this.contractSignReviewService.getInforAmountList(inforId);
					List<ContractSignItem2> itemList = (List<ContractSignItem2>) this.contractSignReviewService.getInforItemList(inforId, ContractSignReviewUtil.INFOR_TYPE_CONTRACT);
					for (ContractSignAmount origAmount : amountList) {
						contractAmount = new ContractContractAmount();
						BeanUtils.copyProperties(contractAmount, origAmount);
						contractAmount.setAmountId(null);
						contractAmount.setAdd(true);
						contractAmount.setContractId(contractId);
						contractAmountList.add(contractAmount);
					}
					for (ContractSignItem2 origItem : itemList) {
						contractGoods = new ContractGoods();
						BeanUtils.copyProperties(contractGoods, origItem);
						contractGoods.setGoodsId(null);
						contractGoods.setAdd(true);
						contractGoods.setContractId(contractId);
						contractGoodsList.add(contractGoods);
					}
				} else {
					List<ContractSignDeptAmount> amountList = this.contractSignDeptReviewService.getInforAmountList(inforId);
					List<ContractSignDeptItem2> itemList = (List<ContractSignDeptItem2>) this.contractSignDeptReviewService.getInforItemList(inforId, ContractSignReviewUtil.INFOR_TYPE_CONTRACT);
					for (ContractSignDeptAmount origAmount : amountList) {
						contractAmount = new ContractContractAmount();
						BeanUtils.copyProperties(contractAmount, origAmount);
						contractAmount.setAmountId(null);
						contractAmount.setAdd(true);
						contractAmount.setContractId(contractId);
						contractAmountList.add(contractAmount);
					}
					for (ContractSignDeptItem2 origItem : itemList) {
						contractGoods = new ContractGoods();
						BeanUtils.copyProperties(contractGoods, origItem);
						contractGoods.setGoodsId(null);
						contractGoods.setAdd(true);
						contractGoods.setContractId(contractId);
						contractGoodsList.add(contractGoods);
					}
				}
				contractOnline.setContractAmountList(contractAmountList);
				contractOnline.setGoodsList(contractGoodsList);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			saveOrUpdate(user, contractOnline, null);
			contractOnline.setAdd(false);
		}
		return contractOnline;
	}


	/**
	 * 删除操作设置前置单为草稿
	 */
	public void updatePrepareDraft(ContractOnline contractOnline) {
		// 删除记录后将前置单合同上线状态置为默认状态
		String inforId = CommonUtil.trim(contractOnline.getInforId());
		this.contractOnlinePrepareService.updateStatusByInforIds(inforId, "onlineStatus", ContractOnlinePrepare.ONLINE_DRAFT);
	}

	/**
	 * 保存记录后设置前置单为进行中
	 */
	public void updatePrepareProcess(ContractOnline contractOnline) {
		// 生成记录后将前置单合同上线状态置为进行中状态
		String inforId = CommonUtil.trim(contractOnline.getInforId());
		this.contractOnlinePrepareService.updateStatusByInforIds(inforId, "onlineStatus", ContractOnlinePrepare.ONLINE_PROCESS);
	}

	/**
	 * 审批完成设置前置单为完成状态
	 */
	public void updatePrepareFinish(ContractOnline contractOnline) {
		// 记录审批完成后将前置单合同上线状态置为已完成状态
		String inforId = CommonUtil.trim(contractOnline.getInforId());
		this.contractOnlinePrepareService.updateStatusByInforIds(inforId, "onlineStatus", ContractOnlinePrepare.ONLINE_FINISH);
	}

	/**
	 * 合同审批完成金额转执行
	 *
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void transferToExecute(UserProfile user, String projectId, String budgetId, double amount) {
		TpcBudgetUtil.transferToExecute(TpcBudgetUtil.TPC_PURCHASE_CONTRACT_ONLINE, user, projectId, budgetId, amount);
	}

	/** 以下是选择采购合同方法 **/

	public ListPage<ContractOnline> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<ContractOnline> listPage = new ListPage<ContractOnline>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.contractOnlineDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}

}
