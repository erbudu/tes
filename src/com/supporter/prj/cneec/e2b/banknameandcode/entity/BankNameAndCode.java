package com.supporter.prj.cneec.e2b.banknameandcode.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.e2b.banknameandcode.entity.base.BaseBankNameAndCode;

/**
 * @Title: BankNameAndCode
 * @Description: 银企直联实体类
 * @author: yanweichao
 * @date: 2018-11-08
 * @version: V1.0
 */
@Entity
@Table(name = "E2B_BANKNAMEANDCODE", schema = "")
public class BankNameAndCode extends BaseBankNameAndCode implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "E2BBANKACODE";
	public static final String DOMAIN_OBJECT_ID = "BankNameAndCode";// 业务对象编号，最长15位

	// Constructors

	/** default constructor */
	public BankNameAndCode() {
		super();
	}

	/** minimal constructor */
	public BankNameAndCode(String codeId) {
		super(codeId);
	}

	private String keyword;// 搜索关键字

	@Transient
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
