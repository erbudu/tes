package com.supporter.prj.linkworks.oa.signed_report.dao;



import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReportContent;

@Repository
public class SignedReportContentDao extends MainDaoSupport < SignedReportContent , String > {
	
	/**
	 * 根据信息动态Id获取编辑人员列表
	 * 
	 * @param signedReportId
	 *            信息动态Id
	 * @return List <SignedReportContent>
	 */
	public List<SignedReportContent> getContentBySignedReportId(String signedReportId) {
		String hql = "from " + SignedReportContent.class.getName() + " where signedReportId = ? ";
		List<SignedReportContent> editers = this.find(hql, signedReportId);
		if (editers == null || editers.size() == 0)
			return null;
		return editers;
	}
	
}
