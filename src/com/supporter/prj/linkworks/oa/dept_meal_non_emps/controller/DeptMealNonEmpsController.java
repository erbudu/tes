package com.supporter.prj.linkworks.oa.dept_meal_non_emps.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
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
import com.supporter.prj.linkworks.oa.dept_meal_non_emps.entity.DeptMealNonEmps;
import com.supporter.prj.linkworks.oa.dept_meal_non_emps.service.DeptMealNonEmpsService;
import com.supporter.prj.linkworks.oa.dept_meal_non_emps.util.DeptMealNonEmpsConstant;
import com.supporter.prj.linkworks.oa.emp_meal_apply.constants.EmpMealAuthConstant;
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
@RequestMapping("oa/dept_meal_non_emps")
public class DeptMealNonEmpsController extends AbstractController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private DeptMealNonEmpsService codeService;

	/**
	 * 根据主键获取.
	 * 
	 * @param nonEmpIds
	 *            主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody
	DeptMealNonEmps get(String nonEmpIds) {
		DeptMealNonEmps code = codeService.get(nonEmpIds);
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
	DeptMealNonEmps initEditOrViewPage(String nonEmpIds,String docClassify) {
		UserProfile user = this.getUserProfile();
		DeptMealNonEmps entity = codeService.initEditOrViewPage(nonEmpIds,docClassify, user);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@AuthCheck(module = EmpMealAuthConstant.MODULE_ID, oper = EmpMealAuthConstant.AUTH_OPER_EMP, failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getTreeGrid(HttpServletRequest request, JqGridReq jqGridReq,
			DeptMealNonEmps code) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.codeService.getGrid(user, jqGrid, code);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param code
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@AuthCheck(module = EmpMealAuthConstant.MODULE_ID, oper = EmpMealAuthConstant.AUTH_OPER_EMP, failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<DeptMealNonEmps> saveOrUpdate(DeptMealNonEmps code) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(DeptMealNonEmps.class);
		DeptMealNonEmps entity = this.codeService.saveOrUpdate(user, code, valueMap);
		return OperResult.succeed(DeptMealNonEmpsConstant.I18nKey.SAVE_SUCCESS, "保存成功",
				entity);
	}
	/**
	 * 删除操作
	 * 
	 * @param nonEmpIdss
	 *            主键集合，多个以逗号分隔
	 * @return
	 */
	@AuthCheck(module = EmpMealAuthConstant.MODULE_ID, oper = EmpMealAuthConstant.AUTH_OPER_EMP, failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult batchDel(String nonEmpIdss) {
		UserProfile user = this.getUserProfile();
		this.codeService.delete(user, nonEmpIdss);
		return OperResult.succeed(DeptMealNonEmpsConstant.I18nKey.DELETE_SUCCESS, null,
				null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param nonEmpIds
	 * @param materialName
	 * @return
	 */
	@RequestMapping("checkNameIsValid")
	public @ResponseBody
	Boolean checkNameIsValid(DeptMealNonEmps entity) {
		return this.codeService.checkNameIsValid(entity);
	}

	@RequestMapping("getNonEmpType")
	public @ResponseBody
	Map<String, String> getNonEmpType() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		/*
		 * List <IComCodeTableItem> list =
		 * EIPService.getComCodeTableService().getCodeTableItems("UNIT");
		 * for(IComCodeTableItem item : list){ map.put(item.getItemId(),
		 * item.getDisplayName()); }
		 */
		map.put("1", "人员卡");
		map.put("2", "公用卡");
		map.put("3", "客饭卡");
		return map;
	}



}
