/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.approving.constant;

/**
 *<p>Title: 投议标备案及许可-对外提报-启动对外提报 常量类</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月4日
 * 
 */
public class ReportStartConstant {
	
	/*以下为角色相关信息*/
	/**
	 * <pre>角色：经营管理部-投议标备案及许可岗位人员</pre>
	 */
	public static final String ROLE_BIDFILING = "BIDFILING";
	
	/*以下为应用信息*/
	public static final String MODULE_ID = "BIDAPPROVING";//对外提报及获批-应用编号
	/**数据编辑权限*/
	public static final String AUTH_EDIT = "EDITAUTH";//经营管理部投议标备案级许可人员拥有此权限
	public static final String CODETABLE_ID = "";//码表编号
	public static final String DOMAIN_OBJECT_ID = "";//业务对象编号
	
	/**      以下为是否生效信息		**/
	/**
	 * 		未生效
	 */
	public static final int  NOT_EFFECT = 0;
	/**
	 * 		已生效
	 */
	public static final int EFFECT = 1;
	
	/**
	 *		已通知 
	 */
	public static final int NOTICEED = 2;
}
