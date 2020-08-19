package com.supporter.prj.ppm.ecc.base_info.partner.entity.base;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 出口管制项目合同伙伴办事处,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@MappedSuperclass
public class BaseEccPartnerFso implements Serializable {

	private static final long serialVersionUID = 1L;
	private String fsoId;
	private String partnerId;
	private String fsoCName;
	private String fsoEName;
	private String fsoCAdd;
	private String fsoEAdd;
	private int fsoAddE;

	/**
	 * 无参构造函数.
	 */
	public BaseEccPartnerFso() {
	}

	/**
	 * 构造函数.
	 *
	 * @param fsoId
	 */
	public BaseEccPartnerFso(String fsoId) {
		this.fsoId = fsoId;
	}

	/**
	 * 方法: 取得FSO_ID.
	 * @return: java.lang.String FSO_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "FSO_ID", nullable = false, length = 32)
	public String getFsoId() {
		return this.fsoId;
	}

	/**
	 * 方法: 设置FSO_ID.
	 * @param: java.lang.String FSO_ID
	 */
	public void setFsoId(String fsoId) {
		this.fsoId = fsoId;
	}

	/**
	 * 方法: 取得合作伙伴id.
	 * @return: java.lang.String 合作伙伴id
	 */
	@Column(name = "PARTNER_ID", nullable = true, length = 32)
	public String getPartnerId() {
		return this.partnerId;
	}

	/**
	 * 方法: 设置合作伙伴id.
	 * @param: java.lang.String 合作伙伴id
	 */
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	/**
	 * 方法: 取得办事处中文名称.
	 * @return: java.lang.String 办事处中文名称
	 */
	@Column(name = "FSO_C_NAME", nullable = true, length = 128)
	public String getFsoCName() {
		return this.fsoCName;
	}

	/**
	 * 方法: 设置办事处中文名称.
	 * @param: java.lang.String 办事处中文名称
	 */
	public void setFsoCName(String fsoCName) {
		this.fsoCName = fsoCName;
	}

	/**
	 * 方法: 取得办事处英文名称.
	 * @return: java.lang.String 办事处英文名称
	 */
	@Column(name = "FSO_E_NAME", nullable = true, length = 128)
	public String getFsoEName() {
		return this.fsoEName;
	}

	/**
	 * 方法: 设置办事处英文名称.
	 * @param: java.lang.String 办事处英文名称
	 */
	public void setFsoEName(String fsoEName) {
		this.fsoEName = fsoEName;
	}

	/**
	 * 方法: 取得办事处中文地址.
	 * @return: java.lang.String 办事处中文地址
	 */
	@Column(name = "FSO_C_ADD", nullable = true, length = 256)
	public String getFsoCAdd() {
		return this.fsoCAdd;
	}

	/**
	 * 方法: 设置办事处中文地址.
	 * @param: java.lang.String 办事处中文地址
	 */
	public void setFsoCAdd(String fsoCAdd) {
		this.fsoCAdd = fsoCAdd;
	}

	/**
	 * 方法: 取得办事处英文地址.
	 * @return: java.lang.String 办事处英文地址
	 */
	@Column(name = "FSO_E_ADD", nullable = true, length = 256)
	public String getFsoEAdd() {
		return this.fsoEAdd;
	}

	/**
	 * 方法: 设置办事处英文地址.
	 * @param: java.lang.String 办事处英文地址
	 */
	public void setFsoEAdd(String fsoEAdd) {
		this.fsoEAdd = fsoEAdd;
	}

	/**
	 * 方法: 取得k客户地址是否是敏感地区 0不是 1是.
	 * @return: int k客户地址是否是敏感地区 0不是 1是
	 */
	@Column(name = "FSO_ADD_E", nullable = true, precision = 10)
	public int getFsoAddE() {
		return this.fsoAddE;
	}

	/**
	 * 方法: 设置k客户地址是否是敏感地区 0不是 1是.
	 * @param: int k客户地址是否是敏感地区 0不是 1是
	 */
	public void setFsoAddE(int fsoAddE) {
		this.fsoAddE = fsoAddE;
	}

}
