package com.supporter.prj.cneec.tpc.deliver.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.deliver.entity.Deliver;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: DeliverDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-12-20
 * @version: V1.0
 */
@Repository
public class DeliverDao extends MainDaoSupport<Deliver, String> {

	/**
	 * 分页查询
	 */
	public List<Deliver> findPage(JqGrid jqGrid, Deliver deliver, String authFilter) {
		if (deliver != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = deliver.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectNo like ? or projectName like ? or contractNo like ? or contractName like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 状态过滤
			if (deliver.getSwfStatus() != null) {
				jqGrid.addHqlFilter(" swfStatus = ? ", deliver.getSwfStatus());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

}
