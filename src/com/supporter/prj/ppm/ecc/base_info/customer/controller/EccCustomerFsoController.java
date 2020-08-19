package com.supporter.prj.ppm.ecc.base_info.customer.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.ecc.base_info.customer.service.EccCustomerFsoService;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerFso;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 出口管制客户办事处.
 * @author YAN
 * @date 2019-08-16 18:30:28
 * @version V1.0
 */
@Controller
@RequestMapping("ecc/customer/eccCustomerFso")
public class EccCustomerFsoController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EccCustomerFsoService eccCustomerFsoService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param fsoId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody EccCustomerFso initEditOrViewPage(String fsoId) {
		EccCustomerFso entity = eccCustomerFsoService.initEditOrViewPage(fsoId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, EccCustomerFso eccCustomerFso) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.eccCustomerFsoService.getGrid(user, jqGrid, eccCustomerFso);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param eccCustomerFso 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(EccCustomerFso eccCustomerFso) {
		UserProfile user = this.getUserProfile();
		EccCustomerFso entity = this.eccCustomerFsoService.saveOrUpdate(user, eccCustomerFso);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param fsoIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String fsoIds) {
		UserProfile user = this.getUserProfile();
		this.eccCustomerFsoService.delete(user, fsoIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param fsoId
	 * @param fsoName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String fsoId,String fsoName) {
		return this.eccCustomerFsoService.nameIsValid(fsoId, fsoName);
	}

}
