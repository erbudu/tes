package com.supporter.prj.cneec.tpc.benefit_budget.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.benefit_budget.entity.VBenefitProject;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: BenefitProjectDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2018-06-06
 * @version: V1.0
 */
@Repository
public class VBenefitProjectDao extends MainDaoSupport<VBenefitProject, String> {

	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param benefitProject
	 * @return
	 */
	public List<VBenefitProject> findPage(JqGrid jqGrid, VBenefitProject benefitProject) {
		if (benefitProject != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = benefitProject.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectName like ?", "%" + keyword + "%");
			}
			// 排列
			jqGrid.addSortPropertyAsc("projectId");
		}
		return this.retrievePage(jqGrid);
	}

}
