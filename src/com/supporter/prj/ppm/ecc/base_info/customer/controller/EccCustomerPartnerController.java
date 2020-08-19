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
import com.supporter.prj.ppm.ecc.base_info.customer.service.EccCustomerPartnerService;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerPartner;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 出口管制客户的合伙人.
 * @author YAN
 * @date 2019-08-16 18:30:29
 * @version V1.0
 */
@Controller
@RequestMapping("ecc/customer/eccCustomerPartner")
public class EccCustomerPartnerController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EccCustomerPartnerService eccCustomerPartnerService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param customerId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody EccCustomerPartner initEditOrViewPage(String customerId) {
		EccCustomerPartner entity = eccCustomerPartnerService.initEditOrViewPage(customerId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, EccCustomerPartner eccCustomerPartner) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.eccCustomerPartnerService.getGrid(user, jqGrid, eccCustomerPartner);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param eccCustomerPartner 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(EccCustomerPartner eccCustomerPartner) {
		UserProfile user = this.getUserProfile();
		EccCustomerPartner entity = this.eccCustomerPartnerService.saveOrUpdate(user, eccCustomerPartner);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 删除操作
	 * 
	 * @param customerIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String customerIds) {
		UserProfile user = this.getUserProfile();
		this.eccCustomerPartnerService.delete(user, customerIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param customerId
	 * @param customerName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String customerId,String customerName) {
		return this.eccCustomerPartnerService.nameIsValid(customerId, customerName);
	}

}
