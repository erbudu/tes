package com.supporter.prj.ppm.contract.effect.review.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.contract.effect.review.entity.base.BaseContractEffectReview;
import com.supporter.util.CodeTable;

/**
 * @Title: Entity
 * @Description: 生效评审表.
 * @author: YAN
 * @date: 2019-09-06
 * @version: V1.0
 */
@Entity(name = "PpmContractEffectReview")
@Table(name = "PPM_CONTRACT_EFFECT_REVIEW", schema = "")
public class PpmContractEffectReview extends BaseContractEffectReview implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "CONEFFREV"; // 应用模块的id
	public static final String DOMAIN_OBJECT_ID = "conEffRev";
	public static final int DB_YEAR = 0;

	/**
	 * 无参构造函数.
	 */
	public PpmContractEffectReview() {
		super();
	}

	/**
	 * 构造函数.
	 * @param contractEffectReviewId
	 */
	public PpmContractEffectReview(String contractEffectReviewId) {
		super(contractEffectReviewId);
	}
	private String countryName;//项目所在国
	private String address; //工程所在地
	private String prjNature;//项目性质
	private double contractAmount;
	private String contractRange;

	private Date contractSignDate;
	@Transient
	public double getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}
	@Transient
	public String getContractRange() {
		return contractRange;
	}

	public void setContractRange(String contractRange) {
		this.contractRange = contractRange;
	}
	@Transient
	public Date getContractSignDate() {
		return contractSignDate;
	}

	public void setContractSignDate(Date contractSignDate) {
		this.contractSignDate = contractSignDate;
	}
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
	@Transient
	public String getPrjNature() {
		return prjNature;
	}

	public void setPrjNature(String prjNature) {
		this.prjNature = prjNature;
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

		// 状态
		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "草稿");
			ct.insertItem(EXAM, "审批中");
			ct.insertItem(COMPLETE, "审批完成");
			return ct;
		}
	}

}
