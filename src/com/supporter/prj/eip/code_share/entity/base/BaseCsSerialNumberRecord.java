package com.supporter.prj.eip.code_share.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

// ~ File Information
// ====================================================================================================================

/**
 * [应用库.编号规则明细表]对应的实体抽象类.
 * 
 * @author 康博
 * @createDate 2017-05-02
 * @since 6.0
 * @modifier 康博
 * @modifiedDate 2017-05-02
 */
@MappedSuperclass
public class BaseCsSerialNumberRecord implements Serializable {

	// ~ Static Fields
	// ================================================================================================================
	
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 20170502095009L;
    
	/**
	 * 属性 recordId.
	 */
	public static final String PROP_RECORD_ID = "recordId";
    
	/**
	 * 属性 serialNumberId.
	 */
	public static final String PROP_SERIAL_NUMBER_ID = "serialNumberId";
    
	/**
	 * 属性 recordType.
	 */
	public static final String PROP_RECORD_TYPE = "recordType";
    
	/**
	 * 属性 recordTypeLabel.
	 */
	public static final String PROP_RECORD_TYPE_LABEL = "recordTypeLabel";
    
	/**
	 * 属性 recordTypeValue.
	 */
	public static final String PROP_RECORD_TYPE_VALUE = "recordTypeValue";
    
	/**
	 * 属性 useNumbering.
	 */
	public static final String PROP_USE_NUMBERING = "useNumbering";
    
	/**
	 * 属性 displayOrder.
	 */
	public static final String PROP_DISPLAY_ORDER = "displayOrder";
  
	// ~ Fields
	// ================================================================================================================
		
	/**
	 * 编号规则明细ID .
	 */
	@Id
	@Column(name = "RECORD_ID", nullable = false, length = 32)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	private String recordId;
		
	/**
	 * 编号规则ID .
	 */
	@Column(name = "SERIAL_NUMBER_ID")
	private String serialNumberId;
		
	/**
	 * 明细类型 .
	 */
	@Column(name = "RECORD_TYPE")
	private String recordType;
		
	/**
	 * 明细类型标签 .
	 */
	@Column(name = "RECORD_TYPE_LABEL")
	private String recordTypeLabel;
		
	/**
	 * 明细类型值 .
	 */
	@Column(name = "RECORD_TYPE_VALUE")
	private String recordTypeValue;
		
	/**
	 * 排序 .
	 */
	@Column(name = "DISPLAY_ORDER")
	private double displayOrder;
		
	// ~ Constructors
	// ================================================================================================================

	// ~ Methods
	// ================================================================================================================
		
	/**
	 * @return 返回  {@link #recordId}。
	 */
	public String getRecordId() {
		return this.recordId;
	}

	/**
	 * @param recordId 要设置的 {@link #recordId}。
	 */
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
		
	/**
	 * @return 返回  {@link #serialNumberId}。
	 */
	public String getSerialNumberId() {
		return this.serialNumberId;
	}

	/**
	 * @param serialNumberId 要设置的 {@link #serialNumberId}。
	 */
	public void setSerialNumberId(String serialNumberId) {
		this.serialNumberId = serialNumberId;
	}
		
	/**
	 * @return 返回  {@link #recordType}。
	 */
	public String getRecordType() {
		return this.recordType;
	}

	/**
	 * @param recordType 要设置的 {@link #recordType}。
	 */
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
		
	/**
	 * @return 返回  {@link #recordTypeLabel}。
	 */
	public String getRecordTypeLabel() {
		return this.recordTypeLabel;
	}

	/**
	 * @param recordTypeLabel 要设置的 {@link #recordTypeLabel}。
	 */
	public void setRecordTypeLabel(String recordTypeLabel) {
		this.recordTypeLabel = recordTypeLabel;
	}
		
	/**
	 * @return 返回  {@link #recordTypeValue}。
	 */
	public String getRecordTypeValue() {
		return this.recordTypeValue;
	}

	/**
	 * @param recordTypeValue 要设置的 {@link #recordTypeValue}。
	 */
	public void setRecordTypeValue(String recordTypeValue) {
		this.recordTypeValue = recordTypeValue;
	}
		
	/**
	 * @return 返回  {@link #displayOrder}。
	 */
	public double getDisplayOrder() {
		return this.displayOrder;
	}

	/**
	 * @param displayOrder 要设置的 {@link #displayOrder}。
	 */
	public void setDisplayOrder(double displayOrder) {
		this.displayOrder = displayOrder;
	}
}
