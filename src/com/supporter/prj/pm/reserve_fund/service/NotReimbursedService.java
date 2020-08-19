package com.supporter.prj.pm.reserve_fund.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.reserve_fund.dao.NotReimbursedDao;
import com.supporter.prj.pm.reserve_fund.dao.NotReimbursedRecDao;
import com.supporter.prj.pm.reserve_fund.entity.NotReimbursed;
import com.supporter.prj.pm.reserve_fund.entity.NotReimbursedRec;


@Service
@Transactional(TransManager.APP)
public class NotReimbursedService {
	@Autowired
	private NotReimbursedDao dao;
	@Autowired
	private NotReimbursedRecDao recDao;
	
	/**
	 * 获取JqGrid表格(未登录).--主表
	 * @param jqGrid 表格
	 * @param params 参数
	 * @return 供应商列表
	 */
	public List<NotReimbursed> getGrid(JqGrid jqGrid, NotReimbursed notReimbursed,UserProfile user) {
		return dao.findPage(jqGrid, notReimbursed,user);
	}
	
	/**
	 * 获取JqGrid表格(未登录).--从表
	 * @param jqGrid 表格
	 * @param params 参数
	 * @return 供应商列表
	 */
	public List<NotReimbursedRec> getRecGrid(JqGrid jqGrid, NotReimbursedRec reserveFundRec,UserProfile user,String reimbursedId) {
		return recDao.findPage(jqGrid, reserveFundRec,user,reimbursedId);
	}
	
}
