package com.supporter.prj.pm.fund_appropriation.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.fund_appropriation.entity.FundReceiptActual;

@Repository
public class FundReceiptActualDao extends MainDaoSupport < FundReceiptActual, String >{
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param qualityProblemIds 模块ids
	 * @return
	 */
	public List<FundReceiptActual> findActualPage(JqGrid jqGrid, FundReceiptActual actual,UserProfile user,String fundId) {	
		if(actual != null){						
			if(StringUtils.isNotBlank(fundId)){
				jqGrid.addHqlFilter("fundId = '"+ fundId +"'");
				return this.retrievePage(jqGrid);
			}	
		}
		return null;
	}
	
	public List<FundReceiptActual> getFundReceiptActual(String fundId){
		String hql = "from FundReceiptActual where fundId = '"+fundId+"'";
		 List<FundReceiptActual> list = find(hql);
		return list;
	}
}
