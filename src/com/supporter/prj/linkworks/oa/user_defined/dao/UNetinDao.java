package com.supporter.prj.linkworks.oa.user_defined.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.doc_in.entity.DocIn;
import com.supporter.prj.linkworks.oa.report.entity.Report;
import com.supporter.prj.linkworks.oa.user_defined.entity.UNetin;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class UNetinDao extends MainDaoSupport < UNetin, String > {
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param contractIds 模块ids
	 * @return
	 */
	public List<UNetin> findPage(JqGrid jqGrid, UNetin uNetin) {
		if(uNetin != null){
			//查询
			String searchKey = uNetin.getPersons();
			if(StringUtils.isNotBlank(searchKey)){
				jqGrid.addHqlFilter(
						"createdBy like ? or deptName like ? or persons like ? ", 
						"%" + searchKey + "%","%" + searchKey + "%","%" + searchKey + "%");
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
	public List<UNetin> getAllList() {
		StringBuffer hql = new StringBuffer("from " + UNetin.class.getName()
				+ " where 1=1 ");
		List<UNetin> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
}
