package com.supporter.prj.ppm.prebid.review_ver.service;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.file_upload.dao.FileUploadDao;
import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.prebid.report.dao.ProjectReportBfdDao;
import com.supporter.prj.ppm.prebid.report.dao.ProjectReportDao;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReport;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReportBfd;
import com.supporter.prj.ppm.prebid.report.service.ProjectReportService;
import com.supporter.prj.ppm.prebid.review.dao.PpmPrebidReviewConDao;
import com.supporter.prj.ppm.prebid.review.dao.PpmPrebidReviewDao;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReview;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReviewCon;
import com.supporter.prj.ppm.prebid.review.service.PpmPrebidReviewService;
import com.supporter.prj.ppm.prebid.review_ver.dao.PpmPrebidReviewVerBfdDao;
import com.supporter.prj.ppm.prebid.review_ver.dao.PpmPrebidReviewVerBfdFDao;
import com.supporter.prj.ppm.prebid.review_ver.dao.PpmPrebidReviewVerDao;
import com.supporter.prj.ppm.prebid.review_ver.dao.PpmPrebidReviewVerRpDao;
import com.supporter.prj.ppm.prebid.review_ver.entity.PpmPrebidReviewVer;
import com.supporter.prj.ppm.prebid.review_ver.entity.PpmPrebidReviewVerBfd;
import com.supporter.prj.ppm.prebid.review_ver.entity.PpmPrebidReviewVerBfdF;
import com.supporter.prj.ppm.prebid.review_ver.entity.PpmPrebidReviewVerInfo;
import com.supporter.prj.ppm.prebid.review_ver.entity.PpmPrebidReviewVerRp;
import com.supporter.prj.ppm.prebid.util.LogConstant;
import com.supporter.prj.ppm.service.PPMService;
import com.supporter.prj.ppm.template_register.dao.TemplateRegisterDetailDao;
import com.supporter.prj.ppm.template_register.entity.TemplateKeyPointsExam;
import com.supporter.prj.ppm.template_register.entity.TemplateRegisterDetail;
import com.supporter.prj.ppm.template_register.service.TemplateRegisterService;
import com.supporter.util.UUIDHex;





/**
 * @author 王康
 *
 */

@Service
@Transactional(TransManager.APP)
public class PpmPrebidReviewVerService {

	@Autowired
	private PpmPrebidReviewVerDao ppmPrebidReviewVerDao;
	@Autowired
	private PpmPrebidReviewVerRpDao ppmPrebidReviewVerRpDao;
	@Autowired
	private ProjectReportDao ppmPrebidReportDao;
	@Autowired
	private ProjectReportBfdDao bfdDao;
	@Autowired
	private PpmPrebidReviewVerBfdDao prebidReviewVerBfdDao;
	@Autowired
	private PpmPrebidReviewVerBfdDao reviewVerbfdDao;
	@Autowired
	private PpmPrebidReviewVerBfdFDao reviewVerbfdFDao;
	@Autowired
	private PpmPrebidReviewDao ppmPrebidReviewDao;
	@Autowired
	private PpmPrebidReviewService reviewService;
	@Autowired
	private PpmPrebidReviewConDao ppmPrebidReviewConDao;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private FileUploadDao fileUploadDao;
	@Autowired
	private PpmPrebidReviewVerFService bfdFileService;
	@Autowired
	private PpmPrebidReviewVerRpDao prebidReviewVerRpDao;
	@Autowired
	private TemplateRegisterDetailDao templateRegisterDetailDao;
	@Autowired
	private TemplateRegisterService templateRegisterService;
	
	
	
	/**
	 * 获取投标前评审对象集合
	 * @param user
	 * @param jqGrid
	 * @param ppmPrebidReview
	 * @return
	 */
	public List<PpmPrebidReviewVer> getGrid(UserProfile user, JqGrid jqGrid, PpmPrebidReviewVer ppmPrebidReviewVer) {
		List<PpmPrebidReviewVer> prebidReviewVerList = new ArrayList<PpmPrebidReviewVer>();
		prebidReviewVerList = this.ppmPrebidReviewVerDao.findBy("prjId",ppmPrebidReviewVer.getPrjId());
		if(prebidReviewVerList!=null) {
			for (int i = 0; i < prebidReviewVerList.size(); i++) {
				String reviewNo = "";
				if ((prebidReviewVerList.get(i)).getPrebidReviewId() != null
						&& !(prebidReviewVerList.get(i)).getPrebidReviewId().equals("")) {
					reviewNo= getReviewNo((prebidReviewVerList.get(i)).getPrebidReviewId());
					prebidReviewVerList.get(i).setReviewNo(reviewNo);
					prebidReviewVerList.get(i).setModifyEvalPointVal(getPonitName(prebidReviewVerList.get(i).getModifyEvalPoint()));
				}
			}
		}
		return prebidReviewVerList;
	}
	/**
	 * 初始化对象
	 * @param id
	 * @param user
	 * @return
	 */
	public PpmPrebidReviewVer initEditOrViewPage(String prjId,
			String reviewVerId, String prebidReviewId, UserProfile user) {
		//List<PpmPrebidReviewVerBfd> dataList = new ArrayList<PpmPrebidReviewVerBfd>();
		PpmPrebidReviewVer ppmPrebidReviewVer;
		if (StringUtils.isBlank(reviewVerId)) {//新建
			ppmPrebidReviewVer = new PpmPrebidReviewVer();
			ppmPrebidReviewVer.setReviewVerId(UUIDHex.newId());
			ppmPrebidReviewVer.setPrebidReviewId(prebidReviewId);
			ppmPrebidReviewVer.setStutas(PpmPrebidReviewVer.DRAFT);
			return ppmPrebidReviewVer;
		} else {//编辑
			ppmPrebidReviewVer = ppmPrebidReviewVerDao.get(reviewVerId);
			List<PpmPrebidReviewVerRp> list = ppmPrebidReviewVerRpDao.findBy("reviewVerId",reviewVerId);
			if(list.size()!=0) {//获取评审要点的Id，若无评审要点则不需要
				ppmPrebidReviewVer.setRpId(list.get(0).getRpId());
			}
			return ppmPrebidReviewVer;
		}
	}
	/**
	 * 获得评审的流程Id
	 * @param prebidReviewId
	 * @return
	 */
	public String getReviewProcId(String prebidReviewId) {
		PpmPrebidReview ppmPrebidReview  = ppmPrebidReviewDao.get(prebidReviewId);
		String procId = "";
		if(ppmPrebidReview!=null) {
			procId = ppmPrebidReview.getProcId();
		}
		return procId;
	}
	/**
	 * 获得评审有条件通过的评审要点
	 * @param prebidReviewId
	 * @return
	 */
	public String getAuditNoPassPoints(String prebidReviewId) {
		List<TemplateKeyPointsExam> pointsExamList = templateRegisterService
				.getPointsExamGridByProcId(getReviewProcId(prebidReviewId));
		List<String> list = new ArrayList<String>();
		for(TemplateKeyPointsExam pointsExam : pointsExamList) {
			list.add(pointsExam.getDetailId());
		} 
		Set<String> set = new  HashSet<String>(); 
        List<String> newList = new  ArrayList<String>(); 
        for (String cd:list) {
           if(set.add(cd)){
               newList.add(cd);
           }
       }
       StringBuilder evalPoints = new StringBuilder();
       boolean first = true;
       //第一个前面不拼接","
       for(String point :newList) {
          if(first) {
             first=false;
          }else{
        	  evalPoints.append(",");
          }
          evalPoints.append(point);
       }
       return evalPoints.toString();
	}
	/**
	 * saveOrUpdate 保存或更新对象
	 * @param user
	 * @param ppmReportingStart
	 * @param valueMap
	 * @return
	 */
	public PpmPrebidReviewVer saveOrUpdate(UserProfile user, PpmPrebidReviewVer ppmPrebidReviewVer, Map<String, Object> valueMap) {
		if (ppmPrebidReviewVer.getIsNew()) {
			String reviewVerificatContent = ppmPrebidReviewVer.getReviewVerificatContent();
			if(reviewVerificatContent.contains("调整及修改评审信息")) {
				ppmPrebidReviewVer.setModifyEvalPoint(getAuditNoPassPoints(ppmPrebidReviewVer.getPrebidReviewId()));
			}
			ppmPrebidReviewVer.setReviewNo(getReviewNo(ppmPrebidReviewVer.getPrebidReviewId()));
			ppmPrebidReviewVer.setCreatedBy(user.getName());
			ppmPrebidReviewVer.setCreatedId(user.getPersonId());
			ppmPrebidReviewVer.setCreatedDate(new Date());
			this.ppmPrebidReviewVerDao.save(ppmPrebidReviewVer);
			PPMService.getScheduleStatusService().saveScheduleStatus(ppmPrebidReviewVer.getPrjId(), "prebid_reviewVer01", user);
		} else {
			// 设置更新时间
			ppmPrebidReviewVer.setModifiedBy(user.getName());
			ppmPrebidReviewVer.setModifiedId(user.getPersonId());
			ppmPrebidReviewVer.setModifiedDate(new Date());
			this.ppmPrebidReviewVerDao.update(ppmPrebidReviewVer);
		}
		if(ppmPrebidReviewVer.getModifyEvalPoint()!=null&&ppmPrebidReviewVer.getModifyEvalPoint()!="") {
			PpmPrebidReviewVerRp ppmPrebidReviewVerRp = saveOrUpdateRp(ppmPrebidReviewVer);
			ppmPrebidReviewVer.setRpId(ppmPrebidReviewVerRp.getRpId());
		}
		return ppmPrebidReviewVer;
	}
	
	public PpmPrebidReviewVerRp saveOrUpdateRp(PpmPrebidReviewVer ppmPrebidReviewVer) {
		PpmPrebidReviewVerRp ppmPrebidReviewVerRp;
		if (ppmPrebidReviewVer.getIsNew()) {
			ppmPrebidReviewVerRp = new PpmPrebidReviewVerRp();
			ppmPrebidReviewVerRp.setRpId(UUIDHex.newId());
			ppmPrebidReviewVerRp.setPrebidReviewId(ppmPrebidReviewVer.getPrebidReviewId());
			ppmPrebidReviewVerRp.setReviewVerId(ppmPrebidReviewVer.getReviewVerId());
			ppmPrebidReviewVerRp.setPrjId(ppmPrebidReviewVer.getPrjId());
			ppmPrebidReviewVerRp.setReviewPointsId(ppmPrebidReviewVer.getModifyEvalPoint());
			ppmPrebidReviewVerRpDao.save(ppmPrebidReviewVerRp);
		} else {
			ppmPrebidReviewVerRp =ppmPrebidReviewVerRpDao.get(ppmPrebidReviewVer.getRpId());
			ppmPrebidReviewVerRpDao.update(ppmPrebidReviewVerRp);
		}
		return ppmPrebidReviewVerRp;
	}
	/**
	 * 保存并提交
	 * @param user
	 * @param ppmReportingStart
	 * @param valueMap
	 * @return
	 */
	public PpmPrebidReviewVer commit(UserProfile user, PpmPrebidReviewVer ppmPrebidReviewVer, Map<String, Object> valueMap) {
		//this.saveOrUpdate(user, ppmPrebidReviewVer, valueMap);
		String logMessage = MessageFormat.format(LogConstant.PUBLISH_LOG_ACTION_REVIEWVER, "(" + ppmPrebidReviewVer.getReviewVerId()+ ")" + ppmPrebidReviewVer.getReviewSubmitDate());
		EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS_REVIEWVER).info(user, LogConstant.PUBLISH_LOG_ACTION_REVIEWVER, logMessage, ppmPrebidReviewVer, null);
		System.out.println(logMessage);
		return ppmPrebidReviewVer;
	}
	/**
	 * <pre>功能:初始化资料清单无序列表数据-新建页面</pre>
	 * <pre>描述：根据资料清单码表ID和启动申报主键获取资料清单数据，获取到数据则返回</pre>
	 * @return 资料清单实体类数据
	 */
	private void getDataListAdd(String prbidReportId,String reviewVerId, UserProfile user) {
		
		List<PpmPrebidReviewVerBfd> list = new ArrayList<PpmPrebidReviewVerBfd>();
		List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PPM_PREBID_REPORT_BFD");//码表数据
		for (IComCodeTableItem item : items) {
			List<PpmPrebidReportBfd> ppmPrebidReportBfd = this.getDataListAdd(prbidReportId,item.getItemId(),reviewVerId);//
			for(int i= 0;i<ppmPrebidReportBfd.size();i++) {
				PpmPrebidReviewVerBfd bfd = new PpmPrebidReviewVerBfd(); 
				bfd.setBfdId(UUIDHex.getUUIDHex());
				bfd.setReviewVerId(reviewVerId);
				bfd.setPrbidReportId(ppmPrebidReportBfd.get(i).getPrbidReportId());
				bfd.setBfdTypeId(ppmPrebidReportBfd.get(i).getBfdTypeId());
				bfd.setBfdTypeName(ppmPrebidReportBfd.get(i).getBfdTypeName());
				reviewVerbfdDao.save(bfd);
				list.add(bfd);
				// 获取评审模块资料清单文件
				List<FileUpload> files = fileUploadService.getList("PREREPORT", "prebidReportFile",
						prbidReportId, items.get(i).getItemId());
				copyBfdFile(files, items.get(i).getItemId(),bfd.getBfdId(), reviewVerId, user);
			}
		}
	}

	/**
	 * 将评审模块资料清单文件复制到评审验证模块
	 * @param files 评审模块资料清单文件集合
	 * @param bfdId 评审验证模块资料清单id
	 * @param revVerId 评审验证模块主键
	 * @param user 当前登录用户
	 */
	private void copyBfdFile(List<FileUpload> files,String itemId,String bfdId, String reviewVerId, UserProfile user) {
		List<PpmPrebidReviewVerBfdF> bfdF = new ArrayList<PpmPrebidReviewVerBfdF>();
		if (CollectionUtils.isNotEmpty(files)) {
			for (int i = 0; i < files.size(); i++) {
				// 资料清单文件信息保存到附件数据表并复制磁盘中的资料清单文件
				FileUpload sourceFile = files.get(i);
				String[] levelId = { reviewVerId, itemId };
				File file = fileUploadService.getFile(sourceFile.getFileId());
				String newfileId = fileUploadService.saveFile(file, PpmPrebidReviewVer.MODULE_ID,  PpmPrebidReviewVer.BUSI_TYPE,
						sourceFile.getFileName(), user, levelId);
				FileUpload targetFile = fileUploadDao.get(newfileId);
				// 初始化评审验证资料清单文件
				PpmPrebidReviewVerBfdF bfdFile = new PpmPrebidReviewVerBfdF();
				bfdFile.setRecordId(com.supporter.util.UUIDHex.newId());
				bfdFile.setBfdId(bfdId);
				bfdFile.setPrbidReportId(reviewVerId);
				bfdFile.setFuFileId(targetFile.getFileId());
				bfdFile.setFuFileName(targetFile.getFileName());
				bfdFile.setIsUseSeal(0);
				bfdFile.setDisplayOrder(targetFile.getDisplayOrder());
				bfdF.add(bfdFile);
				bfdFileService.save(bfdFile);
			}
		}
	}
	/**
	 * 新建时取报审资料清单
	 * <pre>功能:根据启动申报主键和码表中资料清单的ID获取资料清单数据</pre>
	 * <pre>描述:根据启动申报主键和码表资料清单项的id可以确定到对应的而且唯一的，已经保存过的资料清单项</pre>
	 * <pre>调用:1.启动申报编辑页面初始化数据</pre>
	 * @param prbidReportId 资料清单业务表单实体类主键
	 * @param itemId 资料清单id
	 * @return 资料清单业务表单实体类数据
	 */
	public List<PpmPrebidReportBfd> getDataListAdd(String prbidReportId, String bfdTypeId,String reviewVerId) {
		if(prbidReportId == null || prbidReportId == "" && bfdTypeId == null || bfdTypeId == "") {
			return null;
		}
		String hql = "from "+PpmPrebidReportBfd.class.getName()+" where prbidReportId = ? and bfdTypeId = ?";
		List<PpmPrebidReportBfd> list = bfdDao.find(hql,prbidReportId,bfdTypeId);
		return list;
	}
	/**
	 * 编辑时取评审验证清单
	 * <pre>功能:根据启动申报主键和码表中资料清单的ID获取资料清单数据</pre>
	 * <pre>描述:根据启动申报主键和码表资料清单项的id可以确定到对应的而且唯一的，已经保存过的资料清单项</pre>
	 * <pre>调用:1.启动申报编辑页面初始化数据</pre>
	 * @param reviewVerId 资料清单业务表单实体类主键
	 * @param itemId 资料清单id
	 * @return 资料清单业务表单实体类数据
	 */
	public List<PpmPrebidReviewVerBfd> getDataListEdit(String bfdTypeId,String reviewVerId) {
		if(reviewVerId == null || reviewVerId == "" && bfdTypeId == null || bfdTypeId == "") {
			return null;
		}
		String hql = " from "+PpmPrebidReviewVerBfd.class.getName()+" where reviewVerId = ? and bfdTypeId = ?";
		List<PpmPrebidReviewVerBfd> list = prebidReviewVerBfdDao.find(hql,reviewVerId,bfdTypeId);
		return list;
	}
	/**
	 * <pre>保存资料清单</pre>
	 * @param PpmPrebidReportBfd 资料清单业务表单实体类
	 * @return 保存成功后的业务表单实体类数据
	 */
	public PpmPrebidReviewVerBfd saveBfd(PpmPrebidReviewVerBfd ppmPrebidReviewVerBfd) {
		if(ppmPrebidReviewVerBfd.getBfdId() == null || ppmPrebidReviewVerBfd.getBfdId() == "" ) return null;
		PpmPrebidReviewVerBfd entity = reviewVerbfdDao.findUniqueResult("bfdId", ppmPrebidReviewVerBfd.getBfdId());
		if(entity==null) {
			String codeTable = "PPM_PREBID_REPORT_BFD";
			ppmPrebidReviewVerBfd.setBfdTypeName(ProjectReportService.getBfdTypeName(ppmPrebidReviewVerBfd.getBfdTypeId(),codeTable));
			reviewVerbfdDao.save(ppmPrebidReviewVerBfd);
		}
		saveBfdF(ppmPrebidReviewVerBfd.getBfdId(),ppmPrebidReviewVerBfd.getPrbidReportId(),ppmPrebidReviewVerBfd.getFileInfo());
		return ppmPrebidReviewVerBfd;
	}
	/**
	 * <pre>保存清单附件</pre>
	 * @param bfdId 资料清业务实体类主键
	 * @param prbidReportId 启动申报业务实体类主键
	 * @param fileInfo 附件信息 [附件上传成功后的附件主键   上传附件的名称]
	 */
	public void saveBfdF(String bfdId, String prbidReportId, String fileInfo) {
		String[] fileInfoArr = fileInfo.split(",");
		String fileId = "";
		String fileName = "";
		for(int i=0;i<fileInfoArr.length;i++) {
			fileId = fileInfoArr[0];
			fileName = fileInfoArr[1];
		}
		PpmPrebidReviewVerBfdF ppmPrebidReviewVerBfdF = new PpmPrebidReviewVerBfdF();
		ppmPrebidReviewVerBfdF.setRecordId(UUIDHex.newId());
		ppmPrebidReviewVerBfdF.setBfdId(bfdId);
		ppmPrebidReviewVerBfdF.setPrbidReportId(prbidReportId);
		ppmPrebidReviewVerBfdF.setFuFileId(fileId);//实际上传附件ID
		ppmPrebidReviewVerBfdF.setFuFileName(fileName);
		ppmPrebidReviewVerBfdF.setFuSealFileId(fileId);//实际上传附件ID(盖完章的)
		ppmPrebidReviewVerBfdF.setIsUseSeal(0);//是否用印 码表：0否1是
		reviewVerbfdFDao.save(ppmPrebidReviewVerBfdF);
	}
	/**
	 * 删除
	 * @param user
	 * @param ids
	 * @return
	 */
	public void delete(UserProfile user, String reviewVerId) {
		PpmPrebidReviewVer ppmPrebidReviewVer = (PpmPrebidReviewVer) this.ppmPrebidReviewVerDao.get(reviewVerId);
		List<PpmPrebidReviewVerRp> list = prebidReviewVerRpDao.findBy("reviewVerId",reviewVerId);
		prebidReviewVerRpDao.delete(list);
		reviewVerbfdFDao.deleteByProperty("prbidReportId", reviewVerId);
		reviewVerbfdDao.deleteByProperty("reviewVerId", reviewVerId);
		// 删除附件库中附件
		deleteFile(ppmPrebidReviewVer);
		this.ppmPrebidReviewVerDao.delete(ppmPrebidReviewVer);
	}
	/**
	 * 删除相关附件
	 * @param recordFilingId
	 */
	public void deleteFile(PpmPrebidReviewVer ppmPrebidReviewVer){
		List<FileUpload> files = new ArrayList<FileUpload>();
		List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PPM_PREBID_REPORT_BFD");//码表数据
		for (IComCodeTableItem item : items) {
			List<PpmPrebidReviewVerBfd> ppmPrebidReviewVerBfd = this.getDataListEdit(item.getItemId(),
							ppmPrebidReviewVer.getReviewVerId());
			for (int i = 0; i < ppmPrebidReviewVerBfd.size(); i++) {
				files = fileUploadService.getList(PpmPrebidReviewVer.MODULE_ID,PpmPrebidReviewVer.BUSI_TYPE,
						ppmPrebidReviewVer.getReviewVerId(), items.get(i).getItemId());
			}
		}
		if (CollectionUtils.isNotEmpty(files)){
			for(IFile file:files){
				fileUploadService.deleteFile(file.getFileId());
			}
		}
	}
	/**
	 * <pre>删除资料清单</pre>
	 * @param moduleName 应用编号 
	 * @param basiType 业务编号
	 * @param entityId 业务实体类
	 * @param twolevelid 二级业务编号
	 */
	public void deleteBfd(String  filesId,String prbidReportId,String bfdTypeId) {

		String[] split = filesId.split(",");
		for (int i = 0;i<split.length;i++) {
			reviewVerbfdFDao.deleteByProperty("fuFileId", split[i]);
		}
		String hql = "delete " + PpmPrebidReviewVerBfd.class.getName() + " where reviewVerId = ? and bfdTypeId = ?";
		reviewVerbfdDao.execUpdate(hql, prbidReportId,bfdTypeId);
		
	}
	/**
	 * 流程更新对象
	 * @param useSeal
	 * @return
	 */
	public PpmPrebidReviewVer update(PpmPrebidReviewVer ppmPrebidReviewVer) {
		this.ppmPrebidReviewVerDao.update(ppmPrebidReviewVer);
		return ppmPrebidReviewVer;
	}
	/**
	 * 获取某项目下需评审验证但未评审验证的评审单号
	 * @param prjId 项目id
	 * @return map(评审id,评审单号)
	 */
	public Map<String, String> getVerReview(String prjId) {
		// 获取所有待验证的评审（评审模块评审结论为有条件通过的评审）
		Map<String, String> map = reviewService.getVerReview(prjId);
		if (!map.isEmpty()) {
			// 获取评审验证模块的评审ID集合
			List<String> reviewIdList = ppmPrebidReviewVerDao.getReviewId();
			// 去掉所有评审验证模块验证通过的评审
			if (CollectionUtils.isNotEmpty(reviewIdList)) {
				for (int i = 0; i < reviewIdList.size(); i++) {
					map.remove(reviewIdList.get(i));
				}
			}
		}
		return map;
	}
	/**
	 * 评审验证编辑操作下，获取评审验证当前关联的评审单
	 * @param reviewId 评审ID
	 * @return map(评审id,评审单号)
	 */
	public Map<String, String> getVerReviewPlus(String reviewId) {
		Map<String, String> map = new HashMap<String, String>();
		PpmPrebidReview review = reviewService.getReview(reviewId);
		if (review != null) {
			map.put(reviewId, review.getReviewNo());
		}
		return map;
	}
	/**
	 * 获取评审验证对象
	 * @param id
	 * @return
	 */
	public PpmPrebidReviewVer get(String reviewVerId) {
		PpmPrebidReviewVer ppmPrebidReviewVer = this.ppmPrebidReviewVerDao.get(reviewVerId);
		return ppmPrebidReviewVer;
	}
	/**
	 * 获取评审编号
	 * @param prebidReviewId
	 * @return
	 */
	private String getReviewNo(String prebidReviewId) {
		PpmPrebidReview ppmPrebidReview = ppmPrebidReviewDao.get(prebidReviewId);
		String reviewNo = ppmPrebidReview.getReviewNo();
		return reviewNo;
	}
	/**
	 * 获取评审验证对象状态
	 * @param id
	 * @return
	 */
	public Integer getStutas(String reviewVerId) {
		
		String hql = "select stutas from " + PpmPrebidReviewVer.class.getName() + " where reviewVerId = ?";
		List<Integer> ppmPrebidReviewVer = this.ppmPrebidReviewVerDao.find(hql,reviewVerId);
		if(ppmPrebidReviewVer.size()>0) {
			Integer stutas = ppmPrebidReviewVer.get(0);
			return stutas;
		}
		return null;
	}
	/**
	 * 获取评审验证中用到的评审表中的信息
	 * @param prebidReviewId
	 * @return
	 */
	public PpmPrebidReviewVerInfo getReviewVerInfo(String prebidReviewId) {
		PpmPrebidReview prebidReview = this.ppmPrebidReviewDao.get(prebidReviewId);
		List<PpmPrebidReviewCon> prebidReviewCon = this.ppmPrebidReviewConDao.findBy("prebidReviewId", prebidReviewId);
		PpmPrebidReviewVerInfo prebidReviewVerInfo = new PpmPrebidReviewVerInfo();
		prebidReviewVerInfo.setPrebidReviewId(prebidReviewId);
		prebidReviewVerInfo.setReviewSubmitDate(prebidReview.getReviewSubmitDate());
		for(PpmPrebidReviewCon con : prebidReviewCon) {
			prebidReviewVerInfo.setPrebidRevConId(con.getPrebidRevConId());
			prebidReviewVerInfo.setRvConStatus(con.getRvConStatus());
		}
		return prebidReviewVerInfo;
	}
	/**
	 * 拼接评审要点名称
	 * @param detailIds
	 * @return
	 */
	public String getPonitName(String detailIds) {
		String pointName ="";
		try {
			List<String> result = Arrays.asList(detailIds.split(","));
			for(String detailId : result) {
				TemplateRegisterDetail templateRegisterDetail = templateRegisterDetailDao.get(detailId);
				if("".equals(pointName)) {
					pointName = templateRegisterDetail.getTextDisplay();
				}else {
					String pointNameT = templateRegisterDetail.getTextDisplay();
					pointName = pointNameT+","+pointName;
				}
			}
		} catch (Exception e) {
		}
		return pointName;
	}
	/**
	 * 获取评审验证主表信息
	 * @param reviewVerId
	 * @return
	 */
	public PpmPrebidReviewVer getVerEntity(String reviewVerId) {
		PpmPrebidReviewVer prebidReviewVer = this.ppmPrebidReviewVerDao.get(reviewVerId);
		return prebidReviewVer;
	}
	/**
	 * 获取所有待验证的评审（评审模块评审结论为有条件通过的评审）
	 * @param prjId
	 * @return
	 */
	public boolean checkReviewVer(String prjId) {
		Map<String, String> map = reviewService.getVerReview(prjId);
		if (!map.isEmpty()) {
			return true;
		}
		return false;
	}
	/**
	 * 获取本项目最后一个评审验证的验证id
	 * @param prjId
	 * @return
	 */
	public String getMaxReviewVerId(String prjId) {
		return this.ppmPrebidReviewVerDao.getMaxReviewVerId(prjId);
	}
	/**
	 * 将报审中的文件清单复制到验证表中
	 * @param user
	 * @param prjId
	 * @param reviewVerId
	 */
	public void copyFile(UserProfile user, String prjId,
			String reviewVerId) {
		// 获取报审中的资料清单
		List<PpmPrebidReport> ppmPrebidReportList = ppmPrebidReportDao.findBy("prjId",prjId);
		String prbidReportId = "";
		if (ppmPrebidReportList.size() != 0) {
			for (int i = 0; i < ppmPrebidReportList.size(); i++) {
				PpmPrebidReport ppmPrebidReport = ppmPrebidReportList.get(i);
				prbidReportId = ppmPrebidReport.getPrbidReportId();
			}
		}
		PpmPrebidReport ppmPrebidReport = (PpmPrebidReport) this.ppmPrebidReportDao.get(prbidReportId);
		
		//将报审清单负责到评审验证中
		getDataListAdd(ppmPrebidReport.getPrbidReportId(),
				reviewVerId,user);
	}
}
