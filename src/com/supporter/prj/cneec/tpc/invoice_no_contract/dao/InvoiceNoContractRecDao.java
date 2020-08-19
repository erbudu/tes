package com.supporter.prj.cneec.tpc.invoice_no_contract.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.invoice_no_contract.entity.InvoiceNoContractEntity;
import com.supporter.prj.cneec.tpc.invoice_no_contract.entity.InvoiceNoContractRecEntity;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * 非合同付款收发票明细表Dao
 * InvoiceNoContractRecDao
 * @author CHENHAO
 *
 */

@Repository
public class InvoiceNoContractRecDao extends MainDaoSupport<InvoiceNoContractRecEntity, String>{

	/**
	 * 分页查询非合同付款收发票明细表列表
	 * @param jqGrid
	 * @param invoiceId 
	 * @return
	 */
	public List<InvoiceNoContractRecEntity> findPage(JqGrid jqGrid, String invoiceId) {
		
		jqGrid.addHqlFilter("invoiceId = ?", invoiceId);
		
		return retrievePage(jqGrid);
	}

	public List<String> getPaymentNo(String invoiceId, String recordId, String prjId) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		String hql = "select paymentId from " + InvoiceNoContractRecEntity.class.getName() + 
				" a where ( actualPaymentAmount <= (select sum(apInvoiceAmount) from " + InvoiceNoContractRecEntity.class.getName() + 
				" b where a.paymentId = b.paymentId and recordId != :recordId) or invoiceId = :invoiceId) and recordId != :recordId"
				+ " and invoiceId in (select invoiceId from " + InvoiceNoContractEntity.class.getName() + " where prjId = :prjId)"
				+ "group by paymentId";
		param.put("invoiceId", invoiceId);
		param.put("recordId", recordId);
		param.put("prjId", prjId);
		
		return this.find(hql.toString(), param);
	}

	/**
	 * 根据非合同付款单ID和外键查询
	 * @param paymentId
	 * @param invoiceId
	 * @return
	 */
	public InvoiceNoContractRecEntity getByPaymentId(String paymentId, String invoiceId) {
		String hql = "from " + InvoiceNoContractRecEntity.class.getName() + " where invoiceId = ? and paymentId = ?";
		return this.findUniqueResult(hql, invoiceId, paymentId);
	}

	/**
	 * 获取单子的当前累计金额
	 * @param result
	 * @return
	 */
	public Double getSumAmount(String paymentId, String recordId) {
		String hql = "select sum(apInvoiceAmount) from " + InvoiceNoContractRecEntity.class.getName() + 
				" where paymentId = :paymentId and recordId != :recordId"; 
		Map<String, String> map = new HashMap<String, String>();
		map.put("paymentId", paymentId);
		map.put("recordId", recordId);
		return this.findUnique(hql, map);
	}

	
}
