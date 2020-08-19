package com.supporter.prj.linkworks.oa.authority_apply.dao;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApplyContent;

/**   
 * @Title: Entity
 * @Description: 功能模块�?
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class AuthorityApplyContentDao extends MainDaoSupport < AuthorityApplyContent, String > {
	
	public List<AuthorityApplyContent> findPage(JqGrid jqGrid, String applyId) {
			if (StringUtils.isNotBlank(applyId)) {
				jqGrid.addHqlFilter(
						"applyId = ? ", applyId);
			}
	return this.retrievePage(jqGrid);
}

}
