package com.supporter.prj.linkworks.oa.meeting_room.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.meeting_room.dao.MeetingRoomDao;
import com.supporter.prj.linkworks.oa.meeting_room.dao.MeetingRoomEqDao;
import com.supporter.prj.linkworks.oa.meeting_room.entity.MeetingRoom;
import com.supporter.prj.linkworks.oa.meeting_room.entity.MeetingRoomEq;
import com.supporter.prj.linkworks.oa.meeting_room.util.AuthConstant;
import com.supporter.prj.linkworks.oa.meeting_room.util.AuthUtil;
import com.supporter.prj.linkworks.oa.meeting_room.util.LogConstant;

@Service
public class MeetingRoomService {
	@Autowired
	private MeetingRoomDao meetingRoomDao;
	@Autowired
	private MeetingRoomEqDao meetingRoomEqDao;

	public MeetingRoom get(String moduleId) {
		return meetingRoomDao.get(moduleId);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param moduleId
	 * @return
	 */
	public MeetingRoom initEditOrViewPage(String roomId, UserProfile user) {
		if (StringUtils.isBlank(roomId)) {// 新建
			MeetingRoom meetingRoom = newMeetingRoom(user);
			// 显示顺序
			long displayOrder = getDisplayOrder();
			meetingRoom.setDisplayOrder(displayOrder);
			meetingRoom.setAdd(true);
			return meetingRoom;
		} else {// 编辑
			// 获得主表
			MeetingRoom meetingRoom = meetingRoomDao.get(roomId);
			meetingRoom.setAdd(false);
			return meetingRoom;
		}
	}

	/**
	 * 新建实例,并初始化必要的属性.
	 * 
	 * @param auserprf_U
	 * @return
	 */
	public MeetingRoom newMeetingRoom(UserProfile auserprf_U) {
		MeetingRoom lmeetingRoom_N = new MeetingRoom();
		lmeetingRoom_N.setRoomId(com.supporter.util.UUIDHex.newId());
		return lmeetingRoom_N;
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
	@SuppressWarnings("unchecked")
	public List<MeetingRoom> getGrid(UserProfile user, JqGrid jqGrid,
			MeetingRoom meetingRoom) {
		List<MeetingRoom> listZ = new ArrayList<MeetingRoom>();
		List<MeetingRoom> list1 = this.meetingRoomDao.findPage(user,jqGrid,
				meetingRoom);
		if (list1.size() > 0) {
			for (MeetingRoom meetingRoomZ : list1) {
				if(meetingRoomZ.getRoomDesc()==null){
					meetingRoomZ.setRoomDesc("");
				}
				List<MeetingRoomEq> listEq = this.meetingRoomEqDao
						.getByRoomId(meetingRoomZ.getRoomId());
				meetingRoomZ.setList(listEq);
				listZ.add(meetingRoomZ);
			}
		}
		jqGrid.setRows(listZ);
		return listZ;

	}

	// 获取所有的会议室id和会议室名称，用于会议预订中的会议地点下拉列表显示
	public List<Object[]> getMeetRoom() {
		List<Object[]> list = this.meetingRoomDao.findMeetingRoom();
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
	public MeetingRoom saveOrUpdate(UserProfile user, MeetingRoom meetingRoom,
			Map<String, Object> valueMap) {
		MeetingRoom ret = null;
		if (meetingRoom.getAdd()) {// 新建
			// 保存主表
			this.meetingRoomDao.save(meetingRoom);
			ret = meetingRoom;
			// 记录日志
			/*
			 * MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
			 * MaterialCode.LogOper.MATERIALCODE_ADD.getOperName(), null);
			 */
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.ADD_MEETINGROOM_LOG_MESSAGE, meetingRoom.getRoomName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS2).info(
					user, LogConstant.ADD_MEETINGROOM_LOG_ACTION, logMessage,
					meetingRoom, null);
		} else {// 编辑
			// 编辑之后保存主表
			this.meetingRoomDao.update(meetingRoom);
			ret = meetingRoom;
			// 记录日志
			/*
			 * MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
			 * MaterialCode.LogOper.MATERIALCODE_EDIT.getOperName(), null);
			 */
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.EDIT_MEETINGROOM_LOG_MESSAGE, meetingRoom.getRoomName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS2).info(
					user, LogConstant.EDIT_MEETINGROOM_LOG_ACTION, logMessage,
					meetingRoom, null);
		}

		saveOrUpdateEquipment(user, meetingRoom, ret, meetingRoom.getDelIds());
		return ret;

	}

	// 保存从表（会议室设备）
	private void saveOrUpdateEquipment(UserProfile user,
			MeetingRoom meetingRoom, MeetingRoom ret, String delIds) {
		List<MeetingRoomEq> list = meetingRoom.getList();
		if (list != null) {
			for (MeetingRoomEq rec : list) {

				if (rec.getEquipmentId() == null
						|| rec.getEquipmentId().equals("")) {// 从表新建
					rec.setEquipmentId(com.supporter.util.UUIDHex.newId());
					rec.setMeetingRoomId(ret.getRoomId());
					rec.setDisplayOrder(rec.getDisplayOrderDesc());
					rec.setEquipmentStatus(0L);
					meetingRoomEqDao.save(rec);
				} else {// 从表编辑
					rec.setMeetingRoomId(ret.getRoomId());
					rec.setDisplayOrder(rec.getDisplayOrderDesc());
					rec.setEquipmentStatus(0L);
					meetingRoomEqDao.update(rec);
				}
				
			}
		}
		this.deleteEquipment(delIds);

	}

	/**
	 * 删除从表数据
	 * 
	 * @param delIds
	 */
	public void deleteEquipment(String delIds) {
		if (StringUtils.isNotBlank(delIds)) {
			for (String delId : delIds.split(",")) {
				MeetingRoomEq meetingRoomEq = this.meetingRoomEqDao.get(delId);
				if (meetingRoomEq != null) {
					this.meetingRoomEqDao.delete(delId);
				}
			}
		}
	}

	// 得到数据库中数据的条数（以年为单位）
	public Long getDisplayOrder() {
		Long displayOrder = meetingRoomDao.getCount();
		return displayOrder + 1L;
	}

	/**
	 * 删除
	 * 
	 * @param user
	 *            用户信息
	 * @param moduleIds
	 *            主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String roomIds) {
		if (StringUtils.isNotBlank(roomIds)) {
			for (String roomId : roomIds.split(",")) {
				MeetingRoom meetingRoom=this.get(roomId);
				if(meetingRoom==null){
					continue;
				}
				//权限验证（判断是不是有删除会议室的权限）
				AuthUtil.canExecute(user, AuthConstant.AUTH_OPER_NAME_AUTHMEETINGROOMOFBTN, roomId, meetingRoom);
				// 删除主表
				this.meetingRoomDao.delete(meetingRoomDao.get(roomId));
				
				
				//删除从表设备表
				List<MeetingRoomEq> list=this.meetingRoomEqDao.getByRoomId(roomId);
				if(list!=null){
					if(list.size()>0){
						this.meetingRoomEqDao.delete(list);
					}
					
				}
				
			}
			
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.DELETE_MEETINGROOM_LOG_MESSAGE, "删除的会议室主键为："+roomIds);
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS2).info(
					user, LogConstant.DELETE_MEETINGROOM_LOG_ACTION, logMessage,
					null, null);
		}
	}

	/**
	 * 分页表格展示权限数据.
	 * 
	 * @param jqGrid
	 * @param flag
	 * @param recordId
	 * @return
	 */
	public List<MeetingRoomEq> getEquipMentGrid(UserProfile user,
			JqGrid jqGrid, String roomId) {
		List<MeetingRoomEq> listZ = new ArrayList<MeetingRoomEq>();
		List<MeetingRoomEq> list = meetingRoomEqDao.getEquipMentGrid(jqGrid,
				roomId);
		if (list.size() > 0) {
			for (MeetingRoomEq rec : list) {
				rec.setDisplayOrderDesc(rec.getDisplayOrder());
				listZ.add(rec);
			}
		}
		// jqGrid.setRows(listZ);
		return listZ;
	}

}
