package com.supporter.prj.linkworks.oa.meeting.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.meeting.entity.Meeting;
import com.supporter.prj.linkworks.oa.meeting.entity.MeetingType;
import com.supporter.prj.linkworks.oa.meeting.service.MeetingService;
import com.supporter.prj.linkworks.oa.meeting_room.service.MeetingRoomService;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

@Controller
@RequestMapping("oa/meeting")
public class MeetingController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private MeetingService meetingService;

	@Autowired
	private MeetingRoomService meetingRoomService;

	// 根据条件获取指定的会议
	@RequestMapping("getMeeting")
	public @ResponseBody
	Meeting get(HttpServletRequest request, JqGridReq jqGridReq,
			Meeting meeting, String roomId, String periodPoints)
			throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		meeting.setMeetingRoomId(roomId);
		meeting.setPeriodPoints(periodPoints);
		Meeting meetingZ = this.meetingService.get(user, jqGrid, meeting);
		return meetingZ;
	}

	/**
	 * 返回列表.
	 * 
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	List<Meeting> getGrid(HttpServletRequest request, JqGridReq jqGridReq,
			Meeting meeting, String roomId, String periodPoints,
			String li_YearSelect, String weekIndexSelect) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		meeting.setMeetingRoomId(roomId);
		meeting.setPeriodPoints(periodPoints);
		meeting.setLi_YearSelect(li_YearSelect);
		meeting.setWeekIndexSelect(weekIndexSelect);
		List<Meeting> list = this.meetingService.getGrid(user, jqGrid, meeting);
		return list;
	}

	// 根据roomId查询会议对象
	@RequestMapping("getByRoomId")
	public @ResponseBody
	List<Meeting> getByRoomId(String roomId, String expStartDate) {
		List<Meeting> list = this.meetingService.getByRoomId(roomId,
				expStartDate);
        if(list==null){
        	return new ArrayList<Meeting>();
        }
		return list;
	}

	// 获取所有加载预定页面时用到的时间
	@RequestMapping("getTimeDesc")
	public @ResponseBody
	List<String> getTimeDesc(String li_YearSelect, String weekIndexSelect) {
		Meeting meeting = new Meeting();
		meeting.setLi_YearSelect(li_YearSelect);
		meeting.setWeekIndexSelect(weekIndexSelect);
		List<String> list = this.meetingService.getTimeDesc(meeting);
		return list;
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
	Meeting get(String roomId) {
		Meeting meeting = meetingService.get(roomId);
		return meeting;

	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param roomId
	 *            主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	Meeting initEditOrViewPage(String meetingId, String roomId,
			String expStartDate) {
		Meeting entity = meetingService.initEditOrViewPage(meetingId, roomId,
				expStartDate, this.getUserProfile());
		return entity;
	}

	/**
	 * 保存或更新数据
	 * 
	 * @param report
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<Meeting> saveOrUpdate(Meeting meeting, String notifierIds,
			String notifierNames, String equips) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(Meeting.class);
		// 获取参会人员
		String[] meetingAttendEmps = new String[2];
		meetingAttendEmps[0] = notifierIds;
		meetingAttendEmps[1] = notifierNames;
		meeting.setMeetingAttendEmps(meetingAttendEmps);
		// 获取会议所用设备
		String meetingEqs = equips;
		meeting.setMeetingEqs(meetingEqs);
		// //根据会议地点id(roomId)获取会议地点名称
		String meetingRoom = null;
		try {
			meetingRoom = getMeetingRoomName().get(
					meeting.getMeetingRoomId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		meeting.setMeetingRoom(meetingRoom);
		Meeting entity = this.meetingService.saveOrUpdate(user, meeting,
				valueMap);
//		return OperResult.succeed(SalaryConstant.I18nKey.SAVE_SUCCESS,
//				null, null);
		return OperResult.succeed("saveMeetingSuccess", null, entity);
	}

	/**
	 * 验证预定会议时同一时间同一会议室是否发生冲突
	 * @return
	 */
	@RequestMapping("checkClash")
	public @ResponseBody String checkClash(Meeting meeting) {
		String sameTimes=this.meetingService.checkClash(meeting);
		return sameTimes;
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
	OperResult batchDel(String meetingId) {
		UserProfile user = this.getUserProfile();
		this.meetingService.delete(user, meetingId);
//		return OperResult.succeed(SalaryConstant.I18nKey.DELETE_SUCCESS,
//				null, null);
		return OperResult.succeed("deleteMeetingSuccess", null, null);
	}

	/**
	 * 获取字典数据-用于会议类型的下拉显示
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getMeetingTypeCodetable")
	public Map<Integer, String> getMeetingTypeCodetable() throws IOException {
		//Map<String, String> map = Meeting.getMeetingTypeCodeTable();
		List<MeetingType> list = this.meetingService.getAllMeetingType();
		// Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		// map.put(1, "内部会议");
		// map.put(2, "外部会议（内宾）");
		// map.put(3, "外部会议（外宾）");
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		if(list!=null){
			for (MeetingType item : list) {
				map.put(Integer.parseInt(CommonUtil.trim(item.getTypeCode())), item.getTypeName());
			}
		}	
		return map;
	}

	/**
	 * 获取字典数据-用于会议地点的下拉显示
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	public Map<String, String> getMeetingRoomName() throws IOException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<Object[]> list = meetingRoomService.getMeetRoom();
		if(list!=null){
			for (Object[] object : list) {
				String roomId = (String) object[0];
				String roomName = (String) object[1];
				map.put(roomId, roomName);
			}

		}
		return map;
	}

	
	/**
	 * 获取字典数据-用于会议地点的下拉显示
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getMeetingRoomCodetable")
	public List<JSONObject> getMeetingRoomCodetable() throws IOException {
		//Map<String, String> map = new LinkedHashMap<String, String>();
		List<Object[]> list = meetingRoomService.getMeetRoom();
		List<JSONObject> l = new ArrayList<JSONObject>();
		
		if(list!=null){
			for (Object[] object : list) {
				String roomId = (String) object[0];
				String roomName = (String) object[1];
				//JSONObject json = new JSONObject();
				String str ="{\"id\":\""+roomId+"\",\"name\":\""+roomName+"\"}";
				JSONObject json = JSONObject.fromObject(str);
				l.add(json);;
			}

		}
		return l;
	}
	
	
	/**
	 * 通过会议是设备的id获取会议室设备（如果有说明该会议用到了这个会议室的设备）
	 * 
	 * @param meetingId 会议id
	 * @param equipmentId 设备id
	 * @return String (yes/no)
	 */
	@RequestMapping("isExist")
	public @ResponseBody String isExist(String meetingId,String equipmentId) {
		String isExist=this.meetingService.isExist(meetingId,equipmentId);
		return isExist;
	}
	
	
	
	
}
