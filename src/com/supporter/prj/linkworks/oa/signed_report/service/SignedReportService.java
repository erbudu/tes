package com.supporter.prj.linkworks.oa.signed_report.service;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.account.entity.AccountEntity;
import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileManageService;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.role.service.RoleService;
import com.supporter.prj.eip.swf.runtime.dao.WfTaskPfmResultDao;
import com.supporter.prj.eip.swf.runtime.entity.WfExam;
import com.supporter.prj.eip.swf.runtime.entity.WfTaskPfmResult;
import com.supporter.prj.eip.swf.runtime.service.WfExamService;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.account.entity.Account;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.emp.entity.IEmployee;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.role.entity.Role;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.ExtractFiles;
import com.supporter.prj.linkworks.oa.history_exam_record.entity.VCneecExam;
import com.supporter.prj.linkworks.oa.history_exam_record.service.VCneecExamService;
import com.supporter.prj.linkworks.oa.signed_report.constants.SignedReportAuthConstant;
import com.supporter.prj.linkworks.oa.signed_report.dao.SignedReportContentDao;
import com.supporter.prj.linkworks.oa.signed_report.dao.SignedReportDao;
import com.supporter.prj.linkworks.oa.signed_report.dao.SignedReportExamFileDao;
import com.supporter.prj.linkworks.oa.signed_report.dao.SignedReportMessageBoardDao;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReport;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReportContent;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReportExamFile;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReportMessageBoard;
import com.supporter.prj.linkworks.oa.signed_report.util.ConvertUtils;
import com.supporter.prj.linkworks.oa.signed_report.util.SignedReportAuthUtil;
import com.supporter.util.CommonUtil;

import bsh.StringUtil;

/**
 * @Title: Service
 * @Description: 功能模块表
 * @author qiyuanbin
 * 
 */
@Service
@Transactional(TransManager.APP)
public class SignedReportService {
	@Autowired
	private SignedReportDao signedReportDao;
	@Autowired
	private SignedReportContentDao signedReportContentDao;
	@Autowired
	private SignedReportMessageBoardDao boardDao;
	@Autowired
	private SignedReportExamFileDao examFileDao;
	@Autowired
	private WfExamService wfExamService;
	@Autowired
	private WfTaskPfmResultDao wfEPfmResultDao;
	@Autowired
	private VCneecExamService vcneecExamService; 
//	private static final String MODULE_VALUE_COL_NAME = "propertyValue";
	@Autowired
	private ExtractFiles extractFiles;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private FileManageService fileManageService;
	@Autowired
	private RoleService roleService;
	/**
	 * 根据主键获取功能模块表.
	 * 
	 * @param moduleId
	 *            主键
	 * @return Contract
	 */
	public SignedReport get(String signedReportId) {
		SignedReport signedReport = this.signedReportDao.get(signedReportId);
		return signedReport;
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param moduleId
	 * @return
	 */
	public SignedReport initEditOrViewPage(String signedReportId, int isAgreement, UserProfile user) {
		SignedReport signedReport = new SignedReport();
		
		//获取留言板信息
		List<SignedReportMessageBoard> messageBoard = this.boardDao.getMessageBySignedReportId(signedReportId);
		
		
		if (StringUtils.isBlank(signedReportId)) {// 新建
			
			signedReport.setOperatorName(user.getName());
			signedReport.setOperatorId(CommonUtil.trim(user.getPersonId()));
			signedReport.setOperatorDate(CommonUtil.format(new Date(),"yyyy-MM-dd"));
			signedReport.setSignedReportId(com.supporter.util.UUIDHex.newId());
			signedReport.setSwfStatus(SignedReport.DRAFT);
			if(user.getDept() != null){
				signedReport.setDeptName(user.getDept().getName());
	        }
			signedReport.setDeptId(CommonUtil.trim(user.getDeptId()));
//			//部门负责人
//			IEmployee emp = EIPService.getEmpAssignService().getChiefEmployee(signedReport.getDeptId());
			//1获取部门正职这个角色 
			Role role=EIPService.getRoleService().getRole("DEPTLEADER");
			//2获取创建人所在的部门
			Dept dept=user.getDept();
			Person person=null;
			List<Person> persons=EIPService.getRoleService().getPersonsForDept(role, dept);
			if(persons!=null&&persons.size()>0){
				person=persons.get(0);
			}
			if(person!=null){
				signedReport.setDeptLeaderId(person.getPersonId());
				signedReport.setDeptLeaderName(person.getName());
			}
			signedReport.setCreatedById(user.getPersonId());
			signedReport.setCreatedBy(user.getName());
			signedReport.setCreatedDate(CommonUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
			signedReport.setModifiedBy(user.getName());
			signedReport.setModifiedById(user.getPersonId());
			signedReport.setModifiedDate(CommonUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
			signedReport.setType("1");//默认是普通签报
			signedReport.setIsAgreement(isAgreement);
			signedReport.setMessageBoardList(messageBoard);
			return signedReport;
		} else {// 编辑
			signedReport = signedReportDao.get(signedReportId);
			
			SignedReportContent content = this.signedReportContentDao.get(signedReportId);
			if (content == null) {
				content = new SignedReportContent();
			}
			signedReport.setSignedReportContent(content);
			signedReport.setMessageBoardList(messageBoard);			
			return signedReport;
		}
		
	}
	
	/**
     * 获得公司领导.
     * @return
     */
    private List < String > getLeaders(){
    	String empNameKeyWord = "";
    	List < Person > leaders = EIPService.getRoleService().getPersonFromRole("SIGNREPOT_LEADER_EXAM", empNameKeyWord);
    	//公司高管
//    	List < Person > leaders =  EIPService.getEmpService().getEmps(EIPService.getDeptService().getDept("1000000091"));
    	List < String > list = new ArrayList < String >();
    	for (Person person : leaders) {
    		if (person == null)continue;
    		list.add(CommonUtil.trim(person.getPersonId()));
		}
    	return list;
    }
    /**
     * 获得部门领导.
     * @return
     */
    private List < String > getDeptLeaders(){
    	String empNameKeyWord = "";
    	List < Person > leaders = EIPService.getRoleService().getPersonFromRole("DEPTLEADER", empNameKeyWord);
    	List < String > list = new ArrayList < String >();                       
    	for (Person person : leaders) {
    		if (person == null)continue;
    		list.add(CommonUtil.trim(person.getPersonId()));
		}
    	return list;
    }
	
	/**
	 * 获取签报审批意见.
	 * @param signedReportId
	 * @return
	 */
	public Map < String, List < Map < String, Object > > > getExamList(String signedReportId){
		SignedReport rpt = this.get(signedReportId);
		if(rpt.getHistory()){
			return getHistoryExamList(signedReportId);
		}
		boolean orderByTimeAsc = true;
		List < WfExam > recs = wfExamService.getHisExamList(rpt.getProcId(), orderByTimeAsc);
		if (recs == null || recs.size() == 0)return null;
		
		List < Map < String, Object > > deptLeaders = new ArrayList< Map < String, Object > >();//部门领导意见
    	List < Map < String, Object > > leaders = new ArrayList< Map < String, Object > >();//领导审批意见
    	List < Map < String, Object > > signers = new ArrayList< Map < String, Object > >();//会签人意见
    	List < String > leaderList = getLeaders();
//    	List < String > deptList = getDeptLeaders();
    	//部门领导ID
    	String deptLeader = CommonUtil.trim(rpt.getDeptLeaderId());
		
		int size = recs.size();
		for (int i = 0; i < size; i++){
			WfExam rec = recs.get(i);
			String empId = CommonUtil.trim(rec.getEmpId());
			
			Map < String, Object > map = beanToMap(rec);
			Person person = EIPService.getEmpService().getEmp(empId);
			List < Account > accounts = EIPService.getAccountService().getAccounts(person);
			String electronicSignature = "";
			if (accounts != null && accounts.size() > 0){
				for (int j = 0; j < accounts.size(); j++){
					AccountEntity account = (AccountEntity)accounts.get(j);
					electronicSignature = CommonUtil.trim(account.getElectronicSignature());
					if (electronicSignature.length() > 0)break;
				}
			}
			map.put("electronicSignature", electronicSignature);
			String examId=map.get("recordId").toString();
			
			//根据审批实例获取该审批人对应的授权人ID
			WfTaskPfmResult result = wfEPfmResultDao.get(rec.getRecordId());
			String consignerId = result != null ? CommonUtil.trim(result.getConsignerId()) : "";
			if(consignerId.equals("")){//如果授权人ID为空，则按审批人划分map
				if(leaderList.contains(empId)){
	    			map.put("ProFiles", "<div class=\"supp-view-group  w100p\" id=\""+empId+examId+"\" style=\"margin-top: 5px; text-align: left; font-size: 12px;\"></div>");
					leaders.add(map);
	    		} else if(deptLeader.contains(empId)){
	    			deptLeaders.add(map);
	    		} else {
	    			map.put("ProFiles", "<div class=\"supp-view-group  w100p\" id=\""+empId+examId+"\" style=\"margin-top: 5px; text-align: left; font-size: 12px;\"></div>");
	    			signers.add(map);
	    		}
			}else{//否则按授权人ID划分map
				if(leaderList.contains(consignerId)){
	    			map.put("ProFiles", "<div class=\"supp-view-group  w100p\" id=\""+empId+examId+"\" style=\"margin-top: 5px; text-align: left; font-size: 12px;\"></div>");
					leaders.add(map);
	    		} else if(deptLeader.contains(consignerId)){
	    			deptLeaders.add(map);
	    		} else {
	    			map.put("ProFiles", "<div class=\"supp-view-group  w100p\" id=\""+empId+examId+"\" style=\"margin-top: 5px; text-align: left; font-size: 12px;\"></div>");
	    			signers.add(map);
	    		}
			}
		}
		Map < String, List < Map < String, Object > > > map = new HashMap < String, List < Map < String, Object > > >();
		map.put("deptLeaders", deptLeaders);
		map.put("leaders", leaders);
		map.put("signers", signers);
		
		return map;
	}
	
	/**
	 * 获取签报审批意见.
	 * @param signedReportId
	 * @return
	 */
	public Map < String, List < Map < String, Object > > > getHistoryExamList(String signedReportId){
		SignedReport rpt = this.get(signedReportId);
		boolean orderByTimeAsc = true;
		List < VCneecExam > recs = vcneecExamService.getHisExamList("oa_signed_report", "signed_report_id",CommonUtil.parseInt(signedReportId,0),true);
		if (recs == null || recs.size() == 0)return null;
		
		List < Map < String, Object > > deptLeaders = new ArrayList< Map < String, Object > >();//部门领导意见
    	List < Map < String, Object > > leaders = new ArrayList< Map < String, Object > >();//领导审批意见
    	List < Map < String, Object > > signers = new ArrayList< Map < String, Object > >();//会签人意见
    	List < String > leaderList = getLeaders();
    	//List < String > deptList = getDeptLeaders();
    	//部门领导ID
    	String deptLeader = CommonUtil.trim(rpt.getDeptLeaderId());
		
		int size = recs.size();
		for (int i = 0; i < size; i++){
			VCneecExam rec = recs.get(i);
			String empId = rec.getEmpId()+"";
			
			Map < String, Object > map = beanToMap(rec);
			Person person = EIPService.getEmpService().getEmp(empId);
			List < Account > accounts = EIPService.getAccountService().getAccounts(person);
			String electronicSignature = "";
			if (accounts != null && accounts.size() > 0){
				for (int j = 0; j < accounts.size(); j++){
					AccountEntity account = (AccountEntity)accounts.get(j);
					electronicSignature = CommonUtil.trim(account.getElectronicSignature());
					if (electronicSignature.length() > 0)break;
				}
			}
			map.put("electronicSignature", electronicSignature);
			
			if(leaderList.contains(empId)){
				leaders.add(map);
    		} else if(deptLeader.contains(empId)){
    			deptLeaders.add(map);
    		} else {
    			signers.add(map);
    		}
		}
		Map < String, List < Map < String, Object > > > map = new HashMap < String, List < Map < String, Object > > >();
		map.put("deptLeaders", deptLeaders);
		map.put("leaders", leaders);
		map.put("signers", signers);
		
		return map;
	}
	
	/**
	 * 验证是否可以删除签报
	 * @param signedReportId
	 * @param user
	 * @return
	 */
	public boolean verificationIsCanDelete(String signedReportId, UserProfile user){
		if (verificationOtherDeptExam(signedReportId)){//如果存在其他部门审批意见
			if (user.getPersonId().equals("1")){//如果是系统管理员
				return true;
			}else{//判断是否在信息中心管理员角色中
				List<Person> persons = this.roleService.getPersonFromRole("INFORMATIONCENTERADMINISTRATOR", user.getName());
				if (persons.size() > 0 || persons != null){
					String userId = user.getPersonId();
					for (Person p : persons){
						if (userId.equals(p.getPersonId())){
							return true;
						}
					}
				}
			}
		}else{
			return true;
		}
		return false;
	}
	
	/**
	 * 验证是否有其他部门审批意见
	 * @param signedReportId
	 * @return
	 */
	public boolean verificationOtherDeptExam(String signedReportId){
		SignedReport sr = this.get(signedReportId);
		List<WfExam> exams = wfExamService.getHisExamList(sr.getProcId(), true);
		String srDeptId = sr.getDeptId();
		if (exams == null || exams.size() == 0)return false;
		for (int i = 0; i < exams.size(); i++){
			WfExam exam = exams.get(i);
			Person person = EIPService.getEmpService().getEmp(exam.getEmpId());
			if (!srDeptId.equals(person.getDeptId())){
				return true;
			}
		}
		return false;
	}
	
	
    /**
	 * 将javabean实体类转为map类型，然后返回一个map类型的值.
	 * @param obj
	 * @return
	 */
    private Map < String, Object > beanToMap(Object obj) { 
    	Map < String, Object > params = new HashMap < String, Object >(0); 
        try { 
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
            for (int i = 0; i < descriptors.length; i++) { 
                String name = descriptors[i].getName(); 
                if (!"class".equals(name)) { 
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name)); 
                } 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return params; 
    }
	
	public List<SignedReportContent> getContetById(UserProfile user, String signedReportId) {
		List<SignedReportContent> list = this.signedReportContentDao.getContentBySignedReportId(signedReportId);
		return list;
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user
	 *            用户信息
	 * @param jqGridReq
	 *            jqgrid请求对象
	 * @param moduleIds
	 *            多个逗号分隔
	 * @return JqGrid
	 */
	public List<SignedReport> getGrid(UserProfile user, JqGrid jqGrid,String reason, String swfStatus) {
		return signedReportDao.findPage(user,jqGrid, reason , swfStatus);
	}

	/**
	 * 保存或更新
	 * 
	 * @param user
	 *            用户信息
	 * @param module
	 *            实体类
	 * @return
	 */
	public SignedReport saveOrUpdate(UserProfile user,SignedReport signedReport, Map<String, Object> valueMap) {
		SignedReport ret = signedReportDao.get(signedReport.getSignedReportId());;
		
		//获取上传文件名称
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OASIGNREPORT", "SignedReportFiles", signedReport.getSignedReportId());
		for(IFile file:list){
				sb.append(file.getFileName() +",");
		}
		if(list !=null && list.size()>0){
			sb.deleteCharAt(sb.length() - 1);
		}
		signedReport.setOtherFile(sb.toString());
		
		if (ret == null) {// 新建
			this.signedReportDao.save(signedReport);
			SignedReportContent content = new SignedReportContent();
			content.setSignedReportContent(signedReport.getSignedReportContent().getSignedReportContent());
			content.setSignedReportId(signedReport.getSignedReportId());
			signedReport.setModifiedById(user.getPersonId());
			signedReport.setModifiedBy(user.getName());
			signedReport.setModifiedDate(CommonUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
			signedReport.setActresult(0);
			this.signedReportContentDao.save(content);
			return signedReport;
			// EIPService.getLogService("MPM_RPM_PENA_APPLY").info(user,
			// QualityProblem.LogOper.PROBLEM_ADD.getOperName(),
			// "{AddQualityProblem : " + qualityProblem + "}", null, null);
		} else {// 编辑
			SignedReportContent content = signedReportContentDao.get(signedReport.getSignedReportId());
			//保存正文
			if (content != null) {
				content.setSignedReportContent(signedReport.getSignedReportContent().getSignedReportContent());
				this.signedReportContentDao.update(content);
			}else{
				content = new SignedReportContent();
				content.setSignedReportId(signedReport.getSignedReportId());
				content.setSignedReportContent(signedReport.getSignedReportContent().getSignedReportContent());
				this.signedReportContentDao.save(content);
			}
			ret.setPrjId(signedReport.getPrjId());
			ret.setPrjName(signedReport.getPrjName());
			ret.setType(signedReport.getType());
			ret.setReason(signedReport.getReason());
			ret.setOperatorTel(signedReport.getOperatorTel());
			ret.setModifiedById(user.getPersonId());
			ret.setModifiedBy(user.getName());
			ret.setModifiedDate(CommonUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
			ret.setOtherFile(signedReport.getOtherFile());
			
			ret.setSignedDeptIds(signedReport.getSignedDeptIds());
			ret.setSignedDeptNames(signedReport.getSignedDeptNames());
			ret.setDeptSignerIds(signedReport.getDeptSignerIds());
			ret.setDeptSignerNames(signedReport.getDeptSignerNames());
			ret.setAddDeptSignerIds(signedReport.getAddDeptSignerIds());
			ret.setAddDeptSignerNames(signedReport.getAddDeptSignerNames());
			
			ret.setIsAgreement(signedReport.getIsAgreement());
			ret.setIsNeedAgreementNo(signedReport.getIsNeedAgreementNo());
			ret.setNewAgreementType(signedReport.getNewAgreementType());
			ret.setAgreementNo(signedReport.getAgreementNo());
			ret.setFirstAgreementId(signedReport.getFirstAgreementId());
			ret.setAgreementNoChSource(signedReport.getAgreementNoChSource());
			ret.setPrjType(signedReport.getPrjType());
						
			
			this.signedReportDao.update(ret);
			// EIPService.getLogService("MPM_RPM_PENA_APPLY").info(user,
			// QualityProblem.LogOper.PROBLEM_ADD.getOperName(),
			// "{EditQualityProblem : " + qualityProblem + "}", null, null);
			return ret;
		}

	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param moduleId
	 * @param moduleName
	 * @return
	 */
	public boolean checkNameIsValid(String moduleId, String moduleName) {
		return this.signedReportDao.checkNameIsValid(moduleId, moduleName);
	}

	/**
	 * 删除
	 * 
	 * @param user
	 *            用户信息
	 * @param moduleIds
	 *            主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String signedReportIds) {
		if (StringUtils.isNotBlank(signedReportIds)) {
			for (String signedReportId : signedReportIds.split(",")) {
				SignedReport signedReport = this.get(signedReportId);
				SignedReportAuthUtil.canExecute(user, SignedReportAuthConstant.AUTH_OPER_NAME_SETVALSETVALSIGNREPORT, signedReportId, signedReport);
				SignedReportContent content = signedReportContentDao
						.get(signedReportId);
				if (signedReport == null) {
					continue;
				}
				if (content != null) {
					signedReportContentDao.delete(content);
				}
				this.signedReportDao.delete(signedReport);
			}
			// 日志
			// EIPService.getLogService("MPM_RPM_PENA_APPLY").info(user,
			// QualityProblem.LogOper.PROBLEM_DEL.getOperName(),
			// "{delQualityProblem_ids : " + problemIds + "}", null, null);
		}
	}
	
	/**
	 * 重置流程状态
	 * 
	 */
	public void updateStatus(String signedReportId,Integer swfStatus) {
		if (StringUtils.isNotBlank(signedReportId)) {
			SignedReport signedReport = this.get(signedReportId);
			signedReport.setSwfStatus(swfStatus);
			/*if(StringUtils.isNotBlank(signedReport.getProcId())&& signedReport.getSwfStatus()!= SignedReport.PROCESSING){
				EIPService.getWfService().abortProc(signedReport.getProcId());
			}*/
			this.signedReportDao.update(signedReport);
		}
	}
	
	/**
	 * 详情页获取可下载的文件
	 * @param newsAlertsId
	 * @return
	 */
	public String getFiles(String signedReportId){
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OASIGNREPORT", "SignedReportFiles", signedReportId);
		for(IFile file:list){
			//可下载的附件
			sb.append("<a href=\"javaScript:javascript:downloadFile('"+ file.getFileId() +"');\" >" + file.getFileName() +"</a><br>");
		}
		return sb.toString();
	}
	
	/**
	 * 获取扫描件可下载的文件
	 * @param signedReportId
	 * @return
	 */
	public String initScanningCopy(String signedReportId){
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OASIGNREPORT", "scanningCopy", signedReportId);
		for(IFile file:list){
			//可下载的附件
			sb.append("<a href=\"javaScript:javascript:downloads('"+ file.getFileId() +"');\" >" + file.getFileName() +"</a>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					//"<a href=\"javaScript:javascript:deleteFiles('"+ file.getFileId() +"');\" >删除</a>"+"<br/>");
		}
		return sb.toString();
	}

	/**
	 * 上传扫描件
	 * @param signedReportId
	 */
	public void uploadFiles(String signedReportId,String isSweepAttachment) {
		SignedReport report = this.signedReportDao.get(signedReportId);
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OASIGNREPORT", "scanningCopy", signedReportId);
		for(IFile file:list){
				sb.append(file.getFileName() +",");
		}
		if(list !=null && list.size()>0){
			sb.deleteCharAt(sb.length() - 1);
		}
//		int last = sb.toString().lastIndexOf(",");	
//		if(isSweepAttachment!=null&&isSweepAttachment.equals("1")){//列表点击上传扫描附件时执行下面的代码
//			String reportFile = sb.toString().substring(last + 1, sb.toString().length());
//			report.setReportFile(reportFile);
//		}		
		report.setReportFile(sb.toString());
		this.signedReportDao.update(report);
	}
	
	/**
	 * 保存留言板
	 * @param content
	 * @param signedReportId
	 * @return
	 */
	public SignedReportMessageBoard saveMessageBoard(String content,String signedReportId,UserProfile user){
		SignedReportMessageBoard board = new SignedReportMessageBoard();
		board.setContent(content);
		board.setReportId(signedReportId);
		board.setMessDate(ConvertUtils.dateDetailString());
		board.setPersonName(user.getName());
		board.setPersonId(user.getPersonId());
		board.setDeptId(user.getDeptId());
		board.setBoardId(com.supporter.util.UUIDHex.newId());
		Dept dept = user.getDept();
		if(dept !=null){
			board.setDeptName(dept.getName());
		}
		this.boardDao.save(board);
		return board;
	}
	
	public void update(SignedReport signedReport){
		this.signedReportDao.update(signedReport);
	}
	
	
	
	

	/**
	 * 保存会签人,包含部门名称和id
	 * @param signedReportId
	 * @param deptSignerIds
	 */
	public void saveDeptSigner(String signedReportId,String signedType,UserProfile user) {
		SignedReport report = this.signedReportDao.get(signedReportId);
		Person person = EIPService.getEmpService().getEmp(user.getPersonId());
		Dept dept = person.getDept();
		String deptId = dept.getDeptId();
		String deptName=dept.getName();
		if(report != null){
			if(signedType.trim().equals(SignedReport.DEPTSIGNER)){//部门会签人
				if(StringUtils.isNotBlank(report.getDeptSignerIds())){
					if(report.getDeptSignerIds().indexOf(user.getPersonId())==-1){
						report.setDeptSignerIds(report.getDeptSignerIds() +"," +user.getPersonId());
						report.setDeptSignerNames(report.getDeptSignerNames() + "," + user.getName());
					}
				}else{
					report.setDeptSignerIds(user.getPersonId());
					report.setDeptSignerNames(user.getName());
				}
				
			}
			
			if(signedType.trim().equals(SignedReport.LEADERSIGNER)){//分管领导会签人
				if(StringUtils.isNotBlank(report.getLeaderSignerIds())){
					if(report.getLeaderSignerIds().indexOf(user.getPersonId())==-1){
					report.setLeaderSignerIds(report.getLeaderSignerIds() +"," +user.getPersonId());
					report.setLeaderSignerNames(report.getLeaderSignerNames() + "," + user.getName());
					}
				}else{
					report.setLeaderSignerIds(user.getPersonId());
					report.setLeaderSignerNames(user.getName());
				}
				
			}
			
			if(signedType.trim().equals(SignedReport.PRESIDENTSIGNER)){//总裁、总裁秘书会签人
				if(StringUtils.isNotBlank(report.getPresidentSecratryAddId())){
					if(report.getPresidentSecratryAddId().indexOf(user.getPersonId())==-1){
						report.setPresidentSecratryAddId(report.getPresidentSecratryAddId() +"," +user.getPersonId());
						report.setPresidentSecratryAdd(report.getPresidentSecratryAdd() + "," + user.getName());
					}
				}else{
					report.setPresidentSecratryAddId(user.getPersonId());
					report.setPresidentSecratryAdd(user.getName());
				}
				
			}
			
			//保存会签部门
//			if(!ifSignerDeptExist(report,deptId)){
//				if(StringUtils.isBlank(report.getSignedDeptIds())){
//					report.setSignedDeptIds(deptId);
//					if(user.getDept() != null){
//						report.setSignedDeptNames(user.getDept().getName());
//			        }
//				}else{
//					report.setSignedDeptIds(report.getSignedDeptIds() + "," + deptId);
//					if(user.getDept() != null){
//						report.setSignedDeptNames(report.getSignedDeptNames() + "," +user.getDept().getName());
//			        }
//				}
//			}
			if(StringUtils.isBlank(report.getSignedDeptIds())){
				report.setSignedDeptIds(deptId);
				report.setSignedDeptNames(deptName);

			}else{
				if(report.getSignedDeptIds().indexOf(deptId) == -1){
					report.setSignedDeptIds(report.getSignedDeptIds() + "," + deptId);
					report.setSignedDeptNames(report.getSignedDeptNames()+"," +deptName);

				}
			}
			this.signedReportDao.update(report);
		}
		
	}
	
	/**
	 * 部门会签人部门是否已存在
	 * @param report
	 * @param personId
	 * @return 
	 * 存在返回true,不存在返回false
	 */
	public boolean ifSignerDeptExist(SignedReport report,String deptId){
		String signerDept = report.getSignedDeptIds();
		if(StringUtils.isNotBlank(signerDept)){
			for(String id : signerDept.split(",")){
				if(signerDept.contains(id)){
					return true;
				}
			}
			
		}
		return false;
	}
	/**
	 * 根据taskInstanceId获取examId
	 */
	public String getExamId(String taskInstanceId) {
		String  examId = this.signedReportDao.getExamId(taskInstanceId);
		return examId;
	}
	
	/**
	 * 根据附近Id修改附件的二级名称
	 */
	public void updateFileByFileId(String fileId,String twoLevelId) {
		//System.out.println("fileId==="+fileId+"twoLevelId==="+twoLevelId);
		FileUpload fileUpload =fileUploadService.get(fileId);
		if(fileUpload!=null){
			fileUpload.setTwoLevelId(twoLevelId);
		}
		fileUploadService.update(fileUpload); 
	}
	
	/**
	 * 获取上传的文件
	 * @param moduleName
	 * @param busiType
	 * @param oneLevelId
	 * @param twoLevelId
	 * @return
	 */
    public List<FileUpload> getFileUploadList(String moduleName, String busiType, String oneLevelId,String twoLevelId)
    {
        List<FileUpload> list = signedReportDao.getFileUploadList(moduleName, busiType, oneLevelId,twoLevelId);
        List<FileUpload> newList = new ArrayList<FileUpload>();
        FileUpload fileUpload;
        for(Iterator<FileUpload> iterator = list.iterator(); iterator.hasNext(); newList.add(fileUpload))
        {
            fileUpload = (FileUpload)iterator.next();
            fileUpload.setShowImageUrl((new StringBuilder(String.valueOf(fileManageService.getServicePath()))).append("/service/eip/FileUpload/showImage?fileId=").append(fileUpload.getFileId()).toString());
            fileUpload.setFilePictureUrl((new StringBuilder(String.valueOf(fileManageService.getServicePath()))).append("/eip/file_upload/img/").append(fileUpload.getFilePicture()).toString());
        }

        return newList;
    }
	


	/**
	 * 获取相关部门会签人的部门名称
	 * @param signedReportId
	 * @return
	 */
	public String getDeptNameByIds(String signedReportId) {
		StringBuffer deptName = new StringBuffer();
		SignedReport report = this.signedReportDao.get(signedReportId);
/*		if(report!=null&&report.getDeptSignerIds()!=null){
			for(String empNO : report.getDeptSignerIds().split(",")){
				IEmployee emp = EIPService.getEmpService().getEmployee(empNO);
				if(emp != null){
					if(emp.getDept() != null){
						if(report.getSignedDeptNames() == null ){
							if(deptName.indexOf(emp.getDept().getName())== -1){
								deptName.append(emp.getDept().getName());
								deptName.append(",");
							}
						}else{
							if(report.getSignedDeptNames().indexOf(emp.getDept().getName())== -1 && deptName.indexOf(emp.getDept().getName())== -1){
								deptName.append(emp.getDept().getName());
								deptName.append(",");
							}
						}
							
					}
				}
			}
		}

		if(deptName.length()>0){
			deptName.deleteCharAt(deptName.length() - 1);
		}*/
		if(report!=null){
			if(report.getSignedDeptNames()==null){
				deptName.append("");
			}else{
				deptName.append(report.getSignedDeptNames());
			}
			
		}
		return deptName.toString();
	}
	
	/**
	 * 获取部门原有会签人
	 * @param signedReportId
	 * @return
	 */
	public String getDeptSigners(String signedReportId) {
		StringBuffer signerName = new StringBuffer();
		SignedReport report = this.signedReportDao.get(signedReportId);

		if(report!=null){
			if(report.getDeptSignerNames()==null){
				signerName.append("");
			}else{
				signerName.append(report.getDeptSignerNames());
			}
			
		}
		return signerName.toString();
	}
	
	
	/**
	 * 获取原有会签部门
	 * @param signedReportId
	 * @return
	 */
	public String getDeptSignersDeptNames(String signedReportId) {
		StringBuffer signersDeptName = new StringBuffer();
		SignedReport report = this.signedReportDao.get(signedReportId);

		if(report!=null){
			if(report.getSignedDeptNames()==null){
				signersDeptName.append("");
			}else{
				signersDeptName.append(report.getSignedDeptNames());
			}
			
		}
		return signersDeptName.toString();
	}
	
	
	
	
	/**
	 * 根据新增会签人获取其所在的部门名称
	 * @param addDeptSignerIds
	 * @return
	 */
	public String getDeptNameByAddDeptSignerIds(String addDeptSignerIds) {
		String deptName="";
		String[] deptSignerIds=addDeptSignerIds.split(",");	
		int i=1;
		for(String deptSignerId:deptSignerIds){
			Person person = EIPService.getEmpService().getEmp(deptSignerId);
			if(person!=null){
				Dept dept = person.getDept();		
				if(dept!=null){
					if(i!=deptSignerIds.length){
						deptName+=dept.getName()+",";
					}else{
						deptName+=dept.getName();
					}									
				}
			}
			i++;
		}
		
		return deptName;
	}
	
	
	/**
	 * 保存保存拟稿人修改的正文
	 * @param signedReportId
	 * @param content
	 */
	public void saveContentById(String signedReportId, String content) {
		SignedReportContent reportContent = this.signedReportContentDao.get(signedReportId);
		if(reportContent != null){
			reportContent.setSignedReportContent(content);
			this.signedReportContentDao.update(reportContent);
		}
		
	}
	

	/**
	 * 新编号和签字时间,取出后直接更新到数据库
	 * @param signedReportId
	 * @return
	 */
	public String generateNo(String signedReportId){
		SignedReport report = this.get(signedReportId);
		if(StringUtils.isNotBlank(report.getSignNo())){
			return report.getSignNo();
		}
		String newNumber = "";
		String lastNo = "" + this.signedReportDao.getLastedNo();
		if(lastNo.equals("")){
			newNumber = "签"+"00001"+"号（"+Calendar.getInstance().get(Calendar.YEAR)+"）";
		}else{
			String lastedNo = lastNo;
			String number = lastedNo.substring(1,6);
			newNumber = "签"+ConvertUtils.numberToString(Integer.parseInt(number)+1, 5)+"号"+"（"+Calendar.getInstance().get(Calendar.YEAR)+"）";
		}
		report.setSignNo(newNumber);
    	report.setSignedDate(ConvertUtils.dateString());
    	update(report);
    	return newNumber;
	}
	
	/**
	 * 验证编号是否可用
	 * @param signNo
	 * @param verificationNo
	 * @return
	 */
	public boolean verificationNo(String signNo, String signedReportId){
		return this.signedReportDao.verificationNo(CommonUtil.trim(signNo), CommonUtil.trim(signedReportId));
	}
	
	/**
	 * 获取留言板
	 * @param signedReportId
	 * @return
	 */
	public String getMessageBoard(String signedReportId) {
		StringBuffer sb = new StringBuffer();
		List<SignedReportMessageBoard> list = this.boardDao.getMessageBySignedReportId(signedReportId);
		if(list!=null&&list.size()>0){
			for(SignedReportMessageBoard board :list){					
				if(StringUtils.isNotBlank(board.getContent())){
					sb.append("<div style=\"border-top: 1px solid rgb(0, 0, 0);font-size: 13px\">");
					sb.append(board.getContent());
					sb.append("</div>");
					sb.append("<div style=\"height: 15px;font-size: 11px; font-weight: bold\" align=\"right\">");
					sb.append(board.getPersonName()+"</div>");
					sb.append("<div style=\"height: 15px;font-size: 11px; font-weight: bold\" align=\"right\">");
					sb.append(board.getMessDate()+ "</div>");
				}		
			}
		}
		return sb.toString();
	}
	
	/**
	 * 获取留言板
	 * @param signedReportId
	 * @return
	 */
	public String getMessageBoardForIphone(String signedReportId) {
		StringBuffer sb = new StringBuffer();
		List<SignedReportMessageBoard> list = this.boardDao.getMessageBySignedReportId(signedReportId);
		if(list!=null&&list.size()>0){
			for(SignedReportMessageBoard board :list){					
				if(StringUtils.isNotBlank(board.getContent())){
					sb.append("<div style=\"border-top: 1px solid rgb(0, 0, 0);font-size: 32px;text-align:left;padding-left:10px\">");
					sb.append(board.getContent()+"</div>");
					sb.append("<div style=\"height: 30px;font-size: 25px; font-weight: bold;text-align:right;padding-right:60px;padding-bottom:10px\" align=\"right\">");
					sb.append(board.getPersonName()+"</div>");
					sb.append("<div style=\"height: 30px;font-size: 25px; font-weight: bold\" align=\"right\">");
					sb.append(board.getMessDate()+ "</div>");
				}		
			}
		}
		return sb.toString();
	}


	/**
	 * 秘书处保存相关审批信息
	 * @param signedReportId
	 * @param secratryId
	 * @param user
	 */
	public void saveSecretariat(String signedReportId, String secratryId,UserProfile user) {
		SignedReport report = this.get(signedReportId);
		IEmployee emp = EIPService.getEmpService().getEmployee(secratryId);
		if(emp != null){
			report.setSecratryName(emp.getName());
		}
		report.setSecratryId(secratryId);
		report.setSignedDate(ConvertUtils.dateString());
		this.signedReportDao.update(report);
		
	}

	/**
	 * 秘书保存审批领导
	 * @param signedReportId
	 * @param leadersId
	 * @param leadersName
	 */
	public void saveleaders(String signedReportId, String leadersId,String leadersName) {
		SignedReport report = this.get(signedReportId);
		report.setLeadersId(leadersId);
		report.setLeadersName(leadersName);
		update(report);
	}

	
	/**
	 * 保存分管领导会签人
	 * @param signedReportId
	 * @param leaderSignerNames
	 * @param leaderSignerIds
	 */
	public void saveLeadersSigner(String signedReportId,String leaderSignerNames, String leaderSignerIds) {
		SignedReport report = this.get(signedReportId);
		report.setLeaderSignerIds(leaderSignerIds);
		report.setLeaderSignerNames(leaderSignerNames);
		update(report);
	}

	/**
	 * 总裁秘书保存会签人
	 * @param signedReportId
	 * @param presidentSecratryAddId
	 * @param presidentSecratryAdd
	 */
	public void savepresidentSecratryAdd(String signedReportId,String presidentSecratryAddId, String presidentSecratryAdd) {
		SignedReport report = this.get(signedReportId);
		report.setPresidentSecratryAddId(presidentSecratryAddId);
		report.setPresidentSecratryAdd(presidentSecratryAdd);
		update(report);
	}
	
	/**
	 * 保存部门经理审批页面的审批人
	 * @param signedReportId
	 * @param user
	 */
	public void saveDeptLeader(String signedReportId, UserProfile user) {
		SignedReport report = this.get(signedReportId);
//		report.setDeptLeaderId(user.getPersonId());
//		report.setDeptLeaderName(user.getName());
		report.setDeptLeaderSigneId(user.getPersonId());
		report.setDeptLeaderSigneDate(CommonUtil.format(new Date(), "yyyy-MM-dd"));		
		this.signedReportDao.update(report);
	}
	
	
	/**
	 * 保存审批附件
	 * @param user
	 * @param signedReportId
	 */
	public void saveExamFile(UserProfile user,String signedReportId){
		String fileIds = examFileDao.getFileIdsList(user, signedReportId);
	    IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OASIGNREPORT", "ProFiles", signedReportId);
		if(list !=null && list.size()>0){
			for(IFile file : list){
				if(StringUtils.isNotBlank(fileIds)){
					if(fileIds.indexOf(file.getFileId()) == -1){
						saveFiles(user,signedReportId,file);
					}
				}else{
					saveFiles(user,signedReportId,file);
				}
				
			}
		}
	}
	
	public void saveFiles(UserProfile user,String signedReportId,IFile file){
		SignedReportExamFile examFile = new SignedReportExamFile();
		examFile.setFileId(com.supporter.util.UUIDHex.newId());
		examFile.setCreatedBy(user.getName());
		examFile.setCreatedById(user.getPersonId());
		examFile.setCreatedDate(ConvertUtils.dateDetailString());
		examFile.setModifiedBy(user.getName());
		examFile.setModifiedById(user.getPersonId());
		examFile.setModifiedDate(ConvertUtils.dateDetailString());
		examFile.setRelatedEntityId(signedReportId);
		examFile.setRelatedEntityName(SignedReport.class.getName());
		examFile.setFileId(file.getFileId());
		examFile.setFileName(file.getFileName());
		examFileDao.save(examFile);
	}

	
	/**
	 * 单一提取文件.
	 * @param report
	 * @return
	 */
	public String extractFiles(String signedReportId, UserProfile userProfile){
		SignedReport entity =  this.get(signedReportId);
		String str=extraExamFiles(signedReportId,userProfile);
		return  str+extractFiles.extractFiles(entity.getSignedReportId(), entity.getOtherFile(),
				"/oa/signed_report/attachment/", "OASIGNREPORT", "SignedReportFiles", userProfile);
	}
	
	public String extraExamFiles(String signedReportId, UserProfile userProfile){
		String str="";
		String hql="from "+SignedReportExamFile.class.getName()+" where relatedEntityId= ? ";
		List<SignedReportExamFile> files = examFileDao.find(hql, signedReportId);
		for (SignedReportExamFile signedReportExamFile : files) {
			str+=extractFiles.extractDianZiQianMingFiles(signedReportExamFile.getFileId(), signedReportExamFile.getFileName(),
					"/oa/signed_report_exam_file/file/", "OASIGNREPORT", "ProFiles", userProfile);
		}
		return str;
	}
	/**
	 * 批量提取文件.
	 * @param report
	 * @return
	 */
	public String batchExtractFiles(UserProfile userProfile){
		String returnStr = "success";
		List <SignedReport> list= signedReportDao.getSignedReportList();
		for(SignedReport en:list){
			returnStr+=extraExamFiles(en.getSignedReportId(),userProfile);
			returnStr += extractFiles.extractFiles(en.getSignedReportId(), en.getOtherFile(),
					"/oa/signed_report/attachment/", "OASIGNREPORT", "SignedReportFiles", userProfile);
			
			// 记录日志
/*			String logMessage = MessageFormat.format(
					LogConstant.EXTRACT_FILES_LOG_MESSAGE+":"+returnStr, CommonUtil.trim(report.getReportTitle()));
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					userProfile, LogConstant.EXTRACT_FILES_LOG_ACTION, logMessage,
					report, null);*/
		}
		return returnStr;
		//return reportDao.batchExtractFiles(userProfile);
	}
	
	
	/**
	 * 批量提取扫描件
	 * @param userProfile
	 * @return
	 */
	public String batchExtractScanningCopy(UserProfile userProfile){
		System.out.println("开始批量提取扫描件。。。");
		String returnStr = "success";
		List <SignedReport> list = signedReportDao.getSignedReportList();
		for(SignedReport en : list){
			String signedReportId = en.getSignedReportId();
			if (!signedReportId.contains("1000") && signedReportId.length() > 10) continue;
			
			String str = extractFiles.extractFilesForSign(signedReportId, en.getReportFile(),
					"/oa/signed_report/attachment/", "OASIGNREPORT", "scanningCopy", "A", userProfile);
			
			System.out.println(en.getSignNo() + "结果:" + str);
			returnStr += str;
		}
		System.out.println("扫描件提取全部完成！！！");
		return returnStr;
	}
	
	
	
	
	/**
	 * 验证是不是公司领导
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public String checkIsLeaders(String addDeptSignerIds) {
		String isSatisfied="yes";	
		List < String > leaderList = getLeaders();
		if(addDeptSignerIds.indexOf(",")==-1){
			if(leaderList.contains(addDeptSignerIds)){
				isSatisfied="no";
			}
		}else{
			for (String addDeptSignerId : addDeptSignerIds.split(",")) {
				if(leaderList.contains(addDeptSignerId)){
					isSatisfied="no";
					break;
				}
			}
		}
		
		return isSatisfied;
	}
	
	/**
	 * 判断当前登录人有没有权限查看上传的扫描件
	 * @return String (yes/no)
	 */
	public String isShowOfScanningCopy(UserProfile user, String createdById){
		String isShow="no";
		List < String > persons = getCanShowScanningCopy();
		String personId=CommonUtil.trim(user.getPersonId());
		if(persons.contains(personId) || CommonUtil.trim(createdById).equals(personId)){
			isShow="yes";
		}
		return isShow;
	}
	
	/**
     * 获得可以查看签报上传附件的人员.
     * @return
     */
    private List < String > getCanShowScanningCopy(){
    	String empNameKeyWord = "";
    	List < Person > persons = EIPService.getRoleService().getPersonFromRole("SIGNREPOT_SHOW_SCANNINGCOPY", empNameKeyWord);
       	List < String > list = new ArrayList < String >();
    	for (Person person : persons) {
    		if (person == null)continue;
    		list.add(CommonUtil.trim(person.getPersonId()));
		}
    	return list;
    }
    
    /**
     * 获取部门内最大协议编号集合（不包含当前编号）
     * @param signedReportId
     * @param deptId
     * @return
     */
    public Map<String, String> getMaxAgreementNos(String signedReportId, String deptId){
    	Map<String, String> map = new LinkedHashMap<String, String>();
    	if (StringUtils.isNotBlank(signedReportId) && StringUtils.isNotBlank(deptId)) {
    		List<Object[]> agrementNos = this.signedReportDao.getMaxAgreementNos(signedReportId, deptId);
    		if (agrementNos != null) {
    			for (Object[] obj : agrementNos) {
    				map.put(obj[0].toString(), obj[1].toString());
    			}
    		}
    	}
    	return map;
    }
    
    /**
     * 获取新的佣金代理协议编号
     * @param signedReport
     * @return
     */
    public String getNewAgreementNo(SignedReport signedReport) {
    	String agreementNo = "";
    	if (signedReport.getNewAgreementType() == SignedReport.IS_NEW_AGREEMENT) {//新签协议
    		String year = CommonUtil.format(new Date(), "yyyy");
    		String deptNo = signedReport.getDeptNo();
    		String newMaxAgreementNo = this.signedReportDao.getNewMaxAgreementNo();
    		String newIndexNo = "01";
    		if (StringUtils.isNotBlank(newMaxAgreementNo)) {
    			int indexNo = Integer.parseInt(newMaxAgreementNo.substring(newMaxAgreementNo.indexOf("B")+1,newMaxAgreementNo.indexOf("e")));
    			newIndexNo = String.format("%02d", indexNo+1);
    		}
    		agreementNo = "CNEEC" + year + deptNo + "B" + newIndexNo + "e00";
    	}else if (signedReport.getNewAgreementType() == SignedReport.CHANGE_AGREEMENT){//变更协议
    		String agreementNoChSource = signedReport.getAgreementNoChSource();
    		if (StringUtils.isNotBlank(agreementNoChSource)) {
    			String[] str = agreementNoChSource.split("e");
    			int chIndexNo = Integer.parseInt(str[1]);
    			String newChIndexNo = String.format("%02d", chIndexNo + 1);
    			agreementNo = str[0] + "e" + newChIndexNo;
    		}
    	}
    	return agreementNo;
    }
	
	
}
