package com.supporter.prj.cneec.tpc.derivative_contract_change.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractOrder;
import com.supporter.prj.cneec.tpc.contract_change.util.ContractChangeConstant;
import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeConChange;
import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeContractChange;
import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeSeal;
import com.supporter.prj.cneec.tpc.derivative_contract_change.service.DerivativeContractChangeService;
import com.supporter.prj.cneec.tpc.derivative_contract_change.util.DeriveChangeConstant;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.module.constant.ModuleConstant;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**
 * @Title: DerivativeContractOnlineController
 * @Description: 控制器类
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/derivativeContractChange")
public class DerivativeContractChangeController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private DerivativeContractChangeService derivativeContractChangeService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param contractChange
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, DerivativeContractChange contractChange,String contractId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.derivativeContractChangeService.getGrid(user, jqGrid, contractChange,contractId);
		return jqGrid;
	}

	/**
	 * 获取合同额
	 * @param request
	 * @param jqGridReq
	 * @param changeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getContractAmountGrid")
	public @ResponseBody
	JqGrid getContractAmountGrid(HttpServletRequest request, JqGridReq jqGridReq, String chId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.derivativeContractChangeService.getContractAmountGrid(user, jqGrid, chId);
		return jqGrid;
	}
	
	/**
	 * 查询上线合同额是否跟变更合同额相等
	 * @param changeId
	 * @param contractRmbAmount
	 * return true
	 */
	@RequestMapping("checkRmbAmount")
	public @ResponseBody
	boolean checkRmbAmount(String changeId, double contractRmbAmount) {
		DerivativeContractChange derivativeChange = derivativeContractChangeService.get(changeId);
		double orderChangerebAmount = derivativeChange.getContractRmbAmount();
		if(orderChangerebAmount==contractRmbAmount) {
			return true;
		}
		return false;
	}

	/**
	 * 获取货物及服务明细
	 * @param request
	 * @param jqGridReq
	 * @param contractId
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping("getGoodsGrid")
//	public @ResponseBody
//	JqGrid getGoodsGrid(HttpServletRequest request, JqGridReq jqGridReq, String contractId) throws Exception {
//		UserProfile user = this.getUserProfile();
//		JqGrid jqGrid = jqGridReq.initJqGrid(request);
//		this.derivativeContractChangeService.getGoodsGrid(user, jqGrid, contractId);
//		return jqGrid;
//	}

	/**
	 * 获取付款条件
	 * @param request
	 * @param jqGridReq
	 * @param contractId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getCollectionTermsGrid")
	public @ResponseBody
	JqGrid getCollectionTermsGrid(HttpServletRequest request, JqGridReq jqGridReq, String chId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.derivativeContractChangeService.getCollectionTermsGrid(user, jqGrid, chId);
		return jqGrid;
	}
	/**
	 * 选择衍生合同上线对象并赋值给当前对象
	 * @param orderChange
	 * @return
	 */
//	@RequestMapping("selectDerivativeContractToInit")
//	public @ResponseBody
//	OperResult<DerivativeContractChange> selectDerivativeContractToInit(DerivativeContractChange contractChange) {
//		DerivativeContractChange entity = this.derivativeContractChangeService.selectDerivativeContractToInit(contractChange, this.getUserProfile());
//		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
//	}
	
	/**
	 * 根据合同前置表ID初始化对象
	 * @return
	 */
//	@RequestMapping("initEditPageByPrepareId")
//	public @ResponseBody
//	DerivativeContractChange initEditPageByPrepareId() {
//		DerivativeContractChange contractChange = null;
//		UserProfile user = this.getUserProfile();
//		String contractId = CommonUtil.trim(this.getRequestPara("contractId"));
//		if (contractId.length() > 0) {
//			contractChange = this.derivativeContractChangeService.initEditOrViewPage(contractId, user);
//		} else {
//			String prepareId = CommonUtil.trim(this.getRequestPara("prepareId"));
//			if (prepareId.length() > 0)
//				contractChange = this.derivativeContractChangeService.initEditPageByPrepareId(prepareId, user);
//		}
//		return contractChange;
//	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param changeId
	 * @param map
	 */
	@ModelAttribute
	public void getDerivativeContractChange(String changeId, Map<String, Object> map) {
		if (!StringUtils.isBlank(changeId)) {
			DerivativeContractChange contractChange = this.derivativeContractChangeService.get(changeId);
			if (contractChange != null) {
				map.put("derivativeContractChange", contractChange);
			}
		}
	}
	

	/**
	 * 根据ID获取功能模块
	 * @param changeId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	DerivativeContractChange get(String changeId) {
		DerivativeContractChange contractChange = this.derivativeContractChangeService.get(changeId);
		return contractChange;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param changeId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	DerivativeConChange initEditOrViewPage(String changeId) {
		UserProfile user = this.getUserProfile();
		DerivativeConChange derivativeConChange = this.derivativeContractChangeService.initEditOrViewPage(changeId, user);
		// 编辑权限验证
//		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
//		if (editOper) {
//			this.derivativeContractChangeService.getAuthCanExecute(user, contractChange);
//		}
		return derivativeConChange;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractChange 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<DerivativeConChange> saveOrUpdate(DerivativeConChange derivativeConChange) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.derivativeContractChangeService.getAuthCanExecute(user, contractChange);
		Map<String, Object> valueMap = this.getPropValues(DerivativeConChange.class);
		DerivativeConChange entity = this.derivativeContractChangeService.saveOrUpdate(user, derivativeConChange, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}

	/**
	 * 提交数据
	 * @param contractChange
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<DerivativeContractChange> commit(DerivativeContractChange contractChange) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(DerivativeContractChange.class);
		DerivativeContractChange entity = this.derivativeContractChangeService.commit(user, contractChange);
		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS, "提交成功", entity);
	}
	/**
	 * 保存盖章人
	 */
	@RequestMapping("saveStamp")
	@ResponseBody
	public DerivativeSeal saveStamp(String sealId, String stampPersonId, String stampPerson,Date stampDate) {
		DerivativeSeal derivativeSeal = derivativeContractChangeService.saveStamp(sealId, stampPersonId, stampPerson,stampDate);
		return derivativeSeal;
	}
	/**
	 * 删除操作
	 * 
	 * @param changeIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String changeIds) {
		UserProfile user = this.getUserProfile();
		this.derivativeContractChangeService.delete(user, changeIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData() {
		return DerivativeContractChange.getSwfStatusMap();
	}
	/**
	 * 调整性质(可多选)
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/selectAdjustmentNatureData")
	public @ResponseBody
	List<CheckBoxVO> selectAdjustmentNatureData(String key,String chType) {
		String changeId = getRequestPara("changeId");
		return this.derivativeContractChangeService.getAdjustmentNatureList(CommonUtil.trim(changeId),chType);
	}

	/**
	 * 变更方式
	 * @return
	 */
	@RequestMapping(value = "/selectChangeModeData")
	public @ResponseBody
	Map<String, String> selectChangeModeData() {
		return DeriveChangeConstant.getChangeModeMap();
	}
	/**
	 * 增减类型
	 * @return
	 */
	@RequestMapping(value = "/selectChangeTypeData")
	public @ResponseBody
	Map<String, String> selectChangeTypeData() {
		return ContractChangeConstant.getChangeTypeMap();
	}
	/**
	 * 是否有协议
	 * @return
	 */
//	@RequestMapping(value = "/selectHasProtocolData")
//	public @ResponseBody
//	List<CheckBoxVO> selectHasProtocolData(String changeId) {
//		return this.derivativeContractChangeService.getHasProtocolList(CommonUtil.trim(changeId));
//	}
	/**
	 * 保存
	 * @param CompletionPlan
	 * @return
	 */
	@RequestMapping("saveChangeOrder")
	public @ResponseBody OperResult<DerivativeContractChange> saveChangeOrder(DerivativeContractChange derivativeContractChange) {
		UserProfile user = this.getUserProfile();
		DerivativeContractChange entity = this.derivativeContractChangeService.saveChangeOrder(user, derivativeContractChange);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS, null, entity);
	}
	/**
	 * 编辑或查看页面加载对象。
	 */
	@RequestMapping("initChangeOrder")
	@ResponseBody
	public DerivativeContractChange initChangeOrder(String changeId, String contractId, int chType) {
		UserProfile user = this.getUserProfile();
		DerivativeContractChange derivativeContractChange = derivativeContractChangeService.initChangeOrder(changeId, contractId, chType,user);
		return derivativeContractChange;
	}
	/**
	 * 编辑或查看页面加载对象。变更单审批页面
	 */
	@RequestMapping("initOrder")
	@ResponseBody
	public DerivativeContractChange initOrder(String changeId) {
		DerivativeContractChange derivativeContractChange = derivativeContractChangeService.initOrder(changeId);
		return derivativeContractChange;
	}
	/**
	 * 编辑或查看页面加载对象。用印审批页面
	 */
	@RequestMapping("initSeal")
	@ResponseBody
	public DerivativeSeal initSeal(String sealId) {
		DerivativeSeal derivativeSeal = derivativeContractChangeService.initSeal(sealId);
		return derivativeSeal;
	}
	/**
	 * 编辑或查看页面加载对象。合同审批页面
	 */
	@RequestMapping("initContract")
	@ResponseBody
	public DerivativeConChange initContract(String chId) {
		DerivativeConChange derivativeConChange = derivativeContractChangeService.initContract(chId);
		return derivativeConChange;
	}
	/**
	 * 经办人只会
	 *
	 */
	@RequestMapping("callPaper")
	@ResponseBody
	public void callPaper(String chId) {
		derivativeContractChangeService.callPaper(chId);
	}
	/**
	 * 根据变更单ID获取信息到用印单
	 * @param changeId
	 * @return
	 */
	@RequestMapping("initSealPage")
	public @ResponseBody
	DerivativeSeal initSealPage(String changeId) {
		UserProfile user = this.getUserProfile();
		DerivativeSeal derivativeSeal = this.derivativeContractChangeService.initSealPage(changeId,user);
		return derivativeSeal;
	}
	/**
	 * 保存变更用印
	 * 
	 * @param CompletionPlan
	 * @return
	 */
	@RequestMapping("saveChangeSeal")
	public @ResponseBody OperResult<DerivativeSeal> saveChangeSeal(DerivativeSeal derivativeSeal) {
		UserProfile user = this.getUserProfile();
		DerivativeSeal entity = this.derivativeContractChangeService.saveChangeSeal(user, derivativeSeal);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS, null, entity);
	}
	/**
	 * 根据changeId查询contractSeal，用于点击导航菜单的查看页面
	 */
	@RequestMapping("getContractSealByChangeId")
	@ResponseBody
	public DerivativeSeal getContractSealByChangeId(String changeId) {
		DerivativeSeal derivativeSeal = derivativeContractChangeService.getContractSealByChangeId(changeId);
		return derivativeSeal;
	}
	/**
	 * 根据changeId查询contractOrder，用于点击导航菜单的查看页面
	 */
	@RequestMapping("getContractOrderByChangeId")
	@ResponseBody
	public DerivativeConChange getContractOrderByChangeId(String changeId) {
		DerivativeConChange derivativeConChange = derivativeContractChangeService.getContractOrderByChangeId(changeId);
		return derivativeConChange;
	}
	/**
	 * 根据contractId查询变更表有无数据
	 * @param contractId
	 */
	@RequestMapping("checkOrderChange")
	public @ResponseBody
	String checkOrderChange(String contractId) {
		return this.derivativeContractChangeService.checkOrderChange(contractId);
	}
	
	/**
	 * 根据changeId查询变更上线表
	 * @param changeId
	 */
	@RequestMapping("checkContractChOrder")
	public @ResponseBody
	boolean checkContractChOrder(String changeId) {
		return this.derivativeContractChangeService.checkContractChOrder(changeId);
	}
	/**
	 *验证可用预算是否充足
	 * @param changeId
	 */
	@RequestMapping("checkBudgetAvailableAmount")
	public @ResponseBody
	String checkBudgetAvailableAmount(DerivativeContractChange derivativeContractChange) {
		return this.derivativeContractChangeService.checkBudgetAvailableAmount(derivativeContractChange);
	}
	/**
	 * 保存合同额
	 * @return
	 */
	@RequestMapping("saveDetail")
	public @ResponseBody
	 boolean saveDetail(DerivativeConChange order) {
		this.derivativeContractChangeService.saveDetail(order);
		return true;
	}
	/**
	 * 判断付款条款是否可以被删除
	 * @return
	 */
	@RequestMapping("canDelTent")
	@ResponseBody
	public String canDelTent(String termsId) {
		return derivativeContractChangeService.canDelTent(termsId);
	}
}