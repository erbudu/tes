package com.supporter.prj.cneec.tpc.prj_settlement.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.cneec.tpc.util.TpcConstant;

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
	 * 
	 * dateString 日期串yyyy年MM月dd日
	 * 
	 * @desc： (这里描述这个方法适用条件 – 可选)
	 * @参数：
	 * @return
	 * @返回类型 String
	 * @exception
	 * @since 1.0.0
	 */
	public static String dateShowString() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
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
	
	/**
	 * 获取当前日期的前一天
	 * @param date
	 * @return
	 */
	public static String getNextDay(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, -1);  
        date = calendar.getTime();  
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
	
	/**
	 * 根据货币名称获取货币码表
	 * @param currencyType
	 * @return
	 */
	public static String getByCurrencyType(String currencyType){
		Map<String, String> map = TpcConstant.getCurrencyMap();
		String currencyTypeCode = "";
		Set<String>kset=map.keySet();
		for(String ks:kset){
		    if(currencyType.equals(map.get(ks))){
		    	currencyTypeCode = ks;
		    	break;
		    }
		}
		return currencyTypeCode;
	}
	
	/**
	 * 判断参数是否是小数
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String attr) { 
		if(attr.indexOf("-")>0){
			return false;
		}
		Boolean strResult = attr.matches("-?[0-9]+.*[0-9]*");  
		return strResult;
	} 
}
