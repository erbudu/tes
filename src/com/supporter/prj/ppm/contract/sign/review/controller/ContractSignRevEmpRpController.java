package com.supporter.prj.ppm.contract.sign.review.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.contract.sign.review.service.ContractSignRevEmpRpService;
import com.supporter.prj.ppm.contract.sign.review.entity.ContractSignRevEmpRp;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 评审人员的评审要点.
 * @author YAN
 * @date 2019-09-06 18:35:31
 * @version V1.0
 */
@Controller
@RequestMapping("ppm/contract/sign/review/contractSignRevEmpRp")
public class ContractSignRevEmpRpController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractSignRevEmpRpService contractSignRevEmpRpService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param rpId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractSignRevEmpRp initEditOrViewPage(String rpId) {
		ContractSignRevEmpRp entity = contractSignRevEmpRpService.initEditOrViewPage(rpId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractSignRevEmpRp contractSignRevEmpRp) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractSignRevEmpRpService.getGrid(user, jqGrid, contractSignRevEmpRp);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractSignRevEmpRp 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(ContractSignRevEmpRp contractSignRevEmpRp) {
		UserProfile user = this.getUserProfile();
		ContractSignRevEmpRp entity = this.contractSignRevEmpRpService.saveOrUpdate(user, contractSignRevEmpRp);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 删除操作
	 * 
	 * @param rpIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String rpIds) {
		UserProfile user = this.getUserProfile();
		this.contractSignRevEmpRpService.delete(user, rpIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param rpId
	 * @param rpName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String rpId,String rpName) {
		return this.contractSignRevEmpRpService.nameIsValid(rpId, rpName);
	}

}
