package com.supporter.prj.linkworks.oa.user_defined.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.prj.linkworks.oa.user_defined.entity.base.BaseCard;
import com.supporter.util.CodeTable;
@Entity
@Table(name = "U_CARD", schema = "")
public class UCard extends BaseCard implements IBusiEntity{
	private static final long serialVersionUID = 1L;
	private boolean add;
	public static final int DRAFT = 0; // 草稿
	public static final int PROCESSING = 1; // 审核中
	public static final int COMPLETE = 2;// 报告完毕
	private long oldProcId = -1;

	/**
	 * 获取报告的状态码表.
	 * 
	 * @return
	 */
	public static CodeTable getReportStatusCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(DRAFT, "草稿");
		lcdtbl_Return.insertItem(PROCESSING, "审核中");
		lcdtbl_Return.insertItem(COMPLETE, "审批完成");
		return lcdtbl_Return;
	}

	public static Map<Integer, String> getStatusCodeTable() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		map.put(PROCESSING, "审核中");
		map.put(COMPLETE, "审批完成");
		return map;
	}

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	@Transient
	public int getDbYear() {
		return 0;
	}

	@Transient
	public String getDomainObjectId() {
		return "UCARD";
	}

	@Transient
	public String getEntityName() {
		return getClass().getName();
	}

	@Transient
	public String getModuleId() {
		return "UCARD";
	}

	@Transient
	public String getModuleBusiType() {
		return "";
	}

	@Transient
	public String getCompanyNo() {
		return getDeptId();
	}
	@Transient
	public long getOldProcId() {
		return oldProcId;
	}

	public void setOldProcId(long oldProcId) {
		this.oldProcId = oldProcId;
	}
	@Override
	@Transient
	public String getKeyProps() {
		// TODO Auto-generated method stub
		return "id";
	}

	@Override
	@Transient
	public String getKeyValues() {
		// TODO Auto-generated method stub
		return this.getId();
	}
	

}
