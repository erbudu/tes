package com.supporter.prj.ppm.ecc.base_info.owner.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.ecc.base_info.owner.service.EccOwnerPartnerService;
import com.supporter.prj.ppm.ecc.base_info.owner.entity.EccOwnerPartner;
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
 * @date 2019-08-16 18:39:17
 * @version V1.0
 */
@Controller
@RequestMapping("ecc/owner/eccOwnerPartner")
public class EccOwnerPartnerController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EccOwnerPartnerService eccOwnerPartnerService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param ownerPartnerId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody EccOwnerPartner initEditOrViewPage(String ownerPartnerId) {
		EccOwnerPartner entity = eccOwnerPartnerService.initEditOrViewPage(ownerPartnerId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, EccOwnerPartner eccOwnerPartner) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.eccOwnerPartnerService.getGrid(user, jqGrid, eccOwnerPartner);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param eccOwnerPartner 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(EccOwnerPartner eccOwnerPartner) {
		UserProfile user = this.getUserProfile();
		EccOwnerPartner entity = this.eccOwnerPartnerService.saveOrUpdate(user, eccOwnerPartner);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 删除操作
	 * 
	 * @param ownerPartnerIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String ownerPartnerIds) {
		UserProfile user = this.getUserProfile();
		this.eccOwnerPartnerService.delete(user, ownerPartnerIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param ownerPartnerId
	 * @param ownerPartnerName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String ownerPartnerId,String ownerPartnerName) {
		return this.eccOwnerPartnerService.nameIsValid(ownerPartnerId, ownerPartnerName);
	}

}
