package com.supporter.prj.linkworks.oa.bank_account.dao;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankMaintenanceLog;

/**   
 * @Title: dao
 * @Description: 
 * @author jiaotilei
 * @date 2018-05-04
 * @version V1.0   
 *
 */
@Repository
public class BankMaintenanceLogDao extends MainDaoSupport < BankMaintenanceLog, String > {
	

	/**
	 * 分页查询
	 */
	public List<BankMaintenanceLog> findPage(UserProfile user,JqGrid jqGrid, BankMaintenanceLog bankMaintenanceLog) {
		if (bankMaintenanceLog != null) {
			String effectiveId = bankMaintenanceLog.getEffectiveId();
			jqGrid.addHqlFilter(" effectiveId = ? ",effectiveId);
			String logOperName = bankMaintenanceLog.getLogOperName();
			if(StringUtils.isNotBlank(logOperName)){
				jqGrid.addHqlFilter(" logOperName like ? ","%"+logOperName+"%");				
			}
			jqGrid.addSortPropertyDesc("logOperDate");
		}
		return this.retrievePage(jqGrid);
	}
	
	
}
