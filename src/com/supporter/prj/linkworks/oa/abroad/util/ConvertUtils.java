package com.supporter.prj.linkworks.oa.abroad.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.List;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.linkworks.oa.abroad.entity.Abroad;


// ~ File Information
// ====================================================================================================================
/**
 * 
 * <pre>
 * 转换工具类
 * </pre>
 * 
 * @author liyinfeng
 * @createDate 2017-03-27
 * @since TODO: 无
 * @modifier liyinfeng
 * @modifiedDate 2017-03-27
 */
public class ConvertUtils {

	// ~ Static Fields
	// ================================================================================================================
	/**
	 * 
	 */
	private static Calendar calendar = null;
	
	public static Date getNow() {
		calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		return now;
	}


	


	/**
	 * 
	 *  判断是否存在
	 * <pre>
	 * 	判断是否存在
	 * </pre>
	 * 
	 * @param newParentId
	 * @param treeNodeMap
	 * @return
	 */
	public static boolean isExists(String newParentId, List<Abroad> list) {
		boolean isExists = false;
		if (list == null) {
			return isExists;
		}
		for (Abroad node : list) {
			if (node != null && newParentId.equals(node.getRecordId())) {
				isExists = true;
				return isExists;
			}
		}
		return isExists;
	}



	/**
	 * 
	 * dateString 日期串yyyy-MM-dd
	 * 
	 * @desc： (这里描述这个方法适用条件 – 可选)
	 * @参数：
	 * @return
	 * @返回类型 String
	 * @exception
	 * @since 1.0.0
	 */
	public static String dateString() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(new Date());
	}



	/**
	 * 
	 * dateString 日期串yyyyMM
	 * 
	 * @desc： (这里描述这个方法适用条件 – 可选)
	 * @参数：
	 * @return
	 * @返回类型 String
	 * @exception
	 * @since 1.0.0
	 */
	public static String dateString(Date date) {
		String str = "";
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			str = simpleDateFormat.format(date);
		} catch (Exception e) {

		}

		return str;
	}

    /**
     * 获取角色人员下拉框
     * @return
     */
    public static List<Person> getByRoleId(String roleId){
    	List<Person> list = EIPService.getRoleService().getPersonFromRole(roleId,null);
    	return list;
    }

}
