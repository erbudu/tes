package com.supporter.prj.linkworks.oa.abroad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.abroad.entity.Abroad;
import com.supporter.prj.linkworks.oa.abroad.entity.AbroadRealDate;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 * @author Arnold
 *
 */
@Service
public class AbroadRealDateViewerProvider implements IBusiEntityViewerProvider {
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return AbroadRealDate.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		AbroadRealDateService service = (AbroadRealDateService)SpringContextHolder.getBean(AbroadRealDateService.class);
		if(entityId!=null){
			AbroadRealDate entity = service.get(entityId.toString());
			return "/oa/abroad/abroad_real_date_view.html?recordId=" + entity.getAbroadId();
		}
		return null;
	}
	   
}
