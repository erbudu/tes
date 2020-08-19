package com.supporter.prj.cneec.tpc.supplier.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.supplier.entity.Supplier;
import com.supporter.prj.cneec.tpc.supplier.entity.SupplierBankAccount;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

@Repository
public class SupplierBankAccountDao extends MainDaoSupport<SupplierBankAccount, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @param supplierId
	 * @return
	 */
	public List<SupplierBankAccount> findPage(JqGrid jqGrid, String supplierId) {
		if (StringUtils.isNotBlank(supplierId)) {
			// 根据供应商ID获取该供应商名下的收款单位
			jqGrid.addHqlFilter(" supplierId = ? ", supplierId);
			// 根据ID排序
			jqGrid.addSortPropertyDesc("id");
			return this.retrievePage(jqGrid);
		}
		List<SupplierBankAccount> list = new ArrayList<SupplierBankAccount>();
		return list;
	}

	/**
	 * 删除该供应商下所有收款单位
	 */
	public void deleteInner(Supplier supplier) {
		List<SupplierBankAccount> list = getBankAccountBySupplierId(supplier.getSupplierId());
		if (CollectionUtils.isNotEmpty(list)) {
			this.delete(list);
		}
	}

	/**
	 * 根据供应商ID获取收款单位列表
	 * @param supplierId
	 * @return
	 */
	public List<SupplierBankAccount> getBankAccountBySupplierId(String supplierId) {
		String hql = "from " + SupplierBankAccount.class.getName() + " where supplierId = ? ";
		List<SupplierBankAccount> list = this.find(hql, supplierId);
		if (list == null || list.size() == 0) return null;
		return list;
	}

	/**
	 * 根据合同的供方Id及收款单位获得开户银行,或账号
	 * @param supplierId
	 * @param gatheringUnit
	 * @return
	 */
	public List<SupplierBankAccount> getBankBySupplierAndGatheringUnit(String supplierId,String gatheringUnit,String bank){
		String hql = " from " + SupplierBankAccount.class.getName() + " where 1=1 ";
		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
		if (StringUtils.isNotBlank(supplierId)) {
			hql = hql + " and supplierId = ? ";
			paramsMap.put("supplierId", supplierId);
		} else {
			hql = hql + " and 1 <> 1 ";
		}
		if (StringUtils.isNotBlank(gatheringUnit)) {
			hql = hql + " and gatheringUnit = ? ";
			paramsMap.put("gatheringUnit", gatheringUnit);
		}
		if (StringUtils.isNotBlank(bank)) {
			hql = hql + " and bank = ? ";
			paramsMap.put("bank", bank);
		}
		// String[] params = new String[] {};
		String[] params = new String[paramsMap.size()];

		int i = 0;
		for (Map.Entry<String, String> e : paramsMap.entrySet()) {
			params[i] = e.getValue();
			i++;
		}
		List<SupplierBankAccount> retList = this.find(hql, params);
		return retList;
	}

}
