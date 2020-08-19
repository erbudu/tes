package com.supporter.prj.cneec.tpc.contract_sign_review.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.benefit_budget.constant.BenefitBudgetItemConstant;
import com.supporter.prj.cneec.tpc.benefit_budget.entity.BenefitContractCurrency;
import com.supporter.prj.cneec.tpc.benefit_budget.service.BenefitBudgetService;
import com.supporter.prj.cneec.tpc.benefit_note.service.BenefitNoteService;
import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.contract_online_prepare.service.ContractOnlinePrepareService;
import com.supporter.prj.cneec.tpc.contract_sign_review.dao.ContractSignAmountDao;
import com.supporter.prj.cneec.tpc.contract_sign_review.dao.ContractSignInforDao;
import com.supporter.prj.cneec.tpc.contract_sign_review.dao.ContractSignItem1Dao;
import com.supporter.prj.cneec.tpc.contract_sign_review.dao.ContractSignItem2Dao;
import com.supporter.prj.cneec.tpc.contract_sign_review.dao.ContractSignOpinionDao;
import com.supporter.prj.cneec.tpc.contract_sign_review.dao.ContractSignReviewDao;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractReviewRecord;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignAmount;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignInfor;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignItem1;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignItem2;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignOpinion;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignReview;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.dao.ContractSignDeptAmountDao;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptAmount;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptInfor;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptItem1;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptItem2;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptReview;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.service.ContractSignDeptReviewService;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.util.ContractSignItemBean;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.util.ContractSignReviewUtil;
import com.supporter.prj.cneec.tpc.purchase_demand_sheet.service.PurchaseDemandSheetService;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.FilesUtil;
import com.supporter.prj.cneec.tpc.util.TpcBudgetUtil;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.todo.entity.Todo;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.spring_mvc.binding.JsonUtil;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: ContractSignReviewService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2017-09-28
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class ContractSignReviewService {

	@Autowired
	private ContractSignReviewDao contractSignReviewDao;
	@Autowired
	private ContractSignInforDao contractSignInforDao;
	@Autowired
	private ContractSignAmountDao contractSignAmountDao;
	@Autowired
	private ContractSignItem1Dao contractSignItem1Dao;
	@Autowired
	private ContractSignItem2Dao contractSignItem2Dao;
	@Autowired
	private ContractSignOpinionDao contractSignOpinionDao;

	@Autowired
	private ContractSignDeptReviewService contractSignDeptReviewService;
	@Autowired
	private ContractSignDeptAmountDao contractSignDeptAmountDao;

	@Autowired
	private PurchaseDemandSheetService purchaseDemandSheetService;
	@Autowired
	private ContractOnlinePrepareService contractOnlinePrepareService;

	@Autowired
	private BenefitNoteService benefitNoteService;
	@Autowired
	private BenefitBudgetService benefitBudgetService;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, ContractSignReview.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param contractSignReview
	 */
	public void getAuthCanExecute(UserProfile userProfile, ContractSignReview contractSignReview) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, ContractSignReview.MODULE_ID, contractSignReview.getSignId(), contractSignReview);
	}

	/**
	 * 获取合同签约评审对象集合
	 * @param user
	 * @param jqGrid
	 * @param contractSignReview
	 * @return
	 */
	public List<ContractSignReview> getGrid(UserProfile user, JqGrid jqGrid, ContractSignReview contractSignReview) {
		String authFilter = getAuthFilter(user);
		return this.contractSignReviewDao.findPage(jqGrid, contractSignReview, authFilter);
	}

	/**
	 * 获取单个合同签约评审对象
	 * @param signId
	 * @return
	 */
	public ContractSignReview get(String signId) {
		ContractSignReview contractSignReview = this.contractSignReviewDao.get(signId);
		List<ContractSignOpinion> signOpinions = this.contractSignOpinionDao.findBy("signId", signId);
		if (contractSignReview != null) {
			contractSignReview.setSignOpinions(signOpinions);
		}
		return contractSignReview;
	}

	public List<ContractSignInfor> getContractSignInforByInforType(String signId, Integer infortype) {
		return this.contractSignInforDao.getContractSignInforByInforType(signId, infortype);
	}

	/**
	 * 新建合同签约评审对象
	 * @param user
	 * @return
	 */
	public ContractSignReview newContractSignReview(String signId, UserProfile user) {
		ContractSignReview contractSignReview = new ContractSignReview(signId);
		loadContractSignReview(contractSignReview, user);
		contractSignReview.setSwfStatus(ContractSignReview.DRAFT);
//		List<ContractSignOpinion> signOpinions = newContractSignOpinions(contractSignReview);
//		contractSignReview.setSignOpinions(signOpinions);
		return contractSignReview;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public ContractSignReview loadContractSignReview(ContractSignReview contractSignReview, UserProfile user) {
		contractSignReview.setCreatedBy(user.getName());
		contractSignReview.setCreatedById(user.getPersonId());
		contractSignReview.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		contractSignReview.setModifiedBy(user.getName());
		contractSignReview.setModifiedById(user.getPersonId());
		contractSignReview.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			contractSignReview.setDeptName(dept.getName());
			contractSignReview.setDeptId(dept.getDeptId());
		}
		// 设置状态
		contractSignReview.setSwfStatus(ContractSignReview.DRAFT);
		return contractSignReview;
	}

	/**
	 * 初始化评审意见
	 * @param contractSignReview
	 * @return
	 */
	public List<ContractSignOpinion> newContractSignOpinions(ContractSignReview contractSignReview) {
		List<ContractSignOpinion> signOpinions = new ArrayList<ContractSignOpinion>();
		ContractSignOpinion contractSignOpinion;
		for (Map.Entry<Integer, String> entry : ContractSignReviewUtil.getOpinionManMap().entrySet()) {
			contractSignOpinion = new ContractSignOpinion();
			contractSignOpinion.setSignId(contractSignReview.getSignId());
			contractSignOpinion.setDisplayOrder(entry.getKey());
			contractSignOpinion.setOpinionMan(entry.getValue());
			signOpinions.add(contractSignOpinion);
		}
		return signOpinions;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param signId
	 * @param user
	 * @return
	 */
	public ContractSignReview initEditOrViewPage(String signId, UserProfile user) {
		ContractSignReview contractSignReview;
		if (StringUtils.isBlank(signId)) {
			contractSignReview = newContractSignReview(UUIDHex.newId(), user);
			contractSignReview.setAdd(true);
		} else {
			contractSignReview = this.get(signId);
			contractSignReview.setAdd(false);
		}
		return contractSignReview;
	}

	/**
	 * 根据事业部评审单生成公司评审单
	 * @param deptSignId
	 * @param user
	 * @return
	 */
	public ContractSignReview createContractSignReviewByDeptReview(String deptSignId, UserProfile user) {
		ContractSignReview review = new ContractSignReview();
		ContractSignDeptReview deptReview = this.contractSignDeptReviewService.get(deptSignId);
		List<ContractSignInfor> inforList = new ArrayList<ContractSignInfor>();
		List<ContractSignAmount> amountList = new ArrayList<ContractSignAmount>();
		try {
			BeanUtils.copyProperties(review, deptReview);
			// 合同
			List<ContractSignDeptInfor> deptInforList = this.contractSignDeptReviewService.findInforBy("signId", deptSignId);
			ContractSignInfor infor;
			for (ContractSignDeptInfor deptInfor : deptInforList) {
				infor = new ContractSignInfor();
				BeanUtils.copyProperties(infor, deptInfor);
				infor.setReviewConclusion(null);
				infor.setChangeBenefit(false);
				inforList.add(infor);
				// 拷贝附件
				String[] sourceModule = null;
				String[] targetModule = null;
				if (infor.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
					sourceModule = new String[] { ContractSignDeptReview.MODULE_ID, ContractSignDeptReview.BUSI_TYPE_ORDER, infor.getInforId(), "" };
					targetModule = new String[] { ContractSignReview.MODULE_ID, ContractSignReview.BUSI_TYPE_ORDER, infor.getInforId(), "" };
				} else {
					sourceModule = new String[] { ContractSignDeptReview.MODULE_ID, ContractSignDeptReview.BUSI_TYPE_CONTRACT, infor.getInforId(), "" };
					targetModule = new String[] { ContractSignReview.MODULE_ID, ContractSignReview.BUSI_TYPE_CONTRACT, infor.getInforId(), "" };
				}
				//FilesUtil.copyFiles(sourceModule, targetModule, user);
				FilesUtil.copySourceFilesToTargetModule(sourceModule, targetModule);
			}
			// 合同金额
			List<ContractSignDeptAmount> deptAmountList = this.contractSignDeptReviewService.findInforAmountBy("signId", deptSignId);
			ContractSignAmount amount;
			for (ContractSignDeptAmount deptAmount : deptAmountList) {
				amount = new ContractSignAmount();
				BeanUtils.copyProperties(amount, deptAmount);
				amountList.add(amount);
			}
			// 合同明细
			List<ContractSignDeptItem1> saleDeptItemList = this.contractSignDeptReviewService.findSaleInforItemBy("saleReviewId", deptSignId);
			ContractSignItem1 item1;
			for (ContractSignDeptItem1 deptItem : saleDeptItemList) {
				item1 = new ContractSignItem1();
				BeanUtils.copyProperties(item1, deptItem);
				this.contractSignItem1Dao.save(item1);
			}
			List<ContractSignDeptItem2> purchaseDeptItemList = this.contractSignDeptReviewService.findPurchaseInforItemBy("purchaseReviewId", deptSignId);
			ContractSignItem2 item2;
			for (ContractSignDeptItem2 deptItem : purchaseDeptItemList) {
				item2 = new ContractSignItem2();
				BeanUtils.copyProperties(item2, deptItem);
				this.contractSignItem2Dao.save(item2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 装填当前用户信息
		review.setSignerId(null);
		review.setSignerName(null);
		review.setReviewConclusion(null);
		review.setSwfStatus(ContractSignReview.DRAFT);
		review.setProcId(null);
		review.setStepStatus(ContractSignReview.STEP_NOTIFY);
		if (user != null) {
			loadContractSignReview(review, user);
		} else {
			review.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			review.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		}
//		List<ContractSignOpinion> signOpinions = newContractSignOpinions(review);
//		review.setSignOpinions(signOpinions);
		this.contractSignReviewDao.save(review);
		if (inforList.size() > 0) {
			this.contractSignInforDao.save(inforList);
			this.contractSignAmountDao.save(amountList);
		}
		return review;
	}

	/**
	 * 根据合同签约前事业部评审初始化对象
	 * @param deptSignId
	 * @param user
	 * @return
	 */
	public ContractSignReview initEditOrViewByDeptReview(String deptSignId, UserProfile user) {
		ContractSignReview contractSignReview;
		if (StringUtils.isNotBlank(deptSignId)) {
			contractSignReview = this.get(deptSignId);
			if (contractSignReview == null) {
				contractSignReview = createContractSignReviewByDeptReview(deptSignId, user);
			}
			contractSignReview.setAdd(false);
		} else {
			contractSignReview = newContractSignReview(UUIDHex.newId(), user);
			contractSignReview.setAdd(true);
		}
		return contractSignReview;
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param contractSignReview
	 * @param valueMap
	 * @return
	 */
	public ContractSignReview saveOrUpdate(UserProfile user, ContractSignReview contractSignReview, Map<String, Object> valueMap) {
		// 生成编号
		if(StringUtils.isBlank(contractSignReview.getReviewNo())) {
			contractSignReview.setReviewNo(generatorReviewNo());
		}
		if (contractSignReview.getAdd()) {
			// 装配基础信息
			loadContractSignReview(contractSignReview, user);
			this.contractSignReviewDao.save(contractSignReview);
		} else {
			// 设置更新时间
			contractSignReview.setModifiedBy(user.getName());
			contractSignReview.setModifiedById(user.getPersonId());
			contractSignReview.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.contractSignReviewDao.update(contractSignReview);
		}
		if (CollectionUtils.isNotEmpty(contractSignReview.getSignOpinions())) {
			for (ContractSignOpinion opinion : contractSignReview.getSignOpinions()) {
				this.contractSignOpinionDao.saveOrUpdate(opinion);
			}
		}
		return contractSignReview;
	}

	/**
	 * 提交后需要处理业务操作
	 * @param contractSignReview
	 */
	public void afterCommitExecute(UserProfile user, ContractSignReview contractSignReview) {
		String reviewClass = CommonUtil.trim(contractSignReview.getReviewClassification());
		// 设置附件名称
		List<ContractSignInfor> contractList = this.contractSignInforDao.queryBy("signId", contractSignReview.getSignId(), false, "inforId", true);
		for (ContractSignInfor contract : contractList) {
			String fileNames = getFileNames(contract);
			contract.setReviewBasis(fileNames);// 设置评审依据资料为附件名称
			this.contractSignInforDao.update(contract);
			// 非超大额时修改销售合同效益测算表
			if (!ContractSignReviewUtil.REVIEW_SELF_SUPPORT_SUPER.equals(reviewClass) && contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
				// 提交评审修改效益测算表为确认中
				this.benefitNoteService.confirming(contract.getNoteId());
			}
		}
		// 会审直接完成
		if (ContractSignReviewUtil.REVIEW_SELF_SUPPORT_SUPER.equals(reviewClass)) {
			updateBusinissByMeetFinish(user, contractSignReview);
		}
		
	}

	/**
	 * 获取附件名称
	 * 
	 * @param contract
	 * @return
	 */
	public String getFileNames(ContractSignInfor contract) {
		String busiType;
		if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
			busiType = ContractSignReview.BUSI_TYPE_ORDER;
		} else {
			busiType = ContractSignReview.BUSI_TYPE_CONTRACT;
		}
		String fileNames = FilesUtil.getFileNames(ContractSignReview.MODULE_ID, busiType, contract.getInforId(), "");
		return fileNames;
	}

	/**
	 * 会议评审单完成需要处理的业务：
	 * 	1.评审完成后处理合同评审业务
	 *  2.评审完成后处理项目预算业务
	 */
	public void updateBusinissByMeetFinish(UserProfile user, ContractSignReview contractSignReview) {
		finishReviewBusiness(contractSignReview);
		finishCreatedByConfirmBusiness(user, contractSignReview);
	}

	/**
	 * 评审完成后，系统确认完成需要处理的业务
	 *  1.设置销售/采购合同评审意见
	 *  2.设置合同对应的客户需求选择单状态
	 *  3.销售合同设置效益测算表确认状态
	 *  4.设置合同签约前评审单意见
	 * @param contractSignReview
	 */
	public void finishReviewBusiness(ContractSignReview contractSignReview) {
		// 设置合同评审结论为同意并设置选择需求单表完成
		List<ContractSignInfor> contractList = this.contractSignInforDao.queryBy("signId", contractSignReview.getSignId(), false, "inforId", true);
		for (ContractSignInfor contract : contractList) {
			// 1.设置销售/采购合同评审意见
			if (CommonUtil.trim(contract.getReviewConclusion()).length() == 0) {
				contract.setReviewConclusion(TpcConstant.REVIEW_CONCLUSION_AGREE);
			}
			this.contractSignInforDao.update(contract);

			if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
				// 2.设置合同对应的客户需求选择单状态
				this.purchaseDemandSheetService.updateReviewStatusReviewedByRecordIds(contract.getRecordId(), contract.getReviewConclusion());
				// 3.销售合同设置效益测算表确认状态
				this.benefitNoteService.confirmed(contract.getNoteId());
			}
		}
		// 4.设置合同签约前评审意见
		contractSignReview.setReviewConclusion(TpcConstant.REVIEW_CONCLUSION_AGREE);
		// 会审置为完成
		if (contractSignReview.isMeetReview()) {
			contractSignReview.setSwfStatus(ContractSignReview.COMPLETED);
		}
		update(contractSignReview);
	}

	/**
	 * 评审完成后，经办人确认后处理的业务
	 *  1.将合同写入用印、备案、合同信息上线初始化选择单中
	 *  2.将销售合同预算写入项目预算库中
	 *  3.将采购合同金额转入在途（即加锁预算）
	 * @param user
	 * @param contractSignReview
	 */
	public void finishCreatedByConfirmBusiness(UserProfile user, ContractSignReview contractSignReview) {
		List<ContractSignInfor> contractList = this.contractSignInforDao.queryBy("signId", contractSignReview.getSignId(), false, "inforId", true);

		for (ContractSignInfor contract : contractList) {
			// 1.将合同写入用印、备案、合同信息上线初始化选择单中
			this.contractOnlinePrepareService.createdByContractSignReview(contractSignReview, contract);

			// 2.将销售合同预算写入项目预算库中
			if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
				this.benefitBudgetService.createBenefitByBenefitNote(contract.getNoteId());
			}
		}

		// 3.将采购合同金额转入在途（即加锁预算）
		String projectId = contractSignReview.getProjectId();
		// 采购合同扣除预算即为采购合同总金额下货款（服务款）金额
		String budgetId = BenefitBudgetItemConstant.SUMMARY_PURCHASE_PAYMENT;
		String inforId;
		for (ContractSignInfor contract : contractList) {
			if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_CONTRACT) {
				inforId = contract.getInforId();

//				// 按采购合同币别金额扣除预算
//				List<ContractSignDeptAmount> contractAmounts = this.contractSignDeptReviewService.getInforAmountList(contract.getInforId());
//				for (ContractSignDeptAmount contractAmount : contractAmounts) {
//					// 扣除预算时按原币*执行汇率折合人民币计算（执行汇率效益测算表中对应币别汇率，如果该币别在效益测算表中不存在由则业务人员输入）
//					this.addOnwayBudgetAmount(user, projectId, budgetId, contractAmount.getOriginalAmount() * contractAmount.getExecuteRate());
//				}

				// 按采购合同明细币别金额扣除预算
				// 获取采购合同金额
				Map<String, ContractSignAmount> amountMap = this.getInforAmountMap(inforId);
				List<ContractSignItem2> itemList = this.findPurchaseInforItemBy("purchaseContractId", inforId);
				for (ContractSignItem2 item : itemList) {
					double executeRate = 0;
					// 获取采购明细对应销售合同的效益测算表（过程合同效益测算表）
					BenefitContractCurrency benefitContractCurrency = this.benefitBudgetService.queryUniqueCurrency(item.getSaleContractId(), item.getCurrencyId());
					if (benefitContractCurrency != null) {
						executeRate = benefitContractCurrency.getRate();
					} else if (amountMap.get(item.getCurrencyId()) != null) {
						executeRate = amountMap.get(item.getCurrencyId()).getExecuteRate();
					}
					// 扣除预算时按原币*执行汇率折合人民币计算（执行汇率效益测算表中对应币别汇率，如果该币别在效益测算表中不存在由则业务人员输入）
					this.addOnwayBudgetAmount(user, projectId, budgetId, item.getAmount() * executeRate);
				}
			}
		}
	}

	/**
	 * 审批完成执行操作
	 * @param contractSignReview
	 */
	public void completeExam(ContractSignReview contractSignReview) {
		finishReviewBusiness(contractSignReview);
	}

	/**
	 * 经办人确认评审完成(未修改效益测算表直接确认完成)
	 */
	public String completeConfirm(UserProfile user, String signId) {
		ContractSignReview contractSignReview = this.get(signId);
		String json = "{\"success\": true,\"msg\": \"\"}";
		boolean flag = true;
		// 判断是否有未审批完成的效益测算表
		List<ContractSignInfor> contractList = this.contractSignInforDao.queryBy("signId", contractSignReview.getSignId(), false, "inforId", true);
		for (ContractSignInfor contract : contractList) {
			if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
				if (!this.benefitNoteService.isBenefitNoteConfirmed(contract.getNoteId(), false)) {
					json = "{\"success\": false,\"msg\": \"(" + contract.getBenefitNo() + ")" + contract.getContractName() + "\"}";
					flag = false;
					break;
				}
			}
		}
		if (flag) {// 验证通过
			contractSignReview.setSwfStatus(ContractSignReview.COMPLETED);
			update(contractSignReview);
			finishCreatedByConfirmBusiness(user, contractSignReview);
		}
		return json;
	}

	/**
	 * 添加在途金额(审批完成扣除采购合同预算)
	 *
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void addOnwayBudgetAmount(UserProfile user, String projectId, String budgetId, double amount) {
		TpcBudgetUtil.addOnwayBudgetAmount(TpcBudgetUtil.TPC_CONTRACT_SIGN_REVIEW, user, projectId, budgetId, amount);
	}

	/**
	 * 会审提交
	 * 验证是否可以提交（会审验证是否所有效益测算表提交流程确认）
	 */
	public String commitByMeetReview(UserProfile user, ContractSignReview contractSignReview, Map<String, Object> valueMap) {
		String json = "{\"success\": true,\"msg\": \"\"}";
		boolean flag = true;
		// 是否验证效益测算表提交
		boolean validBenefitNote = Boolean.valueOf((String) valueMap.get("validBenefitNote"));
		List<ContractSignInfor> contractList = this.contractSignInforDao.queryBy("signId", contractSignReview.getSignId(), false, "inforId", true);
		for (ContractSignInfor contract : contractList) {
			if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
				if (validBenefitNote) {
					// 验证效益测算表是否已提交
					if (!this.benefitNoteService.isSubmitBenefitNote(contract.getNoteId())) {
						json = "{\"success\": false,\"msg\": \"(" + contract.getBenefitNo() + ")" + contract.getContractName() + "\"}";
						flag = false;
						break;
					}
				} else {
					// 验证效益测算表是否流程审批完成
					if (!this.benefitNoteService.isBenefitNoteConfirmed(contract.getNoteId(), true)) {
						json = "{\"success\": false,\"msg\": \"(" + contract.getBenefitNo() + ")" + contract.getContractName() + "\"}";
						flag = false;
						break;
					}
				}
			}
		}
		if (!validBenefitNote && flag) {// 验证通过
			// 处理会审完成业务
			updateBusinissByMeetFinish(user, contractSignReview);
		}
		return json;
	}

	/**
	 * 保存提交
	 * @param user
	 * @param contractSignReview
	 * @param valueMap
	 * @return
	 */
	public ContractSignReview commit(UserProfile user, ContractSignReview contractSignReview, Map<String, Object> valueMap) {
		saveOrUpdate(user, contractSignReview, valueMap);
		afterCommitExecute(user, contractSignReview);
		// 记录日志
		//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{EditApplication : " + contractSignReview + "}", null, null);
		return contractSignReview;
	}

	/**
	 * 会审发送会签人知会
	 * @param contractSignReview
	 */
	public void sendMeetNotifyMsg(ContractSignReview contractSignReview) {
		contractSignReview.setSwfStatus(ContractSignReview.PROCESSING);
		update(contractSignReview);
		// 获取会签人
		if (StringUtils.isNotBlank(contractSignReview.getSignerId())) {
			String[] signers = contractSignReview.getSignerId().split(",");
			if (signers.length > 0) {
				for (String personId : signers) {
					Message message = new Todo();
					message.setPersonId(personId);
					message.setEventTitle(contractSignReview.getDeptName() + contractSignReview.getProjectName() + contractSignReview.getReviewNo() + "合同签约前公司评审，会议资料如下，请查收");
					message.setNotifyTime(new Date());
					message.setWebPageURL("tpc/contract_sign_review/contract_sign_review_meet_notify.html?isCcPage=true&signId="
							+ contractSignReview.getSignId());
					message.setMessageType(ITodo.MsgType.CC);
					message.setRelatedRecordTable(ContractSignReview.MODULE_ID);
					EIPService.getBMSService().addMessage(message);
				}
			}
		}
		
	}

	/**
	 * 更新对象
	 * @param review
	 * @return
	 */
	public ContractSignReview update(ContractSignReview review) {
		this.contractSignReviewDao.update(review);
		return review;
	}

	/**
	 * 更新合同对象
	 * @param infor
	 * @return
	 */
	public ContractSignInfor update(ContractSignInfor infor) {
		this.contractSignInforDao.update(infor);
		return infor;
	}

	/**
	 * 生成评审单号
	 * @return
	 */
	public synchronized String generatorReviewNo() {
		String reviewNo = null;
		Calendar date = Calendar.getInstance();
		String NoHead = "CNEEC" + String.valueOf(date.get(Calendar.YEAR)) + String.valueOf(date.get(Calendar.MONTH) + 1) + String.valueOf(date.get(Calendar.MINUTE));
		Integer count = date.get(Calendar.MILLISECOND);
		String NoEnd = String.format("%03d", (count));
		reviewNo = NoHead + "-" + NoEnd;
		return reviewNo;
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param signIds
	 */
	public void delete(UserProfile user, String signIds) {
		if (StringUtils.isNotBlank(signIds)) {
			ContractSignReview contractSignReview;
			for (String signId : signIds.split(",")) {
				contractSignReview = this.get(signId);
				if (contractSignReview == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, contractSignReview);
				List<ContractSignInfor> contractList = this.contractSignInforDao.queryBy("signId", signId, false, "inforId", true);
				for (ContractSignInfor contract : contractList) {
					String[] sourceModule;
					if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
						// 删除效益测算表
						String noteId = CommonUtil.trim(contract.getNoteId());
						if (noteId.length() > 0 && contract.isChangeBenefit()) {
							// 效益测算表是新生成的删除
							this.benefitNoteService.delete(user, noteId);
						}
						sourceModule = new String[] {ContractSignReview.MODULE_ID, ContractSignReview.BUSI_TYPE_ORDER, contract.getInforId(), "" };
					} else {
						// 清除销售合同明细的采购合同属性，删除采购合同明细
						this.contractSignItem1Dao.removePurchaseValue(contract.getInforId());
						sourceModule = new String[] {ContractSignReview.MODULE_ID, ContractSignReview.BUSI_TYPE_CONTRACT, contract.getInforId(), "" };
					}
					FilesUtil.deleteFiles(sourceModule);
				}
				this.contractSignInforDao.deleteByProperty("signId", signId);
				this.contractSignAmountDao.deleteByProperty("signId", signId);
				this.contractSignItem1Dao.deleteByProperty("saleReviewId", signId);
				this.contractSignItem2Dao.deleteByProperty("purchaseReviewId", signId);
				this.contractSignOpinionDao.deleteByProperty("signId", signId);
				this.contractSignReviewDao.delete(contractSignReview);
			}
		}
	}

	/**
	 * 判断两个数组是否相同
	 * @param array1
	 * @param array2
	 * @return
	 */
	public boolean isEquals(String[] array1, String[] array2) {
		Arrays.sort(array1);
		Arrays.sort(array2);
		return Arrays.equals(array1, array2);
	}

	/**
	 * 判断数组是否包含另一个数组
	 * @param baseArr
	 * @param targetArr
	 * @return
	 */
	public boolean isContained(String[] baseArr, String[] targetArr) {
	    if(baseArr == null || targetArr == null) return false;
	    if(baseArr.length < targetArr.length) return false;
	    String aStr = StringUtils.join(baseArr, ",");
	    for(int i = 0, len = targetArr.length; i < len; i++){
	       if(aStr.indexOf(targetArr[i]) == -1) return false;
	    }
	    return true;
	}

	/**
	 * 获取合同签约评审对象集合
	 * @param user
	 * @param jqGrid
	 * @param contractSignInfor
	 * @return
	 */
	public List<ContractSignInfor> getGrid(UserProfile user, JqGrid jqGrid, ContractSignInfor contractSignInfor) {
		return this.contractSignInforDao.findPage(jqGrid, contractSignInfor);
	}


	/**
	 * 获取合同金额对象集合
	 * @param user
	 * @param jqGrid
	 * @param parameters
	 * @return
	 */
	public List<ContractSignAmount> getInforAmountGrid(UserProfile user, JqGrid jqGrid, Map<String, Object> parameters) {
		return this.contractSignAmountDao.findPage(jqGrid, parameters);
	}

	public List<ContractSignAmount> getInforAmountList(String inforId) {
		return this.contractSignAmountDao.queryBy("inforId", inforId, false, "amountId", true);
	}

	public Map<String, ContractSignAmount> getInforAmountMap(String inforId) {
		Map<String, ContractSignAmount> amountMap = new HashMap<String, ContractSignAmount>();
		List<ContractSignAmount> amountList = this.getInforAmountList(inforId);
		if (amountList != null && amountList.size() > 0) {
			for (ContractSignAmount amount : amountList) {
				amountMap.put(amount.getCurrencyId(), amount);
			}
		}
		return amountMap;
	}

	/**
	 * 合同金额币别汇率MAP
	 * @param inforId
	 * @return
	 */
	public Map<String, Object> getContractAmountRateMap(String inforId) {
		Map<String, Object> amountRateMap = new HashMap<String, Object>();
		Map<String, Double> amountRateMap1 = new HashMap<String, Double>();
		Map<String, Double> amountRateMap2 = new HashMap<String, Double>();
		List<ContractSignAmount> amountList = this.getInforAmountList(inforId);
		if (amountList != null && amountList.size() > 0) {
			for (ContractSignAmount amount : amountList) {
				amountRateMap1.put(amount.getCurrencyId(), amount.getRate());
				amountRateMap2.put(amount.getCurrency(), amount.getRate());
			}
			amountRateMap.put("result", true);
			amountRateMap.put("amountRateMap1", amountRateMap1);
			amountRateMap.put("amountRateMap2", amountRateMap2);
		}
		return amountRateMap;
	}

	/**
	 * 合同明细所有币别MAP
	 * @param itemList
	 * @return
	 */
	public Map<String, Double[]> getInforItemAmountMap(ContractSignInfor infor, List<?> itemList) {
		if (itemList == null) {
			itemList = this.getInforItemList(infor.getInforId(), infor.getInforType());
		}
		Map<String, Double[]> itemAmountMap = new LinkedHashMap<String, Double[]>();// 所有明细币别
		ContractSignItemBean item;
		for (Object object : itemList) {
			item = (ContractSignItemBean) object;
			String budgetAmountKey = CommonUtil.trim(item.getCurrencyId());
			if (budgetAmountKey.length() > 0) {
				double a = 0, b = 0;
				if (itemAmountMap.containsKey(budgetAmountKey)) {
					a = itemAmountMap.get(budgetAmountKey)[0];
					b = itemAmountMap.get(budgetAmountKey)[1];
				}
				itemAmountMap.put(budgetAmountKey, new Double[] { a + item.getAmount(), b + item.getRmbAmount() });
			}
		}
		return itemAmountMap;
	}

	/**
	 * 获取合同货物明细对象集合
	 * @param user
	 * @param jqGrid
	 * @param parameters
	 * @return
	 */
	public List<?> getInforItemGrid(UserProfile user, JqGrid jqGrid, Map<String, Object> parameters) {
		if (parameters != null && parameters.containsKey("purchaseContractId")) {
			return this.contractSignItem2Dao.findPage(jqGrid, parameters);
		} else {
			return this.contractSignItem1Dao.findPage(jqGrid, parameters);
		}
	}

	public List<?> getInforItemList(String inforId, int inforType) {
		if (inforType == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
			return this.contractSignItem1Dao.findBy("saleContractId", inforId);
		} else {
			return this.contractSignItem2Dao.findBy("purchaseContractId", inforId);
		}
	}

	public List<ContractSignItem1> findSaleInforItemBy(String properName, String propValue) {
		return this.contractSignItem1Dao.findBy(properName, propValue);
	}

	public List<ContractSignItem2> findPurchaseInforItemBy(String properName, String propValue) {
		return this.contractSignItem2Dao.findBy(properName, propValue);
	}

	/**
	 * 获取合同MAP集合
	 * @param signId
	 * @return
	 */
	public Map<String, Object> getContractInforMap(String signId) {
		// 定义MAP
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 销售合同信息
		List<Map<String, Object>> saleContractList = new ArrayList<Map<String, Object>>();
		// 采购合同信息
		List<Map<String, Object>> purchaseContractList = new ArrayList<Map<String, Object>>();
		// 查询参数
		List<ContractSignInfor> inforList = this.contractSignInforDao.queryBy("signId", signId, false, "inforId", true);
		Map<String, Object> contractMap;
		int i = 0, j = 0;
		for (ContractSignInfor contract : inforList) {
			contractMap = new LinkedHashMap<String, Object>();
			contractMap.put("inforId", contract.getInforId());
			contractMap.put("reviewNo", contract.getReviewNo());
			contractMap.put("contractParty", contract.getContractParty());
			if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
				contractMap.put("inforId_index", "saleInforId" + i);
				saleContractList.add(contractMap);
				i++;
			} else {
				contractMap.put("inforId_index", "purchaseInforId" + j);
				purchaseContractList.add(contractMap);
				j++;
			}
		}
		map.put("saleContractSize", saleContractList.size());
		map.put("saleContractList", saleContractList);
		map.put("purchaseContractSize", purchaseContractList.size());
		map.put("purchaseContractList", purchaseContractList);
		return map;
	}

	/**
	 * 获取单个合同签约评审对象
	 * @param inforId
	 * @return
	 */
	public ContractSignInfor getContractSignInfor(String inforId) {
		return this.contractSignInforDao.get(inforId);
	}

	/**
	 * 新建合同签约评审对象
	 * @param user
	 * @return
	 */
	public ContractSignInfor newContractSignInfor(UserProfile user) {
		ContractSignInfor contractSignInfor = new ContractSignInfor();
		return contractSignInfor;
	}

	public static boolean existByLoop(String[] arr, String tarVal) {
		for (String s : arr) {
			if (s.equals(tarVal)) return true;
		}
		return false;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param inforId
	 * @param user
	 * @return
	 */
	public ContractSignInfor initEditOrViewInforPage(String inforId, UserProfile user) {
		ContractSignInfor contractSignInfor;
		if (StringUtils.isBlank(inforId)) {
			contractSignInfor = newContractSignInfor(user);
			contractSignInfor.setAdd(true);
		} else {
			contractSignInfor = this.contractSignInforDao.get(inforId);
			contractSignInfor.setAdd(false);
		}
		return contractSignInfor;
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param contractSignInfor
	 * @param valueMap
	 * @return
	 */
	public ContractSignInfor saveOrUpdate(UserProfile user, ContractSignInfor contractSignInfor, Map<String, Object> valueMap) {
		if (contractSignInfor.getAdd()) {
			this.contractSignInforDao.save(contractSignInfor);
		} else {
			// 避免属性丢失取数据库对象将修改的值覆盖
			contractSignInfor = this.getContractSignInfor(contractSignInfor.getInforId());
			try {
				BeanUtils.populate(contractSignInfor, valueMap);
			} catch (Exception e) {
				System.out.println("saveOrUpdate Error " + e);
			}
			this.contractSignInforDao.update(contractSignInfor);
		}
		// 保存更新金额
		if (valueMap != null && valueMap.containsKey("contractAmountListJson")) {
			saveOrUpdateContractAmount(contractSignInfor, valueMap);
		}
		return contractSignInfor;
	}

	/**
	 * 保存金额
	 * @param contractSignInfor
	 * @param valueMap
	 */
	public void saveOrUpdateContractAmount(ContractSignInfor contractSignInfor, Map<String, Object> valueMap) {
		String inforId = contractSignInfor.getInforId();
		String signId = contractSignInfor.getSignId();
		int inforType = contractSignInfor.getInforType();
		
		// 获取对象list集合JSON字符串
		String contractAmountListJson = valueMap.get("contractAmountListJson").toString();
		Set<String> ignoreProperties = TpcCommonUtil.getIgnoreProperties(ContractSignAmount.class, contractAmountListJson);
		List<ContractSignAmount> list = JsonUtil.toList(contractAmountListJson, ContractSignAmount.class);
		ContractSignAmount saveAmount;
		for (ContractSignAmount amount : list) {
			// 获取数据库记录并替换前台可能修改的属性
			if (amount.getAmountId() == null || amount.getAmountId().contains("rowIdPrev")) {
				amount.setAmountId(null);
				saveAmount = new ContractSignAmount(inforId, signId, inforType, amount.getCurrency(), amount.getCurrencyId());
			} else {
				saveAmount = this.contractSignAmountDao.get(amount.getAmountId());
			}
			TpcCommonUtil.copyWithoutIgnoreProperties(amount, saveAmount, ignoreProperties);
			saveAmount.setInforType(inforType);
			this.contractSignAmountDao.saveOrUpdate(saveAmount);
		}
		// 删除合同金额
		if (valueMap.containsKey("delAmountIds")) {
			String delAmountIds = valueMap.get("delAmountIds").toString();
			if (StringUtils.isNotBlank(delAmountIds)) {
				for (String amountId : delAmountIds.split(",")) {
					this.contractSignDeptAmountDao.deleteByProperty("amountId", amountId);
				}
			}
		}
	}

	/**
	 * 生成评审单号
	 * @return
	 */
	public synchronized String generatorContractSignInforReviewNo() {
		String reviewNo = null;
		Calendar date = Calendar.getInstance();
		String NoHead = "CNEEC" + String.valueOf(date.get(Calendar.YEAR)) + String.valueOf(date.get(Calendar.MONTH) + 1) + String.valueOf(date.get(Calendar.MINUTE));
		Integer count = date.get(Calendar.MILLISECOND);
		String NoEnd = String.format("%03d", (count));
		reviewNo = NoHead + "-" + NoEnd;
		return reviewNo;
	}

	/**
	 * 修改合同金额
	 * @param inforId
	 * @param itemList
	 */
	public void updateContractSignAmountByItem(String inforId, List<?> itemList) {
		Map<String, String> currencyMap = TpcConstant.getCommonCurrencyMap();
		Map<String, String> currencyRateMap = TpcConstant.getCurrencyRateMap();
		ContractSignInfor infor = this.getContractSignInfor(inforId);
		String signId = infor.getSignId();
		int inforType = infor.getInforType();
		if (itemList == null) {
			if (inforType == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("saleContractId", inforId);
				parameters.put("parentItemId", null);
				itemList = this.contractSignItem1Dao.findList(parameters);
			} else {
				itemList = this.findPurchaseInforItemBy("purchaseContractId", inforId);
			}
		}
		if (itemList != null && itemList.size() > 0) {
			Map<String, Double[]> itemAmoutMap = this.getInforItemAmountMap(infor, itemList);
			// 合同原金额项根据货物/服务赋值
			if (itemAmoutMap.size() > 0) {
				// 获取合同已有金额项
				Map<String, ContractSignAmount> amountMap = this.getInforAmountMap(inforId);
				// 设置合同金额
				ContractSignAmount amount = null;
				double totalRmbAmount = 0;
				String currencyId;
				// 遍历明细金额赋值给合同对应币别金额
				for (Map.Entry<String, Double[]> e : itemAmoutMap.entrySet()) {
					currencyId = e.getKey();
					amount = amountMap.get(currencyId);
					if (amount == null) {// 不存在该币别时添加
						amount = new ContractSignAmount(inforId, signId, inforType, currencyMap.get(currencyId), currencyId);
						amount.setRate(Double.parseDouble(currencyRateMap.get(currencyId)));
					}
					amount.setOriginalAmount(itemAmoutMap.get(currencyId)[0]);
					amount.setRmbAmount(itemAmoutMap.get(currencyId)[1]);
					this.contractSignAmountDao.saveOrUpdate(amount);
					amount.getAmountId();
					totalRmbAmount += amount.getRmbAmount();
				}
				infor.setTotalRmbAmount(totalRmbAmount);
				this.contractSignInforDao.update(infor);
			}
		}
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param inforIds
	 */
	public void deleteContractSignInfor(UserProfile user, String inforIds) {
		if (StringUtils.isNotBlank(inforIds)) {
			ContractSignInfor contract;
			for (String inforId : inforIds.split(",")) {
				contract = this.getContractSignInfor(inforId);
				String[] sourceModule;
				if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
					// 删除效益测算表
					String noteId = CommonUtil.trim(contract.getNoteId());
					if (noteId.length() > 0 && contract.isChangeBenefit()) {
						// 效益测算表是新生成的删除
						this.benefitNoteService.delete(user, noteId);
					}
					// 删除销售合同明细及对应采购合同明细
					this.contractSignItem1Dao.deleteByProperty("saleContractId", inforId);
					this.contractSignItem2Dao.deleteByProperty("saleContractId", inforId);
					sourceModule = new String[] {ContractSignReview.MODULE_ID, ContractSignReview.BUSI_TYPE_ORDER, contract.getInforId(), "" };
				} else {
					// 清除销售合同明细的采购合同属性，删除采购合同明细
					this.contractSignItem1Dao.removePurchaseValue(inforId);
					this.contractSignItem2Dao.deleteByProperty("purchaseContractId", inforId);
					sourceModule = new String[] {ContractSignReview.MODULE_ID, ContractSignReview.BUSI_TYPE_CONTRACT, contract.getInforId(), "" };
				}
				FilesUtil.deleteFiles(sourceModule);
				this.contractSignInforDao.delete(contract);
			}
		}
	}

	/**
	 * 批量删除货物服务对象
	 * @param user
	 * @param inforIds
	 */
	public void deleteContractSignItem(UserProfile user, String itemIds, int deleteType) {
		if (StringUtils.isNotBlank(itemIds)) {
			for (String itemId : itemIds.split(",")) {
				String inforId;
				ContractSignItem1 item = this.contractSignItem1Dao.get(itemId);
				if (deleteType == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
					inforId = item.getSaleContractId();
					if (!item.isLeaf()) {// 删除的是一级且存在二级明细
						// 存在二级明细则删除
						this.contractSignItem1Dao.deleteByProperty("parentItemId", item.getItemId());
						this.contractSignItem2Dao.deleteByProperty("parentItemId", item.getItemId());
					} else if (CommonUtil.trim(item.getParentItemId()).length() > 0) {
						ContractSignItem1 parentItem = this.contractSignItem1Dao.get(item.getParentItemId());
						// 删除的是二级明细判断一级明细下是否还有其他二级(仅此1条)，无则修改属性
						int count = this.contractSignItem1Dao.findBy("parentItemId", item.getParentItemId()).size();
						if (count == 1) {
							parentItem.setLeaf(true);
						}
						parentItem.setAmount(parentItem.getAmount() - item.getAmount());
						parentItem.setRmbAmount(parentItem.getRmbAmount() - item.getRmbAmount());
						this.contractSignItem1Dao.update(parentItem);
					}
					// 销售合同明细直接删除
					this.contractSignItem1Dao.delete(item);
				} else {
					// 销售合同明细重置采购合同ID为空
					inforId = item.getPurchaseContractId();
					item.setPurchaseReviewId(null);
					item.setPurchaseContractId(null);
					this.contractSignItem1Dao.update(item);
				}
				// 采购合同明细删除
				this.contractSignItem2Dao.deleteByProperty("itemId", itemId);

				// 更新合同金额
				updateContractSignAmountByItem(inforId, null);
			}
		}
	}

	/**
	 * 选择评审单号select下拉列表
	 * @param projectId
	 * @param type
	 * @return
	 * @throws ParseException 
	 */
	public Map<String, String> getContractSignInforMap(Map<String, Object> parameters, UserProfile userProfile) throws ParseException {
		String authFilter = getAuthFilter(userProfile);
		Map<String, String> inforMap = new LinkedHashMap<String, String>();
		List<ContractSignInfor> inforList = this.contractSignInforDao.getList(parameters, authFilter);
		for (ContractSignInfor infor : inforList) {
			inforMap.put(infor.getInforId(), infor.getReviewNo());
		}
		return inforMap;
	}

	/** 以下是选择签约评审单方法 **/

	// 主表(评审单)
	public ListPage<ContractSignReview> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<ContractSignReview> listPage = new ListPage<ContractSignReview>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.contractSignReviewDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}

	// 从表(销售合同/采购合同信息)
	public ListPage<ContractSignInfor> getInforListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<ContractSignInfor> listPage = new ListPage<ContractSignInfor>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.contractSignInforDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}

	// 主表（评审单）加从表（销售合同/采购合同信息）
	public ListPage<ContractReviewRecord> getRecordListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<ContractReviewRecord> listPage = new ListPage<ContractReviewRecord>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);

		List<ContractReviewRecord> refinedList = new ArrayList<ContractReviewRecord>();

		ListPage<ContractSignReview> reviews = new ListPage<ContractSignReview>();
		reviews.setPageNo(page);
		reviews.setPageSize(pageSize);
		reviews.setAutoCount(true);
		reviews = this.contractSignReviewDao.getListPage(reviews, parameters, authFilter);
		List<ContractSignReview> reviewList = reviews.getRows();
		// 是否展开（默认折叠）
		boolean expand = Boolean.valueOf(String.valueOf(parameters.get("expand")));
		ContractReviewRecord record;
		for (ContractSignReview review : reviewList) {
			// list中记录等于需求记录时不再添加
			if (refinedList.size() == pageSize) {
				break;
			}
			record = new ContractReviewRecord();
			record.setInforId(review.getSignId());
			record.setSignId(review.getSignId());
			record.setReviewNo(review.getReviewNo());
			record.setLevel(0);
			record.setLeaf(false);
			record.setExpanded(expand);
			refinedList.add(record);
			// 获取销售合同/采购合同明细
			parameters.put("signId", review.getSignId());
			ListPage<ContractSignInfor> infors = new ListPage<ContractSignInfor>();
			infors.setPageNo(page);
			infors.setPageSize(pageSize - refinedList.size());// 可添加从表记录只能为总可添加记录减去已添加记录
			infors.setAutoCount(true);
			infors = this.contractSignInforDao.getListPage(infors, parameters, authFilter);
			List<ContractSignInfor> inforList = infors.getRows();
			if (CollectionUtils.isNotEmpty(inforList)) {
				for (ContractSignInfor infor : inforList) {
					record = new ContractReviewRecord();
					BeanUtils.copyProperties(record, infor);
					record.setParent(review.getSignId());
					record.setMontage(review.getReviewNo() + "-" + infor.getContractParty());
					refinedList.add(record);
				}
			} else {
				// 明细为空时清除该评审单
				refinedList.remove(record);
			}
			listPage.setRowCount(listPage.getRowCount() + inforList.size() + 1);
		}
		if (listPage.getRowCount() > 0) {
			listPage.setRowCount(listPage.getRowCount() + 1);
		}
		listPage.setRows(refinedList);
		return listPage;
	}

}
