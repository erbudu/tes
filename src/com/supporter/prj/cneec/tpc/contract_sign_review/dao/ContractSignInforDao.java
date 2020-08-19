package com.supporter.prj.cneec.tpc.contract_sign_review.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignInfor;
import com.supporter.prj.cneec.tpc.record_filing_manager.entity.RecordFilingManager;
import com.supporter.prj.cneec.tpc.use_seal.entity.UseSealDetail;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: ContractSignInforDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-09-29
 * @version: V1.0
 */
@Repository
public class ContractSignInforDao extends MainDaoSupport<ContractSignInfor, String> {

	/**
	 * 分页查询
	 */
	public List<ContractSignInfor> findPage(JqGrid jqGrid, ContractSignInfor contractSignInfor) {
		if (contractSignInfor != null) {
			jqGrid.addHqlFilter("signId = ?", contractSignInfor.getSignId());
			jqGrid.addHqlFilter("inforType = ?", contractSignInfor.getInforType());
		}
		return this.retrievePage(jqGrid);
	}

	public List<ContractSignInfor> getContractSignInforByInforType(String signId, Integer infortype) {
		String hql = " from " + ContractSignInfor.class.getName() + " where signId = ? and inforType= ? ";
		List<ContractSignInfor> editers = find(hql, signId, infortype);
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
	public List<ContractSignInfor> queryBy(String properName, Object propValue, Boolean likeSearch, String orderByName, Boolean sort) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(ContractSignInfor.class, properName, propValue, likeSearch, orderByName, sort);
		return (List<ContractSignInfor>) getHibernateTemplate().findByCriteria(dc);
	}

	/** 以下是选择评审单方法 **/

	@SuppressWarnings("unchecked")
	public List<ContractSignInfor> getList(Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrieve(hql, parameters);
	}

	public ListPage<ContractSignInfor> getListPage(ListPage<ContractSignInfor> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + ContractSignInfor.class.getName() + " t where 1=1");
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
