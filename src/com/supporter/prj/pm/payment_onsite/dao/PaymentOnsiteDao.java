package com.supporter.prj.pm.payment_onsite.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.payment_onsite.entity.PaymentOnsite;

/**
 * 付款申请
 * @author Administrator
 *
 */
@Repository
public class PaymentOnsiteDao extends MainDaoSupport < PaymentOnsite, String > {
	

	/**
	 * 分页查询
	 * @param jqGrid
	 * @param qualityProblemIds 模块ids
	 * @return
	 */
	public List<PaymentOnsite> findPage(JqGrid jqGrid, PaymentOnsite paymentOnsite,UserProfile user) {
		if(paymentOnsite != null){
			//合同名称、付款单号模糊查询
			String paymentNo = paymentOnsite.getPaymentNo();
			if(StringUtils.isNotBlank(paymentNo) ){
				jqGrid.addHqlFilter("paymentNo like ? or contractName like ? ","%" + paymentNo + "%","%" + paymentNo + "%");
			}
			// 只获取某项目下的数据
			String prjId = paymentOnsite.getPrjId();
			if (StringUtils.isNotBlank(prjId)) {
				jqGrid.addHqlFilter(" prjId = ? ", prjId);
			} else {// 判断条件值任意，目的是返回一个空表
				jqGrid.addHqlFilter(" 1 != 1");
			}
			//付款性质
			Integer paymentNature = paymentOnsite.getPaymentNature();
			if (paymentNature != null && StringUtils.isNotBlank(paymentNature + "")) {
				jqGrid.addHqlFilter(" paymentNature = " + paymentNature);
			}
			//付款方式
			Integer paymentMethod = paymentOnsite.getPaymentMethod();
			if (paymentMethod != null && StringUtils.isNotBlank(paymentMethod + "")) {
				jqGrid.addHqlFilter(" paymentMethod = " + paymentMethod);
			}
			//币别
			String currencyId = paymentOnsite.getCurrencyId();
			if (StringUtils.isNotBlank(currencyId)) {
				jqGrid.addHqlFilter(" ((currencyId = ?) or (currencyTwoId = ?)) ", currencyId, currencyId);
			}

			// 申请日期在开始日期和结束日期内的
			Date startDate = paymentOnsite.getStartDate();
			if (startDate != null) {
				jqGrid.addHqlFilter("applyDate >= ? ", startDate);
			}
			Date endDate = paymentOnsite.getEndDate();
			if (endDate != null) {
				jqGrid.addHqlFilter("applyDate <= ? ", endDate);
			}
		}
		return this.retrievePage(jqGrid);
	}
	
	public List<PaymentOnsite> findPageToActual(JqGrid jqGrid, PaymentOnsite paymentOnsite,UserProfile user) {
		if(paymentOnsite != null){
			//合同名称、付款单号模糊查询
			String paymentNo = paymentOnsite.getPaymentNo();
			if(StringUtils.isNotBlank(paymentNo) ){
				jqGrid.addHqlFilter("paymentNo like ? or contractName like ? ","%" + paymentNo + "%","%" + paymentNo + "%");
			}	
			//付款性质
			Integer paymentNature = paymentOnsite.getPaymentNature();
			if (paymentNature != null && StringUtils.isNotBlank(paymentNature + "")) {
				jqGrid.addHqlFilter(" paymentNature = " + paymentNature);
			}
			//币别
			String currency = paymentOnsite.getCurrency();
			if (StringUtils.isNotBlank(currency)) {
				jqGrid.addHqlFilter(" ((currencyId = ?) or (currencyTwoId = ?)) ", currency,currency);
			}
			//申请日期在开始日期和结束日期内的
			Date startDate = paymentOnsite.getStartDate();
			if (startDate != null) {
				jqGrid.addHqlFilter("applyDate >= ? ", startDate);
			}
			Date endDate = paymentOnsite.getEndDate();
			if (endDate != null) {
				jqGrid.addHqlFilter("applyDate <= ? ", endDate);
			}
			
			// 只获取某项目下的数据
			String prjId = paymentOnsite.getPrjId();
			if (StringUtils.isNotBlank(prjId)) {
				jqGrid.addHqlFilter(" prjId = ? ", prjId);
			} else {// 判断条件值任意，目的是返回一个空表
				jqGrid.addHqlFilter(" 1 != 1");
			}

		}
		//支付状态
		jqGrid.addHqlFilter("statusActual = ? ", 1);
		/*String authHql = EIPService.getAuthorityService().getHqlFilter(user,
				PaymentOnsite.APP_NAME, AuthConstant.AUTH_OPER_NAME_PAGE);
		jqGrid.addHqlFilter(authHql);*/
		return this.retrievePage(jqGrid);
	}
	
	public List<PaymentOnsite> findContractPage(JqGrid jqGrid, PaymentOnsite paymentOnsite,UserProfile user) {
		if(paymentOnsite != null){
			//审批完成
			jqGrid.addHqlFilter("status = ? ", 2 );
			// 付款方式：现金 付款性质：合同项下付款
			jqGrid.addHqlFilter(" paymentNature = 0 and paymentMethod = 0");
			//付款单号
			String paymentNo = paymentOnsite.getPaymentNo();
			if(StringUtils.isNotBlank(paymentNo) ){
				jqGrid.addHqlFilter("paymentNo like ? ","%" + paymentNo + "%");
			}	
			//币别
			String currency = paymentOnsite.getCurrency();
			if (StringUtils.isNotBlank(currency)) {
				jqGrid.addHqlFilter(" currency like ? ", "%" + currency + "%");
			}
		}		
		return this.retrievePage(jqGrid);
	}
	
	public List<PaymentOnsite> findSettlementPage(JqGrid jqGrid, PaymentOnsite paymentOnsite,UserProfile user,String contractId) {
		if(paymentOnsite != null){
			//选择的合同
			jqGrid.addHqlFilter("contractId = ? ", contractId );
			//审批完成
			jqGrid.addHqlFilter("status = ? ", 2 );
			// 付款性质：合同项下付款
			jqGrid.addHqlFilter(" paymentNature = 0 ");
			//付款单号
			String paymentNo = paymentOnsite.getPaymentNo();
			if(StringUtils.isNotBlank(paymentNo) ){
				jqGrid.addHqlFilter("paymentNo like ? ","%" + paymentNo + "%");
			}	
			//币别
			String currency = paymentOnsite.getCurrency();
			if (StringUtils.isNotBlank(currency)) {
				jqGrid.addHqlFilter(" currency like ? ", "%" + currency + "%");
			}
		}		
		return this.retrievePage(jqGrid);
	}
	
	//用于选择控件
	public List<PaymentOnsite> findPageToWidget(JqGrid jqGrid, PaymentOnsite paymentOnsite,UserProfile user) {	
		if(paymentOnsite != null){			
			List<PaymentOnsite> retList = new ArrayList<PaymentOnsite>();
			StringBuffer sb = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			sb.append(" from ").append(PaymentOnsite.class.getName()).append(" z where 1=1 ");
			//付款单号模糊查询
			String paymentNo = paymentOnsite.getPaymentNo();
			if(StringUtils.isNotBlank(paymentNo)){
				sb.append("and  paymentNo like '%" + paymentNo + "%'");
			}	
			sb.append("and not exists (select reimburse.paymentId from "
					+ "Reimburse reimburse where z.id = reimburse.paymentId )");		
			this.retrievePage(jqGrid, sb.toString(), params.toArray());		
			return jqGrid.getRows();
		}
		return null;
	}
	
	//付款申请中编辑页面下方的已付款记录列表，根据contractId查询
	public List<PaymentOnsite> findPageByContractId(JqGrid jqGrid, PaymentOnsite paymentOnsite,String contractId,String id,UserProfile user) {
		if(paymentOnsite != null){
			//contractId
			if (StringUtils.isNotBlank(contractId)) {
				jqGrid.addHqlFilter(" contractId = ? and status=2 and id <> ? ", contractId,id);
			}else {
				jqGrid.addHqlFilter(" 1=2 ");
			}
		}
		return this.retrievePage(jqGrid);
	}
	
	public PaymentOnsite getByPaymentNo(String paymentNo){
		String hql = "from PaymentOnsite where  paymentNo = '" + paymentNo + "' ";
		return this.findUniqueResult(hql);
	}
	
	/**
	 * 获取委托代付
	 * @param contractId 合同ID
	 * @param dateFrom 开始日期
	 * @param dateTo 结束日期
	 * @return List<PaymentOnsite>
	 */
	public List<PaymentOnsite> getConsignPay(String contractId, Date dateFrom, Date dateTo) {
		Map <String, Object> params = new HashMap <String, Object>();
		params.put("contractId", contractId);
		params.put("purpose", 2);
		String hql = "from " + PaymentOnsite.class.getName()
				+ " where contractId=:contractId and purpose=:purpose and status="
				+ PaymentOnsite.StatusCodeTable.COMPLETE;
		if (dateFrom != null) {
			params.put("dateFrom", dateFrom);
			hql += " and applyDate >= :dateFrom";
		}
		if (dateTo != null) {
			params.put("dateTo", dateTo);
			hql += " and applyDate <= :dateTo";
		}
		
		return this.find(hql, params);
	}
	
	/**
	 * 数据库中是否存在记录.
	 * @param id 付款申请ID
	 * @return boolean
	 */
	public boolean existInDB(String id) {
		String hql = "select count(id) as recCount from "
				+ PaymentOnsite.class.getName() + " where id=?";
		Object obj = this.retrieveFirst(hql, id);
		if (obj == null) {
			return false;
		} else {
			return (Long) obj > 0;
		}
	}
}
