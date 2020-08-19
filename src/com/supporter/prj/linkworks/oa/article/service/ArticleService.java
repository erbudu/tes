package com.supporter.prj.linkworks.oa.article.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.dept_resource.entity.DeptResource;
import com.supporter.prj.dept_resource.service.DeptResourceService;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.emp.entity.IDeptPostEmp;
import com.supporter.prj.eip_service.emp.entity.IEmployee;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.role.entity.Role;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.ExtractFiles;
import com.supporter.prj.linkworks.oa.article.dao.ArticleContentDao;
import com.supporter.prj.linkworks.oa.article.dao.ArticleDao;
import com.supporter.prj.linkworks.oa.article.dao.VArticleDao;
import com.supporter.prj.linkworks.oa.article.entity.Article;
import com.supporter.prj.linkworks.oa.article.entity.ArticleContent;
import com.supporter.prj.linkworks.oa.article.entity.VArticle;
import com.supporter.prj.linkworks.oa.article.util.CommonUtils;
import com.supporter.prj.linkworks.oa.bulletin.entity.Bulletin;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Service
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Service
public class ArticleService {
	
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private ArticleContentDao articleContentDao;
	@Autowired
	private DeptResourceService deptResourceService;
	@Autowired
	private ExtractFiles extractFiles;
	//资源类型【文章】码表编号
	public static final String RESOURCE_TYPE_CTBL_NO = "OA_ARTICLE_TYPE";
	
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<Article> getGrid(UserProfile user,JqGrid jqGrid,String attr,String dateFrom,String dateTo,String deptResourceId) {				
		String  resourcehaveAuth=deptResourceService.resourcehaveAuth(user,"canRead");
		return  articleDao.findPage(jqGrid,attr,dateFrom,dateTo,deptResourceId,resourcehaveAuth);
	}
	
	
	/**
	 * 分页表格展示数据.(--------没有权限过滤--------)
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<Article> getGridNotAuth(UserProfile user,JqGrid jqGrid,String attr,String dateFrom,String dateTo,String deptResourceId) {				
		//String  resourcehaveAuth=deptResourceService.resourcehaveAuth(user,"canRead");
		return  articleDao.findPage(jqGrid,attr,dateFrom,dateTo,deptResourceId,"");
	}
	
	/**
	 * 获取分组专栏记录.
	 * @return
	 */
	public List < Map < String, Object > > getResourceArticles(){
		List < IComCodeTableItem > items = EIPService.getComCodeTableService().getSubItems(RESOURCE_TYPE_CTBL_NO, "");
		List < Map < String, Object > > list = getCtblItemList(items);
		return list;
	}
	/**
	 * 遍历获取资源码表分类树，以数组列表方式返回.
	 * @param items
	 * @return
	 */
	private List < Map < String, Object > > getCtblItemList(List < IComCodeTableItem > items){
		if (items == null || items.size() == 0)return null;
		
		List < Map < String, Object > > resouceArticles = new ArrayList < Map < String, Object > >();
		for (int i = 0; i < items.size(); i++){
			IComCodeTableItem item = items.get(i);
			boolean showInPortlet = false;
			if(CommonUtil.trim(item.getExtField1()).equals("1"))showInPortlet = true;
			if (!showInPortlet)continue;
			
			Map < String, Object > map = new HashMap < String, Object >();
			map.put("itemId", item.getItemId());
			map.put("displayName", item.getDisplayName());
			int colCount = CommonUtil.parseInt(item.getExtField2(), 1);
			if (colCount <= 0)colCount = 1;
			map.put("colCount", colCount);
			map.put("colWidth", (100 / colCount) + "%");
			List < IComCodeTableItem > subItems = EIPService.getComCodeTableService().getSubItems(RESOURCE_TYPE_CTBL_NO, item.getItemId());
			List < Map < String, Object > > itemsMapList = getCtblItemList(subItems);
			map.put("subItems", itemsMapList);
			if (itemsMapList == null || itemsMapList.size() == 0){
				map.put("hasSubItems", false);
			} else {
				map.put("hasSubItems", true);
			}
			resouceArticles.add(map);
		}
		return resouceArticles;
	}
	
	/**
	 * 进入新建或编辑需要加载的信息.
	 * 
	 * @param moduleId
	 * @return
	 */
	public Article initEditOrViewPage(String articleId, UserProfile user) {
		if (StringUtils.isBlank(articleId)) {// 新建
			Article article = newArticle(user);
			return article;
		} else {// 编辑
			Article article =  articleDao.get(articleId);
			if(article.getPublishStatus() ==Bulletin.DRAFT){
				article.setStatus("草稿");
			}else{
				article.setStatus("已发布");
			}
			article.setFullDeptResourceName("["+ deptResourceService.getDeptNameAndResourceName(article.getDeptResourceId()) + "]");
			article.setArticleContent(articleContentDao.get(articleId));
			//添加详情页的附件下载
			article.setFiles(getFiles(article));

			return article;
		}
	}
	
	
	/**
	 * 封装详情页附件下载部分
	 * @param bulletinId
	 * @return
	 */
	public String getFiles(Article article){
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OAARTICLE", "filesName", article.getArticleId());
		for(IFile file:list){
			sb.append("<a href=\"javascript:downloadFile('"+ file.getFileId() +"');\">" + file.getFileName() +"</a><br/>");
		}
		return sb.toString();
	}
	
	/**
	 * 进入查看页面加载数据
	 * @param bulletinId
	 * @return
	 */
	public synchronized Article getInView(String articleId, UserProfile user){
		
		Article article = this.articleDao.get(articleId);
		if(article.getPublishStatus() ==Article.DRAFT){
			article.setStatus("草稿");
		}else{
			article.setStatus("已发布");
		}
		//添加文章栏
		article.setFullDeptResourceName("["+ deptResourceService.getDeptNameAndResourceName(article.getDeptResourceId()) + "]");
		article.setArticleContent(articleContentDao.get(articleId));
		//添加详情页的附件下载
		article.setFiles(getFiles(article));
		return article;
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
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public Article newArticle(UserProfile auserprf_U){
    	Article article = new Article();
    	article.setArticleId(com.supporter.util.UUIDHex.newId());
    	article.setCreatedBy(auserprf_U.getName());
    	article.setCreatedById(auserprf_U.getPersonId());
    	article.setCreatedDate(CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
    	article.setModifiedBy(auserprf_U.getName());
    	article.setModifiedById(auserprf_U.getPersonId());
    	article.setModifiedDate(CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
    	article.setPublishDate(CommonUtil.formatDate(new Date(),"yyyy-MM-dd"));
    	article.setPublishStatus(Article.DRAFT);
    	article.setPublisherId(auserprf_U.getPersonId());
    	article.setPublisherName(auserprf_U.getName());
    	article.setAlwaysOnTop("F");
    	article.setDeptId(auserprf_U.getDeptId());
        if(auserprf_U.getDept() != null){
        	article.setDeptName(auserprf_U.getDept().getName());
        }
        if(article.getPublishStatus() ==Article.DRAFT){
        	article.setStatus("草稿");
		}else{
			article.setStatus("已发布");
		}
        
        return article;
    }
    
	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String articleIds) {
		if (StringUtils.isNotBlank(articleIds)) {
			for (String articleId : articleIds.split(",")) {
				this.articleDao.delete(articleId);
				this.articleContentDao.delete(articleId);
			}
		}
	}
	
	/**
	 * 取消发布
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void cancelSubmit(UserProfile user, String articleId) {
		if (StringUtils.isNotBlank(articleId)) {
			Article article = articleDao.get(articleId);
			article.setPublishStatus(Bulletin.DRAFT);
			article.setModifiedBy(user.getName());
			article.setModifiedById(user.getPersonId());
			article.setModifiedDate(CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
			articleDao.update(article);
		}
	}
	
	/**
	 * 高级查询中的下拉列表
	 * @return
	 */
	public Map<String, String> getDeptResource(UserProfile user, String canTodo ){
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<DeptResource>   list = deptResourceService.findDeptResourceOfArticleOfCanTodo(user,canTodo);
			map.put("1", "所有栏目");
			if(list!=null){
				if(list.size()>0){
					for (DeptResource deptResource : list) {
						String resourceId = deptResource.getResourceId();
						String resourceName = deptResource.getResourceName();
						map.put(resourceId, resourceName);
					}
				}
			}	
		return map;
	}

	
	/**
	 * 保存更新
	 * @param user
	 * @param article
	 * @return
	 */
	public Article saveOrUpdate(UserProfile user, Article article ) {
		
		//获取相关文件名称
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OAARTICLE", "filesName", article.getArticleId());
		StringBuffer sb = new StringBuffer();
		for(IFile file:list){
			sb.append(file.getFileName()).append(",");
		}
		if(list !=null && list.size()>0){
			sb.deleteCharAt(sb.length() - 1);
		}
		article.setFileName(sb.toString());
		
		Article art = articleDao.get(article.getArticleId());
		if(art == null){//新建
			//添加文章栏
			if(StringUtils.isNotBlank(article.getDeptResourceId())){
				DeptResource deptResource = deptResourceService.get(article.getDeptResourceId());
				article.setDeptResourceName(deptResource.getResourceName());
			}
			//添加发布部门
			IEmployee emp = EIPService.getEmpService().getEmployee(article.getPublisherId());
			article.setDeptId(emp.getDeptId());
	        if(emp.getDept() != null){
	        	article.setDeptName(emp.getDept().getName());
	        }
	        article.setPublishDate(CommonUtils.dateStringSub(article.getPublishDate()));
	        articleDao.save(article);
		}else{//修改
			//添加发布部门
			IEmployee emp = EIPService.getEmpService().getEmployee(article.getPublisherId());
	        if(emp != null && emp.getDept() != null){
	        	article.setDeptId(emp.getDeptId());
	        	article.setDeptName(emp.getDept().getName());
	        }
	        //添加文章栏
	        if(StringUtils.isNotBlank(article.getDeptResourceId())){
				DeptResource deptResource = deptResourceService.get(article.getDeptResourceId());
				article.setDeptResourceName(deptResource.getResourceName());
			}
	        article.setModifiedBy(user.getName());
	        article.setModifiedById(user.getPersonId());
	        article.setModifiedDate(CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
	        article.setPublishDate(CommonUtils.dateStringSub(article.getPublishDate()));
	        articleDao.update(article);
		}
		
		ArticleContent content = articleContentDao.get(article.getArticleId());
		if(content == null){
			content = new ArticleContent();
			content.setArticleId(article.getArticleId());
			if(article.getArticleContent().getArticleContent() != null){
				content.setArticleContent(article.getArticleContent().getArticleContent());
			}
			articleContentDao.save(content);
		}else{
			if(article.getArticleContent().getArticleContent() != null){
				content.setArticleContent(article.getArticleContent().getArticleContent());
			}
			articleContentDao.update(content);
		}
		
		return article;
	}
	
	//171127（没有与部门资源管理授权联系）之前
	public String getTopList(){
		List<Object[]> listDept = deptResourceService.findDeptResourceOfArticle();
		StringBuffer sb = new StringBuffer();
		sb.append("<div>");
		if(listDept!= null){
			if(listDept.size()>0){
				for (Object[] object : listDept) {
					String resourceId = (String) object[0];
					String resourceName = (String) object[1];
					sb.append("<table border=\"0\" width=\"100%\" style=\"color:#0136A2;\" cellspacing=\"0\" cellpadding=\"0\" >");
					sb.append("<tr><td style='width:70%'><b>" + resourceName +"</b></td><td style='width:30%;'><a href=\" JavaScript:javascript:moreArticle('"+ resourceId +"');\" style=\"\">more</a></td></tr>");
					List<Article> list = articleDao.getArticleByResourceId(resourceId,3);
					if(list != null && list.size()>0){
						for(Article article : list){
							sb.append("<tr><td>");
							sb.append("<a href=\" JavaScript:javascript:openView('"+ article.getArticleId() +"');\">");
							sb.append("<img src=\"img/row_icon.gif\"/>&nbsp;&nbsp;");
							sb.append(article.getArticleTitle());
							sb.append("</a>");
							sb.append("</td></tr>");
						}
					}
					sb.append("</table>");
				}
			}
		}	
		sb.append("</div>");
		return sb.toString();
	}
	//系统首页显示文章类型资源用（171128，经过部门资源管理的权限过滤之后）
/*	public String getTopList(UserProfile user){
		//List<Object[]> listDept = deptResourceService.findDeptResourceOfArticle();
		
		List<DeptResource>   list = deptResourceService.findDeptResourceOfArticleOfCanTodo(user,"canRead");
		StringBuffer sb = new StringBuffer();
		sb.append("<div>");
		if(list!= null){
			if(list.size()>0){
				for (DeptResource deptResource : list) {
					String resourceId = deptResource.getResourceId();
					String resourceName =deptResource.getResourceName();
					sb.append("<table border=\"0\" width=\"100%\" style=\"color:#0136A2;\" cellspacing=\"0\" cellpadding=\"0\" >");
					sb.append("<tr><td style='width:70%'><b>" + resourceName +"</b></td><td style='width:30%;'><a href=\" JavaScript:javascript:moreArticle('"+ resourceId +"');\" style=\"\">more</a></td></tr>");
					List<Article> listOfArticle = articleDao.getArticleByResourceId(resourceId,3);
					if(listOfArticle != null && listOfArticle.size()>0){
						for(Article article : listOfArticle){
							sb.append("<tr><td>");
							sb.append("<a href=\" JavaScript:javascript:openView('"+ article.getArticleId() +"');\">");
							sb.append("<img src=\"img/row_icon.gif\"/>&nbsp;&nbsp;");
							sb.append(article.getArticleTitle());
							sb.append("</a>");
							sb.append("</td></tr>");
						}
					}
					sb.append("</table>");
				}
			}
		}	
		sb.append("</div>");
		return sb.toString();
	}*/
	
	
	
	
	/**
	 * 判断用户是否拥有操作的权限
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public String isCanOperate(UserProfile user, String articleIds,String canTodo) {
		String isCanOperate="no";
		
		if(StringUtils.isNotBlank(articleIds)){
			if(articleIds.indexOf(",")==-1){//说明是一条数据
				Article article=this.articleDao.get(articleIds);
				if (article!=null&&StringUtils.isNotBlank(canTodo)) {			
					boolean isHaveAuth=deptResourceService.isHaveAuth(user, article.getDeptResourceId(),canTodo);
					if(isHaveAuth){
						isCanOperate="yes";
					}				
				}
			}else{//说明是多条数据
				for (String articleId : articleIds.split(",")) {
					if(StringUtils.isNotBlank(articleId)){
						Article article=this.articleDao.get(articleId);
						if (article!=null&&StringUtils.isNotBlank(canTodo)) {			
							boolean isHaveAuth=deptResourceService.isHaveAuth(user, article.getDeptResourceId(),canTodo);
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
	public Article get(String id){
		return articleDao.get(id);
	}
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String extractFiles(String articleId, UserProfile userProfile){
		Article report =  this.get(articleId);
		return extractFiles.extractFiles(report.getArticleId(), report.getFileName(),
				"/oa/article/attachment/", "OAARTICLE", "filesName", userProfile);
	}
	
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String batchExtractFiles(UserProfile userProfile){
		String returnStr = "success";
		List <Article> reportList= articleDao.getArticleList();
		for(Article report:reportList){
			returnStr = extractFiles.extractFiles(report.getArticleId(), report.getFileName(),
					"/oa/article/attachment/", "OAARTICLE", "filesName", userProfile);
			
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
