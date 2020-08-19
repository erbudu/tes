package com.supporter.prj.pm.drawing_library.dao;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.pm.drawing_library.entity.DrawingLibraryVersion;
import com.supporter.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DrawingLibraryVersionDao extends MainDaoSupport < DrawingLibraryVersion, String >{
	public List<DrawingLibraryVersion> getDrawingLibraryListByLibraryId(
			JqGrid jqGrid,DrawingLibraryVersion drawingLibraryVersion,String libraryId) {
		if(drawingLibraryVersion != null){
			if(StringUtils.isNotBlank(libraryId) ){
				jqGrid.addHqlFilter(" libraryId = ? )", CommonUtil.trim(libraryId));
			}
		}
		return this.retrievePage(jqGrid);
	}
	
	public List<DrawingLibraryVersion> getListByLibraryId(
			String libraryId) {
		String hql = "from " + DrawingLibraryVersion.class.getName()
			+ " where libraryId = ?";
		List<DrawingLibraryVersion> editers = this.find(hql, CommonUtil.trim(libraryId));
		return editers;
	}
	
	/**
	 * 选择图纸库控件--公用组件方法，不可擅自修改，有关联关系
	 * @param jqGrid
	 * @param drawingLibraryVersion
	 * @param idSrcsStr 页面中已选择图纸的id，用来过滤显示
	 * @param flagStr 各业务模块标志位（1：图纸会审，2图纸外审，3交付施工，4施工图交底）
	 * @param letterTypeStr 意见函类型
	 * @param completionFlagStr 单选图纸控件传过来的参数
	 * @return
	 */
	public List<DrawingLibraryVersion> getWidgetList(JqGrid jqGrid,DrawingLibraryVersion drawingLibraryVersion,
			String idSrcsStr,String flagStr, String completionFlagStr, String letterTypeStr) {
		if(drawingLibraryVersion != null){
			//图纸编号、图纸名称
			String drawingNo = drawingLibraryVersion.getDrawingNo();
			if(StringUtils.isNotBlank(drawingNo) ){
				jqGrid.addHqlFilter("(drawingNo like ? or drawingName like ?)","%" + drawingNo + "%","%" + drawingNo + "%");
			}
			
			//页面传入的已选图纸的id（只是选了还没点保存的）
			String idStr = "'" + "'";
			if (StringUtils.isNotBlank(idSrcsStr)) {
				String[] idArray = idSrcsStr.split(",");
				for (String idSrc : idArray) {
					if (idSrc == null) {
						continue;
					}
					idStr += ",'" + CommonUtil.trim(idSrc) + "'";
				}
				jqGrid.addHqlFilter(" versionId not in (" + idStr + ") ");
			}
			
			if (StringUtils.isNotBlank(flagStr)) {
				//再将图纸会审数据库中已有的记录过滤掉
				if(flagStr.equals("1")) {//等于1说明是图纸会审传过来的参数
					//1、已经审核完成的不再显示（0：未审，1：在审，2审完）
					jqGrid.addHqlFilter(" checkStatus <> 2 ");
					//2、需要内审的
					jqGrid.addHqlFilter(" libraryId in (select id from DrawingLibrary where isIn=1 )");
					//3、将图纸会审的会审内容表中已保存的图纸过滤掉，只显示没有保存过的图纸。
					//jqGrid.addHqlFilter(" versionId not in (select atlasId from InterrorgateContent) ");
					//获取状态是草稿的图纸
					jqGrid.addHqlFilter(" checkStatus = 0 ");
					//4、只能选择R0（初稿）的图纸--会审只审初稿图纸
					jqGrid.addHqlFilter(" ( versionNo = '' or versionNo is null ) ");
				}
				//等于2说明是图纸外审传过来的参数
				if(flagStr.equals("2")) {
					//按状态过滤(状态 0 草稿 2 内审完成 12 升级版本号待审核 )
					jqGrid.addHqlFilter(" ( checkStatus = 0 or  checkStatus = 2 or checkStatus = 12 )");
					//2、只外审 or 既内审又外审的（院方反馈生效后的） or 既内审又外审的（会审有无意见选择了有的）
					/*jqGrid.addHqlFilter(" (libraryId in (select id from DrawingLibrary where isIn=0 and isOut=1 ))"
							+ " or (versionId in ("
									+ "select r.atlasId from HospitaReview h,HospitaReviewRec r where h.feedbackId=r.feedbackId "
									+ "and h.status= 20 and r.atlasId in ("
											+ "select versionId from DrawingLibraryVersion where libraryId in "
												+ "(select id from DrawingLibrary where isIn=1 and isOut=1)))) "
							+ "or versionId in (select n.atlasId from ConstructInterrorgate g.InterrorgateContent n where g.interrorgateId=n.interrorgateId and g.status= 2 and (n.isPass=1 or n.feedBack='pass') )");*/
					jqGrid.addHqlFilter(" (libraryId in (select id from DrawingLibrary where isIn=0 and isOut=1)"
							+ " or ( libraryId in (select id from DrawingLibrary where isIn=1 and isOut=1) and " +
							"(versionId in (select n.atlasId from ConstructInterrorgate g,InterrorgateContent n where g.interrorgateId=n.interrorgateId and g.status= 2 " +
							" " +
							"and (n.isPass = 0 or n.feedBack='pass') " +
							") or checkStatus = 2 or checkStatus = 12 ))))");
					//3、那么将去图纸外审的外审图纸表中将已保存的图纸id过滤掉，只显示没有保存过的图纸
					jqGrid.addHqlFilter(" versionId not in (select drawingId from ExternalDrawingsContent) ");
					//可选状态 初稿 内审完成
				}
				//等于3说明是交付施工传过来的参数
				if(flagStr.equals("3")) {
					//1、既不内审也不外审(可直接交付施工) or 审完的图纸（checkStatus 0：未审，1：在审，2内审完 4外审完 ）
					jqGrid.addHqlFilter(" (libraryId in (select id from DrawingLibrary where isIn=0 and isOut=0 ))"
							+ " or (checkStatus = 2 and libraryId in (select id from DrawingLibrary where isIn=1 and isOut=0 ))" +
							" or (checkStatus = 4 and libraryId in (select id from DrawingLibrary where isOut=1 ))");
					//2、再将已经交付施工的图纸过滤掉
					jqGrid.addHqlFilter(" versionId not in (select drawingId from DeliveryDrawings) ");
				}
				//等于4说明是施工图交底传过来的参数
				if(flagStr.equals("4")) {
					//1、只能选取已经交付施工的图纸
					jqGrid.addHqlFilter(" versionId in (select r.drawingId from DeliveryConstruction t, DeliveryDrawings r "
							+ "where t.deliveryId=r.deliveryId and t.status=1) ");
					//2、再将已经交底的图纸过滤掉
					jqGrid.addHqlFilter(" versionId not in (select atlasId from ConstructDrawingsRec) ");
				}
				//等于5说明是意见函传过来的参数
				if(flagStr.equals("5")) {
					if(StringUtils.isNotBlank(letterTypeStr)) {
						//1、将已保存的letterTypeStr类型的意见函过滤掉（0：会审意见函，1：业主图审意见函）
						jqGrid.addHqlFilter(" versionId not in (select s.versionId from LetterInverstigate i, LetterInverstigateVersion s where i.id=s.letterId and i.letterType = ? ) ", Integer.parseInt(letterTypeStr));
					}else {
						jqGrid.addHqlFilter(" 1=2 ");
					}
				}
			}
			
			//通过选择图纸的控件传入的参数
			if (StringUtils.isNotBlank(completionFlagStr)) {
				//竣工图用来检索图纸信息的参数 
				if(completionFlagStr.equals("1")) {
					//1、只能选取已经交付施工的图纸
					jqGrid.addHqlFilter(" versionId in (select r.drawingId from DeliveryConstruction t, DeliveryDrawings r "
							+ "where t.deliveryId=r.deliveryId and t.status=1) ");
					//2、再将已经存在的竣工图进行过滤
					jqGrid.addHqlFilter(" versionId not in (select drawingId from CompletionPlan) ");
				}
				//设计变更用来选择图纸信息的参数 
				if(completionFlagStr.equals("2")) {
					//1、只能选取已经交付施工的图纸
					jqGrid.addHqlFilter(" versionId in (select r.drawingId from DeliveryConstruction t, DeliveryDrawings r "
							+ "where t.deliveryId=r.deliveryId and t.status=1) ");
				}
//				//图纸会审用来选择图纸信息的参数 
//				if(completionFlagStr.equals("3")) {
//					//1、已经审核完成的不再显示（0：未审，1：在审，2审完）
//					jqGrid.addHqlFilter(" checkStatus <> 2 ");
//					//2、需要内审的
//					jqGrid.addHqlFilter(" libraryId in (select id from DrawingLibrary where isIn=1 )");
//					//3、将图纸会审的会审内容表中已保存的图纸过滤掉，只显示没有保存过的图纸
//					jqGrid.addHqlFilter(" versionId not in (select atlasId from InterrorgateContent) ");
//					//4、只能选择初稿的图纸--会审只审初稿图纸
//					jqGrid.addHqlFilter(" versionNo = '' ");
//				}
			}
		}
		return this.retrievePage(jqGrid);
	}
	
	//验证图纸编号的唯一性
	public List<DrawingLibraryVersion> getVersionByVersionNo(String versionId, String libraryId,String versionNo) {
		String hql = "from " + DrawingLibraryVersion.class.getName()
			+ " where versionId <> ? and libraryId = ? and versionNo = ? ";
		List<DrawingLibraryVersion> editers = this.find(hql, CommonUtil.trim(versionId), CommonUtil.trim(libraryId), CommonUtil.trim(versionNo));
		return editers;
	}
	
	// 获取最大版本号
	public String getMaxVersionNo(String libraryId) {
		String hql = "select max(versionNo) from "
				+ DrawingLibraryVersion.class.getName() + " where libraryId =? ";
		List<Object> result = this.find(hql,libraryId);
		if (result != null && result.size() > 0 && result.get(0) != null) {
			return result.get(0).toString();
		}
		return "";
	}
	
	//根据图纸编号查找图纸
	public List<DrawingLibraryVersion> getLibraryByNo(String versionNo) {
		String hql = "from " + DrawingLibraryVersion.class.getName()
			+ " where drawingNo = ? ";
		List<DrawingLibraryVersion> editers = this.find(hql, CommonUtil.trim(versionNo));
		return editers;
	}

	/**
	 * 根据图纸编号更新图纸状态
	 * @param drawingNo
	 * @param i
	 */
	public void updateDrawingStatus(String versonId, int i) {
		DrawingLibraryVersion entity = get(versonId);
		if(entity!=null){
			entity.setCheckStatus(i);
			update(entity);
		}
	}

//	public List<DrawingLibraryVersion> getVersionView(JqGrid jqGrid, UserProfile user,String versionId){
//		if(StringUtils.isNotBlank(versionId) ){
//			jqGrid.addHqlFilter(" versionId = ? )", CommonUtil.trim(versionId));
//		}
//		return this.retrievePage(jqGrid);
//     }
	
}
