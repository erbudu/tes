package com.supporter.prj.linkworks.oa.doc_in.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.ExtractFiles;
import com.supporter.prj.linkworks.oa.doc_in.constants.AuthConstant;
import com.supporter.prj.linkworks.oa.doc_in.dao.DocInDao;
import com.supporter.prj.linkworks.oa.doc_in.dao.DocInSrcUnitDao;
import com.supporter.prj.linkworks.oa.doc_in.entity.DocIn;
import com.supporter.prj.linkworks.oa.doc_in.entity.DocInSrcUnit;
import com.supporter.prj.linkworks.oa.doc_in.util.AuthUtil;
import com.supporter.prj.linkworks.oa.history_exam_record.service.VCneecExamService;
import com.supporter.prj.linkworks.oa.report.constant.LogConstant;
import com.supporter.prj.linkworks.oa.report.entity.Report;
import com.supporter.util.CommonUtil;

@Service
public class DocInService {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private DocInDao codeDao;
	@Autowired
	private DocInSrcUnitDao unitDao;
	@Autowired
	private VCneecExamService cneecExamService;
	@Autowired
	private ExtractFiles extractFiles;
	private static final int RECORD_NO_COUNT = 10000; // 每年最大可能出现的收发文总数.

	/**
	 * 根据主键获取信息.
	 * 
	 * @param codeId
	 *            主键
	 * @return WmsDocIn
	 */
	public DocIn get(String codeId) {
		return codeDao.get(codeId);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param codeId
	 * @return
	 */
	public DocIn initEditOrViewPage(String codeId, String docClassify,
			UserProfile user) {
		if (StringUtils.isBlank(codeId)) {// 新建
			DocIn code = new DocIn();
			code.setDocId(com.supporter.util.UUIDHex.newId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			code.setCreatedDate(sdf.format(new Date()));
			code.setPublishDate(sdf.format(new Date()));
			code.setAdd(true);
			String deptId = "1";
			String deptName = "测试系统";
			code.setDocStatus(DocIn.DRAFT);
			if (user != null) {
				Dept dept = user.getDept();
				if (dept != null) {
					deptId = user.getDeptId();
					deptName = dept.getName();
				}
				String userName = user.getName();
				String userId = user.getPersonId();
				code.setDeptId(deptId);
				code.setCreatorName(userName);
				code.setPublisherName(userName);
				code.setPublisherId(userId);
				code.setCreatedBy(userId);
				code.setDraftsmanId(user.getPersonId());
				code.setDraftsmanName(user.getName());
				code.setCopyCount(Long.valueOf("1"));
				code.setRecordNo(generateNewRecordNo(DocIn.IN));
				code.setDocNo(getRecordNo(code));
			}
			if (StringUtils.isNotBlank(docClassify)) {
				code.setDocClassify(Long.valueOf(docClassify));
			} else {
				code.setDocClassify(Long.valueOf("1"));
			}
			if(unitDao.check(deptName)){
				DocInSrcUnit unit = new DocInSrcUnit();
				unit.setUnitId(deptId);
				unit.setUnitName(deptName);
				unitDao.saveOrUpdate(unit);
			}
			return code;
		} else {// 编辑
			DocIn code = codeDao.get(codeId);
			code.setAdd(false);
			return code;
		}
	}
	/**
	 * 进入查看页面.
	 * @param moduleId
	 * @return
	 */
	public DocIn viewPage(String docId, UserProfile user) {
		DocIn entity =  codeDao.get(docId);
			//如果有旧系统的流程，则获取旧系统的procId
			long oldProcId = cneecExamService.getProcIdByRecord(entity);
			if (oldProcId > 0)entity.setOldProcId(oldProcId);
			return entity;
	}
	public String getRecordNo(DocIn ldoc_D) {
		// 构造“收###号（YYYY）”并设置到docNo属性中

		int li_DocNo = CommonUtil.parseInt(ldoc_D.getRecordNo().substring(
				"yy".length()), 1);
		String ls_DocNo = "收 " + CommonUtil.format(li_DocNo, "000") + " 号 ("
				+ CommonUtil.format(new Date(), "yyyy") + ")";
		return String.valueOf(ls_DocNo);
	}

	private String generateNewRecordNo(int ai_DocType) {
		int li_MaxRecordNo = getMaxRecordNo(ai_DocType);

		return CommonUtil.format(li_MaxRecordNo + 1, "000000");
	}

	public int getMaxRecordNo(int ai_DocType) {
		String a = CommonUtil.format(new Date(), "yy");
		String ls_SQL = "select max(record_no) from oa_doc_in"
				+ " where record_no >= " + a + "0000" + " and record_no <= ? ";
		List<String> max = codeDao.sqlQuery(ls_SQL, null, a + "9999");
		int li_MaxRecordNo = CommonUtil.parseInt(a) * RECORD_NO_COUNT;
		if (max.size() > 0) {
			String m = max.get(0);
			if (m == null) {
				return li_MaxRecordNo;
			} else {
				li_MaxRecordNo = Integer.valueOf(m);
			}
		}
		return li_MaxRecordNo;
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
	public List<DocIn> getGrid(UserProfile user, JqGrid jqGrid, DocIn code) {
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
				DocIn codeDb = this.getWmsDocInFromBuffer(docId);
				if (codeDb == null) {
					continue;
				}
				this.recursiveDelete(user, codeDb);
			}
			// 记录日志
			/*
			 * DocInUtils.saveDocInOperateLog(user, null,
			 * DocIn.LogOper.DocIn_DEL.getOperName(), "{delDocInIds : " +
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
	public DocIn saveOrUpdate(UserProfile user, DocIn code,
			Map<String, Object> valueMap) {
		DocIn ret = null;
		if (code.getAdd()) {// 新建
			code.setInsideOnly(Long.valueOf("1"));
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
		saveDocInSrc(code);
		return ret;

	}
public void saveDocInSrc(DocIn entity){
	String deptName = entity.getDeptName();
	if(unitDao.check(deptName)){
		DocInSrcUnit src = new DocInSrcUnit();
		src.setUnitId(com.supporter.util.UUIDHex.newId());
		src.setUnitName(deptName);
		unitDao.save(src);
	}
}
	boolean copy(File af_File, String as_TargetFile) {
		// 判断是否已经存在该目标文件
		if (fileExists(as_TargetFile)) {
			System.out.println("file already exists: " + as_TargetFile);
			return false;
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
	 * @param DocIn
	 */
	private void recursiveDelete(UserProfile user, DocIn code) {
		
		if (true) {// 可删除
			String codeId = code.getDocId();
			//权限控制
			AuthUtil.canExecute(user, AuthConstant.AUTH_OPER_NAME_SETVALDOCIN, codeId, code);
			this.codeDao.delete(codeId);
		}

	}

	public DocIn getWmsDocInFromBuffer(String codeIdOrName) {
		if (StringUtils.isBlank(codeIdOrName)) {
			return null;
		}
		DocIn code = this.get(codeIdOrName);
		return code;
	}

	public void update(DocIn doc) {
		this.codeDao.update(doc);
		
	}

	public void getDeptCodeTable(JqGrid jqGrid) {
		unitDao.getGrid(jqGrid);
		
	}
	/**
	 * 单一提取文件.
	 * @param report
	 * @return
	 */
	public String extractFiles(String docId, UserProfile userProfile){
		DocIn entity =  this.get(docId);
		return extractFiles.extractFiles(entity.getDocId(), entity.getFileName(),
				"/oa/doc_in/docs/", "DOCIN", "DOCIN", userProfile);
	}
	
	/**
	 * 批量提取文件.
	 * @param report
	 * @return
	 */
	public String batchExtractFiles(UserProfile userProfile){
		String returnStr = "success";
		List <DocIn> list= codeDao.getDocIntList();
		for(DocIn en:list){
			returnStr = extractFiles.extractFiles(en.getDocId(), en.getFileName(),
					"/oa/doc_in/docs/", "DOCIN", "DOCIN", userProfile);
			
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
