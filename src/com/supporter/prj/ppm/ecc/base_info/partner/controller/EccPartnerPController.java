package com.supporter.prj.ppm.ecc.base_info.partner.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.ecc.base_info.partner.service.EccPartnerPService;
import com.supporter.prj.ppm.ecc.base_info.partner.entity.EccPartnerP;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 出口管制项目合伙人合作伙伴.
 * @author YAN
 * @date 2019-08-16 18:34:21
 * @version V1.0
 */
@Controller
@RequestMapping("ecc/partner/eccPartnerP")
public class EccPartnerPController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EccPartnerPService eccPartnerPService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param partnerPId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody EccPartnerP initEditOrViewPage(String partnerPId) {
		EccPartnerP entity = eccPartnerPService.initEditOrViewPage(partnerPId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, EccPartnerP eccPartnerP) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.eccPartnerPService.getGrid(user, jqGrid, eccPartnerP);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param eccPartnerP 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(EccPartnerP eccPartnerP) {
		UserProfile user = this.getUserProfile();
		EccPartnerP entity = this.eccPartnerPService.saveOrUpdate(user, eccPartnerP);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 删除操作
	 * 
	 * @param partnerPIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String partnerPIds) {
		UserProfile user = this.getUserProfile();
		this.eccPartnerPService.delete(user, partnerPIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param partnerPId
	 * @param partnerPName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String partnerPId,String partnerPName) {
		return this.eccPartnerPService.nameIsValid(partnerPId, partnerPName);
	}

}
