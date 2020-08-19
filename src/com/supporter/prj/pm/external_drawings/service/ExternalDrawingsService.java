package com.supporter.prj.pm.external_drawings.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.drawing_library.entity.DrawingLibraryVersion;
import com.supporter.prj.pm.drawing_library.service.DrawingLibraryService;
import com.supporter.prj.pm.drawing_library.service.DrawingLibraryVersionService;
import com.supporter.prj.pm.external_drawings.dao.ExternalDrawingsContentDao;
import com.supporter.prj.pm.external_drawings.dao.ExternalDrawingsDao;
import com.supporter.prj.pm.external_drawings.entity.ExternalDrawings;
import com.supporter.prj.pm.external_drawings.entity.ExternalDrawingsContent;

@Service
@Transactional(TransManager.APP)
public class ExternalDrawingsService {
	@Autowired
	private ExternalDrawingsDao dao;
	@Autowired
	private ExternalDrawingsContentDao contentDao;
	@Autowired
	private ExternalDrawingsContentService contentService;
	@Autowired
	private DrawingLibraryVersionService versionService;
	@Autowired
	private DrawingLibraryService drawingLibraryService;
	@Autowired
	private FileUploadService fileUploadService;
	// @Autowired
	// private HospitaReviewService reviewService;

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param id
	 * @return
	 */
	public ExternalDrawings initEditOrViewPage(String externalId,UserProfile user) {
		ExternalDrawings externalDrawings = null;
		if (StringUtils.isNotBlank(externalId)) {			
			externalDrawings =  dao.get(externalId);
		}
		if (externalDrawings != null) {			
			externalDrawings.setIsNew(false);
			externalDrawings.setModifiedName(user.getName());
			externalDrawings.setModifiedId(user.getPersonId());
			externalDrawings.setModifiedDate(new Date());
			externalDrawings.setContentList(getRecList(externalId));
			return externalDrawings;
		}else{
			externalDrawings = new ExternalDrawings();
			externalDrawings.setIsNew(true);
			externalDrawings.setStatus(0);
			externalDrawings.setExternalDate(new Date());
			externalDrawings.setExternalId(com.supporter.util.UUIDHex.newId());
			externalDrawings.setCreatedName(user.getName());
			externalDrawings.setCreatedId(user.getPersonId());
			externalDrawings.setCreatedDate(new Date());
			if(user.getDept() != null){
				externalDrawings.setCreatedDept(user.getDept().getName());
				externalDrawings.setCreatedDeptId(user.getDeptId());
			}	
			return externalDrawings;
		}	
	}
	
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param map 
	 * @param jqGrid jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<ExternalDrawings> getGrid(JqGrid jqGrid, ExternalDrawings externalDrawings,UserProfile user, Map<String, Object> map) {
		return dao.findPage(jqGrid, externalDrawings,user,map);
	}
	
	public List<ExternalDrawings> getListByExternalNo(String externalId,String externalNo) {
		return dao.getListByExternalNo(externalId,externalNo);
	}
	
	public List<ExternalDrawingsContent> getContentGrid(UserProfile user, JqGrid jqGrid,
			ExternalDrawingsContent content,String externalId, String isFor) {
		return contentDao.findContentPage(user,jqGrid,content,externalId,isFor);
	}
	
	public List<ExternalDrawingsContent> getSelectContentGrid(UserProfile user, JqGrid jqGrid,ExternalDrawingsContent content) {
		List<ExternalDrawingsContent> list = contentDao.findSelectContent(user,jqGrid,content);
		if(list!=null&&list.size()>0){
			for (ExternalDrawingsContent externalDrawingsContent : list) {
				if (externalDrawingsContent == null) {
					continue;
				}
				//获取该版本的图纸
				DrawingLibraryVersion version = this.drawingLibraryService.getVersionById(externalDrawingsContent.getDrawingId());
				//如果查询的图纸不是升级版的话
				if(version.getCheckStatus()!=DrawingLibraryVersion.UPGRAGE){
					//将升级的信息回写
					List<DrawingLibraryVersion> newVersions = this.drawingLibraryService.getListByLibraryId(version.getLibraryId());
					//遍历各个版本找到升级后的图纸
					for (DrawingLibraryVersion drawingLibraryVersion : newVersions) {
						if (drawingLibraryVersion == null) {
							continue;
						}
						//找到升级后的图纸
						if(drawingLibraryVersion.getCheckStatus()==DrawingLibraryVersion.UPGRAGE){
							externalDrawingsContent.setDrawingId(drawingLibraryVersion.getVersionId());
							externalDrawingsContent.setDrawingName(drawingLibraryVersion.getDrawingName());
							externalDrawingsContent.setDrawingNo(drawingLibraryVersion.getDrawingNo());
						}
					}
				}
			}
			jqGrid.setRows(list);
		}
		return list;
	}
	
	// /**
	// * 保存或更新
	// * @param user 用户信息
	// * @param ExternalDrawings 实体类
	// * @return
	// */
	// public ExternalDrawings saveOrUpdate(UserProfile user, ExternalDrawings
	// externalDrawings) {
	// ExternalDrawings ret = null;
	// boolean isNew=externalDrawings.getIsNew();
	// if(isNew){
	// this.dao.save(externalDrawings);
	// ret = externalDrawings;
	// }else {// 编辑
	// this.dao.update(externalDrawings);
	// ret = externalDrawings;
	// }
	// saveOrUpdateContentList(user, externalDrawings,
	// externalDrawings.getDelContentIds(), "");//非初次外审
	// saveOrUpdateContentTwoList(user, externalDrawings,
	// externalDrawings.getDelContentTwoIds(), "");//初次外审
	// return ret;
	// }
	
	/**
	 * 判断是否为初次外审
	 * @param library
	 * @return 0:非初次外审，1初次外审
	 */
	public String checkIsFor(String libraryId) {
			//当存在外审( 4 外审完成 11 反馈完成)
			List<DrawingLibraryVersion> versons = versionService.find(" from DrawingLibraryVersion where  "
					+ "(checkStatus = 4 or checkStatus = 11) and libraryId = ? ",libraryId);
			if(versons!=null&&versons.size()>0){
				return "0";
			}
		return "1";
		
		/*List<DrawingLibraryVersion> list = this.versionService.find(" from DrawingLibraryVersion where libraryId = ?",libraryId);
		if(list!=null&&list.size()>1){
			return "0";
		}
		return "1";*/
	}
	
	// private void saveOrUpdateContentList(UserProfile user,ExternalDrawings
	// externalDrawings, String delContentIds, String flag) {
	// List<ExternalDrawingsContent> list = externalDrawings.getContentList();
	// if (list != null) {
	// for (ExternalDrawingsContent rec : list) {
	// if (rec == null) {
	// continue;
	// }
	// String recId = rec.getId();
	// if (StringUtils.isNotBlank(recId) && !(recId.indexOf("newId_")!=-1)) {
	// rec.setExternalId(externalDrawings.getExternalId());
	// rec.setIsFor(0);
	// contentDao.clear();
	// contentDao.update(rec);
	// //提交操作的时候将1类和2类的图纸更新为审完状态
	// if(flag.equals("1")) {
	// updateDrawingCheckStatus(flag, rec);
	// }
	// } else {
	//
	// rec.setId(com.supporter.util.UUIDHex.newId());
	// rec.setExternalId(externalDrawings.getExternalId());
	// rec.setIsFor(0);
	// contentDao.save(rec);
	// //提交操作的时候将1类和2类的图纸更新为审完状态
	// /*if(flag.equals("1")) {
	// updateDrawingCheckStatus(flag, rec);
	// }*/
	// }
	// }
	// }
	// if (StringUtils.isNotBlank(delContentIds)) {
	// String[] idArray = delContentIds.split(",");
	// for (String delId : idArray) {
	// if (delId == null) {
	// continue;
	// }
	// ExternalDrawingsContent rec = contentDao.get(delId);
	// if(rec!=null)
	// contentDao.delete(delId);
	// }
	// }
	// }

	// /**
	// * 同步附件
	// * @param moduleName
	// * @param busiType
	// * @param oneLevelId
	// * @param twoLevelId
	// * @param newOneId
	// * @param newTwoId
	// * @param user
	// */
	// public int syncFile(String moduleName,String busiType,String
	// oneLevelId,String twoLevelId,String newOneId,String newTwoId,String
	// newType,UserProfile user) {
	// List<FileUpload> list = fileUploadService.getList(moduleName, busiType,
	// oneLevelId, twoLevelId);
	// int count = 0 ;
	// if(list!=null&&list.size()>0) {
	// for (FileUpload fileUpload : list) {
	// if (fileUpload == null) {
	// continue;
	// }
	// FileUpload file = new FileUpload();
	// try {
	// BeanUtils.copyProperties(file, fileUpload);
	// file.setCreatedDate(fileUpload.getCreatedDate());
	// file.setEndDate(fileUpload.getEndDate());
	// file.setUpdateDate(fileUpload.getUpdateDate());
	// file.setFileId(com.supporter.util.UUIDHex.newId());
	// file.setOneLevelId(newOneId);
	// file.setTwoLevelId(newTwoId);
	// file.setBusiType(newType);
	// file.setFileSize(fileUpload.getFileSize());
	// fileUploadService.save(file);
	// File f =fileUploadService.getFile(fileUpload.getFileId());
	// fileUploadService.saveFile(f, moduleName, newType, user, file.getFileId());
	// count++;
	//
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	// return count;
	// }
	
	// private void saveOrUpdateContentTwoList(UserProfile user,ExternalDrawings
	// externalDrawings, String delContentTwoIds, String flag) {
	// List<ExternalDrawingsContent> list = externalDrawings.getContentTwoList();
	// if (list != null) {
	// for (ExternalDrawingsContent rec : list) {
	// if (rec == null) {
	// continue;
	// }
	// String recId = rec.getId();
	// if (StringUtils.isNotBlank(recId) && !(recId.indexOf("newId_")!=-1)) {
	// rec.setExternalId(externalDrawings.getExternalId());
	// rec.setIsFor(1);
	// contentDao.clear();
	// contentDao.update(rec);
	// //提交操作的时候将1类和2类的图纸更新为审完状态
	// if(flag.equals("1")) {
	// updateDrawingCheckStatus(flag, rec);
	// }
	// } else {
	// //根据图纸ID判断是否是初稿文件，如果是初稿则升级版本
	// DrawingLibraryVersion version = this.versionService.get(rec.getDrawingId());
	//
	// //如果版本号是空的 则升级。
	// if(StringUtils.isBlank(version.getVersionNo())||version.getVersionNo().equals("初稿")||version.getVersionNo().equals("null")||version.getVersionNo().equals("")){
	// //①初始化版本
	// DrawingLibraryVersion newVersion =
	// this.drawingLibraryService.initEditOrViewPageVersion("",version.getLibraryId(),user);
	// //保存
	// newVersion.setCheckStatus(version.getCheckStatus());
	// this.versionService.save(newVersion);
	// //②拷贝附件到新的版本
	// int res =
	// syncFile(DrawingLibrary.APP_NAME,DrawingLibrary.FILE_NAME,version.getVersionId(),"",newVersion.getVersionId(),"",DrawingLibrary.FILE_NAME,user);
	// //如果拷贝附件的结果是0条的话，去复制初稿文件
	// if(res==0){
	// res =
	// syncFile(DrawingLibrary.APP_NAME,DrawingLibrary.FILE_NAME_First,"",version.getVersionId(),newVersion.getVersionId(),"",DrawingLibrary.FILE_NAME,user);
	// }
	// //升级主表的版本号加图纸编号
	// DrawingLibrary library =
	// this.drawingLibraryService.get(newVersion.getLibraryId());
	// //library.setDrawingNo(newVersion.getDrawingNo());
	// if (StringUtils.isBlank(newVersion.getVersionNo())){
	// newVersion.setVersionNo("");
	// }
	// if (StringUtils.isBlank(newVersion.getVersionRule())){
	// newVersion.setVersionRule("");
	// }
	// library.setDrawingVersion((newVersion.getVersionRule()+newVersion.getVersionNo()).equals("")?"初稿":newVersion.getVersionRule()+newVersion.getVersionNo());
	// this.drawingLibraryService.saveOrUpdate(user,library);
	//
	// rec.setDrawingId(newVersion.getVersionId());
	// rec.setDrawingName(newVersion.getDrawingName());
	// rec.setDrawingNo(newVersion.getDrawingNo());
	// }
	// //更新初稿状态
	// version.setCheckStatus(DrawingLibraryVersion.AUDIT_OUT);
	// this.versionService.update(version);
	// rec.setId(com.supporter.util.UUIDHex.newId());
	// rec.setExternalId(externalDrawings.getExternalId());
	// rec.setIsFor(1);
	// contentDao.save(rec);
	// //提交操作的时候将1类和2类的图纸更新为审完状态
	// if(flag.equals("1")) {
	// updateDrawingCheckStatus(flag, rec);
	// }
	// }
	// }
	// }
	// if (StringUtils.isNotBlank(delContentTwoIds)) {
	// String[] idArray = delContentTwoIds.split(",");
	// for (String delId : idArray) {
	// if (delId == null) {
	// continue;
	// }
	// ExternalDrawingsContent rec = contentDao.get(delId);
	// if(rec!=null)
	// contentDao.delete(delId);
	// }
	// }
	// }
	
	// //更新图纸为审完状态
	// public void updateDrawingCheckStatus(String flag,ExternalDrawingsContent rec)
	// {
	// if(rec.getDrawingType().equals(ExternalDrawingsContent.TYPE_ONE) ||
	// rec.getDrawingType().equals(ExternalDrawingsContent.TYPE_TWO)) {
	// DrawingLibraryVersion version = versionService.get(rec.getDrawingId());
	// if(version != null) {
	// //0：未审，1：在审，2审完
	// version.setCheckStatus(2);
	// version.setCheckDate(new Date());//审完日期
	// versionService.update(version);
	// }
	// }
	// }
	
	// /**
	// * 直接生效
	// */
	// public ExternalDrawings valid(UserProfile user, ExternalDrawings
	// externalDrawings) {
	// ExternalDrawings ret = null;
	// boolean isNew = externalDrawings.getIsNew();
	// if(isNew){
	// if(externalDrawings.getExternalId()!= null &&
	// !externalDrawings.getExternalId().equals("")) {
	// externalDrawings.setStatus(ExternalDrawings.StatusCodeTable.EFFECT);
	// this.dao.save(externalDrawings);
	// ret = externalDrawings;
	// }else {
	// return null;
	// }
	// } else {// 编辑
	// externalDrawings.setStatus(ExternalDrawings.StatusCodeTable.EFFECT);
	// this.dao.update(externalDrawings);
	// ret = externalDrawings;
	// }
	// saveOrUpdateContentList(user, externalDrawings,
	// externalDrawings.getDelContentIds(),"1");//非初次外审
	// saveOrUpdateContentTwoList(user, externalDrawings,
	// externalDrawings.getDelContentTwoIds(),"1");//初次外审
	// return externalDrawings;
	// }
	//
	// /**
	// * 失效
	// */
	// public void invalid(UserProfile user, String externalIds) {
	// if (StringUtils.isNotBlank(externalIds)) {
	// String[] idArray = externalIds.split(",");
	// for (String id : idArray) {
	// if (id == null) {
	// continue;
	// }
	// ExternalDrawings externalDrawings = dao.get(id);
	// if (externalDrawings == null) {
	// continue;
	// }
	// externalDrawings.setStatus(ExternalDrawings.StatusCodeTable.EFFECT);
	// this.saveOrUpdate(user,externalDrawings);
	// }
	// }
	// }
	//
	// /**
	// * 删除
	// * @param user 用户信息
	// * @param problemIds 主键集合，多个以逗号分隔
	// */
	// public void delete(UserProfile user, String externalIds) {
	// if (StringUtils.isNotBlank(externalIds)) {
	// String[] idArray = externalIds.split(",");
	// for (String id : idArray) {
	// if (id == null) {
	// continue;
	// }
	// ExternalDrawings externalDrawings = dao.get(id);
	// //当前登录用户，应用编号，权限项编号，主键，对象
	// AuthUtils.canExecute(user,ExternalDrawings.APP_NAME,
	// AuthConstant.AUTH_OPER_NAME_EDIT, id, externalDrawings);
	// dao.delete(externalDrawings);
	//
	// //删除content从表
	// List<ExternalDrawingsContent> contendList =
	// contentService.getContentList(id);
	// if(contendList != null && contendList.size() > 0){
	// for (ExternalDrawingsContent rec : contendList) {
	// if (rec == null) {
	// continue;
	// }
	// contentDao.delete(rec);
	// }
	// }
	// }
	// }
	// }
	
	public ExternalDrawings updateRewinding(UserProfile user, String externalId) {
		ExternalDrawings externalDrawings = this.dao.get(externalId);
		dao.update(externalDrawings);
		return externalDrawings;
	}
	
	public List<ExternalDrawingsContent> getRecList(String externalId){
		return contentDao.getContentList(externalId);
	}
	
	public ExternalDrawings getExternalDrawingsById(String externalId) {
		return dao.get(externalId);
	}


	public void update(ExternalDrawings entity) {
		this.dao.update(entity);
	}

	// /**
	// * 更改图纸状态
	// * @param externalId
	// */
	// public void updateDrawingStatus(String externalId,int status) {
	// int status2 = status;
	// //获取审核图纸列表
	// List<ExternalDrawingsContent> contendList =
	// contentService.getContentList(externalId);
	// if(contendList != null && contendList.size() > 0){
	// //循环列表
	// for (ExternalDrawingsContent rec : contendList) {
	// if (rec == null) {
	// continue;
	// }
	// status2 = status;
	// boolean bo = false;//是否需要重置状态
	// //如果要更新审核完成状态
	// if(status==DrawingLibraryVersion.COMPLETE_OUT){
	// if(!(ExternalDrawingsContent.TYPE_ONE.equals(rec.getDrawingType())||ExternalDrawingsContent.TYPE_TWO.equals(rec.getDrawingType()))){
	// //如果外审未通过 改状态
	// status2 = DrawingLibraryVersion.NO_COMPLETE_OUT;
	// }
	// //更新返回日期
	// //rec.setBackDate(new Date());
	// }
	// //如果是流程启动的话
	// if(status==DrawingLibraryVersion.AUDIT_OUT){
	// //更新提交日期
	// //rec.setExternalDate(new Date());
	// }
	// if(status==DrawingLibraryVersion.ABORT){
	// bo = true;
	// }
	// if(bo){
	// //如果是不I 或2 类的话重置为原来的状态，首先查询是否是需要内审，需要内审则重置为内审完成，否则置为草稿
	// DrawingLibraryVersion version = versionService.get(rec.getDrawingId());
	// DrawingLibrary library = drawingLibraryService.get(version.getLibraryId());
	// if(DrawingLibrary.StatusCodeTable2.ISYES==library.getIsIn()){
	// status2 = DrawingLibraryVersion.COMPLETE_IN;
	// }else{
	// status2 = DrawingLibraryVersion.DRAFT;
	// }
	// }
	// //更新从表
	// this.contentDao.update(rec);
	// drawingLibraryService.changeDrawingStatus(rec.getDrawingId(),status2,new
	// Date());
	// }
	// }
	// }
	//
	// /**
	// * 通知页面加载信息
	// * @param externalId
	// * @param user
	// * @return
	// */
	// public Map initNoticeViewPage(String externalId, UserProfile user) {
	// Map map = new HashMap<String,Object>();
	// if(StringUtils.isNotBlank(externalId)){
	// map.put("externalId",externalId);
	// //获取外审实体
	// ExternalDrawings entity = this.dao.get(externalId);
	// //获取设计方名称，首先获取图纸实例，再根据图纸实例查图纸库主表
	// String drawingId = entity.getExternalId();
	// map.put("externalNo",entity.getExternalNo());
	// map.put("createdDept",entity.getCreatedDept());
	// map.put("progressStatus",entity.getProgressStatus());
	// }
	// return map;
	// }

	// /**
	// * 验证必须要有图纸文件
	// * @param externalId
	// * @param userProfile
	// * @return
	// */
	// public String validDrawingFiles(String externalId, UserProfile userProfile) {
	// //获取会审图纸列表
	// List<ExternalDrawingsContent> contents = this.getRecList(externalId);
	// StringBuffer errorMessage = new StringBuffer("");
	// if(contents!=null&&contents.size()>0){
	// String drawingIds = "";
	// for (ExternalDrawingsContent content:contents) {
	// if (content == null) {
	// continue;
	// }
	// if(content.getDrawingId().indexOf(drawingIds)!=-1){
	// continue;
	// }
	// //查询这个图纸的附件
	// List<FileUpload> fileUploadList =
	// fileUploadService.getList(DrawingLibrary.APP_NAME,DrawingLibrary.FILE_NAME,content.getDrawingId(),"");
	// if(fileUploadList==null||fileUploadList.size()==0){
	// errorMessage.append( content.getDrawingName()).append("缺少附件！\r");
	// break;
	// }
	// drawingIds+=content.getDrawingId()+",";
	// }
	// }
	// return errorMessage.toString();
	// }

	// /**
	// * 接受外审审核完成通知并且处理
	// * @param externalId
	// * @param userProfile
	// * @return
	// */
	// public String receiveAndManage(String externalId,Integer status, UserProfile
	// userProfile) {
	// String feedbackId = "";
	// ExternalDrawings externalDrawings = getExternalDrawingsById(externalId);
	// //如果设计研选择处理的话.
	// if(status==ExternalDrawings.ProgressStatusCodeTable.PROGRESSED){
	// //升级审批未通过的图纸
	// List<ExternalDrawingsContent> contents = getRecList(externalId);
	// int count = 0 ;//计算不通过的个数
	// for (ExternalDrawingsContent content:contents) {
	// if (content == null) {
	// continue;
	// }
	// if(!(ExternalDrawingsContent.TYPE_ONE.equals(content.getDrawingType())||ExternalDrawingsContent.TYPE_ONE.equals(content.getDrawingType()))){
	// DrawingLibraryVersion drawingLibraryVersion =
	// this.versionService.get(content.getDrawingId());
	// DrawingLibraryVersion newDraing =
	// this.drawingLibraryService.initEditOrViewPageVersion("",
	// drawingLibraryVersion.getLibraryId(),userProfile);
	// //如果不是初稿的话 获取初稿版本号
	// String olddrawingNo = drawingLibraryVersion.getDrawingNo();
	// if(StringUtils.isNotBlank(drawingLibraryVersion.getVersionNo())||!(drawingLibraryVersion.getVersionNo().equals("初稿")||drawingLibraryVersion.getVersionNo().equals("null")||drawingLibraryVersion.getVersionNo().equals(""))){
	// List<DrawingLibraryVersion> versongs = this.versionService.find(" from
	// DrawingLibraryVersion where libraryId = ? ",
	// drawingLibraryVersion.getLibraryId());
	// if(versongs!=null&&versongs.size()>0){
	// for (DrawingLibraryVersion drawingLibraryVersion2 : versongs) {
	// if (drawingLibraryVersion2 == null) {
	// continue;
	// }
	// if(StringUtils.isBlank(drawingLibraryVersion2.getVersionNo())||drawingLibraryVersion2.getVersionNo().equals("初稿")||drawingLibraryVersion2.getVersionNo().equals("null")||drawingLibraryVersion2.getVersionNo().equals("")){
	// olddrawingNo =drawingLibraryVersion2.getDrawingNo();
	// break;
	// }
	// }
	// }
	// }
	// //重新赋值状态(升级版本 反馈)
	// newDraing.setCheckStatus(DrawingLibraryVersion.UPGRAGE);
	// //图纸名称
	// newDraing.setDrawingName(drawingLibraryVersion.getDrawingName());
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
	// this.versionService.save(newDraing);
	// //更改图纸状态
	// //升级主表
	// DrawingLibrary library =
	// this.drawingLibraryService.get(newDraing.getLibraryId());
	// if (StringUtils.isBlank(newDraing.getVersionNo())){
	// newDraing.setVersionNo("");
	// }
	// if (StringUtils.isBlank(newDraing.getVersionRule())){
	// newDraing.setVersionRule("");
	// }
	// library.setDrawingVersion((newDraing.getVersionRule()+newDraing.getVersionNo()).equals("")?"初稿":newDraing.getVersionRule()+newDraing.getVersionNo());
	// //library.setDrawingNo(newDraing.getDrawingNo());
	// //library.setStatus(newDraing.getCheckStatus());
	// this.drawingLibraryService.saveOrUpdate(userProfile,library);
	// count++;
	// }
	// }
	// //如果存在不通过的图纸。去新建一个图纸反馈.
	// if(count>0){
	// HospitaReview review =
	// reviewService.initEditOrViewPage("",userProfile,externalId,HospitaReview.AUDIT_STATUS_OUT);
	// //将保存的反馈记录ID返回
	// feedbackId = review.getFeedbackId();
	// }
	// }else if(status==ExternalDrawings.ProgressStatusCodeTable.UNPROGRESSED){
	// //给图纸管理员发代办
	// if(externalDrawings.getIsSend()==0){
	// sendMessage(externalDrawings);
	// }
	// }
	// //更改通知状态
	// externalDrawings.setProgressStatus(status);
	// update(externalDrawings);
	// return feedbackId;
	// }

	// /**
	// * 发知会给图纸管理员
	// * @param interrorgate
	// */
	// public void sendMessage (ExternalDrawings external ){
	// //给设计院发知会
	// Message msgToDesign = EIPService.getBMSService().newMessage();
	// String roleIdDes = "Drawing_Manager";//资料员角色编号
	// String empNameKeyWordDes = ""; //为空检索出所有的账号人员
	// List <Person> personsDes =
	// EIPService.getRoleService().getPersonFromRole(roleIdDes, empNameKeyWordDes);
	// if (personsDes != null && personsDes.size() > 0) {
	// for(int i=0;i<personsDes.size();i++){
	// msgToDesign.setPersonId(personsDes.get(i).getPersonId());
	// msgToDesign.setCreatedDate(new Date());
	// msgToDesign.setEventTitle("图纸外会审通知：" + external.getExternalNo() + " " +
	// external.getModifiedDate()+"。设计院已收到！");
	// msgToDesign.setHasReceived(false);
	// msgToDesign.setModuleId(ExternalDrawings.APP_NAME);
	// msgToDesign.setWebappName(EIPService.getWebappService().getWebappName());
	// msgToDesign.setWebPageURL("/pm/external_drawings/externalDrawings_adjust_swf.html?externalId="+external.getExternalId()+"&isCc=true");
	// //发送待办
	// EIPService.getBMSService().addMessage(msgToDesign);
	// }
	// }
	// }
	//
	// /**
	// * 加载反馈选择外审控件
	// * @param jqGrid
	// * @param externalDrawings
	// * @param user
	// * @param requestParameters
	// * @param keyword
	// */
	// public List<ExternalDrawings> getWidgetGrid(JqGrid jqGrid, ExternalDrawings
	// externalDrawings, UserProfile user, Map<String, Object> requestParameters,
	// String keyword) {
	// return dao.findPageToWidget(jqGrid,
	// externalDrawings,user,requestParameters,keyword);
	// }
	//
	// //根据主键获取实体对象
	// public ExternalDrawings get(String externalId) {
	// return dao.get(externalId);
	// }

}
