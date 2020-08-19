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
import com.supporter.prj.ppm.contract.effect.report.service.ContractEffectReportService;
import com.supporter.prj.ppm.contract.effect.report.entity.ContractEffectReport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 主合同签约报审表.
 * @author YAN
 * @date 2019-09-05 17:09:55
 * @version V1.0
 */
@Controller
@RequestMapping("contract/effect/report/contractEffectReport")
public class ContractEffectReportController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractEffectReportService contractEffectReportService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param contractEffectId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractEffectReport initEditOrViewPage(String contractEffectId) {
		ContractEffectReport entity = contractEffectReportService.initEditOrViewPage(contractEffectId);
		return entity;
	}
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 *
	 * @param contractEffectId 主键
	 * @return
	 */
	@RequestMapping("initPageByPrjId")
	public @ResponseBody ContractEffectReport initPageByPrjId(String prjId) {
		ContractEffectReport entity = contractEffectReportService.initPageByPrjId(prjId);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractEffectReport contractEffectReport) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractEffectReportService.getGrid(user, jqGrid, contractEffectReport);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractEffectReport 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(ContractEffectReport contractEffectReport) {
		UserProfile user = this.getUserProfile();
		ContractEffectReport entity = this.contractEffectReportService.saveOrUpdate(user, contractEffectReport);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param contractEffectIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String contractEffectIds) {
		UserProfile user = this.getUserProfile();
		this.contractEffectReportService.delete(user, contractEffectIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param contractEffectId
	 * @param contractEffectName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String contractEffectId,String contractEffectName) {
		return this.contractEffectReportService.nameIsValid(contractEffectId, contractEffectName);
	}


	@RequestMapping("validEffect")
	public @ResponseBody
	OperResult<?> validSign(String prjId) {
		String message = "";
		String nextPage = "";
		boolean result = false;
		Map<String, Object> map = this.contractEffectReportService.validEffect(prjId, message, result);
		nextPage = (String) map.get("nextPage");
		message = (String) map.get("msg");
		result = (Boolean) map.get("result");
		OperResult<?> o =OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, message, result);
		o.setNextPage(nextPage);
		return o;
	}
}
