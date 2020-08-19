package com.supporter.prj.cneec.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.todo.dao.CneecTodoDao;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.todo.entity.VTodo;
import com.supporter.prj.eip.todo.util.TodoQueryParams;

/**
 * 待办.
 * @author linda
 */
@Service
public class CneecTodoService {
	@Autowired
    private CneecTodoDao todoDao;
    
    /**
     * 在DB中进行过滤后读出数据(用于手机分页显示,耗DB性能).
     * @param jqGrid
     * @param user
     * @param queryParams
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List < VTodo  > getTodosPage(JqGrid jqGrid, String personId, TodoQueryParams queryParams){
		List < VTodo > todos = todoDao.getTodosPage(jqGrid, personId, queryParams);
		return todos;
    }
    /**
	 * 获取记录总个数.
	 * @param personId
	 * @param queryParams
	 * @param urlFilters
	 * @return
	 */
	public int getTotalCount(String personId, TodoQueryParams queryParams){
		return todoDao.getTotalCount(personId, queryParams);
	}
    
    public VTodo getTodo(String personId, String messageId, String webappName){
    	return todoDao.getTodo(personId, messageId, webappName);
    }

}