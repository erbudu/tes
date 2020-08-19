package com.supporter.prj.dept_resource.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.dept_resource.dao.DeptResourceTypeDao;
import com.supporter.prj.dept_resource.dao.DeptResourceTypeGroupDao;
import com.supporter.prj.dept_resource.dao.DeptResourceTypeUiDao;
import com.supporter.prj.dept_resource.entity.DeptResourceType;
import com.supporter.prj.dept_resource.entity.DeptResourceTypeGroup;
import com.supporter.prj.dept_resource.entity.DeptResourceTypeUi;
import com.supporter.prj.dept_resource.util.LogConstant;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;

@Service
public class DeptResourceTypeService {
	@Autowired
	private DeptResourceTypeDao deptResourceTypeDao;
	@Autowired
	private DeptResourceTypeGroupDao deptResourceTypeGroupDao;
	@Autowired
	private DeptResourceTypeUiDao deptResourceTypeUiDao;

	public DeptResourceType get(String moduleId) {
		return deptResourceTypeDao.get(moduleId);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param moduleId
	 * @return
	 */
	public DeptResourceType initEditOrViewPage(JqGrid jqGrid, String typeCode,
			UserProfile user) {
		if (StringUtils.isBlank(typeCode)) {// 新建
			DeptResourceType deptResourceType = newDeptResourceType(user);
			deptResourceType.setAdd(true);
			return deptResourceType;
		} else {// 编辑
			// 获得主表
			DeptResourceType deptResourceType = deptResourceTypeDao
					.get(typeCode);
			if(EIPService.getRoleService().getRole(deptResourceType.getSuperUserGroup())!=null){//根据角色id获取角色
				deptResourceType.setRoleName(EIPService.getRoleService().getRole(deptResourceType.getSuperUserGroup()).getName());
			}else{
				deptResourceType.setRoleName("");
			}
			
			// 获得从表DeptResourceTypeGroup
			List<DeptResourceTypeGroup> groupListNew=new ArrayList<DeptResourceTypeGroup>();
			List<DeptResourceTypeGroup> groupList = deptResourceTypeGroupDao
					.findByTypeCode(typeCode);			
			for(DeptResourceTypeGroup deptResourceTypeGroup:groupList){
				if(EIPService.getRoleService().getRole(deptResourceTypeGroup.getGroupId())!=null){//根据角色id获取角色
					deptResourceTypeGroup.setRoleNameG(EIPService.getRoleService().getRole(deptResourceTypeGroup.getGroupId()).getName());
				}else{
					deptResourceTypeGroup.setRoleNameG("");
				}
				groupListNew.add(deptResourceTypeGroup);
			}
			deptResourceType.setGroupList(groupListNew);
			deptResourceType.setAdd(false);
			return deptResourceType;
		}
	}

	/**
	 * 新建实例,并初始化必要的属性.
	 * 
	 * @param auserprf_U
	 * @return
	 */
	public DeptResourceType newDeptResourceType(UserProfile auserprf_U) {
		DeptResourceType ldeptResourceType_N = new DeptResourceType();
		//ldeptResourceType_N.setTypeCode("null");
		ldeptResourceType_N.setDisplayOrder(getCount());
		return ldeptResourceType_N;
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
	public List<DeptResourceType> getGrid(UserProfile user, JqGrid jqGrid,
			DeptResourceType deptResourceType) {
		List<DeptResourceType> list = this.deptResourceTypeDao.findPage(jqGrid,
				deptResourceType);
		return list;

	}

	/**
	 * 分页表格展示权限数据.
	 * 
	 * @param jqGrid
	 * @param flag
	 * @param recordId
	 * @return
	 */
	public List<DeptResourceTypeUi> getUiGrid(UserProfile user, JqGrid jqGrid,
			String typeCode) {
		return deptResourceTypeUiDao.getUiGrid(jqGrid, typeCode);
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
	public DeptResourceType saveOrUpdate(UserProfile user,
			DeptResourceType deptResourceType, Map<String, Object> valueMap) {
		DeptResourceType ret = null;
		if (deptResourceType.getAdd()) {// 新建
			
			// 保存主表
			this.deptResourceTypeDao.save(deptResourceType);
			ret = deptResourceType;
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.ADD_DEPTR_TYPE_LOG_MESSAGE, deptResourceType.getTypeCode());
			EIPService.getLogService(LogConstant.INFO_TYPE_DEPTR_TYPE_BUSINESS).info(
					user, LogConstant.ADD_DEPTR_TYPE_LOG_ACTION, logMessage,
					deptResourceType, null);
		} else {// 编辑
			// 编辑之后保存主表
			this.deptResourceTypeDao.update(deptResourceType);
			ret = deptResourceType;
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.EDIT_DEPTR_TYPE_LOG_MESSAGE, deptResourceType.getTypeCode());
			EIPService.getLogService(LogConstant.INFO_TYPE_DEPTR_TYPE_BUSINESS).info(
					user, LogConstant.EDIT_DEPTR_TYPE_LOG_ACTION, logMessage,
					deptResourceType, null);
		}
		// 保存UIList
		saveOrUpdateUIList(user, deptResourceType, ret, deptResourceType
				.getDelIds());

		// 保存groupList
		saveOrUpdateGroupList(user, deptResourceType, ret, deptResourceType
				.getDelIds());
		return ret;

	}

	// 保存从表Ui
	private void saveOrUpdateUIList(UserProfile user,
			DeptResourceType deptResourceType, DeptResourceType ret,
			String delIds) {
		List<DeptResourceTypeUi> list = deptResourceType.getUIList();
		if (list != null) {
			for (DeptResourceTypeUi rec : list) {

				if (rec.getUiId() == null || rec.getUiId().equals("")) {// 从表新建
					rec.setUiId(com.supporter.util.UUIDHex.newId());
					rec.setResourceTypeCode(deptResourceType.getTypeCode());
					deptResourceTypeUiDao.save(rec);

				} else {// 从表编辑
					rec.setResourceTypeCode(deptResourceType.getTypeCode());
					deptResourceTypeUiDao.update(rec);
				}
				

			}
		}
		this.deleteUi(delIds);

	}

	// 保存从表group
	private void saveOrUpdateGroupList(UserProfile user,
			DeptResourceType deptResourceType, DeptResourceType ret,
			String delIds) {
		// 保存从表之前先删除原有从表
		List<DeptResourceTypeGroup> listY = deptResourceTypeGroupDao
				.findByTypeCode(ret.getTypeCode());
		if (listY != null) {
			if (listY.size() > 0) {
				for (DeptResourceTypeGroup DeptResourceTypeGroupY : listY) {
					deptResourceTypeGroupDao.delete(DeptResourceTypeGroupY);
				}
			}
		}
		// 删除完之后在重新保存
		List<DeptResourceTypeGroup> list = deptResourceType.getGroupList();
		if (list != null) {
			for (DeptResourceTypeGroup rec : list) {
				DeptResourceTypeGroup deptResourceTypeGroupZ = new DeptResourceTypeGroup();
				deptResourceTypeGroupZ.setCanRead("0");
				deptResourceTypeGroupZ.setCanNew("0");
				deptResourceTypeGroupZ.setCanWrite("0");
				deptResourceTypeGroupZ.setCanDelete("0");
				deptResourceTypeGroupZ.setFullAccess("0");
				deptResourceTypeGroupZ.setRecordId(com.supporter.util.UUIDHex
						.newId());
				deptResourceTypeGroupZ.setResourceTypeCode(deptResourceType
						.getTypeCode());
				if (!rec.getAuthType().equals("0")
						|| !rec.getGroupId().equals("")) {
					deptResourceTypeGroupZ.setAuthType(rec.getAuthType());
					deptResourceTypeGroupZ.setGroupId(rec.getGroupId());
					String authDesc = rec.getAuthDesc();
					String[] authDescs = authDesc.split("0");
					for (int i = 0; i < authDescs.length; i++) {
						if (authDescs[i].equals("canRead")) {
							deptResourceTypeGroupZ.setCanRead("1");
						}
						if (authDescs[i].equals("canNew")) {
							deptResourceTypeGroupZ.setCanNew("1");
						}
						if (authDescs[i].equals("canWrite")) {
							deptResourceTypeGroupZ.setCanWrite("1");
						}
						if (authDescs[i].equals("canDelete")) {
							deptResourceTypeGroupZ.setCanDelete("1");
						}
						if (authDescs[i].equals("fullAccess")) {
							deptResourceTypeGroupZ.setFullAccess("1");
						}
					}
					deptResourceTypeGroupDao.save(deptResourceTypeGroupZ);
				}
			}
		}
	}

	/**
	 * 删除从表数据
	 * 
	 * @param delIds
	 */
	public void deleteUi(String delIds) {
		if (StringUtils.isNotBlank(delIds)) {
			for (String delId : delIds.split(",")) {
				DeptResourceTypeUi person = this.deptResourceTypeUiDao
						.get(delId);
				if (person != null) {
					this.deptResourceTypeUiDao.delete(delId);
				}
			}
		}
	}

	// 得到数据库中数据的条数（以年为单位）
	public long getCount() {
		int displayOrder = deptResourceTypeDao.getCount();
		return displayOrder + 1;
	}

	/**
	 * 删除
	 * 
	 * @param user
	 *            用户信息
	 * @param moduleIds
	 *            主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String typeCodes) {
		if (StringUtils.isNotBlank(typeCodes)) {
			for (String typeCode : typeCodes.split(",")) {
				// 删除部门资源管理注册主表
				if(deptResourceTypeDao.get(typeCode)!=null){
					this.deptResourceTypeDao.delete(deptResourceTypeDao
							.get(typeCode));
				}
				
				
				
				//删除从表DeptResourceTypeUi
				List<DeptResourceTypeUi> listOfUi=this.deptResourceTypeUiDao.getByTypeCode(typeCode);
				if(listOfUi!=null){
					if(listOfUi.size()>0){
						this.deptResourceTypeUiDao.delete(listOfUi);
					}
				}
				
				
				
				//删除从表DeptResourceTypeGroup
				List<DeptResourceTypeGroup> listOfGroup=this.deptResourceTypeGroupDao.findByTypeCode(typeCode);
				if(listOfGroup!=null){
					if(listOfGroup.size()>0){
						this.deptResourceTypeGroupDao.delete(listOfGroup);
					}
				}
				
			}
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.DELETE_DEPTR_TYPE_LOG_MESSAGE, "删除的资源类型为："+typeCodes);
			EIPService.getLogService(LogConstant.INFO_TYPE_DEPTR_TYPE_BUSINESS).info(
					user, LogConstant.DELETE_DEPTR_TYPE_LOG_ACTION, logMessage,
					null, null);
		}
	}

	// 获取所有的部门资源类型，用于部门资源管理中的资源类型的下拉列表显示
	public List<Object[]> getDeptResourceType() {
		List<Object[]> list = this.deptResourceTypeDao.findDeptResourceType();
		return list;
	}
	
	
	
	
	/**
	 * 判断名字是否重复.
	 * @param typeCode
	 * @return
	 */
	public boolean checkNameIsValid(String typeCode) {
		return this.deptResourceTypeDao.checkNameIsValid(typeCode);
	}
	
	/**
	 * 获取资源类型注册码表编号.
	 * @param typeCode
	 * @return
	 */
	public String getResourceTypeCtbl(String typeCode){
		List < DeptResourceType > list = deptResourceTypeDao.getByTypeCode(typeCode);
		if (list == null || list.size() == 0)return "";
		return list.get(0).getResourceTypeCtbl();
	}
}
