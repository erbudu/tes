package com.supporter.prj.eip.code_share.code.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.eip.code_share.code.service.EntityProjectService;
import com.supporter.prj.eip.code_share.code.entity.EntityProject;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.common.entity.OperResult;

/**   
 * @Title: Controller
 * @Description: CS_ENITY_PROJECT.
 * @author Administrator
 * @date 2019-07-17 16:46:42
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("code_share/code/enityProject")
public class EntityProjectController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EntityProjectService enityProjectService;
	
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param prjRecId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody EntityProject initEditOrViewPage(String prjRecId){
		EntityProject entity = enityProjectService.initEditOrViewPage(prjRecId);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * @param request 请求
	 * @param jqGridReq 表格
	 * @param enityProject 项目
	 * @return JqGrid
	 * @throws Exception 异常
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, EntityProject enityProject) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.enityProjectService.getGrid(user, jqGrid, enityProject);
		return jqGrid;
	}
	
	/**
	 * 保存数据.
	 * 
	 * @param enityProject 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult< EntityProject > saveOrUpdate(EntityProject enityProject){
		UserProfile user = this.getUserProfile();
		OperResult< EntityProject > op = this.enityProjectService.saveOrUpdate(user, enityProject);
		return op;
	}

	/**
	 * 删除操作.
	 * 
	 * @param prjRecIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult< ? > batchDel(String prjRecIds) {
		UserProfile user = this.getUserProfile();
		OperResult< ? > op = this.enityProjectService.delete(user, prjRecIds);
		return op;
	}
	
	/**
	 * 根据项目库和项目ID获取项目对象
	 * @param prjLib 项目库
	 * @param prjId 项目ID
	 * @return EntityProject
	 */
	@RequestMapping("getProject")
	@ResponseBody
	public EntityProject getProject(String prjLib, String prjId) {
		return enityProjectService.getPrjInLibById(prjLib, prjId);
	}

}
