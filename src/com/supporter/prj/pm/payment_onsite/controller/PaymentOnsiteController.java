package com.supporter.prj.pm.payment_onsite.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.cxf.JsonDateValueProcessor;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.PmConstant;
import com.supporter.prj.pm.payment_onsite.dao.PaymentOnsiteSwfDao;
import com.supporter.prj.pm.payment_onsite.entity.PaymentOnsite;
import com.supporter.prj.pm.payment_onsite.entity.PaymentOnsiteSwf;
import com.supporter.prj.pm.payment_onsite.service.PaymentOnsiteService;
import com.supporter.prj.pm.procure_contract.entity.ProcureContract;
import com.supporter.prj.pm.procure_contract.service.ProcureContractService;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("pm/payment_onsite/payment")
public class PaymentOnsiteController extends AbstractController {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PaymentOnsiteService paymentOnsiteService;
	@Autowired
	private PaymentOnsiteSwfDao swfDao;
	@Autowired
	private ProcureContractService procureContractService;
	
	/**
	 * 根据主键获取功能模块
	 * @param id
	 */
	@RequestMapping("get")
	public @ResponseBody PaymentOnsite get(String id){	
		PaymentOnsite paymentOnsite=paymentOnsiteService.get(id);
		return paymentOnsite;		
	}
	
	/**
	 * 初始化生成ID
	 */
	@RequestMapping("initEditToId")
	public @ResponseBody
	String initEditToId() {
		return com.supporter.util.UUIDHex.newId();
	}
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param id 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	@ResponseBody
	public Map<String, Object> initEditOrViewPage(String id) {
		UserProfile user = this.getUserProfile();
		PaymentOnsite entity = paymentOnsiteService.initEditOrViewPage(id, user);
		PaymentOnsiteSwf swf = swfDao.get(entity.getId());
		swf.setPaymentOnsite(entity);

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
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, PaymentOnsite paymentOnsite) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		List <PaymentOnsite> list = paymentOnsiteService.getGrid(jqGrid, paymentOnsite, user);
		if (CollectionUtils.isNotEmpty(list)) {
			DecimalFormat df = new DecimalFormat("#");
			for (int i = 0; i < list.size(); i++) {
				PaymentOnsite onsite = list.get(i);
				if (StringUtils.isNotBlank(onsite.getContractId())) {
					ProcureContract pContract = procureContractService.get(onsite.getContractId());
					if (pContract != null) {
						if (pContract.getIsDoubleCoin() == 0) { //单币别
							onsite.setCurreyStr(onsite.getCurrency());
							onsite.setAmountStr(df.format(onsite.getPaymentAmount()));
						} else {
							onsite.setCurreyStr(onsite.getCurrency() + "/" + onsite.getCurrencyTwo()); //将签约币别拼接起来
							onsite.setAmountStr(df.format(onsite.getPaymentAmount()) + "/" + df.format(onsite.getPaymentAmountTwo())); //将签约金额拼接起来
						}
					}
				} else {
					onsite.setCurreyStr(onsite.getCurrency());
					onsite.setAmountStr(df.format(onsite.getBorrowAmount()));
				}
			}
		}
		
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		if (jsonArray != null && jsonArray.size() > 0) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				PaymentOnsiteSwf swf = swfDao.get(jsonObj.getString("id"));
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
	 * 分页表格展示数据.--实际支付列表
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGridToActual")
	public @ResponseBody JqGrid getGridToActual(HttpServletRequest request, JqGridReq jqGridReq, PaymentOnsite paymentOnsite) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.paymentOnsiteService.getGridToActual( jqGrid, paymentOnsite,user);
		
		List<PaymentOnsite> applyRows = jqGrid.getRows();
		List<PaymentOnsite> applys = new ArrayList<PaymentOnsite>();
		DecimalFormat df = new DecimalFormat("#");  
		if (applyRows.size() > 0) {
			for (PaymentOnsite onsite : applyRows) {
				if (onsite == null) {
					continue;
				}
				if(onsite.getContractId() != null && !onsite.getContractId().equals("")) {
					ProcureContract pContract = procureContractService.get(onsite.getContractId());
					if (pContract != null) {
						if(pContract.getIsDoubleCoin()==0){//单币别
							onsite.setCurreyStr(onsite.getCurrency());
							onsite.setAmountStr(df.format(onsite.getPaymentAmount()));
						}else{
							onsite.setCurreyStr(onsite.getCurrency()+"/"+onsite.getCurrencyTwo());//将签约币别拼接起来
							onsite.setAmountStr(df.format(onsite.getPaymentAmount())+"/"+df.format(onsite.getPaymentAmountTwo()));//将签约金额拼接起来
						}
					}
				}else {
					onsite.setCurreyStr(onsite.getCurrency());
					onsite.setAmountStr(df.format(onsite.getBorrowAmount()));
				}
				applys.add(onsite);
			}
		}
		jqGrid.setRows(applys);
		return jqGrid;
	}
	
	@RequestMapping("getContractGrid")
	public @ResponseBody JqGrid getContractGrid(HttpServletRequest request, JqGridReq jqGridReq, PaymentOnsite paymentOnsite) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.paymentOnsiteService.getContractGrid( jqGrid, paymentOnsite,user);
		return jqGrid;
	}
	
	@RequestMapping("getSettlementGrid")
	public @ResponseBody JqGrid getSettlementGrid(HttpServletRequest request, JqGridReq jqGridReq, 
			PaymentOnsite paymentOnsite,String contractId) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.paymentOnsiteService.getSettlementGrid( jqGrid, paymentOnsite,user,contractId);
		return jqGrid;
	}
	
	/**
	 * 分页表格展示数据.--用于选择控件
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGridToWidget")
	public @ResponseBody JqGrid getGridToWidget(HttpServletRequest request, JqGridReq jqGridReq, PaymentOnsite paymentOnsite) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.paymentOnsiteService.getGridToWidget( jqGrid, paymentOnsite,user);
		return jqGrid;
	}
	
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGridByContractId")
	public @ResponseBody JqGrid getGridByContractId(HttpServletRequest request, JqGridReq jqGridReq, PaymentOnsite paymentOnsite) throws Exception  {
		UserProfile user = this.getUserProfile();
		String id = CommonUtil.trim(this.getRequestPara("id"));
		PaymentOnsite onsite = paymentOnsiteService.get(id);
		String contractId = "";
		if(onsite != null) {
			contractId = onsite.getContractId();
		}
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.paymentOnsiteService.getGridByContractId( jqGrid, paymentOnsite,contractId,id,user);
		return jqGrid;
	}
	
	//采购地点
	@RequestMapping("getAddress")
	public @ResponseBody
	Map<Integer, String> getAddress(){
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(0, "总部采购");
		map.put(1, "现场采购");
		return map;
	}
	
	//支付类型
	@RequestMapping("getPaymentType")
	public @ResponseBody
	Map<Integer, String> getPaymentType(){
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(0, "境内");
		map.put(1, "境外");
		return map;
	}
	
	//付款条款
	@RequestMapping("getPaymentClause")
	public @ResponseBody
	Map<String, String> getPaymentClause(){
		return PmConstant.getPaymentClause();
	}

	//款项用途
	@RequestMapping("getPurpose")
	public @ResponseBody
	Map<Integer, String> getPurpose(){
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(0, "支付合同款");
		map.put(1, "零星采购款");
		map.put(2, "代付款");
		return map;
	}
	
	//款项用途
	@RequestMapping("getPurpose1")
	public @ResponseBody
	Map<Integer, String> getPurpose1(){
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(0, "生活费");
		map.put(1, "个人借款");
		map.put(2, "预支津贴");
		return map;
	}
	
	//付款性质
	@RequestMapping("getPaymentNature")
	public @ResponseBody
	Map<Integer, String> getPaymentNature(){
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(0, "合同项下付款");
		map.put(1, "现场管理费支出");
		return map;
	}
	
	// 币别
	@RequestMapping("/getCurrencyTable")
	public @ResponseBody Map<String, String> getCurrencyTable() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems("CNEEC_CURRENCY");
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			if (item == null) {
				continue;
			}
			map.put(item.getItemId(), item.getItemValue());
		}
		return map;
	}


}
