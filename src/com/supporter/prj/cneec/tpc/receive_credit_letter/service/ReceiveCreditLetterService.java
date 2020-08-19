package com.supporter.prj.cneec.tpc.receive_credit_letter.service;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.prj_contract_table.service.PrjContractTableService;
import com.supporter.prj.cneec.tpc.receive_credit_letter.dao.ReceiveCreditLetterDao;
import com.supporter.prj.cneec.tpc.receive_credit_letter.entity.ReceiveCreditLetter;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**   
 * @Title: Service
 * @Description: 报告
 * @author liyinfeng
 * @date 2017-7-05 10:25:07
 * @version V1.0   
 *
 */
@Service
@Transactional(TransManager.APP)
public class ReceiveCreditLetterService {
	
	//public static final String CURRENCY_TYPE = "CURRENCY_TYPE";// 币种
	public static final String CURRENCY_CATEGORY = "CURRENCY_CATEGORY";// 币种
	public static final String BUDGET_ITEM = TpcConstant.TPC_BUDGET_ITEM;// 预算项
	public static final String PrjMainContractCostType = "PRJ_BUDGET_TYPE";// 常用的项目评审类型
	
	//合同付款预算项
	public static String ITEM_PRJ_CATEGORY_SETTLEMENT = "ITEM_PRJ_CATEGORY_SETTLEMENT";
	//收入
	public static String ITEM_PRJ_CATEGORY_GATHERING = "ITEM_PRJ_CATEGORY_GATHERING";
	//出口退税
	public static String ITEM_DRAW_BACK = "ITEM_DRAW_BACK";
	//转收乳
	public static String ITEM_PRJ_CATEGORY_ZSL = "ITEM_PRJ_CATEGORY_ZSL";
	/**
	 * 当前的信用证付款序列号（本年度） .
	 */
	@Autowired
	private RegisterProjectService registerProjectService;
	@Autowired
	private ReceiveCreditLetterDao reportDao;
	@Autowired
	private PrjContractTableService prjContractTableService;

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public ReceiveCreditLetter get(String reportId) {
		return reportDao.get(reportId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public ReceiveCreditLetter initEditOrViewPage(String reportId, UserProfile user) {
		ReceiveCreditLetter report;
		if (StringUtils.isBlank(reportId)) {// 新建
			report = newReceiveCreditLetter(user);
			report.setReceiveCreditLetterId(UUIDHex.newId());
		} else {// 编辑
			report = this.get(reportId);
		}
		return report;
	}
	
	/**
	 * 获取本位币
	 * @return
	 */	
	public String getLsCurrencyType(){
		List <IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(CURRENCY_CATEGORY);		
		String lsCurrencyType = "";
		for(IComCodeTableItem item : list){
			if(item.getExtField4().equals("1")){
				lsCurrencyType = item.getDisplayName();
			}
		}
		return lsCurrencyType;
	}
	
	public double getCurrencyExchangeRate(String ls_currency_type_code){
		List <IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(CURRENCY_CATEGORY);		
		String currencyRate = "";
		for(IComCodeTableItem item : list){
			if(item.getExtField3().equals(ls_currency_type_code)){
				currencyRate = item.getExtField2();
			}
		}
		return Double.valueOf(currencyRate);
	}

	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public ReceiveCreditLetter newReceiveCreditLetter(UserProfile ausrprf_U){
        ReceiveCreditLetter lcls_ReceiveCreditLetter = new ReceiveCreditLetter();
		lcls_ReceiveCreditLetter.setReceiveDate(CommonUtil.format(new Date(),"yyyy-MM-dd"));
		
        lcls_ReceiveCreditLetter.setCreatedById(ausrprf_U.getPersonId());
        lcls_ReceiveCreditLetter.setCreatedBy(ausrprf_U.getName());
        lcls_ReceiveCreditLetter.setCreatedDate(CommonUtil.format(new Date(),"yyyy-MM-dd"));
        
        lcls_ReceiveCreditLetter.setModifiedById(ausrprf_U.getPersonId());
        lcls_ReceiveCreditLetter.setModifiedBy(ausrprf_U.getName());
        lcls_ReceiveCreditLetter.setModifiedDate(CommonUtil.format(new Date(),"yyyy-MM-dd"));
        Dept dept = ausrprf_U.getDept();
        if(dept != null){
	        lcls_ReceiveCreditLetter.setDeptId(dept.getDeptId());
	        lcls_ReceiveCreditLetter.setDeptName(dept.getName());
        }
        lcls_ReceiveCreditLetter.setAdd(true);
        lcls_ReceiveCreditLetter.setSwfStatus(ReceiveCreditLetter.DRAFT);
       
        return lcls_ReceiveCreditLetter;
    }
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<ReceiveCreditLetter> getGrid(UserProfile user, JqGrid jqGrid, ReceiveCreditLetter report) {
		List<ReceiveCreditLetter> list = reportDao.findPage(user, jqGrid, report);
		return list;
	}

	/**
	 * 获取收取的信用证
	 * @param projectId
	 * @param contractId
	 * @return
	 */
	public List<ReceiveCreditLetter> getList(String projectId, String contractId) {
		if (StringUtils.isNotBlank(projectId)) {
			if (StringUtils.isNotBlank(contractId)) {
				return reportDao.getCompleteList(projectId, contractId);
			} else {
				return reportDao.findBy("projectId", projectId);
			}
		} else {
			return reportDao.getAllObjects();
		}
	}

	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public OperResult<ReceiveCreditLetter> saveOrUpdate(UserProfile user, ReceiveCreditLetter report, Map< String, Object > valueMap) {
		if (report.isAdd()) {
			this.reportDao.save(report);
		} else {
			report.setModifiedBy(user.getName());
			report.setModifiedById(user.getPersonId());
			report.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.reportDao.update(report);
		}
		// 设置项目经理
		if (StringUtils.isNotBlank(report.getProjectId()) && StringUtils.isBlank(report.getPrjManagerId())) {
			String projectId = report.getProjectId();
			RegisterProject project = this.registerProjectService.get(projectId);
			report.setPrjManagerId(project.getProjectChargeId());
			report.setPrjManager(project.getProjectCharge());
			this.reportDao.update(report);
		}
		return OperResult.succeed("saveSuccess", null, report);
	}
	
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public ReceiveCreditLetter update(ReceiveCreditLetter report) {
		this.reportDao.update(report);
		return report;
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String reportIds) {
		if (StringUtils.isNotBlank(reportIds)) {
			for (String reportId : reportIds.split(",")) {
				this.reportDao.delete(reportId);
			}
			// 记录日志
//			String logMessage = MessageFormat.format(
//					LogConstant.PUBLISH_REPORT_LOG_MESSAGE, reportIds);
//			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
//					user, LogConstant.PUBLISH_REPORT_LOG_ACTION, logMessage,
//					null, null);
		}
	}

	/**
	 * 获取币种下拉列表
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> getCurrencyTypeCodeTable() throws IOException {
		List <IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(CURRENCY_CATEGORY);		
		Map<String, String> map = new LinkedHashMap<String, String>();
		for(IComCodeTableItem item : list){
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}
	/**
	 * 获取币种下拉列表
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> getBudgetItemCodeTable(String prjId) {
		List <IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(BUDGET_ITEM);		
		Map<String, String> map = new LinkedHashMap<String, String>();
		for(IComCodeTableItem item : list){
			if(ITEM_PRJ_CATEGORY_SETTLEMENT.equals(item.getExtField1())){
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;
	}
	/**
	 * 获取币种下拉列表
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> getPrjMainContractCostType(){
		List <IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(PrjMainContractCostType);		
		Map<String, String> map = new LinkedHashMap<String, String>();
		if(list != null){
			for(IComCodeTableItem item : list){
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;
	}

	/**
	 * 根据执行中项目获取采购合同列表(根据供应商id除重).
	 * @param ap_Project 执行中项目实例.
	 * @retrun List 合同集包括（采购合同）,每一项都是一个合同实例.
	 */
	public Map<String, String> getSubPrjContracts(String projectId, UserProfile userProfile) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (CommonUtil.trim(projectId).length() > 0) {
			map = this.prjContractTableService.getPurchaseContractByPrjId(projectId, userProfile);
		}
		return map;
	}

}
