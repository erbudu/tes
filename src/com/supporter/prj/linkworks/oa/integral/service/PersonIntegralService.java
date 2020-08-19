package com.supporter.prj.linkworks.oa.integral.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.emp.entity.IDeptPostEmp;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.integral.dao.PersonIntegralDao;
import com.supporter.prj.linkworks.oa.integral.entity.PersonIntegral;
import com.supporter.prj.linkworks.oa.integral.util.ExportUtils;
/**
 * @Title: Service
 * @Description: 报告
 * @author liyinfeng
 * @date 2017-7-05 10:25:07
 * @version V1.0
 * 
 */
@Service
@Transactional(TransManager.APP)
public class PersonIntegralService {
	@Autowired
	private PersonIntegralDao personIntegralDao;

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param moduleId
	 * @return
	 */
	public PersonIntegral initEditOrViewPage(String PersonIntegralId,
			UserProfile user) {
		if (StringUtils.isBlank(PersonIntegralId)) {// 新建
			PersonIntegral entity = newPersonIntegral(user);
			return entity;
		} else {// 编辑
			PersonIntegral PersonIntegral = personIntegralDao
					.get(PersonIntegralId);
			return PersonIntegral;
		}
	}

	/**
	 * 新建实例,并初始化必要的属性.
	 * 
	 * @param auserprf_U
	 * @return
	 */
	public PersonIntegral newPersonIntegral(UserProfile auserprf_U) {
		PersonIntegral lPersonIntegral_N = new PersonIntegral();
		lPersonIntegral_N.setId(com.supporter.util.UUIDHex.newId());
		Calendar date = Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR) - 1);
		lPersonIntegral_N.setYear(year);
		lPersonIntegral_N.setAdd(true);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		lPersonIntegral_N.setCreatedDate(sdf.format(new Date()));
		lPersonIntegral_N.setCreatedById(auserprf_U.getPersonId());
		lPersonIntegral_N.setCreatedBy(auserprf_U.getName());
		lPersonIntegral_N.setFinalIntegral(0);
		return lPersonIntegral_N;
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
	public List<PersonIntegral> getGrid(UserProfile user, JqGrid jqGrid,
			PersonIntegral entity) {
		return personIntegralDao.findPage(jqGrid, entity);
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
	public PersonIntegral saveOrUpdate(UserProfile user, PersonIntegral entity,
			Map<String, Object> valueMap) {
		if (entity.getAdd()) {//获取是否是正职
			List<IDeptPostEmp> list = EIPService.getEmpAssignService()
					.findDeptPostEmpsByEmpId(entity.getPersonId());
			String chiefId = EIPService.getRegistryService().get(IntegralImportService.DEPT_MANAGER_ID);
			String isChief = "F";
			for (IDeptPostEmp deptPostEmp : list) {
				if (deptPostEmp.getPostNo().equals(chiefId)) {
					isChief = "T";
				}
			}
			entity.setIsChief(isChief);
			this.personIntegralDao.save(entity);
			// 记录日志
			// ModuleUtils.saveModuleOperateLog(user, module,
			// Contract.LogOper.MODULE_ADD.getOperName(), null);
		} else {// 编辑
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			entity.setModifiedDate(sdf.format(new Date()));
			entity.setModifiedBy(user.getName());
			entity.setModifiedById(user.getPersonId());
			this.personIntegralDao.update(entity);
			// 记录日志
			// ModuleUtils.saveModuleOperateLog(user, module,
			// Contract.LogOper.MODULE_EDIT.getOperName(), null);
		}

		return entity;

	}

	/**
	 * 删除
	 * 
	 * @param user
	 *            用户信息
	 * @param moduleIds
	 *            主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String personIntegralIds) {
		if (StringUtils.isNotBlank(personIntegralIds)) {
			for (String PersonIntegralId : personIntegralIds.split(",")) {
				this.personIntegralDao.delete(PersonIntegralId);
			}
			// EIPService.getLogService("MPM_MCA").info(user,
			// Contract.LogOper.MODULE_DEL.getOperName(), "{delContractIds : " +
			// moduleIds + "}", null, null);
		}
	}

	public Integer getLastYearIntegral(String personIntegralId,
			String personId, Integer year) {
		Integer integral = 0;
		if (StringUtils.isNotBlank(personId) && year != null) {
			integral = personIntegralDao.getLastYearIntegral(personIntegralId,
					personId, year);
		}
		return integral;
	}

	// 导出Excel：查询结果
	public HSSFWorkbook getWorkbook(PersonIntegral pn, UserProfile user) {
		String excelFileName = "Integral.xls";
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
			List<PersonIntegral> list = personIntegralDao.findBySearch(pn);
			if ((list != null) && (list.size() != 0)) {
				int length = list.size();
				Sheet sheet = wb.getSheetAt(0);

				Row row = null;
				Cell cell = null;
				for (int i = 0; i < length; i++) {
					row = sheet.createRow(i + 2);

					cell = row.createCell(0, 1);
					cell.setCellValue(((PersonIntegral) list.get(i))
							.getDeptName());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(1, 1);
					cell.setCellValue(((PersonIntegral) list.get(i))
							.getPersonName());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(2, 1);
					cell.setCellValue(((PersonIntegral) list.get(i))
							.getPersonNo());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(3, 1);
					cell.setCellValue(((PersonIntegral) list.get(i))
							.getYear());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(4, 1);
					cell.setCellValue(((PersonIntegral) list.get(i))
							.getLastYearIntegral());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(5, 1);
					cell.setCellValue(((PersonIntegral) list.get(i))
							.getBasicIntegral());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(6, 1);
					cell.setCellValue(((PersonIntegral) list.get(i))
							.getRewardIntegral());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(7, 1);
					cell.setCellValue(((PersonIntegral) list.get(i))
							.getDeductionIntegral());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(8, 1);
					cell.setCellValue(((PersonIntegral) list.get(i))
							.getFinalIntegral());
					cell.setCellStyle(cellStyle);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}
}
