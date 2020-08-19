package com.supporter.prj.dept_resource.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.dept_resource.entity.DeptResourceAuth;
import com.supporter.prj.dept_resource.service.DeptResourceAuthService;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("util/dept_resourceAuth")
public class DeptResourceAuthController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private DeptResourceAuthService deptResourceAuthService;

	// @Autowired
	// private DeptResourceAuthService deptResourceAuthService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * 
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq,
			DeptResourceAuth deptResourceAuth, String resourceId,
			String authType) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		deptResourceAuth.setResourceId(resourceId);
		deptResourceAuth.setAuthType(authType);
		this.deptResourceAuthService.getGrid(user, jqGrid, deptResourceAuth);
		return jqGrid;
	}

	/**
	 * 根据主键获取功能模块�?.
	 * 
	 * @param reportId
	 *            主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody
	DeptResourceAuth get(String resourceId) {
		UserProfile user = this.getUserProfile();
		DeptResourceAuth deptResourceAuth = deptResourceAuthService
				.get(resourceId);
		return deptResourceAuth;

	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param resourceId
	 *            主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	DeptResourceAuth initEditOrViewPage(HttpServletRequest request,
			JqGridReq jqGridReq, String authId, String resourceId,
			String authorizeeType, String authType) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		DeptResourceAuth entity = deptResourceAuthService.initEditOrViewPage(
				jqGrid, authId, resourceId, authorizeeType, authType, this
						.getUserProfile());
		return entity;
	}

	/**
	 * 保存或更新数据
	 * 
	 * @param report
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<DeptResourceAuth> saveOrUpdate(DeptResourceAuth deptResourceAuth) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this
				.getPropValues(DeptResourceAuth.class);
		DeptResourceAuth entity = this.deptResourceAuthService.saveOrUpdate(
				user, deptResourceAuth, valueMap);
		// return
		// OperResult.succeed(DeptResourceAuthConstant.I18nKey.SAVE_SUCCESS,
		// null, null);
		return OperResult.succeed("saveSuccess", null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param reportIds
	 *            主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult batchDel(String authIds) {
		UserProfile user = this.getUserProfile();
		this.deptResourceAuthService.delete(user, authIds);
		// return
		// OperResult.succeed(DeptResourceAuthConstant.I18nKey.DELETE_SUCCESS,
		// null, null);
		return OperResult.succeed("deleteSuccess", null, null);
	}
}
