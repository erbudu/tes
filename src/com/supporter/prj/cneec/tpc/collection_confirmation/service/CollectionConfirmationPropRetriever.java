package com.supporter.prj.cneec.tpc.collection_confirmation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.collection_confirmation.entity.CollectionConfirmation;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;

/**
 * @Title: CollectionConfirmationPropRetriever
 * @Description: 流程定义变量必备类
 * @author: yanweichao
 * @date: 2017-12-15
 * @version: V1.0
 */
@Service
public class CollectionConfirmationPropRetriever extends AbstractPropRetriever {

	@Autowired
	private CollectionConfirmationService service;
	@Autowired
	private RegisterProjectService prjService;

	public String getId() {
		return CollectionConfirmation.class.getName();
	}

	protected Object getEntity(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		
		CollectionConfirmation entity = this.service.get(entityId.toString());
		RegisterProject prj = prjService.get(entity.getProjectId());
		entity.setPrjDeptId(prj.getProjectDeptId());
		
		return entity;
	}

}