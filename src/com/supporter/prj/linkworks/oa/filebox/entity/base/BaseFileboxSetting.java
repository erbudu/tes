package com.supporter.prj.linkworks.oa.filebox.entity.base;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.supporter.prj.linkworks.oa.filebox.entity.FileboxSetting;

/**   
 * @Title: Entity
 * @Description: OA_FILEBOX_SETTING,字段与数据库字段一一对应
 * @author linda
 * @date 2017-10-17 13:57:49
 * @version V1.0   
 *
 */
@MappedSuperclass
public  class BaseFileboxSetting  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**SETTING_ID*/
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name ="SETTING_ID",nullable=false,length=32)
	private java.lang.String settingId;
	
	@Column(name ="display_name",nullable=false,length=64)
	private java.lang.String displayName;
	
	@Column(name ="filebox_url",nullable=true,length=128)
	private java.lang.String fileboxUrl;
	
	@Column(name ="icon_url",nullable=true,length=128)
	private java.lang.String iconUrl;
	
	/**DISPLAY_ORDER*/
	@Column(name ="DISPLAY_ORDER",nullable=false,precision=10)
	private int displayOrder;
	
	/**IS_ACTIVED*/
	@Column(name ="IS_ACTIVED",nullable=false,length=1)
	@Type(type = "true_false")
	private boolean actived;

	/**
	 *方法: 取得SETTING_ID
	 *@return: java.lang.String  SETTING_ID
	 */
	public java.lang.String getSettingId(){
		return this.settingId;
	}

	/**
	 *方法: 设置SETTING_ID
	 *@param: java.lang.String  SETTING_ID
	 */
	public void setSettingId(java.lang.String settingId){
		this.settingId = settingId;
	}
	/**
	 *方法: 取得displayName
	 *@return: java.lang.String  displayName
	 */
	public java.lang.String getDisplayName(){
		return this.displayName;
	}

	/**
	 *方法: 设置displayName
	 *@param: java.lang.String  displayName
	 */
	public void setDisplayName(java.lang.String paramVal){
		this.displayName = paramVal;
	}
	/**
	 *方法: 取得DISPLAY_ORDER
	 *@return: java.lang.Integer  DISPLAY_ORDER
	 */
	public int getDisplayOrder(){
		return this.displayOrder;
	}

	/**
	 *方法: 设置DISPLAY_ORDER
	 *@param: java.lang.Integer  DISPLAY_ORDER
	 */
	public void setDisplayOrder(int displayOrder){
		this.displayOrder = displayOrder;
	}
	/**
	 *方法: 取得IS_ACTIVED
	 *@return: java.lang.String  IS_ACTIVED
	 */
	public boolean isActived(){
		return this.actived;
	}

	/**
	 *方法: 设置IS_ACTIVED
	 *@param: java.lang.String  IS_ACTIVED
	 */
	public void setIsActived(boolean isActived){
		this.actived = isActived;
	}
	/**
	 *方法: 取得ICON_URL.
	 *@return: java.lang.String  ICON_URL
	 */
	public java.lang.String getIconUrl(){
		return this.iconUrl;
	}

	/**
	 *方法: 设置ICON_URL.
	 *@param: java.lang.String  ICON_URL
	 */
	public void setIconUrl(java.lang.String iconUrl){
		this.iconUrl = iconUrl;
	}
	
	/**
	 *方法: 取得filebox_url.
	 *@return: java.lang.String  filebox_url
	 */
	public java.lang.String getFileboxUrl(){
		return this.fileboxUrl;
	}

	/**
	 *方法: 设置filebox_url.
	 *@param: java.lang.String  filebox_url
	 */
	public void setFileboxUrl(java.lang.String fileboxUrl){
		this.fileboxUrl = fileboxUrl;
	}
	
	public boolean equals(Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof FileboxSetting)) return false;
		else {
			FileboxSetting objInst = (FileboxSetting) obj;
			if (null == this.getSettingId() || null == objInst.getSettingId()) return false;
			else return (this.getSettingId().equals(objInst.getSettingId()));
		}
	}

	@Transient
	private int hashCode = Integer.MIN_VALUE;
	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getSettingId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getSettingId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
}
