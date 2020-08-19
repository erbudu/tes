package com.supporter.prj.linkworks.oa.doc_in.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.doc_in.entity.DocInSrcUnit;
import com.supporter.util.CommonUtil;

/**
 * @Title: Entity
 * @Description: 物资档案设置
 * @author yanbingchao
 * @date 2017-03-27 14:00:00
 * @version V1.0
 * 
 */
@Repository
public class DocInSrcUnitDao extends MainDaoSupport<DocInSrcUnit, String> {
	/**
	 * 查询操作
	 * 
	 * @param budgetYear
	 * @param keyword
	 * @return List <  >
	 */
	public List<DocInSrcUnit> findByKeyword(String keyword) {
		String hql = "from " + DocInSrcUnit.class.getName() + " where keyWords like ?";
		List<DocInSrcUnit> entities = this.find(hql, "%" + CommonUtil.trim(keyword)
				+ "%");
		return entities;
	}
	public boolean check(String name){
		String hql = "from "+ DocInSrcUnit.class.getName() +" where unitName = ? ";
		List<DocInSrcUnit> entities = this.find(hql,name);
		if(CollectionUtils.isEmpty(entities)){
			return true;
		}
		return false;
		}
	public List<DocInSrcUnit> getGrid(JqGrid jqGrid) {
		return this.retrievePage(jqGrid);
		
	}
}
