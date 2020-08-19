package com.supporter.prj.linkworks.oa.article.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.article.entity.base.BaseArticle;
import com.supporter.util.CodeTable;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @version V1.0   
 * @author linxiaosong
 */
@Entity
@Table(name="OA_ARTICLE",schema="")
public class Article extends BaseArticle{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static final int DRAFT = 0; //草稿
    public static final int PUBLISHED = 1; //已发布 
    
    /**
     * 获取发布状态码表.
     * @return
     */
    public static CodeTable getPublishStatusCodeTable() { 
        CodeTable lcdtbl_Return = new CodeTable();
        lcdtbl_Return.insertItem(DRAFT,"草稿");
        lcdtbl_Return.insertItem(PUBLISHED,"已发布");
        
        return lcdtbl_Return;
    }
    
	@Transient
	private ArticleContent articleContent;

	@Transient
	private String status;
	
     //连同部门名称一起的部门资源名，格式为：[部门名].[资源名] .
	@Transient
	private String fullDeptResourceName;
	//详情页下载文件
	@Transient
	private String files;
	
	public ArticleContent getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(ArticleContent articleContent) {
		this.articleContent = articleContent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFullDeptResourceName() {
		return fullDeptResourceName;
	}

	public void setFullDeptResourceName(String fullDeptResourceName) {
		this.fullDeptResourceName = fullDeptResourceName;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}
	
	

}
