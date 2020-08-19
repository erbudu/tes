package com.supporter.prj.cneec.tpc.purchase_demand_sheet.dao;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.purchase_demand_sheet.entity.PurchaseDemandSheet;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: PurchaseDemandSheetDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2018-04-10
 * @version: V1.0
 */
@Repository
public class PurchaseDemandSheetDao extends MainDaoSupport<PurchaseDemandSheet, String> {

	/**
	 * 分页查询
	 */
	public List<PurchaseDemandSheet> findPage(JqGrid jqGrid, PurchaseDemandSheet purchaseDemandSheet, String authFilter) {
		if (purchaseDemandSheet != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = purchaseDemandSheet.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" batchNo like ? or projectName like ? or purchaseDemandName like ? or customerName like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}
			// 项目过滤
			if (StringUtils.isNotBlank(purchaseDemandSheet.getProjectId())) {
				jqGrid.addHqlFilter(" projectId = ? ", purchaseDemandSheet.getProjectId());
			} else {
				jqGrid.addHqlFilter(" 1 <> 1 ");
			}
			// 过滤可选择记录
			if (purchaseDemandSheet.getDefaultSelect()) {
				jqGrid.addHqlFilter(" (reviewStatus = ? or reviewConclusion = ?)", PurchaseDemandSheet.NO_REVIEW, TpcConstant.REVIEW_CONCLUSION_CONTINUE + "");
			}
			// 排列
			jqGrid.addSortPropertyDesc("customerId");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

	/**
	 * 根据字段多个值查询
	 * @param Fields
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PurchaseDemandSheet> getListByFields(String fieldName, String fields) {
		// 定义hql
		StringBuilder hql = new StringBuilder("from " + PurchaseDemandSheet.class.getName() + " t where 1 = 1");

		Map<String, Object> filterMap = new LinkedHashMap<String, Object>();
		String[] recordIdStr = fields.split(",");
		for (int i = 0; i < recordIdStr.length; i++) {
			filterMap.put(fieldName + i, recordIdStr[i]);
		}
		// 过滤参数
		String filter = "";
		for (Map.Entry<String, Object> entry : filterMap.entrySet()) {
			if (filter.length() > 0) filter = filter + " or ";
			filter += fieldName + " = :" + entry.getKey();
		}

		hql.append(" and ").append("(").append(filter).append(")");
		return this.retrieve(hql.toString(), filterMap);
	}

	/**
	 * 选择控件
	 * @param listPage
	 * @param parameters
	 * @param authFilter
	 * @return
	 * @throws ParseException
	 */
	public ListPage<PurchaseDemandSheet> getListPage(ListPage<PurchaseDemandSheet> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + PurchaseDemandSheet.class.getName() + " t where 1 = 1");

		// 有效记录（未评审或评审结论为继续谈判的记录）
		Integer reviewStatus = (Integer) parameters.get("reviewStatus");
		if (reviewStatus == null) {
			reviewStatus = PurchaseDemandSheet.NO_REVIEW;
		}
		String reviewConclusion = (String) parameters.get("reviewConclusion");
		if (StringUtils.isBlank(reviewConclusion)) {
			reviewConclusion = TpcConstant.REVIEW_CONCLUSION_CONTINUE + "";
		}
		hql.append(" and (t.reviewStatus = :reviewStatus or t.reviewConclusion = :reviewConclusion)");
		parameters.put("reviewStatus", reviewStatus);
		parameters.put("reviewConclusion", reviewConclusion);

		// 所属项目，必要字段
		String projectId = (String) parameters.get("projectId");
		if (StringUtils.isNotBlank(projectId)) {
			hql.append(" and t.projectId = :projectId");
		} else {
			hql.append(" and 1 <> 1");
		}

		// 单一类别，必要字段
		String singleCategoryCode = (String) parameters.get("singleCategoryCode");
		if (StringUtils.isNotBlank(singleCategoryCode)) {
			hql.append(" and t.singleCategoryCode = :singleCategoryCode");
		} else {
			hql.append(" and 1 <> 1");
		}

		// 其他过滤条件
		String keyword = (String) parameters.get("keyword");
		if (StringUtils.isNotBlank(keyword)) {
			hql.append(" and (t.batchNo like :keyword or t.projectName like :keyword or t.customerName like :keyword )");
			parameters.put("keyword", "%" + keyword + "%");
		}
		
		//hql.append(" and " + authFilter);
		hql.append(" order by t.customerNo, t.customerName");
		return hql.toString();
	}

	/**
	 * 根据ID批量修改数据
	 * @param values 修改的字段属性键值对
	 * @param sheetIds 修改的记录ID
	 * @return
	 */
	public int updateReview(Map<String, Object> values, String sheetIds, String recordIds) {
		// 判断是否可以修改
		if (values == null || values.isEmpty() || (StringUtils.isBlank(sheetIds) && StringUtils.isBlank(recordIds))) return -1;
		
		Map<String, Object> filterMap = new LinkedHashMap<String, Object>();
		String fileterName = "";
		if (StringUtils.isNotBlank(sheetIds)) {
			fileterName = "sheetId";
			String[] sheetIdStr = sheetIds.split(",");
			for (int i = 0; i < sheetIdStr.length; i++) {
				filterMap.put("sheetId" + i, sheetIdStr[i]);
			}
		} else {
			fileterName = "recordId";
			String[] recordIdStr = recordIds.split(",");
			for (int i = 0; i < recordIdStr.length; i++) {
				filterMap.put("recordId" + i, recordIdStr[i]);
			}
		}

		// 定义hql
		StringBuilder hql = new StringBuilder("update ").append(PurchaseDemandSheet.class.getName()).append(" set ");

		// 修改属性
		boolean isFirst = true;
		for (Map.Entry<String, Object> entry : values.entrySet()) {
			if (isFirst)
				isFirst = false;
			else {
				hql.append(", ");
			}
			hql.append((String) entry.getKey()).append(" = :").append((String) entry.getKey());
		}

		// 过滤参数
		String filter = "";
		for (Map.Entry<String, Object> entry : filterMap.entrySet()) {
			if (filter.length() > 0) filter = filter + " or ";
			filter = filter + fileterName + " = :" + ((String) entry.getKey());
		}

		hql.append(" where ").append("(").append(filter).append(")");

		Map<String, Object> dataParams = new LinkedHashMap<String, Object>();
		dataParams.putAll(values);
		dataParams.putAll(filterMap);
		return execUpdate(hql.toString(), dataParams);
	}

}
