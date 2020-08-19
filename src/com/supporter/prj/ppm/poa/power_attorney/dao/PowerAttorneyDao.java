package com.supporter.prj.ppm.poa.power_attorney.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ppm.poa.power_attorney.entity.PowerAttorney;

/**   
 * @Title: Dao
 * @Description: 
 * @author GuoXiansheng
 * @date 2019-09-25 15:55:07
 * @version V1.0   
 *
 */
@Repository
public class PowerAttorneyDao extends MainDaoSupport < PowerAttorney, String > {
	
	/**
	 * 查询所有授权书
	 * @return
	 */
	public List<PowerAttorney> selectPowerAttorneyAll(){
		String hql = "from PowerAttorney";
		List<PowerAttorney> list = this.find(hql);
		return list;
	}
	/**
	 * 按照授权书主键查询单个授权书信息
	 * @param laId
	 * @return
	 */
	public PowerAttorney selectPowerAttorneyByLaId(String laId){
		PowerAttorney powerAttorney = this.get(laId);
		return powerAttorney;
	}
	/**
	 * 按项目编号查所有授权书信息并按LA_NO降序排列
	 * @param jqGrid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PowerAttorney> getPowerAttorneyGridByPrjId(JqGrid jqGrid, String prjId){
		jqGrid.addHqlFilter("prjId = ?",prjId);
		jqGrid.addSortPropertyDesc("LA_NO");
		List<PowerAttorney> list = this.retrievePage(jqGrid);
		jqGrid.setRows(list);
		return list;
	}
	/**
	 * 查所有授权书信息并按LA_NO降序排列
	 * @param jqGrid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PowerAttorney> getPowerAttorneyGrid(JqGrid jqGrid){
		jqGrid.addSortPropertyDesc("LA_NO");
		List<PowerAttorney> list = this.retrievePage(jqGrid);
		jqGrid.setRows(list);
		return list;
	}
	/**
	 * 按项目和业务查所有授权书信息并按LA_NO降序排列
	 * @param jqGrid
	 * @param laBusinessTypeId
	 * @param prjId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PowerAttorney> getLaGridByProIdAndLaBusinessTypeId(JqGrid jqGrid,String laBusinessTypeId, String prjId){
		jqGrid.addHqlFilter("laBusinessTypeId = ?",laBusinessTypeId);
		jqGrid.addHqlFilter("prjId = ?",prjId);
		jqGrid.addSortPropertyDesc("LA_NO");
		List<PowerAttorney> list = this.retrievePage(jqGrid);
		jqGrid.setRows(list);
		return list;
	}
	/**
	 * 按项目和业务查所有授权书信息
	 * @param laBusinessTypeId
	 * @param prjId
	 * @return
	 */
	public List<PowerAttorney> getLaByProIdAndLaBusinessTypeId(String laBusinessTypeId, String prjId){
		String hql = "from PowerAttorney p where p.laBusinessTypeId= ? and p.prjId = ? order by p.laNo asc";
		List<PowerAttorney> list = this.find(hql, laBusinessTypeId,prjId);
		return list;
	}
	/**
	 * 按项目和业务查所有授权书信息
	 * @param laBusinessTypeId
	 * @param prjId
	 * @return
	 */
	public List<PowerAttorney> getAllLa(){
		String hql = "from PowerAttorney p  order by p.laNo asc";
		List<PowerAttorney> list = this.find(hql);
		return list;
	}
	/**
	 * 按业务主键查所有授权书信息
	 * @param laBusinessTypeId
	 * @param prjId
	 * @return
	 */
	public List<PowerAttorney> getAllLaByBusinessUUID(String businessUUID){
		String hql = "from PowerAttorney p where p.businessUUID = ?";
		List<PowerAttorney> list = this.find(hql, businessUUID);
		return list;
	}
}
