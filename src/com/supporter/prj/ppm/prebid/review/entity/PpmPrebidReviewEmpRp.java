package com.supporter.prj.ppm.prebid.review.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.prebid.review.entity.base.BasePpmPrebidReviewEmpRp;

/**
 * 评审人员的评审要点表
 * @author 王康
 *
 */
@Entity
@Table(name = "PPM_PREBID_REVIEW_EMP_RP", catalog = "")
public class PpmPrebidReviewEmpRp extends BasePpmPrebidReviewEmpRp{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int add;

	@Transient
	public int getAdd() {
		return add;
	}

	public void setAdd(int add) {
		this.add = add;
	}// 是否新增

}
