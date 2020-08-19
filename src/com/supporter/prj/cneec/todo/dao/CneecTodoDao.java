package com.supporter.prj.cneec.todo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jasypt.commons.CommonUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.eip.todo.entity.VTodo;
import com.supporter.prj.eip.todo.util.IDataEntityMeta;
import com.supporter.prj.eip.todo.util.TableSetUtil;
import com.supporter.prj.eip.todo.util.TodoLogType;
import com.supporter.prj.eip.todo.util.TodoQueryParams;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.util.CommonUtil;


/**   
 * VTodo的Dao类.
 *
 */
@Repository
public class CneecTodoDao extends MainDaoSupport < VTodo, String > {

	/**
	 * 构造SQL的where语句，并将参数值设置到params中.
	 * @param personId
	 * @param queryParams
	 * @param params
	 * @param urlFilters
	 * @return
	 */
	private String constructSQLWhere(String personId, TodoQueryParams queryParams, Map < String, Object > params) {
		if (params == null) throw new RuntimeException("params is null.");
		
		StringBuffer sqlWhere = new StringBuffer();
		
		sqlWhere.append(VTodo.PERSON_ID).append(" = :").append(VTodo.PERSON_ID);
		params.put(VTodo.PERSON_ID, CommonUtil.trim(personId));
		
		sqlWhere.append(" and ").append(VTodo.WAITING_FOR_EXTRA_EXAM).append(" <> 'T'");
		
		if (queryParams != null){
			String mstTitle = queryParams.getParamStr(TodoQueryParams.MSG_TITLE);
			if(!CommonUtils.isEmpty(mstTitle)) {
	            sqlWhere.append(" and ").append(VTodo.MSG_TITLE).append(" like :").append(VTodo.MSG_TITLE);
	            params.put(VTodo.MSG_TITLE, "%" + mstTitle + "%");
	        }
		}
        
        return sqlWhere.toString();
	}
	
	/**
	 * 获取待办分页列表.
	 * @param listPage
	 * @param personId
	 * @param queryParams
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List < VTodo > getTodosPage(ListPage < ? extends ITodo > listPage, String personId,
			TodoQueryParams queryParams){
		Map < String, Object > params = new HashMap();
		
		String where = this.constructSQLWhere(personId, queryParams, params);
		
        String orderBy = VTodo.CREATED_DATE + " desc";
        
        String tableSetNo = TableSetUtil.getTableSetNoBySplitVal(personId);
        IDataEntityMeta metaData = getSimpleMetaData(tableSetNo);
        
        List < Map < String, Object > > valueMaps = TableSetDaoUtil.find(metaData, params, where, orderBy, listPage.getPageNo(), listPage.getPageSize(), getHibernateTemplate());
        List < VTodo > todos = this.getVTodos(tableSetNo, valueMaps);
        
        int count = this.getTotalCount(personId, queryParams);
        listPage.setRowCount(count);
        
        return todos;
	}
	
	/**
	 * 获取记录总个数.
	 * @param personId
	 * @param queryParams
	 * @param urlFilters
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getTotalCount(String personId, TodoQueryParams queryParams){
		Map < String, Object > params = new HashMap();
		
		String where = this.constructSQLWhere(personId, queryParams, params);
		
        String orderBy = "";
        
        String tableSetNo = TableSetUtil.getTableSetNoBySplitVal(personId);
        IDataEntityMeta metaData = getSimpleMetaData(tableSetNo);
        
        int count = TableSetDaoUtil.findCount(metaData, params, where, orderBy, getHibernateTemplate());
        
        return count;
	}
	
	private IDataEntityMeta getSimpleMetaData(String tableSetNo) {
		String tblSetNo = CommonUtil.trim(tableSetNo);
		if (tblSetNo.length() == 0) {
			EIPService.getLogService(TodoLogType.DEBUG).error("tableSetNo不能为空白.");
			throw new RuntimeException("tableSetNo不能为空白.");
		}
		
		//注：创建完整的rec记录
		IDataEntityMeta metaData = new VTodo(tblSetNo).getSimpleMetaData();
		return metaData;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List < VTodo > getVTodos(String tableSetNo, List < Map < String, Object > > valueMaps) {
		if (CommonUtil.trim(tableSetNo).length() == 0) {
			EIPService.getLogService().error("tableSetNo is empty.");
			return null;
		}
		if (valueMaps == null) {
			EIPService.getLogService().error("valueMaps is null.");
			return null;
		}
		
		List < VTodo > todos = new ArrayList();
		
		for (Map < String, Object > valueMap : valueMaps) {
			VTodo todo = new VTodo(tableSetNo);
			todo.setValueMap(valueMap);
			
			todos.add(todo);
		}
		
		return todos;
	}
	
	/**
	 * 根据ID和应用名在待办集合中获取待办.
	 * @param personId
	 * @param messageId
	 * @param webappName
	 * @return
	 */
	public VTodo getTodo(String personId, String messageId, String webappName){
		Map < String, Object > params = new HashMap();
		
		String where = VTodo.ID + "=:" + VTodo.ID + " and " + VTodo.WEBAPP_NAME + "=:" + VTodo.WEBAPP_NAME;
		params.put(VTodo.ID, messageId);
		params.put(VTodo.WEBAPP_NAME, webappName);
		
        String orderBy = "";
        
        String tableSetNo = TableSetUtil.getTableSetNoBySplitVal(personId);
        IDataEntityMeta metaData = getSimpleMetaData(tableSetNo);
        
        List < Map < String, Object > > valueMaps = TableSetDaoUtil.find(metaData, params, where, orderBy, 1, 0, getHibernateTemplate());
        List < VTodo > todos = this.getVTodos(tableSetNo, valueMaps);
        if (todos == null || todos.size() == 0)return null;
        
        return todos.get(0);
    }
	
}
