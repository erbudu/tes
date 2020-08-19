package com.supporter.prj.cneec.tpc.credit_letter_apply.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.credit_letter_apply.entity.CreditLetterApply;
import com.supporter.prj.cneec.tpc.credit_letter_apply.service.CreditLetterApplyService;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.log.controller.AbstractController;
import com.supporter.util.CommonUtil;

@Controller
@RequestMapping("tpc/creditLetterApply")
public class CreditLetterApplyController extends AbstractController {

	private static final long serialVersionUID = 1L;
	@Autowired
	private CreditLetterApplyService creditLetterApplyService;

	/**
	 * 返回列表
	 * @param request
	 * @param jqGridReq
	 * @param creditLetterApply
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, CreditLetterApply creditLetterApply) throws Exception{
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.creditLetterApplyService.getGrid(user, jqGrid, creditLetterApply);
		return jqGrid;
	}
	
	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param creditLetterId
	 * @param map
	 */
	@ModelAttribute
	public void getSupplier(@RequestParam(value = "creditLetterId", required = false) String creditLetterId, Map<String, Object> map) {
		if (StringUtils.isNotBlank(creditLetterId)) {
			CreditLetterApply creditLetterApply = this.creditLetterApplyService.get(creditLetterId);
			if (creditLetterApply != null) {
				map.put("creditLetterApply", creditLetterApply);
			}
		}
	}
	
	/**
	 * 进入新建、编辑或查看页面时加载信息
	 * @param creditLetterId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody CreditLetterApply initEditOrView(String creditLetterId){
		UserProfile user = this.getUserProfile();
		CreditLetterApply creditLetterApply = this.creditLetterApplyService.initEditOrViewPage(creditLetterId, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.creditLetterApplyService.getAuthCanExecute(user, creditLetterApply);
		}
		return creditLetterApply;
	}
	
	/**
	 * 保存或更新
	 * @param creditLetterApply
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<CreditLetterApply> saveOrUpdate(CreditLetterApply creditLetterApply){
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.creditLetterApplyService.getAuthCanExecute(user, creditLetterApply);
		CreditLetterApply entity = this.creditLetterApplyService.saveOrUpdate(user, creditLetterApply);
		return OperResult.succeed("saveSuccess", "保存成功", entity);
	}
	
	/**
	 * 提交
	 * @param creditLetterApply
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody OperResult<CreditLetterApply> commit(CreditLetterApply creditLetterApply){
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.creditLetterApplyService.getAuthCanExecute(user, creditLetterApply);
		CreditLetterApply entity = this.creditLetterApplyService.commit(user, creditLetterApply);
		return OperResult.succeed("commitSuccess", "提交成功", entity);
	}
	
	/**
	 * 删除/批量删除
	 * @param creditLetterIds
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String creditLetterIds){
		UserProfile user = this.getUserProfile();
		this.creditLetterApplyService.delete(user, creditLetterIds);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 获取审批状态
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getSwfStatus")
	public Map<Integer,String> getSwfStatus(){
		Map<Integer,String> map = CreditLetterApply.getSwfStatusMap();
		return map;
	}
	
	/**
	 * 获取经营性质
	 * @param key
	 * @return
	 */
	@RequestMapping("getBusinessTypeData")
	public @ResponseBody List<CheckBoxVO> getBusinessTypeData(String key) {
		return this.creditLetterApplyService.getBusinessTypeData(CommonUtil.trim(key));
	}
	
	/**
	 * 获取付款期限
	 * @param termKey
	 * @return
	 */
	@RequestMapping("getSettlementTermData")
	public @ResponseBody List<CheckBoxVO> getSettlementTermData(String termKey) {
		return this.creditLetterApplyService.getSettlementTermData(CommonUtil.trim(termKey));
	}
	
	/**
	 * 获取进口国别
	 * @return
	 */
	@RequestMapping("getimportedFormData")
	public @ResponseBody
	Map<String, String> getForeignData() {
		return this.creditLetterApplyService.getForeignData();
	}
	
	/**
	 * 获取币别
	 * @return
	 */
	@RequestMapping("getCurrencyTypeData")
	public @ResponseBody
	Map<String, String> getCurrencyTypeData() {
		return TpcConstant.getCommonCurrencyMap();
	}
	
	/**
	 * 获取采购合同
	 * @param termKey
	 * @return
	 */
	@RequestMapping("selectContractData")
	public @ResponseBody Map<String,String> selectContractData(String projectId) {
		UserProfile user = this.getUserProfile();
		return this.creditLetterApplyService.selectContractData(CommonUtil.trim(projectId), user);
	}
}
