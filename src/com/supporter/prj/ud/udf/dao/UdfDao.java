package com.supporter.prj.ud.udf.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ud.func.entity.UdFuncPageCell;

@Repository
public class UdfDao extends MainDaoSupport<UdFuncPageCell, String> {
	public List<UdFuncPageCell> findPage(JqGrid jqGrid, String pageId,
			String keyWord, List<UdFuncPageCell> cells) {
		if (StringUtils.isNotBlank(pageId)) {
			String sql = "select * from U_" + pageId.substring(5)
					+ " where 1=1 ";
			String sql2 = "select count(*) from U_" + pageId.substring(5)
					+ " where 1=1 ";
			if (StringUtils.isNotBlank(keyWord)) {
				if (cells != null) {
					String str = " and (";
					for (UdFuncPageCell udFuncPageCell : cells) {
						str += " " + udFuncPageCell.getInputMode()
								+ udFuncPageCell.getListId() + " like '%"
								+ keyWord + "%' or ";
					}
					str += " CREATED_BY like '%" + keyWord
							+ "%' or CREATED_DATE like '" + keyWord
							+ "' or MODIFIED_BY like '%" + keyWord
							+ "%' or modified_date like '%" + keyWord + "%')";
					sql += str;
					sql2 += str;
				}
			}
			Query query = getHibernateTemplate().getSessionFactory()
					.openSession().createSQLQuery(sql).setFirstResult(
							(jqGrid.getPage() - 1) * jqGrid.getPageSize())
					.setMaxResults(jqGrid.getPageSize()).setResultTransformer(
							Transformers.ALIAS_TO_ENTITY_MAP);

			// 查询总条数
			Object totals_obj = getHibernateTemplate().getSessionFactory()
					.openSession().createSQLQuery(sql2).uniqueResult();
			Long totals = Long.valueOf(totals_obj.toString());
			List<Map<String, Object>> list = query.list();
			jqGrid.setRows(list);
			jqGrid.setRowCount(totals);
		}
		return null;
	}
}
