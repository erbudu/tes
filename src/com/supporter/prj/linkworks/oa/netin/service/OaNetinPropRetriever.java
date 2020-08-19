package com.supporter.prj.linkworks.oa.netin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.linkworks.oa.netin.entity.OaNetin;

@Service
public class OaNetinPropRetriever extends AbstractPropRetriever {

	@Autowired
	private OaNetinService netinService;
	
	public String getId() {
		// TODO Auto-generated method stub
		return OaNetin.class.getName();
	}
	
	protected Object getEntity(int budgetYear, Object entityId) {
		// TODO Auto-generated method stub
		if (entityId == null){
			return null;
		}
		return netinService.get(entityId.toString());
	}

	public OaNetinPropRetriever(){
		
	}
}
