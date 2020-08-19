package com.supporter.prj.cneec.tpc.invoice.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.cneec.tpc.invoice.entity.Invoice;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-11-22 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class InvoiceDao extends MainDaoSupport < Invoice, String > {
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param contractIds 模块ids
	 * @return
	 */
	public List<Invoice> findPage(UserProfile user, JqGrid jqGrid, Invoice report) {
		Object[] params = new Object[]{false};
		StringBuffer hql = new StringBuffer("from "+Invoice.class.getName()+" where 1=1 ");
		String authHql = "";
		if(report != null){
			//查询
			String prjName = report.getPrjName();
			if(StringUtils.isNotBlank(prjName)){
				jqGrid.addHqlFilter(
						"prjName like ? or contractorName like ? or invoiceNo like ?", 
						"%" + prjName + "%","%" + prjName + "%","%" + prjName + "%");
//				hql.append(" and reportTitle like ? or empName like ? ");
//				params = ArrayUtils.add(params, "%" + reportTitle + "%");
//				params = ArrayUtils.add(params, "%" + reportTitle + "%");
			}
//			 //日期过滤
			if (report.getDateFrom() != null && report.getDateFrom().length() > 0) {
				jqGrid.addHqlFilter( "  confirmDate >= '" + report.getDateFrom() + "'");
        	}
			if (report.getDateTo() != null && report.getDateTo().length() > 0) {
				jqGrid.addHqlFilter( " confirmDate <= '" + report.getDateTo() + "'");
        	}
	        //授权
//	        authHql = EIPService.getAuthorityService().getHqlFilter(user, ReportConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME_SETVALREPORT);
			
//	        jqGrid.addHqlFilter(authHql);
			
			jqGrid.addSortPropertyDesc("invoiceId");
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
	public List<Invoice> findPagePast(UserProfile user, JqGrid jqGrid, Invoice report, String contractId) {
		String ls_SQL = "select settl.credit_letter_settlement_id as settlement_id " 
			+ "from tpc_credit_letter_apply apply,tpc_credit_letter_settlement settl " 
			+ "where apply.credit_letter_id = settl.credit_letter_id  and to_char(apply.contract_id) = ?"
			+ " and settl.settlement_status in ('"
			+ Invoice.Status.COMPLETE
			+ "','"+ Invoice.Status.PROCESSING
			+ "') ";
		List<String> settlementIds = this.sqlQuery(ls_SQL, null, contractId);
		List<Invoice> ll_Invoices = new ArrayList();
		for (String id : settlementIds) {
			Invoice lps_PrjCreditSettlement = this.get(id);
			ll_Invoices.add(lps_PrjCreditSettlement);
		}
		jqGrid.setRows(ll_Invoices);
		//日志
//		EIPService.getLogService(LogConstant.INFO_TYPE_DEBUG).info("hql:" + hql.toString() + "\t params:" + params+"\t authHql:"+authHql);
		return ll_Invoices;
	}
	/**
	 * 获取所有的报告记录.
	 * @param user
	 * @return
	 */
	public List<Invoice> getReportList() {
		StringBuffer hql = new StringBuffer("from "+Invoice.class.getName()+" where 1=1 ");
		List<Invoice> list = this.find(hql.toString());
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
