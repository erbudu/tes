package com.supporter.prj.cneec.tpc.benefit_budget.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: BaseVBenefitProject
 * @Description: TPC_V_BENEFIT_PROJECT,字段与数据库字段一一对应.
 * @author: Yanweichao
 * @date: 2018-06-06
 * @version: V1.0
 */
@MappedSuperclass
public class BaseVBenefitProject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String projectId;
	private String projectName;

	/**
	 * 无参构造函数.
	 */
	public BaseVBenefitProject() {
	}

	/**
	 * 构造函数.
	 *
	 * @param projectId
	 */
	public BaseVBenefitProject(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * 构造函数.
	 * 
	 * @param projectId
	 * @param projectName
	 */
	public BaseVBenefitProject(String projectId, String projectName) {
		this.projectId = projectId;
		this.projectName = projectName;
	}

	@Id
	@Column(name = "PROJECT_ID", nullable = false, length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "PROJECT_NAME", nullable = true, length = 128)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
