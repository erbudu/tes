/**
 * 
 */
package com.supporter.prj.ppm.poa.use_seal.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.poa.use_seal.constant.UseSealConstant;
import com.supporter.prj.ppm.poa.use_seal.entity.UseSealStartEntity;

/**
 *<p>Title: 启动用印-DAO</p>
 *<p>Description: 启动用印持久层，与数据库做交互</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月18日
 */
@Repository
public class UseSealStartDao extends MainDaoSupport<UseSealStartEntity, String>{

	/**
	 * 获取分页的用印列表
	 * @param jqGrid
	 * @param prjId 
	 * @return
	 */
	public List<UseSealStartEntity> findPage(JqGrid jqGrid, String prjId) {
		
		String hql = "status != ?";
		jqGrid.addHqlFilter(hql, new Object[] {UseSealConstant.DRAFT});
		
		if(prjId != null && prjId != "") {
			
			hql = "prjId = ?";
			jqGrid.addHqlFilter(hql, new Object[] {prjId});
		}
		
		return retrievePage(jqGrid);
		
	}

}
