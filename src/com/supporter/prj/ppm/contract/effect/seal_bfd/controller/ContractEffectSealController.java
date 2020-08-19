package com.supporter.prj.ppm.contract.effect.seal_bfd.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.contract.effect.seal_bfd.service.ContractEffectSealService;
import com.supporter.prj.ppm.contract.effect.seal_bfd.entity.ContractEffectSeal;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 主合同出版.
 * @author YAN
 * @date 2019-09-10 14:57:13
 * @version V1.0
 */
@Controller
@RequestMapping("contract/effect/seal_bfd/contractEffectSeal")
public class ContractEffectSealController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractEffectSealService contractEffectSealService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param effectSealId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractEffectSeal initEditOrViewPage(String effectSealId) {
		ContractEffectSeal entity = contractEffectSealService.initEditOrViewPage(effectSealId);
		return entity;
	}
	@RequestMapping("initPageByPrjId")
	public @ResponseBody ContractEffectSeal initPageByPrjId(String prjId) {
		ContractEffectSeal entity = contractEffectSealService.initPageByPrjId(prjId);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractEffectSeal contractEffectSeal) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractEffectSealService.getGrid(user, jqGrid, contractEffectSeal);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractEffectSeal 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(ContractEffectSeal contractEffectSeal) {
		UserProfile user = this.getUserProfile();
		ContractEffectSeal entity = this.contractEffectSealService.saveOrUpdate(user, contractEffectSeal);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param effectSealIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String effectSealIds) {
		UserProfile user = this.getUserProfile();
		this.contractEffectSealService.delete(user, effectSealIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param effectSealId
	 * @param effectSealName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String effectSealId,String effectSealName) {
		return this.contractEffectSealService.nameIsValid(effectSealId, effectSealName);
	}
	@RequestMapping("validEffect")
	public @ResponseBody
	OperResult<?> validSign(String prjId) {
		String message = "";
		boolean result = false;
		Map<String, Object> map = this.contractEffectSealService.validEffect(prjId, message, result);
		message = (String) map.get("msg");
		result = (Boolean) map.get("result");
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, message, result);
	}

}
