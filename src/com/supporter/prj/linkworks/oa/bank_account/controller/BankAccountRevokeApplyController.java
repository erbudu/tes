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
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountRevokeApply;
import com.supporter.prj.linkworks.oa.bank_account.service.BankAccountRevokeApplyService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("oa/bankAccountRevokeApply")
public class BankAccountRevokeApplyController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private BankAccountRevokeApplyService bankAccountRevokeApplyService; 

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping("getGrid")
//	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, BankAccountRevokeApply bankAccountRevokeApply) throws Exception  {
//		UserProfile user = this.getUserProfile();
//		JqGrid jqGrid = jqGridReq.initJqGrid(request);
//		this.bankAccountRevokeApplyService.getGrid(user, jqGrid, bankAccountRevokeApply);
//			
//		return jqGrid;
//	}

	
	/**
	 * 根据主键获取功能模块�?.
	 * 
	 * @param bankAccountRevokeApplyId 主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody BankAccountRevokeApply get(String bankAccountRevokeApplyId) {
		BankAccountRevokeApply bankAccountRevokeApply = bankAccountRevokeApplyService.get(bankAccountRevokeApplyId);
		return bankAccountRevokeApply;

	}
	/**
	 * 根据生效表的id获取对应的状态为草稿的变更记录的条数
	 * 
	 * @param bankAccountChangeApplyId 主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getRevokeApplyListByEffectiveId")
	public @ResponseBody int getRevokeApplyListByEffectiveId(String effectiveId) {
		return this.bankAccountRevokeApplyService.getRevokeApplyListByEffectiveId(effectiveId);
	}
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param bankAccountRevokeApplyId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody BankAccountRevokeApply initEditOrViewPage(HttpServletRequest request,JqGridReq jqGridReq,String revokeId,String effectiveId) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		BankAccountRevokeApply entity = bankAccountRevokeApplyService.initEditOrViewPage(jqGrid,revokeId,effectiveId,this.getUserProfile());
		return entity;
	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param bankAccountRevokeApplyId 主键
	 * @return
	 */
	@RequestMapping("viewPage")
	@ResponseBody
	public BankAccountRevokeApply viewPage(String bankAccountRevokeApplyId) {
		BankAccountRevokeApply entity = bankAccountRevokeApplyService.viewPage(bankAccountRevokeApplyId,this.getUserProfile());
		return entity;
	}
	
	/**
	 * 保存或更新数据
	 * 
	 * @param bankAccountRevokeApply 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<BankAccountRevokeApply> saveOrUpdate(BankAccountRevokeApply bankAccountRevokeApply) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(BankAccountRevokeApply.class);		
		BankAccountRevokeApply entity = this.bankAccountRevokeApplyService.saveOrUpdate(user,bankAccountRevokeApply,valueMap);
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
	OperResult<BankAccountRevokeApply> commit(BankAccountRevokeApply bankAccountRevokeApply) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this
				.getPropValues(BankAccountRevokeApply.class);
		BankAccountRevokeApply entity = this.bankAccountRevokeApplyService.commit(user,
				bankAccountRevokeApply, valueMap);
//		return OperResult.succeed(SalaryConstant.I18nKey.SAVE_SUCCESS, null, null);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 保存报CMEC审批结果
	 * @param revokeId
	 * @param cmecResult
	 * @return
	 */
	@RequestMapping("saveCMECResult")
	public @ResponseBody OperResult<BankAccountRevokeApply> saveCMECResult(String revokeId, int cmecResult) {
		BankAccountRevokeApply entity = this.bankAccountRevokeApplyService.saveCMECResult(revokeId, cmecResult);
		return OperResult.succeed("saveSuccess", null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param revokeIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String revokeIds) {
		UserProfile user = this.getUserProfile();
		this.bankAccountRevokeApplyService.delete(user, revokeIds);
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
//		Map<Integer, String> map = BankAccountRevokeApply.getModifiedMap();
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
