package com.supporter.prj.ppm.ecc.base_info.fund_plan.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.ecc.base_info.fund_plan.service.EccFundPlanService;
import com.supporter.prj.ppm.ecc.base_info.fund_plan.entity.EccFundPlan;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 出口管制资金安排.
 * @author YAN
 * @date 2019-08-16 18:42:25
 * @version V1.0
 */
@Controller
@RequestMapping("ecc/fund_plan/eccFundPlan")
public class EccFundPlanController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EccFundPlanService eccFundPlanService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param fpId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody EccFundPlan initEditOrViewPage(String fpId) {
		EccFundPlan entity = eccFundPlanService.initEditOrViewPage(fpId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, EccFundPlan eccFundPlan) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.eccFundPlanService.getGrid(user, jqGrid, eccFundPlan);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param eccFundPlan 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(EccFundPlan eccFundPlan) {
		UserProfile user = this.getUserProfile();
		EccFundPlan entity = this.eccFundPlanService.saveOrUpdate(user, eccFundPlan);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param fpIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String fpIds) {
		UserProfile user = this.getUserProfile();
		this.eccFundPlanService.delete(user, fpIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param fpId
	 * @param fpName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String fpId,String fpName) {
		return this.eccFundPlanService.nameIsValid(fpId, fpName);
	}

}
