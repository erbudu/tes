package com.supporter.prj.cneec.tpc.credit_letter_settlement.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.credit_letter_apply.entity.CreditLetterApply;
import com.supporter.prj.cneec.tpc.credit_letter_settlement.entity.CreditLetterSettlement;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-11-22 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class CreditLetterSettlementDao extends MainDaoSupport < CreditLetterSettlement, String > {
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param contractIds 模块ids
	 * @return
	 */
	public List<CreditLetterSettlement> findPage(UserProfile user, JqGrid jqGrid, CreditLetterSettlement report, Map<String, Object> paramsMap) {
		Object[] params = new Object[]{false};
		StringBuffer hql = new StringBuffer("from "+CreditLetterSettlement.class.getName()+" where 1=1 ");
		String authHql = "";
		if(report != null){
			//查询
			String currencyType = report.getCurrencyType();
			if(StringUtils.isNotBlank(currencyType)){
				jqGrid.addHqlFilter(
						"to_char(settlementAmountAct) like ? or currencyType like ? or to_char(amountSettlement) like ?", 
						"%" + currencyType + "%","%" + currencyType + "%","%" + currencyType + "%");
//				hql.append(" and reportTitle like ? or empName like ? ");
//				params = ArrayUtils.add(params, "%" + reportTitle + "%");
//				params = ArrayUtils.add(params, "%" + reportTitle + "%");
			}
//			 //状态过滤
	        if(report.getSettlementStatus() > -1){
	        	jqGrid.addHqlFilter("settlementStatus = ?" ,report.getSettlementStatus());
//	        	hql.append(" and reportStatus = ? ");
//				params = ArrayUtils.add(params, report.getSettlementStatus());
	        }
	        if(report.getControlStatusCode() != null && !"".equals(report.getControlStatusCode())){
	        	jqGrid.addHqlFilter("controlStatusCode = ?" ,report.getControlStatusCode());
//	        	hql.append(" and reportStatus = ? ");
//				params = ArrayUtils.add(params, report.getSettlementStatus());
	        }
	        // 按项目过滤
	        if (paramsMap != null) {
	        	if (paramsMap.containsKey("projectId")) {
	        		jqGrid.addHqlFilter("creditLetterId in (select apply.creditLetterId from " + CreditLetterApply.class.getName() + " apply where apply.projectId = ? )",
	        				paramsMap.get("projectId").toString());
	        	}
	        }
	        //授权
//	        authHql = EIPService.getAuthorityService().getHqlFilter(user, ReportConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME_SETVALREPORT);
			
//	        jqGrid.addHqlFilter(authHql);
	        
			jqGrid.addSortPropertyDesc("creditLetterSettlementId");
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
	public List<CreditLetterSettlement> findPagePast(UserProfile user, JqGrid jqGrid, CreditLetterSettlement report, String contractId) {
		String ls_SQL = "select settl.credit_letter_settlement_id as settlement_id " 
			+ "from tpc_credit_letter_apply apply,tpc_credit_letter_settlement settl " 
			+ "where apply.credit_letter_id = settl.credit_letter_id  and to_char(apply.contract_id) = ?"
			+ " and settl.settlement_status in ('"
			+ CreditLetterSettlement.CreditLetterSettlementStatus.COMPLETE.getKey()
			+ "','"+ CreditLetterSettlement.CreditLetterSettlementStatus.PROCESSING.getKey()
			+ "') and settl.control_status_code='"+CreditLetterSettlement.ControlStatus.EFFECTIV+"'";
		List<String> settlementIds = this.sqlQuery(ls_SQL, null, contractId);
		List<CreditLetterSettlement> ll_CreditLetterSettlements = new ArrayList();
		for (String id : settlementIds) {
			CreditLetterSettlement lps_PrjCreditSettlement = this.get(id);
			ll_CreditLetterSettlements.add(lps_PrjCreditSettlement);
		}
		jqGrid.setRows(ll_CreditLetterSettlements);
		//日志
//		EIPService.getLogService(LogConstant.INFO_TYPE_DEBUG).info("hql:" + hql.toString() + "\t params:" + params+"\t authHql:"+authHql);
		return ll_CreditLetterSettlements;
	}
	/**
	 * 获取所有的报告记录.
	 * @param user
	 * @return
	 */
	public List<CreditLetterSettlement> getReportList() {
		StringBuffer hql = new StringBuffer("from "+CreditLetterSettlement.class.getName()+" where 1=1 ");
		List<CreditLetterSettlement> list = this.find(hql.toString());
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
	
    /**
     * 根据采购合同实例获取它的所有在途金额总和(审核中和审批完成的).
     * @author ts
     * @param PrjContract 合同实例.
     * @return 本合同的在途金额总和.
     */
	public double getTotalAmount(String ls_ContractId,String currencyCode,String content, String settlementDate){
		String ls_SQL = "select ?"
    		+ " from tpc_credit_letter_apply apply,tpc_credit_letter_settlement setl "
    		+ " where apply.contract_id = ?" 
    		+ "  and apply.credit_letter_id = setl.credit_letter_id"
    		+ " and (setl.settlement_status = 1 or setl.settlement_status = 2)"
    		+" and setl.control_status_code='EFFECTIV'"
        	+ " and apply.currency_type_code = ?";
		if (StringUtils.isNotBlank(settlementDate)) {
			ls_SQL += " and apply.created_date < '" + settlementDate + "' ";
		}
		List<BigDecimal> lds_Data = this.sqlQuery(ls_SQL, null, content, ls_ContractId, currencyCode);
        double ld_TotalCreditSettlementAmountAct = 0;
        for (BigDecimal ld_CreditOnwayAmount : lds_Data) {
        		ld_TotalCreditSettlementAmountAct = BigDecimal.valueOf(ld_TotalCreditSettlementAmountAct).add(ld_CreditOnwayAmount).doubleValue();
        }
    	return BigDecimal.valueOf(ld_TotalCreditSettlementAmountAct).doubleValue();
	}
	
}
