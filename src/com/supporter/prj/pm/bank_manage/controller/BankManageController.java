package com.supporter.prj.pm.bank_manage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.module.constant.ModuleConstant;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.PmConstant;
import com.supporter.prj.pm.bank_manage.entity.AccountDetail;
import com.supporter.prj.pm.bank_manage.entity.BankAccount;
import com.supporter.prj.pm.bank_manage.service.BankManageService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("pm/bank_manage/manage")
public class BankManageController extends AbstractController{
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private BankManageService service;
	
	/**
	 * 根据主键获取功能模块
	 * @param id
	 */
	@RequestMapping("get")
	public @ResponseBody BankAccount get(String id){	
		BankAccount bankAccount=service.get(id);
		return bankAccount;		
	}
		

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param id 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody BankAccount initEditOrViewPage(String id) {
		UserProfile user = this.getUserProfile();
		BankAccount entity = service.initEditOrViewPage(id,user);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, BankAccount bankAccount) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		
		String isCancel = this.getRequestPara("isCancel");
		if (StringUtils.isNotBlank(isCancel)) {
			String hqlFilter = "";
			if (hqlFilter.length() > 0) {
				hqlFilter += " or ";
			}
			hqlFilter += "isCancel=" + isCancel;
			if (hqlFilter.length() > 0) {
				jqGrid.addHqlFilter(hqlFilter);
			}
		}
			
		this.service.getGrid( jqGrid, bankAccount,user);
		return jqGrid;
	}	
	
	@RequestMapping("getDetailGrid")
	public @ResponseBody JqGrid getDetailGrid(HttpServletRequest request, JqGridReq jqGridReq, AccountDetail accountDetail,String accountId) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.service.getDetailGrid( jqGrid, accountDetail,user,accountId);
		return jqGrid;
	}
	
	/**
	 * 保存或更新数据.
	 * 
	 * @param BankAccount 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<BankAccount> saveOrUpdate(BankAccount bankAccount) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(BankAccount.class);
		BankAccount entity = this.service.saveOrUpdate(user, bankAccount);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS, null, entity);
	}
	
	
	/**
	 * 删除操作
	 * 
	 * @param problemIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String ids) {
		UserProfile user = this.getUserProfile();
		this.service.delete(user, ids);
		return OperResult.succeed(ModuleConstant.I18nKey.DELETE_SUCCESS,null,null);
	}
	
	// 币别
	@RequestMapping("getCurrencyTable")
	public @ResponseBody Map<String, String> getCurrencyTable() {
		return PmConstant.getCurrencyTable();
	}
	
	@RequestMapping("getBankAmount")
	public  @ResponseBody double getBankAmount(String accountNo){
		BankAccount bankAccount = service.getBankAccountByNo(accountNo);	
		if(bankAccount != null && bankAccount.getBankAmount() != 0){
			double bankAmount = bankAccount.getBankAmount();
			return bankAmount;
		}else{
			return 0;
		}	
	}
	/**
	 * 新建时生成一个id
	 */
	@RequestMapping("getId")
	public @ResponseBody String getId() {
		return com.supporter.util.UUIDHex.newId();
	}
	
	/**
	 * Ajax校验序号是否重复
	 */
	@RequestMapping("checkNosIsValid")
	public @ResponseBody  Boolean checkNosIsValid(String id,String nos){
		return service.getBankAccountByNos(id,nos);		
	}

	
	/**
	 * 作废操作
	 * 
	 * @param applyIds
	 *            主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchCancel")
	public @ResponseBody OperResult batchCancel(String id) {
		UserProfile user = this.getUserProfile();
		BankAccount entity = this.service.cancel(user,id);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS, null, entity);
	}
}
