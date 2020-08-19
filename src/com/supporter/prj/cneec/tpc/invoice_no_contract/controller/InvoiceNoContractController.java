package com.supporter.prj.cneec.tpc.invoice_no_contract.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.invoice_no_contract.constant.InvoiceNoContractConstant;
import com.supporter.prj.cneec.tpc.invoice_no_contract.entity.InvoiceNoContractEntity;
import com.supporter.prj.cneec.tpc.invoice_no_contract.service.InvoiceNoContractService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**
 * 非合同付款收发票Controller
 * @Title:InvoiceNoContractController
 * @author CHENHAO
 *
 */

@Controller
@RequestMapping("tpc/invoice_no_contract")
public class InvoiceNoContractController extends AbstractController{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private InvoiceNoContractService service;
	
	/**
	 * 财务补入
	 * @return
	 */
	@RequestMapping("financeDepartmentAdd")
	@ResponseBody
	public OperResult<InvoiceNoContractEntity> financeDepartmentAdd(InvoiceNoContractEntity entity){
		UserProfile user = this.getUserProfile();
		return service.financeDepartmentAdd(entity, user);
	}
	
	/**
	 * 获取流程状态
	 * @return
	 */
	@RequestMapping("getProcStatus")
	@ResponseBody
	public Map<Integer, String> getProcStatus(){
		return InvoiceNoContractConstant.getProcStatus();
	}
	
	/**
	 * 删除草稿状态的数据
	 * @param invoiceId
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public OperResult<InvoiceNoContractEntity> delete(String invoiceIds){
		return service.delete(invoiceIds);
	}
	/**
	 * 获取分页的列表
	 * @param request
	 * @param jqGridReq
	 * @param keyWord
	 * @param prjId
	 * @return
	 */
	@RequestMapping("getGrid")
	@ResponseBody
	public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, InvoiceNoContractEntity entity) {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		service.getGrid(jqGrid, user, entity);
		return jqGrid;
	}
	
	/**
	 * 填充新建和编辑页面信息
	 * @param prjId		项目ID
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	@ResponseBody
	public InvoiceNoContractEntity initEditOrViewPage(String invoiceId, String prjId) {
		
		UserProfile user = this.getUserProfile();
		return service.initEditOrViewPage(invoiceId, prjId, user);
	}
	
	/**
	 * 保存或修改发票信息
	 * @param entity
	 * @return
	 */
	
	@RequestMapping("saveOrUpdate")
	@ResponseBody
	public OperResult<InvoiceNoContractEntity> saveOrUpdate(InvoiceNoContractEntity entity){
		
		UserProfile user = this.getUserProfile();
		return service.saveOrUpdate(entity, user);
	}
	

	/**
	 * 获取发票类型码表的ID和显示名称，以Map<String,String>返回
	 * @return
	 */
	
	@RequestMapping("getInvoiceType")
	@ResponseBody
	public Map<String, String> getInvoiceType(){
		
		return InvoiceNoContractConstant.getInvoiceType();
	}
}
