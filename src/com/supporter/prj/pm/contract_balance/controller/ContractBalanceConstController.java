package com.supporter.prj.pm.contract_balance.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.supporter.prj.pm.contract_balance.service.ContractBalanceConstService;
import com.supporter.prj.pm.contract_balance.dao.ContractBalanceConstSwfDao;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceConst;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceConstSwf;
import com.supporter.prj.cneec.cxf.JsonDateValueProcessor;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;


/**   
 * @Title: 施工合同结算
 * @Description: PM_CONTRACT_BALANCE_CONST.
 * @author Administrator
 * @date 2018-07-04 18:07:38
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("pm/contract_balance/constructionBalance")
public class ContractBalanceConstController extends AbstractController {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ContractBalanceConstService balanceService;
	@Autowired
	private ContractBalanceConstSwfDao swfDao;
	
	/**
	 * 分页表格展示数据.
	 * @param request 请求对象
	 * @param jqGridReq 表格参数
	 * @param balance 结算对象
	 * @return JqGrid
	 * @throws Exception 异常
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("getGrid")
	@ResponseBody
	public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq,
			ContractBalanceConst balance) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		List <ContractBalanceConst> list = this.balanceService.getGrid(user, jqGrid, balance);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		if (jsonArray != null && jsonArray.size() > 0) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				ContractBalanceConstSwf swf = swfDao.get(jsonObj.getString("balanceId"));
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
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * @param balanceId 结算ID
	 * @param contractId 合同ID
	 * @return ContractBalanceConst
	 */
	@RequestMapping("initEditOrViewPage")
	@ResponseBody
	public Map <String, Object> initEditOrViewPage(String balanceId, String contractId) {
		//UserProfile user, String balanceId, String contractId,
		//Date periodStartDate, Date periodEndDate, String calculationRate, String isFinalBalance,String visaIds
		ContractBalanceConst entity = balanceService.initEditOrViewPage(getUserProfile(),
				balanceId, contractId, null, null, "", "", "");

		ContractBalanceConstSwf swf = swfDao.get(entity.getBalanceId());
		swf.setBalance(entity);
		
		Map <String, Object> map = new HashMap <String, Object>();
		map.put("entity", entity);
		map.put("swf", swf);
		return map;
	}
	
	/**
	 * 判断指定字段唯一性
	 * 
	 * @param balanceId 结算ID
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return Boolean
	 */
	@RequestMapping("checkPropertyUniquenes")
	@ResponseBody
	public Boolean checkPropertyUniquenes(String balanceId, String propertyName, String propertyValue) {
		return this.balanceService.checkPropertyUniquenes(balanceId, propertyName, propertyValue);
	}
	
}
