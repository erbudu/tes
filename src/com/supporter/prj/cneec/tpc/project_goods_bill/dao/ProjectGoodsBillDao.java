package com.supporter.prj.cneec.tpc.project_goods_bill.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.project_goods_bill.entity.ProjectGoodsBill;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: ProjectGoodsBillDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-11-08
 * @version: V1.0
 */
@Repository
public class ProjectGoodsBillDao extends MainDaoSupport<ProjectGoodsBill, String> {

	/**
	 * 分页查询
	 */
	public List<ProjectGoodsBill> findPage(JqGrid jqGrid, ProjectGoodsBill projectGoodsBill, String authFilter) {
		if (projectGoodsBill != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = projectGoodsBill.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" contractName like ? or itemName like ? or hsCode like ? or specification like ? or country like ? or manufacturer like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 状态过滤
			if (projectGoodsBill.getBillStatus() != null) {
				jqGrid.addHqlFilter(" billStatus = ? ", projectGoodsBill.getBillStatus());
			}
			jqGrid.addSortPropertyAsc("projectId, contractId");
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

	/** 以下是选择清单树方法 **/
	
	public ListPage<ProjectGoodsBill> getListPage(ListPage<ProjectGoodsBill> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectGoodsBill> getList(Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrieve(hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + ProjectGoodsBill.class.getName() + " t where 1 = 1");
		if (parameters != null && !parameters.isEmpty()) {
			String projectId = (String) parameters.get("projectId");
			String.valueOf("");
			if (StringUtils.isNotBlank(projectId)) {
				hql.append(" and t.projectId = :projectId");
			} else {
				hql.append(" and 1 <> 1");
			}
			// 查询条件
			String keyword = (String) parameters.get("keyword");
			if (StringUtils.isNotBlank(keyword)) {
				hql.append(" and (");
				// 合同信息
				hql.append(" t.contractNo like :keyword or t.contractName like :keyword");
				// 货物/服务信息
				hql.append(" or t.itemName like :keyword or t.hsCode like :keyword");
				hql.append(" or t.specification like :keyword or t.country like :keyword");
				hql.append(" or t.manufacturer like :keyword");
				hql.append(" )");
				parameters.put("keyword", "%" + keyword + "%");
			}
		} else {
			hql.append(" and 1 <> 1");
		}
		hql.append(" order by t.contractId asc, t.createdDate desc");
		return hql.toString();
	}

}
