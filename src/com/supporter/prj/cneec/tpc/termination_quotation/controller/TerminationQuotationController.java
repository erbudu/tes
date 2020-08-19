package com.supporter.prj.cneec.tpc.termination_quotation.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.termination_quotation.entity.TerminationQuotation;
import com.supporter.prj.cneec.tpc.termination_quotation.service.TerminationQuotationService;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: TerminationQuotationController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-09-18
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/terminationQuotation")
public class TerminationQuotationController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private TerminationQuotationService terminationQuotationService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param terminationQuotation
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, TerminationQuotation terminationQuotation) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.terminationQuotationService.getGrid(user, jqGrid, terminationQuotation);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param quotationId
	 * @param map
	 */
	@ModelAttribute
	public void getTerminationQuotation(String quotationId, Map<String, Object> map) {
		if (!StringUtils.isBlank(quotationId)) {
			TerminationQuotation terminationQuotation = this.terminationQuotationService.get(quotationId);
			if (terminationQuotation != null) {
				map.put("terminationQuotation", terminationQuotation);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param quotationId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	TerminationQuotation get(String quotationId) {
		TerminationQuotation terminationQuotation = this.terminationQuotationService.get(quotationId);
		return terminationQuotation;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param quotationId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	TerminationQuotation initEditOrViewPage(String quotationId) {
		UserProfile user = this.getUserProfile();
		TerminationQuotation terminationQuotation = this.terminationQuotationService.initEditOrViewPage(quotationId, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.terminationQuotationService.getAuthCanExecute(user, terminationQuotation);
		}
		return terminationQuotation;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param terminationQuotation 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<TerminationQuotation> saveOrUpdate(TerminationQuotation terminationQuotation) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.terminationQuotationService.getAuthCanExecute(user, terminationQuotation);
		Map<String, Object> valueMap = this.getPropValues(TerminationQuotation.class);
		TerminationQuotation entity = this.terminationQuotationService.saveOrUpdate(user, terminationQuotation, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}

	/**
	 * 提交数据
	 * @param terminationQuotation
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<TerminationQuotation> commit(TerminationQuotation terminationQuotation) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.terminationQuotationService.getAuthCanExecute(user, terminationQuotation);
		Map<String, Object> valueMap = this.getPropValues(TerminationQuotation.class);
		TerminationQuotation entity = this.terminationQuotationService.commit(user, terminationQuotation, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS, "提交成功", entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param quotationIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String quotationIds) {
		UserProfile user = this.getUserProfile();
		this.terminationQuotationService.delete(user, quotationIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData() {
		return TerminationQuotation.getSwfStatusMap();
	}

}