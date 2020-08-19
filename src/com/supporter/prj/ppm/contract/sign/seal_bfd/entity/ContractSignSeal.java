package com.supporter.prj.ppm.contract.sign.seal_bfd.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.contract.sign.seal_bfd.entity.base.BaseContractSignSeal;

/**
 * @Title: Entity
 * @Description: 主合同出版.
 * @author: YAN
 * @date: 2019-09-10
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_SIGN_SEAL", schema = "")
public class ContractSignSeal extends BaseContractSignSeal implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractSignSeal() {
		super();
	}

	/**
	 * 构造函数.
	 * @param signSealId
	 */
	public ContractSignSeal(String signSealId) {
		super(signSealId);
	}

	List<ContractSignSealBfd> bfds ;
	@Transient
	public List<ContractSignSealBfd> getBfds() {
		return bfds;
	}

	public void setBfds(List<ContractSignSealBfd> bfds) {
		this.bfds = bfds;
	}
	private String prjNo;//项目编码
	private String prjCName;//项目中文名称
	private String prjEName;//项目英文名称
	@Transient
	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}
	@Transient
	public String getPrjCName() {
		return prjCName;
	}

	public void setPrjCName(String prjCName) {
		this.prjCName = prjCName;
	}
	@Transient
	public String getPrjEName() {
		return prjEName;
	}

	public void setPrjEName(String prjEName) {
		this.prjEName = prjEName;
	}
}
