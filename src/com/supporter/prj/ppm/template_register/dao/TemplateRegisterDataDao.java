package com.supporter.prj.ppm.template_register.dao;



import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.dept.util.Params;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
//import com.supporter.prj.linkworks.oa.report.constant.AuthConstant;
import com.supporter.prj.ppm.template_register.entity.TemplateRegister;
import com.supporter.prj.ppm.template_register.entity.TemplateRegisterData;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class TemplateRegisterDataDao extends MainDaoSupport < TemplateRegisterData, String > {
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param contractIds 模块ids
	 * @return
	 */
	public List<TemplateRegisterData> findPage(UserProfile user, JqGrid jqGrid, String templateId, TemplateRegisterData templateRegisterDetail) {
		Object[] params = new Object[]{false};
		StringBuffer hql = new StringBuffer("from "+TemplateRegisterData.class.getName()+" where 1=1 ");
		String authHql = "";
		if(templateRegisterDetail != null){
			//查询
			String keyValue = templateRegisterDetail.getKeyName();
			if(StringUtils.isNotBlank(keyValue)){
				jqGrid.addHqlFilter(
						"textDisplay like ? ", 
						"%" + keyValue + "%");
				hql.append(" and templateName like ? ");
				params = ArrayUtils.add(params, "%" + keyValue + "%");
			}
			 //主表过滤
			jqGrid.addHqlFilter(" templateId = ? ", templateId);
	        //授权
	        authHql = EIPService.getAuthorityService().getHqlFilter(user, TemplateRegister.MODULE_ID, "PAGESHOW");
			
	        jqGrid.addHqlFilter(authHql);
			
			jqGrid.addSortPropertyDesc("displayOrder");
		}
		//日志
		EIPService.getLogService("TEMPLATEREGISTER").info("hql:" + hql.toString() + "\t params:" + params+"\t authHql:"+authHql);
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 获取数据生成树
	 * @param paramMap
	 * @return
	 */
	public List<TemplateRegisterData> getAll(Map<String, Object> paramMap){
		String hql = "from " + TemplateRegisterData.class.getName() + " where directoryStructure < 10 ";
		Params sysParamUtil = new Params(paramMap);
		String isActive = CommonUtil.trim(sysParamUtil.getPara("isActive"));
		if(isActive.equals("false")){
			hql += " and isActive = 'T'";
		}
		 //主表过滤
		hql += " and templateId = '"+ CommonUtil.trim(sysParamUtil.getPara("templateId")) +"'";
		hql += " order by displayOrder ";
		System.out.println("hql="+hql);
		return this.find(hql);
	}
	
	/**
	 * 获取数据预览
	 * @param paramMap
	 * @return
	 */
	public List<TemplateRegisterData> getDetailList(String templateId){
		String hql = "from " + TemplateRegisterData.class.getName() + " where isActive = 'T' ";
		 //主表过滤
		hql += " and templateId = '"+ CommonUtil.trim(templateId) +"'";
		hql += " order by displayOrder ";
		return this.find(hql);
	}
}
