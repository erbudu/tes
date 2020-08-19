package com.supporter.prj.pm.bank_manage.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.pm.bank_manage.entity.base.BaseBankAccount;
import com.supporter.util.CodeTable;

@Entity
@Table(name = "pm_bank_account", schema = "")
public class BankAccount extends BaseBankAccount{
	
	private static final long serialVersionUID = 1L;
	// 顶级TOP_MODULE_ID
	public static final String TOP_MODULE_ID = "0";	
	
	public static final String MODULE_ID = "BANKACCOUNT";

	public BankAccount() {
		super();
	}
	
	public BankAccount(String id, String bankAccount,String address,
			Date openTime, String bankAccountNo, String currency, String nature,
			String postalCode,String contacts,String tel,String email,String authorTerm,
			String reserveSeal,String phone,String manager,String responsibler,String remarks,
			String createdById,String createdBy,Date createdDate, String createdDeptId, String createdDept,
			String modifiedById, String modifiedBy, Date modifiedDate, double bankAmount, Integer isCancel,
			boolean isModified, String prjId, String prjName) {
		super(  id,  bankAccount, address,openTime,bankAccountNo,currency,nature,postalCode,
				contacts,  tel,  email,  authorTerm,reserveSeal,
				phone,manager,responsibler,remarks,createdById,createdBy,createdDate, createdDeptId, createdDept,
				modifiedById, modifiedBy, modifiedDate, bankAmount, isCancel, isModified, prjId, prjName);
	}
	
	private boolean isNew;

	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	// 状态
	public static final class StatusCodeTable {
		public static final int DRAFT = 0;
		public static final int EFFECT = 1;
		
		//状态
		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "正常");
			ct.insertItem(EFFECT, "作废");
			return ct;
		}
	}
	
	// 状态
	@Transient
	public String getIsCancelDesc() {
		if (getIsCancel() != null) {
			return StatusCodeTable.getCodeTable().getDisplay(getIsCancel());
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * 日志操作类型
	 * 
	 */
	public enum LogOper{
		BANKACCOUNT_ADD("新建银行账户"), BANKACCOUNT_EDIT("编辑银行账户"), BANKACCOUNT_DEL("删除银行账户");
		private String operName;
		LogOper(String operName){
			this.operName = operName;
		}
		public String getOperName() {
			return operName;
		}
		public void setOperName(String operName) {
			this.operName = operName;
		}
		
	}
}
