package com.supporter.prj.ppm.contract.pact.seal_bfd.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.pact.review_ver.constant.OperResultConstant;
import com.supporter.prj.ppm.contract.pact.seal_bfd.entity.ContractPactPublish;
import com.supporter.prj.ppm.contract.pact.seal_bfd.service.ContractPactPublishService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("ppm/contract/pact/seal_bfd")
public class ContractPactPubController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	ContractPactPublishService service;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * @param publishId 主键
	 * @return
	 */
	@RequestMapping("/initEditOrViewPage")
	@ResponseBody
	public ContractPactPublish initEditOrViewPage(String publishId, String prjId) {
		UserProfile user = this.getUserProfile();
		ContractPactPublish entity = service.initEditOrViewPage(publishId, prjId, user);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getGrid")
	@ResponseBody
	public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractPactPublish entity) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.service.getGrid(user, jqGrid, entity);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * @param ContractPactPublish 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public OperResult<?> saveOrUpdate(ContractPactPublish ContractPactPublish) {
		UserProfile user = this.getUserProfile();
		ContractPactPublish entity = this.service.saveOrUpdate(user, ContractPactPublish);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * @param publishIds 主键集合，多个以逗号分隔
	 * @return 操作结果
	 */
	@RequestMapping("/batchDel")
	@ResponseBody
	public OperResult<?> batchDel(String publishId) {
		this.service.delete(publishId);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 提交
	 * @param publishId 评审验证主键
	 * @return  操作结果
	 */
	@RequestMapping("/valid")
	@ResponseBody
	public OperResult<?> valid(String publishId) {
		UserProfile user = this.getUserProfile();
		this.service.valid(publishId, user);
		return OperResult.succeed("操作成功", "操作成功", null);
	}

	/**
	 * 根据报审id获取协议出版对象
	 * @param reportId 合同及协议报审主键
	 * @return  协议出版实体对象
	 */
	@RequestMapping("/getPubByReportId")
	@ResponseBody
	public ContractPactPublish getPubByReportId(String reportId) {
		ContractPactPublish entity = service.getPubByReportId(reportId);
		return entity;
	}

	/**
	 * 初始化协议出版资料清单文件
	 */
	@RequestMapping("/initBfdFile")
	@ResponseBody
	public void initBfdFile(String reportId, String bfdsStr, String publishId) {
		UserProfile user = this.getUserProfile();
		service.initBfdFile(reportId, bfdsStr, publishId, user);
	}
	
	/**
	 *  获取某项目下审批结论最终通过且未协议出版的报审单号
	 * @param prjId 项目id
	 * @return map(报审id,报审单号)
	 */
	@RequestMapping("/getAllPassReport")
	@ResponseBody
	public Map<String, String> getAllPassReport(String prjId) {
		return service.getAllPassReport(prjId);
	}

	/**
	 * 协议出版编辑操作下,获取该条协议出版当前关联的报审单
	 * @return map(报审id,报审单号)
	 */
	@RequestMapping("/getPassReport")
	@ResponseBody
	public Map<String, String> getPassReport(String reportId) {
		return service.getPassReport(reportId);
	}


}

