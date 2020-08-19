package com.supporter.prj.cneec.tpc.trade_homepage.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.trade_homepage.dao.TradeProgressDao;
import com.supporter.prj.cneec.tpc.trade_homepage.entity.TradeProgress;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;

@Service
@Transactional(TransManager.APP)
public class TradeProgressService {

	@Autowired
	private TradeProgressDao tradeProgressDao;

	/**
	 * 根据操作用户获取进度
	 * @param user
	 * @return
	 */
	public TradeProgress getTradeProgress(UserProfile user) {
		TradeProgress tradeProgress = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("createdBy", user.getName());
		params.put("createdById", user.getPersonId());
		List<TradeProgress> list = this.tradeProgressDao.find(params);
		if (list != null && list.size() > 0) {
			tradeProgress = list.get(0);
		}
		return tradeProgress;
	}

	/**
	 * 保存更新进度
	 * @param user
	 * @param tradeProgress
	 * @return
	 */
	public TradeProgress saveOrUpdate(UserProfile user, TradeProgress tradeProgress) {
		// 新建时设置用户信息
		if (CommonUtil.isEmpty(tradeProgress.getProgressId())) {
			tradeProgress.setCreatedBy(user.getName());
			tradeProgress.setCreatedById(user.getPersonId());
			tradeProgress.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		}
		tradeProgress.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		this.tradeProgressDao.saveOrUpdate(tradeProgress);
		return tradeProgress;
	}

	/**
	 * 删除进度
	 * @param user
	 */
	public boolean delete(UserProfile user) {
		// 根据用户信息删除该进度
		if (user != null) {
			this.tradeProgressDao.deleteByProperty("createdById", user.getPersonId());
			return true;
		}
		return false;
	}

}
