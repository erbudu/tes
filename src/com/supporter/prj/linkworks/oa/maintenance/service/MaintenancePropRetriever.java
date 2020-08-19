package com.supporter.prj.linkworks.oa.maintenance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.linkworks.oa.maintenance.entity.Maintenance;
@Service
public class MaintenancePropRetriever extends AbstractPropRetriever {

    public MaintenancePropRetriever()
    {
    }

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return Maintenance.class.getName();
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
    private MaintenanceService service;
	
	

}
