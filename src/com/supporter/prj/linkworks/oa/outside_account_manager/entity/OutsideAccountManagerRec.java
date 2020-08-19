package com.supporter.prj.linkworks.oa.outside_account_manager.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.linkworks.oa.outside_account_manager.entity.base.BaseOutsideAccountManagerRec;

/**
 * OutsideAccountManagerRec entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OA_OUTSIDE_ACCOUNT_MANAGER_REC", schema = "SUPP_APP")
public class OutsideAccountManagerRec extends
		BaseOutsideAccountManagerRec implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public OutsideAccountManagerRec() {
	}

	/** minimal constructor */
	public OutsideAccountManagerRec(String id) {
		super(id);
	}

	/** full constructor */
	public OutsideAccountManagerRec(String id, String managerId,
			String outsidePersonId, String name, String nameSpell, String sex,
			String deptId, String deptName, String userAccount, String mail,
			String lastTimeStatus, String thisTimeStatus) {
		super(id, managerId, outsidePersonId, name, nameSpell, sex, deptId,
				deptName, userAccount, mail, thisTimeStatus);
	}

}
