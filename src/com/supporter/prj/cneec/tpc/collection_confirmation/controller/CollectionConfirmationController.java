package com.supporter.prj.cneec.tpc.collection_confirmation.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.collection_confirmation.entity.CollectionConfirmation;
import com.supporter.prj.cneec.tpc.collection_confirmation.entity.CollectionDetail;
import com.supporter.prj.cneec.tpc.collection_confirmation.service.CollectionConfirmationService;
import com.supporter.prj.cneec.tpc.custom.entity.CustomPayaccount;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**
 * @Title: CollectionConfirmationController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-11-17
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/collectionConfirmation")
public class CollectionConfirmationController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CollectionConfirmationService collectionConfirmationService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param collectionConfirmation
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, CollectionConfirmation collectionConfirmation) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.collectionConfirmationService.getGrid(user, jqGrid, collectionConfirmation);
		return jqGrid;
	}

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param collectionConfirmation
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getDetailGrid")
	public @ResponseBody
	JqGrid getDetailGrid(HttpServletRequest request, JqGridReq jqGridReq, String collectionId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.collectionConfirmationService.getDetailGrid(user, jqGrid, collectionId);
		return jqGrid;
	}

	/**
	 * 获取收款明细详细信息
	 * @param payaccountId
	 * @return
	 */
	@RequestMapping(value = "/getDetail")
	public @ResponseBody
	CollectionDetail getDetail(String detailId) {
		return collectionConfirmationService.getDetail(detailId);
	}
	
	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param collectionId
	 * @param map
	 */
	@ModelAttribute
	public void getCollectionConfirmation(String collectionId, Map<String, Object> map) {
		if (!StringUtils.isBlank(collectionId)) {
			CollectionConfirmation collectionConfirmation = this.collectionConfirmationService.get(collectionId);
			if (collectionConfirmation != null) {
				map.put("collectionConfirmation", collectionConfirmation);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param collectionId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	CollectionConfirmation get(String collectionId) {
		CollectionConfirmation collectionConfirmation = this.collectionConfirmationService.get(collectionId);
		return collectionConfirmation;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param collectionId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	CollectionConfirmation initEditOrViewPage(String collectionId) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		CollectionConfirmation collectionConfirmation = this.collectionConfirmationService.initEditOrViewPage(collectionId, valueMap, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.collectionConfirmationService.getAuthCanExecute(user, collectionConfirmation);
		}
		return collectionConfirmation;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param collectionConfirmation 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<CollectionConfirmation> saveOrUpdate(CollectionConfirmation collectionConfirmation) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.collectionConfirmationService.getAuthCanExecute(user, collectionConfirmation);
		Map<String, Object> valueMap = this.getPropValues(CollectionConfirmation.class);
		CollectionConfirmation entity = this.collectionConfirmationService.saveOrUpdate(user, collectionConfirmation, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}

	/**
	 * 提交数据
	 * @param collectionConfirmation
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<CollectionConfirmation> commit(CollectionConfirmation collectionConfirmation) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.collectionConfirmationService.getAuthCanExecute(user, collectionConfirmation);
		Map<String, Object> valueMap = this.getPropValues(CollectionConfirmation.class);
		CollectionConfirmation entity = this.collectionConfirmationService.commit(user, collectionConfirmation, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS, "提交成功", entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param collectionIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String collectionIds) {
		UserProfile user = this.getUserProfile();
		this.collectionConfirmationService.delete(user, collectionIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 删除操作
	 * 
	 * @param detailIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("deleteDetails")
	public @ResponseBody
	OperResult<?> deleteDetails(String detailIds) {
		this.collectionConfirmationService.deleteDetails(detailIds);
		return OperResult.succeed("collection_detail-deleteSuccess");
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData() {
		return CollectionConfirmation.getSwfStatusMap();
	}

	/**
	 * 获取收款业务
	 */
	@RequestMapping(value = "/selectCollectionBusinessData")
	public @ResponseBody
	List<CheckBoxVO> selectCollectionBusinessData(String key) {
		return this.collectionConfirmationService.getCollectionBusinessList(CommonUtil.trim(key));
	}

	/**
	 * 销售合同收款条款
	 * @return
	 */
	@RequestMapping(value = "/selectCollectionClauseData")
	public @ResponseBody
	Map<String, String> selectCollectionClauseData(String contractId) {
		return this.collectionConfirmationService.getCollectionClauseMap(contractId);
	}

	/**
	 * 预算项
	 * @return
	 */
	@RequestMapping(value = "/selectBudgetData")
	public @ResponseBody
	Map<String, String> selectBudgetData() {
		return TpcConstant.getBudgetItems(TpcConstant.TPC_BUDGET_SaleContract_ITEM);
	}

	@RequestMapping(value = "/changeBusiness")
	public  @ResponseBody CustomPayaccount changeBusiness(String businessPreposeId) {
		return collectionConfirmationService.changeBusiness(businessPreposeId);
	}
	
	@RequestMapping(value = "/changeAccount")
	public  @ResponseBody CustomPayaccount changeAccount(String payaccountId) {
		return collectionConfirmationService.changeAccount(payaccountId);
	}
	
	/**
	 * 第三方支付账号信息
	 * @return
	 */
	@RequestMapping(value = "/getPayAccountCodeTable")
	public @ResponseBody
	Map<String, String> getPayAccountCodeTable(String businessPreposeId) {
		Map <String,String > map = collectionConfirmationService.getPayAccountCodeTable(businessPreposeId);
		map.put("", "--请选择--");
		return map;
	}

}