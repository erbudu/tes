/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.approving.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.bid_startup.approving.entity.ReportStartEntity;
import com.supporter.prj.ppm.bid_startup.approving.service.ReportResultService;
import com.supporter.prj.ppm.bid_startup.approving.service.ReportStartService;
import com.supporter.spring_mvc.AbstractController;

/**
 *<p>Title: 对外提报前端控制器</p>
 *<p>Description: 投议标备案及获批-对外提报及获批-启动对外提报 前端控制层</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月2日
 */
@Controller
@RequestMapping("ppm/bid_startup/approving/start")
public class ReportStartController extends AbstractController{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ReportStartService reportStartService;
	
	@Autowired
	private ReportResultService ReportResultService;
	
	/**
	 * <pre>确认通知</pre>
	 * @param prjId 项目主键
	 * @param reportStartId 启动对外提报主键
	 * @return 实体类信息
	 */
	@RequestMapping("confirmNotice")
	public @ResponseBody ReportStartEntity confirmNotice(String prjId,String reportStartId) {
		UserProfile user = getUserProfile();
		ReportStartEntity entity = reportStartService.confirmNotice(prjId,reportStartId,user);
		return entity;
	}
	
	
	/**
	 * <p>删除   -- 删除所又节点的业务表单</p>
	 * @param reportStartId
	 * @return
	 */
	@RequestMapping("deleteBatch")
	public @ResponseBody OperResult deleteBatch(String reportStartId) {
		if(reportStartId == "" || reportStartId == null) {
			return OperResult.fail("删除失败！", null);
		}
		ReportResultService.deleteByRrpStartId(reportStartId);//删除申报审批结果
		reportStartService.deleteSingle(reportStartId);
		return OperResult.succeed(null);
	}
	
	/**
	 * <p>单项删除</P>
	 * @param reportStartId
	 * @return
	 */
	@RequestMapping("deleteSingle")
	public @ResponseBody OperResult deleteSingle(String reportStartId) {
		if(reportStartId == "" || reportStartId == null) {
			return OperResult.fail("删除失败！", null);
		}
		reportStartService.deleteSingle(reportStartId);
		return OperResult.succeed(null);
		
	}
	
	/**
	 * <p>启动申报-- 业务表单保存更新</p>
	 * @param reportStartEntity 启动申报业务表单实体类
	 * @return 保存成功后的业务表单实体类
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<ReportStartEntity> saveOrUpdate(ReportStartEntity reportStartEntity){
		
		UserProfile user = getUserProfile();
		ReportStartEntity entity = reportStartService.saveOrUpdate(user,reportStartEntity);
		return OperResult.succeed("保存成功", null, entity);
		
	}
	
	/**
	 * <pre>初始化新建、编辑页面数据</Pre>
	 * @param prjId 项目主键
	 * @return
	 */
	@RequestMapping("initAddOrEditPage")
	public @ResponseBody ReportStartEntity initAddOrEditPage(String prjId) {
		if(prjId == "" || prjId == null) {
			return null;
		}
		ReportStartEntity entity = reportStartService.initAddOrEditPage(prjId);
		return entity;	
	}
	
	@RequestMapping("validRole")
	public @ResponseBody boolean validRole(String prjId) {
		UserProfile user = this.getUserProfile();
		return this.reportStartService.validRole(user,prjId);
	}

}
