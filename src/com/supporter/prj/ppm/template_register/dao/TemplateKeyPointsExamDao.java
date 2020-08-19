package com.supporter.prj.ppm.template_register.dao;



import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.template_register.entity.TemplateKeyPointsExam;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class TemplateKeyPointsExamDao extends MainDaoSupport < TemplateKeyPointsExam, String > {
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param contractIds 模块ids
	 * @return
	 */
	public List<TemplateKeyPointsExam> findPage(UserProfile user, JqGrid jqGrid, String empId, String templateId, String procId) {
		Object[] params = new Object[]{false};
		StringBuffer hql = new StringBuffer("from "+TemplateKeyPointsExam.class.getName()+" where 1=1 ");
		String authHql = "";
			//查询
			//if(StringUtils.isNotBlank(empId)){
			//	jqGrid.addHqlFilter("empId = ? ", empId);
			//}
			 //主表过滤
			jqGrid.addHqlFilter(" templateId = ? ", templateId);
			//通过树获取子列表，包含本身
			if(StringUtils.isNotBlank(procId)){
				jqGrid.addHqlFilter(" procId = ? ", procId);
			}

			jqGrid.addHqlFilter( " examResult = 'F' ");
	        //授权
	       // authHql = EIPService.getAuthorityService().getHqlFilter(user, TemplateRegister.MODULE_ID, AuthConstant.AUTH_OPER_NAME_PAGESHOW);
			
	        //jqGrid.addHqlFilter(authHql);
			
			jqGrid.addSortPropertyAsc("createdDate");
		//日志
		EIPService.getLogService("TEMPLATEREGISTER").info("hql:" + hql.toString() + "\t params:" + params+"\t authHql:"+authHql);
		return this.retrievePage(jqGrid);
	}
	
	
	
}
