package com.supporter.prj.cneec.tpc.benefit_budget.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.supporter.prj.cneec.tpc.benefit_budget.constant.BenefitBudgetConstant;
import com.supporter.prj.cneec.tpc.benefit_budget.constant.BenefitBudgetItemConstant;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.util.CommonUtil;

/**
 * @Title: BenefitBudgetUtil
 * @Description: 贸易效益测算表工具类
 * @author: yanweichao
 * @date: 2018-05-08
 * @version: V1.0
 */
public class BenefitBudgetUtil {

	/**
	 * 获取预算说明
	 * 
	 * @param budgetTypeCode
	 * @return
	 */
	public static Map<String, String> getBudgetNameMap(String budgetTypeCode) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<IComCodeTableItem> list;
		if (CommonUtil.trim(budgetTypeCode).length() > 0) {
			list = EIPService.getComCodeTableService().getSubItems(BenefitBudgetItemConstant.BENEFIT_BUDGET, budgetTypeCode);
		} else {
			list = EIPService.getComCodeTableService().getCodeTableItems(BenefitBudgetItemConstant.BENEFIT_BUDGET);
		}
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				// 预算项id+预算项名称
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;
	}

	/** 以下：项目效益测算/预算汇总统一格式化预算金额等，避免修改时需要到每个相关模块都改一遍 **/

	/**
	 * 是否特殊预算项(币别无值，通过其他预算计算计入折合人民币值)
	 * 
	 * @param budgetName
	 * @param budgetAmount
	 * @return
	 */
	public static boolean isSpecialBudget(String budgetName) {
		budgetName = CommonUtil.trim(budgetName);
		// 无预算金额的预算项（毛利、毛利率、预计盈亏额、利润率），设为-1
		if (budgetName.contains("毛利") || budgetName.contains("预计盈亏额") || budgetName.contains("利润率")) {
			return true;
		}
		return false;
	}

	/**
	 * 获取预算项默认金额
	 * 
	 * @param budgetName
	 * @param budgetAmount
	 * @return
	 */
	public static double getDefaultBudgetAmount(String budgetName, double budgetAmount) {
		// 无预算金额的预算项，设为-1
		if (isSpecialBudget(budgetName)) {
			return BenefitBudgetConstant.DEFAULT_VALUE;
		}
		return budgetAmount;
	}

	/**
	 * 获取默认占比
	 * 
	 * @param budgetName
	 * @param orderDisplay
	 * @param proportion
	 * @return
	 */
	public static double getDefaultProportion(String budgetName, int orderDisplay, double proportion) {
		// 第四大项及以后不需要占比
		if (orderDisplay >= (3 * BenefitBudgetConstant.ORDER_DISPLAY_ONE)) {
			return BenefitBudgetConstant.DEFAULT_VALUE;
		}
		return proportion;
	}

	/** 格式化属性 **/

	/**
	 * 格式化预算金额
	 * 
	 * @param budgetAmount
	 * @return
	 */
	public static String getBudgetAmountFormat(double budgetAmount) {
		if (budgetAmount == BenefitBudgetConstant.DEFAULT_VALUE) {
			return BenefitBudgetConstant.DEFAULT_VALUE_STR;
		} else {
			return CommonUtil.format(budgetAmount, "#,##0.00");
		}
	}

	/**
	 * 预算折合人民币是否统计为百分比
	 * 
	 * @param budgetName
	 * @return
	 */
	public static boolean isPercentBudget(String budgetName) {
		budgetName = CommonUtil.trim(budgetName);
		// 统计为百分比（毛利率、利润率）
		if (budgetName.contains("毛利率") || budgetName.contains("利润率")) {
			return true;
		}
		return false;
	}

	/**
	 * 格式化折人民币合计（根据预算是否为百分比）
	 * 
	 * @param budgetName
	 * @param totalRmbAmount
	 * @return
	 */
	public static String getTotalRmbAmountFormat(String budgetName, double totalRmbAmount) {
		if (isPercentBudget(budgetName)) {
			return CommonUtil.format(totalRmbAmount * 100, "#0.00") + "%";
		} else {
			return CommonUtil.format(totalRmbAmount, "#,##0.00");
		}
	}

	/**
	 * 格式化占比
	 * 
	 * @param proportion
	 * @return
	 */
	public static String getProportionFormat(double proportion) {
		if (proportion == BenefitBudgetConstant.DEFAULT_VALUE) {
			return BenefitBudgetConstant.DEFAULT_VALUE_STR;
		} else {
			return CommonUtil.format(proportion * 100, "#0.00") + "%";
		}
	}

	/** 格式化前台展示属性 **/

	/**
	 * 前端展示
	 * 
	 * @return
	 */
	public static String replaceToForeground(String value) {
		String defVal = BenefitBudgetConstant.DEFAULT_VALUE_STR;
		return CommonUtil.trim(value).replace(defVal, "<center>" + defVal + "</center>");
	}

	/** 预算项基础属性 **/
	public static final String _serialNumber = "serialNumber";
	public static final String _budgetId = "budgetId";
	public static final String _budgetName = "budgetName";
	public static final String _level = "level";
	public static final String _isLeaf = "isLeaf";
	public static final String _parent = "parent";
	public static final String _expanded = "expanded";

	/**
	 * 定义基础成员属性Map
	 * 
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> getBaseAssembleMap(BenefitAssembleBean bean) {
		Map<String, Object> beanMap = new HashMap<String, Object>();
		beanMap.put(_serialNumber, bean.getSerialNumber());
		beanMap.put(_budgetId, bean.getBudgetId());
		beanMap.put(_budgetName, bean.getBudgetName());
		beanMap.put(_level, bean.getLevel());
		beanMap.put(_isLeaf, bean.getIsLeaf());
		beanMap.put(_parent, String.valueOf(bean.getParent()));// JSON格式数据null会转化为"",前台需要的是null
		beanMap.put(_expanded, bean.isExpanded());
		return beanMap;
	}

}
