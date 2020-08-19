package com.supporter.prj.linkworks.oa.bank_account.dao;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountChangeApply;
import com.supporter.prj.linkworks.oa.bank_account.util.BankAccountChangeAuthConstant;
import com.supporter.prj.linkworks.oa.bank_account.util.BankAccountConstant;

/**   
 * @Title: dao
 * @Description: 
 * @author jiaotilei
 * @date 2018-05-04
 * @version V1.0   
 *
 */
@Repository
public class BankAccountChangeApplyDao extends MainDaoSupport < BankAccountChangeApply, String > {
	
	public String getChangeNumber() {
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
		String year=sdf.format(new Date());
		String hql = "select changeNumber from "
				+ BankAccountChangeApply.class.getName()+" where changeNumber like '变更（申）%号(" + year + ")' order by changeNumber desc";
		List<String> list = this.find(hql);
		if(list!=null){
			if (list.size() > 0) {
				if (list.get(0) != null && !list.get(0).equals("")) {
					return list.get(0).toString();
				} else {
					return "变更（申）000号("+year+")";
				}
			}else{
				return "变更（申）000号("+year+")";
			}
		}else{
			return "变更（申）000号("+year+")";
		}
	}

	
	
	/**
	 * 分页查询
	 */
	public List<BankAccountChangeApply> findPage(UserProfile user,JqGrid jqGrid, BankAccountChangeApply bankAccountChangeApply) {
		if (bankAccountChangeApply != null) {
			String effectiveId = bankAccountChangeApply.getEffectiveId();
			if (StringUtils.isNotBlank(effectiveId)) {
				jqGrid.addHqlFilter(" effectiveId = ? ", effectiveId);
			}
			jqGrid.addSortPropertyDesc("createdDate");
		}
		//权限过滤（判断获取数据范围的权限）
		String authHql="";
		authHql = EIPService.getAuthorityService().getHqlFilter(user,BankAccountConstant.BANK_ACCOUNT_CHAN_MODULE_ID,BankAccountChangeAuthConstant.AUTH_OPER_NAME_DATASHOWAUTH );
		jqGrid.addHqlFilter(authHql);	
		return this.retrievePage(jqGrid);
	}



	public List<BankAccountChangeApply> getBankAccountChangeApplyListByEffectiveId(String effectiveId) {
		StringBuffer hql = new StringBuffer("from " + BankAccountChangeApply.class.getName()
				+ " where effectiveId=? ");
		List<BankAccountChangeApply> list = this.find(hql.toString(),effectiveId);
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
	//根据生效表的id获取对应的状态为草稿的变更记录
	public List<BankAccountChangeApply> getChangeApplyListByEffectiveId(String effectiveId) {
		StringBuffer hql = new StringBuffer("from " + BankAccountChangeApply.class.getName()
				+ " where effectiveId=? and status=0");
		List<BankAccountChangeApply> list = this.find(hql.toString(),effectiveId);
		if (list == null || list.size() == 0)
			return null;
		return list;
	}

	
	
	
	
}
