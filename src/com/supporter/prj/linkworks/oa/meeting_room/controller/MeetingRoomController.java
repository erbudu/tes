package com.supporter.prj.linkworks.oa.meeting_room.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApplyPerson;
import com.supporter.prj.linkworks.oa.meeting_room.entity.MeetingRoom;
import com.supporter.prj.linkworks.oa.meeting_room.entity.MeetingRoomEq;
import com.supporter.prj.linkworks.oa.meeting_room.service.MeetingRoomService;
import com.supporter.prj.linkworks.oa.meeting_room.util.AuthConstant;
import com.supporter.prj.linkworks.oa.meeting_room.util.AuthUtil;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("oa/meeting_room")
public class MeetingRoomController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private MeetingRoomService meetingRoomService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * 
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq,
			MeetingRoom meetingRoom) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.meetingRoomService.getGrid(user, jqGrid, meetingRoom);
		return jqGrid; 
	}

	/**
	 * 根据主键获取功能模块
	 * 
	 * @param reportId
	 *            主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody
	MeetingRoom get(String roomId) {
		MeetingRoom meetingRoom = meetingRoomService.get(roomId);
		return meetingRoom;

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
	MeetingRoom initEditOrViewPage(String roomId) {
		MeetingRoom entity = meetingRoomService.initEditOrViewPage(roomId, this
				.getUserProfile());
		return entity;
	}

	/**
	 * 保存或更新数据
	 * 
	 * @param report
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<MeetingRoom> saveOrUpdate(MeetingRoom meetingRoom) {		
		UserProfile user = this.getUserProfile();
		//权限验证（判断是不是有新建和修改会议室的权限）
		AuthUtil.canExecute(user, AuthConstant.AUTH_OPER_NAME_AUTHMEETINGROOMOFBTN, meetingRoom.getRoomId(), meetingRoom);
		Map<String, Object> valueMap = this.getPropValues(MeetingRoom.class);
		MeetingRoom entity = this.meetingRoomService.saveOrUpdate(user,
				meetingRoom, valueMap);
//		return OperResult.succeed(SalaryConstant.I18nKey.SAVE_SUCCESS,
//				null, null);
		return OperResult.succeed("saveSuccess", null, entity);
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
	OperResult batchDel(String roomIds) {
		UserProfile user = this.getUserProfile();
		this.meetingRoomService.delete(user, roomIds);
//		return OperResult.succeed(SalaryConstant.I18nKey.DELETE_SUCCESS,
//				null, null);
		return OperResult.succeed("deleteSuccess", null, null);
	}

	/**
	 * 会议室设备列表.
	 * 
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getEquipmentGrid")
	public @ResponseBody
	JqGrid getEquipmentGrid(HttpServletRequest request, JqGridReq jqGridReq,
			AuthorityApplyPerson ap, String roomId) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		UserProfile user = this.getUserProfile();
		@SuppressWarnings("unused")
		List<MeetingRoomEq> list = this.meetingRoomService.getEquipMentGrid(
				user, jqGrid, roomId);
		return jqGrid;
	}
}
