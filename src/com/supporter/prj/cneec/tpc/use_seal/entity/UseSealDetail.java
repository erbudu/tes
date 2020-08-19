package com.supporter.prj.cneec.tpc.use_seal.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.use_seal.entity.base.BaseUseSealDetail;
import com.supporter.prj.cneec.tpc.util.TpcConstant;

/**   
 * @Title: Entity
 * @Description: TPC_USE_SEAL_DETAIL.
 * @author Yanweichao
 * @date 2017-10-23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "TPC_USE_SEAL_DETAIL", schema = "")
public class UseSealDetail extends BaseUseSealDetail {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public UseSealDetail(){
		super();
	}

	/**
	 * 构造函数.
	 * @param detailId
	 */
	public UseSealDetail(String detailId) {
		super(detailId);
	}

	private boolean add;// 是否新增

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	/**
	 * 用印业务
	 * @return
	 */
	@Transient
	public String getUseSealBusinessDesc() {
		return TpcConstant.getUseSealTypeMap().get(this.getUseSealBusiness());
	}

	/**
	 * 是否备案
	 * @return
	 */
	@Transient
	public String getFilingDesc() {
		return this.isFiling() ? "是" : "否";
	}

}
