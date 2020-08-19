package com.supporter.prj.ppm.prj_org.base_info.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * ContractTarget entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BasePrjPfi  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
    private String pfId;
    private String prjId;
    private String pfTypeId;
    private String pfType;
    private String pfTerm1;
    private String pfTerm2;
    private String pfTerm3;
    private String pfTerm4;
    private String pfTerm5;
    private String pfgModId;
    private String pfgMod;


   // Constructors

   /** default constructor */
   public BasePrjPfi() {
   }

   
   /** full constructor */
   public BasePrjPfi(String prjId, String pfTypeId, String pfType, String pfTerm1, String pfTerm2, String pfTerm3, String pfTerm4, String pfTerm5, String pfgModId, String pfgMod) {
       this.prjId = prjId;
       this.pfTypeId = pfTypeId;
       this.pfType = pfType;
       this.pfTerm1 = pfTerm1;
       this.pfTerm2 = pfTerm2;
       this.pfTerm3 = pfTerm3;
       this.pfTerm4 = pfTerm4;
       this.pfTerm5 = pfTerm5;
       this.pfgModId = pfgModId;
       this.pfgMod = pfgMod;
   }

  
   // Property accessors
  @Id
   @Column(name="PF_ID", unique=true, nullable=false, length=32)

   public String getPfId() {
       return this.pfId;
   }
   
   public void setPfId(String pfId) {
       this.pfId = pfId;
   }
   
   @Column(name="PRJ_ID", length=32)

   public String getPrjId() {
       return this.prjId;
   }
   
   public void setPrjId(String prjId) {
       this.prjId = prjId;
   }
   
   @Column(name="PF_TYPE_ID", length=32)

   public String getPfTypeId() {
       return this.pfTypeId;
   }
   
   public void setPfTypeId(String pfTypeId) {
       this.pfTypeId = pfTypeId;
   }
   
   @Column(name="PF_TYPE", length=128)

   public String getPfType() {
       return this.pfType;
   }
   
   public void setPfType(String pfType) {
       this.pfType = pfType;
   }
   
   @Column(name="PF_TERM_1", length=512)

   public String getPfTerm1() {
       return this.pfTerm1;
   }
   
   public void setPfTerm1(String pfTerm1) {
       this.pfTerm1 = pfTerm1;
   }
   
   @Column(name="PF_TERM_2", length=512)

   public String getPfTerm2() {
       return this.pfTerm2;
   }
   
   public void setPfTerm2(String pfTerm2) {
       this.pfTerm2 = pfTerm2;
   }
   
   @Column(name="PF_TERM_3", length=512)

   public String getPfTerm3() {
       return this.pfTerm3;
   }
   
   public void setPfTerm3(String pfTerm3) {
       this.pfTerm3 = pfTerm3;
   }
   
   @Column(name="PF_TERM_4", length=512)

   public String getPfTerm4() {
       return this.pfTerm4;
   }
   
   public void setPfTerm4(String pfTerm4) {
       this.pfTerm4 = pfTerm4;
   }
   
   @Column(name="PF_TERM_5", length=512)

   public String getPfTerm5() {
       return this.pfTerm5;
   }
   
   public void setPfTerm5(String pfTerm5) {
       this.pfTerm5 = pfTerm5;
   }
   
   @Column(name="PFG_MOD_ID", length=32)

   public String getPfgModId() {
       return this.pfgModId;
   }
   
   public void setPfgModId(String pfgModId) {
       this.pfgModId = pfgModId;
   }
   
   @Column(name="PFG_MOD")

   public String getPfgMod() {
       return this.pfgMod;
   }
   
   public void setPfgMod(String pfgMod) {
       this.pfgMod = pfgMod;
   }
	
	
    
    
   
}