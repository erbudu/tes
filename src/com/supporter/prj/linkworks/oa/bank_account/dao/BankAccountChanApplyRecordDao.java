package com.supporter.prj.linkworks.oa.bank_account.dao;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountChanApplyRecord;

/**   
 * @Title: dao
 * @Description: 
 * @author jiaotilei
 * @date 2018-05-04
 * @version V1.0   
 *
 */
@Repository
public class BankAccountChanApplyRecordDao extends MainDaoSupport < BankAccountChanApplyRecord, String > {
	
	public String getRecordNumber() {
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
		String year=sdf.format(new Date());
		String hql = "select recordNumber from "
				+ BankAccountChanApplyRecord.class.getName()+" where recordNumber like '变更（备）%号(" + year + ")' order by recordNumber desc";
		List<String> list = this.find(hql);
		if(list!=null){
			if (list.size() > 0) {
				if (list.get(0) != null && !list.get(0).equals("")) {
					return list.get(0).toString();
				} else {
					return "变更（备）000号("+year+")";
				}
			}else{
				return "变更（备）000号("+year+")";
			}
		}else{
			return "变更（备）000号("+year+")";
		}
	}

	
	
	/**
	 * 分页查询
	 */
	public List<BankAccountChanApplyRecord> findPage(UserProfile user,JqGrid jqGrid, BankAccountChanApplyRecord bankAccountOpenApply) {
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



	public List<BankAccountChanApplyRecord> getBankAccountChanApplyRecordList() {
		StringBuffer hql = new StringBuffer("from " + BankAccountChanApplyRecord.class.getName()
				+ " where 1=1 ");
		List<BankAccountChanApplyRecord> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}

	//根据变更单主键ID获取与之对应的变更备案信息
	public BankAccountChanApplyRecord getByChangeApplyId(String changeApplyId) {
		StringBuffer hql = new StringBuffer("from " + BankAccountChanApplyRecord.class.getName()
				+ " where changeApplyId= ? ");
		List<BankAccountChanApplyRecord> list = this.find(hql.toString(),changeApplyId);
		if (list == null || list.size() == 0)
			return null;
		return list.get(0);
	}
	
	//根据生效表主键Id获取与之对应的变更备案信息
	public BankAccountChanApplyRecord getByEffectiveId(String effectiveId) {
		StringBuffer hql = new StringBuffer("from " + BankAccountChanApplyRecord.class.getName()
				+ " where effectiveId= ? order by chanReEffecDate desc");
		List<BankAccountChanApplyRecord> list = this.find(hql.toString(),effectiveId);
		if (list == null || list.size() == 0)
			return null;
		return list.get(0);
	}
	
	
	
	
	
}
