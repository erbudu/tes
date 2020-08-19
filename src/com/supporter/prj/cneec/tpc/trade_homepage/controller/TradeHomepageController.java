package com.supporter.prj.cneec.tpc.trade_homepage.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.trade_homepage.entity.TradeHomepage;
import com.supporter.prj.cneec.tpc.trade_homepage.service.TradeHomepageService;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;
import com.supporter.util.HttpUtil;

/**
 * @Title: TradeHomepageController
 * @Description: 贸易项目导航Controller
 * @author: yanweichao
 * @date: 2017-01-23
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/tradeHomepage")
public class TradeHomepageController extends AbstractController {

	private static final long serialVersionUID = 1L;
	@Autowired
	private TradeHomepageService tradeHomepageService;

	/**
	 * 获取一级菜单
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "one-level" }, method = { RequestMethod.GET })
	public OperResult<List<TradeHomepage>> getOneLevelTradeHomepage() {
		String isActive = this.getRequestPara("isActive");
		String otherParams = CommonUtil.trim(this.getRequestPara("otherParams"));
		OperResult<List<TradeHomepage>> result = new OperResult<List<TradeHomepage>>();
		if (StringUtils.isEmpty(isActive)) {
			result.setBody(this.tradeHomepageService.getAllOneLevels(otherParams));
		} else {
			result.setBody(this.tradeHomepageService.getActivedOneLevel(otherParams));
		}
		return result;
	}

	/**
	 * 获取二级菜单
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "two-level" }, method = { RequestMethod.GET })
	public OperResult<List<TradeHomepage>> getTwoLevelTradeHomepage() {
		String isActive = this.getRequestPara("isActive");
		String parentId = this.getRequestPara("parentId");
		String otherParams = CommonUtil.trim(this.getRequestPara("otherParams"));
		OperResult<List<TradeHomepage>> result = new OperResult<List<TradeHomepage>>();
		if (StringUtils.isEmpty(isActive)) {
			result.setBody(this.tradeHomepageService.getAllTwoLevels(parentId, otherParams));
		} else {
			result.setBody(this.tradeHomepageService.getActivedTwoLevel(parentId, otherParams));
		}
		return result;
	}

	/**
	 * 获取三级菜单
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "three-level" }, method = { RequestMethod.GET })
	public OperResult<Map<String, Object>> getThreeLevelTradeHomepage() {
		String isActive = this.getRequestPara("isActive");
		String parentId = this.getRequestPara("parentId");
		int lowerStyle = this.getRequestParaInt("lowerStyle");
		OperResult<Map<String, Object>> result = new OperResult<Map<String, Object>>();
		if (StringUtils.isEmpty(isActive)) {
			result.setBody(this.tradeHomepageService.getAllThreeLevels(lowerStyle, parentId));
		} else {
			result.setBody(this.tradeHomepageService.getActivedThreeLevel(lowerStyle, parentId));
		}
		return result;
	}

	/**
	 * 获取导航项菜单
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "navigation/{parentId}" }, method = { RequestMethod.GET })
	public OperResult<Map<String, Object>> getTradeHomepageNavigation(@PathVariable String parentId) {
		String isActive = this.getRequestPara("isActive");
		int lowerStyle = this.getRequestParaInt("lowerStyle");
		OperResult<Map<String, Object>> result = new OperResult<Map<String, Object>>();
		if (StringUtils.isEmpty(isActive)) {
			result.setBody(this.tradeHomepageService.getAllThreeLevels(lowerStyle, parentId));
		} else {
			result.setBody(this.tradeHomepageService.getActivedThreeLevel(lowerStyle, parentId));
		}
		return result;
	}

	/**
	 * 获取导航项目信息
	 */
	@RequestMapping("/getTradeNodeParams")
	public void getTradeNodeParams(String projectId, String oneLevelId, HttpServletResponse response) {
		String twoLevelId = CommonUtil.trim(this.getRequestPara("twoLevelId"));
		String threeLevelId = CommonUtil.trim(this.getRequestPara("threeLevelId"));
		String otherParams = CommonUtil.trim(this.getRequestPara("otherParams"));
		String json = this.tradeHomepageService.getTradeNodeParams(projectId, oneLevelId, twoLevelId, threeLevelId, otherParams);
		HttpUtil.write(response, json);
	}

}
