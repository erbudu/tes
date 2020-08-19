package com.supporter.prj.pm.reserve_fund.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.module.constant.ModuleConstant;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.bank_manage.entity.BankAccount;
import com.supporter.prj.pm.bank_manage.service.BankManageService;
import com.supporter.prj.pm.reserve_fund.entity.StockCash;
import com.supporter.prj.pm.reserve_fund.entity.StockCashRec;
import com.supporter.prj.pm.reserve_fund.service.StockCashService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("pm/reserve_fund/stock")
public class StockCashController extends AbstractController{
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private StockCashService stockCashService;
	@Autowired
	private BankManageService bankAccountService;
	
	/**
	 * 根据主键获取功能模块
	 * @param id
	 */
	@RequestMapping("getStockCashByCurrencyId")
	public @ResponseBody StockCash getStockCashByCurrencyId(String currencyId){	
		StockCash stockCash=stockCashService.getStockCashByCurrencyId(currencyId);
		return stockCash;		
	}
	
	/**
	 * 分页表格展示数据.--主表
	 * 用于系统登录首页展示
	 * @param isTrue 
	 * @return
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, StockCash stockCash) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.stockCashService.getGrid( jqGrid, stockCash,user);
		return jqGrid;
	}	
	
	/**
	 * 分页表格展示数据.--明细表
	 * 用于系统登录首页展示
	 * @param isTrue 
	 * @return
	 */
	@RequestMapping("getRecGrid")
	public @ResponseBody JqGrid getRecGrid(HttpServletRequest request, JqGridReq jqGridReq, StockCashRec cashRec) throws Exception  {
		UserProfile user = this.getUserProfile();
		String stockId = this.getRequestPara("stockId");
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.stockCashService.getRecGrid( jqGrid, cashRec,user,stockId);
		return jqGrid;
	}
	
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<StockCash> saveOrUpdate(StockCash cash) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(StockCash.class);
		StockCash entity = this.stockCashService.saveOrUpdate(user, cash);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS, null, entity);
	}
	
	@RequestMapping("getReserveAmount")
	public  @ResponseBody double getReserveAmount(String currencyId){
		StockCash cash = stockCashService.getStockCashByCurrencyId(currencyId);	
		if(cash != null && cash.getReserveAmount() != 0){
			double reserveAmount = cash.getReserveAmount();
			return reserveAmount;
		}else{
			return 0;
		}	
	}
	
	@RequestMapping("getBankAmount")
	public  @ResponseBody double getBankAmount(String accountNo){
		BankAccount bankAccount = bankAccountService.getBankAccountByNo(accountNo);	
		if(bankAccount != null && bankAccount.getBankAmount() != 0){
			double bankAmount = bankAccount.getBankAmount();
			return bankAmount;
		}else{
			return 0;
		}	
	}
}
