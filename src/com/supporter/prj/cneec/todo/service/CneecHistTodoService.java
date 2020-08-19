package com.supporter.prj.cneec.todo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.todo.dao.CneecHistTodoDao;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.eip.todo.entity.VHistTodo;

/**
 * 已办视图服务.
 * @author liug
 */
@Service
public class CneecHistTodoService {
	
	 
	@Autowired
	private CneecHistTodoDao cneecHistTodoDao;	
	
	/**
	 * 获取已办列表.
	 * @param listPage
	 * @param user
	 * @return
	 */
	public List < VHistTodo > getHistTodos(ListPage < VHistTodo > listPage, Map < String, Object> params){
		List < VHistTodo > histTodos = cneecHistTodoDao.getHisTodos(listPage, params);
		return histTodos;
	}
	
}
