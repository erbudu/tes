/**
 * 
 */
package com.supporter.prj.linkworks.oa.overseas_bimonthly_report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.linkworks.oa.overseas_bimonthly_report.entity.OverseasBimonthlyReportEntity;

/**
 *<p>Title: OveBimRepPropRetriever</p>
 *<p>Description: 用来获取流程变量</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年12月26日
 * 
 */
@Service
public class OveBimRepPropRetriever extends AbstractPropRetriever{

	@Autowired
	private OverseasBimonthlyReportService service;
	
	@Override
	protected Object getEntity(int arg0, Object arg1) {
		
		if(arg1 != null) {
			return service.get(arg1.toString());
		}
		return null;
		
	}

	@Override
	public String getId() {
		return OverseasBimonthlyReportEntity.class.getName();
	}

}
