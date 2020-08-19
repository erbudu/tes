/**
 * 
 */
package com.supporter.prj.linkworks.oa.attendance.leave.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.linkworks.oa.attendance.leave.entiyt.LeaveEntity;

/**
 * 流程用类
 * @author YUEYUN
 * @date 2019年7月17日
 * 
 */
@Service
public class LeavePropRetriever extends AbstractPropRetriever{
	
	@Autowired
	LeaveService leaveService;
	
	public Object getEntity(int budgetYear, Object entityId) {
		 if (entityId == null) {
		      return null;
		    }
		return leaveService.get(entityId.toString());
	}


	@Override
	public String getId() {
		return LeaveEntity.class.getName();
	}

}
