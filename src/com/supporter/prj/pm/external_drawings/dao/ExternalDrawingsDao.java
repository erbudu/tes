package com.supporter.prj.pm.external_drawings.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.drawing_library.entity.DrawingLibraryVersion;
import com.supporter.prj.pm.external_drawings.entity.ExternalDrawings;
import com.supporter.prj.pm.external_drawings.entity.ExternalDrawingsContent;
import com.supporter.util.CommonUtil;

@Repository
public class ExternalDrawingsDao extends MainDaoSupport < ExternalDrawings, String >{

	/**
	 * 分页查询
	 * @param jqGrid
	 * @param map 
	 * @param qualityProblemIds 模块ids
	 * @return
	 */
	public List<ExternalDrawings> findPage(JqGrid jqGrid, ExternalDrawings externalDrawings,UserProfile user, Map<String, Object> map) {
		// //列表显示权限
		// String authHql = EIPService.getAuthorityService().getHqlFilter(user,
		// ExternalDrawings.APP_NAME, AuthConstant.AUTH_OPER_NAME_PAGE);
		// jqGrid.addHqlFilter(authHql);
		if(externalDrawings != null){
			//主题
			String topic = externalDrawings.getTopic();
			if (StringUtils.isNotBlank(topic) ) {
				jqGrid.addHqlFilter("topic like ? or externalNo like ? ", "%" + topic + "%","%" + topic + "%");
			}
			//状态
			Integer status = externalDrawings.getStatus();
			if (status != null) {
				jqGrid.addHqlFilter("status = ? ", status);
			}
			// 只获取某项目下的数据
			String prjId = externalDrawings.getPrjId();
			if (StringUtils.isNotBlank(prjId)) {
				jqGrid.addHqlFilter(" prjId = ? ", prjId);
			} else {// 判断条件值任意，目的是返回一个空表
				jqGrid.addHqlFilter(" 1 != 1");
			}
		}
		
		return this.retrievePage(jqGrid);
	}
	
	// 验证图纸编号的唯一性
	public List<ExternalDrawings> getListByExternalNo(String externalId, String externalNo) {
		String hql = "from " + ExternalDrawings.class.getName() + " where externalId <> ? and externalNo = ? ";
		List<ExternalDrawings> editers = this.find(hql, CommonUtil.trim(externalId), CommonUtil.trim(externalNo));
		return editers;
	}

    public List<ExternalDrawings> findPageToWidget(JqGrid jqGrid, ExternalDrawings externalDrawings, UserProfile user, Map<String, Object> requestParameters, String keyword) {
		if(externalDrawings != null){
			//状态
			jqGrid.addHqlFilter("status = 2 ");
			//检索框
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter("externalNo like ? ", "%" + keyword + "%");
			}
			//将  没有“未通过”会审内容  的  会审记录  过滤掉（如果该会审内容都通过了会审，那么就不显示）
			jqGrid.addHqlFilter(" externalId in (select externalId from ExternalDrawingsContent where drawingType='"+ ExternalDrawingsContent.TYPE_THREE+"' or drawingType='"+ ExternalDrawingsContent.TYPE_FOUR+"' group by externalId ) ");
			//将院方反馈中已经反馈完成的会审记录过滤掉
			jqGrid.addHqlFilter(" externalId in (select externalId from ExternalDrawingsContent where" +
					" drawingId in( select versionId from DrawingLibraryVersion where checkStatus = "+String.valueOf(DrawingLibraryVersion.NO_COMPLETE_OUT)+" and libraryId in ( select libraryId from DrawingLibraryVersion where checkStatus = 10 group by libraryId )) and id not in (select contentId from HospitaReviewRec) group by externalId)");
			//
		}
		return this.retrievePage(jqGrid);
    }

	/**
	 * 数据库中是否存在记录.
	 * @param drawingId
	 * @return boolean
	 */
	public boolean existInDB(String externalId) {
		String hql = "select count(externalId)  from " + ExternalDrawings.class.getName() + " where id=?";
		Object obj = this.retrieveFirst(hql, externalId);
		if (obj == null) {
			return false;
		} else {
			return (Long) obj > 0;
		}
	}

}
