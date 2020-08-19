package com.supporter.prj.linkworks.oa.bank_account.dao;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountRevokeApply;

/**   
 * @Title: dao
 * @Description: 
 * @author jiaotilei
 * @date 2018-05-04
 * @version V1.0   
 *
 */
@Repository
public class BankAccountRevokeApplyDao extends MainDaoSupport < BankAccountRevokeApply, String > {
	
	public String getChangeNumber() {
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
		String year=sdf.format(new Date());
		String hql = "select revokeNumber from "
				+ BankAccountRevokeApply.class.getName()+" where revokeNumber like '撤销（申）%号(" + year + ")' order by revokeNumber desc";
		List<String> list = this.find(hql);
		if(list!=null){
			if (list.size() > 0) {
				if (list.get(0) != null && !list.get(0).equals("")) {
					return list.get(0).toString();
				} else {
					return "撤销（申）000号("+year+")";
				}
			}else{
				return "撤销（申）000号("+year+")";
			}
		}else{
			return "撤销（申）000号("+year+")";
		}
	}

	
	
	/**
	 * 分页查询
	 */
	public BankAccountRevokeApply getRevokeByEffectiveId(String effectiveId) {
		StringBuffer hql = new StringBuffer("from " + BankAccountRevokeApply.class.getName()
				+ " where effectiveId = ? ");
		List<BankAccountRevokeApply> list = this.find(hql.toString(),effectiveId);
		if (list == null || list.size() == 0)
			return null;
		return list.get(0);
	}



	public List<BankAccountRevokeApply> getBankAccountRevokeApplyListByEffectiveId(String effectiveId) {
		StringBuffer hql = new StringBuffer("from " + BankAccountRevokeApply.class.getName()
				+ " where effectiveId=? ");
		List<BankAccountRevokeApply> list = this.find(hql.toString(),effectiveId);
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
	//根据生效表的id获取对应的状态为草稿的撤销记录
	public List<BankAccountRevokeApply> getRevokeApplyListByEffectiveId(String effectiveId) {
		StringBuffer hql = new StringBuffer("from " + BankAccountRevokeApply.class.getName()
				+ " where effectiveId=? and status=5");
		List<BankAccountRevokeApply> list = this.find(hql.toString(),effectiveId);
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
	
	
	
	
}
