package com.supporter.prj.cneec.tpc.record_filing_manager.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChange;
import com.supporter.prj.cneec.tpc.contract_online.entity.ContractOnline;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignInfor;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignReview;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.util.ContractSignReviewUtil;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderChange;
import com.supporter.prj.cneec.tpc.order_online.entity.OrderOnline;
import com.supporter.prj.cneec.tpc.protocol_review.entity.ProtocolReview;
import com.supporter.prj.cneec.tpc.record_filing_manager.entity.RecordFilingManager;
import com.supporter.prj.cneec.tpc.use_seal.entity.UseSealDetail;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

@Repository
public class RecordFilingManagerDao extends MainDaoSupport<RecordFilingManager, String> {
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param recordFiling
	 * @return
	 */
	public List<RecordFilingManager> findPage(JqGrid jqGrid, RecordFilingManager recordFiling, String authFilter){
		if (recordFiling != null){
			//查询
			String keyword = recordFiling.getKeyword();
			if(StringUtils.isNotBlank(keyword)){
				jqGrid.addHqlFilter(
						" projectName like ? or recordFilingNo like ?  or reviewNo like ? or businessNo like ?", 
						"%" + keyword + "%",
						"%" + keyword + "%",
						"%" + keyword + "%",
						"%" + keyword + "%");
			}
			//条件过滤
			Integer swfStatus = recordFiling.getSwfStatus();
			if (swfStatus != null){//状态
				jqGrid.addHqlFilter(" swfStatus = ? ", swfStatus);
			}
			String recordFilingTypeCode = recordFiling.getRecordFilingTypeCode();
			if (StringUtils.isNotBlank(recordFilingTypeCode)){//备案类型
				jqGrid.addHqlFilter(" recordFilingTypeCode = ? ", recordFilingTypeCode);
			}
			// 备案类型记录过滤
			String relyBy = recordFiling.getRelyBy();
			if (StringUtils.isNotBlank(relyBy)) {// 用印依赖类型
				jqGrid.addHqlFilter(" relyBy = ? ", relyBy);
			} else {
				jqGrid.addHqlFilter(" 1 <> 1 ");
			}
			String isUseSeal = recordFiling.getIsUseSeal();
			if (StringUtils.isNotBlank(isUseSeal)){//是否用印
				jqGrid.addHqlFilter(" isUseSeal = ? ", isUseSeal);
			}
	        //根据备案编号倒叙排列
			jqGrid.addSortPropertyDesc("recordFilingNo");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 根据评审id获取业务编号
	 * @param reviewId
	 * @return
	 */
	public String getBusinessNoByReviewId(String reviewId){
		String businessNo = null;
//		String Hql = " from " + RecordFilingManager.class.getName() + " where 1=1 reviewId = ? ";
//		List<RecordFilingManager> list = this.retrieve(Hql,reviewId);
		List<RecordFilingManager> list = this.findBy("reviewId", reviewId);
		if (list != null && list.size() > 0){
			RecordFilingManager entity = list.get(0);
			businessNo = entity.getBusinessNo();
		}
		return businessNo;
	}
	
	/**
	 * 根据项目ID获取框架协议评审
	 * @param prjId
	 * @return
	 */
	public List<ProtocolReview> getProtocolReviewList(String prjId){
		String Hql = " from "+ ProtocolReview.class.getName()+" p where 1=1 "
				+ " and projectId = ? and swfStatus = ? and not exists "
				+ " (select r.reviewId from "+RecordFilingManager.class.getName()+" r where p.reviewId = r.reviewId) ";
		List<ProtocolReview> list = this.retrieve(Hql, prjId, ProtocolReview.COMPLETED);
		if (list == null || list.size() == 0){
			return null;
		}
		return list;
	}
	
	/**
	 * 根据项目ID获取销售合同评审
	 * @param prjId
	 * @return
	 */
	public List<ContractSignInfor> getOrderSignReviewList(String prjId){
		String Hql = " from " + ContractSignInfor.class.getName() + " infor where 1=1 "
				+ " and infor.signId in (select review.signId from " + ContractSignReview.class.getName() + " review where "
				+ " review.projectId = ? and review.swfStatus = ?) and infor.inforType = ? "
				+ " and not exists(select rf.reviewId from " + RecordFilingManager.class.getName() + " rf "
				+ " where rf.reviewId = infor.inforId) order by infor.signId desc,infor.inforId asc ";
		List<ContractSignInfor> list = this.retrieve(Hql, prjId, ContractSignReview.COMPLETED, ContractSignReviewUtil.INFOR_TYPE_ORDER);
		if (list == null || list.size() == 0){
			return null;
		}
		return list;
	}
	
	/**
	 * 根据项目ID获取销售合同协议变更
	 * @param prjId
	 * @return
	 */
	public List<OrderChange> getOrderProtocolChange(String prjId){
		String Hql = " from " + OrderChange.class.getName() + " where 1=1 and projectId = ? and hasProtocol = ? "
				+ " and swfStatus = ? ";
		List<OrderChange> list = this.retrieve(Hql,prjId,true,OrderChange.COMPLETED);
//		List<OrderChange> list =  this.find("projectId",prjId,"hasProtocol","T","swfStatus",OrderChange.COMPLETED);
		if (list == null || list.size() == 0){
			return null;
		}
		return list;
	}
	
	/**
	 * 根据项目ID获取采购合同评审
	 * @param prjId
	 * @return
	 */
	public List<ContractSignInfor> getContractSignReviewList(String prjId){
		String Hql = " from " + ContractSignInfor.class.getName() + " infor where 1=1 "
				+ " and infor.signId in (select review.signId from " + ContractSignReview.class.getName() + " review where "
				+ " review.projectId = ? and review.swfStatus = ?) and infor.inforType = ? "
				+ " and not exists(select rf.reviewId from " + RecordFilingManager.class.getName() + " rf "
				+ " where rf.reviewId = infor.inforId) order by infor.signId desc,infor.inforId asc ";
		List<ContractSignInfor> list = this.retrieve(Hql, prjId, ContractSignReview.COMPLETED, ContractSignReviewUtil.INFOR_TYPE_CONTRACT);
		if (list == null || list.size() == 0){
			return null;
		}
		return list;
	}
	
	/**
	 * 根据项目ID获取合同协议变更
	 * @param prjId
	 * @return
	 */
	public List<ContractChange> getContractProtocolChange(String prjId){
		String Hql = " from " + ContractChange.class.getName() + " where projectId = ? and hasProtocol = ? and swfStatus = ? ";
//		List<ContractChange> list = this.find("projectId",prjId,"hasProtocol",true,"swfStatus",ContractChange.COMPLETED);
		List<ContractChange> list = this.retrieve(Hql,prjId,true,ContractChange.COMPLETED);
		if (list == null || list.size() ==0){
			return null;
		}
		return list;
	}
	
	/**
	 * 根据评审ID获取用印单明细
	 * @param prjId
	 * @return
	 */
	public UseSealDetail getUseSealDetail(String reviewId){
		String Hql = " from " + UseSealDetail.class.getName() + " detail where 1=1 detail.signId = ? ";
		List<UseSealDetail> list = this.retrieve(Hql, reviewId);
		if (list == null || list.size() == 0){
			return null;
		}
		UseSealDetail detail = list.get(0);
		return detail;
	}
	
	/**
	 * 获取最大业务单号
	 * @param NoHead
	 * @return
	 */
	public Integer getRecordFilingIndex(String NoHead){
		Integer count = 1;
		String Hql = "select max(rfm.recordFilingNo) from "+ RecordFilingManager.class.getName()+" as rfm where 1=1 "
				+ " and rfm.recordFilingNo like ?";
		List<String> list = this.find(Hql, "%"+ NoHead + "%");
		if (list.size() > 0) {
			String str = list.get(0);
			if (CommonUtil.trim(str).length() > 0) {
				String count_s = str.substring(str.length() - 3);
				Integer b = Integer.parseInt(count_s);
				count = b > 0 ? (b + 1) : 1;
			}
		}
		return count;
	}

	/** 以下是选择备案单方法 **/

	public ListPage<RecordFilingManager> getListPage(ListPage<RecordFilingManager> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return retrievePage(listPage, hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + RecordFilingManager.class.getName() + " t where 1=1");
		// 项目ID，必要字段
		String projectId = (String) parameters.get("projectId");
		if (StringUtils.isNotBlank(projectId)) {
			hql.append(" and t.projectId = :projectId");
			parameters.put("projectId", projectId);
		} else {
			hql.append(" and 1 <> 1");
		}
		String keyword = (String) parameters.get("keyword");
		if (StringUtils.isNotBlank(keyword)) {
			hql.append(" and (t.recordFilingNo like :keyword)");
			parameters.put("keyword", "%" + keyword + "%");
		}
		// 为特定模块选择过滤
		if (parameters.containsKey("selectTo")) {
			String selectTo = (String) parameters.get("selectTo");
			hql.append(getSelectToHql(selectTo, parameters));
		}
		hql.append(" order by t.recordFilingNo");
		return hql.toString();
	}

	/**
	 * 为对外报价评审/合同签约前评审等做过滤
	 * @param hql
	 * @param selectTo
	 * @return
	 */
	private String getSelectToHql(String selectTo, Map<String, Object> parameters) {
		StringBuffer filter = new StringBuffer();
		if (selectTo.equals(TpcConstant.ORDER_CONTRACT)) {// 销售合同上线
			filter.append(" and (");
			filter.append(" t.recordFilingTypeCode = :recordFilingTypeCode");
			parameters.put("recordFilingTypeCode", TpcConstant.ORDER_CONTRACT);
			filter.append(" and t.recordFilingId not in (select t1.filingId from " + OrderOnline.class.getName() + " t1)");
			filter.append(" )");
		} else if (selectTo.equals(TpcConstant.PURCHASE_CONTRACT)) {// 采购合同上线
			filter.append(" and (");
			filter.append(" t.recordFilingTypeCode = :recordFilingTypeCode");
			parameters.put("recordFilingTypeCode", TpcConstant.ORDER_CONTRACT);
			filter.append(" and t.recordFilingId not in (select t1.filingId from " + ContractOnline.class.getName() + " t1)");
			filter.append(" )");
		}
		return filter.toString();
	}

	/**
	 * 过滤查询数据
	 * @param params
	 * @param likeSearhNames
	 * @param orders
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RecordFilingManager> queryByParam(Map<String, Object> params, List<String> likeSearhNames, Map<String, Boolean> orders) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(RecordFilingManager.class, params, likeSearhNames, orders);
		return (List<RecordFilingManager>) getHibernateTemplate().findByCriteria(dc);
	}

}
