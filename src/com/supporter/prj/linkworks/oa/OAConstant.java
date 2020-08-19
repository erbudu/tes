package com.supporter.prj.linkworks.oa;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;

public class OAConstant {  
    //码表
  
    public static final String FBOX_CLASS = "FBOX_CLASS";//文档管理注册 所属分类
    public static final String FBOX_SECCLASS = "FBOX_SECCLASS";// 文档管理注册 所属二级分类
    
    
    
    
    //供应商选择控件
    public static final int supplier = 1;//默认选择所有
    public static final int bid = 2;//全省中标发文通知的供应商
	
    //状态
    public static final int UNSUB = 0;// 草稿/执行期
	public static final int VALID = 1;// 生效/中止
	public static final int INVALID = 2;// 失效/结束
	public static final int REPORT = 3;// 已上报
	public static final int DOWN = 4;// 已下达
	public static final int PROCESSING = 5;// 审批中
	public static final int PROCESSED = 6;// 审批完成
	public static final int REPLY = 7;// 已批复
	public static final int COMPLETED = 8;// 完成填报
	public static final int REJECT = 9;// 已驳回
	public static final int UNREPLY = 10;// 未批复
	public static final int STOP = 4;//中止
	public static final int PREVALID = 2;//预生效
	public static final int SECONDINVALID = 12;// 生效后失效
	public static final int NOTSUBMIT = 13;//未提交
	public static final int ALL = 14;//全部
	
	
	
  
    /**
	 * 获取状态的码表.
	 * @return
	 */
	public static Map<Integer, String> getStatusTable() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(UNSUB, "草稿");
		map.put(PREVALID, "预生效");
		map.put(VALID, "生效");
		map.put(STOP, "中止");
		map.put(REPORT, "已上报");
		return map;
	}
	
	//流程状态
	   public static Map<Integer, String> getStatusCodeTable(){
			Map<Integer, String> map = new LinkedHashMap<Integer, String>();
			map.put(ALL, "全部");
			map.put(NOTSUBMIT, "未提交");
			map.put(PROCESSING, "审批中");
			map.put(REPLY, "已审核");
			map.put(REJECT, "被退回");
			return map;
		}

    
	
	//所属分类
	public static Map<String, String> getFboxClassTable() {
		List <IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(FBOX_CLASS);		
		Map<String, String> map = new LinkedHashMap<String, String>();
		for(IComCodeTableItem item : list){
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
		
		
//		Map<String, String> map = new LinkedHashMap<String, String>();
//    	map.put("0", "软件项目");
//    	map.put("1", "内外协合同");  	
//    	return map;
	}
	
	//所属二级分类
	public static Map<String, String> getFboxSecClassTable() {
		List <IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(FBOX_SECCLASS);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for(IComCodeTableItem item : list){
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
		
//		Map<String, String> map = new LinkedHashMap<String, String>();
//    	map.put("0", "合同类型");
//    	map.put("1", "事前呈批");   	
//    	return map;
	}

}  
