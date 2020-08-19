package com.supporter.prj.cneec.tpc.external_quotation_review.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.external_quotation_review.entity.ExternalQuotationReview;
import com.supporter.prj.cneec.tpc.external_quotation_review.service.ExternalQuotationReviewService;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;
import com.supporter.util.HttpUtil;

/**
 * @Title: ExternalQuotationReviewController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-09-25
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/externalQuotationReview")
public class ExternalQuotationReviewController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ExternalQuotationReviewService externalQuotationReviewService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param externalQuotationReview
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ExternalQuotationReview externalQuotationReview) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.externalQuotationReviewService.getGrid(user, jqGrid, externalQuotationReview);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param externalId
	 * @param map
	 */
	@ModelAttribute
	public void getExternalQuotationReview(String externalId, Map<String, Object> map) {
		if (!StringUtils.isBlank(externalId)) {
			ExternalQuotationReview externalQuotationReview = this.externalQuotationReviewService.get(externalId);
			if (externalQuotationReview != null) {
				map.put("externalQuotationReview", externalQuotationReview);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param externalId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	ExternalQuotationReview get(String externalId) {
		ExternalQuotationReview externalQuotationReview = this.externalQuotationReviewService.get(externalId);
		return externalQuotationReview;
	}

	/**
	 * 根据对外报价事业部评审初始化数据
	 * @return
	 */
	@RequestMapping("initByDeptExternalId")
	public @ResponseBody
	ExternalQuotationReview initByDeptExternalId() {
		ExternalQuotationReview externalQuotationReview;
		UserProfile user = this.getUserProfile();
		String externalId = CommonUtil.trim(this.getRequestPara("externalId"));
		if (externalId.length() > 0) {
			externalQuotationReview = this.externalQuotationReviewService.initEditOrViewPage(externalId, user);
		} else {
			String deptExternalId = CommonUtil.trim(this.getRequestPara("deptExternalId"));
			externalQuotationReview = this.externalQuotationReviewService.initExternalQuotationReviewByDeptExternalId(deptExternalId, user);
		}
		return externalQuotationReview;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param externalId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	ExternalQuotationReview initEditOrViewPage(String externalId) {
		UserProfile user = this.getUserProfile();
		ExternalQuotationReview externalQuotationReview = this.externalQuotationReviewService.initEditOrViewPage(externalId, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.externalQuotationReviewService.getAuthCanExecute(user, externalQuotationReview);
		}
		return externalQuotationReview;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param externalQuotationReview 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<ExternalQuotationReview> saveOrUpdate(ExternalQuotationReview externalQuotationReview) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.externalQuotationReviewService.getAuthCanExecute(user, externalQuotationReview);
		Map<String, Object> valueMap = this.getPropValues(ExternalQuotationReview.class);
		ExternalQuotationReview entity = this.externalQuotationReviewService.saveOrUpdate(user, externalQuotationReview, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}

	/**
	 * 发送会议知会
	 * @param response
	 */
	@RequestMapping("/sendMeetNotifyMsg")
	public void sendMeetNotifyMsg(ExternalQuotationReview externalQuotationReview, HttpServletResponse response) {
		this.externalQuotationReviewService.sendMeetNotifyMsg(externalQuotationReview);
		String json = "{\"success\": true,\"msg\": \"\"}";
		HttpUtil.write(response, json);
	}

	/**
	 * 提交数据
	 * @param externalQuotationReview
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<ExternalQuotationReview> commit(ExternalQuotationReview externalQuotationReview) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.externalQuotationReviewService.getAuthCanExecute(user, externalQuotationReview);
		Map<String, Object> valueMap = this.getPropValues(ExternalQuotationReview.class);
		valueMap.put("reviewClassification", this.getRequestPara("reviewClassification"));
		ExternalQuotationReview entity = this.externalQuotationReviewService.commit(user, externalQuotationReview, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS, "提交成功", entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param externalIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String externalIds) {
		UserProfile user = this.getUserProfile();
		this.externalQuotationReviewService.delete(user, externalIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData(Boolean isMeetReview) {
		return ExternalQuotationReview.getSwfStatusMap(isMeetReview);
	}

}