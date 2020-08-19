package com.supporter.prj.pm.job;

import java.util.List;

import org.quartz.JobExecutionContext;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.job_schedule.IJob;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.pm.constant.PmSwfConstant;
import com.supporter.prj.pm.contract_balance.service.ContractBalanceConstService;
import com.supporter.prj.pm.enginee_negotiate.service.EngineeVisaService;
import com.supporter.prj.pm.fund_appropriation.service.FundAppropriationService;
import com.supporter.prj.pm.payment_onsite.service.PaymentOnsiteService;

/**
 * 启动现场办公业务审批定时执行类.
 * 
 * @author linda
 *
 */
public class StartProcJob implements IJob {

	@Override
	public void execute(JobExecutionContext context) {
		System.out.println("启动定时任务：启动现场业务申请流程");
		try {
			String roleId = PmSwfConstant.PM_SWF_ROLE_CREATOR;
			String empNameKeyWord = ""; // 为空检索出所有的账号人员
			List<Person> persons = EIPService.getRoleService().getPersonFromRole(roleId, empNameKeyWord);
			if (persons == null || persons.size() == 0) {
				throw new RuntimeException("请设置现场办公业务流程的启动者，角色：" + roleId);
			}
	
			String creatorId = persons.get(0).getPersonId();
			// 1.施工合同结算
			ContractBalanceConstService balanceService = SpringContextHolder.getBean(ContractBalanceConstService.class);
			balanceService.startProcBatch(creatorId);
			// 2.洽商与签证
			EngineeVisaService visaService = SpringContextHolder.getBean(EngineeVisaService.class);
			visaService.startProcBatch(creatorId);
			// 3.付款申请
			PaymentOnsiteService payService = SpringContextHolder.getBean(PaymentOnsiteService.class);
			payService.startProcBatch(creatorId);
			// 4.采购合同
			//ProcureContractService contractService = SpringContextHolder.getBean(ProcureContractService.class);
			//contractService.startProcBatch(creatorId);
			// 5.资金拨付
			FundAppropriationService fundService = SpringContextHolder.getBean(FundAppropriationService.class);
			roleId = PmSwfConstant.PM_SWF_ROLE_CREATOR_PRJ;
			persons = EIPService.getRoleService().getPersonFromRole(roleId, empNameKeyWord);
			if (persons == null || persons.size() == 0) {
				throw new RuntimeException("请设置现场办公资金拨付流程的项目经办人，角色：" + roleId);
			}
			creatorId = persons.get(0).getPersonId();
			fundService.startProcBatch(creatorId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("启动现场业务申请流程 扫描启动完毕！！！");
	}

}
