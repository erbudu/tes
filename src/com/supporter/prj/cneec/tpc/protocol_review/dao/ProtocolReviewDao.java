package com.supporter.prj.cneec.tpc.protocol_review.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.protocol_review.entity.ProtocolReview;
import com.supporter.prj.cneec.tpc.record_filing_manager.entity.RecordFilingManager;
import com.supporter.prj.cneec.tpc.use_seal.entity.UseSealDetail;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: ProtocolReviewDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-09-06
 * @version: V1.0
 */
@Repository
public class ProtocolReviewDao extends MainDaoSupport<ProtocolReview, String> {

	/**
	 * 分页查询
	 */
	public List<ProtocolReview> findPage(JqGrid jqGrid, ProtocolReview protocolReview, String authFilter) {
		if (protocolReview != null) {
			String keyword = protocolReview.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" deptName like ? or projectNo like ? or projectName like ? or protocolName like ? or customerName like ?",
						"%" + keyword + "%",
						"%" + keyword + "%",
						"%" + keyword + "%",
						"%" + keyword + "%",
						"%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 状态过滤
			if (protocolReview.getSwfStatus() != null) {
				jqGrid.addHqlFilter(" swfStatus= ? ", protocolReview.getSwfStatus());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}


	/** 以下是选择框架协议评审方法 **/

	@SuppressWarnings("unchecked")
	public List<ProtocolReview> getList(Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrieve(hql, parameters);
	}

	public ListPage<ProtocolReview> getListPage(ListPage<ProtocolReview> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + ProtocolReview.class.getName() + " t where 1=1");
		// 状态(默认为完成)
		Integer swfStatus = (Integer) parameters.get("swfStatus");
		if (swfStatus == null || swfStatus != 0) {
			swfStatus = ProtocolReview.COMPLETED;
		}
		hql.append(" and t.swfStatus = :swfStatus");
		parameters.put("swfStatus", swfStatus);
		// 项目ID，必要字段
		String projectId = (String) parameters.get("projectId");
		if (StringUtils.isNotBlank(projectId)) {
			hql.append(" and t.projectId = :projectId");
		} else {
			hql.append(" and 1 <> 1");
		}
		// 用印选择或备案选择
		String type = (String) parameters.get("type");
		if (CommonUtil.trim(type).equals("useSeal")) {
			// 用印选择评审单时排除掉其他用印单已选择过的评审单
			hql.append(" and t.reviewId not in (select u.signId from " + UseSealDetail.class.getName() + " u where u.signId is not null)");
		} else if (CommonUtil.trim(type).equals("recordFiling")) {
			// 备案选择评审单时排除掉其他用印单已选择过的评审单
			hql.append(" and t.reviewId not in (select f.signId from " + RecordFilingManager.class.getName() + " f where f.signId is not null)");
		}
		// 其他过滤条件
		String keyword = (String) parameters.get("keyword");
		if (StringUtils.isNotBlank(keyword)) {
			hql.append(" and (t.customerName like :keyword or t.protocolName like :keyword)");
			parameters.put("keyword", "%" + keyword + "%");
		}
		hql.append(" order by t.protocolName");
		return hql.toString();
	}

}
