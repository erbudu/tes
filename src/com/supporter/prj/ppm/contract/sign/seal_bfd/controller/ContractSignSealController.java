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
import com.supporter.prj.ppm.contract.sign.seal_bfd.service.ContractSignSealService;
import com.supporter.prj.ppm.contract.sign.seal_bfd.entity.ContractSignSeal;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 主合同出版.
 * @author YAN
 * @date 2019-09-10 14:57:13
 * @version V1.0
 */
@Controller
@RequestMapping("contract/sign/seal_bfd/contractSignSeal")
public class ContractSignSealController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractSignSealService contractSignSealService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param signSealId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractSignSeal initEditOrViewPage(String signSealId) {
		ContractSignSeal entity = contractSignSealService.initEditOrViewPage(signSealId);
		return entity;
	}
	@RequestMapping("initPageByPrjId")
	public @ResponseBody ContractSignSeal initPageByPrjId(String prjId) {
		ContractSignSeal entity = contractSignSealService.initPageByPrjId(prjId);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractSignSeal contractSignSeal) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractSignSealService.getGrid(user, jqGrid, contractSignSeal);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractSignSeal 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(ContractSignSeal contractSignSeal) {
		UserProfile user = this.getUserProfile();
		ContractSignSeal entity = this.contractSignSealService.saveOrUpdate(user, contractSignSeal);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param signSealIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String signSealIds) {
		UserProfile user = this.getUserProfile();
		this.contractSignSealService.delete(user, signSealIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param signSealId
	 * @param signSealName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String signSealId,String signSealName) {
		return this.contractSignSealService.nameIsValid(signSealId, signSealName);
	}

}
