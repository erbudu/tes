package com.supporter.prj.cneec.tpc.out_invoice.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.cneec.tpc.invoice.entity.Invoice;
import com.supporter.prj.cneec.tpc.out_invoice.entity.OutInvoice;
import com.supporter.prj.cneec.tpc.out_invoice.service.OutInvoiceService;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**   
 * @Title: Controller
 * @Description: 报告.
 * @author liyinfeng
 * @date 2016-12-06 15:25:34
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("tpc/out_invoice")
public class OutInvoiceController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Autowired
	private OutInvoiceService reportService;

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, OutInvoice report) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.reportService.getGrid(user, jqGrid, report);
		return jqGrid;
	}
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param reportId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	@ResponseBody
	public OutInvoice initEditOrViewPage(String invoiceOutId, String contractId, String projectId) {
		OutInvoice entity = reportService.initEditOrViewPage(invoiceOutId, contractId, projectId,this.getUserProfile());
		return entity;
	}
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param reportId 主键
	 * @return
	 */
	@RequestMapping("viewPage")
	@ResponseBody
	public OutInvoice viewPage(String invoiceOutId) {
		OutInvoice entity = reportService.viewPage(invoiceOutId,this.getUserProfile());
		return entity;
	}
	
	
	/**
	 * 根据业务对象获取流程实例ID.
	 * @param report
	 * @return
	 */
	@RequestMapping("/getProcId")
	@ResponseBody
	public String getProcId(OutInvoice report){
		return reportService.getProcId(report);
	}
	
	/**
	 * 作废或生效数据.
	 * @param report
	 * @return
	 */
	@RequestMapping("changeStatus")
	public @ResponseBody OperResult changeStatus(String invoiceOutId){
		reportService.changeStatus(invoiceOutId);
		return OperResult.succeed("changeSuccess");
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param report 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
//	@AuthCheck(module = ReportConstant.MODULE_ID, 
//     oper = AuthConstant.AUTH_OPER_NAME_SETVALREPORT, 
//     failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody OperResult<OutInvoice> saveOrUpdate(OutInvoice report) {
		UserProfile user = this.getUserProfile();
		//权限验证
//		canExecute(user, AuthConstant.AUTH_OPER_NAME_SETVALREPORT, report.getReportId(), report);
		Map< String, Object > valueMap = this.getPropValues(OutInvoice.class);
		return this.reportService.saveOrUpdate(user, report, valueMap);
	}
	
	/**
	 * 保存发票号.
	 * @param invoiceOutId
	 * @param invoiceNo
	 * @return
	 */
	@RequestMapping("saveInvoiceNo")
	public @ResponseBody OperResult<OutInvoice> saveInvoiceNo(String invoiceOutId, String invoiceNo) {
		UserProfile user = this.getUserProfile();
		OutInvoice report = this.reportService.get(invoiceOutId);
		report.setInvoiceNo(invoiceNo);
		return this.reportService.saveOrUpdate(user, report, null);
	}

	/**
	 * 删除操作
	 * 
	 * @param reportIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String invoiceOutIds) {
		UserProfile user = this.getUserProfile();
		
		this.reportService.delete(user, invoiceOutIds);
		return OperResult.succeed("deleteSuccess");
	}
//	private void canExecute(UserProfile user, String operInnerName, String entityId, Object entityObj) {
//	    IModule module = EIPService.getModuleService().getModule(ReportConstant.MODULE_ID);
//	    IOper oper = EIPService.getModuleService().getOper(operInnerName, module);
//	    boolean canAccess = EIPService.getAuthorityService().canAccess(user, oper, entityId, entityObj);
//	    if (!canAccess) {
//	      throw new UnauthorizedAccessException("AUTH-002");
//	    }
//	  }
	
	/**
	 * 获取付款状态字典数据
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getSettlementStatusCodeTable")
	public Map<Integer, String> getSettlementStatusCodeTable() throws IOException {
//		Map<Integer, String> map = OutInvoice.getSettlementStatusCodeTable();
		return null;
	}
	/**
	 * 获取发票类型字典数据
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getInvoiceTypeCodeTable")
	public Map<Integer, String> getInvoiceTypeCodeTable() throws IOException {
		return Invoice.InvoiceType.getCodeTable();
	}
	/**
	 * 获取采购合同字典数据
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getContractorCodeTable")
	public Map<String, String> getContractorCodeTable(String prjId) throws IOException {
		UserProfile user = this.getUserProfile();
		return reportService.getSubPrjContracts(prjId, user);
	}
	
	/**
	 * 获取销售合同下客户名称和第三方收款单位名称
	 * @param contractId
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getOwnerNameMap")
	public Map<String, String> getOwnerNameMap(String contractId) throws IOException {
		return reportService.getOwnerNameMap(contractId);
	}
}
