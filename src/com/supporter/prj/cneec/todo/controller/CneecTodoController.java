package com.supporter.prj.cneec.todo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.todo.service.CneecTodoService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.todo.entity.VTodo;
import com.supporter.prj.eip.todo.util.TodoQueryParams;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.exception.ExceptionCode;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.linkworks.oa.news_exam.service.NewsExamRecService;
import com.supporter.prj.linkworks.oa.news_exam.service.NewsExamRecService.WfRole;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**
 * 首页待办controller.
 * @author liug
 *
 */
@Controller
@RequestMapping({"/cneec/todo"})
public class CneecTodoController extends AbstractController {
    private static final long serialVersionUID = 1L;

    @Autowired
    private CneecTodoService todoService;
    
    /**
     * 获取待办翻页列表.
     * @param jqGridReq
     * @param personId 承担人ID
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping({ "/getTodosPage" })
    @ResponseBody
    public JqGrid getTodosPage(JqGridReq jqGridReq, String personId) throws Exception {
        JqGrid jqGrid = jqGridReq.initJqGrid(this.getRequest());
        
        boolean powered = false;
        personId = CommonUtil.trim(personId);
        String currentPersonId = CommonUtil.trim(this.getUserProfile().getPersonId());
        if (personId.equals(currentPersonId)){
        	powered = true;
        } else {
        	//非本人访问待办
        	String empNameKeyWord = "";
        	// 是否总裁秘书
        	List < Person > zcmishu = EIPService.getRoleService().getPersonFromRole("ZONGCAIMISHU", empNameKeyWord);
        	List < Person > vCEStodo = EIPService.getRoleService().getPersonFromRole("VIEWCEOTODO", empNameKeyWord);//查看总裁待办
        	zcmishu.addAll(vCEStodo);
        	if (zcmishu != null && zcmishu.size() > 0){
        		for (int i = 0; i < zcmishu.size(); i++){
        			Person emp = zcmishu.get(i);
        			if (currentPersonId.equals(CommonUtil.trim(emp.getPersonId()))){
        				powered = true;
        				break;
        			}
        		}
        	}
        	//是否其他授权人员
        	if (!powered){
        		//TODO
        	}
        }
        
        if (!powered){
        	throw new RuntimeException("没有授权");
        }
        
        
        TodoQueryParams queryParams = new TodoQueryParams();
        String msgTitle = CommonUtil.trim(this.getRequestPara("msgTitle"));
        if (msgTitle.length() > 0){
        	queryParams.setParam(TodoQueryParams.MSG_TITLE, msgTitle);
        }
		
        List < Map < String, Object > > list = new ArrayList();
        
        List < VTodo > todos = todoService.getTodosPage(jqGrid, personId, queryParams);
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
     * 得到待办对象.
     * @param personId
     * @param messageId
     * @param webappName
     * @return
     */
    @RequestMapping({ "/getTodo" })
    @ResponseBody
    public Map < String, Object > getTodo(String personId, String messageId, String webappName){
    	VTodo todo = todoService.getTodo(personId, messageId, webappName);
    	return this.getTodoMap(todo);
    }
    
    /**
     * 获取人员姓名.
     * @param personId
     * @return
     */
    @RequestMapping({ "/getEmpName" })
    @ResponseBody
    public String getEmpName(String personId){
    	Person person = EIPService.getEmpService().getEmp(personId);
    	if (person == null)return "";
    	
    	return CommonUtil.trim(person.getName());
    }
    
    private Map < String, Object > getTodoMap(VTodo todo){
    	if (todo == null)return null;
    	
    	Map < String, Object > map = new HashMap < String, Object >();
    	
    	map.put("messageId", todo.getId());
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
    	map.put("createdByName", CommonUtil.trim(todo.getCreatedByName()));
    	map.put("createdDate", todo.getCreatedDate());
    	
    	return map;
    }

}
