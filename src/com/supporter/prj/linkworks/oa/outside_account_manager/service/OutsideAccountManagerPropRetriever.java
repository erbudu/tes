package com.supporter.prj.linkworks.oa.outside_account_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.linkworks.oa.outside_account_manager.entity.OutsideAccountManager;

@Service
public class OutsideAccountManagerPropRetriever extends AbstractPropRetriever {

	@Autowired
	private OutsideAccountManagerService service;
	
	protected Object getEntity(int budgetYear, Object entityId) {
		if (entityId == null){
			return null;
		}
		return service.get(entityId.toString());
	}

	public String getId() {
		return OutsideAccountManager.class.getName();
	}
	
	public OutsideAccountManagerPropRetriever(){
		
	}

}
