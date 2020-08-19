/**
 * 
 */
package com.supporter.prj.linkworks.oa.attendance.leave.util;

import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @author YUEYUN
 * @date 2019年7月16日
 * 
 */
public class BeanUtil {

	public static String[] getIgnoreProperties(Object source, Map<String, Object> valueMap,
			String... updateProperties) {
		if (valueMap == null) {
			return new String[0];
		}
		BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = src.getPropertyDescriptors();
		Set<String> emptyNames = new java.util.HashSet();
		for (PropertyDescriptor pd : pds) {
			boolean containsKey1 = valueMap.containsKey(pd.getName());

			boolean containsKey2 = org.apache.commons.lang3.ArrayUtils.contains(updateProperties, pd.getName());
			if ((!containsKey1) && (!containsKey2)) {
				emptyNames.add(pd.getName());
			}
		}
		String[] result = new String[emptyNames.size()];
		return (String[]) emptyNames.toArray(result);
	}

	public static void copyProperties(Object source, Object target, Map<String, Object> valueMap,
			String... updateProperties) {
		String[] ignoreProperties = getIgnoreProperties(source, valueMap, updateProperties);
		org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
	}
}
