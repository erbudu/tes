package com.supporter.prj.ppm.contract.sign.review_ver.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @Description: bean操作工具类.
 * @author YAN
 * @date 2019-09-09 10:46:31
 * @version V1.0
 */
public class BeanUtils {

	/**
	 * 对象间copy属性（排除source对象中值为null的属性）
	 * @param source
	 * @param target
	 */
	public static void copyProperties(Object source, Object target, Map<String, Object> valueMap) {
		if(valueMap != null) {
			final BeanWrapper src = new BeanWrapperImpl(source);
			java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
			Set<String> ignoreNames = new HashSet<String>();
			for(java.beans.PropertyDescriptor pd : pds) {
				boolean containsKey1 = valueMap.containsKey(pd.getName());
				if(!containsKey1) {
					ignoreNames.add(pd.getName());
				}
			}
			String[] result = new String[ignoreNames.size()];
			String[] ignoreProperties = ignoreNames.toArray(result);
			org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
		} else {
			org.springframework.beans.BeanUtils.copyProperties(source, target);
		}
	}

}


