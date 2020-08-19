package com.supporter.prj.pm.payment_onsite.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.pm.payment_onsite.dao.PaymentOnsiteSwfDao;
import com.supporter.prj.pm.payment_onsite.entity.PaymentOnsiteSwf;

/**
 * 付款申请-审批通过
 * @author Administrator
 *
 */
public class PaymentOnsiteHandlerPass extends AbstractExecHandler {
	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		PaymentOnsiteSwfDao dao = SpringContextHolder.getBean(PaymentOnsiteSwfDao.class);
		String id = (String) execContext.getProcVar("id");
		PaymentOnsiteSwf swf =  dao.get(id);
		swf.setOaExamResult(PaymentOnsiteSwf.OAExamResult.SUCCESS);
		swf.setOaExamOpinion(PaymentOnsiteSwf.OAExamResult.getMap().get(swf.getOaExamResult()));
		dao.saveOrUpdate(swf);
		return null;
	} 
}
