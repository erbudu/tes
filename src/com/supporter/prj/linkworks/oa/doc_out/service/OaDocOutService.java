package com.supporter.prj.linkworks.oa.doc_out.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.swf.runtime.entity.WfNodeInstance;
import com.supporter.prj.eip.swf.runtime.service.WfNodeInstanceService;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.ExtractFiles;
import com.supporter.prj.linkworks.oa.doc_out.constants.AuthConstant;
import com.supporter.prj.linkworks.oa.doc_out.dao.OaDocOutDao;
import com.supporter.prj.linkworks.oa.doc_out.entity.OaDocOut;
import com.supporter.prj.linkworks.oa.doc_out.util.AuthUtils;
import com.supporter.prj.linkworks.oa.history_exam_record.service.VCneecExamService;
import com.supporter.util.CommonUtil;

@Service
public class OaDocOutService {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private OaDocOutDao codeDao;
	@Autowired
	private VCneecExamService cneecExamService;
	@Autowired
	private ExtractFiles extractFiles;
	@Autowired
	private WfNodeInstanceService wfNodeInstanceService;
	/**
	 * 根据主键获取信息.
	 * 
	 * @param codeId
	 *            主键
	 * @return WmsOaDocOut
	 */
	public OaDocOut get(String codeId) {
		return codeDao.get(codeId);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param codeId
	 * @return
	 */
	public OaDocOut initEditOrViewPage(String codeId, UserProfile user) {
		if (StringUtils.isBlank(codeId)) {// 新建
			OaDocOut code = new OaDocOut();
			code.setDocId(com.supporter.util.UUIDHex.newId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			code.setCreatedDate(sdf.format(new Date()));
			code.setAdd(true);
			code.setDocStatus(OaDocOut.DRAFT);
			code.setCopyCount(Long.valueOf("1"));
			if (user != null) {
				code.setCreatedBy(user.getName());
				code.setCreatedById(user.getPersonId());
				code.setDraftsmanId(user.getPersonId());
				code.setDraftsmanName(user.getName());
				//赋值当前用户
				code.setUserName(user.getName());
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
			OaDocOut code = codeDao.get(codeId);
			//赋值当前用户
			code.setUserName(user.getName());
			code.setAdd(false);
			return code;
		}
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
	public List<OaDocOut> getGrid(UserProfile user, JqGrid jqGrid, OaDocOut code) {
		return codeDao.findPage(user,jqGrid, code);
	}

	public List<OaDocOut> getGrid(UserProfile user, JqGrid jqGrid,
			OaDocOut code, String classifyId) {
		return codeDao.findPage(user, jqGrid, code, classifyId);
	}

	/**
	 * 进入查看页面.
	 * 
	 * @param moduleId
	 * @return
	 */
	public OaDocOut viewPage(String docId, UserProfile user) {
		OaDocOut entity = codeDao.get(docId);
		entity.setUserName(user.getName());
		// 如果有旧系统的流程，则获取旧系统的procId
		long oldProcId = cneecExamService.getProcIdByRecord(entity);
		if (oldProcId > 0)
			entity.setOldProcId(oldProcId);
		
		
		return entity;
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
				OaDocOut codeDb = this.getWmsOaDocOutFromBuffer(docId);
				if (codeDb == null) {
					continue;
				}
				this.recursiveDelete(user, codeDb);
			}
			// 记录日志
			/*
			 * OaDocOutUtils.saveOaDocOutOperateLog(user, null,
			 * OaDocOut.LogOper.OaDocOut_DEL.getOperName(), "{delOaDocOutIds : " +
			 * codeIds + "}");
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
	public OaDocOut saveOrUpdate(UserProfile user, OaDocOut code,
			Map<String, Object> valueMap) {
		OaDocOut ret = null;
		if (code.getAdd()) {// 新建
			code.setInsideOnly(Long.valueOf("1"));
			code.setNeedReceivers("0");
			String path = getServicePath();
			String docId = code.getDocId();
			String ls_FilePath = path + "/oa/doc_out/docs/" + docId + ".doc";
			if (!fileExists(ls_FilePath)) {
				String ls_TemplateFile = getServicePath()
						+ "/oa/doc_out/templates/doc_templ_" + code.getTem()
						+ ".doc";
				File lfile_Source = new File(ls_TemplateFile);
				copy(lfile_Source, ls_FilePath, "");
			}
			code.setFileNames(ls_FilePath);
			this.codeDao.save(code);
			ret = code;
			// 记录日志
	/*		String logMessage = MessageFormat.format(
					LogConstant.PUBLISH_REPORT_LOG_MESSAGE, reportIds);
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					user, LogConstant.PUBLISH_REPORT_LOG_ACTION, logMessage,
					null, null);*/
		} else {// 编辑
			code.setModifiedBy(user.getName());
			code.setModifiedById(user.getPersonId());
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

		return ret;

	}

	boolean copy(File af_File, String as_TargetFile, String type) {
		// 判断是否已经存在该目标文件
		if (fileExists(as_TargetFile)) {
			System.out.println("file already exists: " + as_TargetFile);
			if (type != null && type.equals("acceptEdit")) {

			} else {
				return false;

			}
		}

		File lfile_Target = new File(as_TargetFile);

		// 开始拷贝
		try {
			lfile_Target.createNewFile();
			FileInputStream lfis_Source = new FileInputStream(af_File);
			FileOutputStream lfos_Target = new FileOutputStream(lfile_Target);
			byte[] larrbyte_Buffer = new byte[1024];
			int li_Bytes = 0;
			while ((li_Bytes = lfis_Source.read(larrbyte_Buffer)) != -1) {
				lfos_Target.write(larrbyte_Buffer, 0, li_Bytes);
			}
			lfis_Source.close();
			lfos_Target.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean fileExists(String as_FileName) {
		File lfile_F = new File(as_FileName);
		return lfile_F.exists();
	}

	public String getSubDirName(String as_FileId) {
		String ls_Id = CommonUtil.format(as_FileId, "000000");
		String ls_Dir = ls_Id.substring(ls_Id.length() - 6).substring(0, 3);
		int li_DirId = CommonUtil.parseInt(ls_Dir, 1);
		return Integer.toString(li_DirId);
	}

	public String getServicePath() {
		/*
		 * String servicePath = this.request.getScheme() + "://" +
		 * this.request.getServerName() + ":" + this.request.getServerPort() +
		 * this.request.getContextPath();
		 */
		String servicePath = this.request.getRealPath("");
		return servicePath;
	}

	/**
	 * 递归删除
	 * 
	 * @param OaDocOut
	 */
	private void recursiveDelete(UserProfile user, OaDocOut code) {
		if (true) {// 可删除
			AuthUtils.canExecute(user,
					AuthConstant.AUTH_OPER_NAME_SETVALDOCOUT, code.getDocId(),
					code);
			String codeId = code.getDocId();
			this.codeDao.delete(codeId);
		}

	}

	public OaDocOut getWmsOaDocOutFromBuffer(String codeIdOrName) {
		if (StringUtils.isBlank(codeIdOrName)) {
			return null;
		}
		OaDocOut code = this.get(codeIdOrName);
		return code;
	}

	public void update(OaDocOut doc) {
		this.codeDao.update(doc);
	}

	public void createdBase(String docId) {
		String path = getServicePath();
		String ls_FilePath = path + "/oa/doc_out/revised/" + docId + ".doc";
		if (!fileExists(ls_FilePath)) {
			String ls_TemplateFile = getServicePath() + "/oa/doc_out/docs/"
					+ docId + ".doc";
			File lfile_Source = new File(ls_TemplateFile);
			copy(lfile_Source, ls_FilePath, "acceptEdit");
		}

	}
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String extractFiles(String docId, UserProfile userProfile){
		OaDocOut entity =  this.get(docId);
		return extractFiles.extractFiles(entity.getDocId(), entity.getFileNames(),
				"/oa/doc_out/files/", "DOCOUT", "DOCOUT", userProfile);
	}
	
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String batchExtractFiles(UserProfile userProfile){
		String returnStr = "success";
		List <OaDocOut> list= codeDao.getAlltList();
		for(OaDocOut en:list){
			returnStr = extractFiles.extractFiles(en.getDocId(), en.getFileNames(),
					"/oa/doc_out/files/", "DOCOUT", "DOCOUT", userProfile);
			
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
	 * 保存会签人
	 * @param docId
	 * @param signedType
	 */
	public void saveSigner(String docId,String signedType,UserProfile user) {
		OaDocOut docOut = this.codeDao.get(docId);
		if(docOut != null){
			if(signedType.trim().equals("deptSigner")){//部门领导会签
				if(StringUtils.isNotBlank(docOut.getDeptLeaderIds())){
					if(docOut.getDeptLeaderIds().indexOf(user.getPersonId())==-1){
						docOut.setDeptLeaderIds(docOut.getDeptLeaderIds() +"," +user.getPersonId());
						docOut.setDeptLeaderNames(docOut.getDeptLeaderNames() + "," + user.getName());
					}
				}else{
					docOut.setDeptLeaderIds(user.getPersonId());
					docOut.setDeptLeaderNames(user.getName());
				}
				
			}
			
			if(signedType.trim().equals("leaderSigner")){//公司领导会签
				if(StringUtils.isNotBlank(docOut.getTopLeaderIds())){
					if(docOut.getTopLeaderIds().indexOf(user.getPersonId())==-1){
					docOut.setTopLeaderIds(docOut.getTopLeaderIds() +"," +user.getPersonId());
					docOut.setTopLeaderNames(docOut.getTopLeaderNames() + "," + user.getName());
					}
				}else{
					docOut.setTopLeaderIds(user.getPersonId());
					docOut.setTopLeaderNames(user.getName());
				}
				
			}
			this.codeDao.update(docOut);
		}
		
	}
	
    /**
     * 判断是否已过接受修改
     * @param procId
     * @return
     */
    public boolean getIsAccept(String procId) {
    	boolean isAccept = false;
    	if(StringUtils.isNotBlank(procId)) {
    		List<WfNodeInstance> nodes = wfNodeInstanceService.getNodeInstances(procId);
    		if (nodes != null && nodes.size() > 0) {
    			for (WfNodeInstance node : nodes) {
    				if ("T".equals(node.getCompleted()) && "接收修改".equals(node.getNodeName())) {
    					isAccept = true;
    					break;
    				}
    			}
    		}
    	}
    	return isAccept;
    }
	
	
}
