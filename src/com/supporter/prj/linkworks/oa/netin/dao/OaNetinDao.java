package com.supporter.prj.linkworks.oa.netin.dao;

import java.util.List;

import javax.security.auth.kerberos.KerberosKey;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;


import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.netin.entity.OaNetin;

@Repository
public class OaNetinDao extends MainDaoSupport<OaNetin, String> {
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param netin
	 * @return
	 */
	public List<OaNetin> findPage(JqGrid jqGrid, OaNetin netin){
		if (netin != null){
			//查询框
			String keyword = netin.getKeyword();
			if (StringUtils.isNotBlank(keyword)){
				jqGrid.addHqlFilter(" createdBy like ? or deptName like ? ", "%"+keyword+"%", "%"+keyword+"%");
			}
			//按申请时间倒叙排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		return this.retrievePage(jqGrid);
	}
}
