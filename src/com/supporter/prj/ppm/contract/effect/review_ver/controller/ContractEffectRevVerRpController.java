package com.supporter.prj.ppm.contract.effect.review_ver.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.contract.effect.review_ver.service.ContractEffectRevVerRpService;
import com.supporter.prj.ppm.contract.effect.review_ver.entity.ContractEffectRevVerRp;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 验证评审要点表.
 * @author YAN
 * @date 2019-09-09 10:46:31
 * @version V1.0
 */
@Controller
@RequestMapping("contract/effect/review_ver/contractEffectRevVerRp")
public class ContractEffectRevVerRpController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractEffectRevVerRpService contractEffectRevVerRpService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param rpId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractEffectRevVerRp initEditOrViewPage(String rpId) {
		ContractEffectRevVerRp entity = contractEffectRevVerRpService.initEditOrViewPage(rpId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractEffectRevVerRp contractEffectRevVerRp) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractEffectRevVerRpService.getGrid(user, jqGrid, contractEffectRevVerRp);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractEffectRevVerRp 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(ContractEffectRevVerRp contractEffectRevVerRp) {
		UserProfile user = this.getUserProfile();
		ContractEffectRevVerRp entity = this.contractEffectRevVerRpService.saveOrUpdate(user, contractEffectRevVerRp);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param rpIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String rpIds) {
		UserProfile user = this.getUserProfile();
		this.contractEffectRevVerRpService.delete(user, rpIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param rpId
	 * @param rpName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String rpId,String rpName) {
		return this.contractEffectRevVerRpService.nameIsValid(rpId, rpName);
	}

}
