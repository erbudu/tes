package com.supporter.prj.linkworks.oa.news_exam.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.linkworks.oa.news_exam.dao.NewsGenerateHtmlDao;
import com.supporter.prj.meip_service.news.entity.News;
import com.supporter.util.CommonUtil;

/**
 * @Title: Service
 * @Description: 新闻动态表.
 * @author TS
 * @date 2017-11-24 14:45:29
 * @version V1.0
 *
 */
@Service
public class NewsGenerateHtmlService  {
	@Autowired
    private NewsGenerateHtmlDao newsGenerateHtmlDao;	
	
	/**
	 * 创建新闻HTML网页.
	 * @param picCount 图片个数
     * @param newsCount 新闻个数
	 */
    public void toMakeHtml(int picCount, int newsCount){
    	String[] html = this.getHtmlInfo(picCount, newsCount);
    	if (html == null)return;
    	if (CommonUtil.isEmpty(html[2]))return;
    	
        try {
        	String webPath = getClass().getResource("/").getPath();// 得到工程名WEB-INF/classes/路径
        	webPath = webPath.substring(0, webPath.indexOf("WEB-INF")); //例如：d:/eip/
        	
			//创建图片新闻静态文件=====================================
        	String templateFilePath = webPath + "eip/news/news_portlet_pic_template.html";
			//System.out.print("新闻模板：" + templateFilePath);
            FileInputStream fileinputstream = new FileInputStream(templateFilePath);
        	
            int lenght = fileinputstream.available();
            byte bytes[] = new byte[lenght];
            fileinputstream.read(bytes);
            fileinputstream.close();
            
            String templateContent = new String(bytes, "UTF-8");
            templateContent = templateContent.replace("[[GenerateHtmlImg]]", html[0]);
            templateContent = templateContent.replace("[[GenerateHtmlPoint]]", html[1]);
            
            String filename = webPath + "eip/news/news_portlet_pic_static.html"; // 生成的html文件保存路径。
            //System.out.print("新闻静态文件：" + filename);
            FileOutputStream fileoutputstream = new FileOutputStream(filename);// 建立文件输出流
            
            byte tag_bytes[] = templateContent.getBytes("UTF-8");
            fileoutputstream.write(tag_bytes);
            fileoutputstream.close();
            
            //创建列表新闻静态文件=====================================
            templateFilePath = webPath + "eip/news/news_portlet_list_template.html";
			//System.out.print("新闻模板：" + templateFilePath);
            fileinputstream = new FileInputStream(templateFilePath);
            lenght = fileinputstream.available();
            byte listBytes[] = new byte[lenght];
            fileinputstream.read(listBytes);
            fileinputstream.close();
            
            String templateContentList = new String(listBytes, "UTF-8");
            templateContentList = templateContentList.replace("[[GenerateHtmlList]]", html[2]);
            
            filename = webPath + "eip/news/news_portlet_list_static.html"; // 生成的html文件保存路径。
            //System.out.print("新闻静态文件：" + filename);
            fileoutputstream = new FileOutputStream(filename);// 建立文件输出流
            
            byte list_bytes[] = templateContentList.getBytes("UTF-8");
            fileoutputstream.write(list_bytes);
            fileoutputstream.close();
            
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }
    
    /**
     * 获取页面替换字符串.
     * @param picCount 图片个数
     * @param newsCount 新闻个数
     * @return 图片html[0],滚动图标html[1],新闻列表html[2]
     */
    private String[] getHtmlInfo(int picCount, int newsCount){
    	if (newsCount < picCount)newsCount = picCount;
    	
		List < News > newsList = newsGenerateHtmlDao.getNewsList(newsCount);
		if (newsList == null || newsList.size() == 0)return null;

		String[] html = new String[3];
		StringBuffer imgHtml = new StringBuffer(""); //图片html[0]
		StringBuffer pointHtml = new StringBuffer(""); //滚动图标html[1]
		StringBuffer listHtml = new StringBuffer(""); //新闻列表html[2]
		
		int newsSize = newsList.size();
		
		//构造列表新闻html
		int listIndex = 0;
		for (int i = 0; i < newsSize; i++){
			News news = newsList.get(i);
			
			String newsId = CommonUtil.trim(news.getNewsId());
			String title = CommonUtil.trim(news.getTitle());

			if (listIndex < newsCount){
				listHtml.append("<li class=\"newsList\">");
				listHtml.append("<span class=\"textSpan\" news_id=\"").append(newsId).append("\"");
				listHtml.append(" title=\"").append(title).append("\">").append(title).append("</span>");
				listHtml.append("<span class=\"publishDate\">").append(CommonUtil.formatDate(news.getPublishTime(), "MM-dd")).append("</span>");
				listHtml.append("</li>");
			}
		}
		html[2] = listHtml.toString();
		
		//构造图片新闻html
		String path = EIPService.getRegistryService().get("NewsImgSrcPath");
		if (CommonUtil.isEmpty(path)){
			throw new RuntimeException("请注册业务参数：NewsImgSrcPath,例如：http://127.0.0.1/eip/data/supp/file_upload/EIPNEWS/newsFile/");
		}

		int picIndex = 0;
		for (int i = 0; i < newsSize; i++){
			if (picIndex == picCount)break;
			
			News news = newsList.get(i);
			String newsId = CommonUtil.trim(news.getNewsId());
			String title = CommonUtil.trim(news.getTitle());
			
			List < IFile> files = EIPService.getFileUploadService().getFileList("EIPNEWS", "newsFile", newsId);
			if (files == null || files.size() == 0)continue;
			int fileSize = files.size();
			
			for (int j = 0; j < fileSize; j++){
				if (picIndex == picCount)break;
				
				FileUpload file = (FileUpload)files.get(j);
				String folder = CommonUtil.formatDate(file.getCreatedDate(),"yyyyMM/");

				if (CommonUtil.trim(file.getContentType()).toLowerCase().startsWith("image/")){
					imgHtml.append("<li>");
					imgHtml.append("<a onclick=\"viewNews('").append(newsId).append("','").append(title).append("');\">");
					imgHtml.append("<img src=\"").append(path).append(folder).append(file.getFileId()).append("\" />");
					imgHtml.append("<p class=\"w80p tal fs14 ellipsis\">").append(title).append("</p>");
					imgHtml.append("</a>");
					imgHtml.append("</li>");
					
					pointHtml.append("<li></li>");
					picIndex++;
				}
			}
			
		}
		
		html[0] = imgHtml.toString();
		html[1] = pointHtml.toString();
		
		return html;
    }
    
    /**
     * 根据新闻ID查找新闻对象.
     * @param news
     * @param newsId
     * @return
     */
    private News findNewsById(List < News > newsList, String newsId){
    	if (newsList == null)return null;
    	int size = newsList.size();
    	for (int i = 0; i < size; i++){
    		News news = newsList.get(i);
    		if (CommonUtil.trim(news.getNewsId()).equals(newsId)){
    			return news;
    		}
    	}
    	return null;
    }

  
}
