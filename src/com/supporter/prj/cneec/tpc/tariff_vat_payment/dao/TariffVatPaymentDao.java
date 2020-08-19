package com.supporter.prj.cneec.tpc.tariff_vat_payment.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.tariff_vat_payment.entity.TariffVatPayment;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: TariffVatPaymentDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-12-20
 * @version: V1.0
 */
@Repository
public class TariffVatPaymentDao extends MainDaoSupport<TariffVatPayment, String> {

	/**
	 * 分页查询
	 */
	public List<TariffVatPayment> findPage(JqGrid jqGrid, TariffVatPayment tariffVatPayment, String authFilter) {
		if (tariffVatPayment != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = tariffVatPayment.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectName like ? or collectionUnit like ? or depositBank like ? or bankAccount like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 状态过滤
			if (tariffVatPayment.getSwfStatus() != null) {
				jqGrid.addHqlFilter(" swfStatus = ? ", tariffVatPayment.getSwfStatus());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
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
	public List<TariffVatPayment> queryBy(String properName, Object propValue, Boolean likeSearch, String orderByName, Boolean sort) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(TariffVatPayment.class, properName, propValue, likeSearch, orderByName, sort);
		return (List<TariffVatPayment>) getHibernateTemplate().findByCriteria(dc);
	}
	
	/**
	 * 获取汇款用途
	 * @return
	 */
	public List<String> getRemittancePurpose(){
		String hql = " select t.remittancePurpose from " + TariffVatPayment.class.getName() + " t where t.remittancePurpose is not null group by t.remittancePurpose ";
		List<String> list = this.retrieve(hql);
		return list;
	}

	/**
	 * 获取非合同付款单信息
	 * @param projectId	
	 * @param paymentIds 
	 * @return
	 */
	public List<TariffVatPayment> getComplete(String projectId, List<String> paymentIds) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer("from " + TariffVatPayment.class.getName() + 
				" where paymentNo != Null and projectId = :projectId");
//		StringBuffer hql = new StringBuffer("from " + TariffVatPayment.class.getName() + 
//				" where swfStatus = :swfStatus and projectId = :projectId");
//		param.put("swfStatus", TariffVatPayment.COMPLETED);
		param.put("projectId", projectId);
		if(paymentIds.size() > 0) {
			hql.append(" and paymentId not in (:paymentIds)");
			param.put("paymentIds", paymentIds.toArray());
		}
		hql.append(" order by paymentNo desc");
		return this.find(hql.toString(), param);
	}

}
