/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.preparation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.bid_startup.preparation.entity.StartEntity;

/**
 *<p>Title: StartPropRetriever</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月4日
 * 
 */
@Service
public class StartPropRetriever extends AbstractPropRetriever{

	@Autowired
	private StartService service;
	
	@Override
	protected Object getEntity(int budgetYear, Object entityId) {
		if(entityId == null) {
			return null;
		}
		return service.get(entityId.toString());
	}

	
	@Override
	public String getId() {
		
		return StartEntity.class.getName();
	}

}
