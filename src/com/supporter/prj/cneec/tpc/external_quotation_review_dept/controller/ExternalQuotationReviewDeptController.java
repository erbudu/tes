package com.supporter.prj.cneec.tpc.external_quotation_review_dept.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.external_quotation_review_dept.entity.ExternalQuotationReviewDept;
import com.supporter.prj.cneec.tpc.external_quotation_review_dept.service.ExternalQuotationReviewDeptService;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;
import com.supporter.util.HttpUtil;

/**
 * @Title: ExternalQuotationReviewDeptController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2018-03-20
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/externalQuotationReviewDept")
public class ExternalQuotationReviewDeptController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ExternalQuotationReviewDeptService externalQuotationReviewDeptService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param externalQuotationReviewDept
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ExternalQuotationReviewDept externalQuotationReviewDept) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.externalQuotationReviewDeptService.getGrid(user, jqGrid, externalQuotationReviewDept);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param externalId
	 * @param map
	 */
	@ModelAttribute
	public void getExternalQuotationReviewDept(String externalId, Map<String, Object> map) {
		if (!StringUtils.isBlank(externalId)) {
			ExternalQuotationReviewDept externalQuotationReviewDept = this.externalQuotationReviewDeptService.get(externalId);
			if (externalQuotationReviewDept != null) {
				map.put("externalQuotationReviewDept", externalQuotationReviewDept);
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
	ExternalQuotationReviewDept get(String externalId) {
		ExternalQuotationReviewDept externalQuotationReviewDept = this.externalQuotationReviewDeptService.get(externalId);
		return externalQuotationReviewDept;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping("initEditPageByDemandId")
	public @ResponseBody
	ExternalQuotationReviewDept initEditPageByDemandId() {
		ExternalQuotationReviewDept externalQuotationReviewDept;
		UserProfile user = this.getUserProfile();
		String externalId = CommonUtil.trim(this.getRequestPara("externalId"));
		if (externalId.length() > 0) {
			externalQuotationReviewDept = this.externalQuotationReviewDeptService.initEditOrViewPage(externalId, user);
		} else {
			String demandId = CommonUtil.trim(this.getRequestPara("demandId"));
			externalQuotationReviewDept = this.externalQuotationReviewDeptService.initExternalQuotationReviewDeptByDemandId(demandId, user);
		}
		return externalQuotationReviewDept;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param externalId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	ExternalQuotationReviewDept initEditOrViewPage(String externalId) {
		UserProfile user = this.getUserProfile();
		ExternalQuotationReviewDept externalQuotationReviewDept = this.externalQuotationReviewDeptService.initEditOrViewPage(externalId, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.externalQuotationReviewDeptService.getAuthCanExecute(user, externalQuotationReviewDept);
		}
		return externalQuotationReviewDept;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param externalQuotationReviewDept 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<ExternalQuotationReviewDept> saveOrUpdate(ExternalQuotationReviewDept externalQuotationReviewDept) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.externalQuotationReviewDeptService.getAuthCanExecute(user, externalQuotationReviewDept);
		Map<String, Object> valueMap = this.getPropValues(ExternalQuotationReviewDept.class);
		ExternalQuotationReviewDept entity = this.externalQuotationReviewDeptService.saveOrUpdate(user, externalQuotationReviewDept, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}

	/**
	 * 发送会议知会
	 * @param response
	 */
	@RequestMapping("/sendMeetNotifyMsg")
	public void sendMeetNotifyMsg(ExternalQuotationReviewDept externalQuotationReviewDept, HttpServletResponse response) {
		this.externalQuotationReviewDeptService.sendMeetNotifyMsg(externalQuotationReviewDept);
		String json = "{\"success\": true,\"msg\": \"\"}";
		HttpUtil.write(response, json);
	}

	/**
	 * 提交数据
	 * @param externalQuotationReviewDept
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<ExternalQuotationReviewDept> commit(ExternalQuotationReviewDept externalQuotationReviewDept) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.externalQuotationReviewDeptService.getAuthCanExecute(user, externalQuotationReviewDept);
		Map<String, Object> valueMap = this.getPropValues(ExternalQuotationReviewDept.class);
		valueMap.put("reviewClassification", this.getRequestPara("reviewClassification"));
		ExternalQuotationReviewDept entity = this.externalQuotationReviewDeptService.commit(user, externalQuotationReviewDept, valueMap);
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
		this.externalQuotationReviewDeptService.delete(user, externalIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData(Boolean isMeetReview) {
		return ExternalQuotationReviewDept.getSwfStatusMap(isMeetReview);
	}

}