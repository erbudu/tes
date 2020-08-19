package com.supporter.prj.cneec.tpc.out_invoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.out_invoice.entity.OutInvoice;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;

/**
 * Report的IPropRetriever实现类.
 * @author liyinfeng
 *
 */
@Service
public class OutInvoicePropRetriever extends AbstractPropRetriever {
	
	/**
	 * 注入需要使用的Service.
	 */
	@Autowired
	private OutInvoiceService service;
	@Autowired
	private RegisterProjectService prjService;

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	@Override
	public String getId() {
		return OutInvoice.class.getName();
	}

	@Override
	protected Object getEntity(int budgetYear, Object entityId) {
		if(entityId == null){
			return null;
		}
		
		OutInvoice entity = this.service.get(entityId.toString());
		RegisterProject prj = prjService.get(entity.getPrjId());
		entity.setPrjDeptId(prj.getProjectDeptId());
		return entity;
	}
	   
}
