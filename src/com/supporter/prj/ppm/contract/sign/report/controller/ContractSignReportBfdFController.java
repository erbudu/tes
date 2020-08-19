package com.supporter.prj.ppm.contract.sign.report.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.contract.sign.report.service.ContractSignReportBfdFService;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReportBfdF;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 主合同签约评审资料清单单文件.
 * @author YAN
 * @date 2019-09-05 17:09:58
 * @version V1.0
 */
@Controller
@RequestMapping("contract/sign/report/contractSignReportBfdF")
public class ContractSignReportBfdFController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractSignReportBfdFService contractSignReportBfdFService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param recordId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractSignReportBfdF initEditOrViewPage(String recordId) {
		ContractSignReportBfdF entity = contractSignReportBfdFService.initEditOrViewPage(recordId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractSignReportBfdF contractSignReportBfdF) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractSignReportBfdFService.getGrid(user, jqGrid, contractSignReportBfdF);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractSignReportBfdF 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(ContractSignReportBfdF contractSignReportBfdF) {
		UserProfile user = this.getUserProfile();
		ContractSignReportBfdF entity = this.contractSignReportBfdFService.saveOrUpdate(user, contractSignReportBfdF);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 删除操作
	 * 
	 * @param recordIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String recordIds) {
		UserProfile user = this.getUserProfile();
		this.contractSignReportBfdFService.delete(user, recordIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param recordId
	 * @param recordName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String recordId,String recordName) {
		return this.contractSignReportBfdFService.nameIsValid(recordId, recordName);
	}

	/**
	 * 删除操作
	 *
	 * @param bfdIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDelFiles")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDelFiles(String filesId) {
		UserProfile user = this.getUserProfile();
		this.contractSignReportBfdFService.batchDelFiles(user, filesId);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 保存新数据.
	 *
	 * @param contractSignReportBfdF 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("save")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > save(ContractSignReportBfdF contractSignReportBfdF) {
		UserProfile user = this.getUserProfile();
		ContractSignReportBfdF entity = this.contractSignReportBfdFService.saveFile(user, contractSignReportBfdF);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

}
