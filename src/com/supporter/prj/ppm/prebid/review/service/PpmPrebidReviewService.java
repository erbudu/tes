package com.supporter.prj.ppm.prebid.review.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.prebid.report.dao.ProjectReportDao;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReport;
import com.supporter.prj.ppm.prebid.report.service.ProjectReportService;
import com.supporter.prj.ppm.prebid.review.dao.PpmPrebidReviewConDao;
import com.supporter.prj.ppm.prebid.review.dao.PpmPrebidReviewDao;
import com.supporter.prj.ppm.prebid.review.dao.PpmPrebidReviewEmpDao;
import com.supporter.prj.ppm.prebid.review.dao.PpmPrebidReviewEmpRpDao;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReview;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReviewCon;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReviewEmp;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReviewEmpForGrid;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReviewEmpRp;
import com.supporter.prj.ppm.prebid.review_ver.dao.PpmPrebidReviewVerDao;
import com.supporter.prj.ppm.prebid.review_ver.entity.PpmPrebidReviewVer;
import com.supporter.prj.ppm.prebid.util.LogConstant;
import com.supporter.prj.ppm.prj_org.base_info.common.PrjInfo;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.service.PPMService;
import com.supporter.prj.ppm.template_register.dao.TemplateKeyPointsExamDao;
import com.supporter.prj.ppm.template_register.dao.TemplateRegisterDetailDao;
import com.supporter.prj.ppm.template_register.entity.TemplateKeyPointsExam;
import com.supporter.prj.ppm.template_register.entity.TemplateRegisterDetail;
import com.supporter.util.UUIDHex;


/**
 * @author 王康
 *
 */

@Service
@Transactional(TransManager.APP)
public class PpmPrebidReviewService {
	

	@Autowired
	private PpmPrebidReviewDao ppmPrebidReviewDao;
	@Autowired
	private PpmPrebidReviewEmpDao ppmPrebidReviewEmpDao;
	@Autowired
	private PpmPrebidReviewEmpRpDao ppmPrebidReviewEmpRpDao;
	@Autowired
	private PpmPrebidReviewConDao ppmPrebidReviewConDao;
	@Autowired
	private PrjInfo prjInfo;
	@Autowired
	private ProjectReportDao reportDao;
	@Autowired
	private ProjectReportService repotSe;
	@Autowired
	private TemplateRegisterDetailDao templateRegisterDetailDao;
	@Autowired
	private TemplateKeyPointsExamDao templateKeyPointsExamDao;
	@Autowired
	private PpmPrebidReviewVerDao ppmPrebidReviewVerDao;
	/**
	 * 获取投标前评审对象集合
	 * @param user
	 * @param jqGrid
	 * @param ppmPrebidReview
	 * @return
	 */
	public List<PpmPrebidReview> getGrid(UserProfile user, JqGrid jqGrid, PpmPrebidReview ppmPrebidReview) {
		List<PpmPrebidReview> ppmPrebidReviewList = new ArrayList<PpmPrebidReview>();
		ppmPrebidReviewList = this.ppmPrebidReviewDao.findBy("prjId",ppmPrebidReview.getPrjId());
		for (int i = 0; i < ppmPrebidReviewList.size(); i++) {
			String personName = "";
			String personNameT = "";
			if ((ppmPrebidReviewList.get(i)).getPrebidReviewId() != null
					&& !(ppmPrebidReviewList.get(i)).getPrebidReviewId()
							.equals("")) {
				personName = getPersonNames((ppmPrebidReviewList.get(i)).getPrebidReviewId(),PpmPrebidReviewEmp.BUSINESS);
				personNameT = getPersonNames((ppmPrebidReviewList.get(i)).getPrebidReviewId(),PpmPrebidReviewEmp.TECHNOLOGY);
				ppmPrebidReviewList.get(i).setPersonName(personName);
				ppmPrebidReviewList.get(i).setPersonNameT(personNameT);
			}
			
		}
		return ppmPrebidReviewList;
	}
	
	/**
	 * 
	 * 获取personName
	 * @param prjId
	 * @return
	 */
	public String getPersonNames(String prebidReviewId,String reviewRoleId) {
		String empHql = "select personName from " + PpmPrebidReviewEmp.class.getName()
				+ " where prebidReviewId = ? and reviewRoleId = ?";
		List<String> empBList = this.ppmPrebidReviewEmpDao.find(empHql, prebidReviewId,reviewRoleId);
		String personName = "";
		if(empBList != null && empBList.size()>0) {
			for(int i = 0;i<empBList.size();i++) {
				if(personName.equals("")) {
					personName += "" + empBList.get(i) + ""; 
				}else {
					personName += "," + empBList.get(i) + ""; 
				}
			}
		}
		return personName;
	}
	/**
	 * 获取开发小组负责人集合
	 * @param user
	 * @param requestParameters
	 * @return
	 */
	public List<PpmPrebidReviewEmpForGrid> getGridForEmp(UserProfile user, JqGrid jqGrid,
			Map<String, Object> map, String prebidReviewId,String reviewRoleId) {
		//获取某一评审项目某负责人类型的负责人姓名
		List<PpmPrebidReviewEmpForGrid> empData = new ArrayList<PpmPrebidReviewEmpForGrid>();
		String empHql = " from " + PpmPrebidReviewEmp.class.getName()
				+ " where prebidReviewId = ? and reviewRoleId = ?";
		List<PpmPrebidReviewEmp> emplist = this.ppmPrebidReviewEmpDao
				.find(empHql, prebidReviewId,reviewRoleId);
		String reviewEmpId="";
		for (PpmPrebidReviewEmp ppmPrebidReviewEmp : emplist) {
			PpmPrebidReviewEmpForGrid ppmPrebidReviewEmpForGrid  = new PpmPrebidReviewEmpForGrid();
			ppmPrebidReviewEmpForGrid
					.setPersonId(ppmPrebidReviewEmp.getPersonId());
			ppmPrebidReviewEmpForGrid
					.setPersonName(ppmPrebidReviewEmp.getPersonName());
			ppmPrebidReviewEmpForGrid
					.setReviewEmpId(ppmPrebidReviewEmp.getReviewEmpId());
			reviewEmpId = ppmPrebidReviewEmp.getReviewEmpId();
			//获取某一评审项目某负责人类型的负责人评审要点
			String empRpHql = " from " + PpmPrebidReviewEmpRp.class.getName()
					+ " where prebidReviewId = ? and reviewEmpId = ?";
			List<PpmPrebidReviewEmpRp> empRplist = this.ppmPrebidReviewEmpRpDao
					.find(empRpHql, prebidReviewId,reviewEmpId);
			for (PpmPrebidReviewEmpRp ppmPrebidReviewEmpRp : empRplist) {
				ppmPrebidReviewEmpForGrid
						.setReviewPointsId(ppmPrebidReviewEmpRp.getReviewPointsId());
				//System.out.println(ppmPrebidReviewEmpRp.getReviewPointsId());
				String[] reviewPointIds = ppmPrebidReviewEmpRp.getReviewPointsId().split(",");
				String reviewPointId = "";
				String reviewPoint ="";
				for(int i=0;i<reviewPointIds.length;i++) {
					reviewPointId= reviewPointIds[i];
					List<TemplateRegisterDetail> items = this.getDetailListByReviewKeyPoint(reviewPointId);
					if(items != null && !items.isEmpty()) {
						for(TemplateRegisterDetail item : items) {
							reviewPoint += item.getTextDisplay();
						}
					}
		        }
				ppmPrebidReviewEmpForGrid.setReviewPoint(reviewPoint);
			}
			empData.add(ppmPrebidReviewEmpForGrid);
		}
		return empData;
	}
	/**
	 * 获取评审信息
	 * @param prebidReviewId
	 * @return
	 */
	public PpmPrebidReview getDataByPK(String prebidReviewId) {
		PpmPrebidReview ppmPrebidReview = (PpmPrebidReview) this.ppmPrebidReviewDao.get(prebidReviewId);
		return ppmPrebidReview;
	}
	/**
	 * 获取评审结果状态
	 * @param prebidReviewId
	 * @return
	 */
	public PpmPrebidReviewCon getConStatus(String prebidReviewId) {
		List<PpmPrebidReviewCon> ppmPrebidReviewConList = ppmPrebidReviewConDao.findBy("prebidReviewId",prebidReviewId);

		if (CollectionUtils.isNotEmpty(ppmPrebidReviewConList)) {
			return ppmPrebidReviewConList.get(0);
		}
		return null;
	}
	/**
	 * 获取评审结果状态为通过的评审记录
	 * @return
	 */
	public List<PpmPrebidReview> getProForPass() {
		List<String> prebidReviewIdList = ppmPrebidReviewConDao
				.getPrebidReviewIdByStutas();
		if (prebidReviewIdList != null) {
			List<PpmPrebidReview> ppmPrebidReview = new ArrayList<PpmPrebidReview>();
			String hql = " from " + PpmPrebidReview.class.getName()
					+ " where prebidReviewId in(:prebidReviewId)";
			Map < String, Object > param = new HashMap < String, Object > ();
			param.put("prebidReviewId", prebidReviewIdList);
			ppmPrebidReview = ppmPrebidReviewDao.find(hql, param);
			return ppmPrebidReview;
		} else {
			return null;
		}
	}

	public boolean checkPowerAttorney(String prjId) {
		boolean stuta = false;
		List<PpmPrebidReview> prebidReview = ppmPrebidReviewDao.findBy("prjId", prjId);
		for(int i = 0;i<prebidReview.size();i++) {
			String prebidReviewId = prebidReview.get(i).getPrebidReviewId();
			PpmPrebidReviewCon reviewCon = this.getConStatus(prebidReviewId);
			if(reviewCon!=null) {
				Integer con = reviewCon.getRvConStatus();
				if(con==2) {
					stuta = true;
					break;
				}
				if(con==1) {
					List<PpmPrebidReviewVer> verList = ppmPrebidReviewVerDao.findBy("prebidReviewId", prebidReviewId);
					PpmPrebidReviewVer PpmPrebidReviewVer =ppmPrebidReviewVerDao.get(verList.get(0).getReviewVerId());
					Integer result = PpmPrebidReviewVer.getResult();
					if(result.equals(1)||result.equals(2)) {
						stuta = true;
						break;
					}
				}
				
			}
		}
		return stuta;
	}
	/**
	 * 获取评审要点
	 * @param paramMap
	 * @return
	 */
	public List<TemplateRegisterDetail> getDetailListByReviewKeyPoint(String detailId){
		String hql = "from " + TemplateRegisterDetail.class.getName() + " where isActive = 'T' ";
		 //主表过滤
		hql += " and detailId = '"+ detailId +"'";
		//过滤评审要点
		hql += " and isReviewKeyPoint = 'T'";
		hql += " order by displayOrder ";
		return templateRegisterDetailDao.find(hql);
	}
	/**
	 * 当投标前评审被创建
	 * @param user
	 * @return
	 */
	public PpmPrebidReview newPpmReportingStart(String prjId,UserProfile user) {
		PpmPrebidReview ppmPrebidReview = new PpmPrebidReview();
		loadPpmPrebidReview(ppmPrebidReview,prjId, user);
		return ppmPrebidReview;
	}
	
	/**
	 * 加载基础信息
	 * @param ppmPrebidReview
	 * @param user
	 * @return
	 */
	private PpmPrebidReview loadPpmPrebidReview(PpmPrebidReview ppmPrebidReview,String prjId,
			UserProfile user) {
		ppmPrebidReview.setPrebidReviewId(UUIDHex.newId());
		ppmPrebidReview.setReviewTypeId("10");
		ppmPrebidReview.setReviewType("投标前评审");
		ppmPrebidReview.setPrjId(prjId);
		ppmPrebidReview.setAdd(true);
		return ppmPrebidReview;
	}
	
	/**
	 * 初始化对象
	 * @param id
	 * @param user
	 * @return
	 */
	public PpmPrebidReview initEditOrViewPage(String prebidReviewId, String prjId,UserProfile user) {
		PpmPrebidReview ppmPrebidReview;
		if (StringUtils.isNotBlank(prebidReviewId)&&prebidReviewId !="") {
			ppmPrebidReview = (PpmPrebidReview) this.ppmPrebidReviewDao.get(prebidReviewId);
		} else {
			ppmPrebidReview = newPpmReportingStart(prjId,user);
		}

		if (StringUtils.isNotBlank(prjId)&&prjId !="") {
			Prj prjInformation = prjInfo.PrjInformation(prjId);//项目信息
			ppmPrebidReview.setProjectLevel(prjInformation.getProjectLevel());
			PpmPrebidReport report = reportDao.get(repotSe.getPrbidReportIdByPrjId(prjId));
			ppmPrebidReview.setIsNeedGuarantee(report.getIsNeedGuarantee());
		}
		return ppmPrebidReview;
	}
	/**
	 * 初始化对象
	 * @param id
	 * @param user
	 * @return
	 */
	public PpmPrebidReviewCon initEditOrViewConResult(String prebidReviewId,UserProfile user) {
		List<PpmPrebidReviewCon> ppmPrebidReviewConList = this.ppmPrebidReviewConDao.findBy("prebidReviewId",prebidReviewId);
		if(ppmPrebidReviewConList.size()!=0) {
			PpmPrebidReviewCon ppmPrebidReviewCon = new PpmPrebidReviewCon();
			ppmPrebidReviewCon.setPrebidReviewId(prebidReviewId);
			ppmPrebidReviewCon.setChangeStatus(ppmPrebidReviewConList.get(0).getChangeStatus());
			ppmPrebidReviewCon.setPrebidRevConId(ppmPrebidReviewConList.get(0).getPrebidRevConId());
			ppmPrebidReviewCon.setRvConBussinesStatus(ppmPrebidReviewConList.get(0).getRvConBussinesStatus());
			ppmPrebidReviewCon.setRvConStatus(ppmPrebidReviewConList.get(0).getRvConStatus());
			return ppmPrebidReviewCon;
		}
		return null;
	}
	/**
	 * 生产评估编号
	 * （规则：KF+项目代号(5位)+评估性质代码+评估次数）
	 *	评估性质代码：投标前评审-BQ、签约前评审-QY、主合同生效评审-SX
	 *	用户投标前评审（固定为BQ）
	 * @return
	 */
	public synchronized String generatorReviewNo(String prjId,Integer num) {
		String reviewNoTemp = "KF";
		//String oldNo = this.ppmPrebidReviewDao.getMaxReviewNo(reviewNoTemp);
		Prj prjInformation = prjInfo.PrjInformation(prjId);
		String prjNo = prjInformation.getPrjCodeName();//项目代号
		String reviewNo = reviewNoTemp +prjNo+"BQ"+ num;
		return reviewNo;
	}
	/**
	 * saveOrUpdate 保存或更新对象
	 * @param user
	 * @param ppmReportingStart
	 * @param valueMap
	 * @return
	 */
	public PpmPrebidReview saveOrUpdate(UserProfile user, PpmPrebidReview ppmPrebidReview, Map<String, Object> valueMap) {
		if (ppmPrebidReview.getAdd()) {
			ppmPrebidReview.setPrjId(ppmPrebidReview.getPrjId());
			int num =  ppmPrebidReview.getReviewNumber();
			if (StringUtils.isBlank(ppmPrebidReview.getReviewNo())) {
				ppmPrebidReview.setReviewNo(generatorReviewNo(ppmPrebidReview.getPrjId(),num));
			}
			// 保存评估性质编号
			if (StringUtils.isNotBlank(ppmPrebidReview.getReviewTypeId())) {
				Map<String, String> map = PpmPrebidReview.getReviewTypeMap();
				ppmPrebidReview.setReviewType(
						map.get(ppmPrebidReview.getReviewTypeId()));
			}
			// 拟评估项目名称
			Prj prjInformation = prjInfo.PrjInformation(ppmPrebidReview.getPrjId());//项目信息
			ppmPrebidReview.setPrjName(prjInformation.getPrjCName());//项目中文名称
			
			ppmPrebidReview.setPrebidReviewStutas(PpmPrebidReview.DRAFT);
			ppmPrebidReview.setCreatedBy(user.getName());
			ppmPrebidReview.setCreatedId(user.getPersonId());
			ppmPrebidReview.setCreatedDate(new Date());
			ppmPrebidReview.setAdd(false);
			this.ppmPrebidReviewDao.save(ppmPrebidReview);
			PPMService.getScheduleStatusService().saveScheduleStatus(ppmPrebidReview.getPrjId(), "prebid_review01", user);
		} else {
			// 设置更新时间
			ppmPrebidReview.setPrebidReviewStutas(PpmPrebidReview.DRAFT);
			ppmPrebidReview.setModifiedBy(user.getName());
			ppmPrebidReview.setModifiedId(user.getPersonId());
			ppmPrebidReview.setModifiedDate(new Date());
			this.ppmPrebidReviewDao.update(ppmPrebidReview);
		}
		return ppmPrebidReview;
	}
	
	/**
	 * 审批时保存评审结果
	 * @param prebidReviewId
	 * @param isPass
	 * @param rvConBussinesStatus
	 * @param rvConStatus
	 * @return
	 */
	public PpmPrebidReviewCon saveOrUpdateReviewCon( PpmPrebidReviewCon ppmPrebidReviewCon, Map<String, Object> valueMap) {
		if (ppmPrebidReviewCon.getChangeStatus()!=1) {
			ppmPrebidReviewCon.setPrebidRevConId(UUIDHex.newId());
			ppmPrebidReviewCon
					.setPrebidReviewId(ppmPrebidReviewCon.getPrebidRevConId());
			ppmPrebidReviewCon
					.setRvConStatus(ppmPrebidReviewCon.getRvConStatus());
			ppmPrebidReviewCon.setRvConBussinesStatus(
					ppmPrebidReviewCon.getRvConBussinesStatus());
			ppmPrebidReviewCon.setChangeStatus(1);
			ppmPrebidReviewConDao.save(ppmPrebidReviewCon);
		} else {
			ppmPrebidReviewCon
					.setRvConStatus(ppmPrebidReviewCon.getRvConStatus());
			ppmPrebidReviewCon.setRvConBussinesStatus(
					ppmPrebidReviewCon.getRvConBussinesStatus());
			ppmPrebidReviewConDao.update(ppmPrebidReviewCon);
		}
		return ppmPrebidReviewCon;
	}
	/**
	 * 保存并提交
	 * @param user
	 * @param ppmReportingStart
	 * @param valueMap
	 * @return
	 */
	public PpmPrebidReview commit(UserProfile user, PpmPrebidReview ppmPrebidReview, Map<String, Object> valueMap) {
		String logMessage = MessageFormat.format(LogConstant.PUBLISH_LOG_ACTION_REVIEW, "(" + ppmPrebidReview.getPrjId()+ ")" + ppmPrebidReview.getReviewSubmitDate());
		EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS_REVIEW).info(user, LogConstant.PUBLISH_LOG_ACTION_REVIEW, logMessage, ppmPrebidReview, null);
		System.out.println(logMessage);
		return ppmPrebidReview;
	}
	/**
	 * 删除
	 * @param user
	 * @param ids
	 * @return
	 */
	public void delete(UserProfile user, String prebidReviewId) {
		PpmPrebidReview ppmPrebidReview = (PpmPrebidReview) this.ppmPrebidReviewDao.get(prebidReviewId);
		// 删除附件
		//deleteFile(ppmPrebidReview);
		this.ppmPrebidReviewDao.delete(ppmPrebidReview);
		this.ppmPrebidReviewEmpDao.deleteByProperty("prebidReviewId", prebidReviewId);
		this.ppmPrebidReviewEmpRpDao.deleteByProperty("prebidReviewId", prebidReviewId);
		this.deleModel(prebidReviewId);
	}

	/**
	 * 删除相关附件
	 * @param recordFilingId
	 */
	public void deleteFile(PpmPrebidReview ppmPrebidReview){
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList(PpmPrebidReview.MODULE_ID, PpmPrebidReview.BUSI_TYPE, ppmPrebidReview.getPrebidReviewId());
		if (CollectionUtils.isNotEmpty(list)){
			for(IFile file:list){
				fileUploadService.deleteFile(file.getFileId());
			}
		}
	}
	/**
	 * 删除人员
	 * @param user
	 * @param reviewEmpId
	 */
	public void reviewEmpDel(UserProfile user,String reviewEmpId){
		for (String id : reviewEmpId.split(",")) {
			this.ppmPrebidReviewEmpDao.delete(id);
			this.ppmPrebidReviewEmpRpDao.deleteByProperty("prebidReviewId", id);
		}
	}
	/**
	 * 删除模版信息
	 * @param prebidReviewId
	 */
	public void deleModel(String dataId) {
		PPMService.getTemplateRegisterService().delTemplateDataByDataId(dataId);
	}
	/**
	 * 流程更新对象
	 * @param useSeal
	 * @return
	 */
	public PpmPrebidReview update(PpmPrebidReview ppmPrebidReview) {
		ppmPrebidReview.setReviewSubmitDate(new Date());
		this.ppmPrebidReviewDao.update(ppmPrebidReview);
		return ppmPrebidReview;
	}

	/**
	 * 流程结束或中止保存审核状态
	 * @param prebidReviewId
	 * @param isPass
	 * @return
	 */
	public PpmPrebidReviewCon saveCon(String prebidReviewId,Integer isPass,String procId) {
		PpmPrebidReview ppmPrebidReview = this.ppmPrebidReviewDao.get(prebidReviewId);
		if(isPass.equals(20)) {
			List<TemplateKeyPointsExam> pointsList = templateKeyPointsExamDao.findBy("procId", procId);
			if(pointsList.size()>0) {
				for(int i =1;i<pointsList.size()+1;i++) {
					String result = pointsList.get(i-1).getExamResult();
					if(result.equals("F")) {
						this.saveConStatus(i,prebidReviewId,PpmPrebidReviewCon.PROCESSING,PpmPrebidReviewCon.COMPLETED);
						//评审通过 保存进度状态：评审通过 启动用印
						PPMService.getScheduleStatusService().saveScheduleStatus(ppmPrebidReview.getPrjId(), "prebid_review03", null);
						break;
					}else {
						this.saveConStatus(i,prebidReviewId,PpmPrebidReviewCon.COMPLETED,null);
						//评审有条件通过 保存进度状态：评审有条件通过 启动评审验证
						PPMService.getScheduleStatusService().saveScheduleStatus(ppmPrebidReview.getPrjId(), "prebid_review04", null);
					}
				}
			}
		}
		if(isPass.equals(0)) {
			this.saveConStatus(0,prebidReviewId,PpmPrebidReviewCon.DRAFT,null);
			//评审不通过通过 保存进度状态：评审不通过 重新发起评审
			PPMService.getScheduleStatusService().saveScheduleStatus(ppmPrebidReview.getPrjId(), "prebid_review05", null);
		}
		return null;
	}
	/**
	 * 将评审结果保存到评审表
	 * @param prebidReviewId
	 * @param isPass
	 * @param rvConBussinesStatus
	 * @return
	 */
	private PpmPrebidReviewCon saveConStatus(Integer i ,String prebidReviewId,Integer rvConStatus,Integer rvConBussinesStatus) {
		//删除冗余数据再执行保存
		if (i != 0 && i != 1) {//i=0时 为不同意，i=1时为同意时循环的第一条
			this.ppmPrebidReviewConDao.deleteByProperty("prebidReviewId", prebidReviewId);
		}
		PpmPrebidReviewCon ppmPrebidReviewCon = new PpmPrebidReviewCon();
		ppmPrebidReviewCon.setPrebidRevConId(UUIDHex.newId());
		ppmPrebidReviewCon.setPrebidReviewId(prebidReviewId);
		ppmPrebidReviewCon.setRvConStatus(rvConStatus);
		ppmPrebidReviewCon.setRvConBussinesStatus(rvConBussinesStatus);
		ppmPrebidReviewConDao.save(ppmPrebidReviewCon);
		return ppmPrebidReviewCon;
	}
	/**
	 * 获取报审对象
	 * @param id
	 * @return
	 */
	public PpmPrebidReview get(String prebidReviewId) {
		PpmPrebidReview ppmPrebidReview = this.ppmPrebidReviewDao.get(prebidReviewId);
		return ppmPrebidReview;
	}
	/**
	 * 获取某项目下所有审批结果为有条件通过的评审ID
	 * @param prjId 项目id
	 * @return Map<评审ID, 评审编号>
	 */
	public Map<String, String> getVerReview(String prjId) {
		Map<String, String> map = new HashMap<String, String>();
		// 获取某项目下所有审批结果为有条件通过的评审
		List<PpmPrebidReview> reviewIdList = ppmPrebidReviewDao.getConPassReview(prjId);
		if (CollectionUtils.isNotEmpty(reviewIdList)) {
			for (int i = 0; i < reviewIdList.size(); i++) {
				if (reviewIdList.get(i) != null) {
					map.put(reviewIdList.get(i).getPrebidReviewId(), reviewIdList.get(i).getReviewNo());
				}
			}
		}
		return map;
	}
	/**
	 * 根据当前登录人 获取可以看的评审要点
	 * @param userId
	 * @param id
	 * @return
	 */
	public Map<String, String> getPointsForUser(String userId,String id){
		Map<String, String> map = new HashMap<String, String>();
		String hql = " from"+ PpmPrebidReviewEmp.class.getName() + " where prebidReviewId = ? and personId = ?";
		List<PpmPrebidReviewEmp> emplist = ppmPrebidReviewEmpDao.find(hql, id,userId);
		for(int i=0;i<emplist.size();i++) {
			String empId = emplist.get(i).getReviewEmpId();
			List<PpmPrebidReviewEmpRp> empRplist = ppmPrebidReviewEmpRpDao.findBy("reviewEmpId", empId);
			map.put(emplist.get(i).getPersonId(), empRplist.get(0).getReviewPointsId());
		}
		return map;
	}
	
	
	
	
	/**
	 * 根据主键获取评审对象
	 * @param reviewId 评审主键
	 * @return 实体对象
	 */
	public PpmPrebidReview getReview(String prebidReviewId) {
		PpmPrebidReview entity = ppmPrebidReviewDao.get(prebidReviewId);
		return entity;
	}	
	
}
