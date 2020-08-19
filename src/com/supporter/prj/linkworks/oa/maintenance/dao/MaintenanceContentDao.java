package com.supporter.prj.linkworks.oa.maintenance.dao;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.maintenance.entity.MaintenanceContent;

/**   
 * @Title: Entity
 * @Description: 功能模块�?
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class MaintenanceContentDao extends MainDaoSupport < MaintenanceContent, String > {
	
	public List<MaintenanceContent> findPage(JqGrid jqGrid, String maintenanceId) {
			if (StringUtils.isNotBlank(maintenanceId)) {
				jqGrid.addHqlFilter(
						"maintenanceId = ? ", maintenanceId);
			}
	return this.retrievePage(jqGrid);
	
   }
	
//	/**
//	 * 分页查询
//	 * 
//	 * @param jqGrid
//	 * @param contractIds
//	 *            模块ids
//	 * @return
//	 */
//	public List<MaintenanceContent> findPage(String useSealReason) {		
//            String hql=" from "+MaintenanceContent.class.getName()+" where 1=1 ";
//			// //事由
//			if (StringUtils.isNotBlank(useSealReason)) {			
//                 hql=hql+"and useSealReason like '%"+useSealReason+"%'";
//                 return this.find(hql);
//			}else{
//				 return null;
//			}
//		
//	}
}
