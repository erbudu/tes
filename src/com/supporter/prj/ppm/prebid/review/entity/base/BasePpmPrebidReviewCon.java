package com.supporter.prj.ppm.prebid.review.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author 王康
 *
 * PPM_EVALUATION_REPORTING  entity
 */
@MappedSuperclass
public class BasePpmPrebidReviewCon  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

    private String prebidRevConId;
    private String prebidReviewId;
    private Integer rvConStatus;
    private Integer rvConBussinesStatus;

	@Id
	@Column(name = "PREBID_REV_CON_ID", unique = true, nullable = false, length = 32)
	public String getPrebidRevConId() {
		return prebidRevConId;
	}
	public void setPrebidRevConId(String prebidRevConId) {
		this.prebidRevConId = prebidRevConId;
	}
	@Column(name = "PREBID_REVIEW_ID", length = 32)
	public String getPrebidReviewId() {
		return prebidReviewId;
	}
	public void setPrebidReviewId(String prebidReviewId) {
		this.prebidReviewId = prebidReviewId;
	}
	@Column(name = "RV_CON_STATUS", length = 32)
	public Integer getRvConStatus() {
		return rvConStatus;
	}
	public void setRvConStatus(Integer rvConStatus) {
		this.rvConStatus = rvConStatus;
	}
	@Column(name = "RV_CON_BUSSINES_STATUS", length = 32)
	public Integer getRvConBussinesStatus() {
		return rvConBussinesStatus;
	}
	public void setRvConBussinesStatus(Integer rvConBussinesStatus) {
		this.rvConBussinesStatus = rvConBussinesStatus;
	}

	
    
}
