package com.supporter.prj.cneec.tpc.prj_contract_table.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractCollectionTerms;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractTable;
import com.supporter.prj.cneec.tpc.prj_contract_table.util.PrjContractTableConstant;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: PrjContractTableDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2018-03-15
 * @version: V1.0
 */
@Repository
public class PrjContractTableDao extends MainDaoSupport<PrjContractTable, String> {

	/**
	 * 分页查询
	 */
	public List<PrjContractTable> findPage(JqGrid jqGrid, PrjContractTable prjContractTable, String authFilter) {
		if (prjContractTable != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = prjContractTable.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectNo like ? or projectName like ? or contractNo like ? or contractName like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 状态过滤
			if (CommonUtil.trim(prjContractTable.getContractStatusCode()).length() > 0) {
				jqGrid.addHqlFilter(" contractStatusCode = ? ", prjContractTable.getContractStatusCode());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}
	
	public List<PrjContractTable> findSalePage(JqGrid jqGrid, PrjContractTable prjContractTable, String authFilter,
			String projectId, String contractTypeCode) {
		if (prjContractTable != null) {
			if (StringUtils.isNotBlank(contractTypeCode)) {
				jqGrid.addHqlFilter("contractTypeCode = ? ", new Object[] { contractTypeCode });
			}
			if (StringUtils.isNotBlank(projectId)) {
				jqGrid.addHqlFilter("projectId = ? ", new Object[] { projectId });
			}

			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter, new Object[0]);
		return retrievePage(jqGrid);
	}
	
	public List<PrjContractTable> findDerivatePage(JqGrid jqGrid, PrjContractTable prjContractTable, String authFilter,
			String projectId, String contractTypeCode) {
		if (prjContractTable != null) {
			if (StringUtils.isNotBlank(contractTypeCode)) {
				jqGrid.addHqlFilter("contractTypeCode = ? ", new Object[] { contractTypeCode });
			}
			if (StringUtils.isNotBlank(projectId)) {
				jqGrid.addHqlFilter("projectId = ? ", new Object[] { projectId });
			}

			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter, new Object[0]);
		return retrievePage(jqGrid);
	}

	public List<PrjContractTable> findContractPage(JqGrid jqGrid, PrjContractTable prjContractTable, String authFilter,
			String projectId, String contractTypeCode) {
		if (prjContractTable != null) {
			if (StringUtils.isNotBlank(contractTypeCode)) {
				jqGrid.addHqlFilter("contractTypeCode = ? ", new Object[] { contractTypeCode });
			}
			if (StringUtils.isNotBlank(projectId)) {
				jqGrid.addHqlFilter("projectId = ? ", new Object[] { projectId });
			}

			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter, new Object[0]);
		return retrievePage(jqGrid);
	}

	/** 以下是选择合同信息方法 **/
	@SuppressWarnings("unchecked")
	public List<PrjContractTable> getList(Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrieve(hql, parameters);
	}

	public ListPage<PrjContractTable> getListPage(ListPage<PrjContractTable> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + PrjContractTable.class.getName() + " t where 1=1");
		// 状态(默认为有效合同)
		String contractStatusCode = (String) parameters.get("contractStatusCode");
		if (CommonUtil.trim(contractStatusCode).length() == 0) {
			contractStatusCode = PrjContractTableConstant.EFFECT;
		}
		hql.append(" and t.contractStatusCode = :contractStatusCode");
		parameters.put("contractStatusCode", contractStatusCode);
		// 项目ID，必要条件
		String projectId = (String) parameters.get("projectId");
		if (StringUtils.isNotBlank(projectId)) {
			hql.append(" and t.projectId = :projectId");
		} else {
			hql.append(" and 1 <> 1");
		}
		// 合同类型，必要条件
		String contractTypeCode = (String) parameters.get("contractTypeCode");
		if (StringUtils.isNotBlank(contractTypeCode)) {
			hql.append(" and t.contractTypeCode = :contractTypeCode");
		} else {
			hql.append(" and 1 <> 1");
		}
		// 其他过滤条件
		String keyword = (String) parameters.get("keyword");
		if (StringUtils.isNotBlank(keyword)) {
			hql.append(" and (t.contractNo like :keyword or t.contractName like :keyword" +
					" or t.filingNo like :keyword or t.customerName like :keyword" +
					" or t.thirdParty like :keyword or t.contractParty like :keyword)");
			parameters.put("keyword", "%" + keyword + "%");
		}
		// 为特定模块选择过滤
		if (parameters.containsKey("selectTo")) {
			String selectTo = (String) parameters.get("selectTo");
			hql.append(getSelectToHql(selectTo, parameters));
		}
		hql.append(" order by t.contractNo");
		//System.out.println(hql);
		return hql.toString();
	}

	/**
	 * 为选择类型做过滤
	 * @param hql
	 * @param selectTo
	 * @return
	 */
	private String getSelectToHql(String selectTo, Map<String, Object> parameters) {
		StringBuffer filter = new StringBuffer();
		/*if (selectTo.equals(PrjContractTableConstant.FILTER_BENEFIT_CALCULATION)) {
			// 选择未生成项目效益预算表的合同
			filter.append(" and (");
			filter.append(" t.contractId not in (select t1.contractId from " + BenefitContract.class.getName() + " t1 where t1.projectId = :projectId)");
			filter.append(" )");
		}*/
		if (selectTo.equals(PrjContractTableConstant.FILTER_SALE_CONTRACT_COLLECTION)) {
			// 选择可进行销售合同收款的销售合同(按原币金额计算)
			filter.append(" and (");
			filter.append(" t.contractId in (select t1.contractId from " + PrjContractCollectionTerms.class.getName() + " t1 where t1.receiveAmount > (t1.onwayAmount + t1.realReceiveAmount) )");
			filter.append(" )");
		} else if (selectTo.equals(PrjContractTableConstant.FILTER_PURCHASE_CONTRACT_PAYMENT)) {
			// 选择可进行采购合同付款的采购合同(按原币金额计算)
			filter.append(" and (");
			filter.append(" t.contractId in (select t1.contractId from " + PrjContractCollectionTerms.class.getName() + " t1 where t1.receiveAmount > (t1.onwayAmount + t1.realReceiveAmount) )");
			filter.append(" )");
		}
		return filter.toString();
	}

}
