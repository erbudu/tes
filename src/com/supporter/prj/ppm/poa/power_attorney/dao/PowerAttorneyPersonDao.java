package com.supporter.prj.ppm.poa.power_attorney.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ppm.poa.power_attorney.entity.PowerAttorneyPerson;

/**   
 * @Title: Dao
 * @Description: 
 * @author GuoXiansheng
 * @date 2019-09-25 14:00:07
 * @version V1.0   
 *
 */
@Repository
public class PowerAttorneyPersonDao extends MainDaoSupport < PowerAttorneyPerson, String > {
	
	/**
	 * 根据授权书id分页获取被授权人信息列表
	 * @param jqGrid
	 * @param laId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PowerAttorneyPerson> getPersonGrid(JqGrid jqGrid,String laId){
		jqGrid.addHqlFilter("laId = ?  ",laId);			
		List<PowerAttorneyPerson> list = this.retrievePage(jqGrid);
		jqGrid.setRows(list);
		return list;
	}
	/**
	 * 根据laid获取获取被授权人信息列表
	 * @param laId
	 * @return
	 */
	public List<PowerAttorneyPerson> getPersonByLaId(String laId){
		String hql = "from PowerAttorneyPerson where laId = ? ";
		List<PowerAttorneyPerson> list = this.find(hql,laId);
		return list;
	}
	/**
	 * 授权书按id删除
	 * @param laId
	 * @return
	 */
	public void deletePersonByLaId(String laId){
		String hql = "from PowerAttorneyPerson where laId = ? ";
		List<PowerAttorneyPerson> list = this.find(hql,laId);
		this.delete(list);
	}
}
