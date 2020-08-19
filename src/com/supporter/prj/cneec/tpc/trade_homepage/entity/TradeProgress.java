package com.supporter.prj.cneec.tpc.trade_homepage.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.tpc.trade_homepage.entity.base.BaseTradeProgress;

/**
 * @Title: Entity
 * @Description: TPC_TRADE_HOMEPAGE_PROGRESS.
 * @author Yanweichao
 * @date 2018-02-02
 * @version V1.0
 */
@Entity
@Table(name = "TPC_TRADE_PROGRESS", schema = "")
public class TradeProgress extends BaseTradeProgress implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public TradeProgress() {
		super();
	}

	/**
	 * 构造函数.
	 * @param progressId
	 */
	public TradeProgress(String progressId) {
		super(progressId);
	}

}
