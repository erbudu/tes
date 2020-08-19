package com.supporter.prj.cneec.tpc.purchase_demand.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.purchase_demand.entity.PurchaseDemandDetail;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: PurchaseDemandDetailDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-09-06
 * @version: V1.0
 */
@Repository
public class PurchaseDemandDetailDao extends MainDaoSupport<PurchaseDemandDetail, String> {

	/**
	 * 分页查询
	 */
	public List<PurchaseDemandDetail> findPage(JqGrid jqGrid, String demandId, String recordId) {
		// 客户明细须通过主表ID和客户表ID获取
		if (StringUtils.isNotBlank(demandId) && StringUtils.isNotBlank(recordId)) {
			jqGrid.addHqlFilter(" demandId = ? ", demandId);
			jqGrid.addHqlFilter(" recordId = ? ", recordId);
		} else {
			return null;
		}
		return this.retrievePage(jqGrid);
	}

	/**
	 * 根据客户ID获取所有客户从表集合
	 * @param demandId
	 * @return
	 */
	public List<PurchaseDemandDetail> getDetailListByRecordId(String recordId) {
		String hql = "from " + PurchaseDemandDetail.class.getName() + " where recordId = ? ";
		List<PurchaseDemandDetail> list = this.find(hql, recordId);
		if (list == null || list.size() == 0) return null;
		return list;
	}

	/** 以下是选择客户采购订单方法 **/
	
	public ListPage<PurchaseDemandDetail> getListPage(ListPage<PurchaseDemandDetail> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<PurchaseDemandDetail> getList(Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrieve(hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + PurchaseDemandDetail.class.getName() + " t where 1=1");
		// 客户采购单ID，必要字段
		String recordId = (String) parameters.get("recordId");
		String recordIds = (String) parameters.get("recordIds");
		if (StringUtils.isNotBlank(recordId)) {
			hql.append(" and t.recordId = :recordId");
		} else if (StringUtils.isNotBlank(recordIds)) {
			recordIds = recordIds.replace(",", "','");
			hql.append(" and t.recordId in ('" + recordIds + "')");
		} else {
			hql.append(" and 1 <> 1");
		}
		// 交货期
		String dateFrom = (String) parameters.get("dateFrom");
		String dateTo = (String) parameters.get("dateTo");
		if (StringUtils.isNotBlank(dateFrom) || StringUtils.isNotBlank(dateTo)) {
			hql.append(" and t.deliveryTime between :dateFrom and :dateTo");
			parameters.put("dateFrom", dateFrom);
			parameters.put("dateTo", dateTo);
		}
		hql.append(" order by t.recordId");
		return hql.toString();
	}

}
