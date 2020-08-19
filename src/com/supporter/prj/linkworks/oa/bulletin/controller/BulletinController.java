package com.supporter.prj.linkworks.oa.bulletin.controller;

import java.io.IOException;
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
import com.supporter.prj.linkworks.oa.bulletin.entity.Bulletin;
import com.supporter.prj.linkworks.oa.bulletin.service.BulletinService;
import com.supporter.spring_mvc.AbstractController;

/**   
 * @Title: Controller
 * @Description: 公司公告.
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("oa/bulletin_oa")
public class BulletinController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private BulletinService bulletinService;
	
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq,String attr,String dateFrom,String dateTo,String bulletinType,String bull_type) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.bulletinService.getGrid(user, jqGrid, attr, dateFrom, dateTo, bulletinType,bull_type);
		return jqGrid;
	}
	
	
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGridNotAuth")
	public @ResponseBody
	JqGrid getGridNotAuth(HttpServletRequest request, JqGridReq jqGridReq,String attr,String dateFrom,String dateTo,String bulletinType) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.bulletinService.getGridNotAuth(user, jqGrid, attr, dateFrom, dateTo, bulletinType);
		return jqGrid;
	}
	
	/**
	 * 判断有没有权限（该权限在部门资源管理模块设置）操作
	 * 
	 * @param bulletinIds 主键集合，多个以逗号分隔
	 * @param canTodo 操作类型（包括 canNew、canWrite、canDelete）
	 * @return String (yes/no)
	 */
	@RequestMapping("isCanOperate")
	public @ResponseBody String isCanOperate(String bulletinId,String canTodo) {
		UserProfile user = this.getUserProfile();
		String isCanOperate=this.bulletinService.isCanOperate(user,bulletinId,canTodo);
		return isCanOperate;
	}
	
	/**
	 * 手机端分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("iphonePage")
	public @ResponseBody
	JqGrid iphonePage(HttpServletRequest request, JqGridReq jqGridReq) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.bulletinService.iphonePage(user, jqGrid);
		return jqGrid;
	}
	
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getTopGrid")
	@ResponseBody
	public JqGrid getTopGrid(HttpServletRequest request, JqGridReq jqGridReq) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		int includeConsignment = this.getRequestParaInt("includeConsignment", BulletinService.IncludeConsignment.INCLUDE_CONSIGNMENT);
		this.bulletinService.getTopGrid(user, jqGrid, includeConsignment);
		return jqGrid;
	}
	
	
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getTopGridNotAuth")
	@ResponseBody
	public JqGrid getTopGridNotAuth(HttpServletRequest request, JqGridReq jqGridReq) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		int includeConsignment = this.getRequestParaInt("includeConsignment", BulletinService.IncludeConsignment.INCLUDE_CONSIGNMENT);
		this.bulletinService.getTopGridNotAuth(user, jqGrid, includeConsignment);
		return jqGrid;
	}
	
	
	/**
	 * 获取手机端首页展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPhoneList")
	@ResponseBody
	public List < Bulletin > getPhoneList(HttpServletRequest request, JqGridReq jqGridReq) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		List<Bulletin> list = this.bulletinService.getTopGrid(user, jqGrid, BulletinService.IncludeConsignment.INCLUDE_CONSIGNMENT);
		return list;
	}

	
	/**
	 * 获取字典数据
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getBulletinTypeCodetable")
	public Map<String, String> getBulletinTypeCodetable() throws IOException {
		Map<String, String> map = Bulletin.getBulletinTypeCodetable();
		return map;
	}

	
	@ResponseBody
	@RequestMapping(value = "/getBulletinTypeMoreCodetable")
	public Map<String, String> getBulletinTypeMoreCodetable() throws IOException {
		Map<String, String> map = Bulletin.getBulletinTypeMoreCodetable();
		return map;
	}
	
	/**
	 * 进入新建或编辑时加载的信息
	 * 
	 * @param newsId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody Bulletin initEditOrViewPage(String bulletinId) {
		Bulletin entity = bulletinService.initEditOrViewPage(bulletinId,this.getUserProfile());
		return entity;
	}
	
	/**
	 * 进入查看页面时加载的信息
	 * 
	 * @param newsId 主键
	 * @return
	 */
	@RequestMapping("getInView")
	public @ResponseBody Bulletin getInView(String bulletinId) {
		Bulletin entity = bulletinService.getInView(bulletinId,this.getUserProfile());
		return entity;
	}
	
	
	/**
	 * 保存或更新数据.
	 * 
	 * @param news 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<Bulletin> saveOrUpdate(Bulletin bulletin,String filesName) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(Bulletin.class);
		Bulletin entity = this.bulletinService.saveOrUpdate(user, bulletin, valueMap);
		if(bulletin.getPublishStatus() == Bulletin.PUBLISHED){
			return OperResult.succeed("submitSuccess", null, entity);
		}else{
			return OperResult.succeed("saveSuccess", null, entity);
		}
		
	}
	
	/**
	 * 删除操作
	 * 
	 * @param reportIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String bulletinIds) {
		UserProfile user = this.getUserProfile();
		this.bulletinService.delete(user, bulletinIds);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 取消发布操作
	 * 
	 * @param reportIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("cancelSubmit")
	public @ResponseBody OperResult cancelSubmit(String bulletinId) {
		UserProfile user = this.getUserProfile();
		this.bulletinService.cancelSubmit(user, bulletinId);
		return OperResult.succeed("cancelSuccess");
	}
	
	/**
	 * 通过员工id获取部门名称
	 * 
	 * @param newsId 主键
	 * @return
	 */
	@RequestMapping("getDeptName")
	public @ResponseBody String getDeptName(String emp) {
		String deptName = bulletinService.getDeptName(emp);
		return deptName;
	}
	
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("extractFiles")
	public @ResponseBody String extractFiles(String bulletinId){
		return bulletinService.extractFiles(bulletinId,this.getUserProfile());
	}
	
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("batchExtractFiles")
	public @ResponseBody String batchExtractFiles(){
		return bulletinService.batchExtractFiles(this.getUserProfile());
	}

}
