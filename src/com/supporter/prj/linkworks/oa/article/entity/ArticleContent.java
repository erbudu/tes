package com.supporter.prj.linkworks.oa.article.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.linkworks.oa.article.entity.base.BaseArticleContent;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @version V1.0   
 * @author linxiaosong
 */
@Entity
@Table(name="OA_ARTICLE_CONTENT",schema="")
public class ArticleContent extends BaseArticleContent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
