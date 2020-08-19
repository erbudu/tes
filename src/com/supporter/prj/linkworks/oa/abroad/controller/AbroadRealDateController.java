package com.supporter.prj.linkworks.oa.abroad.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.abroad.entity.AbroadRealDate;
import com.supporter.prj.linkworks.oa.abroad.service.AbroadRealDateService;
import com.supporter.spring_mvc.AbstractController;

/**   
 * @Title: Controller
 * @Description: 出国审批.
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("oa/abroadRealDate")
public class AbroadRealDateController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AbroadRealDateService abroadRealDateService;
	


	/**
	 * 通过出国审批id获取实际出国时间
	 * @param recordId
	 * @return
	 */
	@RequestMapping("getRealDateByAbroadId")
	public @ResponseBody String getRealDateByAbroadId(String recordId) {
		String realDateApply = abroadRealDateService.getRealDateByAbroadId(recordId);
		return realDateApply;
	}
	@RequestMapping("getEntityByAbroadId")
	public @ResponseBody AbroadRealDate getEntityByAbroadId(String recordId) {
		AbroadRealDate realDateApply = abroadRealDateService.getEntityByAbroadId(recordId);
		return realDateApply;
	}
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param reportId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody AbroadRealDate initEditOrViewPage(String recordId) {
		AbroadRealDate entity = abroadRealDateService.initEditOrViewPage(recordId,this.getUserProfile());
		return entity;
	}
	
	/**
	 * 保存或更新数据.
	 * 
	 * @param abroad 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<AbroadRealDate> saveOrUpdate(String realId,String realDateApply) {
		UserProfile user = this.getUserProfile();
		AbroadRealDate entity = this.abroadRealDateService.saveOrUpdate(realId,realDateApply,user);
		return OperResult.succeed("saveSuccess", null, entity);
	}

}
