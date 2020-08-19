package com.supporter.prj.ppm.prj_org.member_duty.constant;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;

public class MemberDutyConstant {

	/**
	 * 	草稿
	 */
	public static final int DRAFT = 0;
	
	/**
	 * 应用名称
	 */
	public static final String MODULE_NAME = "";
	
	/**
	 * 负责业务码表编号
	 */
	public static final String RESPONSIBLE = "PPM_PRJ_BUSINESS_TABLE";
	
	/**
	 *   	码表数据： 成员角色
	 */
	public static final String PPM_MEMBER_ROLE = "PPM_MEMBER_ROLE";
	
	/**
	 * 		此方法用于获取项目开发工作组成员，成员角色下拉选数据
	 * @return 成员角色
	 */
	public static Map<String, String> getMemberRoleOfSelect() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(PPM_MEMBER_ROLE);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}
	
	
	/**
	 * 获取负责业务下拉列表内容
	 * @return
	 */
	public static List<Map<String,String>> getResponsible() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(RESPONSIBLE);
		List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				Map<String, String> map = new LinkedHashMap<String, String>();
				map.put("id", item.getItemId());
				map.put("describe", item.getDisplayName());
				map.put("value", item.getItemId());
				map.put("name", "responsible");
				resultList.add(map);
			}
		}
		return resultList;
	}
	
	/**
	 * 根据业务id获取业务名称
	 * @param responsible
	 * @return
	 */
	public static String getResponsibleDisplayName(String responsible) {
		
		if(responsible == null || responsible == "") {
			
			return null;
		}
		
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(RESPONSIBLE);
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		for (IComCodeTableItem item : list) {
		
			map.put(item.getItemId(), item.getDisplayName());
		}
		StringBuilder responsibleDisplayName = new StringBuilder();
		
		for(String id : responsible.split(",")) {
			
			responsibleDisplayName.append(map.get(id)).append(",");
		}
		return responsibleDisplayName.toString().substring(0,responsibleDisplayName.toString().length() - 1);
	}
}
