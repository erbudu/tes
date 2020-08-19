package com.supporter.prj.ppm.prj_org.base_info.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.prj_org.base_info.entity.base.BasePrjAddr;

/**
 * PpmPrjAddr entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PPM_PRJ_ADDR", schema = "")
public class PrjAddr extends BasePrjAddr {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数
	 */
	public PrjAddr() {
		super();
	}
	
	private String countryAddrC;
	private String countryAddrE;
	private String provinceAddrC;
	private String provinceAddrE;

	@Transient
	public String getCountryAddrC() {
		return countryAddrC;
	}
	public void setCountryAddrC(String countryAddrC) {
		this.countryAddrC = countryAddrC;
	}
	
	@Transient
	public String getCountryAddrE() {
		return countryAddrE;
	}
	public void setCountryAddrE(String countryAddrE) {
		this.countryAddrE = countryAddrE;
	}
	
	@Transient
	public String getProvinceAddrC() {
		return provinceAddrC;
	}
	public void setProvinceAddrC(String provinceAddrC) {
		this.provinceAddrC = provinceAddrC;
	}
	
	@Transient
	public String getProvinceAddrE() {
		return provinceAddrE;
	}
	public void setProvinceAddrE(String provinceAddrE) {
		this.provinceAddrE = provinceAddrE;
	}

	
	

}
