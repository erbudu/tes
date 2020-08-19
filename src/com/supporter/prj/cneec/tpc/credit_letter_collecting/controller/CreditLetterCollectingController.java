package com.supporter.prj.cneec.tpc.credit_letter_collecting.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.cneec.tpc.credit_letter_collecting.entity.CreditLetterCollecting;
import com.supporter.prj.cneec.tpc.credit_letter_collecting.service.CreditLetterCollectingService;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**   
 * @Title: Controller
 * @Description: 报告.
 * @author liyinfeng
 * @date 2016-12-06 15:25:34
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("tpc/credit_letter_collecting")
public class CreditLetterCollectingController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Autowired
	private CreditLetterCollectingService reportService;

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, CreditLetterCollecting report) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.reportService.getGrid(user, jqGrid, report);
		return jqGrid;
	}
	
	/**
	 * 分页表格展示信用证付款明细数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getRecGrid")
	public @ResponseBody JqGrid getRecGrid(HttpServletRequest request, JqGridReq jqGridReq, String creditLetterCollectingId) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		UserProfile user = this.getUserProfile();
		this.reportService.getRecGrid(user,jqGrid, creditLetterCollectingId);
		return jqGrid;
	}
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param reportId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	@ResponseBody
	public CreditLetterCollecting initEditOrViewPage(String creditLetterCollectingId, String creditLetterId) {
		CreditLetterCollecting entity = reportService.initEditOrViewPage(creditLetterCollectingId, creditLetterId,this.getUserProfile());
		return entity;
	}
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param reportId 主键
	 * @return
	 */
	@RequestMapping("viewPage")
	@ResponseBody
	public CreditLetterCollecting viewPage(String creditLetterCollectingId) {
		CreditLetterCollecting entity = reportService.viewPage(creditLetterCollectingId,this.getUserProfile());
		return entity;
	}
	
	
	/**
	 * 根据业务对象获取流程实例ID.
	 * @param report
	 * @return
	 */
	@RequestMapping("/getProcId")
	@ResponseBody
	public String getProcId(CreditLetterCollecting report){
		return reportService.getProcId(report);
	}
	
	/**
	 * 作废或生效数据.
	 * @param report
	 * @return
	 */
	@RequestMapping("changeStatus")
	public @ResponseBody OperResult changeStatus(String creditLetterCollectingId){
//		reportService.changeStatus(creditLetterCollectingId);
		return OperResult.succeed("changeSuccess");
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param report 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
//	@AuthCheck(module = ReportConstant.MODULE_ID, 
//     oper = AuthConstant.AUTH_OPER_NAME_SETVALREPORT, 
//     failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody OperResult<CreditLetterCollecting> saveOrUpdate(CreditLetterCollecting report) {
		UserProfile user = this.getUserProfile();
		//权限验证
//		canExecute(user, AuthConstant.AUTH_OPER_NAME_SETVALREPORT, report.getReportId(), report);
		Map< String, Object > valueMap = this.getPropValues(CreditLetterCollecting.class);
		return this.reportService.saveOrUpdate(user, report, valueMap);
	}

	/**
	 * 删除操作
	 * 
	 * @param reportIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String creditLetterCollectingIds) {
		UserProfile user = this.getUserProfile();
		
		this.reportService.delete(user, creditLetterCollectingIds);
		return OperResult.succeed("deleteSuccess");
	}

	/**
	 * 获取合同控制状态字典数据
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getControlStatusCodeTable")
	public Map<String, String> getControlStatusCodeTable() throws IOException {
		Map<String, String> map = new HashMap();//CreditLetterCollecting.getControlStatusCodeTable();
		return map;
	}
	/**
	 * 获取付款状态字典数据
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getSettlementStatusCodeTable")
	public Map<Integer, String> getSettlementStatusCodeTable() throws IOException {
		Map<Integer, String> map = CreditLetterCollecting.getSettlementStatusCodeTable();
		return map;
	}
	
	/**
	 * 获取币种字典数据
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getCollectingClauseCodeTable")
	public Map<String, String> getCollectingClauseCodeTable() throws IOException {
		return CreditLetterCollecting.getCollectingClauseCodeTable();
	}
	/**
	 * 获取币种字典数据
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getBudgetItemCodeTable")
	public Map<String, String> getBudgetItemCodeTable(String prjId) throws IOException {
		return reportService.getBudgetItemCodeTable(prjId);
	}

	/**
	 * 根据销售合同获取收取的信用证
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getCreditLetterCodeTable")
	public Map<String, String> getCreditLetter(String projectId, String contractId) throws Exception {
		/*Map<String, Object> valueMap = this.getRequestParameters();
		System.out.println(contractId);
		if (StringUtils.isBlank(contractId) && valueMap.containsKey("value") && valueMap.get("value") != null) {
			
		}*/
		return reportService.getCreditLetter(projectId, contractId);
	}

}
