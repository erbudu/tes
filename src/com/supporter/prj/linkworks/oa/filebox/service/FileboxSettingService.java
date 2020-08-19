package com.supporter.prj.linkworks.oa.filebox.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.linkworks.oa.filebox.dao.FileboxSettingDao;
import com.supporter.prj.linkworks.oa.filebox.entity.FileboxSetting;

/**
 * 文件柜设置服务类.
 * @author linda
 *
 */
@Service
public class FileboxSettingService {
	@Autowired
	private FileboxSettingDao settingDao;
	
	/**
	 * 获取设置的可显示的文件柜列表.
	 * @return
	 */
	public List < FileboxSetting > getActivedFileboxes(){
		return settingDao.getActivedFileboxes();
	}
}
