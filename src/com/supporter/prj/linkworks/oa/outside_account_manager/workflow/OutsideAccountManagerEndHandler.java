package com.supporter.prj.linkworks.oa.outside_account_manager.workflow;

import java.util.List;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.outside_account_manager.dao.OutsideAccountManagerRecDao;
import com.supporter.prj.linkworks.oa.outside_account_manager.entity.OutsideAccountManager;
import com.supporter.prj.linkworks.oa.outside_account_manager.entity.OutsideAccountManagerRec;
import com.supporter.prj.linkworks.oa.outside_account_manager.service.OutsideAccountManagerService;
import com.supporter.prj.linkworks.oa.outside_person.entity.OutsidePerson;
import com.supporter.prj.linkworks.oa.outside_person.service.OutsidePersonService;

public class OutsideAccountManagerEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;
	
	@Override
	public Object execute(ExecContext execContext) {
		// TODO Auto-generated method stub
		OutsideAccountManagerService service = (OutsideAccountManagerService)SpringContextHolder.getBean(OutsideAccountManagerService.class);
		OutsidePersonService personService = (OutsidePersonService)SpringContextHolder.getBean(OutsidePersonService.class);
		OutsideAccountManagerRecDao recDao = (OutsideAccountManagerRecDao)SpringContextHolder.getBean(OutsideAccountManagerRecDao.class);
	    String managerId = (String)execContext.getProcVar("managerId");
	    OutsideAccountManager entity = service.get(managerId);
	    entity.setStatus(OutsideAccountManager.COMPLETED);
	    service.update(entity);
	    //将明细中的人员状态回写到外聘人员管理
	    List<OutsideAccountManagerRec> recList = recDao.getOutsideAccountManagerRecsByDeptId(entity.getManagerId(), "all");
	    if (recList != null && recList.size() > 0){
	    	for (OutsideAccountManagerRec rec : recList){
	    		String outsidePerosnId = rec.getOutsidePersonId();
	    		OutsidePerson person = personService.get(outsidePerosnId);
	    		person.setStatus(rec.getThisTimeStatus());
	    		personService.update(person);
	    	}
	    }
		return null;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}

}
