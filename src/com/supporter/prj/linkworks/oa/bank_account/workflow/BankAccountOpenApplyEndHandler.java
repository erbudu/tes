package com.supporter.prj.linkworks.oa.bank_account.workflow;

import java.util.Date;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountOpenApplyEffecDao;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApply;
import com.supporter.prj.linkworks.oa.bank_account.service.BankAccountOpenApplyService;
import com.supporter.util.CommonUtil;

public class BankAccountOpenApplyEndHandler extends AbstractExecHandler {

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
					
		BankAccountOpenApplyService bankAccountOpenApplyService = SpringContextHolder.getBean(BankAccountOpenApplyService.class);
		BankAccountOpenApplyEffecDao bankAccountOpenApplyEffecDao = SpringContextHolder.getBean(BankAccountOpenApplyEffecDao.class);

		String applyId = (String) execContext.getProcVar("applyId");
		BankAccountOpenApply bankAccountOpenApply = bankAccountOpenApplyService.get(applyId);
		if (bankAccountOpenApply.getCmecResult() == BankAccountOpenApply.CMEC_FAIL) {
			bankAccountOpenApply.setStatus(Long.valueOf(BankAccountOpenApply.CMEC_NO_PASS));
		}else {
			bankAccountOpenApply.setStatus(Long.valueOf(BankAccountOpenApply.COMPLETED_NORE));
		}
			//审批完成日期
			String targetDate = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			bankAccountOpenApply.setTargetDate(targetDate);
			bankAccountOpenApply.setProcId(execContext.getProcId());
			
//			BankAccountOpenApplyEffec bankAccountOpenApplyEffec= new BankAccountOpenApplyEffec();
//			//将申请表相同于生效表属性的值复制到生效表
//			BeanUtils.copyProperties(bankAccountOpenApply,bankAccountOpenApplyEffec);
			
			//保存申请表
			bankAccountOpenApplyService.update(bankAccountOpenApply);
			//保存生效表
//			bankAccountOpenApplyEffec.setBankAccountName(bankAccountOpenApply.getAccountName());//银行账户名称
//			bankAccountOpenApplyEffec.setEffectiveId(com.supporter.util.UUIDHex.newId());
//			bankAccountOpenApplyEffec.setChangeTimes(0l);
//			//bankAccountOpenApplyEffec.setStatus(Long.valueOf(BankAccountOpenApplyEffec.COMPLETED_NORE+""));//开立完成未备案
//			if(bankAccountOpenApplyEffec.getNationalityId()!=null&&bankAccountOpenApplyEffec.getNationalityId().equals("BANK-C-001")){//国别等于中国
//				bankAccountOpenApplyEffec.setIsOutside("no");
//			}else{//国别不等于中国
//				bankAccountOpenApplyEffec.setIsOutside("yes");
//			}
//			bankAccountOpenApplyEffecDao.save(bankAccountOpenApplyEffec);
					
		return null;
				
	} 
}
