/**
 * 
 */
package com.supporter.prj.linkworks.oa.attendance.leave.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.role.entity.Role;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.attendance.leave.dao.LeaveDao;
import com.supporter.prj.linkworks.oa.attendance.leave.entiyt.LeaveEntity;
import com.supporter.prj.linkworks.oa.attendance.reportBack.service.ReportBackService;
import com.supporter.util.CommonUtil;

/**
 * @author YUEYUN
 * @date 2019年7月15日
 * 
 */
@Service
public class LeaveService {
	
	@Autowired
	private LeaveDao leaveDao;
	@Autowired
	private ReportBackService reportBackService;
	/**
	 * 描述：此方法用于根据id获取数据
	 * @param leaveId
	 * @return
	 */
	public LeaveEntity get(String leaveId) {
		LeaveEntity leaveEntity = leaveDao.get(leaveId);
		return leaveEntity;
		
	}
	
	
	
	/**
	 * @param jqGrid
	 * @param leaveEntity
	 * @param user
	 */
	public List<LeaveEntity> getGrid(JqGrid jqGrid, LeaveEntity leaveEntity, UserProfile user) {
		String authFilter = EIPService.getAuthorityService().getHqlFilter(user, LeaveEntity.MODULE_ID, "LIST_VIEW_AUTH");
		return leaveDao.findPage(jqGrid, leaveEntity, authFilter);
		
	}
	

	/**
	 * 描述：此方法用于从码表获取请假类型
	 * @return
	 */
	public List<Map<String, String>> getSelectOpinion() {
		List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems(LeaveEntity.CODETABLE_ID);
		List<Map<String, String>> rltList = new ArrayList();
		Map<String, String> allMap = new HashMap();
		allMap.put("id", "");
		allMap.put("name", "");
		rltList.add(allMap);
		for (IComCodeTableItem item : items) {
			Map<String, String> rltMap = new HashMap();
			rltMap.put("id",item.getDisplayName());//item.getItemId()
			rltMap.put("name",item.getDisplayName());
			rltList.add(rltMap);
		}
		return rltList;
	}


	/**
	 * 描述：此方法为请假单业务层保存更新方法，用于保存请假单表单数据，调用了持久层的保存更新方法
	 * @param user 当前登陆用户
	 * @param leaveEntity 表单数据模型
	 * @param dataMap 
	 */
	public LeaveEntity saveOrUpdate(UserProfile user, LeaveEntity leaveEntity) {
		String leaveId = leaveEntity.getLeaveId();
		Map<String,Object> filtMap = new HashMap<String,Object>();
		if(StringUtils.isBlank(leaveId)) {//保存
			leaveEntity.setLeaveId(com.supporter.util.UUIDHex.newId());
			setModifiedInfo(user,leaveEntity);
			leaveDao.save(leaveEntity);
		}else {//更新
			setModifiedInfo(user,leaveEntity);
			leaveDao.update(leaveEntity);
		}
		return leaveEntity;
		
	}


	/**
	 *        描述：此方法用于设置修改人信息
	 * @param user 当前登陆用户
	 * @param dataMap
	 */
	private void setModifiedInfo(UserProfile user, LeaveEntity leaveEntity) {
		leaveEntity.setModifiedBy(user.getName());
		leaveEntity.setModifiedById(user.getPersonId());
		leaveEntity.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
	}


	/**
	 * 	描述：此方法用于设置创建人信息
	 * @param user
	 * @param leaveEntity
	 */
	private void setCreateInfo(UserProfile user, LeaveEntity leaveEntity) {
		leaveEntity.setCreateBy(user.getName());
		leaveEntity.setCreateById(user.getPersonId());
		leaveEntity.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
	}


	/** 
	 *     描述：此方法为请假单业务层方法，用于批量删除选定的请假单数据，调用了请假单持久层的删除方法
	 * @param user
	 * @param leaveId
	 */
	public void deleteBatch(UserProfile user, String leaveId) {
		if (StringUtils.isNotBlank(leaveId)) {
			this.leaveDao.delete(leaveId);
		}
	}


	/**
	 * 填充表单数据
	 * @param leaveId
	 */
	public LeaveEntity initForm(String leaveId, UserProfile user) {
		LeaveEntity entity = null;
		if(StringUtils.isBlank(leaveId)) {
			entity = new LeaveEntity();
			if (user != null) {
				entity.setLeavePersonId(user.getPersonId());
				entity.setLeavePersonName(user.getName());
				entity.setDeptId(user.getDeptId());
				entity.setDeptName(user.getDept().getName());
				String level = EIPService.getEmpService().getEmployee(user.getPersonId()).getEmpLevel();//登陆用户的职位等级
				entity.setEmpLevel(level);
				setCreateInfo(user, entity);
				setModifiedInfo(user, entity);
			}
			entity.setStatus(LeaveEntity.DEFAULT);
			entity.setIsSellingOff(LeaveEntity.SELLING_NO);
		}else {
			entity = leaveDao.get(leaveId);
		}
		return entity;
	}

	/**
	 * 更新 
	 * @param entity
	 */
	public void update(LeaveEntity entity) {
		leaveDao.saveOrUpdate(entity);
	}

	/**
	 * 	描述：此方法用于判断请假单中是否有未销假的数据，如果有则返回false，如果没有则返回true
	 * 	前端根据此状态来判断是否可以进行再次请假的操作。如果有一条请假单未销假，返回值未false则不能进行请假操作
	 * @return booelan
	 */
	public boolean isCanBuild(UserProfile user) {//流程状态为 已审批并且显示未销假 的数据
		//根据登陆用户id查询用户所有的请假单数据
		List<LeaveEntity> list = leaveDao.findBy("leavePersonId", user.getPersonId());
		if(list.size() == 0) {//如果 长度为0，说明是第一次填写返回true
			return true;
		}else {
			for (LeaveEntity leaveEntity : list) {
				int isSellingOff = leaveEntity.getIsSellingOff();
				if(isSellingOff == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 是否可编辑删除
	 * @param user
	 * @param leaveId
	 * @return
	 */
	public boolean isCanEdit(UserProfile user, String leaveId) {
		if (StringUtils.isNotBlank(leaveId) && user != null) {
			LeaveEntity leave = this.get(leaveId);
			if (leave.getCreateById().equals(user.getPersonId())) {
				return true;
			}
		}
		return false;
	}
   
}
