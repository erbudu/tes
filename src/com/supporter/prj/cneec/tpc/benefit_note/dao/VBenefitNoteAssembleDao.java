package com.supporter.prj.cneec.tpc.benefit_note.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.benefit_note.entity.VBenefitNoteAssemble;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: VBenefitNoteAssembleDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2018-05-08
 * @version: V1.0
 */
@Repository
public class VBenefitNoteAssembleDao extends MainDaoSupport<VBenefitNoteAssemble, String> {

	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param parameters
	 * @return
	 */
	public List<VBenefitNoteAssemble> findPage(JqGrid jqGrid, Map<String, Object> parameters) {
		if (parameters != null && !parameters.isEmpty()) {
			String projectId = (String) parameters.get("projectId");
			jqGrid.addHqlFilter(" projectId = ? ", projectId);
		} else {
			jqGrid.addHqlFilter(" projectId = ? ", 0);
		}
		// 根据排序属性排列
		jqGrid.addSortPropertyAsc("orderDisplay");
		return this.retrievePage(jqGrid);
	}

	/**
	 * 多条件过滤
	 * 
	 * @param params
	 * @param likeSearhNames
	 * @param orders
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<VBenefitNoteAssemble> queryByParam(Map<String, Object> params, List<String> likeSearhNames, Map<String, Boolean> orders) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(VBenefitNoteAssemble.class, params, likeSearhNames, orders);
		return (List<VBenefitNoteAssemble>) getHibernateTemplate().findByCriteria(dc);
	}

	/**
	 * 条件过滤
	 * 
	 * @param properName
	 *            过滤字段
	 * @param propValue
	 *            过滤字段值
	 * @param likeSearch
	 *            是否like
	 * @param orderByName
	 *            排序字段
	 * @param sort
	 *            排序方式
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<VBenefitNoteAssemble> queryBy(String properName, Object propValue, Boolean likeSearch, String orderByName, Boolean sort) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(VBenefitNoteAssemble.class, properName, propValue, likeSearch, orderByName, sort);
		return (List<VBenefitNoteAssemble>) getHibernateTemplate().findByCriteria(dc);
	}

}
