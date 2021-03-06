package com.supporter.prj.cneec.tpc.benefit_note.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: TPC_BENEFIT_NOTE_CURRENCY,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2018-06-01
 * @version V1.0
 */
@MappedSuperclass
public class BaseBenefitNoteCurrency implements Serializable {

	private static final long serialVersionUID = 1L;
	private String recordId;
	private String noteId;
	private String currencyId;
	private String currency;
	private double rate;
	private int orderDisplay;

	/**
	 * 无参构造函数.
	 */
	public BaseBenefitNoteCurrency() {
	}

	/**
	 * 构造函数.
	 * 
	 * @param noteId
	 */
	public BaseBenefitNoteCurrency(String recordId) {
		this.recordId = recordId;
	}

	public BaseBenefitNoteCurrency(String noteId, int orderDisplay) {
		this.noteId = noteId;
		this.orderDisplay = orderDisplay;
	}

	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "RECORD_ID", nullable = false, length = 32)
	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	@Column(name = "NOTE_ID", nullable = false, length = 32)
	public String getNoteId() {
		return this.noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	@Column(name = "CURRENCY_ID", nullable = true, length = 32)
	public String getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name = "CURRENCY", nullable = true, length = 32)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "RATE", nullable = true, precision = 10, scale = 4)
	public double getRate() {
		return this.rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	@Column(name = "ORDER_DISPLAY", nullable = true, precision = 10)
	public int getOrderDisplay() {
		return this.orderDisplay;
	}

	public void setOrderDisplay(int orderDisplay) {
		this.orderDisplay = orderDisplay;
	}

}
