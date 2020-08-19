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
import com.supporter.prj.ppm.contract.effect.review_ver.service.ContractEffectRevVerBfdService;
import com.supporter.prj.ppm.contract.effect.review_ver.entity.ContractEffectRevVerBfd;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 验证标前评审资料清单.
 * @author YAN
 * @date 2019-09-09 10:46:29
 * @version V1.0
 */
@Controller
@RequestMapping("contract/effect/review_ver/contractEffectRevVerBfd")
public class ContractEffectRevVerBfdController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractEffectRevVerBfdService contractEffectRevVerBfdService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param bfdId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractEffectRevVerBfd initEditOrViewPage(String bfdId) {
		ContractEffectRevVerBfd entity = contractEffectRevVerBfdService.initEditOrViewPage(bfdId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractEffectRevVerBfd contractEffectRevVerBfd) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractEffectRevVerBfdService.getGrid(user, jqGrid, contractEffectRevVerBfd);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractEffectRevVerBfd 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(ContractEffectRevVerBfd contractEffectRevVerBfd) {
		UserProfile user = this.getUserProfile();
		ContractEffectRevVerBfd entity = this.contractEffectRevVerBfdService.saveOrUpdate(user, contractEffectRevVerBfd);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param bfdIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String bfdIds) {
		UserProfile user = this.getUserProfile();
		this.contractEffectRevVerBfdService.delete(user, bfdIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param bfdId
	 * @param bfdName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String bfdId,String bfdName) {
		return this.contractEffectRevVerBfdService.nameIsValid(bfdId, bfdName);
	}

}
