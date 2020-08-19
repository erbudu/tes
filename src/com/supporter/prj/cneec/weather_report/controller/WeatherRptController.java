package com.supporter.prj.cneec.weather_report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.weather_report.entity.WertherReport;
import com.supporter.prj.cneec.weather_report.service.WertherReportService;
import com.supporter.spring_mvc.AbstractController;

/**
 * 天气预报.
 * @author linda
 *
 */
@Controller
@RequestMapping("/oa/weather")
public class WeatherRptController extends AbstractController{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private WertherReportService wertherReportService;
	
	/**
	 * 获取天气预报.
	 * @return
	 */
	@RequestMapping("/getWeather")
	@ResponseBody
	public String getWeather(){
		WertherReport weatherRpt = wertherReportService.get("1");
		if (weatherRpt == null)return "";
		return weatherRpt.getWeatherReportContent();
	}
	
	/**
	 * 刷新天气预报.
	 * @return
	 */
	@RequestMapping("/refreshWeather")
	@ResponseBody
	public String refreshWeather(){
		return wertherReportService.refreshWeather();
	}
	
}
