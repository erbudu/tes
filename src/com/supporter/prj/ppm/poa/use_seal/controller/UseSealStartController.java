/**
 * 
 */
package com.supporter.prj.ppm.poa.use_seal.controller;

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
import com.supporter.prj.ppm.poa.use_seal.constant.UseSealConstant;
import com.supporter.prj.ppm.poa.use_seal.entity.UseSealStartEntity;
import com.supporter.prj.ppm.poa.use_seal.service.UseSealStartService;
import com.supporter.spring_mvc.AbstractController;

/**
 *<p>Title: 启动用印前端控制器</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月18日
 */
@Controller
@RequestMapping("ppm/poa/use_seal/start/")
public class UseSealStartController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private UseSealStartService useSealStartService;
	
	/**
	 *<pre>初始化页面数据：盖章文件页面用</pre> 
	 */
	@RequestMapping("initPageData")
	public @ResponseBody UseSealStartEntity initPageData(String useSealId) {
		UseSealStartEntity entity = useSealStartService.initPageData(useSealId);
		return entity;
	}
	/**
	 * <pre>保存更新 </pre>
	 * @param useSealStartEntity 启动用印业务单信息
	 * @return 保存后的启动用印业务单信息
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<UseSealStartEntity> saveOrUpdate(UseSealStartEntity useSealStartEntity) {
		if(useSealStartEntity == null) {return null;}
		UserProfile user = getUserProfile();
		UseSealStartEntity backEntity =useSealStartService.saveOrUpdate(useSealStartEntity,user);
		if(backEntity == null) {
			return OperResult.fail("保存失败", null, null);
		}
		return OperResult.succeed("保存成功", null, backEntity);
	}
	
	/**
	 * <pre>初始化页面数据</pre>
	 * @param prjId 项目主键
	 * @param useSealBusiness 用印业务
	 * @param UUID 启动用印的业务单主键
	 * @param backPath 上传盖章文件完成后的数据处理路径 
	 * @return 用印业务表单实体类
	 */
	@RequestMapping("initAddOrEditPage")
	public  @ResponseBody UseSealStartEntity initAddOrEditPage(String prjId,String useSealBusiness,String businessUUID,String backPath) {
		if(prjId == "" || prjId == null) {};
		UserProfile user = getUserProfile();
		UseSealStartEntity useSealStartEntity = useSealStartService.initAddOrEditPage(user,prjId,useSealBusiness,businessUUID,backPath);
		return useSealStartEntity;
	}
	
	/**
	 * <pre>获取用印列表</pre>
	 * @param request
	 * @param jqGridReq
	 * @return 用印列表数据
	 */
	@RequestMapping("getPrjGrid")
	public @ResponseBody JqGrid getGridData(HttpServletRequest request, JqGridReq jqGridReq,String prjId){
		
		UserProfile user = getUserProfile();
		
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
	    
	    this.useSealStartService.getGridData(user, jqGrid, prjId);
		return jqGrid;
	}
	
	/**
	 * <pre>通过主键获取项目信息</pre>
	 * @param useSealId 用印主键
	 * @return 用印业务单信息
	 */
	@RequestMapping("getById")
	public @ResponseBody UseSealStartEntity getById(String useSealId) {
		return useSealStartService.get(useSealId);
	}
	
	/**
	 * <pre>通过项目id获取项目信息</pre>
	 * @param prjId 业务主键
	 * @return 用印业务单信息
	 */
	@RequestMapping("getByBusinessId")
	public @ResponseBody UseSealStartEntity getByBusinessId(String businessId) {
		return useSealStartService.getByBusinessId(businessId);
	}
	
	/**
	 * <pre>上传盖章文件后改变流程状态</pre>
	 * @param useSealId
	 * @return
	 */
	@RequestMapping("updateStatus")
	public @ResponseBody UseSealStartEntity updateStatus(String useSealId) {
		return useSealStartService.updateStatus(useSealId);
	} 
	
	/**
	 * <pre>获取用印类型码表</pre>
	 * @return
	 */
	@RequestMapping("getUseSealType")
	public @ResponseBody List<Map<String,String>> getUseSealType(){
		return UseSealConstant.getUseSealTypeMap();
	}
	/**
	 * <pre>
	 * @Title : 删除用印单信息
	 * @param useSealId  用印单主键
	 * @return
	 * </pre>
	 */
	
	@RequestMapping("deleteUseSeal")
	public @ResponseBody UseSealStartEntity deleteUseSeal(String useSealId){
		return useSealStartService.deleteUseSeal(useSealId);
	}
	
	/**
	 * 将流程状态设为草稿
	 * @param entity
	 * @return
	 */
	@RequestMapping("clearProcId")
	public @ResponseBody String clearProcId(UseSealStartEntity entity) {
		
		UserProfile user = getUserProfile();
		useSealStartService.clearProcId(user, entity);
		
		return "success";
	}
}
