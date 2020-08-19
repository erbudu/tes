package com.supporter.prj.cneec.tpc.collection_confirmation.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: TPC_COLLECTION_DETAIL,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2017-11-17
 * @version V1.0
 */
@MappedSuperclass
public class BaseCollectionDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	private String detailId;
	private String collectionId;
	private String collectionClauseId;
	private String collectionClause;
	private String budgetId;
	private String budgetName;
	private double collectionAmount;
	private String currency;
	private String currencyId;
	private double rate;
	private double collectionRmbAmount;
	private String collectionDate;
	private double onwayAmount;// 在途金额(按原币)
	private double realCollectionAmount;// 实际收款金额(按原币)
	private String remarks;

	/**
	 * 无参构造函数.
	 */
	public BaseCollectionDetail() {
	}

	/**
	 * 构造函数.
	 *
	 * @param detailId
	 */
	public BaseCollectionDetail(String detailId) {
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
	 * GET方法: 取得COLLECTION_ID.
	 *
	 * @return: String  COLLECTION_ID
	 */
	@Column(name = "COLLECTION_ID", nullable = true, length = 32)
	public String getCollectionId() {
		return this.collectionId;
	}

	/**
	 * SET方法: 设置COLLECTION_ID.
	 *
	 * @param: String  COLLECTION_ID
	 */
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}

	@Column(name = "COLLECTION_CLAUSE_ID", nullable = true, length = 32)
	public String getCollectionClauseId() {
		return this.collectionClauseId;
	}

	public void setCollectionClauseId(String collectionClauseId) {
		this.collectionClauseId = collectionClauseId;
	}

	/**
	 * GET方法: 取得COLLECTION_CLAUSE.
	 *
	 * @return: String  COLLECTION_CLAUSE
	 */
	@Column(name = "COLLECTION_CLAUSE", nullable = true, length = 128)
	public String getCollectionClause() {
		return this.collectionClause;
	}

	/**
	 * SET方法: 设置COLLECTION_CLAUSE.
	 *
	 * @param: String  COLLECTION_CLAUSE
	 */
	public void setCollectionClause(String collectionClause) {
		this.collectionClause = collectionClause;
	}

	/**
	 * GET方法: 取得BUDGET_ID.
	 *
	 * @return: String  BUDGET_ID
	 */
	@Column(name = "BUDGET_ID", nullable = true, length = 32)
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
	 * GET方法: 取得COLLECTION_AMOUNT.
	 *
	 * @return: double  COLLECTION_AMOUNT
	 */
	@Column(name = "COLLECTION_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getCollectionAmount() {
		return this.collectionAmount;
	}

	/**
	 * SET方法: 设置COLLECTION_AMOUNT.
	 *
	 * @param: double  COLLECTION_AMOUNT
	 */
	public void setCollectionAmount(double collectionAmount) {
		this.collectionAmount = collectionAmount;
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
	 * GET方法: 取得COLLECTION_RMB_AMOUNT.
	 *
	 * @return: double  COLLECTION_RMB_AMOUNT
	 */
	@Column(name = "COLLECTION_RMB_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getCollectionRmbAmount() {
		return this.collectionRmbAmount;
	}

	/**
	 * SET方法: 设置COLLECTION_RMB_AMOUNT.
	 *
	 * @param: double  COLLECTION_RMB_AMOUNT
	 */
	public void setCollectionRmbAmount(double collectionRmbAmount) {
		this.collectionRmbAmount = collectionRmbAmount;
	}

	/**
	 * GET方法: 取得COLLECTION_DATE.
	 *
	 * @return: String  COLLECTION_DATE
	 */
	@Column(name = "COLLECTION_DATE", nullable = true, length = 27)
	public String getCollectionDate() {
		return this.collectionDate;
	}

	/**
	 * SET方法: 设置COLLECTION_DATE.
	 *
	 * @param: String  COLLECTION_DATE
	 */
	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}

	@Column(name = "ONWAY_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getOnwayAmount() {
		return this.onwayAmount;
	}

	public void setOnwayAmount(double onwayAmount) {
		this.onwayAmount = onwayAmount;
	}

	@Column(name = "REAL_COLLECTION_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getRealCollectionAmount() {
		return this.realCollectionAmount;
	}

	public void setRealCollectionAmount(double realCollectionAmount) {
		this.realCollectionAmount = realCollectionAmount;
	}

	@Column(name = "REMARKS", nullable = true, length = 27)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
