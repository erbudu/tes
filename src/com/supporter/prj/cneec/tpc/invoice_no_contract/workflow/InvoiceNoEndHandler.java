/**
 * 
 */
package com.supporter.prj.cneec.tpc.invoice_no_contract.workflow;

import com.supporter.prj.cneec.tpc.invoice_no_contract.constant.InvoiceNoContractConstant;
import com.supporter.prj.cneec.tpc.invoice_no_contract.entity.InvoiceNoContractEntity;
import com.supporter.prj.cneec.tpc.invoice_no_contract.service.InvoiceNoContractService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 *<p>Title: InvoiceNoEndHandler</p>
 *<p>Description:流程结束服务类 </p>
 *<p>Company: </p>
 * @author CHENHAO
 * 
 */
public class InvoiceNoEndHandler extends AbstractExecHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object execute(ExecContext execContext) {
		
		InvoiceNoContractService service =  (InvoiceNoContractService)SpringContextHolder.getBean(InvoiceNoContractService.class);//获取模块的service
		
		String invoiceId = (String)execContext.getProcVar("invoiceId");//--------------------------------------------------------获取流程变量中的业务单主键
		
		InvoiceNoContractEntity entity = service.get(invoiceId);//--------------------------------------------根据业务单主键获取业务实体类信息
		
		 if (entity != null) {
			 
			 entity.setProcId(execContext.getProcId());//--------------------------------------------------------------设 流程ID，改变流程状态
			 entity.setStatus(InvoiceNoContractConstant.COMPLETE);//-----------------------------------------------------------流程开始审批中状态代码1
			 service.update(entity);//---------------------------------------------------------------------更新业务表单实体类信息
			
		 }else {
			 EIPService.getLogService().error("无效的业务单主键invoiceId："+invoiceId);
		 }
		return null;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}


	
	
}
