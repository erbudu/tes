package com.supporter.prj.pm.design_change.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.pm.design_change.entity.DesignChange;
import com.supporter.prj.pm.public_proc.dao.PublicProcDao;
import com.supporter.prj.pm.public_proc.entity.PublicProc;
import com.supporter.util.CommonUtil;

@Service
public class DesignChangePropRetriever extends AbstractPropRetriever {
	/**
	 * 注入需要使用的Service.
	 */
	@Autowired
	private DesignChangeService service;
	@Autowired
	private PublicProcDao procDao;

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	@Override
	public String getId() {
		return DesignChange.class.getName();
	}

	@Override
	protected Object getEntity(int budgetYear, Object entityId) {
		if(entityId == null){
			return null;
		}
		String applyId = CommonUtil.trim(entityId.toString());
		DesignChange fileReceive = service.getDesignChangeById(applyId);
		//将流程表set进来
		PublicProc publicProc = procDao.getPublicProcByEntityId(applyId);
		fileReceive.setPublicProc(publicProc);
		
		return fileReceive;
	}
	   
}
