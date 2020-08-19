package com.supporter.prj.linkworks.oa.signed_report.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.linkworks.oa.signed_report.entity.base.BaseSignedReportContent;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author gongjiwen
 * @date 2016-12-05 10:25:07
 * @version V1.0   
 *
 */
@Entity
@Table(name = "oa_signed_report_content", schema = "")
public class SignedReportContent extends BaseSignedReportContent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


}
