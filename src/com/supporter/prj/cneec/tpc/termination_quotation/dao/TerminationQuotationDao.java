package com.supporter.prj.cneec.tpc.termination_quotation.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.termination_quotation.entity.TerminationQuotation;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: TerminationQuotationDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-09-18
 * @version: V1.0
 */
@Repository
public class TerminationQuotationDao extends MainDaoSupport<TerminationQuotation, String> {

	/**
	 * 分页查询
	 */
	public List<TerminationQuotation> findPage(JqGrid jqGrid, TerminationQuotation terminationQuotation, String authFilter) {
		if (terminationQuotation != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = terminationQuotation.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectName like ? or terminationReson like ? or terminationTime like ? or notifyPerson like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 状态过滤
			if (terminationQuotation.getSwfStatus() != null) {
				jqGrid.addHqlFilter(" swfStatus = ? ", terminationQuotation.getSwfStatus());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

}
