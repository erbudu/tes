package com.supporter.prj.linkworks.oa.dept_meal_non_emps.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.dept_meal_non_emps.dao.DeptMealNonEmpsDao;
import com.supporter.prj.linkworks.oa.dept_meal_non_emps.dao.DeptMealNonEmpsRecDao;
import com.supporter.prj.linkworks.oa.dept_meal_non_emps.entity.DeptMealNonEmps;
import com.supporter.prj.linkworks.oa.dept_meal_non_emps.entity.DeptMealNonEmpsRec;

@Service
public class DeptMealNonEmpsService {
	@Autowired
	private DeptMealNonEmpsDao codeDao;
	@Autowired
	private DeptMealNonEmpsRecService personsService;
	@Autowired
	private DeptMealNonEmpsRecDao personsDao;

	/**
	 * 根据主键获取信息.
	 * 
	 * @param codeId
	 *            主键
	 * @return WmsDeptMealNonEmps
	 */
	public DeptMealNonEmps get(String codeId) {
		return codeDao.get(codeId);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param codeId
	 * @return
	 */
	public DeptMealNonEmps initEditOrViewPage(String codeId, String docClassify,
			UserProfile user) {
		if (StringUtils.isBlank(codeId)) {// 新建
			DeptMealNonEmps code = new DeptMealNonEmps();
			code.setNonEmpIds(com.supporter.util.UUIDHex.newId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			code.setCreatedDate(sdf.format(new Date()));
			code.setAdd(true);
			if (user != null) {
				code.setCreatedBy(user.getName());
				code.setCreatedById(user.getPersonId());
				String deptId = user.getDeptId();
				Dept dept = user.getDept();
				String deptName = null;
				if(dept != null){
					deptName = dept.getName();
					code.setDeptId(deptId);
					code.setDeptName(deptName);
				}else{
					code.setDeptId("1");
					code.setDeptName("测试系统");	
				}
			}
			return code;
		} else {// 编辑
			DeptMealNonEmps code = codeDao.get(codeId);
			code.setAdd(false);
			return code;
		}
	
		
	}
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user
	 *            用户信息
	 * @param jqGridReq
	 *            jqgrid请求对象
	 * @param codeIds
	 *            多个逗号分隔
	 * @return JqGrid
	 */
	public List<DeptMealNonEmps> getGrid(UserProfile user, JqGrid jqGrid, DeptMealNonEmps code) {
		return codeDao.findPage(jqGrid, code);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param codeId
	 * @param materialName
	 * @return
	 */
	public boolean checkNameIsValid(DeptMealNonEmps entity) {
		return this.codeDao.checkNameIsValid(entity);
	}

	/**
	 * 删除
	 * 
	 * @param user
	 *            用户信息
	 * @param moduleIds
	 *            主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String docIds) {
		if (StringUtils.isNotBlank(docIds)) {
			for (String docId : docIds.split(",")) {
				DeptMealNonEmps codeDb = this.getWmsDeptMealNonEmpsFromBuffer(docId);
				if (codeDb == null) {
					continue;
				}
				this.recursiveDelete(user, codeDb);
			}
			// 记录日志
			/*
			 * DeptMealNonEmpsUtils.saveDeptMealNonEmpsOperateLog(user, null,
			 * DeptMealNonEmps.LogOper.DeptMealNonEmps_DEL.getOperName(), "{delDeptMealNonEmpsIds : " +
			 * codeIds + "}");
			 */
		}
	}

	/**
	 * 保存或更新
	 * 
	 * @param user
	 *            用户信息
	 * @param materialCode
	 *            实体类
	 * @return
	 */
	public DeptMealNonEmps saveOrUpdate(UserProfile user, DeptMealNonEmps code,
			Map<String, Object> valueMap) {
		DeptMealNonEmps ret = null;
		if (code.getAdd()) {// 新建
			this.codeDao.save(code);
			ret = code;
			// 记录日志
			/*
			 * MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
			 * MaterialCode.LogOper.MATERIALCODE_ADD.getOperName(), null);
			 */
		} else {// 编辑
			code.setModifiedBy(user.getPersonId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			code.setModifiedDate(sdf.format(new Date()));
			this.codeDao.update(code);
			ret = code;
			// 记录日志
			/*
			 * MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
			 * MaterialCode.LogOper.MATERIALCODE_EDIT.getOperName(), null);
			 */
		}
		saveOrUpdateRecs(user,code,ret,code.getDelIds());

		return ret;

	}
	private void saveOrUpdateRecs(UserProfile user,
			DeptMealNonEmps clcReport, DeptMealNonEmps ret, String delIds) {
		List<DeptMealNonEmpsRec> list = clcReport.getList();
		if (list != null) {
			for (DeptMealNonEmpsRec rec : list) {	
				if(StringUtils.isNotBlank(rec.getNonEmpName())){
					rec.setNonEmpIds(ret.getNonEmpIds());
					if(rec.getNonEmpType()==null){
						rec.setNonEmpType(Long.valueOf("0"));
					}
					personsDao.saveOrUpdate(rec);
				}
				
			}
		}
		personsService.deleteRec(clcReport.getNonEmpIds(), delIds);

	}
	/**
	 * 递归删除
	 * 
	 * @param DeptMealNonEmps
	 */
	private void recursiveDelete(UserProfile user, DeptMealNonEmps code) {
		if (true) {// 可删除
			String codeId = code.getNonEmpIds();
			this.codeDao.delete(codeId);
			personsService.deleteRecByPlcId(codeId);
		}

	}

	public DeptMealNonEmps getWmsDeptMealNonEmpsFromBuffer(String codeIdOrName) {
		if (StringUtils.isBlank(codeIdOrName)) {
			return null;
		}
		DeptMealNonEmps code = this.get(codeIdOrName);
		return code;
	}

}
