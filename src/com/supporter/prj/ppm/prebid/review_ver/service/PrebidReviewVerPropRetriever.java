package com.supporter.prj.ppm.prebid.review_ver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.prebid.review_ver.entity.PpmPrebidReviewVer;

@Service
public class PrebidReviewVerPropRetriever extends AbstractPropRetriever {
	
	/**
	 * 注入需要使用的Service.
	 */
	@Autowired
	private PpmPrebidReviewVerService service;

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	@Override
	public String getId() {
		return PpmPrebidReviewVer.class.getName();
	}

	@Override
	protected Object getEntity(int budgetYear, Object entityId) {
		if(entityId == null){
			return null;
		}
		return service.get(entityId.toString());
	}
	   
}
