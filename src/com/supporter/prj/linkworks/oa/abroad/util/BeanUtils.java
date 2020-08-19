package com.supporter.prj.linkworks.oa.abroad.util;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class BeanUtils
{
  public static String[] getIgnoreProperties(Object source, Map<String, Object> valueMap, String[] updateProperties)
  {
    if (valueMap == null) {
      return new String[0];
    }
    BeanWrapper src = new BeanWrapperImpl(source);
    PropertyDescriptor[] pds = src.getPropertyDescriptors();
    Set emptyNames = new HashSet();
    for (PropertyDescriptor pd : pds) {
      boolean containsKey1 = valueMap.containsKey(pd.getName());

      boolean containsKey2 = ArrayUtils.contains(updateProperties, pd.getName());
      if ((!containsKey1) && (!containsKey2)) {
        emptyNames.add(pd.getName());
      }
    }
    String[] result = new String[emptyNames.size()];
    return (String[])emptyNames.toArray(result);
  }

  public static void copyProperties(Object source, Object target, Map<String, Object> valueMap, String[] updateProperties)
  {
    String[] ignoreProperties = getIgnoreProperties(source, valueMap, updateProperties);
    org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
  }
}