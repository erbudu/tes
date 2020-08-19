package com.supporter.prj.pm.public_proc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.eip.module.constant.ModuleConstant;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.public_proc.service.PublicProcService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("pm/public_proc")
public class PublicProcController extends AbstractController{
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private PublicProcService service;	

	/**
	 * 根据业务单的ID（entityId）来更新和保存
	 * entityId    业务单ID
	 * entityName  业务单来源
	 * examOne     审核人
	 * examTwo     批准人
	 * examThree   预留审批人1
	 * examFour    预留审批人2
	 * @return
	 */
	@RequestMapping("updatePublicProcByEntityId")
	public @ResponseBody OperResult updatePublicProcByEntityId() {
		String entityId = this.getRequest().getParameter("entityId");
		String entityName = this.getRequest().getParameter("entityName");
		String examOne = this.getRequest().getParameter("examOne");
		String examTwo = this.getRequest().getParameter("examTwo");
		String examThree = this.getRequest().getParameter("examThree");
		String examFour = this.getRequest().getParameter("examFour");
		UserProfile user = this.getUserProfile();
		this.service.updatePublicProcByEntityId(user, entityId, entityName, examOne, examTwo, examThree, examFour);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS, null, null);
	}
	
}
