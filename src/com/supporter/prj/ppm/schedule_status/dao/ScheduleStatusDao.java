package com.supporter.prj.ppm.schedule_status.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.ppm.schedule_status.entity.ScheduleStatus;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: ScheduleStatusDao
 * @Description: DAO类
 * @author: liyinfeng
 * @date: 2018-07-13
 * @version: V1.0
 */
@Repository
public class ScheduleStatusDao extends MainDaoSupport<ScheduleStatus, String> {

	/**
	 * 分页查询
	 */
	public List<ScheduleStatus> findPage(JqGrid jqGrid, ScheduleStatus scheduleStatus) {
		if (scheduleStatus != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = scheduleStatus.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" textDisplay like ? ",
									"%" + keyword + "%" );
			}
			/* 以下是更多条件中选择项 */
			// 应用过滤
			if (StringUtils.isNotBlank(scheduleStatus.getModuleId())) {
				jqGrid.addHqlFilter(" moduleId = ? ", scheduleStatus.getModuleId());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		//jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

	@SuppressWarnings("unchecked")
	public ScheduleStatus getScheduleStatusByModuleId(String moduleId) {
		String hql = "from ScheduleStatus where moduleId = ? ";
		List<ScheduleStatus> list = this.retrieve(hql, moduleId);
		if(list != null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
}
