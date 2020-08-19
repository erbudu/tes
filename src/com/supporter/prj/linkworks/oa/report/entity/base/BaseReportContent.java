package com.supporter.prj.linkworks.oa.report.entity.base;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.Type;

/**   
 * @Title: Entity
 * @Description: 功能模块,字段与数据库字段一一对应.
 * @author liyinfeng
 * @date 2017-03-15 16:25:15
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BaseReportContent  implements Serializable {
	private static final long serialVersionUID = 1L;
	//功能模块id.
	
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "assigned")
	@Column(name = "report_id", unique = true, nullable = false, length = 32)
	private String reportId;
	//报告内容.
	@Column(name = "report_content",length = 12)
	private java.lang.String reportContent;
	
	
	/**
	 *方法: 取得功能模块id.
	 *@return: java.lang.String  功能模块id
	 */
	public String getReportId(){
		return this.reportId;
	}

	/**
	 *方法: 设置功能模块id.
	 *@param: java.lang.String  功能模块id
	 */
	public void setReportId(String reportId){
		this.reportId = reportId;
	}
	/**
	 *方法: 取得功能模块名称.
	 *@return: java.lang.String  报告内容
	 */
	public java.lang.String getReportContent(){
		return this.reportContent;
	}

	/**
	 *方法: 设置功能模块名称.
	 *@param: java.lang.String  报告内容
	 */
	public void setReportContent(java.lang.String reportContent){
		this.reportContent = reportContent;
	}


	/**
	 * 无参构造函数.
	 */
	public BaseReportContent(){
	
	}
	
	/**
	 * 构造函数.
	 * @param contractId
	 */
	public BaseReportContent(String reportId){
		this.reportId = reportId;
	}

	 
}
