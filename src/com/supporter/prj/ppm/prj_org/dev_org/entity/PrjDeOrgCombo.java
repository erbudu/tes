package com.supporter.prj.ppm.prj_org.dev_org.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.prj_org.dev_org.constant.DevOrgConstant;
import com.supporter.prj.ppm.prj_org.dev_org.entity.base.BasePrjDeOrgCombo;


/**
 * @Title: Entity
 * @Description: 
 * @author lidabin
 * 
 */
@Entity
@Table(name = "PPM_PRJ_DE_ORG_COMBO", schema = "")
public class PrjDeOrgCombo extends BasePrjDeOrgCombo {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数
	 */
	public PrjDeOrgCombo() {
		super();
	}
	
	@Transient
	public String getIsMainSideDesc() {
		return DevOrgConstant.getIsMainSideName().get(this.getIsMainSide());
	}
}
