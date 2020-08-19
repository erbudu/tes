package com.supporter.prj.ppm.util;

import com.supporter.prj.eip.todo.dao.TodoDao;
import com.supporter.prj.eip.todo.entity.Todo;
import com.supporter.prj.eip.todo.service.TodoEntityService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.bms.service.IBMSService;
import com.supporter.prj.eip_service.person.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @autor 颜丙超
 * @date 2019/11/29
 */
@Service
public class PpmSendMessageUtil implements IBMSService {
    @Autowired
    private TodoDao todoDao;
    @Autowired
    private TodoEntityService todoEntityService;
    @Override
    public Message addMessage(Message message) {
        Todo todo = new Todo();
        todo.setPersonId(message.getPersonId());
        todo.setCreatedDate(new Date());
        todo.setMsgTitle(message.getTitle());
        todo.setModuleId(message.getModuleId());
        todo.setMsgType(message.getMessageType());
        todo.setWebPageUrl(message.getWebPageURL());
        todo.setWebappName(message.getWebappName());
        todo.setProcId(message.getWfProcId());
        System.out.println("保存的 nodeName:"+message.getNotifyModes());
        todo.setNodeName(message.getNotifyModes());
        this.todoDao.save(todo);

        return todo;
    }

    @Override
    public void updateMessage(Message message) {

    }

    @Override
    public void removeMessage(long l, long l1, Person person) {

    }

    @Override
    public void removeMessages(long l, long l1) {

    }

    @Override
    public void removeMessage(String s) {

    }

    @Override
    public void removeMessage(Message message) {

    }

    @Override
    public void setMessageReceived(String s) {

    }

    @Override
    public List<Message> getProcMessages(long l, long l1) {
        return null;
    }

    @Override
    public Message getMessage(long l, long l1, Person person) {
        return null;
    }

    @Override
    public Message getMessage(String msgId) {
        return this.todoEntityService.get(msgId);
    }

    @Override
    public Message newMessage() {
        Todo todo = new Todo();
        return todo;
    }

    @Override
    public List<Message> getMessageList(Person person) {
        return null;
    }

    @Override
    public List<Message> getMessageList(Person person, String s) {
        return null;
    }

    @Override
    public List<Message> getMessageList(Person person, String s, String s1) {
        return null;
    }

    @Override
    public List<Message> getUnreceivedMessageList(Person person, String s, String s1) {
        return null;
    }
}
