package com.supporter.prj.cneec.tpc.credit_letter_settlement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.credit_letter_apply.entity.CreditLetterApply;
import com.supporter.prj.cneec.tpc.credit_letter_apply.service.CreditLetterApplyService;
import com.supporter.prj.cneec.tpc.credit_letter_settlement.entity.CreditLetterSettlement;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;

/**
 * Report的IPropRetriever实现类.
 * @author liyinfeng
 *
 */
@Service
public class CreditLetterSettlementPropRetriever extends AbstractPropRetriever {
	
	/**
	 * 注入需要使用的Service.
	 */
	@Autowired
	private CreditLetterSettlementService service;
	@Autowired
	private RegisterProjectService prjService;
	@Autowired
	private CreditLetterApplyService creditLetterApplyService;

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	@Override
	public String getId() {
		return CreditLetterSettlement.class.getName();
	}

	@Override
	protected Object getEntity(int budgetYear, Object entityId) {
		if(entityId == null){
			return null;
		}
		
		CreditLetterSettlement entity = this.service.get(entityId.toString());
		CreditLetterApply apply = this.creditLetterApplyService.get(entity.getCreditLetterId());
		RegisterProject prj = this.prjService.get(apply.getProjectId());
		entity.setPrjId(prj.getProjectId());
		entity.setPrjDeptId(prj.getProjectDeptId());
		return entity;
	}
	   
}
