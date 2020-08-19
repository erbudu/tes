package com.supporter.prj.linkworks.oa.bulletin.entity.base;

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
public class BaseBulletinContent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "BULLETIN_ID", unique = true, nullable = false, precision = 32, scale = 0)
	private String bulletinId;
	
	
	//公告标题
	@Column(name="BULLETIN_CONTENT", nullable = true)
	private String bulletinContent;


	public BaseBulletinContent() {
		super();
	}


	public String getBulletinId() {
		return bulletinId;
	}


	public void setBulletinId(String bulletinId) {
		this.bulletinId = bulletinId;
	}


	public String getBulletinContent() {
		return bulletinContent;
	}


	public void setBulletinContent(String bulletinContent) {
		this.bulletinContent = bulletinContent;
	}


	public BaseBulletinContent(String bulletinId, String bulletinContent) {
		super();
		this.bulletinId = bulletinId;
		this.bulletinContent = bulletinContent;
	}
	
	
}
