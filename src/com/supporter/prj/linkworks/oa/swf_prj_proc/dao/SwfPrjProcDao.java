package com.supporter.prj.linkworks.oa.swf_prj_proc.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.linkworks.oa.swf_prj_proc.entity.SwfPrjProc;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

@Repository
public class SwfPrjProcDao extends MainDaoSupport<SwfPrjProc, String> {
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param swfPrjProc
	 * @return
	 */
	public List<SwfPrjProc> findPage(JqGrid jqGrid, SwfPrjProc swfPrjProc){
		if (swfPrjProc != null){
			String keyword = swfPrjProc.getKeyword();
			if (StringUtils.isNotBlank(keyword)){
				jqGrid.addHqlFilter(" createdBy like ? or prjName like ? " +
						" or prjNo like ? or businessInnerName like ? " +
						" or businessName like ? or procInnerName like ? " +
						" or procName like ? ", "%"+keyword+"%", "%"+keyword+"%", "%"+keyword+"%",
						"%"+keyword+"%", "%"+keyword+"%", "%"+keyword+"%", "%"+keyword+"%");
			}
			//按创建时间倒叙排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		return this.retrievePage(jqGrid);
	}
}
