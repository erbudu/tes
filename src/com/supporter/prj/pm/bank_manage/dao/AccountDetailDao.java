package com.supporter.prj.pm.bank_manage.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.bank_manage.entity.AccountDetail;

@Repository
public class AccountDetailDao extends MainDaoSupport < AccountDetail, String >{
	

	/**
	 * 分页查询
	 * @param jqGrid
	 * @param qualityProblemIds 模块ids
	 * @return
	 */
	public List<AccountDetail> findPage(JqGrid jqGrid, AccountDetail accountDetail,UserProfile user,String accountId) {
		if(accountDetail != null){
			jqGrid.addHqlFilter("accountId = ? ", accountId);
			//性质
			String nature = accountDetail.getNature();
			if(StringUtils.isNotBlank(nature) ){
				jqGrid.addHqlFilter("nature like ? ","%" + nature + "%");
			}	
			//币别
			String currency = accountDetail.getCurrency();
			if(StringUtils.isNotBlank(currency) ){
				jqGrid.addHqlFilter("currency like ? ","%" + currency + "%");
			}
		}
		return this.retrievePage(jqGrid);
	}
	
}
