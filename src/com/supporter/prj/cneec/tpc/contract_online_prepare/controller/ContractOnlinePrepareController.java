package com.supporter.prj.cneec.tpc.contract_online_prepare.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.contract_online_prepare.entity.ContractOnlinePrepare;
import com.supporter.prj.cneec.tpc.contract_online_prepare.service.ContractOnlinePrepareService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: ContractOnlinePrepareController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2018-05-15
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/contractOnlinePrepare")
public class ContractOnlinePrepareController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractOnlinePrepareService contractOnlinePrepareService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param contractOnlinePrepare
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractOnlinePrepare contractOnlinePrepare) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		Map<String, Object> valueMap = this.getRequestParameters();
		this.contractOnlinePrepareService.getGrid(user, jqGrid, valueMap);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param prepareId
	 * @param map
	 */
	@ModelAttribute
	public void getContractOnlinePrepare(String prepareId, Map<String, Object> map) {
		if (!StringUtils.isBlank(prepareId)) {
			ContractOnlinePrepare contractOnlinePrepare = this.contractOnlinePrepareService.get(prepareId);
			if (contractOnlinePrepare != null) {
				map.put("contractOnlinePrepare", contractOnlinePrepare);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param prepareId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	ContractOnlinePrepare get(String prepareId) {
		ContractOnlinePrepare contractOnlinePrepare = this.contractOnlinePrepareService.get(prepareId);
		return contractOnlinePrepare;
	}

	/**
	 * 获取用印状态字典数据
	 */
	@RequestMapping(value = "/selectSealStatusData")
	public @ResponseBody
	Map<Integer, String> selectSealStatusData() {
		return ContractOnlinePrepare.getSealStatusMap();
	}

	/**
	 * 获取备案状态字典数据
	 */
	@RequestMapping(value = "/selectFilingStatusData")
	public @ResponseBody
	Map<Integer, String> selectFilingStatusData() {
		return ContractOnlinePrepare.getFilingStatusMap();
	}

	/**
	 * 获取信息上线状态字典数据
	 */
	@RequestMapping(value = "/selectOnlineStatusData")
	public @ResponseBody
	Map<Integer, String> selectOnlineStatusData() {
		return ContractOnlinePrepare.getOnlineStatusMap();
	}

}