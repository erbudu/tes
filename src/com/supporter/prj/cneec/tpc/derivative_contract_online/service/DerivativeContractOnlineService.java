package com.supporter.prj.cneec.tpc.derivative_contract_online.service;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.constant.LogConstant;
import com.supporter.prj.cneec.tpc.contract_online_prepare.entity.ContractOnlinePrepare;
import com.supporter.prj.cneec.tpc.contract_online_prepare.service.ContractOnlinePrepareService;
import com.supporter.prj.cneec.tpc.derivative_contract.entity.DerivativeContract;
import com.supporter.prj.cneec.tpc.derivative_contract.service.DerivativeContractService;
import com.supporter.prj.cneec.tpc.derivative_contract_online.dao.DerivativeContractCollectionTermsDao;
import com.supporter.prj.cneec.tpc.derivative_contract_online.dao.DerivativeContractContractAmountDao;
import com.supporter.prj.cneec.tpc.derivative_contract_online.dao.DerivativeContractGoodsDao;
import com.supporter.prj.cneec.tpc.derivative_contract_online.dao.DerivativeContractOnlineDao;
import com.supporter.prj.cneec.tpc.derivative_contract_online.entity.DerivativeContractCollectionTerms;
import com.supporter.prj.cneec.tpc.derivative_contract_online.entity.DerivativeContractContractAmount;
import com.supporter.prj.cneec.tpc.derivative_contract_online.entity.DerivativeContractGoods;
import com.supporter.prj.cneec.tpc.derivative_contract_online.entity.DerivativeContractOnline;
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
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
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
public class DerivativeContractOnlineService {

	@Autowired
	private DerivativeContractOnlineDao contractOnlineDao;
	@Autowired
	private DerivativeContractContractAmountDao contractAmountDao;
	@Autowired
	private DerivativeContractGoodsDao goodsDao;
	@Autowired
	private DerivativeContractCollectionTermsDao collectionTermsDao;

	@Autowired
	private ContractOnlinePrepareService contractOnlinePrepareService;
	@Autowired
	private DerivativeContractService derivativeContractService;;
	@Autowired
	private RecordFilingManagerDao filingManagerDao;
	@Autowired
	private PrjContractTableService contractTableService;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, DerivativeContractOnline.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param contractOnline
	 */
	public void getAuthCanExecute(UserProfile userProfile, DerivativeContractOnline contractOnline) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, DerivativeContractOnline.MODULE_ID, contractOnline.getContractId(), contractOnline);
	}

	/**
	 * 获取销售合同上线对象集合
	 * @param user
	 * @param jqGrid
	 * @param contractOnline
	 * @return
	 */
	public List<DerivativeContractOnline> getGrid(UserProfile user, JqGrid jqGrid, DerivativeContractOnline contractOnline) {
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
	public List<DerivativeContractContractAmount> getContractAmountGrid(UserProfile user, JqGrid jqGrid, String contractId) {
		return this.contractAmountDao.findPage(jqGrid, contractId);
	}

	/**
	 * 获取货物及服务明细集合
	 * @param user
	 * @param jqGrid
	 * @param contractId
	 * @return
	 */
	public List<DerivativeContractGoods> getGoodsGrid(UserProfile user, JqGrid jqGrid, String contractId) {
		return this.goodsDao.findPage(jqGrid, contractId);
	}

	/**
	 * 获取收款条件集合
	 * @param user
	 * @param jqGrid
	 * @param contractId
	 * @return
	 */
	public List<DerivativeContractCollectionTerms> getCollectionTermsGrid(UserProfile user, JqGrid jqGrid, String contractId) {
		return this.collectionTermsDao.findPage(jqGrid, contractId);
	}

	/**
	 * 获取单个销售合同上线对象
	 * @param contractId
	 * @return
	 */
	public DerivativeContractOnline get(String contractId) {
		DerivativeContractOnline contractOnline = this.contractOnlineDao.get(contractId);
		List<DerivativeContractContractAmount> contractAmountList = this.contractAmountDao.findBy("contractId", contractId);
		List<DerivativeContractGoods> goodsList = this.goodsDao.findBy("contractId", contractId);
		List<DerivativeContractCollectionTerms> collectionTermsList = this.collectionTermsDao.findBy("contractId", contractId);
		if (contractOnline != null) {
			contractOnline.setContractAmountList(contractAmountList);
			contractOnline.setGoodsList(goodsList);
			contractOnline.setCollectionTermsList(collectionTermsList);
		}
		return contractOnline;
	}

	/**
	 * 新建销售合同上线对象
	 * @param user
	 * @return
	 */
	public DerivativeContractOnline newContractOnline(UserProfile user) {
		DerivativeContractOnline contractOnline = new DerivativeContractOnline();
		loadContractOnline(contractOnline, user);
		contractOnline.setSwfStatus(DerivativeContractOnline.DRAFT);
		return contractOnline;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public DerivativeContractOnline loadContractOnline(DerivativeContractOnline contractOnline, UserProfile user) {
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
		contractOnline.setSwfStatus(DerivativeContractOnline.DRAFT);
		return contractOnline;
	}

	/**
	 * 进入新建、编辑或查看页面需要加载的信息
	 * @param recordFilingId
	 * @param user
	 * @return
	 */
	public DerivativeContractOnline initEditPageByPrepareId(String prepareId, UserProfile user) {
		ContractOnlinePrepare prepare = contractOnlinePrepareService.get(prepareId);
		DerivativeContract contract = this.derivativeContractService.get(prepare.getDerivativeId());
		DerivativeContractOnline contractOnline = newContractOnline(user);
		String contractId = UUIDHex.newId();
		contractOnline.setContractId(contractId);
		contractOnline.setSwfStatus(RecordFilingManager.DRAFT);
		contractOnline.setAdd(true);
		contractOnline.setProjectId(contract.getProjectId());
		contractOnline.setProjectName(contract.getProjectName());
		contractOnline.setSaleContractId(contract.getSaleContractId());
		contractOnline.setSaleContractNo(contract.getSaleContractNo());
		contractOnline.setSaleContractName(contract.getSaleContractName());
		contractOnline.setPaymentType(contract.getPaymentType());
		double rate = contract.getExecuteRate();
		contractOnline.setContractRmbAmount(contract.getPaymentAmount() * rate);
		contractOnline.setPreformId(contract.getDerivativeId());
		RecordFilingManager recordFiling = this.filingManagerDao.findUniqueResult("preformId", contractOnline.getPreformId());
		if (recordFiling != null) {
			contractOnline.setFilingId(recordFiling.getRecordFilingId());
			contractOnline.setFilingNo(recordFiling.getRecordFilingNo());
			contractOnline.setContractNo(recordFiling.getBusinessNo());
		}
		List<DerivativeContractContractAmount> contractAmountList = new ArrayList<DerivativeContractContractAmount>();
		DerivativeContractContractAmount contractAmount = new DerivativeContractContractAmount();
		contractAmount.setAdd(true);
		contractAmount.setContractId(contractId);
		contractAmount.setCurrency(contract.getCurrency());
		contractAmount.setCurrencyId(contract.getCurrencyId());
		contractAmount.setOriginalAmount(contract.getPaymentAmount());
		contractAmount.setRate(rate);
		contractAmount.setRmbAmount(contract.getPaymentAmount() * rate);
		contractAmountList.add(contractAmount);
		contractOnline.setContractAmountList(contractAmountList);
		saveOrUpdate(user, contractOnline, null);
		contractOnline.setAdd(false);
		return contractOnline;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param contractId
	 * @param user
	 * @return
	 */
	public DerivativeContractOnline initEditOrViewPage(String contractId, UserProfile user) {
		DerivativeContractOnline contractOnline;
		if (StringUtils.isBlank(contractId)) {
			contractOnline = newContractOnline(user);
			contractOnline.setContractId(UUIDHex.newId());
			contractOnline.setAdd(true);
		} else {
			contractOnline = (DerivativeContractOnline) this.contractOnlineDao.get(contractId);
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
	public DerivativeContractOnline saveOrUpdate(UserProfile user, DerivativeContractOnline contractOnline, Map<String, Object> valueMap) {
		if (contractOnline.getAdd()) {
			// 装配基础信息
			loadContractOnline(contractOnline, user);
			this.contractOnlineDao.save(contractOnline);
			//保存用印/备案合同附件
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
		List<DerivativeContractContractAmount> contractAmountList = contractOnline.getContractAmountList();
		if(CollectionUtils.isNotEmpty(contractAmountList)){
			saveOrUpdateContractAmountList(contractOnline.getContractId(), contractAmountList);
		}
		// 保存或更新货物及服务明细
		List<DerivativeContractGoods> goodsList = contractOnline.getGoodsList();
		if(CollectionUtils.isNotEmpty(goodsList)){
			saveOrUpdateGoodsList(contractOnline.getContractId(), goodsList);
		}
		// 保存或更新收款条件
		List<DerivativeContractCollectionTerms> collectionTermsList = contractOnline.getCollectionTermsList();
		if(CollectionUtils.isNotEmpty(collectionTermsList)){
			saveOrUpdateCollectionTermsList(contractOnline.getContractId(), collectionTermsList);
		}
		return contractOnline;
	}

	/**
	 * 将用印/备案的附件保存到衍生合同上线的附件中
	 * @param contractOnline
	 */
	private void saveFile(DerivativeContractOnline contractOnline, UserProfile user) {
		RecordFilingManager recordFiling = this.filingManagerDao.findUniqueResult("preformId", contractOnline.getPreformId());
		if (recordFiling != null) {
			List<IFile> fileList = EIPService.getFileUploadService().getFileList(RecordFilingManager.MODULE_ID, RecordFilingManager.BUSI_TYPE_CONTRACT,
					recordFiling.getRecordFilingId());
			for (int i = 0; i < fileList.size(); i++) {
				File file = EIPService.getFileUploadService().getFile(fileList.get(i).getFileId());
				String fileName = fileList.get(i).getFileName();
				EIPService.getFileUploadService().saveFile(file, DerivativeContractOnline.MODULE_ID, DerivativeContractOnline.BUSI_TYPE, fileName, i, user,
						contractOnline.getContractId());
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
	public void saveOrUpdateContractAmountList(String contractId, List<DerivativeContractContractAmount> contractAmountList) {
		for (DerivativeContractContractAmount amount : contractAmountList) {
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
	public void saveOrUpdateGoodsList(String contractId, List<DerivativeContractGoods> goodsList) {
		for (DerivativeContractGoods goods : goodsList) {
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
	public void saveOrUpdateCollectionTermsList(String contractId, List<DerivativeContractCollectionTerms> collectionTermsList) {
		for (DerivativeContractCollectionTerms terms : collectionTermsList) {
			terms.setContractId(contractId);
			if (StringUtils.isBlank(terms.getTermsId()) || terms.getAdd()) {
				this.collectionTermsDao.save(terms);
			} else {
				this.collectionTermsDao.update(terms);
			}
		}
	}

	/**
	 * 保存提交
	 * @param user
	 * @param contractOnline
	 * @param valueMap
	 * @return
	 */
	public DerivativeContractOnline commit(UserProfile user, DerivativeContractOnline contractOnline, Map<String, Object> valueMap) {
		saveOrUpdate(user, contractOnline, valueMap);
		// 记录日志
		String logMessage = MessageFormat.format(LogConstant.PUBLISH_LOG_MESSAGE_DERIVATIVECONTRACTONLINE, "(" + contractOnline.getContractNo() + ")" + contractOnline.getContractName());
		EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS_DERIVATIVECONTRACTONLINE).info(user, LogConstant.PUBLISH_LOG_ACTION_DERIVATIVECONTRACTONLINE, logMessage, contractOnline, null);
		return contractOnline;
	}

	/**
	 * 审批完成操作
	 * @param contractOnline
	 */
	public void completeExam(DerivativeContractOnline contractOnline) {
		// 将合同信息录入合同正式表中
		this.contractTableService.createTpcPrjContractTableByDerivativeContractOnline(contractOnline);
		// 修改合同前置表的上线状态，为已上线。
		updatePrepareFinish(contractOnline);

		// 在途金额转执行
		Person person = EIPService.getEmpService().getEmp(contractOnline.getCreatedById());
		Account account = EIPService.getAccountService().getDefaultAccount(person);
		UserProfile user = EIPService.getLogonService().getUserProfile(account);
		String projectId = contractOnline.getProjectId();
		// 衍生合同扣除预算即为费用合计下衍生合同付款类型
		String budgetId = contractOnline.getPaymentType();
		List<DerivativeContractContractAmount> contractAmounts = contractOnline.getContractAmountList();
		if (contractAmounts == null || contractAmounts.size() == 0) {
			contractAmounts = this.contractAmountDao.findBy("contractId", contractOnline.getContractId());
		}
		for (DerivativeContractContractAmount contractAmount : contractAmounts) {
			this.transferToExecute(user, projectId, budgetId, contractAmount.getRmbAmount());
		}
	}

	/**
	 * 删除操作设置前置单为草稿
	 */
	public void updatePrepareDraft(DerivativeContractOnline contractOnline) {
		// 删除记录后将前置单合同上线状态置为默认状态
		String preformId = CommonUtil.trim(contractOnline.getPreformId());
		this.contractOnlinePrepareService.updateStatusByDerivativeId(preformId, "onlineStatus", ContractOnlinePrepare.ONLINE_DRAFT);
	}

	/**
	 * 保存记录后设置前置单为进行中
	 */
	public void updatePrepareProcess(DerivativeContractOnline contractOnline) {
		// 生成记录后将前置单合同上线状态置为进行中状态
		String preformId = CommonUtil.trim(contractOnline.getPreformId());
		this.contractOnlinePrepareService.updateStatusByDerivativeId(preformId, "onlineStatus", ContractOnlinePrepare.ONLINE_PROCESS);
	}

	/**
	 * 审批完成设置前置单为完成状态
	 */
	public void updatePrepareFinish(DerivativeContractOnline contractOnline) {
		// 记录审批完成后将前置单合同上线状态置为已完成状态
		String preformId = CommonUtil.trim(contractOnline.getPreformId());
		this.contractOnlinePrepareService.updateStatusByDerivativeId(preformId, "onlineStatus", ContractOnlinePrepare.ONLINE_FINISH);
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param contractIds
	 */
	public void delete(UserProfile user, String contractIds) {
		if (StringUtils.isNotBlank(contractIds)) {
			DerivativeContractOnline contractOnline;
			for (String contractId : contractIds.split(",")) {
				contractOnline = this.get(contractId);
				if (contractOnline == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, contractOnline);
				// 重置前置单状态
				updatePrepareDraft(contractOnline);
				//先删除相关附件
				deleteFile(contractOnline);
				this.contractOnlineDao.delete(contractOnline);
				this.contractAmountDao.deleteByProperty("contractId", contractId);
				this.goodsDao.deleteByProperty("contractId", contractId);
				this.collectionTermsDao.deleteByProperty("contractId", contractId);
			}
		}
	}

	/**
	 * 删除相关附件
	 * @param recordFilingId
	 */
	public void deleteFile(DerivativeContractOnline contractOnline){
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList(DerivativeContractOnline.MODULE_ID, DerivativeContractOnline.BUSI_TYPE, contractOnline.getContractId());
		if (CollectionUtils.isNotEmpty(list)){
			for(IFile file:list){
				fileUploadService.deleteFile(file.getFileId());
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
	public DerivativeContractOnline update(DerivativeContractOnline contractOnline) {
		this.contractOnlineDao.update(contractOnline);
		return contractOnline;
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
		TpcBudgetUtil.transferToExecute(TpcBudgetUtil.TPC_DERIVATIVE_CONTRACT_ONLINE, user, projectId, budgetId, amount);
	}

	/** 以下是选择采购合同方法 **/

	public ListPage<DerivativeContractOnline> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<DerivativeContractOnline> listPage = new ListPage<DerivativeContractOnline>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.contractOnlineDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}

}
