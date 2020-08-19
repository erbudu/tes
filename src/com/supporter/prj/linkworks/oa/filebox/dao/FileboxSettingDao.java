package com.supporter.prj.linkworks.oa.filebox.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.linkworks.oa.filebox.entity.FileboxSetting;


/**   
 * @Title: 文件柜设置DAO.
 * @Description: 功能模块表
 * @author linda
 * @version V1.0   
 *
 */
@Repository
public class FileboxSettingDao extends MainDaoSupport < FileboxSetting, String >{
	
	/**
	 * 获取设置的可显示的文件柜列表.
	 * @return
	 */
	public List < FileboxSetting > getActivedFileboxes(){
		String hql = "from " + FileboxSetting.class.getName() + " where actived=true order by displayOrder";
		return this.find(hql);
	}
}
