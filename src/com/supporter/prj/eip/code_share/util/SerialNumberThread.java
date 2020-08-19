package com.supporter.prj.eip.code_share.util;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.code_share.service.CsSerialNumberBusinessService;
import com.supporter.prj.eip_service.security.entity.UserProfile;


// ~ File Information
// ====================================================================================================================

public class SerialNumberThread implements Runnable {

	// ~ Static Fields
	// ================================================================================================================

	// ~ Fields
	// ================================================================================================================
	
	private String moduleNo;
	
	private String category2No;
	
	private int year;
	private int month;
	private UserProfile user;
	private String serialNumberNo;

	// ~ Constructors
	// ================================================================================================================
	
	public SerialNumberThread(int year, int month, String moduleNo, String category2No, UserProfile user) {
		this.year = year;
		this.month = month;
		this.moduleNo = moduleNo;
		this.category2No = category2No;
		this.user = user;
	}

	// ~ Methods
	// ================================================================================================================
	
	public void run() {
		CsSerialNumberBusinessService service = SpringContextHolder.getBean(CsSerialNumberBusinessService.class);
		serialNumberNo = service.generateNumber(year, month, moduleNo, category2No, new Object(), user);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>" + serialNumberNo);
	}

	/**
	 * @return 返回  {@link #moduleNo}。
	 */
	public String getModuleNo() {
		return moduleNo;
	}

	/**
	 * @param moduleNo 要设置的 {@link #moduleNo}。
	 */
	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
	}

	/**
	 * @return 返回  {@link #category2No}。
	 */
	public String getCategory2No() {
		return category2No;
	}

	/**
	 * @param category2No 要设置的 {@link #category2No}。
	 */
	public void setCategory2No(String category2No) {
		this.category2No = category2No;
	}

	/**
	 * @return 返回  {@link #year}。
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year 要设置的 {@link #year}。
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return 返回  {@link #month}。
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @param month 要设置的 {@link #month}。
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * @return 返回  {@link #user}。
	 */
	public UserProfile getUser() {
		return user;
	}

	/**
	 * @param user 要设置的 {@link #user}。
	 */
	public void setUser(UserProfile user) {
		this.user = user;
	}

	/**
	 * @return 返回  {@link #serialNumberNo}。
	 */
	public String getSerialNumberNo() {
		return serialNumberNo;
	}

	/**
	 * @param serialNumberNo 要设置的 {@link #serialNumberNo}。
	 */
	public void setSerialNumberNo(String serialNumberNo) {
		this.serialNumberNo = serialNumberNo;
	}

}
