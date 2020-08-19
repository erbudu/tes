package com.supporter.prj.cneec.tpc.navigation.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.supporter.prj.cneec.tpc.navigation.service.TpcNavigationService;
import com.supporter.util.HttpUtil;


/**
 * @Title: TpcNavigationController
 * @Description: 贸易项目导航Controller
 * @author: yanweichao
 * @date: 2017-12-25
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/navigation")
public class TpcNavigationController {

	@Autowired
	private TpcNavigationService navigationService;

	/**
	 * 获取导航项目信息
	 */
	@RequestMapping("/findTpcProject")
	public void findTpcProject(String projectId, HttpServletResponse response) {
		String json = this.navigationService.findTpcProject(projectId);
		HttpUtil.write(response, json);
	}

	/**
	 * 获取导航采购需求单信息
	 */
	@RequestMapping("/findTpcPurchaseDemand")
	public void findTpcPurchaseDemand(String demandId, HttpServletResponse response) {
		String json = this.navigationService.findTpcPurchaseDemand(demandId);
		HttpUtil.write(response, json);
	}

}
