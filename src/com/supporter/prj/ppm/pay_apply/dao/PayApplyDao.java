package com.supporter.prj.ppm.pay_apply.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.pay_apply.entity.PayApply;

/**
 * @Title: PayApplyDao
 * @Description: DAO类
 * @author: 
 * @date: 2018-07-13
 * @version: V1.0
 */
@Repository
public class PayApplyDao extends MainDaoSupport<PayApply, String> {

	/**
	 * 分页查询
	 */
	public List<PayApply> findPage(JqGrid jqGrid,  PayApply  payApply, UserProfile user) {
		// 只显示当前项目对应的记录
		String prjId = payApply.getPrjId();
		String hql = "prjId = ?";
		jqGrid.addHqlFilter(hql, prjId);
		return this.retrievePage(jqGrid);
	}

	/**
	 * 支付申请编号生成规则
	 * @return 支付申请编号
	 */
	public List<PayApply> generatePayNo() {
		String hql = "from " + PayApply.class.getName() + " where 1=1 order by createdDate desc";
		List<PayApply> list = this.find(hql);
		return list;
	}

}
