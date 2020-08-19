package com.supporter.prj.ppm.ecc.dept_review.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.ecc.dept_review.service.EccDeptRevieConService;
import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptRevieCon;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 部门出口管制结论.
 * @author YAN
 * @date 2019-08-16 18:43:21
 * @version V1.0
 */
@Controller
@RequestMapping("ecc/dept_review/eccDeptRevieCon")
public class EccDeptRevieConController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EccDeptRevieConService eccDeptRevieConService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param prjId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody EccDeptRevieCon initEditOrViewPage(String prjId) {
		EccDeptRevieCon entity = eccDeptRevieConService.initEditOrViewPage(prjId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, EccDeptRevieCon eccDeptRevieCon) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.eccDeptRevieConService.getGrid(user, jqGrid, eccDeptRevieCon);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param eccDeptRevieCon 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(EccDeptRevieCon eccDeptRevieCon) {
		UserProfile user = this.getUserProfile();
		EccDeptRevieCon entity = this.eccDeptRevieConService.saveOrUpdate(user, eccDeptRevieCon);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 删除操作
	 * 
	 * @param deptEccRvConIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String deptEccRvConIds) {
		UserProfile user = this.getUserProfile();
		this.eccDeptRevieConService.delete(user, deptEccRvConIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param deptEccRvConId
	 * @param deptEccRvConName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String deptEccRvConId,String deptEccRvConName) {
		return this.eccDeptRevieConService.nameIsValid(deptEccRvConId, deptEccRvConName);
	}

}
