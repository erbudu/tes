package com.supporter.prj.cneec.tpc.contract_change.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChange;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;

/**
 * @Title: ContractChangePropRetriever
 * @Description: 流程定义变量必备类
 */
@Service
public class ContractChangePropRetriever extends AbstractPropRetriever {

	@Autowired
	private ContractChangeService service;

	public String getId() {
		return ContractChange.class.getName();
	}

	protected Object getEntity(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		
		ContractChange entity = this.service.get(entityId.toString());
		
		return entity; 
	}

}