package com.supporter.prj.cneec.tpc.contract_sign_review_dept.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptItem1;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptReview;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.util.ContractSignReviewUtil;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: ContractSignDeptItemDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2018-03-21
 * @version: V1.0
 */
@Repository
public class ContractSignDeptItem1Dao extends MainDaoSupport<ContractSignDeptItem1, String> {

	/**
	 * 分页查询
	 */
	public List<ContractSignDeptItem1> findPage(JqGrid jqGrid, Map<String, Object> parameters) {
		if (parameters != null && !parameters.isEmpty()) {
			if (parameters.containsKey("saleContractId")) {// 销售合同一级明细
				jqGrid.addEq("saleContractId", (String) parameters.get("saleContractId"));
				if (parameters.containsKey("parentItemId") && ((String) parameters.get("parentItemId")).length() == 0) {
					jqGrid.addHqlFilter("parentItemId is null");
				}
			} else if (parameters.containsKey("parentItemId")) {// 销售合同二级明细
				jqGrid.addEq("parentItemId", (String) parameters.get("parentItemId"));
			} else if (parameters.containsKey("purchaseContractId")) {// 采购合同明细
				jqGrid.addEq("purchaseContractId", (String) parameters.get("purchaseContractId"));
			} else {
				jqGrid.addHqlFilter(" 1 <> 1 ");
			}
			jqGrid.addSortPropertyAsc("itemId");
		}
		return this.retrievePage(jqGrid);
	}

	/**
	 * 根据条件过滤数据
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ContractSignDeptItem1> findList(Map<String, Object> parameters) {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + ContractSignDeptItem1.class.getName());
		String filter = "";
		for (Map.Entry<String, Object> e : parameters.entrySet()) {
			if (filter.length() > 0) filter = filter + " and ";
			if (e.getValue() != null) {
				filter += e.getKey() + " = :" + e.getKey();
			} else {
				filter += e.getKey() + " is null";
				//parameters.remove(e.getKey());
			}
		}
		hql.append(" where ").append(filter);
		return this.retrieve(hql.toString(), parameters);
	}

	/**
	 * 清除采购合同评审属性
	 */
	public void removePurchaseValue(String inforId) {
		String hql = "update " + ContractSignDeptItem1.class.getName() + " set purchaseReviewId = null, purchaseContractId = null where purchaseContractId = ?";
		this.execUpdate(hql, inforId);
	}

	/** 以下是选择控件方法 **/

	@SuppressWarnings("unchecked")
	public List<ContractSignDeptItem1> getList(Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrieve(hql, parameters);
	}

	public ListPage<ContractSignDeptItem1> getListPage(ListPage<ContractSignDeptItem1> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return this.retrievePage(listPage, hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + ContractSignDeptItem1.class.getName() + " t where 1 = 1");
		// 过滤项目，必要条件
		if (parameters.containsKey("projectId")) {
			hql.append(" and t.saleReviewId in (select s.signId from " + ContractSignDeptReview.class.getName() + " s where s.projectId = :projectId and s.swfStatus = :swfStatus)");
		} else {
			hql.append(" and 1 <> 1");
		}
		// 评审单状态（本评审单为草稿，其他评审单为审批完成）
		int swfStatus = ContractSignDeptReview.DRAFT;
		// 选择过滤条件
		if (parameters.containsKey("selectTo")) {
			int selectTo = Integer.parseInt(parameters.get("selectTo").toString());
			if (selectTo == ContractSignReviewUtil.INFOR_TYPE_CONTRACT) {
				// 为采购合同添加明细过滤货物/服务表
				hql.append(" and (");
				// 无下级的一级明细或二级明细
				hql.append(" (t.leaf = 'T' and t.purchaseContractId is null)");
				// 有下级且二级明细未全部添加到采购合同中的一级明细
				hql.append(" or ( t.leaf = 'F' and t.itemId in (select t1.parentItemId from " + ContractSignDeptItem1.class.getName() + " t1 where t1.purchaseContractId is null) )");
				hql.append(" )");
				if (parameters.containsKey("initialReviewType")) {
					int initialReviewType = Integer.parseInt(parameters.get("initialReviewType").toString());
					if (initialReviewType == ContractSignReviewUtil.REVIEW_TYPE_SUBCONTRACT) {
						// 本次评审类型为销售+采购时只取本评审单中销售合同下的货物/服务明细，评审单ID，必要字段
						if (parameters.containsKey("signId")) {
							hql.append(" and t.saleReviewId = :signId");
						} else {
							hql.append(" and 1 <> 1");
						}
					} else if (initialReviewType == ContractSignReviewUtil.REVIEW_TYPE_CONTRACT) {
						// 本次评审为采购合同评审时取所有未分配采购合同且已评审完成的货物/服务明细(可能是之前销售合同评审也可能是销售+采购合同评审下货物)
						hql.append(" and t.purchaseReviewId is null");
						swfStatus = ContractSignDeptReview.COMPLETED;
						
						if (parameters.containsKey("itemNames")) {
							parameters.put("itemNames", parameters.get("itemNames").toString().replaceAll("%2C", ","));
							String itemNames = parameters.get("itemNames").toString();
							if(itemNames.length()>0) {
								//hql.append(" and instr(:itemNames,itemName)<=0");
								
								for (String itemName : itemNames.split(",")) {
									hql.append(" and instr(:itemNames,','||itemName||',')<=0");
								}
							}
						} 
//						System.out.println(parameters.get("itemNames"));
//						System.out.println(hql);
					}
				}
			}
		} else {
			hql.append(" and 1 <> 1");
		}
		parameters.put("swfStatus", swfStatus);
		// 其他过滤参数
		if (parameters.containsKey("keyword")) {
			String keyword = (String) parameters.get("keyword");
			hql.append(" and (");
			hql.append(" t.itemName like :keyword");
			hql.append(" or t.specification like :keyword");
			hql.append(" or t.unit like :keyword");
			hql.append(" or t.remarks like :keyword");
			hql.append(")");
			parameters.put("keyword", "%" + keyword + "%");
		}
		hql.append(" order by t.itemId");
		//System.out.println(hql);
		return hql.toString();
	}

}
