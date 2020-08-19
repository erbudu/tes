package com.supporter.prj.pm.contract_balance.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseContractBalanceVisa {

	private String id;
	private String balanceId;
	private String visaId;

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "balance_id", length = 32)
	public String getBalanceId() {
		return this.balanceId;
	}

	public void setBalanceId(String balanceId) {
		this.balanceId = balanceId;
	}

	@Column(name = "visa_id", length = 32)
	public String getVisaId() {
		return this.visaId;
	}

	public void setVisaId(String visaId) {
		this.visaId = visaId;
	}
}
