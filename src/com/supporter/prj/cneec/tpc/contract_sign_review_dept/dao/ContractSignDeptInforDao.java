package com.supporter.prj.cneec.tpc.contract_sign_review_dept.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptInfor;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptReview;
import com.supporter.prj.cneec.tpc.record_filing_manager.entity.RecordFilingManager;
import com.supporter.prj.cneec.tpc.use_seal.entity.UseSealDetail;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: ContractSignDeptInforDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2018-03-21
 * @version: V1.0
 */
@Repository
public class ContractSignDeptInforDao extends MainDaoSupport<ContractSignDeptInfor, String> {

	/**
	 * 分页查询
	 */
	public List<ContractSignDeptInfor> findPage(JqGrid jqGrid, Map<String, Object> parameters) {
		if (parameters != null && !parameters.isEmpty()) {
			if (parameters.containsKey("signId")) {
				jqGrid.addHqlFilter("signId = ?", parameters.get("signId").toString());
			}
			if (parameters.containsKey("inforType")) {
				jqGrid.addHqlFilter("inforType = ?", Integer.parseInt(parameters.get("inforType").toString()));
			}
			jqGrid.addSortPropertyAsc("inforId");
		}
		return this.retrievePage(jqGrid);
	}

	/**
	 * 根据条件过滤数据
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ContractSignDeptInfor> findList(Map<String, Object> parameters) {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + ContractSignDeptInfor.class.getName() + " t where 1 = 1");
		if (parameters.containsKey("inforType")) {
			hql.append(" and t.inforType = :inforType");// 合同类型
		}
		if (parameters.containsKey("singleCategoryCode")) {
			String singleCategoryCode = (String) parameters.get("singleCategoryCode");
			if (singleCategoryCode.equals(TpcConstant.SINGLE_SUPPLIER)) {
				// 为单一供方选择本项目下已完成的销售合同评审单下采购合同
				hql.append(" and t.signId in (select s.signId from " + ContractSignDeptReview.class.getName() + " s where s.projectId = :projectId)");
			}
		}
		//System.out.println(hql);
		return this.retrieve(hql.toString(), parameters);
	}
	
	public List<ContractSignDeptInfor> getContractSignDeptInforByInforType(String signId, Integer infortype) {
		String hql = " from " + ContractSignDeptInfor.class.getName() + " where signId = ? and inforType = ? ";
		List<ContractSignDeptInfor> editers = find(hql, signId, infortype);
		if (editers == null || editers.size() == 0) return null;
		return editers;
	}

	/**
	 * 条件过滤
	 * @param properName
	 * @param propValue
	 * @param likeSearch
	 * @param orderByName
	 * @param sort
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ContractSignDeptInfor> queryBy(String properName, Object propValue, Boolean likeSearch, String orderByName, Boolean sort) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(ContractSignDeptInfor.class, properName, propValue, likeSearch, orderByName, sort);
		return (List<ContractSignDeptInfor>) getHibernateTemplate().findByCriteria(dc);
	}

	/** 以下是选择评审单方法 **/

	@SuppressWarnings("unchecked")
	public List<ContractSignDeptInfor> getList(Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrieve(hql, parameters);
	}

	public ListPage<ContractSignDeptInfor> getListPage(ListPage<ContractSignDeptInfor> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + ContractSignDeptInfor.class.getName() + " t where 1=1");
		// 评审单ID，必要字段
		String signId = (String) parameters.get("signId");
		if (StringUtils.isNotBlank(signId)) {
			hql.append(" and t.signId = :signId");
		} else {
			hql.append(" and 1 <> 1");
		}
		// 用印选择或备案选择
		String type = (String) parameters.get("type");
		if (CommonUtil.trim(type).equals("useSeal")) {
			// 用印选择评审单时排除掉其他用印单已选择过的评审单(用印单从表存储销售合同/采购合同记录ID)
			hql.append(" and t.inforId not in (select u.signId from " + UseSealDetail.class.getName() + " u where u.signId is not null)");
		} else if (CommonUtil.trim(type).equals("recordFiling")) {
			// 备案选择评审单时排除掉其他用印单已选择过的评审单(备案单存储销售合同/采购合同记录ID)
			hql.append(" and t.inforId not in (select f.signId from " + RecordFilingManager.class.getName() + " f where f.signId is not null)");
		}
		// 评审类别(即销售合同/采购合同，用印/备案业务类型选择)，必要字段
		int inforType = CommonUtil.parseInt(String.valueOf(parameters.get("reviewType")), 0);
		if (inforType != 0) {
			hql.append(" and (t.inforType = :reviewType)");
			parameters.put("reviewType", inforType);
		} else {
			hql.append(" and 1 <> 1");
		}
		hql.append(" order by t.contractParty");
		return hql.toString();
	}

}
