package com.supporter.prj.ppm.ecc.group_review.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.ecc.group_review.service.EccGroupRevieConService;
import com.supporter.prj.ppm.ecc.group_review.entity.EccGroupRevieCon;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 集团评审结论.
 * @author YAN
 * @date 2019-08-16 18:46:30
 * @version V1.0
 */
@Controller
@RequestMapping("ecc/group_review/eccGroupRevieCon")
public class EccGroupRevieConController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EccGroupRevieConService eccGroupRevieConService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param groupEccRvConId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody EccGroupRevieCon initEditOrViewPage( String prjId,String eccId) {
		EccGroupRevieCon entity = eccGroupRevieConService.initEditOrViewPage(prjId,eccId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, EccGroupRevieCon eccGroupRevieCon) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.eccGroupRevieConService.getGrid(user, jqGrid, eccGroupRevieCon);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param eccGroupRevieCon 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(EccGroupRevieCon eccGroupRevieCon,Integer status) {
		UserProfile user = this.getUserProfile();
		EccGroupRevieCon entity = this.eccGroupRevieConService.saveOrUpdate(user, eccGroupRevieCon,status);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param groupEccRvConIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String groupEccRvConIds) {
		UserProfile user = this.getUserProfile();
		this.eccGroupRevieConService.delete(user, groupEccRvConIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param groupEccRvConId
	 * @param groupEccRvConName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String groupEccRvConId,String groupEccRvConName) {
		return this.eccGroupRevieConService.nameIsValid(groupEccRvConId, groupEccRvConName);
	}

}
