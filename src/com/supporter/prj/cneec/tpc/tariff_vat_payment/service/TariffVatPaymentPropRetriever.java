package com.supporter.prj.cneec.tpc.tariff_vat_payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.tariff_vat_payment.entity.TariffVatPayment;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;

/**
 * @Title: TariffVatPaymentPropRetriever
 * @Description: 流程定义变量必备类
 * @author: yanweichao
 * @date: 2017-12-20
 * @version: V1.0
 */
@Service
public class TariffVatPaymentPropRetriever extends AbstractPropRetriever {

	@Autowired
	private TariffVatPaymentService service;
	@Autowired
	private RegisterProjectService prjService;

	public String getId() {
		return TariffVatPayment.class.getName();
	}

	protected Object getEntity(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		
		TariffVatPayment entity = this.service.get(entityId.toString());
		RegisterProject prj = prjService.get(entity.getProjectId());
		entity.setPrjDeptId(prj.getProjectDeptId());
		return entity;
	}

}