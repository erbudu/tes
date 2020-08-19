package com.supporter.prj.pm.enginee_negotiate.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class BaseEngineeVisaMeet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "REC_ID", unique = true, nullable = false, length = 32)
	private java.lang.String recId;

	@Column(name = "VISA_ID", nullable = true, length = 32)
	private java.lang.String visaId;

	@Column(name = "PROC_ID", nullable = true, length = 32)
	private java.lang.String procId;

	@Column(name = "MEETING_DATE", nullable = true)
	private java.util.Date meetingDate;

	@Column(name = "HOST_NAME", nullable = true, length = 128)
	private java.lang.String hostName;

	@Column(name = "HOST_ID", nullable = true, length = 256)
	private java.lang.String hostId;

	@Column(name = "MEETING_ADDRESS", nullable = true, length = 256)
	private java.lang.String meetingAddress;

	@Column(name = "MEETING_RESULT", nullable = true)
	private int meetingResult;

	public java.lang.String getRecId() {
		return recId;
	}

	public void setRecId(java.lang.String recId) {
		this.recId = recId;
	}

	public java.lang.String getVisaId() {
		return visaId;
	}

	public void setVisaId(java.lang.String visaId) {
		this.visaId = visaId;
	}

	public java.lang.String getProcId() {
		return procId;
	}

	public void setProcId(java.lang.String procId) {
		this.procId = procId;
	}

	public java.util.Date getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(java.util.Date meetingDate) {
		this.meetingDate = meetingDate;
	}

	public java.lang.String getHostName() {
		return hostName;
	}

	public void setHostName(java.lang.String hostName) {
		this.hostName = hostName;
	}

	public java.lang.String getHostId() {
		return hostId;
	}

	public void setHostId(java.lang.String hostId) {
		this.hostId = hostId;
	}

	public java.lang.String getMeetingAddress() {
		return meetingAddress;
	}

	public void setMeetingAddress(java.lang.String meetingAddress) {
		this.meetingAddress = meetingAddress;
	}

	public int getMeetingResult() {
		return meetingResult;
	}

	public void setMeetingResult(int meetingResult) {
		this.meetingResult = meetingResult;
	}

	public BaseEngineeVisaMeet() {

	}

}
