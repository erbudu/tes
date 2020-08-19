package com.supporter.prj.ppm.prj_op.prj_close.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.prj_op.prj_close.entity.PrjClose;

@Repository
public class PrjCloseDao extends MainDaoSupport<PrjClose, String> {

	/**
	 * 获取项目激活列表
	 * @param jqGrid 列表
	 * @param PrjClose 实体对象
	 * @param user 当前登录用户
	 * @return 项目激活列表
	 */
	public List<PrjClose> findPage(JqGrid jqGrid, PrjClose prjClose, UserProfile user) {
		String prjId = prjClose.getPrjId();
		// 只显示当前项目对应的记录
		String hql = "prjId = ?";
		jqGrid.addHqlFilter(hql, prjId);
		return this.retrievePage(jqGrid);
	}

	/**
	 *  检查该项目是否正在关闭
	 * @param prjId 项目id
	 * @return 查询结果
	 */
	public List<PrjClose> getClosingPrj(String prjId) {
		String hql = "from " + PrjClose.class.getName() + " where prjId = ? and ( status = ? or status = ?)";
		return this.find(hql, prjId, PrjClose.StatusCodeTable.DRAFT, PrjClose.StatusCodeTable.EXAM);
	}
}
