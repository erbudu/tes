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
public class TodoMobileDao extends MainDaoSupport < VTodo, String > {

	/**
	 * 构造SQL的where语句，并将参数值设置到params中.
	 * @param personId
	 * @param queryParams
	 * @param params
	 * @param urlFilters
	 * @return
	 */
	private String constructSQLWhere(String personId, TodoQueryParams queryParams, Map < String, Object > params,
			List < String > urlFilters) {
		if (params == null) throw new RuntimeException("params is null.");
		
		StringBuffer sqlWhere = new StringBuffer();
		
		sqlWhere.append(VTodo.PERSON_ID).append(" = :").append(VTodo.PERSON_ID);
		params.put(VTodo.PERSON_ID, CommonUtil.trim(personId));
		
		sqlWhere.append(" and ").append(VTodo.POSTPONED).append("<>'T' and ").append(VTodo.WAITING_FOR_EXTRA_EXAM).append(" <> 'T'");
		
		sqlWhere.append(" and (").append(VTodo.WEBAPP_NAME).append("=:webappName1 or ")
			.append(VTodo.WEBAPP_NAME).append("=:webappName2 or ").append(VTodo.WEBAPP_NAME).append("=:webappName3 or ")
			.append(VTodo.WEBAPP_NAME).append("=:webappName4 or ").append(VTodo.WEBAPP_NAME).append("=:webappName5 or ")
			.append(VTodo.WEBAPP_NAME).append("=:webappName6)");
		params.put("webappName1", "BM");
		params.put("webappName2", "CNEEC_OA");
		params.put("webappName3", "CNEEC_PC");
		params.put("webappName4", "CM");
		params.put("webappName5", "CNEEC_PL");
		params.put("webappName6", "AM");
		
		String mstTitle = "";
		if (queryParams != null)mstTitle = queryParams.getParamStr(TodoQueryParams.MSG_TITLE);
        if(!CommonUtils.isEmpty(mstTitle)) {
            sqlWhere.append(" and ").append(VTodo.MSG_TITLE).append(" like :").append(VTodo.MSG_TITLE);
            params.put(VTodo.MSG_TITLE, "%" + mstTitle + "%");
        }
        
        if (urlFilters != null && urlFilters.size() > 0){
        	sqlWhere.append(" and (");
        	int size = urlFilters.size();
        	for (int i = 0; i < size; i++){
        		if (i > 0)sqlWhere.append(" or ");
        		String paramName = "url" + i;
        		sqlWhere.append(VTodo.WEB_PAGE_URL).append(" like :").append(paramName);
        		params.put(paramName, "%" + urlFilters.get(i) + "%");
        	}
        	sqlWhere.append(")");
        }
        
        return sqlWhere.toString();
	}
	
	/**
	 * 从DB中读出数据后进行过滤(用于手机首页记录显示).
	 * @param listPage
	 * @param personId
	 * @param queryParams
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List < VTodo > getMobileTodos(ListPage < ? extends ITodo > listPage, String personId, TodoQueryParams queryParams){
		Map < String, Object > params = new HashMap();
		
		String where = this.constructSQLWhere(personId, queryParams, params, null);
		
        String orderBy = VTodo.CREATED_DATE + " desc";
        
        String tableSetNo = TableSetUtil.getTableSetNoBySplitVal(personId);
        IDataEntityMeta metaData = getSimpleMetaData(tableSetNo);
        
        List < Map < String, Object > > valueMaps = TableSetDaoUtil.find(metaData, params, where, orderBy, listPage.getPageNo(), listPage.getPageSize(), getHibernateTemplate());
        List < VTodo > todos = this.getVTodos(tableSetNo, valueMaps);
        
        return todos;
	}
	
	/**
	 * 在DB中进行过滤后读出数据(用于手机分页显示,耗DB性能).
	 * @param listPage
	 * @param personId
	 * @param queryParams
	 * @param urlFilters
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List < VTodo > getMobileTodosPage(ListPage < ? extends ITodo > listPage, String personId,
			TodoQueryParams queryParams, List < String > urlFilters){
		Map < String, Object > params = new HashMap();
		
		String where = this.constructSQLWhere(personId, queryParams, params, urlFilters);
		
        String orderBy = VTodo.CREATED_DATE + " desc";
        
        String tableSetNo = TableSetUtil.getTableSetNoBySplitVal(personId);
        IDataEntityMeta metaData = getSimpleMetaData(tableSetNo);
        
        List < Map < String, Object > > valueMaps = TableSetDaoUtil.find(metaData, params, where, orderBy, listPage.getPageNo(), listPage.getPageSize(), getHibernateTemplate());
        List < VTodo > todos = this.getVTodos(tableSetNo, valueMaps);
        
        int count = this.getTotalCount(personId, queryParams, urlFilters);
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
	public int getTotalCount(String personId, TodoQueryParams queryParams, List < String > urlFilters){
		Map < String, Object > params = new HashMap();
		
		String where = this.constructSQLWhere(personId, queryParams, params, urlFilters);
		
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
