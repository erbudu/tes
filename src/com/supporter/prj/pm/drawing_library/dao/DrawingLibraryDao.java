package com.supporter.prj.pm.drawing_library.dao;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.drawing_library.entity.DrawingLibrary;
import com.supporter.util.CommonUtil;


@Repository
public class DrawingLibraryDao extends MainDaoSupport < DrawingLibrary, String >{
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param qualityProblemIds 模块ids
	 * @return
	 */
	public List<DrawingLibrary> findPage(JqGrid jqGrid, DrawingLibrary drawingLibrary,UserProfile user) {
		// //验证维护权限
		// String authHql = EIPService.getAuthorityService().getHqlFilter(user,
		// DrawingLibrary.APP_NAME, AuthConstant.AUTH_OPER_NAME_PAGE);
		// jqGrid.addHqlFilter(authHql);
		if(drawingLibrary != null){
			//图纸编号、图纸名称
			String drawingNo = drawingLibrary.getDrawingNo();
			if(StringUtils.isNotBlank(drawingNo) ){
				jqGrid.addHqlFilter("(drawingNo like ? or drawingName like ? or drawingAttribute like ? or regionName like ? " +
						")","%" + drawingNo + "%","%" + drawingNo + "%","%" + drawingNo + "%","%" + drawingNo + "%");
			}
			// 只获取某项目下的数据
			String prjId = drawingLibrary.getPrjId();
			if (StringUtils.isNotBlank(prjId)) {
				jqGrid.addHqlFilter(" prjId = ? ", prjId);
			} else {// 判断条件值任意，目的是返回一个空表
				jqGrid.addHqlFilter(" 1 != 1");
			}

			//获取状态
			Integer status = drawingLibrary.getStatus();
			if(status!=null){
				jqGrid.addHqlFilter(" status = ? ",status);
			}
			//获取是否内审
			Integer isIn = drawingLibrary.getIsIn();
			if(isIn!=null){
				jqGrid.addHqlFilter(" isIn = ? ",isIn);
			}
			//获取是否外审
			Integer isOut = drawingLibrary.getIsOut();
			if(isOut!=null){
				jqGrid.addHqlFilter(" isOut = ? ",isOut);
			}
			//获取日期
			java.util.Date date = drawingLibrary.getCreatedDate();
			if(date!=null){
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				int day = c.get(Calendar.DATE);
				c.set(Calendar.DATE, day + 1);
				java.util.Date date2= c.getTime();
				jqGrid.addHqlFilter(" createdDate >= ? and createdDate< ? ",date,date2);
			}
			//获取部位
			String regionId  = drawingLibrary.getRegionId();
			if(StringUtils.isNotBlank(regionId)){
				jqGrid.addHqlFilter(" regionId = ? ",regionId);
			}
			//获取专业
			String drawingAttributeId = drawingLibrary.getDrawingAttributeId();
			if(StringUtils.isNotBlank(drawingAttributeId)){
				jqGrid.addHqlFilter(" drawingAttributeId = ? ",drawingAttributeId);
			}
		}
		return this.retrievePage(jqGrid);
	}
	
	// 验证图纸编号的唯一性
	public List<DrawingLibrary> getDrawingLibraryListByDrawingNo(String id, String drawingNo) {
		String hql = "from " + DrawingLibrary.class.getName() + " where id <> ? and drawingNo = ? ";
		List<DrawingLibrary> editers = this.find(hql, CommonUtil.trim(id), CommonUtil.trim(drawingNo));
		return editers;
	}

	/**
	 * 数据库中是否存在记录.
	 * @param drawingId
	 * @return boolean
	 */
	public boolean existInDB(String drawingId) {
		String hql = "select count(id)  from " + DrawingLibrary.class.getName() + " where id=?";
		Object obj = this.retrieveFirst(hql, drawingId);
		if (obj == null) {
			return false;
		} else {
			return (Long) obj > 0;
		}
	}

	//
	// //验证图纸编号的唯一性--用于导入功能
	// public List<DrawingLibrary> getDrawingLibraryListByDrawingNo(String
	// drawingNo) {
	// String hql = "from " + DrawingLibrary.class.getName()
	// + " where drawingNo = ? ";
	// List<DrawingLibrary> editers = this.find(hql, CommonUtil.trim(drawingNo));
	// return editers;
	// }

	// /**
	// * 获取所有未报盘记录的主键
	// * @return ids
	// */
	// @SuppressWarnings("unchecked")
	// public List<String> getUnOfferedDatas() {
	// String hql = "select d.id from " + DrawingLibrary.class.getName()
	// + " d where not exists ("
	// +"select o.offerId from " + Offer.class.getName()
	// + " o where o.entityClassName=? and d.id=o.entityId)";
	// return this.retrieve(hql, DrawingLibrary.class.getName());
	// }
	
}
