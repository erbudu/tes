/**
 * 
 */
package com.supporter.prj.ppm.poa.use_seal.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.poa.use_seal.constant.UseSealConstant;																   
import com.supporter.prj.ppm.poa.use_seal.entity.base.BaseUseSealStartEntity;

/**
 *<p>Title: 用印业务表单实体类扩展类</p>
 *<p>Description: 扩展功能-字段不记录到数据表中</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月12日
 */
@Entity
@Table(name="PPM_USE_SEAL",schema="")
public class UseSealStartEntity extends BaseUseSealStartEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Transient
	private boolean isNew;//用于标识是否新建状态
	
	@Transient
	private String useSealFileGrid;//用印文件列表
	
	
	
	/**
	 * @return the useSealFileGrid
	 */
	public String getUseSealFileGrid() {
		return useSealFileGrid;
	}

	/**
	 * @param useSealFileGrid the useSealFileGrid to set
	 */
	public void setUseSealFileGrid(String useSealFileGrid) {
		this.useSealFileGrid = useSealFileGrid;
	}

	/**
	 * @return the isNew
	 */
	public boolean getIsNew() {
		return isNew;
	}

	/**
	 * @param isNew the isNew to set
	 */
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	/**
	 * This method is constructor
	 */
	public UseSealStartEntity() {
		
	}
	
	/**
	 * This method is constructor
	 */
	public UseSealStartEntity(String useSealId) {
		super(useSealId);
	}
	
	/** The following is process variable*/
	
	
	@Transient
	public int getDbYear(){
		return 0;
	}
	
	@Transient
	public String getModuleId(){//应用编号
		return com.supporter.prj.ppm.poa.use_seal.constant.UseSealConstant.MODULE_ID;
	}
	
	@Transient
	public String getDomainObjectId(){//业务对象
		return com.supporter.prj.ppm.poa.use_seal.constant.UseSealConstant.DOMAIN_OBJECT_ID;
		
	}
	
	@Transient
	public String getEntityId(){//业务主键
		return this.getId();
	}
	
	@Transient
	public String getId() {//业务主键
		return this.getUseSealId();
	}
	
	@Transient
	public String getCompanyNo(){//公司编码
		
		return this.getDeptId();
	}
	
	@Transient
	public String getModuleBusiType(){//应用业务类型
		return "";
	}
	
	@Transient
	public String getEntityName(){//业务实体类名称
		return this.getClass().getName();
	}
	@Transient
	public String getStatusName() {
		switch(this.getStatus()) {
			case 0 :
				return UseSealConstant.DRAFT_NAME;
			case 1 :
				return UseSealConstant.ONGOING_NAME;
			case 2 :
				return UseSealConstant.COMPLETE_NAME;
			case 3 :
				return UseSealConstant.ABORT_NAME;
			case 4 :
				return UseSealConstant.UPLOAD_NAME;
		}
		return null;
	}	   
}
