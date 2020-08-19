package com.supporter.prj.cneec.tpc.collection_confirmation.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.collection_confirmation.entity.CollectionDetail;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: CollectionDetailDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-11-17
 * @version: V1.0
 */
@Repository
public class CollectionDetailDao extends MainDaoSupport<CollectionDetail, String> {

	/**
	 * 分页查询
	 */
	public List<CollectionDetail> findPage(JqGrid jqGrid, String collectionId) {
		// 客户明细须通过主表ID获取
		if (StringUtils.isNotBlank(collectionId)) {
			jqGrid.addHqlFilter(" collectionId = ? ", collectionId);
		} else {
			jqGrid.addHqlFilter(" collectionId = ? ", "");
		}
		jqGrid.addSortPropertyAsc("detailId");
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 根据主表ID获取list
	 * @param collectionId
	 * @return
	 */
	public List<CollectionDetail> getListByCollectionId(String collectionId){
		String hql = " from "+CollectionDetail.class.getName()+" where collectionId = ? ";
		return find(hql,collectionId);
	}
}
