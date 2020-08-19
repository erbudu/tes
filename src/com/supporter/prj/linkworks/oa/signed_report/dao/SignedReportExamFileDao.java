package com.supporter.prj.linkworks.oa.signed_report.dao;



import java.util.List;

import org.springframework.stereotype.Repository;


import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReport;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReportExamFile;
import com.supporter.prj.eip_service.security.entity.UserProfile;


/**
 * @Title: DAO
 * @Description: 功能模块表
 * @version V1.0
 * 
 */
@Repository
public class SignedReportExamFileDao extends MainDaoSupport < SignedReportExamFile , String > {
	

	/**
	 * 获得某个人在某个审批模块中上传的附件
	 * @param impl
	 * @param uf
	 * @return
	 */
	public List<SignedReportExamFile> getFileList(UserProfile user,String signedReportId){
		String hql = "from " + SignedReportExamFile.class.getName()
		+ " where createdById = ? and relatedEntityName = '"+ SignedReport.class.getName() +"' relatedEntityId = '"+ signedReportId +"' order by createdDate desc";
		List<SignedReportExamFile> list = this.find(hql, user.getPersonId());
		return list;
	}
	
	/**
	 * 获得某个人在某个审批模块中上传的附件
	 * @param impl
	 * @param uf
	 * @return
	 */
	public String getFileIdsList(UserProfile user,String signedReportId){
		String hql = "from " + SignedReportExamFile.class.getName()
		+ " where createdById = '"+user.getPersonId()+"' and relatedEntityName = '"+ SignedReport.class.getName() +"' and  relatedEntityId = '"+ signedReportId +"' order by createdDate desc";
		List<SignedReportExamFile> list = this.find(hql);
		StringBuffer fileIds = new StringBuffer();
		for(SignedReportExamFile examFile : list){
			fileIds.append(examFile.getFileId());
		}
		return fileIds.toString();
	}
	
}
