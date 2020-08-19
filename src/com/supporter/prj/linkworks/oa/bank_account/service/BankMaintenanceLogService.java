package com.supporter.prj.linkworks.oa.bank_account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankMaintenanceLogDao;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankMaintenanceLog;


@Service
public class BankMaintenanceLogService {
	@Autowired
	private BankMaintenanceLogDao bankMaintenanceLogDao;

	public BankMaintenanceLog get(String moduleId) {
		return bankMaintenanceLogDao.get(moduleId);
	}	
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<BankMaintenanceLog> getGrid(UserProfile user, JqGrid jqGrid, BankMaintenanceLog bankMaintenanceLog) {
		List<BankMaintenanceLog> list=this.bankMaintenanceLogDao.findPage(user,jqGrid, bankMaintenanceLog);//根据输入的查询条件查询列表
		return list;	
		
	}
	
	
}
