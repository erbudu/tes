package com.supporter.prj.cneec.data_migration.dept_data_migration.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.data_migration.business_registration.entity.BusinessRegistration;
import com.supporter.prj.cneec.data_migration.dept_data_migration.entity.base.BaseDeptBusiness;

@Entity
@Table(name = "OA_DEPT_BUSINESS", schema = "")
public class DeptBusiness extends BaseDeptBusiness {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private String businessTypeDisplay;

	public String getBusinessTypeDisplay() {
		String type = getBusinessType();
		if (type != null) {
			return BusinessRegistration.getBusinessTypeCodeTable().getDisplay(
					type);
		}
		return null;
	}

	public void setBusinessTypeDisplay(String businessTypeDisplay) {
		this.businessTypeDisplay = businessTypeDisplay;
	}

}
