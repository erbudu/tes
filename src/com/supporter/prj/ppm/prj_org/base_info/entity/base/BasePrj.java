package com.supporter.prj.ppm.prj_org.base_info.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;


/**
 * Contract entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class BasePrj  implements java.io.Serializable {

	 private String prjId;
     private String prjNo;
     private String prjCodeName;
     private String prjCName;
     private String prjEName;
     private String ownerCName;
     private String ownerEName;
     private String prjCCompany;
     private String prjECompany;
     private String prjApproGovOrg;//项目立项政府部门
     private String prjNatureId;
     private String prjNature;
     private String prjScale;
     private String prjFieldId;
     private String prjField;
     private String prjModId;
     private String prjMod;
     private String projectLevelId;
     private String projectLevel;
     private String bidWayId;
     private String bidWay;
     private String prjPlanAmount;//项目估算金额
     private Long isRmbSettlement;
     private String cbdAmount;
     private String cbdWay;
     private Integer prjDewStatus;//项目开发工作状态
     private String createdById;
     private String createdBy;
     private Date createdDate;
     private String modifiedBy;
     private String modifiedById;
     private Date modifiedDate;
     private String deptName;//创建部门名称
     private String deptId;//创建人部门id
     private String prjNote;
     private boolean isEccConfirm;//是否需要出口管制确认
     private String leadingDeptName;//主导部门名称YUEYUN-2019-09-24
     private String leadingDeptId;//主导部门idYUEYUN2019/09/24
     private String deptNo;//创建人部门系统编号YUEYUN2019/09/24
     private Integer status;//生效状态0未生效1生效
     private String procId;//流程id
     private int prjActiveStatus;//项目开发状态 开发激活、开发关闭  
     
     //2019-10-29
     private String developmentModeId;//开发模式ID
     private String developmentModeName;//开发模式显示名称
     private String settlementWayId;//是否人名币结算
     private String settlementWayName;//是否人名币结算
     
     //2019-10-30
     private String businessLeader;//开发小组商务负责人
     private String technologyLeader;//开发小组技术负责人
     private String coopDept;//合作开发部门
     
     private String businessLeaderId;//开发小组商务负责人
     private String technologyLeaderId;//开发小组技术负责人
     private String coopDeptId;//合作开发部门
     
     //private String prjDewStatusName;//项目开发工作状态-码表中文名称
    // Constructors

    /** default constructor */
    public BasePrj() {
    }

    
    /** full constructor */
    public BasePrj(String prjNo, String prjCName, String prjEName, String ownerCName, String ownerEName, String prjCCompany, String prjECompany, String prjApproGovOrg, String prjNatureId, String prjNature, String prjScale, String prjFieldId, String prjField, String prjModId, String prjMod, String projectLevelId, String projectLevel, String bidWayId, String bidWay, String prjPlanAmount, Long isRmbSettlement, String cbdAmount, String cbdWay, Integer prjDewStatus, String createdById, Date createdDate, String modifiedBy, String modifiedById, Date modifiedDate, String deptName, String deptId) {
        this.prjNo = prjNo;
        this.prjCName = prjCName;
        this.prjEName = prjEName;
        this.ownerCName = ownerCName;
        this.ownerEName = ownerEName;
        this.prjCCompany = prjCCompany;
        this.prjECompany = prjECompany;
        this.prjApproGovOrg = prjApproGovOrg;
        this.prjNatureId = prjNatureId;
        this.prjNature = prjNature;
        this.prjScale = prjScale;
        this.prjFieldId = prjFieldId;
        this.prjField = prjField;
        this.prjModId = prjModId;
        this.prjMod = prjMod;
        this.projectLevelId = projectLevelId;
        this.projectLevel = projectLevel;
        this.bidWayId = bidWayId;
        this.bidWay = bidWay;
        this.prjPlanAmount = prjPlanAmount;
        this.isRmbSettlement = isRmbSettlement;
        this.cbdAmount = cbdAmount;
        this.cbdWay = cbdWay;
        this.prjDewStatus = prjDewStatus;
        this.createdById = createdById;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedById = modifiedById;
        this.modifiedDate = modifiedDate;
        this.deptName = deptName;
        this.deptId = deptId;
    }

   
    // Property accessors
    @Id
    @Column(name="PRJ_ID", unique=true, nullable=false, length=32)

    public String getPrjId() {
        return this.prjId;
    }
    
    public void setPrjId(String prjId) {
        this.prjId = prjId;
    }
    
    @Column(name="PRJ_NO", length=128)

    public String getPrjNo() {
        return this.prjNo;
    }
    
    public void setPrjNo(String prjNo) {
        this.prjNo = prjNo;
    }
    
    
    @Column(name="PRJ_CODE_NAME", length=64)
    public String getPrjCodeName() {
		return prjCodeName;
	}


	public void setPrjCodeName(String prjCodeName) {
		this.prjCodeName = prjCodeName;
	}


	@Column(name="PRJ_C_NAME", length=64)

    public String getPrjCName() {
        return this.prjCName;
    }
    
    public void setPrjCName(String prjCName) {
        this.prjCName = prjCName;
    }
    
    @Column(name="PRJ_E_NAME", length=64)

    public String getPrjEName() {
        return this.prjEName;
    }
    
    public void setPrjEName(String prjEName) {
        this.prjEName = prjEName;
    }
    
    @Column(name="OWNER_C_NAME", length=128)

    public String getOwnerCName() {
        return this.ownerCName;
    }
    
    public void setOwnerCName(String ownerCName) {
        this.ownerCName = ownerCName;
    }
    
    @Column(name="OWNER_E_NAME", length=128)

    public String getOwnerEName() {
        return this.ownerEName;
    }
    
    public void setOwnerEName(String ownerEName) {
        this.ownerEName = ownerEName;
    }
    
    @Column(name="PRJ_C_COMPANY", length=128)

    public String getPrjCCompany() {
        return this.prjCCompany;
    }
    
    public void setPrjCCompany(String prjCCompany) {
        this.prjCCompany = prjCCompany;
    }
    
    @Column(name="PRJ_E_COMPANY", length=128)

    public String getPrjECompany() {
        return this.prjECompany;
    }
    
    public void setPrjECompany(String prjECompany) {
        this.prjECompany = prjECompany;
    }
    
    @Column(name="PRJ_APPRO_GOV_ORG", length=128)

    public String getPrjApproGovOrg() {
        return this.prjApproGovOrg;
    }
    
    public void setPrjApproGovOrg(String prjApproGovOrg) {
        this.prjApproGovOrg = prjApproGovOrg;
    }
    
    @Column(name="PRJ_NATURE_ID", length=32)

    public String getPrjNatureId() {
        return this.prjNatureId;
    }
    
    public void setPrjNatureId(String prjNatureId) {
        this.prjNatureId = prjNatureId;
    }
    
    @Column(name="PRJ_NATURE", length=64)

    public String getPrjNature() {
        return this.prjNature;
    }
    
    public void setPrjNature(String prjNature) {
        this.prjNature = prjNature;
    }
    
    @Column(name="PRJ_SCALE", length=512)

    public String getPrjScale() {
        return this.prjScale;
    }
    
    public void setPrjScale(String prjScale) {
        this.prjScale = prjScale;
    }
    
    @Column(name="PRJ_FIELD_ID", length=32)

    public String getPrjFieldId() {
        return this.prjFieldId;
    }
    
    public void setPrjFieldId(String prjFieldId) {
        this.prjFieldId = prjFieldId;
    }
    
    @Column(name="PRJ_FIELD", length=512)

    public String getPrjField() {
        return this.prjField;
    }
    
    public void setPrjField(String prjField) {
        this.prjField = prjField;
    }
    
    @Column(name="PRJ_MOD_ID", length=32)

    public String getPrjModId() {
        return this.prjModId;
    }
    
    public void setPrjModId(String prjModId) {
        this.prjModId = prjModId;
    }
    
    @Column(name="PRJ_MOD", length=128)

    public String getPrjMod() {
        return this.prjMod;
    }
    
    public void setPrjMod(String prjMod) {
        this.prjMod = prjMod;
    }
    
    @Column(name="PROJECT_LEVEL_ID", length=32)

    public String getProjectLevelId() {
        return this.projectLevelId;
    }
    
    public void setProjectLevelId(String projectLevelId) {
        this.projectLevelId = projectLevelId;
    }
    
    @Column(name="PROJECT_LEVEL", length=128)

    public String getProjectLevel() {
        return this.projectLevel;
    }
    
    public void setProjectLevel(String projectLevel) {
        this.projectLevel = projectLevel;
    }
    
    @Column(name="BID_WAY_ID", length=64)

    public String getBidWayId() {
        return this.bidWayId;
    }
    
    public void setBidWayId(String bidWayId) {
        this.bidWayId = bidWayId;
    }
    
    @Column(name="BID_WAY", length=128)

    public String getBidWay() {
        return this.bidWay;
    }
    
    public void setBidWay(String bidWay) {
        this.bidWay = bidWay;
    }
    
    @Column(name="PRJ_PLAN_AMOUNT", length=64)

    public String getPrjPlanAmount() {
        return this.prjPlanAmount;
    }
    
    public void setPrjPlanAmount(String prjPlanAmount) {
        this.prjPlanAmount = prjPlanAmount;
    }
    
    @Column(name="IS_RMB_SETTLEMENT", precision=1, scale=0)

    public Long getIsRmbSettlement() {
        return this.isRmbSettlement;
    }
    
    public void setIsRmbSettlement(Long isRmbSettlement) {
        this.isRmbSettlement = isRmbSettlement;
    }
    
    @Column(name="CBD_AMOUNT")

    public String getCbdAmount() {
        return this.cbdAmount;
    }
    
    public void setCbdAmount(String cbdAmount) {
        this.cbdAmount = cbdAmount;
    }
    
    @Column(name="CBD_WAY", length=128)

    public String getCbdWay() {
        return this.cbdWay;
    }
    
    public void setCbdWay(String cbdWay) {
        this.cbdWay = cbdWay;
    }
    
    @Column(name="PRJ_DEW_STATUS", length=2)

    public Integer getPrjDewStatus() {
        return this.prjDewStatus;
    }
    
    public void setPrjDewStatus(Integer prjDewStatus) {
        this.prjDewStatus = prjDewStatus;
    }
    
    @Column(name="CREATED_BY_ID", length=32)

    public String getCreatedById() {
        return this.createdById;
    }
    
    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }
    
    @Column(name="CREATED_BY", length=64)

    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_DATE")
    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    @Column(name="MODIFIED_BY", length=64)

    public String getModifiedBy() {
        return this.modifiedBy;
    }
    
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    
    @Column(name="MODIFIED_BY_ID", length=32)

    public String getModifiedById() {
        return this.modifiedById;
    }
    
    public void setModifiedById(String modifiedById) {
        this.modifiedById = modifiedById;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="MODIFIED_DATE")

    public Date getModifiedDate() {
        return this.modifiedDate;
    }
    
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    
    @Column(name="DEPT_NAME", length=256)

    public String getDeptName() {
        return this.deptName;
    }
    
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    
    @Column(name="DEPT_ID", length=32)

    public String getDeptId() {
        return this.deptId;
    }
    
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Column(name="PRJ_NOTE", length=512)
	public String getPrjNote() {
		return prjNote;
	}


	public void setPrjNote(String prjNote) {
		this.prjNote = prjNote;
	}

	@Column(name="IS_ECC_CONFIRM", length=1)
	@Type(type = "true_false")
	public boolean getIsEccConfirm() {
		return isEccConfirm;
	}


	public void setIsEccConfirm(boolean isEccConfirm) {
		this.isEccConfirm = isEccConfirm;
	}


	/**
	 * @return the leadingDeptName
	 */
	@Column(name="LEADING_DEPT_NAME")
	public String getLeadingDeptName() {
		return leadingDeptName;
	}


	/**
	 * @param leadingDeptName the leadingDeptName to set
	 */
	public void setLeadingDeptName(String leadingDeptName) {
		this.leadingDeptName = leadingDeptName;
	}


	/**
	 * @return the leadingDeptId
	 */
	@Column(name="LEADING_DEPT_ID")
	public String getLeadingDeptId() {
		return leadingDeptId;
	}


	/**
	 * @param leadingDeptId the leadingDeptId to set
	 */
	
	public void setLeadingDeptId(String leadingDeptId) {
		this.leadingDeptId = leadingDeptId;
	}


	/**
	 * 
	 * @return the deptNo
	 */
	@Column(name="DEPT_NO")
	public String getDeptNo() {
		return deptNo;
	}


	/**
	 * @param deptNo the deptNo to set
	 */
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
   
	
    
    private String keyword;

	/**
	 * @return the keyword
	 */
    @Transient
	public String getKeyword() {
		return keyword;
	}


	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	/**
	 * @return the status
	 */
	@Column(name="STATUS")
	public Integer getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}


	/**
	 * @return the procId
	 */
	@Column(name="PROC_ID",length=32)
	public String getProcId() {
		return procId;
	}


	/**
	 * @param procId the procId to set
	 */
	public void setProcId(String procId) {
		this.procId = procId;
	}


	/**
	 * @return the prjActiveStatus
	 */
	@Column(name="PRJ_ACTIVE_STATUS")
	public int getPrjActiveStatus() {
		return prjActiveStatus;
	}


	/**
	 * @param prjActiveStatus the prjActiveStatus to set
	 */
	public void setPrjActiveStatus(int prjActiveStatus) {
		this.prjActiveStatus = prjActiveStatus;
	}

	/**
	 * This method is used get 'developmentModeId'.
	 * @return the developmentModeId
	 */
	@Column(name="DEVELOPMENT_MODE_ID",length=32)
	public String getDevelopmentModeId() {
		return developmentModeId;
	}


	/**
	 * @param developmentModeId the developmentModeId to set
	 */
	public void setDevelopmentModeId(String developmentModeId) {
		this.developmentModeId = developmentModeId;
	}


	/**
	 * This method is used get 'developmentModeName'.
	 * @return the developmentModeName
	 */
	@Column(name="DEVELOPMENT_MODE_NAME",length=64)
	public String getDevelopmentModeName() {
		return developmentModeName;
	}


	/**
	 * @param developmentModeName the developmentModeName to set
	 */
	public void setDevelopmentModeName(String developmentModeName) {
		this.developmentModeName = developmentModeName;
	}


	/**
	 * @return the settlementWayId
	 */
	@Column(name="SETTLEMENT_WAY_ID",length=32)
	public String getSettlementWayId() {
		return settlementWayId;
	}


	/**
	 * @param settlementWayId the settlementWayId to set
	 */
	public void setSettlementWayId(String settlementWayId) {
		this.settlementWayId = settlementWayId;
	}


	/**
	 * @return the settlementWayName
	 */
	@Column(name="SETTLEMENT_WAY_NAME",length=64)
	public String getSettlementWayName() {
		return settlementWayName;
	}


	/**
	 * @param settlementWayName the settlementWayName to set
	 */
	public void setSettlementWayName(String settlementWayName) {
		this.settlementWayName = settlementWayName;
	}


	/**
	 * @return the businessLeader
	 */
	@Column(name="BUSINESS_LEADER",length=64)
	public String getBusinessLeader() {
		return businessLeader;
	}


	/**
	 * @param businessLeader the businessLeader to set
	 */
	public void setBusinessLeader(String businessLeader) {
		this.businessLeader = businessLeader;
	}


	/**
	 * @return the technologyLeader
	 */
	@Column(name="TECHNOLOGY_LEADER",length=64)
	public String getTechnologyLeader() {
		return technologyLeader;
	}


	/**
	 * @param technologyLeader the technologyLeader to set
	 */
	public void setTechnologyLeader(String technologyLeader) {
		this.technologyLeader = technologyLeader;
	}


	/**
	 * @return the coopDept
	 */
	@Column(name="COOP_DEPT",length=256)
	public String getCoopDept() {
		return coopDept;
	}


	/**
	 * @param coopDept the coopDept to set
	 */
	public void setCoopDept(String coopDept) {
		this.coopDept = coopDept;
	}


	/**
	 * @return the businessLeaderId
	 */
	@Column(name="BUSINESS_LEADER_ID",length=32)
	public String getBusinessLeaderId() {
		return businessLeaderId;
	}


	/**
	 * @param businessLeaderId the businessLeaderId to set
	 */
	public void setBusinessLeaderId(String businessLeaderId) {
		this.businessLeaderId = businessLeaderId;
	}


	/**
	 * @return the technologyLeaderId
	 */
	@Column(name="TECHNOLOGY_LEADER_ID",length=32)
	public String getTechnologyLeaderId() {
		return technologyLeaderId;
	}


	/**
	 * @param technologyLeaderId the technologyLeaderId to set
	 */
	public void setTechnologyLeaderId(String technologyLeaderId) {
		this.technologyLeaderId = technologyLeaderId;
	}


	/**
	 * @return the coopDeptId
	 */
	@Column(name="COOP_DEPT_ID",length=512)
	public String getCoopDeptId() {
		return coopDeptId;
	}


	/**
	 * @param coopDeptId the coopDeptId to set
	 */
	public void setCoopDeptId(String coopDeptId) {
		this.coopDeptId = coopDeptId;
	}


	
	
    
	
	
	
	
	
	
	
	
	
    
}