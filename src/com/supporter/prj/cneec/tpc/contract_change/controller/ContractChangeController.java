package com.supporter.prj.cneec.tpc.contract_change.controller;

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

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChange;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChangeGoods;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractOrder;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractSeal;
import com.supporter.prj.cneec.tpc.contract_change.service.ContractChangeService;
import com.supporter.prj.cneec.tpc.contract_change.util.ContractChangeConstant;
import com.supporter.prj.cneec.tpc.order_change.entity.ContractChOrder;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.module.constant.ModuleConstant;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

@Controller
@RequestMapping("tpc/contractChange")
public class ContractChangeController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractChangeService contractChangeService;

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
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractChange contractChange,String contractId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractChangeService.getGrid(user, jqGrid, contractChange,contractId);
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
		this.contractChangeService.getContractAmountGrid(user, jqGrid, chId);
		return jqGrid;
	}

	/**
	 * 获取货物及服务明细
	 * @param request
	 * @param jqGridReq
	 * @param changeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGoodsGrid")
	public @ResponseBody
	JqGrid getGoodsGrid(HttpServletRequest request, JqGridReq jqGridReq, String chId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractChangeService.getGoodsGrid(user, jqGrid, chId);
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
		ContractChange derivativeChange = contractChangeService.get(changeId);
		double conChangeAmount = derivativeChange.getContractRmbAmount();
		if(conChangeAmount==contractRmbAmount) {
			return true;
		}
		return false;
	}

	@RequestMapping("getGoodsGridSingle")
	public @ResponseBody
	JqGrid getGoodsGridSingle(HttpServletRequest request, JqGridReq jqGridReq, String goodsId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractChangeService.getGoodsGridSingle(user, jqGrid, goodsId);
		return jqGrid;
	}
	@RequestMapping("getGoodsChildGrid")
	public @ResponseBody
	JqGrid getGoodsChildGrid(HttpServletRequest request, JqGridReq jqGridReq, String goodsId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractChangeService.getGoodsChildGrid(user, jqGrid, goodsId);
		return jqGrid;
	}
	/**
	 * 获取收款条件
	 * @param request
	 * @param jqGridReq
	 * @param changeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getCollectionTermsGrid")
	public @ResponseBody
	JqGrid getCollectionTermsGrid(HttpServletRequest request, JqGridReq jqGridReq, String chId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractChangeService.getCollectionTermsGrid(user, jqGrid, chId);
		return jqGrid;
	}

	/**
	 * 选择采购合同上线对象并赋值给当前对象
	 * @param changeId
	 * @param contractId
	 * @return
	 */
//	@RequestMapping("selectPurchaseContractToInit")
//	public @ResponseBody
//	OperResult<ContractChange> selectPurchaseContractToInit(ContractChange contractChange) {
//		ContractChange entity = this.contractChangeService.selectPurchaseContractToInit(contractChange, this.getUserProfile());
//		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
//	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param changeId
	 * @param map
	 */
	@ModelAttribute
	public void getContractChange(String changeId, Map<String, Object> map) {
		if (!StringUtils.isBlank(changeId)) {
			ContractChange contractChange = this.contractChangeService.get(changeId);
			if (contractChange != null) {
				map.put("contractChange", contractChange);
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
	ContractChange get(String changeId) {
		ContractChange contractChange = this.contractChangeService.get(changeId);
		return contractChange;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractChange 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<ContractOrder> saveOrUpdate(ContractOrder contractOrder) {
		UserProfile user = this.getUserProfile();
		// 权限验证
//		this.contractChangeService.getAuthCanExecute(user, contractOrder);
		Map<String, Object> valueMap = this.getPropValues(ContractOrder.class);
		ContractOrder entity = this.contractChangeService.saveOrUpdate(user, contractOrder, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}

	@RequestMapping("saveFirstDetail")
	public @ResponseBody
	OperResult<ContractChangeGoods> saveFirstDetail(ContractChangeGoods contractChangeGoods) {
		this.contractChangeService.saveFirstDetail(contractChangeGoods);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", null);
	}
	
	@RequestMapping("saveTwoDetail")
	public @ResponseBody
	OperResult<ContractChangeGoods> saveTwoDetail(ContractChangeGoods contractChangeGoods) {
		this.contractChangeService.saveTwoDetail(contractChangeGoods);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", null);
	}
	/**
	 * 提交数据
	 * @param contractChange
	 * @return
	 */
//	@RequestMapping("commit")
//	public @ResponseBody
//	OperResult<ContractChange> commit(ContractChange contractChange) {
//		UserProfile user = this.getUserProfile();
//		// 权限验证
//		//this.contractChangeService.getAuthCanExecute(user, contractChange);
////		Map<String, Object> valueMap = this.getPropValues(ContractChange.class);
//		ContractChange entity = this.contractChangeService.commit(user, contractChange);
//		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS, "提交成功", entity);
//	}

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
		this.contractChangeService.delete(user, changeIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}
	/**
	 * 获取合同类型码表
	 * @return Map <Integer, String>
	 */
	@RequestMapping("getChTypeMap")
	@ResponseBody
	public Map <Integer, String> getDepositTypeMap() {
		return ContractChange.chType.getMap();
	}
	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData() {
		return ContractChange.getSwfStatusMap();
	}

	/**
	 * 调整性质(可多选)
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/selectAdjustmentNatureData")
	public @ResponseBody
	List<CheckBoxVO> selectAdjustmentNatureData(String changeId,String chType) {
		
		return this.contractChangeService.getAdjustmentNatureList(CommonUtil.trim(changeId),chType);
	}

	/**
	 * 变更方式
	 * @return
	 */
	@RequestMapping(value = "/selectChangeModeData")
	public @ResponseBody
	Map<String, String> selectChangeModeData() {
		return ContractChangeConstant.getChangeModeMap();
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
//		return this.contractChangeService.getHasProtocolList(CommonUtil.trim(changeId));
//	}
	//根据选择的合同id来获取合同信息，并同时保存到数据库中
//		@RequestMapping("copyContract")
//		public @ResponseBody ContractChange copyContract(String contractId) {
//			UserProfile user = this.getUserProfile();
//			boolean addOldContract = false;
//			return contractChangeService.copyContract(contractId,user, addOldContract);
//		}
	/**
	 * 保存
	 * @param CompletionPlan
	 * @return
	 */
	@RequestMapping("saveChangeOrder")
	public @ResponseBody OperResult<ContractChange> saveChangeOrder(ContractChange contractChange) {
		UserProfile user = this.getUserProfile();
		ContractChange entity = this.contractChangeService.saveChangeOrder(user, contractChange);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS, null, entity);
	}
	/**
	 * 编辑或查看页面加载对象。
	 */
	@RequestMapping("initChangeOrder")
	@ResponseBody
	public ContractChange initChangeOrder(String changeId, String contractId, int chType) {
		UserProfile user = this.getUserProfile();
		ContractChange contractChange = contractChangeService.initChangeOrder(changeId, contractId, chType,user);
		return contractChange;
	}
	/**
	 * 编辑或查看页面加载对象。变更单审批页面
	 */
	@RequestMapping("initOrder")
	@ResponseBody
	public ContractChange initOrder(String changeId) {
		ContractChange contractChange = contractChangeService.initOrder(changeId);
		return contractChange;
	}
	/**
	 * 编辑或查看页面加载对象。用印审批页面
	 */
	@RequestMapping("initSeal")
	@ResponseBody
	public ContractSeal initSeal(String sealId) {
		ContractSeal contractSeal = contractChangeService.initSeal(sealId);
		return contractSeal;
	}
	
	/**
	 * 根据changeId查询contractSeal，用于点击导航菜单的查看页面
	 */
	@RequestMapping("getContractSealByChangeId")
	@ResponseBody
	public ContractSeal getContractSealByChangeId(String changeId) {
		ContractSeal contractSeal = contractChangeService.getContractSealByChangeId(changeId);
		return contractSeal;
	}
	/**
	 * 根据changeId查询contractOrder，用于点击导航菜单的查看页面
	 */
	@RequestMapping("getContractOrderByChangeId")
	@ResponseBody
	public ContractOrder getContractOrderByChangeId(String changeId) {
		ContractOrder contractOrder = contractChangeService.getContractOrderByChangeId(changeId);
		return contractOrder;
	}
	
	/**
	 * 编辑或查看页面加载对象。合同审批页面
	 */
	@RequestMapping("initContract")
	@ResponseBody
	public ContractOrder initContract(String chId) {
		ContractOrder contractOrder = contractChangeService.initContract(chId);
		return contractOrder;
	}
	/**
	 * 经办人只会
	 *
	 */
	@RequestMapping("callPaper")
	@ResponseBody
	public void callPaper(String chId) {
		contractChangeService.callPaper(chId);
	}
	/**
	 * 根据变更单ID获取信息到用印单
	 * @param changeId
	 * @return
	 */
	@RequestMapping("initSealPage")
	public @ResponseBody
	ContractSeal initSealPage(String changeId) {
		UserProfile user = this.getUserProfile();
		ContractSeal contractSeal = this.contractChangeService.initSealPage(changeId,user);
		return contractSeal;
	}
	/**
	 * 编辑或查看页面加载对象
	 * @param changeId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	ContractOrder initEditOrViewPage(String changeId) {
		UserProfile user = this.getUserProfile();
		ContractOrder contractOrder = this.contractChangeService.initEditOrViewPage(changeId, user);
		// 编辑权限验证
//		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
//		if (editOper) {
//			this.contractChangeService.getAuthCanExecute(user, contractOrder);
//		}
		return contractOrder;
	}
	/**
	 * 保存变更用印
	 * 
	 * @param CompletionPlan
	 * @return
	 */
	@RequestMapping("saveChangeSeal")
	public @ResponseBody OperResult<ContractSeal> saveChangeSeal(ContractSeal contractSeal) {
		UserProfile user = this.getUserProfile();
		ContractSeal entity = this.contractChangeService.saveChangeSeal(user, contractSeal);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS, null, entity);
	}
	/**
	 * 保存盖章人
	 */
	@RequestMapping("saveStamp")
	@ResponseBody
	public ContractSeal saveStamp(String sealId, String stampPersonId, String stampPerson,Date stampDate) {
		ContractSeal contractSeal = contractChangeService.saveStamp(sealId, stampPersonId, stampPerson,stampDate);
		return contractSeal;
	}
	@RequestMapping({ "checkOrderChange" })
	@ResponseBody
	public String checkOrderChange(String contractId) {
		return this.contractChangeService.checkOrderChange(contractId);
	}

	@RequestMapping({ "checkContractChOrder" })
	@ResponseBody
	public boolean checkContractChOrder(String changeId) {
		return this.contractChangeService.checkContractChOrder(changeId);
	}
	/**
	 *验证可用预算是否充足
	 * @param changeId
	 */
	@RequestMapping("checkBudgetAvailableAmount")
	public @ResponseBody
	String checkBudgetAvailableAmount(ContractChange contractChange) {
		return this.contractChangeService.checkBudgetAvailableAmount(contractChange);
	}
	/**
	 * 保存或更新数据.
	 * @param contractSignDeptInfor 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdateContractItem")
	public @ResponseBody
	OperResult<?> saveOrUpdateContractItem() {
		Map<String, Object> valueMap = this.getRequestParameters();
		String goodsId = this.contractChangeService.saveOrUpdateItem(valueMap);
		return OperResult.succeed("inforItem-saveSuccess", "保存成功", goodsId);
	}
	/**
	 * 判断付款条款是否可以被删除
	 * @return
	 */
	@RequestMapping("canDelTent")
	@ResponseBody
	public String canDelTent(String termsId) {
		return contractChangeService.canDelTent(termsId);
	}
	/**
	 * 保存合同额
	 * @return
	 */
	@RequestMapping("saveDetail")
	public @ResponseBody
	 boolean saveDetail(ContractOrder order) {
		this.contractChangeService.saveDetail(order);
		return true;
	}
}