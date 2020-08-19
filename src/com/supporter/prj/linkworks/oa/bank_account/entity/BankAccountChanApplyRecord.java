package com.supporter.prj.linkworks.oa.bank_account.entity;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.bank_account.entity.base.BaseBankAccountChanApplyRecord;

/**
 * BankAccountChanApplyRecord entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BANK_ACCOUNT_CHAN_APPLY_RECORD"
    ,schema=""
)

public class BankAccountChanApplyRecord  extends BaseBankAccountChanApplyRecord {


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
    //国别（用于显示或者隐藏有效签字和授权印鉴的上传控件）
    @Transient
    private String nationality;
    @Transient
    public String getNationality() {
        return this.nationality;
    }
    @Transient
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }	
}