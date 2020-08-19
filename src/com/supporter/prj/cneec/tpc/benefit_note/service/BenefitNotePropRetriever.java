package com.supporter.prj.cneec.tpc.benefit_note.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.benefit_note.entity.BenefitNote;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;

/**
 * @Title: BenefitNotePropRetriever
 * @Description: 流程定义变量必备类
 * @author: yanweichao
 * @date: 2018-07-17
 * @version: V1.0
 */
@Service
public class BenefitNotePropRetriever extends AbstractPropRetriever {

	@Autowired
	private BenefitNoteService service;
	@Autowired
	private RegisterProjectService prjService;

	public String getId() {
		return BenefitNote.class.getName();
	}

	protected Object getEntity(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		
		BenefitNote entity = this.service.get(entityId.toString());
		RegisterProject prj = prjService.get(entity.getProjectId());
		entity.setPrjDeptId(prj.getProjectDeptId());
		
		return entity;
	}

}