package com.supporter.prj.ppm.ecc.base_info.customer.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 出口管制客户,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@MappedSuperclass
public class BaseEccCustomer implements Serializable {

	private static final long serialVersionUID = 1L;
	private String customerId;
	private String customerCName;
	private String customerEName;
	private String customerCCofName;
	private String customerCCofId;
	private String customerECofId;
	private String customerECofName;
	private String customerCMbuc;
	private String customerEMbuc;
	private String customerCAdd;
	private String customerEAdd;
	private int customerAddE;
	private String eccId;

	@Column(name = "ECC_ID", nullable = true, length = 32)
	public String getEccId() {
		return eccId;
	}

	public void setEccId(String eccId) {
		this.eccId = eccId;
	}

	/**
	 * 无参构造函数.
	 */
	public BaseEccCustomer() {
	}

	/**
	 * 构造函数.
	 *
	 * @param customerId
	 */
	public BaseEccCustomer(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * 方法: 取得CUSTOMER_ID.
	 * @return: java.lang.String CUSTOMER_ID
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "CUSTOMER_ID", nullable = false, length = 32)
	public String getCustomerId() {
		return this.customerId;
	}

	/**
	 * 方法: 设置CUSTOMER_ID.
	 * @param: java.lang.String CUSTOMER_ID
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * 方法: 取得客户中文名.
	 * @return: java.lang.String 客户中文名
	 */
	@Column(name = "CUSTOMER_C_NAME", nullable = true, length = 256)
	public String getCustomerCName() {
		return this.customerCName;
	}

	/**
	 * 方法: 设置客户中文名.
	 * @param: java.lang.String 客户中文名
	 */
	public void setCustomerCName(String customerCName) {
		this.customerCName = customerCName;
	}

	/**
	 * 方法: 取得客户英文名.
	 * @return: java.lang.String 客户英文名
	 */
	@Column(name = "CUSTOMER_E_NAME", nullable = true, length = 256)
	public String getCustomerEName() {
		return this.customerEName;
	}

	/**
	 * 方法: 设置客户英文名.
	 * @param: java.lang.String 客户英文名
	 */
	public void setCustomerEName(String customerEName) {
		this.customerEName = customerEName;
	}

	/**
	 * 方法: 取得客户组织形式（中）.
	 * @return: java.lang.String 客户组织形式（中）
	 */
	@Column(name = "CUSTOMER_C_COF_NAME", nullable = true, length = 256)
	public String getCustomerCCofName() {
		return this.customerCCofName;
	}

	/**
	 * 方法: 设置客户组织形式（中）.
	 * @param: java.lang.String 客户组织形式（中）
	 */
	public void setCustomerCCofName(String customerCCofName) {
		this.customerCCofName = customerCCofName;
	}

	/**
	 * 方法: 取得客户组织形式（中）.
	 * @return: java.lang.String 客户组织形式（中）
	 */
	@Column(name = "CUSTOMER_C_COF_ID", nullable = true, length = 32)
	public String getCustomerCCofId() {
		return this.customerCCofId;
	}

	/**
	 * 方法: 设置客户组织形式（中）.
	 * @param: java.lang.String 客户组织形式（中）
	 */
	public void setCustomerCCofId(String customerCCofId) {
		this.customerCCofId = customerCCofId;
	}

	/**
	 * 方法: 取得客户组织形式（英）.
	 * @return: java.lang.String 客户组织形式（英）
	 */
	@Column(name = "CUSTOMER_E_COF_ID", nullable = true, length = 32)
	public String getCustomerECofId() {
		return this.customerECofId;
	}

	/**
	 * 方法: 设置客户组织形式（英）.
	 * @param: java.lang.String 客户组织形式（英）
	 */
	public void setCustomerECofId(String customerECofId) {
		this.customerECofId = customerECofId;
	}

	/**
	 * 方法: 取得客户组织形式（英）.
	 * @return: java.lang.String 客户组织形式（英）
	 */
	@Column(name = "CUSTOMER_E_COF_NAME", nullable = true, length = 256)
	public String getCustomerECofName() {
		return this.customerECofName;
	}

	/**
	 * 方法: 设置客户组织形式（英）.
	 * @param: java.lang.String 客户组织形式（英）
	 */
	public void setCustomerECofName(String customerECofName) {
		this.customerECofName = customerECofName;
	}

	/**
	 * 方法: 取得客户主营业务（中）.
	 * @return: java.lang.String 客户主营业务（中）
	 */
	@Column(name = "CUSTOMER_C_MBUC", nullable = true, length = 256)
	public String getCustomerCMbuc() {
		return this.customerCMbuc;
	}

	/**
	 * 方法: 设置客户主营业务（中）.
	 * @param: java.lang.String 客户主营业务（中）
	 */
	public void setCustomerCMbuc(String customerCMbuc) {
		this.customerCMbuc = customerCMbuc;
	}

	/**
	 * 方法: 取得客户主营业务（英）.
	 * @return: java.lang.String 客户主营业务（英）
	 */
	@Column(name = "CUSTOMER_E_MBUC", nullable = true, length = 256)
	public String getCustomerEMbuc() {
		return this.customerEMbuc;
	}

	/**
	 * 方法: 设置客户主营业务（英）.
	 * @param: java.lang.String 客户主营业务（英）
	 */
	public void setCustomerEMbuc(String customerEMbuc) {
		this.customerEMbuc = customerEMbuc;
	}

	/**
	 * 方法: 取得客户注册地（中）.
	 * @return: java.lang.String 客户注册地（中）
	 */
	@Column(name = "CUSTOMER_C_ADD", nullable = true, length = 512)
	public String getCustomerCAdd() {
		return this.customerCAdd;
	}

	/**
	 * 方法: 设置客户注册地（中）.
	 * @param: java.lang.String 客户注册地（中）
	 */
	public void setCustomerCAdd(String customerCAdd) {
		this.customerCAdd = customerCAdd;
	}

	/**
	 * 方法: 取得客户注册地（英）.
	 * @return: java.lang.String 客户注册地（英）
	 */
	@Column(name = "CUSTOMER_E_ADD", nullable = true, length = 512)
	public String getCustomerEAdd() {
		return this.customerEAdd;
	}

	/**
	 * 方法: 设置客户注册地（英）.
	 * @param: java.lang.String 客户注册地（英）
	 */
	public void setCustomerEAdd(String customerEAdd) {
		this.customerEAdd = customerEAdd;
	}

	/**
	 * 方法: 取得客户地址是否敏感.
	 * @return: int 客户地址是否敏感
	 */
	@Column(name = "CUSTOMER_ADD_E", nullable = true, precision = 10)
	public int getCustomerAddE() {
		return this.customerAddE;
	}

	/**
	 * 方法: 设置客户地址是否敏感.
	 * @param: int 客户地址是否敏感
	 */
	public void setCustomerAddE(int customerAddE) {
		this.customerAddE = customerAddE;
	}

}
