package com.supporter.prj.pm.drawing_library.entity;

import com.supporter.prj.pm.drawing_library.entity.base.BaseDrawingLibrary;
import com.supporter.util.CodeTable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "pm_drawing_library", schema = "")
public class DrawingLibrary extends BaseDrawingLibrary{
	
	private static final long serialVersionUID = 1L;
	// 顶级TOP_MODULE_ID
	public static final String TOP_MODULE_ID = "0";	
	public static final String APP_NAME = "DrawingLib";//应用编号
	public static final String DOMAIN_OBJECT_ID = "DrawingLibrary";//业务对象编号
	public static final String FILE_NAME = "fileLibrary";//附件注册二级编号
	public static final String FILE_NAME_First = "fileLibraryUpload";//附件注册二级编号
	// 状态
	public static final class StatusCodeTable {
		public static final int DRAFT = 0;
		public static final int EFFECT = 1;
		
		//状态
		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
		/*	ct.insertItem(DRAFT, "正常");
			ct.insertItem(EFFECT, "作废");*/
			ct.insertItem(DrawingLibraryVersion.DRAFT, "未审核");
			ct.insertItem(DrawingLibraryVersion.AUDIT_IN, "内审中");
			ct.insertItem(DrawingLibraryVersion.COMPLETE_IN, "内审完成");
			ct.insertItem(DrawingLibraryVersion.AUDIT_OUT, "外审中");
			ct.insertItem(DrawingLibraryVersion.COMPLETE_OUT, "外审完成");
			ct.insertItem(DrawingLibraryVersion.DELIVERED, "已交付");
			ct.insertItem(DrawingLibraryVersion.DISCLOSURE, "已交底");
			ct.insertItem(DrawingLibraryVersion.COMPLETE_ALL, "已完成");
			ct.insertItem(DrawingLibraryVersion.NO_COMPLETE_OUT, "外审未通过");
			ct.insertItem(DrawingLibraryVersion.UPGRAGE, "已升级版本待反馈");
			ct.insertItem(DrawingLibraryVersion.FINISH_REPLY, "反馈完成");
			ct.insertItem(DrawingLibraryVersion.WAIT_AUDIT, "反馈完成待外审");
			return ct;
		}
		
	}
	
	@Transient
	public String getStatusDesc() {
		if(getStatus()!=null){
			return StatusCodeTable.getCodeTable().getDisplay(getStatus());
		}else{
			return null;
		}				
	}

	// 是否内审/外审/历史记录
	public static final class StatusCodeTable2 {
		public static final int ISNO = 0;
		public static final int ISYES= 1;
		
		//状态
		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(ISNO, "否");
			ct.insertItem(ISYES, "是");
			return ct;
		}
		
	}
	private String isInDesc;
	@Transient
	public String getIsInDesc() {
		if(getIsIn()!=null){
			return StatusCodeTable2.getCodeTable().getDisplay(getIsIn());
		}else{
			return null;
		}				
	}
	private String isOutDesc;
	@Transient
	public String getIsOutDesc() {
		if(getIsOut()!=null){
			return StatusCodeTable2.getCodeTable().getDisplay(getIsOut());
		}else{
			return null;
		}				
	}
	private String isHistoryDesc;
	@Transient
	public String getIsHistoryDesc() {
		if(getIsHistory()!=null){
			return StatusCodeTable2.getCodeTable().getDisplay(getIsHistory());
		}else{
			return null;
		}				
	}
	
	private boolean isNew;

	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	private String files;
	@Transient
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

}
