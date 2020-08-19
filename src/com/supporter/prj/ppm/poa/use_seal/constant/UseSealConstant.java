/**
 * 
 */
package com.supporter.prj.ppm.poa.use_seal.constant;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;

/**
 *<p>Title: UseSealConstant</p>
 *<p>Description: 用印业务中用到的常量</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年10月18日
 * 
 */
public final class UseSealConstant {

	/**
	 * <p>用印业务-应用编号</p>
	 */
	public static final String MODULE_ID = "USESEAL";
	
	/**
	 * <p>用印业务-业务对象编号</p>
	 */
	public static final String DOMAIN_OBJECT_ID = "USESEALENTITY";
	
	/**
	 * <p>用印角色-用印管理员</p>
	 */
	public static final String USE_SEAL_ADMIN = "use_sealAdmin";
	
	/**
	 * <p>用印种类-公章</p>
	 */
	public static final String USE_SEAL_FILE_KIND = "USESEAL_TYPE_4039257816";
	
	/**								   
	 * <p>流程状态-草稿</p>
	 */
	public static final Integer DRAFT = 0;
	
	public static final String DRAFT_NAME = "草稿";
	
	/**
	 * <p>流程状态-审批中</p>
	 */
	public static final Integer WF_ONGOING = 1;
	
	public static final String ONGOING_NAME = "审批中";
	
	/**
	 * <p>流程状态-审批完成</p>
	 */
	public static final Integer WF_COMPLETE = 2;
	
	public static final String COMPLETE_NAME = "审批完成";
	
	/**
	 * <p>流程状态-驳回</p>
	 */
	public static final Integer WF_ABORT = 3;
	
	public static final String ABORT_NAME = "驳回";
	
	/**
	 * <p>流程状态-上传完成</p>
	 */
	public static final Integer WF_UPLOAD = 4;
	
	public static final String UPLOAD_NAME = "盖章文件已上传";
	
	
	public static final String USESEAL_TYPE = "USESEAL_TYPE";
	
	public static List<Map<String,String>> getUseSealTypeMap() {

		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(USESEAL_TYPE);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				Map<String,String> map = new LinkedHashMap<String, String>();
				map.put("value", item.getItemId());
				map.put("optionText",item.getDisplayName());
				result.add(map);
			}
		}
		return result;
	}
	
	public static String getUseSealTypeDisplam(String useSealTypeId) {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(USESEAL_TYPE);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map.get(useSealTypeId);
	}
	
	
}
