package com.supporter.prj.ppm.prebid.review.entity;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.prebid.review.entity.base.BasePpmPrebidReviewEmp;

/**
 * 评审人员
 * @author 王康
 *
 */
@Entity
@Table(name = "PPM_PREBID_REVIEW_EMP", catalog = "")
public class PpmPrebidReviewEmp extends BasePpmPrebidReviewEmp{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUSINESS = "1";
	public static final String TECHNOLOGY = "2";
	
	private int add;

	@Transient
	public int getAdd() {
		return add;
	}

	public void setAdd(int add) {
		this.add = add;
	}// 是否新增


	
	
}
