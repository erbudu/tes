package com.supporter.prj.ppm.prj_org.dev_org.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * the PpmPrjDeOrgCoop entity.
 * @author MyEclipse Persistence Tools
 */

@MappedSuperclass
public class BasePrjDeOrgCoop  implements java.io.Serializable {

	    // Fields
		private static final long serialVersionUID = 1L;
		private String coopId;
	     private String prjId;
	     private String coopCName;
	     private String coopEName;
	     private String coopRoleId;
	     private String coopRole;
	     private Long displayOrder;


	    // Constructors
	    /** default constructor */
	    public BasePrjDeOrgCoop() {
	    }

	    
	    /** full constructor */
	    public BasePrjDeOrgCoop(String prjId, String coopCName, String coopEName, String coopRoleId, String coopRole, Long displayOrder) {
	        this.prjId = prjId;
	        this.coopCName = coopCName;
	        this.coopEName = coopEName;
	        this.coopRoleId = coopRoleId;
	        this.coopRole = coopRole;
	        this.displayOrder = displayOrder;
	    }

	   
	    // Property accessors
	    @Id
	    @Column(name="COOP_ID", unique=true, nullable=false, length=32)

	    public String getCoopId() {
	        return this.coopId;
	    }
	    
	    public void setCoopId(String coopId) {
	        this.coopId = coopId;
	    }
	    
	    @Column(name="PRJ_ID", length=32)

	    public String getPrjId() {
	        return this.prjId;
	    }
	    
	    public void setPrjId(String prjId) {
	        this.prjId = prjId;
	    }
	    
	    @Column(name="COOP_C_NAME", length=256)

	    public String getCoopCName() {
	        return this.coopCName;
	    }
	    
	    public void setCoopCName(String coopCName) {
	        this.coopCName = coopCName;
	    }
	    
	    @Column(name="COOP_E_NAME", length=256)

	    public String getCoopEName() {
	        return this.coopEName;
	    }
	    
	    public void setCoopEName(String coopEName) {
	        this.coopEName = coopEName;
	    }
	    
	    @Column(name="COOP_ROLE_ID", length=32)

	    public String getCoopRoleId() {
	        return this.coopRoleId;
	    }
	    
	    public void setCoopRoleId(String coopRoleId) {
	        this.coopRoleId = coopRoleId;
	    }
	    
	    @Column(name="COOP_ROLE", length=256)

	    public String getCoopRole() {
	        return this.coopRole;
	    }
	    
	    public void setCoopRole(String coopRole) {
	        this.coopRole = coopRole;
	    }
	    
	    @Column(name="DISPLAY_ORDER", precision=11, scale=0)

	    public Long getDisplayOrder() {
	        return this.displayOrder;
	    }
	    
	    public void setDisplayOrder(Long displayOrder) {
	        this.displayOrder = displayOrder;
	    }
   
}