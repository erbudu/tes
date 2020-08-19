package com.supporter.prj.linkworks.oa.authority_apply.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApply;
@Service
public class AuthorityApplyViewerProvider implements IBusiEntityViewerProvider{
	
	
	@Autowired
    private AuthorityApplyService service;
	/**
	 * 返回本实例所服务的业务实体类.
	 */	
	public String getId() {
		 return AuthorityApply.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		AuthorityApply authorityApply=service.get(entityId.toString());
		String type="";
		if(authorityApply!=null){
			type=authorityApply.getType();
		}
		return "/oa/authority_apply/authorityApply_overall_view.html?applyId=" + entityId+"&type="+type;
	}

}
