package com.supporter.prj.pm.drawing_library.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.drawing_library.dao.DrawingLibraryDao;
import com.supporter.prj.pm.drawing_library.dao.DrawingLibraryVersionDao;
import com.supporter.prj.pm.drawing_library.entity.DrawingLibrary;
import com.supporter.prj.pm.drawing_library.entity.DrawingLibraryVersion;

@Service
@Transactional(TransManager.APP)
public class DrawingLibraryService {
	@Autowired
	private DrawingLibraryDao dao;
	@Autowired
	private DrawingLibraryVersionDao versionDao;
	// @Autowired
	// private DrawingLibraryOfferService drawingOfferService;
	// @Autowired
	// private OfferService offerService;

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 */
	public DrawingLibrary initEditOrViewPage(String id,UserProfile user) {
		DrawingLibrary drawingLibrary = null;
		if (StringUtils.isNotBlank(id)) {	
			drawingLibrary =  dao.get(id);
		}
		if (drawingLibrary != null) {	
			drawingLibrary.setIsNew(false);
			drawingLibrary.setModifiedName(user.getName());
			drawingLibrary.setModifiedId(user.getPersonId());
			drawingLibrary.setModifiedDate(new Date());
			return drawingLibrary;
		}else{
			drawingLibrary = new DrawingLibrary();
			drawingLibrary.setIsNew(true);
			drawingLibrary.setStatus(0);//状态
			drawingLibrary.setIsIn(0);
			drawingLibrary.setIsOut(1);
			drawingLibrary.setIsHistory(0);
//			drawingLibrary.setDrawingVersion("初稿");;//版本
			drawingLibrary.setId(com.supporter.util.UUIDHex.newId());
			drawingLibrary.setCreatedName(user.getName());
			drawingLibrary.setCreatedId(user.getPersonId());
			drawingLibrary.setCreatedDate(new Date());
			if(user.getDept() != null){
				drawingLibrary.setCreatedDept(user.getDept().getName());
				drawingLibrary.setCreatedDeptId(user.getDeptId());
			}	
			return drawingLibrary;
		}	
	}
	
	public DrawingLibraryVersion initEditOrViewPageVersion(String versionId, String libraryId, UserProfile user) {
		DrawingLibraryVersion rec = new DrawingLibraryVersion();
		rec = versionDao.get(versionId);

		return rec;
	}
	
	/*
	 * 判断是否为整数 
	  * @param str 传入的字符串 
	  * @return 是整数返回true,否则返回false 
	*/
	public static boolean isInteger(String str) {  
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
	}
	
	public DrawingLibraryVersion getVersionById(String versionId) {
		return versionDao.get(versionId);
	}
	
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @param drawingLibrary
	 * @param user
	 * @return
	 */
	public List< DrawingLibrary > getGrid(JqGrid jqGrid, DrawingLibrary drawingLibrary,UserProfile user) {
		return dao.findPage(jqGrid, drawingLibrary,user);
	}
	public List< Map < String, Object > > getVersionGrid(JqGrid jqGrid, DrawingLibraryVersion drawingLibraryVersion,UserProfile user,String libraryId) {
		List < DrawingLibraryVersion > list = versionDao.getDrawingLibraryListByLibraryId(jqGrid, drawingLibraryVersion, libraryId);
		if (list == null || list.size() == 0) {
			return null;
		}
		
		List < Map < String, Object > > mapList = new ArrayList< Map < String, Object > >();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			DrawingLibraryVersion rec = list.get(i);
			Map < String, Object > map = new HashMap < String, Object >();
			map.put("versionId", rec.getVersionId());
			map.put("versionRule", rec.getVersionRule());
			map.put("versionNo", rec.getVersionNo());
			map.put("createdId", rec.getCreatedId());
			map.put("createdName", rec.getCreatedName());
			map.put("createdDate", rec.getCreatedDate());
			map.put("files", EIPService.getFileUploadService().getFileList(DrawingLibrary.APP_NAME, DrawingLibrary.FILE_NAME,
					rec.getVersionId()));
			mapList.add(map);
		}
		jqGrid.setRows(mapList);
		return mapList;
	}
	
	public List< DrawingLibraryVersion > getWidgetGrid(JqGrid jqGrid, DrawingLibraryVersion drawingLibraryVersion,
			UserProfile user,String idSrcsStr,String flagStr, String completionFlagStr, String letterTypeStr) {
		return versionDao.getWidgetList(jqGrid, drawingLibraryVersion, idSrcsStr, flagStr, completionFlagStr, letterTypeStr);
	}
	
	public List<DrawingLibrary> getProjectListByPrjName(String id,String drawingNo) {
		return dao.getDrawingLibraryListByDrawingNo(id,drawingNo);
	}
	
	//选择图纸库控件--公用组件方法，不可擅自修改，有关联关系
	public List<DrawingLibraryVersion> getVersionByVersionNo(String versionId, String libraryId,String versionNo) {
		return versionDao.getVersionByVersionNo(versionId, libraryId,versionNo);
	}
	
	// /**
	// * 保存或更新
	// */
	// public DrawingLibrary saveOrUpdate(UserProfile user, DrawingLibrary
	// drawingLibrary) {
	// DrawingLibrary ret = null;
	// boolean isNew = drawingLibrary.getIsNew();
	// if(isNew){
	// //图纸专业
	// String drawingAttribute =
	// EIPService.getComCodeTableService().getCodeTable("DRAWING_SPECIALTY").getDisplay(drawingLibrary.getDrawingAttributeId());
	// drawingLibrary.setDrawingAttribute(drawingAttribute);
	// //图纸部位
	// String regionName =
	// EIPService.getComCodeTableService().getCodeTable("DRAWING_POSITION").getDisplay(drawingLibrary.getRegionId());
	// drawingLibrary.setRegionName(regionName);
	// this.dao.save(drawingLibrary);
	// ret = drawingLibrary;
	// } else {// 编辑
	// //图纸专业
	// String drawingAttribute =
	// EIPService.getComCodeTableService().getCodeTable("DRAWING_SPECIALTY").getDisplay(drawingLibrary.getDrawingAttributeId());
	// drawingLibrary.setDrawingAttribute(drawingAttribute);
	// //图纸部位
	// String regionName =
	// EIPService.getComCodeTableService().getCodeTable("DRAWING_POSITION").getDisplay(drawingLibrary.getRegionId());
	// drawingLibrary.setRegionName(regionName);
	// this.dao.update(drawingLibrary);
	// ret = drawingLibrary;
	// }
	//
	// return ret;
	// }
	//
	// public DrawingLibraryVersion saveOrUpdateVersion(UserProfile user,
	// DrawingLibraryVersion rec) {
	// DrawingLibraryVersion ret = null;
	// String libraryId = rec.getLibraryId();
	// DrawingLibrary library = dao.get(libraryId);
	// if (StringUtils.isBlank(rec.getVersionId())) {
	// rec.setDrawingNo(library.getDrawingNo()+rec.getVersionRule()+rec.getVersionNo());
	// rec.setDrawingName(library.getDrawingName());
	// rec.setCheckStatus(0);
	// versionDao.save(rec);
	// ret = rec;
	// library.setDrawingVersion((rec.getVersionRule()+rec.getVersionNo()).equals("")?"初稿":rec.getVersionRule()+rec.getVersionNo());
	// dao.update(library);
	// } else {
	// rec.setDrawingNo(library.getDrawingNo()+rec.getVersionRule()+rec.getVersionNo());
	// rec.setDrawingName(library.getDrawingName());
	// rec.setCheckStatus(0);
	// versionDao.update(rec);
	// ret = rec;
	// }
	// return ret;
	// }
	//
	// /**
	// *
	// * @param atlasId 图纸id
	// * @param checkStatus 装太
	// * @param date 审完日期
	// */
	// public void changeDrawingStatus(String atlasId, int checkStatus, Date date) {
	// if(StringUtils.isBlank(atlasId)){
	// return;
	// }
	// DrawingLibraryVersion version = this.versionDao.get(atlasId);
	// version.setCheckStatus(checkStatus);
	// if(date!=null){
	// version.setCheckDate(date);
	// }
	// versionDao.update(version);
	// //更新主表状态
	// DrawingLibrary library = this.get(version.getLibraryId());
	// //更改版本号
	// if(StringUtils.isNotBlank(version.getVersionRule())&&StringUtils.isNotBlank(version.getVersionNo())){
	// library.setDrawingVersion(version.getVersionRule()+version.getVersionNo());
	// }
	// //更新编号
	// //library.setDrawingNo(version.getDrawingNo());
	// library.setStatus(version.getCheckStatus());
	// library.setModifiedDate(version.getCheckDate());
	// this.dao.update(library);
	// }
	// /**
	// * 图纸作废
	// */
//	public DrawingLibrary toVoid(UserProfile user, DrawingLibrary drawingLibrary) {
//		DrawingLibrary ret = null;
//		boolean isNew = drawingLibrary.getIsNew();
//		if(isNew){		
//			drawingLibrary.setStatus(DrawingLibrary.StatusCodeTable.EFFECT);
//				this.dao.save(drawingLibrary);	
//				ret = drawingLibrary;
//		} else {// 编辑
//			drawingLibrary.setStatus(ExamReport.StatusCodeTable.EFFECT);
//			this.dao.update(drawingLibrary);
//			ret = drawingLibrary;
//		}
//		return drawingLibrary;
//	}
	
	public List<DrawingLibraryVersion> getListByLibraryId(String libraryId) {
		return versionDao.getListByLibraryId(libraryId);	
	}
	
	// /**
	// * 删除
	// */
	// public void delete(UserProfile user, String ids) {
	// if (StringUtils.isNotBlank(ids)) {
	// String[] idArray = ids.split(",");
	// for (String id : idArray) {
	// if (id == null) {
	// continue;
	// }
	// //先删主表再删从表
	// DrawingLibrary drawingLibrary = dao.get(id);
	// if(drawingLibrary != null) {
	// //当前登录用户，应用编号，权限项编号，主键，对象
	// AuthUtils.canExecute(user,DrawingLibrary.APP_NAME,
	// AuthConstant.AUTH_OPER_NAME_EDIT,id,drawingLibrary);
	// dao.delete(drawingLibrary);
	// }
	//
	// List<DrawingLibraryVersion> drawingLibraryVersionList =
	// versionDao.getListByLibraryId(id);
	// if(drawingLibraryVersionList != null) {
	// versionDao.delete(drawingLibraryVersionList);
	// }
	//
	// }
	// }
	// }
	//
	// // 删除明细信息
	// @Transactional(transactionManager = TransManager.APP)
	// public String delDrawingLibraryVersion(DrawingLibraryVersion version) {
	// versionDao.delete(version);
	// //删除后将主表中的版本号进行一下更新
	// DrawingLibrary library = dao.get(version.getLibraryId());
	// if(library != null) {
	// String maxVersionNoStr = versionDao.getMaxVersionNo(library.getId());
	// if(!maxVersionNoStr.equals("") && maxVersionNoStr != null) {
	// if(isInteger(maxVersionNoStr)) {
	// Integer maxVersionNo = Integer.parseInt(maxVersionNoStr);
	// library.setDrawingVersion(version.getVersionRule()+maxVersionNo);
	// dao.update(library);
	// return version.getVersionRule()+maxVersionNo;
	// }else {
	// char[] c = maxVersionNoStr.toCharArray();
	// int now = (int) c[0];
	// char uppercase = (char) (now);
	// library.setDrawingVersion(version.getVersionRule()+String.valueOf(uppercase));
	// dao.update(library);
	// return version.getVersionRule()+String.valueOf(uppercase);
	// }
	// }
	// }
	// return "";
	// }
	//
	// public DrawingLibrary get(String libraryId) {
	// return this.dao.get(libraryId);
	// }
	// /**
	// * 获取导入动态列
	// * @param dbYear 年库
	// * @param user
	// * @param memberId
	// * @return
	// */
	// public List<String> getImprotExpFields (){
	// List<String> colNames = new ArrayList<String>();
	// colNames.add("drawingNo");
	// colNames.add("drawingName");
	// colNames.add("versionNo");
	// colNames.add("drawingAttribute");
	// colNames.add("regionName");
	// colNames.add("submitDate");
	// colNames.add("noticeDate");
	// colNames.add("approvalNo");
	// return colNames;
	// }
	//
	// @SuppressWarnings("deprecation")
	// public HSSFWorkbook getWorkbookByTreeId(UserProfile userProfile){
	// String excelFileName = "DRAWING_HISTORY.xls";
	// HSSFWorkbook wb = null; //工作薄
	// try {
	// //导出Excel文件数据
	// ExportUtils util = new ExportUtils();
	// File file =
	// util.getExcelDemoFile(File.separator+"template_excel_def"+File.separator+excelFileName);
	// wb = util.getExcelWorkbook(file);
	//
	// HSSFCellStyle cellStyle = wb.createCellStyle();
	//
	// cellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
	// cellStyle.setBorderLeft(BorderStyle.THIN); // 左边框
	// cellStyle.setBorderTop(BorderStyle.THIN); // 上边框
	// cellStyle.setBorderRight(BorderStyle.THIN); // 右边框
	//
	// String color = "ddd9c4";
	// int r = Integer.parseInt((color.substring(0,2)),16); //转为16进制
	// int g = Integer.parseInt((color.substring(2,4)),16);
	// int b = Integer.parseInt((color.substring(4,6)),16);
	//
	// HSSFPalette palette = wb.getCustomPalette();
	// palette.setColorAtIndex((short)9, (byte) r, (byte) g, (byte) b);
	// HSSFCellStyle style = wb.createCellStyle();
	// style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	// style.setFillForegroundColor((short)9);
	// style.setBorderBottom(BorderStyle.THIN); // 下边框
	// style.setBorderLeft(BorderStyle.THIN); // 左边框
	// style.setBorderTop(BorderStyle.THIN); // 上边框
	// style.setBorderRight(BorderStyle.THIN); // 右边框
	//
	// style.setAlignment(HorizontalAlignment.CENTER); // 居中
	//
	// HSSFFont font = wb.createFont();
	// font.setFontName("微软雅黑");
	// font.setFontHeightInPoints((short) 10);//设置字体大小
	// style.setFont(font);//选择需要用到的字体格式
	//
	// style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直
	// style.setAlignment(HorizontalAlignment.CENTER_SELECTION);//水平
	//
	// //专业
	// List<IComCodeTableItem> list = EIPService.getComCodeTableService()
	// .getCodeTableItems("DRAWING_SPECIALTY");
	// Sheet sheet = wb.getSheetAt(1);
	// // 下面是具体内容
	// Row row = null;
	// Cell cell = null;
	// row = sheet.createRow(1);
	// if (list != null && list.size() != 0) {
	// int length = list.size();
	// for (int i = 0; i < length; i++) {
	// row = sheet.createRow(i + 1);
	// // 专业
	// cell = row.createCell(0, Cell.CELL_TYPE_STRING);
	// cell.setCellValue(list.get(i).getDisplayName());
	// cell.setCellStyle(cellStyle);
	// }
	// }
	// //部位
	// List<IComCodeTableItem> list1 = EIPService.getComCodeTableService()
	// .getCodeTableItems("DRAWING_POSITION");
	// Sheet sheetTwo = wb.getSheetAt(2);
	// // 下面是具体内容
	// Row rowTwo = null;
	// Cell cellTwo = null;
	// rowTwo = sheetTwo.createRow(1);
	// if (list1 != null && list1.size() != 0) {
	// int length = list1.size();
	// for (int i = 0; i < length; i++) {
	// rowTwo = sheetTwo.createRow(i + 1);
	//
	// // 部位
	// cellTwo = rowTwo.createCell(0, Cell.CELL_TYPE_STRING);
	// cellTwo.setCellValue(list1.get(i).getDisplayName());
	// cellTwo.setCellStyle(cellStyle);
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return wb;
	// }
	// /**
	// * 获取导入动态列
	// * @param dbYear 年库
	// * @param user
	// * @param memberId
	// * @return
	// */
	// public List<String> getImprotFields (){
	// List<String> colNames = new ArrayList<String>();
	// colNames.add("drawingNo");
	// colNames.add("drawingName");
	// colNames.add("isIn");
	// colNames.add("isOut");
	// colNames.add("drawingAttribute");
	// colNames.add("regionName");
	// colNames.add("summary");
	// colNames.add("approvalNo");
	// return colNames;
	// }
	// @SuppressWarnings("deprecation")
	// public HSSFWorkbook getWorkByTreeId(UserProfile userProfile){
	// String excelFileName = "DRAWING_LIBRARY.xls";
	// HSSFWorkbook wb = null; //工作薄
	// try {
	// //导出Excel文件数据
	// ExportUtils util = new ExportUtils();
	// File file =
	// util.getExcelDemoFile(File.separator+"template_excel_def"+File.separator+excelFileName);
	// wb = util.getExcelWorkbook(file);
	//
	// HSSFCellStyle cellStyle = wb.createCellStyle();
	//
	// cellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
	// cellStyle.setBorderLeft(BorderStyle.THIN); // 左边框
	// cellStyle.setBorderTop(BorderStyle.THIN); // 上边框
	// cellStyle.setBorderRight(BorderStyle.THIN); // 右边框
	//
	// String color = "ddd9c4";
	// int r = Integer.parseInt((color.substring(0,2)),16); //转为16进制
	// int g = Integer.parseInt((color.substring(2,4)),16);
	// int b = Integer.parseInt((color.substring(4,6)),16);
	//
	// HSSFPalette palette = wb.getCustomPalette();
	// palette.setColorAtIndex((short)9, (byte) r, (byte) g, (byte) b);
	// HSSFCellStyle style = wb.createCellStyle();
	// style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	// style.setFillForegroundColor((short)9);
	// style.setBorderBottom(BorderStyle.THIN); // 下边框
	// style.setBorderLeft(BorderStyle.THIN); // 左边框
	// style.setBorderTop(BorderStyle.THIN); // 上边框
	// style.setBorderRight(BorderStyle.THIN); // 右边框
	//
	// style.setAlignment(HorizontalAlignment.CENTER); // 居中
	//
	// HSSFFont font = wb.createFont();
	// font.setFontName("微软雅黑");
	// font.setFontHeightInPoints((short) 10);//设置字体大小
	// style.setFont(font);//选择需要用到的字体格式
	//
	// style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直
	// style.setAlignment(HorizontalAlignment.CENTER_SELECTION);//水平
	//
	// //专业
	// List<IComCodeTableItem> list = EIPService.getComCodeTableService()
	// .getCodeTableItems("DRAWING_SPECIALTY");
	// Sheet sheet = wb.getSheetAt(1);
	// // 下面是具体内容
	// Row row = null;
	// Cell cell = null;
	// row = sheet.createRow(1);
	// if (list != null && list.size() != 0) {
	// int length = list.size();
	// for (int i = 0; i < length; i++) {
	// row = sheet.createRow(i + 1);
	// // 专业
	// cell = row.createCell(0, Cell.CELL_TYPE_STRING);
	// cell.setCellValue(list.get(i).getDisplayName());
	// cell.setCellStyle(cellStyle);
	// }
	// }
	// //部位
	// List<IComCodeTableItem> list1 = EIPService.getComCodeTableService()
	// .getCodeTableItems("DRAWING_POSITION");
	// Sheet sheetTwo = wb.getSheetAt(2);
	// // 下面是具体内容
	// Row rowTwo = null;
	// Cell cellTwo = null;
	// rowTwo = sheetTwo.createRow(1);
	// if (list1 != null && list1.size() != 0) {
	// int length = list1.size();
	// for (int i = 0; i < length; i++) {
	// rowTwo = sheetTwo.createRow(i + 1);
	//
	// // 部位
	// cellTwo = rowTwo.createCell(0, Cell.CELL_TYPE_STRING);
	// cellTwo.setCellValue(list1.get(i).getDisplayName());
	// cellTwo.setCellStyle(cellStyle);
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return wb;
	// }

	// /**
	// * 更新图纸版本
	// * @param drawingLibraryVersion
	// */
	// public void updateVersion(DrawingLibraryVersion drawingLibraryVersion) {
	// this.versionDao.update(drawingLibraryVersion);
	// }
	//
	// /**
	// * 更新图纸库
	// * @param library
	// */
	// public void update(DrawingLibrary library) {
	// this.dao.update(library);
	//
	// }

	// /**
	// * 图纸变更
	// * @param ids
	// * @return
	// */
	// public String drawingChange(String ids, UserProfile user) {
	// String mesage = "变更失败！";
	// if (StringUtils.isNotBlank(ids)){
	// DrawingLibrary library =this.get(ids);
	// if (library==null){
	// return mesage;
	// }
	// //获取最新版本
	// List<DrawingLibraryVersion> versions = this.getListByLibraryId(ids);
	// if (versions!=null&&versions.size()>0){
	// for (DrawingLibraryVersion v: versions ) {
	// if (v == null) {
	// continue;
	// }
	// //如果图纸是外审完成的话
	// if (v.getCheckStatus()==DrawingLibraryVersion.COMPLETE_OUT){
	// //变更图纸状态为外审未通过
	// v.setCheckStatus(DrawingLibraryVersion.NO_COMPLETE_OUT);
	// //更新
	// this.versionDao.update(v);
	// //图纸升级
	// DrawingLibraryVersion newDraing = this.initEditOrViewPageVersion("",
	// ids,user);
	// //如果不是初稿的话 获取初稿版本号
	// String olddrawingNo = library.getDrawingNo();
	//
	// //重新赋值状态(升级版本 反馈)
	// newDraing.setCheckStatus(DrawingLibraryVersion.WAIT_AUDIT);
	// //图纸名称
	// newDraing.setDrawingName(v.getDrawingName());
	// //拼接新的图纸编号
	// StringBuffer drawingNo = new StringBuffer("");
	// if(StringUtils.isNotBlank(olddrawingNo)){
	// drawingNo.append(olddrawingNo);
	// }
	// if(StringUtils.isNotBlank(newDraing.getVersionRule())){
	// drawingNo.append(newDraing.getVersionRule());
	// }
	// if(StringUtils.isNotBlank(newDraing.getVersionNo())){
	// drawingNo.append(newDraing.getVersionNo());
	// }
	// newDraing.setDrawingNo(drawingNo.toString());
	// //保存升级版本后的对象
	// this.versionDao.save(newDraing);
	// //更新主表状态
	// //更改版本号
	// if(StringUtils.isNotBlank(newDraing.getVersionRule())&&StringUtils.isNotBlank(newDraing.getVersionNo())){
	// library.setDrawingVersion(newDraing.getVersionRule()+newDraing.getVersionNo());
	// }
	// //更新编号
	// //library.setDrawingNo(version.getDrawingNo());
	// library.setStatus(DrawingLibraryVersion.FINISH_REPLY);
	// library.setModifiedDate(newDraing.getCheckDate());
	// this.dao.update(library);
	// mesage = "变更成功！";
	// break;
	// }
	// }
	// }
	// }
	// return mesage;
	// }

	// /**
	// * 报盘推送数据
	// * @return 推送条数
	// */
	// public int batchOffer() {
	// List <String> ids = dao.getUnOfferedDatas();
	// if (ids != null && ids.size() > 0) {
	// int size = ids.size();
	// for (int i = 0; i < size; i++) {
	// IOfferRec rec = drawingOfferService.initOfferRec(ids.get(i));
	// offerService.addOffer(null, rec);
	// }
	// return size;
	// } else {
	// return 0;
	// }
	// }
}
