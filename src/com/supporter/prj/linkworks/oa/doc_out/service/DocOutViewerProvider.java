package com.supporter.prj.linkworks.oa.doc_out.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.doc_out.entity.OaDocOut;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 * @author Arnold
 *
 */
@Service
public class DocOutViewerProvider implements IBusiEntityViewerProvider {
	
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		 return OaDocOut.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/oa/doc_out/doc_out_view.html?docId=" + entityId;
	}
	   
}
