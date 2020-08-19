package com.supporter.prj.ppm.contract.effect.direct.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.contract.effect.direct.service.ContractEffectDirectService;
import com.supporter.prj.ppm.contract.effect.direct.entity.ContractEffectDirect;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 主合同直接生效.
 * @author YAN
 * @date 2019-09-18 10:30:48
 * @version V1.0
 */
@Controller
@RequestMapping("contract/effect/direct/contractEffectDirect")
public class ContractEffectDirectController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractEffectDirectService ContractEffectDirectService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param directId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractEffectDirect initEditOrViewPage(String directId) {
		ContractEffectDirect entity = ContractEffectDirectService.initEditOrViewPage(directId);
		return entity;
	}

	@RequestMapping("initPageByPrjId")
	public @ResponseBody ContractEffectDirect initPageByPrjId(String prjId) {
		ContractEffectDirect entity = ContractEffectDirectService.initPageByPrjId(prjId);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractEffectDirect ContractEffectDirect) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.ContractEffectDirectService.getGrid(user, jqGrid, ContractEffectDirect);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param ContractEffectDirect 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(ContractEffectDirect ContractEffectDirect) {
		UserProfile user = this.getUserProfile();
		ContractEffectDirect entity = this.ContractEffectDirectService.saveOrUpdate(user, ContractEffectDirect);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity );
	}

	/**
	 * 删除操作
	 * 
	 * @param directIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String directIds) {
		UserProfile user = this.getUserProfile();
		this.ContractEffectDirectService.delete(user, directIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param directId
	 * @param directName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String directId,String directName) {
		return this.ContractEffectDirectService.nameIsValid(directId, directName);
	}

	@RequestMapping("validEffect")
	public @ResponseBody
	OperResult<?> validSign(String prjId) {
		String message = "";
		String nextPage = "";
		boolean result = false;
		Map<String, Object> map = this.ContractEffectDirectService.validEffect(prjId, message, result);
		message = (String) map.get("msg");
		result = (Boolean) map.get("result");
		nextPage = (String) map.get("nextPage");
		OperResult<?> o =OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, message, result);
		o.setNextPage(nextPage);
		return o;
	}

}
