package com.supporter.prj.cneec.tpc.credit_letter_settlement.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.cneec.tpc.credit_letter_settlement.entity.CreditLetterSettlement;
import com.supporter.prj.cneec.tpc.credit_letter_settlement.service.CreditLetterSettlementService;
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
@RequestMapping("tpc/credit_letter_settlement")
public class CreditLetterSettlementController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Autowired
	private CreditLetterSettlementService reportService;

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, CreditLetterSettlement report) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.reportService.getGrid(user, jqGrid, report, this.getRequestParameters());
		return jqGrid;
	}
	
	/**
	 * 分页表格展示信用证付款明细数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getRecGrid")
	public @ResponseBody JqGrid getRecGrid(HttpServletRequest request, JqGridReq jqGridReq, String creditLetterSettlementId) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		UserProfile user = this.getUserProfile();
		this.reportService.getRecGrid(user,jqGrid, creditLetterSettlementId);
		return jqGrid;
	}
	
	/**
     * 返回列表.
	 * 分页表格展示数据.根据
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getPastGrid")
	public @ResponseBody JqGrid getPastGrid(HttpServletRequest request, JqGridReq jqGridReq, CreditLetterSettlement report, String creditLetterId) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		String contractId = "";
		if(creditLetterId != null && !"".equals(creditLetterId)){
			contractId = this.reportService.getContractId(creditLetterId);
		}
		this.reportService.getPastGrid(user, jqGrid, report, contractId);
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
	public CreditLetterSettlement initEditOrViewPage(String creditLetterSettlementId, String creditLetterId) {
		CreditLetterSettlement entity = reportService.initEditOrViewPage(creditLetterSettlementId, creditLetterId,this.getUserProfile());
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
	public CreditLetterSettlement viewPage(String creditLetterSettlementId) {
		CreditLetterSettlement entity = reportService.viewPage(creditLetterSettlementId,this.getUserProfile());
		return entity;
	}
	
	/**
	 * 作废或生效数据.
	 * @param report
	 * @return
	 */
	@RequestMapping("changeStatus")
	public @ResponseBody OperResult changeStatus(String creditLetterSettlementId){
		reportService.changeStatus(creditLetterSettlementId);
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
	public @ResponseBody OperResult<CreditLetterSettlement> saveOrUpdate(CreditLetterSettlement report) {
		UserProfile user = this.getUserProfile();
		//权限验证
//		canExecute(user, AuthConstant.AUTH_OPER_NAME_SETVALREPORT, report.getReportId(), report);
		Map< String, Object > valueMap = this.getPropValues(CreditLetterSettlement.class);
		return this.reportService.saveOrUpdate(user, report, valueMap);
	}

	/**
	 * 删除操作
	 * 
	 * @param reportIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String creditLetterSettlementIds) {
		UserProfile user = this.getUserProfile();
		
		this.reportService.delete(user, creditLetterSettlementIds);
		return OperResult.succeed("deleteSuccess");
	}
//	private void canExecute(UserProfile user, String operInnerName, String entityId, Object entityObj) {
//	    IModule module = EIPService.getModuleService().getModule(ReportConstant.MODULE_ID);
//	    IOper oper = EIPService.getModuleService().getOper(operInnerName, module);
//	    boolean canAccess = EIPService.getAuthorityService().canAccess(user, oper, entityId, entityObj);
//	    if (!canAccess) {
//	      throw new UnauthorizedAccessException("AUTH-002");
//	    }
//	  }
	/**
	 * 获取合同控制状态字典数据
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getControlStatusCodeTable")
	public Map<String, String> getControlStatusCodeTable() throws IOException {
		Map<String, String> map = CreditLetterSettlement.getControlStatusCodeTable();
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
		Map<Integer, String> map = CreditLetterSettlement.getSettlementStatusCodeTable();
		return map;
	}
	
	/**
	 * 获取币种字典数据
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getCurrencyTypeCodeTable")
	public Map<String, String> getCurrencyTypeCodeTable() throws IOException {
		return this.reportService.getCurrencyTypeCodeTable();
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
}
