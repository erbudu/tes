/**
 * 
 */
package com.supporter.prj.ppm.prj_org.base_info.service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;

/**
 *<p>Title: PrjPropRetriever</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年12月9日
 * 
 */
public class PrjPropRetriever extends AbstractPropRetriever{
	
	private BaseInfoService service;

	/* (non-Javadoc)
	 * @see com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever#getId()
	 */
	@Override
	public String getId() {
		return Prj.class.getName();
	}

	/* (non-Javadoc)
	 * @see com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever#getEntity(int, java.lang.Object)
	 */
	@Override
	protected Object getEntity(int paramInt, Object entityId) {
		if(entityId == null) {
			return null;
		}
		return service.get(entityId.toString());
	}

}
