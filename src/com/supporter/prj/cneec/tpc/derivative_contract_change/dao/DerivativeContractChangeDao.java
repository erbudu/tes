package com.supporter.prj.cneec.tpc.derivative_contract_change.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeContractChange;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: ContractOnlineDao
 * @Description: DAO类
 * @version: V1.0
 */
@Repository
public class DerivativeContractChangeDao extends MainDaoSupport<DerivativeContractChange, String> {

	/**
	 * 分页查询
	 */
	public List<DerivativeContractChange> findPage(JqGrid jqGrid, DerivativeContractChange contractChange,  String contractId, String authFilter) {
		if (contractId != null) {
		if (contractChange != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = contractChange.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectName like ? or contractNo like ? or contractName like ? or filingNo like ? or contractParty like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			jqGrid.addHqlFilter("contractId = ?", contractId);
			// 状态过滤
			if (contractChange.getSwfStatus() != null) {
				jqGrid.addHqlFilter(" swfStatus = ? ", contractChange.getSwfStatus());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

	/** 以下是选择采购合同上线方法 **/

	@SuppressWarnings("unchecked")
	public List<DerivativeContractChange> getList(Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrieve(hql, parameters);
	}

	public ListPage<DerivativeContractChange> getListPage(ListPage<DerivativeContractChange> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + DerivativeContractChange.class.getName() + " t where 1=1");
		// 状态(默认为完成)
		Integer swfStatus = (Integer) parameters.get("swfStatus");
		if (swfStatus == null || swfStatus != 0) {
			swfStatus = DerivativeContractChange.COMPLETED;
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
		// 其他过滤条件
		String keyword = (String) parameters.get("keyword");
		if (StringUtils.isNotBlank(keyword)) {
			hql.append(" and (t.contractNo like :keyword)");
			parameters.put("keyword", "%" + keyword + "%");
		}
		hql.append(" order by t.contractNo");
		return hql.toString();
	}

	public String checkOrderChange(String contractId) {
		String hql = "from DerivativeContractChange where contractId = ? order by createdDate desc";
		List<DerivativeContractChange> orderList = find(hql, contractId );
		if ((orderList != null) && (orderList.size() > 0)) {
			return ((DerivativeContractChange) orderList.get(0)).getChangeId();
		}
		return "";
	}
	
}
