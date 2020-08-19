package com.supporter.prj.ppm.prj_org.dev_org.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * the PpmPrjDeOrgAgent entity.
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BasePrjDeOrgAgent  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	 // Fields

    /**
	 * 
	 */
	
	private String agentId;
    private String prjId;
    private String agentCName;
    private String agentEName;
    private String inOut;
    private Long displayOrder;


   // Constructors

   /** default constructor */
   public BasePrjDeOrgAgent() {
   }

   
   /** full constructor */
   public BasePrjDeOrgAgent(String prjId, String agentCName, String agentEName, String inOut, Long displayOrder) {
       this.prjId = prjId;
       this.agentCName = agentCName;
       this.agentEName = agentEName;
       this.inOut = inOut;
       this.displayOrder = displayOrder;
   }

  
   // Property accessors
   @Id
   @Column(name="AGENT_ID", unique=true, nullable=false, length=32)
   public String getAgentId() {
		return agentId;
	}


	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

  
   @Column(name="PRJ_ID", length=32)

   public String getPrjId() {
       return this.prjId;
   }
   
  


public void setPrjId(String prjId) {
       this.prjId = prjId;
   }
   
   @Column(name="AGENT_C_NAME", length=256)

   public String getAgentCName() {
       return this.agentCName;
   }
   
   public void setAgentCName(String agentCName) {
       this.agentCName = agentCName;
   }
   
   @Column(name="AGENT_E_NAME", length=256)

   public String getAgentEName() {
       return this.agentEName;
   }
   
   public void setAgentEName(String agentEName) {
       this.agentEName = agentEName;
   }
   
   @Column(name="IN_OUT", length=10)

   public String getInOut() {
       return this.inOut;
   }
   
   public void setInOut(String inOut) {
       this.inOut = inOut;
   }
   
   @Column(name="DISPLAY_ORDER", precision=11, scale=0)

   public Long getDisplayOrder() {
       return this.displayOrder;
   }
   
   public void setDisplayOrder(Long displayOrder) {
       this.displayOrder = displayOrder;
   }
    
   
}