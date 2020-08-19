package com.supporter.prj.linkworks.oa.authority_apply.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApplyBoard;

/**   
 * @Title: Entity
 * @Description: 功能模块�?
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class AuthorityApplyBoardDao extends MainDaoSupport < AuthorityApplyBoard, String > {
	
//	public AuthorityApplyBoard getContractByIdOrName(String moduleIdOrName) {
//		String hql = "from " + ReportContent.class.getName() + " where materialId = ? or materialName = ?";
//		List < ReportContent > modules = this.find(hql, moduleIdOrName, moduleIdOrName);
//		if (modules == null || modules.size() == 0) return null;
//		
//		if (modules.size() > 1) {
//			EIPService.getLogService().error("找到 " + modules.size() + " 个模块：" + moduleIdOrName);
//		}
//		
//		return modules.get(0);
//	}
	/**
	 * 根据applyId获取所有留言板.
	 * @param applyId
	 * @return
	 */
	public List < AuthorityApplyBoard > getMessageByApplyId(String applyId) {
		String hql = "from " + AuthorityApplyBoard.class.getName() + " where reportId = ? order by authorityApplyTime desc";
		List < AuthorityApplyBoard > list = this.find(hql, applyId);
		
		if (list == null || list.size() == 0) return null;
		
		return list;
	}
}
