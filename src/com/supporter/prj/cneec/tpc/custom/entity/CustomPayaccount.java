package com.supporter.prj.cneec.tpc.custom.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.custom.entity.base.BaseCustomLinkman;
import com.supporter.prj.cneec.tpc.custom.entity.base.BaseCustomPayaccount;
import com.supporter.util.CodeTable;

/**
 * @Title: Entity
 * @Description: TPC_CUSTOM_PAYACCOUNT.
 * @author: liuermeng
 * @date: 2018-09-05
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_CUSTOM_PAYACCOUNT", schema = "")
public class CustomPayaccount  extends BaseCustomPayaccount {

	private static final long serialVersionUID = 1L;
	
	private String other;
	@Transient
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	
	private boolean isNew;
	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	
	

}
