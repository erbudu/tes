/**
 * 
 */
package com.supporter.prj.ppm.poa.use_seal.dao;

import java.util.List;

import org.jasypt.commons.CommonUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ppm.poa.use_seal.entity.UseSealFileEntity;

/**
 *<p>Title: 用印文件列表DAO</p>
 *<p>Description: 用印文件列表持久层-与数据库做交互</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月18日
 */
@Repository
public class UseSealFileDao extends MainDaoSupport<UseSealFileEntity, String>{

	
	/**
	 * 获取分页的用印详单列表
	 * @param useSealId 外键
	 * @param jqGrid
	 * @return
	 */
	public List<UseSealFileEntity> findPage(String useSealId, JqGrid jqGrid) {
		
		String hql = "";

	    if (CommonUtils.isNotEmpty(useSealId)) {
	      hql = "useSealId = ?";
	      jqGrid.addHqlFilter(hql, new Object[] {useSealId});
	    }
		
	    return retrievePage(jqGrid);
	    
	}

	public List<UseSealFileEntity> findPageByBusinessId(String businessUUId, JqGrid jqGrid) {
		String hql = "";

	    if (CommonUtils.isNotEmpty(businessUUId)) {
	      hql = "businessUUID = ?";
	      jqGrid.addHqlFilter(hql, new Object[] {businessUUId});
	    }
		
	    return retrievePage(jqGrid);
	}

}
