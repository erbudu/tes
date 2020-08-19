package com.supporter.prj.linkworks.oa.bank_account.entity;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.bank_account.entity.base.BaseBankCooperativeCont;
import com.supporter.util.CodeTable;

/**
 * BankCooperativeCont entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BANK_COOPERATIVE_CONT"
    ,schema=""
)

public class BankCooperativeCont  extends BaseBankCooperativeCont{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static final int DRAFT = 0; //草稿
    public static final int EFFECTIVE = 1; //生效 
    
    
    /**
	 * 获取报告的状态码表.
	 * @return
	 */
	public static CodeTable getStatusDescCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(DRAFT, "草稿");
		lcdtbl_Return.insertItem(EFFECTIVE, "生效");
		return lcdtbl_Return;
	}
	
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
    
	public String statusDesc;

	@Transient
	public String getStatusDesc() {
		return getStatusDescCodeTable().getDisplay(this.getStatus());
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}







}