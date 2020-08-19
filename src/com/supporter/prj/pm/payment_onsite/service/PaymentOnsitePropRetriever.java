package com.supporter.prj.pm.payment_onsite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.pc.pm_prj.dao.VPmProjectDao;
import com.supporter.prj.cneec.pc.pm_prj.entity.VPmProject;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.pm.payment_onsite.dao.PaymentOnsiteSwfDao;
import com.supporter.prj.pm.payment_onsite.entity.PaymentOnsite;
import com.supporter.prj.pm.payment_onsite.entity.PaymentOnsiteSwf;
import com.supporter.util.CommonUtil;

@Service
public class PaymentOnsitePropRetriever extends AbstractPropRetriever {
	/**
	 * 注入需要使用的Service.
	 */
	@Autowired
	private PaymentOnsiteService service;
	@Autowired
	private PaymentOnsiteSwfDao swfDao;
	@Autowired
	private VPmProjectDao prjDao;

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	@Override
	public String getId() {
		return PaymentOnsiteSwf.class.getName();
	}

	@Override
	protected Object getEntity(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		String id = CommonUtil.trim(entityId.toString());
		PaymentOnsiteSwf swf = swfDao.get(id);
		PaymentOnsite paymentOnsite = service.get(id);
		swf.setPaymentOnsite(paymentOnsite);
		
		VPmProject prj = prjDao.get(swf.getPrjId());
		if (prj != null) {
			swf.setPrjManagerId(prj.getPrjManagerId());
			swf.setPrjManagerName(prj.getPrjManager());
			swf.setPrjDeptId(prj.getDeptId());
		}
		return swf;
	}
	   
}
