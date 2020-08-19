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
import com.supporter.prj.ppm.ecc.base_info.owner.service.EccOwnerFsoService;
import com.supporter.prj.ppm.ecc.base_info.owner.entity.EccOwnerFso;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 出口管制主办事处.
 * @author YAN
 * @date 2019-08-16 18:39:16
 * @version V1.0
 */
@Controller
@RequestMapping("ecc/owner/eccOwnerFso")
public class EccOwnerFsoController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EccOwnerFsoService eccOwnerFsoService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param fsoId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody EccOwnerFso initEditOrViewPage(String fsoId) {
		EccOwnerFso entity = eccOwnerFsoService.initEditOrViewPage(fsoId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, EccOwnerFso eccOwnerFso) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.eccOwnerFsoService.getGrid(user, jqGrid, eccOwnerFso);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param eccOwnerFso 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(EccOwnerFso eccOwnerFso) {
		UserProfile user = this.getUserProfile();
		EccOwnerFso entity = this.eccOwnerFsoService.saveOrUpdate(user, eccOwnerFso);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
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
		this.eccOwnerFsoService.delete(user, fsoIds);
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
		return this.eccOwnerFsoService.nameIsValid(fsoId, fsoName);
	}

}
