package com.supporter.prj.linkworks.oa.bulletin.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.linkworks.oa.bulletin.entity.base.BaseBulletinContent;


/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @version V1.0   
 *
 */
@Entity
@Table(name="OA_BULLETIN_CONTENT",schema="")
public class BulletinContent extends BaseBulletinContent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
