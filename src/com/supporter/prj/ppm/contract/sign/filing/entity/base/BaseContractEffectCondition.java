package com.supporter.prj.ppm.contract.sign.filing.entity.base;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @Title: Entity
 * @Description: PPM_CONTRACT_FILING_BFD_F,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-09-17
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractEffectCondition implements Serializable {

	private static final long serialVersionUID = 1L;
	private String conditionId;
	private String signFilingId;
	private String prjId;
	private String conditionContent;
	private int status;
	private int displayOrder;

	/**
	 * 无参构造函数.
	 */
	public BaseContractEffectCondition() {
	}

	/**
	 * 构造函数.
	 *
	 * @param recordId
	 */
	public BaseContractEffectCondition(String conditionId) {
		this.conditionId = conditionId;
	}
	/**
	 * 方法: 取得RECORD_ID.
	 * @return: java.lang.String RECORD_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "CONDITION_ID", nullable = false, length = 32)
	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}
	@Column(name = "SIGN_FILING_ID", nullable = true, length = 32)
	public String getSignFilingId() {
		return signFilingId;
	}

	public void setSignFilingId(String signFilingId) {
		this.signFilingId = signFilingId;
	}
	@Column(name = "PRJ_ID", nullable = true, length = 32)

	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}
	@Column(name = "CONDITION_CONTENT", nullable = true, length = 32)

	public String getConditionContent() {
		return conditionContent;
	}

	public void setConditionContent(String conditionContent) {
		this.conditionContent = conditionContent;
	}
	@Column(name = "STATUS", nullable = true)

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 方法: 取得排序.
	 * @return: int 排序
	 */
	@Column(name = "DISPLAYORDER", nullable = true, precision = 10)
	public int getDisplayOrder() {
		return this.displayOrder;
	}

	/**
	 * 方法: 设置排序.
	 * @param: int 排序
	 */
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

}
