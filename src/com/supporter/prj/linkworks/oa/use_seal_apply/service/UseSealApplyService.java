package com.supporter.prj.linkworks.oa.use_seal_apply.service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.ExtractFiles;
import com.supporter.prj.linkworks.oa.use_seal_apply.dao.UseSealApplyBoardDao;
import com.supporter.prj.linkworks.oa.use_seal_apply.dao.UseSealApplyContentDao;
import com.supporter.prj.linkworks.oa.use_seal_apply.dao.UseSealApplyDao;
import com.supporter.prj.linkworks.oa.use_seal_apply.dao.VUseSealApplyDao;
import com.supporter.prj.linkworks.oa.use_seal_apply.entity.UseSealApply;
import com.supporter.prj.linkworks.oa.use_seal_apply.entity.UseSealApplyBoard;
import com.supporter.prj.linkworks.oa.use_seal_apply.entity.UseSealApplyContent;
import com.supporter.prj.linkworks.oa.use_seal_apply.entity.VUseSealApply;
import com.supporter.prj.linkworks.oa.use_seal_apply.util.AuthConstant;
import com.supporter.prj.linkworks.oa.use_seal_apply.util.AuthUtil;
import com.supporter.prj.linkworks.oa.use_seal_apply.util.LogConstant;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;

@Service
public class UseSealApplyService {
	@Autowired
	private UseSealApplyDao useSealApplyDao;
	@Autowired
	private UseSealApplyBoardDao useSealApplyBoardDao;	
	@Autowired
	private UseSealApplyContentDao useSealApplyContentDao;
	@Autowired
	private VUseSealApplyDao vUseSealApplyDao;
	
	@Autowired
	private ExtractFiles extractFiles;


	public UseSealApply get(String moduleId) {
		return useSealApplyDao.get(moduleId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public UseSealApply initEditOrViewPage(JqGrid jqGrid,String applyId, UserProfile user) {
		if (StringUtils.isBlank(applyId)) {// 新建
			UseSealApply useSealApply = newUseSealApply(user);
			useSealApply.setDeptAndApplyPersonDesc(useSealApply.getApplyDept()+":"+useSealApply.getCreatedBy());
			useSealApply.setAdd(true);
			return useSealApply;
		} else {// 编辑
			//获得主表
			UseSealApply useSealApply =  useSealApplyDao.get(applyId);
			//获得从表UseSealApplyContent
			UseSealApplyContent useSealApplyContent=useSealApplyContentDao.get(applyId);
			if(useSealApplyContent!=null){
				useSealApply.setUseSealApplyContent(useSealApplyContent);
			}
//			useSealApply.setLanguage(useSealApply.getType());
			useSealApply.setSealTypeIds(useSealApply.getSealTypeId());
			useSealApply.setBusinessPretrials(useSealApply.getBusinessPretrial());
			useSealApply.setStorageAndTransPretrials(useSealApply.getStorageAndTransPretrial());
			useSealApply.setNotifierIds(useSealApply.getRelatedIds());
			useSealApply.setNotifierNames(useSealApply.getRelatedNames());
			useSealApply.setExamIds(useSealApply.getCompanyLeaderId());
			useSealApply.setExamNames(useSealApply.getCompanyLeaderName());
			useSealApply.setUseSealReasonDesc(useSealApply.getUseSealApplyContent().getUseSealReason());
			useSealApply.setFiles(getFiles(useSealApply));
			useSealApply.setDeptAndApplyPersonDesc(useSealApply.getApplyDept()+":"+useSealApply.getCreatedBy());
//			//创建时间（相当于用印申请时间）
//			useSealApply.setCreatedDate(CommonUtil.format(useSealApply.getCreatedDate(),"yyyy-MM-dd"));	
			
/*			//获取用印上传附件
			IFileUploadService fileUploadService = EIPService.getFileUploadService();
			List<IFile> list  = fileUploadService.getFileList("OAUSAPPLY", "useSealApplyFile", applyId);
			if(list !=null && list.size()>0){
				//useSealApply.setFileId(list.get(0).getFileId());
				useSealApply.setDocumentName((list.get(0).getFileName()));
				
			}	*/		
			
			useSealApply.setAdd(false);		
			return useSealApply;
		}
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public UseSealApply initSignNo(JqGrid jqGrid,String applyId, UserProfile user) {
			UseSealApply useSealApply =  useSealApplyDao.get(applyId);
			useSealApply.setSignNo(this.getCurrentNo());
			//useSealApplyDao.update(useSealApply);
			return useSealApply;
	}
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public UseSealApply newUseSealApply(UserProfile auserprf_U){
    	UseSealApply luseSealApply_N = new UseSealApply();
    	luseSealApply_N.setApplyId(com.supporter.util.UUIDHex.newId());
    	luseSealApply_N.setCreatedById(auserprf_U.getPersonId());
    	luseSealApply_N.setCreatedBy(auserprf_U.getName()); 
    	String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
    	luseSealApply_N.setCreatedDate(date);
    	luseSealApply_N.setUseSealTime(date);
    	luseSealApply_N.setApplyDeptId(auserprf_U.getDeptId());
    	//因为管理员没有所在部门。所以需要判断一下
    	if(!auserprf_U.getPersonId().equals("1")){
    		luseSealApply_N.setApplyDept(auserprf_U.getDept().getName());
    	}else{
    	    luseSealApply_N.setApplyDept("管理员所在部门");
    	}
    	
    	//编号
		//luseSealApply_N.setSignNo(getCurrentNo());	
    	luseSealApply_N.setApplyStatus(Long.valueOf(UseSealApply.DRAFT+""));
        return luseSealApply_N;
    }
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<VUseSealApply> getGrid(UserProfile user, JqGrid jqGrid, VUseSealApply vUseSealApply) {	
		List<VUseSealApply> list=vUseSealApplyDao.getVUseSealApply(user, jqGrid, vUseSealApply);
		return list;	
		
	}
	
	
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public UseSealApply saveOrUpdate(UserProfile user, UseSealApply useSealApply, Map< String, Object > valueMap) {
		UseSealApply ret = null;
		
		//获取上传文件名称
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OAUSAPPLY", "useSealApplyFile", useSealApply.getApplyId());
		if(list !=null && list.size()>0){
			for(IFile file:list){
					sb.append(file.getFileName() +",");
			}
			if(sb!=null&&sb.length()>0){
				sb.deleteCharAt(sb.length() - 1);
			}			
		}
		useSealApply.setOtherFile(sb.toString());
		
		if (useSealApply.getAdd()) {// 新建
			
			//用印种类
			String sealType=UseSealApply.getCodeTable2().getDisplay(useSealApply.getSealTypeId());
			useSealApply.setSealType(sealType);
//			//预审方式
//			useSealApply.setBusinessPretrial(businessPretrial);
			//保存主表
			this.useSealApplyDao.save(useSealApply);
			//保存从表UseSealApplyboard
			//保存从表UseSealApplyContent
			UseSealApplyContent useSealApplyContent=new UseSealApplyContent();
			useSealApplyContent.setApplyId(useSealApply.getApplyId());
			useSealApplyContent.setUseSealReason(useSealApply.getUseSealReasonDesc());
			useSealApplyContent.setUseSealApplyContent(useSealApply.getUseSealApplyContent().getUseSealApplyContent());
			this.useSealApplyContentDao.save(useSealApplyContent);			
			ret = useSealApply;
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.ADD_USE_SEAL_APPLY_LOG_MESSAGE,useSealApply.getUseSealReasonDesc());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					user, LogConstant.ADD_USE_SEAL_APPLY_LOG_ACTION, logMessage,
					useSealApply, null);
		} else {// 编辑
			
			//权限验证(判断是不是有编辑用印的权限)
			AuthUtil.canExecute(user, AuthConstant.AUTH_OPER_NAME_AUTHUSESEALODBTN, useSealApply.getApplyId(), useSealApply);
			
			useSealApply.setModifiedBy(user.getName());
			useSealApply.setModifiedById(user.getPersonId());
		    String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			useSealApply.setModifiedDate(date);
			//用印种类
			String sealType=UseSealApply.getCodeTable2().getDisplay(useSealApply.getSealTypeId());
			useSealApply.setSealType(sealType);
			//编辑之后保存主表
			this.useSealApplyDao.update(useSealApply);
			//保存从表UseSealApplyContent
			UseSealApplyContent useSealApplyContent=this.useSealApplyContentDao.get(useSealApply.getApplyId());			
			useSealApplyContent.setUseSealReason(useSealApply.getUseSealReasonDesc());
			useSealApplyContent.setUseSealApplyContent(useSealApply.getUseSealApplyContent().getUseSealApplyContent());			
			this.useSealApplyContentDao.update(useSealApplyContent);
			ret = useSealApply;			
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.EDIT_USE_SEAL_APPLY_LOG_MESSAGE, useSealApply.getUseSealReasonDesc());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					user, LogConstant.EDIT_USE_SEAL_APPLY_LOG_ACTION, logMessage,
					useSealApply, null);		
		}
		return ret;

	}
	
	/**
     * 保存留言板意见的从表
     * @param num
     * @param size
     * @return
     */	
	public void saveBoard(UserProfile user, UseSealApply useSealApply){
		UseSealApplyBoard useSealApplyBoard=new UseSealApplyBoard();
		useSealApplyBoard.setBoardId(com.supporter.util.UUIDHex.newId());
		useSealApplyBoard.setReportId(useSealApply.getApplyId());
		useSealApplyBoard.setApplyDeptId(user.getDeptId());
		if(user.getDept()!=null){
			useSealApplyBoard.setApplyDept(user.getDept().getName());
		}else{
			useSealApplyBoard.setApplyDept("");
		}
		useSealApplyBoard.setApplyPersonId(user.getPersonId());
		useSealApplyBoard.setApplyPersonName(user.getName());
		useSealApplyBoard.setContent(useSealApply.getUseSealApplyBoardDesc());
	    String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
		useSealApplyBoard.setUseSealTime(date);
		this.useSealApplyBoardDao.save(useSealApplyBoard);	
	}
	
	/**
	 * 保存会签人
	 * @param 
	 * @param 
	 */
	public void saveSigner(String applyId,UserProfile user) {
		UseSealApply useSealApply = this.useSealApplyDao.get(applyId);
		if(useSealApply != null){
				if(StringUtils.isNotBlank(useSealApply.getRelatedIds())){
					if(useSealApply.getRelatedIds().indexOf(user.getPersonId())==-1){
						useSealApply.setRelatedIds(useSealApply.getRelatedIds() +"," +user.getPersonId());
						useSealApply.setRelatedNames(useSealApply.getRelatedNames() + "," + user.getName());
					}
				}
			}
			this.useSealApplyDao.update(useSealApply);
		}
		
	

	/**
	 * 获取留言板
	 * @param applyId
	 * @return
	 */
	public String getMessageBoard(String applyId) {
		StringBuffer sb = new StringBuffer();
		List<UseSealApplyBoard> list = this.useSealApplyBoardDao.getMessageByApplyId(applyId);
		if(list!=null&&list.size()>0){
			for(UseSealApplyBoard board :list){
				sb.append("<div style=\"border-top: 1px solid rgb(0, 0, 0);font-size: 13px\">");
				sb.append(board.getContent()+"</div>");
				sb.append("<div style=\"height: 15px;font-size: 11px; font-weight: bold\" align=\"right\">");
				sb.append(board.getApplyPersonName()+"</div>");
				sb.append("<div style=\"height: 15px;font-size: 11px; font-weight: bold\" align=\"right\">");
				sb.append(board.getUseSealTime()+ "</div>");
			}
		}
	
		return sb.toString();
	}
	
	/**
	 * 获取留言板
	 * @param applyId
	 * @return
	 */
	public String getMessageBoardForIphone(String applyId) {
		StringBuffer sb = new StringBuffer();
		List<UseSealApplyBoard> list = this.useSealApplyBoardDao.getMessageByApplyId(applyId);
		if(list!=null&&list.size()>0){
			for(UseSealApplyBoard board :list){
				sb.append("<div style=\"border-top: 1px solid rgb(0, 0, 0);font-size: 32px;text-align:left;padding-left:10px\">");
				sb.append(board.getContent()+"</div>");
				sb.append("<div style=\"height: 30px;font-size: 25px; font-weight: bold;text-align:right;padding-right:60px;padding-bottom:10px\" align=\"right\">");
				sb.append(board.getApplyPersonName()+"</div>");
				sb.append("<div style=\"height: 30px;font-size: 25px; font-weight: bold\" align=\"right\">");
				sb.append(board.getUseSealTime()+ "</div>");
			}
		}
	
		return sb.toString();
	}
	
	/**
     * 保存是否需要公司领导审批
     * @param num
     * @param size
     * @return
     */	
	public void saveOfNeedLeader(UseSealApply useSealApply){
		this.useSealApplyDao.update(useSealApply);
	}

		
	 public  String getCurrentNo(){
	 		String no=useSealApplyDao.getSignNo();
		    SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
			String year=sdf.format(new Date());
 		if(year.equals(no.substring(9,13))){
 			//2017-002
 			//印000012号（2017）
 			String nm = no.substring(1,7);
 			String number = String.valueOf((Integer.parseInt(nm)+1));
 			if(number.length()<nm.length()){
 				no = "";
 				for (int i = 0; i < nm.length()-number.length(); i++) {
						no += "0";
					}
 				no += number;
 			}else{
 				no = number;
 			}
 			no = "印"+no+"号（"+year+"）";
 		}else{
 			no = "印000001号（"+year+"）";
 		}
 	return no;
 }
	
	
    /**
	 * 保存提交
	 * 
	 * @param user 用户信息
	 * @param apply 实体类
	 * @return
	 */
	public UseSealApply commit(UserProfile user, UseSealApply useSealApply, Map< String, Object > valueMap) {
		UseSealApply ret = null;
		boolean isNew=useSealApply.getAdd();
		if(isNew){
			//编号
			useSealApply.setSignNo(getCurrentNo());	
			//用印种类
			String sealType=UseSealApply.getCodeTable2().getDisplay(useSealApply.getSealTypeId());
			useSealApply.setSealType(sealType);
//			//预审方式
//			useSealApply.setBusinessPretrial(businessPretrial);
			//状态（审批中）
			useSealApply.setApplyStatus(Long.valueOf(UseSealApply.PROCESSING+""));
			//保存主表
			this.useSealApplyDao.save(useSealApply);
			//保存从表UseSealApplyboard
			//保存从表UseSealApplyContent
			UseSealApplyContent useSealApplyContent=new UseSealApplyContent();
			useSealApplyContent.setApplyId(useSealApply.getApplyId());
			useSealApplyContent.setUseSealReason(useSealApply.getUseSealApplyContent().getUseSealReason());
			useSealApplyContent.setUseSealApplyContent(useSealApply.getUseSealApplyContent().getUseSealApplyContent());
			this.useSealApplyContentDao.save(useSealApplyContent);			
			//保存从表UseSealApplyPerson			
			ret = useSealApply;
			//日志
			//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{AddApplication : " + apply + "}", null, null);
		} else {// 编辑
			useSealApply.setModifiedBy(user.getName());
			useSealApply.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			useSealApply.setModifiedDate(date);
			//用印种类
			String sealType=UseSealApply.getCodeTable2().getDisplay(useSealApply.getSealTypeId());
			useSealApply.setSealType(sealType);
			//状态（审批中）
			useSealApply.setApplyStatus(Long.valueOf(UseSealApply.PROCESSING+""));
			//编辑之后保存主表
			this.useSealApplyDao.update(useSealApply);
			//保存从表UseSealApplyContent
			UseSealApplyContent useSealApplyContent=this.useSealApplyContentDao.get(useSealApply.getApplyId());			
			useSealApplyContent.setUseSealReason(useSealApply.getUseSealApplyContent().getUseSealReason());
			useSealApplyContent.setUseSealApplyContent(useSealApply.getUseSealApplyContent().getUseSealApplyContent());			
			this.useSealApplyContentDao.update(useSealApplyContent);
			ret = useSealApply;
			//日志
			//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{EditApplication : " + apply + "}", null, null);
		}
		return ret;
	}
	

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String applyIds) {
		if (StringUtils.isNotBlank(applyIds)) {
			for (String applyId : applyIds.split(",")) {
				UseSealApply useSealApply=useSealApplyDao.get(applyId);
				if(useSealApply==null){
					continue;
				}
				//权限验证(判断是不是有删除用印的权限)
				AuthUtil.canExecute(user, AuthConstant.AUTH_OPER_NAME_AUTHUSESEALODBTN, applyId, useSealApply);			

				//删除用印主表
				this.useSealApplyDao.delete(useSealApplyDao.get(applyId));
				//删除留言板信息
				deleteUseSealApplyBoard(applyId);
				//删除content
				UseSealApplyContent useSealApplyContent=useSealApplyContentDao.get(applyId);
				if(useSealApplyContent!=null&&!useSealApplyContent.equals("")){
					this.useSealApplyContentDao.delete(applyId);
				}			
			}
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.DELETE_USE_SEAL_APPLY_LOG_MESSAGE, "删除用印的主键为："+applyIds);
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					user, LogConstant.DELETE_USE_SEAL_APPLY_LOG_ACTION, logMessage,
					null, null);
		}
	}
	
	/**
	 * 删除从表数据(留言板从表)
	 * @param delIds
	 */
	public void deleteUseSealApplyBoard(String applyId) {
		//首先根据applyId获取与之关联的所有从表
		List<UseSealApplyBoard> list=this.useSealApplyBoardDao.getMessageByApplyId(applyId);
		if(list!=null&&list.size()>0){
			this.useSealApplyBoardDao.delete(list);
		}
	}
	
	/**
	 * 重置流程状态
	 * 
	 */
	public void updateStatus(String applyId ,Integer applyStatus) {
		if (StringUtils.isNotBlank(applyId)) {
			UseSealApply useSealApply = this.get(applyId);
			useSealApply.setApplyStatus(Long.valueOf(applyStatus));
			this.useSealApplyDao.update(useSealApply);
		}
	}
	
	
	
	
	/**
	 * 附件下载部分
	 * @param useSealApply
	 * @return
	 */
	public String getFiles( UseSealApply useSealApply){
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("USESEALAPPLY", "USESEALAPPLY2", useSealApply.getApplyId());
		for(IFile file:list){
			sb.append("<a onclick=\"downloads('"+ file.getFileId() +"');\">" + file.getFileName() +"</a><br/>");
		}
		return sb.toString();
	}
	
	
	
	
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public UseSealApply update(UseSealApply useSealApply) {
			this.useSealApplyDao.update(useSealApply);
			//记录日志
//			ModuleUtils.saveModuleOperateLog(user, module, Contract.LogOper.MODULE_EDIT.getOperName(), null);

		return useSealApply;

	}
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String extractFiles(String id, UserProfile userProfile){
		UseSealApply report =  this.get(id);
		return extractFiles.extractFiles(report.getApplyId(), report.getOtherFile(),
				"/oa/use_seal_apply/attachment/", "OAUSAPPLY", "useSealApplyFile", userProfile);
	}
	
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String batchExtractFiles(UserProfile userProfile){
		String returnStr = "success";
		List <UseSealApply> reportList= useSealApplyDao.getUseSealApplyList();
		for(UseSealApply report:reportList){
			returnStr = extractFiles.extractFiles(report.getApplyId(), report.getOtherFile(),
					"/oa/use_seal_apply/attachment/", "OAUSAPPLY", "useSealApplyFile", userProfile);
			
		/*	// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.EXTRACT_FILES_LOG_MESSAGE+":"+returnStr, CommonUtil.trim(report.getReportTitle()));
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					userProfile, LogConstant.EXTRACT_FILES_LOG_ACTION, logMessage,
					report, null);*/
		}
		return returnStr;
		//return reportDao.batchExtractFiles(userProfile);
	}
	
	
	/**
     * 获得公司领导.
     * @return
     */
    private List < String > getLeaders(){
    	String empNameKeyWord = "";
    	List < Person > leaders = EIPService.getRoleService().getPersonFromRole("SIGNREPOT_LEADER_EXAM", empNameKeyWord);
    	List < String > list = new ArrayList < String >();
    	for (Person person : leaders) {
    		if (person == null)continue;
    		list.add(CommonUtil.trim(person.getPersonId()));
		}
    	return list;
    }
	
	
	
	/**
	 * 验证是不是公司领导
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public String checkIsLeaders(String relatedIds) {
		String isSatisfied="yes";	
		List < String > leaderList = getLeaders();
		if(relatedIds.indexOf(",")==-1){
			if(leaderList.contains(relatedIds)){
				isSatisfied="no";
			}
		}else{
			for (String relatedId : relatedIds.split(",")) {
				if(leaderList.contains(relatedId)){
					isSatisfied="no";
					break;
				}
			}
		}
		
		return isSatisfied;
	}
	
}
