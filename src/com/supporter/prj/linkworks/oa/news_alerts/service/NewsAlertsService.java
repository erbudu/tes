package com.supporter.prj.linkworks.oa.news_alerts.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.emp.entity.IEmployee;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.ExtractFiles;
import com.supporter.prj.linkworks.oa.bulletin.entity.Bulletin;
import com.supporter.prj.linkworks.oa.news_alerts.dao.NewsAlertsContentDao;
import com.supporter.prj.linkworks.oa.news_alerts.dao.NewsAlertsDao;
import com.supporter.prj.linkworks.oa.news_alerts.entity.NewsAlerts;
import com.supporter.prj.linkworks.oa.news_alerts.entity.NewsAlertsContent;
import com.supporter.util.CommonUtil;
import com.supporter.prj.linkworks.oa.news_alerts.util.CommonUtils;

/**   
 * @Title: Service
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Service
public class NewsAlertsService {

	@Autowired
	private NewsAlertsDao newsAlertsDao;
	@Autowired
	private NewsAlertsContentDao newsAlertsContentDao;
	@Autowired
	private ExtractFiles extractFiles;
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param moduleId
	 * @return
	 */
	public NewsAlerts initEditOrViewPage(String newsAlertsId, UserProfile user) {
		if (StringUtils.isBlank(newsAlertsId)) {// 新建
			NewsAlerts newsAlerts = newNewsAlerts(user);
			return newsAlerts;
		} else {// 编辑
			NewsAlerts newsAlerts =  newsAlertsDao.get(newsAlertsId);
			newsAlerts.setNewsAlertsContent(newsAlertsContentDao.get(newsAlertsId));
			IEmployee emp = EIPService.getEmpService().getEmployee(newsAlerts.getCreatedById());
	        if(emp != null && emp.getDept()!= null){
	        	newsAlerts.setDeptName(emp.getDept().getName());
	        }
	        
	        //查看详情页时显示附件
	        newsAlerts.setFilesInView(getFiles(newsAlertsId));
	        newsAlerts.setImages(getImages(newsAlertsId));
	        
			return newsAlerts;
		}
	}
	
	/**
	 * 创建新实例
	 * @param user
	 * @return
	 */
	public NewsAlerts newNewsAlerts(UserProfile user){
		NewsAlerts newsAlerts  = new NewsAlerts();
		newsAlerts.setNewsAlertsId(com.supporter.util.UUIDHex.newId());
		newsAlerts.setCreatedBy(user.getName());
		newsAlerts.setCreatedById(user.getPersonId());
		newsAlerts.setCreatedDate(CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		newsAlerts.setModifiedBy(user.getName());
		newsAlerts.setModifiedById(user.getPersonId());
		newsAlerts.setModifiedDate(CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
//		newsAlerts.setPublishStartDate(CommonUtil.formatDate(new Date(),"yyyy-MM-dd"));
		newsAlerts.setImageWidth(280);
		newsAlerts.setImageHeight(140);
		newsAlerts.setWindowWidth(235);
		newsAlerts.setWindowHeight(60);
		IEmployee emp = EIPService.getEmpService().getEmployee(user.getPersonId());
        if(emp.getDept() !=null){
        	newsAlerts.setDeptName(emp.getDept().getName());
        }
		return newsAlerts;
	}
	
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<NewsAlerts> getGrid(UserProfile user, JqGrid jqGrid, String attr) {
		return newsAlertsDao.findPage(jqGrid, attr);
	}
	
	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String newsAlertsIds) {
		if (StringUtils.isNotBlank(newsAlertsIds)) {
			for (String newsAlertsId : newsAlertsIds.split(",")) {
				this.newsAlertsContentDao.delete(newsAlertsId);
				this.newsAlertsDao.delete(newsAlertsId);
			}
		}
	}

	/**
	 * 保存新建或修改
	 * @param user
	 * @param newsAlerts
	 * @param valueMap
	 * @return
	 */
	public NewsAlerts saveOrUpdate(UserProfile user, NewsAlerts newsAlerts,Map<String, Object> valueMap) {
		NewsAlerts alerts = newsAlertsDao.get(newsAlerts.getNewsAlertsId());
		if(alerts != null){//修改
			NewsAlertsContent content = newsAlertsContentDao.get(alerts.getNewsAlertsId());
			if(content == null){
				content = new NewsAlertsContent();
			}
			content.setNewsAlertsId(alerts.getNewsAlertsId());
			content.setNewsContent(newsAlerts.getNewsAlertsContent().getNewsContent());
			
			alerts.setModifiedById(user.getPersonId());
			alerts.setModifiedBy(user.getName());
			alerts.setModifiedDate(CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
			alerts.setPublishStartDate(CommonUtils.dateStringSub(newsAlerts.getPublishStartDate()));
			alerts.setPublishEndDate(CommonUtils.dateStringSub(newsAlerts.getPublishEndDate()));
			alerts.setNewsTitle(newsAlerts.getNewsTitle());
			alerts.setDisplayType(newsAlerts.getDisplayType());
			alerts.setImageHeight(newsAlerts.getImageHeight());
			alerts.setImageWidth(newsAlerts.getImageWidth());
			alerts.setWindowHeight(newsAlerts.getWindowHeight());
			alerts.setWindowWidth(newsAlerts.getWindowWidth());
			alerts.setPublishStatus(newsAlerts.getPublishStatus());
			
			newsAlertsContentDao.saveOrUpdate(content);
			newsAlertsDao.update(alerts);
			return alerts;
		}else{//新建
			newsAlerts.setModifiedById(user.getPersonId());
			newsAlerts.setModifiedBy(user.getName());
			newsAlerts.setModifiedDate(CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
			newsAlerts.setPublishStartDate(CommonUtils.dateStringSub(newsAlerts.getPublishStartDate()));
			newsAlerts.setPublishEndDate(CommonUtils.dateStringSub(newsAlerts.getPublishEndDate()));
			NewsAlertsContent content = new NewsAlertsContent();
			content.setNewsAlertsId(newsAlerts.getNewsAlertsId());
			content.setNewsContent(newsAlerts.getNewsAlertsContent().getNewsContent());
			newsAlertsContentDao.save(content);
			newsAlertsDao.save(newsAlerts);
			return newsAlerts;
		}
		
	}
	
	/**
	 * 取消发布
	 * @param newsAlertsId
	 * @return
	 */
	public NewsAlerts cancelSubmit(String newsAlertsId){
		NewsAlerts newsAlerts = newsAlertsDao.get(newsAlertsId);
		newsAlerts.setPublishStatus(0);
		newsAlertsDao.update(newsAlerts);
		return newsAlerts;
	}
	
	
	/**
	 * 获取可下载的文件
	 * @param newsAlertsId
	 * @return
	 */
	public String getFiles(String newsAlertsId){
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OANEWSALERTS", "Images", newsAlertsId);
		for(IFile file:list){
			//可下载的附件
			sb.append("<a href=\"javaScript:javascript:downloadFile('"+ file.getFileId() +"');\">" + file.getFileName() +"</a><br/>");
		}
		return sb.toString();
	}
	
	/**
	 * 获取图片id
	 * @param newsAlertsId
	 * @return
	 */
	public List<String> getImages(String newsAlertsId){
		List<String> list = new ArrayList<String>();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> files  = fileUploadService.getFileList("OANEWSALERTS", "Images", newsAlertsId);
		for(IFile file:files){
			//图片附件
			if(file.getFileExt().equalsIgnoreCase("jpg") || file.getFileExt().equalsIgnoreCase("gif")){
				String fileId = file.getFileId();
				list.add(fileId);
			}
		}
		return list;
	}
	
	
	
	
	/**
	 * 获取首页显示新闻快讯(弹出).
	 * @param lna_NewAlerts
	 * @return
	 */
	public String getNewAlertsIndexDisplayTanChu(String newsAlertsId,String path){
		
		NewsAlerts lna_NewAlerts = newsAlertsDao.get(newsAlertsId);
		if(lna_NewAlerts == null || lna_NewAlerts.getWindowWidth() == null ||lna_NewAlerts.getWindowHeight()== null){
			return null;
		}
		String ls_Div ="<div id=\"msn\" onselectstart=\"return false\" onmousedown=dragStart(this,event) ondragstart=\"return false\" style=\"BORDER-RIGHT:#455690 1px solid; BORDER-TOP:#a6b4cf 1px solid; Z-INDEX:99999; right:0px; top:5%; BORDER-LEFT:#a6b4cf 1px solid;BORDER-BOTTOM:#455690 1px solid; POSITION:absolute;BACKGROUND-COLOR:#c9d3f3\">"
			    	 +	"<table  width="+ lna_NewAlerts.getWindowWidth() + " height=" +lna_NewAlerts.getWindowHeight() + " border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"BORDER-TOP:#FFFF60 2px solid;BORDER-RIGHT:#FFFF60 2px solid;BORDER-BOTTOM:#FFFF60 2px solid; BORDER-LEFT:#FFFF60 2px solid\" bgcolor=\"#cfdef4\" >"
			    	 +		"<tr>" 
			    	 +  		"<td height=\"24\" style=\"FONT-SIZE:12px;BACKGROUND-IMAGE:url(../news_alerts/css/images/msgTopBg.gif);COLOR:#0f2c8c\" valign=\"middle\"></td>"
			    	 +  		"<td style=\"FONT-WEIGHT:normal;FONT-SIZE:9pt;BACKGROUND-IMAGE:url(../news_alerts/css/images/msgTopBg.gif);COLOR:#1f336b\" width=\"100%\" align=\"center\"><img src=\"css/images/zd_1.gif\" /><img  src=\"css/images/zd.gif\" /><img src=\"css/images/zd_2.gif\" /></td>"
			    	 +  		"<td style=\"BACKGROUND-IMAGE:url(../news_alerts/css/images/msgTopBg.gif);PADDING-TOP:2px\" valign=\"middle\" width=\"19\" align=\"right\"><img src=\"css/images/msgClose.gif\" hspace=\"3\" style=\"CURSOR:pointer\" onclick=\"closeDiv()\" title=\"关闭\"/></td>"
			    	 +		"</tr>" 
			    	 +		"<tr>" 
			    	 +  		"<td colspan=\"3\"  style=\"PADDING-RIGHT:1px;BACKGROUND-IMAGE:url(../news_alerts/css/images/msgBottomBg.jpg);PADDING-BOTTOM:1px\">"
			    	 +	  		"<div style=\"BORDER-RIGHT: #b9c9ef 1px solid; PADDING-RIGHT: 13px; BORDER-TOP: #728eb8 1px solid; PADDING-LEFT: 13px; FONT-SIZE: 9pt; PADDING-BOTTOM: 13px; BORDER-LEFT: #728eb8 1px solid; WIDTH: 100%; COLOR: #ff0000; PADDING-TOP: 18px; BORDER-BOTTOM: #b9c9ef 1px solid; HEIGHT: 100%\">" 
			    	 +    		"<a style=\"CURSOR:pointer\" href= \"javaScript:javascript:openWindow('"+lna_NewAlerts.getNewsAlertsId()+"');\" ><font color=\"red\">" + CommonUtil.trim(lna_NewAlerts.getNewsTitle()) + "</font></a>"
			    	 + 	  		"</div></div>"
			    	 +			"</td>"
			    	 + 		"</tr>"  
			    	 +	"</table>" 
			    	 +"</div>"
			    	 + "<script>msn.style.top=70;msn.style.left=document.body.clientWidth-" + (lna_NewAlerts.getWindowWidth() + 10) + ";</script>";
    	return ls_Div;
     }
	
	/**
	 * 预览中漂浮显示时，获取图片id
	 * @param newsAlertsId
	 * @return
	 */
	public  String getNewAlertsIndexDisplayPiaofu(String newsAlertsId){
		String fileId = null;
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> files  = fileUploadService.getFileList("OANEWSALERTS", "Images", newsAlertsId);
		for(IFile file:files){
			//获取第一个图片的id
			if(file.getFileExt().equalsIgnoreCase("jpg") || file.getFileExt().equalsIgnoreCase("gif")){
				fileId = file.getFileId();
				break;
			}
		}
		return fileId;
	}
	
	/**
	 * 判断是弹出还是漂浮
	 * @param newsAlertsId
	 * @return
	 */
	public String chooseDisplayType(String newsAlertsId,String path){
		NewsAlerts newsAlerts = newsAlertsDao.get(newsAlertsId);
		if(newsAlerts.getDisplayType() == NewsAlerts.PIAOFU){
			String ls_Div = getNewAlertsIndexDisplayPiaofu(newsAlertsId);
			return ls_Div;
		}else{
			String ls_Div = getNewAlertsIndexDisplayTanChu(newsAlertsId,path);
			return ls_Div;
		}
	}
	
    /**
     * 获取显示Html.
     */
    public  String getNewsAlertsDisplay(JqGrid jqGrid,String path){
    	NewsAlerts newsAlerts = newsAlertsDao.getNewAlerts(jqGrid);
    	if(newsAlerts != null){
    		return chooseDisplayType(newsAlerts.getNewsAlertsId(), path);
    	}else{
    		return null;
    	}
    	
    }

    /**
     * 首页获取新闻快讯对象
     * @param jqGrid
     * @return
     */
	public NewsAlerts getNewsAlerts(JqGrid jqGrid) {
		NewsAlerts newsAlerts = newsAlertsDao.getNewAlerts(jqGrid);
		if(newsAlerts != null){
			return newsAlerts;
		}
		return null;
	}
	public NewsAlerts get(String id){
		return newsAlertsDao.get(id);
	}
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String extractFiles(String id, UserProfile userProfile){
		NewsAlerts report =  this.get(id);
		return extractFiles.extractUtilFiles(report.getNewsAlertsId(), "oa_news_alerts",
				"/oa/news_alerts/files/", "OANEWSALERTS", "Images", userProfile);
	}
	



	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String batchExtractFiles(UserProfile userProfile){
		String returnStr = "success";
		List <NewsAlerts> reportList= newsAlertsDao.getNewsAlertsList();
		for(NewsAlerts report:reportList){
			return extractFiles.extractUtilFiles(report.getNewsAlertsId(), "oa_news_alerts",
					"/oa/news_alerts/files/", "OANEWSALERTS", "Images", userProfile);
			
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
