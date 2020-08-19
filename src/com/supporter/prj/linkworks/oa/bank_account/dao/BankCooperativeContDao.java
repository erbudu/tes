package com.supporter.prj.linkworks.oa.bank_account.dao;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankCooperativeCont;

/**   
 * @Title: dao
 * @Description: 
 * @author jiaotilei
 * @date 2018-05-04
 * @version V1.0   
 *
 */
@Repository
public class BankCooperativeContDao extends MainDaoSupport < BankCooperativeCont, String > {
	
	public String getChangeNumber() {
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
		String year=sdf.format(new Date());
		String hql = "select revokeNumber from "
				+ BankCooperativeCont.class.getName()+" where revokeNumber like '____-___' order by revokeNumber desc";
		List<String> list = this.find(hql);
		if(list!=null){
			if (list.size() > 0) {
				if (list.get(0) != null && !list.get(0).equals("")) {
					return list.get(0).toString();
				} else {
					return year+"-000";
				}
			}else{
				return year+"-000";
			}
		}else{
			return year+"-000";
		}
	}

	/**
	 * 分页查询
	 */
	public List<BankCooperativeCont> findPage(UserProfile user,JqGrid jqGrid, BankCooperativeCont bankCooperativeCont) {
		if (bankCooperativeCont != null) {
			String bankAccountName = bankCooperativeCont.getBankAccountName();
			if (StringUtils.isNotBlank(bankAccountName)) {
				jqGrid
						.addHqlFilter(
								" bankAccountName like ? ","%" + bankAccountName + "%");
			}
			jqGrid.addSortPropertyDesc("createdDate");
		}
		//权限过滤（判断获取数据范围的权限）
//		String authHql="";
//		authHql = EIPService.getAuthorityService().getHqlFilter(user,BankAccountOpenApplyConstant.MODULE_ID,BankaccountOpenAuthConstant.AUTH_OPER_NAME_AUTHMAINTENANCEOFLIST );
//		jqGrid.addHqlFilter(authHql);
		return this.retrievePage(jqGrid);
	}
	
//	/**
//	 * 分页查询
//	 */
//	public BankCooperativeCont getRevokeByEffectiveId(String cooperativeId) {
//		StringBuffer hql = new StringBuffer("from " + BankCooperativeCont.class.getName()
//				+ " where cooperativeId = ? ");
//		List<BankCooperativeCont> list = this.find(hql.toString(),cooperativeId);
//		if (list == null || list.size() == 0)
//			return null;
//		return list.get(0);
//	}



	public List<BankCooperativeCont> getBankCooperativeContList() {
		StringBuffer hql = new StringBuffer("from " + BankCooperativeCont.class.getName()
				+ " where 1=1 ");
		List<BankCooperativeCont> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
/**
 * 根据主键获取该银行的地址
 * @param cooperativeId
 * @return
 */
	public String getAddressOfBank(String cooperativeId) {
		String hql = "select bankAddress,bankAccountName,openBankFirstNameId,openBankFirstName from "
				+ BankCooperativeCont.class.getName()+" where cooperativeId=? ";
		List<Object[]> list = this.find(hql,cooperativeId);
		if(list!=null){
			if (list.size() > 0) {
				if (list.get(0) != null&&list.get(0).length>1) {
					return list.get(0)[0].toString()+","+list.get(0)[1].toString()+","+
						   list.get(0)[2].toString()+","+list.get(0)[3].toString();
				} else {
					return "";
				}
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
	
	
	
}
