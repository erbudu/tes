package com.supporter.prj.linkworks.oa.article.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.article.entity.base.BaseVArticle;
import com.supporter.util.CodeTable;
@Entity
@Table(name="OA_V_ARTICLE"
    ,schema=""
)
public class VArticle extends BaseVArticle {

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
	@Transient
	public ArticleContent getArticleContent() {
		return articleContent;
	}
	@Transient
	public void setArticleContent(ArticleContent articleContent) {
		this.articleContent = articleContent;
	}
	@Transient
	public String getStatus() {
		return status;
	}
	@Transient
	public void setStatus(String status) {
		this.status = status;
	}
	@Transient
	public String getFullDeptResourceName() {
		return fullDeptResourceName;
	}
	@Transient
	public void setFullDeptResourceName(String fullDeptResourceName) {
		this.fullDeptResourceName = fullDeptResourceName;
	}
	@Transient
	public String getFiles() {
		return files;
	}
	@Transient
	public void setFiles(String files) {
		this.files = files;
	}
	
	

}
