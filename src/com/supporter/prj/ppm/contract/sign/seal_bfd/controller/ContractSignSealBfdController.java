package com.supporter.prj.ppm.contract.sign.seal_bfd.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.contract.sign.seal_bfd.service.ContractSignSealBfdService;
import com.supporter.prj.ppm.contract.sign.seal_bfd.entity.ContractSignSealBfd;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 主合同签约出版资料清单.
 * @author YAN
 * @date 2019-09-10 14:57:15
 * @version V1.0
 */
@Controller
@RequestMapping("contract/sign/seal_bfd/contractSignSealBfd")
public class ContractSignSealBfdController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractSignSealBfdService contractSignSealBfdService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param bfdId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractSignSealBfd initEditOrViewPage(String bfdId) {
		ContractSignSealBfd entity = contractSignSealBfdService.initEditOrViewPage(bfdId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractSignSealBfd contractSignSealBfd) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractSignSealBfdService.getGrid(user, jqGrid, contractSignSealBfd);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractSignSealBfd 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(ContractSignSealBfd contractSignSealBfd) {
		UserProfile user = this.getUserProfile();
		ContractSignSealBfd entity = this.contractSignSealBfdService.saveOrUpdate(user, contractSignSealBfd);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 删除操作
	 * 
	 * @param bfdIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String bfdIds) {
		UserProfile user = this.getUserProfile();
		this.contractSignSealBfdService.delete(user, bfdIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param bfdId
	 * @param bfdName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String bfdId,String bfdName) {
		return this.contractSignSealBfdService.nameIsValid(bfdId, bfdName);
	}

}
