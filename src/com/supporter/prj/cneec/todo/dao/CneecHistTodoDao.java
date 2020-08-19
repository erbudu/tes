package com.supporter.prj.cneec.todo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.StringType;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.todo.entity.CneecHistTodo;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.eip.todo.entity.VHistTodo;
import com.supporter.prj.eip.todo.util.TableSetUtil;
import com.supporter.util.CommonUtil;

/**
 * 已办事宜DAO.
 * @author ArnoldH
 *
 */
@Repository
public class CneecHistTodoDao extends MainDaoSupport < CneecHistTodo, String > {
	
	private final static String[] COL_NAMES = new String[]{
		"todo_id","msg_title","proc_id","msg_type","person_id","consigner_name","assigner_name","webapp_name",
		"module_id","node_name","created_date","complete_date"
	};
	
	/**
	 * 根据关键字查询.
	 * @param keyword
	 * @return
	 */
	public List < CneecHistTodo > findByKeyword(String keyword) {
		String hql = "from " + CneecHistTodo.class.getName() + " where msgTitle like ?";
		return this.find(hql, "%" + CommonUtil.trim(keyword) + "%");
	}
	
	/**
	 * 获取已办数据列表.
	 * @param listPage
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List < VHistTodo > getHisTodos(ListPage < VHistTodo > listPage, Map < String, Object > params){
		List < VHistTodo > histTodos = new ArrayList < VHistTodo >();
		
		String sql = CneecHistTodoDao.getSql(params,false);
		//EIP6.0本地系统应用的SQL
		String eip6Sql = CneecHistTodoDao.getSql(params,true);
		
		//处理完成时间
		String dateFrom1 = "";
		String dateFrom2 = "";
		String dateTo1 = "";
		String dateTo2 = "";
		if (params.containsKey("completeDateFrom")){
			Date date = CommonUtil.parseDate((String)params.get("completeDateFrom"));
			if (date != null){
				dateFrom1 = "'" + CommonUtil.formatDate(date, "yyyy-MM-dd") + "'";
				dateFrom2 = "to_date(" + dateFrom1 + ",'yyyy-MM-dd')";
			}
		}
		if (params.containsKey("completeDateTo")){
			Date date = CommonUtil.parseDate((String)params.get("completeDateTo"));
			if (date != null){
				dateTo1 = "'" + CommonUtil.formatDate(date, "yyyy-MM-dd 23:59:59") + "'";
				dateTo2 = "to_date(" + dateTo1 + ",'yyyy-MM-dd HH24:mi:ss')";
			}
		}
		
		//协同办公系统(OA)
		String newSql = eip6Sql.replace("{{tableName}}", "EIP_V_TODO_HIST");
		if (dateFrom1.length() > 0){
			newSql = newSql.replace("{{completeDateFrom}}", dateFrom2);
		}
		if (dateTo1.length() > 0){
			newSql = newSql.replace("{{completeDateTo}}", dateTo2);
		}
		List < VHistTodo > histTodosOld = this.getOldHistList(newSql);
		if (histTodosOld != null && histTodosOld.size() > 0)histTodos.addAll(histTodosOld);
		
		//老OA
		newSql = sql.replace("{{tableName}}", "EIP_V_TODO_HIST_OA_OLD");
		if (dateFrom1.length() > 0){
			newSql = newSql.replace("{{completeDateFrom}}", dateFrom1);
		}
		if (dateTo1.length() > 0){
			newSql = newSql.replace("{{completeDateTo}}", dateTo1);
		}
		histTodosOld = this.getOldHistList(newSql);
		if (histTodosOld != null && histTodosOld.size() > 0)histTodos.addAll(histTodosOld);
		
		//PC
		newSql = sql.replace("{{tableName}}", "EIP_V_TODO_HIST_PC_OLD");
		if (dateFrom1.length() > 0){
			newSql = newSql.replace("{{completeDateFrom}}", dateFrom1);
		}
		if (dateTo1.length() > 0){
			newSql = newSql.replace("{{completeDateTo}}", dateTo1);
		}
		histTodosOld = this.getOldHistList(newSql);
		if (histTodosOld != null && histTodosOld.size() > 0)histTodos.addAll(histTodosOld);
		
		//CM
		newSql = sql.replace("{{tableName}}", "EIP_V_TODO_HIST_CM_OLD");
		if (dateFrom1.length() > 0){
			newSql = newSql.replace("{{completeDateFrom}}", dateFrom2);
		}
		if (dateTo1.length() > 0){
			newSql = newSql.replace("{{completeDateTo}}", dateTo2);
		}
		histTodosOld = this.getOldHistList(newSql);
		if (histTodosOld != null && histTodosOld.size() > 0)histTodos.addAll(histTodosOld);
		
		//PL
		newSql = sql.replace("{{tableName}}", "EIP_V_TODO_HIST_PL_OLD");
		if (dateFrom1.length() > 0){
			newSql = newSql.replace("{{completeDateFrom}}", dateFrom2);
		}
		if (dateTo1.length() > 0){
			newSql = newSql.replace("{{completeDateTo}}", dateTo2);
		}
		histTodosOld = this.getOldHistList(newSql);
		if (histTodosOld != null && histTodosOld.size() > 0)histTodos.addAll(histTodosOld);
		
		//AM
		newSql = sql.replace("{{tableName}}", "EIP_V_TODO_HIST_AM_OLD");
		if (dateFrom1.length() > 0){
			newSql = newSql.replace("{{completeDateFrom}}", dateFrom2);
		}
		if (dateTo1.length() > 0){
			newSql = newSql.replace("{{completeDateTo}}", dateTo2);
		}
		histTodosOld = this.getOldHistList(newSql);
		if (histTodosOld != null && histTodosOld.size() > 0)histTodos.addAll(histTodosOld);
		
		//BUDGET
		newSql = sql.replace("{{tableName}}", "EIP_V_TODO_HIST_BUDGET_OLD");
		if (dateFrom1.length() > 0){
			newSql = newSql.replace("{{completeDateFrom}}", dateFrom2);
		}
		if (dateTo1.length() > 0){
			newSql = newSql.replace("{{completeDateTo}}", dateTo2);
		}
		histTodosOld = this.getOldHistList(newSql);
		if (histTodosOld != null && histTodosOld.size() > 0)histTodos.addAll(histTodosOld);

        listPage.setPageSize(0);
        listPage.setRows(histTodos);
        
        return listPage.getRows();
	}
	
	/**
	 * 获取老系统中的已办数据列表.
	 * @param tableName
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List < VHistTodo > getOldHistList(final String sql){
		HibernateTemplate template = getHibernateTemplate();
		List results = (List) template.executeWithNativeSession(new HibernateCallback() {
			public List doInHibernate(Session session) {
				SQLQuery query = session.createSQLQuery(sql);
				//query.setFirstResult(1);
				//query.setMaxResults(50);

				//query.addScalar("recCount", new IntegerType());
				int colSize = COL_NAMES.length;
				StringType stringType = new StringType();
				//TimestampType timestampType = new TimestampType();
				for (int i = 0; i < colSize; i++){
					if (i == 1){
						query.addScalar(COL_NAMES[i], stringType);
					} else {
						query.addScalar(COL_NAMES[i]);
					}
				}
				
				return query.list();
			}
		});
		
		if (results == null || results.size() == 0)return null;
		
		List < VHistTodo > hisTodos = new ArrayList < VHistTodo >();
		
		int size = results.size();
		for (int i = 0; i < size; i++){
			Object[] objArray = (Object[])(results.get(i));
			VHistTodo histTodo = this.getVHistTodo(objArray);
			hisTodos.add(histTodo);
		}
		
		return hisTodos;
		
	}
	
	/**
	 * 根据检索的结果集合对象获取已办对象.
	 * @param objArray
	 * @return
	 */
	private VHistTodo getVHistTodo(Object[] objArray){
		VHistTodo histTodo = new VHistTodo();
		histTodo.setId(objArray[0].toString());
		if (objArray[1] != null)histTodo.setMsgTitle(CommonUtil.trim(objArray[1].toString()));
		if (objArray[2] != null)histTodo.setProcId(objArray[2].toString());
		histTodo.setMsgType(objArray[3].toString());
		histTodo.setPersonId(objArray[4].toString());
		
		if (objArray[5] != null) histTodo.setConsignerName(objArray[5].toString());
		
		if (objArray[6] != null) histTodo.setAssignerName(objArray[6].toString());
		
		histTodo.setWebappName(objArray[7].toString());
		histTodo.setModuleId(objArray[8].toString());
		
		if (objArray[9] != null)histTodo.setNodeName(CommonUtil.trim(objArray[9].toString()));
		
		if (objArray[10] != null)histTodo.setCreatedDate(CommonUtil.parseDate(objArray[10].toString()));
		if (objArray[11] != null)histTodo.setCompleteDate(CommonUtil.parseDate(objArray[11].toString()));
		
		return histTodo;
	}
	
	/**
	 * 构建sql语句.
	 * @param tableName
	 * @param params
	 * @return
	 */
	private static String getSql(Map < String, Object > params,boolean localSystem){
		StringBuffer sql = new StringBuffer("select ");
		int colSize = COL_NAMES.length;
		for (int i = 0; i < colSize; i++){
			if (i != 0)sql.append(",");
			sql.append(COL_NAMES[i]);
		}
		sql.append(" from ").append("{{tableName}}");
		if (params == null || params.size() == 0)return sql.toString();
		
		boolean hasFilters = false;
		//应用
		if (params.containsKey("moduleId")){
			String moduleId = CommonUtil.trim((String)params.get("moduleId"));
			if (moduleId.length() > 0){
				if (hasFilters){
					sql.append(" and ");
				} else {
					sql.append(" where ");
				}
				if (moduleId.equals(TableSetUtil.NOT_MODULE_ID)) {
	        		sql.append("moduleId is null");
				} else {
					sql.append("module_id=" + moduleId);
				}
				
				hasFilters = true;
			}
		}
		if(localSystem){
			//承担人
			if (params.containsKey("Eip6PersonId")){
				String personId = (String)params.get("Eip6PersonId");
				if (hasFilters){
					sql.append(" and ");
				} else {
					sql.append(" where ");
				}
				sql.append(" (person_id='").append(personId).append("'");
				sql.append(" or ");
				sql.append("consigner_id='").append(personId).append("') ");
				hasFilters = true;
			}
		}else{
			//承担人
			if (params.containsKey("OldpersonId")){
				String personId = (String)params.get("OldpersonId");
				if (hasFilters){
					sql.append(" and ");
				} else {
					sql.append(" where ");
				}
				sql.append(" (person_id='").append(personId).append("'");
				sql.append(" or ");
				sql.append("consigner_id='").append(personId).append("') ");
				hasFilters = true;
			}
		}
		
		
		//处理完成时间
		if (params.containsKey("completeDateFrom")){
			Date date = CommonUtil.parseDate((String)params.get("completeDateFrom"));
			if (date != null){
				if (hasFilters){
					sql.append(" and ");
				} else {
					sql.append(" where ");
				}
				sql.append("complete_date>={{completeDateFrom}}");
				hasFilters = true;
			}
		}
		if (params.containsKey("completeDateTo")){
			Date date = CommonUtil.parseDate((String)params.get("completeDateTo"));
			if (date != null){
				if (hasFilters){
					sql.append(" and ");
				} else {
					sql.append(" where ");
				}
				sql.append("complete_date<={{completeDateTo}}");
				hasFilters = true;
			}
		}
		
		//已办标题
		if (params.containsKey("msgTitle")){
			String msgTitle = CommonUtil.trim((String)params.get("msgTitle"));
			if (msgTitle.length() > 0){
				if (hasFilters){
					sql.append(" and ");
				} else {
					sql.append(" where ");
				}
				sql.append("msg_title like '%").append(msgTitle).append("%'");
				hasFilters = true;
			}
		}
		
		
		sql.append(" order by created_date desc");
		return sql.toString();
	}
	
}
