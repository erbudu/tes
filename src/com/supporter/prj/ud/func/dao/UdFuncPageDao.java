package com.supporter.prj.ud.func.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ud.func.entity.UdFuncPage;
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
public class UdFuncPageDao extends
		MainDaoSupport<UdFuncPage, String> {
	/**
	 * 查询操作
	 * 
	 * 
	 * @param keyword
	 * @return List < UdFuncPage >
	 */
	public List<UdFuncPage> findByKeyword(String keyword) {
		String hql = "from " + UdFuncPage.class.getName()
				+ " where keyWords like ?";
		List<UdFuncPage> entities = this.find(hql, "%"
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
	public List<UdFuncPage> findPage(JqGrid jqGrid,
			UdFuncPage code) {
		if (code != null) {
			String key = code.getFuncName();
			if (StringUtils.isNotBlank(key)) {
				jqGrid.addHqlFilter(
						" funcName like ? ",
						"%" + key + "%");
			}
		}
		return this.retrievePage(jqGrid);
	}
}
