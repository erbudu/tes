package com.supporter.prj.linkworks.oa.bank_account.workflow;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountOpenApplyEffecDao;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApply;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApplyEffec;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountRevokeApply;
import com.supporter.prj.linkworks.oa.bank_account.service.BankAccountRevokeApplyService;
import com.supporter.util.CommonUtil;

public class BankAccountRevokeApplyEndHandler extends AbstractExecHandler {

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
					
		BankAccountRevokeApplyService bankAccountRevokeApplyService = SpringContextHolder.getBean(BankAccountRevokeApplyService.class);
		BankAccountOpenApplyEffecDao bankAccountOpenApplyEffecDao = SpringContextHolder.getBean(BankAccountOpenApplyEffecDao.class);

		String revokeId = (String) execContext.getProcVar("revokeId");
		BankAccountRevokeApply bankAccountRevokeApply = bankAccountRevokeApplyService.get(revokeId);
		if (bankAccountRevokeApply.getCmecResult() == BankAccountRevokeApply.CMEC_FAIL) {//如果CMEC审批不通过，放弃执行后续操作
			bankAccountRevokeApply.setStatus(Long.valueOf(BankAccountRevokeApply.CMEC_NO_PASS));//设置CMEC审批未通过 状态
			bankAccountRevokeApply.setProcId(execContext.getProcId());
			//保存申请表
			bankAccountRevokeApplyService.update(bankAccountRevokeApply);
			return null;
		}
		bankAccountRevokeApply.setStatus(Long.valueOf(BankAccountRevokeApply.COMPLETED));

		bankAccountRevokeApply.setProcId(execContext.getProcId());			
		//保存申请表
		bankAccountRevokeApplyService.update(bankAccountRevokeApply);
		//撤销审批完成之后修改生效表中的状态
		BankAccountOpenApplyEffec bankAccountOpenApplyEffec=bankAccountOpenApplyEffecDao.get(bankAccountRevokeApply.getEffectiveId());
		if(bankAccountOpenApplyEffec!=null){
	    	String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd");
			bankAccountOpenApplyEffec.setRevokeDate(date);
			bankAccountOpenApplyEffec.setStatus(Long.valueOf(BankAccountOpenApplyEffec.REVOKE+""));
		}
		bankAccountOpenApplyEffecDao.save(bankAccountOpenApplyEffec);
					
		return null;
				
	} 
}
