package com.supporter.prj.pm.enginee_negotiate.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.pc.pm_prj.dao.VPmProjectDao;
import com.supporter.prj.cneec.pc.pm_prj.entity.VPmProject;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.pm.enginee_negotiate.dao.EngineeVisaSwfDao;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisa;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisaSwf;
import com.supporter.util.CommonUtil;

/**
 * 流程变量服务类
 * @author Administrator
 *
 */
@Service
public class EngineeVisaPropRetriever extends AbstractPropRetriever {
	/**
	 * 注入需要使用的Service.
	 */
	@Autowired
	private EngineeVisaService service;
	@Autowired
	private EngineeVisaSwfDao visaSwfDao;
	@Autowired
	private VPmProjectDao prjDao;

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	@Override
	public String getId() {
		return EngineeVisaSwf.class.getName();
	}

	@Override
	protected Object getEntity(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		String visaId = CommonUtil.trim(entityId.toString());
		
		EngineeVisaSwf swf = visaSwfDao.get(visaId);
		EngineeVisa engineeVisa = service.get(visaId);
		swf.setEngineeVisa(engineeVisa);
		
		if (StringUtils.isNotBlank(swf.getPrjId())) {
			VPmProject prj = prjDao.get(swf.getPrjId());
			if (prj != null) {
				swf.setPrjManagerId(prj.getPrjManagerId());
				swf.setPrjManagerName(prj.getPrjManager());
				swf.setPrjDeptId(prj.getDeptId());
			}
		}
		
		return swf;
	}
	   
}
