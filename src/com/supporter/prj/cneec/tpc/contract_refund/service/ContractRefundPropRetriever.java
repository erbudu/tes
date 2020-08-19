package com.supporter.prj.cneec.tpc.contract_refund.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.contract_refund.entity.ContractRefund;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;

/**
 * @Title: ContractRefundPropRetriever
 * @Description: 流程定义变量必备类
 * @author: yanweichao
 * @date: 2017-11-25
 * @version: V1.0
 */
@Service
public class ContractRefundPropRetriever extends AbstractPropRetriever {

	@Autowired
	private ContractRefundService service;
	@Autowired
	private RegisterProjectService prjService;

	public String getId() {
		return ContractRefund.class.getName();
	}

	protected Object getEntity(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		
		ContractRefund entity = this.service.get(entityId.toString());
		RegisterProject prj = prjService.get(entity.getProjectId());
		entity.setPrjDeptId(prj.getProjectDeptId());
		return entity;
	}

}