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
import com.supporter.prj.ppm.contract.effect.seal_bfd.service.ContractEffectSealBfdFService;
import com.supporter.prj.ppm.contract.effect.seal_bfd.entity.ContractEffectSealBfdF;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 资料清单文件.
 * @author YAN
 * @date 2019-09-10 14:57:16
 * @version V1.0
 */
@Controller
@RequestMapping("contract/effect/seal_bfd/contractEffectSealBfdF")
public class ContractEffectSealBfdFController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractEffectSealBfdFService contractEffectSealBfdFService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param recordId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractEffectSealBfdF initEditOrViewPage(String recordId) {
		ContractEffectSealBfdF entity = contractEffectSealBfdFService.initEditOrViewPage(recordId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractEffectSealBfdF contractEffectSealBfdF) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractEffectSealBfdFService.getGrid(user, jqGrid, contractEffectSealBfdF);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractEffectSealBfdF 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(ContractEffectSealBfdF contractEffectSealBfdF) {
		UserProfile user = this.getUserProfile();
		ContractEffectSealBfdF entity = this.contractEffectSealBfdFService.saveOrUpdate(user, contractEffectSealBfdF);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 删除操作
	 * 
	 * @param recordIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String recordIds) {
		UserProfile user = this.getUserProfile();
		this.contractEffectSealBfdFService.delete(user, recordIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param recordId
	 * @param recordName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String recordId,String recordName) {
		return this.contractEffectSealBfdFService.nameIsValid(recordId, recordName);
	}

}
