package com.supporter.prj.linkworks.oa.use_seal_apply.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseUseSealApplyBoard implements java.io.Serializable {

    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String boardId;
     private String reportId;
     private String content;
     private String applyDept;
     private String applyDeptId;
     private String useSealTime;
     private String applyPersonId;
     private String applyPersonName;


    // Constructors

    /** default constructor */
    public BaseUseSealApplyBoard() {
    }

	/** minimal constructor */
    public BaseUseSealApplyBoard(String boardId) {
        this.boardId = boardId;
    }
    
    /** full constructor */
    public BaseUseSealApplyBoard(String boardId, String reportId, String content, String applyDept, String applyDeptId, String useSealTime, String applyPersonId, String applyPersonName) {
        this.boardId = boardId;
        this.reportId = reportId;
        this.content = content;
        this.applyDept = applyDept;
        this.applyDeptId = applyDeptId;
        this.useSealTime = useSealTime;
        this.applyPersonId = applyPersonId;
        this.applyPersonName = applyPersonName;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="BOARD_ID", unique=true, nullable=false, length=32)

    public String getBoardId() {
        return this.boardId;
    }
    
    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
    
    @Column(name="REPORT_ID", length=32)

    public String getReportId() {
        return this.reportId;
    }
    
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
    
    @Column(name="CONTENT", length=2000)

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @Column(name="APPLY_DEPT", length=64)

    public String getApplyDept() {
        return this.applyDept;
    }
    
    public void setApplyDept(String applyDept) {
        this.applyDept = applyDept;
    }
    
    @Column(name="APPLY_DEPT_ID", length=32)

    public String getApplyDeptId() {
        return this.applyDeptId;
    }
    
    public void setApplyDeptId(String applyDeptId) {
        this.applyDeptId = applyDeptId;
    }
    
    @Column(name="USE_SEAL_TIME", length=27)

    public String getUseSealTime() {
        return this.useSealTime;
    }
    
    public void setUseSealTime(String useSealTime) {
        this.useSealTime = useSealTime;
    }
    
    @Column(name="APPLY_PERSON_ID", length=32)

    public String getApplyPersonId() {
        return this.applyPersonId;
    }
    
    public void setApplyPersonId(String applyPersonId) {
        this.applyPersonId = applyPersonId;
    }
    
    @Column(name="APPLY_PERSON_NAME", length=32)

    public String getApplyPersonName() {
        return this.applyPersonName;
    }
    
    public void setApplyPersonName(String applyPersonName) {
        this.applyPersonName = applyPersonName;
    }
   

}
