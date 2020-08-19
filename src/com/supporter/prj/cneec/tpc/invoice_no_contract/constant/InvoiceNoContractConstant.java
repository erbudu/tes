package com.supporter.prj.cneec.tpc.invoice_no_contract.constant;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;

public class InvoiceNoContractConstant {

	public static final String DOMAIN_OBJECT_ID = "INVOICENOENTITY";	//业务对象ID

	public static final String MODULE_NAME = "INVOICENO";		//应用ID
	
	/**
	 * 附件类型
	 */
	public static final String FILE_BUSI_TYPE = "INVOICE_NO_UPLOAD";
	
	/**
	 * 码表编号---发票类型
	 */
	public static final String INVOICE_TYPE_CODE = "INVOICE_NO_TYPE_CODE";
	
	/**
	 * 码表编号---币别
	 */
	public static final String CURRENCY_TYPE_CODE = "CURRENCY_CATEGORY";
	
	/**
	 * 状态---草稿
	 */
	public static final int DRAFT = 0;
	public static final String _DRAFT = "草稿";
	
	/**
	 * 状态---审批中
	 */
	public static final int PROCESSING = 1;
	public static final String _PROCESSING = "审批中";
	
	/**
	 * 状态---审批完成
	 */
	public static final int COMPLETE = 2;
	public static final String _COMPLETE = "审批完成";
	
	/**
	 * 状态---财务补入
	 */
	public static final int FINANCE_DEPARTMENT_ADD = 3;
	public static final String _FINANCE_DEPARTMENT_ADD = "财务补入";
	
	/**
	 * 收完状态------是
	 */
	public static final int END_UP = 1;
	public static final String _END_UP = "是";
	
	/**
	 * 收完状态-------否
	 */
	public static final int NO_END_UP = 0;
	public static final String _NO_END_UP = "否";
	
	/**
	 * 贸易进口关税和增值税
	 */
	public static final String TARIFF = "贸易进口关税和增值税";
	
	/**
	 * 费用支付
	 */
	public static final String SETTLEMENT = "费用支付";

	/**
	 * 数据权限
	 */
	public static final String MODULE_AUTH = "DATA_AUTH";
	
	/**
	 * 获取发票类型码表的ID和显示名称，以Map<String,String>返回
	 * @return
	 */
	public static Map<String,String> getInvoiceType() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(INVOICE_TYPE_CODE);
		Map<String,String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}
	/**
	 * 获取流程状态map
	 * @return
	 */
	public static Map<Integer, String> getProcStatus(){
		Map<Integer ,String> map = new HashMap<Integer, String>();
		map.put(DRAFT, _DRAFT);
		map.put(PROCESSING, _PROCESSING);
		map.put(COMPLETE, _COMPLETE);
		map.put(FINANCE_DEPARTMENT_ADD, _FINANCE_DEPARTMENT_ADD);
		return map;
	}
	
	/**
	 * 获取币别码表内容
	 * @return
	 */	
	public static Map<String, String> getCurrencyType(){
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(CURRENCY_TYPE_CODE);
		Map<String,String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}
	
}


