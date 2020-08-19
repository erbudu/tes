package com.supporter.prj.cneec.tpc.contract_sign_review_dept.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignReview;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptInfor;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptReview;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.util.ContractSignReviewUtil;
import com.supporter.prj.cneec.tpc.purchase_demand.util.PurchaseDemandConstant;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: ContractSignDeptReviewDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2018-03-21
 * @version: V1.0
 */
@Repository
public class ContractSignDeptReviewDao extends MainDaoSupport<ContractSignDeptReview, String> {

	/**
	 * 分页查询
	 */
	public List<ContractSignDeptReview> findPage(JqGrid jqGrid, ContractSignDeptReview contractSignDeptReview, String authFilter) {
		if (contractSignDeptReview != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = contractSignDeptReview.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectName like ? or reviewNo like ? or reviewContent like ? or reviewConclusion like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 状态过滤
			if (contractSignDeptReview.getSwfStatus() != null) {
				jqGrid.addHqlFilter(" swfStatus = ? ", contractSignDeptReview.getSwfStatus());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

	/** 以下是选择合同签约前评审单方法 **/

	@SuppressWarnings("unchecked")
	public List<ContractSignDeptReview> getList(Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrieve(hql, parameters);
	}

	public ListPage<ContractSignDeptReview> getListPage(ListPage<ContractSignDeptReview> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + ContractSignDeptReview.class.getName() + " t where 1=1");
		// 状态(默认为完成)
		Integer swfStatus = (Integer) parameters.get("swfStatus");
		if (swfStatus == null || swfStatus != 0) {
			swfStatus = ContractSignDeptReview.COMPLETED;
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
			hql.append(" and (");
			hql.append(" (t.reviewNo like :keyword or t.projectName like :keyword )");
			// 评审内容概要
			hql.append(" or t.reviewContent like :keyword");
			// 客户采购需求单名称
			hql.append(" or t.purchaseDemandName like :keyword");
			// 客户/合同对方
			hql.append(" or t.signId in (select i.signId from " + ContractSignDeptInfor.class.getName() + " i where i.contractParty like :keyword and i.signId is not null)");
			hql.append(" )");
			parameters.put("keyword", "%" + keyword + "%");
		}
		// 为特定模块选择过滤
		if (parameters.containsKey("selectTo")) {
			String selectTo = (String) parameters.get("selectTo");
			hql.append(getSelectToHql(selectTo, parameters));
		}
		//hql.append(" and " + authFilter);
		hql.append(" order by t.reviewNo");
		return hql.toString();
	}

	/**
	 * 
	 * @param selectTo
	 * @return
	 */
	private String getSelectToHql(String selectTo, Map<String, Object> parameters) {
		StringBuffer filter = new StringBuffer();
		if (selectTo.equals(PurchaseDemandConstant.CONTRACT_SIGN_REVIEW)) {
			// 过滤未公司评审过的事业部评审单(可以公司评审的)
			filter.append(" and t.signId not in (select t1.signId from " + ContractSignReview.class.getName() + " t1)");
			filter.append(" and (t.reviewClassification = :reviewClassification1 or t.reviewClassification = :reviewClassification2)");
			parameters.put("reviewClassification1", ContractSignReviewUtil.REVIEW_SELF_SUPPORT_DA);
			parameters.put("reviewClassification2", ContractSignReviewUtil.REVIEW_SELF_SUPPORT_SUPER);
		} else {
			// 评审类别(即销售合同/采购合同，用印/备案业务类型选择)，必要字段
			int reviewType = CommonUtil.parseInt(String.valueOf(parameters.get("reviewType")), 0);
			if (reviewType != 0) {
				filter.append(" and (t.reviewType = :reviewType or t.reviewType = :subContractType)");
				parameters.put("reviewType", reviewType);
				parameters.put("subContractType", ContractSignReviewUtil.REVIEW_TYPE_SUBCONTRACT);
			} else {
				filter.append(" and 1 <> 1");
			}
		}
		return filter.toString();
	}

}
