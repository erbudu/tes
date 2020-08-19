package com.supporter.prj.pm.reserve_fund.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.reserve_fund.entity.NotReimbursed;


@Repository
public class NotReimbursedDao extends MainDaoSupport < NotReimbursed, String >{
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param qualityProblemIds 模块ids
	 * @return
	 */
	public List < NotReimbursed > findPage(JqGrid jqGrid, NotReimbursed reserveFund,UserProfile user){
		if(reserveFund != null){
			return this.retrievePage(jqGrid);
		}else{
			return null;
		}   	
    }
	
	/**
	 * 数据库中是否存在记录.
	 * @param reimbursedId 未报销现金ID
	 * @return boolean
	 */
	public boolean existInDB(String reimbursedId) {
		String hql = "select count(reimbursedId) as recCount from "
				+ NotReimbursed.class.getName() + " where reimbursedId=?";
		Object obj = this.retrieveFirst(hql, reimbursedId);
		if (obj == null) {
			return false;
		} else {
			return (Long) obj > 0;
		}
	}
	
}
