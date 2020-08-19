package com.supporter.prj.pm.payment_onsite.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.constant.PmSwfConstant;
import com.supporter.prj.pm.payment_onsite.dao.PaymentOnsiteDao;
import com.supporter.prj.pm.payment_onsite.dao.PaymentOnsiteSwfDao;
import com.supporter.prj.pm.payment_onsite.entity.PaymentOnsite;
import com.supporter.prj.pm.payment_onsite.entity.PaymentOnsiteSwf;
import com.supporter.prj.pm.util.PmSwfUtil;


@Service
public class PaymentOnsiteService {
	@Autowired
	private PaymentOnsiteDao dao;
	@Autowired
	private PaymentOnsiteSwfDao swfDao;
	
	/**
	 * 根据主键获取功能模块表.
	 * @param id 主键
	 * @return BankAccount
	 */
	public PaymentOnsite get(String id) {
		return dao.get(id);
	}
	
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param id 付款申请ID
	 * @param user 用户
	 * @return PaymentOnsite
	 */
	public PaymentOnsite initEditOrViewPage(String id, UserProfile user) {
		PaymentOnsite paymentOnsite;
		paymentOnsite =  dao.get(id);
		if (paymentOnsite != null) {	
			paymentOnsite.setIsNew(false);
			paymentOnsite.setModifiedBy(user.getName());
			paymentOnsite.setModifiedById(user.getPersonId());
			paymentOnsite.setModifiedDate(new Date());

			return paymentOnsite;
		} else {
			return null;
		}	
	}

	/**
	 * 分页表格展示数据.
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<PaymentOnsite> getGrid(JqGrid jqGrid, PaymentOnsite paymentOnsite, UserProfile user) {
		List< PaymentOnsite > list = dao.findPage(jqGrid, paymentOnsite, user);

		return list;
	}
	
	public List<PaymentOnsite> getGridToActual(JqGrid jqGrid, PaymentOnsite paymentOnsite, UserProfile user) {
		List< PaymentOnsite > list = dao.findPageToActual(jqGrid, paymentOnsite, user);
		return list;
	}
	
	public List<PaymentOnsite> getContractGrid(JqGrid jqGrid, PaymentOnsite paymentOnsite,UserProfile user) {
		return dao.findContractPage(jqGrid, paymentOnsite,user);
	}
	
	public List<PaymentOnsite> getSettlementGrid(JqGrid jqGrid, PaymentOnsite paymentOnsite,UserProfile user,String contractId) {
		return dao.findSettlementPage(jqGrid, paymentOnsite,user,contractId);
	}
	
	public List<PaymentOnsite> getGridToWidget(JqGrid jqGrid, PaymentOnsite paymentOnsite,UserProfile user) {
		return dao.findPageToWidget(jqGrid, paymentOnsite,user);
	}
	
	public List<PaymentOnsite> getGridByContractId(JqGrid jqGrid, PaymentOnsite paymentOnsite,String contractId,String id,UserProfile user) {
		return dao.findPageByContractId(jqGrid, paymentOnsite,contractId,id,user);
	}


	
	//double比较
	public boolean equal(double a, double b) {
	    if ((a- b> -0.0001) && (a- b) < 0.0001)
	        return true;
	    else
	        return false;
	}
	
	/**
	 * 批量启动流程
	 * @param creatorId 创建者ID
	 */
	public void startProcBatch(String creatorId) {
		List<PaymentOnsiteSwf> swfs = swfDao.getNotStartProcList();
		if (swfs != null && swfs.size() > 0) {
			int size = swfs.size();
			final String CNEEC_SPOT_PAY_1 = PmSwfConstant.PM_SWF_PROC_INNER_NAME_PAYMENT_1;
			final String CNEEC_SPOT_PAY_2 = PmSwfConstant.PM_SWF_PROC_INNER_NAME_PAYMENT_2;
			for (int i = 0; i < size; i++) {
				PaymentOnsiteSwf swf = swfs.get(i);
				swf.setPaymentOnsite(this.get(swf.getId()));
				try {
					if (swf.getPaymentNature() == 0) {
						// 业务项下付款
						PmSwfUtil.startProc(swf, creatorId, CNEEC_SPOT_PAY_1);
					} else {
						// 费用支出、非银行货币兑换
						PmSwfUtil.startProc(swf, creatorId, CNEEC_SPOT_PAY_2);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
