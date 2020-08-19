package com.supporter.prj.ppm.schedule_status.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.ppm.schedule_status.dao.ScheduleStatusDao;


import com.supporter.prj.ppm.schedule_status.entity.ScheduleStatus;
import com.supporter.prj.ppm.service.IScheduleStatusService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 * @Title: ScheduleStatusService
 * @Description: 业务操作类
 * @author: liyinfeng
 * @date: 2018-07-13
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class ScheduleStatusService implements IScheduleStatusService{

	@Autowired
	private ScheduleStatusDao scheduleStatusDao;
	
	/**
	 * 获取销售合同上线对象集合
	 * @param user
	 * @param jqGrid
	 * @param scheduleStatus
	 * @return
	 */
	public List<ScheduleStatus> getGrid(UserProfile user, JqGrid jqGrid, ScheduleStatus scheduleStatus) {
		return this.scheduleStatusDao.findPage(jqGrid, scheduleStatus);
	}

	/**
	 * 获取单个销售合同上线对象
	 * @param id
	 * @return
	 */
	public ScheduleStatus get(String id) {
		ScheduleStatus scheduleStatus = this.scheduleStatusDao.get(id);
		return scheduleStatus;
	}

	/**
	 * 新建销售合同上线对象
	 * @param user
	 * @return
	 */
	public ScheduleStatus newScheduleStatus(UserProfile user) {
		ScheduleStatus scheduleStatus = new ScheduleStatus();
		loadScheduleStatus(scheduleStatus, user);
		return scheduleStatus;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public ScheduleStatus loadScheduleStatus(ScheduleStatus scheduleStatus, UserProfile user) {
		if(user != null) {
			scheduleStatus.setCreatedBy(user.getName());
			scheduleStatus.setCreatedById(user.getPersonId());
			scheduleStatus.setModifiedBy(user.getName());
			scheduleStatus.setModifiedById(user.getPersonId());
			Dept dept = user.getDept();
			if (dept != null) {
				scheduleStatus.setDeptName(dept.getName());
				scheduleStatus.setDeptId(dept.getDeptId());
			}
		}else {
			EIPService.getLogService("SCHEDULE").info(user, "保存进度状态成功", 
					"user为空", null, null);
		}
		scheduleStatus.setCreatedDate(new Date());
		scheduleStatus.setModifiedDate(new Date());
		return scheduleStatus;
	}
	

	/**
	 * 为编辑或查看页面初始化对象
	 * @param id
	 * @param user
	 * @return
	 */
	public ScheduleStatus initEditOrViewPage(String id, UserProfile user) {
		ScheduleStatus scheduleStatus = scheduleStatusDao.get(id);
		if (scheduleStatus == null) {
			scheduleStatus = newScheduleStatus(user);
			scheduleStatus.setScheduleId(id);
			scheduleStatus.setAdd(true);
		} else {
			scheduleStatus = (ScheduleStatus) this.scheduleStatusDao.get(id);
			scheduleStatus.setAdd(false);
		}
		return scheduleStatus;
	}
	
	/**
	 * 为编辑或查看页面初始化对象
	 * @param id
	 * @param user
	 * @return
	 */
	public String getTextDisplay(UserProfile user, String moduleId) {
		ScheduleStatus scheduleStatus = this.scheduleStatusDao.getScheduleStatusByModuleId(moduleId);
		if(scheduleStatus != null){
			Map<String, String> map = getScheduleStatusCodeTables();
			String status = map.get(scheduleStatus.getTextDisplay());
			return status;
		}else{
			//日志
			EIPService.getLogService("SCHEDULE").info(user, "获取进度状态不成功", "moduleId："+moduleId+",未能找到进度状态信息", null, null);
			return null;
		}
	}
	
	/**
	 * 进度状态码表
	 * @return
	 */
	public Map<String, String> getScheduleStatusCodeTables() {
		List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PPM_SCHEDULE_STATUS");
		Map<String, String> map = new HashMap<String, String>();
		for(IComCodeTableItem item : items) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}
	
	/**
	 * 保存或修改对象
	 * @param user
	 * @param scheduleStatus
	 * @param valueMap
	 * @return
	 */
	public ScheduleStatus saveOrUpdate(UserProfile user, ScheduleStatus scheduleStatus, Map<String, Object> valueMap) {
		if (scheduleStatus.getAdd()) {
			this.scheduleStatusDao.save(scheduleStatus);
			//保存用印/备案附件
//			this.saveFile(scheduleStatus ,user);
		} else {
			// 设置更新时间
			scheduleStatus.setModifiedBy(user.getName());
			scheduleStatus.setModifiedById(user.getPersonId());
			scheduleStatus.setModifiedDate(new Date());
			//scheduleStatus.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.scheduleStatusDao.update(scheduleStatus);
		}
		return scheduleStatus;
	}

	/**
	 * 保存提交
	 * @param user
	 * @param scheduleStatus
	 * @param valueMap
	 * @return
	 */
	public ScheduleStatus commit(UserProfile user, ScheduleStatus scheduleStatus, Map<String, Object> valueMap) {
		saveOrUpdate(user, scheduleStatus, valueMap);
		this.scheduleStatusDao.update(scheduleStatus);
		// 记录日志
		EIPService.getLogService("TOOLTIPS").info(user, "生效", scheduleStatus.getScheduleId()+"生效成功", scheduleStatus, null);
		return scheduleStatus;
	}

		
	/**
	 * 各功能调用此方法进行保存或更新进度状态
	 * 
	 * @param moduleId
	 * @param textDisplay
	 * @param user
	 * @return
	 */
	public void saveScheduleStatus(String moduleId, String textDisplay, UserProfile user) {
		if(StringUtils.isNotBlank(moduleId) && StringUtils.isNotBlank(textDisplay)){
			ScheduleStatus scheduleStatus = this.scheduleStatusDao.getScheduleStatusByModuleId(moduleId);
			if(scheduleStatus != null){
				scheduleStatus.setTextDisplay(textDisplay);
				if(user != null) {
					scheduleStatus.setModifiedBy(user.getName());
					scheduleStatus.setModifiedById(user.getPersonId());
				}else {
					EIPService.getLogService("SCHEDULE").info(user, "保存进度状态成功", 
							"user为空", null, null);
				}
				scheduleStatus.setModifiedDate(new Date());
				this.scheduleStatusDao.update(scheduleStatus);
			}else{
				scheduleStatus = new ScheduleStatus();
				this.loadScheduleStatus(scheduleStatus, user);
				scheduleStatus.setScheduleId(com.supporter.util.UUIDHex.newId());
				scheduleStatus.setModuleId(moduleId);
				scheduleStatus.setTextDisplay(textDisplay);
				this.scheduleStatusDao.save(scheduleStatus);
			}
		}else{
			//日志
			EIPService.getLogService("SCHEDULE").info(user, "保存进度状态不成功", "moduleId为空或textDisplay为空", null, null);
		}
	}

}
