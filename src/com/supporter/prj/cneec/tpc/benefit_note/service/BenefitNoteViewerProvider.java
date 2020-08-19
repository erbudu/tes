package com.supporter.prj.cneec.tpc.benefit_note.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.benefit_note.entity.BenefitNote;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class BenefitNoteViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return BenefitNote.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/benefit_note/benefit_note_view.html?noteId=" + entityId;
	}

}
