package com.supporter.prj.linkworks.oa.news_exam.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.swf.util.WfModule;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.news_exam.entity.NewsExamRec;
import com.supporter.prj.linkworks.oa.news_exam.service.NewsExamRecService;
import com.supporter.util.CommonUtil;


/**   
 * @Title: 新闻审批DAO.
 * @Description: 功能模块表
 * @author linda
 * @version V1.0   
 *
 */
@Repository
public class NewsExamRecDao extends MainDaoSupport < NewsExamRec, String >{
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public List < NewsExamRec > findByKeyword(String searchKeyword){
		String hql = "from " + NewsExamRec.class.getName() + " where newsTitle like ?";
		return this.retrieve(hql, "%" + CommonUtil.trim(searchKeyword) + "%");
	}
	
	/**
     * 得到jqGrid表格.
     * @param jqGrid
     * @param params
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
	public List < NewsExamRec > getGrid(UserProfile user, JqGrid jqGrid, Map < String, Object > params){
    	if (user == null)return null;
    	
    	String hql = "from " + NewsExamRec.class.getName();
    	
    	StringBuffer where = new StringBuffer("");
    	//查询条件
    	if (params != null){
    		if (params.containsKey("searchKeyword")){
    			String searchKeyword = CommonUtil.trim((String)params.get("searchKeyword"));
    			
    			if (searchKeyword.length() > 0){
    				params.put("searchKeyword", "%" + searchKeyword + "%");
	    			if (where.length() > 0)where.append(" and ");
	    			where.append("newsTitle like :searchKeyword");
    			}
    		}
    		
    	}

    	//权限
    	String authFilter = EIPService.getAuthorityService().getHqlFilter(user, WfModule.APP_NAME,
    			NewsExamRecService.Auth.LIST, "");
    	if (authFilter.length() > 0){
    		if (where.length() > 0)where.append(" and ");
    		where.append("(" + authFilter + ")");
    	}
    	
    	if (where.length() > 0){
    		hql += " where " + where.toString();
    	}
    	
    	//排序
    	String orderBy = jqGrid.getOrder();
    	if (orderBy.length() > 0){
    		hql += orderBy;
    	} else {
    		hql += " order by applyDate desc";
    	}
    	
    	return this.retrievePage(jqGrid, hql, params).getRows();
    }
}
