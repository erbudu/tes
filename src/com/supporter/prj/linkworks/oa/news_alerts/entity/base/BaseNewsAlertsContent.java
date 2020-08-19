package com.supporter.prj.linkworks.oa.news_alerts.entity.base;

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
public class BaseNewsAlertsContent implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "NEWS_ALERTS_ID", unique = true, nullable = false, precision = 32, scale = 0)
	private String newsAlertsId;
	
	
	//新闻内容
	@Column(name="NEWS_CONTENT", nullable = true)
	private String newsContent;


	
	
	public BaseNewsAlertsContent(String newsAlertsId, String newsContent) {
		super();
		this.newsAlertsId = newsAlertsId;
		this.newsContent = newsContent;
	}

	
	

	public BaseNewsAlertsContent() {
		super();
	}




	public String getNewsAlertsId() {
		return newsAlertsId;
	}


	public void setNewsAlertsId(String newsAlertsId) {
		this.newsAlertsId = newsAlertsId;
	}


	public String getNewsContent() {
		return newsContent;
	}


	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	
}
