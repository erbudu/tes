package com.supporter.prj.pm.enginee_negotiate.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisaSite;
import com.supporter.util.CommonUtil;

/**   
 * @Title: 签证-工程部位
 * @Description: PM_ENGINEE_VISA_SITE.
 * @author Administrator
 * @date 2018-07-04 18:08:55
 * @version V1.0   
 *
 */
@Repository
public class EngineeVisaSiteDao extends MainDaoSupport< EngineeVisaSite, String > {
	/**
	 * 分页查询
	 * @param jqGrid 表格参数
	 * @param visaId 签证ID
	 * @return List<EngineeVisaSite>
	 */
	public List<EngineeVisaSite> findPage(JqGrid jqGrid, String visaId) {
		jqGrid.addHqlFilter(" visaId = ? ", CommonUtil.trim(visaId));
		jqGrid.addSortPropertyAsc("siteId");
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 获取根节点.
	 * @param visaId 签证ID
	 * @return EngineeVisaSite
	 */
	public EngineeVisaSite getRootSite(String visaId) {               
		String hql = "from " + EngineeVisaSite.class.getName()
				+ " where visaId=:visaId and parentSiteId is null";
		EngineeVisaSite site = this.findFirstByNamedParam(hql, "visaId", visaId);
		return site;
	}
	
	public List<EngineeVisaSite> getSiteList(String visaId){
		String hql = "from EngineeVisaSite where visaId = '"+visaId+"'";
		 List<EngineeVisaSite> list = find(hql);
		return list;
	}
	
	public EngineeVisaSite getSiteByParentId(String parentId){
		List<EngineeVisaSite> sites = this.find("from EngineeVisaSite where siteId ='"+parentId+"'");
		if(sites.size()!=0)
		return sites.get(0);
		return null;
	}
	
	//get tasks
	public List<EngineeVisaSite> getSites(String hql){
		return this.find(hql);
	}
}

