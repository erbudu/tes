package com.supporter.prj.linkworks.oa.article.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.dept_resource.service.DeptResourceService;
import com.supporter.prj.linkworks.oa.article.entity.Article;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Repository
public class ArticleDao extends MainDaoSupport < Article, String >{
	
	@Autowired
	private DeptResourceService deptResourceService;

	/**
	 * 获取发布的滚动信息
	 * @param jqGrid
	 * @param attr
	 * @return
	 */
	public List<Article> findPage(JqGrid jqGrid,String attr,String dateFrom,String dateTo,String deptResourceId,String resourcehaveAuth) {
		if(StringUtils.isNotBlank(resourcehaveAuth)){
			jqGrid.addHqlFilter("deptResourceId in ("+resourcehaveAuth+")"); 
		}
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
		
		
		jqGrid.addSortPropertyDesc("createdDate");
		List<Article> list = this.retrievePage(jqGrid);
		
		List<Article> retList = new ArrayList<Article>();
		
		for (Article article : list) {
		if(article.getPublishStatus() ==Article.DRAFT){
			article.setStatus("草稿");
		}else{
			article.setStatus("已发布");
		}
		
		//添加列表显示的文章栏
		if(StringUtils.isNotBlank(article.getDeptResourceId())){
			String fullDeptResourceName= deptResourceService.getDeptNameAndResourceName(article.getDeptResourceId());
			article.setFullDeptResourceName(fullDeptResourceName);
		}
		
		retList.add(article);
		}
		jqGrid.setRows(retList);
		return retList;
		
	}
	
	/**
	 * 根据部门资源获取首页专栏列表.
	 * @param resourceId
	 * @param recCount 每组最大记录数
	 * @return
	 */
	public List < Article > getArticleByResourceId(String resourceId, int recCount) {
		Map < String, Object > params = new HashMap < String, Object >();
		params.put("resourceId", resourceId);
		
		String hql = "from " + Article.class.getName()
				+ " where deptResourceId = :resourceId and publishStatus = " + Article.PUBLISHED
				+ " order by alwaysOnTop desc,modifiedDate desc";
		
		ListPage < Article > listPage = new ListPage < Article >();
		listPage.setPageSize(recCount);
		List < Article > list = this.findPage(listPage, hql, params).getRows();
		
		return list;
	}

	public List<Article> getArticleList() {
		StringBuffer hql = new StringBuffer("from "+Article.class.getName()+" where 1=1 ");
		List<Article> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
	
	public List<Article> getArticleList(int articleStatus) {
		StringBuffer hql = new StringBuffer("from "+Article.class.getName()+" where publishStatus = ? ");
		List<Article> list = this.find(hql.toString(), articleStatus);
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
}
