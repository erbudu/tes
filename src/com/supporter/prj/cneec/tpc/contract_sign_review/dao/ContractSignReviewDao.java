package com.supporter.prj.cneec.tpc.contract_sign_review.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignInfor;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignReview;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.util.ContractSignReviewUtil;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: ContractSignReviewDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-09-28
 * @version: V1.0
 */
@Repository
public class ContractSignReviewDao extends MainDaoSupport<ContractSignReview, String> {

	/**
	 * 分页查询
	 */
	public List<ContractSignReview> findPage(JqGrid jqGrid, ContractSignReview contractSignReview, String authFilter) {
		if (contractSignReview != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = contractSignReview.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectName like ? or reviewNo like ? or reviewContent like ? or reviewConclusion like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 状态过滤
			if (contractSignReview.getSwfStatus() != null) {
				jqGrid.addHqlFilter(" swfStatus = ? ", contractSignReview.getSwfStatus());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

	/** 以下是选择合同签约前评审单方法 **/

	@SuppressWarnings("unchecked")
	public List<ContractSignReview> getList(Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrieve(hql, parameters);
	}

	public ListPage<ContractSignReview> getListPage(ListPage<ContractSignReview> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + ContractSignReview.class.getName() + " t where 1=1");
		// 状态(默认为完成)
		Integer swfStatus = (Integer) parameters.get("swfStatus");
		if (swfStatus == null || swfStatus != 0) {
			swfStatus = ContractSignReview.COMPLETED;
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
		// 评审类别(即销售合同/采购合同，用印/备案业务类型选择)，必要字段
		int reviewType = CommonUtil.parseInt(String.valueOf(parameters.get("reviewType")), 0);
		if (reviewType != 0) {
			hql.append(" and (t.reviewType = :reviewType or t.reviewType = :subContractType)");
			parameters.put("reviewType", reviewType);
			parameters.put("subContractType", ContractSignReviewUtil.REVIEW_TYPE_SUBCONTRACT);
		} else {
			hql.append(" and 1 <> 1");
		}
		// 其他过滤条件
		String keyword = (String) parameters.get("keyword");
		if (StringUtils.isNotBlank(keyword)) {
			hql.append(" and (t.reviewNo like :keyword or t.projectName like :keyword )");
			// 客户/合同对方
			hql.append(" or t.signId in (select i.signId from " + ContractSignInfor.class.getName() + " i where i.contractParty like :keyword and i.signId is not null))");
			parameters.put("keyword", "%" + keyword + "%");
		}
		// 评审内容概要
		String reviewContent = (String) parameters.get("reviewContent");
		if (StringUtils.isNotBlank(reviewContent)) {
			hql.append(" and t.reviewContent like :reviewContent");
			parameters.put("reviewContent", "%" + reviewContent + "%");
		}
		// 评审内容概要
		String purchaseDemandName = (String) parameters.get("purchaseDemandName");
		if (StringUtils.isNotBlank(purchaseDemandName)) {
			hql.append(" and t.purchaseDemandName like :purchaseDemandName");
			parameters.put("purchaseDemandName", "%" + purchaseDemandName + "%");
		}
		//hql.append(" and " + authFilter);
		hql.append(" order by t.reviewNo");
		return hql.toString();
	}

}
