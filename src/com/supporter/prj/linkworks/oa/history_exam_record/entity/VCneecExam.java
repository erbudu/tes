package com.supporter.prj.linkworks.oa.history_exam_record.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.emp.entity.IEmployee;
import com.supporter.prj.linkworks.oa.history_exam_record.entity.base.BaseVCneecExam;
import com.supporter.util.CommonUtil;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**   
 * @Title: Entity
 * @Description: CNEEC_V_SWF_EXAM.
 * @author T
 * @date 2017-09-30 10:27:58
 * @version V1.0   
 *
 */
@Entity
@Table(name = "CNEEC_V_SWF_EXAM", schema = "")
public class VCneecExam extends BaseVCneecExam {

	private static final long serialVersionUID = 1L;
	
	//扩展数据库以外的其他属性，注意属性前加@Transient注解标示非数据库字段.
	@Transient
	private String electronicSignature; //电子签名
	
	/**
	 * 无参构造函数.
	 */
	public VCneecExam(){
		super();
	}
	
	/**
	 * 构造函数.
	 * @param examId
	 */
	public VCneecExam(long examId){
		super(examId);
	}
	
	@Transient
	public String getEmpSex(){
		IEmployee emp = EIPService.getEmpService().getEmp(Integer.toString(this.getEmpId()));
		if (emp == null)return "M";
		String sex = CommonUtil.trim(emp.getGender());
		if (!sex.equals("F"))sex = "M";
		return sex;
	}
	
	public String getElectronicSignature(){
		return this.electronicSignature;
	}
	public void setElectronicSignature(String electronicSignature){
		this.electronicSignature = electronicSignature;
	}
	
	


	public int hashCode()
	{
		return new HashCodeBuilder().append(getExamId()).toHashCode();
	}
}
