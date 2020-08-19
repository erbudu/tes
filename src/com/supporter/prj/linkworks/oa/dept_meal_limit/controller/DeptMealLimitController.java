package com.supporter.prj.linkworks.oa.dept_meal_limit.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.dept_meal_limit.entity.DeptMealLimit;
import com.supporter.prj.linkworks.oa.dept_meal_limit.entity.DeptMealLimitBo;
import com.supporter.prj.linkworks.oa.dept_meal_limit.service.DeptMealLimitService;
import com.supporter.prj.linkworks.oa.dept_meal_limit.util.DeptMealLimitConstant;
import com.supporter.prj.linkworks.oa.emp_meal_apply.constants.EmpMealAuthConstant;
import com.supporter.prj.linkworks.oa.integral.constants.IntegralAuthConstant;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: Controller
 * @Description: 物资信息设置.
 * @author yanbingchao
 * @date 2017-3-27 15:25:34
 * @version V1.0
 * 
 */

@Controller
@RequestMapping("oa/deptMealLimit")
public class DeptMealLimitController extends AbstractController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private DeptMealLimitService codeService;

	/**
	 * 根据主键获取.
	 * 
	 * @param deptMealLimitId
	 *            主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody
	DeptMealLimit get(String deptMealLimitId) {
		DeptMealLimit code = codeService.get(deptMealLimitId);
		return code;

	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param cideId
	 *            主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	DeptMealLimit initEditOrViewPage(String deptMealLimitId, String docClassify) {
		UserProfile user = this.getUserProfile();
		DeptMealLimit entity = codeService.initEditOrViewPage(deptMealLimitId,
				docClassify, user);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@AuthCheck(module = EmpMealAuthConstant.MODULE_ID, oper = EmpMealAuthConstant.AUTH_OPER_LIMIT, failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getTreeGrid(HttpServletRequest request, JqGridReq jqGridReq,
			DeptMealLimit code, String pagetype) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.codeService.getGrid(user, jqGrid, code, pagetype);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param code
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@AuthCheck(module = EmpMealAuthConstant.MODULE_ID, oper = EmpMealAuthConstant.AUTH_OPER_LIMIT, failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<DeptMealLimit> saveOrUpdate(DeptMealLimitBo f) {
		UserProfile user = this.getUserProfile();
		List<DeptMealLimit> limits = f.getLimits();
		Map<String, Object> valueMap = this.getPropValues(DeptMealLimit.class);
		this.codeService.saveOrUpdate(user, limits, valueMap);
		return OperResult.succeed(DeptMealLimitConstant.I18nKey.SAVE_SUCCESS,
				"保存成功", null);
	}

	/**
	 * 删除操作
	 * 
	 * @param deptMealLimitIds
	 *            主键集合，多个以逗号分隔
	 * @return
	 */
	@AuthCheck(module = EmpMealAuthConstant.MODULE_ID, oper = EmpMealAuthConstant.AUTH_OPER_LIMIT, failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult batchDel(String deptMealLimitIds) {
		UserProfile user = this.getUserProfile();
		this.codeService.delete(user, deptMealLimitIds);
		return OperResult.succeed(DeptMealLimitConstant.I18nKey.DELETE_SUCCESS,
				null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param deptMealLimitId
	 * @param materialName
	 * @return
	 */
	@RequestMapping("checkNameIsValid")
	public @ResponseBody
	Boolean checkNameIsValid(String deptMealLimitId, String materialName) {
		return this.codeService.checkNameIsValid(deptMealLimitId, materialName);
	}

}
