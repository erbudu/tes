package com.supporter.prj.cneec.tpc.purchase_demand.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptReview;
import com.supporter.prj.cneec.tpc.external_quotation_review.entity.ExternalQuotationReview;
import com.supporter.prj.cneec.tpc.external_quotation_review_dept.entity.ExternalQuotationReviewDept;
import com.supporter.prj.cneec.tpc.purchase_demand.entity.PurchaseDemand;
import com.supporter.prj.cneec.tpc.purchase_demand.util.PurchaseDemandConstant;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: PurchaseDemandDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-09-06
 * @version: V1.0
 */
@Repository
public class PurchaseDemandDao extends MainDaoSupport<PurchaseDemand, String> {

	/**
	 * 分页查询
	 */
	public List<PurchaseDemand> findPage(JqGrid jqGrid, PurchaseDemand purchaseDemand, String authFilter) {
		if (purchaseDemand != null) {
			String keyword = purchaseDemand.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectName like ? or batchNo like ? or purchaseDemandName like ?",
						"%" + keyword + "%",
						"%" + keyword + "%",
						"%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 状态过滤
			if (purchaseDemand.getSwfStatus() != null) {
				jqGrid.addHqlFilter(" swfStatus= ? ", purchaseDemand.getSwfStatus());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

	/** 以下是选择客户采购需求方法 **/

	public ListPage<PurchaseDemand> getListPage(ListPage<PurchaseDemand> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + PurchaseDemand.class.getName() + " t where 1=1");
		// 状态(默认为完成)
		Integer swfStatus = (Integer) parameters.get("swfStatus");
		if (swfStatus == null || swfStatus != 0) {
			swfStatus = PurchaseDemand.COMPLETED;
		}
		hql.append(" and t.swfStatus = :swfStatus");
		parameters.put("swfStatus", swfStatus);
		// 项目ID，必要字段
		String projectId = (String) parameters.get("projectId");
		if (StringUtils.isNotBlank(projectId)) {
			hql.append(" and t.projectId = :projectId");
		} else {
			hql.append(" and 1 <> 1");
		}
		// 其他过滤条件
		String keyword = (String) parameters.get("keyword");
		if (StringUtils.isNotBlank(keyword)) {
			hql.append(" and (t.batchNo like :keyword or t.projectName like :keyword )");
			parameters.put("keyword", "%" + keyword + "%");
		}
		// 预估金额
		double estimatedAmount1 = CommonUtil.parseDouble(String.valueOf(parameters.get("estimatedAmount1")), 0);
		double estimatedAmount2 = CommonUtil.parseDouble(String.valueOf(parameters.get("estimatedAmount2")), 0);
		if (estimatedAmount1 != 0 || estimatedAmount2 != 0) {
			hql.append(" and t.estimatedAmount between :estimatedAmount1 and :estimatedAmount2");
			parameters.put("estimatedAmount1", estimatedAmount1);
			parameters.put("estimatedAmount2", estimatedAmount2);
		}
		// 币别
		if (StringUtils.isNotBlank((String) parameters.get("currencyId"))) {
			hql.append(" and t.currencyId = :currencyId");
		}
		// 为特定模块选择过滤
		if (parameters.containsKey("selectTo")) {
			String selectTo = (String) parameters.get("selectTo");
			hql.append(getSelectToHql(selectTo, parameters));
		}
		hql.append(" order by t.batchNo, t.projectName");
		return hql.toString();
	}

	/**
	 * 为对外报价评审/合同签约前评审等做过滤
	 * @param hql
	 * @param selectTo
	 * @return
	 */
	private String getSelectToHql(String selectTo, Map<String, Object> parameters) {
		StringBuffer filter = new StringBuffer();
		if (selectTo.equals(PurchaseDemandConstant.CONTRACT_SIGN_REVIEW_DEPT)) {// 合同事业部评审
			filter.append(" and (");
			// 不评审直接通过
			filter.append(" t.reviewClassification in ('" + PurchaseDemandConstant.REVIEW_AGENT_NO + "', '" + PurchaseDemandConstant.REVIEW_SELF_SUPPORT_XIAO_NO + "')");
			// 需要事业部评审查询对外报价事业部评审表完成
			filter.append(" or ((t.reviewClassification in ('" + PurchaseDemandConstant.REVIEW_AGENT_YES + "', '" + PurchaseDemandConstant.REVIEW_SELF_SUPPORT_XIAO_YES + "'))");
			filter.append(" and t.demandId in ( select e1.demandId from " + ExternalQuotationReviewDept.class.getName() + " e1 where e1.swfStatus='" + ExternalQuotationReviewDept.COMPLETED + "'))");
			// 需要公司评审查询对外报价公司评审表完成
			filter.append(" or ((t.reviewClassification in ('" + PurchaseDemandConstant.REVIEW_SELF_SUPPORT_DA + "', '" + PurchaseDemandConstant.REVIEW_SELF_SUPPORT_SUPER + "'))");
			filter.append(" and t.demandId in ( select e2.demandId from " + ExternalQuotationReview.class.getName() + " e2 where e2.swfStatus='" + ExternalQuotationReview.COMPLETED + "'))");
			filter.append(" )");
		} else if (selectTo.equals(PurchaseDemandConstant.CONTRACT_SIGN_REVIEW)) {// 合同签约公司评审
			// 需要合同签约前事业部评审完成
			filter.append(" and t.demandId in ( select c.demandId from " + ContractSignDeptReview.class.getName() + " t where c.swfStatus='" + ContractSignDeptReview.COMPLETED + "')");
		}
		return filter.toString();
	}

}
