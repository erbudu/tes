package com.supporter.prj.cneec.tpc.control.dao;

import java.text.ParseException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.custom.entity.Custom;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;

/**
 * 
 * @Title: TpcCustomDao
 * @Description: 客户DAO
 * @author: yanweichao
 * @date: 2017-9-18
 * @version: V1.0
 */
@Repository("tpcCustomDao")
public class TpcCustomDao extends MainDaoSupport<Custom, String> {

	public ListPage<Custom> getListPage(ListPage<Custom> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + Custom.class.getName() + " t where 1=1");
		// 有效记录（默认选择有效记录）
		String customControlStatusCode = (String) parameters.get("customControlStatusCode");
		if (StringUtils.isBlank(customControlStatusCode)) {
			customControlStatusCode = Custom.EFFECTIV;
		}
		hql.append(" and t.customControlStatusCode = :customControlStatusCode");
		parameters.put("customControlStatusCode", customControlStatusCode);
		// 其他过滤条件
		String keyword = (String) parameters.get("keyword");
		if (StringUtils.isNotBlank(keyword)) {
			hql.append(" and (t.customerNo like :keyword or t.customerName like :keyword )");
			parameters.put("keyword", "%" + keyword + "%");
		}
		// 是否国外
		if (StringUtils.isNotBlank((String) parameters.get("isForeign"))) {
			hql.append(" and t.isForeign = :isForeign");
		}
		// 所属区域
		if (StringUtils.isNotBlank((String) parameters.get("areaCode"))) {
			hql.append(" and t.areaCode = :areaCode");
		}
		// 国家/省份
		if (StringUtils.isNotBlank((String) parameters.get("areaItemCode"))) {
			hql.append(" and t.areaItemCode = :areaItemCode");
		}
		//hql.append(" and " + authFilter);
		hql.append(" order by t.customerNo, t.customerName");
		return hql.toString();
	}

}
