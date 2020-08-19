package com.supporter.prj.cneec.e2b.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.e2b.util.E2bUtil;
import com.supporter.spring_mvc.AbstractController;

import net.sf.json.JSONObject;

/**
 * @Title: E2bController.java
 * @Description: 银企直连通用Controller
 * @author: yanweichao
 * @date: 2019年1月25日
 * @version: V1.0
 */
@Controller
@RequestMapping("e2b/build")
public class E2bController extends AbstractController {

	private static final long serialVersionUID = 1L;

	/**
	 * 获取银企直连-资金用途
	 * @return
	 */
	@RequestMapping(value = "/getCapticalpurposeData")
	public @ResponseBody
	List<JSONObject> getCapticalpurposeData() {
		Map<String, String> map = E2bUtil.getCapticalpurposeCodeTable();
		List<JSONObject> list = new ArrayList<JSONObject>();
		for (Map.Entry<String, String> e : map.entrySet()) {
			String str = "{\"id\":\"" + e.getKey() + "\",\"text\":\"" + e.getValue() + "\"}";
			JSONObject json = JSONObject.fromObject(str);
			list.add(json);
		}
		return list;
	}

}
