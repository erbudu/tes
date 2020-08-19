package com.supporter.prj.ppm.contract.pact.review.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReview;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReviewCon;

@Repository
public class ContractPactReviewDao extends MainDaoSupport<ContractPactReview, String> {
	/**
	 * 获取合同及协议评审列表
	 * @param jqGrid 列表
	 * @param report 实体对象
	 * @param user 当前登录用户
	 * @return 合同及协议评审列表
	 */
	public List<ContractPactReview> findPage(JqGrid jqGrid, ContractPactReview review, UserProfile user) {
		String prjId = review.getPrjId();
		// 只显示当前项目对应的记录
		String hql = "prjId = ?";
		jqGrid.addHqlFilter(hql, prjId);
		return this.retrievePage(jqGrid);
	}

	/**
	 * 编号生成规则
	 * @return 评审编号
	 */
	public List<ContractPactReview> generateReviewNo() {
		String hql = "from " + ContractPactReview.class.getName() + " where reviewNo is not null order by creationDate desc";
		List<ContractPactReview> list = this.find(hql);
		return list;
	}

	/**
	 * 获取所有报审单id
	 * @return 报审id集合
	 */
	public List<String> getReportId() {
		String hql = "select reportId from " + ContractPactReview.class.getName() + " where 1=1 group by reportId";
		return this.find(hql);
	}

	/**
	 * 获取某项目下所有审批结果为有条件通过的评审ID
	 * @param prjId 项目id
	 * @return 评审ID集合
	 */
	public List<ContractPactReview> getConPassReview(String prjId) {
		String hql = "from " + ContractPactReview.class.getName() + " where prjId = ? and " + "reviewId in (select reviewId from "
				+ ContractPactReviewCon.class.getName() + " where reviewConStatus = ? )";
		return this.find(hql, prjId, ContractPactReviewCon.ConStatusCodeTable.CONDITIONPASS);
	}

	/**
	 * 获取某项目下所有评审审批结果为通过的报审ID
	 * @param prjId 项目id
	 * @return 报审ID集合
	 */
	public List<String> getReviewPassReport(String prjId) {
		String hql = "select reportId from " + ContractPactReview.class.getName() + " where prjId = ? and " + "reviewId in (select reviewId from "
				+ ContractPactReviewCon.class.getName() + " where reviewConStatus = ? )";
		return this.find(hql, prjId, ContractPactReviewCon.ConStatusCodeTable.PASS);
	}

}
