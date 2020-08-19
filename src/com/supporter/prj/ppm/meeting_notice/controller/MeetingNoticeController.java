package com.supporter.prj.ppm.meeting_notice.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.meeting_notice.entity.MeetingNoticeEntity;
import com.supporter.prj.ppm.meeting_notice.service.MeetingNoticeService;
import com.supporter.spring_mvc.AbstractController;

/**
 *<p>Title: 会议通知</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author CHENHAO
 * @date 2019年11月25日
 */
@Controller
@RequestMapping("ppm/meeting_notice/")
public class MeetingNoticeController extends AbstractController{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MeetingNoticeService meetingNoticeService;
	
	/**
	 * <pre>
	 * @Title :获取会议通知页面数据
	 * @param businesPkName		业务单主键名称
	 * @param businesPkValue	业务单主键值
	 * @return 会议通知实体类
	 * </pre>
	 */
	
	@RequestMapping("initPageData")
	public @ResponseBody MeetingNoticeEntity initPageData(String businesPkName, String businesPkValue) {
		
		return meetingNoticeService.initPageData(businesPkName, businesPkValue);
	}

	/**
	 * <pre>
	 * @Titile : 保存或修改会议通知信息
	 * @param entity	表单数据绑定的实体类
	 * @return 执行结果
	 * </pre>
	 */
	
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<MeetingNoticeEntity> saveOrUpdate(MeetingNoticeEntity entity){
		
		UserProfile user = getUserProfile();
		
		return meetingNoticeService.saveOrUpdate(entity,user);
	}
	
	/**
	 * <pre>
	 * @Title : 删除会议通知信息
	 * @param noticeId	会议通知实体主键	
	 * @return		执行结果
	 * </pre>
	 */
	
	@RequestMapping("delete")
	public @ResponseBody OperResult delete(String noticeId) {
		
		meetingNoticeService.delete(noticeId);
		
		return OperResult.succeed(null);
	}
	
	/**
	 * <pre>
	 * @Title : 发送会议通知待办信息
	 * @param noticeId	会议通知实体主键
	 * @return		会议通知信息
	 * </pre>
	 */
	
	@RequestMapping("sendNotice")
	public @ResponseBody MeetingNoticeEntity sendNotice(String noticeId) {
		
		return meetingNoticeService.sendNotice(noticeId);
	}
	
	/**
	 * <pre>
	 * @Title : 获取通知了的所有会议通知信息
	 * @param request
	 * @param jqGridReq
	 * @return	数据列表
	 * </pre>
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, String businesPkValue, String businesPkName, String prjId) {
		
		UserProfile user = getUserProfile();
		
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
	    
	    meetingNoticeService.getGrid(user, jqGrid, businesPkValue, businesPkName, prjId);
	    
		return jqGrid;
		
	}
	
	/**
	 * <pre>
	 * @Title : 根据主键查找会议通知内容
	 * @param noticeId	主键
	 * @return	会议通知实体
	 * </pre>
	 */
	
	@RequestMapping("initPageDataByNoticeId")
	public @ResponseBody MeetingNoticeEntity initPageDataByNoticeId(String noticeId){
		
		return meetingNoticeService.getByNoticeId(noticeId);
		
	}
}

