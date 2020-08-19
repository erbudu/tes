package com.supporter.prj.linkworks.oa.meeting.service;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.mail.auto_send_mail.SendMailService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.todo.entity.Todo;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.account.entity.Account;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.emp.entity.IEmployee;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.prj.linkworks.oa.meeting.dao.MeetingAttendEmpDao;
import com.supporter.prj.linkworks.oa.meeting.dao.MeetingDao;
import com.supporter.prj.linkworks.oa.meeting.dao.MeetingEqDao;
import com.supporter.prj.linkworks.oa.meeting.dao.MeetingTypeDao;
import com.supporter.prj.linkworks.oa.meeting.entity.Meeting;
import com.supporter.prj.linkworks.oa.meeting.entity.MeetingAttendEmp;
import com.supporter.prj.linkworks.oa.meeting.entity.MeetingEq;
import com.supporter.prj.linkworks.oa.meeting.entity.MeetingType;
import com.supporter.prj.linkworks.oa.meeting_room.dao.MeetingRoomEqDao;
import com.supporter.prj.linkworks.oa.meeting_room.entity.MeetingRoom;
import com.supporter.prj.linkworks.oa.meeting_room.service.MeetingRoomService;
import com.supporter.prj.linkworks.oa.meeting_room.util.AuthConstant;
import com.supporter.prj.linkworks.oa.meeting_room.util.AuthUtil;
import com.supporter.prj.linkworks.oa.meeting_room.util.LogConstant;
import com.supporter.util.CommonUtil;
import com.supporter.util.XCalendar;

@Service
public class MeetingService {
	@Autowired
	private MeetingDao meetingDao;
	@Autowired
	private MeetingEqDao meetingEqDao;
	@Autowired
	private MeetingAttendEmpDao meetingAttendEmpDao;
	@Autowired
	private MeetingRoomEqDao meetingRoomEqDao;
	@Autowired
	private MeetingTypeDao meetingTypeDao;
	@Autowired
	private MeetingRoomService meetingRoomService;
	

	public Meeting get(String moduleId) {
		return meetingDao.get(moduleId);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param moduleId
	 * @return
	 */
	public Meeting initEditOrViewPage(String meetingId, String roomId,
			String expStartDate, UserProfile user) {
		if (StringUtils.isBlank(meetingId)) {// 新建
			Meeting meeting = newMeeting(user);
			// 会议日期
			meeting.setExpStartDate(expStartDate);
			meeting.setMeetingRoomId(roomId);
			meeting.setAdd(true);
			return meeting;
		} else {// 编辑
			// 获得主表
			Meeting meeting = meetingDao.get(meetingId);
			if(meeting!=null){				
				// 处理会议类型的显示
				
				String meetingTypeDesc ="";
	            int meetingType=meeting.getMeetingType();
				if(meetingType==1){
					 meetingTypeDesc = "内部会议";			
				}else if(meetingType==2){
					meetingTypeDesc = "外部会议（内宾）";
				}else if(meetingType==3){
					meetingTypeDesc = "外部会议（外宾）";
				}else if(meetingType==4){
					meetingTypeDesc = "其他";
				}
				meeting.setMeetingTypeDesc(meetingTypeDesc);
				
				
	
				// 根据Meeting的id获取所有的参会人员
				List<MeetingAttendEmp> listEmp = this.meetingAttendEmpDao
						.findByMeetingId(meetingId);
				String notifierIds="";
				String notifierNames="";
							
				if (listEmp != null) {
					//String[] meetingAttendEmps = new String[listEmp.size()];
					for (int i = 0; i < listEmp.size(); i++) {
						//meetingAttendEmps[i] = listEmp.get(i).getEmpName();
						notifierIds=notifierIds+listEmp.get(i).getEmpId()+",";
						notifierNames=notifierNames+listEmp.get(i).getEmpName()+",";
					}
					//meeting.setMeetingAttendEmps(meetingAttendEmps);
					if(notifierIds.length()>0){
						meeting.setNotifierIds(notifierIds.substring(0, notifierIds.length()-1));
					}
					if(notifierNames.length()>0){
						meeting.setNotifierNames(notifierNames.substring(0, notifierNames.length()-1));
					}
					
				}
				meeting.setListEmp(listEmp);
	
				// 根据Meeting的id获取所有的参会设备
				List<MeetingEq> listEq = this.meetingEqDao
						.findByMeetingId(meetingId);
				if (listEq != null) {
					String meetingEqs = "";
					int count=1;
					for (MeetingEq meetingEq : listEq) {
						if(count==listEq.size()){
							meetingEqs+= meetingEq.getEquipmentName();
						}else{
							meetingEqs+= meetingEq.getEquipmentName()+",";
						}
						count++;
						
					}
					meeting.setMeetingEqs(meetingEqs);
				}
				meeting.setListEq(listEq);
				meeting.setAdd(false);
				return meeting;
			}else{
				return new Meeting();
			}
			
		}
		
	}

	/**
	 * 新建实例,并初始化必要的属性.
	 * 
	 * @param auserprf_U
	 * @return
	 */
	public Meeting newMeeting(UserProfile auserprf_U) {
		Meeting lmeeting_N = new Meeting();
		lmeeting_N.setMeetingId(com.supporter.util.UUIDHex.newId());
		lmeeting_N.setCreatedById(auserprf_U.getPersonId());
		lmeeting_N.setCreatedBy(auserprf_U.getName());
		String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
		lmeeting_N.setCreatedDate(date);
		return lmeeting_N;
	}

	// 根据roomId查询会议对象，
	public List<Meeting> getByRoomId(String roomId, String expStartDate) {
		List<Meeting> listZ = new ArrayList<Meeting>();
		List<Meeting> list = this.meetingDao.getByRoomId(roomId, expStartDate);
		// 遍历会议list
		if (list != null) {
			for (Meeting meetingY : list) {

				// 根据Meeting的id获取所有的参会人员
				List<MeetingAttendEmp> listEmp = this.meetingAttendEmpDao
						.findByMeetingId(meetingY.getMeetingId());
				// 根据Meeting的id获取所有的参会设备
				List<MeetingEq> listEq = this.meetingEqDao
						.findByMeetingId(meetingY.getMeetingId());

				meetingY.setListEmp(listEmp);
				meetingY.setListEq(listEq);
				listZ.add(meetingY);
			}
		}
		return list;
	}
	
	//从会议类型数据库表中遍历出所有的会议类型
	public List<MeetingType> getAllMeetingType(){
		List<MeetingType> list = this.meetingTypeDao.findAll();
		return list;
	}

	// 获取所有加载预定页面时用到的时间
	public List<String> getTimeDesc(Meeting meeting) {
		List<String> list = new ArrayList<String>();
		int li_Year = 0;
		int weekIndex = 0;
		String dateBegin = "";
		String dateEnd = "";
		Date ld_WeekBegin = null;
		Date ld_WeekEnd = null;

		if (!meeting.getLi_YearSelect().equals("")
				&& !meeting.getWeekIndexSelect().equals("")) {
			// 将String类型的year 转成int
			li_Year = Integer.parseInt(meeting.getLi_YearSelect());
			weekIndex = Integer.parseInt(meeting.getWeekIndexSelect());
			// 获取第weekIndex周的第一天
			ld_WeekBegin = meeting.getFirstDateOfWeek(li_Year, weekIndex);
			// 获取第weekIndex周的最后一天
			ld_WeekEnd = meeting.getLastDateOfWeek(li_Year, weekIndex);
			// 首先要将date转换成String
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateBegin = sdf.format(ld_WeekBegin);
			dateEnd = sdf.format(ld_WeekEnd);

		} else {
			// 得到当前时间的年份 然后转成int类型
			SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
			String year = sdfYear.format(new Date());
			// 将String类型的year 转成int
			li_Year = Integer.parseInt(year);
			// 获取当前时间是第几周
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String ad_Date = format.format(date);
			weekIndex = 0;
			try {
				weekIndex = meeting.getWeekIndex(format.parse(ad_Date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 获取第weekIndex周的第一天
			ld_WeekBegin = meeting.getFirstDateOfWeek(li_Year, weekIndex);
			// 获取第weekIndex周的最后一天
			ld_WeekEnd = meeting.getLastDateOfWeek(li_Year, weekIndex);

			// 首先要将date转换成String
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateBegin = sdf.format(ld_WeekBegin);
			dateEnd = sdf.format(ld_WeekEnd);
		}
		list.add(String.valueOf(li_Year));
		list.add(String.valueOf(weekIndex));
		list.add(dateBegin);
		list.add(dateEnd);

		for (int li_DayIndex = 1; li_DayIndex <= 7; li_DayIndex++) {
			Date ld_Date = XCalendar
					.relativeDate(ld_WeekBegin, li_DayIndex - 1);
			XCalendar lcal_C = new XCalendar();
			lcal_C.setDate(ld_Date);
			String ls_DayDesc = CommonUtil.format(ld_Date, "yyyy-MM-dd") + "("
					+ lcal_C.getDayOfWeekC() + ")";
			list.add(ls_DayDesc);
		}

		return list;
	}

	public Meeting get(UserProfile user, JqGrid jqGrid, Meeting meeting) {
		List<Meeting> listZ = new ArrayList<Meeting>();
		List<Meeting> list = this.meetingDao.findPage(meeting);
		// 遍历会议list
		if (list.size() > 0) {
			for (Meeting meetingY : list) {

				// 根据Meeting的id获取所有的参会人员
				List<MeetingAttendEmp> listEmp = this.meetingAttendEmpDao
						.findByMeetingId(meetingY.getMeetingId());
				// 根据Meeting的id获取所有的参会设备
				List<MeetingEq> listEq = this.meetingEqDao
						.findByMeetingId(meetingY.getMeetingId());

				meetingY.setListEmp(listEmp);
				meetingY.setListEq(listEq);
				listZ.add(meetingY);
			}
		}
		if (listZ.size() > 0) {
			return listZ.get(0);
		} else {

			return new Meeting();
		}

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
	 * @return List<Meeting>
	 */
	public List<Meeting> getGrid(UserProfile user, JqGrid jqGrid,
			Meeting meeting) {
		List<Meeting> listZ = new ArrayList<Meeting>();

		List<Meeting> list = this.meetingDao.findPage(meeting);
		// 遍历会议list
		if (list.size() > 0) {
			for (Meeting meetingY : list) {

				// 根据Meeting的id获取所有的参会人员
				List<MeetingAttendEmp> listEmp = this.meetingAttendEmpDao
						.findByMeetingId(meetingY.getMeetingId());
				// 根据Meeting的id获取所有的参会设备
				List<MeetingEq> listEq = this.meetingEqDao
						.findByMeetingId(meetingY.getMeetingId());

				meetingY.setListEmp(listEmp);
				meetingY.setListEq(listEq);
				listZ.add(meetingY);
			}
		}
		return listZ;
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
	public Meeting saveOrUpdate(UserProfile user, Meeting meeting,
			Map<String, Object> valueMap) {
		Meeting ret = null;
		String editType = "update";
		if (meeting.getAdd()) {// 新建
			editType = "save";
			//会议开始时间		   
			meeting.setExpStartDate(startDate(meeting));
			
			// 1.保存从表 OA_MEETING_EQ（会议所用设备）
			if(meeting.getMeetingEqs()!=null&&!meeting.getMeetingEqs().equals("")){
				String[] meetingEqs = meeting.getMeetingEqs().split(",");
				for (int i = 0; i < meetingEqs.length; i++) {
					MeetingEq meetingEq = new MeetingEq();
					meetingEq.setRecordId(com.supporter.util.UUIDHex.newId());
					meetingEq.setMeetingId(meeting.getMeetingId());

					meetingEq.setEquipmentId(meetingEqs[i]);
					if(meetingRoomEqDao.get(meetingEqs[i])!=null){
						String equipmentName = meetingRoomEqDao.get(meetingEqs[i])
						.getEquipmentName();
						meetingEq.setEquipmentName(equipmentName);
					}					
					
					this.meetingEqDao.save(meetingEq);
				}
			}
			// 2.保存主表
			this.meetingDao.save(meeting);
			
			// 3.保存从表OA_MEETING_ATTEND_EMP（参见会议人员）
			String[] meetingAttend = meeting.getMeetingAttendEmps();
			if(meetingAttend!=null){
				String[] empId = meetingAttend[0].split(",");
				String[] empName = meetingAttend[1].split(",");
				for (int i = 0; i < empId.length; i++) {
					MeetingAttendEmp meetingAttendEmp = new MeetingAttendEmp();
					meetingAttendEmp
							.setAttendId(com.supporter.util.UUIDHex.newId());
					meetingAttendEmp.setMeetingId(meeting.getMeetingId());
					meetingAttendEmp.setEmpId(empId[i]);
					meetingAttendEmp.setEmpName(empName[i]);
					meetingAttendEmp.setIsNotified(0L);
					meetingAttendEmp.setConfirmedStatus(0L);
					meetingAttendEmp.setMemberSource(0L);
					meetingAttendEmp.setActAttendStatus(0L);
					meetingAttendEmp.setDisplayOrder(0L);
					this.meetingAttendEmpDao.save(meetingAttendEmp);
					
					if(meeting.getNoticeModes()!=null){//通知方式不为空
						if(meeting.getNoticeModes().equals("IM")){//即时消息
					    	//调用发代办的方法
							sendMessagesToEmp(meeting, empId[i], "save", false);
					    }else if(meeting.getNoticeModes().equals("EMAIL")){//电子邮件
					    	//调用发电子邮件的方法
					    	sendEmaillToEmp(meeting, empId[i], "save");
					    }else if(meeting.getNoticeModes().equals("IM,EMAIL")){//即时消息，电子邮件
					    	//调用发代办的方法
							sendMessagesToEmp(meeting, empId[i], "save", false);
					    	//调用发电子邮件的方法
					    	sendEmaillToEmp(meeting, empId[i], "save");						
					    }
					}
				}
				
			}

		    
			ret = meeting;
			// 记录日志
			/*
			 * MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
			 * MaterialCode.LogOper.MATERIALCODE_ADD.getOperName(), null);
			 */
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.ADD_MEETING_LOG_MESSAGE, meeting.getMeetingName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS1).info(
					user, LogConstant.ADD_MEETING_LOG_ACTION, logMessage,
					meeting, null);
		} else {// 编辑
			
			//权限验证(判断是不是有修改会议的权限)
			AuthUtil.canExecute(user, AuthConstant.AUTH_OPER_NAME_AUTHMEETINGOFBTN, meeting.getMeetingId(), meeting);			
			//会议开始时间		
			meeting.setExpStartDate(startDate(meeting));
			meeting.setModifiedBy(user.getName());
			meeting.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			meeting.setModifiedDate(date);
			
			
			
			// 编辑之后保存从表 OA_MEETING_EQ（会议所用设备）
			//1.首先删除修改之前的设备
				// 根据MeetingId获取所有的参会设备
				List<MeetingEq> listEq = this.meetingEqDao
						.findByMeetingId(meeting.getMeetingId());
	
				if (listEq != null) {
					// 遍历删除
					for (MeetingEq meetingEq : listEq) {
						this.meetingEqDao.delete(meetingEq.getRecordId());
					}
				}
			//2.然后保存修改之后的设备
			if(meeting.getMeetingEqs()!=null){
				String[] meetingEqs = meeting.getMeetingEqs().split(",");
				for (int i = 0; i < meetingEqs.length; i++) {
					MeetingEq meetingEq = new MeetingEq();
					meetingEq.setRecordId(com.supporter.util.UUIDHex.newId());
					meetingEq.setMeetingId(meeting.getMeetingId());

					meetingEq.setEquipmentId(meetingEqs[i]);
					String equipmentName = meetingRoomEqDao.get(meetingEqs[i])
							.getEquipmentName();
					meetingEq.setEquipmentName(equipmentName);
					this.meetingEqDao.save(meetingEq);
				}	

			}
						
			// 编辑之后保存主表
			this.meetingDao.update(meeting);
			
			
			// 编辑之后保存从表OA_MEETING_ATTEND_EMP（参加会议人员）
			//1.首先删除修改之前的人员
				    //根据MeetingId获得与之相关的参会人员的列表，然后删除
					List<MeetingAttendEmp> listEmp = this.meetingAttendEmpDao
					.findByMeetingId(meeting.getMeetingId());
			
					if (listEmp != null) {
						// 遍历删除
						for (MeetingAttendEmp meetingAttendEmp : listEmp) {
							this.meetingAttendEmpDao.delete(meetingAttendEmp
									.getAttendId());
						}
					}
					
			//2.保存修改之后的人员
			String[] meetingAttend = meeting.getMeetingAttendEmps();
			if(meetingAttend!=null){
				String[] empId = meetingAttend[0].split(",");
				String[] empName = meetingAttend[1].split(",");
				for (int i = 0; i < empId.length; i++) {
					MeetingAttendEmp meetingAttendEmp = new MeetingAttendEmp();
					meetingAttendEmp
							.setAttendId(com.supporter.util.UUIDHex.newId());
					meetingAttendEmp.setMeetingId(meeting.getMeetingId());
					meetingAttendEmp.setEmpId(empId[i]);
					meetingAttendEmp.setEmpName(empName[i]);
					meetingAttendEmp.setIsNotified(0L);
					meetingAttendEmp.setConfirmedStatus(0L);
					meetingAttendEmp.setMemberSource(0L);
					meetingAttendEmp.setActAttendStatus(0L);
					meetingAttendEmp.setDisplayOrder(0L);
					this.meetingAttendEmpDao.save(meetingAttendEmp);
					
					if(meeting.getNoticeModes()!=null){//通知方式不为空
						if(meeting.getNoticeModes().equals("IM")){//即时消息
					    	//调用发代办的方法
							sendMessagesToEmp(meeting, empId[i], "update", false);
					    }else if(meeting.getNoticeModes().equals("EMAIL")){//电子邮件
					    	//调用发电子邮件的方法
					    	sendEmaillToEmp(meeting, empId[i], "update");
					    }else if(meeting.getNoticeModes().equals("IM,EMAIL")){//即时消息，电子邮件
					    	//调用发代办的方法
							sendMessagesToEmp(meeting, empId[i], "update", false);
					    	//调用发电子邮件的方法
					    	sendEmaillToEmp(meeting, empId[i], "update");						
					    }
					}
				}
			}


			ret = meeting;
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.EDIT_MEETING_LOG_MESSAGE, meeting.getMeetingName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS1).info(
					user, LogConstant.EDIT_MEETING_LOG_ACTION, logMessage,
					meeting, null);
		}
		
		MeetingRoom mettingRoom = meetingRoomService.get(meeting.getMeetingRoomId());
		String notityPartyIds = mettingRoom.getNotityPartyIds();
		if (StringUtils.isNotBlank(notityPartyIds)) {//如果所选会议室的通知人员不为空，则给通知人员发送通知
			String[] notityPersonIds = notityPartyIds.split(",");
			for (String notityPartyId:notityPersonIds) {
				//调用发代办的方法
				sendMessagesToEmp(meeting, notityPartyId, editType, true);
			}
		}
		return ret;

	}
	
	public String checkClash(Meeting meeting){
		//在保存之前首先要判断一下该会议室在此时间段内是否已经被预定
		//分两种情况（1：单纯的时间冲突 2：修改会议）
		String meetRoomId=meeting.getMeetingRoomId();//会议室的id
		String periodPoints=meeting.getPeriodPoints();//使用会议室的时间段
		String expStartDate=meeting.getExpStartDate();//会议日期
		String sameTime="";
		if(periodPoints!=null){
			String[] periodPointIsNotRe=periodPoints.split(",");
			List<Meeting> list=this.meetingDao.getByRoomId(meetRoomId,expStartDate);
			sameTime=getClashTime(meeting.getMeetingId(),list,periodPointIsNotRe);
		}

		return sameTime;
	}
	/**
	 * 返回预定会议时 发生冲突的时间
	 * 
	 * @param list
	 * @param periodPointIsNotRe
	 * @return
	 */
	public String getClashTime(String meetingId,List<Meeting> list, String[] periodPointIsNotRe) {
		Set<String> sameElementSet = new HashSet<String>();// 用来存放两个数组中相同的元素
		Set<String> tempSet = new HashSet<String>();// 用来存放已经预定的会议的开会时间段
		if (list != null && list.size()>0) {
			for (Meeting meetingIsReserve : list) {
				if (meetingIsReserve != null) {												
					if(meetingIsReserve.getMeetingId()!=null){						
						if(meetingIsReserve.getMeetingId().equals(meetingId)){//说明是修改会议室预定
							continue;
						}else{//说明是预定冲突
							String periodPointsIsRe = meetingIsReserve.getPeriodPoints();
							if (periodPointsIsRe != null) {
								String[] periodPointZ = periodPointsIsRe.split(",");
								for (int i = 0; i < periodPointZ.length; i++) {
									tempSet.add(periodPointZ[i]);// 把数组的元素放到Set中，可以去除重复的元素
								}						
			
							}						
						}
					}	
				}
			}
		}	
		for (int j = 0; j < periodPointIsNotRe.length; j++) {
			// 如果tempSet中已存在相同的元素，则tempSet.add（periodPointIsNotRe[j]）返回false
			if (!tempSet.add(periodPointIsNotRe[j])) {
				// 返回false,说明当前元素是两个数组中相同的元素
				sameElementSet.add(periodPointIsNotRe[j]);
			}
		}
		
		Iterator<String> it = sameElementSet.iterator();  
		String sameTime="";
		while (it.hasNext()) {  
		  String samePeriodPoint = it.next();  
			int periodPoint=Integer.parseInt(samePeriodPoint);
			String ls_TimePart = "30:00";	
			if ((periodPoint % 2) == 0){ 
				ls_TimePart = "00:00";
			}
			String sameTimes = CommonUtil.format((periodPoint / 2),"00") + ":" + ls_TimePart;
			sameTime+=sameTimes+" ";
		}
		return sameTime;
	}
	
	
	
	
	/**
	 * 发送即时消息给参会人员
	 * @param meeting
	 * @param empId
	 * @param type
	 * @param isNotifyParty
	 */
	public void sendMessagesToEmp(Meeting meeting,String empId,String type, boolean isNotifyParty){
		//登记人部门（创建人部门）
		//因为管理员没有所在部门。所以需要判断一下
		String deptName="";
    	if(!meeting.getCreatedById().equals("1")){
    		IEmployee lemp_CreatedBy= EIPService.getEmpService().getEmployee(meeting.getCreatedById());
    		if(lemp_CreatedBy!=null){
    			if(lemp_CreatedBy.getDept()!=null){
    				deptName=lemp_CreatedBy.getDept().getName();
    			}  			
    		}
    	}else{
    		deptName="管理员所在部门";
    	}
    	String title="";
    	if(type.equals("save")){//保存会议时
    		if (isNotifyParty) {
    			title="会议通知：";
    		}else {
    			title="请您参加会议：";
    		}
    	}else if(type.equals("delete")){//取消会议时
    		if (isNotifyParty) {
    			title="会议取消通知：";
    		}else {
    			title="会议取消：";
    		}
    	}else if(type.equals("update")){//会议更改
    		if (isNotifyParty) {
    			title="会议更改通知：";
    		}else {
    			title="会议更改：";
    		}
    	}
    	if(!title.equals("")){
    		String meetingName = meeting.getMeetingName();
    		meetingName = meetingName.length() <= 20 ? meetingName : meetingName.substring(0, 20) + "...";
    		title = title + meetingName + ",时间:" + CommonUtil.format(meeting.getExpStartDate(),"yyyy-MM-dd HH:mm")
            + ",地点:" + meeting.getMeetingRoom()
            + ",联系方式：(部门：" + deptName + " 登记人：" + CommonUtil.trim(meeting.getCreatedBy())
            + " 电话：" + CommonUtil.trim(meeting.getLinkmanPhone()) + ")";
    		//发送待办
    		Message messageCreat =new Todo();
    	    messageCreat.setPersonId(empId);
    		messageCreat.setEventTitle(title);
    		messageCreat.setNotifyTime(new Date());
    		messageCreat.setWebPageURL("oa/meeting/meetingSendTodo_view_zh.html?meetingId="
    				+ CommonUtil.trim(meeting.getMeetingId()));
//    		messageCreat.setWebPageURL("");
//    		messageCreat.setInToDoList(true); //强行指定在待办事宜中
    		messageCreat.setInvalidatedTime(CommonUtil.parseDateTime(meeting.getExpStartDate())); //预计开会时间就是待办提醒的失效时间
    		messageCreat.setMessageType(ITodo.MsgType.CC);	
    		messageCreat.setRelatedRecordTable(EIPService.getWebappService().getWebappName());
    		EIPService.getBMSService().addMessage(messageCreat);		
    	}
		
		
		
		
		
	}
	

	/**
	 * 发送邮件给参会人员
	 * @param empId
	 * @return
	 */
	public void sendEmaillToEmp(Meeting meeting,String empId,String type){	
		//登记人部门（创建人部门）
    	//因为管理员没有所在部门。所以需要判断一下
		String deptName="";
    	if(!meeting.getCreatedById().equals("1")){
    		IEmployee lemp_CreatedBy= EIPService.getEmpService().getEmployee(meeting.getCreatedById());
    		if(lemp_CreatedBy!=null){
    			if(lemp_CreatedBy.getDept()!=null){
    				deptName=lemp_CreatedBy.getDept().getName();
    			}  
    		}
    	}else{
    		deptName="管理员所在部门";
    	}
    	String title="";
    	if(type.equals("save")){//保存会议时
    		title="请您参加会议：";
    	}else if(type.equals("delete")){//取消会议时
    		title="会议取消：";
    	}else if(type.equals("update")){//会议更改
    		title="会议更改：";
    	}
    	
    	if(!title.equals("")){
    		title=title+meeting.getMeetingName()+",时间:"+CommonUtil.format(meeting.getExpStartDate(),"yyyy-MM-dd HH:mm")
            + ",地点:" + meeting.getMeetingRoom()
            + ",联系方式：(部门：" + deptName + " 登记人：" + CommonUtil.trim(meeting.getCreatedBy())
            + " 电话：" + CommonUtil.trim(meeting.getLinkmanPhone()) + ")";

		//邮件接收人
		Person person=EIPService.getEmpService().getEmployee(empId);
		//一个人可以对应多个账号
		List<Account>  list=EIPService.getAccountService().getAccounts(person);
			if(list!=null&&list.size()>0){
				//调用发送邮件的方法：
		        SendMailService.sendMail(CommonUtil.trim(list.get(0).getName()),CommonUtil.trim(title),
		        CommonUtil.trim(title));
			}
    	}
		 
		
	}
	
	
    //生成会议开始时间
	public String startDate(Meeting meeting){
		String periodPoints=meeting.getPeriodPoints();
		String[] periodPointZ=periodPoints.split(",");
		int periodPoint=Integer.parseInt(periodPointZ[0]);
		String ls_TimePart = "30:00";	
		if ((periodPoint % 2) == 0){ 
			ls_TimePart = "00:00";
		}
		String expStartDate = CommonUtil.format(meeting.getExpStartDate(),"yyyy-MM-dd");
		String ls_BeginTime = expStartDate
			+ " " 
			+ CommonUtil.format((periodPoint / 2),"00") + ":" + ls_TimePart;
		return ls_BeginTime;
	}

	/**
	 * 删除
	 * 
	 * @param user
	 *            用户信息
	 * @param moduleIds
	 *            主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String meetingIds) {
		if (StringUtils.isNotBlank(meetingIds)) {
			for (String meetingId : meetingIds.split(",")) {
				Meeting meeting = this.get(meetingId);
				if(meeting == null){
					continue;
				}else {
					//权限验证(判断是不是有删除会议的权限)
					AuthUtil.canExecute(user, AuthConstant.AUTH_OPER_NAME_AUTHMEETINGOFBTN, meetingId, meeting);
					
					// 根据MeetingId获取所有的参会设备，并删除参会设备信息
					List<MeetingEq> listEq = this.meetingEqDao.findByMeetingId(meetingId);
					if (listEq != null) {
						// 遍历删除
						for (MeetingEq meetingEq : listEq) {
							this.meetingEqDao.delete(meetingEq.getRecordId());
						}
					}
					
					// 根据MeetingId获取所有的参会人员，并发送知会
					List<MeetingAttendEmp> listEmp = this.meetingAttendEmpDao.findByMeetingId(meetingId);
					if (listEmp != null) {
						// 遍历删除
						for (MeetingAttendEmp meetingAttendEmp : listEmp) {
							//先发送取消会议待办
							if(meeting.getNoticeModes()!=null){//通知方式不为空
								if(meeting.getNoticeModes().equals("IM")){//即时消息
							    	//调用发代办的方法
									sendMessagesToEmp(meeting, meetingAttendEmp.getEmpId(), "delete", false);
							    }else if(meeting.getNoticeModes().equals("EMAIL")){//电子邮件
							    	//调用发电子邮件的方法
							    	sendEmaillToEmp(meeting, meetingAttendEmp.getEmpId(), "delete");
							    }else if(meeting.getNoticeModes().equals("IM,EMAIL")){//即时消息，电子邮件
							    	//调用发代办的方法
									sendMessagesToEmp(meeting, meetingAttendEmp.getEmpId(), "delete", false);
							    	//调用发电子邮件的方法
							    	sendEmaillToEmp(meeting, meetingAttendEmp.getEmpId(), "delete");						
							    }
							}
							//再删除参会人员
							this.meetingAttendEmpDao.delete(meetingAttendEmp.getAttendId());
						}
					}

					//给会议室通知人员发送通知
					MeetingRoom mettingRoom = meetingRoomService.get(meeting.getMeetingRoomId());
					String notityPartyIds = mettingRoom.getNotityPartyIds();
					if (StringUtils.isNotBlank(notityPartyIds)) {//如果所选会议室的通知人员不为空，则给通知人员发送通知
						String[] notityPersonIds = notityPartyIds.split(",");
						for (String notityPartyId:notityPersonIds) {
							//调用发代办的方法
							sendMessagesToEmp(meeting, notityPartyId, "delete", true);
						}
					}
					
					//最后删除主表
					this.meetingDao.delete(meeting);
				}
			}
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.DELETE_MEETING_LOG_MESSAGE, "删除的会议主键为："+meetingIds);
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS1).info(
					user, LogConstant.DELETE_MEETING_LOG_ACTION, logMessage,
					null, null);
		}
	}
	
	
	
	
	/**
	 * 判断预定的会议有没有用到会议室设备，如果用到则该设备在会议室修改页面会议室设备一栏中被选中 
	 * 
	 * @param meetingId 会议id
	 * @param equipmentId 会议设备id
	 */
	public String isExist(String meetingId,String equipmentId) {
		String isExist="no";
		
		if(StringUtils.isNotBlank(meetingId)&&StringUtils.isNotBlank(equipmentId)){
				List<MeetingEq> list=this.meetingEqDao.findByMeetingIdAndMeeingEqId(meetingId,equipmentId);
				if (list!=null&&list.size()>0) {			
					isExist="yes";			
				}
		}

		return isExist;
	}
	
	
	
	

}
