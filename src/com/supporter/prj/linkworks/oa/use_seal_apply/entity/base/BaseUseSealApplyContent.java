package com.supporter.prj.linkworks.oa.use_seal_apply.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseUseSealApplyContent implements java.io.Serializable {
	 // Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applyId;
    private String useSealReason;//事由
    private String useSealApplyContent;


   // Constructors

   /** default constructor */
   public BaseUseSealApplyContent() {
   }

	/** minimal constructor */
   public BaseUseSealApplyContent(String applyId) {
       this.applyId = applyId;
   }
   
   /** full constructor */
   public BaseUseSealApplyContent(String applyId, String useSealReason, String useSealApplyContent) {
       this.applyId = applyId;
       this.useSealReason = useSealReason;
       this.useSealApplyContent = useSealApplyContent;
   }

  
   // Property accessors
   @Id 
   
   @Column(name="APPLY_ID", unique=true, nullable=false, length=32)

   public String getApplyId() {
       return this.applyId;
   }
   
   public void setApplyId(String applyId) {
       this.applyId = applyId;
   }
   
   @Column(name="USE_SEAL_REASON")

   public String getUseSealReason() {
       return this.useSealReason;
   }
   
   public void setUseSealReason(String useSealReason) {
       this.useSealReason = useSealReason;
   }
   
   @Column(name="USE_SEAL_APPLY_CONTENT")

   public String getUseSealApplyContent() {
       return this.useSealApplyContent;
   }
   
   public void setUseSealApplyContent(String useSealApplyContent) {
       this.useSealApplyContent = useSealApplyContent;
   }
  
}
