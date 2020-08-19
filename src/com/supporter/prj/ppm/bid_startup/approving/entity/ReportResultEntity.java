/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.approving.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.bid_startup.approving.entity.base.BaseReportResultEntity;

/**
 *<p>Title: 批复结果业务单实体类扩展类</p>
 *<p>Description: 业务单中用到的其他字段信息及方法，不跟数据库做对应关系</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月2日
 * 
 */
@Entity
@Table(name="PPM_BIDING_REPORT_RESULT",schema = "")
public class ReportResultEntity extends BaseReportResultEntity{
	
	
	@Transient
	private boolean newBuild;//用于标识是否新建
	
	
	
	//----------The following is construction method
	
	/**
	 * @return the newBuild
	 */
	public boolean getNewBuild() {
		return newBuild;
	}

	/**
	 * @param newBuild the newBuild to set
	 */
	public void setNewBuild(boolean newBuild) {
		this.newBuild = newBuild;
	}

	public ReportResultEntity() {
		
	}
	
	public ReportResultEntity(String reportResultId) {
		super(reportResultId);
	}

	
}
