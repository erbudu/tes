package com.supporter.prj.cneec.data_migration.business_registration.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.data_migration.business_registration.entity.base.BaseBusinessRegistration;
import com.supporter.util.CodeTable;

@Entity
@Table(name = "OA_BUSINESS_REGISTRATION", schema = "")
public class BusinessRegistration extends BaseBusinessRegistration {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean add;
	private String businessTypeDesc;
	private String isEnabledDisplayDesc;

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	@Transient
	public String getBusinessTypeDesc() {
		String type = getBusinessType();
		if(type!=null){
			return getBusinessTypeCodeTable().getDisplay(getBusinessType());
		}
		return null;
	}

	public void setBusinessTypeDesc(String businessTypeDesc) {
		this.businessTypeDesc = businessTypeDesc;
	}

	@Transient
	public String getIsEnabledDisplayDesc() {
		if (getIsEnabled()!=null&&getIsEnabled().equals("Y")){
			return "是";
		}	
		return "否";
	}

	public void setIsEnabledDisplayDesc(String isEnabledDisplayDesc) {
		this.isEnabledDisplayDesc = isEnabledDisplayDesc;
	}
	public static final String NORMAL = "10";
	public static final String PRJ = "20";

	/**
	 * 获取业务类型码表.
	 */
	public static CodeTable getBusinessTypeCodeTable() {
		CodeTable lcdtb_Return = new CodeTable();
		lcdtb_Return.insertItem(NORMAL, "一般", 0);
		lcdtb_Return.insertItem(PRJ, "项目相关", 1);
		return lcdtb_Return;
	}
}
