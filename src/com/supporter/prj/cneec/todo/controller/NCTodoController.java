package com.supporter.prj.cneec.todo.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.todo.service.NCTodoService;
import com.supporter.prj.log.controller.AbstractController;

@Controller
@RequestMapping({"/NC/todo"})
public class NCTodoController extends AbstractController {
    private static final long serialVersionUID = 1L;

    @Autowired
    private NCTodoService ncTodoService;
    
    /**
     * 获取网报系统单点登录url
     * @return
     * @throws NoSuchAlgorithmException 
     */
    @RequestMapping({ "/getSSOUrl" })
    @ResponseBody
    public String getSSOUrl() throws NoSuchAlgorithmException{
    	String userCode = this.getUserProfile().getAccount().getName();
    	userCode = "666" + userCode;
    	return ncTodoService.getSSOUrl(userCode);
    }
    
    /**
     * 获取网报系统消息数量
     * @return
     */
    @RequestMapping({ "/queryUserMsgCount" })
    @ResponseBody
    public String queryUserMsgCount(){
    	String userCode = this.getUserProfile().getAccount().getName();
    	userCode = "666" + userCode;
    	return ncTodoService.queryUserMsgCount(userCode);
    }
}
