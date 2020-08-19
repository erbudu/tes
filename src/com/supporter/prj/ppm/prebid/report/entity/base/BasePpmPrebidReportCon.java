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
public class BasePpmPrebidReportCon  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
    private String bidingupRvConId;
    private String prbidReportId;
    private Integer rvConStatus;
    private Integer rvConBussinesStatus;
	@Id
	@Column(name = "BIDINGUP_RV_CON_ID", unique = true, nullable = false, length = 32)
	public String getBidingupRvConId() {
		return bidingupRvConId;
	}
	public void setBidingupRvConId(String bidingupRvConId) {
		this.bidingupRvConId = bidingupRvConId;
	}
	@Column(name = "PRBID_REPORT_ID", length = 32)
	public String getPrbidReportId() {
		return prbidReportId;
	}
	public void setPrbidReportId(String prbidReportId) {
		this.prbidReportId = prbidReportId;
	}
	@Column(name = "RV_CON_STATUS", length = 1)
	public Integer getRvConStatus() {
		return rvConStatus;
	}
	public void setRvConStatus(Integer rvConStatus) {
		this.rvConStatus = rvConStatus;
	}
	@Column(name = "RV_CON_BUSSINES_STATUS", length = 1)
	public Integer getRvConBussinesStatus() {
		return rvConBussinesStatus;
	}
	public void setRvConBussinesStatus(Integer rvConBussinesStatus) {
		this.rvConBussinesStatus = rvConBussinesStatus;
	}

    
}
