/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.preparation.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.bid_startup.preparation.entity.base.BaseBFD_FEntity;

/**
 *<p>Title: BFD_FEntity</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年8月27日
 * 
 */
@Entity
@Table(name="PPM_BIDING_STARTUP_BFD_F",schema = "")
public class BFD_FEntity extends BaseBFD_FEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BFD_FEntity() {
		
	}

	public BFD_FEntity(String recordId) {
		super(recordId);
	}

}
