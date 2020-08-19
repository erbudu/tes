package com.supporter.prj.cneec.tpc.util;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.account.entity.Account;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 * @Title: TpcCommonUtil
 * @Description: 贸易公共方法工具
 * @author: yanweichao
 * @date: 2018-5-29
 * @version: V1.0
 */
public class TpcCommonUtil {

	/**
	 * 判断字符串数组是否包含特定值
	 *
	 * @param arr 字符串数组
	 * @param tarVal 特定字符值
	 * @return
	 */
	public static boolean existByLoop(String[] arr, String tarVal) {
		for (String s : arr) {
			if (s.equals(tarVal)) return true;
		}
		return false;
	}

	/**
	 * DAO中通用多参数过滤记录方法
	 * 
	 * @param clazz
	 *            Bean类
	 * @param params
	 *            过滤参数
	 * @param likeSearhNames
	 *            like参数字段名
	 * @param orders
	 *            排序
	 * @return
	 */
	public static DetachedCriteria getQueryDetachedCriteria(Class<?> clazz, Map<String, Object> params, List<String> likeSearhNames, Map<String, Boolean> orders) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		// 填充过滤条件
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, Object> e : params.entrySet()) {
				if (StringUtils.isBlank(e.getKey())) {
					continue;
				} else if ((likeSearhNames != null) && (likeSearhNames.contains(e.getKey()))) {
					// 需要模糊查询的字段
					dc.add(Restrictions.like(e.getKey(), e.getValue()));
				} else if (e.getValue() == null || (e.getValue() instanceof String && StringUtils.isBlank((String) e.getValue()))) {
					// 属性值是空的字段(null或"")
					dc.add(Restrictions.isNull(e.getKey()));
				} else if (e.getValue().getClass().isArray()) {
					// 属性值可以是多个的字段
					dc.add(Restrictions.in(e.getKey(), (Object[]) e.getValue()));
				} else {
					// 属性值是单个的字段
					dc.add(Restrictions.eq(e.getKey(), e.getValue()));
				}
			}
		}
		// 填充排序
		if (orders != null && !orders.isEmpty()) {
			for (Map.Entry<String, Boolean> e : orders.entrySet()) {
				if (StringUtils.isBlank(e.getKey())) {
					continue;
				} else {
					dc.addOrder(((Boolean) e.getValue()).booleanValue() ? Order.asc(e.getKey()) : Order.desc(e.getKey()));
				}
			}
		}
		return dc;
	}

	/**
	 * DAO中通用单一参数过滤记录方法
	 * 
	 * @param clazz
	 *            Bean类
	 * @param properName
	 *            过滤字段
	 * @param propValue
	 *            过滤字段值
	 * @param likeSearch
	 *            是否like
	 * @param orderByName
	 *            排序字段
	 * @param sort
	 *            排序方式
	 * @return
	 */
	public static DetachedCriteria getQueryDetachedCriteria(Class<?> clazz, String properName, Object propValue, Boolean likeSearch, String orderByName, Boolean sort) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		if (StringUtils.isNotBlank(properName)) {
			if ((likeSearch != null) && (likeSearch == true)) {
				// 需要模糊查询的字段
				dc.add(Restrictions.like(properName, propValue));
			} else if (propValue == null || (propValue instanceof String && StringUtils.isBlank((String) propValue))) {
				// 属性值是空的字段(null或"")
				dc.add(Restrictions.isNull(properName));
			} else if (propValue.getClass().isArray()) {
				// 属性值可以是多个的字段
				dc.add(Restrictions.in(properName, (Object[]) propValue));
			} else {
				// 属性值是单个的字段
				dc.add(Restrictions.eq(properName, propValue));
			}
		}
		if (StringUtils.isNotBlank(orderByName) && sort != null) {
			dc.addOrder(sort ? Order.asc(orderByName) : Order.desc(orderByName));
		}
		return dc;
	}

	/**
	 * 根据json格式的list集合获取对象不存在的属性集
	 * 
	 * @param clazz
	 * @param jsonList
	 * @return
	 */
	public static Set<String> getIgnoreProperties(Class<?> clazz, String jsonList) {
		Set<String> resultSet = null;
		if (StringUtils.isNotBlank(jsonList)) {
			resultSet = new HashSet<String>();
			try {
				// 获取json中对象属性集
				JSONArray jsonArray = JSONArray.fromObject(jsonList);
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(0));
				Set<?> attrNames = jsonObject.keySet();

				// 获取对象所有属性数组
				final BeanWrapper src = new BeanWrapperImpl(clazz);
				java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

				// 遍历所有属性组成需要排除属性集
				for (java.beans.PropertyDescriptor pd : pds) {
					boolean containsKey = attrNames.contains(pd.getName());
					if (!containsKey) {
						resultSet.add(pd.getName());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultSet;
	}

	/**
	 * 对象copy（排除source对象中不在valueMap集合的属性）
	 * 
	 * @param source
	 *            源对象
	 * @param target
	 *            目标对象
	 * @param valueMap
	 *            需要拷贝的属性
	 */
	public static void copyProperties(Object source, Object target, Map<String, Object> valueMap) {
		Set<String> ignoreNames = new HashSet<String>();
		if (valueMap != null && !valueMap.isEmpty()) {
			// 获取对象所有属性数组
			final BeanWrapper src = new BeanWrapperImpl(source);
			java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

			// 遍历所有属性组成需要排除属性集
			for (java.beans.PropertyDescriptor pd : pds) {
				boolean containsKey = valueMap.containsKey(pd.getName());
				if (!containsKey) {
					ignoreNames.add(pd.getName());
				}
			}
		}
		copyWithoutIgnoreProperties(source, target, ignoreNames);
	}

	/**
	 * 对象copy（排除source对象中在ignoreNames集合的属性）
	 * 
	 * @param source
	 *            源对象
	 * @param target
	 *            目标对象
	 * @param ignoreNames
	 *            忽略的属性
	 */
	public static void copyWithoutIgnoreProperties(Object source, Object target, Set<String> ignoreNames) {
		if (ignoreNames != null && !ignoreNames.isEmpty()) {
			// 将排除属性Set集转为数组
			String[] result = new String[ignoreNames.size()];
			String[] ignoreProperties = ignoreNames.toArray(result);
			org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
		} else {
			copyProperties(source, target);
		}
	}

	/**
	 * 对象copy
	 * 
	 * @param source
	 *            源对象
	 * @param target
	 *            目标对象
	 */
	public static void copyProperties(Object source, Object target) {
		org.springframework.beans.BeanUtils.copyProperties(source, target);
	}

	/**
	 * 根据人员ID获取用户信息
	 *
	 * @param personId
	 * @return
	 */
	public static UserProfile getUserProfile(String personId) {
		Person person = EIPService.getEmpService().getEmp(personId);
		Account account = EIPService.getAccountService().getDefaultAccount(person);
		UserProfile userProfile = EIPService.getLogonService().getUserProfile(account);
		return userProfile;
	}

}
