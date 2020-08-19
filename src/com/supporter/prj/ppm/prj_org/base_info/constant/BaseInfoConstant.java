/**
 * 
 */
package com.supporter.prj.ppm.prj_org.base_info.constant;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.util.CodeTable;

/**
 *<p>Title: 项目基本信息常量类</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月25日
 */
public  final class  BaseInfoConstant {
	
	public static final String MODULE_ID = "PRJBASE";
	
	/**应用权限*/
	public static final String MODULE_AUTH = "TOVIEW";
	
	/**编辑权限*/
	public static final String MODULE_AUTH_EDIT = "TOEDIT";
	
	//------------------以下为项目开发状态信息----------------------------//
	/**项目处于关闭状态*/
	public static final int CLOSE = 0;
	/**项目处于激活状态*/
	public static final int ACTIVE = 1;
	/**草稿状态*/
	public static final int DRFT = 2;
	/**已确认呢*/
	public static final int CONFIRMED = 3;
	
	public static Map<Integer,String> getActiveDesc(){
		
	Map<Integer,String> activeStatus = new HashMap<Integer, String>();
		activeStatus.put(CLOSE, "关闭");
		activeStatus.put(ACTIVE, "激活");
		activeStatus.put(DRFT, "草稿");
		activeStatus.put(CONFIRMED, "已确认");
		return activeStatus;
	}
	
	
	//----------------------以下信息在码表中配置---------------↓------↓--------//
	
	/**
	 * <pre>全球区域码表编号；码表类别</pre>
	 */
	public static final String CODETABLE_ADDR = "PPM_PRJ_ADDR_AREA";
	
	/**
	 * <pre>全球区域码表编号；子项国家</pre>
	 */
	public static final String CODETABLE_ITME = "PPM_PRJ_ADDR_COUNTRY";
	
	/**
	 * <p>出口管制受制裁国家码表信息</p>
	 */
	public static final String CODETABLE_PPM_CONTROLS_COUNTRY = "PPM_PRJ_CONTROLS_COUNTRY";
	
	//---------------以下角色信息是在角色中配置的--------------↓------↓-------//
	/**
	 * <p>部门：经营管理部-部门编号</p>
	 */
	public static final String MANAGEMENT_DEPARTMENT = "48876683";
	
	/**
	 * <pre>角色：经营管理部-项目信息记录员</pre>
	 */
	public static final String ROLE_RECORDER = "BASEINFORECORDER";
	
	/**
	 * <pre>角色：经营管理部-投议标备案及许可人员</pre>
	 */
	public static final String ROLE_BIDFILING = "BIDFILING";
	
	/**
	 * <pre>角色：经营管理部-经营管理部外派事业部经理</pre>
	 */
	public static final String ROLE_ExpatriateManagers = "BUSINESSMANAGERTOBUSINESS";
	
	
	/**
	 * <pre>角色：事业部-出口管制专员</pre>
	 */
	public static final String ROLE_EXPORTCONTROL = "ROLE_EXPORTCONTROL";
	
	//----------------------以下为获取码表中配置的信息---------------↓------↓--------//
	
	/**
	 * <p>获取受制裁国家码表信息MAP</p>
	 * @return 受制裁国家码表信息MAP
	 */
	public static Map<String, String> getControlsCountryMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(CODETABLE_PPM_CONTROLS_COUNTRY);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				item.getExtField1();
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;
	}
	
	/**
	 * <pre>根据码表类别系统编号和码表项父项的系统编号获取码表信息</pre>
	 * @return
	 */
	public static Map<String, String> getCountryMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getSubItems(CODETABLE_ADDR, CODETABLE_ITME);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;
	}
	
	/**
	 * <pre>根据码类别系统编号和码表项的父级系统编号获取所有子项</pre>
	 * @param itmeId 父级系统编号
	 * @return
	 */
	public static Map<String, String> getCityMap(String itmeId) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (itmeId.length() > 0) {
			List<IComCodeTableItem> list = EIPService.getComCodeTableService().getSubItems(CODETABLE_ADDR, itmeId);
			if (list != null && list.size() > 0) {
				for (IComCodeTableItem item : list) {
					map.put(item.getItemId(), item.getDisplayName());
				}
			}
		}
		return map;
	}

}
