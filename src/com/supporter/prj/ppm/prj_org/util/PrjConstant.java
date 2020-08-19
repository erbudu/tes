package com.supporter.prj.ppm.prj_org.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj.StatusCodeTable;

public class PrjConstant {
	
	// 码表
		public static final String PRJ_NATURE = "PRJ_NATURE";//项目性质
		public static final String PRJ_FIELD = "PRJ_FIELD";//项目领域
		public static final String PFJ_MOD = "PFJ_MOD";//承揽方式
		public static final String DEPT_LEVEL = "DEPT_LEVEL";//部门级别
		public static final String EMP_ROLE = "EMP_ROLE";//人员角色
		public static final String COOP_ROLE = "COOP_ROLE";//承担角色
		public static final String PRJ_DEW_STATUS = "PRJ_DEW_STATUS";//将启动开发工作
		public static final String PPM_DEVELOPMENT_MODE = "PPM_DEVELOPMENT_MODE";//开发模式
		public static final String MONE_SOURCE_TYPE = "MONE_SOURCE_TYPE";//资金来源类型
		
		public static final String PPM_PRJ_BID_WAY = "PPM_PRJ_BID_WAY";//投议标方式
		public static final String PPM_PRJ_FINANCING = "PPM_PRJ_FINANCING";//融资要求
		public static final String PPM_PRJ_GUARANTEE = "PPM_PRJ_GUARANTEE";//融资担保
		public static final String PPM_SETTLEMENT_WAY = "PPM_SETTLEMENT_WAY";//是否人民币结算
		
		public static final String PPM_MARGIN_WAY = "PPM_MARGIN_WAY";// 项目初始登录-招标信息-保证金形式
		// 项目领域-下拉选码表数据
		public static Map<String, String> selectMoneySourceType() {
			List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(MONE_SOURCE_TYPE);
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
			return map;
		}
		
		/**
		 * <pre>开发模式下拉选码表数据</pre>
		 * @return 开发模式 码表数据
		 */
		public static Map<String, String> selectDevelopmentMode() {
			List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(PPM_DEVELOPMENT_MODE);
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
			return map;
		}
		
		// 项目初始登录-招标信息-保证金形式 下拉选
		public static Map<String, String> selectMarginWay() {
			List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(PPM_MARGIN_WAY);
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
			return map;
		}
		
		// 项目性质-下拉选数据
				public static Map<String, String> selectPrjNature() {
					List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(PRJ_NATURE);
					Map<String, String> map = new LinkedHashMap<String, String>();
					for (IComCodeTableItem item : list) {
						map.put(item.getItemId(), item.getDisplayName());
					}
					return map;
				}
	
		// 项目领域-下拉选码表数据
		public static Map<String, String> selectPrjField() {
			List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(PRJ_FIELD);
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
			return map;
		}
	
		/**承揽方式-下拉选数据*/
		public static Map<String, String> selectPrjPrjMod() {
			List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(PFJ_MOD);
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
			return map;
		}
		
		//将启动开发工作
		public static Map<Integer, String> selectPrjDewStatus() {
			//List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(PRJ_DEW_STATUS);
			Map<Integer, String> map = new LinkedHashMap<Integer, String>();
			map.put(StatusCodeTable.FOCUS, "关注");
			map.put(StatusCodeTable.START, "启动项目跟踪");
			map.put(StatusCodeTable.PREPARE, "准备投标");
			map.put(StatusCodeTable.IMPORTANT, "重点跟踪");
			return map;
		}

		/**融资要求-下拉选码表信息*/
		public static Map<String, String> selectPfType() {
			List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(PPM_PRJ_FINANCING);
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
			return map;
		}

		/**融资担保*/
		public static Map<String, String> selectPfgMod() {
			List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(PPM_PRJ_GUARANTEE);
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
			return map;
		}
		
		/**是人民币结算*/
		public static Map<String, String> selectPrjSettlementWay() {
			List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(PPM_SETTLEMENT_WAY);
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
			return map;
		}
		
		/**投议标方式-下拉选码表数据*/
		public static Map<String, String> selectBidWay() {
			List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(PPM_PRJ_BID_WAY);
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
			return map;
		}
		
		public static Map<String, String> selectDeptLevel() {
			List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(DEPT_LEVEL);
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
			return map;
		}

		public static Map<String, String> selectEmpRole() {
			List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(EMP_ROLE);
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
			return map;
		}

		public static Map<String, String> selectCoopRole() {
			List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(COOP_ROLE);
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
			return map;
		}

}
