package com.supporter.prj.pm.fund_appropriation.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.cxf.JsonDateValueProcessor;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.com_codetable.dao.CodetableItemDao;
import com.supporter.prj.eip.com_codetable.entity.CodetableItem;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.PmConstant;
import com.supporter.prj.pm.fund_appropriation.dao.FundAppropriationDao;
import com.supporter.prj.pm.fund_appropriation.dao.FundAppropriationSwfDao;
import com.supporter.prj.pm.fund_appropriation.entity.FundAppropriation;
import com.supporter.prj.pm.fund_appropriation.entity.FundAppropriationSwf;
import com.supporter.prj.pm.fund_appropriation.entity.FundBudgetExpend;
import com.supporter.prj.pm.fund_appropriation.entity.FundReceipt;
import com.supporter.prj.pm.fund_appropriation.entity.FundReceiptActual;
import com.supporter.prj.pm.fund_appropriation.service.FundAppropriationService;
import com.supporter.spring_mvc.AbstractController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("pm/fund_appropriation/appropriation")
public class FundAppropriationController extends AbstractController {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private FundAppropriationService service;	
	@Autowired
	private CodetableItemDao codetableItemDao;
	@Autowired
	private FundAppropriationDao fundDao;
	@Autowired
	private FundAppropriationSwfDao swfDao;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param id 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	@ResponseBody
	public Map<String, Object> initEditOrViewPage(String lastFundId, String fundId, Date startDate, Date endDate) {
		UserProfile user = this.getUserProfile();
		FundAppropriation entity = service.initEditOrViewPage(lastFundId, fundId, user, startDate, endDate);
		FundAppropriationSwf swf = swfDao.get(entity.getFundId());
		swf.setFund(entity);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", entity);
		map.put("swf", swf);
		return map;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, FundAppropriation fundAppropriation) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		List <FundAppropriation> list = this.service.getGrid(jqGrid, fundAppropriation, user);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		if (jsonArray != null && jsonArray.size() > 0) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				FundAppropriationSwf swf = swfDao.get(jsonObj.getString("fundId"));
				if (swf != null) {
					jsonObj.put("oaProcId", swf.getOaProcId());
					jsonObj.put("oaExamStatusDesc", swf.getOaExamStatusDesc());
				} else {
					jsonObj.put("oaProcId", "");
					jsonObj.put("oaExamStatusDesc", "");
				}
			}
			jqGrid.setRows(jsonArray);
		}
		return jqGrid;
	}	
	
	@RequestMapping("getBudgetIncomeGrid")
	public @ResponseBody JqGrid getBudgetIncomeGrid(HttpServletRequest request, JqGridReq jqGridReq, 
			FundReceipt fundReceipt,String fundId) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.service.getBudgetIncomeGrid( jqGrid, fundReceipt,user,fundId);
		return jqGrid;
	}	
	
	@RequestMapping("getIncomeActualGrid")
	public @ResponseBody JqGrid getIncomeActualGrid(HttpServletRequest request, JqGridReq jqGridReq, 
			FundReceiptActual receiptActual,String fundId) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.service.getIncomeActualGrid( jqGrid, receiptActual,user,fundId);
		return jqGrid;
	}	
	
	@RequestMapping("getExpendGrid")
	public @ResponseBody JqGrid getExpendGrid(HttpServletRequest request, JqGridReq jqGridReq,
			FundBudgetExpend other,String fundId,String isContract) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.service.getExpendGrid(jqGrid, other,user,fundId,isContract);
		return jqGrid;
	}	
	

	@RequestMapping("getFundProperty")
	public @ResponseBody
	Map<Integer, String> getFundProperty(){
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(0, "现场支付无需拨款");
		map.put(1, "现场支付需要拨款");
		return map;
	}
	
	//币别
	@RequestMapping("getCurrency")
	public @ResponseBody
	Map<String, String> getCurrency(){
		return PmConstant.getCurrencyTable();
	}
	
	@RequestMapping("getCostAccount")
	public @ResponseBody
	Map<Integer, String> getCostAccount(){
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(0, "合同款");
		map.put(1, "总部拨款");
		map.put(2, "其他收入");
		return map;
	}
	
	@RequestMapping("getIsContract")
	public @ResponseBody
	Map<Integer, String> getIsContract(){
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(0, "有");
		map.put(1, "无");
		return map;
	}
	
	@RequestMapping("getRateByCurrency")
	public @ResponseBody double getRateByCurrency(String currency){
		String hql = "from CodetableItem where codetableItemId = ? and codetableId='CNEEC_CURRENCY'";
		List<CodetableItem> areaItemList = codetableItemDao.find(hql,currency);
		if(areaItemList != null && areaItemList.size()>0){
			return Double.parseDouble(areaItemList.get(0).getExtField1());
		}else{
			return 0;
		}
	}
	
	@RequestMapping("getLastFundId")
	public @ResponseBody String getLastFundId(Integer budgetPeriod){
		String hql = "from FundAppropriation where budgetPeriod = ?";
		List<FundAppropriation> list = fundDao.find(hql,budgetPeriod);
		if(list != null && list.size() > 0){
			return list.get(0).getFundId();
		}else{
			return null;
		}
	}
	
	/**
	 * 保存跟业务相关的流程变量
	 * @param swf 业务流程对象
	 * @return FundAppropriationSwf
	 */
	@RequestMapping("saveSwfVars")
	@ResponseBody
	public FundAppropriationSwf saveSwfVars(FundAppropriationSwf swf) {
		FundAppropriationSwf entity = swfDao.get(swf.getFundId());
		if (entity == null) {
			entity = swf;
			swfDao.save(entity);
		} else {
			this.setPropValues(entity);
			swfDao.update(entity);
		}
		return entity;
	}
	
}
