package com.supporter.prj.linkworks.oa.filebox.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.supporter.prj.linkworks.oa.filebox.entity.base.BaseFileboxSetting;

/**   
 * @Title: 文件柜设置实体类
 * @Description: OA_FILEBOX_SETTING
 * @author linda
 * @date 2017-10-17 13:57:49
 * @version V1.0   
 *
 */
@Entity
@Table(name = "OA_FILEBOX_SETTING", schema = "")
public class FileboxSetting extends BaseFileboxSetting {

	private static final long serialVersionUID = 1L;
	
	//扩展数据库以外的其他属性，注意属性前加@Transient注解标示非数据库字段
	
	/**
	 * 构造方法.
	 */
	public FileboxSetting() {
		
	}
	
}
