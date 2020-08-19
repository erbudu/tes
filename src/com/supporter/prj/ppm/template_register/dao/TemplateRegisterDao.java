package com.supporter.prj.ppm.template_register.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
//import com.supporter.prj.linkworks.oa.report.constant.AuthConstant;
import com.supporter.prj.ppm.template_register.entity.TemplateRegister;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class TemplateRegisterDao extends MainDaoSupport < TemplateRegister, String > {
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param contractIds 模块ids
	 * @return
	 */
	public List<TemplateRegister> findPage(UserProfile user, JqGrid jqGrid, TemplateRegister templateRegister) {
		Object[] params = new Object[]{false};
		StringBuffer hql = new StringBuffer("from "+TemplateRegister.class.getName()+" where 1=1 ");
		String authHql = "";
		if(templateRegister != null){
			//查询
			String keyValue = templateRegister.getTemplateName();
			if(StringUtils.isNotBlank(keyValue)){
				jqGrid.addHqlFilter(
						"templateName like ? or templateNo like ? ", 
						"%" + keyValue + "%","%" + keyValue + "%");
				hql.append(" and templateName like ? or templateNo like ? ");
				params = ArrayUtils.add(params, "%" + keyValue + "%");
				params = ArrayUtils.add(params, "%" + keyValue + "%");
			}
			 //状态过滤
	        /*if(templateRegister.getStatus() > -1){
	        	jqGrid.addHqlFilter("status = ?" ,templateRegister.getStatus());
	        	hql.append(" and status = ? ");
				params = ArrayUtils.add(params, templateRegister.getStatus());
	        }*/
	        //授权
	        authHql = EIPService.getAuthorityService().getHqlFilter(user, TemplateRegister.MODULE_ID, "PAGESHOW");
			
	        jqGrid.addHqlFilter(authHql);
			
			jqGrid.addSortPropertyDesc("templateId");
		}
		//日志
		EIPService.getLogService("TEMPLATEREGISTER").info("hql:" + hql.toString() + "\t params:" + params+"\t authHql:"+authHql);
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 获取所有的报告记录.
	 * @param user
	 * @return
	 */
	public List<TemplateRegister> getTemplateRegisterList() {
		StringBuffer hql = new StringBuffer("from "+TemplateRegister.class.getName()+" where 1=1 ");
		List<TemplateRegister> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
	
	/**
	 * 判断编码是否重复
	 * 
	 * @param templateId
	 * @param templateNo
	 * @return
	 */
	public boolean checkTemplateNoIsValid(String templateId, String templateNo) {
		String hql = "from " + TemplateRegister.class.getName() + " where templateId != ? and templateNo = ?";
		List<TemplateRegister> retList = this.find(hql, templateId, templateNo);
		if (retList != null && !retList.isEmpty()){
			return false;
		}
		return true;
	}
	
}
