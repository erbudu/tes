package com.supporter.prj.ppm.poa.bor.entity.base;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
@MappedSuperclass
public   class BaseBidInf implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String bidInfId;
	private String borId;
	private String bidNo;
	private String bidAgainstName;
	private String orderNo;
	private String equivalentDollars;
	private String bidPriceDeference;
	private String discountRate;
	private String bidValidityId;
	private String bidValidityName;
	public BaseBidInf() {
		super();
	}
	

	@Id
	@GeneratedValue(generator = "assigned")
    @GenericGenerator(name = "assigned", strategy = "assigned")
	@Column(name = "BID_INF_ID" , nullable = false, length = 32)
	public String getBidInfId() {
		return bidInfId;
	}
	public void setBidInfId(String bidInfId) {
		this.bidInfId = bidInfId;
	}
	@Column(name = "BOR_ID" , nullable = true, length = 32)
	public String getBorId() {
		return borId;
	}
	public void setBorId(String borId) {
		this.borId = borId;
	}
	@Column(name = "Bid_NO" , nullable = true, length = 32)
	public String getBidNo() {
		return bidNo;
	}
	public void setBidNo(String bidNo) {
		this.bidNo = bidNo;
	}
	@Column(name = "BID_AGAINST_NAME" , nullable = true, length = 32)
	public String getBidAgainstName() {
		return bidAgainstName;
	}

	public void setBidAgainstName(String bidAgainstName) {
		this.bidAgainstName = bidAgainstName;
	}
	@Column(name = "ORDER_NO" , nullable = true, length = 32)
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	@Column(name = "DISCOUNT_RATE" , nullable = true, length = 32)
	public String getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}
	@Column(name = "EQUIVALENT_DOLLARS" , nullable = true, length = 32)
	public String getEquivalentDollars() {
		return equivalentDollars;
	}
	public void setEquivalentDollars(String equivalentDollars) {
		this.equivalentDollars = equivalentDollars;
	}
	@Column(name = "BID_PRICE_DEFERENCE" , nullable = true, length = 32)
	public String getBidPriceDeference() {
		return bidPriceDeference;
	}
	
	
	public void setBidPriceDeference(String bidPriceDeference) {
		this.bidPriceDeference = bidPriceDeference;
	}
	@Column(name = "BID_VALIDITY_ID" , nullable = true, length = 32)
	public String getBidValidityId() {
		return bidValidityId;
	}
	public void setBidValidityId(String bidValidityId) {
		this.bidValidityId = bidValidityId;
	}
	@Column(name = "BID_VALIDITY_NAME" , nullable = true, length = 32)
	public String getBidValidityName() {
		return bidValidityName;
	}
	public void setBidValidityName(String bidValidityName) {
		this.bidValidityName = bidValidityName;
	}


	@Override
	public String toString() {
		return "BaseBidInf [bidInfId=" + bidInfId + ", borId=" + borId + ", bidNo=" + bidNo + ", bidAgainstName="
				+ bidAgainstName + ", orderNo=" + orderNo + ", equivalentDollars=" + equivalentDollars
				+ ", bidPriceDeference=" + bidPriceDeference + ", discountRate=" + discountRate + ", bidValidityId="
				+ bidValidityId + ", bidValidityName=" + bidValidityName + "]";
	}


	public BaseBidInf(String bidInfId, String borId, String bidNo, String bidAgainstName, String orderNo,
			String equivalentDollars, String bidPriceDeference, String discountRate, String bidValidityId,
			String bidValidityName) {
		super();
		this.bidInfId = bidInfId;
		this.borId = borId;
		this.bidNo = bidNo;
		this.bidAgainstName = bidAgainstName;
		this.orderNo = orderNo;
		this.equivalentDollars = equivalentDollars;
		this.bidPriceDeference = bidPriceDeference;
		this.discountRate = discountRate;
		this.bidValidityId = bidValidityId;
		this.bidValidityName = bidValidityName;
	}
	
	
}
