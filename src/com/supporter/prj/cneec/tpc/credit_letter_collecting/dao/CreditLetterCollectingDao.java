package com.supporter.prj.cneec.tpc.credit_letter_collecting.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.cneec.tpc.credit_letter_collecting.entity.CreditLetterCollecting;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-11-22 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class CreditLetterCollectingDao extends MainDaoSupport < CreditLetterCollecting, String > {
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param contractIds 模块ids
	 * @return
	 */
	public List<CreditLetterCollecting> findPage(UserProfile user, JqGrid jqGrid, CreditLetterCollecting report) {
		Object[] params = new Object[]{false};
		StringBuffer hql = new StringBuffer("from "+CreditLetterCollecting.class.getName()+" where 1=1 ");
		String authHql = "";
		if(report != null){
			//查询
			String currencyType = report.getCurrencyType();
			if(StringUtils.isNotBlank(currencyType)){
				jqGrid.addHqlFilter(
						"projectName like ? or currencyType like ? or contractName like ?", 
						"%" + currencyType + "%","%" + currencyType + "%","%" + currencyType + "%");
//				hql.append(" and reportTitle like ? or empName like ? ");
//				params = ArrayUtils.add(params, "%" + reportTitle + "%");
//				params = ArrayUtils.add(params, "%" + reportTitle + "%");
			}
//			 //状态过滤
	        //if(report.getSwfStatus() > -1){
	        //	jqGrid.addHqlFilter("swfStatus = ?" ,report.getSwfStatus());
//	        	hql.append(" and reportStatus = ? ");
//				params = ArrayUtils.add(params, report.getSettlementStatus());
	       // }
	        //授权
//	        authHql = EIPService.getAuthorityService().getHqlFilter(user, ReportConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME_SETVALREPORT);
			
//	        jqGrid.addHqlFilter(authHql);
			
			jqGrid.addSortPropertyDesc("creditLetterCollectingId");
		}
		//日志
//		EIPService.getLogService(LogConstant.INFO_TYPE_DEBUG).info("hql:" + hql.toString() + "\t params:" + params+"\t authHql:"+authHql);
		return this.retrievePage(jqGrid);
	}

	/**
	 * 分页查询
	 * @param jqGrid
	 * @param contractIds 模块ids
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CreditLetterCollecting> findPagePast(UserProfile user, JqGrid jqGrid, CreditLetterCollecting report, String contractId) {
		String ls_SQL = "select settl.credit_letter_collecting_id as settlement_id " 
			+ "from tpc_credit_letter_apply apply,tpc_credit_letter_collecting settl " 
			+ "where apply.credit_letter_id = settl.credit_letter_id  and to_char(apply.contract_id) = ?"
			+ " and settl.settlement_status in ('"
			+ CreditLetterCollecting.CreditLetterSwfStatus.COMPLETE.getKey()
			+ "','"+ CreditLetterCollecting.CreditLetterSwfStatus.PROCESSING.getKey()
			+ "') ";
		List<String> settlementIds = this.sqlQuery(ls_SQL, null, contractId);
		List<CreditLetterCollecting> ll_CreditLetterCollectings = new ArrayList();
		for (String id : settlementIds) {
			CreditLetterCollecting lps_PrjCreditSettlement = this.get(id);
			ll_CreditLetterCollectings.add(lps_PrjCreditSettlement);
		}
		jqGrid.setRows(ll_CreditLetterCollectings);
		//日志
//		EIPService.getLogService(LogConstant.INFO_TYPE_DEBUG).info("hql:" + hql.toString() + "\t params:" + params+"\t authHql:"+authHql);
		return ll_CreditLetterCollectings;
	}
	/**
	 * 获取所有的报告记录.
	 * @param user
	 * @return
	 */
	public List<CreditLetterCollecting> getReportList() {
		StringBuffer hql = new StringBuffer("from "+CreditLetterCollecting.class.getName()+" where 1=1 ");
		List<CreditLetterCollecting> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
	
	/**
	 * 判断名字是否重复
	 * 
	 * @param contractId
	 * @param contractName
	 * @return
	 */
//	public boolean checkNameIsValid(String contractId, String contractName) {
//		String hql = null;
//		List retList = null;
//		if (StringUtils.isBlank(contractId)) {// 新建时
//			hql = "from " + Report.class.getName() + " where contractName = ?";
//			retList = this.retrieve(hql, contractName);
//		} else {// 编辑时
//			hql = "from " + Report.class.getName() + " where contractId != ? and contractName = ?";
//			retList = this.retrieve(hql, contractId, contractName);
//		}
//		if (CollectionUtils.isEmpty(retList)) {
//			return true;
//		}
//		return false;
//	}
	
}
