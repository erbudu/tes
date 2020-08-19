package com.supporter.prj.linkworks.oa.news.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.news.dao.NewsContentDao;
import com.supporter.prj.eip.news.dao.NewsDao;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.meip_service.news.entity.News;
import com.supporter.prj.meip_service.news.entity.NewsContent;

/**   
 * @Title: Service
 * @Description: 功能模块表
 * @author tiansen
 * @version V1.0   
 *
 */
@Service
public class NewsServiceByFulltextSearch {

	@Autowired
	private NewsDao newsDao;
	
	@Autowired
	private NewsContentDao newsContentDao;
	
	
	
	/**
	 * 封装详情页附件下载部分
	 * @param newsId
	 * @return
	 */
	public String getFiles(News news){
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("EIPNEWS", "newsFile", news.getNewsId());
		for(IFile file:list){
			sb.append("<a href=\"javaScript:javascript:downloadFile('"+ file.getFileId() +"');\">" + file.getFileName() +"</a><br/>");
		}
		return sb.toString();
	}
	
	public NewsContent getNewsContent(News news) {
		StringBuffer hql = new StringBuffer("from " + NewsContent.class.getName()
				+ " where  newsId = ? ");
		List<NewsContent> list = newsContentDao.find(hql.toString(),news.getNewsId());
		if (list == null || list.size() == 0)
			return null;
		return list.get(0);
	}
	/**
	 * 获取公告列表
	 * @param NewsStatus
	 * @return
	 */
	public <News> List getNewsList(int newsStatus){
		StringBuffer hql = new StringBuffer("from News"
				+ " where  status = ? order by publishTime desc");
		List<News> list = newsDao.find(hql.toString(),newsStatus);
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
}
