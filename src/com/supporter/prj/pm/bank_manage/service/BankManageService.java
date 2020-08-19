package com.supporter.prj.pm.bank_manage.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.bank_manage.dao.AccountDetailDao;
import com.supporter.prj.pm.bank_manage.dao.BankManageDao;
import com.supporter.prj.pm.bank_manage.entity.AccountDetail;
import com.supporter.prj.pm.bank_manage.entity.BankAccount;


@Service
@Transactional(TransManager.APP)
public class BankManageService {
	@Autowired
	private BankManageDao dao;
	@Autowired
	private AccountDetailDao detailDao;

	/**
	 * 根据主键获取功能模块表.
	 * @param id 主键
	 * @return BankAccount
	 */
	public BankAccount get(String id) {
		return dao.get(id);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param id
	 * @return
	 */
	public BankAccount initEditOrViewPage(String id,UserProfile user) {
		BankAccount bankAccounts = new BankAccount();
		
		if (StringUtils.isNotBlank(id)) {	
			bankAccounts =  dao.get(id);
			bankAccounts.setIsNew(false);
			bankAccounts.setModifiedBy(user.getName());
			bankAccounts.setModifiedById(user.getPersonId());
			bankAccounts.setModifiedDate(new Date());
			
			return bankAccounts;
		}else{
			bankAccounts.setIsNew(true);
			bankAccounts.setIsCancel(0);
			bankAccounts.setId(com.supporter.util.UUIDHex.newId());
			bankAccounts.setCreatedBy(user.getName());		
			bankAccounts.setCreatedById(user.getPersonId());	
			bankAccounts.setCreatedDate(new Date());
			
			return bankAccounts;
		}	
	}
	
	

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<BankAccount> getGrid(JqGrid jqGrid, BankAccount bankAccount,UserProfile user) {
		return dao.findPage(jqGrid, bankAccount,user);
	}
	
	public List<AccountDetail> getDetailGrid(JqGrid jqGrid, AccountDetail accountDetail,UserProfile user,String accountId) {
		return detailDao.findPage(jqGrid, accountDetail,user,accountId);
	}

	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param bankAccount 实体类
	 * @return
	 */
	public BankAccount saveOrUpdate(UserProfile user, BankAccount bankAccount) {
		BankAccount ret = null;
		bankAccount.setIsCancel(BankAccount.StatusCodeTable.DRAFT);
		if(bankAccount.getIsNew()){
			bankAccount.setCurrency(EIPService.getComCodeTableService()
					.getCodetableItem(bankAccount.getCurrencyId())
					.getItemValue());// 根据付款条款id获取名称
			this.dao.save(bankAccount);			
			ret = bankAccount;
		} else {// 编辑			
			bankAccount.setCurrency(EIPService.getComCodeTableService()
					.getCodetableItem(bankAccount.getCurrencyId())
					.getItemValue());// 根据付款条款id获取名称
			this.dao.update(bankAccount);
			ret = bankAccount;
		}		
		return ret;
	}
	

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param problemIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String ids) {
		if (StringUtils.isNotBlank(ids)) {
			for (String id : ids.split(",")) {
				BankAccount bankAccount = this.get(id);		
				dao.delete(bankAccount);
			}
		}
	}
	
	public BankAccount getBankAccountByNo(String accountNo){
		return this.dao.getBankAccountByNo(accountNo);
	}
	
	public boolean getBankAccountByNos(String id,String nos) {
		return dao.getBankAccountByNos(id,nos);
	}
	
	public BankAccount cancel(UserProfile user, String id) {
		BankAccount account = dao.get(id);
		account.setIsCancel(BankAccount.StatusCodeTable.EFFECT);
		dao.update(account);
		return account;
	}
	
}
