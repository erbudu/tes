package com.supporter.prj.cneec.tpc.protocol_review.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.protocol_review.entity.ProtocolReview;
import com.supporter.prj.cneec.tpc.protocol_review.service.ProtocolReviewService;
import com.supporter.prj.cneec.tpc.protocol_review.util.ProtocolReviewConstant;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**
 * @Title: ProtocolReviewController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-09-06
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/protocolReview")
public class ProtocolReviewController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ProtocolReviewService protocolReviewService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param protocolReview
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ProtocolReview protocolReview) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.protocolReviewService.getGrid(user, jqGrid, protocolReview);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param reviewId
	 * @param map
	 */
	@ModelAttribute
	public void getProtocolReview(String reviewId, Map<String, Object> map) {
		if (!StringUtils.isBlank(reviewId)) {
			ProtocolReview protocolReview = this.protocolReviewService.get(reviewId);
			if (protocolReview != null) {
				map.put("protocolReview", protocolReview);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param reviewId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	ProtocolReview get(String reviewId) {
		ProtocolReview protocolReview = this.protocolReviewService.get(reviewId);
		return protocolReview;
	}

	/**
	 * 根据项目ID实例化框架协议评审单
	 * @param reviewId
	 * @return
	 */
	@RequestMapping("initProtocolReviewByProjectId")
	public @ResponseBody
	ProtocolReview initProtocolReviewByProjectId(String projectId) {
		UserProfile user = this.getUserProfile();
		ProtocolReview protocolReview = this.protocolReviewService.initEditOrViewPageByProjectId(projectId, user);
		return protocolReview;
	}

	/**
	 * 根据项目ID获取框架协议评审单
	 * @param reviewId
	 * @return
	 */
	@RequestMapping("getProtocolReviewByProjectId")
	public @ResponseBody
	ProtocolReview getProtocolReviewByProjectId(String projectId) {
		ProtocolReview protocolReview = this.protocolReviewService.getProtocolReviewByProjectId(projectId);
		return protocolReview;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param reviewId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	ProtocolReview initEditOrViewPage(String reviewId) {
		UserProfile user = this.getUserProfile();
		ProtocolReview protocolReview = this.protocolReviewService.initEditOrViewPage(reviewId, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.protocolReviewService.getAuthCanExecute(user, protocolReview);
		}
		return protocolReview;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param protocolReview 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<ProtocolReview> saveOrUpdate(ProtocolReview protocolReview) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.protocolReviewService.getAuthCanExecute(user, protocolReview);
		Map<String, Object> valueMap = this.getPropValues(ProtocolReview.class);
		ProtocolReview entity = this.protocolReviewService.saveOrUpdate(user, protocolReview, valueMap);
		return OperResult.succeed("saveSuccess", "保存成功", entity);
	}

	/**
	 * 提交数据
	 * @param protocolReview
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<ProtocolReview> commit(ProtocolReview protocolReview) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.protocolReviewService.getAuthCanExecute(user, protocolReview);
		Map<String, Object> valueMap = this.getPropValues(ProtocolReview.class);
		ProtocolReview entity = this.protocolReviewService.commit(user, protocolReview, valueMap);
		return OperResult.succeed("saveSuccess", "保存成功", entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param reviewIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String reviewIds) {
		UserProfile user = this.getUserProfile();
		this.protocolReviewService.delete(user, reviewIds);
		return OperResult.succeed("deleteSuccess");
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData() {
		return ProtocolReview.getSwfStatusMap();
	}

	/**
	 * 获取签约评审结论
	 */
	@RequestMapping(value = "/selectReviewConclusionData")
	public @ResponseBody
	Map<Integer, String> selectReviewConclusionData() {
		return ProtocolReviewConstant.getReviewConclusionMap();
	}
	
	/**
	 * 获取签约评审结论
	 */
	@RequestMapping(value = "/selectReviewConclusionData1")
	public @ResponseBody
	List<CheckBoxVO> selectReviewConclusionData1(String reviewId) {
		return this.protocolReviewService.getReviewConclusionList(CommonUtil.trim(reviewId));
	}

}