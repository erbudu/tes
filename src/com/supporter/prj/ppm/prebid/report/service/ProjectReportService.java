package com.supporter.prj.ppm.prebid.report.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.bid_startup.approving.service.ReportResultService;
import com.supporter.prj.ppm.bid_startup.preparation.constant.StartContant;
import com.supporter.prj.ppm.ecc.project_ecc.service.EccService;
import com.supporter.prj.ppm.prebid.report.dao.ProjectReportBfdDao;
import com.supporter.prj.ppm.prebid.report.dao.ProjectReportBfdFDao;
import com.supporter.prj.ppm.prebid.report.dao.ProjectReportDao;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReport;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReportBfd;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReportBfdF;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReportCon;
import com.supporter.prj.ppm.prebid.util.LogConstant;
import com.supporter.prj.ppm.prj_org.base_info.common.PrjInfo;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.service.PPMService;
import com.supporter.util.UUIDHex;
/**
 * @author 王康
 *
 */

@Service
@Transactional(TransManager.APP)
public class ProjectReportService {
	
	@Autowired
	private PrjInfo prjInfo;
	@Autowired
	private ProjectReportDao projectReportDao;
	@Autowired
	private ProjectReportBfdDao bfdDao;
	@Autowired
	private ProjectReportBfdFDao bfdFDao;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private EccService eccService;
	@Autowired
	private ReportResultService reportResultService;
	
	//编辑或查看页面加载数据
	public PpmPrebidReport initEditOrViewPageReport(String prbidReportId,UserProfile user,String prjId) {
		PpmPrebidReport ppmPrebidReport = new PpmPrebidReport();
		if (prbidReportId != null && prbidReportId != "") {
			return getLis(prbidReportId);
		}
		Prj prjInformation = prjInfo.PrjInformation(prjId);//项目信息
		ppmPrebidReport.setProNo(prjInformation.getPrjNo());//项目编号
		ppmPrebidReport.setProNameC(prjInformation.getPrjCName());//项目中文名称
		ppmPrebidReport.setProNameE(prjInformation.getPrjEName());//项目 英文名称
		if (prjInformation.getPrjPlanAmount()!=null&&!"".equals(prjInformation.getPrjPlanAmount())) {
			ppmPrebidReport.setPrjPlanAmount(Double.parseDouble(prjInformation.getPrjPlanAmount()));//项目估算金额
		}
//		List<PpmPrebidReportBfd> dataList = getBfdDateListInit(ppmPrebidReport.getPrbidReportId());//资料清单
//		ppmPrebidReport.setDataList(dataList);
		List<PpmPrebidReport> list = this.projectReportDao.findBy("prjId", prjId);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				PpmPrebidReport report = list.get(i);
				prbidReportId = report.getPrbidReportId();
			}
		}
		if (prbidReportId != null && prbidReportId != "") {
			return getLis(prbidReportId);
		}
		ppmPrebidReport.setPrbidReportId(UUIDHex.newId());
		ppmPrebidReport.setPrjId(prjId);
		ppmPrebidReport.setAdd(true);
		ppmPrebidReport.setPretenderReportStatus(PpmPrebidReport.DRAFT);
		return ppmPrebidReport;
	}
	/**
	 * 获取项目信息及资料清单信息
	 * @param prbidReportId
	 * @return
	 */
	public PpmPrebidReport getLis(String prbidReportId) {
		PpmPrebidReport ppmPrebidReport = new PpmPrebidReport();
		ppmPrebidReport = (PpmPrebidReport) this.projectReportDao
				.get(prbidReportId);
		Prj prjInformation2 = prjInfo.PrjInformation(ppmPrebidReport.getPrjId());//项目信息
		ppmPrebidReport.setProNo(prjInformation2.getPrjNo());//项目编号
		ppmPrebidReport.setProNameC(prjInformation2.getPrjCName());//项目中文名称
		ppmPrebidReport.setProNameE(prjInformation2.getPrjEName());//项目 英文名称
//		List<PpmPrebidReportBfd> dataListView = getDataListEdit(prbidReportId);
//		ppmPrebidReport.setDataList(dataListView);
		return ppmPrebidReport;
	}
	
	/**
	 * 项目是否通过报审
	 * @param prjId
	 * @return
	 */
	public List<PpmPrebidReport> isOrNotReport(String prjId) {
		return projectReportDao.getPrebidReportListByPrjId(prjId);
	}
	
	/**
	 * 根据prjId获取prbidReportId
	 * @param prjId
	 * @return
	 */
	public String getPrbidReportIdByPrjId(String prjId) {
		List<PpmPrebidReport> prebidReportlist = projectReportDao.getPrebidReportListByPrjId(prjId);
		String prbidReportId = "";
		if(prebidReportlist!=null) {
			prbidReportId = prebidReportlist.get(0).getPrbidReportId();
		}
		return prbidReportId;
	}
	/**
	 * saveOrUpdate 保存或更新对象
	 * @param user
	 * @param ppmReportingStart
	 * @param valueMap
	 * @return
	 */
	public PpmPrebidReport saveOrUpdate(UserProfile user,
			PpmPrebidReport ppmPrebidReport, Map<String, Object> valueMap) {
		if (ppmPrebidReport.getAdd()) {
			ppmPrebidReport.setCreatedBy(user.getName());
			ppmPrebidReport.setCreatedId(user.getPersonId());
			ppmPrebidReport.setCreatedDate(new Date());
			ppmPrebidReport.setAdd(false);
			this.projectReportDao.save(ppmPrebidReport);
			PPMService.getScheduleStatusService().saveScheduleStatus(ppmPrebidReport.getPrjId(), "report01", user);
		} else {
			ppmPrebidReport.setModifiedBy(user.getName());
			ppmPrebidReport.setModifiedId(user.getPersonId());
			ppmPrebidReport.setModifiedDate(new Date());
			this.projectReportDao.update(ppmPrebidReport);
		}
		return ppmPrebidReport;
	}
	/**
	 * 直接生效 --测试用
	 * @param user
	 * @param ppmPrebidReport
	 * @param valueMap
	 * @return
	 */
	public PpmPrebidReport passStutas(UserProfile user,
			PpmPrebidReport ppmPrebidReport, Map<String, Object> valueMap) {
		
		PpmPrebidReportCon prebidReportCon = new PpmPrebidReportCon();
		prebidReportCon.setBidingupRvConId(UUIDHex.newId());
		prebidReportCon.setPrbidReportId(ppmPrebidReport.getPrbidReportId());
		prebidReportCon.setRvConStatus(1);
		prebidReportCon.setRvConBussinesStatus(1);
		
		if (ppmPrebidReport.getAdd()) {
			ppmPrebidReport.setPretenderReportStatus(PpmPrebidReport.COMPLETED);
			ppmPrebidReport.setCreatedBy(user.getName());
			ppmPrebidReport.setCreatedId(user.getPersonId());
			ppmPrebidReport.setCreatedDate(new Date());
			ppmPrebidReport.setAdd(false);
			this.projectReportDao.save(ppmPrebidReport);
			PPMService.getScheduleStatusService().saveScheduleStatus(ppmPrebidReport.getPrjId(), "report03", user);
		} else {
			ppmPrebidReport.setPretenderReportStatus(PpmPrebidReport.COMPLETED);
			ppmPrebidReport.setModifiedBy(user.getName());
			ppmPrebidReport.setModifiedId(user.getPersonId());
			ppmPrebidReport.setModifiedDate(new Date());
			this.projectReportDao.update(ppmPrebidReport);
			PPMService.getScheduleStatusService().saveScheduleStatus(ppmPrebidReport.getPrjId(), "report03", user);
		}
		return ppmPrebidReport;
		
	}
	/**
	 * <pre>保存资料清单</pre>
	 * @param PpmPrebidReportBfd 资料清单业务表单实体类
	 * @return 保存成功后的业务表单实体类数据
	 */
	public PpmPrebidReportBfd saveBfd(PpmPrebidReportBfd ppmPrebidReportBfd) {
		if(ppmPrebidReportBfd.getBfdId() == null || ppmPrebidReportBfd.getBfdId() == "" ) {
			return null;
		} 
		PpmPrebidReportBfd entity = bfdDao.findUniqueResult("bfdId", ppmPrebidReportBfd.getBfdId());
		if(entity==null) {
			String codeTable = "PPM_PREBID_REPORT_BFD";
			ppmPrebidReportBfd.setBfdTypeName(ProjectReportService.getBfdTypeName(ppmPrebidReportBfd.getBfdTypeId(),codeTable));
			bfdDao.save(ppmPrebidReportBfd);
		}
		saveBfdF(ppmPrebidReportBfd.getBfdId(),ppmPrebidReportBfd.getPrbidReportId(),ppmPrebidReportBfd.getFileInfo());
		return ppmPrebidReportBfd;
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
		Integer maxDisplayOrder = this.bfdFDao.getMaxDisplayOrder(prbidReportId,bfdId);
		
		PpmPrebidReportBfdF ppmPrebidReportBfdF = new PpmPrebidReportBfdF();
		ppmPrebidReportBfdF.setRecordId(UUIDHex.newId());
		ppmPrebidReportBfdF.setBfdId(bfdId);
		ppmPrebidReportBfdF.setPrbidReportId(prbidReportId);
		ppmPrebidReportBfdF.setFuFileId(fileId);//实际上传附件ID
		ppmPrebidReportBfdF.setDisplayOrder(maxDisplayOrder+1);
		ppmPrebidReportBfdF.setFuFileName(fileName);//实际上传附件的名称(资料清单文件类型对应的文件名称)
		ppmPrebidReportBfdF.setIsUseSeal(0);//是否用印 码表：0否1是
		bfdFDao.save(ppmPrebidReportBfdF);
	}
	/**
	 * 保存并提交
	 * @param user
	 * @param ppmReportingStart
	 * @param valueMap
	 * @return
	 */
	public PpmPrebidReport commit(UserProfile user, PpmPrebidReport ppmPrebidReport, Map<String, Object> valueMap) {
		//判断项目出口管制通过，投标评议许可办理完毕 ----未判断
		if(ppmPrebidReport.getPrjPlanAmount()<500) {
			ppmPrebidReport.setProcId(UUIDHex.newId());
			ppmPrebidReport.setPretenderReportStatus(PpmPrebidReport.COMPLETED);
		}
		this.saveOrUpdate(user,ppmPrebidReport,valueMap);
		PPMService.getScheduleStatusService().saveScheduleStatus(ppmPrebidReport.getPrjId(), "report02", user);
		String logMessage = MessageFormat.format(LogConstant.PUBLISH_LOG_ACTION_PPMREPORTINGSTART, "(" + ppmPrebidReport.getPrbidReportId()+ ")"+ppmPrebidReport.getPrjPlanAmount() + ppmPrebidReport.getReportStratDate());
		EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS_PPMREPORTINGSTART).info(user, LogConstant.PUBLISH_LOG_ACTION_PPMREPORTINGSTART, logMessage, ppmPrebidReport, null);
		System.out.println(logMessage);
		return ppmPrebidReport;
	}
	
	/**
	 * 流程更新对象
	 * @param useSeal
	 * @return
	 */
	public PpmPrebidReport update(PpmPrebidReport ppmPrebidReport) {
		this.projectReportDao.update(ppmPrebidReport);
		return ppmPrebidReport;
	}
	/**
	 * 流程结束或中止保存审核状态
	 * @param prbidReportId
	 * @param isPass
	 * @return
	 */
	public PpmPrebidReportCon saveCon(String prbidReportId,int isPass) {
		PpmPrebidReportCon prebidReportCon = new PpmPrebidReportCon();
		prebidReportCon.setBidingupRvConId(UUIDHex.newId());
		prebidReportCon.setPrbidReportId(prbidReportId);
		if(isPass==PpmPrebidReport.COMPLETED) {
			prebidReportCon.setRvConStatus(1);
			prebidReportCon.setRvConBussinesStatus(1);
		}else {
			prebidReportCon.setRvConStatus(0);
			prebidReportCon.setRvConBussinesStatus(0);
		}
		return prebidReportCon;
	}
	/**
	 * 初始化资料清单列表数据-初始化新建页面
	 * @return 资料清单实体类
	 */
	private List<PpmPrebidReportBfd> getBfdDateListInit(String prbidReportId) {
		List<PpmPrebidReportBfd> list = new ArrayList<PpmPrebidReportBfd>();

		List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PREBID_REPORT_BFD");//资料清单码表
		for (IComCodeTableItem item : items) {
			PpmPrebidReportBfd bfd = new PpmPrebidReportBfd();
			bfd.setBfdTypeId(item.getItemId());
			bfd.setBfdTypeName(item.getItemValue());
			bfd.setPrbidReportId(prbidReportId);
			bfd.setBfdId(com.supporter.util.UUIDHex.newId());
			list.add(bfd);
		}
		return list;
	}
	/**
	 * <pre>功能:初始化资料清单无序列表数据-编辑页面</pre>
	 * <pre>描述：根据资料清单码表ID和启动申报主键获取资料清单数据，获取到数据则返回</pre>
	 * @return 资料清单实体类数据
	 */
	public List<PpmPrebidReportBfd> getDataListEdit(String prbidReportId) {
		List<PpmPrebidReportBfd> list = new ArrayList<PpmPrebidReportBfd>();
		List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PREBID_REPORT_BFD");//码表数据
		for (IComCodeTableItem item : items) {
			List<PpmPrebidReportBfd> ppmPrebidReportBfd = this.getDataListEdit(prbidReportId,item.getItemId());//
			if(ppmPrebidReportBfd.size() == 0 || ppmPrebidReportBfd == null) {
				PpmPrebidReportBfd bfd = new PpmPrebidReportBfd();
				bfd.setBfdTypeId(item.getItemId());
				bfd.setBfdTypeName(item.getItemValue());
				bfd.setPrbidReportId(prbidReportId);
				bfd.setBfdId(com.supporter.util.UUIDHex.newId());
				list.add(bfd);
			}else {
				for(int i= 0;i<ppmPrebidReportBfd.size();i++) {
					PpmPrebidReportBfd bfd = new PpmPrebidReportBfd(); 
					bfd.setBfdId(ppmPrebidReportBfd.get(i).getBfdId());
					bfd.setPrbidReportId(ppmPrebidReportBfd.get(i).getPrbidReportId());
					bfd.setBfdTypeId(ppmPrebidReportBfd.get(i).getBfdTypeId());
					bfd.setBfdTypeName(ppmPrebidReportBfd.get(i).getBfdTypeName());
					list.add(bfd);
				}
			}
		}
		return list;
	}
	/**
	 * <pre>功能:根据启动申报主键和码表中资料清单的ID获取资料清单数据</pre>
	 * <pre>描述:根据启动申报主键和码表资料清单项的id可以确定到对应的而且唯一的，已经保存过的资料清单项</pre>
	 * <pre>调用:1.启动申报编辑页面初始化数据</pre>
	 * @param prbidReportId 资料清单业务表单实体类主键
	 * @param itemId 资料清单id
	 * @return 资料清单业务表单实体类数据
	 */
	public List<PpmPrebidReportBfd> getDataListEdit(String prbidReportId, String bfdTypeId) {
		boolean existed = prbidReportId == null || prbidReportId == "" && bfdTypeId == null || bfdTypeId == "";
		if(existed) {
			return null;
		}
		String hql = "from "+PpmPrebidReportBfd.class.getName()+" where prbidReportId = ? and bfdTypeId = ?";
		List<PpmPrebidReportBfd> list = bfdDao.find(hql,prbidReportId,bfdTypeId);
		return list;
	}
	/**
	 * 获取报审对象
	 * @param id
	 * @return
	 */
	public PpmPrebidReport get(String prbidReportId) {
		PpmPrebidReport ppmPrebidReport = this.projectReportDao.get(prbidReportId);
		//List<PpmPrebidReport> objectList = this.projectReportDao.findBy("prbidReportId", prbidReportId);
		return ppmPrebidReport;
	}
	/**
	 * 通过报审id获取实体对象
	 * @param reportId 协议报审主键
	 * @return 评审验证对象
	 */
	public PpmPrebidReport getRevVerByReportId(String reportId) {
		List<PpmPrebidReport> list = projectReportDao.findBy("reportId", reportId);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
	/**
	 * <pre>判断是否通过出口管制，投议标备案及许可是否办理完毕</pre>
	 * @param prjId
	 * @return 通过都已通过 ：true，未通过false
	 */
	public Boolean checkStatus(String prjId) {
		if (prjId == "" || prjId == null) {
			return null;
		}
		Prj prjInformation = prjInfo.PrjInformation(prjId);
		if (prjInformation.getIsEccConfirm()) { // 判断是否需要进行出口管制确认
			if (eccService.prjIsEccComplete(prjId)) { // 判断出口管制审查是否完毕
				if(reportResultService.reportResult(prjId)) {//判断投议标备案及许可是否办理完毕
					return true;
				}
			}
		} else { //若不需要管制确认 
			if(reportResultService.reportResult(prjId)) {//判断投议标备案及许可是否办理完毕
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 删除报审
	 * @param user
	 * @param ids
	 * @return
	 */
	public void delete(UserProfile user, String prbidReportId) {
		//删除附件
		bfdFDao.deleteByProperty("prbidReportId", prbidReportId);
		bfdDao.deleteByProperty("prbidReportId",prbidReportId);

		PpmPrebidReport ppmPrebidReport = this.projectReportDao.get(prbidReportId);
		deleteFile(ppmPrebidReport,prbidReportId);
		this.projectReportDao.delete(ppmPrebidReport);
		
	}
	/**
	 * 删除相关附件
	 * @param recordFilingId
	 */
	public void deleteFile(PpmPrebidReport ppmPrebidReport,String prbidReportId){
		List<FileUpload> files = new ArrayList<FileUpload>();
		List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PREBID_REPORT_BFD");//码表数据
		for (IComCodeTableItem item : items) {
			List<PpmPrebidReportBfd> ppmPrebidReportBfd = this.getDataListEdit(prbidReportId,item.getItemId());//
			for (int i = 0; i < ppmPrebidReportBfd.size(); i++) {
				files = fileUploadService.getList(PpmPrebidReport.MODULE_ID,PpmPrebidReport.BUSI_TYPE,
						ppmPrebidReportBfd.get(i).getBfdId(), "");
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
	public void deleteBfd(String filesId,String prbidReportId,String bfdTypeId) {
		String[] split = filesId.split(",");
		for (int i = 0;i<split.length;i++) {
			bfdFDao.deleteByProperty("fuFileId", split[i]);
		}
		String hql = "delete " + PpmPrebidReportBfd.class.getName() + " where prbidReportId = ? and bfdTypeId = ?";
		bfdFDao.execUpdate(hql, prbidReportId,bfdTypeId);
	}
	
	public static String getBfdTypeName(String id,String codeTable ){
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(codeTable);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map.get(id);
	}

}
