package com.supporter.prj.linkworks.oa.user_defined.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.user_defined.entity.Empchange;

/**
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0
 * 
 */
@Repository
public class EmpchangeDao extends MainDaoSupport<Empchange, String> {

	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param contractIds
	 *            模块ids
	 * @return
	 */
	public List<Empchange> findPage(UserProfile user,JqGrid jqGrid, Empchange Empchange) {
		if (Empchange != null) {
			// 查询
			String searchKey = Empchange.getCreatedBy();
			if (StringUtils.isNotBlank(searchKey)) {
				jqGrid
						.addHqlFilter(
								"createdBy like ? or oldDept like ? or toDept like ? or changeNo like ? " +
								"or changeName like ? or political like ? ",
								"%" + searchKey + "%", "%" + searchKey + "%",
								"%" + searchKey + "%", "%" + searchKey + "%",
								"%" + searchKey + "%", "%" + searchKey + "%");
			}
		}
		String personId = user.getPersonId();
		if(StringUtils.isNotBlank(personId)){
			jqGrid.addHqlFilter(" createdById = ? or departmentHeadId like ? ",personId,"%"+personId+"%");
		}else{
			jqGrid.addHqlFilter(" 1<>1 ");
		}
		
		return this.retrievePage(jqGrid);
	}
	/**
	 * 获取所有的记录.
	 * 
	 * @param user
	 * @return
	 */
	public List<Empchange> getAllList() {
		StringBuffer hql = new StringBuffer("from " + Empchange.class.getName()
				+ " where 1=1 ");
		List<Empchange> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
}
