/**
 * 
 */
package com.supporter.prj.ppm.prj_org.dev_org.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.prj_org.dev_org.entity.PrjCoreEquipmentEntity;
import com.supporter.prj.ppm.prj_org.dev_org.entity.PrjDeOrgCoop;

/**
 *<p>Title: PrjCoreEquipmentDao</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月27日
 * 
 */
@Repository
public class PrjCoreEquipmentDao extends MainDaoSupport<PrjCoreEquipmentEntity, String>{

	/**
	 *<pre>核心供应商列表分页数据</pre>
	 * @param user 当前登录用户
	 * @param jqGrid 分页列表
	 * @param prjId 项目主键
	 * @return 列表分页数据
	 */
	public List<PrjCoreEquipmentEntity> findPage(UserProfile user, JqGrid jqGrid, String prjId) {
		jqGrid.addHqlFilter("prjId = ? ",prjId);
		return this.retrievePage(jqGrid);
	}

	/**
	 * @return
	 */
	public PrjCoreEquipmentEntity getByPrjId(String prjId) {
		String hql = " from "+ PrjCoreEquipmentEntity.class.getName() 
				+" where prjId = ? ";
			List<PrjCoreEquipmentEntity> editers = find(hql,prjId);
			if (editers == null || editers.size() == 0) {
				return null;
			}	
			return editers.get(0);	
	}

	

}
