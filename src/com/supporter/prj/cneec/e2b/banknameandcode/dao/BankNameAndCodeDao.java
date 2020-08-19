package com.supporter.prj.cneec.e2b.banknameandcode.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.e2b.banknameandcode.entity.BankNameAndCode;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: BankNameAndCodeDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2018-11-08
 * @version: V1.0
 */
@Repository
public class BankNameAndCodeDao extends MainDaoSupport<BankNameAndCode, String> {

	/**
	 * 分页查询
	 */
	public List<BankNameAndCode> findPage(JqGrid jqGrid, BankNameAndCode bankNameAndCode, String authFilter) {
		if (bankNameAndCode != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = bankNameAndCode.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" bankName like ? or bankCode like ?",
									"%" + keyword + "%",
									"%" + keyword + "%");
			}
			// 排序
			jqGrid.addSortPropertyAsc("displayOrder");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

	public ListPage<BankNameAndCode> getListPage(ListPage<BankNameAndCode> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + BankNameAndCode.class.getName() + " t where 1=1");
		// 其他过滤条件
		String keyword = (String) parameters.get("keyword");
		if (StringUtils.isNotBlank(keyword)) {
			hql.append(" and (t.bankName like :keyword or t.bankCode like :keyword )");
			parameters.put("keyword", "%" + keyword + "%");
		}
		hql.append(" order by t.displayOrder asc");
		return hql.toString();
	}
}
