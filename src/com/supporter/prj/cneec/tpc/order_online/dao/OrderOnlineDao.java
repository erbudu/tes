package com.supporter.prj.cneec.tpc.order_online.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.order_online.entity.OrderOnline;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: OrderOnlineDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-10-30
 * @version: V1.0
 */
@Repository
public class OrderOnlineDao extends MainDaoSupport<OrderOnline, String> {

	/**
	 * 分页查询
	 */
	public List<OrderOnline> findPage(JqGrid jqGrid, OrderOnline orderOnline, String authFilter) {
		if (orderOnline != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = orderOnline.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectName like ? or orderNo like ? or orderName like ? or filingNo like ? or customerName like ? or thirdParty like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 状态过滤
			if (orderOnline.getSwfStatus() != null) {
				jqGrid.addHqlFilter(" swfStatus = ? ", orderOnline.getSwfStatus());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

	/** 以下是选择销售合同上线方法 **/

	@SuppressWarnings("unchecked")
	public List<OrderOnline> getList(Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrieve(hql, parameters);
	}

	public ListPage<OrderOnline> getListPage(ListPage<OrderOnline> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + OrderOnline.class.getName() + " t where 1=1");
		// 状态(默认为完成)
		Integer swfStatus = (Integer) parameters.get("swfStatus");
		if (swfStatus == null || swfStatus != 0) {
			swfStatus = OrderOnline.COMPLETED;
		}
		hql.append(" and t.swfStatus = :swfStatus");
		parameters.put("swfStatus", swfStatus);
		// 项目ID，必要字段
		String projectId = (String) parameters.get("projectId");
		if (StringUtils.isNotBlank(projectId)) {
			hql.append(" and t.projectId = :projectId");
			parameters.put("projectId", projectId);
		} else {
			hql.append(" and 1 <> 1");
		}
		// 其他过滤条件
		String keyword = (String) parameters.get("keyword");
		if (StringUtils.isNotBlank(keyword)) {
			hql.append(" and (t.orderNo like :keyword)");
			parameters.put("keyword", "%" + keyword + "%");
		}
		//hql.append(" and " + authFilter);
		hql.append(" order by t.orderNo");
		return hql.toString();
	}

}
