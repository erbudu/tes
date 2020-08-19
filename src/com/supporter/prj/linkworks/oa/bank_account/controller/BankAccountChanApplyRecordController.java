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
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountChanApplyRecord;
import com.supporter.prj.linkworks.oa.bank_account.service.BankAccountChanApplyRecordService;
import com.supporter.prj.linkworks.oa.bank_account.util.BankAccountConstant;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("oa/bankAccountChanApplyRec")
public class BankAccountChanApplyRecordController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private BankAccountChanApplyRecordService bankAccountChanApplyRecordService; 

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, BankAccountChanApplyRecord bankAccountChanApplyRecord) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.bankAccountChanApplyRecordService.getGrid(user, jqGrid, bankAccountChanApplyRecord);	
		return jqGrid;
	}
    

	/**
	 * 根据主键获取功能模块�?.
	 * 
	 * @param bankAccountChanApplyRecordId 主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody BankAccountChanApplyRecord get(String bankAccountChanApplyRecordId) {
		BankAccountChanApplyRecord bankAccountChanApplyRecord = bankAccountChanApplyRecordService.get(bankAccountChanApplyRecordId);
		return bankAccountChanApplyRecord;

	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param bankAccountChanApplyRecordId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody BankAccountChanApplyRecord initEditOrViewPage(HttpServletRequest request,JqGridReq jqGridReq,String changeApplyId) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		BankAccountChanApplyRecord entity = bankAccountChanApplyRecordService.initEditOrViewPage(jqGrid,changeApplyId,this.getUserProfile());
		return entity;
	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param bankAccountChanApplyRecordId 主键
	 * @return
	 */
	@RequestMapping("viewPage")
	@ResponseBody
	public BankAccountChanApplyRecord viewPage(String bankAccountChanApplyRecordId) {
		BankAccountChanApplyRecord entity = bankAccountChanApplyRecordService.viewPage(bankAccountChanApplyRecordId,this.getUserProfile());
		return entity;
	}
	
	
	
	
	
	
	
	/**
	 * 保存或更新数据
	 * 
	 * @param bankAccountChanApplyRecord 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<BankAccountChanApplyRecord> saveOrUpdate(BankAccountChanApplyRecord bankAccountChanApplyRecord,String notifierIds,String notifierNames,String examIds,String examNames) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(BankAccountChanApplyRecord.class);		
		BankAccountChanApplyRecord entity = this.bankAccountChanApplyRecordService.saveOrUpdate(user,bankAccountChanApplyRecord,valueMap);
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
	OperResult<BankAccountChanApplyRecord> commit(BankAccountChanApplyRecord bankAccountChanApplyRecord) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this
				.getPropValues(BankAccountChanApplyRecord.class);
		BankAccountChanApplyRecord entity = this.bankAccountChanApplyRecordService.commit(user,
				bankAccountChanApplyRecord, valueMap);
//		return OperResult.succeed(SalaryConstant.I18nKey.SAVE_SUCCESS, null, null);
		return OperResult.succeed("saveSuccess", null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param changeApplyIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String changeApplyIds) {
		UserProfile user = this.getUserProfile();
		this.bankAccountChanApplyRecordService.delete(user, changeApplyIds);
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
//		Map<Integer, String> map = BankAccountChanApplyRecord.getModifiedMap();
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
	Map<String, String> selectAccountNatureData() {
		return BankAccountConstant.getAccountNatureOfOpenMap();
	}
	
	
	/**
	 * 获取字典数据-用于新增编辑页面国别下拉
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/selectNationalityData")
	public Map<String, String> selectNationalityData()
			throws IOException {
		return BankAccountConstant.getNationalityOfOpenMap();
	}

	
	
}
