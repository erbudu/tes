package com.supporter.prj.cneec.tpc.invoice.dao;



import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.cneec.tpc.invoice.entity.Invoice;
import com.supporter.prj.cneec.tpc.invoice.entity.InvoiceRec;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class InvoiceRecDao extends MainDaoSupport < InvoiceRec, String > {
	
	/**
	 * 根据信用证付款id获取明细信息
	 * @param jqGrid
	 * @param invoiceId
	 * @return
	 */
	public List<InvoiceRec> getRecGrid(JqGrid jqGrid, String invoiceId) {
		if (StringUtils.isNotBlank(invoiceId)) {
			jqGrid.addHqlFilter("invoiceId = ?  ", invoiceId);
			jqGrid.addSortPropertyAsc("recordId");
			return this.retrievePage(jqGrid);
		} else {
			return null;
		}
	}
	/**
	 * 根据信用证付款id获取明细信息
	 * @param jqGrid
	 * @param invoiceId
	 * @return
	 */
	public List<InvoiceRec> getRecList(String invoiceId){
		String hql = "from " + InvoiceRec.class.getName() + " where invoiceId = ?  ";
		return this.find(hql, invoiceId);
	}
	/**
	 * 修改时删除原有明细数据
	 * @param recordId
	 * @return
	 */
	public void deleteBySettlementId(String invoiceId){	
		String hql = "from " + InvoiceRec.class.getName() + " where invoiceId = ?  ";
		List<InvoiceRec> lists = this.find(hql, invoiceId);
		this.delete(lists);
	}

	/**
	 * 累计收到发票金额(带审批中)
	 * @param contractId
	 * @return
	 */
	public double getDetailInvoiceAmount(String contractId) {
		double amount = 0;
		String hql = "select sum(rec.apInvoiceAmount) "
				+ "from " + InvoiceRec.class.getName() + " rec," + Invoice.class.getName() + " invoice where"
				+ " rec.contractId = ?"
				+ " and ( invoice.invoiceStatus = " + Invoice.Status.PROCESSING
				+ " or invoice.invoiceStatus = " + Invoice.Status.FINANCE_DEPARTMENT_ADD 
				+ " or invoice.invoiceStatus = " + Invoice.Status.COMPLETE + ")"
				+ " and rec.invoiceId = invoice.invoiceId ";
		List<Object> list = this.find(hql, contractId);
		if (list != null && list.size() > 0) {
			if (list.get(0) != null) {// 可能为空
				amount = Double.valueOf(list.get(0).toString());
			}
		}
		return amount;
	}

	/**
	 * 累计收到发票金额
	 * @param contractId
	 * @return
	 */
	public double getTotalInvoiceAmount(String contractId) {
		double amount = 0;
		String hql = "select sum(rec.apInvoiceAmount) "
				+ "from " + InvoiceRec.class.getName() + " rec," + Invoice.class.getName() + " invoice where"
				+ " rec.contractId = ?"
				+ " and ( invoice.invoiceStatus = " + Invoice.Status.FINANCE_DEPARTMENT_ADD 
				+ " or invoice.invoiceStatus = " + Invoice.Status.COMPLETE + ")"
				+ " and rec.invoiceId = invoice.invoiceId ";
		List<Object> list = this.find(hql, contractId);
		if (list != null && list.size() > 0) {
			if (list.get(0) != null) {// 可能为空
				amount = Double.valueOf(list.get(0).toString());
			}
		}
		return amount;
	}

}
