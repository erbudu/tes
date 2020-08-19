/**
 * 
 */
package com.supporter.prj.linkworks.oa.overseas_bimonthly_report.constant;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *<p>Title: OverseasBimonthlyreportConstant</p>
 *<p>Description: 境外双月报审批 常量类 </p>
 *<p>Company: 东华后盾</p>
 * @author YUEYUN
 * @date 2019年12月23日
 * 
 */
public final class OverseasBimonthlyReportConstant {
	
	/**应用编号*/
	public final static String 	MODULE_ID = "OVBIMREPORT";
	/**业务对象编号*/
	public final static String 	DOMAIN_OBJECT_ID = "OVBIMREPORTOBJ";
	/**编辑权限*/
	public final static String 	DATA_AUTH = "DATA_AUTH";
	/**编辑权限*/
	public final static String 	EDIT_AUTH = "EDIT_AUTH";
	/*审批状态*/
	public final static Integer DRAFT = 0; 
	public final static Integer AUDITING = 1;
	public final static Integer COMPLETE = 2;
	
	/**用流程状态码表值获取码表显示名称*/
	public static String stutsDesc(Integer status) {
		
		return statusCode().get(status);
		
	}
	
	/**流程状态码表*/
	public static Map<Integer,String> statusCode(){
		
		Map<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.put(-1, "全部状态");
		map.put(DRAFT, "草稿");
		map.put(AUDITING, "审批中");
		map.put(COMPLETE, "审批完成");
		return map;
		
	}

}
