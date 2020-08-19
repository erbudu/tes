package com.supporter.prj.linkworks.oa.bank_account.workflow;

import java.util.Date;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountOpenApplyEffecDao;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountChangeApply;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApplyEffec;
import com.supporter.prj.linkworks.oa.bank_account.service.BankAccountChangeApplyService;
import com.supporter.util.CommonUtil;

public class BankAccountChangeApplyEndHandler extends AbstractExecHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
					
		BankAccountChangeApplyService bankAccountChangeApplyService = SpringContextHolder.getBean(BankAccountChangeApplyService.class);
		BankAccountOpenApplyEffecDao bankAccountOpenApplyEffecDao = SpringContextHolder.getBean(BankAccountOpenApplyEffecDao.class);

		String changeApplyId = (String) execContext.getProcVar("changeApplyId");
		BankAccountChangeApply bankAccountChangeApply = bankAccountChangeApplyService.get(changeApplyId);
		if (bankAccountChangeApply.getCmecResult() == BankAccountChangeApply.CMEC_FAIL) {//如果CMEC审批不通过，放弃执行后续操作
			bankAccountChangeApply.setStatus(Long.valueOf(BankAccountChangeApply.CMEC_NO_PASS));//设置CMEC审批未通过 状态
			bankAccountChangeApply.setProcId(execContext.getProcId());
			//保存申请表
			bankAccountChangeApplyService.update(bankAccountChangeApply);
			return null;
		}
		bankAccountChangeApply.setStatus(Long.valueOf(BankAccountChangeApply.COMPLETED_NORE));
		bankAccountChangeApply.setProcId(execContext.getProcId());
		//保存申请表
		bankAccountChangeApplyService.update(bankAccountChangeApply);
		//更改生效表
		BankAccountOpenApplyEffec bankAccountOpenApplyEffec=bankAccountOpenApplyEffecDao.get(bankAccountChangeApply.getEffectiveId());
		if(bankAccountOpenApplyEffec!=null){
			//审批完成日期
			String chanTargetDate = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			bankAccountOpenApplyEffec.setChanTargetDate(chanTargetDate);
			bankAccountOpenApplyEffec.setStatus(Long.valueOf(BankAccountOpenApplyEffec.COMPLETED_CHAN_NORE+""));
		}
		bankAccountOpenApplyEffecDao.update(bankAccountOpenApplyEffec);
		return null;
	} 
}
