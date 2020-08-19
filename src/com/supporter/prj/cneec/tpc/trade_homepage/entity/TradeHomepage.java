package com.supporter.prj.cneec.tpc.trade_homepage.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.trade_homepage.entity.base.BaseTradeHomepage;
import com.supporter.util.CommonUtil;

/**
 * @Title: Entity
 * @Description: TPC_TRADE_HOMEPAGE.
 * @author Yanweichao
 * @date 2018-01-25
 * @version V1.0
 */
@Entity
@Table(name = "TPC_TRADE_HOMEPAGE", schema = "")
public class TradeHomepage extends BaseTradeHomepage implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public TradeHomepage() {
		super();
	}

	/**
	 * 构造函数.
	 * 
	 * @param operId
	 */
	public TradeHomepage(String operId) {
		super(operId);
	}

	@Transient
	public String getDisplayName() {
		return getOperName();
	}

	@Transient
	public String getTradeHomepageURL() {
		String pagePath = this.getPagePath();
		if (!CommonUtil.isNullOrEmpty(pagePath) && !pagePath.startsWith("/")) {
			pagePath = "/" + pagePath;
		}
		return pagePath;
	}

}
