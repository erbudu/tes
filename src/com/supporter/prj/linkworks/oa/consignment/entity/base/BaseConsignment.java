package com.supporter.prj.linkworks.oa.consignment.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 功能模块,字段与数据库字段一一对应.
 * @author linxiaosong
 * @version V1.0   
 */

@MappedSuperclass
public class BaseConsignment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "CONSIGNMENT_ID", unique = true, nullable = false, precision = 32, scale = 0)
	private String consignmentId;
	//授权人id
	@Column(name="CONSIGNER_ID" ,length=32, nullable = true)
	private String consignerId;
	//被授权人id
	@Column(name="CONSIGNEE_ID" ,length=32, nullable = true)
	private String consigneeId;
	//授权开始日期
	@Column(name="DATE_FROM" ,length=27, nullable = true)
	private String dateFrom;
	//授权结束日期
	@Column(name="DATE_TO" ,length=27, nullable = true)
	private String dateTo;
	//授权人姓名
	@Column(name="CONSIGNER_NAME" ,length=32, nullable = true)
	private String consignerName;
	//被授权人姓名
	@Column(name="CONSIGNEE_NAME" ,length=32, nullable = true)
	private String consigneeName;
	//授权原因
	@Column(name="CONSIGN_REASON" ,length=255, nullable = true)
	private String consignReason;
	//是否发布公告
	@Column(name="PUBLISH_BULLETIN" ,length=1, nullable = true)
	private String publishBulletin;
	//委托表id
	@Column(name="CONSIGN_ID" ,length=32, nullable = true)
	private String consignId;
	//是否有效
    @Column(name = "IS_FAILURE", precision = 1, scale = 0, nullable = true)
	private Integer isFailure = 1;
  //标识历史数据
	@Column(name = "IS_HISTORY")
	@org.hibernate.annotations.Type(type="true_false")
    private boolean history;

	public boolean getHistory() {
		return history;
	}
	public BaseConsignment(String consignmentId, String consignerId,
			String consigneeId, String dateFrom, String dateTo,
			String consignerName, String consigneeName, String consignReason,
			String publishBulletin, Integer isFailure,String consignId,boolean history) {
		super();
		this.consignmentId = consignmentId;
		this.consignerId = consignerId;
		this.consigneeId = consigneeId;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.consignerName = consignerName;
		this.consigneeName = consigneeName;
		this.consignReason = consignReason;
		this.publishBulletin = publishBulletin;
		this.isFailure = isFailure;
		this.consigneeId = consignId;
		this.history=history;
	}

	public BaseConsignment() {
		super();
	}

	public String getConsignmentId() {
		return consignmentId;
	}

	public void setConsignmentId(String consignmentId) {
		this.consignmentId = consignmentId;
	}

	public String getConsignerId() {
		return consignerId;
	}

	public void setConsignerId(String consignerId) {
		this.consignerId = consignerId;
	}

	public String getConsigneeId() {
		return consigneeId;
	}

	public void setConsigneeId(String consigneeId) {
		this.consigneeId = consigneeId;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getConsignerName() {
		return consignerName;
	}

	public void setConsignerName(String consignerName) {
		this.consignerName = consignerName;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsignReason() {
		return consignReason;
	}

	public void setConsignReason(String consignReason) {
		this.consignReason = consignReason;
	}

	public String getPublishBulletin() {
		return publishBulletin;
	}

	public void setPublishBulletin(String publishBulletin) {
		this.publishBulletin = publishBulletin;
	}

	public Integer getIsFailure() {
		return isFailure;
	}

	public void setIsFailure(Integer isFailure) {
		this.isFailure = isFailure;
	}

	public String getConsignId() {
		return consignId;
	}

	public void setConsignId(String consignId) {
		this.consignId = consignId;
	}
    
    
}
