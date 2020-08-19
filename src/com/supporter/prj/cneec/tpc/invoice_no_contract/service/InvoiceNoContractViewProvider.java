/**
 * 
 */
package com.supporter.prj.cneec.tpc.invoice_no_contract.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.invoice_no_contract.entity.InvoiceNoContractEntity;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 *<p>Title: OveBimRepViewProvider</p>
 *<p>Description: 显示已办</p>
 *<p>Company: </p>
 * @author CHENHAO
 * @date 2019年12月31日
 * 
 */
@Service
public class InvoiceNoContractViewProvider implements IBusiEntityViewerProvider{

	@Override
	public String getId() {
		return InvoiceNoContractEntity.class.getName();
	}

	@Override
	public String getViewerUrl(int dbYer, Object entityId) {
		return "/tpc/invoice_no_contract/invoice_noContract_view.html?invoiceId="+entityId;
	}
	
	

}
