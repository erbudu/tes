package com.supporter.prj.pm.reserve_fund.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.bank_manage.dao.AccountDetailDao;
import com.supporter.prj.pm.bank_manage.dao.BankManageDao;
import com.supporter.prj.pm.bank_manage.entity.AccountDetail;
import com.supporter.prj.pm.bank_manage.entity.BankAccount;
import com.supporter.prj.pm.reserve_fund.dao.StockCashDao;
import com.supporter.prj.pm.reserve_fund.dao.StockCashRecDao;
import com.supporter.prj.pm.reserve_fund.entity.StockCash;
import com.supporter.prj.pm.reserve_fund.entity.StockCashRec;


@Service
@Transactional(TransManager.APP)
public class StockCashService {
	@Autowired
	private StockCashDao dao;
	@Autowired
	private StockCashRecDao recDao;
	@Autowired
	private BankManageDao bankDao;
	@Autowired
	private AccountDetailDao detailDao;
	
	/**
	 * 获取JqGrid表格(未登录).--主表
	 * @param jqGrid 表格
	 * @param params 参数
	 * @return 供应商列表
	 */
	public List<StockCash> getGrid(JqGrid jqGrid, StockCash stockCash,UserProfile user) {
		return dao.findPage(jqGrid, stockCash,user);
	}
	
	/**
	 * 获取JqGrid表格(未登录).--从表
	 * @param jqGrid 表格
	 * @param params 参数
	 * @return 供应商列表
	 */
	public List<StockCashRec> getRecGrid(JqGrid jqGrid, StockCashRec stockRec,UserProfile user,String stockId) {
		return recDao.findPage(jqGrid, stockRec,user,stockId);
	}
	
	public StockCash saveOrUpdate(UserProfile user, StockCash stockCash) {
		StockCash ret = null;	
		StockCash entity = dao.getStockCashByCurrencyId(stockCash.getCurrencyId());
		if(entity == null){
			
		}else{
			String id = entity.getStockId();
			StockCash cash = dao.get(id);	
			cash.setStockId(id);
			cash.setReserveAmount(stockCash.getReserveAmount() + stockCash.getWithdrawAmount());
			this.dao.update(cash);	
			
			//更新银行账户信息
			String accountNo = stockCash.getAccountNo();
			BankAccount bankAccount = bankDao.getBankAccountByNo(accountNo);
			if(bankAccount != null){
				double withdraw = stockCash.getWithdrawAmount();
				double bankAmount = bankAccount.getBankAmount();
				bankAccount.setBankAmount(bankAmount - withdraw);
				bankDao.update(bankAccount);
				//set银行账户明细
				AccountDetail detail = new AccountDetail();
				detail.setId(com.supporter.util.UUIDHex.newId());
				detail.setAccountId(bankAccount.getId());
				detail.setCurrencyId(bankAccount.getCurrencyId());
				detail.setCurrency(bankAccount.getCurrency());
				detail.setAmount(withdraw);
				detail.setNature("出账");
				detail.setCreatedDate(new Date());
				detailDao.save(detail);
			}		
			
			//set从表记录
			StockCashRec rec = new StockCashRec();
			rec.setId(com.supporter.util.UUIDHex.newId());
			rec.setStockId(cash.getStockId());
			rec.setCurrencyId(cash.getCurrencyId());
			rec.setCurrency(cash.getCurrency());
			rec.setNature("入库");
			rec.setReserveAmount(stockCash.getWithdrawAmount());
			rec.setReserveDate(new Date());
			recDao.save(rec);
			
			ret = cash;
		}	
		return ret;
	}
	
	public StockCash getStockCashByCurrencyId(String currencyId){
		return this.dao.getStockCashByCurrencyId(currencyId);
	}
}
