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
import com.supporter.prj.ppm.ecc.base_info.owner.service.EccOwnerService;
import com.supporter.prj.ppm.ecc.base_info.owner.entity.EccOwner;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 出口管制客户.
 * @author YAN
 * @date 2019-08-16 18:39:15
 * @version V1.0
 */
@Controller
@RequestMapping("ecc/owner/eccOwner")
public class EccOwnerController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EccOwnerService eccOwnerService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param ownerId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody EccOwner initEditOrViewPage(String ownerId) {
		EccOwner entity = eccOwnerService.initEditOrViewPage(ownerId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, EccOwner eccOwner) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.eccOwnerService.getGrid(user, jqGrid, eccOwner);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param eccOwner 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(EccOwner eccOwner) {
		UserProfile user = this.getUserProfile();
		EccOwner entity = this.eccOwnerService.saveOrUpdate(user, eccOwner);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param ownerIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String ownerIds) {
		UserProfile user = this.getUserProfile();
		this.eccOwnerService.delete(user, ownerIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param ownerId
	 * @param ownerName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String ownerId,String ownerName) {
		return this.eccOwnerService.nameIsValid(ownerId, ownerName);
	}

}
