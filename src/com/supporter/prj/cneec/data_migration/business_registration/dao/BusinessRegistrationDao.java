package com.supporter.prj.cneec.data_migration.business_registration.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.data_migration.business_registration.entity.BusinessRegistration;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: dao
 * @Description: 数据迁移业务注册
 * @author yanbingchao
 * @date 2017-08-09 14:00:00
 * @version V1.0
 * 
 */
@Repository
public class BusinessRegistrationDao extends
		MainDaoSupport<BusinessRegistration, String> {
	/**
	 * 查询操作
	 * 
	 * 
	 * @param keyword
	 * @return List < BusinessRegistration >
	 */
	public List<BusinessRegistration> findByKeyword(String keyword) {
		String hql = "from " + BusinessRegistration.class.getName()
				+ " where keyWords like ?";
		List<BusinessRegistration> entities = this.find(hql, "%"
				+ CommonUtil.trim(keyword) + "%");
		return entities;
	}

	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param code
	 *            
	 * @return
	 */
	// 查询,年月,发布者姓名,发布部门名称.
	public List<BusinessRegistration> findPage(JqGrid jqGrid,
			BusinessRegistration code) {
		if (code != null) {
			String key = code.getBusinessName();
			if (StringUtils.isNotBlank(key)) {
				jqGrid.addHqlFilter(
						" businessName like ? or businessInnerName like ? ",
						"%" + key + "%", "%" + key + "%");
			}
			String dataSource = code.getDataSource();
			if (StringUtils.isNotBlank(dataSource)) {
				jqGrid.addHqlFilter(" dataSource = ? ", dataSource);
			}
		}
		return this.retrievePage(jqGrid);
	}
}
