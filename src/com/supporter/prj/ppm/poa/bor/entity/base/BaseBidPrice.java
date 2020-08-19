package com.supporter.prj.ppm.poa.bor.entity.base;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
@MappedSuperclass
public   class BaseBidPrice implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String recordId;
	private String borId;
	private String bidInfId;
	private String bidCurrencyId;
	private String QuotedAmount;
	public BaseBidPrice() {
		super();
	}
	public BaseBidPrice(String recordId, String borId, String bidInfId, String bidCurrencyId, String quotedAmount) {
		super();
		this.recordId = recordId;
		this.borId = borId;
		this.bidInfId = bidInfId;
		this.bidCurrencyId = bidCurrencyId;
		QuotedAmount = quotedAmount;
	}
	@Id
	@GeneratedValue(generator = "assigned")
    @GenericGenerator(name = "assigned", strategy = "assigned")
	@Column(name = "RECORD_ID" , nullable = false, length = 32)
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	@Column(name = "BOR_ID" , nullable = true, length = 32)
	public String getBorId() {
		return borId;
	}
	public void setBorId(String borId) {
		this.borId = borId;
	}
	
	@Column(name = "BID_INF_ID" , nullable = true, length = 32)
	public String getBidInfId() {
		return bidInfId;
	}
	public void setBidInfId(String bidInfId) {
		this.bidInfId = bidInfId;
	}
	@Column(name = "BID_CURRENCY_ID" , nullable = true, length = 32)
	public String getBidCurrencyId() {
		return bidCurrencyId;
	}
	public void setBidCurrencyId(String bidCurrencyId) {
		this.bidCurrencyId = bidCurrencyId;
	}
	@Column(name = "QUOTED_AMOUNT " , nullable = true, length = 32)
	public String getQuotedAmount() {
		return QuotedAmount;
	}
	public void setQuotedAmount(String quotedAmount) {
		QuotedAmount = quotedAmount;
	}
	
}
