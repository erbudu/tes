package com.supporter.prj.linkworks.oa.util.file_upload.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.user_defined.entity.NetMaintain;
import com.supporter.prj.linkworks.oa.util.file_upload.service.DianZiQianMingService;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Controller
 * @Description: 报告.
 * @author liyinfeng
 * @date 2016-12-06 15:25:34
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("oa/dianziqianming")
public class DianZiController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private DianZiQianMingService service;

	
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("batchExtractFiles")
	public @ResponseBody String batchExtractFiles(){
		return service.batchExtractFiles(this.getUserProfile());
	}		
	
}
