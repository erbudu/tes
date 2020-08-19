package com.supporter.prj.linkworks.oa.salary.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.salary.entity.Salary;
import com.supporter.prj.linkworks.oa.salary.service.SalaryService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("oa/salary")
public class SalaryController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private SalaryService salaryService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * 
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq,
			Salary salary) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.salaryService.getGrid(user, jqGrid, salary);
		return jqGrid;
	}
	
	
	/**
	 * 返回列表. 分页表格展示数据.
	 * 
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGridByOwnerId")
	public @ResponseBody
	JqGrid getGridByOwnerId(HttpServletRequest request, JqGridReq jqGridReq,String dateFrom,String dateTo) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.salaryService.getGridByOwnerId(user, jqGrid,dateFrom,dateTo);
		return jqGrid;
	}
	

	/**
	 * 根据主键获取功能模块�?.
	 * 
	 * @param reportId
	 *            主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody
	Salary get(String recordId) {
		Salary salary = salaryService.get(recordId);
		return salary;

	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param recordId
	 *            主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	Salary initEditOrViewPage(String recordId) {
		Salary entity = salaryService.initEditOrViewPage(recordId, this
				.getUserProfile());
		return entity;
	}

	/**
	 * 保存或更新数据
	 * 
	 * @param report
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<Salary> saveOrUpdate(Salary salary) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(Salary.class);
		Salary entity = this.salaryService.saveOrUpdate(user,
				salary, valueMap);
//		return OperResult.succeed(SalaryConstant.I18nKey.SAVE_SUCCESS,
//				null, null);
		return OperResult.succeed("saveSuccess", null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param reportIds
	 *            主键集合，多个以逗号分隔
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult batchDel(String recordIds) {
		UserProfile user = this.getUserProfile();
		this.salaryService.delete(user, recordIds);
//		return OperResult.succeed(SalaryConstant.I18nKey.DELETE_SUCCESS,
//				null, null);
		return OperResult.succeed("deleteSuccess", null, null);
	}

	/**
	 * 按月删除工资操作
	 * 
	 * @param reportIds
	 *            主键集合，多个以逗号分隔
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("deleteMonthSalary")
	public @ResponseBody
	OperResult deleteMonthSalary(String salaryOfYear,String salaryOfMonth) {
		UserProfile user = this.getUserProfile();
		if(salaryOfMonth.length()==1){
			salaryOfMonth="0"+salaryOfMonth;
		}
		String salaryMonth=salaryOfYear+"年"+salaryOfMonth+"月";
		
		this.salaryService.deleteMonthSalary(user, salaryMonth);
//		return OperResult.succeed(SalaryConstant.I18nKey.DELETE_SUCCESS,
//				null, null);
		return OperResult.succeed("deleteSuccess", null, null);
	}
	
	
	/**
	 * 获取字典数据-用于按月删除年份的下拉显示
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getYearCodetable")
	public Map<Integer, String> getYearCodetable()
			throws IOException {
		Map<Integer, String> map = new HashMap<Integer, String>();
		for(int i=1970;i<2051;i++){
			map.put(i,i+"年");
		}
		return map;
	}
	
	/**
	 * 获取字典数据-用于按月删除月份的下拉显示
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getMonthCodetable")
	public Map<Integer, String> getMonthCodetable()
			throws IOException {
		Map<Integer, String> map = new HashMap<Integer, String>();
		for(int i=1;i<13;i++){
			map.put(i,i+"月");
		}
		return map;
	}
	
	@RequestMapping("downExcel")
	public @ResponseBody void downExcel(HttpServletRequest request, HttpServletResponse response,
			Salary salary,String empName) throws Exception {
		String name = request.getParameter("empName");//此行代码由于系统字符集问题与测试系统不一致，若有修改请慎重！
		salary.setEmpName(name);
		//System.out.println(salary.getEmpName()+"----"+salary.getDeptId()+"---"+salary.getDateFrom()+"---"+salary.getDateTo());
		
		OutputStream os = null;
		UserProfile user = this.getUserProfile();
		try {
			String fileName = "工资导出表.xls";
//			if (salary != null) {
//				String year = salary.getYear();
//				if (StringUtils.isNotBlank(year)) {
//					fileName = year + "年人员积分.xls";
//				} else {
//					return null;
//				}
//			}
			HSSFWorkbook wb = this.salaryService.getWorkbook(salary, user);
			//System.out.println(wb.);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ URLEncoder.encode(fileName, "utf-8"));
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
