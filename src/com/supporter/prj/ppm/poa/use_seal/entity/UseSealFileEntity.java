/**
 * 
 */
package com.supporter.prj.ppm.poa.use_seal.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.poa.use_seal.constant.UseSealConstant;
import com.supporter.prj.ppm.poa.use_seal.entity.base.BaseUseSealFileEntity;

/**
 *<p>Title: 用印文件实体类-扩展类</p>
 *<p>Description: 字段信息不在数据库做记录</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月17日
 * 
 */
@Entity
@Table(name="PPM_USE_SEAL_FILE",schema="")
public class UseSealFileEntity extends BaseUseSealFileEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * This method is constructor
	 */
	public UseSealFileEntity() {
		
	}
	
	/**
	 * This method is constructor
	 */
	public UseSealFileEntity(String sealFileId) {
		super(sealFileId);
	}
	
	public String getUseSealKindDisplayName() {
		return UseSealConstant.getUseSealTypeDisplam(this.getUseSealKind());
	}
}
