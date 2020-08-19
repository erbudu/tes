package com.supporter.prj.cneec.tpc.benefit_note.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.benefit_note.entity.BenefitNote;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: BenefitNoteDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2018-06-01
 * @version: V1.0
 */
@Repository
public class BenefitNoteDao extends MainDaoSupport<BenefitNote, String> {

	/**
	 * 分页查询
	 */
	public List<BenefitNote> findPage(JqGrid jqGrid, Map<String, Object> valueMap, String authFilter) {
		if (valueMap != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = (String) valueMap.get("keyword");
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" benefitNo like ? or projectName like ? or contractNo like ? or contractName like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}

			/* 以下是更多条件中选择项 */
			// 流程确认的效益测算表
			if (valueMap.containsKey("hasSwfProc")) {
				jqGrid.addHqlFilter(" hasSwfProc = 'T' ");
			}
			// 状态过滤
			if (valueMap.containsKey("swfStatus")) {
				Integer swfStatus = (Integer) valueMap.get("swfStatus");
				jqGrid.addHqlFilter(" swfStatus = ? ", swfStatus);
			}
			// 根据创建时间倒序排列
			//jqGrid.addSortPropertyDesc("createdDate");
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
	public List<BenefitNote> queryByParam(Map<String, Object> params, List<String> likeSearhNames, Map<String, Boolean> orders) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitNote.class, params, likeSearhNames, orders);
		return (List<BenefitNote>) getHibernateTemplate().findByCriteria(dc);
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
	public List<BenefitNote> queryBy(String properName, Object propValue, Boolean likeSearch, String orderByName, Boolean sort) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitNote.class, properName, propValue, likeSearch, orderByName, sort);
		return (List<BenefitNote>) getHibernateTemplate().findByCriteria(dc);
	}

	/** 以下是选择效益测算表方法 **/

	@SuppressWarnings("unchecked")
	public List<BenefitNote> getList(Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrieve(hql, parameters);
	}

	public ListPage<BenefitNote> getListPage(ListPage<BenefitNote> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + BenefitNote.class.getName() + " t where 1=1");
		// 状态(默认为完成)
		Integer swfStatus = (Integer) parameters.get("swfStatus");
		if (swfStatus == null) {
			swfStatus = BenefitNote.CONFIRMED;
		}
		hql.append(" and t.swfStatus = :swfStatus");
		parameters.put("swfStatus", swfStatus);
		// 版本(默认为初版)
		Integer versions = 0;
		if (parameters.containsKey("versions")) {
			versions = Integer.valueOf(parameters.get("versions").toString());
		}
		hql.append(" and t.versions = :versions");
		parameters.put("versions", versions);
		// 项目ID，必要字段
		String projectId = (String) parameters.get("projectId");
		if (StringUtils.isNotBlank(projectId)) {
			hql.append(" and t.projectId = :projectId");
		} else {
			hql.append(" and 1 <> 1");
		}
		// 其他过滤条件
		String keyword = (String) parameters.get("keyword");
		if (StringUtils.isNotBlank(keyword)) {
			hql.append(" and (");
			hql.append(" t.contractNo like :keyword or t.contractName like :keyword");
			hql.append(" )");
			parameters.put("keyword", "%" + keyword + "%");
		}
		// 为特定模块选择过滤
		if (parameters.containsKey("selectTo")) {
			String selectTo = (String) parameters.get("selectTo");
			hql.append(getSelectToHql(selectTo, parameters));
		}
		//hql.append(" and " + authFilter);
		hql.append(" order by t.benefitNo desc");
		System.out.println(hql);
		return hql.toString();
	}

	/**
	 * 其他过滤参数
	 * @param selectTo
	 * @return
	 */
	private String getSelectToHql(String selectTo, Map<String, Object> parameters) {
		StringBuffer filter = new StringBuffer();
		return filter.toString();
	}

	/**
	 * 提交确认修改状态
	 *
	 * @param noteId
	 * @param swfStatus
	 */
	public void updateNoteStatusById(String noteId, Integer swfStatus) {
		String hql = "update " + BenefitNote.class.getName() + " set swfStatus = ? where noteId = ?";
		this.execUpdate(hql, swfStatus, noteId);
	}

}
