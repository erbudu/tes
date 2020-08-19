package com.supporter.prj.linkworks.oa.news_alerts.controller;


import java.io.IOException;
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
import com.supporter.prj.linkworks.oa.bulletin.entity.Bulletin;
import com.supporter.prj.linkworks.oa.news_alerts.entity.NewsAlerts;
import com.supporter.prj.linkworks.oa.news_alerts.service.NewsAlertsService;
import com.supporter.spring_mvc.AbstractController;


/**   
 * @Title: Controller
 * @Description: 新闻公告.
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("oa/newsAlerts")
public class NewsAlertsController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private NewsAlertsService newsAlertsService;
	

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
		this.newsAlertsService.getGrid(user, jqGrid, attr);
		return jqGrid;
	}
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param newsId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody NewsAlerts initEditOrViewPage(String newsAlertsId) {
		NewsAlerts entity = newsAlertsService.initEditOrViewPage(newsAlertsId,this.getUserProfile());
		return entity;
	}
	
	/**
	 * 删除操作
	 * 
	 * @param newsIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String newsAlertsIds) {
		UserProfile user = this.getUserProfile();
		this.newsAlertsService.delete(user, newsAlertsIds);
		return OperResult.succeed("deleteSuccess");
	}
	
	
	/**
	 * 获取字典数据
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getDisplayTypeCodetable")
	public Map<Integer, String> getDisplayTypeCodetable() throws IOException {
		Map<Integer, String> map = NewsAlerts.getTypeCodeTable();
		return map;
	}
	
	/**
	 * 保存或更新数据.
	 * 
	 * @param news 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<NewsAlerts> saveOrUpdate(NewsAlerts newsAlerts,String filesName) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(Bulletin.class);
		NewsAlerts entity = this.newsAlertsService.saveOrUpdate(user, newsAlerts, valueMap);
		if(newsAlerts.getPublishStatus() == NewsAlerts.PUBLISHED){
			return OperResult.succeed("submitSuccess", null, entity);
		}
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 取消发布
	 * 
	 * @param news 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("cancelSubmit")
	public @ResponseBody OperResult<NewsAlerts> saveOrUpdate(String newsAlertsId ) {
		NewsAlerts entity = this.newsAlertsService.cancelSubmit(newsAlertsId);
		return OperResult.succeed("cancelSuccess", null, entity);
	}
	
	
	/**
	 * 获取预览时的窗口
	 * @param newsAlertsId
	 * @return
	 */
	@RequestMapping("getNewAlertsDisplay")
	public @ResponseBody String getNewAlertsDisplay(String newsAlertsId,String path) {
		String ls_Div = newsAlertsService.chooseDisplayType(newsAlertsId,path);
		return ls_Div;
	}
	
	/**
	 * 获取首页的窗口
	 * @param newsAlertsId
	 * @return
	 */
	@RequestMapping("getNewsAlertsDisplay")
	public @ResponseBody String getNewsAlertsDisplay(HttpServletRequest request, JqGridReq jqGridReq, String path) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		String ls_Div = newsAlertsService.getNewsAlertsDisplay(jqGrid,path);
		return ls_Div;
	}
	
	/**
	 * 首页获取新闻快讯对象
	 * 
	 * @param newsId 主键
	 * @return
	 */
	@RequestMapping("getNewsAlerts")
	public @ResponseBody NewsAlerts getNewsAlerts(HttpServletRequest request, JqGridReq jqGridReq) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		NewsAlerts entity = newsAlertsService.getNewsAlerts(jqGrid);
		return entity;
	}
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("extractFiles")
	public @ResponseBody String extractFiles(String newsAlertsId){
		return newsAlertsService.extractFiles(newsAlertsId,this.getUserProfile());
	}
	
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("batchExtractFiles")
	public @ResponseBody String batchExtractFiles(){
		return newsAlertsService.batchExtractFiles(this.getUserProfile());
	}
}
