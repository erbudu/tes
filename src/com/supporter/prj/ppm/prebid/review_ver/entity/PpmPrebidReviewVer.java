package com.supporter.prj.ppm.prebid.review_ver.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.prebid.review_ver.entity.base.BasePpmPrebidReviewVer;

/**
 * 评审验证表
 * @author 王康
 *
 */
@Entity
@Table(name = "PPM_PREBID_REVIEW_VER", catalog = "")
public class PpmPrebidReviewVer extends BasePpmPrebidReviewVer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String BUSI_TYPE = "prebidRevFile";
	public static final String MODULE_ID = "PREREVIEWVER";
	public static final String DOMAIN_OBJECT_ID = "PrebidReviewVer";
	
	public static final int DRAFT = 0; // 草稿
	public static final int PROCESSING = 10; // 审核中
	public static final int COMPLETED = 20;// 审批完成

	public static final int NOPASS = 0; // 未通过
	public static final int PASS = 1; // 通过未知会
	public static final int PASSNOTICE = 2;// 通过
	
	public static final String USE_SEAL_BUSINESS = "投议前评审-评审验证";//用印业务名称
	
	private boolean isNew;
	private List<PpmPrebidReviewVerBfd> dataList;
	private String rpId;
	private String reviewNo;
	private String modifyEvalPointVal;
	@Transient
	public String getProcTitle() {
		String procTitle = "项目评审验证申请";;
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
		return this.getReviewVerId();
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
    
	@Transient
	public List<PpmPrebidReviewVerBfd> getDataList() {
		return dataList;
	}

	public void setDataList(List<PpmPrebidReviewVerBfd> dataList) {
		this.dataList = dataList;
	}
	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	@Transient
	public String getRpId() {
		return rpId;
	}
	public void setRpId(String rpId) {
		this.rpId = rpId;
	}
	@Transient
	public String getReviewNo() {
		return reviewNo;
	}
	public void setReviewNo(String reviewNo) {
		this.reviewNo = reviewNo;
	}
	@Transient
	public String getModifyEvalPointVal() {
		return modifyEvalPointVal;
	}
	public void setModifyEvalPointVal(String modifyEvalPointVal) {
		this.modifyEvalPointVal = modifyEvalPointVal;
	}
	
}
