/**
 * 
 */
package com.supporter.prj.linkworks.oa.attendance.leave.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.attendance.leave.entiyt.LeaveEntity;
import org.apache.commons.lang.StringUtils;
import org.jasypt.commons.CommonUtils;

/**
 * @author YUEYUN
 * @date 2019年7月15日
 * 
 */
@Repository
public class LeaveDao extends MainDaoSupport<LeaveEntity, String> {

	public List<LeaveEntity> findPage(JqGrid jqGrid, LeaveEntity leaveEntity, String authFilter) {
		String hql = "";
		if (leaveEntity != null) {
			String keyword = leaveEntity.getCondition();
			if (StringUtils.isNotBlank(keyword)) {
				hql = "deptName like ? or leavePersonName like ?";
				 jqGrid.addHqlFilter(hql, "%"+keyword+"%", "%"+keyword+"%");
			}
			
			if(StringUtils.isNotBlank(leaveEntity.getLeaveType())) {
				hql = "leaveType = ?";
				jqGrid.addHqlFilter(hql, leaveEntity.getLeaveType());
			}
			
			if(leaveEntity.getStatus() >0) {
				hql = "status = ?";
				jqGrid.addHqlFilter(hql, leaveEntity.getStatus()-1);
			}

			if(leaveEntity.getIsSellingOff() >0) {
				hql = "isSellingOff = ?";
				jqGrid.addHqlFilter(hql, leaveEntity.getIsSellingOff()-1);
			}
			// 按申请时间倒叙排列
			jqGrid.addSortPropertyDesc("createdDate");
			// 增加权限过滤
			jqGrid.addHqlFilter(authFilter);
			return this.retrievePage(jqGrid);
		}
		
		return null;
	}
	
	

}
