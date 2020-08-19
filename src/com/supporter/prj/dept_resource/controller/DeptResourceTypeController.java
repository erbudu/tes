package com.supporter.prj.dept_resource.controller;

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
import com.supporter.prj.dept_resource.entity.DeptResourceType;
import com.supporter.prj.dept_resource.entity.DeptResourceTypeUi;
import com.supporter.prj.dept_resource.service.DeptResourceTypeService;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApplyPerson;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("util/dept_resourceType")
public class DeptResourceTypeController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private DeptResourceTypeService deptResourceTypeService;

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
			DeptResourceType deptResourceType) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.deptResourceTypeService.getGrid(user, jqGrid, deptResourceType);
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
	DeptResourceType get(String typeCode) {
		UserProfile user = this.getUserProfile();
		DeptResourceType deptResourceType = deptResourceTypeService
				.get(typeCode);
		return deptResourceType;

	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param typeCode
	 *            主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	DeptResourceType initEditOrViewPage(HttpServletRequest request,
			JqGridReq jqGridReq, String typeCode) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		DeptResourceType entity = deptResourceTypeService.initEditOrViewPage(
				jqGrid, typeCode, this.getUserProfile());
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
	OperResult<DeptResourceType> saveOrUpdate(
			DeptResourceType deptResourceType, String typeCodeOld) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this
				.getPropValues(DeptResourceType.class);
		//batchDel(typeCodeOld);
		DeptResourceType entity = this.deptResourceTypeService.saveOrUpdate(
				user, deptResourceType, valueMap);
		// return OperResult.succeed(DeptResourceConstant.I18nKey.SAVE_SUCCESS,
		// null, null);
		return OperResult.succeed("saveSuccess", null, entity);
	}

	/**
	 * 展示Ui列表.
	 * 
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getUiGrid")
	public @ResponseBody
	JqGrid getUiGrid(HttpServletRequest request, JqGridReq jqGridReq,
			AuthorityApplyPerson ap, String typeCode) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		UserProfile user = this.getUserProfile();
		List<DeptResourceTypeUi> list = this.deptResourceTypeService.getUiGrid(
				user, jqGrid, typeCode);
		return jqGrid;
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
	OperResult batchDel(String typeCodes) {
		UserProfile user = this.getUserProfile();
		this.deptResourceTypeService.delete(user, typeCodes);
		// return
		// OperResult.succeed(DeptResourceConstant.I18nKey.DELETE_SUCCESS, null,
		// null);
		return OperResult.succeed("deleteSuccess", null, null);
	}
	
	/**
	 * 判断名字是否重复.
	 * @param typeCode
	 * @return
	 */
	@RequestMapping("checkNameIsValid")
	@ResponseBody
	public Boolean checkNameIsValid(String typeCode) {
		return this.deptResourceTypeService.checkNameIsValid(typeCode);
	}
	
	/**
	 * 获取资源类型注册码表编号.
	 * @param typeCode
	 * @return
	 */
	@RequestMapping("/getResourceTypeCtbl")
	@ResponseBody
	public String getResourceTypeCtbl(String typeCode){
		return deptResourceTypeService.getResourceTypeCtbl(typeCode);
	}

}
