package com.supporter.prj.pm;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.util.CommonUtil;

/**
 * 常量.
 * @author Administrator
 *
 */
public class PmConstant {
	
	// 码表
	public static final String URGENCY = "URGENCY"; // 紧急程度
	public static final String SECRECY = "SECRECY"; // 保密程度
	public static final String FILETYPE = "FILETYPE"; // 文件类型
	public static final String PBS_CATEGORY = "PBS_CATEGORY";
	public static final String PBS_TYPE = "PBS_TYPE";
	public static final String CNEEC_CURRENCY = "CNEEC_CURRENCY"; // 币别
	public static final String SOURCE_TYPE = "SOURCE_TYPE"; //收入来源
	// 付款条款(采购合同.付款条款.付款项；财务管理.付款申请.付款条款)
	public static final String PAYMENT_CLAUSE = "PAYMENT_CLAUSE";
	public static final String CHANGE_REASON = "change_reason"; // 设计变更原因
	public static final String APPLY_TYPE = "apply_type"; // 洽商申请类型
	public static final String CONSTRUCTION_TYPE = "CONSTRUCTION_TYPE"; //施工工程类型（土建/安装）
	public static final String negotiate_way = "negotiate_way"; // 洽商方式
	public static final String Specialized = "Specialized"; // 专业（专项）管理代码
	public static final String DRAWING_SPECIALTY = "DRAWING_SPECIALTY"; // 图纸专业
	public static final String DRAWING_POSITION = "DRAWING_POSITION";// 图纸部位
	
	//获取报表服务器地址
	public static String getReportUrl() {
		String reportUrl = EIPService.getRegistryService().get("REPORT_URL");
		return reportUrl;
	}

	/**
	 * 紧急程度
	 * 
	 * @return Map<String, String>
	 */
	public static Map<String, String> getUrgencyCd() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService()
				.getCodeTableItems(URGENCY);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}

	/**
	 * 保密程度
	 * 
	 * @return Map<String, String>
	 */
	public static Map<String, String> getSecrecyCd() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService()
				.getCodeTableItems(SECRECY);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}
	
	/**
	 * 模板类型
	 * @param fileTypeId 文件类型ID
	 * @return Map<String, String>
	 */
	public static Map<String, String> getModelTypeCd(String fileTypeId) {
		if (fileTypeId != null && CommonUtil.trim(fileTypeId).length() > 0) {
			List<IComCodeTableItem> list = EIPService.getComCodeTableService()
					.getSubItems(fileTypeId);
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
			return map;
		}
		return null;
	}
	
	/**
	 * 获取PBS类别码表.
	 * 
	 * @return Map<String, String>
	 */
	public static Map<String, String> getPbsCategoryTable() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService()
				.getCodeTableItems(PBS_CATEGORY);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}
	
	/**
	 * 获取PBS类型码表.
	 * 
	 * @return Map<String, String>
	 */
	public static Map<String, String> getPbsTypeTable() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService()
				.getCodeTableItems(PBS_TYPE);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}
	
	/**
	 * 获取系统“币别”码表
	 * 码表的key和value一致
	 * 比如（key：人民币，value：人民币）
	 * @return Map<String, String>
	 */
	public static Map<String, String> getCurrencyTable() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService()
				.getCodeTableItems(CNEEC_CURRENCY);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}
	
	/**
	 * 获取收入来源码表.
	 * @return Map<String, String>
	 */
	public static Map<String, String> getSourceTypeTable() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService()
				.getCodeTableItems(SOURCE_TYPE);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}
	
	/**
	 * 付款条款
	 * @return Map<String, String>
	 */
	public static Map<String, String> getPaymentClause() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService()
				.getCodeTableItems(PAYMENT_CLAUSE);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}
	
	/**
	 * 设计变更原因
	 * @return Map<String, String>
	 */
	public static Map<String, String> getChangeReason() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService()
				.getCodeTableItems(CHANGE_REASON);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}
	
	/**
	 * 洽商申请类型
	 * @return Map<String, String>
	 */
	public static Map<String, String> getApplyType() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService()
				.getCodeTableItems(APPLY_TYPE);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}
	
	/**
	 * CONSTRUCTION_TYPE = "CONSTRUCTION_TYPE"; //施工工程类型（土建/安装）
	 * @return Map<String, String>
	 */
	public static Map<String, String> getConstructionType() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService()
				.getCodeTableItems(CONSTRUCTION_TYPE);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}
	
	public static Map<String, String> getNegotiateWay() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService()
				.getCodeTableItems(negotiate_way);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}

	
	//专业（专项）管理代码
	public static Map<String, String> getSpecialized() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService()
				.getCodeTableItems(Specialized);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}
	
	// 图纸专业
	public static Map<String, String> getSpecialty() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(DRAWING_SPECIALTY);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}

	// 图纸部位
	public static Map<String, String> getRegionName() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(DRAWING_POSITION);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}

	// 获取图纸版本规则--业务参数设置
	public static String getDrawingVersion() {
		String drawingVersion = EIPService.getRegistryService().get("DRAWING_VERSION");
		return drawingVersion;
	}

	// 获取图纸版本号规则--业务参数设置
	public static String getDrawingVersionNo() {
		String drawingVersionNo = EIPService.getRegistryService().get("DRAWING_VERSION_NUM");
		return drawingVersionNo;
	}

}
