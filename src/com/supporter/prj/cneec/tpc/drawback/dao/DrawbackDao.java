package com.supporter.prj.cneec.tpc.drawback.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.drawback.entity.Drawback;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: DrawbackDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-11-20
 * @version: V1.0
 */
@Repository
public class DrawbackDao extends MainDaoSupport<Drawback, String> {

	/**
	 * 分页查询
	 */
	public List<Drawback> findPage(JqGrid jqGrid, Drawback drawback, String authFilter) {
		if (drawback != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = drawback.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectName like ? or contractNo like ? or contractName like ? or budgetName like ? or drawbackAmount like ? or drawbackDate like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 状态过滤
			if (drawback.getSwfStatus() != null) {
				jqGrid.addHqlFilter(" swfStatus = ? ", drawback.getSwfStatus());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

}
