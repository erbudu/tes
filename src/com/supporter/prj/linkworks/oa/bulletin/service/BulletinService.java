package com.supporter.prj.linkworks.oa.bulletin.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.dept_resource.entity.DeptResource;
import com.supporter.prj.dept_resource.service.DeptResourceService;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.emp.entity.IEmployee;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.ExtractFiles;
import com.supporter.prj.linkworks.oa.bulletin.dao.AcccntAccessCountDao;
import com.supporter.prj.linkworks.oa.bulletin.dao.BulletinContentDao;
import com.supporter.prj.linkworks.oa.bulletin.dao.BulletinDao;
import com.supporter.prj.linkworks.oa.bulletin.entity.Bulletin;
import com.supporter.prj.linkworks.oa.bulletin.entity.BulletinContent;
import com.supporter.prj.linkworks.oa.consignment.entity.Consignment;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Service
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Service
public class BulletinService {

	@Autowired
	private BulletinDao bulletinDAO;
	@Autowired
	private BulletinContentDao bulletinContentDAO;
	@Autowired
	private DeptResourceService deptResourceService;
	@Autowired
	private ExtractFiles extractFiles;
	@Autowired
	private AcccntAccessCountDao acccntAccessCountDao ;
	
	/**
	 * 分页表格展示数据.
	 * @param user
	 * @param jqGrid
	 * @param abroad
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Bulletin> getGrid(UserProfile user, JqGrid jqGrid, String attr,String dateFrom,String dateTo,String bulletinType,String bull_type) {
		//修改日期格式
		String fromDate = dateFrom;
		String toDate = dateTo;
		if(StringUtils.isNotBlank(dateFrom)){
			fromDate = dateFrom.substring(0, 10);
		}
		if(StringUtils.isNotBlank(dateTo)){
			toDate = dateTo.substring(0, 10);
		}
		String  resourcehaveAuth=deptResourceService.resourcehaveAuth(user,"canRead");		
		List<Bulletin>  list=this.bulletinDAO.findPage(jqGrid ,attr, fromDate, toDate, bulletinType,resourcehaveAuth,bull_type);
		return list;
		  
	}	
	
	
	/**
	 * 分页表格展示数据.
	 * @param user
	 * @param jqGrid
	 * @param abroad
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Bulletin> getGridNotAuth(UserProfile user, JqGrid jqGrid, String attr,String dateFrom,String dateTo,String bulletinType) {
		//修改日期格式
		String fromDate = dateFrom;
		String toDate = dateTo;
		if(StringUtils.isNotBlank(dateFrom)){
			fromDate = dateFrom.substring(0, 10);
		}
		if(StringUtils.isNotBlank(dateTo)){
			toDate = dateTo.substring(0, 10);
		}
		//String  resourcehaveAuth=deptResourceService.resourcehaveAuth(user,"canRead");		
		List<Bulletin>  list=this.bulletinDAO.findPage(jqGrid ,attr, fromDate, toDate, bulletinType,"","");
		return list;
		  
	}	
	
	
	/**
	 * 分页表格展示数据.
	 * @param user
	 * @param jqGrid
	 * @param abroad
	 * @return
	 */
	public List<Bulletin> iphonePage(UserProfile user, JqGrid jqGrid) {
		
		return bulletinDAO.iphonePage(jqGrid);
	}
	/**
	 * 分页表格展示数据.
	 * @param user
	 * @param jqGrid
	 * @param abroad
	 * @return
	 */
	public List < Bulletin > getTopGrid(UserProfile user, JqGrid jqGrid, int includeConsignmentType) {
		String  resourcehaveAuth=deptResourceService.resourcehaveAuth(user,"canRead");
		List < Bulletin > list = bulletinDAO.findPageOnTopOne(jqGrid,includeConsignmentType,resourcehaveAuth);
		for (int i = 0;i < list.size(); i++){
			Bulletin bulletin = list.get(i);
			bulletin.setBulletinType("[" + bulletin.getBulletinType() + "]");
			list.set(i, bulletin);
		}
		jqGrid.setRows(list);
		return list;
	}
	
	
	/**
	 * 分页表格展示数据.
	 * @param user
	 * @param jqGrid
	 * @param abroad
	 * @return
	 */
	public List < Bulletin > getTopGridNotAuth(UserProfile user, JqGrid jqGrid, int includeConsignmentType) {
		//String  resourcehaveAuth=deptResourceService.resourcehaveAuth(user,"canRead");
		List < Bulletin > list = bulletinDAO.findPageOnTopOne(jqGrid,includeConsignmentType,"");
		for (int i = 0;i < list.size(); i++){
			Bulletin bulletin = list.get(i);
			bulletin.setBulletinType("[" + bulletin.getBulletinType() + "]");
			list.set(i, bulletin);
		}
		jqGrid.setRows(list);
		return list;
	}
	
	/**
	 * 进入新建或编辑需要加载的信息.
	 * 
	 * @param moduleId
	 * @return
	 */
	public Bulletin initEditOrViewPage(String bulletinId, UserProfile user) {
		if (StringUtils.isBlank(bulletinId)) {// 新建
			Bulletin bulletin = newBulletin(user);
			return bulletin;
		} else {// 编辑
			Bulletin bulletin =  bulletinDAO.get(bulletinId);
			if(bulletin.getPublishStatus() ==Bulletin.DRAFT){
				bulletin.setPublishName("草稿");
			}else{
				bulletin.setPublishName("已发布");
			}
			bulletin.setFullDeptResourceName("["+ deptResourceService.getDeptNameAndResourceName(bulletin.getDeptResourceId()) + "]");
			bulletin.setBulletinContent(bulletinContentDAO.get(bulletinId));
			//添加详情页的附件下载
			bulletin.setFiles(getFiles(bulletin));

			return bulletin;
		}
	}
	
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public Bulletin newBulletin(UserProfile auserprf_U){
    	Bulletin bulletin = new Bulletin();
    	bulletin.setBulletinId(com.supporter.util.UUIDHex.newId());
    	bulletin.setCreatorName(auserprf_U.getName());
    	bulletin.setCreatedBy(auserprf_U.getName());
    	bulletin.setCreatedById(auserprf_U.getPersonId());
        bulletin.setCreatedDate(CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        bulletin.setMessageDate(CommonUtil.formatDate(new Date(),"yyyy-MM-dd"));
        bulletin.setModifiedBy(auserprf_U.getName());
        bulletin.setModifierName(auserprf_U.getName());
        bulletin.setModifiedById(auserprf_U.getPersonId());
        bulletin.setModifiedDate(CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        bulletin.setPublishStatus(0);
        bulletin.setPublisherId(auserprf_U.getPersonId());
        bulletin.setPublisherName(auserprf_U.getName());
        bulletin.setDeptId(auserprf_U.getDeptId());
        if(auserprf_U.getDept() != null){
        	bulletin.setDeptName(auserprf_U.getDept().getName());
        }
        if(bulletin.getPublishStatus() ==Bulletin.DRAFT){
			bulletin.setPublishName("草稿");
		}else{
			bulletin.setPublishName("已发布");
		}
        
        bulletin.setIsNew(true);
        return bulletin;
    }

    
    /**
     * 保存更新
     * @param user
     * @param bulletin
     * @param valueMap
     * @return
     */
	public Bulletin saveOrUpdate(UserProfile user, Bulletin bulletin,Map<String, Object> valueMap) {
		//获取相关文件名称
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OABULLETIN", "filesName", bulletin.getBulletinId());
		StringBuffer sb = new StringBuffer();
		for(IFile file:list){
			sb.append(file.getFileName()).append(",");
		}
		if(list !=null && list.size()>0){
			sb.deleteCharAt(sb.length() - 1);
		}
		bulletin.setFileName(sb.toString());
		
		
		Bulletin bulletins = bulletinDAO.get(bulletin.getBulletinId());
		if(bulletins == null){//新建
			if(StringUtils.isNotBlank(bulletin.getDeptResourceId())){
				DeptResource deptResource = deptResourceService.get(bulletin.getDeptResourceId());
				bulletin.setDeptResourceName(deptResource.getResourceName());
			}
			IEmployee emp = EIPService.getEmpService().getEmployee(bulletin.getPublisherId());
	        bulletin.setDeptId(emp.getDeptId());
	        if(emp.getDept() !=null){
	        	bulletin.setDeptName(emp.getDept().getName());
	        }
			bulletinDAO.save(bulletin);
			BulletinContent bulletinContent = bulletin.getBulletinContent();
			bulletinContent.setBulletinId(bulletin.getBulletinId());
			bulletinContentDAO.save(bulletinContent);
			//新建阅读次数对象
			acccntAccessCountDao.newAcccntCount(bulletin);
			
		}else{
			IEmployee emp = EIPService.getEmpService().getEmployee(bulletin.getPublisherId());
	        if(emp != null && emp.getDept() != null){
	        	bulletin.setDeptId(emp.getDeptId());
	        	bulletin.setDeptName(emp.getDept().getName());
	        }
	        if(StringUtils.isNotBlank(bulletin.getDeptResourceId())){
				DeptResource deptResource = deptResourceService.get(bulletin.getDeptResourceId());
				bulletin.setDeptResourceName(deptResource.getResourceName());
			}
	        bulletin.setModifiedBy(user.getName());
	        bulletin.setModifierName(user.getName());
	        bulletin.setModifiedById(user.getPersonId());
	        bulletin.setModifiedDate(CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
	        bulletinDAO.update(bulletin);
	        BulletinContent bulletinContent = bulletinContentDAO.get(bulletin.getBulletinId());
	        if(bulletinContent != null){
	        	bulletinContent.setBulletinContent(bulletin.getBulletinContent().getBulletinContent());
	        	bulletinContentDAO.update(bulletinContent);
	        }else{
	        	bulletinContent.setBulletinId(bulletin.getBulletinId());
	        	bulletinContent.setBulletinContent(bulletin.getBulletinContent().getBulletinContent());
	        	bulletinContentDAO.save(bulletinContent);
	        }
		}
		return bulletin;
	}
	
	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String bulletinIds) {
		if (StringUtils.isNotBlank(bulletinIds)) {
			for (String bulletinId : bulletinIds.split(",")) {
				this.bulletinContentDAO.delete(bulletinId);
				this.bulletinDAO.delete(bulletinId);
				//删除阅读次数对象
				this.acccntAccessCountDao.deleteAcccntCount(bulletinId);
			}
		}
	}
	
	
	
	/**
	 * 判断用户是否拥有操作的权限
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public String isCanOperate(UserProfile user, String bulletinIds,String canTodo) {
		String isCanOperate="no";		
		if(StringUtils.isNotBlank(bulletinIds)){
			if(bulletinIds.indexOf(",")==-1){//说明是一条数据
				Bulletin bulletin=this.bulletinDAO.get(bulletinIds);
				if (bulletin!=null&&StringUtils.isNotBlank(canTodo)) {			
					boolean isHaveAuth=deptResourceService.isHaveAuth(user, bulletin.getDeptResourceId(),canTodo);
					if(isHaveAuth){
						isCanOperate="yes";
					}				
				}
			}else{//说明是多条数据
				for (String bulletinId : bulletinIds.split(",")) {
					if(StringUtils.isNotBlank(bulletinId)){
						Bulletin bulletin=this.bulletinDAO.get(bulletinId);
						if (bulletin!=null&&StringUtils.isNotBlank(canTodo)) {			
							boolean isHaveAuth=deptResourceService.isHaveAuth(user, bulletin.getDeptResourceId(),canTodo);
							if(isHaveAuth){
								isCanOperate="yes";
							}else{
								isCanOperate="no";
								break;
							}				
						}
					}
				}
			}
		}

		return isCanOperate;
	}
	
	
	
	
	/**
	 * 取消发布
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void cancelSubmit(UserProfile user, String bulletinId) {
		if (StringUtils.isNotBlank(bulletinId)) {
			Bulletin bulletin = bulletinDAO.get(bulletinId);
			bulletin.setPublishStatus(Bulletin.DRAFT);
			bulletin.setModifiedBy(user.getName());
	        bulletin.setModifierName(user.getName());
	        bulletin.setModifiedById(user.getPersonId());
	        bulletin.setModifiedDate(CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
	        bulletinDAO.update(bulletin);
		}
	}
	
	/**
	 * 新建发布的授权时，添加到公告板
	 * @param cconsignment
	 */
	public void addConsignment(Consignment consignment ,UserProfile auserprf_U){
		//创建公告对象
		Bulletin bulletin = newBulletin(auserprf_U);
		bulletin.setPublishName("已发布");
		bulletin.setBulletinType("授权公告");
		bulletin.setBulletinTitle("授权书");
		bulletin.setDeptResourceId(deptResourceService.getListByResourceName("授权书公告板").get(0).getResourceId());
		bulletin.setDeptResourceName("授权书公告板");
		bulletin.setPublishStatus(Bulletin.PUBLISHED);
		bulletin.setPublisherId(consignment.getConsignerId());
		bulletin.setPublisherName(consignment.getConsignerName());
		bulletin.setAlwaysOnTop("1");
		bulletin.setRelatedTable(Consignment.class.getName());
		bulletin.setRelatedIdVal(consignment.getConsignmentId());
		IEmployee emp = EIPService.getEmpService().getEmployee(bulletin.getPublisherId());
        bulletin.setDeptId(emp.getDeptId());
        if(emp.getDept() != null){
        	bulletin.setDeptName(emp.getDept().getName());
        }

        //创建公告内容
        String content = "公司领导、设计院及各相关部门：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;本人因" 
        	+ consignment.getConsignReason() + "," + "授权" 
        	+ consignment.getConsigneeName() + "代理本人行使职能，所有签字有效并承担涉及本职务的各项事宜。<br><br>" 
        	+ "授权有效期自" + CommonUtil.formatDate(consignment.getDateFrom(), "yyyy年MM月dd日") + "至" 
        	+ CommonUtil.formatDate(consignment.getDateTo(), "yyyy年MM月dd日") + "。<br><br>" + "&nbsp;&nbsp;&nbsp;&nbsp;" + consignment.getConsignerName();
        BulletinContent bulletinContent = new BulletinContent();
        bulletinContent.setBulletinId(bulletin.getBulletinId());
        bulletinContent.setBulletinContent(content);
        
        //保存
        bulletinDAO.save(bulletin);
        bulletinContentDAO.save(bulletinContent);
        //新建阅读次数对象
		acccntAccessCountDao.newAcccntCount(bulletin);
	}
	
	
	/**
	 * 修改业务授权时，修改公司公告
	 * @param cconsignment
	 * @param auserprf_U
	 */
	public void updateConsignment(Consignment consignment ,UserProfile auserprf_U){
		String consignmentId = consignment.getConsignmentId();
		Bulletin bulletin = bulletinDAO.getByRelatedIdVal(consignmentId);
		if(bulletin != null){
			bulletinDAO.delete(bulletin);
			bulletinContentDAO.delete(bulletin.getBulletinId());
			addConsignment(consignment,auserprf_U);
		}
		
	}
	
	/**
	 * 删除业务授权时，删除公司公告
	 * @param cconsignment
	 * @param auserprf_U
	 */
	public void deleteConsignment(String consignmentId ,UserProfile auserprf_U){
		Bulletin bulletin = bulletinDAO.getByRelatedIdVal(consignmentId);
		if(bulletin != null){
			bulletinDAO.delete(bulletin);
			bulletinContentDAO.delete(bulletin.getBulletinId());
		}
		
	}
	
	
	/**
	 * 封装详情页附件下载部分
	 * @param bulletinId
	 * @return
	 */
	public String getFiles(Bulletin bulletin){
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OABULLETIN", "filesName", bulletin.getBulletinId());
		for(IFile file:list){
			sb.append("<a href=\"javaScript:javascript:downloadFile('"+ file.getFileId() +"');\">" + file.getFileName() +"</a><br/>");
		}
		return sb.toString();
	}
	
	
	/**
	 * 进入查看页面加载数据
	 * @param bulletinId
	 * @return
	 */
	public synchronized Bulletin getInView(String bulletinId, UserProfile user){
		//增加阅读次数
		this.acccntAccessCountDao.updateAcccntCount(bulletinId);
		
		Bulletin bulletin = this.bulletinDAO.get(bulletinId);
		if(bulletin.getPublishStatus() ==Bulletin.DRAFT){
			bulletin.setPublishName("草稿");
		}else{
			bulletin.setPublishName("已发布");
		}
		bulletin.setFullDeptResourceName("["+ deptResourceService.getDeptNameAndResourceName(bulletin.getDeptResourceId()) + "]");
		bulletin.setBulletinContent(bulletinContentDAO.get(bulletinId));
		//添加详情页的附件下载
		bulletin.setFiles(getFiles(bulletin));
		//添加阅读次数
		int readCount = 0;
		if(acccntAccessCountDao.getcount(bulletin.getBulletinId()) != null){
			readCount = acccntAccessCountDao.getcount(bulletin.getBulletinId()).getReadCount();
		};
		bulletin.setReadCount(readCount);
		return bulletin;
	}

	/**
	 * 通过员工id获取部门名称
	 * @param emp
	 * @return
	 */
	public String getDeptName(String emp) {
		String deptName = null;
		IEmployee user = EIPService.getEmpService().getEmployee(emp);
		if(user != null){
			if(user.getDept() != null){
				deptName = user.getDept().getName();
			}
		}
		return deptName;
	}
	
	/**
	 * 授权书类型.
	 * @author linda
	 *
	 */
	public static final class IncludeConsignment{
		public static final int INCLUDE_CONSIGNMENT = 1; //包括授权书
		public static final int NO_CONSIGNMENT = 2; //不包括授权书
		public static final int ONLY_CONSIGNMENT = 3; //仅有授权书
	}
	
	private Bulletin get(String bulletinId) {
		// TODO Auto-generated method stub
		return bulletinDAO.get(bulletinId);
	}
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String extractFiles(String bulletinId, UserProfile userProfile){
		Bulletin report =  this.get(bulletinId);
		return extractFiles.extractFiles(report.getBulletinId(), report.getFileName(),
				"/oa/bulletin_oa/attachment/", "OABULLETIN", "filesName", userProfile);
	}
	



	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String batchExtractFiles(UserProfile userProfile){
		String returnStr = "success";
		List <Bulletin> reportList= bulletinDAO.getBulletinList();
		for(Bulletin report:reportList){
			returnStr = extractFiles.extractFiles(report.getBulletinId(), report.getFileName(),
					"/oa/bulletin_oa/attachment/", "OAARTICLE", "filesName", userProfile);
			
			/*// 记录日志
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
