package com.supporter.prj.cneec.tpc.contract_sign_review.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: TPC_CONTRACT_SIGN_OPINION,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2017-09-28
 * @version V1.0
 * 
 */
@MappedSuperclass
public class BaseContractSignOpinion implements Serializable {

	private static final long serialVersionUID = 1L;
	private String opinionId;
	private String signId;
	private String opinionMan;
	private String opinions;
	private int displayOrder;

	/**
	 * 无参构造函数.
	 */
	public BaseContractSignOpinion() {
	}

	/**
	 * 构造函数.
	 * 
	 * @param opinionId
	 */
	public BaseContractSignOpinion(String opinionId) {
		this.opinionId = opinionId;
	}

	/**
	 * GET方法: 取得OPINION_ID.
	 * 
	 * @return: String OPINION_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "OPINION_ID", nullable = false, length = 32)
	public String getOpinionId() {
		return this.opinionId;
	}

	/**
	 * SET方法: 设置OPINION_ID.
	 * 
	 * @param: String OPINION_ID
	 */
	public void setOpinionId(String opinionId) {
		this.opinionId = opinionId;
	}

	/**
	 * GET方法: 取得SIGN_ID.
	 * 
	 * @return: String SIGN_ID
	 */
	@Column(name = "SIGN_ID", nullable = true, length = 32)
	public String getSignId() {
		return this.signId;
	}

	/**
	 * SET方法: 设置SIGN_ID.
	 * 
	 * @param: String SIGN_ID
	 */
	public void setSignId(String signId) {
		this.signId = signId;
	}

	/**
	 * GET方法: 取得OPINION_MAN.
	 * 
	 * @return: String OPINION_MAN
	 */
	@Column(name = "OPINION_MAN", nullable = true, length = 64)
	public String getOpinionMan() {
		return this.opinionMan;
	}

	/**
	 * SET方法: 设置OPINION_MAN.
	 * 
	 * @param: String OPINION_MAN
	 */
	public void setOpinionMan(String opinionMan) {
		this.opinionMan = opinionMan;
	}

	/**
	 * GET方法: 取得OPINIONS.
	 * 
	 * @return: String OPINIONS
	 */
	@Column(name = "OPINIONS", nullable = true, length = 128)
	public String getOpinions() {
		return this.opinions;
	}

	/**
	 * SET方法: 设置OPINIONS.
	 * 
	 * @param: String OPINIONS
	 */
	public void setOpinions(String opinions) {
		this.opinions = opinions;
	}

	/**
	 * GET方法: 取得DISPLAY_ORDER.
	 * 
	 * @return: int DISPLAY_ORDER
	 */
	@Column(name = "DISPLAY_ORDER", nullable = true, precision = 10)
	public int getDisplayOrder() {
		return this.displayOrder;
	}

	/**
	 * SET方法: 设置DISPLAY_ORDER.
	 * 
	 * @param: int DISPLAY_ORDER
	 */
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

}
