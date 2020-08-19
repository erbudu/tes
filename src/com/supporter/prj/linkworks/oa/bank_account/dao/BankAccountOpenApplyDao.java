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
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApply;
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
public class BankAccountOpenApplyDao extends MainDaoSupport < BankAccountOpenApply, String > {
	
	public String getApplicationNumber() {
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
		String year=sdf.format(new Date());
		String hql = "select applicationNumber from "
				+ BankAccountOpenApply.class.getName()+" where applicationNumber like '开立（申）%号(" + year + ")' order by applicationNumber desc";
		List<String> list = this.find(hql);
		if(list!=null){
			if (list.size() > 0) {
				if (list.get(0) != null && !list.get(0).equals("")) {
					return list.get(0).toString();
				} else {
					return "开立（申）000号("+year+")";
				}
			}else{
				return "开立（申）000号("+year+")";
			}
		}else{
			return "开立（申）000号("+year+")";
		}
	}

	
	
	/**
	 * 分页查询
	 */
	public List<BankAccountOpenApply> findPage(UserProfile user,JqGrid jqGrid, BankAccountOpenApply bankAccountOpenApply) {
		if (bankAccountOpenApply != null) {
			String openingBank = bankAccountOpenApply.getOpeningBank();
			if (StringUtils.isNotBlank(openingBank)) {
				jqGrid
						.addHqlFilter(
								" openingBank like ? or nationality like ? ",
								"%" + openingBank + "%", "%" + openingBank + "%");
			}
			Long status=bankAccountOpenApply.getStatus();
			if (status!=null) {
				jqGrid.addHqlFilter(
								" status = ? ",status);
			}
			jqGrid.addSortPropertyDesc("createdDate");
		}
		//权限过滤（判断获取数据范围的权限）
		String authHql="";
		authHql = EIPService.getAuthorityService().getHqlFilter(user,BankAccountConstant.BANK_ACCOUNT_OPEN_MODULE_ID,BankAccountOpenAuthConstant.AUTH_OPER_NAME_DATASHOWAUTH );
		jqGrid.addHqlFilter(authHql);		
		return this.retrievePage(jqGrid);
	}



	public List<BankAccountOpenApply> getBankAccountOpenApplyList() {
		StringBuffer hql = new StringBuffer("from " + BankAccountOpenApply.class.getName()
				+ " where 1=1 ");
		List<BankAccountOpenApply> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
	/**
	 * 获取老系统中的已办数据列表.
	 * @param tableName
	 * @param params
	 * @return
	 */
/*	public Map<String, String> getAllCountryList(){
		HibernateTemplate template = getHibernateTemplate();
		List results = (List) template.executeWithNativeSession(new HibernateCallback() {
			public List doInHibernate(Session session) {
				String sql=" SELECT item_id,item_value FROM CNEEC_PC_COM_CODETABLE_ITEM where codetable_id ='ALL_COUNTRIES'order by display_order";
				SQLQuery query = session.createSQLQuery(sql);

//				int colSize = COL_NAMES.length;
//				StringType stringType = new StringType();
//				//TimestampType timestampType = new TimestampType();
//				for (int i = 0; i < colSize; i++){
//					if (i == 1){
//						query.addScalar(COL_NAMES[i], stringType);
//					} else {
//						query.addScalar(COL_NAMES[i]);
//					}
//				}
				
				return query.list();
			}
		});
		
		if (results == null || results.size() == 0)return null;
		
		Map<String, String> allCountry = new LinkedHashMap<String, String>();
		String json="";
		int size = results.size();
		for (int i = 0; i < size; i++){
			Object[] objArray = (Object[])(results.get(i));
			allCountry.put(objArray[0].toString(), objArray[1].toString());
		}
		
		return allCountry;
		
	}*/
	
	
	
	
}
