package com.supporter.prj.linkworks.oa.signed_report.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.person.entity.Person;

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
	 * dateString 日期串yyyy-MM-dd HH:mm:ss
	 * 
	 * @desc： (这里描述这个方法适用条件 – 可选)
	 * @参数：
	 * @return
	 * @返回类型 String
	 * @exception
	 * @since 1.0.0
	 */
	public static String dateDetailString() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
     * 将数字转换成字符串
     * @param num
     * @param size
     * @return
     */
    public static String numberToString(int num,int size){
    	if(String.valueOf(num).length()>=size){
    		System.out.println("超过指定的位数。");
    		return "";
    	}
    	int numLength = String.valueOf(num).length();
    	int diffLength = size - numLength;//差的位数
    	StringBuffer sf = new StringBuffer();
    	for(int i=0;i<diffLength;i++){
    		sf.append("0");
    	}
    	sf.append(num);
    	return sf.toString();
    }
    
    /**
     * 获取角色人员下拉框
     * @return
     */
    public static Map<String, String> getByRoleId(String roleId){
    	Map<String, String> map = new LinkedHashMap<String, String>();
    	List<Person> list = EIPService.getRoleService().getPersonFromRole(roleId,null);
    	if(list != null && list.size()>0){
    		for(Person person :list){
    			map.put(person.getPersonId(), person.getName());
    		}
    	}
    	return map;
    }
}
