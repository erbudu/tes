package com.supporter.prj.cneec.tpc.control.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**
 * @Title: TpcController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-09-19
 * @version: V1.0
 */
@Controller("tpcController")
@RequestMapping("tpc/build")
public class TpcController extends AbstractController {

	private static final long serialVersionUID = 1L;

	/**
	 * 开户行所在省
	 * @return
	 */
	@RequestMapping(value = "/selectRemitProvinceData")
	public @ResponseBody
	Map<String, String> selectRemitProvinceData() {
		return TpcConstant.getRemitProvinceMap();
	}

	/**
	 * 开户行所在市
	 * @param value 级联选择时上级select传递过来的参数名必须为value
	 * @return
	 */
	@RequestMapping(value = "/selectRemitCityData")
	public @ResponseBody
	Map<String, String> selectRemitCityData() {
		String remitProvinceId = CommonUtil.trim(this.getRequestPara("value"));
		return TpcConstant.getRemitCityMap(remitProvinceId);
	}

}
