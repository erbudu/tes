package com.supporter.prj.linkworks.oa.article.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.dept_resource.service.DeptResourceService;
import com.supporter.prj.linkworks.oa.article.entity.Article;
import com.supporter.prj.linkworks.oa.article.entity.VArticle;

@Repository
public class VArticleDao extends MainDaoSupport < VArticle , String >{
	@Autowired
	private DeptResourceService deptResourceService;	
	
	public List<VArticle> findPage(JqGrid jqGrid,String attr,String dateFrom,String dateTo,String deptResourceId,String authorizeeIds) {
		
		
		if(StringUtils.isNotBlank(attr)){
			jqGrid.addHqlFilter(
					"articleTitle like ?  or publisherName like ? ", 
					"%" + attr + "%","%" + attr + "%");
		}
		
		if(StringUtils.isNotBlank(dateFrom)){
			jqGrid.addHqlFilter(" publishDate >= ? ", dateFrom);
			
		}
		
		if(StringUtils.isNotBlank(dateTo)){
			jqGrid.addHqlFilter("  publishDate <= ? ",dateTo);
			
		}
		
		if(StringUtils.isNotBlank(deptResourceId)&& !deptResourceId.equals("1")){
			jqGrid.addHqlFilter(" deptResourceId like ? ","%" + deptResourceId+ "%");
			
		}
		if(StringUtils.isNotBlank(authorizeeIds)){
			jqGrid.addHqlFilter(" authorizeeId in ("+authorizeeIds+")");
			jqGrid.addHqlFilter("canRead = ? or canWrite =? or canDelete=? or fullAccess= ? ", 
					"1","1","1","1");
		}
		
		
		List<VArticle> list = this.retrievePage(jqGrid);
		List<VArticle> retList = new ArrayList<VArticle>();
		
		for (VArticle article : list) {
		if(article.getPublishStatus() ==Article.DRAFT){
			article.setStatus("草稿");
		}else{
			article.setStatus("已发布");
		}
		
		//添加列表显示的公告板
		if(StringUtils.isNotBlank(article.getDeptResourceId())){
			String fullDeptResourceName= deptResourceService.getDeptNameAndResourceName(article.getDeptResourceId());
			article.setFullDeptResourceName(fullDeptResourceName);
		}
		
		retList.add(article);
		}
		jqGrid.setRows(retList);
		return retList;
		
	}
}
