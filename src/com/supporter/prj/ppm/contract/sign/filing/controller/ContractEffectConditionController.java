package com.supporter.prj.ppm.contract.sign.filing.controller;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.sign.filing.entity.ContractEffectCondition;
import com.supporter.prj.ppm.contract.sign.filing.service.ContractEffectConditionService;
import com.supporter.prj.ppm.ecc.OperResultConstant;
import com.supporter.spring_mvc.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title: Controller
 * @Description: PPM_CONTRACT_FILING_BFD_F.
 * @author YAN
 * @date 2019-09-17 11:37:26
 * @version V1.0
 */
@Controller
@RequestMapping("contract/sign/filing/contractEffectCondition")
public class ContractEffectConditionController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractEffectConditionService contractEffectConditionService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param conditionId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractEffectCondition initEditOrViewPage(String conditionId) {
		ContractEffectCondition entity = contractEffectConditionService.initEditOrViewPage(conditionId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractEffectCondition contractEffectCondition) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractEffectConditionService.getGrid(user, jqGrid, contractEffectCondition);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractEffectCondition 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(ContractEffectCondition contractEffectCondition) {
		UserProfile user = this.getUserProfile();
		ContractEffectCondition entity = this.contractEffectConditionService.saveOrUpdate(user, contractEffectCondition);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param conditionIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String conditionIds) {
		UserProfile user = this.getUserProfile();
		this.contractEffectConditionService.delete(user, conditionIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

}
