package com.supporter.prj.cneec.tpc.contract_online_prepare.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_online_prepare.entity.ContractOnlinePrepare;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: ContractOnlinePrepareDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2018-05-15
 * @version: V1.0
 */
@Repository
public class ContractOnlinePrepareDao extends MainDaoSupport<ContractOnlinePrepare, String> {

	/**
	 * 分页查询
	 */
	public List<ContractOnlinePrepare> findPage(JqGrid jqGrid, Map<String, Object> parameters, String authFilter) {
		if (parameters != null && !parameters.isEmpty()) {
			// 列表页面搜索输入框可查询字段
			if (parameters.containsKey("keyword")) {
				String keyword = (String) parameters.get("keyword");
				if (StringUtils.isNotBlank(keyword)) {
					jqGrid.addHqlFilter(" signReviewNo like ? or contractParty like ? or reviewNo like ?",
										"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
				}
			}

			// 数据过滤
			if (parameters.containsKey("stemFrom")) {
				String stemFrom = (String) parameters.get("stemFrom");
				// 合同签约评审：事业部评审、公司评审
				if (stemFrom.equals(ContractOnlinePrepare.STEM_FROM_REVIEW)) {
					jqGrid.addHqlFilter(" stemFrom = ? or stemFrom = ?", ContractOnlinePrepare.STEM_FROM_DEPT_REVIEW, ContractOnlinePrepare.STEM_FROM_REVIEW);
				} else {
					jqGrid.addHqlFilter(" stemFrom = ? ", stemFrom);
				}
			} else {
				jqGrid.addHqlFilter(" 1 <> 1 ");
			}
			/* 以下是更多过滤条件 */
			if (parameters.containsKey("sealStatus")) {
				String sealStatus = (String) parameters.get("sealStatus");
				Integer status = Integer.parseInt(sealStatus);
				if (status != null) {
					jqGrid.addHqlFilter(" sealStatus = ? ", status);
				}
			}
			if (parameters.containsKey("filingStatus")) {
				String filingStatus = (String) parameters.get("filingStatus");
				Integer status = Integer.parseInt(filingStatus);
				if (status != null) {
					jqGrid.addHqlFilter(" filingStatus = ? ", status);
				}
			}
			if (parameters.containsKey("onlineStatus")) {
				String onlineStatus = (String) parameters.get("onlineStatus");
				Integer status = Integer.parseInt(onlineStatus);
				if (status != null) {
					jqGrid.addHqlFilter(" onlineStatus = ? ", status);
				}
			}
			if (parameters.containsKey("inforType")) {
				String inforType = (String) parameters.get("inforType");
				Integer infor = Integer.parseInt(inforType);
				if (infor != null) {
					jqGrid.addHqlFilter(" inforType = ? ", infor);
				}
			}
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

	/**
	 * 过滤查询数据
	 * @param params
	 * @param likeSearhNames
	 * @param orders
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ContractOnlinePrepare> queryByParam(Map<String, Object> params, List<String> likeSearhNames, Map<String, Boolean> orders) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(ContractOnlinePrepare.class, params, likeSearhNames, orders);
		return (List<ContractOnlinePrepare>) getHibernateTemplate().findByCriteria(dc);
	}

	/**
	 * 条件过滤更新数据
	 * @param params
	 * @param orders
	 * @return
	 */
	public boolean updateByParam(Map<String, Object> values, Map<String, Object> filterMap) {
		if (values == null || values.isEmpty() || filterMap == null || filterMap.isEmpty()) {
			return false;
		}
		// 定义hql
		StringBuilder hql = new StringBuilder("update " + ContractOnlinePrepare.class.getName() + " set ");
		// 修改属性
		boolean isFirst = true;
		for (Map.Entry<String, Object> e : values.entrySet()) {
			if (isFirst) {
				isFirst = false;
			} else {
				hql.append(", ");
			}
			hql.append((String) e.getKey()).append(" = :").append((String) e.getKey());
		}
		
		// 定义修改或变更值
		Map<String, Object> dataParams = new LinkedHashMap<String, Object>();
		dataParams.putAll(values);
		// 添加过滤条件
		String filter = "";
		for (Map.Entry<String, Object> e : filterMap.entrySet()) {
			if (filter.length() > 0) filter += " and ";
			if (e.getValue() == null || (e.getValue() instanceof String && StringUtils.isBlank((String) e.getValue()))) {
				// 字段值是空的字段(null或"")
				return false;
			} else if (e.getValue().getClass().isArray()) {
				// 属性值可以是数组的字段
				String key = (String) e.getKey();
				filter += "(";
				Object[] vals = (Object[]) e.getValue();
				for (int i = 0; i < vals.length; i++) {
					if (i != 0) {
						filter += " or ";
					}
					filter += key + " = :" + key + i;
					dataParams.put(key + i, vals[i]);
				}
				filter += ")";
			} else {
				// 属性值是单个值的字段
				String key = (String) e.getKey();
				filter += key + " = :" + key;
				dataParams.put(key, e.getValue());
			}
		}
		hql.append(" where ").append(filter);
		return execUpdate(hql.toString(), dataParams) > 0;
	}

}
