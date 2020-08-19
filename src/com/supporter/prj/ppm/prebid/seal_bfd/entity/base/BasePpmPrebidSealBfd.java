package com.supporter.prj.ppm.prebid.seal_bfd.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author 王康
 *
 * PPM_EVALUATION_REPORTING  entity
 */
@MappedSuperclass
public class BasePpmPrebidSealBfd  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

    private String bfdId;
    private String prbidReportId;
    private String bfdTypeId;
    private String bfdTypeName;

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
    
    
}
