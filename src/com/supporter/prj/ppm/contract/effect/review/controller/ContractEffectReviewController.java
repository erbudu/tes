package com.supporter.prj.ppm.contract.effect.review.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.contract.effect.review.service.PpmContractEffectReviewService;
import com.supporter.prj.ppm.contract.effect.review.entity.PpmContractEffectReview;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 生效评审表.
 * @author YAN
 * @date 2019-09-06 18:35:28
 * @version V1.0
 */
@Controller("PpmContractEffectReviewController")
@RequestMapping("ppm/contract/effect/review/contractEffectReview")
public class ContractEffectReviewController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PpmContractEffectReviewService contractEffectReviewService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param contractEffectReviewId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody PpmContractEffectReview initEditOrViewPage(String contractEffectReviewId) {
		PpmContractEffectReview entity = contractEffectReviewService.initEditOrViewPage(contractEffectReviewId);
		return entity;
	}

	/**
	 * 根据项目id初始化项目
	 * @param prjId
	 * @return
	 */
	@RequestMapping("initPageByPrjId")
	public @ResponseBody PpmContractEffectReview initPageByPrjId(String contractEffectReviewId,String prjId) {
		PpmContractEffectReview entity = contractEffectReviewService.initPageByPrjId(contractEffectReviewId,prjId);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, PpmContractEffectReview contractEffectReview) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractEffectReviewService.getGrid(user, jqGrid, contractEffectReview);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractEffectReview 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(PpmContractEffectReview contractEffectReview) {
		UserProfile user = this.getUserProfile();
		PpmContractEffectReview entity = this.contractEffectReviewService.saveOrUpdate(user, contractEffectReview);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}
	@RequestMapping("commit")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > commit(PpmContractEffectReview contractEffectReview) {
		UserProfile user = this.getUserProfile();
		PpmContractEffectReview entity = this.contractEffectReviewService.commit(user, contractEffectReview);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param contractEffectReviewIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String contractEffectReviewIds) {
		UserProfile user = this.getUserProfile();
		this.contractEffectReviewService.delete(user, contractEffectReviewIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param contractEffectReviewId
	 * @param contractEffectReviewName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String contractEffectReviewId,String contractEffectReviewName) {
		return this.contractEffectReviewService.nameIsValid(contractEffectReviewId, contractEffectReviewName);
	}
	@RequestMapping("validEffect")
	public @ResponseBody
	OperResult<?> validSign(String prjId) {
		String message = "";
		boolean result = false;
		Map<String, Object> map = this.contractEffectReviewService.validEffect(prjId, message, result);
		message = (String) map.get("msg");
		result = (Boolean) map.get("result");
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, message, result);
	}

	/**
	 * 用印文件列表
	 * @param businessUUID
	 * @return
	 */
	@RequestMapping("getUseSealGrid")
	public @ResponseBody
	List<Map<String,String>> getUseSealGrid(String businessUUID){
		List<Map<String,String>> useSealFileGrid = contractEffectReviewService.getUseSealGrid(businessUUID);
		return useSealFileGrid;
	}
	/**
	 * 标记用印文件
	 * @param chkValueStr
	 * @param bidIngUpId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("markUsePrintFile")
	public @ResponseBody OperResult markUsePrintFile(String chkValueStr,String contractEffectReportId) {
		if(chkValueStr == null || chkValueStr == "") return OperResult.succeed(null, null, null);
		contractEffectReviewService.markUsePrintFile(chkValueStr,contractEffectReportId);
		return OperResult.succeed("success", null, null);
	}
}
