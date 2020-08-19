package com.supporter.prj.cneec.tpc.order_online.service;

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
import com.supporter.prj.cneec.tpc.benefit_budget.service.BenefitBudgetService;
import com.supporter.prj.cneec.tpc.benefit_note.entity.BenefitNote;
import com.supporter.prj.cneec.tpc.benefit_note.entity.BenefitNoteBudget;
import com.supporter.prj.cneec.tpc.benefit_note.service.BenefitNoteService;
import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.constant.LogConstant;
import com.supporter.prj.cneec.tpc.contract_online_prepare.entity.ContractOnlinePrepare;
import com.supporter.prj.cneec.tpc.contract_online_prepare.service.ContractOnlinePrepareService;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignAmount;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignItem1;
import com.supporter.prj.cneec.tpc.contract_sign_review.service.ContractSignReviewService;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptAmount;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptInfor;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptItem1;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.service.ContractSignDeptReviewService;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.util.ContractSignReviewUtil;
import com.supporter.prj.cneec.tpc.order_online.dao.OrderCollectionTermsDao;
import com.supporter.prj.cneec.tpc.order_online.dao.OrderContractAmountDao;
import com.supporter.prj.cneec.tpc.order_online.dao.OrderGoodsDao;
import com.supporter.prj.cneec.tpc.order_online.dao.OrderOnlineDao;
import com.supporter.prj.cneec.tpc.order_online.entity.OrderCollectionTerms;
import com.supporter.prj.cneec.tpc.order_online.entity.OrderContractAmount;
import com.supporter.prj.cneec.tpc.order_online.entity.OrderGoods;
import com.supporter.prj.cneec.tpc.order_online.entity.OrderOnline;
import com.supporter.prj.cneec.tpc.prj_contract_table.service.PrjContractTableService;
import com.supporter.prj.cneec.tpc.project_goods_bill.service.ProjectGoodsBillService;
import com.supporter.prj.cneec.tpc.record_filing_manager.dao.RecordFilingManagerDao;
import com.supporter.prj.cneec.tpc.record_filing_manager.entity.RecordFilingManager;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: OrderOnlineService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2017-10-30
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class OrderOnlineService {

	@Autowired
	private OrderOnlineDao orderOnlineDao;
	@Autowired
	private OrderContractAmountDao contractAmountDao;
	@Autowired
	private OrderGoodsDao goodsDao;
	@Autowired
	private OrderCollectionTermsDao collectionTermsDao;

	@Autowired
	private ContractOnlinePrepareService contractOnlinePrepareService;
	@Autowired
	private RecordFilingManagerDao filingManagerDao;
	@Autowired
	private ContractSignDeptReviewService contractSignDeptReviewService;
	@Autowired
	private ContractSignReviewService contractSignReviewService;

	@Autowired
	private BenefitNoteService benefitNoteService;
	@Autowired
	private BenefitBudgetService benefitBudgetService;
	@Autowired
	private PrjContractTableService contractTableService;
	@Autowired
	private ProjectGoodsBillService billService;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, OrderOnline.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param orderOnline
	 */
	public void getAuthCanExecute(UserProfile userProfile, OrderOnline orderOnline) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, OrderOnline.MODULE_ID, orderOnline.getOrderId(), orderOnline);
	}

	/**
	 * 获取销售合同上线对象集合
	 * @param user
	 * @param jqGrid
	 * @param orderOnline
	 * @return
	 */
	public List<OrderOnline> getGrid(UserProfile user, JqGrid jqGrid, OrderOnline orderOnline) {
		String authFilter = getAuthFilter(user);
		return this.orderOnlineDao.findPage(jqGrid, orderOnline, authFilter);
	}

	/**
	 * 获取合同额集合
	 * @param user
	 * @param jqGrid
	 * @param orderId
	 * @return
	 */
	public List<OrderContractAmount> getContractAmountGrid(UserProfile user, JqGrid jqGrid, String orderId) {
		return this.contractAmountDao.findPage(jqGrid, orderId);
	}

	/**
	 * 获取货物及服务明细集合
	 * @param user
	 * @param jqGrid
	 * @param orderId
	 * @return
	 */
	public List<OrderGoods> getGoodsGrid(UserProfile user, JqGrid jqGrid, String orderId) {
		return this.goodsDao.findPage(jqGrid, orderId);
	}

	/**
	 * 获取收款条件集合
	 * @param user
	 * @param jqGrid
	 * @param orderId
	 * @return
	 */
	public List<OrderCollectionTerms> getCollectionTermsGrid(UserProfile user, JqGrid jqGrid, String orderId) {
		return this.collectionTermsDao.findPage(jqGrid, orderId);
	}

	/**
	 * 获取单个销售合同上线对象
	 * @param orderId
	 * @return
	 */
	public OrderOnline get(String orderId) {
		OrderOnline orderOnline = this.orderOnlineDao.get(orderId);
		List<OrderContractAmount> contractAmountList = this.contractAmountDao.findBy("orderId", orderId);
		List<OrderGoods> goodsList = this.goodsDao.findBy("orderId", orderId);
		List<OrderCollectionTerms> collectionTermsList = this.collectionTermsDao.findBy("orderId", orderId);
		if (orderOnline != null) {
			orderOnline.setContractAmountList(contractAmountList);
			orderOnline.setGoodsList(goodsList);
			orderOnline.setCollectionTermsList(collectionTermsList);
		}
		return orderOnline;
	}

	/**
	 * 币别
	 * @param orderId
	 * @return
	 */
	public List<OrderContractAmount> getContractAmountList(String orderId) {
		if (StringUtils.isNotBlank(orderId)) {
			orderId = orderId.split(",")[0];
		}
		return this.contractAmountDao.findBy("orderId", orderId);
	}

	public Map<String, String> getContractCurrencyMap(String orderId) {
		Map<String, String> amountMap = new HashMap<String, String>();
		List<OrderContractAmount> amountList = this.getContractAmountList(orderId);
		if (amountList != null) {
			for (OrderContractAmount amount : amountList) {
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
	public OrderOnline newOrderOnline(UserProfile user) {
		OrderOnline orderOnline = new OrderOnline();
		loadOrderOnline(orderOnline, user);
		orderOnline.setSwfStatus(OrderOnline.DRAFT);
		return orderOnline;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public OrderOnline loadOrderOnline(OrderOnline orderOnline, UserProfile user) {
		orderOnline.setCreatedBy(user.getName());
		orderOnline.setCreatedById(user.getPersonId());
		orderOnline.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		orderOnline.setModifiedBy(user.getName());
		orderOnline.setModifiedById(user.getPersonId());
		orderOnline.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			orderOnline.setDeptName(dept.getName());
			orderOnline.setDeptId(dept.getDeptId());
		}
		// 设置状态
		orderOnline.setSwfStatus(OrderOnline.DRAFT);
		return orderOnline;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param orderId
	 * @param user
	 * @return
	 */
	public OrderOnline initEditOrViewPage(String orderId, UserProfile user) {
		OrderOnline orderOnline;
		if (StringUtils.isBlank(orderId)) {
			orderOnline = newOrderOnline(user);
			orderOnline.setOrderId(UUIDHex.newId());
			orderOnline.setAdd(true);
		} else {
			orderOnline = (OrderOnline) this.orderOnlineDao.get(orderId);
			orderOnline.setAdd(false);
		}
		return orderOnline;
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param orderOnline
	 * @param valueMap
	 * @return
	 */
	public OrderOnline saveOrUpdate(UserProfile user, OrderOnline orderOnline, Map<String, Object> valueMap) {
		if (orderOnline.getAdd()) {
			// 装配基础信息
			loadOrderOnline(orderOnline, user);
			this.orderOnlineDao.save(orderOnline);
			//保存备案合同附件
			this.saveFile(orderOnline ,user);
			updatePrepareProcess(orderOnline);
		} else {
			// 设置更新时间
			orderOnline.setModifiedBy(user.getName());
			orderOnline.setModifiedById(user.getPersonId());
			orderOnline.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			// 删除合同额
			deleteContractAmount(orderOnline.getDelAmountIds());
			// 删除货物及服务明细
			deleteGoods(orderOnline.getDelGoodsIds());
			// 删除收款条件
			deleteCollectionTerms(orderOnline.getDelTermsIds());
			this.orderOnlineDao.update(orderOnline);
		}
		// 保存或更新合同额
		List<OrderContractAmount> contractAmountList = orderOnline.getContractAmountList();
		if(CollectionUtils.isNotEmpty(contractAmountList)){
			saveOrUpdateContractAmountList(orderOnline.getOrderId(), contractAmountList);
		}
		// 保存或更新货物及服务明细
		List<OrderGoods> goodsList = orderOnline.getGoodsList();
		if(CollectionUtils.isNotEmpty(goodsList)){
			saveOrUpdateGoodsList(orderOnline.getOrderId(), goodsList);
		}
		// 保存或更新收款条件
		List<OrderCollectionTerms> collectionTermsList = orderOnline.getCollectionTermsList();
		if(CollectionUtils.isNotEmpty(collectionTermsList)){
			saveOrUpdateCollectionTermsList(orderOnline.getOrderId(), collectionTermsList);
		}
		return orderOnline;
	}

	/**
	 * 拷贝备案附件
	 * @param orderOnline
	 * @param user
	 */
	private void saveFile(OrderOnline orderOnline, UserProfile user) {
		RecordFilingManager recordFiling = this.filingManagerDao.findUniqueResult("preformId", orderOnline.getInforId());
		if (recordFiling != null) {
			List<IFile> fileList = EIPService.getFileUploadService().getFileList(RecordFilingManager.MODULE_ID, RecordFilingManager.BUSI_TYPE_CONTRACT,
					recordFiling.getRecordFilingId());
			for (int i = 0; i < fileList.size(); i++) {
				File file = EIPService.getFileUploadService().getFile(fileList.get(i).getFileId());
				String fileName = fileList.get(i).getFileName();
				EIPService.getFileUploadService().saveFile(file, OrderOnline.MODULE_ID, OrderOnline.BUSI_TYPE, fileName, i, user, orderOnline.getOrderId());
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
	 * @param orderId
	 * @param contractAmountList
	 */
	public void saveOrUpdateContractAmountList(String orderId, List<OrderContractAmount> contractAmountList) {
		for (OrderContractAmount amount : contractAmountList) {
			amount.setOrderId(orderId);
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
	 * @param orderId
	 * @param goodsList
	 */
	public void saveOrUpdateGoodsList(String orderId, List<OrderGoods> goodsList) {
		for (OrderGoods goods : goodsList) {
			goods.setOrderId(orderId);
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
	 * @param orderId
	 * @param collectionTermsList
	 */
	public void saveOrUpdateCollectionTermsList(String orderId, List<OrderCollectionTerms> collectionTermsList) {
		for (OrderCollectionTerms terms : collectionTermsList) {
			terms.setOrderId(orderId);
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
	 * @param orderOnline
	 * @param valueMap
	 * @return
	 */
	public OrderOnline commit(UserProfile user, OrderOnline orderOnline, Map<String, Object> valueMap) {
		saveOrUpdate(user, orderOnline, valueMap);
		// 记录日志
		String logMessage = MessageFormat.format(LogConstant.PUBLISH_LOG_MESSAGE_ORDERONLINE, "(" + orderOnline.getOrderNo() + ")" + orderOnline.getOrderName());
		EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS_ORDERONLINE).info(user, LogConstant.PUBLISH_LOG_ACTION_ORDERONLINE, logMessage, orderOnline, null);
		return orderOnline;
	}

	/**
	 * 审批完成操作
	 * @param orderOnline
	 */
	public void completeExam(OrderOnline orderOnline) {
		// 将合同信息录入合同正式表中
		contractTableService.createTpcPrjContractTableByOrderOnline(orderOnline);
		// 生成项目货品清单
		billService.saveProjectGoodsBillByOrderOnline(orderOnline);
		updatePrepareFinish(orderOnline);
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param orderIds
	 */
	public void delete(UserProfile user, String orderIds) {
		if (StringUtils.isNotBlank(orderIds)) {
			OrderOnline orderOnline;
			for (String orderId : orderIds.split(",")) {
				orderOnline = this.get(orderId);
				if (orderOnline == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, orderOnline);
				updatePrepareDraft(orderOnline);
				this.contractAmountDao.deleteByProperty("orderId", orderId);
				this.goodsDao.deleteByProperty("orderId", orderId);
				this.collectionTermsDao.deleteByProperty("orderId", orderId);
				this.orderOnlineDao.delete(orderOnline);
			}
		}
	}

	public boolean createContractOnlineBySheet(Map<String, Object> parameters) {
		return true;
	}

	/**
	 * 根据ID获取付款金额
	 * @param orderId
	 * @return
	 */
	public Map<String, Double> getPayPlanAmounts(String orderId) {
		return this.collectionTermsDao.getPayPlanAmounts(orderId);
	}

	/**
	 * 流程更新对象
	 * @param useSeal
	 * @return
	 */
	public OrderOnline update(OrderOnline orderOnline) {
		this.orderOnlineDao.update(orderOnline);
		return orderOnline;
	}

	/**
	 * 
	 * @param inforId
	 * @param valueMap
	 * @param user
	 * @return
	 */
	public OrderOnline initOrderOnlineByInforId(String inforId, Map<String, Object> valueMap, UserProfile user) {
		OrderOnline orderOnline = this.orderOnlineDao.findUniqueResult("inforId", inforId);
		if (orderOnline == null) {
			ContractOnlinePrepare prepare = this.contractOnlinePrepareService.findUniqueResult("inforId", inforId);
			orderOnline = newOrderOnline(user);
			String orderId = UUIDHex.newId();
			orderOnline.setOrderId(orderId);
			orderOnline.setSwfStatus(OrderOnline.DRAFT);
			orderOnline.setAdd(true);
			orderOnline.setProjectId(prepare.getProjectId());
			orderOnline.setProjectName(prepare.getProjectName());
			orderOnline.setOrderName(prepare.getContractName());
			orderOnline.setCustomerId(prepare.getCustomerId());
			orderOnline.setCustomerName(prepare.getContractParty());
			orderOnline.setContractRmbAmount(prepare.getTotalRmbAmount());
			// 退税额、毛利从效益测算表中取，退税额即应收出口退税
			BenefitNote benefitNote = this.benefitNoteService.getBenefitNoteTopByInforId(inforId);
			List<BenefitNoteBudget> noteBudgetList = this.benefitNoteService.getBudgetListByNoteId(benefitNote.getNoteId());
			for (BenefitNoteBudget noteBudget : noteBudgetList) {
				if (noteBudget.getBudgetId().equals(BenefitBudgetItemConstant.SUMMARY_EXPORT_DRAWBACK)) {
					// 退税额
					orderOnline.setTaxRefund(noteBudget.getTotalRmbAmount());
				} else if (noteBudget.getBudgetId().equals(BenefitBudgetItemConstant.SUMMARY_GROSS_PROFIT)) {
					// 毛利
					orderOnline.setGrossProfit(noteBudget.getTotalRmbAmount());
				}
			}
			orderOnline.setInforId(inforId);
			RecordFilingManager recordFiling = this.filingManagerDao.findUniqueResult("preformId", inforId);
			if (recordFiling != null) {
				orderOnline.setFilingId(recordFiling.getRecordFilingId());
				orderOnline.setFilingNo(recordFiling.getRecordFilingNo());
				orderOnline.setOrderNo(recordFiling.getBusinessNo());
			}
			try {
				List<OrderContractAmount> contractAmountList = new ArrayList<OrderContractAmount>();
				List<OrderGoods> contractGoodsList = new ArrayList<OrderGoods>();
				OrderContractAmount contractAmount;
				OrderGoods contractGoods;
				if (prepare.isCompanyReview()) {
					List<ContractSignAmount> amountList = this.contractSignReviewService.getInforAmountList(inforId);
					List<ContractSignItem1> itemList = (List<ContractSignItem1>) this.contractSignReviewService.getInforItemList(inforId, ContractSignReviewUtil.INFOR_TYPE_ORDER);
					for (ContractSignAmount origAmount : amountList) {
						contractAmount = new OrderContractAmount();
						BeanUtils.copyProperties(contractAmount, origAmount);
						contractAmount.setAmountId(null);
						contractAmount.setAdd(true);
						contractAmount.setOrderId(orderId);
						contractAmountList.add(contractAmount);
					}
					for (ContractSignItem1 origItem : itemList) {
						// 只取实际采购项
						if (origItem.isLeaf()) {
							contractGoods = new OrderGoods();
							BeanUtils.copyProperties(contractGoods, origItem);
							contractGoods.setGoodsId(null);
							contractGoods.setAdd(true);
							contractGoods.setOrderId(orderId);
							contractGoodsList.add(contractGoods);
						}
					}
				} else {
					List<ContractSignDeptAmount> amountList = this.contractSignDeptReviewService.getInforAmountList(inforId);
					List<ContractSignDeptItem1> itemList = (List<ContractSignDeptItem1>) this.contractSignDeptReviewService.getInforItemList(inforId, ContractSignReviewUtil.INFOR_TYPE_ORDER);
					for (ContractSignDeptAmount origAmount : amountList) {
						contractAmount = new OrderContractAmount();
						BeanUtils.copyProperties(contractAmount, origAmount);
						contractAmount.setAmountId(null);
						contractAmount.setAdd(true);
						contractAmount.setOrderId(orderId);
						contractAmountList.add(contractAmount);
					}
					for (ContractSignDeptItem1 origItem : itemList) {
						// 只取实际采购项
						if (origItem.isLeaf()) {
							contractGoods = new OrderGoods();
							BeanUtils.copyProperties(contractGoods, origItem);
							contractGoods.setGoodsId(null);
							contractGoods.setAdd(true);
							contractGoods.setOrderId(orderId);
							contractGoodsList.add(contractGoods);
						}
					}
				}
				orderOnline.setContractAmountList(contractAmountList);
				orderOnline.setGoodsList(contractGoodsList);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			saveOrUpdate(user, orderOnline, null);
			orderOnline.setAdd(false);
		}
		return orderOnline;
	}

	/**
	 * 删除操作设置前置单为草稿
	 */
	public void updatePrepareDraft(OrderOnline orderOnline) {
		// 删除记录后将前置单合同上线状态置为默认状态
		String inforId = CommonUtil.trim(orderOnline.getInforId());
		this.contractOnlinePrepareService.updateStatusByInforIds(inforId, "onlineStatus", ContractOnlinePrepare.ONLINE_DRAFT);
	}

	/**
	 * 保存记录后设置前置单为进行中
	 */
	public void updatePrepareProcess(OrderOnline orderOnline) {
		// 生成记录后将前置单合同上线状态置为进行中状态
		String inforId = CommonUtil.trim(orderOnline.getInforId());
		this.contractOnlinePrepareService.updateStatusByInforIds(inforId, "onlineStatus", ContractOnlinePrepare.ONLINE_PROCESS);
	}

	/**
	 * 审批完成设置前置单为完成状态
	 */
	public void updatePrepareFinish(OrderOnline orderOnline) {
		// 记录审批完成后将前置单合同上线状态置为已完成状态
		String inforId = CommonUtil.trim(orderOnline.getInforId());
		this.contractOnlinePrepareService.updateStatusByInforIds(inforId, "onlineStatus", ContractOnlinePrepare.ONLINE_FINISH);
		// 给效益测算/预算表设置合同ID等（根据效益编号可以获取录入表，变更表，效益预算表）
		ContractSignDeptInfor infor = this.contractSignDeptReviewService.getContractSignDeptInfor(inforId);
		String benefitNo = infor.getBenefitNo();
		String contractId = orderOnline.getOrderId();
		String contractNo = orderOnline.getOrderNo();
		String contractName = orderOnline.getOrderName();
		if (StringUtils.isNotBlank(benefitNo)) {
			this.benefitNoteService.updateContractByBenefitNo(benefitNo, contractId, contractNo, contractName);
			this.benefitBudgetService.updateContractByBenefitNo(benefitNo, contractId, contractNo, contractName);
		}
	}

	/** 以下是选择销售合同方法 **/

	public ListPage<OrderOnline> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<OrderOnline> listPage = new ListPage<OrderOnline>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.orderOnlineDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}

}
