package com.supporter.prj.cneec.todo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.supporter.prj.core.orm.hibernate.util.SQLUtil; 
import com.supporter.prj.eip.todo.util.IDataEntity;
import com.supporter.prj.eip.todo.util.IDataEntityMeta;
import com.supporter.prj.eip.todo.util.ITableColumn; 
import com.supporter.prj.eip.todo.util.TodoLogType;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * 提供在dao包中共用的方法，不对dao包之外的类开发.
 * @author ArnoldH
 *
 */
final class TableSetDaoUtil {
	
	/**
	 * 构造方法.
	 */
	private TableSetDaoUtil() {}
	
	/**
	 * 获取Insert语句.
	 * @param entityModel
	 * @return
	 */
	public static String getSQLForInsert(IDataEntityMeta entityModel) {
		if (entityModel == null) {
			EIPService.getLogService(TodoLogType.DEBUG).error("entityModel is null.");
			return null;
		}
		
		List < ITableColumn > cols = entityModel.getColumns();
		
		String fields = "";
		String variables = "";
		for (ITableColumn col : cols) {
			if (fields.length() > 0) {
				fields += ",";
				variables += ",";
			}
			
			fields += col.getName();
			variables += ":" + col.getName();
		}
		
		String sql = "insert into " + entityModel.getTableName() + "(" + fields + ") values (" + variables + ")";
		return sql;
	}
	
	/**
	 * 获取Update语句.
	 * @param entityModel
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String getSQLForUpdate(IDataEntityMeta entityModel) {
		if (entityModel == null) {
			EIPService.getLogService(TodoLogType.DEBUG).error("entityModel is null.");
			return null;
		}
		
		List < ITableColumn > pkCols = entityModel.getPkColumns();
		Set < String > pkColNames = new HashSet();
		String where = ""; 
		for (ITableColumn col : pkCols) {
			if (where.length() > 0) {
				where += " and "; 
			}
			
			where += col.getName() + "=:" + col.getName();
			pkColNames.add(col.getName());
		}
		
		List < ITableColumn > cols = entityModel.getColumns();
		
		String sqlSet = ""; 
		for (ITableColumn col : cols) {
			if (pkColNames.contains(col.getName())) continue;  //如果是关键字，那么忽略
			
			if (sqlSet.length() > 0) {
				sqlSet += " , "; 
			}
			
			sqlSet += col.getName() + "=:" + col.getName();
		}
		
		String sql = "update " + entityModel.getTableName() + " set " + sqlSet + " where " + where;
		return sql;
	}
	
	/**
	 * 获取用于创建预算事实表的SQL语句.
	 * @param budgetYear
	 * @param metaData
	 * @param dialect
	 * @return
	 */
	public static String getSQLForCreatingTable(int budgetYear, IDataEntityMeta metaData, String dialect) {
		if (metaData == null) {
			EIPService.getLogService(TodoLogType.DEBUG).error("metaData is null");
			throw new RuntimeException("metaData is null");
		}
		
		String tableName = metaData.getTableName().toLowerCase(); 
		
		List < ITableColumn > pkCols = metaData.getPkColumns();
		List < ITableColumn > allCols = metaData.getColumns();

		//字段定义部分
		String sqlFields = "";
		for (ITableColumn col : allCols) {
			if (sqlFields.length() > 0) sqlFields += ",";
		
			String dataType = col.getDataType();
			if (dialect.indexOf("oracle") > 0) {
				dataType = dataType.replaceAll("varchar", "varchar2");
			}
			if (dataType.indexOf("numeric") >= 0) {
				dataType += " default 0 ";
			}
						
			sqlFields += col.getName() + " " + dataType + " ";
			if (col.isNotNull()) sqlFields += " not null";
		}
		
		//主键字段部分
		String sqlPK = "";
		for (ITableColumn pkCol : pkCols) {
			if (sqlPK.length() > 0) sqlPK += ",";
			sqlPK += pkCol.getName();
		}
		
		String sql = "create table " + tableName + "("
				+ sqlFields + ", "
				+ "primary key (" + sqlPK +  ")"	
			+ ")";
		
		EIPService.getLogService().debug("sql:" + sql);
		return sql;
	}
	
	/**
	 * 获取delete语句.
	 * @param entityModel
	 * @return
	 */
	public static String getSQLForDelete(IDataEntityMeta entityModel) {
		if (entityModel == null) {
			EIPService.getLogService(TodoLogType.DEBUG).error("entityModel is null.");
			return null;
		}
		
		List < ITableColumn > pkCols = entityModel.getPkColumns();
		
		String where = ""; 
		for (ITableColumn col : pkCols) {
			if (where.length() > 0) {
				where += " and "; 
			}
			
			where += col.getName() + "=:" + col.getName();
		}
		
		String sql = "delete from " + entityModel.getTableName() + " where " + where;
		return sql;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void save(IDataEntity entity, HibernateTemplate hibTemplate) {
		if (entity == null) return;
		
		List < IDataEntity > entities = new ArrayList();
		entities.add(entity);
		
		save(entities, hibTemplate);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void save(List < ? extends IDataEntity > entities, HibernateTemplate hibTemplate) {
		if (entities == null || entities.size() == 0) return;
		
		//将valueMap中的null处理好
		adjustNullValues(entities);
		
		IDataEntityMeta metaData = entities.get(0).getMetaData();
		List < ITableColumn > pkCols = metaData.getPkColumns();
		
		List < Map < String, Object > > paramMaps = new ArrayList();
		for (IDataEntity entity : entities) {
			Map < String, Object > paramMap = entity.getValueMap(); 
			
			//检查一下主键，如果是空白的，并且是字符串类型的，那么赋予一个uuid
			for (ITableColumn pkCol : pkCols) {
				Object val = paramMap.get(pkCol.getName());
				if (val == null && pkCol.isString()) {
					val = UUIDHex.getUUIDHex();
					paramMap.put(pkCol.getName(), val); //设置到关键字中
				}
			}
			
			paramMaps.add(paramMap);
		}
		
		String sql = getSQLForInsert(metaData);
		
		SQLUtil.batchUpdate(sql, paramMaps, hibTemplate);
	}
	
	/**
	 * 将valueMap中的null值处理好.
	 * @param entities
	 */
	private static void adjustNullValues(List < ? extends IDataEntity > entities) {
		if (entities == null || entities.size() == 0) return;
		
		IDataEntityMeta metaData = entities.get(0).getMetaData(); 
		List < ITableColumn > cols = metaData.getColumns();
		 
		for (IDataEntity entity : entities) {
			Map < String, Object > paramMap = entity.getValueMap(); 
			
			//检查一下个字段值，如果是数字的，并且是null，那么设置为0
			for (ITableColumn col : cols) {
				Object val = paramMap.get(col.getName());
				if (val == null && col.isNumber()) {
					String dataType = CommonUtil.trim(col.getDataType()).toLowerCase();
					if (dataType.startsWith("int") 
							|| (dataType.startsWith("num") && dataType.endsWith(",0)"))
							|| (dataType.startsWith("decimal") && dataType.endsWith(",0)"))) {
						//整型
						paramMap.put(col.getName(), 0);
					} else {
						//Double类型
						paramMap.put(col.getName(), 0D); //设置到关键字中
					}
				}
				
				if (val == null && col.getDataType().equalsIgnoreCase("char(1)")) {
					paramMap.put(col.getName(), "F"); //注：如果是char(1)，那么一般地映射为boolean，默认地设置为'F'
				}
			}  
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void update(List < ? extends IDataEntity > entities, HibernateTemplate hibTemplate) {
		if (entities == null || entities.size() == 0) return;
		
		//将valueMap中的null处理好
		adjustNullValues(entities);
		
		IDataEntityMeta metaData = entities.get(0).getMetaData();
		List < ITableColumn > pkCols = metaData.getPkColumns();
		
		List < Map < String, Object > > paramMaps = new ArrayList();
		for (IDataEntity entity : entities) {
			Map < String, Object > paramMap = entity.getValueMap(); 
			
			//检查一下主键，如果是空白的，抛异常
			for (ITableColumn pkCol : pkCols) {
				Object val = paramMap.get(pkCol.getName());
				if (val == null) {
					EIPService.getLogService(TodoLogType.DEBUG).error("主键不能为空");
					throw new RuntimeException("主键不能为空");
				}
			}
									
			paramMaps.add(paramMap);			
		}
		
		String sql = getSQLForUpdate(metaData);
		SQLUtil.batchUpdate(sql, paramMaps, hibTemplate);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void delete(IDataEntity entity, HibernateTemplate hibTemplate) {
		if (entity == null) return;
		
		List < IDataEntity > entities = new ArrayList();
		entities.add(entity);
		
		delete(entities, hibTemplate);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void delete(List < ? extends IDataEntity > entities, HibernateTemplate hibTemplate) {
		if (entities == null) {
			EIPService.getLogService(TodoLogType.DEBUG).error("entities is null");
			return;
		}
		if (entities.size() == 0) return;
		
		IDataEntityMeta metaData = entities.get(0).getMetaData();
		List < ITableColumn > pkCols = metaData.getPkColumns();
		
		List < Map < String, Object > > paramMaps = new ArrayList();
		for (IDataEntity entity : entities) {
			Map < String, Object > paramMap = new HashMap();
			for (ITableColumn pkCol : pkCols) {
				String colName = pkCol.getName();
				paramMap.put(colName, entity.getValueMap().get(colName));
			}
			
			paramMaps.add(paramMap);
		}

		String sql = getSQLForDelete(metaData); 
		SQLUtil.batchUpdate(sql, paramMaps, hibTemplate);
	}
	
	/**
	 * 将大写的字母更换为"_小写"，即为对应的字段名，例如:extDmsnNo3-> ext_dmsn_no_3.
	 * @param propName
	 * @return
	 */
	public static String translatePropToFieldName(String propName) {
		String fieldName = "";
		char[] chars = propName.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i]; 
			String s = String.valueOf(c);
			if (CommonUtil.isNumber(s)) {
				if (i > 0) {
					char lastC = chars[ i - 1];
					if (!CommonUtil.isNumber(String.valueOf(lastC))) {
						fieldName += "_" + s;
					} else {
						fieldName += s;
					}
				}
			} else if (!Character.isLowerCase(c)) {
					fieldName += "_" + s.toLowerCase();			
			} else {
				fieldName += s;
			}
		}
		
		return fieldName;
	}

	
	/**
	 * 获取记录数.
	 * @param tableName
	 * @param template
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int getTableRecCount(final String tableName, HibernateTemplate template) {
		List results = (List) template.executeWithNativeSession(new HibernateCallback() {
			public List doInHibernate(Session session) {
				String sql = "select count(*) as recCount from " + tableName;
				SQLQuery query = session.createSQLQuery(sql);
				
				query.addScalar("recCount", new IntegerType());
				
				return query.list();
			}
		}); 

		if (results == null || results.size() == 0) {
			return 0;
		} else {
			int recCount = (Integer) results.get(0);
			return recCount;
		}
	}
	
	
	/**
	 * 根据源数据和一些过滤条件，构造出Select语句，并返回相应的值Map.
	 * @param metaData
	 * @param paramMap
	 * @param hibTemplate
	 * @return map实例，key为字段名，值为字段值
	 */
	public static Map < String, Object > getValueMap(IDataEntityMeta metaData, final Map < String, Object > paramMap, HibernateTemplate hibTemplate) {
		if (metaData == null) {
			EIPService.getLogService(TodoLogType.DEBUG).error("metaData is null.");
			return null;
		}
		if (paramMap == null) {
			EIPService.getLogService(TodoLogType.DEBUG).error("paramMap is null.");
			return null;
		}
		
		List < Map < String, Object > > valueMaps = find(metaData, paramMap, true, hibTemplate);
		if (valueMaps == null || valueMaps.size() == 0) {
			return null;
		} else {
			return valueMaps.get(0);
		}
	}
	
	/**
	 * 根据源数据和一些过滤条件，构造出Select语句，并返回相应的值Map.
	 * @param metaData
	 * @param paramMap
	 * @param hibTemplate
	 * @return
	 */
	public static List < Map < String, Object > > retrieveValueMaps(final String sql, 
			final Map < String, Object > paramMap, final Map < String, Type > scalarMap,
			HibernateTemplate hibTemplate) {
		int pageSize = 0;
		int pageIndex = 0;
		return retrieveValueMaps(sql, paramMap, scalarMap, pageIndex, pageSize, hibTemplate);
	}
	
	/**
	 * 根据源数据和一些过滤条件，构造出Select语句，并返回相应的值Map.
	 * @param metaData
	 * @param paramMap
	 * @param pageIndex 第几页，从1开始
	 * @param pageSize 每页多少条记录
	 * @param hibTemplate
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List < Map < String, Object > > retrieveValueMaps(final String sql, 
			final Map < String, Object > paramMap, final Map < String, Type > scalarMap,
			final int pageIndex, final int pageSize,
			HibernateTemplate hibTemplate) {
		//获取colNames
		String sqlOfLowerCase = CommonUtil.trim(sql).toLowerCase();
		if (sqlOfLowerCase.length() == 0) {
			EIPService.getLogService(TodoLogType.DEBUG).error("sql is empty.");
			return new ArrayList();
		}
		
		final String SELECT = "select", FROM = " from";
		int pos = sqlOfLowerCase.indexOf(SELECT);
		String selectedCols = sqlOfLowerCase.substring(pos + SELECT.length()); //去掉"select"
		selectedCols = CommonUtil.trim(selectedCols.substring(0, selectedCols.indexOf(FROM))); //去掉" from"之后的部分
		if (selectedCols.length() == 0) {
			EIPService.getLogService(TodoLogType.DEBUG).error("failed to get selected fields");
			return new ArrayList();
		}
		
		final Map < String, Type > scalarMapForSelectedCols = new LinkedHashMap();
		List < String > selectedColNames = new ArrayList();
		String[] colNameArray = selectedCols.split(",");
		for (String colName : colNameArray) {
			colName = CommonUtil.trim(colName);
			if (colName.length() == 0) continue;
			
			selectedColNames.add(colName);
			
			//确认scalarMap中包含该列
			if (scalarMap != null && !scalarMap.containsKey(colName)) {
				scalarMapForSelectedCols.put(colName, new StringType()); //默认使用STRING
			} else {
				scalarMapForSelectedCols.put(colName, scalarMap.get(colName));
			}
		}
				
		List < ? > results = (List) hibTemplate.executeWithNativeSession(new HibernateCallback() {
			public List doInHibernate(Session session) {
				SQLQuery query = session.createSQLQuery(sql); 
				if (paramMap != null) query.setProperties(paramMap);
				
				//显式指定返回数据的类型
				if (scalarMap != null) {
					Set < String > keys = scalarMapForSelectedCols.keySet();
					for (String key : keys) {
						query.addScalar(key, scalarMapForSelectedCols.get(key));
					}
				}
				
				if (pageSize > 0) {
					query.setMaxResults(pageSize);
					if (pageIndex <= 0) {
						query.setFirstResult(0);
					} else {
						query.setFirstResult((pageIndex - 1) * pageSize);
					}
				}
				
				return query.list();
			}
		}); 
		
		if (results == null) results = new ArrayList();
		 
		List < Map < String, Object > > valueMaps = new ArrayList();
		for (Object values : results) {
			if (values == null) continue;
			
			Map < String, Object > valueMap = new LinkedHashMap();
			
			if (selectedColNames.size() == 1) {
				//只选择1单列
				String colName = selectedColNames.get(0); 
				valueMap.put(colName, values);
			} else {
				//多列
				Object[] objArray = (Object[]) values;
				for (int i = 0; i < selectedColNames.size(); i++) {
					String colName = selectedColNames.get(i);
					Object val = objArray[i];
					valueMap.put(colName, val);
				}
			} 
			
			valueMaps.add(valueMap);
		}
		 
		return valueMaps;
	}
	
	
	/**
	 * 根据源数据和一些过滤条件，构造出Select语句，并返回相应的条数.
	 * @param metaData
	 * @param paramMap
	 * @param hibTemplate
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int retrieveCount(final String sql, final Map < String, Object > paramMap, HibernateTemplate hibTemplate) {
				
		List < ? > results = (List) hibTemplate.executeWithNativeSession(new HibernateCallback() {
			public List doInHibernate(Session session) {
				SQLQuery query = session.createSQLQuery(sql); 
				if (paramMap != null) query.setProperties(paramMap);
				
				query.addScalar("recCount", new IntegerType());
				return query.list();
			}
		}); 
		
		if (results == null || results.size() == 0) {
			return 0;
		} else {
			int recCount = (Integer) results.get(0);
			return recCount;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map < String, Type > getScalarMap(IDataEntityMeta metaData) {
		if (metaData == null) throw new RuntimeException("metaData is null");
		
		List < ITableColumn > cols = metaData.getColumns();
		
		//构造ScalarMap
		Map < String, Type > scalarMap = new LinkedHashMap();
		for (ITableColumn col : cols) {
			Type type = null;
			String dataType = col.getDataType().toLowerCase();
			if (dataType.startsWith("numeric") && dataType.endsWith(",0)")) {
				type = new LongType();
			} else if (dataType.startsWith("decimal") && dataType.endsWith(",0")) {
				type = new LongType();
			} else if (dataType.startsWith("numeric") || dataType.startsWith("decimal")) {
				type = new DoubleType();
			} else if (dataType.startsWith("char") || dataType.startsWith("varchar")) {
				type = new StringType();
			} else if (dataType.startsWith("int")) {
				type = new IntegerType();
			} else if (dataType.startsWith("datetime") || dataType.startsWith("timestamp")) {
				type = new TimestampType();
			} else if (dataType.startsWith("date")) {
				type = new DateType();
			} else {
				EIPService.getLogService(TodoLogType.DEBUG).error("尚未支持的类型:" + dataType);
			} 
			scalarMap.put(col.getName(), type);
		}
		
		return scalarMap;
	}
	
	public static String getSelectedFields(IDataEntityMeta metaData) {
		if (metaData == null) throw new RuntimeException("metaData is null");
		
		List < ITableColumn > cols = metaData.getColumns();
		
		String selectedFields = ""; 
		for (ITableColumn col : cols) {
			if (selectedFields.length() > 0) selectedFields += ",";
			
			selectedFields += col.getName();
		}
		
		return selectedFields;
	}
	/**
	 * 根据源数据和一些过滤条件，构造出Select语句，并返回相应的值Map.
	 * 示例代码： retrieveValueMaps(metaData, "bgtTbl", "bgtTbl.template_id = :templateId and ....", paramMap, hibTemplate);
	 * @param metaData
	 * @param dataEntityTableAlias 实体类对应的表的别名（注：实体类对应的表可以通过metaData.getTableName()获取) 
	 * @param paramMap
	 * @param hibTemplate
	 * @return
	 */
	public static List < Map < String, Object > > retrieveValueMaps(final IDataEntityMeta metaData, 
			String dataEntityTableAlias, String sqlWhere, final Map < String, Object > paramMap, 
			HibernateTemplate hibTemplate) {
		//构造ScalarMap
		Map < String, Type > scalarMap = getScalarMap(metaData);

		List < ITableColumn > cols = metaData.getColumns();
		
		//构造SQL语句
		//构造selectedFields
		String selectedFields = "";
		for (ITableColumn col : cols) {
			if (selectedFields.length() > 0) {
				selectedFields += ","; 
			} 
			selectedFields += col.getName();
		}
		
		String sql = "select " + selectedFields
				+ " from " + metaData.getTableName() + " " + dataEntityTableAlias;
		if (CommonUtil.trim(sqlWhere).length() > 0) sql += " where " + sqlWhere;

		return retrieveValueMaps(sql, paramMap, scalarMap, hibTemplate);

	}
	
	/**
	 * 根据源数据和一些过滤条件，构造出Select语句，并返回相应的值Map.
	 * 示例代码： retrieveValueMaps(metaData, "template_id = :templateId and ....", paramMap, hibTemplate);
	 * @param metaData 
	 * @param paramMap
	 * @param hibTemplate
	 * @return
	 */
	public static List < Map < String, Object > > retrieveValueMaps(final IDataEntityMeta metaData, String sqlWhere, final Map < String, Object > paramMap, 
			HibernateTemplate hibTemplate) {
		String dataEntityTableAlias = ""; 
		return retrieveValueMaps(metaData, dataEntityTableAlias, sqlWhere, paramMap, hibTemplate);
	}
 	
	/**
	 * 根据指定字段（在paramMap中）的值，查询出指定数据表的valueMap(精确匹配).
	 * @param metaData
	 * @param paramMap
	 * @param hibTemplate
	 * @return map实例，key为字段名，值为字段值
	 */
	public static List < Map < String, Object > > find(IDataEntityMeta metaData, final Map < String, Object > paramMap, HibernateTemplate hibTemplate) {
		boolean exactlyMatching = true;
		return find(metaData, paramMap, exactlyMatching, hibTemplate);
	}
	
	public static List < Map < String, Object > > find(final IDataEntityMeta metaData, final Map < String, Object > paramMap, 
			final boolean exactlyMatching, HibernateTemplate hibTemplate) {
		String orderBy = "";
		return find(metaData, paramMap, exactlyMatching, orderBy, hibTemplate);
	}
	
	public static List < Map < String, Object > > find(final IDataEntityMeta metaData, final Map < String, Object > paramMap, 
			final String orderBy, HibernateTemplate hibTemplate) {
		boolean exactlyMatching = true;
		return find(metaData, paramMap, exactlyMatching, orderBy, hibTemplate);
	}

	/**
	 * 根据指定字段（在paramMap中）的值，查询出指定数据表的valueMap，可指定是否精确匹配.
	 * @param metaData
	 * @param paramMap
	 * @param exactMatching 是否精确匹配
	 * @param hibTemplate
	 * @return map实例，key为字段名，值为字段值
	 */
	public static List < Map < String, Object > > find(final IDataEntityMeta metaData, final Map < String, Object > paramMap, 
			final boolean exactlyMatching, String orderBy, HibernateTemplate hibTemplate) {
		Set < String > selectedFields = null;
		return find(metaData, selectedFields, paramMap, exactlyMatching, orderBy, hibTemplate);
	}
	
	/**
	 * 根据指定字段（在paramMap中）的值，查询出指定数据表的valueMap，可指定是否精确匹配.
	 * @param metaData
	 * @param selectedFieldSet 需要selected的字段集合
	 * @param paramMap
	 * @param hibTemplate
	 * @return map实例，key为字段名，值为字段值
	 */
	public static List < Map < String, Object > > find(final IDataEntityMeta metaData, 
			final Set < String > selectedFieldSet, final Map < String, Object > paramMap, 
			HibernateTemplate hibTemplate) {
		boolean exactlyMatching = true;
		String orderBy = ""; 
		return find(metaData, selectedFieldSet, paramMap, exactlyMatching, orderBy, hibTemplate);
	}
	
	/**
	 * 根据指定字段（在paramMap中）的值，查询出指定数据表的valueMap，可指定是否精确匹配.
	 * @param metaData
	 * @param selectedFieldSet 需要selected的字段集合
	 * @param paramMap
	 * @param hibTemplate
	 * @return map实例，key为字段名，值为字段值
	 */
	public static List < Map < String, Object > > find(final IDataEntityMeta metaData, 
			final Set < String > selectedFieldSet, final Map < String, Object > paramMap, 
			String orderBy, HibernateTemplate hibTemplate) {
		boolean exactlyMatching = true;
		return find(metaData, selectedFieldSet, paramMap, exactlyMatching, orderBy, hibTemplate);
	}
	
	/**
	 * 根据指定字段（在paramMap中）的值，查询出指定数据表的valueMap，可指定是否精确匹配.
	 * @param metaData
	 * @param selectedFieldSet 需要selected的字段集合
	 * @param paramMap
	 * @param exactMatching 是否精确匹配
	 * @param hibTemplate
	 * @return map实例，key为字段名，值为字段值
	 */
	public static List < Map < String, Object > > find(final IDataEntityMeta metaData, 
			final Set < String > selectedFieldSet, final Map < String, Object > paramMap, 
			final boolean exactlyMatching, String orderBy, HibernateTemplate hibTemplate) {
		int pageIndex = 0, pageSize = 0;
		return find(metaData, selectedFieldSet, paramMap, exactlyMatching, orderBy, pageIndex, pageSize, hibTemplate);
	}
	
	/**
	 * 根据指定字段（在paramMap中）的值，查询出指定数据表的valueMap，可指定是否精确匹配.
	 * @param metaData
	 * @param selectedFieldSet 需要selected的字段集合
	 * @param paramMap
	 * @param exactMatching 是否精确匹配
	 * @param pageIndex 页数，从1开始
	 * @param pageSize 每页多少条记录
	 * @param hibTemplate
	 * @return map实例，key为字段名，值为字段值
	 */
	public static List < Map < String, Object > > find(final IDataEntityMeta metaData, 
			final Map < String, Object > paramMap, String orderBy, int pageIndex, int pageSize, HibernateTemplate hibTemplate) {
		Set < String > selectedFieldSet = null;
		boolean exactlyMatching = true;
		return find(metaData,  selectedFieldSet, paramMap, exactlyMatching, orderBy, pageIndex, pageSize, hibTemplate);
	}
	
	/**
	 * 根据指定字段（在paramMap中）的值，查询出指定数据表的valueMap，可指定是否精确匹配.
	 * @param metaData
	 * @param selectedFieldSet 需要selected的字段集合
	 * @param paramMap
	 * @param exactMatching 是否精确匹配
	 * @param pageIndex 页数，从1开始
	 * @param pageSize 每页多少条记录
	 * @param hibTemplate
	 * @return map实例，key为字段名，值为字段值
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List < Map < String, Object > > find(final IDataEntityMeta metaData, 
			final Set < String > selectedFieldSet, final Map < String, Object > paramMap, 
			final boolean exactlyMatching, String orderBy, int pageIndex, int pageSize, HibernateTemplate hibTemplate) {
		if (metaData == null) {
			EIPService.getLogService(TodoLogType.DEBUG).error("metaData is null.");
			return null;
		}
		if (paramMap == null) {
			EIPService.getLogService(TodoLogType.DEBUG).error("paramMap is null.");
			return null;
		}
			
		//构造ScalarMap
		Map < String, Type > scalarMap = getScalarMap(metaData);
		
		//构造SQL语句
		//构造selectedFields
		String selectedFields = "";
		if (selectedFieldSet != null && selectedFieldSet.size() > 0) {
			 for (String field : selectedFieldSet) {
				 field = CommonUtil.trim(field);
				 if (field.length() == 0) continue;
				 if (selectedFields.length() > 0) selectedFields += ",";
				 selectedFields += field;
			 }
		} else {
			selectedFields = getSelectedFields(metaData);
		}
		
		//构造sqlWhere和params
		Map < String, Object > params = new HashMap();
		
		String sqlWhere = "";
		Set < String > paramKeys = paramMap.keySet();
		for (String paramKey : paramKeys) {
			if (sqlWhere.length() > 0) sqlWhere += " and ";
			
			String value = null;
			Object valObj = paramMap.get(paramKey);
			if (valObj != null) {
				if (valObj instanceof String) {
					value = CommonUtil.trim((String) valObj);
				} else if (valObj instanceof Boolean) {
					if ((Boolean) valObj) {
						value = "T";
					} else {
						value = "F";
					}
				} else {
					value = valObj.toString();
				}
			}
			
			if (exactlyMatching) {
				sqlWhere += paramKey + " = :" + paramKey;
				params.put(paramKey, value);
			} else {
				sqlWhere += paramKey + " like :" + paramKey;
				params.put(paramKey, "%" + value + "%");		 
			}
		}
		
		String sql = "select " + selectedFields
				+ " from " + metaData.getTableName()
				+ " where " + sqlWhere;
		if (CommonUtil.trim(orderBy).length() > 0) sql += " order by " + orderBy;
		
		List < Map < String, Object > > valueMaps = retrieveValueMaps(sql, params, scalarMap, pageIndex, pageSize, hibTemplate);
		return valueMaps;
	}
	
	/**
	 * 由调用者自行构造where语句和orderBy，然后执行SQL语句.
	 * @param metaData
	 * @param paramMap
	 * @param exactMatching 是否精确匹配
	 * @param hibTemplate
	 * @return map实例，key为字段名，值为字段值
	 */
	public static List < Map < String, Object > > find(final IDataEntityMeta metaData, final Map < String, Object > paramMap, 
			String where, String orderBy, HibernateTemplate hibTemplate) {
		int pageIndex = 0, pageSize = 0;
		return find(metaData, paramMap, where, orderBy, pageIndex, pageSize, hibTemplate);
	}
	
	/**
	 * 由调用者自行构造where语句和orderBy，然后执行SQL语句.
	 * @param metaData
	 * @param paramMap
	 * @param exactMatching 是否精确匹配
	 * @param hibTemplate
	 * @return map实例，key为字段名，值为字段值
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List < Map < String, Object > > find(final IDataEntityMeta metaData, final Map < String, Object > paramMap, 
			String where, String orderBy, int pageIndex, int pageSize, HibernateTemplate hibTemplate) {
		if (metaData == null) {
			EIPService.getLogService(TodoLogType.DEBUG).error("metaData is null.");
			return null;
		}
					
		//构造ScalarMap
		Map < String, Type > scalarMap = getScalarMap(metaData);
		
		//构造SQL语句
		//构造selectedFields
		String selectedFields = getSelectedFields(metaData); 
		
		//构造sqlWhere和params
		Map < String, Object > params = new HashMap();
		if (paramMap != null) {
			Set < String > paramKeys = paramMap.keySet();
			for (String paramKey : paramKeys) {
				Object valueObj = paramMap.get(paramKey);
				
				String value = "";
				if (valueObj != null) {
					if (valueObj instanceof String) {
						value = CommonUtil.trim((String) valueObj);
						params.put(paramKey, value);
					} else if (valueObj instanceof Boolean) {
						if ((Boolean) valueObj) {
							value = "T";
						} else {
							value = "F";
						}
						params.put(paramKey, value);
					} else if (valueObj instanceof Date) {
						params.put(paramKey, valueObj);
					}  else {
						value = valueObj.toString();
						params.put(paramKey, value);
					}
				}
				
			}	
		}
		
		String sql = "select " + selectedFields	+ " from " + metaData.getTableName();
		
		String sqlWhere = CommonUtil.trim(where);
		if (sqlWhere.length() > 0)	sql += " where " + sqlWhere;
		
		if (CommonUtil.trim(orderBy).length() > 0) sql += " order by " + orderBy;
		
		List < Map < String, Object > > valueMaps = retrieveValueMaps(sql, params, scalarMap, pageIndex, pageSize, hibTemplate);	
		return valueMaps;
	}
	
	/**
	 * 由调用者自行构造where语句和orderBy，然后执行SQL语句获取待办个数.
	 * @param metaData
	 * @param paramMap
	 * @param exactMatching 是否精确匹配
	 * @param hibTemplate
	 * @return map实例，key为字段名，值为字段值
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int findCount(final IDataEntityMeta metaData, final Map < String, Object > paramMap, 
			String where, String orderBy, HibernateTemplate hibTemplate) {
		if (metaData == null) {
			EIPService.getLogService(TodoLogType.DEBUG).error("metaData is null.");
			return 0;
		}
					
		//构造sqlWhere和params
		Map < String, Object > params = new HashMap();
		if (paramMap != null) {
			Set < String > paramKeys = paramMap.keySet();
			for (String paramKey : paramKeys) {
				Object valueObj = paramMap.get(paramKey);
				
				String value = "";
				if (valueObj != null) {
					if (valueObj instanceof String) {
						value = CommonUtil.trim((String) valueObj);
					} else if (valueObj instanceof Boolean) {
						if ((Boolean) valueObj) {
							value = "T";
						} else {
							value = "F";
						}
					} else {
						value = valueObj.toString();
					}
				}
				params.put(paramKey, value);
			}	
		}	
		String sql = "select count(*) as recCount  from " + metaData.getTableName();
		
		String sqlWhere = CommonUtil.trim(where);
		if (sqlWhere.length() > 0)	sql += " where " + sqlWhere;
		
		if (CommonUtil.trim(orderBy).length() > 0) sql += " order by " + orderBy;
		
		int count = retrieveCount(sql, params, hibTemplate);
				
		return count;
	}
}
