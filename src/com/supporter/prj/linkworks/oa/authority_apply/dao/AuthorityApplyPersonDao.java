package com.supporter.prj.linkworks.oa.authority_apply.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApplyPerson;

/**   
 * @Title: Entity
 * @Description: 功能模块�?
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class AuthorityApplyPersonDao extends MainDaoSupport < AuthorityApplyPerson, String > {
	
	/**
	 * 根据ID获取.
	 * @param applyId
	 * @return
	 */
	public List <AuthorityApplyPerson> getByApplyId(String applyId) {
		String hql = "from " + AuthorityApplyPerson.class.getName() + " where applyId = ?";
		List <AuthorityApplyPerson> list = this.find(hql, applyId);
		
		if (list == null || list.size() == 0) return null;
		
		return list;
	}
	
	/**
	 * 根据授权书id和人员来源获取公司内部人员
	 * @param jqGrid
	 * @param ap
	 * @param recordId
	 * @return
	 */
	public List<AuthorityApplyPerson> getPersonGrid(JqGrid jqGrid,String applyId,String flag){
		
		jqGrid.addHqlFilter("applyId = ?  ",applyId);			
	
		if(flag.equals("inner")){
			jqGrid.addHqlFilter("poleId = 10 ");
		}else if(flag.equals("outer")){
			jqGrid.addHqlFilter("poleId = 20 ");
		}		
		List<AuthorityApplyPerson> list = this.retrievePage(jqGrid);
		jqGrid.setRows(list);
		
		return list;
		
	}
		
}
