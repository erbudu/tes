package com.supporter.prj.ppm.contract.effect.report.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.contract.effect.report.service.ContractEffectReportConService;
import com.supporter.prj.ppm.contract.effect.report.entity.ContractEffectReportCon;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 报审审核结果.
 * @author YAN
 * @date 2019-09-05 17:09:59
 * @version V1.0
 */
@Controller
@RequestMapping("contract/effect/report/contractEffectReportCon")
public class ContractEffectReportConController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractEffectReportConService contractEffectReportConService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param contractEffectRvConId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractEffectReportCon initEditOrViewPage(String contractEffectRvConId) {
		ContractEffectReportCon entity = contractEffectReportConService.initEditOrViewPage(contractEffectRvConId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractEffectReportCon contractEffectReportCon) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractEffectReportConService.getGrid(user, jqGrid, contractEffectReportCon);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractEffectReportCon 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(ContractEffectReportCon contractEffectReportCon) {
		UserProfile user = this.getUserProfile();
		ContractEffectReportCon entity = this.contractEffectReportConService.saveOrUpdate(user, contractEffectReportCon);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 删除操作
	 * 
	 * @param contractEffectRvConIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String contractEffectRvConIds) {
		UserProfile user = this.getUserProfile();
		this.contractEffectReportConService.delete(user, contractEffectRvConIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param contractEffectRvConId
	 * @param contractEffectRvConName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String contractEffectRvConId,String contractEffectRvConName) {
		return this.contractEffectReportConService.nameIsValid(contractEffectRvConId, contractEffectRvConName);
	}

}
