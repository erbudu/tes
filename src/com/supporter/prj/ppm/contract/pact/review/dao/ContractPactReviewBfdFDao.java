package com.supporter.prj.ppm.contract.pact.review.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReviewBfdF;

@Repository
public class ContractPactReviewBfdFDao extends MainDaoSupport<ContractPactReviewBfdF, String> {

	/**
	 * 获取待用印资料清单文件列表
	 * @param reviewId-主表主键
	 * @return 资料清单
	 */
	public List<ContractPactReviewBfdF> getUseSealGrid(String reviewId) {
		String hql = "from " + ContractPactReviewBfdF.class.getName() + " where reviewId = ? and isUseSeal = ?";
		List<ContractPactReviewBfdF> list = this.find(hql, reviewId, 1);
		return list;
	}

}
