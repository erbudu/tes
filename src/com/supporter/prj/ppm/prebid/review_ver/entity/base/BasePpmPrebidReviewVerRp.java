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
public class BasePpmPrebidReviewVerRp  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String rpId;
    private String reviewVerId;
    private String prebidReviewId;
    private String prjId;
    private String reviewPointsId;

	@Id
	@Column(name = "RP_ID", unique = true, nullable = false, length = 32)
	public String getRpId() {
		return rpId;
	}
	public void setRpId(String rpId) {
		this.rpId = rpId;
	}
	@Column(name = "REVIEW_VER_ID", length = 32)
	public String getReviewVerId() {
		return reviewVerId;
	}
	public void setReviewVerId(String reviewVerId) {
		this.reviewVerId = reviewVerId;
	}
	@Column(name = "PREBID_REVIEW_ID", length = 32)
	public String getPrebidReviewId() {
		return prebidReviewId;
	}
	public void setPrebidReviewId(String prebidReviewId) {
		this.prebidReviewId = prebidReviewId;
	}
	@Column(name = "PRJ_ID", length = 32)
	public String getPrjId() {
		return prjId;
	}
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}
	@Column(name = "REVIEW_POINTS_ID", length = 512)
	public String getReviewPointsId() {
		return reviewPointsId;
	}
	public void setReviewPointsId(String reviewPointsId) {
		this.reviewPointsId = reviewPointsId;
	}
	@Override
	public String toString() {
		return "BasePpmPrebidReviewVerRp [rpId=" + rpId + ", reviewVerId="
				+ reviewVerId + ", prebidReviewId=" + prebidReviewId
				+ ", prjId=" + prjId + ", reviewPointsId=" + reviewPointsId
				+ "]";
	}
    
    
}
