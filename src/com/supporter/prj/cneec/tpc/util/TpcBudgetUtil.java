package com.supporter.prj.cneec.tpc.util;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.cneec.tpc.benefit_budget.entity.BenefitBudget;
import com.supporter.prj.cneec.tpc.benefit_budget.service.BenefitBudgetService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.log.LoggerClient;

/**
 * @Title: 贸易项目预算控制
 * @Description: 贸易项目预算工具类
 * 		1.在合同签约前评审完成后生成项目预算
 * 		2.合同签约前评审完成后将采购合同金额转入在途
 * 		3.衍生合同单录入完成将付款金额转入在途
 * 		4.采购/衍生合同上线完成合同金额转入执行预算
 * 		5.销售合同收款/采购合同付款设置正式合同收/付款条款金额
 * 		6.费用支付扣除费用合计下相应预算项预算
 * @author: yanweichao
 * @date: 2018-6-25
 * @version: V1.0
 */
public class TpcBudgetUtil {

	/** 预算变动记录日志表名（最长21位） **/
	public static final String TPC_CONTRACT_SIGN_DEPT_REVIEW = "tpc_con_sign_dept_rv";// 合同签约评审
	public static final String TPC_CONTRACT_SIGN_REVIEW = "tpc_con_sign_review";// 合同签约评审
	public static final String TPC_PURCHASE_CONTRACT_ONLINE = "tpc_contract_online";// 采购合同信息上线
	public static final String TPC_DERIVATIVE_CONTRACT = "tpc_deriv_contract";// 衍生合同
	public static final String TPC_DERIVATIVE_CONTRACT_ONLINE = "tpc_deriv_cont_onli";// 衍生合同信息上线
	public static final String TPC_DERIVATIVE_CONTRACT_CHANGE_ONLINE = "tpc_deriv_cont_change_onli";// 衍生合同变更合同信息上线
	public static final String TPC_DERIVATIVE_CONTRACT_CHANGE = "tpc_deriv_cont_change";// 衍生合同调整或变更
	
	public static final String TPC_CONTRACT_CHANGE_ONLINE = "tpc_cont_change_onli";// 合同变更合同信息上线
	public static final String TPC_CONTRACT_CHANGE = "tpc_cont_change";// 合同调整或变更
	
	public static final String TPC_PRJ_CONTRACT_SETTLEMENT = "tpc_prj_contract_sett";// 采购合同付款
	public static final String TPC_PRJ_SETTLEMENT = "tpc_prj_settlement";// 费用支付
	public static final String TPC_TARIFF_VAT_PAYMENT = "tpc_tariff_vat_pay";// 进口关税增值税
	public static final String TPC_DRAWBACK = "tpc_drawback";// 退税
	
	public static final String TPC_AOS_ADD = "tpc_aos_add";// 预算增加
	public static final String TPC_AOS_SUB = "tpc_aos_sub";// 预算减额

	private static BenefitBudgetService benefitBudgetService;

	public static BenefitBudgetService getBenefitBudgetService() {
		if (benefitBudgetService == null) {
			benefitBudgetService = SpringContextHolder.getBean(BenefitBudgetService.class);
		}
		return benefitBudgetService;
	}

	/**
	 * 根据项目ID,预算ID获取预算项
	 * @param projectId
	 * @param budgetId
	 * @return
	 */
	public static BenefitBudget getBenefitBudget(String projectId, String budgetId) {
		benefitBudgetService = getBenefitBudgetService();
		return benefitBudgetService.getBenefitBudget(projectId, budgetId);
	}

	/**
	 * 添加在途金额
	 * 
	 * @param user
	 * @param projectId
	 *            项目ID
	 * @param budgetId
	 *            预算项ID
	 * @param amount
	 *            金额
	 * @return 遍历前的预算(即第一次调用方法的budgetId对应的预算)
	 */
	public static BenefitBudget addOnwayBudgetAmount(UserProfile user, String projectId, String budgetId, double amount) {
		if (StringUtils.isBlank(projectId) || StringUtils.isBlank(budgetId)) {
			System.out.println(TpcBudgetUtil.class.getName() + ".addOnwayBudgetAmount failed!!!");
			return null;
		}
		BenefitBudget budget = getBenefitBudget(projectId, budgetId);
		// 在途预算添加
		budget.setOnWayAmount(BigDecimal.valueOf(budget.getOnWayAmount()).add(BigDecimal.valueOf(amount)).doubleValue());
		// 可用预算减少
		budget.setAvailableBudgetAmount(BigDecimal.valueOf(budget.getAvailableBudgetAmount()).subtract(BigDecimal.valueOf(amount)).doubleValue());
		benefitBudgetService.updateBenefitBudget(budget);
		// 有父级预算需将父级预算也添加在途
		if (StringUtils.isNotBlank(budget.getParentBudgetId())) {
			addOnwayBudgetAmount(user, projectId, budget.getParentBudgetId(), amount);
		}
		return budget;
	}

	/**
	 * 在途金额增加日志信息
	 * @param objects
	 * @return
	 */
	public static String addOnwayBudgetAmountMsg(Object... objects) {
		return MessageFormat.format("在途金额增加{0}", objects);
	}

	/**
	 * 加在途
	 */
	public static void addOnwayBudgetAmount(String innerName, UserProfile user, String projectId, String budgetId, double amount) {
		BenefitBudget budget = addOnwayBudgetAmount(user, projectId, budgetId, amount);
		LoggerClient.info(innerName, addOnwayBudgetAmountMsg(amount), user, budget, budget.getId());
	}

	/**
	 * 移除在途金额
	 * 
	 * @param user
	 * @param projectId
	 *            项目ID
	 * @param budgetId
	 *            预算项ID
	 * @param amount
	 *            金额
	 * @return 遍历前的预算(即第一次调用方法的budgetId对应的预算)
	 */
	public static BenefitBudget removeOnwayBudgetAmount(UserProfile user, String projectId, String budgetId, double amount) {
		if (StringUtils.isBlank(projectId) || StringUtils.isBlank(budgetId)) {
			System.out.println(TpcBudgetUtil.class.getName() + ".removeOnwayBudgetAmount failed!!!");
			return null;
		}
		BenefitBudget budget = getBenefitBudget(projectId, budgetId);
		// 在途预算减少
		budget.setOnWayAmount(BigDecimal.valueOf(budget.getOnWayAmount()).subtract(BigDecimal.valueOf(amount)).doubleValue());
		// 可用预算增加
		budget.setAvailableBudgetAmount(BigDecimal.valueOf(budget.getAvailableBudgetAmount()).add(BigDecimal.valueOf(amount)).doubleValue());
		benefitBudgetService.updateBenefitBudget(budget);
		// 有父级预算需将父级预算也移除在途
		if (StringUtils.isNotBlank(budget.getParentBudgetId())) {
			removeOnwayBudgetAmount(user, projectId, budget.getParentBudgetId(), amount);
		}
		return budget;
	}

	/**
	 * 在途金额减少日志信息
	 * @param objects
	 * @return
	 */
	public static String removeOnwayBudgetAmountMsg(Object... objects) {
		return MessageFormat.format("在途金额减少{0}", objects);
	}

	/**
	 * 移除在途
	 */
	public static void removeOnwayBudgetAmount(String innerName, UserProfile user, String projectId, String budgetId, double amount) {
		BenefitBudget budget = removeOnwayBudgetAmount(user, projectId, budgetId, amount);
		LoggerClient.info(innerName, removeOnwayBudgetAmountMsg(amount), user, budget, budget.getId());
	}

	/**
	 * 将在途金额改为执行金额
	 * 
	 * @param user
	 * @param projectId
	 *            项目ID
	 * @param budgetId
	 *            预算项ID
	 * @param amount
	 *            金额
	 * @return 遍历前的预算(即第一次调用方法的budgetId对应的预算)
	 */
	public static BenefitBudget transferToExecute(UserProfile user, String projectId, String budgetId, double amount) {
		if (StringUtils.isBlank(projectId) || StringUtils.isBlank(budgetId)) {
			System.out.println(TpcBudgetUtil.class.getName() + ".transferToExecute failed!!!");
			return null;
		}
		BenefitBudget budget = getBenefitBudget(projectId, budgetId);
		// 在途预算减少
		budget.setOnWayAmount(BigDecimal.valueOf(budget.getOnWayAmount()).subtract(BigDecimal.valueOf(amount)).doubleValue());
		// 执行预算增加
		budget.setExecuteAmount(BigDecimal.valueOf(budget.getExecuteAmount()).add(BigDecimal.valueOf(amount)).doubleValue());
		benefitBudgetService.updateBenefitBudget(budget);
		// 有父级预算需将父级预算也改为执行金额
		if (StringUtils.isNotBlank(budget.getParentBudgetId())) {
			transferToExecute(user, projectId, budget.getParentBudgetId(), amount);
		}
		return budget;
	}
	
	/**
	 * 销售合同预算调整完成后，调整预算.
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param aosType  是增加还是减少；
 	 * @param amount  传入正数.
	 * @return
	 */
	public static BenefitBudget adjustBenfitBudget(UserProfile user, String projectId, String budgetId,String aosType ,double amount){
		if (StringUtils.isBlank(projectId) || StringUtils.isBlank(budgetId)) {
			System.out.println(TpcBudgetUtil.class.getName() + ".transferToExecute failed!!!");
			return null;
		}
		BenefitBudget budget = getBenefitBudget(projectId, budgetId);
		//预算增加
		if(aosType.equals(TpcBudgetUtil.TPC_AOS_ADD)){
			budget.setTotalBudgetAmount(BigDecimal.valueOf(budget.getTotalBudgetAmount()).add(BigDecimal.valueOf(amount)).doubleValue());
			budget.setAvailableBudgetAmount(BigDecimal.valueOf(budget.getAvailableBudgetAmount()).add(BigDecimal.valueOf(amount)).doubleValue());
		}
		//预算减额
		if(aosType.equals(TpcBudgetUtil.TPC_AOS_SUB)){
			budget.setTotalBudgetAmount(BigDecimal.valueOf(budget.getTotalBudgetAmount()).subtract(BigDecimal.valueOf(amount)).doubleValue());
			budget.setAvailableBudgetAmount(BigDecimal.valueOf(budget.getAvailableBudgetAmount()).subtract(BigDecimal.valueOf(amount)).doubleValue());
		}
		benefitBudgetService.updateBenefitBudget(budget);
		return budget;
	}
	

	/**
	 * 在途金额转入执行日志信息
	 * @param objects
	 * @return
	 */
	public static String transferToExecuteMsg(Object... objects) {
		return MessageFormat.format("将在途金额{0}转入执行金额。", objects);
	}

	/**
	 * 在途转入执行
	 */
	public static void transferToExecute(String innerName, UserProfile user, String projectId, String budgetId, double amount) {
		BenefitBudget budget = transferToExecute(user, projectId, budgetId, amount);
		LoggerClient.info(innerName, transferToExecuteMsg(amount), user, budget, budget.getId());
	}
	
	/**
	 * 将在执行金额减少
	 * 
	 * @param user
	 * @param projectId
	 *            项目ID
	 * @param budgetId
	 *            预算项ID
	 * @param amount
	 *            金额
	 * @return 遍历前的预算(即第一次调用方法的budgetId对应的预算)
	 */
	public static BenefitBudget removeExecuteBudgetAmount(UserProfile user, String projectId, String budgetId, double amount) {
		if (StringUtils.isBlank(projectId) || StringUtils.isBlank(budgetId)) {
			System.out.println(TpcBudgetUtil.class.getName() + ".removeExecuteBudgetAmount failed!!!");
			return null;
		}
		BenefitBudget budget = getBenefitBudget(projectId, budgetId);
		// 执行预算减少
		budget.setExecuteAmount(BigDecimal.valueOf(budget.getExecuteAmount()).subtract(BigDecimal.valueOf(amount)).doubleValue());
		// 可用预算增加
		budget.setAvailableBudgetAmount(BigDecimal.valueOf(budget.getAvailableBudgetAmount()).add(BigDecimal.valueOf(amount)).doubleValue());
		benefitBudgetService.updateBenefitBudget(budget);
		// 有父级预算需将父级预算也改为执行金额
		if (StringUtils.isNotBlank(budget.getParentBudgetId())) {
			removeExecuteBudgetAmount(user, projectId, budget.getParentBudgetId(), amount);
		}
		return budget;
	}
	
	
	/**
	 * 在执行预算扣减执行日志信息
	 * @param objects
	 * @return
	 */
	public static String removeExecuteBudgetAmounteMsg(Object... objects) {
		return MessageFormat.format("将预算执行金额减少{0}。", objects);
	}

	/**
	 * 执行预算扣减
	 */
	public static void removeExecuteBudgetAmount(String innerName, UserProfile user, String projectId, String budgetId, double amount) {
		BenefitBudget budget = removeExecuteBudgetAmount(user, projectId, budgetId, amount);
		LoggerClient.info(innerName, removeExecuteBudgetAmounteMsg(amount), user, budget, budget.getId());
	}

	/**
	 * 获取项目下所有预算
	 * @param projectId
	 * @return
	 */
	public static List<BenefitBudget> getBenefitBudgetList(String projectId) {
		benefitBudgetService = getBenefitBudgetService();
		return benefitBudgetService.getBenefitBudgetList(projectId);
	}

	/**
	 * 获取项目下所有预算MAP
	 * @param projectId
	 * @return
	 */
	public static Map<String, BenefitBudget> getBenefitBudgetMap(String projectId) {
		Map<String, BenefitBudget> budgetMap = new LinkedHashMap<String, BenefitBudget>();
		List<BenefitBudget> budgetList = getBenefitBudgetList(projectId);
		for (BenefitBudget benefitBudget : budgetList) {
			budgetMap.put(benefitBudget.getBudgetId(), benefitBudget);
		}
		return budgetMap;
	}

	/**
	 * 获取项目预算相关金额（预算总金额、已用金额、可用金额）
	 * 
	 * @author 田森
	 * @param List
	 * @return
	 */
	public static List<Double> getBudgetById(String prjId, String itemId) {
		benefitBudgetService = getBenefitBudgetService();
		List<Double> list = new ArrayList<Double>();
		BenefitBudget benefitBudget = benefitBudgetService.getBenefitBudget(prjId, itemId);
		double budgetAmount = benefitBudget.getTotalBudgetAmount();
		double availableAmount = benefitBudget.getAvailableBudgetAmount();
		double usableAmount = budgetAmount - availableAmount;
		list.add(budgetAmount);// 预算总金额
		list.add(usableAmount);// 预算已用金额（包括在途金额、执行金额）
		list.add(availableAmount);// 预算可用金额
		return list;
	}

	/**
	 * 获取项目预算金额
	 * 
	 * @author 田森
	 * @param List
	 * @return
	 */
	public static double getBudgetAmount(String prjId, String itemId) {
		benefitBudgetService = getBenefitBudgetService();
		BenefitBudget benefitBudget = benefitBudgetService.getBenefitBudget(prjId, itemId);
		double budgetAmount = benefitBudget.getTotalBudgetAmount();
		return budgetAmount;
	}

	/**
	 * 获取项目预算可用金额
	 * 
	 * @author 田森
	 * @return
	 */
	public static double getBudgetAvailableAmount(String prjId, String itemId) {
		benefitBudgetService = getBenefitBudgetService();
		BenefitBudget benefitBudget = benefitBudgetService.getBenefitBudget(prjId, itemId);
		double availableAmount = benefitBudget.getAvailableBudgetAmount();
		return availableAmount;
	}

	/**
	 * 获取项目预算已用金额（包括在途金额、执行金额）
	 * 
	 * @author 田森
	 * @return
	 */
	public static double getBudgetUsableAmountAmount(String prjId, String itemId) {
		benefitBudgetService = getBenefitBudgetService();
		BenefitBudget benefitBudget = benefitBudgetService.getBenefitBudget(prjId, itemId);
		double budgetAmount = benefitBudget.getTotalBudgetAmount();
		double availableAmount = benefitBudget.getAvailableBudgetAmount();
		double usableAmount = budgetAmount - availableAmount;
		return usableAmount;
	}

}
