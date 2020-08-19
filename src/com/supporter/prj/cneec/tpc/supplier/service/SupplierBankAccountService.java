package com.supporter.prj.cneec.tpc.supplier.service;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.supplier.dao.SupplierBankAccountDao;
import com.supporter.prj.cneec.tpc.supplier.dao.SupplierDao;
import com.supporter.prj.cneec.tpc.supplier.entity.Supplier;
import com.supporter.prj.cneec.tpc.supplier.entity.SupplierBankAccount;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

@Service
@Transactional(TransManager.APP)
public class SupplierBankAccountService {
	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private SupplierBankAccountDao supplierBankAccountDao;
	
	/**
	 * 初始化收款单位
	 * 
	 * @param user 用户信息
	 */
	public SupplierBankAccount newSupplierBankAccount(UserProfile user){
		SupplierBankAccount supplierBankAccount = new SupplierBankAccount();
		loadingSupplierBankAccount(supplierBankAccount, user);
		return supplierBankAccount;
	}
	
	/**
	 * 装填基础信息
	 * 
	 * @param user 用户信息
	 */
	public SupplierBankAccount loadingSupplierBankAccount(SupplierBankAccount lbankAccount_N, UserProfile user){
		lbankAccount_N.setCreatedBy(user.getName());
		lbankAccount_N.setCreatedById(user.getPersonId());
		lbankAccount_N.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		lbankAccount_N.setModifiedBy(user.getName());
		lbankAccount_N.setModifiedById(user.getPersonId());
		lbankAccount_N.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		return lbankAccount_N;
	}
	
	/**
	 * 进入新建、编辑或查看页面需要加载的信息
	 */
	public SupplierBankAccount initEditOrViewPage(String id,String supplierId, UserProfile user){
		SupplierBankAccount bankAccount;
		if (StringUtils.isBlank(id)){//新建
			bankAccount = newSupplierBankAccount(user);
			bankAccount.setId(UUIDHex.newId());
			bankAccount.setSupplierId(supplierId);
			bankAccount.setGatheringUnit(getSupplier(supplierId).getSupplierName());
			bankAccount.setIsNew(true);
		}else {//编辑
			bankAccount = this.supplierBankAccountDao.get(id);
			bankAccount.setIsNew(false);
		}
		return bankAccount;
	}
	
	/**
	 * 保存或更新
	 */
	public SupplierBankAccount saveOrUpdate(UserProfile user, SupplierBankAccount bankAccount, Map<String, Object> valueMap){
		if(bankAccount.getIsNew()){
			bankAccount = loadingSupplierBankAccount(bankAccount, user);
			this.supplierBankAccountDao.save(bankAccount);
		}else{
			bankAccount.setModifiedBy(user.getName());
			bankAccount.setModifiedById(user.getPersonId());
			bankAccount.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.supplierBankAccountDao.update(bankAccount);
		}
		return bankAccount;
	}
	
	/**
	 * 删除
	 */
	public void delete(UserProfile user, String id) {
		if (StringUtils.isNotBlank(id)) {
			this.supplierBankAccountDao.delete(id);
		}
	}
	
	/**
	 * 获取单个收款单位对象
	 */
	public SupplierBankAccount get(String id) {
		return this.supplierBankAccountDao.get(id);
	}
	
	public Supplier getSupplier(String supplierId){
		return this.supplierDao.get(supplierId);
	}

}
