package com.supporter.prj.cneec.tpc.collection_confirmation.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.collection_confirmation.entity.CollectionConfirmation;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: CollectionConfirmationDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-11-17
 * @version: V1.0
 */
@Repository
public class CollectionConfirmationDao extends MainDaoSupport<CollectionConfirmation, String> {

	/**
	 * 分页查询
	 */
	public List<CollectionConfirmation> findPage(JqGrid jqGrid, CollectionConfirmation collectionConfirmation, String authFilter) {
		if (collectionConfirmation != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = collectionConfirmation.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectName like ? or collectionNo like ? or collectionBusiness like ? or businessPreposeRecord like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 状态过滤
			if (collectionConfirmation.getSwfStatus() != null) {
				jqGrid.addHqlFilter(" swfStatus = ? ", collectionConfirmation.getSwfStatus());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

	/**
	 * 多条件过滤
	 * 
	 * @param params
	 *            过滤字段集
	 * @param likeSearhNames
	 *            like字段集
	 * @param orders
	 *            排序字段集
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CollectionConfirmation> queryByParam(Map<String, Object> params, List<String> likeSearhNames, Map<String, Boolean> orders) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(CollectionConfirmation.class, params, likeSearhNames, orders);
		return (List<CollectionConfirmation>) getHibernateTemplate().findByCriteria(dc);
	}

	/**
	 * 获取销售合同下所有收款
	 * @param contractId
	 * @return
	 */
	public List<CollectionConfirmation> getCollectionConfirmationByContractId(String contractId) {
		if (StringUtils.isNotBlank(contractId)) {
			String hql = " from " + CollectionConfirmation.class.getName() + " where businessPreposeId = ? ";
			List<CollectionConfirmation> list = this.find(hql, contractId);
			if (list != null) {
				return list;
			}
		}
		return null;
	}

}
