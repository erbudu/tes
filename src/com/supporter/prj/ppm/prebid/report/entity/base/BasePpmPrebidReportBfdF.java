package com.supporter.prj.ppm.prebid.report.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author 王康
 *
 * PPM_EVALUATION_REPORTING  entity
 */
@MappedSuperclass
public class BasePpmPrebidReportBfdF  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
    private String recordId;
    private String bfdId;
    private String prbidReportId;
    private String fuFileId;
    private Integer isUseSeal;
    private Integer displayOrder;
    private String fuFileName;

	@Id
	@Column(name = "RECORD_ID", unique = true, nullable = false, length = 32)
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	@Column(name = "BFD_ID", length = 32)
	public String getBfdId() {
		return bfdId;
	}
	public void setBfdId(String bfdId) {
		this.bfdId = bfdId;
	}
	@Column(name = "PRBID_REPORT_ID", length = 32)
	public String getPrbidReportId() {
		return prbidReportId;
	}
	public void setPrbidReportId(String prbidReportId) {
		this.prbidReportId = prbidReportId;
	}
	@Column(name = "FU_FILE_ID", length = 32)
	public String getFuFileId() {
		return fuFileId;
	}
	public void setFuFileId(String fuFileId) {
		this.fuFileId = fuFileId;
	}
	@Column(name = "IS_USE_SEAL", length = 1)
	public Integer getIsUseSeal() {
		return isUseSeal;
	}
	public void setIsUseSeal(Integer isUseSeal) {
		this.isUseSeal = isUseSeal;
	}
	@Column(name = "DISPLAY_ORDER", length = 1)
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	@Column(name = "FU_FILE_NAME", length = 32)
	public String getFuFileName() {
		return fuFileName;
	}
	public void setFuFileName(String fuFileName) {
		this.fuFileName = fuFileName;
	}

    
}
