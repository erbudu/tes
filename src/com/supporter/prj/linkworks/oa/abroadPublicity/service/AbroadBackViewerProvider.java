package com.supporter.prj.linkworks.oa.abroadPublicity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.abroad.entity.AbroadRealDate;
import com.supporter.prj.linkworks.oa.abroadPublicity.entity.AbroadBack;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 * @author Arnold
 *
 */
@Service
public class AbroadBackViewerProvider implements IBusiEntityViewerProvider {
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return AbroadBack.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
			return "/oa/abroadPublicity/abroad_back_audit_view_swf.html?isView=true&backId=" + entityId;

	}
	   
}
