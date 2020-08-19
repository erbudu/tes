package com.supporter.prj.pm.payment_onsite.workflow;

import org.apache.commons.lang3.StringUtils;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.swf.dao.MyWfExamDao;
import com.supporter.prj.eip.swf.runtime.entity.WfExam;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.pm.payment_onsite.dao.PaymentOnsiteSwfDao;
import com.supporter.prj.pm.payment_onsite.entity.PaymentOnsiteSwf;

/**
 * 付款申请-审批不通过
 * @author Administrator
 *
 */
public class PaymentOnsiteHandlerFail extends AbstractExecHandler {
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
		swf.setOaExamResult(PaymentOnsiteSwf.OAExamResult.FAILED);
		
		MyWfExamDao examDao = SpringContextHolder.getBean(MyWfExamDao.class);
		String procId = execContext.getProcId();
		String nodeName = execContext.getCurrNodeName();
		WfExam exam = examDao.getLatestExamByNodeName(procId, nodeName);
		String empName = "";
		if (exam != null) {
			swf.setOaExamOpinion(exam.getOpinionDesc());
			empName = exam.getEmpName();
		}
		if (StringUtils.isBlank(swf.getOaExamOpinion())) {
			swf.setOaExamOpinion(PaymentOnsiteSwf.OAExamResult.getMap().get(swf.getOaExamResult()));
		}
		swf.setOaExamOpinion(nodeName + "驳回（" + empName + "）：" + swf.getOaExamOpinion());
		
		dao.saveOrUpdate(swf);
		return null;
	} 
}
