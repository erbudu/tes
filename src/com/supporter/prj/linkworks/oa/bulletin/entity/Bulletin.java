package com.supporter.prj.linkworks.oa.bulletin.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.bulletin.entity.base.BaseBulletin;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @version V1.0   
 * @author linxiaosong
 */
@Entity
@Table(name="OA_BULLETIN_OA",schema="")
public class Bulletin extends BaseBulletin{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static final int DRAFT = 0; //草稿
    public static final int PUBLISHED = 1; //已发布
	/**
	 * 获取公告类型码表.
	 * @return
	 */
	public static Map<String, String> getBulletinTypeCodetable(){
    	Map<String, String> map = new LinkedHashMap<String, String>();
    	map.put("新闻公告", "新闻公告");
    	map.put("通知公告", "通知公告");
    	map.put("会议通知", "会议通知");
    	map.put("规章制度", "规章制度");
		return map;
	}
	
	/**
	 * 获取公告类型码表.
	 * @return
	 */
	public static Map<String, String> getBulletinTypeMoreCodetable(){
    	Map<String, String> map = new LinkedHashMap<String, String>();
    	map.put("新闻公告", "新闻公告");
    	map.put("通知公告", "通知公告");
    	map.put("会议通知", "会议通知");
    	map.put("规章制度", "规章制度");
    	map.put("1", "全部");
		return map;
	}
	

	
	//发布状态
	@Transient
	private String publishName;
	//阅读次数
	@Transient	
	private int readCount;
	//部门与部门公告板
	@Transient
	private String fullDeptResourceName;
	//公告内容
	@Transient
	private BulletinContent bulletinContent;
	//详情页显示附件
	@Transient
	private String files;
	
	//是否新建
	@Transient
	private boolean isNew = false;
	
	//手机端第一列
	@Transient
	private String rowOne;
	//手机端第二列
	@Transient
	private String rowTwo;
	//手机端第三列
	@Transient
	private String rowThree;
	
	public String getPublishName() {
		return publishName;
	}

	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public String getFullDeptResourceName() {
		return fullDeptResourceName;
	}

	public void setFullDeptResourceName(String fullDeptResourceName) {
		this.fullDeptResourceName = fullDeptResourceName;
	}

	public BulletinContent getBulletinContent() {
		return bulletinContent;
	}

	public void setBulletinContent(BulletinContent bulletinContent) {
		this.bulletinContent = bulletinContent;
	}

	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public String getRowOne() {
		return rowOne;
	}

	public void setRowOne(String rowOne) {
		this.rowOne = rowOne;
	}

	public String getRowTwo() {
		return rowTwo;
	}

	public void setRowTwo(String rowTwo) {
		this.rowTwo = rowTwo;
	}

	public String getRowThree() {
		return rowThree;
	}

	public void setRowThree(String rowThree) {
		this.rowThree = rowThree;
	}

	
	

}
