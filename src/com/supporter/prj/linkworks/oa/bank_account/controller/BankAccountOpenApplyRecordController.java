package com.supporter.prj.linkworks.oa.bank_account.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApplyRecord;
import com.supporter.prj.linkworks.oa.bank_account.service.BankAccountOpenApplyRecordService;
import com.supporter.prj.linkworks.oa.bank_account.util.BankAccountConstant;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("oa/bankAccountOpenApplyRec")
public class BankAccountOpenApplyRecordController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private BankAccountOpenApplyRecordService bankAccountOpenApplyRecordService; 

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, BankAccountOpenApplyRecord bankAccountOpenApplyRecord) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.bankAccountOpenApplyRecordService.getGrid(user, jqGrid, bankAccountOpenApplyRecord);	
		return jqGrid;
	}
    

	/**
	 * 根据主键获取功能模块�?.
	 * 
	 * @param bankAccountOpenApplyRecordId 主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody BankAccountOpenApplyRecord get(String bankAccountOpenApplyRecordId) {
		BankAccountOpenApplyRecord bankAccountOpenApplyRecord = bankAccountOpenApplyRecordService.get(bankAccountOpenApplyRecordId);
		return bankAccountOpenApplyRecord;

	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param bankAccountOpenApplyRecordId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody BankAccountOpenApplyRecord initEditOrViewPage(HttpServletRequest request,JqGridReq jqGridReq,String applyId,String recordId) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		BankAccountOpenApplyRecord entity = bankAccountOpenApplyRecordService.initEditOrViewPage(jqGrid,applyId,recordId,this.getUserProfile());
		return entity;
	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param bankAccountOpenApplyRecordId 主键
	 * @return
	 */
	@RequestMapping("viewPage")
	@ResponseBody
	public BankAccountOpenApplyRecord viewPage(String bankAccountOpenApplyRecordId) {
		BankAccountOpenApplyRecord entity = bankAccountOpenApplyRecordService.viewPage(bankAccountOpenApplyRecordId,this.getUserProfile());
		return entity;
	}
	
	
	
	
	
	
	
	/**
	 * 保存或更新数据
	 * 
	 * @param bankAccountOpenApplyRecord 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<BankAccountOpenApplyRecord> saveOrUpdate(BankAccountOpenApplyRecord bankAccountOpenApplyRecord,String notifierIds,String notifierNames,String examIds,String examNames) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(BankAccountOpenApplyRecord.class);		
		BankAccountOpenApplyRecord entity = this.bankAccountOpenApplyRecordService.saveOrUpdate(user,bankAccountOpenApplyRecord,valueMap);
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
	OperResult<BankAccountOpenApplyRecord> commit(BankAccountOpenApplyRecord bankAccountOpenApplyRecord) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this
				.getPropValues(BankAccountOpenApplyRecord.class);
		BankAccountOpenApplyRecord entity = this.bankAccountOpenApplyRecordService.commit(user,
				bankAccountOpenApplyRecord, valueMap);
//		return OperResult.succeed(SalaryConstant.I18nKey.SAVE_SUCCESS, null, null);
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
		this.bankAccountOpenApplyRecordService.delete(user, applyIds);
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
