/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.preparation.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.util.CodeTable;


/**
 *<p>Title: StartContant</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年8月23日
 * 
 */
public class StartContant {  
	
	//码表编号
	public static final String LETTERS_TYPE = "E_LETTERS_TYPE";//函件类型
	
	/*以下为应用相关信息*/
	/**
	 * <p>应用编号：申报准备</p>
	 */
	public static final String MODULE_ID = "BIDREPARATIO";//应用编号
	/**
	 * <p>码表编号</p>
	 */
	public static final String CODETABLE_ID = "PPM_BIDSTARTUP_BFD";//码表编号-资料清单码表
	/**
	 * <p>业务对象编号</p>
	 */
	public static final String DOMAIN_OBJECT_ID = "BIDING_STARTUP";//业务对象编号
	
	/**
	 * <p>用印业务</p>
	 */
	public static final String USE_SEAL_BUSINESS = "投议标备案及许可-申报准备";
	
	public static final String FILE_UP_ID = "";
	/*以下为流程状态用到的常量*/
	/**
	 * 	草稿
	 */
	public static final int DRAFT = 0;
	/**
	 * 	 审批中 
	 */
    public static final int PROCESSING = 1; 
    /**
     * 	   审批完成
     */
    public static final int COMPLETE = 2; 
    
    /**
     * 	  已启动用印流程
     */
    public static final int START_UP_PRINT = 3; 	
	
    /*以下为是否向大使馆致函码表信息·用于在查看页面显示*/
	/*key*/
	public static final int YES = 0;
	public static final int NO = 1;
	/*value*/
	public static final String YESDisplayName = "是";
	public static final String NODisplayName = "否";
	/*map*/
	public static Map<Integer,String> getIsSendELettersCodeTab(){
		Map<Integer,String> map = new HashMap<Integer, String>();
		map.put(YES, "是");
		map.put(NO, "否");
		return map;
	}
	
	public static String getBfdTypeName(String id){
		
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(CODETABLE_ID);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map.get(id);
		
	}
	
	/**
	 * <pre>
	 *  启动申报页面的资料清单数据-弃用
	 * </pre>
	 * @return
	 */
	public static List<Map<String, String>> dataList() {
		
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map <String,String> map1 = new HashMap<String,String>();
		map1.put("informationId", "0");
		map1.put("informationTitle", "《项目许可申请报告》");
		map1.put("moduleName", "NETIN");
		map1.put("basiType", "NETIN");
		map1.put("readOnly", "F");
		list.add(map1);
		Map <String,String> map2 = new HashMap<String,String>();
		map2.put("informationId", "1");
		map2.put("informationTitle", "《对外承包工程项目投标(议标)许可申请表》");
		map2.put("moduleName", "OAREPORT");
		map2.put("basiType", "OAREPORT");
		map2.put("readOnly", "F");
		list.add(map2);
		Map <String,String> map3 = new HashMap<String,String>();
		map3.put("informationId", "2");
		map3.put("informationTitle", "《我驻外、使领馆经商参处(室)对我企业参与该项目投标(议标)的书面意见》");
		map3.put("moduleName", "");
		map3.put("basiType", "");
		map3.put("readOnly", "F");
		list.add(map3);
		Map <String,String> map4 = new HashMap<String,String>();
		map4.put("informationId", "3");
		map4.put("informationTitle", "《外经贸部要求提供的其他材料》");
		map4.put("moduleName", "");
		map4.put("basiType", "");
		map4.put("readOnly", "F");
		list.add(map4);
		Map <String,String> map5 = new HashMap<String,String>();
		map5.put("informationId", "4");
		map5.put("informationTitle", "《企业上两个会计年度经会计师事务所审计的资产负债表、损益表》");
		map5.put("moduleName", "");
		map5.put("basiType", "");
		map5.put("readOnly", "F");
		list.add(map5);
		Map <String,String> map6 = new HashMap<String,String>();
		map6.put("informationId", "5");
		map6.put("informationTitle", "《有关商会的书面协调意见》");
		map6.put("moduleName", "");
		map6.put("basiType", "");
		map6.put("readOnly", "F");
		list.add(map6);
		return list;
	}
	
	/**
	 * 函件类型
	 * @return
	 */
	public static Map<String, String> getLettersType() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(LETTERS_TYPE);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;
	}
}
