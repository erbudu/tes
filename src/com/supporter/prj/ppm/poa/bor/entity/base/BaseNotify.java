package com.supporter.prj.ppm.poa.bor.entity.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class BaseNotify  {
	private String recordId;
	private String bidNotifyId;
	
	private  String notifyId;
	private String notifyName;
	@Override
	public String toString() {
		return "BaseNotify [recordId=" + recordId + ", bidNotifyId=" + bidNotifyId + ", notifyId=" + notifyId
				+ ", notifyName=" + notifyName + "]";
	}
	public BaseNotify() {
		super();
	}
	public BaseNotify(String recordId, String bidNotifyId, String notifyId, String notifyName) {
		super();
		this.recordId = recordId;
		this.bidNotifyId = bidNotifyId;
		this.notifyId = notifyId;
		this.notifyName = notifyName;
	}
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "RECORD_ID" , nullable = false, length = 32)
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	@Column(name = "NOTIFY_ID" , nullable = true, length = 32)
	public String getNotifyId() {
		return notifyId;
	}
	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}
	@Column(name = "NOTIFY_NAME" , nullable = true, length = 32)
	public String getNotifyName() {
		return notifyName;
	}
	public void setNotifyName(String notifyName) {
		this.notifyName = notifyName;
	}
	@Column(name = "BID_NOTIFY_ID" , nullable = true, length = 32)
	public String getBidNotifyId() {
		return bidNotifyId;
	}
	public void setBidNotifyId(String bidNotifyId) {
		this.bidNotifyId = bidNotifyId;
	}
}
