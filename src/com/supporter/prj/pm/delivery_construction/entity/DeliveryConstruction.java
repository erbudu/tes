package com.supporter.prj.pm.delivery_construction.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.pm.delivery_construction.entity.base.BaseDeliveryConstruction;
import com.supporter.util.CodeTable;
@Entity
@Table(name = "pm_delivery_construction", catalog = "")
public class DeliveryConstruction extends BaseDeliveryConstruction {
	private static final long serialVersionUID = 1L;
    //应用编号
	public static final String APP_NAME = "DELIVERY";

	private boolean isNew;

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
		public static final Integer EFFECT = 1;

		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "草稿");
			ct.insertItem(EFFECT, "生效");
			return ct;
		}
	}
	
	private String statusDesc;
	@Transient
	public String getStatusDesc() {
		return StatusCodeTable.getCodeTable().getDisplay(getStatus());
	}
	
	private List<DeliveryDrawings> contentList;
	@Transient
	public List<DeliveryDrawings> getContentList() {
		return contentList;
	}

	public void setContentList(List<DeliveryDrawings> contentList) {
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
	
}
