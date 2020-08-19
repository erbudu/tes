package com.supporter.prj.linkworks.oa.authority_apply.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApply;
@Service
public class AuthorityApplyPropRetriever extends AbstractPropRetriever {

    public AuthorityApplyPropRetriever()
    {
    }

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return AuthorityApply.class.getName();
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
    private AuthorityApplyService service;
	
	

}
