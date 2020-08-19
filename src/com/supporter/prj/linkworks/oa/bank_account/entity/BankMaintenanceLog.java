package com.supporter.prj.linkworks.oa.bank_account.entity;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.bank_account.entity.base.BaseBankMaintenanceLog;

/**
 * BankAccountChanApplyRecord entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BANK_MAINTENANCE_LOG"
    ,schema=""
)

public class BankMaintenanceLog  extends BaseBankMaintenanceLog {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
    private boolean add;

    @Transient
	public boolean getAdd() {
		return add;
	}
    @Transient
	public void setAdd(boolean add) {
		this.add = add;
	}
}