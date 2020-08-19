package com.supporter.prj.pm.fund_appropriation.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.constant.PmSwfConstant;
import com.supporter.prj.pm.fund_appropriation.dao.FundAppropriationDao;
import com.supporter.prj.pm.fund_appropriation.dao.FundAppropriationSwfDao;
import com.supporter.prj.pm.fund_appropriation.dao.FundBudgetExpendDao;
import com.supporter.prj.pm.fund_appropriation.dao.FundReceiptActualDao;
import com.supporter.prj.pm.fund_appropriation.dao.FundReceiptDao;
import com.supporter.prj.pm.fund_appropriation.entity.FundAppropriation;
import com.supporter.prj.pm.fund_appropriation.entity.FundAppropriationSwf;
import com.supporter.prj.pm.fund_appropriation.entity.FundBudgetExpend;
import com.supporter.prj.pm.fund_appropriation.entity.FundReceipt;
import com.supporter.prj.pm.fund_appropriation.entity.FundReceiptActual;
import com.supporter.prj.pm.util.PmSwfUtil;

/**
 * 资金拨付
 * @author Administrator
 *
 */
@Service
public class FundAppropriationService {
	public final static String FILE_payRequest = "payRequest"; //请款单
	@Autowired
	private FundAppropriationDao dao;
	@Autowired
	private FundReceiptDao fundReceiptDao;
	@Autowired
	private FundReceiptActualDao actualDao;
	@Autowired
	private FundBudgetExpendDao expendDao;
	@Autowired
	private FundAppropriationSwfDao swfDao;

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param lastFundId lastFundId
	 * @param fundId 资金拨付ID
	 * @param user 用户
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return FundAppropriation
	 */
	public FundAppropriation initEditOrViewPage(String lastFundId, String fundId, UserProfile user,
			Date startDate, Date endDate) {
		FundAppropriation fundAppropriation = new FundAppropriation();			
		if (StringUtils.isNotBlank(fundId)) {
			fundAppropriation =  dao.get(fundId);
			fundAppropriation.setIsNew(false);
			fundAppropriation.setModifiedBy(user.getName());
			fundAppropriation.setModifiedById(user.getPersonId());
			fundAppropriation.setModifiedDate(new Date());
			
			if (lastFundId != null) {
				List<FundReceiptActual> actualList = actualDao.getFundReceiptActual(lastFundId);
				if (actualList != null && actualList.size() > 0) {
					for (FundReceiptActual actual : actualList) {
						actualDao.delete(actual);
					}
				}
			}
			
			return fundAppropriation;
		} else {
			return null;
		}
	}
	
	public  Integer getPeriods(){
		Integer periods = dao.getMaxPeriods();
		if(periods == null){
			periods = 1;
		}else{
			periods =  periods + 1;
		}
		return periods;
	}
	
	/**
	 * 分页表格展示数据.
	 * @param jqGrid 表格请求参数
	 * @param fundAppropriation 资金拨付对象
	 * @param user 用户
	 * @return List<FundAppropriation>
	 */
	public List<FundAppropriation> getGrid(JqGrid jqGrid, FundAppropriation fundAppropriation, UserProfile user) {
		List<FundAppropriation> list = dao.findPage(jqGrid, fundAppropriation, user);

		return list;
	}
	
	public List<FundReceipt> getBudgetIncomeGrid(JqGrid jqGrid, FundReceipt fundReceipt,UserProfile user,String fundId) {
		return fundReceiptDao.findPage(jqGrid, fundReceipt,user,fundId);
	}
	
	public List<FundReceiptActual> getIncomeActualGrid(JqGrid jqGrid, FundReceiptActual receiptActual,UserProfile user,String fundId) {
		return actualDao.findActualPage(jqGrid, receiptActual,user,fundId);
	}
	
	public List<FundBudgetExpend> getExpendGrid(JqGrid jqGrid, FundBudgetExpend expend,UserProfile user,String fundId,String isContract) {
		return expendDao.findPage(jqGrid, expend,user,fundId,isContract);
	}

	public FundAppropriation get(String id) {
		return dao.get(id);
	}
	
	/**
	 * 批量启动流程
	 * @param creatorId 创建者ID
	 */
	public void startProcBatch(String creatorId) {
		List<FundAppropriationSwf> swfs = swfDao.getNotStartProcList();
		if (swfs != null && swfs.size() > 0) {
			int size = swfs.size();
			final String CNEEC_SPOT_FUND = PmSwfConstant.PM_SWF_PROC_INNER_NAME_FUND;
			for (int i = 0; i < size; i++) {
				FundAppropriationSwf swf = swfs.get(i);
				swf.setFund(this.get(swf.getFundId()));
				try {
					PmSwfUtil.startProc(swf, creatorId, CNEEC_SPOT_FUND);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
