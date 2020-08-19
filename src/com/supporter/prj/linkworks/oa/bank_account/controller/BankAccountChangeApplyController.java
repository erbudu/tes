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
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountChangeApply;
import com.supporter.prj.linkworks.oa.bank_account.service.BankAccountChangeApplyService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("oa/bankAccountChangeApply")
public class BankAccountChangeApplyController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private BankAccountChangeApplyService bankAccountChangeApplyService; 

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, BankAccountChangeApply bankAccountChangeApply) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.bankAccountChangeApplyService.getGrid(user, jqGrid, bankAccountChangeApply);
			
		return jqGrid;
	}
    

	/**
	 * 根据主键获取功能模块�?.
	 * 
	 * @param bankAccountChangeApplyId 主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody BankAccountChangeApply get(String bankAccountChangeApplyId) {
		BankAccountChangeApply bankAccountChangeApply = bankAccountChangeApplyService.get(bankAccountChangeApplyId);
		return bankAccountChangeApply;

	}
	/**
	 * 根据生效表的id获取对应的状态为草稿的变更记录的条数
	 * 
	 * @param bankAccountChangeApplyId 主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getChangeApplyListByEffectiveId")
	public @ResponseBody int getChangeApplyListByEffectiveId(String effectiveId) {
		return this.bankAccountChangeApplyService.getChangeApplyListByEffectiveId(effectiveId);
	}
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param bankAccountChangeApplyId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody BankAccountChangeApply initEditOrViewPage(HttpServletRequest request,JqGridReq jqGridReq,String changeApplyId,String effectiveId) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		BankAccountChangeApply entity = bankAccountChangeApplyService.initEditOrViewPage(jqGrid,changeApplyId,effectiveId,this.getUserProfile());
		return entity;
	}

	/**
	 * 保存或更新数据
	 * 
	 * @param bankAccountChangeApply 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<BankAccountChangeApply> saveOrUpdate(BankAccountChangeApply bankAccountChangeApply) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(BankAccountChangeApply.class);		
		BankAccountChangeApply entity = this.bankAccountChangeApplyService.saveOrUpdate(user,bankAccountChangeApply,valueMap);
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
	OperResult<BankAccountChangeApply> commit(BankAccountChangeApply bankAccountChangeApply) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this
				.getPropValues(BankAccountChangeApply.class);
		BankAccountChangeApply entity = this.bankAccountChangeApplyService.commit(user,
				bankAccountChangeApply, valueMap);
//		return OperResult.succeed(SalaryConstant.I18nKey.SAVE_SUCCESS, null, null);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 保存报CMEC审批结果
	 * @param changeApplyId
	 * @param cmecResult
	 * @return
	 */
	@RequestMapping("saveCMECResult")
	public @ResponseBody OperResult<BankAccountChangeApply> saveCMECResult(String changeApplyId, int cmecResult) {
		BankAccountChangeApply entity = this.bankAccountChangeApplyService.saveCMECResult(changeApplyId, cmecResult);
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
		this.bankAccountChangeApplyService.delete(user, changeApplyIds);
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
//		Map<Integer, String> map = BankAccountChangeApply.getModifiedMap();
//		return map;
		
    	List <IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems("SWF-INFO");
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("", "");
		for(IComCodeTableItem item : list){
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
		
	}
	


	
	
	
	
}
