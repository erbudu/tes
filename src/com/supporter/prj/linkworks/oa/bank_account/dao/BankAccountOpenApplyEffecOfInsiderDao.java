package com.supporter.prj.linkworks.oa.bank_account.dao;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApply;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApplyEffec;
import com.supporter.prj.linkworks.oa.bank_account.util.BankAccountConstant;
import com.supporter.prj.linkworks.oa.bank_account.util.BankAccountOpenAuthConstant;

/**   
 * @Title: dao
 * @Description: 
 * @author jiaotilei
 * @date 2018-05-04
 * @version V1.0   
 *
 */
@Repository
public class BankAccountOpenApplyEffecOfInsiderDao extends MainDaoSupport < BankAccountOpenApplyEffec, String > {
	
	public String getApplicationNumber() {
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
		String year=sdf.format(new Date());
		String hql = "select applicationNumber from "
				+ BankAccountOpenApplyEffec.class.getName()+" where applicationNumber like '开立（维）%号(" + year + ")' order by applicationNumber desc";
		List<String> list = this.find(hql);
		if(list!=null){
			if (list.size() > 0) {
				if (list.get(0) != null && !list.get(0).equals("")) {
					return list.get(0).toString();
				} else {
					return "开立（维）000号("+year+")";
				}
			}else{
				return "开立（维）000号("+year+")";
			}
		}else{
			return "开立（维）000号("+year+")";
		}
	}

	
	
	/**
	 * 分页查询
	 */
	public List<BankAccountOpenApplyEffec> findPage(UserProfile user,JqGrid jqGrid, BankAccountOpenApplyEffec bankAccountOpenApplyEffec) {
		if (bankAccountOpenApplyEffec != null) {
			String thebankAccount = bankAccountOpenApplyEffec.getThebankAccount();
			if (StringUtils.isNotBlank(thebankAccount)) {
				jqGrid
						.addHqlFilter(
								" thebankAccount like ? or authorizationPersonNames like ?  or accountOpener like ? or openingBank like ?",
								"%" + thebankAccount + "%", "%" + thebankAccount + "%" , "%" + thebankAccount + "%", "%" + thebankAccount + "%");
			}
			Long status=bankAccountOpenApplyEffec.getStatus();
			if (status!=null&&status!=0) {
				jqGrid
						.addHqlFilter(
								" status = ? ",status);
			}
			
			String accountCurrency=bankAccountOpenApplyEffec.getAccountCurrency();
			if (accountCurrency!=null&&!accountCurrency.equals("")) {
				jqGrid
						.addHqlFilter(
								" accountCurrency = ? ",accountCurrency);
			}
			String dateFrom = bankAccountOpenApplyEffec.getDateFrom();		
			if (StringUtils.isNotBlank(dateFrom)) {
/*				String year=dateFrom.substring(0, 4);
				String month=dateFrom.substring(5, 7);
				String salaryMonth=year+"年"+month+"月";*/
				jqGrid.addHqlFilter(" openTime >= ? ", dateFrom);
			}
			String dateTo = bankAccountOpenApplyEffec.getDateTo();			
			if (StringUtils.isNotBlank(dateTo)) {
/*				String year=dateTo.substring(0, 4);
				String month=dateTo.substring(5, 7);
				String salaryMonth=year+"年"+month+"月";*/
				jqGrid.addHqlFilter(" openTime <=?  ", dateTo);
			}			
			jqGrid.addSortPropertyDesc("createdDate");
		}
		//权限过滤（判断获取数据范围的权限）
		String authHql="";
		authHql = EIPService.getAuthorityService().getHqlFilter(user,BankAccountConstant.BANK_ACCOUNT_OPEN_MODULE_ID,BankAccountOpenAuthConstant.AUTH_OPER_NAME_DATASHOWAUTH );
		jqGrid.addHqlFilter(authHql);	
		return this.retrievePage(jqGrid);
	}



	public List<BankAccountOpenApplyEffec> getBankAccountOpenApplyEffecList() {
		StringBuffer hql = new StringBuffer("from " + BankAccountOpenApplyEffec.class.getName()
				+ " where 1=1 ");
		List<BankAccountOpenApplyEffec> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
	//根据applyId获取记录
	public BankAccountOpenApplyEffec getByApplyId(String applyId) {
		StringBuffer hql = new StringBuffer("from " + BankAccountOpenApplyEffec.class.getName()
				+ " where applyId= ? ");
		List<BankAccountOpenApplyEffec> list = this.find(hql.toString(),applyId);
		if (list == null || list.size() == 0)
			return null;
		return list.get(0);
	}
	
	/**
	 * 按查询条件过滤数据，然后导出数据
	 * @param entity
	 * @return
	 */
	public List<BankAccountOpenApplyEffec> findBySearch(BankAccountOpenApplyEffec entity) {
		String hql = "from " + BankAccountOpenApplyEffec.class.getName()
		+ " where 1 =1 ";
		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
		String name = entity.getAccountOpener();
		if (StringUtils.isNotBlank(name)) {
			//hql+= " and empName like '"+name+"'  or deptName like '"+name+"'";
			hql+= " and accountOpener like ? ";			
			paramsMap.put("accountOpener","%"+name+"%");
		}
		String thebankAccount = entity.getThebankAccount();
		if (StringUtils.isNotBlank(thebankAccount)) {
			hql+= " and thebankAccount = ?";
			paramsMap.put("thebankAccount",thebankAccount);
		}
//		String dateFrom = entity.getDateFrom();	
//		if (StringUtils.isNotBlank(dateFrom)) {
//			String year=dateFrom.substring(0, 4);
//			String month=dateFrom.substring(5, 7);
//			String salaryMonth=year+"年"+month+"月";
//			hql+= " and  salaryMonth >= '"+salaryMonth+"'";
//			paramsMap.put("salaryMonth",salaryMonth);
//		}
//		String dateTo = entity.getDateTo();	
//		if (StringUtils.isNotBlank(dateTo)) {
//			String year=dateTo.substring(0, 4);
//			String month=dateTo.substring(5, 7);
//			String salaryMonth=year+"年"+month+"月";
//			hql+= " and  salaryMonth <= '"+salaryMonth+"'";
//			paramsMap.put("salaryMonth",salaryMonth);
//		}
		hql+= " order by createdDate desc";
		String[] params = new String[paramsMap.size()];

		int i = 0;
		for (Map.Entry<String, String> e : paramsMap.entrySet()) {
			params[i] = e.getValue();
			i++;
		}
		List<BankAccountOpenApplyEffec> retList = this.find(hql, params);
		return retList;
	}

	
	//根据applyId获取记录
	public BankAccountOpenApplyEffec getAccountByDeptBankCurrency(BankAccountOpenApply bankAccountOpenApply) {
		StringBuffer hql = new StringBuffer("from " + BankAccountOpenApplyEffec.class.getName()
				+ " where accountOpenerId = ? and accountCurrency = ? and openingBank = ?");
		List<BankAccountOpenApplyEffec> list = this.find(hql.toString(),bankAccountOpenApply.getAccountOpenerId(),bankAccountOpenApply.getAccountCurrency(),bankAccountOpenApply.getOpeningBank());
		if (list == null || list.size() == 0)
			return null;
		return list.get(0);
	}
	
}
