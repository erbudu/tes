package com.supporter.prj.linkworks.oa.invitation_f.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.ExtractFiles;
import com.supporter.prj.linkworks.oa.doc_in.entity.DocIn;
import com.supporter.prj.linkworks.oa.history_exam_record.service.VCneecExamService;
import com.supporter.prj.linkworks.oa.invitation_f.constants.InvitationAuthConstant;
import com.supporter.prj.linkworks.oa.invitation_f.dao.InvitationForeignerApplyDao;
import com.supporter.prj.linkworks.oa.invitation_f.dao.InvitationPersonsDao;
import com.supporter.prj.linkworks.oa.invitation_f.entity.InvitationForeignerApply;
import com.supporter.prj.linkworks.oa.invitation_f.entity.InvitationPersons;
import com.supporter.prj.linkworks.oa.invitation_f.util.AuthUtils;

@Service
public class InvitationForeignerApplyService {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private InvitationForeignerApplyDao codeDao;
	@Autowired
	private InvitationPersonsService personsService;
	@Autowired
	private InvitationPersonsDao personsDao;
	@Autowired
	private ExtractFiles extractFiles;
	@Autowired
	private VCneecExamService cneecExamService;
	private static final int RECORD_NO_COUNT = 10000; // 每年最大可能出现的收发文总数.

	/**
	 * 根据主键获取信息.
	 * 
	 * @param codeId
	 *            主键
	 * @return WmsInvitationForeignerApply
	 */
	public InvitationForeignerApply get(String codeId) {
		return codeDao.get(codeId);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param codeId
	 * @return
	 */
	public InvitationForeignerApply initEditOrViewPage(String codeId,
			String docClassify, UserProfile user) {
		if (StringUtils.isBlank(codeId)) {// 新建
			InvitationForeignerApply code = new InvitationForeignerApply();
			code.setInvitationId(com.supporter.util.UUIDHex.newId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			code.setCreatedDate(sdf.format(new Date()));
			code.setPlanInboundDate(sdf.format(new Date()));
			code.setAdd(true);
			//初始化流程状态
			code.setInvitationStatus(InvitationForeignerApply.DRAFT);
			if (user != null) {
				code.setCreatedById(user.getPersonId());
				code.setCreatedBy(user.getName());
				code.setApplyId(user.getPersonId());
				code.setApplyName(user.getName());
				Dept dept = user.getDept();
				if (dept != null) {
					code.setDeptId(user.getDeptId());
					code.setDeptName(dept.getName());
				} else {
					code.setDeptId("1");
					code.setDeptName("测试系统");
				}
			}
			return code;
		} else {// 编辑
			InvitationForeignerApply code = codeDao.get(codeId);
			code.setAdd(false);
			return code;
		}

	}
	/**
	 * 进入查看页面.
	 * @param moduleId
	 * @return
	 */
	public InvitationForeignerApply viewPage(String id, UserProfile user) {
		InvitationForeignerApply entity =  codeDao.get(id);
			//如果有旧系统的流程，则获取旧系统的procId
			long oldProcId = cneecExamService.getProcIdByRecord(entity);
			if (oldProcId > 0)entity.setOldProcId(oldProcId);
			return entity;
	}
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user
	 *            用户信息
	 * @param jqGridReq
	 *            jqgrid请求对象
	 * @param codeIds
	 *            多个逗号分隔
	 * @return JqGrid
	 */
	public List<InvitationForeignerApply> getGrid(UserProfile user,
			JqGrid jqGrid, InvitationForeignerApply code) {
		return codeDao.findPage(user,jqGrid, code);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param codeId
	 * @param materialName
	 * @return
	 */
	public boolean checkNameIsValid(String codeId, String materialName) {
		return this.codeDao.checkNameIsValid(codeId, materialName);
	}

	/**
	 * 删除
	 * 
	 * @param user
	 *            用户信息
	 * @param moduleIds
	 *            主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String docIds) {
		if (StringUtils.isNotBlank(docIds)) {
			for (String docId : docIds.split(",")) {
				InvitationForeignerApply codeDb = this
						.getWmsInvitationForeignerApplyFromBuffer(docId);
				if (codeDb == null) {
					continue;
				}
				this.recursiveDelete(user, codeDb);
			}
			// 记录日志
			/*
			 * InvitationForeignerApplyUtils.saveInvitationForeignerApplyOperateLog(user,
			 * null,
			 * InvitationForeignerApply.LogOper.InvitationForeignerApply_DEL.getOperName(),
			 * "{delInvitationForeignerApplyIds : " + codeIds + "}");
			 */
		}
	}

	/**
	 * 保存或更新
	 * 
	 * @param user
	 *            用户信息
	 * @param materialCode
	 *            实体类
	 * @return
	 */
	public InvitationForeignerApply saveOrUpdate(UserProfile user,
			InvitationForeignerApply code, Map<String, Object> valueMap) {
		InvitationForeignerApply ret = null;
		if (code.getAdd()) {// 新建
			this.codeDao.save(code);
			ret = code;
			// 记录日志
			/*
			 * MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
			 * MaterialCode.LogOper.MATERIALCODE_ADD.getOperName(), null);
			 */
		} else {// 编辑
			code.setModifiedBy(user.getPersonId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			code.setModifiedDate(sdf.format(new Date()));
			this.codeDao.update(code);
			ret = code;
			// 记录日志
			/*
			 * MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
			 * MaterialCode.LogOper.MATERIALCODE_EDIT.getOperName(), null);
			 */
		}
		saveOrUpdateRecs(user, code, ret, code.getDelIds());

		return ret;

	}

	private void saveOrUpdateRecs(UserProfile user,
			InvitationForeignerApply clcReport, InvitationForeignerApply ret, String delIds) {
		List<InvitationPersons> list = clcReport.getList();
		if (list != null) {
			for (InvitationPersons rec : list) {
				rec.setInvitationId(ret.getInvitationId());	
				if(StringUtils.isNotBlank(rec.getEmpName()) || StringUtils.isNotBlank(rec.getFamilyName()) || StringUtils.isNotBlank(rec.getGivenName())){
					String str = rec.getBirthdate();
					if(StringUtils.isNotBlank(str)&&str.length()>=10){
						rec.setBirthdate(str.substring(0,10));
					}
					personsDao.saveOrUpdate(rec);
				}
			}
		}
		personsService.deleteRec(clcReport.getInvitationId(), delIds);

	}

	/**
	 * 递归删除
	 * 
	 * @param InvitationForeignerApply
	 */
	private void recursiveDelete(UserProfile user, InvitationForeignerApply code) {
		if (true) {// 可删除
			String codeId = code.getInvitationId();
			AuthUtils.canExecute(user, InvitationAuthConstant.AUTH_OPER_NAME_SETVALINVITATION, codeId, code);
			this.codeDao.delete(codeId);
			personsService.deleteRecByPlcId(codeId);
		}

	}

	public InvitationForeignerApply getWmsInvitationForeignerApplyFromBuffer(
			String codeIdOrName) {
		if (StringUtils.isBlank(codeIdOrName)) {
			return null;
		}
		InvitationForeignerApply code = this.get(codeIdOrName);
		return code;
	}

	public void update(InvitationForeignerApply entity) {
		this.codeDao.update(entity);
		
	}
	/**
	 * 单一提取文件.
	 * @param report
	 * @return
	 */
	public String extractFiles(String invitationId, UserProfile userProfile){
		InvitationForeignerApply entity =  this.get(invitationId);
		return extractFile(userProfile,entity);
	}
	public String extractFile(UserProfile userProfile,InvitationForeignerApply entity){
		String str = "";
		 str+="多次入境说明:"+extractFiles.extractFiles(entity.getInvitationId(), entity.getInboundCommentFile(),
				"/oa/invitation_f/docs/", "INVITATION", "INVITATION", userProfile);
		 str+="详细日程安排:"+extractFiles.extractFiles(entity.getInvitationId(), entity.getScheduleFile(),
					"/oa/invitation_f/docs/", "INVITATION", "INVITATION_2", userProfile);
		 str+="被邀请来华人员中是否有副部级以上官员及原、现国家政要:"+extractFiles.extractFiles(entity.getInvitationId(), entity.getPoliticiansFile(),
				 "/oa/invitation_f/docs/", "INVITATION", "INVITATION_3", userProfile);
		 str+=extractPersonsFiles(userProfile, entity.getInvitationId());
		 return str;
	}
	public String extractPersonsFiles(UserProfile userProfile,String invitationId) {
		String returnStr = "success";
		List<InvitationPersons> persons = personsDao.getPersonsByApply(invitationId);
		for (InvitationPersons p : persons) {
			returnStr=extractFiles.extractFiles(p.getRecordId(), p.getPassportFileName(),
					"/oa/invitation_f/passort_docs/", "INVITATION", "INVITATION_PERSON", userProfile);
		}
		return returnStr;
	}

	/**
	 * 批量提取文件.
	 * @param report
	 * @return
	 */
	public String batchExtractFiles(UserProfile userProfile){
		String returnStr = "success";
		List <InvitationForeignerApply> list= codeDao.getAllList();
		for(InvitationForeignerApply en:list){
			returnStr=extractFile(userProfile,en);
			
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
}
