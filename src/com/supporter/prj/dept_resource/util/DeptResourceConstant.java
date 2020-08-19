package com.supporter.prj.dept_resource.util;
public abstract class DeptResourceConstant {
	  //授权类型	
	  public static final String AUTH_TYPE_RESOURCE_CONTENT = "RESOURCE_CONTENT";//资源内容
	  public static final String AUTH_TYPE_DEFINETION = "DEFINETION";//资源设置
	  //授权权限类型
	  public static final String AUTHORIZEE_TYPE_DEPT = "DEPT";//授权给部门
	  public static final String AUTHORIZEE_TYPE_PERSON = "PERSON";//授权给个人
	  public static final String AUTHORIZEE_TYPE_GROUP = "GROUP";//授权给用户组（这里指的是角色）
	  public static final String AUTHORIZEE_TYPE_POST = "POST";//授权给岗位


	  
	  public static class I18nKey
	  {
	    public static final String SAVE_SUCCESS = "saveSuccess";
	    public static final String DELETE_SUCCESS = "deleteSuccess";
	    public static final String CHANGE_DISPLAY_ORDER_SUCCESS = "changeDisplayOrderSuccess";
	  }
}