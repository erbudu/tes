package com.supporter.prj.pm.design_change.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.design_change.entity.DesignChange;


@Repository
public class DesignChangeDao extends MainDaoSupport < DesignChange, String >{
	

	/**
	 * 分页查询
	 * @param jqGrid
	 * @param qualityProblemIds 模块ids
	 * @return
	 */
	public List<DesignChange> findPage(JqGrid jqGrid, DesignChange design,UserProfile user) {
		if(design != null){
			//申请单号
			String applyNo = design.getApplyNo();
			if(StringUtils.isNotBlank(applyNo)){
				jqGrid.addHqlFilter("applyNo like ? ","%" + applyNo + "%");
			}
			//申请日期
			Date applyDate = design.getApplyDate();
			if(applyDate != null){
				jqGrid.addHqlFilter("applyDate = ? ", applyDate);
			}
			//变更原因,此处获取到的是id
			String changeReason = design.getChangeReason();
			if(StringUtils.isNotBlank(changeReason)){
				jqGrid.addHqlFilter("changeReasonId = ? ", changeReason);
			}
			//状态
			Integer status = design.getStatus();
			if (status != null) {
				jqGrid.addHqlFilter("status = ? ", status);
			}
		//String authHql = EIPService.getAuthorityService().getHqlFilter(user,
				//DesignChange.MODULE_ID,AuthConstant.AUTH_OPER_NAME_SETVALPROBLEM);
		//jqGrid.addHqlFilter(authHql);	
		
			return this.retrievePage(jqGrid);
		}else{
			return null;
		}
	}
	
	public List<DesignChange> findExamPage(JqGrid jqGrid, DesignChange design,UserProfile user) {
		if(design != null){
			jqGrid.addHqlFilter("status = 20 ");//审批完成
			//申请单号
			String applyNo = design.getApplyNo();
			if(StringUtils.isNotBlank(applyNo)){
				jqGrid.addHqlFilter("applyNo like ? ","%" + applyNo + "%");
			}
			//申请日期
			Date applyDate = design.getApplyDate();
			if(applyDate != null){
				jqGrid.addHqlFilter("applyDate = ? ", applyDate);
			}
			//变更原因
			String changeReason = design.getChangeReason();
			if(StringUtils.isNotBlank(changeReason)){
				jqGrid.addHqlFilter("changeReasonId = ? ", changeReason);
			}
			return this.retrievePage(jqGrid);
		}
		return null;
	}
	
	/**
	 * 数据库中是否存在记录.
	 * @param applyId 设计变更ID
	 * @return boolean
	 */
	public boolean existInDB(String applyId) {
		String hql = "select count(applyId) as recCount from "
				+ DesignChange.class.getName() + " where applyId=?";
		Object obj = this.retrieveFirst(hql, applyId);
		if (obj == null) {
			return false;
		} else {
			return (Long) obj > 0;
		}
	}
}