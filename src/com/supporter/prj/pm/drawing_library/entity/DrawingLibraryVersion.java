package com.supporter.prj.pm.drawing_library.entity;

import com.supporter.prj.pm.drawing_library.entity.base.BaseDrawingLibraryVersion;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "pm_drawing_library_version", schema = "")
public class DrawingLibraryVersion extends BaseDrawingLibraryVersion{
	public static int  DRAFT = 0;//草稿
	public static int AUDIT_IN = 1;//内审中
	public static int COMPLETE_IN = 2;//内审完成
	public static int AUDIT_OUT = 3;//外审中
	public static int COMPLETE_OUT = 4;//外审完成
	public static int DELIVERED = 5;//已交付
	public static int DISCLOSURE = 6;//已交底
	public static int COMPLETE_ALL = 7;//已完成
	public static int ABORT = 8;//中止
	public static int NO_COMPLETE_OUT = 9;//外审未通过
	public static int UPGRAGE = 10;//升级版本号待反馈
	public static int FINISH_REPLY = 11;//院方已反馈
	public static int WAIT_AUDIT = 12;//升级版本号待审核
	private static final long serialVersionUID = 1L;
	
	private String files;
	@Transient
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}
	
	//用于判断-版本信息编辑页面-初稿文件-是否为新上传的文件
	private boolean draftIsNew;
	@Transient
	public boolean getDraftIsNew() {
		return draftIsNew;
	}

	public void setDraftIsNew(boolean draftIsNew) {
		this.draftIsNew = draftIsNew;
	}


}
