package com.supporter.prj.ppm.util;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip_service.bms.entity.Message;

/**
 * @autor 颜丙超
 * @date 2019/11/29
 */
public class PpmSendMessage {
    public static Message addMessage(Message message){
        Message m = getService().addMessage(message);
        return m;
    }
    public static PpmSendMessageUtil getService(){
        PpmSendMessageUtil service = (PpmSendMessageUtil) SpringContextHolder.getBean(PpmSendMessageUtil.class);
        return service;
    }

}
