package com.supporter.prj.linkworks.oa.seal_manage.destruction.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.seal_manage.destruction.entity.SealDestruction;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 * @author Arnold
 *
 */
@Service
public class SealDestructionViewerProvider implements IBusiEntityViewerProvider {
	
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		 return SealDestruction.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "oa/seal_manage/destruction/sealDestruction_view.html?applyId=" + entityId;
	}
	   
}
