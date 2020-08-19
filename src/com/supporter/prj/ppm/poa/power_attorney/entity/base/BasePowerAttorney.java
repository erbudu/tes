package com.supporter.prj.ppm.poa.power_attorney.entity.base;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**   
 *
 * @author GuoXiansheng
 * @date 2019-09-25 09:25:07
 * @version V1.0   
 *
 */
@MappedSuperclass
public class BasePowerAttorney  implements java.io.Serializable {
	// Fields
	private static final long serialVersionUID = 1L;
	private String laId;   //主键
	private String laBusinessTypeId;   //授权业务类型ID
	private String laBusinessType;   //授权业务类型
	private String laTypeId;   //授权书格式ID
	private String laType;   //授权书格式
	private String laEffectiveDate;   //生效日期
	private String laExpirationDate;   //失效日期
	private Integer laCopyies;   //份数
	private String laNo;   //授权书编号
	private String prjId;   //项目ID
	private String procId;   //流程ID
	private Integer procStatus;   //流程状态
	private String moduleId;   //应用编号
	private Integer reviewTimes;   //评审次数
	private String enclosureName;   //附件名称
	private Integer hasEnclosure;   //是否有附件（有为1，无为0）
	private Integer hasSealDocument;   //是否有盖章文件（有为1，无为0）
	private String createdById;   //创建人ID
	private String createdBy;   //创建人
	private String deptId;   //创建人部门名称ID
	private String deptName;   //创建人部门名称
	private Date createdDate;   //创建时间
	private String modifiedById;   //修改人ID
	private String modifiedBy;   //修改人
	private Date modifiedDate;   //修改时间
	private String modifiedDeptId;   //修改人部门名称ID
	private String modifiedDeptName;   //修改人部门名称
	private String businessUUID;    //业务主键

	
	// Constructors
	/** default constructor */
	public BasePowerAttorney() {
	}

	/** minimal constructor */
	public BasePowerAttorney(String laId) {
		this.laId = laId;
	}

	/** full constructor */
	public BasePowerAttorney(String laId, String prjId, String laBusinessTypeId, String laBusinessType,String laNo, 
			String laTypeId, String laType, String laEffectiveDate, Integer hasEnclosure,Integer hasSealDocument,
			String laExpirationDate, Integer laCopyies, String procId, Integer procStatus, String moduleId,
			Integer reviewTimes, String enclosureName, String createdById, String createdBy, String deptId,
			String deptName, Date createdDate, String modifiedById, String modifiedBy, Date modifiedDate,
			String modifiedDeptId, String modifiedDeptName, String businessUUID) {
		super();
		this.laId = laId;
		this.prjId = prjId;
		this.laBusinessTypeId = laBusinessTypeId;
		this.laBusinessType = laBusinessType;
		this.laNo = laNo;
		this.laTypeId = laTypeId;
		this.laType = laType;
		this.laEffectiveDate = laEffectiveDate;
		this.laExpirationDate = laExpirationDate;
		this.laCopyies = laCopyies;
		this.procId = procId;
		this.procStatus = procStatus;
		this.moduleId = moduleId;
		this.reviewTimes = reviewTimes;
		this.enclosureName = enclosureName;
		this.createdById = createdById;
		this.createdBy = createdBy;
		this.deptId = deptId;
		this.deptName = deptName;
		this.createdDate = createdDate;
		this.modifiedById = modifiedById;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.hasEnclosure = hasEnclosure;
		this.hasSealDocument = hasSealDocument;
		this.modifiedDeptId = modifiedDeptId;
		this.modifiedDeptName = modifiedDeptName;
		this.businessUUID = businessUUID;

	}
	// Property accessors

	@Id
	@Column(name="LA_ID", nullable=false, length=32)
	public String getLaId() {
		return laId;
	}
	public void setLaId(String laId) {
		this.laId = laId;
	}
	@Column(name="PRJ_ID", length=32)
	public String getPrjId() {
		return prjId;
	}
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}
	@Column(name="LA_BUSINESS_TYPE_ID", length=32)
	public String getLaBusinessTypeId() {
		return laBusinessTypeId;
	}
	public void setLaBusinessTypeId(String laBusinessTypeId) {
		this.laBusinessTypeId = laBusinessTypeId;
	}
	@Column(name="LA_BUSINESS_TYPE", length=128)
	public String getLaBusinessType() {
		return laBusinessType;
	}
	public void setLaBusinessType(String laBusinessType) {
		this.laBusinessType = laBusinessType;
	}
	@Column(name="LA_NO", length=128)
	public String getLaNo() {
		return laNo;
	}
	public void setLaNo(String laNo) {
		this.laNo = laNo;
	}
	@Column(name="LA_TYPE_ID", length=32)
	public String getLaTypeId() {
		return laTypeId;
	}
	public void setLaTypeId(String laTypeId) {
		this.laTypeId = laTypeId;
	}
	@Column(name="LA_TYPE", length=128)
	public String getLaType() {
		return laType;
	}
	public void setLaType(String laType) {
		this.laType = laType;
	}
	@Column(name="LA_EFFECTIVE_DATE")
	public String getLaEffectiveDate() {
		return laEffectiveDate;
	}
	public void setLaEffectiveDate(String laEffectiveDate) {
		this.laEffectiveDate = laEffectiveDate;
	}
	@Column(name="LA_EXPIRATION_DATE")
	public String getLaExpirationDate() {
		return laExpirationDate;
	}
	public void setLaExpirationDate(String laExpirationDate) {
		this.laExpirationDate = laExpirationDate;
	}
	@Column(name="LA_COPYIES", length=2)
	public Integer getLaCopyies() {
		return laCopyies;
	}
	public void setLaCopyies(Integer laCopyies) {
		this.laCopyies = laCopyies;
	}
	@Column(name="PROC_ID", length=32)
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	@Column(name="PROC_STATUS", length=2)
	public Integer getProcStatus() {
		return procStatus;
	}
	public void setProcStatus(Integer procStatus) {
		this.procStatus = procStatus;
	}
	@Column(name="MODULE_ID", length=64)
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	@Column(name="REVIEW_TIMES", length=3)
	public Integer getReviewTimes() {
		return reviewTimes;
	}
	public void setReviewTimes(Integer reviewTimes) {
		this.reviewTimes = reviewTimes;
	}
	@Column(name="ENCLOSURE_NAME", length=64)
	public String getEnclosureName() {
		return enclosureName;
	}
	public void setEnclosureName(String enclosureName) {
		this.enclosureName = enclosureName;
	}
	@Column(name="CREATED_BY_ID", length=32)
	public String getCreatedById() {
		return createdById;
	}
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}
	@Column(name="CREATED_BY", length=64)
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name="DEPT_ID", length=32)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(name="DEPT_NAME", length=200)
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name="MODIFIED_BY_ID", length=32)
	public String getModifiedById() {
		return modifiedById;
	}
	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}
	@Column(name="MODIFIED_BY", length=64)
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	@Column(name="HAS_ENCLOSURE", length=2)
	public Integer getHasEnclosure() {
		return hasEnclosure;
	}
	public void setHasEnclosure(Integer hasEnclosure) {
		this.hasEnclosure = hasEnclosure;
	}
	@Column(name="HAS_SEAL_DOCUMENT", length=2)
	public Integer getHasSealDocument() {
		return hasSealDocument;
	}
	public void setHasSealDocument(Integer hasSealDocument) {
		this.hasSealDocument = hasSealDocument;
	}
	@Column(name="MODIFIED_DEPT_ID", length=32)
	public String getModifiedDeptId() {
		return modifiedDeptId;
	}
	public void setModifiedDeptId(String modifiedDeptId) {
		this.modifiedDeptId = modifiedDeptId;
	}
	@Column(name="MODIFIED_DEPT_NAME", length=200)
	public String getModifiedDeptName() {
		return modifiedDeptName;
	}
	public void setModifiedDeptName(String modifiedDeptName) {
		this.modifiedDeptName = modifiedDeptName;
	}
	@Column(name="BUSINESS_UUID", length=32)
	public String getBusinessUUID() {
		return businessUUID;
	}
	public void setBusinessUUID(String businessUUID) {
		this.businessUUID = businessUUID;
	}

}
