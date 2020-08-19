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
import com.supporter.util.CommonUtil;

/**   
 * @Title: dao
 * @Description: 
 * @author jiaotilei
 * @date 2018-05-04
 * @version V1.0   
 *
 */
@Repository
public class BankAccountOpenApplyEffecDao extends MainDaoSupport < BankAccountOpenApplyEffec, String > {
	
	public String getApplicationNumber() {
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
		String year=sdf.format(new Date());
		String hql = "select applicationNumber from "
				+ BankAccountOpenApplyEffec.class.getName()+" where applicationNumber like '____-___' order by applicationNumber desc";
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
	public List<BankAccountOpenApplyEffec> findPage(UserProfile user,JqGrid jqGrid, BankAccountOpenApplyEffec bankAccountOpenApplyEffec) {
		if (bankAccountOpenApplyEffec != null) {
			String thebankAccount = bankAccountOpenApplyEffec.getThebankAccount();
			if (StringUtils.isNotBlank(thebankAccount)) {
				jqGrid
						.addHqlFilter(
								" thebankAccount like ? or createdBy like ?  or accountOpener like ? or openingBank like ?",
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
			String colorLight=bankAccountOpenApplyEffec.getColorOfLight();
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd");
			String date1 = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			if (StringUtils.isNotBlank(colorLight)) {
				if(colorLight.equals("g")){//查询绿灯记录
	//				select t.*, t.rowid from BANK_ACCOUNT_OPEN_APPLY_EFFEC t where 
	//				t.status in(1,2,4,5)
	//				and 
	//				to_date(t.authdate_to,'yyyy-mm-dd')-30>=sysdate

					jqGrid.addHqlFilter(" " +
							"(status in(?,?,?,?) and authdateTo is null ) " +
							"or " +
							"(status in(?,?,?,?) and authdateTo is not null and to_char(to_date(authdateTo,'yyyy-mm-dd')-30,'yyyy-mm-dd')>=?) ",1l,2l,4l,5l,1l,2l,4l,5l,date);										
				}else if(colorLight.equals("y")){//查看黄灯记录
	//				select t.*, t.rowid from BANK_ACCOUNT_OPEN_APPLY_EFFEC t where 
	//				(t.status=3 and to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')<=to_char(to_date(t.chan_target_date,'yyyy-mm-dd hh24:mi:ss') + 5,'yyyy-mm-dd hh24:mi:ss'))
	//				or
	//				(to_char(sysdate,'yyyy-mm-dd')>=to_char(to_date(t.authdate_to,'yyyy-mm-dd') -30,'yyyy-mm-dd') and to_char(sysdate,'yyyy-mm-dd')<to_char(to_date(t.authdate_to,'yyyy-mm-dd'),'yyyy-mm-dd'))

					jqGrid.addHqlFilter(" " +
							"(" +
								"(status =? and ?<=to_char(to_date(chan_target_date, 'yyyy-mm-dd hh24:mi:ss')+ 5,'yyyy-mm-dd hh24:mi:ss')) " +
								"and " +
								"(authdate_to is not null and (?>=to_char(to_date(authdate_to,'yyyy-mm-dd')-30,'yyyy-mm-dd')) and (?<to_char(to_date(authdate_to,'yyyy-mm-dd'),'yyyy-mm-dd'))) " +
							")or " +
							"(authdate_to is null and status =? and ?<=to_char(to_date(chan_target_date, 'yyyy-mm-dd hh24:mi:ss')+ 5,'yyyy-mm-dd hh24:mi:ss')) " +
							"or (" +
							"status !=? and authdate_to is not null and (?>=to_char(to_date(authdate_to,'yyyy-mm-dd')-30,'yyyy-mm-dd')) and (?<to_char(to_date(authdate_to,'yyyy-mm-dd'),'yyyy-mm-dd'))" +
							")" +
							"",3l,date1,date,date,3l,date1,3l,date,date);
				}else if(colorLight.equals("r")){//查询红灯记录
	//				select t.*, t.rowid from BANK_ACCOUNT_OPEN_APPLY_EFFEC t where 
	//				(t.status=3 and to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')>to_char(to_date(t.chan_target_date,'yyyy-mm-dd hh24:mi:ss') + 5,'yyyy-mm-dd hh24:mi:ss'))
	//				or
	//				to_date(t.authdate_to,'yyyy-mm-dd')<=sysdate	
					jqGrid.addHqlFilter(" " +
							"(status =? and ?>to_char(to_date(chan_target_date, 'yyyy-mm-dd hh24:mi:ss')+ 5,'yyyy-mm-dd hh24:mi:ss')) " +
							"or " +
							"(authdate_to is not null and (to_char(to_date(authdate_to,'yyyy-mm-dd'),'yyyy-mm-dd') <=?)) ",3l,date1,date);								
				}
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
