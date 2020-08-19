package com.supporter.prj.cneec.tpc.external_quotation_review_dept.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.external_quotation_review_dept.entity.ExternalQuotationReviewDept;
import com.supporter.prj.cneec.tpc.record_filing_manager.entity.RecordFilingManager;
import com.supporter.prj.cneec.tpc.use_seal.entity.UseSealDetail;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: ExternalQuotationReviewDeptDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2018-03-20
 * @version: V1.0
 */
@Repository
public class ExternalQuotationReviewDeptDao extends MainDaoSupport<ExternalQuotationReviewDept, String> {

	/**
	 * 分页查询
	 */
	public List<ExternalQuotationReviewDept> findPage(JqGrid jqGrid, ExternalQuotationReviewDept externalQuotationReviewDept, String authFilter) {
		if (externalQuotationReviewDept != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = externalQuotationReviewDept.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectName like ? or batchNo like ? or quotationTitle like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 状态过滤
			if (externalQuotationReviewDept.getSwfStatus() != null) {
				jqGrid.addHqlFilter(" swfStatus = ? ", externalQuotationReviewDept.getSwfStatus());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

	/** 以下是选择对外报价评审方法 **/

	@SuppressWarnings("unchecked")
	public List<ExternalQuotationReviewDept> getList(Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrieve(hql, parameters);
	}

	public ListPage<ExternalQuotationReviewDept> getListPage(ListPage<ExternalQuotationReviewDept> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ExternalQuotationReviewDept> getExternalQuotationReviewDeptList(Map<String, Object> parameters) throws ParseException {
		String hql = getHql(parameters, null);
		return this.retrieve(hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + ExternalQuotationReviewDept.class.getName() + " t where 1=1");
		// 状态(默认为完成)
		Integer swfStatus = (Integer) parameters.get("swfStatus");
		if (swfStatus == null || swfStatus != 0) {
			swfStatus = ExternalQuotationReviewDept.COMPLETED;
		}
		hql.append(" and t.swfStatus = :swfStatus");
		parameters.put("swfStatus", swfStatus);
		// 项目ID，必要字段
		String projectId = (String) parameters.get("projectId");
		if (StringUtils.isNotBlank(projectId)) {
			hql.append(" and t.projectId = :projectId");
		} else {
			hql.append(" and 1 <> 1");
		}
		// 用印选择或备案选择
		String type = (String) parameters.get("type");
		if (CommonUtil.trim(type).equals("useSeal")) {
			// 用印选择评审单时排除掉其他用印单已选择过的评审单
			hql.append(" and t.externalId not in (select u.signId from " + UseSealDetail.class.getName() + " u where u.signId is not null)");
		} else if (CommonUtil.trim(type).equals("recordFiling")) {
			// 备案选择评审单时排除掉其他用印单已选择过的评审单
			hql.append(" and t.externalId not in (select f.signId from " + RecordFilingManager.class.getName() + " f where f.signId is not null)");
		}
		// 其他过滤条件
		String keyword = (String) parameters.get("keyword");
		if (StringUtils.isNotBlank(keyword)) {
			hql.append(" and (t.customerNo like :keyword or t.customerName like :keyword )");
			parameters.put("keyword", "%" + keyword + "%");
		}
		/*// 需求单ID，必要字段，根据客户采购需求单过滤对外报价评审
		String demandId = (String) parameters.get("demandId");
		String demandIds = (String) parameters.get("demandIds");
		if (StringUtils.isNotBlank(demandId)) {
			hql.append(" and t.demandId = :demandId");
		} else if (StringUtils.isNotBlank(demandIds)) {
			demandIds = demandIds.replace(",", "','");
			hql.append(" and t.demandId in ('" + demandIds + "')");
		} else {
			hql.append(" and 1 <> 1");
		}*/
		hql.append(" order by t.demandId, t.quotationTitle");
		return hql.toString();
	}

}
