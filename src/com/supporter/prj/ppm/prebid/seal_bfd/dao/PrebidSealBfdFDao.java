package com.supporter.prj.ppm.prebid.seal_bfd.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReportBfdF;
import com.supporter.prj.ppm.prebid.seal_bfd.entity.PpmPrebidSealBfdF;

@Repository
public class PrebidSealBfdFDao extends MainDaoSupport<PpmPrebidSealBfdF, String> {
	/**
	 * 获取同项目下的最大的DisplayOrder
	 * @param reviewNo
	 * @return
	 */
	public int getMaxDisplayOrder(String prbidReportId, String bfdId) {
		String hql = "select count(*) from " + PpmPrebidReportBfdF.class.getName() 
				+ " where prbidReportId = ? and bfdId = ?";
		List list = this.find(hql, prbidReportId,bfdId);
		Number num = (Number) list.get(0);
	     return num.intValue(); 
	}
}
