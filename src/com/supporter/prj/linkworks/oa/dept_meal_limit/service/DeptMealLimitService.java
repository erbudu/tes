package com.supporter.prj.linkworks.oa.dept_meal_limit.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.dept_meal_limit.dao.DeptMealLimitDao;
import com.supporter.prj.linkworks.oa.dept_meal_limit.entity.DeptMealLimit;
import com.supporter.prj.linkworks.oa.emp_meal_apply.entity.EmpCustormerMealRec;

@Service
public class DeptMealLimitService {

	@Autowired
	private DeptMealLimitDao codeDao;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 根据主键获取信息.
	 * 
	 * @param codeId
	 *            主键
	 * @return WmsDeptMealLimit
	 */
	public DeptMealLimit get(String codeId) {
		return codeDao.get(codeId);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param codeId
	 * @return
	 */
	public DeptMealLimit initEditOrViewPage(String codeId, String docClassify,
			UserProfile user) {
		if (StringUtils.isBlank(codeId)) {// 新建
			DeptMealLimit code = new DeptMealLimit();
			code.setDeptMealLimitId(com.supporter.util.UUIDHex.newId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			code.setCreatedDate(sdf.format(new Date()));
			code.setAdd(true);
			if (user != null) {
				String userId = user.getPersonId();
				code.setCreatedBy(userId);
			}
			return code;
		} else {// 编辑
			DeptMealLimit code = codeDao.get(codeId);
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
	public List<DeptMealLimit> getGrid(UserProfile user, JqGrid jqGrid,
			DeptMealLimit code,String pagetype) {
			List<DeptMealLimit> persons = codeDao.findPage(jqGrid, code);
			if (pagetype != null && pagetype.equals("view")) {
				return persons;
			}
			Long order =Long.valueOf("0");
			if(persons!=null&&persons.size()!=0){
				order = persons.get(persons.size()-1).getDisplayOrder();
			}
			int a = 3;
				for (int i = 0; i < a; i++) {
					DeptMealLimit person = new DeptMealLimit();
					person.setDeptMealLimitId(com.supporter.util.UUIDHex.newId());
					person.setAdd(true);
					person.setLimitAmount(0.00);
					order+= 10;
					person.setDisplayOrder(order);
					persons.add(person);
				
			}
			jqGrid.setRows(persons);
			jqGrid.setRowCount(persons.size());
			return persons;
		
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param codeId
	 * @param materialName
	 * @return
	 */
	public boolean checkNameIsValid(String codeId, String materialName) {
		return this.codeDao.checkNameIsValid(codeId, materialName);
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
				DeptMealLimit codeDb = this
						.getWmsDeptMealLimitFromBuffer(docId);
				if (codeDb == null) {
					continue;
				}
				this.recursiveDelete(user, codeDb);
			}
			// 记录日志
			/*
			 * DeptMealLimitUtils.saveDeptMealLimitOperateLog(user, null,
			 * DeptMealLimit.LogOper.DeptMealLimit_DEL.getOperName(),
			 * "{delDeptMealLimitIds : " + codeIds + "}");
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
	public DeptMealLimit saveOrUpdate(UserProfile user, List<DeptMealLimit> limits,
			Map<String, Object> valueMap) {
		DeptMealLimit ret = null;
		for (DeptMealLimit code : limits) {
			if (code.getAdd()&&StringUtils.isNotBlank(code.getDeptName())) {// 新建
				code.setCreatedDate(sdf.format(new Date()));
				code.setCreatedBy(user.getPersonId());
				this.codeDao.save(code);
				ret = code;
				// 记录日志
				/*
				 * MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
				 * MaterialCode.LogOper.MATERIALCODE_ADD.getOperName(), null);
				 */
			} else {// 编辑
				code.setModifiedBy(user.getPersonId());
				code.setModifiedDate(sdf.format(new Date()));
				this.codeDao.update(code);
				ret = code;
				// 记录日志
				/*
				 * MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
				 * MaterialCode.LogOper.MATERIALCODE_EDIT.getOperName(), null);
				 */
			}
		}
		

		return ret;

	}

	/**
	 * 递归删除
	 * 
	 * @param DeptMealLimit
	 */
	private void recursiveDelete(UserProfile user, DeptMealLimit code) {
		if (true) {// 可删除
			String codeId = code.getDeptMealLimitId();
			this.codeDao.delete(codeId);
		}

	}

	public DeptMealLimit getWmsDeptMealLimitFromBuffer(String codeIdOrName) {
		if (StringUtils.isBlank(codeIdOrName)) {
			return null;
		}
		DeptMealLimit code = this.get(codeIdOrName);
		return code;
	}

}
