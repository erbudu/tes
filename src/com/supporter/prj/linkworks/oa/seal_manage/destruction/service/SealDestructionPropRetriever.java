package com.supporter.prj.linkworks.oa.seal_manage.destruction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.linkworks.oa.seal_manage.destruction.entity.SealDestruction;

/**
 * Report的IPropRetriever实现类.
 *
 */
@Service
public class SealDestructionPropRetriever extends AbstractPropRetriever {
	
	/**
	 * 注入需要使用的Service.
	 */
	@Autowired
	private SealDestructionService service;

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	@Override
	public String getId() {
		return SealDestruction.class.getName();
	}

	@Override
	protected Object getEntity(int budgetYear, Object entityId) {
		if(entityId == null){
			return null;
		}
//		System.out.println("service====="+entityId);
//		Report report = service.get(entityId.toString());
//		Person taskPerformer = EIPService.getEmpService().getEmp(report.getExamIds());
//		System.out.println("service====="+taskPerformer);
//		System.out.println("service====="+report.getCreatedBy()+"=="+report.getCreatorName());
		return service.get(entityId.toString());
	}
	   
}
