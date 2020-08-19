package com.supporter.prj.cneec.todo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.todo.service.TodoMobileService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.todo.entity.VTodo;
import com.supporter.prj.eip.todo.util.TodoQueryParams;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**
 * 首页待办controller.
 * @author liug
 *
 */
@Controller
@RequestMapping({"/cneec/mobileTodo"})
public class TodoMobileController extends AbstractController {
    private static final long serialVersionUID = 1L;

    @Autowired
    private TodoMobileService todoMobileService;
    
    /**
     * 返回指定个数的待办记录.
     * @param jqGridReq
     * @param rowCount
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping({ "/getTodos" })
    @ResponseBody
    public OperResult < List < Map < String, Object > > > getTodos(JqGridReq jqGridReq, int rowCount) throws Exception {
        JqGrid jqGrid = jqGridReq.initJqGrid(this.getRequest());
        
        TodoQueryParams queryParams = new TodoQueryParams();
        String msgTitle = CommonUtil.trim(this.getRequestPara("msgTitle"));
        if (msgTitle.length() > 0){
        	queryParams.setParam(TodoQueryParams.MSG_TITLE, msgTitle);
        }
		
        List < VTodo > msgs = todoMobileService.getTodos(jqGrid, this.getUserProfile(), queryParams, rowCount);
        
        List < Map < String, Object > > list = new ArrayList();
        if (msgs != null && msgs.size() > 0){
        	int size = msgs.size();
        	for (int i = 0; i < size; i++){
        		list.add(this.getTodoMap(msgs.get(i)));
        	}
        }

        return OperResult.succeed("0", "获取信息成功", list);
    }
    
    /**
     * 获取待办翻页列表.
     * @param jqGridReq
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping({ "/getTodosPage" })
    @ResponseBody
    public JqGrid getTodosPage(JqGridReq jqGridReq) throws Exception {
        
        JqGrid jqGrid = jqGridReq.initJqGrid(this.getRequest());
        
        TodoQueryParams queryParams = new TodoQueryParams();
        String msgTitle = CommonUtil.trim(this.getRequestPara("msgTitle"));
        if (msgTitle.length() > 0){
        	queryParams.setParam(TodoQueryParams.MSG_TITLE, msgTitle);
        }
		
        List < Map < String, Object > > list = new ArrayList();
        
        List < VTodo > todos = todoMobileService.getTodosPage(jqGrid, this.getUserProfile(), queryParams);
        if (todos == null || todos.size() == 0)return jqGrid;
        
        int size = todos.size();
        for (int i = 0; i < size; i++) {
        	VTodo todo = todos.get(i);
        	
            list.add(getTodoMap(todo));
        }
        jqGrid.setRows(list);
        return jqGrid;
    }
    
    /**
     * 获取待办总记录数.
     * @return
     * @throws Exception
     */
    @RequestMapping({ "/getTotalCount" })
    @ResponseBody
    public int getTotalCount() throws Exception {
    	return todoMobileService.getTotalCount(this.getUserProfile(), null);
    }
    
    /**
     * 得到待办对象.
     * @param messageId
     * @param webappName
     * @return
     */
    @RequestMapping({ "/getTodo" })
    @ResponseBody
    public Map < String, Object > getTodo(String messageId, String webappName){
    	VTodo todo = todoMobileService.getTodo(this.getUserProfile().getPersonId(), messageId, webappName);
    	return this.getTodoMap(todo);
    }
    
    private Map < String, Object > getTodoMap(VTodo todo){
    	if (todo == null)return null;
    	
    	Map < String, Object > map = new HashMap < String, Object >();
    	
    	map.put("messageId", todo.getId());
    	map.put("procId", todo.getProcId());
    	map.put("messageType", todo.getMsgType());
    	map.put("messageTitle", CommonUtil.trim(todo.getMsgTitle()));
    	map.put("postponed", todo.isPostponed());
    	map.put("waitingForExtraExam", todo.isWaitingForExtraExam());
    	map.put("webappName", todo.getWebappName());
    	map.put("webPageUrl", todo.getWebPageUrl());
    	map.put("taskInstanceId", todo.getTaskInstanceId());
    	if(todo.getHasReceived()){
    		map.put("receivedIcon", "");
        } else {
        	map.put("receivedIcon", "redBell");
        }
    	map.put("webNameAndId", todo.getWebNameAndId());
    	return map;
    }

}
