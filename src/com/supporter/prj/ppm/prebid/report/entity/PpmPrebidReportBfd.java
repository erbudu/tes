package com.supporter.prj.ppm.prebid.report.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.prebid.report.entity.base.BasePpmPrebidReportBfd;

/**
 * 标前评审资料清单
 * @author 王康
 *
 */
@Entity
@Table(name = "PPM_PREBID_REPORT_BFD", catalog = "")
public class PpmPrebidReportBfd extends BasePpmPrebidReportBfd{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String fileInfo;//上传附件成功后返回的附件信息

	@Transient
	public String getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}
}
