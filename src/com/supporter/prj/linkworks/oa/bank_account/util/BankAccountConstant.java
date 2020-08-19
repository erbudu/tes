package com.supporter.prj.linkworks.oa.bank_account.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;

/**
 * 
 * @Title: BankAccountConstant
 * @Description: 贸易项目通用常量
 * @author: yanweichao
 * @date: 2017-11-9
 * @version: V1.0
 */
public class BankAccountConstant {
	//应用Id
	public static final String BANK_ACCOUNT_OPEN_MODULE_ID = "BAOPENAPPLY";
	public static final String BANK_ACCOUNT_CHAN_MODULE_ID = "BACHANAPPLY";
	public static final String BANK_ACCOUNT_REVO_MODULE_ID = "BAREVOAPPLY";



	/** 币别 **/
	public static final String CURRENCY_CATEGORY = "CURRENCY_CATEGORY";
	
	// 币别编码+币别名称
	public static Map<String, String> getCurrencyMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 取码表项管理中“币别”
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(CURRENCY_CATEGORY);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				// 币别编码+币别名称
				map.put("", "--请选择--");
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;
	}
	

	// 币别编码+币别汇率
	public static Map<String, String> getCurrencyRateMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 取码表项管理中“币别”
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(CURRENCY_CATEGORY);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				// 币别编码+币别汇率
				map.put(item.getExtField3(), item.getExtField2());
			}
		}
		return map;
	}

	/** 常用币别 **/
	public static final String STANDARD_CURRENCY = "CNY";
	public static final String CURRENCY1 = "CNY";
	public static final String CURRENCY2 = "USD";
	public static final String CURRENCY3 = "EUR";
	public static final String CURRENCY4 = "GBP";
	public static final String CURRENCY5 = "JPY";

	public static Map<String, String> getCommonCurrencyMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(CURRENCY1, "人民币");
		map.put(CURRENCY2, "美元");
		map.put(CURRENCY3, "欧元");
		map.put(CURRENCY4, "英镑");
		map.put(CURRENCY5, "日元");
		return map;
	}	
	
	/**
	 * 账户开立申请/账户单位性质
	 */
	public static final String BANK_ACCOUNT_OPEN_COMPANY = "BANK_ACCOUNT_OPEN_COMPANY";
	/**
	 * 账户开立申请/账户单位性质下拉
	 */
	public static Map<String, String> getAccountPropertyOfOpenMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 取码表项管理中“银行账号账户单位性质”
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(BANK_ACCOUNT_OPEN_COMPANY);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				// 部门ID+部门名称
				map.put("", "--请选择--");
				map.put(item.getItemId(), item.getItemValue());
			}
		}
//		map.put(PARENT_COMPANY, "公司总部");
//		map.put(FILIALE_COMPANY, "分公司");
//		map.put(SUB_COMPANY, "子公司");
		return map;
	}
	
	
	/** 账户开立申请-账户性质 **/
	public static final String BANK_ACCOUNT_OPEN_ACCOUNT = "BANK_ACCOUNT_OPEN_ACCOUNT";

	/**
	 * 账户开立申请/账户性质下拉
	 */
	public static Map<String, String> getAccountNatureOfOpenMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 取码表项管理中“银行账号账户性质”
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(BANK_ACCOUNT_OPEN_ACCOUNT);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				// 部门ID+部门名称
				map.put(item.getItemId(), item.getItemValue());
			}
		}
//		map.put(BASIC_ACCOUNT, "基本账户");
//		map.put(BAIL_ACCOUNT, "保证金账户");
		return map;
	}
	
	/** 账户开立申请-开户银行一级名称 **/
	public static final String BANK_ACCOUNT_OPEN_FRIST_N = "BANK_ACCOUNT_OPEN_FRIST_N";

	/**
	 * 账户开立申请/开户银行一级名称
	 */
	public static Map<String, String> getOpenBankFirstNameMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 取码表项管理中“银行账号账户性质”
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(BANK_ACCOUNT_OPEN_FRIST_N);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				map.put("", "--请选择--");
				map.put(item.getItemId(), item.getItemValue());
			}
		}
//		map.put(BASIC_ACCOUNT, "基本账户");
//		map.put(BAIL_ACCOUNT, "保证金账户");
		return map;
	}
	
	
	/** 账户开立申请-账户属性 **/
	public static final String BANK_ACCOUNT_OPEN_PRO = "BANK_ACCOUNT_OPEN_PRO";

	/**
	 * 账户开立申请/账户属性
	 */
	public static Map<String, String> getAccountProMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 取码表项管理中“银行账号账户性质”
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(BANK_ACCOUNT_OPEN_PRO);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				// 部门ID+部门名称
				map.put(item.getItemId(), item.getItemValue());
			}
		}
//		map.put(BASIC_ACCOUNT, "基本账户");
//		map.put(BAIL_ACCOUNT, "保证金账户");
		return map;
	}
	
	
	
	
	/** 账户开立申请-国别 **/
	public static final String ALL_COUNTRIES = "ALL_COUNTRIES";

	public static Map<String, String> getNationalityOfOpenMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 取码表项管理中“世界国家”
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(ALL_COUNTRIES);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				// 部门ID+部门名称
				map.put(item.getItemId(), item.getItemValue());
			}
		}
//		map.put("C-001", "中国");
//		map.put("C-002", "美国");
//		map.put("C-003", "俄罗斯");
		return map;
	}



}
