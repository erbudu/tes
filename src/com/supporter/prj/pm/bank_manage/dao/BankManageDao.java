package com.supporter.prj.pm.bank_manage.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.bank_manage.entity.BankAccount;

@Repository
public class BankManageDao extends MainDaoSupport < BankAccount, String > {
	

	/**
	 * 分页查询
	 * @param jqGrid
	 * @param qualityProblemIds 模块ids
	 * @return
	 */
	public List<BankAccount> findPage(JqGrid jqGrid, BankAccount bankAccount,UserProfile user) {
		if(bankAccount != null){
			// 只获取某项目下的数据
			String prjId = bankAccount.getPrjId();
			if (StringUtils.isNotBlank(prjId)) {
				jqGrid.addHqlFilter(" prjId = ? ", prjId);
			} else {// 判断条件值任意，目的是返回一个空表
				jqGrid.addHqlFilter(" 1 != 1");
			}
			//银行账号
			String bankAccountNo = bankAccount.getBankAccountNo();
			if(StringUtils.isNotBlank(bankAccountNo) ){
				jqGrid.addHqlFilter("bankAccountNo like ? or bankAccount like ?","%" + bankAccountNo + "%","%" + bankAccountNo + "%");
			}
			//账号币别
			String currencyId = bankAccount.getCurrencyId();
			if(StringUtils.isNotBlank(currencyId) ){
				jqGrid.addHqlFilter("currencyId = ? ",currencyId);
			}
			//是否作废
			if (bankAccount.getIsCancel() != null) {
				jqGrid.addHqlFilter("isCancel = ?", bankAccount.getIsCancel());
			}
		}
		//String authHql = EIPService.getAuthorityService().getHqlFilter(user,
				//BankAccount.MODULE_ID,AuthConstant.AUTH_OPER_NAME_SETVALPROBLEM);
		//jqGrid.addHqlFilter(authHql);	
		return this.retrievePage(jqGrid);
	}
	
	public BankAccount getBankAccountByNo(String accountNo){
		String hql = "from BankAccount where bankAccountNo ='" + accountNo + "'";
		return this.findUniqueResult(hql);
	}
	
	public boolean getBankAccountByNos(String id,String nos){	
		String hql = null;
		List retList = null;
		if (StringUtils.isBlank(id)) {// 新建时
			hql = "from " + BankAccount.class.getName() + " where bankAccountNo = ?";
			retList = this.retrieve(hql, nos);
		} else {// 编辑时
			hql = "from " + BankAccount.class.getName() + " where id != ? and bankAccountNo = ?";
			retList = this.retrieve(hql, id, nos);
		}
		if (CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}
	
	public List<BankAccount> getBankAccountByDate(Date startDate,Date endDate){
		Map <String, Object> params = new HashMap <String, Object>();
		String hql = "from " + BankAccount.class.getName() + " where isCancel = " + BankAccount.StatusCodeTable.EFFECT;
		if (startDate != null) {
			params.put("startDate", startDate);
			hql += " and openTime >= :startDate";
		}
		if (endDate != null) {
			params.put("endDate", endDate);
			hql += " and openTime <= :endDate";
		}	
		return this.find(hql, params);
	}
	
	/**
	 * 数据库中是否存在记录.
	 * @param id 银行账户ID
	 * @return boolean
	 */
	public boolean existInDB(String id) {
		String hql = "select count(id) as recCount from "
				+ BankAccount.class.getName() + " where id=?";
		Object obj = this.retrieveFirst(hql, id);
		if (obj == null) {
			return false;
		} else {
			return (Long) obj > 0;
		}
	}
}
