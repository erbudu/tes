package com.supporter.prj.ppm.tool_tips.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.ppm.tool_tips.entity.ToolTips;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: ToolTipsDao
 * @Description: DAO类
 * @author: liyinfeng
 * @date: 2018-07-13
 * @version: V1.0
 */
@Repository
public class ToolTipsDao extends MainDaoSupport<ToolTips, String> {

	/**
	 * 分页查询
	 */
	public List<ToolTips> findPage(JqGrid jqGrid, ToolTips toolTips) {
		if (toolTips != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = toolTips.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" labelName like ? or labelNo like ?  "
						+ "or domainObjectName like ?  or moduleName like ? "
						+ "or moduleId like ?  or domainObjectId like ? ",
									"%" + keyword + "%", "%" + keyword + "%", 
									"%" + keyword + "%", "%" + keyword + "%", 
									"%" + keyword + "%", "%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 状态过滤
			//System.out.println("toolTips.getStatus()"+toolTips.getStatus());
			//if (toolTips.getStatus() != -1) {
			//	jqGrid.addHqlFilter(" status = ? ", toolTips.getStatus());
			//}
			if (StringUtils.isNotBlank(toolTips.getModuleId())) {
				jqGrid.addHqlFilter(" moduleId = ? ", toolTips.getModuleId());
			}
			if (StringUtils.isNotBlank(toolTips.getInputType())) {
				jqGrid.addHqlFilter(" inputType = ? ", toolTips.getInputType());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		//jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

	/**
	 * 通过应用编码和标签编码
	 * @param moduleName
	 * @param busiType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ToolTips getToolTipsByModuleNameAndBusiType(String moduleId, String labelNo) {
		String hql = "from ToolTips where moduleId = ? and labelNo = ?";
		List<ToolTips> list = this.retrieve(hql, moduleId, labelNo);
		if(list != null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
//	@SuppressWarnings("unchecked")
//	public RecordFilingManager getRecordFilingManager(String prepareId) {
//		String hql = "from RecordFilingManager where inforId = ?";
//		List<RecordFilingManager> list = this.retrieve(hql, prepareId);
//		return list.get(0);
//	}
	
}
