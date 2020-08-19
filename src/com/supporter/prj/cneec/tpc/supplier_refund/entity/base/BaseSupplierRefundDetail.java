package com.supporter.prj.cneec.tpc.supplier_refund.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: TPC_SUPPLIER_REFUND_DETAIL字段与数据库字段一一对应.
 * @author liuermeng
 * @date 2018-09-12
 * @version V1.0
 */
@MappedSuperclass
public class BaseSupplierRefundDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	private String detailId;
	private String refundId ;
	private String collectionDetailId;
	private String refundClauseId;
	private String refundClause;
	private String budgetId;
	private String budgetName;
	private double refundAmount;
	private String currency;
	private String currencyId;
	private double rate;
	private double refundRmbAmount;
	private double retreatedAmount;//已退金额
	private double refundableAmount;//可退金额


	/**
	 * 无参构造函数.
	 */
	public BaseSupplierRefundDetail() {
	}

	/**
	 * 构造函数.
	 *
	 * @param detailId
	 */
	public BaseSupplierRefundDetail(String detailId) {
		this.detailId = detailId;
	}

	/**
	 * GET方法: 取得DETAIL_ID.
	 *
	 * @return: String  DETAIL_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "DETAIL_ID", nullable = false, length = 32)
	public String getDetailId() {
		return this.detailId;
	}

	/**
	 * SET方法: 设置DETAIL_ID.
	 *
	 * @param: String  DETAIL_ID
	 */
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	
	/**
	 * GET方法: 取得REFUND_ID.
	 *
	 * @return: String  REFUND_ID
	 */
	@Column(name = "REFUND_ID", nullable = true, length = 32)
	public String getRefundId() {
		return this.refundId;
	}

	/**
	 * SET方法: 设置REFUND_ID.
	 *
	 * @param: String  REFUND_ID
	 */
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	/**
	 * GET方法: 取得COLLECTION_DETAIL_ID.
	 *
	 * @return: String  COLLECTION_DETAIL_ID
	 */
	@Column(name = "COLLECTION_DETAIL_ID", nullable = true, length = 32)
	public String getCollectionDetailId() {
		return collectionDetailId;
	}


	/**
	 * SET方法: 设置COLLECTIONDETAIL_ID.
	 *
	 * @param: String  COLLECTIONDETAIL_ID
	 */
	public void setCollectionDetailId(String collectionDetailId) {
		this.collectionDetailId = collectionDetailId;
	}
	/**
	 * GET方法: 取得REFUND_CLAUSE_ID.
	 *
	 * @return: String  REFUND_CLAUSE_ID
	 */
	@Column(name = "REFUND_CLAUSE_ID", nullable = true, length = 32)
	public String getRefundClauseId() {
		return this.refundClauseId;
	}

	/**
	 * SET方法: 设置REFUND_CLAUSE_ID.
	 *
	 * @param: String  REFUND_CLAUSE_ID
	 */
	public void setRefundClauseId(String refundClauseId) {
		this.refundClauseId = refundClauseId;
	}
	/**
	 * GET方法: 取得REFUND_CLAUSE.
	 *
	 * @return: String  REFUND_CLAUSE
	 */
	@Column(name = "REFUND_CLAUSE", nullable = true, length = 64)
	public String getRefundClause() {
		return this.refundClause;
	}

	/**
	 * SET方法: 设置REFUND_CLAUSE.
	 *
	 * @param: String  REFUND_CLAUSE
	 */
	public void setRefundClause(String refundClause) {
		this.refundClause = refundClause;
	}

	/**
	 * GET方法: 取得BUDGET_ID.
	 *
	 * @return: String  BUDGET_ID
	 */
	@Column(name = "BUDGET_ID", nullable = true, length = 64)
	public String getBudgetId() {
		return this.budgetId;
	}

	/**
	 * SET方法: 设置BUDGET_ID.
	 *
	 * @param: String  BUDGET_ID
	 */
	public void setBudgetId(String budgetId) {
		this.budgetId = budgetId;
	}

	/**
	 * GET方法: 取得BUDGET_NAME.
	 *
	 * @return: String  BUDGET_NAME
	 */
	@Column(name = "BUDGET_NAME", nullable = true, length = 64)
	public String getBudgetName() {
		return this.budgetName;
	}

	/**
	 * SET方法: 设置BUDGET_NAME.
	 *
	 * @param: String  BUDGET_NAME
	 */
	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}

	/**
	 * GET方法: 取得REFUND_AMOUNT.
	 *
	 * @return: double  REFUND_AMOUNT
	 */
	@Column(name = "REFUND_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getRefundAmount() {
		return this.refundAmount;
	}

	/**
	 * SET方法: 设置REFUND_AMOUNT.
	 *
	 * @param: double  REFUND_AMOUNT
	 */
	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}

	/**
	 * GET方法: 取得CURRENCY.
	 *
	 * @return: String  CURRENCY
	 */
	@Column(name = "CURRENCY", nullable = true, length = 32)
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * SET方法: 设置CURRENCY.
	 *
	 * @param: String  CURRENCY
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * GET方法: 取得CURRENCY_ID.
	 *
	 * @return: String  CURRENCY_ID
	 */
	@Column(name = "CURRENCY_ID", nullable = true, length = 32)
	public String getCurrencyId() {
		return this.currencyId;
	}

	/**
	 * SET方法: 设置CURRENCY_ID.
	 *
	 * @param: String  CURRENCY_ID
	 */
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	/**
	 * GET方法: 取得RATE.
	 *
	 * @return: double  RATE
	 */
	@Column(name = "RATE", nullable = true, precision = 10, scale = 4)
	public double getRate() {
		return this.rate;
	}

	/**
	 * SET方法: 设置RATE.
	 *
	 * @param: double  RATE
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}
	/**
	 * GET方法: 取得REFUND_RMB_AMOUNT.
	 *
	 * @return: double  REFUND_RMB_AMOUNT
	 */
	@Column(name = "REFUND_RMB_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getRefundRmbAmount() {
		return this.refundRmbAmount;
	}

	/**
	 * SET方法: 设置REFUND_RMB_AMOUNT.
	 *
	 * @param: double  REFUND_RMB_AMOUNT
	 */
	public void setRefundRmbAmount(double refundRmbAmount) {
		this.refundRmbAmount = refundRmbAmount;
	}

	/**
	 * GET方法: 取得RETREATED_AMOUNT.
	 *
	 * @return: double  RETREATED_AMOUNT
	 */
	@Column(name = "RETREATED_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getRetreatedAmount() {
		return this.retreatedAmount;
	}

	/**
	 * SET方法: 设置RETREATED_AMOUNT.
	 *
	 * @param: double  RETREATED_AMOUNT
	 */
	public void setRetreatedAmount(double retreatedAmount) {
		this.retreatedAmount = retreatedAmount;
	}

	/**
	 * GET方法: 取得REFUNDABLE_AMOUNT.
	 *
	 * @return: double  REFUNDABLE_AMOUNT
	 */
	@Column(name = "REFUNDABLE_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getRefundableAmount() {
		return this.refundableAmount;
	}

	/**
	 * SET方法: 设置REFUNDABLE_AMOUNT.
	 *
	 * @param: double  REFUNDABLE_AMOUNT
	 */
	public void setRefundableAmount(double refundableAmount) {
		this.refundableAmount = refundableAmount;
	}

	
}
