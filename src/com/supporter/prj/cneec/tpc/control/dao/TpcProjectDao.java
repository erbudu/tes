package com.supporter.prj.cneec.tpc.control.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.protocol_review.entity.ProtocolReview;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * 
 * @Title: TpcProjectDao
 * @Description: 
 * @author: yanweichao
 * @date: 2017-9-18
 * @version: V1.0
 */
@Repository("tpcProjectDao")
public class TpcProjectDao extends MainDaoSupport<RegisterProject, String> {

	/**
	 * 获取对象ListPage集合
	 * @param listPage
	 * @param parameters
	 * @param authFilter
	 * @return
	 * @throws ParseException
	 */
	public ListPage<RegisterProject> getListPage(ListPage<RegisterProject> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	/**
	 * 获取查询HQL
	 * @param parameters
	 * @param authFilter
	 * @return
	 * @throws ParseException
	 */
	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + RegisterProject.class.getName() + " t where 1=1");
		// 状态(默认为进行中)
		Integer swfStatus = (Integer) parameters.get("swfStatus");
		if (swfStatus == null || swfStatus != 0) {
			swfStatus = RegisterProject.PROCESSING;
		}
		hql.append(" and t.swfStatus = :swfStatus");
		parameters.put("swfStatus", swfStatus);
		// 其他过滤条件
		String keyword = (String) parameters.get("keyword");
		if (StringUtils.isNotBlank(keyword)) {
			hql.append(" and (t.projectNo like :keyword or t.projectName like :keyword )");
			parameters.put("keyword", "%" + keyword + "%");
		}
		// 项目性质
		if (StringUtils.isNotBlank((String) parameters.get("projectNatureId"))) {
			hql.append(" and t.projectNatureId = :projectNatureId");
		}
		// 项目类别
		if (StringUtils.isNotBlank((String) parameters.get("projectCategoryId"))) {
			hql.append(" and t.projectCategoryId = :projectCategoryId");
			// 非String字段属性需要将参数转换对象该属性定义的类型
			parameters.put("projectCategoryId", Integer.parseInt(parameters.get("projectCategoryId").toString()));
		}
		// 采购类型
		if (StringUtils.isNotBlank((String) parameters.get("purchaseTypeId"))) {
			hql.append(" and t.purchaseTypeId = :purchaseTypeId");
			parameters.put("purchaseTypeId", Integer.parseInt(parameters.get("purchaseTypeId").toString()));
		}
		// 预估金额
		double estimatedAmount1 = CommonUtil.parseDouble(String.valueOf(parameters.get("estimatedAmount1")), 0);
		double estimatedAmount2 = CommonUtil.parseDouble(String.valueOf(parameters.get("estimatedAmount2")), 0);
		if (estimatedAmount1 != 0 || estimatedAmount2 != 0) {
			hql.append(" and t.estimatedAmount between :estimatedAmount1 and :estimatedAmount2");
			parameters.put("estimatedAmount1", estimatedAmount1);
			parameters.put("estimatedAmount2", estimatedAmount2);
		}
		// 币别
		if (StringUtils.isNotBlank((String) parameters.get("currencyId"))) {
			hql.append(" and t.currencyId = :currencyId");
		}
		// 业务负责人
		if (StringUtils.isNotBlank((String) parameters.get("projectChargeId"))) {
			hql.append(" and t.projectChargeId = :projectChargeId");
		}
		// 是否框架协议评审选择项目（排除掉所有框架协议评审单已选择的项目）
		if (StringUtils.isNotBlank((String) parameters.get("isProtocolReview"))) {
			hql.append(" and t.hasFrameworkAgreement = :isProtocolReview");
			parameters.put("isProtocolReview", Boolean.parseBoolean(parameters.get("isProtocolReview").toString()));
			hql.append(" and t.projectId not in (select p.projectId from " + ProtocolReview.class.getName() + " p where p.projectId is not null)");
		}
		// 是否有框架协议
		if (StringUtils.isNotBlank((String) parameters.get("hasFrameworkAgreement"))) {
			hql.append(" and t.hasFrameworkAgreement = :hasFrameworkAgreement");
			parameters.put("hasFrameworkAgreement", Boolean.parseBoolean(parameters.get("hasFrameworkAgreement").toString()));
		}
		// 采购内容
		if (StringUtils.isNotBlank((String) parameters.get("purchaseContent"))) {
			hql.append(" and t.purchaseContent like :purchaseContent");
			parameters.put("purchaseContent", "%" + parameters.get("purchaseContent").toString() + "%");
		}
		// 项目所属部门
		if (StringUtils.isNotBlank((String) parameters.get("projectDeptId"))) {
			hql.append(" and t.projectDeptId = :projectDeptId");
		}
		//hql.append(" and " + authFilter);
		hql.append(" order by t.projectNo, t.projectName");
		return hql.toString();
	}

	/**
	 * JqGrid方式查询
	 * @param jqGrid
	 * @param registerProject
	 * @return
	 */
	public List<RegisterProject> findPage(JqGrid jqGrid, RegisterProject registerProject, String authFilter) {
		if (registerProject != null) {
			String keyword = registerProject.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" deptName like ? or projectNo like ? or projectName like ?",
						"%" + keyword + "%",
						"%" + keyword + "%",
						"%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 项目性质过滤
			if (registerProject.getProjectNatureId() != null) {
				jqGrid.addHqlFilter(" projectNatureId like ? ", "%" + registerProject.getProjectNatureId().replace("", "%") + "%");
			}
			// 项目类别过滤
			if (registerProject.getProjectCategoryId() != null) {
				jqGrid.addHqlFilter(" projectCategoryId= ? ", registerProject.getProjectCategoryId());
			}
			// 采购类型过滤
			if (registerProject.getPurchaseTypeId() != null) {
				jqGrid.addHqlFilter(" purchaseTypeId= ? ", registerProject.getPurchaseTypeId());
			}
			// 状态过滤
			if (registerProject.getSwfStatus() != null) {
				jqGrid.addHqlFilter(" swfStatus= ? ", registerProject.getSwfStatus());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

}
