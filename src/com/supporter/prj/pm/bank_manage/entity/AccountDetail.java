package com.supporter.prj.pm.bank_manage.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.pm.bank_manage.entity.base.BaseAccountDetail;

@Entity
@Table(name = "pm_bank_account_detail", schema = "")
public class AccountDetail extends BaseAccountDetail{

	public AccountDetail() {
		super();
	}
	
	public AccountDetail(String id, String accountId,String currency,
			Date date, String nature, double amount) {
		super( id,  accountId, currency,date,nature,amount);
	}
}
