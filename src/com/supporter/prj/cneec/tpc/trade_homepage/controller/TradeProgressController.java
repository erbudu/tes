package com.supporter.prj.cneec.tpc.trade_homepage.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.trade_homepage.entity.TradeProgress;
import com.supporter.prj.cneec.tpc.trade_homepage.service.TradeProgressService;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: TradeProgressController
 * @Description: 贸易项目导航进度Controller
 * @author: yanweichao
 * @date: 2017-01-23
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/tradeProgress")
public class TradeProgressController extends AbstractController {

	private static final long serialVersionUID = 1L;
	@Autowired
	private TradeProgressService tradeProgressService;

	/**
	 * 初始化加载对象
	 * @param map
	 */
	@ModelAttribute
	public void getTradeProgress(Map<String, Object> map) {
		UserProfile user = this.getUserProfile();
		if (user != null) {
			TradeProgress tradeProgress = this.tradeProgressService.getTradeProgress(user);
			if (tradeProgress != null) {
				map.put("tradeProgress", tradeProgress);
			}
		}
	}

	/**
	 * 获取当前操作用户贸易项目进度记录
	 */
	@RequestMapping("/getTradeProgress")
	public @ResponseBody
	TradeProgress getTradeProgress() {
		UserProfile user = this.getUserProfile();
		TradeProgress tradeProgress = this.tradeProgressService.getTradeProgress(user);
		return tradeProgress;
	}

	/**
	 * 保存当前操作用户贸易项目进度记录
	 * @param tradeProgress
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<TradeProgress> saveOrUpdate(TradeProgress tradeProgress) {
		UserProfile user = this.getUserProfile();
		TradeProgress entity = this.tradeProgressService.saveOrUpdate(user, tradeProgress);
		return OperResult.succeed("TradeProgress-saveSuccess", "进度保存成功", entity);
	}

	/**
	 * 删除当前操作用户贸易项目进度记录
	 * @return
	 */
	@RequestMapping("delete")
	public @ResponseBody
	OperResult<?> delete() {
		UserProfile user = this.getUserProfile();
		this.tradeProgressService.delete(user);
		return OperResult.succeed("TradeProgress-deleteSuccess", "进度删除成功", null);
	}

}
