package com.supporter.prj.cneec.tpc.credit_letter_collecting.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.tpc.credit_letter_collecting.entity.base.BaseCreditLetterCollectingR;
import com.supporter.prj.eip_service.module.entity.IModule;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author gongjiwen
 * @date 2016-12-05 10:25:07
 * @version V1.0   
 *
 */
@Entity
@Table(name = "TPC_CREDIT_LETTER_COLLECTING_R", schema = "")
public class CreditLetterCollectingR extends BaseCreditLetterCollectingR {

	private static final long serialVersionUID = 1L;
	// 顶级TOP_MODULE_ID
	public static final String TOP_MODULE_ID = "0";
	

	// 扩展数据库以外的其他属性，注意属性前加@Transient注解标示非数据库字段



	//public String getReportContentForHtml() {return CommonUtil.getEncodedStringForHTML(getReportContent());}
	/**
	 * 无参构造函数
	 */
	public CreditLetterCollectingR() {
		super();
	}
	public enum AuthManageType{
		normal(IModule.AuthManageType.NORMAL,"普通角色&权限项方式"),byAdmin(IModule.AuthManageType.BY_ADMIN,"管理员权限"),none(IModule.AuthManageType.NONE,"不控制");
		private int key;
		private String value;
		AuthManageType(int key, String value){
			this.key = key;
			this.value = value;
		}
		public static  int getKeyByValue(String value){
			AuthManageType[] types = AuthManageType.values();
			for(AuthManageType type : types){
				if(StringUtils.equals(value, type.getValue())){
					return type.getKey();
				}
			}
			return 0;
		}
		public static String getValueByKey(int key){
			AuthManageType[] types = AuthManageType.values();
			for(AuthManageType type : types){
				if(key == type.getKey()){
					return type.getValue();
				}
			}
			return null;
		}
		public int getKey() {
			return key;
		}
		public void setKey(int key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	/**
	 * 
	 * 日志操作类型
	 * @author gongjiwen
	 * @createDate 2017年3月7日
	 * @since TODO: 来源版本
	 * @modifier gongjiwen
	 * @modifiedDate 2017年3月7日
	 */
	public enum LogOper{
		MODULE_ADD("新建应用"),MODULE_EDIT("编辑应用"),MODULE_DEL("删除应用"),MODULE_CHANGE_DISPLAY_ORDER("调整应用显示顺序");
		private String operName;
		LogOper(String operName){
			this.operName = operName;
		}
		public String getOperName() {
			return operName;
		}
		public void setOperName(String operName) {
			this.operName = operName;
		}
		
	}
	/**
	 * 构造函数
	 * @param contractId
	 */
	public CreditLetterCollectingR(String recordId) {
		super(recordId);
	}

	public boolean equals(Object other) {
		if (!(other instanceof CreditLetterCollectingR)) {
			return false;
		}
		CreditLetterCollectingR castOther = (CreditLetterCollectingR) other;
		return StringUtils.equals(this.getRecordId(), castOther.getRecordId());
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getRecordId()).toHashCode();
	}


}
