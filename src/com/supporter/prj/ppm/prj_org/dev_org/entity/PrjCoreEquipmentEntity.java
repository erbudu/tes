/**
 * 
 */
package com.supporter.prj.ppm.prj_org.dev_org.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.prj_org.dev_org.entity.base.BasePrjCoreEquipment;

/**
 * <pre>核心设备供应商：实体类-拓展类</pre>
 * @author YUEYUN
 * @date 2019年9月27日
 * 
 */
@Entity
@Table(name="PPM_CORE_EQUIPMENT")
public class PrjCoreEquipmentEntity extends BasePrjCoreEquipment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PrjCoreEquipmentEntity() {

	}

	public PrjCoreEquipmentEntity(String equipmentId) {
		super(equipmentId);
	}
}
