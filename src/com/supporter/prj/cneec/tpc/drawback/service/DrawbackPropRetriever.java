package com.supporter.prj.cneec.tpc.drawback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.drawback.entity.Drawback;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;

/**
 * @Title: DrawbackPropRetriever
 * @Description: 流程定义变量必备类
 * @author: yanweichao
 * @date: 2017-11-21
 * @version: V1.0
 */
@Service
public class DrawbackPropRetriever extends AbstractPropRetriever {

	@Autowired
	private DrawbackService service;
	@Autowired
	private RegisterProjectService prjService;

	public String getId() {
		return Drawback.class.getName();
	}

	protected Object getEntity(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		
		Drawback entity = this.service.get(entityId.toString());
		RegisterProject prj = prjService.get(entity.getProjectId());
		entity.setPrjDeptId(prj.getProjectDeptId());
		return entity;
	}

}