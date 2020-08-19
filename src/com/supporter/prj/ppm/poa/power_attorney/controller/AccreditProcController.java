package com.supporter.prj.ppm.poa.power_attorney.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.eip.swf.runtime.service.WfEngine;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.account.entity.Account;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.poa.power_attorney.entity.PowerAttorney;
import com.supporter.prj.ppm.poa.power_attorney.service.PowerAttorneyService;
import com.supporter.spring_mvc.AbstractController;

import net.sf.json.JSONObject;

/**
 * 授权书流程相关方法
 */
@Controller
@RequestMapping("ppm/swf/proc/")
public class AccreditProcController extends AbstractController {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private WfEngine wfEngine;
	@Autowired
	private PowerAttorneyService powerAttorneyService;//授权书服务类
	
	/**
	 * 启动授权书流程方法
	 * @param procDefName 流程编号
	 * @param nextActorId 下一节点承担人id
	 * @param leavingTransId
	 * @param busiJson 各模块实体对象流程相关属性的json串
	 * @param nextNodeId 下一节点id
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping({"startAccreditProc"})
	@ResponseBody
	public OperResult < ? > startProc(String procDefName, String nextActorId,
			String leavingTransId, String busiJson, String nextNodeId) {
		//获取授权书创建人
		JSONObject json = JSONObject.fromObject(busiJson);  
		String laId = json.getString("entityId");
		if(StringUtils.isNotBlank(laId)) {
			PowerAttorney powerAttorney = powerAttorneyService.get(laId);
			Person person = EIPService.getEmpService().getEmp(powerAttorney.getCreatedById());
	        Account account = EIPService.getAccountService().getAccounts(person).get(0);
	        UserProfile user = EIPService.getLogonService().getUserProfile(account);
			String procId = wfEngine.startProc(procDefName, nextActorId, leavingTransId, busiJson, nextNodeId, user);
			return OperResult.succeed("200", "ok", procId);
		}else {
			return OperResult.fail("500", "error");
		}
		
	}
	

}
