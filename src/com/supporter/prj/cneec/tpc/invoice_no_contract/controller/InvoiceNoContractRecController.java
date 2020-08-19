package com.supporter.prj.cneec.tpc.invoice_no_contract.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.invoice_no_contract.constant.InvoiceNoContractConstant;
import com.supporter.prj.cneec.tpc.invoice_no_contract.entity.InvoiceNoContractRecEntity;
import com.supporter.prj.cneec.tpc.invoice_no_contract.service.InvoiceNoContractRecService;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**
 * 非合同付款收发票明细表Controller
 * InvoiceNoContractRecController
 * @author CHENHAO
 *
 */

@Controller
@RequestMapping("tpc/invoice_no_contract/rec/")
public class InvoiceNoContractRecController extends AbstractController{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private InvoiceNoContractRecService service;
	
	/**
	 * 获取币别下拉列表内容
	 * @return
	 */
	@RequestMapping("getCurrencyType")
	@ResponseBody
	public Map<String, String> getCurrencyType() {
		
		return InvoiceNoContractConstant.getCurrencyType();
	}
	
	@RequestMapping("saveOrUpdate")
	@ResponseBody
	public OperResult<InvoiceNoContractRecEntity> saveOrUpdate(InvoiceNoContractRecEntity entity){
		UserProfile user = this.getUserProfile();
		return service.saveOrUpdate(user, entity);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public OperResult<InvoiceNoContractRecEntity> delete(String recordId){
		return service.delete(recordId);
	}
	
	/**
	 * 选中单号后初始化页面
	 * @param paymentNo
	 * @param noType	
	 * @return
	 */
	@RequestMapping("initPage")
	@ResponseBody
	public InvoiceNoContractRecEntity initPage(InvoiceNoContractRecEntity entity) {
			
		return service.initPage(entity);
	}
	
	/**
	 * 返回固定格式的单号
	 * @return
	 */
	@RequestMapping("getPayMentNo")
	@ResponseBody
	public List<Map<String, Object>> getPayMentNo(String invoiceId, String recordId, String prjId){
		return service.getPaymentNo(invoiceId,recordId, prjId);
	}
	
	/**
	 * 查询非合同付款收发票明细表列表
	 * @param invoiceId 
	 * @return
	 */
	
	@RequestMapping("getGrid")
	@ResponseBody
	public List<InvoiceNoContractRecEntity> getGrid(String invoiceId) {
		return service.getGrid( invoiceId);
	}
}
