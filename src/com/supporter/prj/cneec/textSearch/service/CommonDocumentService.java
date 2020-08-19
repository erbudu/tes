package com.supporter.prj.cneec.textSearch.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.supporter.prj.cneec.textSearch.dao.DataFileDao;
import com.supporter.prj.cneec.textSearch.dao.FolderDao;
import com.supporter.prj.cneec.textSearch.entity.CommonDocument;
import com.supporter.prj.cneec.textSearch.entity.DataFile;
import com.supporter.prj.cneec.textSearch.entity.Filebox;
import com.supporter.prj.cneec.textSearch.entity.Folder;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bulletin.service.BulletinService;
import com.supporter.util.CommonUtil;
@Service
public class CommonDocumentService {
	
	private LuceneService luceneService;
	@Autowired
	private LuceneServiceByNoFilebox luceneServiceByNoFilebox;
	@Autowired
	private DataFileDao dataFileDao;
	@Autowired
	private FolderDao folderDao;
	@Autowired
	private BulletinService bulletinService;
	//每页显示的记录条数
//	private static  int PAGE_CONTAIN = 20;
	
	//在核心词前显示的字数
	private static  int CENTER_BEFORE = 50;
	
	//在核心词后显示的字数
	private static  int CENTER_AFTER = 50;
	private static String dirPath;
	
	public static String getDirPath() {
//		  return ServletActionContext.getServletContext().getRealPath(File.separator);
		if(dirPath != null)return dirPath;
		String absPath = CommonDocumentService.class.getResource("").getPath();
//		System.out.println("absPath:" + absPath);
		String needPath = absPath.split("WEB-INF")[0];
		//判断是windows系统还是linux系统
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		if(os.startsWith("win") || os.startsWith("Win")){
			//是windows系统
			dirPath = new String(needPath.toCharArray(),1,needPath.toCharArray().length - 1);
		}else{
			dirPath = needPath;
		}
//		System.out.println("dirPath:" + dirPath);
		return dirPath;
	}
	
	//以下是索引的存放位置
	//全部的
	public static String getIndex(){
		return getDirPath() + "/data/supp/kl/index";
	}
	//主题新闻
	public static String getIndex_News(){
		return getDirPath() + "/data/supp/kl/index/index_news";
	}
	//公司公告
	public static String getIndex_Bulletin(){
		return getDirPath() + "/data/supp/kl/index/index_bulletin";
	}
	//文章栏
	public static String getIndex_Article(){
		return getDirPath() + "/data/supp/kl/index/index_article";
	}
	//文件柜
	public static String getIndex_Filebox(){
		return getDirPath() + "/data/supp/kl/index/index_filebox";
	}
//	//	全部文件的索引位置
//	public static String getINDEX_ALL(){
//		return getDirPath() +"/kl" + "/"+ "index" + "/"+ "index_all";
//	}
//	//优秀项目案例维护
//	public static String getINDEX_Excellent(){
//		return getDirPath() +"/kl" + "/"+ "index" + "/"+ "index_excellent";
//	}
//	//项目相关信息维护
//	public static String getINDEX_PRJ_MSG(){
//		return getDirPath() + "/kl" + "/"+ "index" + "/"+ "prj_relate_knowledge";
//	}
//	//作业模板文档管理
//	public static String getINDEX_TEMPLATE(){
//		return getDirPath() + "/kl" + "/"+ "index"  + "/"+ "template";
//	}
//	//公司管理相关信息维护
//	public static String getINDEX_COMPANY_MANAGE(){
//		return getDirPath() + "/kl" + "/"+ "index"  + "/"+ "company_manage_msg";
//	}
//	//其它信息资源维护
//	public static String getINDEX_OTHER_MSG(){
//		return getDirPath() + "/kl" + "/"+ "index"  + "/"+ "other_knowledge_msg";
//	}
	
	//以下是存放文档的位置
	//主题新闻还有日期一层文件夹
	public static String getData_News(){
		return getDirPath() + "/data/supp/file_upload/EIPNEWS/newsFile";
	}
	//公司公告
	public static String getData_Bulletin(){
		return getDirPath() + "/data/supp/file_upload/OABULLETIN/filesName";
	}
	//文章栏
	public static String getData_Article(){
		return getDirPath() + "/data/supp/file_upload/OAARTICLE/filesName";
	}
	//文件柜
	public static String getData_Filebox(){
		return getDirPath() + "/data/supp/remot_foxbox_data";
	}
//	//优秀项目案例维护
//	public static String getData_Excellent(){
//		return getDirPath() + "/kl" + "/"+ "excellent_prj"  + "/"+ "files";
//	}
//	//项目相关信息维护
//	public static String getData_PRJ_MSG(){
//		return getDirPath() + "/kl" + "/" + "prj_relate_knowledge" + "/"+ "files";
//	}
//	//作业模板文档管理
//	public static String getData_TEMPLATE(){
//		return getDirPath() + "/kl" + "/" + "template" + "/"+ "files";
//	}
//	//公司管理相关信息维护
//	public static String getData_COMPANY_MANAGE(){
//		return getDirPath() + "/kl" + "/" + "company_manage_msg" + "/"+ "files";
//	}
//	//其它信息资源维护
//	public static String getData_OTHER_MSG(){
//		return getDirPath() + "/kl" + "/" + "other_knowledge_msg" + "/"+ "files";
//	}
	
	public LuceneService getLuceneService(){
		if(luceneService != null)return luceneService;
		luceneService = new LuceneService();
		return luceneService;
	}
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<CommonDocument> getGrid(UserProfile user, JqGrid jqGrid, Map < String, Object > amap_Parameters,int indexType) {
		List < CommonDocument > llist_Return = new ArrayList < CommonDocument >();
		//llist_Return = getDocumentList(amap_Parameters,user,indexType);
		llist_Return = getDocumentListByNoFile(amap_Parameters,user,indexType);
		jqGrid.setRows(llist_Return);
		//jqGrid.setRowCount(llist_Return.size());
		return llist_Return;
	}
	
	/**
	 * 返回查询列表.
	 * @param amap_Parameters
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public List < CommonDocument > getDocumentListByNoFile(Map < String, Object > amap_Parameters,
			UserProfile auserprofile_Current,int indexType){
		List < CommonDocument > llist_Return = new ArrayList < CommonDocument >();
		long ll_StartTime = System.currentTimeMillis();
		if(amap_Parameters == null) {System.out.println("params is null"); return llist_Return;}
		if(amap_Parameters.containsKey("query") && amap_Parameters.containsKey("currentPage")){
			if(amap_Parameters.get("query").toString().trim().length() == 0){
				return llist_Return;
			}
			String ls_Query = (String)amap_Parameters.get("query");
			if(amap_Parameters.get("indexType") != null){
				indexType = CommonUtil.parseInt((String)amap_Parameters.get("indexType"),1);
			}
			//System.out.println("indexType:" + amap_Parameters.get("indexType")+"=="+indexType);
//			System.out.println("ls_Quer:" + ls_Query);
//			System.out.println("索引目录:" + getIndexDir(indexType));
			List < Document > llist_Document = getLuceneService().simpleSearch(getIndexDir(indexType), ls_Query);
			System.out.println("索引目录--size:" + llist_Document.size());
			long ll_EndTime = System.currentTimeMillis();
			System.out.println("搜索===="+(ll_EndTime - ll_StartTime));
			
			long ll_StartTime1 = System.currentTimeMillis();
			Map<String, Folder> folderMap = new HashMap<String, Folder>();
			Map<String, DataFile> dataFileMap =  new HashMap<String, DataFile>();
//			Map<String, Filebox> fileboxMap =  new HashMap<String, Filebox>();
//			Map<String, String> accessModesMap = new HashMap<String, String>();
			if((indexType == CommonDocument.FILEBOX || indexType == CommonDocument.ALL) && llist_Document.size()>0){
				folderMap = getFolderMap();
				dataFileMap = getDataFileByFileIdMap();
//				fileboxMap = getFileboxMap();
//				Map<String, String> dataFileAclMap = dataFileDao.getDataFileAclMap(auserprofile_Current);
//				Map<String, String> folderAclMap = folderDao.getFolderAclMap(auserprofile_Current);
//				List<DataFile> dataFileList = dataFileDao.getDataFileList();
//				List<Folder> folderList = folderDao.getFolderList();
//				accessModesMap = dataFileDao.getAccessModes(auserprofile_Current, dataFileList, fileboxMap, 
//						folderMap, dataFileAclMap, folderAclMap, folderList);
			}
			ll_EndTime = System.currentTimeMillis();
			System.out.println("搜索整理目录===="+(ll_EndTime - ll_StartTime1));
			llist_Return = getList(llist_Document, ls_Query, folderMap, dataFileMap, auserprofile_Current);
		}else{
			System.out.println("没有查询参数");
		}
		long ll_EndTime = System.currentTimeMillis();
		System.out.println("总共搜索时间111===="+(ll_EndTime - ll_StartTime));
		return llist_Return;
	}
	private  List < CommonDocument > getList(List < Document > llist_Document, String ls_Query, 
			Map<String, Folder> folderMap, Map<String, DataFile> dataFileMap, UserProfile auserprofile_Current){
		long ll_StartTime1 = System.currentTimeMillis();
		List < CommonDocument > llist_Return = new ArrayList < CommonDocument >();
		for (Document ldocument_Result: llist_Document) {
			String name = CommonUtil.trim(ldocument_Result.get(luceneServiceByNoFilebox.INDEX_NAME));
			String path = CommonUtil.trim(ldocument_Result.get(luceneServiceByNoFilebox.INDEX_PATH));
			String fileName = CommonUtil.trim(ldocument_Result.get(luceneServiceByNoFilebox.INDEX_FILE_NAME));
			String fileId = CommonUtil.trim(ldocument_Result.get(luceneServiceByNoFilebox.INDEX_BUSSNIS_ID));
			//获取文档内容
			String ls_Content = CommonUtil.trim(ldocument_Result.get(getLuceneService().INDEX_CONTENT));
			String ls_ContentDisplay = null;
			
			//System.out.println("==path="+path);
//			ll_StartTime1 = System.currentTimeMillis();
			if(ls_Content != null && ls_Content.indexOf(ls_Query) > -1){
				ls_ContentDisplay = getContentDisplay(ls_Content, ls_Query);//以首个存在的分词为核心
				//所有的分词都变红
				ls_ContentDisplay = center2HTML(ls_ContentDisplay,ls_Query);
			} else {
//				System.out.println("ls_Content:" + ls_Content+"====="+ls_Content.indexOf(ls_Query));
//				System.out.println("false");
				ls_ContentDisplay = center2HTML(getContentDisplay(ls_Content), 
						ls_Query);
			}
			if(ls_ContentDisplay == null){
				ls_ContentDisplay = "";
			}
//			String contentDisplay = "<span class=\"cot\">文件内容概述：</span>	<span class=\"cot\">" + ls_ContentDisplay +"</span>";
			String contentDisplay = ls_ContentDisplay ;
			String display = name;
			if(name.indexOf(ls_Query) >= 0){
				display = center2HTML(name, ls_Query);
			}
//			System.out.println("分词变红===="+(System.currentTimeMillis() - ll_StartTime1));
			CommonDocument lcommondocument_Result = new CommonDocument();
//			lcommondocument_Result.setName(name);
			
//			lcommondocument_Result.setPath(path);
			//取最后一个32位ID，是文件的主键
//			ll_StartTime1 = System.currentTimeMillis();
			//String fileId = path.substring(path.lastIndexOf("/")+1);//linux
			//String fileId = path.substring(path.lastIndexOf("\\")+1);//windows机器
//			String temp = "<span class=\"title\">文字标题：</span>	<span class=\"figcaption\">" +
//							"<a href=\"javascript:view('"+ path +"');\">" + display +"</a></span>";
			String temp = "<a href=\"javascript:view('"+ path +"');\">" + display +"</a>";
			
			String folder = "";
			String fileNameDisplay = center2HTML(fileName, ls_Query);
//			folder = "<span class=\"lucene_origin\">附件名称：</span>	<span class=\"lucene_origin\">" + center2HTML(fileName, ls_Query) +"</span>";
			if(path.indexOf("id") > -1){
				if (dataFileMap.containsKey(fileId)) {
					String folderId = dataFileMap.get(fileId).getFolderId();
					folder = getFolderPath(folderId, folderMap);
				}
//				System.out.println("folder="+folder);
//				temp = "<span class=\"title\">文字标题：</span>	<span class=\"figcaption\">" +
//				"<a >" + display +"</a></span>";
				temp = "<a >" + display +"</a>";
//				//path = path.substring(0,path.lastIndexOf("\\")).replace("\\", "/");//windows
//				path = path.substring(0,path.lastIndexOf("/"));//linux
//				//判断权限
//				//boolean isRead = getCanBeReadByCurrUser(accessModesMap.get(fileId));
//				boolean isRead = getCanBeReadByCurrUser(accessModesMap.get(fileId));
////				System.out.println("isRead="+isRead+"=fileId="+fileId);
//				if(!isRead){
//					temp = "<span class=\"titlePower\">文字标题：</span>	<span class=\"figcaptionpower\"><a>" + display +"</a></span>";
//					folder = "<span class=\"lucene_origin_power\">文件来源：</span>	<span class=\"lucene_origin_power\">" + folder +"</span>";
//					contentDisplay = "<span class=\"cot_power\">文件内容概述：</span>	<span class=\"cot_power\">" + ls_ContentDisplay +"</span>";
//				}else{
//					temp = "<span class=\"title\">文字标题：</span>	<span class=\"figcaption\">" +
//							"<a href=\"javascript:downloadFileBox('"+ fileId +"','"+path+"');\">" + display +"</a></span>";
//					folder = "<span class=\"lucene_origin\">文件来源：</span>	<span class=\"lucene_origin\">" + folder +"</span>";
//				}
//				
			}
			if(path.indexOf("newsId") > -1){
				folder = "主题新闻";
			}
			if(path.indexOf("articleId") > -1){
				folder = "文章栏";
			}
			lcommondocument_Result.setFileId(fileId);
			lcommondocument_Result.setName(fileNameDisplay);
			lcommondocument_Result.setDisplay(temp);
			lcommondocument_Result.setFolder(folder);
			lcommondocument_Result.setContent(contentDisplay);
			if(path.indexOf("bulletinId") > -1){
				folder = "公司公告";
				lcommondocument_Result.setFolder(folder);
				String isCanOperate=this.bulletinService.isCanOperate(auserprofile_Current,fileId,"canRead");
				if(isCanOperate.equals("yes")){
					llist_Return.add(lcommondocument_Result);
				}
			}else{
				llist_Return.add(lcommondocument_Result);
			}
		}
		long ll_EndTime = System.currentTimeMillis();
		System.out.println("整理显示===="+(ll_EndTime - ll_StartTime1));
		return llist_Return;
	}
	/**
	 * 返回查询列表.
	 * @param amap_Parameters
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public List < CommonDocument > getDocumentList(Map < String, Object > amap_Parameters,
			UserProfile auserprofile_Current,int indexType){
		List < CommonDocument > llist_Return = new ArrayList < CommonDocument >();
		long ll_StartTime = System.currentTimeMillis();
		if(amap_Parameters == null) {System.out.println("params is null"); return llist_Return;}
		if(amap_Parameters.containsKey("query") && amap_Parameters.containsKey("currentPage")){
			if(amap_Parameters.get("query").toString().trim().length() == 0){
				return llist_Return;
			}
			String ls_Query = (String)amap_Parameters.get("query");
			if(amap_Parameters.get("indexType") != null){
				indexType = CommonUtil.parseInt((String)amap_Parameters.get("indexType"),1);
			}
//			System.out.println("indexType:" + amap_Parameters.get("indexType")+"=="+indexType);
//			System.out.println("ls_Quer:" + ls_Query);
//			System.out.println("索引目录:" + getIndexDir(indexType));
			List < Document > llist_Document = getLuceneService().simpleSearch(getIndexDir(indexType), ls_Query);
			System.out.println("索引目录--size:" + llist_Document.size());
			long ll_EndTime = System.currentTimeMillis();
			System.out.println("搜索===="+(ll_EndTime - ll_StartTime));
//			int li_CurrentPage = CommonUtil.parseInt((String)amap_Parameters.get("currentPage"),1);
//			int li_TotalPage = getTotalPage(amap_Parameters,auserprofile_Current,indexType);
//			if(li_CurrentPage > li_TotalPage){
//				li_CurrentPage = li_TotalPage;
//			}
//			if(li_CurrentPage < 1){
//				li_CurrentPage = 1;
//			}
			
//			//每页条目数
//			int li_PageContain = PAGE_CONTAIN;
			
//			int li_Start = (li_CurrentPage - 1) * li_PageContain;
//			int li_End = li_CurrentPage * li_PageContain;
//			int li_Count = getCount(amap_Parameters,auserprofile_Current,indexType);
//			if(li_End > li_Count){
//				li_End = li_Count;
//			}
			ll_StartTime = System.currentTimeMillis();
			Map<String, Folder> folderMap = new HashMap<String, Folder>();
			Map<String, DataFile> dataFileMap =  new HashMap<String, DataFile>();
			Map<String, Filebox> fileboxMap =  new HashMap<String, Filebox>();
			Map<String, String> accessModesMap = new HashMap<String, String>();
			if((indexType == CommonDocument.FILEBOX || indexType == CommonDocument.ALL) && llist_Document.size()>0){
				folderMap = getFolderMap();
				dataFileMap = getDataFileByFileIdMap();
				fileboxMap = getFileboxMap();
				Map<String, String> dataFileAclMap = dataFileDao.getDataFileAclMap(auserprofile_Current);
				Map<String, String> folderAclMap = folderDao.getFolderAclMap(auserprofile_Current);
				List<DataFile> dataFileList = dataFileDao.getDataFileList();
				List<Folder> folderList = folderDao.getFolderList();
				accessModesMap = dataFileDao.getAccessModes(auserprofile_Current, dataFileList, fileboxMap, 
						folderMap, dataFileAclMap, folderAclMap, folderList);
			}
			ll_EndTime = System.currentTimeMillis();
			System.out.println("搜索整理目录===="+(ll_EndTime - ll_StartTime));
			ll_StartTime = System.currentTimeMillis();
//			System.out.println("开始循环：li_Start" + li_Start + "--li_End:" + li_End);
			for (Document ldocument_Result: llist_Document) {
				String name = CommonUtil.trim(ldocument_Result.get(getLuceneService().INDEX_NAME));
				String path = CommonUtil.trim(ldocument_Result.get(getLuceneService().INDEX_PATH));
				//获取文档内容
//				long ll_StartTime1 = System.currentTimeMillis();
//				System.out.println("path===="+path);
				String ls_Content = getLuceneService().getAllText(path,name);
//				System.out.println("获取文档内容===="+(System.currentTimeMillis() - ll_StartTime1));
				if(ls_Content == null){
					System.out.println("content is null path:" + path);
					continue;
				}
				String ls_ContentDisplay = null;
				
//				System.out.println("==path="+path);
//				ll_StartTime1 = System.currentTimeMillis();
				if(ls_Content != null && ls_Content.indexOf(ls_Query) > -1){
					StringReader lstringreader_Query = new StringReader(ls_Query);
					try {
						TokenStream ltokenstream_TS = getLuceneService().getAnalyzer().tokenStream(
								"just a name", lstringreader_Query);
						
						String tokenText = "";
						ltokenstream_TS.reset();
						 while (ltokenstream_TS.incrementToken()) {
							 CharTermAttribute termAtt = (CharTermAttribute)ltokenstream_TS.addAttribute(CharTermAttribute.class);
							 tokenText = termAtt.toString();
							 if(tokenText != null && tokenText.length() > 0){
								 if(ls_Content.indexOf(tokenText) >= 0){
										ls_ContentDisplay = getContentDisplay(ls_Content, tokenText);//以首个存在的分词为核心
										//所有的分词都变红
										ls_ContentDisplay = center2HTML(ls_ContentDisplay,tokenText);
										break;
								}
							 }
						}
						ltokenstream_TS.end();
						ltokenstream_TS.close();
					} catch (IOException e) {
						System.out.println("getDocumentList() IOException");
						e.printStackTrace();
					}
					lstringreader_Query.close();
				} else {
//					System.out.println("ls_Content:" + ls_Content+"====="+ls_Content.indexOf(ls_Query));
//					System.out.println("false");
					ls_ContentDisplay = center2HTML(getContentDisplay(ls_Content), 
							ls_Query);
				}
				if(ls_ContentDisplay == null){
					ls_ContentDisplay = "";
				}
				String contentDisplay = "<span class=\"cot\">文件内容概述：</span>	<span class=\"cot\">" + ls_ContentDisplay +"</span>";
				String display = name;
				if(name.indexOf(ls_Query) >= 0){
					display = center2HTML(name, ls_Query);
				}
//				System.out.println("分词变红===="+(System.currentTimeMillis() - ll_StartTime1));
				CommonDocument lcommondocument_Result = new CommonDocument();
//				lcommondocument_Result.setName(name);
				
//				lcommondocument_Result.setPath(path);
				//取最后一个32位ID，是文件的主键
//				ll_StartTime1 = System.currentTimeMillis();
				String fileId = path.substring(path.lastIndexOf("/")+1);//linux
				//String fileId = path.substring(path.lastIndexOf("\\")+1);//windows机器
				String temp = "<span class=\"title\">文字标题：</span>	<span class=\"figcaption\">" +
								"<a href=\"javascript:downloadFile('"+ fileId +"');\">" + display +"</a></span>";
				String folder = "";
				if(path.indexOf("remot_foxbox_data")>-1){
					if(fileId.lastIndexOf(".")>-1){
						fileId = fileId.substring(0,fileId.lastIndexOf("."));
					}
					if (dataFileMap.containsKey(fileId)) {
						String folderId = dataFileMap.get(fileId).getFolderId();
						folder = getFolderPath(folderId, folderMap);
					}
					
//					DataFile dataFile = dataFileDao.get(fileId);
//					if(dataFile != null ){
//						folder = getFolderPath(dataFile.getFolderId(), subItemsMap);
//						//folder = getFolderPath(dataFile.getFolderId());
//					}
//					folder = fileboxName;
					//path = path.substring(0,path.lastIndexOf("\\")).replace("\\", "/");//windows
					path = path.substring(0,path.lastIndexOf("/"));//linux
					//判断权限
					//boolean isRead = getCanBeReadByCurrUser(accessModesMap.get(fileId));
					boolean isRead = getCanBeReadByCurrUser(accessModesMap.get(fileId));
//					System.out.println("isRead="+isRead+"=fileId="+fileId);
					if(!isRead){
						temp = "<span class=\"titlePower\">文字标题：</span>	<span class=\"figcaptionpower\"><a>" + display +"</a></span>";
						folder = "<span class=\"lucene_origin_power\">文件来源：</span>	<span class=\"lucene_origin_power\">" + folder +"</span>";
						contentDisplay = "<span class=\"cot_power\">文件内容概述：</span>	<span class=\"cot_power\">" + ls_ContentDisplay +"</span>";
					}else{
						temp = "<span class=\"title\">文字标题：</span>	<span class=\"figcaption\">" +
								"<a href=\"javascript:downloadFileBox('"+ fileId +"','"+path+"');\">" + display +"</a></span>";
						folder = "<span class=\"lucene_origin\">文件来源：</span>	<span class=\"lucene_origin\">" + folder +"</span>";
					}
					
				}
//				lcommondocument_Result.setFileId(fileId);
				lcommondocument_Result.setDisplay(temp);
				lcommondocument_Result.setFolder(folder);
				lcommondocument_Result.setContent(contentDisplay);
				llist_Return.add(lcommondocument_Result);
//				System.out.println("整理目录及显示===="+(System.currentTimeMillis() - ll_StartTime1));
			}
			ll_EndTime = System.currentTimeMillis();
			System.out.println("整理显示===="+(ll_EndTime - ll_StartTime));
		}else{
			System.out.println("没有查询参数");
		}
//		System.out.println("循环结束");
		return llist_Return;
	}

	public boolean getCanBeReadByCurrUser(String ls_AccessModes) {
		return (containStr(ls_AccessModes, "R"))
				|| (containStr(ls_AccessModes, "W"))
				|| (containStr(ls_AccessModes, "X"))
				|| (containStr(ls_AccessModes, "F"));
	}
//	public boolean getCanBeReadByCurrUser(String ls_AccessModes) {
//		String ls_AccessModes = CommonUtil.trim(dataFileDao.getAccessModes(
//				ausrprf_U, adfile_F, fileboxMap, folderMap));
//
//		return (containStr(ls_AccessModes, "R"))
//				|| (containStr(ls_AccessModes, "W"))
//				|| (containStr(ls_AccessModes, "X"))
//				|| (containStr(ls_AccessModes, "F"));
//	}

	private boolean containStr(String as_S1, String as_S2) {
		return as_S1.indexOf(as_S2) >= 0;
	}

	/**
	 * 获取本目录的路径.
	 * 
	 * @return 格式为： \[root]\folder1\folder11之类的
	 */
    public String getFolderPath(String folderId, Map<String, Folder> subItemsMap) {
//    	long ll_StartTime1 = System.currentTimeMillis();
    	Folder folder = subItemsMap.get(folderId);
    	String ls_Path = "";
    	if(folder != null){
	        ls_Path = CommonUtil.trim(folder.getFolderName());
	        Folder lfolder_Parent = subItemsMap.get(folder.getParentFolderId());
	        while (lfolder_Parent != null) {
	        	//ls_Path = CommonUtil.trim(lfolder_Parent.getFolderName()) + "\\" + ls_Path;
	        	ls_Path = CommonUtil.trim(lfolder_Parent.getFolderName()) + "/" + ls_Path;
	            String ls_ParentFolderId = lfolder_Parent.getId();
	            lfolder_Parent = subItemsMap.get(lfolder_Parent.getParentFolderId());    
	            if (lfolder_Parent != null && ls_ParentFolderId == lfolder_Parent.getId()) break; 
	        }
    	}
//    	System.out.println("获取目录===="+(System.currentTimeMillis() - ll_StartTime1));
        return ls_Path;
    }
//    public String getFolderPath(String folderId) {
//    	Folder folder = getFolder(folderId);
//    	String ls_Path = "";
//    	if(folder != null){
//	        ls_Path = CommonUtil.trim(folder.getFolderName());
//	        Folder lfolder_Parent = getFolder(folder.getParentFolderId());
//	        while (lfolder_Parent != null) {
//	        	ls_Path = CommonUtil.trim(lfolder_Parent.getFolderName()) + "\\" + ls_Path;
//	        	//ls_Path = CommonUtil.trim(lfolder_Parent.getFolderName()) + "/" + ls_Path;
//	            String ls_ParentFolderId = lfolder_Parent.getId();
//	            lfolder_Parent = getFolder(lfolder_Parent.getParentFolderId());    
//	            if (lfolder_Parent != null && ls_ParentFolderId == lfolder_Parent.getId()) break; 
//	        }
//    	}
//        return ls_Path;
//    }
//    public Folder getFolder(String folderId) {
//    	Folder folder = null;
//    	if(folderId != null){
//    		folder = this.folderDao.get(folderId);
//    	}
//        return folder;
//    }
    private Map<String, Filebox> getFileboxMap(){
    	List<Filebox> dataFileList = dataFileDao.find("from Filebox");
    	Map<String, Filebox> folderIdMap = new HashMap<String, Filebox>();
    	for (Filebox pbs : dataFileList) {
			String id = CommonUtil.trim(pbs.getId());
			if (!folderIdMap.containsKey(id)) {
				folderIdMap.put(id, pbs);
			} 
		}
		return folderIdMap;
    }
    private Map<String, DataFile> getDataFileByFileIdMap(){
    	List<DataFile> dataFileList = dataFileDao.find("from DataFile");
    	Map<String, DataFile> folderIdMap = new HashMap<String, DataFile>();
    	for (DataFile pbs : dataFileList) {
			String id = CommonUtil.trim(pbs.getId());
			if (!folderIdMap.containsKey(id)) {
				folderIdMap.put(id, pbs);
			} 
		}
		return folderIdMap;
    }
	private Map<String, Folder> getFolderMap() {
		List<Folder> pbsList = folderDao.getFolderList();
		Map<String, Folder> subItemsMap = new HashMap<String, Folder>();
		for (Folder pbs : pbsList) {
			String id = CommonUtil.trim(pbs.getId());
			if (!subItemsMap.containsKey(id)) {
				subItemsMap.put(id, pbs);
			} 
		}
		return subItemsMap;
	}
	/**
	 * 使文字内容以某个词为核心展示.
	 * @param as_Content 文字内容
	 * @param as_CenterWord 核心词
	 * @return
	 * @throws IOException 
	 */
	public String getContentDisplay(String as_Content,String as_CenterWord) {
		if(as_Content == null || as_Content.trim().length() == 0 
				|| as_CenterWord == null || as_CenterWord.trim().length() == 0){
//			System.out.println("The arguments of getContentDisplay is null or is zero.");
			return null;
		}
		
		int li_FirstAppear = as_Content.indexOf(as_CenterWord);
		if(li_FirstAppear < 0){
			return null;
		}
		
		int li_CenterBefore = CENTER_BEFORE;
//		if(CommonUtil.parseInt(AppSetting.get("CENTER_BEFORE"),0) != 0){
//			li_CenterBefore = CommonUtil.parseInt(AppSetting.get("CENTER_BEFORE"));
//		}
		
		String ls_CenterBefore;
		if(li_FirstAppear <= li_CenterBefore){
			ls_CenterBefore = as_Content.substring(0, li_FirstAppear);
		} else {
			ls_CenterBefore = "……" + as_Content.substring(li_FirstAppear - li_CenterBefore,
					li_FirstAppear);
		}
		
		int li_CenterAfter = CENTER_AFTER;
//		if(CommonUtil.parseInt(AppSetting.get("CENTER_AFTER"),0) != 0){
//			li_CenterAfter = CommonUtil.parseInt(AppSetting.get("CENTER_AFTER"));
//		}
		
		String ls_CenterAndAfter;
		if(li_FirstAppear + as_CenterWord.length() + li_CenterAfter >= as_Content.length()){
			ls_CenterAndAfter = as_Content.substring(li_FirstAppear);
		} else {
			ls_CenterAndAfter = as_Content.substring(li_FirstAppear,
					li_FirstAppear + as_CenterWord.length() + li_CenterAfter) + "……";
		}
		
		String ls_Return = ls_CenterBefore + ls_CenterAndAfter;
		return ls_Return;
	}
	/**
	 * 使文字内容
	 * 未找到核心词.
	 * @param as_Content 文字内容
	 * @param as_CenterWord 核心词
	 * @return
	 * @throws IOException 
	 */
	public String getContentDisplay(String as_Content) {
		if(as_Content == null || as_Content.trim().length() == 0 ){
//			System.out.println("The arguments of getContentDisplay is null or is zero.");
			return null;
		}
		String ls_Return = "";
		int li_CenterBefore = CENTER_BEFORE;
		int li_CenterAfter = CENTER_AFTER;
		if(li_CenterBefore + li_CenterAfter <= as_Content.length()){
			ls_Return = as_Content.substring(0,li_CenterBefore + li_CenterAfter) + "……";
		} else {
			ls_Return = as_Content;
		}
		return ls_Return;
	}
	/**
	 * 把核心词变红.
	 * @param as_Content 文字内容
	 * @param as_CenterWord 核心词
	 * @return
	 */
	public String center2HTML(String as_Content,String as_CenterWord){
		if(as_Content == null || as_Content.trim().length() == 0 
				|| as_CenterWord == null || as_CenterWord.trim().length() == 0){
			//System.out.println("The arguments of center2HTML is null or is zero.");
			return "";
		}
		String ls_Return = as_Content.replaceAll(as_CenterWord, 
				"<font color='red'>" + as_CenterWord + "</font>");
		
		return ls_Return;
	}
	
//	/**
//	 * 得到查询到的记录条数.
//	 * @param amap_Parameters
//	 * @return
//	 * @throws IOException
//	 */
//	public int getCount(Map < String, Object > amap_Parameters,UserProfile auserprofile_Current,int indexType){
//		int li_Return = 0;
//		
////		String ls_IndexDir = CommonUtil.trim(AppSetting.get("PMS_LUCENE_INDEX"));
//		String ls_IndexDir = getIndexDir(indexType);
////		if(amap_Parameters.containsKey("queryType")){
////			ls_IndexDir = getIndexDir(CommonUtil.parseInt((String)amap_Parameters.get("queryType"),0));
////		}
//		
//		if(amap_Parameters.containsKey("query")){
//			if(amap_Parameters.get("query").toString().trim().length() == 0){
//				return li_Return;
//			}
//			List < Document > llist_Document = getLuceneService().simpleSearch(ls_IndexDir, 
//					(String)amap_Parameters.get("query"));
//			
//			//根据权限过滤
//			//llist_Document = getViewList(llist_Document, auserprofile_Current);
//			
//			li_Return = llist_Document.size();
//		}
//		return li_Return;
//	}
	
//	/**
//	 * 得到分页总数.
//	 * @param amap_Parameters
//	 * @return
//	 * @throws IOException
//	 */
//	public int getTotalPage(Map < String, Object > amap_Parameters,UserProfile auserprofile_Current,int indexType){
//		int li_Return = 0;
//		if(amap_Parameters.containsKey("query")){
//			if(amap_Parameters.get("query").toString().trim().length() == 0){
//				return li_Return;
//			}
//			int li_Count = getCount(amap_Parameters,auserprofile_Current,indexType);
//			
//			int li_PageContain = PAGE_CONTAIN;
////			if(CommonUtil.parseInt(AppSetting.get("PAGE_CONTAIN"),0) != 0){
////				li_PageContain = CommonUtil.parseInt(AppSetting.get("PAGE_CONTAIN"));
////			}
//			
//			li_Return = li_Count / li_PageContain;
//			if(li_Count % li_PageContain != 0){
//				li_Return += 1;
//			}
//		}
//		return li_Return;
//	}
	
	/**
	 * 得到查询的时间(貌似有点诡异).
	 * @param amap_Parameters
	 * @return
	 * @throws IOException
	 */
//	public long getSearchTime(Map < String, Object > amap_Parameters){
//		long ll_Return = 0;
//		
////		String ls_IndexDir = CommonUtil.trim(AppSetting.get("PMS_LUCENE_INDEX"));
//		String ls_IndexDir = getin;
////		if(amap_Parameters.containsKey("queryType")){
////			ls_IndexDir = getIndexDir(CommonUtil.parseInt((String)amap_Parameters.get("queryType"),0));
////		}
//		
//		if(amap_Parameters.containsKey("query")){
//			if(amap_Parameters.get("query").toString().trim().length() == 0){
//				return ll_Return;
//			}
//			long ll_StartTime = System.currentTimeMillis();
//			getLuceneService().simpleSearch(ls_IndexDir, (String)amap_Parameters.get("query"));
//			long ll_EndTime = System.currentTimeMillis();
//			ll_Return = ll_EndTime - ll_StartTime;
//		}
//		return ll_Return;
//	}
	
	//优秀项目案例
//	public static final  int EXCELLENT_PRJ = 1;
//	//项目相关信息维护
//	public static final  int PRJ_MSG = 2;
//	//作业模板文档管理
//	public static final int TEMPLATE_DOC = 3;
//	//公司管理相关信息维护
//	public static final int COMPANY_MANAGE = 4;
//	//其它信息资源维护
//	public static final int MSG_RESOURCE = 5;
//	//主题新闻
//	public static final  int NEWS = 6;
//	//公司公告
//	public static final  int BULLETIN = 7;
//	//文章栏
//	public static final int ARTICLE = 8;
//	//文件柜
//	public static final int FILEBOX = 9;
//	//所有
//	public static final int ALL = 10;
	
	public static String getIndexDir(int indexType){
		String ls_IndexDir = null;//getINDEX_ALL();
		switch(indexType){
//			case EXCELLENT_PRJ:
//				ls_IndexDir = getINDEX_Excellent();
//				break;
//			case PRJ_MSG:
//				ls_IndexDir = getINDEX_PRJ_MSG();
//				break;
//			case TEMPLATE_DOC:
//				ls_IndexDir = getINDEX_TEMPLATE();
//				break;
//			case COMPANY_MANAGE:
//				ls_IndexDir = getINDEX_COMPANY_MANAGE();
//				break;
//			case MSG_RESOURCE:
//				ls_IndexDir = getINDEX_OTHER_MSG();
//				break;
			case CommonDocument.NEWS:
				ls_IndexDir = getIndex_News();
				break;
			case CommonDocument.BULLETIN:
				ls_IndexDir = getIndex_Bulletin();
				break;
			case CommonDocument.ARTICLE:
				ls_IndexDir = getIndex_Article();
				break;
			case CommonDocument.FILEBOX:
				ls_IndexDir = getIndex_Filebox();
				break;
			default:
				ls_IndexDir = getIndex();//getINDEX_ALL();
		}
		
		return ls_IndexDir;
	}
	
	
	/**
	 * 根据索引路径找数据路径.
	 * @param indexDir
	 * @return
	 */
//	public static String getDataDir(String indexDir){
//		String result = null;
//		if(indexDir != null){
//			if(indexDir.equals(getINDEX_Excellent())){
//				result = getData_Excellent();
//			}else if(indexDir.equals(getINDEX_PRJ_MSG())){
//				result = getData_PRJ_MSG();
//			}else if(indexDir.equals(getINDEX_TEMPLATE())){
//				result = getData_TEMPLATE();
//			}else if(indexDir.equals(getINDEX_COMPANY_MANAGE())){
//				result = getData_COMPANY_MANAGE();
//			}else if(indexDir.equals(getINDEX_OTHER_MSG())){
//				result = getData_OTHER_MSG();
//			}else if(indexDir.equals(getIndex_News())){
//				result = getData_News();
//			}else if(indexDir.equals(getIndex_Bulletin())){
//				result = getData_Bulletin();
//			}else if(indexDir.equals(getIndex_Article())){
//				result = getData_Article();
//			}else if(indexDir.equals(getIndex_Filebox())){
//				result = getData_Filebox();
//			}
//		}
//		return result;
//	}
	
	public static String getDataDir(int indexType){
		String ls_IndexDir = null;
		switch(indexType){
//			case EXCELLENT_PRJ:
//				ls_IndexDir = getData_Excellent();
//				break;
//			case PRJ_MSG:
//				ls_IndexDir = getData_PRJ_MSG();
//				break;
//			case TEMPLATE_DOC:
//				ls_IndexDir = getData_TEMPLATE();
//				break;
//			case COMPANY_MANAGE:
//				ls_IndexDir = getData_COMPANY_MANAGE();
//				break;
//			case MSG_RESOURCE:
//				ls_IndexDir = getData_OTHER_MSG();
//				break;
			case CommonDocument.NEWS:
				ls_IndexDir = getData_News();
				break;
			case CommonDocument.BULLETIN:
				ls_IndexDir = getData_Bulletin();
				break;
			case CommonDocument.ARTICLE:
				ls_IndexDir = getData_Article();
				break;
			case CommonDocument.FILEBOX:
				ls_IndexDir = getData_Filebox();
				break;
			default:
				ls_IndexDir = null;
		}
		
		return ls_IndexDir;
	}
	
}
