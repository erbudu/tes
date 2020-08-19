package com.supporter.prj.cneec.tpc.derivative_contract.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.derivative_contract.entity.DerivativeContract;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: DerivativeContractDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2018-05-21
 * @version: V1.0
 */
@Repository
public class DerivativeContractDao extends MainDaoSupport<DerivativeContract, String> {

	/**
	 * 分页查询
	 */
	public List<DerivativeContract> findPage(JqGrid jqGrid, DerivativeContract derivativeContract, String authFilter) {
		if (derivativeContract != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = derivativeContract.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" derivativeNo like ? or contractNo like ? or contractName like ? or paymentTypeDesc like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 状态过滤
			if (derivativeContract.getSwfStatus() != null) {
				jqGrid.addHqlFilter(" swfStatus = ? ", derivativeContract.getSwfStatus());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}
	
	/** 以下是选择合同方法 **/

	public ListPage<DerivativeContract> getListPage(ListPage<DerivativeContract> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return retrievePage(listPage, hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + DerivativeContract.class.getName() + " t where 1=1");
		// 项目ID，必要字段
		String projectId = (String) parameters.get("projectId");
		if (StringUtils.isNotBlank(projectId)) {
			hql.append(" and t.projectId = :projectId");
			parameters.put("projectId", projectId);
		} else {
			hql.append(" and 1 <> 1");
		}
		String keyword = (String) parameters.get("keyword");
		if (StringUtils.isNotBlank(keyword)) {
			hql.append(" and (t.derivativeNo like :keyword)");
			parameters.put("keyword", "%" + keyword + "%");
		}
		hql.append(" order by t.derivativeNo");
		return hql.toString();
	}

}
