package com.supporter.prj.pm.reserve_fund.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.reserve_fund.entity.NotReimbursedRec;


@Repository
public class NotReimbursedRecDao extends MainDaoSupport < NotReimbursedRec, String >{
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param qualityProblemIds 模块ids
	 * @return
	 */
	public List < NotReimbursedRec > findPage(JqGrid jqGrid, NotReimbursedRec notReimbursedRec,UserProfile user,String reimbursedId){
		if(notReimbursedRec != null){
			if(StringUtils.isNotBlank(reimbursedId) ){
				jqGrid.addHqlFilter("reimbursedId = ? ",reimbursedId);
			}
		}
    	return this.retrievePage(jqGrid);
    }
	
}
