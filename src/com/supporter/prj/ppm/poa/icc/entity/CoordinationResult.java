package com.supporter.prj.ppm.poa.icc.entity;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.poa.icc.entity.base.BaseCoordinationResult;
@Entity
@Table(name = "ppm_icc_result",schema="")
public class CoordinationResult   extends BaseCoordinationResult{
	private static final long serialVersionUID = 1L;
	private boolean isNew=true;
	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
}
