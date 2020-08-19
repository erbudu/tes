package com.supporter.prj.ppm.poa.power_attorney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.poa.power_attorney.entity.PowerAttorney;

@Service
public class PowerAttorneyPropRetriever extends AbstractPropRetriever {

	@Autowired
    private PowerAttorneyService service;
	
    public PowerAttorneyPropRetriever() {
    }

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return PowerAttorney.class.getName();
	}
	
	@Override
    protected Object getEntity(int budgetYear, Object entityId){
        if(entityId == null)
            return null;
        else
            return service.get(entityId.toString());
    }

	
	

}
