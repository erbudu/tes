package com.supporter.prj.ppm.prebid.report.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author 王康
 *
 * PPM_PREBID_REPORT_BFD  entity
 */
@MappedSuperclass
public class BasePpmPrebidReportBfd  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String bfdId;
	private String prbidReportId;
	private String bfdTypeId;
	private String bfdTypeName;
	private Integer isUseSeal;//是否用印
	

	@Id
	@Column(name = "BFD_ID", unique = true, nullable = false, length = 32)
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
	@Column(name = "BFD_TYPE_ID", length = 32)
	public String getBfdTypeId() {
		return bfdTypeId;
	}
	public void setBfdTypeId(String bfdTypeId) {
		this.bfdTypeId = bfdTypeId;
	}
	@Column(name = "BFD_TYPE_NAME", length = 256)
	public String getBfdTypeName() {
		return bfdTypeName;
	}
	public void setBfdTypeName(String bfdTypeName) {
		this.bfdTypeName = bfdTypeName;
	}

	@Column(name="IS_USE_SEAL")
	public Integer getIsUseSeal() {
		return isUseSeal;
	}
	public void setIsUseSeal(Integer isUseSeal) {
		this.isUseSeal = isUseSeal;
	}
	
}
