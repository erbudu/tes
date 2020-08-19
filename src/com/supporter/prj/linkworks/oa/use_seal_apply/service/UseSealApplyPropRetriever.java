package com.supporter.prj.linkworks.oa.use_seal_apply.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.linkworks.oa.use_seal_apply.entity.UseSealApply;
@Service
public class UseSealApplyPropRetriever extends AbstractPropRetriever {

    public UseSealApplyPropRetriever()
    {
    }

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return UseSealApply.class.getName();
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
    private UseSealApplyService service;
	
	

}
