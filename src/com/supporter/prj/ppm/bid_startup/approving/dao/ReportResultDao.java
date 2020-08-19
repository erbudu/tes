/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.approving.dao;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.ppm.bid_startup.approving.entity.ReportResultEntity;

/**
 *<p>Title: ReportResultDao</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月3日
 * 
 */
@Repository
public class ReportResultDao extends MainDaoSupport<ReportResultEntity, String>{

	/**
	 * <P>根据申报审批结果外键获取业务表单信息</P>
	 * @param reportStartId 外键 启动对外提报主键
	 * @return 申报审批结果业务单实体类
	 */
	public ReportResultEntity getEntityByRepStartId(String reportStartId) {
		if(reportStartId == null || reportStartId == " ") {
			return null;
		}
		ReportResultEntity entity = this.findUniqueResult("reportStartId", reportStartId);
		return entity;
	}

}
