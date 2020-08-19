package com.supporter.prj.ppm.meeting_notice.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.prj.ppm.meeting_notice.constant.MeetingNoticeContant;
import com.supporter.prj.ppm.meeting_notice.dao.MeetingNoticeDao;
import com.supporter.prj.ppm.meeting_notice.entity.MeetingNoticeEntity;
import com.supporter.util.UUIDHex;

/**
 *<p>Title: MeetingNoticeService</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author CHENHAO
 * @date 2019年11月25日
 * 
 */

@Service
public class MeetingNoticeService {

	@Autowired
	private MeetingNoticeDao meetingNoticeDao;
	
	/**
	 * <pre>
	 * @Title : 获取会议通知页面数据
	 * @param businesPkName		业务单主键名称
	 * @param businesPkValue	业务单主键值
	 * @return 会议通知实体类
	 * </pre>
	 */
	
	public MeetingNoticeEntity initPageData(String businesPkName, String businesPkValue) {
		
		if(businesPkName == "" || businesPkName == null || businesPkValue == "" || businesPkValue == null) {
			
			return null;
		}
		MeetingNoticeEntity entity = meetingNoticeDao.getByBusinesPk(businesPkName, businesPkValue);
		
		if(entity == null) {
			entity = new MeetingNoticeEntity();
			entity.setNoticeId(UUIDHex.newId());
			entity.setIsNew(true);
			entity.setBusinesPkName(businesPkName);
			entity.setBusinesPkValue(businesPkValue);
			entity.setStatus(MeetingNoticeContant.CREATE_MEETING);
		}
		
		return entity;
	}
	
	/**
	 * <pre>
	 * @Title : 保存或修改会议通知信息
	 * @param entity 表单数据绑定的实体类
	 * @param user
	 * @return	执行结果
	 * </pre>
	 */

	public OperResult<MeetingNoticeEntity> saveOrUpdate(MeetingNoticeEntity entity, UserProfile user) {
		
		if(entity.getIsNew()) {
			
			setCreaetInfo(user, entity);
			
			entity.setNoticeId(UUIDHex.newId());
			
			meetingNoticeDao.save(entity);
			
			entity.setIsNew(false);
			
		}else {
			
			setMOdifiedInfo(user, entity);
			
			meetingNoticeDao.update(entity);
		}
		
		return OperResult.succeed("保存成功！", null, entity);
	}
	
	/**
	 * <pre>
	 * @Title : 删除会议通知信息
	 * @param noticeId	会议通知实体主键
	 * </pre>
	 */
	
	public void delete(String noticeId) {
		
		if(noticeId != "" && noticeId != null) {
			
			MeetingNoticeEntity entity = meetingNoticeDao.get(noticeId);
			
			if(entity != null) {
				
				meetingNoticeDao.delete(entity);
			}
		}
	}
	
	/**
	 * <pre>
	 * @Title : 发送会议通知待办信息
	 * @param noticeId	会议通知实体主键
	 * @return	会议通知实体信息
	 * </pre>
	 */
	
	public MeetingNoticeEntity sendNotice(String noticeId) {
		
		if(noticeId == "" || noticeId == null) {
			
			return null;
		}
		
		MeetingNoticeEntity entity = meetingNoticeDao.get(noticeId);
		
		//entity.setProcId(UUIDHex.newId());  			//暂用
		entity.setStatus(MeetingNoticeContant.MEETING_VIEW);
		
		String hostTitle = "知会:" + entity.getDeptName() + "-" + entity.getCreatedBy() + "发起的会议，请您作为主持人参加。";
		String personTitle = "知会:" + entity.getDeptName() + "-" + entity.getCreatedBy() + "发起的会议，请您参加。";
		String url = "ppm/meeting_notice/meeting_noticeAduit.html?noticeId="+entity.getNoticeId();
		
		sendNotice(entity.getHostId(), hostTitle, url, entity.getProcId());
		
		for(String personId : entity.getPersonIds().split(",")) {
			sendNotice(personId, personTitle, url, entity.getProcId());
		}
		
		meetingNoticeDao.update(entity);
		
		return entity;
	}

	/**
	 * <pre>
	 * 发送待办通知
	 * </pre>
	 * 
	 * @param personId 通知人主键
	 * @param title    待办标题
	 * @param url      待办页面路径
	 * @param procId   流程id
	 */
	private void sendNotice(String personId, String title, String url, String procId) {

		Message message = EIPService.getBMSService().newMessage();// --------------------------------获取待办通知服务内容
		
		message.setPersonId(personId);// ------------------------------------------------------------通知人
		message.setEventTitle(title);// -------------------------------------------------------------通知待办标题
		message.setNotifyTime(new Date());// --------------------------------------------------------通知待办日期
		message.setWebPageURL(url);// ---------------------------------------------------------------通知待办地址
		message.setModuleId(MeetingNoticeContant.MODULE_ID);// -----------------------------------------应用编号
		message.setMessageType(ITodo.MsgType.CC);// -------------------------------------------------待办类型（默认地指定为“待办”类型）
		message.setWebappName("BM");
		message.setWfProcId(procId);
		
		EIPService.getBMSService().addMessage(message);// -------------------------------------------获取待办服务发送待办

	}
	
	/**
	 * <pre>
	 * @Title : 获取会议通知列表
	 * @param user
	 * @param jqGrid
	 * </pre>
	 * @param businesPkName 
	 * @param businesPkValue 
	 * @param prjId 
	 */
	public void getGrid(UserProfile user, JqGrid jqGrid, String businesPkValue, String businesPkName, String prjId) {
			
			meetingNoticeDao.getGrid(jqGrid, businesPkValue, businesPkName, prjId);
	}
	
	/**
	 * <pre>
	 * @Title : 根据主键查找会议通知信息
	 * @param noticeId  会议通知实体类主键
	 * @return		会议通知信息
	 * </pre>
	 */
	public MeetingNoticeEntity getByNoticeId(String noticeId) {
		
		if(noticeId == "" || noticeId == null) {
			return null;
		}
		
		return meetingNoticeDao.get(noticeId);
	}
	
	/**
	 * <pre>
	 * 给实体类添加创建人信息
	 * @param user
	 * @param entity
	 * </pre>
	 */
	
	private void setCreaetInfo(UserProfile user, MeetingNoticeEntity entity) {
		
		entity.setStatus(MeetingNoticeContant.CREATE_MEETING);
		entity.setCreatedBy(user.getName());
		entity.setCreatedById(user.getPersonId());
		entity.setCreatedDate(new Date());
		Dept dept = user.getDept();
		if(dept != null) {
			entity.setDeptName(dept.getName());
			entity.setDeptId(dept.getDeptId());
		}else {
			entity.setDeptName("admin");
			entity.setDeptId("1");
		}
	}
	
	/**
	 * <pre>
	 * 给实体类添加修改人信息
	 * @param user
	 * @param entity
	 * </pre>
	 */
	
	private void setMOdifiedInfo(UserProfile user, MeetingNoticeEntity entity) {

		entity.setModifiedBy(user.getName());
		entity.setModifiedById(user.getPersonId());
		entity.setModifiedDate(new Date());
		
	}

	
	
}
