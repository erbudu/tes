package com.supporter.prj.linkworks.oa.use_seal_apply.dao;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.use_seal_apply.entity.UseSealApplyContent;

/**   
 * @Title: Entity
 * @Description: 功能模块�?
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class UseSealApplyContentDao extends MainDaoSupport < UseSealApplyContent, String > {
	
	public List<UseSealApplyContent> findPage(JqGrid jqGrid, String applyId) {
			if (StringUtils.isNotBlank(applyId)) {
				jqGrid.addHqlFilter(
						"applyId = ? ", applyId);
			}
	return this.retrievePage(jqGrid);
	
   }
	
	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param contractIds
	 *            模块ids
	 * @return
	 */
	public List<UseSealApplyContent> findPageByUseSealReason(JqGrid jqGrid,String useSealReason) {		
           
			// //事由
			if (StringUtils.isNotBlank(useSealReason)) {	
//				 String hql=" from "+UseSealApplyContent.class.getName()+" where 1=1 ";
//                 hql=hql+"and useSealReason like '%"+useSealReason+"%'";
//                 return this.find(hql);
				jqGrid.addHqlFilter(
						"useSealReason like ? ", "%" + useSealReason + "%");
				return this.retrievePage(jqGrid);
			}else{
				 return null;
			}
		
	}
}
