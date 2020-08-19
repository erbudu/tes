package com.supporter.prj.linkworks.oa.bank_account.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApply;
import com.supporter.prj.linkworks.oa.bank_account.service.BankAccountOpenApplyService;
import com.supporter.prj.linkworks.oa.bank_account.util.BankAccountConstant;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("oa/bankAccountOpenApply")
public class BankAccountOpenApplyController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private BankAccountOpenApplyService bankAccountOpenApplyService; 

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, BankAccountOpenApply bankAccountOpenApply) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.bankAccountOpenApplyService.getGrid(user, jqGrid, bankAccountOpenApply);		
		return jqGrid;
	}
    

	/**
	 * 根据主键获取功能模块�?.
	 * 
	 * @param bankAccountOpenApplyId 主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody BankAccountOpenApply get(String bankAccountOpenApplyId) {
		BankAccountOpenApply bankAccountOpenApply = bankAccountOpenApplyService.get(bankAccountOpenApplyId);
		return bankAccountOpenApply;

	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param bankAccountOpenApplyId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody BankAccountOpenApply initEditOrViewPage(HttpServletRequest request,JqGridReq jqGridReq,String applyId) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		BankAccountOpenApply entity = bankAccountOpenApplyService.initEditOrViewPage(jqGrid,applyId,this.getUserProfile());
		return entity;
	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param bankAccountOpenApplyId 主键
	 * @return
	 */
	@RequestMapping("viewPage")
	@ResponseBody
	public BankAccountOpenApply viewPage(String bankAccountOpenApplyId) {
		BankAccountOpenApply entity = bankAccountOpenApplyService.viewPage(bankAccountOpenApplyId,this.getUserProfile());
		return entity;
	}
	
	
	
	
	
	
	
	/**
	 * 保存或更新数据
	 * 
	 * @param bankAccountOpenApply 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<BankAccountOpenApply> saveOrUpdate(BankAccountOpenApply bankAccountOpenApply,String notifierIds,String notifierNames,String examIds,String examNames) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(BankAccountOpenApply.class);		
		BankAccountOpenApply entity = this.bankAccountOpenApplyService.saveOrUpdate(user,bankAccountOpenApply,valueMap);
//		return OperResult.succeed(SalaryConstant.I18nKey.SAVE_SUCCESS, null, null);
		return OperResult.succeed("saveSuccess", null, entity);
	}
		
	
	/**
	 * 保存提交.
	 * 
	 * @param apply
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<BankAccountOpenApply> commit(BankAccountOpenApply bankAccountOpenApply) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this
				.getPropValues(BankAccountOpenApply.class);
		BankAccountOpenApply entity = this.bankAccountOpenApplyService.commit(user,
				bankAccountOpenApply, valueMap);
//		return OperResult.succeed(SalaryConstant.I18nKey.SAVE_SUCCESS, null, null);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 保存报CMEC审批结果
	 * @param applyId
	 * @param cmecResult
	 * @return
	 */
	@RequestMapping("saveCMECResult")
	public @ResponseBody OperResult<BankAccountOpenApply> saveCMECResult(String applyId, int cmecResult) {
		BankAccountOpenApply entity = this.bankAccountOpenApplyService.saveCMECResult(applyId, cmecResult);
		return OperResult.succeed("saveSuccess", null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param applyIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String applyIds) {
		UserProfile user = this.getUserProfile();
		this.bankAccountOpenApplyService.delete(user, applyIds);
//		return OperResult.succeed(SalaryConstant.I18nKey.DELETE_SUCCESS, null, null);
		return OperResult.succeed("deleteSuccess", null, null);
	}
	

	
	/**
	 * 获取字典数据-用于新增编辑页面账户单位性质下拉
	 * 
	 * @param key
	 * @throws IOException
	 */
	@RequestMapping(value = "/selectAccountPropertyData")
	public @ResponseBody
	Map<String, String> selectAccountPropertyData() {
		return BankAccountConstant.getAccountPropertyOfOpenMap();
	}
	
	/**
	 * 获取字典数据-用于新增编辑页面账户性质下拉
	 * 
	 * @param key
	 * @throws IOException
	 */
	@RequestMapping(value = "/selectAccountNatureData")
	public @ResponseBody
	Map<String, String> selectAccountNatureData(String accountCurrencyId) {
		//return BankAccountConstant.getAccountNatureOfOpenMap();
		Map<String, String> map = new LinkedHashMap<String, String>();
		if(!accountCurrencyId.equals("C-001")){
			map.put("YB_ACCOUNT", "一般账户");
			map.put("BZJ_ACCOUNT", "保证金账户");
			map.put("DHC_ACCOUNT", "待核查账户");
			map.put("GG_ACCOUNT", "共管账户");
			map.put("XH_ACCOUNT", "现汇账户");
			map.put("XH_ACCOUNT", "离岸账户");
		}else{
			map.put("YB_ACCOUNT", "一般账户");
			map.put("BZJ_ACCOUNT", "保证金账户");
			map.put("GG_ACCOUNT", "共管账户");
			map.put("XH_ACCOUNT", "离岸账户");
		}
		return map;
	}
	
	
	/**
	 * 获取字典数据-用于新增编辑页面国别下拉
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/selectNationalityData")
	public List<JSONObject> selectNationalityData()
			throws IOException {
		Map<String, String> map= BankAccountConstant.getNationalityOfOpenMap();
		List<JSONObject> list = new ArrayList<JSONObject>();
		for (Map.Entry<String, String> e : map.entrySet()) {
			String str = "{\"id\":\"" + e.getKey() + "\",\"text\":\"" + e.getValue() + "\"}";
			JSONObject json = JSONObject.fromObject(str);
			list.add(json);
		}
		return list;		
	}
	
	/**
	 * 获取字典数据-用于新增编辑页面账户币别下拉
	 * 
	 * @param key
	 * @throws IOException
	 */
	@RequestMapping(value = "/selectCurrencyMap")
	public @ResponseBody
	Map<String, String> getCurrencyMap() {
		return BankAccountConstant.getCurrencyMap();
	}
		

	/**
	 * 验证在当前申请单位、当前银行、当前币别是否已经存在该账户(账号)
	 * @return
	 */
	@RequestMapping("checkAccountByDeptBankCurrency")
	public @ResponseBody String checkAccountByDeptBankCurrency(BankAccountOpenApply bankAccountOpenApply) {
		String check=this.bankAccountOpenApplyService.checkAccountByDeptBankCurrency(bankAccountOpenApply);
		return check;
	}

	
	/**
	 * 获取字典数据-用于列表页面状态下拉
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/selectStatusOfListData")
	public Map<Integer, String> selectStatusOfListData()
			throws IOException {
		return BankAccountOpenApply.getStatusCodeTable();
	}
	
	/**
	 * 获取字典数据-用于新增编辑页面开户银行一级名称下拉
	 * 
	 * @param key
	 * @throws IOException
	 */
	@RequestMapping(value = "/selectOpenBankFirstNameMap")
	public @ResponseBody
	Map<String, String> selectOpenBankFirstNameMap() {
		return BankAccountConstant.getOpenBankFirstNameMap();
	}
	
	
	/**
	 * 获取字典数据-用于新增编辑页面账户属性下拉
	 * 
	 * @param key
	 * @throws IOException
	 */
	@RequestMapping(value = "/selectAccountProMap")
	public @ResponseBody
	Map<String, String> selectAccountProMap() {
		return BankAccountConstant.getAccountProMap();
	}
	
	
}
