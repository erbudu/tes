package com.supporter.prj.linkworks.oa.filebox.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.linkworks.oa.filebox.entity.FileboxSetting;
import com.supporter.prj.linkworks.oa.filebox.service.FileboxSettingService;
import com.supporter.spring_mvc.AbstractController;

/**
 * 文件柜设置.
 * @author linda
 *
 */
@Controller
@RequestMapping("/oa/filebox_setting")
public class FileboxSettingController extends AbstractController{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private FileboxSettingService settingService;
	
	/**
	 * 获取文件柜列表.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getActivedFileboxes")
	@ResponseBody
	public List < FileboxSetting > getActivedFileboxes() throws Exception {
		return settingService.getActivedFileboxes();
	}
	
}
