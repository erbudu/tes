package com.supporter.prj.ppm.prj_op.pact_close.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ppm.prj_op.pact_close.entity.PactClose;

@Repository
public class PactCloseDao extends MainDaoSupport<PactClose, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<PactClose> findPage(JqGrid jqGrid, PactClose entity) {
		String prjId = entity.getPrjId();
		// 只显示当前项目对应的记录
		String hql = "prjId = ?";
		jqGrid.addHqlFilter(hql, prjId);
		return this.retrievePage(jqGrid);
	}


}

