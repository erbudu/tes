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
import com.supporter.prj.ppm.ecc.base_info.customer.service.EccCustomerWarnService;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerWarn;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 出口管制客户警告.
 * @author YAN
 * @date 2019-08-16 18:30:30
 * @version V1.0
 */
@Controller
@RequestMapping("ecc/customer/eccCustomerWarn")
public class EccCustomerWarnController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EccCustomerWarnService eccCustomerWarnService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param warnId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody EccCustomerWarn initEditOrViewPage(String warnId) {
		EccCustomerWarn entity = eccCustomerWarnService.initEditOrViewPage(warnId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, EccCustomerWarn eccCustomerWarn) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.eccCustomerWarnService.getGrid(user, jqGrid, eccCustomerWarn);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param eccCustomerWarn 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(EccCustomerWarn eccCustomerWarn) {
		UserProfile user = this.getUserProfile();
		EccCustomerWarn entity = this.eccCustomerWarnService.saveOrUpdate(user, eccCustomerWarn);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 删除操作
	 * 
	 * @param warnIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String warnIds) {
		UserProfile user = this.getUserProfile();
		this.eccCustomerWarnService.delete(user, warnIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param warnId
	 * @param warnName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String warnId,String warnName) {
		return this.eccCustomerWarnService.nameIsValid(warnId, warnName);
	}

}
