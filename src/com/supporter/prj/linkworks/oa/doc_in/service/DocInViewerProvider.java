package com.supporter.prj.linkworks.oa.doc_in.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.doc_in.entity.DocIn;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 * @author Arnold
 *
 */
@Service
public class DocInViewerProvider implements IBusiEntityViewerProvider {
	
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		 return DocIn.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/oa/doc_in/doc_in_view.html?docId=" + entityId;
	}
	   
}
