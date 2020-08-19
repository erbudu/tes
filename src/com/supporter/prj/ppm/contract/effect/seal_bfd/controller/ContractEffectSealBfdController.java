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
import com.supporter.prj.ppm.contract.effect.seal_bfd.service.ContractEffectSealBfdService;
import com.supporter.prj.ppm.contract.effect.seal_bfd.entity.ContractEffectSealBfd;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 主合同签约出版资料清单.
 * @author YAN
 * @date 2019-09-10 14:57:15
 * @version V1.0
 */
@Controller
@RequestMapping("contract/effect/seal_bfd/contractEffectSealBfd")
public class ContractEffectSealBfdController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractEffectSealBfdService contractEffectSealBfdService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param bfdId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractEffectSealBfd initEditOrViewPage(String bfdId) {
		ContractEffectSealBfd entity = contractEffectSealBfdService.initEditOrViewPage(bfdId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractEffectSealBfd contractEffectSealBfd) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractEffectSealBfdService.getGrid(user, jqGrid, contractEffectSealBfd);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractEffectSealBfd 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(ContractEffectSealBfd contractEffectSealBfd) {
		UserProfile user = this.getUserProfile();
		ContractEffectSealBfd entity = this.contractEffectSealBfdService.saveOrUpdate(user, contractEffectSealBfd);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
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
		this.contractEffectSealBfdService.delete(user, bfdIds);
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
		return this.contractEffectSealBfdService.nameIsValid(bfdId, bfdName);
	}

}
