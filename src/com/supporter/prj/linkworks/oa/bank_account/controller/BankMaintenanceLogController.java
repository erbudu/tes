package com.supporter.prj.linkworks.oa.bank_account.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankMaintenanceLog;
import com.supporter.prj.linkworks.oa.bank_account.service.BankMaintenanceLogService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("oa/bankMaintenanceLog")
public class BankMaintenanceLogController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private BankMaintenanceLogService bankMaintenanceLogService; 

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, BankMaintenanceLog bankMaintenanceLog) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.bankMaintenanceLogService.getGrid(user, jqGrid, bankMaintenanceLog);
			
		return jqGrid;
	}

	
	/**
	 * 根据主键获取功能模块�?.
	 * 
	 * @param bankMaintenanceLogId 主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody BankMaintenanceLog get(String cooperativeId) {
		BankMaintenanceLog bankMaintenanceLog = bankMaintenanceLogService.get(cooperativeId);
		return bankMaintenanceLog;
	}
	
}
