package com.supporter.prj.ppm.template_register.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.template_register.entity.base.BaseTemplateRegister;
import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.prj.eip_service.module.entity.IModule;
import com.supporter.util.CodeTable;

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
@Table(name = "PPM_TEMPLATE_REGISTER", schema = "")
public class TemplateRegister extends BaseTemplateRegister {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TEMPLATEREG";
	public static final String DOMAIN_OBJECT_ID = "Template";
	public static final String LOG_TYPE = "TEMPLATE_REGISTER";

	public static final int DRAFT = 0; //草稿
	public static final int PROCESSING = 1; //审核中
	public static final int COMPLETE = 2;//报告完毕
	private boolean add;// 是否新增
	
	
	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}
	/**
	 * 获取报告的状态码表.
	 * @return
	 */
	public static CodeTable getReportStatusCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(DRAFT, "草稿");
		lcdtbl_Return.insertItem(PROCESSING, "审核中");
		lcdtbl_Return.insertItem(COMPLETE, "报告完成");
		return lcdtbl_Return;
	}
	
	public static Map<Integer, String> getStatusCodeTable(){
    	Map<Integer, String> map = new LinkedHashMap<Integer, String>();
    	map.put(-1, "请选择");
    	map.put(DRAFT, "草稿");
    	map.put(PROCESSING, "审核中");
    	map.put(COMPLETE, "报告完成");
		return map;
	}
	
	private TemplateRegisterDetail templateRegisterDetail;
	
	
	
	


	/**
	 * 无参构造函数
	 */
	public TemplateRegister() {
		super();
	}



	/**
	 * 构造函数
	 * @param contractId
	 */
	public TemplateRegister(String templateId) {
		super(templateId);
	}

	public boolean equals(Object other) {
		if (!(other instanceof TemplateRegister)) {
			return false;
		}
		TemplateRegister castOther = (TemplateRegister) other;
		return StringUtils.equals(this.getTemplateId(), castOther.getTemplateId());
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getTemplateId()).toHashCode();
	}
	@Transient
	public TemplateRegisterDetail getTemplateRegisterDetail() {
		return templateRegisterDetail;
	}

	public void setTemplateRegisterDetail(TemplateRegisterDetail templateRegisterDetail) {
		this.templateRegisterDetail = templateRegisterDetail;
	}

}
