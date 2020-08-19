package com.supporter.prj.linkworks.oa.signed_report.dao;



import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReportMessageBoard;


/**
 * @Title: DAO
 * @Description: 功能模块表
 * @version V1.0
 * 
 */
@Repository
public class SignedReportMessageBoardDao extends MainDaoSupport < SignedReportMessageBoard , String > {
	
	/**
	 * 根据签报Id获取留言板信息
	 * 
	 * @param signedReportId
	 *            信息动态Id
	 * @return List <SignedReportContent>
	 */
	public List<SignedReportMessageBoard> getMessageBySignedReportId(String reportId) {
		String hql = "from " + SignedReportMessageBoard.class.getName() + " where reportId = ? "+" order by messDate " ;
		List<SignedReportMessageBoard> editers = this.find(hql, reportId);
		if (editers == null || editers.size() == 0)
			return null;
		return editers;
	}
	
}
