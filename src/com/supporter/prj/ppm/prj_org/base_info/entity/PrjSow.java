package com.supporter.prj.ppm.prj_org.base_info.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.prj_org.base_info.entity.base.BasePrjSow;


/**
 * @Title: Entity
 * @Description: 
 * @author lidabin
 * 
 */
@Entity
@Table(name = "PPM_PRJ_SOW", schema = "")
public class PrjSow extends BasePrjSow {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数
	 */
	public PrjSow() {
		super();
	}

	


}
