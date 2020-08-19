package com.supporter.prj.linkworks.oa.history_exam_record.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.linkworks.oa.history_exam_record.entity.VCneecExam;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Entity
 * @Description: CNEEC_V_SWF_EXAM.
 * @author T
 * @date 2017-09-30 10:27:57
 * @version V1.0   
 *
 */
@Repository
public class VCneecExamDao extends MainDaoSupport < VCneecExam, Long > {
	/**
	 * 分页查询.
	 * @param jqGrid
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public List < VCneecExam > findPage(JqGrid jqGrid, long procId) {
		if (procId <= 0)return null;
		
		String hql = "from " + VCneecExam.class.getName() + " where wfProcId = :procId";
		Map < String, Object > params = new HashMap < String, Object >();
		params.put("procId", procId);
		String orderBy = CommonUtil.rTrim(jqGrid.getOrder());
		if (orderBy.length() == 0){
			orderBy = " order by examDate";
		}
		hql += orderBy;
		return this.retrievePage(jqGrid, hql, params).getRows();
	}
	
	/**
	 * 获取流程实例历史审批意见.
	 * @param tableName
	 * @param idFieldName
	 * @param idFieldValue
	 * @param orderByTimeAsc
	 * @return
	 */
	public List < VCneecExam > getHisExamList(String tableName, String idFieldName, int idFieldValue, boolean orderByTimeAsc) {
		 String ls_AscOrDesc = "DESC";
	     if (orderByTimeAsc) ls_AscOrDesc = "ASC";
	        
		String hql = "from " + VCneecExam.class.getName() + " where tableName = ? and idFieldName=? and idFieldValue=?"
        		+ " order by examDate " + ls_AscOrDesc;
		Object[] params = {tableName,idFieldName,idFieldValue};
		return this.find(hql, params);
	}
	
	/**
	 * 根据业务对象获取旧系统中的procId.
	 * @param entity
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public long getProcIdByRecord(IBusiEntity entity){
		if (entity == null)return -1;
		try {
			Integer keyValues = Integer.valueOf(entity.getKeyValues());
			Map < String, Object > params = new HashMap < String, Object >();
			params.put("tableName", entity.getEntityName());
			params.put("idFieldName", entity.getKeyProps());
			params.put("idFieldValue", keyValues);
			
			String hql = "select wfProcId from " + VCneecExam.class.getName()
				+ " where tableName=:tableName and idFieldName=:idFieldName and idFieldValue=:idFieldValue";
			List < Integer > procIds = this.retrieve(hql, params);
			if (procIds == null || procIds.size() == 0)return -1;
			return procIds.get(0);
		} catch (java.lang.NumberFormatException e){
			return -1;
		}
	}
}

