package com.supporter.prj.cneec.pm.region_plus_manage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.pm.region_plus_manage.entity.PmRegionPlusManageSubtable;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import org.hibernate.Query;
import org.hibernate.Session;

@Repository
public class PmRegionPlusManageSubtableDao extends MainDaoSupport<PmRegionPlusManageSubtable, String> {
	
	/**
	 * 获取区域＋下所有明细集合
	 * @param manageId
	 * @return
	 */
	public List<PmRegionPlusManageSubtable> getSubtableListByManageId(String manageId){
		String hql = " from " + PmRegionPlusManageSubtable.class.getName() + " where manageId = ? ";
		List<PmRegionPlusManageSubtable> subList = this.find(hql, manageId);
		if (subList.size() <= 0 || subList == null){
			return null;
		}
		return subList;
	}
	
	/**
	 * 根据所属市场获取大洲或地区
	 * @param areaInername
	 * @return
	 */
	public List<Object[]> getAreaList(String areaInername){
		String ls_sql = " SELECT TRIM(T.AREA_ID), TRIM(T.AREA_NAME)  FROM PL_SUPPLIER_AREA T "
				+ " WHERE T.AREA_INERNAME = '" + areaInername + "' ORDER BY T.DISPLAY_ORDER ";
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		try{
			Query query = session.createSQLQuery(ls_sql);
			List<Object[]> list = query.list();
			if (list.size() > 0 && list != null) {
				return list;
			}
		}finally{
			session.close();
		}
		return null;
	}
	
	/**
	 * 根据大洲或地区ID获取国家或省份
	 * @param areaId
	 * @return
	 */
	public List<Object[]> getAreaItemList(String areaId){
		String ls_sql = " SELECT TRIM(T.AREA_ITEM_ID), TRIM(T.AREA_ITEM_NAME) FROM PL_SUPPLIER_AREA_ITEM T "
				+ " WHERE T.AREA_ID = '" + areaId + "' ORDER BY T.DISPLAY_ORDER_ID ";
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		try{
			Query query = session.createSQLQuery(ls_sql);
			List<Object[]> list = query.list();
			if (list.size() > 0 && list != null) {
				return list;
			}
		}finally{
			session.close();
		}
		return null;
	}
	
	/**
	 * 获取区域+下的所有国家ID
	 * @param manageId
	 * @return
	 */
	public List<String> getAreaIdListByManageId(String manageId){
		String ls_sql = " SELECT T.AREA_ITEM_ID FROM PM_REGION_PLUS_MANAGE_SUBTABLE T WHERE T.MANAGE_ID = '" + manageId + "' ";
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		try{
			Query query = session.createSQLQuery(ls_sql);
			List<String> list = query.list();
			if (list.size() > 0 && list != null){
				return list;
			}
		}finally{
			session.close();
		}
		return null;
	}
}
