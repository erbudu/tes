package com.supporter.prj.cneec.tpc.receive_credit_letter.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.receive_credit_letter.entity.ReceiveCreditLetter;
import com.supporter.prj.cneec.tpc.receive_credit_letter.service.ReceiveCreditLetterService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
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
@RequestMapping("tpc/receive_credit_letter")
public class ReceiveCreditLetterController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Autowired
	private ReceiveCreditLetterService reportService;

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ReceiveCreditLetter report) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.reportService.getGrid(user, jqGrid, report);
		return jqGrid;
	}

	@ModelAttribute
	public void getReceiveCreditLetter(String receiveCreditLetterId, Map<String, Object> map) {
		if (!StringUtils.isBlank(receiveCreditLetterId)) {
			ReceiveCreditLetter receiveCreditLetter = this.reportService.get(receiveCreditLetterId);
			if (receiveCreditLetter != null) {
				map.put("receiveCreditLetter", receiveCreditLetter);
			}
		}
	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param reportId
	 *            主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	@ResponseBody
	public ReceiveCreditLetter initEditOrViewPage(String receiveCreditLetterId) {
		ReceiveCreditLetter entity = reportService.initEditOrViewPage(receiveCreditLetterId, this.getUserProfile());
		return entity;
	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param reportId
	 *            主键
	 * @return
	 */
	@RequestMapping("viewPage")
	@ResponseBody
	public ReceiveCreditLetter viewPage(String receiveCreditLetterId) {
		ReceiveCreditLetter entity = reportService.get(receiveCreditLetterId);
		return entity;
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
	public @ResponseBody OperResult<ReceiveCreditLetter> saveOrUpdate(ReceiveCreditLetter report) {
		UserProfile user = this.getUserProfile();
		//权限验证
//		canExecute(user, AuthConstant.AUTH_OPER_NAME_SETVALREPORT, report.getReportId(), report);
		Map< String, Object > valueMap = this.getPropValues(ReceiveCreditLetter.class);
		return this.reportService.saveOrUpdate(user, report, valueMap);
	}

	/**
	 * 删除操作
	 * 
	 * @param reportIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String receiveCreditLetterIds) {
		UserProfile user = this.getUserProfile();
		
		this.reportService.delete(user, receiveCreditLetterIds);
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
	 * 获取付款状态字典数据
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getSettlementStatusCodeTable")
	public Map<Integer, String> getSettlementStatusCodeTable() throws IOException {
		Map<Integer, String> map = ReceiveCreditLetter.getSettlementStatusCodeTable();
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
