package com.supporter.prj.linkworks.oa.critical_incident_remind.dao;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.critical_incident_remind.entity.CriticalIncidentRemind;
import com.supporter.prj.linkworks.oa.critical_incident_remind.util.AuthConstant;
import com.supporter.prj.linkworks.oa.critical_incident_remind.util.CriticalIncidentRemindConstant;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Entity
 * @Description: 功能模块
 * @author jiaotilei
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class CriticalIncidentRemindDao extends MainDaoSupport < CriticalIncidentRemind, String > {
	
	//筛选出已经状态已经生效的，提醒状态为未提醒的
	public List<CriticalIncidentRemind>  getCriticalIncidentRemind() {
		String date=CommonUtil.format(new Date(), "yyyy-MM-dd");
		String hql = " from "
				+ CriticalIncidentRemind.class.getName()+" where status=1 and reminderStatus=0 and expireDate>= '" + date + "'";
		List<CriticalIncidentRemind> list = this.find(hql);
		return list;
	}
		
	
	/**
	 * 分页查询
	 */
	public List<CriticalIncidentRemind> findPage(UserProfile user,JqGrid jqGrid, CriticalIncidentRemind criticalIncidentRemind) {
		if (criticalIncidentRemind != null) {
			String incidentName = criticalIncidentRemind.getIncidentName();//根据事件名称查询或者提醒摘要查询
			if (StringUtils.isNotBlank(incidentName)) {
				jqGrid
						.addHqlFilter(
								" incidentName like ? or incidentRoundup like ? ",
								"%" + incidentName + "%", "%" + incidentName + "%");
			}
			jqGrid.addSortPropertyDesc("createdDate");
		}
		//权限过滤（判断获取数据范围的权限）
		String authHql="";
		authHql = EIPService.getAuthorityService().getHqlFilter(user,CriticalIncidentRemindConstant.MODULE_ID,AuthConstant.AUTH_OPER_NAME_AUTHCRITICALINCIDENTREMINDOFLIST );
		//System.out.println("authHql==="+authHql);
		jqGrid.addHqlFilter(authHql);
		return this.retrievePage(jqGrid);
	}

	
	
	
	
}
