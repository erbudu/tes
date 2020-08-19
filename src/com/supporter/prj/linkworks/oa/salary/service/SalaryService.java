package com.supporter.prj.linkworks.oa.salary.service;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.membership_dues.service.MembershipDuesService;
import com.supporter.prj.linkworks.oa.salary.dao.SalaryDao;
import com.supporter.prj.linkworks.oa.salary.entity.Salary;
import com.supporter.prj.linkworks.oa.salary.util.AuthConstant;
import com.supporter.prj.linkworks.oa.salary.util.AuthUtil;
import com.supporter.prj.linkworks.oa.salary.util.ExportUtils;
import com.supporter.prj.linkworks.oa.salary.util.LogConstant;
import com.supporter.util.CommonUtil;

@Service
public class SalaryService {
	@Autowired
	private SalaryDao salaryDao;
	@Autowired
	private MembershipDuesService membershipDuesService;
	public Salary get(String moduleId) {
		return salaryDao.get(moduleId);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param moduleId
	 * @return
	 */
	public Salary initEditOrViewPage(String recordId, UserProfile user) {
		if (StringUtils.isBlank(recordId)) {// 新建
			Salary salary = newSalary(user);
		
			salary.setAdd(true);
			return salary;
		} else {// 编辑
			// 获得主表
			Salary salary = salaryDao.get(recordId);
			salary.setAdd(false);
			return salary;
		}
	}

	/**
	 * 新建实例,并初始化必要的属性.
	 * 
	 * @param auserprf_U
	 * @return
	 */
	public Salary newSalary(UserProfile auserprf_U) {
		Salary lsalary_N = new Salary();
		lsalary_N.setRecordId(com.supporter.util.UUIDHex.newId());
		lsalary_N.setBaoXiaoYaoFei(0d);
		lsalary_N.setBuChongYangLao(0d);
		lsalary_N.setBuFaYangLao(0d);
		lsalary_N.setDaiKouShui(0d);
		lsalary_N.setFangBu(0d);
		lsalary_N.setFangZu(0d);
		lsalary_N.setFuLiBuTile(0d);
		lsalary_N.setGangWeiGongZi(0d);
		lsalary_N.setGongLing(0d);
		lsalary_N.setGongZiKouKuan(0d);
		lsalary_N.setKouBuChongYangLao(0d);
		lsalary_N.setKouKuanHeJi(0d);
		lsalary_N.setQiTaBuFa(0d);
		lsalary_N.setQiTaKouKuan(0d);
		lsalary_N.setQuNuanBuTie(0d);
		lsalary_N.setShiFaHeJi(0d);
		lsalary_N.setShiYeBaoXian(0d);
		lsalary_N.setSiLing(0d);
		lsalary_N.setWuCanKouKuan(0d);
		lsalary_N.setXueLiZhiCheng(0d);
		lsalary_N.setYangLaoBaoXian(0d);
		lsalary_N.setYaoFei(0d);
		lsalary_N.setYiLiaoBaoXian(0d);
		lsalary_N.setYingFaHeJi(0d);
		lsalary_N.setYuZhiJiangJin(0d);		
		lsalary_N.setJiaoTongFei(0d);	
		lsalary_N.setTongXunFei(0d);
		return lsalary_N;
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user
	 *            用户信息
	 * @param jqGridReq
	 *            jqgrid请求对象
	 * @param moduleIds
	 *            多个逗号分隔
	 * @return JqGrid
	 */
	public List<Salary> getGrid(UserProfile user, JqGrid jqGrid,
			Salary salary) {
		List<Salary> list = this.salaryDao.findPage(user,jqGrid,salary);	
		return list;

	}
	
	
	/**
	 * 当前登录人查看自己的工资.
	 * 
	 * @param user
	 *            用户信息
	 * @return JqGrid
	 */
	public List<Salary> getGridByOwnerId(UserProfile user, JqGrid jqGrid,String dateFrom,String dateTo) {		
		List<Salary> list = this.salaryDao.findPageByOwnerId(jqGrid,user.getPersonId(),dateFrom,dateTo);	
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				double dangfeiyingjiaoe=membershipDuesService.findMembershipDues(list.get(i));
				list.get(i).setDangfeiyingjiaoe(dangfeiyingjiaoe);
				list.set(i, list.get(i));
			}
		}
		return list;

	}


	/**
	 * 保存或更新
	 * 
	 * @param user
	 *            用户信息
	 * @param module
	 *            实体类
	 * @return
	 */
	public Salary saveOrUpdate(UserProfile user, Salary salary,
			Map<String, Object> valueMap) {
		Salary ret = null;
		if (salary.isAdd()) {// 新建
			// 保存主表
			this.salaryDao.save(salary);
			ret = salary;
/*			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.PUBLISH_REPORT_LOG_MESSAGE, salary.getSalaryMonth());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					user, LogConstant.PUBLISH_REPORT_LOG_ACTION, logMessage,
					salary, null);*/
		} else {// 编辑
			// 编辑之后保存主表
			this.salaryDao.update(salary);
			ret = salary;
/*			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.PUBLISH_REPORT_LOG_MESSAGE, salary.getSalaryMonth());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					user, LogConstant.PUBLISH_REPORT_LOG_ACTION, logMessage,
					salary, null);*/
		}
		return ret;

	}


	/**
	 * 删除
	 * 
	 * @param user
	 *            用户信息
	 * @param moduleIds
	 *            主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String recordIds) {
		
		String logMsg="";
		
		if (StringUtils.isNotBlank(recordIds)) {
			for (String recordId : recordIds.split(",")) {
				Salary entity = salaryDao.get(recordId);
				if(entity==null){
					continue;
				}
				//权限验证(判断是不是有删除工资的权限)
				AuthUtil.canExecute(user, AuthConstant.AUTH_OPER_NAME_AUTHSALARYOFBTN, recordId, entity);									
				logMsg=logMsg+entity.getEmpName()+entity.getSalaryMonth()+"的工资  ";
				// 删除主表
				this.salaryDao.delete(entity);
				//删除党费
				this.membershipDuesService.delete(user, entity);
				
			}
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.DELETE_SALARY_LOG_MESSAGE, logMsg);
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					user, LogConstant.DELETE_SALARY_LOG_ACTION, logMessage,
					null, null);
		}
	}
	

	/**
	 * 按月删除工资
	 * 
	 * @param user
	 *            用户信息
	 * @param moduleIds
	 *            主键集合，多个以逗号分隔
	 */
	public void deleteMonthSalary(UserProfile user, String salaryMonth) {
		if (StringUtils.isNotBlank(salaryMonth)) {
			List<Salary> list=this.salaryDao.deleteMonthSalary(salaryMonth);
		    if(list!=null){
		    	for(Salary entity :list){
		    		if(entity==null){
		    			continue;
		    		}else{
						//权限验证(判断是不是有删除工资的权限)
						AuthUtil.canExecute(user, AuthConstant.AUTH_OPER_NAME_AUTHSALARYOFBTN, entity.getRecordId(), entity);									
						
						// 删除主表
						this.salaryDao.delete(entity);
						//删除党费
						this.membershipDuesService.delete(user, entity);
		    		}
		    	}
		    	//不加权限之前是可以直接删除list的，加了记录集权限之后改成了上述方式删除		    	
//		    	this.salaryDao.delete(list);
//		    	//刪除党费
//		    	this.membershipDuesService.delete(user, list);
		    }
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.DELETEBYMONTH_SALARY_LOG_MESSAGE, "所删工资的月份："+CommonUtil.format(salaryMonth,"yyyy年MM月"));
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					user, LogConstant.DELETEBYMONTH_SALARY_LOG_ACTION, logMessage,
					null, null);
		}
	}
	
	
	
	
	/**
	 *   导出Excel：查询结果
	 */ 
	public HSSFWorkbook getWorkbook(Salary salary, UserProfile user) {
		String excelFileName = "salary.xls";
		HSSFWorkbook wb = null;
		try {
			ExportUtils util = new ExportUtils();
			File file = util.getExcelDemoFile(File.separator
					+ "template_excel_def" + File.separator + excelFileName);
			wb = util.getExcelWorkbook(file);
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setBorderBottom((short) 1);
			cellStyle.setBorderLeft((short) 1);
			cellStyle.setBorderTop((short) 1);
			cellStyle.setBorderRight((short) 1);
			List<Salary> list = salaryDao.findBySearch(salary);
			if ((list != null) && (list.size() != 0)) {
				int length = list.size();
				Sheet sheet = wb.getSheetAt(0);

				Row row = null;
				Cell cell = null;
				for (int i = 0; i < length; i++) {
					row = sheet.createRow(i + 2);

					cell = row.createCell(0, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getDeptName());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(1, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getEmpNo());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(2, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getEmpName());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(3, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getSalaryMonth());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(4, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getXueLiZhiCheng());
					cell.setCellStyle(cellStyle);
	
					cell = row.createCell(5, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getZiGeBuTie()); 
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(6, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getGongLing());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(7, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getSiLing());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(8, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getFuLiBuTile());
					cell.setCellStyle(cellStyle);
															
					cell = row.createCell(9, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getFangBu());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(10, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getQuNuanBuTie());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(11, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getGangWeiGongZi());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(12, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getBuChongYangLao());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(13, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getJiaoTongFei());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(14, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getTongXunFei());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(15, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getBuFaYangLao());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(16, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getYuZhiJiangJin());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(17, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getBaoXiaoYaoFei());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(18, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getQiTaBuFa());
					cell.setCellStyle(cellStyle);
																					
					cell = row.createCell(19, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getYingFaHeJi());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(20, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getGongZiKouKuan());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(21, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getWuCanKouKuan());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(22, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getFangZu());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(23, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getZhuFangGongJiJin());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(24, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getKouBuChongYangLao());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(25, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getYangLaoBaoXian());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(26, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getShiYeBaoXian());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(27, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getYiLiaoBaoXian());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(28, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getQiTaKouKuan());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(29, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getYaoFei());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(30, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getDaiKouShui());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(31, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getKouKuanHeJi());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(32, 1);
					cell.setCellValue(((Salary) list.get(i))
							.getShiFaHeJi());
					cell.setCellStyle(cellStyle);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}


}
