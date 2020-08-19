package com.supporter.prj.linkworks.oa.report.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.report.entity.base.BaseReport;
import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.prj.eip_service.module.entity.IModule;
import com.supporter.util.CodeTable;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;


/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author gongjiwen
 * @date 2016-12-05 10:25:07
 * @version V1.0   
 *
 */
@Entity
@Table(name = "oa_report", schema = "")
public class Report extends BaseReport implements IBusiEntity {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "EIPREPORT";
	public static final String DOMAIN_OBJECT_ID = "Report";

	public static final int DRAFT = 0; //草稿
	public static final int PROCESSING = 1; //审核中
	public static final int COMPLETE = 2;//报告完毕

	/**
	 * 获取报告的状态码表.
	 * @return
	 */
	public static CodeTable getReportStatusCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(DRAFT, "草稿");
		lcdtbl_Return.insertItem(PROCESSING, "审核中");
		lcdtbl_Return.insertItem(COMPLETE, "报告完成");
		return lcdtbl_Return;
	}
	
	public static Map<Integer, String> getStatusCodeTable(){
    	Map<Integer, String> map = new LinkedHashMap<Integer, String>();
    	map.put(-1, "请选择");
    	map.put(DRAFT, "草稿");
    	map.put(PROCESSING, "审核中");
    	map.put(COMPLETE, "报告完成");
		return map;
	}
	
	private String notifierIds = "";
	private String notifierNames = "";
	private ReportContent reportContent;
	//是否在旧系统中审批过,如果审批过则 oldProcId > 0
	private long oldProcId = -1;
	
	@Transient
	public String getNotifierIds() {
		return notifierIds;
	}

	public void setNotifierIds(String notifierIds) {
		this.notifierIds = notifierIds;
	}
	@Transient
	public String getNotifierNames() {
		return notifierNames;
	}

	public void setNotifierNames(String notifierNames) {
		this.notifierNames = notifierNames;
	}
	@Transient
	public String getReportStatusDesc() {
		return getReportStatusCodeTable().getDisplay(this.getReportStatus());
	}
	
	@Transient
	public long getOldProcId(){
		return oldProcId;
	}
	public void setOldProcId(long procId){
		this.oldProcId = procId;
	}
	
	


	/**
	 * 无参构造函数
	 */
	public Report() {
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
	 * @param contractId
	 */
	public Report(String reportId) {
		super(reportId);
	}

	public boolean equals(Object other) {
		if (!(other instanceof Report)) {
			return false;
		}
		Report castOther = (Report) other;
		return StringUtils.equals(this.getReportId(), castOther.getReportId());
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getReportId()).toHashCode();
	}
	@Transient
	public ReportContent getReportContent() {
		return reportContent;
	}

	public void setReportContent(ReportContent reportContent) {
		this.reportContent = reportContent;
	}
	public enum Status {
		//zero(0, ""), 
		draft(0, "草稿"), auditing(1, "审核中"), COMPLETED(2, "报告完成"),rejected(3,"驳回");
		private int key;
		private String value;

		Status(int key, String value) {
			this.key = key;
			this.value = value;
		}

		public static int getKeyByValue(String value) {
			Status[] types = Status.values();
			for (Status type : types) {
				if (StringUtils.equals(value, type.getValue())) {
					return type.getKey();
				}
			}
			return 1;
		}

		public static String getValueByKey(int key) {
			Status[] types = Status.values();
			for (Status type : types) {
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

	@Transient
	public String getDomainObjectId() {
		return "Report";
	}
	@Transient
	public String getEntityName() {
		return getClass().getName();
	}
	@Transient
	public String getModuleId() {
		return "OAREPORT";
	}
	@Transient
	public String getModuleBusiType() {
		return "";
	}
	@Transient
	public String getCompanyNo() {
		return this.getDeptId();
	}

//  @Override
//	@Transient
//	public String getEntityName() {
//		return "oa_report";
//	}


	@Override
	@Transient
	public String getKeyProps() {
		return "report_id";
	}

	@Override
	@Transient
	public String getKeyValues() {
		return this.getReportId();
	}
	//详情页下载文件
	@Transient
	private String files;
	@Transient
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}
}
