package com.supporter.prj.linkworks.oa.roll_information.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.roll_information.entity.RollInformation;
import com.supporter.prj.linkworks.oa.roll_information.entity.WeatherReportContent;
import com.supporter.prj.linkworks.oa.roll_information.service.RollInformationService;
import com.supporter.prj.linkworks.oa.roll_information.service.WeatherReportContentService;
import com.supporter.spring_mvc.AbstractController;


/**   
 * @Title: Controller
 * @Description: 新闻公告.
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("oa/rollInformation")
public class RollInformationController  extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private RollInformationService rollInformationService;
	@Autowired
	private WeatherReportContentService weatherReportService;
	
	
	/**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, String attr) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.rollInformationService.getGrid(user, jqGrid, attr);
		return jqGrid;
	}
	
	/**
	 * 进入新建或编辑时加载的信息
	 * 
	 * @param rollInforId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody RollInformation initEditOrViewPage(String rollInforId) {
		RollInformation entity = rollInformationService.initEditOrViewPage(rollInforId,this.getUserProfile());
		return entity;
	}
	
	
	/**
	 * 获取字典数据
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getInforTitleCodetable")
	public Map<String, String> getInforTitleCodetable() throws IOException {
		Map<String, String> map = RollInformation.getInforTitleCodetable();
		return map;
	}
	
	/**
	 * 保存或更新数据.
	 * 
	 * @param rollInformation 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<RollInformation> saveOrUpdate(RollInformation rollInformation  ) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(RollInformation.class);
		RollInformation entity = this.rollInformationService.saveOrUpdate(user, rollInformation, valueMap);
		if(entity.getPublishStatus() == RollInformation.PUBLISHED){
			return OperResult.succeed("submitSuccess", null, entity);
		}
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 删除操作
	 * 
	 * @param rollInforIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String rollInforIds) {
		UserProfile user = this.getUserProfile();
		this.rollInformationService.delete(user, rollInforIds);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 取消发布
	 * 
	 * @param RollInformation 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("cancelSubmit")
	public @ResponseBody OperResult<RollInformation> saveOrUpdate(String rollInforId ) {
		RollInformation entity = this.rollInformationService.cancelSubmit(rollInforId);
		return OperResult.succeed("cancelSuccess", null, entity);
	}
	
	/**
	 * 进入查看页面时加载的信息
	 * 
	 * @param rollInforId 主键
	 * @return
	 */
	@RequestMapping("getInView")
	public @ResponseBody RollInformation getInView(String rollInforId) {
		RollInformation entity = rollInformationService.getInView(rollInforId,this.getUserProfile());
		return entity;
	}
	
	/**
	 * 获取首页滚动信息
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getSrollInformation")
	public List<RollInformation> getSrollInformation(HttpServletRequest request, JqGridReq jqGridReq) throws IOException {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		List<RollInformation> list = rollInformationService.getSrollInformation(jqGrid);
		return list;
	}
	
	/**
	 * 获取首页天气滚动信息
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getWeatherReport")
	public List<WeatherReportContent> getWeatherReport() throws IOException {
		List<WeatherReportContent> list = new ArrayList<WeatherReportContent>();
		list.addAll(weatherReportService.getWeatherReport());
		return list;
	}
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("extractFiles")
	public @ResponseBody String extractFiles(String rollInforId){
		return rollInformationService.extractFiles(rollInforId,this.getUserProfile());
	}
	
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("batchExtractFiles")
	public @ResponseBody String batchExtractFiles(){
		return rollInformationService.batchExtractFiles(this.getUserProfile());
	}
}
