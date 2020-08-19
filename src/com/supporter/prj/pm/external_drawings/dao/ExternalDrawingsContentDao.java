package com.supporter.prj.pm.external_drawings.dao;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.external_drawings.entity.ExternalDrawingsContent;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class ExternalDrawingsContentDao extends MainDaoSupport < ExternalDrawingsContent, String >{
	
	public List<ExternalDrawingsContent> findContentPage(UserProfile user, JqGrid jqGrid,
			ExternalDrawingsContent content,String externalId, String isFor) {
		if(content != null){						
			if(StringUtils.isNotBlank(externalId)){
				jqGrid.addHqlFilter("externalId = '"+externalId +"'");
			}
			if(StringUtils.isNotBlank(isFor)) {
				if(isFor.equals("0")) {//是否初次外审：否
					jqGrid.addHqlFilter("isFor = 0");
				}if(isFor.equals("1")) {//是否初次外审：是
					jqGrid.addHqlFilter("isFor = 1");
				}
			}
		}
		return this.retrievePage(jqGrid);
	}
	
	public List<ExternalDrawingsContent> findSelectContent(UserProfile user, JqGrid jqGrid,ExternalDrawingsContent content) {
		if(content != null){			
			List<ExternalDrawingsContent> retList = new ArrayList<ExternalDrawingsContent>();
			StringBuffer sb = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			sb.append(" from ").append(ExternalDrawingsContent.class.getName()).append(" z where 1=1 ");
			//编号
			String drawingNo = content.getDrawingNo();
			if(StringUtils.isNotBlank(drawingNo)){
				sb.append(" and  drawingNo like '%" + drawingNo + "%'");
			}
				
			//只获取已经生效的记录
			sb.append(" and exists (select externalDrawings.externalId from "
					+ "ExternalDrawings externalDrawings where z.externalId = externalDrawings.externalId "
					+ " and externalDrawings.status='2')");
			//是否选择是选择审核不通过的数据
			String message = content.getSuggest();
			if(StringUtils.isNotBlank(message)&&message.equals("外审不通过")){
				sb.append(" and (  drawingType = '"+ExternalDrawingsContent.TYPE_THREE+"' or drawingType = '"
						+ExternalDrawingsContent.TYPE_FOUR+"' ) ");

				sb.append(" and not exists( select contentId from HospitaReviewRec r where z.id = r.contentId  )");

			}
			this.retrievePage(jqGrid, sb.toString(), params.toArray());		
			return jqGrid.getRows();
		}
		return null;
	}
	
	public List<ExternalDrawingsContent> getContentList(String id){
		String hql = "from ExternalDrawingsContent where externalId = '" + id + "'";
		List<ExternalDrawingsContent> list = find(hql);
		if(list != null && list.size() > 0){
			return list;
		}else{
			return null;	
		} 	
	}
	
	public List<ExternalDrawingsContent> getContentListByDrawingId(String drawingId){
		String hql = "from ExternalDrawingsContent where drawingId = '" + drawingId + "'";
		List<ExternalDrawingsContent> list = find(hql);
		if(list != null && list.size() > 0){
			return list;
		}else{
			return null;	
		} 	
	}
	
	//获取所有已保存的图纸信息
	public List<ExternalDrawingsContent> getAllContentList(){
		String hql = "from ExternalDrawingsContent";
		List<ExternalDrawingsContent> list = find(hql);
		if(list != null && list.size() > 0){
			return list;
		}else{
			return null;	
		} 	
	}
	
}
