/**
 * 
 */
package com.supporter.prj.cneec.tpc.invoice_no_contract.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.invoice_no_contract.entity.InvoiceNoContractEntity;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;

/**
 *<p>Title: InvoiceNoPropRetriever</p>
 *<p>Description: 获取流程变量</p>
 *<p>Company: </p>
 * @author CHENHAO
 */
@Service
public class InvoiceNoPropRetriever extends AbstractPropRetriever{

	@Autowired
	private InvoiceNoContractService service;
	
	@Override
	protected Object getEntity(int budgetYear, Object entityId) {
		if(entityId == null) {
			return null;
		}
		return service.get(entityId.toString());
	}

	
	@Override
	public String getId() {
		
		return InvoiceNoContractEntity.class.getName();
	}
}
