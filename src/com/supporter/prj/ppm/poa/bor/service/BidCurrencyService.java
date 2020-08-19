package com.supporter.prj.ppm.poa.bor.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.hibernate.HibernateUtil;
import com.supporter.prj.ppm.poa.bor.dao.BidCurrencyDao;
import com.supporter.prj.ppm.poa.bor.dao.BidOpenResultDao;
import com.supporter.prj.ppm.poa.bor.entity.BidCurrency;
import com.supporter.prj.ppm.poa.bor.entity.BidOpenResult;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

@Service
public class BidCurrencyService {
	@Autowired
	private BidOpenResultDao bidOpenResultDao;
	@Autowired
	private BidCurrencyDao bidCurrencyDao;

	/*public List<BidOpenResult> getGrid(UserProfile user, JqGrid jqGrid, BidOpenResult bidOpenResult) {
		System.out.println("service");
		return this.bidOpenResultDao.findPage(user, jqGrid, bidOpenResult);
	}*/
	public List<BidCurrency> getGridCurrency(UserProfile user, JqGrid jqGrid, String borId) {
		System.out.println("service");
		return this.bidOpenResultDao.findPageCurrency(user, jqGrid, borId);
	}
	/**点击暂存进行保存操作*/
	public BidOpenResult saveOrUpdate(BidOpenResult bidOpenResult, BidCurrency bidCurrency, UserProfile user,HttpServletRequest request , HttpServletResponse response) {
		if (bidOpenResult.getIsNew()) {
			System.out.println(bidOpenResult.getIsNew());
			bidOpenResult.setBorId((UUIDHex.newId()));
			bidOpenResult.setStatus(Integer.valueOf(0));
			bidOpenResult.setCreatedBy(user.getName());
			if (user.getDept() != null) {
				bidOpenResult.setCreatedByDept(user.getDept().getDeptId());
			} else {
				bidOpenResult.setCreatedByDept("");
			}

			bidOpenResult.setCreatedById(user.getPersonId());
			bidOpenResult.setCreatedDate(new Date());
			bidOpenResult.setModifiedDate(new Date());
			bidOpenResult.setModifiedName(user.getAccount().getName());
			bidOpenResult.setModifiedId(user.getAccount().getAccountId());
			this.bidOpenResultDao.save(bidOpenResult);
		} else {
			bidOpenResult.setStatus(Integer.valueOf(0));
			bidOpenResult.setModifiedDate(new Date());
			bidOpenResult.setModifiedName(user.getName());
			bidOpenResult.setModifiedId(user.getPersonId());
			this.bidOpenResultDao.update(bidOpenResult);
		}
		if(bidCurrency!=null) {
		saveOrUpdateCurrency(request,response,user,bidOpenResult,bidOpenResult.getBorId());}
		return bidOpenResult;
	}
	/**点击下一步进行保存操作同时将状态变为1*/
	public BidOpenResult saveOrUpdates(BidOpenResult bidOpenResult, BidCurrency bidCurrency, UserProfile user,HttpServletRequest request , HttpServletResponse response) {
		if (bidOpenResult.getIsNew()) {
			System.out.println(bidOpenResult.getIsNew());
			bidOpenResult.setBorId((UUIDHex.newId()));
			bidOpenResult.setStatus(Integer.valueOf(1));
			bidOpenResult.setCreatedBy(user.getName());
			if (user.getDept() != null) {
				bidOpenResult.setCreatedByDept(user.getDept().getDeptId());
			} else {
				bidOpenResult.setCreatedByDept("");
			}

			bidOpenResult.setCreatedById(user.getPersonId());
			bidOpenResult.setCreatedDate(new Date());
			bidOpenResult.setModifiedDate(new Date());
			bidOpenResult.setModifiedName(user.getAccount().getName());
			bidOpenResult.setModifiedId(user.getAccount().getAccountId());
			this.bidOpenResultDao.save(bidOpenResult);
		} else {
			bidOpenResult.setStatus(Integer.valueOf(1));
			bidOpenResult.setModifiedDate(new Date());
			bidOpenResult.setModifiedName(user.getName());
			bidOpenResult.setModifiedId(user.getPersonId());
			this.bidOpenResultDao.update(bidOpenResult);
		}
		if(bidCurrency!=null) {
		saveOrUpdateCurrency(request,response,user,bidOpenResult,bidOpenResult.getBorId());}
		return bidOpenResult;
	}

	public void saveOrUpdateCurrency(HttpServletRequest request, HttpServletResponse response, UserProfile user,
			BidOpenResult bidOpenResult, String borId) {
		List<BidCurrency> bidCurrencys = bidOpenResult.getBidCurrencyList();

		if (bidCurrencys != null && bidCurrencys.size() > 0) {
			for (BidCurrency bidCurrency1 : bidCurrencys) {
				if (bidCurrency1.getBorCurrencyId() != null && bidCurrency1.getBorCurrencyId() != "") {
					BidCurrency isIn = this.bidCurrencyDao.get(bidCurrency1.getBorCurrencyId());
					System.out.println(isIn);
					if (isIn != null) {
						isIn.setBorCurrencyId(bidCurrency1.getBorCurrencyId());
						
						String CurrencyName = EIPService.getComCodeTableService()
								.getCodetableItem(bidCurrency1.getCurrencyId()).getItemValue();
						isIn.setCurrencyName(CurrencyName);
						isIn.setExchangeRate(bidCurrency1.getExchangeRate());
						this.bidCurrencyDao.update(isIn);
					} else {
						bidCurrency1.setBorId(bidOpenResult.getBorId());
						bidCurrency1.setBorCurrencyId(bidCurrency1.getBorCurrencyId());
						if (bidCurrency1.getBorCurrencyId() != null) {
							String CurrencyName = EIPService.getComCodeTableService()
									.getCodetableItem(bidCurrency1.getCurrencyId()).getItemValue();
							bidCurrency1.setCurrencyName(CurrencyName);
							this.bidCurrencyDao.save(bidCurrency1);
						}
					}

				}
			}
		}
	
}
	

	public void delete(UserProfile user, String bidCurrencyIds) {
		System.out.println(bidCurrencyIds);
		if (StringUtils.isNotBlank(bidCurrencyIds)) {
			for (String bidOpenResultId : bidCurrencyIds.split(",")) {
				this.bidCurrencyDao.delete(bidOpenResultId);
				// EIPService.getLogService("CPP_SUPPLIER").info(user, "ɾ����Ӧ��", "ɾ����Ӧ�̳ɹ�" ,
				// null, null);
			}
		}
	}
}
