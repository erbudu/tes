package com.supporter.prj.ppm.template_register.util;

import java.util.HashMap;
import java.util.Map;

import com.supporter.util.JsonUtils;

/**
 * 公用参数类型转换
 * <pre>
 * 提供参数类型转换工具
 * </pre>
 * 
 * @author duancunming
 * @createDate 2016-12-14
 * @since 无
 * @modifier duancunming
 * @modifiedDate 2016-12-14
 */
public class Params {
	private Map<?, ?> param = null;

	/**
	 * 
	 * 构造函数
	 *
	 * @param paraMap
	 */
	public Params(Map<?, ?> paraMap) {
		this.param = paraMap;
	}

	/**
	 * 
	 * <pre>
	 * 	转换
	 * </pre>
	 * 
	 * @param name
	 * @return
	 */
	public Integer getParaToInt(String name) {
		return getParaToInt_((String) this.param.get(name), null);
	}

	/**
	 * 
	 * <pre>
	 * 	转换int
	 * </pre>
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Integer getParaToInt(String name, Integer defaultValue) {
		return getParaToInt_((String) this.param.get(name), defaultValue);
	}

	/**
	 * 
	 * <pre>
	 * 	转换为int
	 * </pre>
	 * 
	 * @param result
	 * @param defaultValue
	 * @return
	 */
	private Integer getParaToInt_(String result, Integer defaultValue) {
		if (result == null)
			return defaultValue;
		if ((result.startsWith("N")) || (result.startsWith("n")))
			return Integer.valueOf(-Integer.parseInt(result.substring(1)));
		return Integer.valueOf(Integer.parseInt(result));
	}

	/**
	 * 
	 * <pre>
	 * 	转换为long
	 * </pre>
	 * 
	 * @param name
	 * @return
	 */
	public Long getParaToLong(String name) {
		return getParaToLong_((String) this.param.get(name), null);
	}

	/**
	 * 
	 * <pre>
	 * 转换为long
	 * </pre>
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Long getParaToLong(String name, Long defaultValue) {
		return getParaToLong_((String) this.param.get(name), defaultValue);
	}

	/**
	 * 
	 * <pre>
	 * 转换为long
	 * </pre>
	 * 
	 * @param result
	 * @param defaultValue
	 * @return
	 */
	private Long getParaToLong_(String result, Long defaultValue) {
		if (result == null)
			return defaultValue;
		if ((result.startsWith("N")) || (result.startsWith("n")))
			return Long.valueOf(-Long.parseLong(result.substring(1)));
		return Long.valueOf(Long.parseLong(result));
	}

	/**
	 * 
	 * <pre>
	 * 	转换为boolean
	 * </pre>
	 * 
	 * @param name
	 * @return
	 */
	public Boolean getParaToBoolean(String name) {
		String result = (String) this.param.get(name);
		if (result != null) {
			result = result.trim().toLowerCase();
			if ((result.equals("1")) || (result.equals("true")))
				return Boolean.TRUE;
			if ((result.equals("0")) || (result.equals("false"))) {
				return Boolean.FALSE;
			}
		}
		return null;
	}

	/**
	 * 
	 * <pre>
	 * 	转换为boolean
	 * </pre>
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Boolean getParaToBoolean(String name, Boolean defaultValue) {
		Boolean result = getParaToBoolean(name);
		return result != null ? result : defaultValue;
	}

	/**
	 * 
	 * <pre>
	 * 	get para
	 * </pre>
	 * 
	 * @param name
	 * @return
	 */
	public String getPara(String name) {
		return (String) this.param.get(name);
	}

	/**
	 * 
	 * <pre>
	 * 	get para
	 * </pre>
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public String getPara(String name, String defaultValue) {
		String result = (String) this.param.get(name);
		return (result != null) && (!"".equals(result)) ? result : defaultValue;
	}

	/**
	 * 
	 * <pre>
	 *  get param
	 * </pre>
	 * 
	 * @return
	 */
	public Map<?, ?> getParam() {
		return this.param;
	}

	/**
	 * 
	 * 
	 * <pre>
	 * set param
	 * </pre>
	 * 
	 * @param param
	 */
	public void setParam(Map<?, ?> param) {
		this.param = param;
	}

	/**
	 * 
	 * <pre>
	 * main 函数
	 * </pre>
	 * 
	 * @param arg
	 */
	public static void main(String[] arg) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "123");
		map.put("name", "名称");
		String json = JsonUtils.toJson(map);
		System.out.println(json);
	}
}
