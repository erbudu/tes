package com.supporter.prj.ppm.prebid.review.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReviewCon;

@Repository
public class PpmPrebidReviewConDao extends MainDaoSupport<PpmPrebidReviewCon, String> {
	public List<String> getPrebidReviewIdByStutas() {
		String hql = "select prebidReviewId from "+PpmPrebidReviewCon.class.getName() + " where rvConStatus = ?";
		List<String> list =this.find(hql, PpmPrebidReviewCon.COMPLETED);
		if(list != null && list.size()>0) {
			return list;
		}else {
			return null;
		}
	}
}
