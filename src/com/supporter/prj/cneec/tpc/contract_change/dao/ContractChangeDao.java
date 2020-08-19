package com.supporter.prj.cneec.tpc.contract_change.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChange;
import com.supporter.prj.cneec.tpc.record_filing_manager.entity.RecordFilingManager;
import com.supporter.prj.cneec.tpc.use_seal.entity.UseSealDetail;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;


@Repository
public class ContractChangeDao extends MainDaoSupport<ContractChange, String> {

	/**
	 * 分页查询
	 */
	public List<ContractChange> findPage(JqGrid jqGrid, ContractChange contractChange, String contractId, String authFilter) {
//		System.out.println("contractId:"+contractId);
		if (contractId != null) {
//			System.out.println("contractId222:"+contractId);
			if(contractChange != null) {
				// 列表页面搜索输入框可查询字段
				String keyword = contractChange.getKeyword();
//				System.out.println("keyword:"+keyword);
				if (StringUtils.isNotBlank(keyword)) {
					jqGrid.addHqlFilter(" adjustmentNature like ? or changeMode like ? ",
										"%" + keyword + "%", "%" + keyword + "%");
				}
				//采购合同id
				jqGrid.addHqlFilter("contractId = ?", contractId);
				/* 以下是更多条件中选择项 */
				// 状态过滤
				if (contractChange.getSwfStatus() != null) {
					jqGrid.addHqlFilter(" swfStatus = ? ", contractChange.getSwfStatus());
				}
				//权限
				jqGrid.addHqlFilter(authFilter);
				// 根据创建时间倒序排列
				jqGrid.addSortPropertyDesc("createdDate");
			}
		}
		return this.retrievePage(jqGrid);
	}


	/** 以下是选择采购合同变更方法 **/

	@SuppressWarnings("unchecked")
	public List<ContractChange> getList(Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrieve(hql, parameters);
	}

	public ListPage<ContractChange> getListPage(ListPage<ContractChange> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + ContractChange.class.getName() + " t where 1=1");
		// 状态(默认为完成)
		Integer swfStatus = (Integer) parameters.get("swfStatus");
		if (swfStatus == null || swfStatus != 0) {
			swfStatus = ContractChange.COMPLETED;
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
			hql.append(" and (t.contractNo like :keyword or t.contractName like :keyword)");
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
		hql.append(" order by t.contractNo");
		return hql.toString();
	}


	 public String checkOrderChange(String contractId)
	  {
	    String hql = "from ContractChange where contractId = ? order by createdDate desc";
	    List<ContractChange> orderList = find(hql, contractId );
	    if ((orderList != null) && (orderList.size() > 0)) {
	      return ((ContractChange)orderList.get(0)).getChangeId();
	    }
	    return "";
	  }

}
