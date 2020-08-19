package com.supporter.prj.ppm.contract.sign.review.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.contract.sign.review.entity.base.BaseContractSignReview;
import com.supporter.util.CodeTable;

/**
 * @Title: Entity
 * @Description: 签约评审表.
 * @author: YAN
 * @date: 2019-09-06
 * @version: V1.0
 */
@Entity(name = "PpmContractSignReview")
@Table(name = "PPM_CONTRACT_SIGN_REVIEW", schema = "")
public class PpmContractSignReview extends BaseContractSignReview implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "CONTSIGNREV"; // 应用模块的id
	public static final String DOMAIN_OBJECT_ID = "conSignRev";
	public static final int DB_YEAR = 0;


	/**
	 * 无参构造函数.
	 */
	public PpmContractSignReview() {
		super();
	}

	/**
	 * 构造函数.
	 * @param contractSignReviewId
	 */
	public PpmContractSignReview(String contractSignReviewId) {
		super(contractSignReviewId);
	}

	private String countryName;//项目所在国
	private String address; //工程所在地
	private String prjNature;//项目性质
	private String zichou;//自筹资金
	private String shangye;//商业贷款
	private String zhengfu;//政府优惠贷款
	private String guoji;//国际金融组织
	@Transient
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	@Transient
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "PRJ_NATURE", nullable = true, length = 32)
	public String getPrjNature() {
		return prjNature;
	}

	public void setPrjNature(String prjNature) {
		this.prjNature = prjNature;
	}
	@Transient
	public String getZichou() {
		return zichou;
	}

	public void setZichou(String zichou) {
		this.zichou = zichou;
	}
	@Transient
	public String getShangye() {
		return shangye;
	}

	public void setShangye(String shangye) {
		this.shangye = shangye;
	}
	@Transient
	public String getZhengfu() {
		return zhengfu;
	}

	public void setZhengfu(String zhengfu) {
		this.zhengfu = zhengfu;
	}
	@Transient
	public String getGuoji() {
		return guoji;
	}

	public void setGuoji(String guoji) {
		this.guoji = guoji;
	}

	private boolean isNew;

	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	@Transient
	public String getModuleId() {
		return MODULE_ID;
	}

	@Transient
	public int getDbYear() {
		return DB_YEAR;
	}

	@Transient
	public String getDomainObjectId() {
		return DOMAIN_OBJECT_ID;
	}

	@Transient
	public String getEntityName() {
		return getClass().getName();
	}

	@Transient
	public String getModuleBusiType() {
		return "";
	}

	@Transient
	public String getCompanyNo() {
		return "";
	}

	// 状态
	public static final class StatusCodeTable {
		/** 草稿 */
		public static final int DRAFT = 0;
		/** 审批中 */
		public static final int EXAM = 1;
		/** 审批完成(生效) */
		public static final int COMPLETE = 2;
		/** 失效 */
		public static final int INVALID = 3;

		// 状态
		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "草稿");
			ct.insertItem(EXAM, "审批中");
			ct.insertItem(COMPLETE, "审批完成");
			ct.insertItem(INVALID, "失效");
			return ct;
		}
	}
}
