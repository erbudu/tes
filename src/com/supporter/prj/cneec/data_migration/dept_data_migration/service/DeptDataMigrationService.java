package com.supporter.prj.cneec.data_migration.dept_data_migration.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.data_migration.dept_data_migration.dao.DeptBusinessDao;
import com.supporter.prj.cneec.data_migration.dept_data_migration.dao.DeptDataMigrationDao;
import com.supporter.prj.cneec.data_migration.dept_data_migration.entity.DeptBusiness;
import com.supporter.prj.cneec.data_migration.dept_data_migration.entity.DeptDataMigration;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;

@Service
public class DeptDataMigrationService {
	@Autowired
	private DeptDataMigrationDao codeDao;
	@Autowired
	private DeptBusinessService personsService;
	@Autowired
	private DeptBusinessDao personsDao;

	/**
	 * 根据主键获取信息.
	 * 
	 * @param codeId
	 *            主键
	 * @return WmsDeptDataMigration
	 */
	public DeptDataMigration get(String codeId) {
		return codeDao.get(codeId);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param codeId
	 * @return
	 */
	public DeptDataMigration initEditOrViewPage(String codeId,
			String docClassify, UserProfile user) {
		if (StringUtils.isBlank(codeId)) {// 新建
			DeptDataMigration code = new DeptDataMigration();
			code.setDeptDataMigrationId(com.supporter.util.UUIDHex.newId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			code.setCreatedDate(sdf.format(new Date()));
			code.setAdd(true);
			if (user != null) {
				code.setCreatedBy(user.getName());
				code.setCreatedById(user.getPersonId());
				Dept dept = user.getDept();
				if (dept != null) {
					code.setCreatedDeptId(user.getDeptId());
					code.setCreatedDeptName(dept.getName());
				} else {
					code.setCreatedDeptId("1");
					code.setCreatedDeptName("测试系统");
				}
			}
			return code;
		} else {// 编辑
			DeptDataMigration code = codeDao.get(codeId);
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
	public List<DeptDataMigration> getGrid(UserProfile user, JqGrid jqGrid,
			DeptDataMigration code) {
		return codeDao.findPage(jqGrid, code);
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
				DeptDataMigration codeDb = this
						.getWmsDeptDataMigrationFromBuffer(docId);
				if (codeDb == null) {
					continue;
				}
				this.recursiveDelete(user, codeDb);
			}
			// 记录日志
			/*
			 * DeptDataMigrationUtils.saveDeptDataMigrationOperateLog(user,
			 * null,
			 * DeptDataMigration.LogOper.DeptDataMigration_DEL.getOperName(),
			 * "{delDeptDataMigrationIds : " + codeIds + "}");
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
	public DeptDataMigration saveOrUpdate(UserProfile user,
			DeptDataMigration code, Map<String, Object> valueMap) {
		DeptDataMigration ret = null;
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
		saveOrUpdateRecs(user, code, ret, code.getDelIds());

		return ret;

	}

	private void saveOrUpdateRecs(UserProfile user,
			DeptDataMigration clcReport, DeptDataMigration ret, String delIds) {
		List<DeptBusiness> list = clcReport.getList();
		if (list != null) {
			for (DeptBusiness rec : list) {
				rec.setDeptDataMigrationId(ret.getDeptDataMigrationId());
				personsDao.saveOrUpdate(rec);
			}
		}
		personsService.deleteRec(clcReport.getDeptDataMigrationId(), delIds);

	}

	/**
	 * 递归删除
	 * 
	 * @param DeptDataMigration
	 */
	private void recursiveDelete(UserProfile user, DeptDataMigration code) {
		if (true) {// 可删除
			String codeId = code.getDeptDataMigrationId();
			this.codeDao.delete(codeId);
			personsService.deleteRecByPlcId(codeId);
		}

	}

	public DeptDataMigration getWmsDeptDataMigrationFromBuffer(
			String codeIdOrName) {
		if (StringUtils.isBlank(codeIdOrName)) {
			return null;
		}
		DeptDataMigration code = this.get(codeIdOrName);
		return code;
	}

}
