package com.supporter.prj.eip.code_share.code.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.eip.code_share.code.entity.EntityProject;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 * @Title: 项目
 * @Description: CS_ENITY_PROJECT.
 * @author Administrator
 * @date 2019-07-17 16:46:42
 * @version V1.0
 *
 */
@Repository
public class EntityProjectDao extends MainDaoSupport<EntityProject, String> {
	/**
	 * 分页查询
	 * 
	 * @param user 用户
	 * @param jqGrid 表格
	 * @param enityProject 项目对象
	 * @return List<EnityProject>
	 */
	public List<EntityProject> findPage(UserProfile user, JqGrid jqGrid, EntityProject enityProject) {
		if (enityProject != null) {
			// 搜索关键字
			String keyword = enityProject.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				String likeKeyword = "%" + keyword + "%";
				String hql = "prjNo like ? or prjName like ?";
				jqGrid.addHqlFilter(hql, likeKeyword, likeKeyword);
			}
		}
		jqGrid.addSortPropertyDesc("createdDate");
		return this.retrievePage(jqGrid);
	}

}
