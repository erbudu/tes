package com.supporter.prj.cneec.tpc.purchase_demand.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.purchase_demand.entity.PurchaseDemand;
import com.supporter.prj.cneec.tpc.purchase_demand.service.PurchaseDemandService;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: PurchaseDemandController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-09-06
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/purchaseDemand")
public class PurchaseDemandController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PurchaseDemandService purchaseDemandService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param purchaseDemand
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, PurchaseDemand purchaseDemand) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.purchaseDemandService.getGrid(user, jqGrid, purchaseDemand);
		return jqGrid;
	}

	/**
	 * 返回客户从表列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param demandId 需求单ID
	 * @param recordId 客户表ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getDetailGrid")
	public @ResponseBody
	JqGrid getDetailGrid(HttpServletRequest request, JqGridReq jqGridReq, String demandId, String recordId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.purchaseDemandService.getDetailGrid(user, jqGrid, demandId, recordId);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param demandId
	 * @param map
	 */
	@ModelAttribute
	public void getPurchaseDemand(String demandId, Map<String, Object> map) {
		if (!StringUtils.isBlank(demandId)) {
			PurchaseDemand purchaseDemand = this.purchaseDemandService.get(demandId);
			if (purchaseDemand != null) {
				map.put("purchaseDemand", purchaseDemand);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param demandId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	PurchaseDemand get(String demandId) {
		PurchaseDemand purchaseDemand = this.purchaseDemandService.get(demandId);
		return purchaseDemand;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param demandId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	PurchaseDemand initEditOrViewPage(String demandId) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		PurchaseDemand purchaseDemand = this.purchaseDemandService.initEditOrViewPage(demandId, valueMap, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.purchaseDemandService.getAuthCanExecute(user, purchaseDemand);
		}
		return purchaseDemand;
	}

	/**
	 * 拷贝对象
	 * @param demandIds
	 * @return
	 */
	@RequestMapping("copy")
	public @ResponseBody
	OperResult<?> copy(String demandIds) {
		UserProfile user = this.getUserProfile();
		this.purchaseDemandService.copy(user, demandIds, this.getRequestParameters());
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param purchaseDemand 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<PurchaseDemand> saveOrUpdate(PurchaseDemand purchaseDemand) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.purchaseDemandService.getAuthCanExecute(user, purchaseDemand);
		Map<String, Object> valueMap = this.getRequestParameters();
		PurchaseDemand entity = this.purchaseDemandService.saveOrUpdate(user, purchaseDemand, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}

	/**
	 * 提交数据
	 * @param purchaseDemand
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<PurchaseDemand> commit(PurchaseDemand purchaseDemand) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.purchaseDemandService.getAuthCanExecute(user, purchaseDemand);
		Map<String, Object> valueMap = this.getRequestParameters();
		PurchaseDemand entity = this.purchaseDemandService.commit(user, purchaseDemand, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS, "提交成功", entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param demandIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String demandIds) {
		UserProfile user = this.getUserProfile();
		this.purchaseDemandService.delete(user, demandIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData() {
		return PurchaseDemand.getSwfStatusMap();
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectCurrencyRateData")
	public @ResponseBody
	Map<String, String> selectCurrencyRateData() {
		return TpcConstant.getCurrencyRateMap();
	}

}