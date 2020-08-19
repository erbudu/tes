package com.supporter.prj.eip.sso.util;

import com.supporter.util.CodeTable;

/**
 * 单点登录系统.
 * @author linda
 *
 */
public class SSOConstant {
	/**
	 * 单点登录系统注册表KEY.
	 *
	 */
	public static final class SSOAppSettingInerName {
		//单点登录的业务系统.
		public static final String CNEEC_PC = "cneec_pc";
		public static final String CNEEC_PL = "cneec_pl";
		public static final String CNEEC_CM = "cm";
		public static final String CNEEC_AM = "cneec_am";
		public static final String CNEEC_DM = "dm";
		public static final String CNEEC_EIP = "eip";
		public static final String CNEEC_OA = "cneec_oa";
		public static final String CNEEC_WARROOM = "warroom";
		public static final String CNEEC_PRJ_BUDGET = "prj_budget";
		public static final String CNEEC_DESIGNER = "designer";
		
		
		//CNEEC_PC访问的基本地址
		public static final String CNEEC_PC_BASE_URL = "CNEEC_PC_BASE_URL";
		public static final String CNEEC_PL_BASE_URL = "CNEEC_PL_BASE_URL";
		public static final String CNEEC_CM_BASE_URL = "CNEEC_CM_BASE_URL";
		public static final String CNEEC_AM_BASE_URL = "CNEEC_AM_BASE_URL";
		public static final String CNEEC_DM_BASE_URL = "CNEEC_DM_BASE_URL";
		public static final String CNEEC_EIP_BASE_URL = "CNEEC_EIP_BASE_URL";
		public static final String CNEEC_OA_BASE_URL = "CNEEC_OA_BASE_URL";
		public static final String CNEEC_WARROOM_BASE_URL = "CNEEC_WARROOM_BASE_URL";
		public static final String CNEEC_PRJ_BUDGET_BASE_URL = "CNEEC_PRJ_BUDGET_BASE_URL";
		public static final String CNEEC_DESIGNER_BASE_URL = "CNEEC_DESIGNER_BASE_URL";
		
		//单点登录的KEY
		public static final String CNEEC_PC_SSO_KEY = "CNEEC_PC_SSO_KEY";
		public static final String CNEEC_PL_SSO_KEY = "CNEEC_PL_SSO_KEY";
		public static final String CNEEC_CM_SSO_KEY = "CNEEC_CM_SSO_KEY";
		public static final String CNEEC_AM_SSO_KEY = "CNEEC_AM_SSO_KEY";
		public static final String CNEEC_DM_SSO_KEY = "CNEEC_DM_SSO_KEY";
		public static final String CNEEC_EIP_SSO_KEY = "CNEEC_EIP_SSO_KEY";
		public static final String CNEEC_OA_SSO_KEY = "CNEEC_OA_SSO_KEY";
		public static final String CNEEC_WARROOM_SSO_KEY = "CNEEC_WARROOM_SSO_KEY";
		public static final String CNEEC_PRJ_BUDGET_SSO_KEY = "CNEEC_PRJ_BUDGET_SSO_KEY";
		public static final String CNEEC_DESIGNER_SSO_KEY = "CNEEC_DESIGNER_SSO_KEY";
		
		public static CodeTable getBaseUrlCtbl(){
			CodeTable ctbl = new CodeTable();
			ctbl.insertItem(CNEEC_PC, CNEEC_PC_BASE_URL);
			ctbl.insertItem(CNEEC_PL, CNEEC_PL_BASE_URL);
			ctbl.insertItem(CNEEC_CM, CNEEC_CM_BASE_URL);
			ctbl.insertItem(CNEEC_AM, CNEEC_AM_BASE_URL);
			ctbl.insertItem(CNEEC_DM, CNEEC_DM_BASE_URL);
			ctbl.insertItem(CNEEC_EIP, CNEEC_EIP_BASE_URL);
			ctbl.insertItem(CNEEC_OA, CNEEC_OA_BASE_URL);
			ctbl.insertItem(CNEEC_WARROOM, CNEEC_WARROOM_BASE_URL);
			ctbl.insertItem(CNEEC_PRJ_BUDGET, CNEEC_PRJ_BUDGET_BASE_URL);
			ctbl.insertItem(CNEEC_DESIGNER, CNEEC_DESIGNER_BASE_URL);
			return ctbl;
		}
		
		public static CodeTable getSSOKeyCtbl(){
			CodeTable ctbl = new CodeTable();
			ctbl.insertItem(CNEEC_PC, CNEEC_PC_SSO_KEY);
			ctbl.insertItem(CNEEC_PL, CNEEC_PL_SSO_KEY);
			ctbl.insertItem(CNEEC_CM, CNEEC_CM_SSO_KEY);
			ctbl.insertItem(CNEEC_AM, CNEEC_AM_SSO_KEY);
			ctbl.insertItem(CNEEC_DM, CNEEC_DM_SSO_KEY);
			ctbl.insertItem(CNEEC_EIP, CNEEC_EIP_SSO_KEY);
			ctbl.insertItem(CNEEC_OA, CNEEC_OA_SSO_KEY);
			ctbl.insertItem(CNEEC_WARROOM, CNEEC_WARROOM_SSO_KEY);
			ctbl.insertItem(CNEEC_PRJ_BUDGET, CNEEC_PRJ_BUDGET_SSO_KEY);
			ctbl.insertItem(CNEEC_DESIGNER, CNEEC_DESIGNER_SSO_KEY);
			return ctbl;
		}
	}
}
