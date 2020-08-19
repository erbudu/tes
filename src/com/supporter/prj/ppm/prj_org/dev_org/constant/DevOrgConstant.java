/**
 * 
 */
package com.supporter.prj.ppm.prj_org.dev_org.constant;

import java.util.HashMap;
import java.util.Map;

/**
 *<p>Title: DevOrgConstant</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年11月18日
 * 
 */
public final class DevOrgConstant {
	
	public static Map<Integer,String> getIsMainSideName() {
		Map<Integer,String> map = new HashMap<Integer, String>();
		map.put(0, "是");
		map.put(1, "否");
		return map;
	}

}
