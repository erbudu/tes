package com.supporter.prj.pm.reserve_fund.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.pm.reserve_fund.entity.base.BaseNotReimbursedRec;

@Entity
@Table(name = "pm_not_reimbursed_rec", catalog = "")
public class NotReimbursedRec extends BaseNotReimbursedRec{	
	public NotReimbursedRec() {
		super();
	}
}
