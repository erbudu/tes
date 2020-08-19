package com.supporter.prj.cneec.tpc.supplier.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.supplier.entity.base.BaseSupplierBankAccount;

/**
 * TpcSupplierBankAccount entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TPC_SUPPLIER_BANK_ACCOUNT", schema = "SUPP_APP")
public class SupplierBankAccount extends BaseSupplierBankAccount
		implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public SupplierBankAccount() {
	}

	/** minimal constructor */
	public SupplierBankAccount(String id) {
		super(id);
	}

	/** full constructor */
	public SupplierBankAccount(String id, String supplierId,
			String gatheringUnit, String bank, String bankAccount,
			String bankCode, String remitProvince, String remitProvinceId,
			String remitCity, String remitCityId, String createdBy,
			String createdById, String createdDate, String modifiedBy,
			String modifiedById, String modifiedDate) {
		super(id, supplierId, gatheringUnit, bank, bankAccount, bankCode,
				remitProvince, remitProvinceId, remitCity, remitCityId,
				createdBy, createdById, createdDate, modifiedBy, modifiedById,
				modifiedDate);
	}
	
	private boolean isNew;
	
	@Transient
	public boolean getIsNew(){
		return this.isNew;
	}
	
	public void setIsNew(boolean isNew){
		this.isNew = isNew;
	}
}
