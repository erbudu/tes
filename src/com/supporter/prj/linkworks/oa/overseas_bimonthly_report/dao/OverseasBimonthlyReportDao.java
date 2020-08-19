/**
 * 
 */
package com.supporter.prj.linkworks.oa.overseas_bimonthly_report.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.overseas_bimonthly_report.constant.OverseasBimonthlyReportConstant;
import com.supporter.prj.linkworks.oa.overseas_bimonthly_report.entity.OverseasBimonthlyReportEntity;
import com.supporter.prj.linkworks.oa.report.constant.AuthConstant;
import com.supporter.prj.linkworks.oa.report.constant.LogConstant;
import com.supporter.prj.linkworks.oa.report.constant.ReportConstant;
import com.supporter.prj.linkworks.oa.report.entity.Report;

/**
 *<p>Title: OverseasBimonthlyreportDao</p>
 *<p>Description: 境外机构双月报审批 持久层，与数据库做交互</p>
 *<p>Company:东华后盾 </p>
 * @author YUEYUN
 * @date 2019年12月23日
 * 
 */
@Repository
public class OverseasBimonthlyReportDao extends MainDaoSupport<OverseasBimonthlyReportEntity,String>{

	public List<OverseasBimonthlyReportEntity> findPage(UserProfile user, JqGrid jqGrid, OverseasBimonthlyReportEntity entity) {
//		String authHql = "";
		if(entity != null){
			//查询
			String reportTitle = entity.getReportTitle();
			if(StringUtils.isNotBlank(reportTitle)){
				jqGrid.addHqlFilter(
						"reportTitle like ? or reportNo like ? ", 
						"%" + reportTitle + "%","%" + reportTitle + "%");
			}
			 //状态过滤
	        if(entity.getStatus() > -1){
	        	jqGrid.addHqlFilter("status = ?" ,entity.getStatus());
	        }
	        
	        //时间区间过滤
	        if(StringUtils.isNotBlank(entity.getStartDate()) && StringUtils.isNotBlank(entity.getEndDate())) {
	        	jqGrid.addHqlFilter(
						"createdByDate >= ? and createdByDate <= ? ", 
						entity.getStartDate(),entity.getEndDate());
	        }else if(StringUtils.isNotBlank(entity.getStartDate())) {
	        	jqGrid.addHqlFilter("createdByDate >= ?", entity.getStartDate());
	        }else if(StringUtils.isNotBlank(entity.getEndDate())) {
	        	jqGrid.addHqlFilter("createdByDate <= ?", entity.getEndDate());
	        }
	        
	        //授权
	        String authHql = EIPService.getAuthorityService().getHqlFilter(user, OverseasBimonthlyReportConstant.MODULE_ID, OverseasBimonthlyReportConstant.DATA_AUTH);
	        jqGrid.addHqlFilter(authHql);
			
			jqGrid.addSortPropertyDesc("createdByDate");
		}
		return this.retrievePage(jqGrid);
	}

}
