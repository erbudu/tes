package com.supporter.prj.cneec.tpc.receive_credit_letter.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.cneec.tpc.receive_credit_letter.entity.ReceiveCreditLetter;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-11-22 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class ReceiveCreditLetterDao extends MainDaoSupport < ReceiveCreditLetter, String > {
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param contractIds 模块ids
	 * @return
	 */
	public List<ReceiveCreditLetter> findPage(UserProfile user, JqGrid jqGrid, ReceiveCreditLetter report) {
		if(report != null){
			//查询
			String projectName = report.getProjectName();
			if(StringUtils.isNotBlank(projectName)){
				jqGrid.addHqlFilter(
						"projectName like ? or contractNo like ? or creditLetterNo like ?", 
						"%" + projectName + "%","%" + projectName + "%","%" + projectName + "%");
			}
			jqGrid.addSortPropertyDesc("receiveCreditLetterId");
		}
		//日志
//		EIPService.getLogService(LogConstant.INFO_TYPE_DEBUG).info("hql:" + hql.toString() + "\t params:" + params+"\t authHql:"+authHql);
		return this.retrievePage(jqGrid);
	}

	/**
	 * 获取已完成的记录
	 * @param projectId
	 * @param contractdId
	 * @return
	 */
	public List<ReceiveCreditLetter> getCompleteList(String projectId, String contractId) {
		StringBuffer hql = new StringBuffer("from " + ReceiveCreditLetter.class.getName() + " t where 1=1");
		hql.append("  and swfStatus = ? ");
		hql.append("  and projectId = ? ");
		hql.append("  and contractId = ? ");
		List<ReceiveCreditLetter> list = this.find(hql.toString(), ReceiveCreditLetter.COMPLETE, projectId, contractId);
		return list;
	}

}
