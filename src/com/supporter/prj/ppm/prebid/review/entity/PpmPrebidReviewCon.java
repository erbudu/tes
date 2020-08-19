package com.supporter.prj.ppm.prebid.review.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.prebid.review.entity.base.BasePpmPrebidReviewCon;

/**
 * 评审结果
 * @author 王康
 *
 */
@Entity
@Table(name = "PPM_PREBID_REVIEW_CON", catalog = "")
public class PpmPrebidReviewCon extends BasePpmPrebidReviewCon{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int DRAFT = 0; //不通过
	public static final int PROCESSING = 1; //有条件通过          条件为补充上传文件
	public static final int COMPLETED = 2;//通过        条件为调整评审要点
	public static final int ALL = 3; // 条件为调整评审要点和补充上传文件

	private int changeStatus;// 
	@Transient
	public int getChangeStatus() {
		return changeStatus;
	}


	public void setChangeStatus(int changeStatus) {
		this.changeStatus = changeStatus;
	}
}
