package com.supporter.prj.linkworks.oa.abroadPublicity.entity.base;

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
public class BaseAbroadPublicity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "PUB_ID", unique = true, nullable = false, precision = 32, scale = 0)
	private String pubId;
	
	//出国申请id
	@Column(name="ABROAD_ID" ,length=32, nullable = true)
	private String abroadId;
	
	
	//公示天数
    @Column(name = "PUB_DAY", precision = 2, scale = 0, nullable = true)
	private Integer pubDay = 5;
    
    //公示开始日期
	@Column(name="PUB_START_DATE" ,length=27, nullable = true)
	private String pubStartDate;
	
	//公示结束日期
	@Column(name="PUB_END_DATE" ,length=27, nullable = true)
	private String pubEndDate;

	
	
	public BaseAbroadPublicity() {
		super();
	}

	public BaseAbroadPublicity(String pubId, String abroadId, Integer pubDay,
			String pubStartDate, String pubEndDate) {
		super();
		this.pubId = pubId;
		this.abroadId = abroadId;
		this.pubDay = pubDay;
		this.pubStartDate = pubStartDate;
		this.pubEndDate = pubEndDate;
	}

	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public String getAbroadId() {
		return abroadId;
	}

	public void setAbroadId(String abroadId) {
		this.abroadId = abroadId;
	}

	public Integer getPubDay() {
		return pubDay;
	}

	public void setPubDay(Integer pubDay) {
		this.pubDay = pubDay;
	}

	public String getPubStartDate() {
		return pubStartDate;
	}

	public void setPubStartDate(String pubStartDate) {
		this.pubStartDate = pubStartDate;
	}

	public String getPubEndDate() {
		return pubEndDate;
	}

	public void setPubEndDate(String pubEndDate) {
		this.pubEndDate = pubEndDate;
	}
	
	
}
