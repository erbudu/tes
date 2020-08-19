package com.supporter.prj.cneec.e2b.banknameandcode.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: E2B_BANKNAMEANDCODE,字段与数据库字段一一对应.
 * @author: Yanweichao
 * @date: 2018-11-08
 * @version: V1.0
 */
@MappedSuperclass
public abstract class BaseBankNameAndCode implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String codeId;
	private String bankCode;// 联行号
	private String bankName;// 银行名称
	private String bankTypeCode;// 
	private String areaCode;
	private String updateDate;
	private String isSyn;
	private Integer displayOrder;

	/**
	 * 无参构造函数.
	 */
	public BaseBankNameAndCode() {
	}

	/**
	 * 构造函数.
	 *
	 * @param codeId
	 */
	public BaseBankNameAndCode(String codeId) {
		this.codeId = codeId;
	}

	@Id
	@Column(name = "CODE_ID", nullable = false, length = 32)
	public String getCodeId() {
		return this.codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	@Column(name = "BANK_CODE", nullable = true, length = 24)
	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "BANK_NAME", nullable = true, length = 512)
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "BANK_TYPE_CODE", nullable = true, length = 24)
	public String getBankTypeCode() {
		return this.bankTypeCode;
	}

	public void setBankTypeCode(String bankTypeCode) {
		this.bankTypeCode = bankTypeCode;
	}

	@Column(name = "AREA_CODE", nullable = true, length = 24)
	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "UPDATE_DATE", nullable = true, length = 27)
	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "IS_SYN", nullable = true, length = 1)
	public String getIsSyn() {
		return this.isSyn;
	}

	public void setIsSyn(String isSyn) {
		this.isSyn = isSyn;
	}

	@Column(name = "DISPLAY_ORDER", nullable = true, precision = 10)
	public Integer getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

}
