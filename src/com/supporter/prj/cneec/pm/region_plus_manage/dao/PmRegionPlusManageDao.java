package com.supporter.prj.cneec.pm.region_plus_manage.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.pm.region_plus_manage.entity.PmRegionPlusManage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.role.entity.RoleMember;

@Repository
public class PmRegionPlusManageDao extends MainDaoSupport<PmRegionPlusManage, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @param plus
	 * @return
	 */
	public List<PmRegionPlusManage> findPage(JqGrid jqGrid, PmRegionPlusManage plus){
		if (plus != null){
			//查询框
			String keyword = plus.getKeyword();
			if (StringUtils.isNotBlank(keyword)){
				jqGrid.addHqlFilter(" regionPlusName like ? or manageLeaderName like ? ", "%"+keyword+"%", "%"+keyword+"%");
			}
			//按申请时间倒叙排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 根据人员获取区域+
	 * @param personId
	 * @return
	 */
	public List<PmRegionPlusManage> getplusList(String personId){
		String hql = " from " + PmRegionPlusManage.class.getName() + " where 1=1 ";
		if (!personId.equals("all")){
			hql += " and manageLeaderId = '" + personId + "' ";
		}
		List<PmRegionPlusManage> subList = this.find(hql);
		if (subList.size() <= 0 || subList == null){
			return null;
		}
		return subList;
	}
	
	/**
	 * 判断人员是否存在于某个角色中
	 * @param roleId
	 * @param accountId
	 * @return
	 */
	public boolean getPersonIsInRole(String roleId, String accountId){
		String ls_sql = " SELECT R.MEMBER_ID FROM SUPP_SYS.SPSYS_ROLE_MEMBER R WHERE R.ROLE_ID = '" + roleId + "' AND R.MEMBER_ID = '" + accountId + "' ";
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		try{
			Query query = session.createSQLQuery(ls_sql);
			List<String> list = query.list();
			if (list.size() > 0 && list != null){
				return true;
			}
		}finally{
			session.close();
		}
		return false;
	}

}
