package com.supporter.prj.cneec.tpc.purchase_demand.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignInfor;
import com.supporter.prj.cneec.tpc.purchase_demand.entity.PurchaseDemandCustom;
import com.supporter.prj.cneec.tpc.purchase_demand.entity.PurchaseDemandDetail;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: PurchaseDemandCustomDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-09-09
 * @version: V1.0
 */
@Repository
public class PurchaseDemandCustomDao extends MainDaoSupport<PurchaseDemandCustom, String> {

	/**
	 * 分页查询
	 */
	public List<PurchaseDemandCustom> findPage(JqGrid jqGrid, PurchaseDemandCustom purchaseDemandCustom) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 根据主表ID获取所有客户集合
	 * @param demandId
	 * @return
	 */
	public List<PurchaseDemandCustom> getCustomListByDemandId(String demandId) {
		String hql = "from " + PurchaseDemandCustom.class.getName() + " where demandId = ? order by recordId desc";
		List<PurchaseDemandCustom> list = this.find(hql, demandId);
		if (list == null || list.size() == 0) return null;
		return list;
	}

	/** 以下是选择客户采购订单方法 **/
	
	public ListPage<PurchaseDemandCustom> getListPage(ListPage<PurchaseDemandCustom> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<PurchaseDemandCustom> getList(Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrieve(hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + PurchaseDemandCustom.class.getName() + " t where 1=1");
		// 需求单ID，必要字段
		String demandId = (String) parameters.get("demandId");
		String demandIds = (String) parameters.get("demandIds");
		if (StringUtils.isNotBlank(demandId)) {
			hql.append(" and t.demandId = :demandId");
		} else if (StringUtils.isNotBlank(demandIds)) {
			demandIds = demandIds.replace(",", "','");
			hql.append(" and t.demandId in ('" + demandIds + "')");
		} else {
			hql.append(" and 1 <> 1");
		}
		// 排除订单
		String _recordIds = (String) parameters.get("_recordIds");
		if (StringUtils.isNotBlank(_recordIds)) {
			hql.append(" and t.recordId not in (select infor.recordId from " + ContractSignInfor.class.getName() + " infor where 1=1 and infor.recordId is not null)");
		}
		// 查询条件
		String keyword = (String) parameters.get("keyword");
		if (StringUtils.isNotBlank(keyword)) {
			// 客户名称
			hql.append(" and (t.customerName like :keyword ");
			// 货物/服务名称
			hql.append(" or t.recordId in (select detail.recordId from " + PurchaseDemandDetail.class.getName() + " detail where detail.itemName like :keyword and detail.recordId is not null))");
			parameters.put("keyword", "%" + keyword + "%");
		}
		hql.append(" order by t.customerName,t.recordId");
		return hql.toString();
	}

}
