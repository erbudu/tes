package com.supporter.prj.pm.fund_appropriation.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.fund_appropriation.entity.FundBudgetExpend;

@Repository
public class FundBudgetExpendDao extends MainDaoSupport < FundBudgetExpend, String >{
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param qualityProblemIds 模块ids
	 * @return
	 */
	public List<FundBudgetExpend> findPage(JqGrid jqGrid, FundBudgetExpend other,UserProfile user,String fundId,String isContract) {	
		if(other != null){						
			jqGrid.addHqlFilter("fundId = '"+ fundId +"'");
			if(StringUtils.isNotBlank(isContract)){
				jqGrid.addHqlFilter("isContract = '"+ isContract +"'");
			}
			return this.retrievePage(jqGrid);
		}
		return null;
	}
	
	public List<FundBudgetExpend> getFundBudgetExpend(String fundId){
		String hql = "from FundBudgetExpend where fundId = '"+fundId+"'";
		 List<FundBudgetExpend> list = find(hql);
		return list;
	}
}
