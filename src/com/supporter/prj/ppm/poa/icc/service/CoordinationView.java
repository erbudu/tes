package com.supporter.prj.ppm.poa.icc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.ppm.poa.icc.entity.Coordination;
@Service
public class CoordinationView implements IBusiEntityViewerProvider{
	@Autowired
	CoordinationService supplierService;
	
	public String getId() {
		return Coordination.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object iccId) {
		Coordination coordination = supplierService.get(iccId.toString());
		
		  if(coordination != null){ return
		  "ppm/poa/icc/icc_coordination_view.html?iccId=" + iccId;
		  }else{ return "/eip/swf/todo/todo_noPage_view.html"; }
		 
		
	}
}
