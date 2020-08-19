package com.supporter.prj.cneec.tpc.contract_change.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractSeal;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;

/**
 * @Title: ContractChangePropRetriever
 * @Description: 流程定义变量必备类
 */
@Service
public class ContractSealPropRetriever extends AbstractPropRetriever {

	@Autowired
	private ContractChangeService service;
	@Autowired
	private RegisterProjectService prjService;

	public String getId() {
		return ContractSeal.class.getName();
	}

	protected Object getEntity(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		
		ContractSeal entity = this.service.getContractSealBySealId(entityId.toString());
		RegisterProject prj = prjService.get(entity.getProjectId());
		entity.setPrjDeptId(prj.getProjectDeptId());
		return entity;
	}

}