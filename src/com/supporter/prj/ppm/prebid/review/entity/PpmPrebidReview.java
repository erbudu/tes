package com.supporter.prj.ppm.prebid.review.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.prebid.review.entity.base.BasePpmPrebidReview;


/**
 * 投标评审表
 * @author 王康
 *
 */
@Entity
@Table(name = "PPM_PREBID_REVIEW", catalog = "")
public class PpmPrebidReview extends BasePpmPrebidReview{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String BUSI_TYPE = "PREREVIEW";
	public static final String MODULE_ID = "PREREVIEW";
	public static final String DOMAIN_OBJECT_ID = "PrebidReview";
	
	private boolean add;
	private String personNameT;
	private String personName;
	private String projectLevel;//项目级别
	private Integer isNeedGuarantee; //是否需要投标保函
	
	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}
	@Transient
	public String getPersonNameT() {
		return personNameT;
	}

	public void setPersonNameT(String personNameT) {
		this.personNameT = personNameT;
	}
	@Transient
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	@Transient
	public String getProcTitle() {
		String procTitle = this.getCreatedBy()+"发起的"+"项目评审申请";;
		return procTitle;
	}
	@Transient
	public int getDbYear()
    {
        return 0;
    }
	@Transient
	public String getModuleId() {
		return MODULE_ID;
	}
	@Transient
	public String getDomainObjectId() {
		return DOMAIN_OBJECT_ID;
	}
	@Transient
	public String getEntityId() {
		return this.getPrebidReviewId();
	}

    @Transient
    public String getEntityName()
    {
        return getClass().getName();
    }
    @Transient
    public String getModuleBusiType()
    {
        return "";
    }

	public static final int DRAFT = 0; // 草稿
	public static final int PROCESSING = 10; // 审核中
	public static final int COMPLETED = 20;// 审批完成
	
	//评估性质
	public static final String PREBID_REPORT = "10"; // 投标前评审
	public static final String SIGNING_REPORT = "20"; // 签约前评审
	public static final String CONTRACT_EFFECTIVE = "30";//主合同生效评审
	

	public static final String USE_SEAL_BUSINESS = "投议前评审-投标评审";//用印业务名称

	/**
	 * 获取类型码表.
	 * @return
	 */
	public static Map<String, String> getReviewTypeMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(PREBID_REPORT, "投标前评审");
//		map.put(SIGNING_REPORT, "签约前评审");
//		map.put(CONTRACT_EFFECTIVE, "主合同生效评审");
		return map;
	}

	// 类型
	@Transient
	public String getReviewTypeDesc() {
		if (this.getReviewType() != null) {
			return getReviewTypeMap().get(this.getReviewType());
		}
		return "";
	}

	@Transient
	public String getProjectLevel() {
		return projectLevel;
	}

	public void setProjectLevel(String projectLevel) {
		this.projectLevel = projectLevel;
	}

	@Transient
	public Integer getIsNeedGuarantee() {
		return isNeedGuarantee;
	}

	public void setIsNeedGuarantee(Integer isNeedGuarantee) {
		this.isNeedGuarantee = isNeedGuarantee;
	}
	
}
