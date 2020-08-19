/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.preparation.entity;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.bid_startup.preparation.constant.StartContant;
import com.supporter.prj.ppm.bid_startup.preparation.entity.base.BaseBFDEntity;
import com.supporter.prj.ppm.bid_startup.preparation.entity.base.BaseStartEntity;

/**
 *<p>Title: 投议标备案及许可->申报准备->启动申报实体类的扩展类</p>
 *<p>Description: 启动申报实体类的扩展类，扩展数据库以外其他用到的字段信息</p>
 *<p>Company: 东华后盾</p>
 * @author YUEYUN
 * @date 2019年8月13日
 * 
 */
@Entity
@Table(name="PPM_BIDING_STARTUP" ,schema = "")
public class StartEntity extends BaseStartEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//扩展数据库以外的其他属性，注意属性前加@Transient注解标示非数据库字段.不在数据库中存储
	
	@Transient
	private String isSendELettersDisPlayName;//是否向大使馆致函查看页面显示的字段中文
	
	@Transient
	private boolean isNew;//用于判断是否新建
	
	@Transient
	private List<BaseBFDEntity> dataList;		//资料清单  Varchar2(500) YUEYUN-2019/08/23
	
	@Transient
	private String use_seal_business;//用印业务

	
	
	//*****申明流程所用属性开始**********************************************
	
	@Transient
	public int getDbYear(){//年度
		return 0;
	}
	
	@Transient
	public String getModuleId(){
		return StartContant.MODULE_ID;
	}
	
	@Transient
	public String getDomainObjectId(){
		return StartContant.DOMAIN_OBJECT_ID;
		
	}
	
	@Transient
	public String getEntityId(){
		return this.getId();
	}
	
	@Transient
	public String getId() {
		return this.getBidIngUpId();
	}
	
	@Transient
	public String getCompanyNo(){
		
		return this.getDeptId();
	}
	
	@Transient
	public String getModuleBusiType(){
		return "";
	}
	
	@Transient
	public String getEntityName(){
		return this.getClass().getName();
	}
	
	//*****申明流程所用属性结束**********************************************
	
	/**
	 * <pre>获取用印业务</pre>
	 * @return the use_seal_business
	 */
	public String getUse_seal_business() {
		return StartContant.USE_SEAL_BUSINESS;
	}

	
	/**
	 * <p>根据是否向大使馆致函保存的int值，获取查看页面展示的String值</p>
	 * @return the isSendELettersDisPlayName
	 */
	public String getIsSendELettersDisPlayName() {
		return StartContant.getIsSendELettersCodeTab().get(this.getIsSendELetters());
	}

	/**
	 * @return the isNew
	 */
	public boolean getIsNew() {
		return isNew;
	}

	/**
	 * @param isNew the isNew to set
	 */
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	/**
	 * @return the dataList
	 */
	public List<BaseBFDEntity> getDataList() {
		return dataList;
	}

	/**
	 * @param dataList the dataList to set
	 */
	public void setDataList(List<BaseBFDEntity> dataList) {
		this.dataList = dataList;
	}

	/**
	 * 描述：无参构造方法
	 */
	public StartEntity() {
	}

	/**
	 * 描述：有参构造方法 
	 * @param bidIngUpId 主键
	 */
	public StartEntity(String bidIngUpId) {
		super(bidIngUpId);
	}
	

}
