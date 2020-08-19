package com.supporter.prj.linkworks.oa.use_seal_apply.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApply;
import com.supporter.prj.linkworks.oa.use_seal_apply.entity.UseSealApply;

/**   
 * @Title: Entity
 * @Description: 功能模块
 * @author jiaotilei
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class UseSealApplyDao extends MainDaoSupport < UseSealApply, String > {	
	public String getSignNo() {
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
		String year=sdf.format(new Date());
		String hql = "select max(signNo) from "
				+ UseSealApply.class.getName()+" where signNo like '印%号（"+year+"）%'";
		List<String> list = this.find(hql);
		if(list!=null){
			if (list.size() > 0) {
				if (list.get(0) != null && !list.get(0).equals("")) {
					return list.get(0).toString();
				} else {					
					return "印000000号（"+year+"）";
				}
			}else{
				return "印000000号（"+year+"）";
			}
		}else{
			return "印000000号（"+year+"）";
		}
	}

	
//	
//	/**
//	 * 分页查询
//	 */
//	public List<UseSealApply> findPage(UserProfile user,JqGrid jqGrid, UseSealApply useSealApply) {
//		if (useSealApply != null) {
//			String applyDept = useSealApply.getApplyDept();
//			if (StringUtils.isNotBlank(applyDept)) {
//				jqGrid
//						.addHqlFilter(
//								" applyDept like ? or signNo like ? or sealType like ? or createdDate like ? ",
//								"%" + applyDept + "%", "%" + applyDept + "%", "%"
//										+ applyDept + "%", "%"+ applyDept + "%");
//			}
//			// 状态过滤
//			if (useSealApply.getApplyStatus() != null) {
//				jqGrid.addHqlFilter(" applyStatus= ? ",useSealApply.getApplyStatus());
//			}
//			jqGrid.addSortPropertyDesc("createdDate");
//		}
//		
//		//权限过滤（判断获取数据范围的权限）
//		String authHql="";
//		authHql = EIPService.getAuthorityService().getHqlFilter(user,SalaryConstant.MODULE_ID,AuthConstant.AUTH_OPER_NAME_AUTHUSESEALOFLIST );
//		System.out.println("authHql==="+authHql);
//		
//		return this.retrievePage(jqGrid);
//	}



	public List<UseSealApply> getUseSealApplyList() {
		StringBuffer hql = new StringBuffer("from "+UseSealApply.class.getName()+" where 1=1 ");
		List<UseSealApply> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}

	
	
	
	
}
