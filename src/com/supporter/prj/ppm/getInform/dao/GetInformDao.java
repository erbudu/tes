package com.supporter.prj.ppm.getInform.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.eip.todo.dao.HistTodoDao;
import com.supporter.prj.eip.todo.entity.HistTodo;
@Repository
public class GetInformDao extends HistTodoDao{

	public List<HistTodo> getByProcId(String procId)
  {
    String hql = "from " + HistTodo.class.getName() + " where procId = ?";
    return find(hql, new Object[] { procId });
  }


}
