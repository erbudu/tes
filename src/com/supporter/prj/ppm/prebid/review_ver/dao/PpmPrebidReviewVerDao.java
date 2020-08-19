package com.supporter.prj.ppm.prebid.review_ver.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReportBfdF;
import com.supporter.prj.ppm.prebid.review_ver.entity.PpmPrebidReviewVer;

@Repository
public class PpmPrebidReviewVerDao extends MainDaoSupport<PpmPrebidReviewVer, String> {
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param ppmPrebidReview
	 * @return
	 */
	public List<PpmPrebidReviewVer> findPage(JqGrid jqGrid, PpmPrebidReviewVer ppmPrebidReviewVer) {
		
		return this.retrievePage(jqGrid);
	}
	/**
	 * 获取所有评审验证的评审id集合
	 * @return 
	 */
	public List<String> getReviewId() {
		String hql = "select prebidReviewId from " + PpmPrebidReviewVer.class.getName() + " where 1=1 group by prebidReviewId";
		return this.find(hql);
	}
	/**
	 * 获取本项目需要验证的最大的验证编号
	 * @param prjId
	 * @return
	 */
	public String getMaxReviewVerId(String prjId) {
		String hql = "select reviewVerId from "
				+ PpmPrebidReviewVer.class.getName()
				+ " where prjId = ? ORDER BY reviewVerId DESC ";
		List<PpmPrebidReviewVer> list = this.find(hql, prjId);
		String reviewVerId = list.get(0).getReviewVerId();
		return reviewVerId;
	}
}