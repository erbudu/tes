package com.supporter.prj.cneec.tpc.custom.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.custom.entity.Custom;
import com.supporter.prj.cneec.tpc.custom.entity.CustomPayaccount;
import com.supporter.prj.cneec.tpc.custom.entity.CustomPayaccount;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

@Repository
public class CustomPayaccountDao extends MainDaoSupport<CustomPayaccount, String> {
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param payaccountId
	 * @return
	 */
	public List<CustomPayaccount> findPage(JqGrid jqGrid, String customId){
		if (StringUtils.isNotBlank(customId)){
	        //根据客户编号获取该客户名下的付款账号
			jqGrid.addHqlFilter(" customId = ? ", customId);
			return this.retrievePage(jqGrid);
		}
		List<CustomPayaccount> list = new ArrayList<CustomPayaccount>();
		return list;
	}
	
	/**
	 * 删除该客户下所有账号信息
	 */
	public void deleteInner(Custom custom){
		List<CustomPayaccount> accounts = getAccountByCustomId(custom.getCustomId());
		this.delete(accounts);
	}
	
	/**
	 * 根据客户ID获取开户行列表
	 * @param abroadRecordId
	 * @return
	 */
	public List<CustomPayaccount> getAccountByCustomId(String customId) {
		String hql = "from " + CustomPayaccount.class.getName()
				+ " where customId = ? ";
		List<CustomPayaccount> payaccount = this.find(hql, customId);
		if (payaccount == null || payaccount.size() == 0) {
			return null;
		}
		return payaccount;
	}
	
	/**
	 * 获取该客户下的付款账号
	 * @param customId
	 * @return
	 */
	public List<CustomPayaccount> getpayaccountList(String customId, String payaccountId){
		List<CustomPayaccount> payaccount = new ArrayList();
		String hql = "from " + CustomPayaccount.class.getName()
				+ " where customId = ? and ispayaccount = 1 and payaccoutId <> ? ";
		payaccount = this.find(hql, customId,payaccount);
		if (payaccount == null || payaccount.size() == 0) {
			return null;
		}
		return payaccount;
	}

	/**
	 * 查询 不是第三方支付的列表
	 * @param customId
	 * @return
	 */
	public List<CustomPayaccount> findNotOtherPay(String customId,String payaccoutId) {
		String hql = "from "+CustomPayaccount.class.getName()+" where customId= ? and payaccountId !=? and isOtherPay = false";
		String [] param = new String[] {customId,payaccoutId};
		return find(hql,param);
	}

	public List<CustomPayaccount> findOtherPay(String customId) {
		String hql = "from "+CustomPayaccount.class.getName()+" where customId= ? and isOtherPay = true";
		return find(hql,customId);
	}
}
