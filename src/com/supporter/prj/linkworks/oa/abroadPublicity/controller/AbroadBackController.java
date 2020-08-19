package com.supporter.prj.linkworks.oa.abroadPublicity.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.abroadPublicity.entity.AbroadBack;
import com.supporter.prj.linkworks.oa.abroadPublicity.service.AbroadBackService;
import com.supporter.spring_mvc.AbstractController;


/**   
 * @Title: Controller
 * @Description: 回国报告.
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("oa/abroadBack")
public class AbroadBackController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	AbroadBackService abroadBackService;
	
	
	/**
	 * 进入新建或编辑时加载的信息
	 * 
	 * @param reportId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody AbroadBack initEditOrViewPage(String pubId) {
		AbroadBack entity = abroadBackService.initEditOrViewPage(pubId,this.getUserProfile());
		return entity;
	}
	
	/**
	 * 进入审批页面时加载的信息
	 * 
	 * @param reportId 主键
	 * @return
	 */
	@RequestMapping("initswfPage")
	public @ResponseBody AbroadBack initswfPage(String backId) {
		AbroadBack entity = abroadBackService.get(backId);
		return entity;
	}
	
	
	/**
	 * 保存或更新数据.
	 * 
	 * @param abroad 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("save")
	public @ResponseBody OperResult<AbroadBack> save(AbroadBack abroadBack ,String submitEndDate) {
		UserProfile user = this.getUserProfile();
		AbroadBack entity = this.abroadBackService.save(user, abroadBack,submitEndDate);
		return OperResult.succeed("saveSuccess", null, entity);
		
	}
	
	/**
	 * 保存最晚报告时间.
	 * 
	 * @param abroad 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("updateSubmitEndDate")
	public @ResponseBody OperResult<AbroadBack> updateSubmitEndDate(String backId ,String submitEndDate) {
		this.abroadBackService.updateSubmitEndDate(backId,submitEndDate);
		return OperResult.succeed("saveSuccess", null, null);
		
	}
	
}
