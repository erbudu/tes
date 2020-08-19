package com.supporter.prj.cneec.tpc.order_change.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.order_change.entity.OrderChange;
import com.supporter.prj.cneec.tpc.record_filing_manager.entity.RecordFilingManager;
import com.supporter.prj.cneec.tpc.use_seal.entity.UseSealDetail;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: OrderChangeDao
 * @Description: DAO类
 */
@Repository
public class OrderChangeDao extends MainDaoSupport<OrderChange, String> {

	/**
	 * 分页查询
	 */
	public List<OrderChange> findPage(JqGrid jqGrid, OrderChange orderChange,  String contractId, String authFilter) {
	  if (contractId != null) {
	 	if (orderChange != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = orderChange.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectName like ? or orderNo like ? or orderName like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}
			jqGrid.addHqlFilter("contractId = ?", contractId);
			/* 以下是更多条件中选择项 */
			// 状态过滤
			if (orderChange.getSwfStatus() != null) {
				jqGrid.addHqlFilter(" swfStatus = ? ", orderChange.getSwfStatus());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}
	
	public String checkOrderChange(String contractId) {
		String hql = "from OrderChange where contractId = ? order by createdDate desc";
		List<OrderChange> orderList = find(hql, contractId );
		if ((orderList != null) && (orderList.size() > 0)) {
			return ((OrderChange) orderList.get(0)).getChangeId();
		}
		return "";
	}

	/**
	 * 获取当前年月最大流水号
	 * @param NoHead
	 * @return
	 */
	public Integer getSerialNumberIndex(String NoHead) {
		Integer count = 1;
		String hql = "select max(t.serialNumber) from " + OrderChange.class.getName() + " t where t.serialNumber like ?";
		List<String> list = this.find(hql, NoHead + "%");
		if (list.size() > 0) {
			String str = list.get(0);
			if (StringUtils.isNotBlank(str)) {
				String count_s = str.substring(str.length() - 3);
				Integer b = Integer.parseInt(count_s);
				count = b > 0 ? (b + 1) : 1;
			}
		}
		return count;
	}

	/** 以下是选择销售合同变更方法 **/

	@SuppressWarnings("unchecked")
	public List<OrderChange> getList(Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrieve(hql, parameters);
	}

	public ListPage<OrderChange> getListPage(ListPage<OrderChange> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + OrderChange.class.getName() + " t where 1=1");
		// 状态(默认为完成)
		Integer swfStatus = (Integer) parameters.get("swfStatus");
		if (swfStatus == null || swfStatus != 0) {
			swfStatus = OrderChange.COMPLETED;
		}
		hql.append(" and t.swfStatus = :swfStatus");
		parameters.put("swfStatus", swfStatus);
		// 默认选择有协议
		String hasProtocol = (String) parameters.get("hasProtocol");
		boolean isHasProtocol = true;
		if (StringUtils.isBlank(hasProtocol)) {
			isHasProtocol = true;
		} else {
			isHasProtocol = Boolean.valueOf(String.valueOf(parameters.get("hasProtocol")));
		}
		hql.append(" and t.hasProtocol = :isHasProtocol");
		parameters.put("isHasProtocol", isHasProtocol);
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
			hql.append(" and t.changeId not in (select u.signId from " + UseSealDetail.class.getName() + " u where u.signId is not null)");
		} else if (CommonUtil.trim(type).equals("recordFiling")) {
			// 备案选择评审单时排除掉其他用印单已选择过的评审单
			hql.append(" and t.changeId not in (select f.signId from " + RecordFilingManager.class.getName() + " f where f.signId is not null)");
		}
		// 其他过滤条件
		String keyword = (String) parameters.get("keyword");
		if (StringUtils.isNotBlank(keyword)) {
			hql.append(" and (t.orderNo like :keyword or t.orderName like :keyword)");
			parameters.put("keyword", "%" + keyword + "%");
		}
		// 调整性质
		String adjustmentNatureId = (String) parameters.get("adjustmentNatureId");
		if (StringUtils.isNotBlank(adjustmentNatureId)) {
			hql.append(" and t.adjustmentNatureId = :adjustmentNatureId");
		}
		// 变更方式
		String changeModeId = (String) parameters.get("changeModeId");
		if (StringUtils.isNotBlank(changeModeId)) {
			hql.append(" and t.changeModeId = :changeModeId");
		}
		// 合同金额
		double contractRmbAmount1 = CommonUtil.parseDouble(String.valueOf(parameters.get("contractRmbAmount1")), 0);
		double contractRmbAmount2 = CommonUtil.parseDouble(String.valueOf(parameters.get("contractRmbAmount2")), 0);
		if (contractRmbAmount1 != 0 || contractRmbAmount2 != 0) {
			hql.append(" and t.contractRmbAmount between :contractRmbAmount1 and :contractRmbAmount2");
			parameters.put("contractRmbAmount1", contractRmbAmount1);
			parameters.put("contractRmbAmount2", contractRmbAmount2);
		}
		hql.append(" order by t.orderNo");
		return hql.toString();
	}

}
