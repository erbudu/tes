package com.supporter.prj.cneec.pc.pre_prj_manager.export_control.entity.base;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**   
 * @Title: Entity
 * @Description: 出口管制,字段与数据库字段一一对应.
 * @author kangh_000
 * @date 2018-12-20 17:53:30
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BaseExportControl  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 *ID.
	 */
	@Id
	@GeneratedValue(generator = "uuid")
  	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "EXPORT_CONTROL_ID",nullable = false,length = 32)
	private String exportControlId;
	/**
	 *国家ID.
	 */
	@Column(name = "COUNTRY_ID",nullable = true,length = 32)
	private String countryId;
	/**
	 *国家名称.
	 */
	@Column(name = "COUNTRY_NAME_ZH",nullable = true,length = 90)
	private String countryNameZh;
	/**
	 *国家名称(英文).
	 */
	@Column(name = "COUNTRY_NAME_EN",nullable = true,length = 90)
	private String countryNameEn;
	/**
	 *国家代码.
	 */
	@Column(name = "COUNTRY_CODE",nullable = true,length = 30)
	private String countryCode;
	/**
	 *地区名称.
	 */
	@Column(name = "REGIN_NAME_ZH",nullable = true,length = 90)
	private String reginNameZh;
	/**
	 *地区名称(英文).
	 */
	@Column(name = "REGIN_NAME_EN",nullable = true,length = 90)
	private String reginNameEn;
	/**
	 *制裁级别ID.
	 */
	@Column(name = "SANCTION_LEVEL_ID",nullable = true,length = 32)
	private String sanctionLevelId;
	/**
	 *制裁级别描述.
	 */
	@Column(name = "SANCTION_LEVEL_NAME",nullable = true,length = 90)
	private String sanctionLevelName;
	/**
	 *备注（CMEC要求）.
	 */
	@Column(name = "CMEC_REMARK",nullable = true,length = 1000)
	private String cmecRemark;
	/**
	 *备注（其它要求）.
	 */
	@Column(name = "OTHER_REMARK",nullable = true,length = 1000)
	private String otherRemark;
	/**
	 *控制规则.
	 */
	@Column(name = "CONTROL_RULE",nullable = true,precision = 10)
	private int controlRule;
	/**
	 *是否启用.
	 */
	@Column(name = "IS_ENABLE",nullable = true)
	@Type(type = "true_false")
	private boolean isEnable;
	/**
	 *管制类型.
	 */
	@Column(name = "CONTROL_CATEGORY",nullable = true,precision = 10)
	private int controlCategory;
	/**
	 *名称（中文）.
	 */
	@Column(name = "NAME_ZH",nullable = true,length = 90)
	private String nameZh;
	/**
	 *名称（英文）.
	 */
	@Column(name = "NAME_EN",nullable = true,length = 90)
	private String nameEn;
	
	/**
	 *方法: 取得ID.
	 *@return: java.lang.String  ID
	 */
	public String getExportControlId(){
		return this.exportControlId;
	}

	/**
	 *方法: 设置ID.
	 *@param: java.lang.String  ID
	 */
	public void setExportControlId(String exportControlId){
		this.exportControlId = exportControlId;
	}
	/**
	 *方法: 取得国家ID.
	 *@return: java.lang.String  国家ID
	 */
	public String getCountryId(){
		return this.countryId;
	}

	/**
	 *方法: 设置国家ID.
	 *@param: java.lang.String  国家ID
	 */
	public void setCountryId(String countryId){
		this.countryId = countryId;
	}
	/**
	 *方法: 取得国家名称.
	 *@return: java.lang.String  国家名称
	 */
	public String getCountryNameZh(){
		return this.countryNameZh;
	}

	/**
	 *方法: 设置国家名称.
	 *@param: java.lang.String  国家名称
	 */
	public void setCountryNameZh(String countryNameZh){
		this.countryNameZh = countryNameZh;
	}
	/**
	 *方法: 取得国家名称(英文).
	 *@return: java.lang.String  国家名称(英文)
	 */
	public String getCountryNameEn(){
		return this.countryNameEn;
	}

	/**
	 *方法: 设置国家名称(英文).
	 *@param: java.lang.String  国家名称(英文)
	 */
	public void setCountryNameEn(String countryNameEn){
		this.countryNameEn = countryNameEn;
	}
	/**
	 *方法: 取得国家代码.
	 *@return: java.lang.String  国家代码
	 */
	public String getCountryCode(){
		return this.countryCode;
	}

	/**
	 *方法: 设置国家代码.
	 *@param: java.lang.String  国家代码
	 */
	public void setCountryCode(String countryCode){
		this.countryCode = countryCode;
	}
	/**
	 *方法: 取得地区名称.
	 *@return: java.lang.String  地区名称
	 */
	public String getReginNameZh(){
		return this.reginNameZh;
	}

	/**
	 *方法: 设置地区名称.
	 *@param: java.lang.String  地区名称
	 */
	public void setReginNameZh(String reginNameZh){
		this.reginNameZh = reginNameZh;
	}
	/**
	 *方法: 取得地区名称(英文).
	 *@return: java.lang.String  地区名称(英文)
	 */
	public String getReginNameEn(){
		return this.reginNameEn;
	}

	/**
	 *方法: 设置地区名称(英文).
	 *@param: java.lang.String  地区名称(英文)
	 */
	public void setReginNameEn(String reginNameEn){
		this.reginNameEn = reginNameEn;
	}
	/**
	 *方法: 取得制裁级别ID.
	 *@return: java.lang.String  制裁级别ID
	 */
	public String getSanctionLevelId(){
		return this.sanctionLevelId;
	}

	/**
	 *方法: 设置制裁级别ID.
	 *@param: java.lang.String  制裁级别ID
	 */
	public void setSanctionLevelId(String sanctionLevelId){
		this.sanctionLevelId = sanctionLevelId;
	}
	/**
	 *方法: 取得制裁级别描述.
	 *@return: java.lang.String  制裁级别描述
	 */
	public String getSanctionLevelName(){
		return this.sanctionLevelName;
	}

	/**
	 *方法: 设置制裁级别描述.
	 *@param: java.lang.String  制裁级别描述
	 */
	public void setSanctionLevelName(String sanctionLevelName){
		this.sanctionLevelName = sanctionLevelName;
	}
	/**
	 *方法: 取得备注（CMEC要求）.
	 *@return: java.lang.String  备注（CMEC要求）
	 */
	public String getCmecRemark(){
		return this.cmecRemark;
	}

	/**
	 *方法: 设置备注（CMEC要求）.
	 *@param: java.lang.String  备注（CMEC要求）
	 */
	public void setCmecRemark(String cmecRemark){
		this.cmecRemark = cmecRemark;
	}
	/**
	 *方法: 取得备注（其它要求）.
	 *@return: java.lang.String  备注（其它要求）
	 */
	public String getOtherRemark(){
		return this.otherRemark;
	}

	/**
	 *方法: 设置备注（其它要求）.
	 *@param: java.lang.String  备注（其它要求）
	 */
	public void setOtherRemark(String otherRemark){
		this.otherRemark = otherRemark;
	}
	/**
	 *方法: 取得控制规则.
	 *@return: int  控制规则
	 */
	public int getControlRule(){
		return this.controlRule;
	}

	/**
	 *方法: 设置控制规则.
	 *@param: int  控制规则
	 */
	public void setControlRule(int controlRule){
		this.controlRule = controlRule;
	}
	/**
	 *方法: 取得是否启用.
	 *@return: boolean  是否启用
	 */
	public boolean getIsEnable(){
		return this.isEnable;
	}

	/**
	 *方法: 设置是否启用.
	 *@param: boolean  是否启用
	 */
	public void setIsEnable(boolean isEnable){
		this.isEnable = isEnable;
	}
	/**
	 *方法: 取得管制类型.
	 *@return: int  管制类型
	 */
	public int getControlCategory(){
		return this.controlCategory;
	}

	/**
	 *方法: 设置管制类型.
	 *@param: int  管制类型
	 */
	public void setControlCategory(int controlCategory){
		this.controlCategory = controlCategory;
	}
	/**
	 *方法: 取得名称（中文）.
	 *@return: java.lang.String  名称（中文）
	 */
	public String getNameZh(){
		return this.nameZh;
	}

	/**
	 *方法: 设置名称（中文）.
	 *@param: java.lang.String  名称（中文）
	 */
	public void setNameZh(String nameZh){
		this.nameZh = nameZh;
	}
	/**
	 *方法: 取得名称（英文）.
	 *@return: java.lang.String  名称（英文）
	 */
	public String getNameEn(){
		return this.nameEn;
	}

	/**
	 *方法: 设置名称（英文）.
	 *@param: java.lang.String  名称（英文）
	 */
	public void setNameEn(String nameEn){
		this.nameEn = nameEn;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BaseExportControl(){
	
	}
	
	/**
	 * 构造函数.
	 * @param exportControlId
	 */
	public BaseExportControl(String exportControlId){
		this.exportControlId = exportControlId;
	} 
}
