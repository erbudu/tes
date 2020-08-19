package com.supporter.prj.ppm.contract.effect.seal_bfd.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.contract.effect.seal_bfd.entity.base.BaseContractEffectSeal;

/**
 * @Title: Entity
 * @Description: 主合同出版.
 * @author: YAN
 * @date: 2019-09-10
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_EFFECT_SEAL", schema = "")
public class ContractEffectSeal extends BaseContractEffectSeal implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractEffectSeal() {
		super();
	}

	/**
	 * 构造函数.
	 * @param effectSealId
	 */
	public ContractEffectSeal(String effectSealId) {
		super(effectSealId);
	}

	List<ContractEffectSealBfd> bfds ;
	@Transient
	public List<ContractEffectSealBfd> getBfds() {
		return bfds;
	}

	public void setBfds(List<ContractEffectSealBfd> bfds) {
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
