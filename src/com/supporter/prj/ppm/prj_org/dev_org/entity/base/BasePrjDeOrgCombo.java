package com.supporter.prj.ppm.prj_org.dev_org.entity.base;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class BasePrjDeOrgCombo  implements java.io.Serializable {

	// Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String comboId;//主键
    private String prjId;//项目主键
    private String comboCName;//联合体中文名称
    private String comboEName;//联合体英文名称
    private String comboJobContent;
    private Long displayOrder;
    
    //后添加字段
    private String divisionOfLabor;//分工
    private String proportion;//比例  - 先放着	
   
    private Integer isMainSide;//牵头方-2019-11.18 客户反馈添加
    
    // Constructors

   /** default constructor */
   public BasePrjDeOrgCombo() {
   }

   
   /** full constructor */
   public BasePrjDeOrgCombo(String prjId, String comboCName, String comboEName, String comboJobContent, Long displayOrder) {
       this.prjId = prjId;
       this.comboCName = comboCName;
       this.comboEName = comboEName;
       this.comboJobContent = comboJobContent;
       this.displayOrder = displayOrder;
   }

  
   // Property accessors
   @Id
   @Column(name="COMBO_ID", unique=true, nullable=false, length=32)

   public String getComboId() {
       return this.comboId;
   }
   
   public void setComboId(String comboId) {
       this.comboId = comboId;
   }
   
   @Column(name="PRJ_ID", length=32)

   public String getPrjId() {
       return this.prjId;
   }
   
   public void setPrjId(String prjId) {
       this.prjId = prjId;
   }
   
   @Column(name="COMBO_C_NAME", length=256)

   public String getComboCName() {
       return this.comboCName;
   }
   
   public void setComboCName(String comboCName) {
       this.comboCName = comboCName;
   }
   
   @Column(name="COMBO_E_NAME", length=256)

   public String getComboEName() {
       return this.comboEName;
   }
   
   public void setComboEName(String comboEName) {
       this.comboEName = comboEName;
   }
   
   @Column(name="COMBO_JOB_CONTENT", length=512)

   public String getComboJobContent() {
       return this.comboJobContent;
   }
   
   public void setComboJobContent(String comboJobContent) {
       this.comboJobContent = comboJobContent;
   }
   
   @Column(name="DISPLAY_ORDER", precision=11, scale=0)

   public Long getDisplayOrder() {
       return this.displayOrder;
   }
   
   public void setDisplayOrder(Long displayOrder) {
       this.displayOrder = displayOrder;
   }


/**
 * @return the divisionOfLabor
 */
@Column(name="DIVISION_OF_LABOR",length=128)   
public String getDivisionOfLabor() {
	return divisionOfLabor;
}


/**
 * @param divisionOfLabor the divisionOfLabor to set
 */
public void setDivisionOfLabor(String divisionOfLabor) {
	this.divisionOfLabor = divisionOfLabor;
}


/**
 * @return the proportion
 */
@Column(name="PROPORTION",length=32)
public String getProportion() {
	return proportion;
}


/**
 * @param proportion the proportion to set
 */
public void setProportion(String proportion) {
	this.proportion = proportion;
}


/**
 * @return the isMainSide
 */

@Column(name="IS_MAIN_SIDE")
public Integer getIsMainSide() {
	return isMainSide;
}


/**
 * @param isMainSide the isMainSide to set
 */
public void setIsMainSide(Integer isMainSide) {
	this.isMainSide = isMainSide;
}


  
  
 
   
}