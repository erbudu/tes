package com.supporter.prj.ppm.template_register.dao;



import java.util.ArrayList;
import java.util.HashMap;
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
import com.supporter.prj.ppm.template_register.entity.TemplateRegisterDetail;
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
public class TemplateRegisterDetailDao extends MainDaoSupport < TemplateRegisterDetail, String > {
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param contractIds 模块ids
	 * @return
	 */
	public List<TemplateRegisterDetail> findPage(UserProfile user, JqGrid jqGrid, String templateId, String parentId, TemplateRegisterDetail templateRegisterDetail) {
		Object[] params = new Object[]{false};
		StringBuffer hql = new StringBuffer("from "+TemplateRegisterDetail.class.getName()+" where 1=1 ");
		String authHql = "";
		if(templateRegisterDetail != null){
			//查询
			String keyValue = templateRegisterDetail.getTextDisplay();
			if(StringUtils.isNotBlank(keyValue)){
				jqGrid.addHqlFilter(
						"textDisplay like ? ", 
						"%" + keyValue + "%");
				hql.append(" and templateName like ? ");
				params = ArrayUtils.add(params, "%" + keyValue + "%");
			}
			 //主表过滤
			jqGrid.addHqlFilter(" templateId = ? ", templateId);
			//通过树获取子列表，包含本身
			if(StringUtils.isNotBlank(parentId)){
				jqGrid.addHqlFilter(" parentId = ? or detailId = ?", parentId, parentId);
			}
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
	 * 显示大纲级别树
	 * @param paramMap
	 * @return
	 */
	public List<TemplateRegisterDetail> getAll(Map<String, Object> paramMap){
		String hql = "from " + TemplateRegisterDetail.class.getName() + " where directoryStructure < 10 ";
		Params sysParamUtil = new Params(paramMap);
		String isActive = CommonUtil.trim(sysParamUtil.getPara("isActive"));
		if(isActive.equals("false")){
			hql += " and isActive = 'T'";
		}
		 //主表过滤
		hql += " and templateId = '"+ CommonUtil.trim(sysParamUtil.getPara("templateId")) +"'";
		hql += " order by displayOrder ";
		//System.out.println("hql="+hql);
		return this.find(hql);
	}
	
	/**
	 * 获取数据生成树
	 * 显示评审要点
	 * @param paramMap
	 * @return
	 */
	public List<TemplateRegisterDetail> getAllByReviewKeyPoint(Map<String, Object> paramMap){
		String hql = "from " + TemplateRegisterDetail.class.getName() + " where 1=1 ";
		Params sysParamUtil = new Params(paramMap);
		//获取启用状态
		hql += " and isActive = 'T'";
		//模板过滤
		hql += " and templateId = '"+ CommonUtil.trim(sysParamUtil.getPara("templateId")) +"'";
		//评审要点过滤
		hql += " and isReviewKeyPoint = 'T'";
		
		hql += " order by displayOrder ";
		System.out.println("hql="+hql);
		return this.find(hql);
	}
	
	/**
	 * 获取数据预览
	 * @param paramMap
	 * @return
	 */
	public List<TemplateRegisterDetail> getDetailList(String templateId, String paragraphNo){
		String hql = "from " + TemplateRegisterDetail.class.getName() + " where isActive = 'T' ";
		 //主表过滤
		hql += " and templateId = '"+ CommonUtil.trim(templateId) +"'";
		//过滤段落
		if(StringUtils.isNotBlank(paragraphNo)){
			hql += " and paragraphNo = '"+ CommonUtil.trim(paragraphNo) +"'";
		}
		hql += " order by displayOrder ";
		return this.find(hql);
	}
	
	/**
	 * 获取评审要点
	 * @param paramMap
	 * @return
	 */
	public List<TemplateRegisterDetail> getDetailListByReviewKeyPoint(String templateId){
		String hql = "from " + TemplateRegisterDetail.class.getName() + " where isActive = 'T' ";
		 //主表过滤
		hql += " and templateId = '"+ CommonUtil.trim(templateId) +"'";
		//过滤评审要点
		hql += " and isReviewKeyPoint = 'T'";
		hql += " order by displayOrder ";
		return this.find(hql);
	}
	
	/**
	 * 获取评审要点的段落
	 * @param paramMap
	 * @return
	 */
	public List<TemplateRegisterDetail> getDetailListByParagraphNo(String templateId){
		List<TemplateRegisterDetail> details = this.getDetailListByReviewKeyPoint(templateId);
		Map<String, List<TemplateRegisterDetail>> map = new HashMap<String, List<TemplateRegisterDetail>>();
		if(details != null && !details.isEmpty()){
			for(TemplateRegisterDetail detail : details){
				List<TemplateRegisterDetail> list = map.get(detail.getParagraphNo());
				if(list == null || list.isEmpty()){
					list = new ArrayList<TemplateRegisterDetail>();
				}
				list.add(detail);
				map.put(detail.getParagraphNo(), list);
			}
		}
		//按段落分组，取每组最小顺序值的
		List<TemplateRegisterDetail> detaillist = new ArrayList<TemplateRegisterDetail>();
		String sql = "select detail_id, text_display, Paragraph_No from ppm_Template_Register_Detail d, " +
				"(select n.paragraph_No nn, min(n.display_Order)  ss from ppm_Template_Register_Detail n " +
				"where n.is_Active = 'T' and n.paragraph_No is not null and n.template_Id = ? group by n.paragraph_No) aa " +
				"where aa.nn = d.paragraph_No and aa.ss = d.display_Order and d.template_id = ? order by d.display_Order";
		List<Object[]> list = this.sqlQuery(sql, null, templateId, templateId);
		if(list != null && !list.isEmpty()){
			for(Object[] objs : list){
				TemplateRegisterDetail detail = new TemplateRegisterDetail();
				detail.setDetailId((String)objs[0]);
				detail.setTextDisplay((String)objs[1]);
				detail.setReviewKeyPoint( map.get((String)objs[2]));
				detaillist.add(detail);
			}
		}
		return detaillist;
	}
	
	/**
	 * 获取自动赋值数据
	 * @param paramMap
	 * @return
	 */
	public List<TemplateRegisterDetail> getDetailListByAuto(String templateId, String paragraphNo){
		String hql = "from " + TemplateRegisterDetail.class.getName() + " where isActive = 'T' ";
		 //主表过滤
		hql += " and templateId = '"+ CommonUtil.trim(templateId) +"'";
		//过滤段落
		hql += " and paragraphNo = '"+ CommonUtil.trim(paragraphNo) +"'";
		hql += " and (contentType = 'AUTO' or contentType = 'DATAREAD') ";
		hql += " order by displayOrder ";
		return this.find(hql);
	}
	
	/**
	 * 判断信息项编码是否重复
	 * 
	 * @param detailId
	 * @param contentNo
	 * @return
	 */
	public boolean checkContentNoIsValid(String detailId, String contentNo) {
		if(StringUtils.isNotBlank(contentNo)){
			String hql = "from " + TemplateRegisterDetail.class.getName() + " where detailId != ? and contentNo = ? " +
					"and (contentNo is not null and contentNo != '')";
			List<TemplateRegisterDetail> retList = this.find(hql, detailId, contentNo);
			if (retList != null && !retList.isEmpty()){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断信息项编码是否重复
	 * 
	 * @param detailId
	 * @param paragraphNo
	 * @param templateId
	 * @return
	 */
	public boolean checkParagraphNoIsValid(String detailId, String paragraphNo, String templateId) {
		String hql = "from " + TemplateRegisterDetail.class.getName() + " where detailId != ? and paragraphNo = ? and templateId = ?";
		List<TemplateRegisterDetail> retList = this.find(hql, detailId, paragraphNo, templateId);
		if (retList != null && !retList.isEmpty()){
			return false;
		}
		return true;
	}
	
}
