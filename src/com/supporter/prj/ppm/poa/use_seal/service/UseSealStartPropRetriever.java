/**
 * 
 */
package com.supporter.prj.ppm.poa.use_seal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.poa.use_seal.entity.UseSealStartEntity;

/**
 *<p>Title: UseSealPropRetriever</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年10月18日
 */
@Service
public class UseSealStartPropRetriever extends AbstractPropRetriever{

	@Autowired
	private UseSealStartService useSealStrtService;
	
	@Override
	protected Object getEntity(int budgetYear, Object entityId) {
		if(entityId == null) {
			return null;
		}
		return useSealStrtService.get(entityId.toString());
	}

	
	@Override
	public String getId() {
		
		return UseSealStartEntity.class.getName();
	}
}
