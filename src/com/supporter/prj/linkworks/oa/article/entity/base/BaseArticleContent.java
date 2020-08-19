package com.supporter.prj.linkworks.oa.article.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 功能模块,字段与数据库字段一一对应.
 * @author linxiaosong
 * @version V1.0   
 */
@MappedSuperclass
public class BaseArticleContent implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ARTICLE_ID", unique = true, nullable = false, precision = 32, scale = 0)
	private String articleId;
	
	
	//新闻内容
	@Column(name="ARTICLE_CONTENT", nullable = true)
	private String articleContent;


	public BaseArticleContent() {
		super();
	}


	public BaseArticleContent(String articleId, String articleContent) {
		super();
		this.articleId = articleId;
		this.articleContent = articleContent;
	}


	public String getArticleId() {
		return articleId;
	}


	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}


	public String getArticleContent() {
		return articleContent;
	}


	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	
	

}
