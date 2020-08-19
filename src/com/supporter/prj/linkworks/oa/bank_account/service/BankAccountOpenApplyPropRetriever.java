package com.supporter.prj.linkworks.oa.bank_account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApply;
@Service
public class BankAccountOpenApplyPropRetriever extends AbstractPropRetriever {

    public BankAccountOpenApplyPropRetriever()
    {
    }

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return BankAccountOpenApply.class.getName();
	}
	@Override
    protected Object getEntity(int budgetYear, Object entityId)
    {
        if(entityId == null)
            return null;
        else
            return service.get(entityId.toString());
    }
	@Autowired
    private BankAccountOpenApplyService service;
	
	

}
