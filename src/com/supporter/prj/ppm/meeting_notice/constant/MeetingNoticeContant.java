package com.supporter.prj.ppm.meeting_notice.constant;


/**
 *<p>Title: MeetingNoticeContant</p>
 *<p>Description: 会议通知 常量类</p>
 * @author CHENHAO
 * @date 2019年11月25日
 */
public class MeetingNoticeContant {

	//会议通知状态
	/**
     * 	  新建会议通知
     */
    public static final int CREATE_MEETING = 0; 	
    
    /**
     * 	  会议通知已发送
     */
    public static final int MEETING_VIEW = 1;

    /**
     * 	会议通知应用ID
     */
	public static final String MODULE_ID = "MEETING"; 	
	
	/**
	 * 状态：草稿
	 */
	public static final String DRAFT = "草稿";
	
	/**
	 * 状态：已通知
	 */
	public static final String NOTICED = "已通知";
	
}
