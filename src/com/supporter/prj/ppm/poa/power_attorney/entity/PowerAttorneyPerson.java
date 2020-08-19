package com.supporter.prj.ppm.poa.power_attorney.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.poa.power_attorney.entity.base.BasePowerAttorneyPerson;


/**   
 * @Title: Entity
 * @Description: 功能模块
 * @author guoxiansheng
 * @date 2019-09-25 09:59:54
 * @version V1.0   
 *
 */
@Entity
@Table(name="PPM_LETTER_AUTHORITY_PERSON",schema="")

public class PowerAttorneyPerson  extends BasePowerAttorneyPerson {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}