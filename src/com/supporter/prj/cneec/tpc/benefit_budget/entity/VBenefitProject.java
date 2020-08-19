package com.supporter.prj.cneec.tpc.benefit_budget.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.benefit_budget.entity.base.BaseVBenefitProject;

/**
 * @Title: VBenefitProject
 * @Description: 贸易项目效益预算-项目表.
 * @author: yanweichao
 * @date: 2018-06-06
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_V_BENEFIT_PROJECT", schema = "")
public class VBenefitProject extends BaseVBenefitProject implements Serializable {

	private static final long serialVersionUID = 1L;

	// Constructors

	/** default constructor */
	public VBenefitProject() {
		super();
	}

	public VBenefitProject(String projectId) {
		super(projectId);
	}

	public VBenefitProject(String projectId, String projectName) {
		super(projectId, projectName);
	}

	private String keyword;// 搜索关键字

	@Transient
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
