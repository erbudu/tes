/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.approving.dao;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.ppm.bid_startup.approving.entity.ReportStartEntity;

/**
 *<p>Title: 启动对外提报持久层</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月2日
 * 
 */
@Repository
public class ReportStartDao extends MainDaoSupport<ReportStartEntity, String>{
	
	/**
	 * <pre>描述：根据项目主键查询启动对外提报表单数据</pre>
	 * <pre>调用：1.新建或编辑页面初始数据调用ReportStartService.initAddOrEditPage</pre>	 
	 * @param prjId 项目主键
	 * @return
	 */
	public ReportStartEntity getEntityByPrjId(String prjId) {
		ReportStartEntity resultEntity = this.findUniqueResult("prjId", prjId);
		return resultEntity;
		
	}

}
