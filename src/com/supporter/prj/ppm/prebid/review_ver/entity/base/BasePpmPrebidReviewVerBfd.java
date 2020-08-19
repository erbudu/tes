package com.supporter.prj.ppm.prebid.review_ver.entity.base;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author 王康
 *
 * PPM_EVALUATION_REPORTING  entity
 */
@MappedSuperclass
public class BasePpmPrebidReviewVerBfd  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

    private String bfdId;
    private String reviewVerId;
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
	@Column(name = "REVIEW_VER_ID", length = 32)
	public String getReviewVerId() {
		return reviewVerId;
	}
	public void setReviewVerId(String reviewVerId) {
		this.reviewVerId = reviewVerId;
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
	@Override
	public String toString() {
		return "BasePpmPrebidReviewVerBfd [bfdId=" + bfdId + ", reviewVerId="
				+ reviewVerId + ", prbidReportId=" + prbidReportId
				+ ", bfdTypeId=" + bfdTypeId + ", bfdTypeName=" + bfdTypeName
				+ "]";
	}

	@Column(name="IS_USE_SEAL")
	public Integer getIsUseSeal() {
		return isUseSeal;
	}
	public void setIsUseSeal(Integer isUseSeal) {
		this.isUseSeal = isUseSeal;
	}
    
}
