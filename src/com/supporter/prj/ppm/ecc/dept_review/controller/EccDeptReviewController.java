package com.supporter.prj.ppm.ecc.dept_review.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.ecc.dept_review.service.EccDeptReviewService;
import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptReview;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 部门出口管制评审.
 * @author YAN
 * @date 2019-08-16 18:43:19
 * @version V1.0
 */
@Controller
@RequestMapping("ecc/dept_review/eccDeptReview")
public class EccDeptReviewController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EccDeptReviewService eccDeptReviewService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param deptEccId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody EccDeptReview initEditOrViewPage(String prjId) {
		EccDeptReview entity = eccDeptReviewService.initPageByPrjId(prjId);
		return entity;
	}
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 *
	 * @param prjId 主键
	 * @return
	 */
	@RequestMapping("initPageByPrjId")
	public @ResponseBody EccDeptReview initPageByPrjId(String prjId) {
		EccDeptReview entity = eccDeptReviewService.initPageByPrjId(prjId);
		if (StringUtils.isBlank(entity.getCreatedBy())){
			entity.setLinkmanName(getUserProfile().getName());
			entity.setCreatedBy(getUserProfile().getName());
			entity.setCreatedById(getUserProfile().getPersonId());
		}
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, EccDeptReview eccDeptReview) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.eccDeptReviewService.getGrid(user, jqGrid, eccDeptReview);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param eccDeptReview 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(EccDeptReview eccDeptReview) {
		UserProfile user = this.getUserProfile();
		EccDeptReview entity = this.eccDeptReviewService.saveOrUpdate(user, eccDeptReview);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity );
	}

	/**
	 * 删除操作
	 * 
	 * @param deptEccIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String deptEccIds) {
		UserProfile user = this.getUserProfile();
		this.eccDeptReviewService.delete(user, deptEccIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}
	@RequestMapping("batchDelAll")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDelAll(String eccId) {
		UserProfile user = this.getUserProfile();
		this.eccDeptReviewService.batchDelAll(user, eccId);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}
	/**
	 * 判断名字是否重复
	 * 
	 * @param deptEccId
	 * @param deptEccName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String deptEccId,String deptEccName) {
		return this.eccDeptReviewService.nameIsValid(deptEccId, deptEccName);
	}
	@RequestMapping("validEcc")
	public @ResponseBody OperResult< ? > validEcc(String prjId) {
		UserProfile user = this.getUserProfile();
		String message = "";
		boolean result = false;
		Map<String , Object> map = this.eccDeptReviewService.validEcc(prjId,message,result);
		message = (String) map.get("msg");
		result = (Boolean) map.get("result");
		String nextPage = (String)map.get("nextPage");
		OperResult<?> o =OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, message, result);
		o.setNextPage(nextPage);
		return o;
	}

}
