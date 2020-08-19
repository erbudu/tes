package com.supporter.prj.cneec.tpc.benefit_budget.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.benefit_budget.entity.BenefitContract;
import com.supporter.prj.cneec.tpc.benefit_budget.entity.VBenefitProject;
import com.supporter.prj.cneec.tpc.benefit_budget.service.BenefitBudgetService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.HttpUtil;

/**
 * @Title: BenefitBudgetController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2018-06-06
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/benefitBudget")
public class BenefitBudgetController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private BenefitBudgetService benefitBudgetService;

	/**
	 * 项目Grid
	 * 
	 * @param request
	 * @param jqGridReq
	 * @param benefitBudget
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, VBenefitProject benefitProject) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.benefitBudgetService.getGrid(jqGrid, benefitProject);
		return jqGrid;
	}

	/**
	 * 过程合同Grid
	 * 
	 * @param request
	 * @param jqGridReq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getContractGrid")
	public @ResponseBody
	JqGrid getContractGrid(HttpServletRequest request, JqGridReq jqGridReq) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.benefitBudgetService.getBenefitContractGrid(jqGrid, this.getRequestParameters());
		return jqGrid;
	}

	/**
	 * 获取效益预算集合表头
	 * 
	 * @param response
	 * @param projectId
	 * @throws Exception
	 */
	@RequestMapping("getBenefitBudgetAssembleTitleData")
	public void getBenefitBudgetAssembleTitleData(HttpServletResponse response, String projectId) throws Exception {
		boolean allBudget = Boolean.valueOf(this.getRequestPara("allBudget"));
		String json = this.benefitBudgetService.getBenefitBudgetAssembleTitleData(projectId, allBudget);
		HttpUtil.write(response, json);
	}

	/**
	 * 获取效益预算集合数据
	 * 
	 * @param response
	 * @param projectId
	 * @throws Exception
	 */
	@RequestMapping("getBenefitBudgetAssembleGrid")
	public void getBenefitBudgetAssembleGrid(HttpServletResponse response, String projectId) throws Exception {
		boolean allBudget = Boolean.valueOf(this.getRequestPara("allBudget"));
		String json = this.benefitBudgetService.getBenefitBudgetAssembleGrid(projectId, allBudget);
		HttpUtil.write(response, json);
	}
	
	/**
	 * 获取效益预算集合（项目过程）表头
	 * 
	 * @param response
	 * @param projectId
	 * @throws Exception
	 */
	@RequestMapping("getBenefitContractAssembleTitleData")
	public void getBenefitContractAssembleTitleData(HttpServletResponse response, String projectId) throws Exception {
		String json = this.benefitBudgetService.getBenefitContractAssembleTitleData(projectId);
		HttpUtil.write(response, json);
	}
	
	/**
	 * 获取效益预算集合（项目过程）数据
	 * 
	 * @param response
	 * @param projectId
	 * @throws Exception
	 */
	@RequestMapping("getBenefitContractAssembleGrid")
	public void getBenefitContractAssembleGrid(HttpServletResponse response, String projectId) throws Exception {
		String json = this.benefitBudgetService.getBenefitContractAssembleGrid(projectId);
		HttpUtil.write(response, json);
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * 
	 * @param projectId
	 * @param map
	 */
	@ModelAttribute
	public void getBenefitProject(String projectId, Map<String, Object> map) {
		if (!StringUtils.isBlank(projectId)) {
			VBenefitProject benefitProject = this.benefitBudgetService.get(projectId);
			if (benefitProject != null) {
				map.put("benefitProject", benefitProject);
			}
		}
	}

	/**
	 * 根据ID获取项目对象
	 * 
	 * @param projectId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	VBenefitProject get(String projectId) {
		VBenefitProject benefitProject = this.benefitBudgetService.get(projectId);
		return benefitProject;
	}

	/**
	 * 根据ID获取合同对象
	 * 
	 * @param projectId
	 * @return
	 */
	@RequestMapping("getContract")
	public @ResponseBody
	BenefitContract getContract(String processId) {
		BenefitContract benefitContract = this.benefitBudgetService.getBenefitContract(processId);
		return benefitContract;
	}

	/**
	 * 获取合同预算Grid
	 *
	 * @param request
	 * @param jqGridReq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getContractBudgetGrid")
	public @ResponseBody
	JqGrid getContractBudgetGrid(HttpServletRequest request, JqGridReq jqGridReq) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.benefitBudgetService.getBenefitContractBudgetGrid(jqGrid, this.getRequestParameters());
		return jqGrid;
	}

	/**
	 * 导出效益预算过程表
	 *
	 * @param response
	 * @param projectId
	 * @throws Exception
	 */
	@RequestMapping("downExcel")
	public @ResponseBody
	void downExcel(HttpServletResponse response, String projectId) throws Exception {
		// 下载文件名
		String downFileName = new String(StringUtils.trimToEmpty(this.getRequestPara("downFileName")).getBytes("ISO8859-1"), "UTF-8");
		// 是否全部预算
		boolean allBudget = Boolean.valueOf(this.getRequestPara("allBudget"));
		OutputStream os = null;
		UserProfile user = this.getUserProfile();
		try {
			Workbook wb = this.benefitBudgetService.getProjectWorkbook(projectId, downFileName, allBudget, user);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(downFileName, "utf-8"));
			os = response.getOutputStream();
			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			os.flush();
			os.close();
		}
	}

	/**
	 * 导出效益预算过程表
	 *
	 * @param response
	 * @param projectId
	 * @throws Exception
	 */
	@RequestMapping("downProcessExcel")
	public @ResponseBody
	void downProcessExcel(HttpServletResponse response, String projectId) throws Exception {
		// 下载文件名
		String downFileName = new String(StringUtils.trimToEmpty(this.getRequestPara("downFileName")).getBytes("ISO8859-1"), "UTF-8");
		OutputStream os = null;
		UserProfile user = this.getUserProfile();
		try {
			Workbook wb = this.benefitBudgetService.getProcessWorkbook(projectId, downFileName, user);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(downFileName, "utf-8"));
			os = response.getOutputStream();
			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			os.flush();
			os.close();
		}
	}

	/**
	 * 导出单合同效益预算表
	 *
	 * @param response
	 * @param processId
	 * @throws Exception
	 */
	@RequestMapping("downContractExcel")
	public @ResponseBody
	void downContractExcel(HttpServletResponse response, String processId) throws Exception {
		// 下载文件名
		String downFileName = new String(StringUtils.trimToEmpty(this.getRequestPara("downFileName")).getBytes("ISO8859-1"), "UTF-8");
		OutputStream os = null;
		response.setContentType("charset=GBK");
		UserProfile user = this.getUserProfile();
		try {
			Workbook wb = this.benefitBudgetService.getContractWorkbook(processId, downFileName, user);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(downFileName, "utf-8"));
			os = response.getOutputStream();
			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			os.flush();
			os.close();
		}
	}

}