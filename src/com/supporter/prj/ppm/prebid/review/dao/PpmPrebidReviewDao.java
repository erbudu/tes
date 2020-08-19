package com.supporter.prj.ppm.prebid.review.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReviewCon;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReview;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReviewCon;

@Repository
public class PpmPrebidReviewDao extends MainDaoSupport<PpmPrebidReview, String> {
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param ppmPrebidReview
	 * @return
	 */
	public List<PpmPrebidReview> findPage(JqGrid jqGrid, PpmPrebidReview ppmPrebidReview) {
		
		return this.retrievePage(jqGrid);
	}
	/**
	 * 获取同项目下的最大的reviewNo
	 * @param reviewNo
	 * @return
	 */
	public String getMaxReviewNo(String reviewNo) {
		String hql = "select max(reviewNo) from " + PpmPrebidReview.class.getName() 
				+ " where reviewNo like ? ";
		List<String> list = this.find(hql, reviewNo+"%");
		if(list != null && list.size()>0) {
			return (String)list.get(0);
		}else {
			return "";
		}
	}
	/**
	 * 获取某项目下所有审批结果为有条件通过的评审ID
	 * @param prjId 项目id
	 * @return 评审ID集合
	 */
	public List<PpmPrebidReview> getConPassReview(String prjId) {
		String hql = "from " + PpmPrebidReview.class.getName() + " where prjId = ? and " + "prebidReviewId in (select prebidReviewId from "
				+ PpmPrebidReviewCon.class.getName() + " where rvConStatus = ? )";
		return this.find(hql, prjId, ContractPactReviewCon.ConStatusCodeTable.CONDITIONPASS);
	}
}
