package com.supporter.prj.linkworks.oa.abroad.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 功能模块,字段与数据库字段一一对应.
 * @author linxiaosong
 * @version V1.0   
 */

@MappedSuperclass
public class BaseAbroad implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name = "RECORD_ID", unique = true, nullable = false, precision = 32, scale = 0)
	private String recordId;
	
	//申请人id
	@Column(name="APPLIER_ID" ,length=32, nullable = true)
	private String applierId;
	//申请人姓名
	@Column(name="APPLIER_NAME" ,length=64, nullable = true)
	private String applierName;
	
	//审批状态（已通过2，审核中1，草稿0）
    @Column(name = "RECORD_STATUS", precision = 2, scale = 0, nullable = true)
	private Integer recordStatus = 0;
	//申请人部门Id
	@Column(name="APPLIER_DEPT_ID" ,length=32, nullable = true)
	private String applierDeptId;
	//申请人部门名称
	@Column(name="APPLIER_DEPT" ,length=64, nullable = true)
	private String applierDept;
	//出国组团单位
	@Column(name="ORGANIZING_UNIT" ,length=128, nullable = true)
	private String organizingUnit;
	//项目ID
	@Column(name="PRJ_ID" ,length=32, nullable = true)
	private String prjId;
	//项目名称
	@Column(name="PRJ_NAME" ,length=128, nullable = true)
	private String prjName;
	//项目性质
	@Column(name="PRJ_CHAR" ,length=64, nullable = true)
	private String prjChar;
	//出国人数
	@Column(name="PEOPLE_COUNT" ,length=64, nullable = true)
	private String peopleCount;
	//前往国家
	@Column(name="TGT_COUNTRIES" ,length=128, nullable = true)
	private String tgtCountries;
	//途径国家
	@Column(name="PASSED_COUNTRIES" ,length=128, nullable = true)
	private String passedCountries;
	//境外接待单位(中英文)
	@Column(name="ABROAD_UNIT" ,length=128, nullable = true)
	private String abroadUnit;
	//出国日期
	@Column(name="LEAVING_DATE" ,length=27, nullable = true)
	private String leavingDate;
	//停留时间
	@Column(name="STAYING_PERIOD" ,length=128, nullable = true)
	private String stayingPeriod;
	//费用标准来源
	@Column(name="EXPENSE_SOURCE" ,length=128, nullable = true)
	private String expenseSource;
	//任务通知书主送抄送单位
	@Column(name="NOTIFIED_UNIT" ,length=128, nullable = true)
	private String notifiedUnit;
	
		
	//部门领导Id
	@Column(name="DEPT_LEADER_ID" ,length=32, nullable = true)
	private String deptLeaderId;
	//部门领导名称
	@Column(name="DEPT_LEADER" ,length=64, nullable = true)
	private String deptLeader;
	//专门领导Id
	@Column(name="SPECIAL_MANAGER_ID" ,length=32, nullable = true)
	private String specialManagerId;
	//专门领导名称
	@Column(name="SPECIAL_MANAGER" ,length=64, nullable = true)
	private String specialManager;
	//财务领导Id
	@Column(name="FINANCIAL_LEADER_ID" ,length=32, nullable = true)
	private String financialLeaderId;
	//财务领导名称
	@Column(name="FINANCIAL_LEADER" ,length=64, nullable = true)
	private String financialLeader;
	//办公室领导Id
	@Column(name="OFFICE_LEADER_ID" ,length=32, nullable = true)
	private String officeLeaderId;
	//办公室领导名称
	@Column(name="OFFICE_LEADER" ,length=64, nullable = true)
	private String officeLeader;
	//高级领导Id
	@Column(name="SENIOR_LEADER_ID" ,length=32, nullable = true)
	private String seniorLeaderId;
	//高级领导名称
	@Column(name="SENIOR_LEADER" ,length=64, nullable = true)
	private String seniorLeader;
	//顶级领导Id
	@Column(name="TOP_LEADER_ID" ,length=32, nullable = true)
	private String topLeaderId;
	//顶级领导名称
	@Column(name="TOP_LEADER" ,length=64, nullable = true)
	private String topLeader;
	//公司分管领导Id
	@Column(name="COMPANY_LEADER_ID" ,length=32, nullable = true)
	private String companyLeaderId;
	//公司分管领导姓名
	@Column(name="COMPANY_LEADER_NAME" ,length=64, nullable = true)
	private String companyLeaderName;
	
	
    //业主名称
    @Column(name="OWNER_NAME" ,length=255, nullable = true)
	private String ownerName;
    //投资方
    @Column(name="INVESTOR_NAME" ,length=255, nullable = true)
	private String investorName;
    //投资额
    @Column(name="INVESTMENT_AMOUNT" ,length=64, nullable = true)
	private String investmentAmount;
    //施工地点
    @Column(name="CONSTRUCT_LOCATION" ,length=255, nullable = true)
	private String constructLocation;
    //工程规模
    @Column(name="CONSTRUCT_SCALE" ,length=255, nullable = true)
	private String constructScale;
    //拟于何时建设
    @Column(name="CONSTRUCT_TIME" ,length=255, nullable = true)
	private String constructTime;
    //我公司在该项目中承担部分
    @Column(name="CONSTRUCT_PART" ,length=255, nullable = true)
	private String constructPart;
    //项目目前所处阶段
    @Column(name="PRJ_PHASE" ,length=256, nullable = true)
	private String prjPhase;
    //本次出国拟执行何任务
    @Column(name="TASK_DESC" ,length=512, nullable = true)
	private String taskDesc;
    
    //项目简介
    @Column(name="PRJ_INTRO" ,length=1024, nullable = true)
	private String prjIntro;
    //日程安排
    @Column(name="SCHEDULE" ,length=2000, nullable = true)
	private String schedule;
    //往返航线
    @Column(name="ROUND_TRIP_ROUTE" ,length=1024, nullable = true)
	private String roundTripRoute;
    //邀请函
    @Column(name="INVITATION" ,length=256, nullable = true)
	private String invitation;
    //邀请单位情况介绍
    @Column(name="INVITATION_INTRODUCTION" ,length=2000, nullable = true)
	private String invitationIntroduction;
    //是否公示
    @Column(name = "IF_PUBLICITY", precision = 2, scale = 0, nullable = true)
	private Integer ifPublicity = 0;
    //公示时间
    @Column(name = "PUBLICITY_DAY", precision = 2, scale = 0, nullable = true)
	private Integer publicityDay = 0;
    //申请时间
    @Column(name="APPLY_DATE" ,length=32, nullable = true)
	private String applyDate;
    //通过时间
    @Column(name="PASS_DATE" ,length=64, nullable = true)
	private String passDate;
    //是否同意
    @Column(name = "IS_AGREE", precision = 2, scale = 0, nullable = true)
	private Integer isAgree = 1;

    //流程Id
    @Column(name="PROC_ID" ,length=32, nullable = true)
    private String procId;
    //标识历史数据
	@Column(name = "IS_HISTORY")
	@org.hibernate.annotations.Type(type="true_false")
    private boolean history;
	//往返类型
	@Column(name="ROUNDTRIP_TYPE" ,length=32, nullable = true)
	private String roundtripType;
	//往返期限
	@Column(name="TERM" ,length=128, nullable = true)
	private String term;
	//往返次数
	@Column(name="ROUNDTRIP_NUMBER" ,length=32, nullable = true)
	private String roundtripNumber;
	//往返次数
	@Column(name="DIVISION" ,length=2000, nullable = true)
	private String division;

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getRoundtripType() {
		return roundtripType;
	}

	public void setRoundtripType(String roundtripType) {
		this.roundtripType = roundtripType;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getRoundtripNumber() {
		return roundtripNumber;
	}

	public void setRoundtripNumber(String roundtripNumber) {
		this.roundtripNumber = roundtripNumber;
	}

	public boolean getHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}
    public BaseAbroad(){   	
    }
    
    public BaseAbroad(String recordId) {	
		this.recordId = recordId;
	}
    
    
	public BaseAbroad(String recordId, String applierId, String applierName,
			Integer recordStatus, String applierDeptId, String applierDept,
			String organizingUnit, String prjId, String prjName,
			String prjChar, String peopleCount, String tgtCountries,
			String passedCountries, String abroadUnit, String leavingDate,
			String stayingPeriod, String expenseSource, String notifiedUnit,
			String deptLeaderId, String deptLeader, String specialManagerId,
			String specialManager, String financialLeaderId,
			String financialLeader, String officeLeaderId, String officeLeader,
			String seniorLeaderId, String seniorLeader, String topLeaderId,
			String topLeader, String companyLeaderId, String companyLeaderName,
			String ownerName, String investorName, String investmentAmount,
			String constructLocation, String constructScale,
			String constructTime, String constructPart, String prjPhase,
			String taskDesc, String prjIntro, String schedule,
			String roundTripRoute, String invitation,
			String invitationIntroduction, Integer ifPublicity,
			Integer publicityDay, String applyDate, String passDate,
			Integer isAgree,String procId, String roundtripType, String term,
			String roundtripNumber, String division) {
		super();
		this.recordId = recordId;
		this.applierId = applierId;
		this.applierName = applierName;
		this.recordStatus = recordStatus;
		this.applierDeptId = applierDeptId;
		this.applierDept = applierDept;
		this.organizingUnit = organizingUnit;
		this.prjId = prjId;
		this.prjName = prjName;
		this.prjChar = prjChar;
		this.peopleCount = peopleCount;
		this.tgtCountries = tgtCountries;
		this.passedCountries = passedCountries;
		this.abroadUnit = abroadUnit;
		this.leavingDate = leavingDate;
		this.stayingPeriod = stayingPeriod;
		this.expenseSource = expenseSource;
		this.notifiedUnit = notifiedUnit;
		this.deptLeaderId = deptLeaderId;
		this.deptLeader = deptLeader;
		this.specialManagerId = specialManagerId;
		this.specialManager = specialManager;
		this.financialLeaderId = financialLeaderId;
		this.financialLeader = financialLeader;
		this.officeLeaderId = officeLeaderId;
		this.officeLeader = officeLeader;
		this.seniorLeaderId = seniorLeaderId;
		this.seniorLeader = seniorLeader;
		this.topLeaderId = topLeaderId;
		this.topLeader = topLeader;
		this.companyLeaderId = companyLeaderId;
		this.companyLeaderName = companyLeaderName;
		this.ownerName = ownerName;
		this.investorName = investorName;
		this.investmentAmount = investmentAmount;
		this.constructLocation = constructLocation;
		this.constructScale = constructScale;
		this.constructTime = constructTime;
		this.constructPart = constructPart;
		this.prjPhase = prjPhase;
		this.taskDesc = taskDesc;
		this.prjIntro = prjIntro;
		this.schedule = schedule;
		this.roundTripRoute = roundTripRoute;
		this.invitation = invitation;
		this.invitationIntroduction = invitationIntroduction;
		this.ifPublicity = ifPublicity;
		this.publicityDay = publicityDay;
		this.applyDate = applyDate;
		this.passDate = passDate;
		this.isAgree = isAgree;
		this.procId = procId;
		this.roundtripType = roundtripType;
		this.term = term;
		this.roundtripNumber = roundtripNumber;
		this.division = division;
	}
	
	
	
	public Integer getIsAgree() {
		return isAgree;
	}
	public void setIsAgree(Integer isAgree) {
		this.isAgree = isAgree;
	}
	
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getApplierId() {
		return applierId;
	}
	public void setApplierId(String applierId) {
		this.applierId = applierId;
	}
	public String getApplierName() {
		return applierName;
	}
	public void setApplierName(String applierName) {
		this.applierName = applierName;
	}
	public Integer getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(Integer recordStatus) {
		this.recordStatus = recordStatus;
	}
	public String getApplierDeptId() {
		return applierDeptId;
	}
	public void setApplierDeptId(String applierDeptId) {
		this.applierDeptId = applierDeptId;
	}
	public String getApplierDept() {
		return applierDept;
	}
	public void setApplierDept(String applierDept) {
		this.applierDept = applierDept;
	}
	public String getOrganizingUnit() {
		return organizingUnit;
	}
	public void setOrganizingUnit(String organizingUnit) {
		this.organizingUnit = organizingUnit;
	}
	public String getPrjId() {
		return prjId;
	}
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}
	public String getPrjName() {
		return prjName;
	}
	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}
	public String getPrjChar() {
		return prjChar;
	}
	public void setPrjChar(String prjChar) {
		this.prjChar = prjChar;
	}
	public String getPeopleCount() {
		return peopleCount;
	}
	public void setPeopleCount(String peopleCount) {
		this.peopleCount = peopleCount;
	}
	public String getTgtCountries() {
		return tgtCountries;
	}
	public void setTgtCountries(String tgtCountries) {
		this.tgtCountries = tgtCountries;
	}
	public String getPassedCountries() {
		return passedCountries;
	}
	public void setPassedCountries(String passedCountries) {
		this.passedCountries = passedCountries;
	}
	public String getAbroadUnit() {
		return abroadUnit;
	}
	public void setAbroadUnit(String abroadUnit) {
		this.abroadUnit = abroadUnit;
	}
	public String getLeavingDate() {
		return leavingDate;
	}
	public void setLeavingDate(String leavingDate) {
		this.leavingDate = leavingDate;
	}
	public String getStayingPeriod() {
		return stayingPeriod;
	}
	public void setStayingPeriod(String stayingPeriod) {
		this.stayingPeriod = stayingPeriod;
	}
	public String getExpenseSource() {
		return expenseSource;
	}
	public void setExpenseSource(String expenseSource) {
		this.expenseSource = expenseSource;
	}
	public String getNotifiedUnit() {
		return notifiedUnit;
	}
	public void setNotifiedUnit(String notifiedUnit) {
		this.notifiedUnit = notifiedUnit;
	}
	public String getDeptLeaderId() {
		return deptLeaderId;
	}
	public void setDeptLeaderId(String deptLeaderId) {
		this.deptLeaderId = deptLeaderId;
	}
	public String getDeptLeader() {
		return deptLeader;
	}
	public void setDeptLeader(String deptLeader) {
		this.deptLeader = deptLeader;
	}
	public String getSpecialManagerId() {
		return specialManagerId;
	}
	public void setSpecialManagerId(String specialManagerId) {
		this.specialManagerId = specialManagerId;
	}
	public String getSpecialManager() {
		return specialManager;
	}
	public void setSpecialManager(String specialManager) {
		this.specialManager = specialManager;
	}
	public String getFinancialLeaderId() {
		return financialLeaderId;
	}
	public void setFinancialLeaderId(String financialLeaderId) {
		this.financialLeaderId = financialLeaderId;
	}
	public String getFinancialLeader() {
		return financialLeader;
	}
	public void setFinancialLeader(String financialLeader) {
		this.financialLeader = financialLeader;
	}
	public String getOfficeLeaderId() {
		return officeLeaderId;
	}
	public void setOfficeLeaderId(String officeLeaderId) {
		this.officeLeaderId = officeLeaderId;
	}
	public String getOfficeLeader() {
		return officeLeader;
	}
	public void setOfficeLeader(String officeLeader) {
		this.officeLeader = officeLeader;
	}
	public String getSeniorLeaderId() {
		return seniorLeaderId;
	}
	public void setSeniorLeaderId(String seniorLeaderId) {
		this.seniorLeaderId = seniorLeaderId;
	}
	public String getSeniorLeader() {
		return seniorLeader;
	}
	public void setSeniorLeader(String seniorLeader) {
		this.seniorLeader = seniorLeader;
	}
	public String getTopLeaderId() {
		return topLeaderId;
	}
	public void setTopLeaderId(String topLeaderId) {
		this.topLeaderId = topLeaderId;
	}
	public String getTopLeader() {
		return topLeader;
	}
	public void setTopLeader(String topLeader) {
		this.topLeader = topLeader;
	}
	public String getCompanyLeaderId() {
		return companyLeaderId;
	}
	public void setCompanyLeaderId(String companyLeaderId) {
		this.companyLeaderId = companyLeaderId;
	}
	public String getCompanyLeaderName() {
		return companyLeaderName;
	}
	public void setCompanyLeaderName(String companyLeaderName) {
		this.companyLeaderName = companyLeaderName;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getInvestorName() {
		return investorName;
	}
	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}
	public String getInvestmentAmount() {
		return investmentAmount;
	}
	public void setInvestmentAmount(String investmentAmount) {
		this.investmentAmount = investmentAmount;
	}
	public String getConstructLocation() {
		return constructLocation;
	}
	public void setConstructLocation(String constructLocation) {
		this.constructLocation = constructLocation;
	}
	public String getConstructScale() {
		return constructScale;
	}
	public void setConstructScale(String constructScale) {
		this.constructScale = constructScale;
	}
	public String getConstructTime() {
		return constructTime;
	}
	public void setConstructTime(String constructTime) {
		this.constructTime = constructTime;
	}
	public String getConstructPart() {
		return constructPart;
	}
	public void setConstructPart(String constructPart) {
		this.constructPart = constructPart;
	}
	public String getPrjPhase() {
		return prjPhase;
	}
	public void setPrjPhase(String prjPhase) {
		this.prjPhase = prjPhase;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	public String getPrjIntro() {
		return prjIntro;
	}
	public void setPrjIntro(String prjIntro) {
		this.prjIntro = prjIntro;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getRoundTripRoute() {
		return roundTripRoute;
	}
	public void setRoundTripRoute(String roundTripRoute) {
		this.roundTripRoute = roundTripRoute;
	}
	public String getInvitation() {
		return invitation;
	}
	public void setInvitation(String invitation) {
		this.invitation = invitation;
	}
	public String getInvitationIntroduction() {
		return invitationIntroduction;
	}
	public void setInvitationIntroduction(String invitationIntroduction) {
		this.invitationIntroduction = invitationIntroduction;
	}
	public Integer getIfPublicity() {
		return ifPublicity;
	}
	public void setIfPublicity(Integer ifPublicity) {
		this.ifPublicity = ifPublicity;
	}
	public Integer getPublicityDay() {
		return publicityDay;
	}
	public void setPublicityDay(Integer publicityDay) {
		this.publicityDay = publicityDay;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getPassDate() {
		return passDate;
	}
	public void setPassDate(String passDate) {
		this.passDate = passDate;
	}

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}
    
    
}
