package com.supporter.prj.linkworks.oa.abroadPublicity.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class CommonUtils {

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
	 * 修改前台传来的日期，解决带时分秒的问题
	 * @param date
	 * @return
	 */
	public static String dateStringSub(String date){
		if(StringUtils.isNotBlank(date)&&date.length()>10){
			return date.substring(0, 10);
		}else{
			return date;
		}
		
	}
}
