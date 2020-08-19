package com.supporter.prj.ppm.ecc.cac_review.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.ecc.cac_review.service.EccCacRevieConService;
import com.supporter.prj.ppm.ecc.cac_review.entity.EccCacRevieCon;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 出口管制委员会评审结论.
 * @author YAN
 * @date 2019-08-16 18:45:23
 * @version V1.0
 */
@Controller
@RequestMapping("ecc/cac_review/eccCacRevieCon")
public class EccCacRevieConController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EccCacRevieConService eccCacRevieConService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param cacEccRvConId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody EccCacRevieCon initEditOrViewPage(String eccId) {
		EccCacRevieCon entity = eccCacRevieConService.initEditOrViewPage(eccId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, EccCacRevieCon eccCacRevieCon) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.eccCacRevieConService.getGrid(user, jqGrid, eccCacRevieCon);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param eccCacRevieCon 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(EccCacRevieCon eccCacRevieCon) {
		UserProfile user = this.getUserProfile();
		EccCacRevieCon entity = this.eccCacRevieConService.saveOrUpdate(user, eccCacRevieCon);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param cacEccRvConIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String cacEccRvConIds) {
		UserProfile user = this.getUserProfile();
		this.eccCacRevieConService.delete(user, cacEccRvConIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param cacEccRvConId
	 * @param cacEccRvConName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String cacEccRvConId,String cacEccRvConName) {
		return this.eccCacRevieConService.nameIsValid(cacEccRvConId, cacEccRvConName);
	}

}
