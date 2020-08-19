package com.supporter.prj.cneec.weather_report.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.cneec.weather_report.dao.RemoteWertherReportDao;
import com.supporter.prj.cneec.weather_report.dao.WertherReportDao;
import com.supporter.prj.cneec.weather_report.entity.WertherReport;
import com.supporter.util.CommonUtil;


/**   
 * @Title: Service
 * @Description: OA_WERTHER_REPORT.
 * @author T
 * @date 2017-09-27 17:44:31
 * @version V1.0   
 *
 */
@Service
public class WertherReportService {
	@Autowired
	private WertherReportDao wertherReportDao;
	@Autowired
	private RemoteWertherReportDao remoteWertherReportDao;
	
	
	/**
	 * 根据主键获取OA_WERTHER_REPORT.
	 * 
	 * @param weatherReportId 主键
	 * @return WertherReport
	 */
	public WertherReport get(String weatherReportId){
		return  wertherReportDao.get(weatherReportId);
	}
	
	
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param user
	 * @param weatherReportId
	 * @return
	 */
	public WertherReport initEditOrViewPage(String weatherReportId) {
		if (StringUtils.isBlank(weatherReportId)) {// 新建
			WertherReport entity = new WertherReport();
			return entity;
		} else {// 编辑
			WertherReport entity = wertherReportDao.get(weatherReportId);
			return entity;
		}
	}
	
	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param wertherReport 实体类
	 * @return
	 */
	public WertherReport saveOrUpdate(UserProfile user, WertherReport wertherReport) {
		if (StringUtils.isBlank(wertherReport.getWeatherReportId())) {// 新建
			wertherReport.setWeatherReportId("1");
			return this.save(user, wertherReport);
		} else {// 编辑
			return this.update(user, wertherReport);
		}
	}
	
	
	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param wertherReport 实体类
	 * @return
	 */
	public void saveOrUpdate(String weatherReportContend) {
		WertherReport wertherReport = wertherReportDao.get("1");
		if(wertherReport == null){
			wertherReport = new WertherReport();
		}
		
		wertherReport.setWeatherReportContent(weatherReportContend);
		saveOrUpdate(null, wertherReport);
	}
	
	
	/**
	 * 保存.
	 * @param user 用户信息
	 * @param wertherReport 实体类
	 * @return
	 */
	private WertherReport save(UserProfile user, WertherReport wertherReport){
		this.wertherReportDao.save(wertherReport);
		return wertherReport;
	}
	/**
	 * 更新.
	 * @param user 用户信息
	 * @param wertherReport 实体类
	 * @return
	 */
	private WertherReport update(UserProfile user, WertherReport wertherReport){
		WertherReport wertherReportDb = this.wertherReportDao.get(wertherReport.getWeatherReportId());
		if(wertherReportDb == null){
			return this.save(user, wertherReport);
		} else {
			this.wertherReportDao.update(wertherReport);
			return wertherReport;
		}
		
	}
	
	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param weatherReportIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String weatherReportIds){
		if(StringUtils.isNotBlank(weatherReportIds)){
			for(String weatherReportId : weatherReportIds.split(",")){
				WertherReport wertherReportDb = this.wertherReportDao.get(weatherReportId);
				if (wertherReportDb != null) {
					this.wertherReportDao.delete(wertherReportDb);
				}
				
			}
		}
	}
	/**
	 * 获取远程天气情况.
	 * @return
	 */
	public String getRemoteWertherReport(){
		String city = EIPService.getRegistryService().get("CNEEC_WERTHER_CITY");
		String weatherContent = remoteWertherReportDao.getWeather(city);
		return weatherContent;
	}
	
	/**
	 * 刷新天气信息.
	 * @return
	 */
	public String refreshWeather(){
		String remoteWertherReport = CommonUtil.trim(getRemoteWertherReport());
		if(remoteWertherReport.length() > 0){
			saveOrUpdate(remoteWertherReport);
		} else {
			return "对不起，没有获取到天气数据";
		}
		
		String[] infos = remoteWertherReport.split("#\n");
		
		Date publishDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			publishDate = formatter.parse(infos[3]);
		} catch (Exception e){
		}
		
		if (publishDate != null){
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(publishDate);
			
			Calendar cal2 = Calendar.getInstance();
			int month = cal1.get(Calendar.DAY_OF_MONTH);
			if (month == cal2.get(Calendar.DAY_OF_MONTH)){
				return "";
			} else {
				return "天气信息的发布日期为：" + infos[3] + "，请核对今天的天气信息";
			}
		} else {
			return "天气信息无发布日期，请核对今天的天气信息";
		}
	}
	
}

