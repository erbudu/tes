package com.supporter.prj.linkworks.oa.user_defined.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.user_defined.entity.UCard;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class UCardDao extends MainDaoSupport < UCard, String > {
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param contractIds 模块ids
	 * @return
	 */
	public List<UCard> findPage(JqGrid jqGrid, UCard card) {
		if(card != null){
			//查询
			String searchKey = card.getCreatedBy();
			if(StringUtils.isNotBlank(searchKey)){
				jqGrid.addHqlFilter(
						"createdBy like ? or project like ?  ", 
						"%" + searchKey + "%","%" + searchKey + "%");
			}
		}
		return this.retrievePage(jqGrid);
	}

	/**
	 * 获取所有的记录.
	 * 
	 * @param user
	 * @return
	 */
	public List<UCard> getAllList() {
		StringBuffer hql = new StringBuffer("from " + UCard.class.getName()
				+ " where 1=1 ");
		List<UCard> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
}
