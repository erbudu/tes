package com.supporter.prj.ppm.contract.sign.report.controller;

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
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReport;
import com.supporter.prj.ppm.contract.sign.report.service.ContractSignReportService;
import com.supporter.prj.ppm.ecc.OperResultConstant;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: Controller
 * @Description: 主合同签约报审表.
 * @author YAN
 * @date 2019-09-05 17:09:55
 * @version V1.0
 */
@Controller
@RequestMapping("contract/sign/report/contractSignReport")
public class ContractSignReportController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractSignReportService contractSignReportService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param contractSignId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractSignReport initEditOrViewPage(String contractSignId) {
		ContractSignReport entity = contractSignReportService.initEditOrViewPage(contractSignId);
		return entity;
	}
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 *
	 * @param contractSignId 主键
	 * @return
	 */
	@RequestMapping("initPageByPrjId")
	public @ResponseBody ContractSignReport initPageByPrjId(String prjId) {
		ContractSignReport entity = contractSignReportService.initPageByPrjId(prjId);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractSignReport contractSignReport) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractSignReportService.getGrid(user, jqGrid, contractSignReport);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractSignReport 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(ContractSignReport contractSignReport) {
		UserProfile user = this.getUserProfile();
		ContractSignReport entity = this.contractSignReportService.saveOrUpdate(user, contractSignReport);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param contractSignIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String contractSignIds) {
		UserProfile user = this.getUserProfile();
		this.contractSignReportService.delete(user, contractSignIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param contractSignId
	 * @param contractSignName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String contractSignId,String contractSignName) {
		return this.contractSignReportService.nameIsValid(contractSignId, contractSignName);
	}

	@RequestMapping("validSign")
	public @ResponseBody
	OperResult<?> validSign(String prjId) {
		String message = "";
		boolean result = false;
		Map<String, Object> map = this.contractSignReportService.validSign(prjId, message, result);
		message = (String) map.get("msg");
		result = (Boolean) map.get("result");
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, message, result);
	}

}
