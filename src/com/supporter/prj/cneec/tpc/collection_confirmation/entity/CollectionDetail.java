package com.supporter.prj.cneec.tpc.collection_confirmation.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.collection_confirmation.entity.base.BaseCollectionDetail;

/**
 * @Title: Entity
 * @Description: TPC_COLLECTION_DETAIL.
 * @author Yanweichao
 * @date 2017-11-17
 * @version V1.0
 */
@Entity
@Table(name = "TPC_COLLECTION_DETAIL", schema = "")
public class CollectionDetail extends BaseCollectionDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public CollectionDetail(){
		super();
	}

	/**
	 * 构造函数.
	 * @param detailId
	 */
	public CollectionDetail(String detailId) {
		super(detailId);
	}

	private boolean add;

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	/**
	 * 收款明细已退金额(收款金额-实际收款金额)
	 * @return
	 */
	@Transient
	public double getRefundAmount() {
		return this.getCollectionAmount() - this.getRealCollectionAmount();
	}

	/**
	 * 收款明细可退金额(实际收款金额-在途金额)
	 * @return
	 */
	@Transient
	public double getRefundableAmount() {
		return this.getRealCollectionAmount() - this.getOnwayAmount();
	}

	/**
	 * 是否可退款（可退金额大于0）
	 * @return
	 */
	@Transient
	public boolean canRefund() {
		return this.getRefundableAmount() > 0;
	}

}
