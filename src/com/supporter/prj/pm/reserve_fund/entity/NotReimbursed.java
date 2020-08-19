package com.supporter.prj.pm.reserve_fund.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.pm.reserve_fund.entity.base.BaseNotReimbursed;

@Entity
@Table(name = "pm_not_reimbursed", catalog = "")
public class NotReimbursed extends BaseNotReimbursed{	
	public NotReimbursed() {
		super();
	}
}
