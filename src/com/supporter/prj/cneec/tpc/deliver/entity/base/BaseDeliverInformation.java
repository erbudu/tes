package com.supporter.prj.cneec.tpc.deliver.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**   
 * @Title: Entity
 * @Description: TPC_DELIVER_INFORMATION,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2017-12-20
 * @version V1.0   
 *
 */
@MappedSuperclass
public class BaseDeliverInformation implements Serializable {

	private static final long serialVersionUID = 1L;
	private String informationId;
	private String deliverId;
	private int serialNumber;
	private String deliveryTime;
	private String sendeeId;
	private String sendeeName;
	private String sendeeTel;
	private int status;

	/**
	 * 无参构造函数.
	 */
	public BaseDeliverInformation() {
	}

	/**
	 * 构造函数.
	 *
	 * @param informationId
	 */
	public BaseDeliverInformation(String informationId) {
		this.informationId = informationId;
	}

	/**
	 * GET方法: 取得INFORMATION_ID.
	 *
	 * @return: String  INFORMATION_ID
	 */
	@Id
	@Column(name = "INFORMATION_ID", nullable = false, length = 32)
	public String getInformationId() {
		return this.informationId;
	}

	/**
	 * SET方法: 设置INFORMATION_ID.
	 *
	 * @param: String  INFORMATION_ID
	 */
	public void setInformationId(String informationId) {
		this.informationId = informationId;
	}

	/**
	 * GET方法: 取得DELIVER_ID.
	 *
	 * @return: String  DELIVER_ID
	 */
	@Column(name = "DELIVER_ID", nullable = true, length = 32)
	public String getDeliverId() {
		return this.deliverId;
	}

	/**
	 * SET方法: 设置DELIVER_ID.
	 *
	 * @param: String  DELIVER_ID
	 */
	public void setDeliverId(String deliverId) {
		this.deliverId = deliverId;
	}

	/**
	 * GET方法: 取得SERIAL_NUMBER.
	 *
	 * @return: int  SERIAL_NUMBER
	 */
	@Column(name = "SERIAL_NUMBER", nullable = true, precision = 10)
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 * SET方法: 设置SERIAL_NUMBER.
	 *
	 * @param: int  SERIAL_NUMBER
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * GET方法: 取得DELIVERY_TIME.
	 *
	 * @return: String  DELIVERY_TIME
	 */
	@Column(name = "DELIVERY_TIME", nullable = true, length = 27)
	public String getDeliveryTime() {
		return deliveryTime;
	}

	/**
	 * SET方法: 设置DELIVERY_TIME.
	 *
	 * @param: String  DELIVERY_TIME
	 */
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	/**
	 * GET方法: 取得SENDEE_ID.
	 *
	 * @return: String  SENDEE_ID
	 */
	@Column(name = "SENDEE_ID", nullable = true, length = 32)
	public String getSendeeId() {
		return this.sendeeId;
	}

	/**
	 * SET方法: 设置SENDEE_ID.
	 *
	 * @param: String  SENDEE_ID
	 */
	public void setSendeeId(String sendeeId) {
		this.sendeeId = sendeeId;
	}

	/**
	 * GET方法: 取得SENDEE_NAME.
	 *
	 * @return: String  SENDEE_NAME
	 */
	@Column(name = "SENDEE_NAME", nullable = true, length = 32)
	public String getSendeeName() {
		return this.sendeeName;
	}

	/**
	 * SET方法: 设置SENDEE_NAME.
	 *
	 * @param: String  SENDEE_NAME
	 */
	public void setSendeeName(String sendeeName) {
		this.sendeeName = sendeeName;
	}

	/**
	 * GET方法: 取得SENDEE_TEL.
	 *
	 * @return: String  SENDEE_TEL
	 */
	@Column(name = "SENDEE_TEL", nullable = true, length = 32)
	public String getSendeeTel() {
		return this.sendeeTel;
	}

	/**
	 * SET方法: 设置SENDEE_TEL.
	 *
	 * @param: String  SENDEE_TEL
	 */
	public void setSendeeTel(String sendeeTel) {
		this.sendeeTel = sendeeTel;
	}

	@Column(name = "STATUS", nullable = true, precision = 10)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
