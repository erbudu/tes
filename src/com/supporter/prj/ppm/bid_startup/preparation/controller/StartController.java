/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.preparation.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.bid_startup.preparation.constant.StartContant;
import com.supporter.prj.ppm.bid_startup.preparation.entity.StartEntity;
import com.supporter.prj.ppm.bid_startup.preparation.service.StartService;
import com.supporter.spring_mvc.AbstractController;

/**
 *<p>Title: 投议标备案及许可->申报准备->启动申报前端控制层</p>
 *<p>Description: 接收前端请求，调用业务层处理请求，返回结果</p>
 *<p>Company: </p>s
 * @author YUEYUN
 * @date 2019年8月13日
 */
@Controller
@RequestMapping("ppm/bid_startup/preparation/")
public class StartController extends AbstractController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private StartService startService;
	
	/**用印流程启动成功后更新业务表单的流程状态*/
	@RequestMapping("updateStautsAfterPrintStart")
	public @ResponseBody StartEntity updateStautsAfterPrintStart(String bidIngUpId) {
		
		StartEntity entity = startService.updateStautsAfterPrintStart(bidIngUpId);
		return entity;
	}
	
	@RequestMapping("getUseSealBusiness")
	public @ResponseBody String getUseSealBusiness() {
		return startService.getUseSealBusiness();
	}
	
	@RequestMapping("staging")
	public @ResponseBody OperResult<StartEntity> staging(String prjId,String otherContent){
		StartEntity entity = startService.staging(prjId,otherContent);
		if(entity == null) {
			return OperResult.fail("保存失败", "", null);
		}
		return OperResult.succeed("保存成功", "", null);
	}
	
	/**
	 * @Title 批量删除
	 * @Description 删除所有表单信息
	 * @param bidIngUpID
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public OperResult deleteBatch(String bidIngUpId) {

		startService.delete(bidIngUpId);
		//TODO 调用其他表单的service删除其他表单信息
		return OperResult.succeed(null);
	}
	
	/**
	 * <pre>
	 * @Title : 单项删除
	 * @Description ： 只删除当前表的信息
	 * </pre>
	 */
	@RequestMapping("deleteSingle")
	@ResponseBody
	public OperResult delete(String bidIngUpId) {
		startService.delete(bidIngUpId);
		return OperResult.succeed(null);
	}
	
	
	/**
	 * <pre>
	 * @Title : 新建或编辑页面初始化表单数据
	 * @param prjId 项目主键
	 * @return 申报准备-启动申报 实体类
	 * </pre>
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody StartEntity initEditOrViewPage(String prjId) {
		
		if(prjId == "" || prjId == null) {
			return null;
		}
		StartEntity backEntity = startService.initEditOrViewPage(prjId);
		return backEntity;

	}
	
	/**
	 * <pre>
	 * @Title : 启动申报 表单数据保存、更新
	 * @Description : 
	 * @param entity 表单数据绑定的实体类
	 * @return 执行结果
	 * </pre>
	 */
	@RequestMapping("saveOrUpdate")
	@ResponseBody
	public OperResult<StartEntity>  savaOrUpdate(StartEntity entity) {
		 UserProfile user = getUserProfile();
		 StartEntity backEntity = startService.saveOrUpdate(user,entity);
		 if(backEntity != null) {
			 return OperResult.succeed("保存成功", null,backEntity);
		 }
		return OperResult.fail("保存失败", null);
	}
	
	/**
	 * 是否需要出口管制
	 * @param prjId
	 * @return
	 */
	@RequestMapping("checkECC")
	public @ResponseBody OperResult< ? > checkECC(String prjId) {
		UserProfile user = this.getUserProfile();
		Map<String , Object> map = this.startService.checkECC(prjId);
		String message = (String)map.get("msg");
		boolean result = (Boolean)map.get("flag");
		OperResult<?> o =OperResult.succeed(null, message, result);
		return o;
	}
	
	/**
	 * 获取函件类型
	 * @return
	 */
	@RequestMapping("getLettersType")
	public @ResponseBody Map<String, String> getLettersType(){
		return StartContant.getLettersType();
	}
	
	/**
	 * 将流程id置空
	 * @param entity
	 * @return
	 */
	
	@RequestMapping("clearProcId")
	public @ResponseBody String clearProcId(StartEntity entity) {
		
		startService.clearProcId(entity);
		return "success";
	}

}
