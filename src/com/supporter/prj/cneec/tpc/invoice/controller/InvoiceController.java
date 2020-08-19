package com.supporter.prj.cneec.tpc.invoice.controller;

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
import com.supporter.prj.cneec.tpc.invoice.service.InvoiceService;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**   
 * @Title: Controller
 * @Description: 收到发票.
 * @author liyinfeng
 * @date 2016-12-06 15:25:34
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("tpc/invoice")
public class InvoiceController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Autowired
	private InvoiceService invoiceService;

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, Invoice invoice) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.invoiceService.getGrid(user, jqGrid, invoice);
		return jqGrid;
	}
	
	/**
	 * 分页表格展示信用证付款明细数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getRecGrid")
	public @ResponseBody JqGrid getRecGrid(HttpServletRequest request, JqGridReq jqGridReq, String invoiceId) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		UserProfile user = this.getUserProfile();
		this.invoiceService.getRecGrid(user,jqGrid, invoiceId);
		return jqGrid;
	}
	

	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param invoiceId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	@ResponseBody
	public Invoice initEditOrViewPage(String invoiceId, String contractId, String projectId) {
		Invoice entity = invoiceService.initEditOrViewPage(invoiceId, contractId,projectId,this.getUserProfile());
		return entity;
	}
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param invoiceId 主键
	 * @return
	 */
	@RequestMapping("viewPage")
	@ResponseBody
	public Invoice viewPage(String invoiceId) {
		Invoice entity = invoiceService.viewPage(invoiceId,this.getUserProfile());
		return entity;
	}
	
	
	/**
	 * 根据业务对象获取流程实例ID.
	 * @param invoice
	 * @return
	 */
	@RequestMapping("/getProcId")
	@ResponseBody
	public String getProcId(Invoice invoice){
		return invoiceService.getProcId(invoice);
	}
	
	/**
	 * 作废或生效数据.
	 * @param invoice
	 * @return
	 */
	@RequestMapping("changeStatus")
	public @ResponseBody OperResult changeStatus(String invoiceId){
		invoiceService.changeStatus(invoiceId);
		return OperResult.succeed("changeSuccess");
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param invoice 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
//	@AuthCheck(module = ReportConstant.MODULE_ID, 
//     oper = AuthConstant.AUTH_OPER_NAME_SETVALREPORT, 
//     failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody OperResult<Invoice> saveOrUpdate(Invoice invoice) {
		UserProfile user = this.getUserProfile();
		//权限验证
//		canExecute(user, AuthConstant.AUTH_OPER_NAME_SETVALREPORT, invoice.getReportId(), invoice);
		Map< String, Object > valueMap = this.getPropValues(Invoice.class);
		return this.invoiceService.saveOrUpdate(user, invoice, valueMap);
	}

	/**
	 * 财务费用补录
	 * @param invoice
	 * @return
	 */
	@RequestMapping("saveOrUpdateFinanceDepartmentAdd")
	public @ResponseBody
	OperResult<Invoice> saveOrUpdateFinanceDepartmentAdd(Invoice invoice) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(Invoice.class);
		return this.invoiceService.saveOrUpdateFinanceDepartmentAdd(user, invoice, valueMap);
	}

	/**
	 * 删除操作
	 * 
	 * @param invoiceIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String invoiceIds) {
		UserProfile user = this.getUserProfile();
		
		this.invoiceService.delete(user, invoiceIds);
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
		Map<Integer, String> map = Invoice.Status.getCodeTable();
		return map;
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
	@RequestMapping(value = "/getContractCodeTable")
	public Map<String, String> getContractCodeTable(String prjId, String contractId) throws IOException {
		return invoiceService.getFBContractCodeTable(prjId, contractId);
	}
	/**
	 * 获取项目选择的所有供方
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getContractorCodeTable")
	public Map<String, String> getContractorCodeTable(String prjId) throws IOException {
		UserProfile user = this.getUserProfile();
		return invoiceService.getSubPrjContracts(prjId, user);
	}

	/**
	 * 选择合同后获取相关金额
	 * @param contractId
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getInvoiceAmount")
	public Map<String, Double> getInvoiceAmount(String contractId) throws IOException {
		return invoiceService.getInvoiceAmount(contractId);
	}

}
