package com.supporter.prj.pm.external_drawings.entity;

import com.supporter.prj.pm.external_drawings.entity.base.BaseExternalDrawings;
import com.supporter.util.CodeTable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
@Entity
@Table(name = "pm_external_drawings", catalog = "")
public class ExternalDrawings extends BaseExternalDrawings {
	private static final long serialVersionUID = 1L;

	private boolean isNew;
	public static String DOMAIN_OBJECT_ID="External";
	public static String APP_NAME="EXTERNAL";

	// 状态
	public static final class ProgressStatusCodeTable {
		public static final int DRAFT = 0;//未处理
		public static final int PROGRESSED = 1;//已反馈并处理
		public static final int UNPROGRESSED = 2;//已反馈但未处理
	}
	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	// 状态
	public static final class StatusCodeTable {
		public static final Integer DRAFT = 0;
		public static final Integer APPROVAL = 1;
		public static final Integer EFFECT = 2;

		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "草稿");
			ct.insertItem(APPROVAL, "审批中");
			ct.insertItem(EFFECT, "审批完成");
			return ct;
		}
	}
	
	private String statusDesc;
	@Transient
	public String getStatusDesc() {
		return StatusCodeTable.getCodeTable().getDisplay(getStatus());
	}
	
	private List<ExternalDrawingsContent> contentList;
	@Transient
	public List<ExternalDrawingsContent> getContentList() {
		return contentList;
	}

	public void setContentList(List<ExternalDrawingsContent> contentList) {
		this.contentList = contentList;
	}
	
	private String delContentIds;
	@Transient
	public String getDelContentIds() {
		return delContentIds;
	}

	public void setDelContentIds(String delContentIds) {
		this.delContentIds = delContentIds;
	}
	
	private List<ExternalDrawingsContent> contentTwoList;
	@Transient
	public List<ExternalDrawingsContent> getContentTwoList() {
		return contentTwoList;
	}

	public void setContentTwoList(List<ExternalDrawingsContent> contentTwoList) {
		this.contentTwoList = contentTwoList;
	}
	
	private String delContentTwoIds;
	@Transient
	public String getDelContentTwoIds() {
		return delContentTwoIds;
	}

	public void setDelContentTwoIds(String delContentTwoIds) {
		this.delContentTwoIds = delContentTwoIds;
	}

	@Transient
	public int getDbYear() {
		return 0;
	}
	@Transient
	public String getDomainObjectId() {
		return DOMAIN_OBJECT_ID;
	}
	//获取主键ID，用于公共流程页面的传参
	@Transient
	public String getEntityId() {
		return this.getExternalId();
	}
	@Transient
	public String getEntityName() {
		return this.getClass().getName();
	}
	@Transient
	public String getModuleId() {
		return APP_NAME;
	}
	@Transient
	public String getModuleBusiType() {
		return "";
	}
	@Transient
	public String getCompanyNo() {
		return "";
	}
	
}
