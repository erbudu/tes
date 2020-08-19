package com.supporter.prj.cneec.tpc.collection_confirmation.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.collection_confirmation.entity.CollectionConfirmation;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class CollectionConfirmationViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return CollectionConfirmation.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/collection_confirmation/collection_confirmation_view.html?collectionId=" + entityId;
	}

}
