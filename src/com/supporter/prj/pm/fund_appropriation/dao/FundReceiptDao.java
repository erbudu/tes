package com.supporter.prj.pm.fund_appropriation.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.fund_appropriation.entity.FundReceipt;

@Repository
public class FundReceiptDao extends MainDaoSupport < FundReceipt, String >{
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param qualityProblemIds 模块ids
	 * @return
	 */
	public List<FundReceipt> findPage(JqGrid jqGrid, FundReceipt fundReceipt,UserProfile user,String fundId) {	
		if(fundReceipt != null){						
			if(StringUtils.isNotBlank(fundId)){
				jqGrid.addHqlFilter("fundId = '"+ fundId +"'");
				return this.retrievePage(jqGrid);
			}	
		}
		return null;
	}
	
	public List<FundReceipt> getFundReceipt(String fundId){
		String hql = "from FundReceipt where fundId = '"+fundId+"'";
		 List<FundReceipt> list = find(hql);
		return list;
	}
}
