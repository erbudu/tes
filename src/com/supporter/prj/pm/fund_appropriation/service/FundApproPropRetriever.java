package com.supporter.prj.pm.fund_appropriation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.pc.pm_prj.dao.VPmProjectDao;
import com.supporter.prj.cneec.pc.pm_prj.entity.VPmProject;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.pm.fund_appropriation.dao.FundAppropriationSwfDao;
import com.supporter.prj.pm.fund_appropriation.entity.FundAppropriation;
import com.supporter.prj.pm.fund_appropriation.entity.FundAppropriationSwf;
import com.supporter.util.CommonUtil;

/**
 * 流程变量服务类
 * @author Administrator
 *
 */
@Service
public class FundApproPropRetriever extends AbstractPropRetriever {
	/**
	 * 注入需要使用的Service.
	 */
	@Autowired
	private FundAppropriationService service;
	@Autowired
	private FundAppropriationSwfDao fundSwfDao;
	@Autowired
	private VPmProjectDao prjDao;

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	@Override
	public String getId() {
		return FundAppropriationSwf.class.getName();
	}

	@Override
	protected Object getEntity(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		String fundId = CommonUtil.trim(entityId.toString());
		
		FundAppropriationSwf swf = fundSwfDao.get(fundId);
		FundAppropriation fund = service.get(fundId);
		swf.setFund(fund);
		
		VPmProject prj = prjDao.get(swf.getPrjId());
		if (prj != null) {
			swf.setPrjManagerId(prj.getPrjManagerId());
			swf.setPrjManagerName(prj.getPrjManager());
			swf.setPrjDeptId(prj.getDeptId());
		}
		
		return swf;
	}
	   
}
