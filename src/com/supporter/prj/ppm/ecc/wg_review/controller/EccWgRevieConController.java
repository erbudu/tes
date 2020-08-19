package com.supporter.prj.ppm.ecc.wg_review.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.ecc.wg_review.service.EccWgRevieConService;
import com.supporter.prj.ppm.ecc.wg_review.entity.EccWgRevieCon;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 出口管制工作组评审结论.
 * @author YAN
 * @date 2019-08-16 18:44:49
 * @version V1.0
 */
@Controller
@RequestMapping("ecc/wg_review/eccWgRevieCon")
public class EccWgRevieConController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EccWgRevieConService eccWgRevieConService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param wgEccRvConId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody EccWgRevieCon initEditOrViewPage(String eccId) {
		EccWgRevieCon entity = eccWgRevieConService.initEditOrViewPage(eccId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, EccWgRevieCon eccWgRevieCon) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.eccWgRevieConService.getGrid(user, jqGrid, eccWgRevieCon);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param eccWgRevieCon 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(EccWgRevieCon eccWgRevieCon) {
		UserProfile user = this.getUserProfile();
		EccWgRevieCon entity = this.eccWgRevieConService.saveOrUpdate(user, eccWgRevieCon);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param wgEccRvConIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String wgEccRvConIds) {
		UserProfile user = this.getUserProfile();
		this.eccWgRevieConService.delete(user, wgEccRvConIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param wgEccRvConId
	 * @param wgEccRvConName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String wgEccRvConId,String wgEccRvConName) {
		return this.eccWgRevieConService.nameIsValid(wgEccRvConId, wgEccRvConName);
	}

}
