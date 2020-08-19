package com.supporter.prj.linkworks.oa.bank_account.entity;


import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.bank_account.entity.base.BaseBankAccountOpenApplyEffec;
import com.supporter.util.CodeTable;



/**
 * BankAccountOpenApplyEffec entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BANK_ACCOUNT_OPEN_APPLY_EFFEC"
    ,schema="SUPP_APP"
)

public class BankAccountOpenApplyEffec  extends BaseBankAccountOpenApplyEffec {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	//public static final int DRAFT = 0; //草稿
//    //public static final int PROCESSING = 1; //开立审批中 
//    //public static final int COMPLETED = 2; //开立审批完成 
//    public static final int COMPLETED_NORE = 1; //开立完成未备案
//    public static final int COMPLETED_RE = 2; //开立完成已备案 
//    //public static final int PROCESSING_CHAN = 5; //变更审批中 
//    //public static final int COMPLETED_CHAN = 6; //变更审批完成 
//    public static final int COMPLETED_CHAN_NORE = 3; //变更完成未备案
//    public static final int COMPLETED_CHAN_Re = 4; //变更完成已备案
//    //public static final int PROCESSING_REVOKE = 9; //撤销审批中 
//    //public static final int COMPLETED_REVOKE = 10; //撤销审批完成 
//    public static final int REVOKE = 5; //撤销
    
    public static final int COMPLETED_RE =1; //开立完成已备案 
    public static final int PROCESSING_CHAN = 2; //变更审批中 
    public static final int COMPLETED_CHAN_NORE = 3; //变更完成未备案
    public static final int COMPLETED_CHAN_Re = 4; //变更完成已备案
    public static final int PROCESSING_REVOKE = 5; //撤销审批中 
    public static final int REVOKE = 6; //已撤销
    /**
	 * 获取报告的状态码表.
	 * @return
	 */
	public static CodeTable getStatusDescCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(COMPLETED_RE, "开立完成已备案");
		lcdtbl_Return.insertItem(PROCESSING_CHAN, "变更审批中");
		lcdtbl_Return.insertItem(COMPLETED_CHAN_NORE, "变更完成未备案");
		lcdtbl_Return.insertItem(COMPLETED_CHAN_Re, "变更完成已备案");
		lcdtbl_Return.insertItem(PROCESSING_REVOKE, "撤销审批中");
		lcdtbl_Return.insertItem(REVOKE, "已撤销");
		return lcdtbl_Return;
	}
	
	public static Map<Integer, String> getStatusCodeTable(){
    	Map<Integer, String> map = new LinkedHashMap<Integer, String>();
    	map.put(0, "--请选择--");
    	map.put(COMPLETED_RE, "开立完成已备案");
    	map.put(PROCESSING_CHAN, "变更审批中");
    	map.put(COMPLETED_CHAN_NORE, "变更完成未备案");
    	map.put(COMPLETED_CHAN_Re, "变更完成已备案");
    	map.put(PROCESSING_REVOKE, "撤销审批中");
    	map.put(REVOKE, "已撤销");
		return map;
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

	@Transient
	private String colorOfLight;

	@Transient
	public String getColorOfLight() {
		return colorOfLight;
	}
	@Transient
	public void setColorOfLight(String colorOfLight) {
		this.colorOfLight = colorOfLight;
	}

   
	private String dateFrom;
	private String dateTo;
	@Transient
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	@Transient
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}


}