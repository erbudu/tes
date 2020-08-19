package com.supporter.prj.cneec.tpc.order_change.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.order_change.entity.ContractChOrder;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;

/**
 * @Title: ContractChangePropRetriever
 * @Description: 流程定义变量必备类
 */
@Service
public class ContractChOrderPropRetriever extends AbstractPropRetriever {

	@Autowired
	private OrderChangeService service;
	@Autowired
	private RegisterProjectService prjService;

	public String getId() {
		return ContractChOrder.class.getName();
	}

	protected Object getEntity(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		
		ContractChOrder entity = this.service.initContract(entityId.toString());
		RegisterProject prj = prjService.get(entity.getProjectId());
		entity.setPrjDeptId(prj.getProjectDeptId());
		return entity;
	}

}