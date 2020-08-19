package com.supporter.prj.linkworks.oa.article.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.linkworks.oa.article.dao.ArticleContentDao;
import com.supporter.prj.linkworks.oa.article.dao.ArticleDao;
import com.supporter.prj.linkworks.oa.article.entity.Article;
import com.supporter.prj.linkworks.oa.article.entity.ArticleContent;

/**   
 * @Title: Service
 * @Description: 功能模块表
 * @author tiansen
 * @version V1.0   
 *
 */
@Service
public class ArticleServiceByFulltextSearch {

	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private ArticleContentDao articleContentDao;
	
	
	
	/**
	 * 封装详情页附件下载部分
	 * @param articleId
	 * @return
	 */
	public String getFiles(Article article){
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OAARTICLE", "filesName", article.getArticleId());
		for(IFile file:list){
			sb.append("<a href=\"javaScript:javascript:downloadFile('"+ file.getFileId() +"');\">" + file.getFileName() +"</a><br/>");
		}
		return sb.toString();
	}
	
	
	public ArticleContent getArticleContent(Article article) {
		// TODO Auto-generated method stub
		return articleContentDao.get(article.getArticleId());
	}
	/**
	 * 获取公告列表
	 * @param ArticleStatus
	 * @return
	 */
	public <Article> List getArticleList(int articleStatus){
		return articleDao.getArticleList(articleStatus);
	}
}
