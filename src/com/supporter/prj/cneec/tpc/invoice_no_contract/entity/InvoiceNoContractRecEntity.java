package com.supporter.prj.cneec.tpc.invoice_no_contract.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.invoice_no_contract.constant.InvoiceNoContractConstant;
import com.supporter.prj.cneec.tpc.invoice_no_contract.entity.base.BaseInvoiceNoContractRecEntity;

/**
 * 非合同付款收发票明细实体类
 * @Title: InvoiceNoContractRecEntity
 * @author CHENHAO
 *
 */

@Entity
@Table(name = "TPC_INVOICE_NOCONTRACT_REC", schema = "")
public class InvoiceNoContractRecEntity extends BaseInvoiceNoContractRecEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 用于记录单号是来自那儿的
	 */
	@Transient
	private String paymentType;
	
	/**
	 * 用于标记是否是查看页面  true：是，false：否
	 */
	@Transient
	private boolean isView;
	
	/**
	 * 付款状态显示名称
	 */
	@Transient
	public String getIsEndUpDisplayName() {
		if(this.getIsEndUp() != null) {
			if(this.getIsEndUp() == InvoiceNoContractConstant.END_UP) {
				return InvoiceNoContractConstant._END_UP;
			}else if(this.getIsEndUp() == InvoiceNoContractConstant.NO_END_UP) {
				return InvoiceNoContractConstant._NO_END_UP;
			}
		}
		return null;
	}

	public boolean getIsView() {
		return isView;
	}

	public void setIsView(boolean isView) {
		this.isView = isView;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	
}
