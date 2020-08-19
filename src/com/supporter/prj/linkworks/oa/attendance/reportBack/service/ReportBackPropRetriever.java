/**
 * 
 */
package com.supporter.prj.linkworks.oa.attendance.reportBack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.linkworks.oa.attendance.reportBack.entity.ReportBackEntity;

/**
 * @title 流程服务类 用于获取流程变量实例
 * @author YUEYUN
 * @date 2019年7月21日
 * 
 */
@Service
public class ReportBackPropRetriever extends AbstractPropRetriever{

	
	@Autowired
	ReportBackService reportBackService;
	
	public Object getEntity(int budgetYear, Object entityId) {
		 if (entityId == null) {
		      return null;
		    }
		return reportBackService.get(entityId.toString());
	}


	@Override
	public String getId() {
		return ReportBackEntity.class.getName();
	}

}
