package com.supporter.prj.eip.swf.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.eip.swf.runtime.entity.WfExam;
import com.supporter.prj.eip.swf.runtime.entity.WfProcBusiEntity;

/**
 * 流程审批意见
 * @author Administrator
 *
 */
@Repository(value = "myWfExamDao")
public class MyWfExamDao extends MainDaoSupport< WfExam, String > {
	
	/**
	 * 获取指定节点的最近一次的审批意见
	 * @param procId 流程ID
	 * @param nodeName 流程节点名称
	 * @return WfExam
	 */
	public WfExam getLatestExamByNodeName(String procId, String nodeName) {
		String hql = "from " + WfExam.class.getName()
				+ " where wfProcId=? and wfActName=? order by examDate desc";
		List <WfExam> list = this.find(hql, procId, nodeName);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	/**
	 * 获取指定业务对象的所有流程实例的审批意见
	 * @param entityId 业务对象ID
	 * @param orderByTimeAsc 审批日期是否正排序
	 * @return WfExam
	 */
	public List <WfExam> getExamByEntityId(String entityId, boolean orderByTimeAsc) {
		String hql = "from " + WfExam.class.getName()
				+ " exam where exists (select pe.recordId from " + WfProcBusiEntity.class.getName()
				+ " pe where exam.wfProcId=pe.procId and pe.entityId=?)";
		if (orderByTimeAsc) {
			hql += " order by exam.examDate asc";
		} else {
			hql += " order by exam.examDate desc";
		}
		List <WfExam> list = this.find(hql, entityId);
		return list;
	}
}

