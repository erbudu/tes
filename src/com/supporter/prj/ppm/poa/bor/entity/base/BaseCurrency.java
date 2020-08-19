package com.supporter.prj.ppm.poa.bor.entity.base;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
@MappedSuperclass
public   class BaseCurrency implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String borCurrencyId;
	private String borId;
	private String currencyId;
	private String currencyName;
	private String exchangeRate;
	public BaseCurrency() {
		super();
	}
	

	


	public BaseCurrency(String borCurrencyId, String borId, String currencyId, String currencyName,
			String exchangeRate) {
		super();
		this.borCurrencyId = borCurrencyId;
		this.borId = borId;
		this.currencyId = currencyId;
		this.currencyName = currencyName;
		this.exchangeRate = exchangeRate;
	}


	@Id
	@GeneratedValue(generator = "assigned")
    @GenericGenerator(name = "assigned", strategy = "assigned")
	@Column(name = "BOR_CURRENCY_ID" , nullable = false, length = 32)
	public String getBorCurrencyId() {
		return borCurrencyId;
	}
	public void setBorCurrencyId(String borCurrencyId) {
		this.borCurrencyId = borCurrencyId;
	}
	@Column(name = "BOR_ID" , nullable = true, length = 32)
	public String getBorId() {
		return borId;
	}
	public void setBorId(String borId) {
		this.borId = borId;
	}
	@Column(name = "CURRENCY_ID" , nullable = true, length = 32)
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	@Column(name = "CURRENCY_NAME" , nullable = true, length = 32)
	
	public String getCurrencyName() {
		return currencyName;
	}


	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	@Column(name = "EXCHANGE_RATE" , nullable = true, length = 32)
	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Override
	public String toString() {
		return "BaseCurrency [borCurrencyId=" + borCurrencyId + ", borId=" + borId + ", currencyId=" + currencyId
				+ ", currencyName=" + currencyName + ", exchangeRate=" + exchangeRate + "]";
	}
	
	
}
