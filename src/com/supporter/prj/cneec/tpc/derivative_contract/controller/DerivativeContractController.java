package com.supporter.prj.cneec.tpc.derivative_contract.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.derivative_contract.entity.DerivativeContract;
import com.supporter.prj.cneec.tpc.derivative_contract.service.DerivativeContractService;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;
import com.supporter.util.HttpUtil;

/**
 * @Title: DerivativeContractController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2018-05-21
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/derivativeContract")
public class DerivativeContractController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private DerivativeContractService derivativeContractService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param derivativeContract
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, DerivativeContract derivativeContract) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.derivativeContractService.getGrid(user, jqGrid, derivativeContract);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param derivativeId
	 * @param map
	 */
	@ModelAttribute
	public void getDerivativeContract(String derivativeId, Map<String, Object> map) {
		if (!StringUtils.isBlank(derivativeId)) {
			DerivativeContract derivativeContract = this.derivativeContractService.get(derivativeId);
			if (derivativeContract != null) {
				map.put("derivativeContract", derivativeContract);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param derivativeId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	DerivativeContract get(String derivativeId) {
		DerivativeContract derivativeContract = this.derivativeContractService.get(derivativeId);
		return derivativeContract;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param derivativeId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	DerivativeContract initEditOrViewPage(String derivativeId) {
		UserProfile user = this.getUserProfile();
		DerivativeContract derivativeContract = this.derivativeContractService.initEditOrViewPage(derivativeId, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.derivativeContractService.getAuthCanExecute(user, derivativeContract);
		}
		return derivativeContract;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param derivativeContract 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<DerivativeContract> saveOrUpdate(DerivativeContract derivativeContract) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(DerivativeContract.class);
		DerivativeContract entity = this.derivativeContractService.saveOrUpdate(user, derivativeContract, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}

	/**
	 * 提交数据
	 * @param derivativeContract
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<DerivativeContract> commit(DerivativeContract derivativeContract) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(DerivativeContract.class);
		DerivativeContract entity = this.derivativeContractService.commit(user, derivativeContract, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS, "提交成功", entity);
	}

	/**
	 * 验证销售合同效益测算表审批完成并提交
	 * @param response
	 */
	@RequestMapping("/validCommit")
	public void validCommit(DerivativeContract derivativeContract, HttpServletResponse response) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		String json = this.derivativeContractService.validCommit(user, derivativeContract, valueMap);
		HttpUtil.write(response, json);
	}

	/**
	 * 删除操作
	 * 
	 * @param derivativeIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String derivativeIds) {
		UserProfile user = this.getUserProfile();
		this.derivativeContractService.delete(user, derivativeIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 获取合同付款类型
	 */
	@RequestMapping(value = "/selectPaymentTypeData")
	public @ResponseBody
	List<CheckBoxVO> selectPaymentTypeData(String key) {
		return this.derivativeContractService.getPaymentTypeList(CommonUtil.trim(key));
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData() {
		return DerivativeContract.getSwfStatusMap();
	}

}