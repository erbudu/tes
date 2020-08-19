package com.supporter.prj.ppm.contract.effect.filing.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.contract.effect.filing.service.ContractEffectFilingService;
import com.supporter.prj.ppm.contract.effect.filing.entity.ContractEffectFiling;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: PPM_CONTRACT_FILING.
 * @author YAN
 * @date 2019-09-17 11:37:24
 * @version V1.0
 */
@Controller
@RequestMapping("contract/effect/filing/contractFiling")
public class ContractEffectFilingController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractEffectFilingService contractFilingService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param filingId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractEffectFiling initEditOrViewPage(String filingId) {
		ContractEffectFiling entity = contractFilingService.initEditOrViewPage(filingId);
		return entity;
	}
	@RequestMapping("initPageByPrjId")
	public @ResponseBody ContractEffectFiling initPageByPrjId(String prjId) {
		ContractEffectFiling entity = contractFilingService.initPageByPrjId(prjId);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractEffectFiling contractFiling) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractFilingService.getGrid(user, jqGrid, contractFiling);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractFiling 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(ContractEffectFiling contractFiling) {
		UserProfile user = this.getUserProfile();
		ContractEffectFiling entity = this.contractFilingService.saveOrUpdate(user, contractFiling);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param filingIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String filingIds) {
		UserProfile user = this.getUserProfile();
		this.contractFilingService.delete(user, filingIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param filingId
	 * @param filingName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String filingId,String filingName) {
		return this.contractFilingService.nameIsValid(filingId, filingName);
	}

	@RequestMapping("validEffect")
	public @ResponseBody
	OperResult<?> validSign(String prjId) {
		String message = "";
		boolean result = false;
		Map<String, Object> map = this.contractFilingService.validEffect(prjId, message, result);
		message = (String) map.get("msg");
		result = (Boolean) map.get("result");
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, message, result);
	}

}
