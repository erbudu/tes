package com.supporter.prj.linkworks.oa.bank_account.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankCooperativeCont;
import com.supporter.prj.linkworks.oa.bank_account.service.BankCooperativeContService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("oa/bankCooperative")
public class BankCooperativeController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private BankCooperativeContService bankCooperativeContService; 

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, BankCooperativeCont bankCooperativeCont) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.bankCooperativeContService.getGrid(user, jqGrid, bankCooperativeCont);
			
		return jqGrid;
	}

	
	/**
	 * 根据主键获取功能模块�?.
	 * 
	 * @param bankCooperativeContId 主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody BankCooperativeCont get(String cooperativeId) {
		BankCooperativeCont bankCooperativeCont = bankCooperativeContService.get(cooperativeId);
		return bankCooperativeCont;
	}
	
	/**
	 * 根据主键获去银行地址.
	 * 
	 * @param bankCooperativeContId 主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getAddressOfBank")
	public @ResponseBody String getAddressOfBank(String cooperativeId) {
		String addressOfBank = bankCooperativeContService.getAddressOfBank(cooperativeId);
		return addressOfBank;
	}	
	
	

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param bankCooperativeContId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody BankCooperativeCont initEditOrViewPage(HttpServletRequest request,JqGridReq jqGridReq,String cooperativeId) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		BankCooperativeCont entity = bankCooperativeContService.initEditOrViewPage(jqGrid,cooperativeId,this.getUserProfile());
		return entity;
	}

	
	/**
	 * 保存或更新数据
	 * 
	 * @param bankCooperativeCont 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<BankCooperativeCont> saveOrUpdate(BankCooperativeCont bankCooperativeCont) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(BankCooperativeCont.class);		
		BankCooperativeCont entity = this.bankCooperativeContService.saveOrUpdate(user,bankCooperativeCont,valueMap);
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
	OperResult<BankCooperativeCont> commit(BankCooperativeCont bankCooperativeCont) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this
				.getPropValues(BankCooperativeCont.class);
		BankCooperativeCont entity = this.bankCooperativeContService.commit(user,
				bankCooperativeCont, valueMap);
//		return OperResult.succeed(SalaryConstant.I18nKey.SAVE_SUCCESS, null, null);
		return OperResult.succeed("saveSuccess", null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param cooperativeIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String cooperativeIds) {
		UserProfile user = this.getUserProfile();
		this.bankCooperativeContService.delete(user, cooperativeIds);
//		return OperResult.succeed(SalaryConstant.I18nKey.DELETE_SUCCESS, null, null);
		return OperResult.succeed("deleteSuccess", null, null);
	}
	
	/**
	 * 获取字典数据-用于高级查询页面的下拉显示
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getSwfCodetable")
	public Map<String, String> getSwfCodetable()
			throws IOException {
//		Map<Integer, String> map = BankCooperativeCont.getModifiedMap();
//		return map;
		
    	List <IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems("SWF-INFO");
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("", "");
		for(IComCodeTableItem item : list){
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
		
	}
	
	/**
	 * 获取字典数据-部门资源的下拉列表获取资源类型为文章的部门资源（文章栏菜单编辑页面用到，这是获取的“canWrite”也就是拥有编辑权限的）
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getCooperationOfBankCodetable")
	public Map<String, String> getCooperationOfBankCodetable()
		throws IOException {		
	Map<String, String> map = new LinkedHashMap<String, String>();
	List<BankCooperativeCont> list = bankCooperativeContService.findBankCooperativeContList();
	map.put("", "--请选择--");	
	if(list!=null){
			if(list.size()>0){
				for (BankCooperativeCont BankCooperativeCont : list) {
					
					String openingBankId = BankCooperativeCont.getCooperativeId();
					String openingBank = BankCooperativeCont.getBankAccountName();
					map.put(openingBankId, openingBank);
				}
			}
		}	
	return map;
		
	}

	
	
	
	
}
