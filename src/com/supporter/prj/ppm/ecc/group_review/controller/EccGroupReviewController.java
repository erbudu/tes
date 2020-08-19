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
import com.supporter.prj.ppm.ecc.group_review.service.EccGroupReviewService;
import com.supporter.prj.ppm.ecc.group_review.entity.EccGroupReview;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 集团管制审核表.
 * @author YAN
 * @date 2019-08-16 18:46:29
 * @version V1.0
 */
@Controller
@RequestMapping("ecc/group_review/eccGroupReview")
public class EccGroupReviewController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EccGroupReviewService eccGroupReviewService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param eccGroupId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody EccGroupReview initEditOrViewPage(String prjId) {
		EccGroupReview entity = eccGroupReviewService.initEditOrViewPage(this.getUserProfile(),prjId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, EccGroupReview eccGroupReview) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.eccGroupReviewService.getGrid(user, jqGrid, eccGroupReview);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param eccGroupReview 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(EccGroupReview eccGroupReview) {
		UserProfile user = this.getUserProfile();
		EccGroupReview entity = this.eccGroupReviewService.saveOrUpdate(user, eccGroupReview);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param eccGroupIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String eccGroupIds) {
		UserProfile user = this.getUserProfile();
		this.eccGroupReviewService.delete(user, eccGroupIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param eccGroupId
	 * @param eccGroupName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String eccGroupId,String eccGroupName) {
		return this.eccGroupReviewService.nameIsValid(eccGroupId, eccGroupName);
	}
	@RequestMapping("validEcc")
	public @ResponseBody OperResult< ? > validEcc(String prjId) {
		String message = "";
		boolean result = false;
		Map<String , Object> map = this.eccGroupReviewService.validEcc(prjId,message,result);
		message = (String) map.get("msg");
		result = (Boolean) map.get("result");
		OperResult< ? > o = OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, message, result);
		o.setNextPage((String)map.get("nextPage"));
		return o;
	}
}
