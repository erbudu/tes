/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.approving.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.bid_startup.approving.entity.ReportResultEntity;
import com.supporter.prj.ppm.bid_startup.approving.service.ReportResultService;
import com.supporter.spring_mvc.AbstractController;

/**
 *<p>Title: 对外提报 - 审核结果</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月3日
 */
@Controller
@RequestMapping("ppm/bid_startup/approving/result/")
public class ReportResultController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ReportResultService reportResultService;
	
	
	/**
	 * <pre>获取通知人员</pre>
	 * @param prjId 项目主键
	 * @return 通知人员列表
	 */
	@RequestMapping("initNoticePerson")
	public @ResponseBody List<Map<String,String>> initNoticePerson(String prjId){
		List<Map<String,String>> list = reportResultService.initNoticePerson(prjId);
		return list;
	}
	
	/**
	 * 删除
	 * @param reportResultId  申报审批结果主键
	 * @return 
	 */
	@RequestMapping("delete")
	public @ResponseBody OperResult delete(String reportResultId) {
		reportResultService.delete(reportResultId);
		return OperResult.succeed(null);
	}
	
	/**
	 * <P>申报审核结果保存更新</P>
	 * @param entity 申报审核结果业务表单实体类
	 * @return 保存成功后的 申报审核结果业务表单实体类数据
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<ReportResultEntity> saveOrUpdate(ReportResultEntity entity){
		if(entity == null) return null;
		UserProfile user = getUserProfile();
		ReportResultEntity returnEntity = reportResultService.saveOrUpdate(entity,user);
		if(returnEntity == null) {
			OperResult.fail("保存失败！", null);
		}
		return OperResult.succeed("保存成功", null,returnEntity);
	}
	
	/**
	 * 保存并生效
	 * @param entity
	 * @return
	 */
	@RequestMapping("effect")
	public @ResponseBody OperResult<ReportResultEntity> effect(ReportResultEntity entity){
		if(entity == null) return null;
		UserProfile user = getUserProfile();
		ReportResultEntity returnEntity = reportResultService.effect(entity,user);
		if(returnEntity == null) {
			OperResult.fail("保存失败！", null);
		}
		return OperResult.succeed("已生效", null,returnEntity);
	}
	/**
	 * <p>初始化新建 编辑页面数据</p>
	 * @param reportStartId 启动申报业务单主键
	 * @param prjId 项目主键
	 * @return 申报审批结果 业务单实体类
	 */
	@RequestMapping("initAddOrEditPage")
	public @ResponseBody ReportResultEntity initAddOrEditPage(String reportStartId,String prjId) {
		if(reportStartId == "" || reportStartId == null) {
			return null;
		}
		ReportResultEntity entity =reportResultService.initAddOrEditPage(reportStartId,prjId);
		return entity;
	}

}
