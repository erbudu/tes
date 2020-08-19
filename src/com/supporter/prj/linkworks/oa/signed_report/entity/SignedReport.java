package com.supporter.prj.linkworks.oa.signed_report.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.emp.entity.IEmployee;
import com.supporter.prj.eip_service.module.entity.IModule;
import com.supporter.prj.linkworks.oa.signed_report.entity.base.BaseSignedReport;
import com.supporter.util.CodeTable;
import com.supporter.util.CommonUtil;

/**
 * @Title: Entity
 * @Description: 功能模块表
 * @author qiyuanbin
 * 
 */
@Entity
@Table(name = "OA_SIGNED_REPORT", schema = "")
public class SignedReport extends BaseSignedReport {
	private static final long serialVersionUID = 1L;
	
	public static final int DRAFT = 0; //草稿
	public static final int PROCESSING = 1; //审核中
	public static final int COMPLETE = 2;//报告完毕
	
	public static final int PASS = 1;//同意
	public static final int PASS_COMPLETE_PROC = 2;//同意并结束流程
	public static final int FAIL = 3;//不同意
	public static final int FAIL_ABORT_PROC = 4;//不同意并结束流程
	public static final int PASS_COMPLETE_EXAM = 5;//同意并结束审批

	
	public static final String DEPTSIGNER = "DeptSigner"; //部门会签人
	public static final String LEADERSIGNER = "leaderSigner"; //分管领导会签人
	public static final String PRESIDENTSIGNER = "presidentSecretryAdd";//总裁、总裁秘书会签人
	
	public static final int IS_AGREEMENT = 1;//协议类（佣金代理）
	public static final int NO_AGREEMENT = 0;//非协议类
	
	public static CodeTable getIsAgreementCodeTable(){
		CodeTable ct = new CodeTable();
		ct.insertItem(IS_AGREEMENT, "是");
		ct.insertItem(NO_AGREEMENT, "否");
		return ct;
	}
	
	public static Map<Integer, String> getIsAgreementMap(){
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(IS_AGREEMENT, "是");
		map.put(NO_AGREEMENT, "否");
		return map;
	}
	
	public String getIsAgreementDesc(){
		return getIsAgreementCodeTable().getDisplay(this.getIsAgreement());
	}
	
	public static final int IS_NEW_AGREEMENT = 1;//新签协议
	public static final int CHANGE_AGREEMENT = 2;//变更协议
	
	public static CodeTable getNewAgreementTypeCodeTable() {
		CodeTable ct = new CodeTable();
		ct.insertItem(IS_NEW_AGREEMENT, "新签");
		ct.insertItem(CHANGE_AGREEMENT, "变更");
		return ct;
	}
	
	public static Map<Integer, String> getNewAgreementTypeMap(){
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(IS_NEW_AGREEMENT, "新签");
		map.put(CHANGE_AGREEMENT, "变更");
		return map;
	}
	
	public String getNewAgreementTypeDesc() {
		String newAgreementTypeDesc = "";
		if (this.getNewAgreementType() != null) {
			newAgreementTypeDesc = getNewAgreementTypeCodeTable().getDisplay(this.getNewAgreementType());
		}
		return newAgreementTypeDesc;
	}
	
	
	/**
	 * 获取报告的状态码表.
	 * @return
	 */
//		   public static CodeTable getReportStatusCodeTable() {
//		       CodeTable lcdtbl_Return = new CodeTable();
//		       lcdtbl_Return.insertItem(-1,"请选择",1);
//		       lcdtbl_Return.insertItem(DRAFT,"草稿",2);
//		       lcdtbl_Return.insertItem(PROCESSING,"审核中",3);
//		       lcdtbl_Return.insertItem(COMPLETE,"审批完成",4);
//		       return lcdtbl_Return;
//		   }
	
	public static Map<Integer, String> getSingnedReportStatusCodetable() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		map.put(PROCESSING, "审批中");
		map.put(COMPLETE, "审批完成");
		return map;
	}

	// 状态
	public String getSwfStatusStr() {
		String property = "";
		if (getSwfStatus() != null) {
			if (getSwfStatus() == 0) {
				property = "草稿";
			} else if (getSwfStatus() == 1) {
				property = "审批中";
			} else if (getSwfStatus() == 2) {
				property = "审批完成";
			}
		}
		return property;
	}
	
	// 获取日期中的用于查看页面显示年份
	public String getOperatorDateToYear() {
		String property = "";
		if (getOperatorDate() != null) {
			property = CommonUtil.format(getOperatorDate(),"yyyy");
		}
		return property;
	}
	
	// 获取日期中的用于查看页面显示月份
	public String getOperatorDateToMonth() {
		String property = "";
		if (getOperatorDate() != null) {
			property = CommonUtil.format(getOperatorDate(),"MM");
		}
		return property;
	}
	
	// 获取日期中的用于查看页面显示日期
	public String getOperatorDateToDay() {
		String property = "";
		if (getOperatorDate() != null) {
			property = CommonUtil.format(getOperatorDate(),"dd");
		}
		return property;
	}
	
	// 获取右上角方框中的签号
	public String getNum() {
		String num = "";
		if (StringUtils.isNotBlank(getSignNo())) {
			num = getSignNo().substring(1,6);
		}
		return num;
	}
	
	// 获取右上角方框中的签号
	public String getSignNoYear() {
		String signNoYear = "";
		if (StringUtils.isNotBlank(getSignNo())) {
			signNoYear = getSignNo().substring(8,12);
		}
		return signNoYear;
	}
	
	// 获取右上角方框中的签收日期
	public String getSignYear() {
		String signYear = "";
		if (StringUtils.isNotBlank(getSignedDate())) {
			signYear = getSignedDate().substring(0,4);
		}
		return signYear;
	}
	
	// 获取右上角方框中的签收日期
	public String getSignMonth() {
		String signMonth = "";
		if (StringUtils.isNotBlank(getSignedDate())) {
			signMonth = getSignedDate().substring(5,7);
		}
		return signMonth;
	}
	
	// 获取右上角方框中的签收日期
	public String getSignDay() {
		String signDay = "";
		if (StringUtils.isNotBlank(getSignedDate())) {
			signDay = getSignedDate().substring(8,10);
		}
		return signDay;
	}
	
	// 获取日期中的用于查看页面显示日期
	public String getArchiveYear() {
		String archiveYear = "";
		if(StringUtils.isNotBlank(getArchiveDate())){
			archiveYear = getArchiveDate().substring(0,4);
		}
		return archiveYear;
	}
	
	// 获取日期中的用于查看页面显示日期
	public String getArchiveMonth() {
		String archiveMonth = "";
		if(StringUtils.isNotBlank(getArchiveDate())){
			archiveMonth = getArchiveDate().substring(5,7);
		}
		return archiveMonth;
	}
	
	// 获取日期中的用于查看页面显示日期
	public String getArchiveDay() {
		String archiveDay = "";
		if(StringUtils.isNotBlank(getArchiveDate())){
			archiveDay = getArchiveDate().substring(8,10);
		}
		return archiveDay;
	}
	
	
	//获取历史审批人
	public String getCirculateIds(){
		String circulateIds = "";
		if(getActresult()==3){
			
			if(StringUtils.isNotBlank(getDeptLeaderId())){
				circulateIds = circulateIds + getDeptLeaderId();
			}
			if(StringUtils.isNotBlank(getDeptSignerIds())){
				if(getDeptSignerIds().indexOf(circulateIds) == -1){//如果部门会签人里不包括部门领导，添加部门领导
					circulateIds = circulateIds + "," + getDeptSignerIds();
				}else{//部门会签人里包括部门领导
					circulateIds = getDeptSignerIds();
				}
			}
			if(StringUtils.isNotBlank(getSecratryId())){
				if(circulateIds.indexOf(getSecratryId()) == -1){//如果历史审批人里不包括秘书，添加秘书
					circulateIds = circulateIds + "," + getSecratryId();
				}
			}
			if(StringUtils.isNotBlank(getLeadersId())){
				if(circulateIds.indexOf(getLeadersId()) == -1){//如果历史审批人里不包分管领导，添加分管领导
					circulateIds = circulateIds + "," + getLeadersId();
				}
			}
			if(StringUtils.isNotBlank(getLeaderSignerIds())){
				for(String id : getLeaderSignerIds().split(",")){
					if(circulateIds.indexOf(id) == -1){//历史审批人里不包括分管领导会签人，添加
						circulateIds = circulateIds + "," + id;
					}
				}
			}
		}
		if(getActresult()==1){
			if(StringUtils.isNotBlank(getCreatedById())){
				circulateIds = circulateIds + getCreatedById();
			}
			if(StringUtils.isNotBlank(getDeptSignerIds())){
				if(getDeptSignerIds().indexOf(circulateIds) == -1){//不包含创建人，添加创建人
					circulateIds = circulateIds + "," + getDeptSignerIds();
				}else{
					circulateIds = getDeptSignerIds();
				}
			}
			if(StringUtils.isNotBlank(getLeadersId())){
				if(circulateIds.indexOf(getLeadersId()) == -1){//如果历史审批人里不包分管领导，添加分管领导
					circulateIds = circulateIds + "," + getLeadersId();
				}
			}
			if(StringUtils.isNotBlank(getLeaderSignerIds())){
				for(String id : getLeaderSignerIds().split(",")){
					if(circulateIds.indexOf(id) == -1){//历史审批人里不包括分管领导会签人，添加会签人
						circulateIds = circulateIds + "," + id;
					}
				}
			}
			if(StringUtils.isNotBlank(getSecratryId())){
				if(circulateIds.indexOf(getSecratryId()) == -1){//如果历史审批人里不包括秘书，添加秘书
					circulateIds = circulateIds + "," + getSecratryId();
				}
			}
			if(StringUtils.isNotBlank(getPresidentSecratryAddId())){
				for(String id : getPresidentSecratryAddId().split(",")){
					if(circulateIds.indexOf(id) == -1){//历史审批人里不包括总裁会签人，添加总裁会签人
						circulateIds = circulateIds + "," + id;
					}
				}
			}
		}
		return circulateIds;
	}
	
	//审批页面中的已有会签人
	public String getSignerNames(){
		String deptSignerName = "";
		if(StringUtils.isNotBlank(getDeptSignerNames())){
			deptSignerName = deptSignerName  + getDeptSignerNames();
		}
		if(StringUtils.isNotBlank(getLeaderSignerNames())){
			for(String name : getLeaderSignerNames().split(",")){
				if(StringUtils.isNotBlank(getDeptSignerNames()) && deptSignerName.indexOf(name) == -1){
					if(StringUtils.isNotBlank(deptSignerName)){
						deptSignerName = deptSignerName + "," + name;						
					}else{
						deptSignerName = deptSignerName  + name;
					}
				}
			}
		}
		if(StringUtils.isNotBlank(getPresidentSecratryAdd())){
			for(String name : getPresidentSecratryAdd().split(",")){
				if(StringUtils.isNotBlank(getDeptSignerNames()) && deptSignerName.indexOf(name) == -1){
					if(StringUtils.isNotBlank(deptSignerName)){
						deptSignerName = deptSignerName + "," + name;						
					}else{
						deptSignerName = deptSignerName  + name;
					}
				}
			}
		}
		return deptSignerName;
	}

	public String getDeptLeaderSigneName(String deptLeaderSigneId) {
		IEmployee userZ = EIPService.getEmpService().getEmployee(deptLeaderSigneId);
		if(userZ!=null){
			return userZ.getName();
		}else{
			return "";
		}
	}

	/**
	 * 无参构造函数
	 */
	public SignedReport() {
		super();
	}

	public enum AuthManageType {
		normal(IModule.AuthManageType.NORMAL, "普通角色&权限项方式"), byAdmin(
				IModule.AuthManageType.BY_ADMIN, "管理员权限"), none(
				IModule.AuthManageType.NONE, "不控制");
		private int key;
		private String value;

		AuthManageType(int key, String value) {
			this.key = key;
			this.value = value;
		}

		public static int getKeyByValue(String value) {
			AuthManageType[] types = AuthManageType.values();
			for (AuthManageType type : types) {
				if (StringUtils.equals(value, type.getValue())) {
					return type.getKey();
				}
			}
			return 0;
		}

		public static String getValueByKey(int key) {
			AuthManageType[] types = AuthManageType.values();
			for (AuthManageType type : types) {
				if (key == type.getKey()) {
					return type.getValue();
				}
			}
			return null;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	/**
	 * 
	 * 日志操作类型
	 * 
	 * @author gongjiwen
	 * @createDate 2017年3月7日
	 * @since TODO: 来源版本
	 * @modifier gongjiwen
	 * @modifiedDate 2017年3月7日
	 */
	public enum LogOper {
		MODULE_ADD("新建应用"), MODULE_EDIT("编辑应用"), MODULE_DEL("删除应用"), MODULE_CHANGE_DISPLAY_ORDER(
				"调整应用显示顺序");
		private String operName;

		LogOper(String operName) {
			this.operName = operName;
		}

		public String getOperName() {
			return operName;
		}

		public void setOperName(String operName) {
			this.operName = operName;
		}

	}

	/**
	 * 构造函数
	 * 
	 * @param contractId
	 */
	public SignedReport(String signedReportId) {
		super(signedReportId);
	}

	public boolean equals(Object other) {
		if (!(other instanceof SignedReport)) {
			return false;
		}
		SignedReport castOther = (SignedReport) other;
		return StringUtils.equals(this.getSignedReportId(), castOther
				.getSignedReportId());
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getSignedReportId()).toHashCode();
	}

	@Transient
	public SignedReportContent signedReportContent;

	@Transient
	public SignedReportContent getSignedReportContent() {
		return signedReportContent;
	}
	
	public void setSignedReportContent(SignedReportContent signedReportContent) {
		this.signedReportContent = signedReportContent;
	}
	
	@Transient
	public List<SignedReportMessageBoard> messageBoardList;

	public List<SignedReportMessageBoard> getMessageBoardList() {
		return messageBoardList;
	}

	/**
	 * 获取已有会签人id
	 * @return
	 */
	public String getSignerIds(){
		String signerIds = "";
		if(StringUtils.isNotBlank(getDeptSignerIds())){
			signerIds = getDeptSignerIds();
		}
		if(StringUtils.isNotBlank(signerIds)){
			signerIds = signerIds +",";
		}
		if(StringUtils.isNotBlank(getLeaderSignerIds())){
			for(String id :getLeaderSignerIds().split(",")){
				if(signerIds != null && signerIds.indexOf(id) == -1){
					signerIds = signerIds + "," + id;
				}
			}
		}
		if(StringUtils.isNotBlank(signerIds)){
			signerIds = signerIds +",";
		}
		if(StringUtils.isNotBlank(getPresidentSecratryAddId())){
			for(String id :getPresidentSecratryAddId().split(",")){
				if(signerIds != null && signerIds.indexOf(id) == -1){
					signerIds = signerIds + "," + id;
				}
			}
		}
		return signerIds;
	}
	
	//流程标题用的签报事由
	@Transient
	public String getReasonTitle(){
		String reason = this.getReason();
		if (reason != null){
			if (reason.length() <= 40){
				return reason;
			}else{
				return reason.substring(0, 40) + "...";
			}
		}
		return "";
	}
	
	/**
	 * 获取部门代码
	 * @return
	 */
	@Transient
	public String getDeptNo() {
		String deptNo = "";
		if (StringUtils.isNotBlank(this.getDeptId())) {
			deptNo = EIPService.getDeptService().getDept(this.getDeptId()).getDeptNo();
		}
		return deptNo;
	}
	
	public void setMessageBoardList(List<SignedReportMessageBoard> messageBoardList) {
		this.messageBoardList = messageBoardList;
	}

	
	public int getDbYear(){
	    return 0;
	 }
	
	public String getDomainObjectId(){
	    return "SignedReport";
	}
	
	public String getEntityName() {
		return getClass().getName();
	}

	public String getModuleId() {
		return "OASIGNREPORT";
	}

	public String getModuleBusiType(){
		 return "";
	}


}
