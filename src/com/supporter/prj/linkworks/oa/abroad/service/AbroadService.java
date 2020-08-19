package com.supporter.prj.linkworks.oa.abroad.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.SimpleDateFormat;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.dao.PersonAdminDutiesInfoDao;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.dao.PersonBasicInfoDao;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.dao.PersonBirthPlaceDao;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.PersonBasicInfo;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.dept.service.TreeNodeService;
import com.supporter.prj.eip.online_user.util.ExportUtils;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.emp.entity.IDeptPostEmp;
import com.supporter.prj.eip_service.emp.entity.IEmployee;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.ExtractFiles;
import com.supporter.prj.linkworks.oa.abroad.constants.AbroadAuthConstant;
import com.supporter.prj.linkworks.oa.abroad.dao.AbroadDAO;
import com.supporter.prj.linkworks.oa.abroad.dao.AbroadPersonDAO;
import com.supporter.prj.linkworks.oa.abroad.entity.Abroad;
import com.supporter.prj.linkworks.oa.abroad.entity.AbroadPerson;
import com.supporter.prj.linkworks.oa.abroad.util.AbroadAuthUtil;
import com.supporter.prj.linkworks.oa.abroad.util.ConvertUtils;
import com.supporter.prj.linkworks.oa.abroadPublicity.dao.AbroadBackDao;
import com.supporter.prj.linkworks.oa.abroadPublicity.dao.AbroadPublicityDao;
import com.supporter.prj.linkworks.oa.abroadPublicity.entity.AbroadBack;
import com.supporter.prj.linkworks.oa.abroadPublicity.entity.AbroadPublicity;
import com.supporter.prj.linkworks.oa.abroadPublicity.service.AbroadPublicityService;
import com.supporter.util.CommonUtil;
/**   
 * @Title: Service
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Service
public class AbroadService {

	@Autowired
	private AbroadDAO abroadDAO;
	@Autowired
	private AbroadPersonDAO abroadPersonDAO;
	@Autowired
	private TreeNodeService treeNodeService;
	@Autowired
	private AbroadPublicityService abroadPublicityService;
	@Autowired
	private AbroadPublicityDao abroadPublicityDao;
	@Autowired
	private ExtractFiles extractFiles;
	@Autowired
	private AbroadBackDao abroadBackDao;
	
	@Autowired
	private PersonBasicInfoDao personBasicInfoDao;
	@Autowired
	private PersonAdminDutiesInfoDao personAdminDutiesInfoDao;
	@Autowired
	private PersonBirthPlaceDao personBirthPlaceDao;
	/**
	 * 分页表格展示数据.
	 * @param user
	 * @param jqGrid
	 * @param abroad
	 * @return
	 */
	public List<Abroad> getGrid(UserProfile user, JqGrid jqGrid, String attr) {
		return abroadDAO.findPage(user,jqGrid, attr);
	}
	
	
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public Abroad initEditOrViewPage(String recordId, UserProfile user) {
		if (StringUtils.isBlank(recordId)) {// 新建
			Abroad abroad = newAbroad(user);
			abroad.setIsNew(true);
			return abroad;
		} else {// 编辑
			Abroad abroad = abroadDAO.get(recordId);
			//获取邀请函
			IFileUploadService fileUploadService = EIPService.getFileUploadService();
			List<IFile> list  = fileUploadService.getFileList("OAABROAD", "InvitationFile", recordId);
			if(list !=null && list.size()>0){
				abroad.setFileId(list.get(0).getFileId());
				abroad.setFileName(list.get(0).getFileName());
				
			}
//			//获取分管领导
//			abroad.setEmpId(abroad.getCompanyLeaderId());
//			abroad.setEmpName(abroad.getCompanyLeaderName());
			abroad.setIsNew(false);
			abroad.setMaterialList(abroadPersonDAO.getForeignByAbroadId(recordId));
			//获取内部出国人员
			List<AbroadPerson> listInner = abroadPersonDAO.getEmployeesName(recordId);
			if(CollectionUtils.isNotEmpty(listInner)){
				StringBuffer sb = new StringBuffer();
				StringBuffer sbb = new StringBuffer();
				for(AbroadPerson abroadPerson : listInner){
					sb.append(abroadPerson.getPersonName()).append(",");
					sbb.append(abroadPerson.getPersonId()).append(",");
				}
				sb.deleteCharAt(sb.length() - 1);
				sbb.deleteCharAt(sbb.length() - 1);
				abroad.setEmpNames(sb.toString());
				abroad.setEmpSysNos(sbb.toString());
			}
			
			return abroad;
		}
	}
	
	
	/**
     * 新建实例,并初始化必要的属性.
     * @param user
     * @return
     */
	public Abroad newAbroad(UserProfile user){
		Abroad abroad = new Abroad();
		abroad.setRecordId(com.supporter.util.UUIDHex.newId());
		abroad.setApplierId(user.getPersonId());
		abroad.setApplierName(user.getName());
		abroad.setApplierDeptId(user.getDeptId());
		Dept dept = user.getDept();
		if(dept !=null){
			abroad.setApplierDept(dept.getName());
		}
		abroad.setApplyDate(ConvertUtils.dateString());
		return abroad;
	}
	
	/**
	 * 分页表格展示全体出国人员.
	 * @param jqGrid
	 * @param flag
	 * @param recordId
	 * @return
	 */
	public List<AbroadPerson> getRecGrid(UserProfile user,JqGrid jqGrid,String recordId) {
		return abroadPersonDAO.getRecGrid(jqGrid,recordId);
	}
	
	/**
	 * 分页表格展示外部出国人员.
	 * @param jqGrid
	 * @param flag
	 * @param recordId
	 * @return
	 */
	public List<AbroadPerson> getForeignGrid(UserProfile user,JqGrid jqGrid,AbroadPerson ap,String recordId) {
		return abroadPersonDAO.getForeignGrid(jqGrid, ap,recordId);
	}
	

	/**
	 * 保存或修改目录
	 * @param user
	 * @param abroad
	 * @param valueMap
	 * @return
	 */
	public Abroad saveOrUpdate(UserProfile user,Abroad abroad, Map<String, Object> valueMap){
		
		List<AbroadPerson> list = new ArrayList<AbroadPerson>();
		//删除原有出国人员
		abroadPersonDAO.deleteInner(abroad);
		
		if(StringUtils.isNotBlank(abroad.getEmpSysNos())){//将内部人员加入出国人员
			
			for (String empNO : abroad.getEmpSysNos().split(",")) {
				IEmployee emp = EIPService.getEmpService().getEmployee(empNO);
				AbroadPerson abroadPerson = new AbroadPerson();
				if(emp != null && emp.getName()!= null){
					String idCrad = emp.getIdCard();
					if(idCrad!=null){
						PersonBasicInfo personBasicInfo = personBasicInfoDao.get(idCrad);
						//性别 
						abroadPerson.setPersonGender(personBasicInfo.getSex());
						//生日
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date birth = personBasicInfo.getBirthDate();
						if(birth!=null){
							abroadPerson.setBirthDate(sdf.format(birth));
						}
						//获取出生地
						String personBirthPlaceJosn = personBirthPlaceDao.getPersonBirthPlaceJosn(CommonUtil.trim(personBasicInfo.getEmpId()));
						personBirthPlaceJosn="{"+personBirthPlaceJosn+"}";
						JSONObject birthPlaceJosn = JSONObject.fromObject(personBirthPlaceJosn);
						if(birthPlaceJosn!=null){
							abroadPerson.setBirthPlace(birthPlaceJosn.getString("realBirthPlace"));
						}
						//职务
						String zhiwu = personAdminDutiesInfoDao.getPersonAdminDutiesInfoJosn(CommonUtil.trim(personBasicInfo.getEmpId()));
						zhiwu = "{"+zhiwu+"}";
						JSONObject json = JSONObject.fromObject(zhiwu);
						if(json!=null){
							abroadPerson.setPositionTitle(json.getString("adminDuties"));
						}else{
							List<IDeptPostEmp> listPost = EIPService.getEmpAssignService().findDeptPostEmpsByEmpId(empNO);
					        for(IDeptPostEmp deptPostEmp : listPost){
					        	int nodeType = treeNodeService.getRootByTreeId(deptPostEmp.getTreeNodeId()).getNodeType();
					        	//deptPostEmp.getTreeNodeId()是部门或单位ID
					        	if( nodeType == 3 || deptPostEmp.getPostNo() != null){
					        		abroadPerson.setPositionTitle(deptPostEmp.getPostName());//职务
					        	}
					        }		
						}
					}
					abroadPerson.setWorkingUnit("中国电力工程有限公司");
					abroadPerson.setPersonName(emp.getName());					
				}
				abroadPerson.setPersonType(1);
				abroadPerson.setPersonId(empNO);
				//添加职务
				
					
			
		        list.add(abroadPerson);	
			}
		}
		
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> files  = fileUploadService.getFileList("OAABROAD", "InvitationFile", abroad.getRecordId());
//		if(files !=null && files.size()>0){
//			abroad.setInvitation(files.get(0).getFileName());
//		}
		Abroad ab = abroadDAO.get(abroad.getRecordId());
		String leavingDate = abroad.getLeavingDate();
		if (StringUtils.isNotBlank(leavingDate)) {
			abroad.setLeavingDate(leavingDate.substring(0, 10));
		}
		if(ab == null){//新建
			abroad.setPrjId(com.supporter.util.UUIDHex.newId());
			if(StringUtils.isBlank(abroad.getPassedCountries())){
				abroad.setPassedCountries("无");
			}
			this.abroadDAO.save(abroad);
		}else{//修改
			deleteEmployees(abroad.getDelIds());
			this.abroadDAO.update(abroad);
		}
		
		//保存出国人员
		if(CollectionUtils.isNotEmpty(abroad.getMaterialList())){
			list.addAll(abroad.getMaterialList());
		}
		abroad.setMaterialList(list);			
		if(CollectionUtils.isNotEmpty(abroad.getMaterialList())){
			saveMaterialList(abroad);
		}
		
		return abroad;
	}
	
	/**
	 * 通过出国人员id集合删除权限
	 * @param delIds
	 */
	public void deleteEmployees(String delIds) {
		if (StringUtils.isNotBlank(delIds)) {
			for (String delId : delIds.split(",")) {
				AbroadPerson ap = this.abroadPersonDAO.get(delId);
				if (ap != null) {
					this.abroadPersonDAO.delete(delId);
				}
			}
		}
	}
	
	/**
	 * 保存出国员工
	 * @param abroad
	 */
	public void saveMaterialList(Abroad abroad){
		List<AbroadPerson> materialList = abroad.getMaterialList();
		if(CollectionUtils.isNotEmpty(abroad.getMaterialList())){
			for(AbroadPerson ap:materialList){
				ap.setRecordId(com.supporter.util.UUIDHex.newId());
				ap.setAbroadRecordId(abroad.getRecordId());
				String birthday = ap.getBirthDate();
				if(StringUtils.isNotBlank(birthday)){
					ap.setBirthDate(birthday.substring(0,10));
				}
				if(ap.getPersonType()== null || ap.getPersonType()<1){
					ap.setPersonType(0);
				}
				String name = ap.getPersonName();
				if(StringUtils.isNotBlank(name)){
					this.abroadPersonDAO.save(ap);
				}
				
			}
			
		}
	}
	
	/**
	 * 保存出国公示
	 * @param abroadPublicity
	 */
	public void saveAbroadPublicity(AbroadPublicity abroadPublicity){
		abroadPublicity.setPubId(com.supporter.util.UUIDHex.newId());
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		//临时将公示天数设置为五天，加入流程后修改
//		int pubDay = abroadPublicity.getPubDay();
		int pubDay = abroadPublicity.getPubDay() - 1;
		Date d=new Date();   
		abroadPublicity.setPubStartDate(df.format(d));
		abroadPublicity.setPubEndDate(df.format(new Date(d.getTime() + pubDay * 24 * 60 * 60 * 1000)));
		this.abroadPublicityDao.save(abroadPublicity);
	}
	
	
	/**
	 * 发布公示
	 */
	public void submitPub(String recordId,String publicityDay,String ifPublicity){
		//发布公示
		int pub = 0;
		int pubDay = 0;
		if(StringUtils.isNotBlank(ifPublicity)){
			pub = Integer.parseInt(ifPublicity);
		}
		if(StringUtils.isNotBlank(publicityDay)){
			pubDay = Integer.parseInt(publicityDay);
		}
		if(pub == Abroad.YESPUBLICITY){
			AbroadPublicity abroadPublicity = new AbroadPublicity();
			if(abroadPublicityService.getByAbroadId(recordId)== null){
				abroadPublicity.setAbroadId(recordId);
				abroadPublicity.setPubDay(pubDay);
				saveAbroadPublicity(abroadPublicity);
				
			}
			//出国审批修改是否公示
			Abroad abroad = this.abroadDAO.get(recordId);
			if(abroad != null){
				abroad.setIfPublicity(Abroad.YESPUBLICITY);
				abroad.setPublicityDay(pubDay);
				this.abroadDAO.update(abroad);
			}
		}
	}
	
	/**
	 * 通过id获取Abroad
	 * @param recordId
	 * @return
	 */
	public Abroad getById(String recordId){
		Abroad abroad = this.abroadDAO.get(recordId);
		return abroad;
	}
	
	/**
	 * 删除出国审批
	 * @param recordId
	 */
	public void batchDel(UserProfile user,String recordId){
		Abroad abroad = this.abroadDAO.get(recordId);
		AbroadAuthUtil.canExecute(user, AbroadAuthConstant.AUTH_OPER_NAME_SETVALABROAD, recordId, abroad);
		List<AbroadPerson> list = this.abroadPersonDAO.getManagerByAbroadId(recordId);
		if(list != null && list.size() > 0){
			for(AbroadPerson abroadPerson:list){
				this.abroadPersonDAO.delete(abroadPerson);
			}
		}		
		if(abroad !=null){
			this.abroadDAO.delete(abroad);
			AbroadPublicity abroadPublicity = abroadPublicityService.getByAbroadId(recordId);
			if(abroadPublicity!=null){
				//删除出国审批同时删除出国公示和出国报告，加入流程后修改
				AbroadBack abroadBack = abroadBackDao.getByPubId(abroadPublicity.getPubId());
				abroadPublicityService.delete(abroadPublicity);
				if(abroadBack != null){
					abroadBackDao.delete(abroadBack);
				}
			}
		}
	}
	
	/**
	 * 撤回出国审批
	 * @param recordId
	 */
	public void returnBack(String recordId){
		Abroad abroad = this.abroadDAO.get(recordId);
		abroad.setRecordStatus(0);
		abroad.setProcId("");
		if(abroad !=null){
			this.abroadDAO.update(abroad);
			AbroadPublicity abroadPublicity = abroadPublicityService.getByAbroadId(recordId);
			if(abroadPublicity!=null){
				//删除出国审批同时删除出国公示和出国报告，加入流程后修改
				AbroadBack abroadBack = abroadBackDao.getByPubId(abroadPublicity.getPubId());
				abroadPublicityService.delete(abroadPublicity);
				if(abroadBack != null){
					abroadBackDao.delete(abroadBack);
				}
			}
		}
	}
	
	
	
	/**
	 * 通过出国审批id获取出国公示id
	 * @param recordId
	 * @return
	 */
	public String getPubByAbroadId(String recordId){
		String pubId = null;
		String hql = " from "+AbroadPublicity.class.getName()+ " where abroadId = '"+recordId+"'";
		List<AbroadPublicity> list = this.abroadPublicityDao.find(hql);
		if(list!=null &&list.size()>0){
			AbroadPublicity abroadPublicity =list.get(0);
			pubId = abroadPublicity.getPubId();
		}
		return pubId;
	}
	
	
	/**
	 * 回国报告审批流程初始化加载出国审批
	 * @param backId
	 * @param user
	 * @return
	 */
	public Abroad initAbroad(String backId,UserProfile user){
		AbroadBack abroadBack = this.abroadBackDao.get(backId);
		if(abroadBack == null){
			return null;
		}
		AbroadPublicity abroadPublicity = this.abroadPublicityDao.get(abroadBack.getPubId());
		if(abroadPublicity == null){
			return null;
		}
		Abroad abroad = initEditOrViewPage(abroadPublicity.getAbroadId(),user);
		return abroad;
	}
	
	
	/**
	 * 导出
	 * @param codeIds
	 * @return
	 */
	public HSSFWorkbook getWorkbook(String codeIds) {
		String excelFileName = "AbroadEXP.xls";
		HSSFWorkbook wb = null;
		try {
			ExportUtils util = new ExportUtils();
			File file = util.getExcelDemoFile(File.separator
					+ "template_excel_def" + File.separator + excelFileName);
			wb = util.getExcelWorkbook(file);

			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setBorderBottom((short) 1);
			cellStyle.setBorderLeft((short) 1);
			cellStyle.setBorderTop((short) 1);
			cellStyle.setBorderRight((short) 1);
            //第一个sheet,导出主表
			List<Abroad> list = new ArrayList<Abroad>();
			if (StringUtils.isNotBlank(codeIds)) {
				for (String codeId : codeIds.split(",")) {
					Abroad codeDb = this .getById(codeId);
					if (codeDb == null) {
						continue;
					}
					list.add(codeDb);
				}
				if ((list != null) && (list.size() != 0)) {
					int length = list.size();
					HSSFSheet sheet = wb.getSheetAt(0);

					HSSFRow row = null;
					HSSFCell cell = null;
					for (int i = 0; i < length; i++) {
						row = sheet.createRow(i + 2);
						
						cell = row.createCell(0, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getApplierDept());// 申请单位
						cell.setCellStyle(cellStyle);
						
						cell = row.createCell(1, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getPrjName());// 项目名称
						cell.setCellStyle(cellStyle);						
						
						cell = row.createCell(2, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getPrjChar());// 项目性质
						cell.setCellStyle(cellStyle);

						cell = row.createCell(3, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getPeopleCount());// 出国人数
						cell.setCellStyle(cellStyle);

						cell = row.createCell(4, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getLeavingDate());// 出国时间
						cell.setCellStyle(cellStyle);

						cell = row.createCell(5, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getPassedCountries());// 途径国家和地区
						cell.setCellStyle(cellStyle);

						cell = row.createCell(6, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getTgtCountries());// 前往国家或地区
						cell.setCellStyle(cellStyle);

						cell = row.createCell(7, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getStayingPeriod());// 境外停留时间
						cell.setCellStyle(cellStyle);

						cell = row.createCell(8, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getExpenseSource());// 费用来源
						cell.setCellStyle(cellStyle);
						
						cell = row.createCell(9, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getAbroadUnit());// 境外主要接待单位
						cell.setCellStyle(cellStyle);

						cell = row.createCell(10, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getOwnerName());// 业主名称
						cell.setCellStyle(cellStyle);


						cell = row.createCell(11, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getInvestorName());// 投资方
						cell.setCellStyle(cellStyle);


						cell = row.createCell(12, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getInvestmentAmount());// 投资额
						cell.setCellStyle(cellStyle);


						cell = row.createCell(13, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getConstructLocation());// 工程地点
						cell.setCellStyle(cellStyle);


						cell = row.createCell(14, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getConstructScale());// 工程规模
						cell.setCellStyle(cellStyle);


						cell = row.createCell(15, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getConstructTime());//拟于何时建设
						cell.setCellStyle(cellStyle);


						cell = row.createCell(16, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getConstructPart());// 我公司承担的部分
						cell.setCellStyle(cellStyle);


						cell = row.createCell(17, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getPrjPhase());// 项目所处阶段
						cell.setCellStyle(cellStyle);


						cell = row.createCell(18, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getTaskDesc());// 出国任务描述
						cell.setCellStyle(cellStyle);


						cell = row.createCell(19, 1);
						cell.setCellValue(((Abroad) list.get(i))
								.getPrjIntro());// 项目简介
						cell.setCellStyle(cellStyle);

					}
										
				}
								
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}


	public Abroad get(String recordId) {
		Abroad abroad = this.abroadDAO.get(recordId);
		return abroad;
	}


	public void update(Abroad abroad) {
		if(abroad != null){
			this.abroadDAO.update(abroad);
		}
		
	}
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String extractFiles(String recordId, UserProfile userProfile){
		Abroad report =  this.get(recordId);
		return extractFiles.extractFiles(report.getRecordId(), report.getInvitation(),
				"/oa/abroad/file/", "OAABROAD", "InvitationFile", userProfile);
	}
	
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String batchExtractFiles(UserProfile userProfile){
		String returnStr = "success";
		List <Abroad> reportList= abroadDAO.getReportList();
		for(Abroad report:reportList){
			returnStr = extractFiles.extractFiles(report.getRecordId(), report.getInvitation(),
					"/oa/abroad/file/", "OAABROAD", "InvitationFile", userProfile);
			
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
	
}
