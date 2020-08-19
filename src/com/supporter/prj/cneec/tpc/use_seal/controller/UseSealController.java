package com.supporter.prj.cneec.tpc.use_seal.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.use_seal.entity.UseSeal;
import com.supporter.prj.cneec.tpc.use_seal.service.UseSealService;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.HttpUtil;

/**
 * @Title: UseSealController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-10-23
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/useSeal")
public class UseSealController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UseSealService useSealService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param useSeal
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, UseSeal useSeal) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.useSealService.getGrid(user, jqGrid, useSeal);
		return jqGrid;
	}

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param useSeal
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getDetailGrid")
	public @ResponseBody
	JqGrid getDetailGrid(HttpServletRequest request, JqGridReq jqGridReq, String useSealId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.useSealService.getDetailGrid(user, jqGrid, useSealId);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param sealId 记录主键ID，贸易导航主页保存页面需传递sealId值，否则会丢失未放入input字段值
	 * @param map
	 */
	@ModelAttribute
	public void getUseSeal(String sealId, Map<String, Object> map) {
		if (!StringUtils.isBlank(sealId)) {
			UseSeal useSeal = this.useSealService.get(sealId);
			if (useSeal != null) {
				map.put("useSeal", useSeal);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param sealId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	UseSeal get(String sealId) {
		UseSeal useSeal = this.useSealService.get(sealId);
		return useSeal;
	}

	/**
	 * 根据项目框架协议评审单ID实例化用印单
	 * @param reviewId
	 * @return
	 */
	@RequestMapping("initUseSealByProtocolReviewId")
	public @ResponseBody
	UseSeal initUseSealByProtocolReviewId(String protocolReviewId) {
		UserProfile user = this.getUserProfile();
		UseSeal useSeal = this.useSealService.initEditOrViewPageByProtocolReviewId(protocolReviewId, user);
		return useSeal;
	}

	/**
	 * 根据项目框架协议评审单ID获取用印单
	 * @param reviewId
	 * @return
	 */
	@RequestMapping("getUseSealByProtocolReviewId")
	public @ResponseBody
	UseSeal getUseSealByProtocolReviewId(String protocolReviewId) {
		UseSeal protocolReview = this.useSealService.getUseSealByProtocolReviewId(protocolReviewId);
		return protocolReview;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param sealId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	UseSeal initEditOrViewPage(String sealId) {
		UserProfile user = this.getUserProfile();
		UseSeal useSeal = this.useSealService.initEditOrViewPage(sealId, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.useSealService.getAuthCanExecute(user, useSeal);
		}
		return useSeal;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param useSeal 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<UseSeal> saveOrUpdate(UseSeal useSeal) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.useSealService.getAuthCanExecute(user, useSeal);
		Map<String, Object> valueMap = this.getPropValues(UseSeal.class);
		UseSeal entity = this.useSealService.saveOrUpdate(user, useSeal, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}

	/**
	 * 提交数据
	 * @param useSeal
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<UseSeal> commit(UseSeal useSeal) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.useSealService.getAuthCanExecute(user, useSeal);
		Map<String, Object> valueMap = this.getPropValues(UseSeal.class);
		UseSeal entity = this.useSealService.commit(user, useSeal, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS, "提交成功", entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param sealIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String sealIds) {
		UserProfile user = this.getUserProfile();
		this.useSealService.delete(user, sealIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping("initUseSealByPre")
	public @ResponseBody
	UseSeal initUseSealByPre() {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		UseSeal useSeal = this.useSealService.initUseSealByPre(valueMap, user);
		return useSeal;
	}

	@RequestMapping("saveUseSealByPre")
	public @ResponseBody
	OperResult<UseSeal> saveUseSealByPre(UseSeal useSeal) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		UseSeal entity = this.useSealService.saveUseSealByPre(useSeal, valueMap, user);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}

	/**
	 * 保存用印单并提交用印单
	 * @return
	 */
	@RequestMapping("saveAndSubmitByPre")
	public @ResponseBody
	OperResult<UseSeal> saveAndSubmitByPre(UseSeal useSeal) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		UseSeal entity = this.useSealService.saveAndSubmitByPre(useSeal, valueMap, user);
		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS, "提交成功", entity);
	}

	/**
	 * 流程中的保存
	 * 
	 * @param useSeal 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveProc")
	public @ResponseBody
	void saveProc(UseSeal useSeal, HttpServletResponse response) {
		this.useSealService.update(useSeal);
		String json = "{\"msg\": " + true + "}";
		HttpUtil.write(response, json);
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData() {
		return UseSeal.getSwfStatusMap();
	}

}