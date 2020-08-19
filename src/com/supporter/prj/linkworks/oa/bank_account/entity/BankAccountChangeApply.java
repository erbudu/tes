package com.supporter.prj.linkworks.oa.bank_account.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.supporter.prj.linkworks.oa.bank_account.entity.base.BaseBankAccountChangeApply;
import com.supporter.util.CodeTable;



/**
 * BankAccountChangeApply entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BANK_ACCOUNT_CHANGE_APPLY"
    ,schema=""
)

public class BankAccountChangeApply  extends BaseBankAccountChangeApply {


    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int DRAFT = 0; //草稿
    public static final int PROCESSING = 1; //审批中 
    public static final int COMPLETED = 2; //审批完成
    public static final int COMPLETED_NORE = 3; //变更完成未备案
    public static final int COMPLETED_RE = 4; //变更完成已备案   
    public static final int DRAFT_REVO = 5; //草稿
    public static final int PROCESSING_REVO = 6; //审批中
    public static final int COMPLETED_REVO = 7; //审批完成   
    public static final int CMEC_NO_PASS = 8; //CMEC审批未通过 
    
    public static final int CMEC_ADOPT = 1; //报CMEC审批通过 
    public static final int CMEC_FAIL = 0; //报CMEC审批未通过 
    /**
	 * 获取报告的状态码表.
	 * @return
	 */
	public static CodeTable getStatusDescCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(DRAFT, "草稿");
		lcdtbl_Return.insertItem(PROCESSING, "审批中");
		//lcdtbl_Return.insertItem(COMPLETED, "审批完成");
		lcdtbl_Return.insertItem(COMPLETED_NORE, "变更完成未备案");
		lcdtbl_Return.insertItem(COMPLETED_RE, "变更完成已备案");
		lcdtbl_Return.insertItem(DRAFT_REVO, "草稿");
		lcdtbl_Return.insertItem(PROCESSING_REVO, "审批中");
		lcdtbl_Return.insertItem(COMPLETED_REVO, "审批完成");		
		lcdtbl_Return.insertItem(CMEC_NO_PASS, "CMEC审批未通过");
		return lcdtbl_Return;
	}
	
	public static CodeTable getCmecResultCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(CMEC_ADOPT, "报CMEC审批通过");
		lcdtbl_Return.insertItem(CMEC_FAIL, "报CMEC审批未通过");
		return lcdtbl_Return;
	}
    
	// 报CMEC审批结果描述
	@Transient
	public String getCmecResultDesc() {
		Integer cmecRusult = this.getCmecResult();
		if (cmecRusult != null && this.getStatus() != DRAFT && this.getStatus() != PROCESSING ) {
			return getCmecResultCodeTable().getDisplay(this.getCmecResult());
		}else {
			return "等待CMEC审批结果";
		}
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
	private String revokeId;
	@Transient
    public String getRevokeId() {
        return this.revokeId;
    }
	@Transient
    public void setRevokeId(String revokeId) {
        this.revokeId = revokeId;
    }
	
	
	
	
	/**
	 *   声明流程用到的参数
	 */
	
	@Transient
	public int getDbYear()
    {
        return 0;
    }
	@Transient
    public String getDomainObjectId()
    {
        return "BAChanApply";
    }
    @Transient
    public String getEntityName()
    {
        return getClass().getName();
    }
    @Transient
    public String getModuleId()
    {
        return "BACHANAPPLY";
    }
    @Transient
    public String getModuleBusiType()
    {
        return "";
    }
	@Transient
	public String getCompanyNo() {
		return this.getAccountOpenerId();
	}

	private String procTitle;
	@Transient
	public String getProcTitle() {
		String prjName=this.getProjectName();
		String accountNature=this.getAccountNature();//账户性质
		if(prjName!=null&&!prjName.equals("")&&accountNature!=null&&!accountNature.equals("")){
			procTitle=prjName+"的"+accountNature+"性质的银行账户变更申请";			
		}else if(accountNature!=null&&!accountNature.equals("")){
			procTitle=accountNature+"性质的银行账户变更申请";
		}else{
			procTitle="银行账户变更申请";
		}
		return procTitle;
	}

	public void setProcTitle(String procTitle) {
		this.procTitle = procTitle;
	}   



}