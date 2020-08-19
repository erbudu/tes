package com.supporter.prj.linkworks.oa.bank_account.dao;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApplyRecord;

/**   
 * @Title: dao
 * @Description: 
 * @author jiaotilei
 * @date 2018-05-04
 * @version V1.0   
 *
 */
@Repository
public class BankAccountOpenApplyRecordDao extends MainDaoSupport < BankAccountOpenApplyRecord, String > {
	
	public String getRecordNumber() {
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
		String year=sdf.format(new Date());
		String hql = "select recordNumber from "
				+ BankAccountOpenApplyRecord.class.getName()+" where recordNumber like '开立（备）%号(" + year + ")' order by recordNumber desc";
		List<String> list = this.find(hql);
		if(list!=null){
			if (list.size() > 0) {
				if (list.get(0) != null && !list.get(0).equals("")) {
					return list.get(0).toString();
				} else {
					return "开立（备）000号("+year+")";
				}
			}else{
				return "开立（备）000号("+year+")";
			}
		}else{
			return "开立（备）000号("+year+")";
		}
	}

	
	
	/**
	 * 分页查询
	 */
	public List<BankAccountOpenApplyRecord> findPage(UserProfile user,JqGrid jqGrid, BankAccountOpenApplyRecord bankAccountOpenApply) {
//		if (bankAccountOpenApply != null) {
//			String openingBank = bankAccountOpenApply.getOpeningBank();
//			if (StringUtils.isNotBlank(openingBank)) {
//				jqGrid
//						.addHqlFilter(
//								" openingBank like ? or nationality like ? ",
//								"%" + openingBank + "%", "%" + openingBank + "%");
//			}
//			jqGrid.addSortPropertyDesc("createdDate");
//		}
		return this.retrievePage(jqGrid);
	}



	public List<BankAccountOpenApplyRecord> getBankAccountOpenApplyRecordList() {
		StringBuffer hql = new StringBuffer("from " + BankAccountOpenApplyRecord.class.getName()
				+ " where 1=1 ");
		List<BankAccountOpenApplyRecord> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}

	
	public BankAccountOpenApplyRecord getByApplyId(String applyId) {
		StringBuffer hql = new StringBuffer("from " + BankAccountOpenApplyRecord.class.getName()
				+ " where applyId= ? ");
		List<BankAccountOpenApplyRecord> list = this.find(hql.toString(),applyId);
		if (list == null || list.size() == 0)
			return null;
		return list.get(0);
	}
	
	
	
	
}
