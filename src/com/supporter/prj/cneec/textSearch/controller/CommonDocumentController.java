package com.supporter.prj.cneec.textSearch.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.textSearch.dao.DataFileDao;
import com.supporter.prj.cneec.textSearch.entity.CommonDocument;
import com.supporter.prj.cneec.textSearch.entity.DataFile;
import com.supporter.prj.cneec.textSearch.service.CommonDocumentService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.dept_resource.entity.DeptResource;
import com.supporter.prj.eip.file_upload.service.FileManageService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**
 * 文件的ListAction类.
 * @author qy
 *
 */
@Controller
@RequestMapping("lucene/common_document")
public class CommonDocumentController extends AbstractController{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CommonDocumentService commonDocumentService;
//	@Autowired
//	private LuceneService luceneService;
	@Autowired
	private  FileManageService fileManageService;
	@Autowired
	private DataFileDao dataFileDao;
//	long ll_Return;
//	public int indexType;
//	public int indexTypeInt;
//	public String query;
	
	/**
	 * 返回列表. 分页表格展示数据.
	 * 
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq,
			DeptResource deptResource) throws Exception {
		UserProfile user = this.getUserProfile();
//		//计算用时
		long ll_StartTime = System.currentTimeMillis();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
//		//准备数据
		this.commonDocumentService.getGrid(user, jqGrid, getRequestParameters(),10);
		long ll_EndTime = System.currentTimeMillis();
//		ll_Return = (ll_EndTime - ll_StartTime)/1000;
		
		System.out.println("总共搜索时间===="+(ll_EndTime - ll_StartTime));
		return jqGrid;
	}
//	@RequestMapping("getSearchTime")
//	public @ResponseBody long getSearchTime(){
//		return ll_Return;
//	}
	/**
	 * 获取字典数据-用于高级查询页面的下拉显示
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getSourceCodetable")
	public Map<Integer, String> getSourceCodetable()throws IOException {
		Map<Integer, String> map = CommonDocument.getSourceCodetable();
		return map;
	}
//	@RequestMapping( { "downloadFile" })
//	public void downloadFile(HttpServletResponse response) throws Exception {
////		UserProfile user = getUserProfile();
//		String fileId = getRequest().getParameter("fileId");
//		String path = getRequest().getParameter("path");
//		System.out.println("path=="+path);
//		if (CommonUtil.trim(fileId).length() <= 0) {
//			return;
//		}
//		DataFile fileUpload = this.luceneService.getDataFile(fileId);
//
//		String fileName = fileUpload.getFileName();
////		String contentType = fileUpload.getContentType();
//		FileInputStream lfis_Source = null;
//		OutputStream los_OutputStream = null;
//		try {
//			response.reset();
//
////			response.setContentType(contentType);
//			response.setHeader("Content-Disposition", "attachment; filename="
//					+ CommonUtil.unicodeToGB(fileName));
//			response.setBufferSize(1024);
//
//			File lfile_F = new File(path, fileId+".dat");
//			lfis_Source = new FileInputStream(lfile_F);
//			
//			los_OutputStream = response.getOutputStream();
//
//			byte[] larrbyte_Buffer = new byte[1024];
//			int li_Bytes = 0;
//			while ((li_Bytes = lfis_Source.read(larrbyte_Buffer)) != -1)
//				los_OutputStream.write(larrbyte_Buffer, 0, li_Bytes);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (lfis_Source != null) {
//				lfis_Source.close();
//			}
//			if (los_OutputStream != null)
//				los_OutputStream.close();
//		}
//	}
	@RequestMapping( { "downloadFile" })
	public void downloadFile(HttpServletResponse response) throws Exception {
//		UserProfile user = getUserProfile();
		//接收参数
		String fileId = getRequest().getParameter("fileId");
//		String path = getRequest().getParameter("path");
		final String CONTENT_TYPE = "application/x-msdownload";
		
	 	response.setContentType(CONTENT_TYPE); 

	    
//	 	Date createDate = new Date();
//	    String pathss = this.fileManageService.getStoragePath(user.getTenantNo(), "OAARTICLE", "filesName", createDate);
//	    System.out.println("pathss=="+pathss);
	    DataFile ldfile_F = dataFileDao.get(fileId);
	    //pathss===E:\workspace\cneec_eip6.2\data\
//	    String ls_FilePath = this.fileManageService.getStoragePath() 
//	    	+ "supp\\remot_foxbox_data" + "\\" + ldfile_F.getStoragePath().replace("/", "\\");
	    String ls_FilePath = this.fileManageService.getStoragePath() 
	    	+ "supp/remot_foxbox_data" + "/" + ldfile_F.getStoragePath();	
	    System.out.println("ls_FilePath="+ls_FilePath);
	    String ls_FileType = ldfile_F.getFileExt();
	    String ls_ContentType = CONTENT_TYPE;
	    if (ls_FileType.equalsIgnoreCase("GIF")) ls_ContentType = "image/gif";
	    if (ls_FileType.equalsIgnoreCase("JPG")) ls_ContentType = "image/jpeg";
	    if (ls_FileType.equalsIgnoreCase("JPEG")) ls_ContentType = "image/jpeg";
	    if (ls_FileType.equalsIgnoreCase("BMP")) ls_ContentType = "image/bmp";
	     
		try{
			response.reset();
	        response.setContentLength((int)ldfile_F.getFileSize());
	        response.setContentType(ls_ContentType);
	        response.setHeader("Content-Disposition", "attachment; filename=" + CommonUtil.unicodeToGB(ldfile_F.getFileName()));
	        
			File lfile_F = new File(ls_FilePath);
	      	FileInputStream lfis_Source = new FileInputStream(lfile_F);
	      	OutputStream los_OutputStream = response.getOutputStream();
	      	
	      	byte[] larrbyte_Buffer = new byte[1024];
		    int li_Bytes = 0;
		    while((li_Bytes = lfis_Source.read(larrbyte_Buffer)) != -1){
		        los_OutputStream.write(larrbyte_Buffer,0,li_Bytes);
		   	} 
	      
	      	lfis_Source.close();
	      	los_OutputStream.close();
	      	
	    } catch(IOException e){
	      	e.printStackTrace(); 
	    } 
	}
// public List < CommonDocument > documentList;
	
// public List < CommonDocument > getDocumentList(){
// // luceneService.index(LuceneService.as_DataDir, LuceneService.as_IndexDir);
////		System.out.println("jinlaile");
////		System.out.println("currentPage:" + getRequestPara("currentPage"));
////		System.out.println("query:" + getRequestPara("query"));
////		System.out.println("getIndexType:" + getIndexType());
//		try {
//			//计算用时
//			long ll_StartTime = System.currentTimeMillis();
//			
//			if(getQuery() != null){
//				getRequestParameters().put("query", getQuery());
//			}
//			getRequestParameters().put("currentPage", getCurrentPage());
//			
//			//准备数据getIndexType()
//			documentList= commonDocumentService.getDocumentList(getRequestParameters(),null,7);
//			long ll_EndTime = System.currentTimeMillis();
//			ll_Return = (ll_EndTime - ll_StartTime)/1000;
//		} catch (Exception e) {
//			System.out.println("CommonDocumentListAction error!");
//			e.printStackTrace();
//		}
//		return documentList;
//	}
	
//	public String reGetList(){
//		getDocumentList();
//		return "SUCCESS";
//	}
	
//	public int getCount(){
//		return commonDocumentService.getCount(getRequestParameters(),getUserProfile(),getIndexType());
//	}
//	
//	public int getTotalPage(){
//		return commonDocumentService.getTotalPage(getRequestParameters(),getUserProfile(),getIndexType());
//	}
	

	
	/*[页面参数部分：开始]*/ 
//	public String getQuery(){
//		 return getRequestPara("query");
//	}
	
//	public String getQueryTypeValue(){
//		return getRequestPara("queryType");
//	}
	
//	public int getCurrentPage(){
//		int li_Return = CommonUtil.parseInt(getRequestPara("currentPage"),1);
//		if(li_Return < 1) li_Return = 1;
//		int li_TotalPage = commonDocumentService.getTotalPage(getRequestParameters(),getUserProfile(),getIndexType());
//		if(li_Return > li_TotalPage) li_Return = li_TotalPage;
//		return li_Return;
//	}
	/*[页面参数部分：结束]*/

//	public int getIndexType() {
//		return indexType;
//	}
//
//	public void setIndexType(int indexType) {
//		this.indexType = indexType;
//	}
	
//	public int getIndexTypeInt() {
//		return indexTypeInt;
//	}
//	
//	public void setIndexTypeInt(int indexTypeInt) {
//		this.indexTypeInt = indexTypeInt;
//	}

//	public void downLoad() throws FileNotFoundException{
//		String path = this.getRequestPara("path");
//		String unicodeFileName = this.getRequestPara("fileName");
//		String fileName = null;
////		try {
////			fileName = java.net.URLDecoder.decode(unicodeFileName,"UTF-8");
////		} catch (UnsupportedEncodingException e1) {
////			e1.printStackTrace();
////			return;
////		}
//		
//		// 读到流中
//		InputStream inStream = new FileInputStream(path);// 文件的存放路径
//		// 设置输出的格式
//		getResponse().reset();
//		getResponse().setContentType("bin");
//		getResponse().addHeader("Content-Disposition", "attachment; filename= " + unicodeFileName);
//		// 循环取出流中的数据
//		byte[] b = new byte[100];
//		int len;
//		try {
//		while ((len = inStream.read(b)) > 0)
//			getResponse().getOutputStream().write(b, 0, len);
//			inStream.close();
//		} catch (IOException e) {
//		e.printStackTrace();
//		}
//	}
}
