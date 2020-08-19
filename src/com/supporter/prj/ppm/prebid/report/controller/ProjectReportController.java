package com.supporter.prj.ppm.prebid.report.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.util.OperResultConstant;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReport;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReportBfd;
import com.supporter.prj.ppm.prebid.report.service.ProjectReportService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("ppm/prebid/report")
public class ProjectReportController extends AbstractController {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ProjectReportService projectReportService;
	/**
	 * 查看页面加载对象
	 * 
	 * @param prbidReportId
	 * @return
	 */
	@RequestMapping("initEditOrViewPageReport")
	public @ResponseBody PpmPrebidReport initEditOrViewPageReport(
			String prbidReportId,String prjId) {
		UserProfile user = this.getUserProfile();
		PpmPrebidReport PpmPrebidReport = this.projectReportService
				.initEditOrViewPageReport(prbidReportId, user,prjId);
		return PpmPrebidReport;
	}
	
	/**
	 * 保存或更新数据.
	 * 
	 * @param ppmPrebidReport
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdateRepotrting")
	public @ResponseBody OperResult<PpmPrebidReport> saveOrUpdate(
			PpmPrebidReport ppmPrebidReport) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		Map<String, Object> valueMap = this
				.getPropValues(PpmPrebidReport.class);
		PpmPrebidReport entity = this.projectReportService.saveOrUpdate(user,
				ppmPrebidReport, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS,
				"保存成功", entity);
	}	
	/**
	 * 直接生效.
	 * 
	 * @param ppmPrebidReport
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("passStutas")
	public @ResponseBody OperResult<PpmPrebidReport> passStutas(
			PpmPrebidReport ppmPrebidReport) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		Map<String, Object> valueMap = this
				.getPropValues(PpmPrebidReport.class);
		PpmPrebidReport entity = this.projectReportService.passStutas(user,
				ppmPrebidReport, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS,
				"保存成功", entity);
	}
	/**
	 * <pre>保存资料清单</pre>
	 * @param ppmPrebidReportBfd 资料清单业务实体类
	 * @return
	 */
	@RequestMapping("saveBfd")
	public @ResponseBody OperResult<PpmPrebidReportBfd> saveBFD(PpmPrebidReportBfd ppmPrebidReportBfd){
		if(ppmPrebidReportBfd == null) {
			return null;
		}
		PpmPrebidReportBfd backEntity = projectReportService.saveBfd(ppmPrebidReportBfd);
		if(backEntity == null) {
			return OperResult.fail("保存失败", null);
		}
		return OperResult.succeed("保存成功", null,backEntity);
	}
	/**
	 * 提交数据
	 * 
	 * @param purchaseDemand
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody OperResult<PpmPrebidReport> commit(
			PpmPrebidReport ppmPrebidReport) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this
				.getPropValues(PpmPrebidReport.class);
		PpmPrebidReport entity = this.projectReportService.commit(user,
				ppmPrebidReport, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS,
				"提交成功", entity);
	}
	/**
	 * 删除操作
	 * 
	 * @param ids
	 *            主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("reportingStartDel")
	public @ResponseBody OperResult<?> reportingStartDel(String prbidReportId) {
		UserProfile user = this.getUserProfile();
		this.projectReportService.delete(user, prbidReportId);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}
	/**
	 *  资料清单删除
	 * @param moduleName
	 * @param basiType
	 * @param entityId
	 * @param twolevelid
	 * @return
	 */
	@RequestMapping("deleteBfd")
	public @ResponseBody OperResult delteBFD(String  filesId,String prbidReportId,String bfdTypeId) {
		projectReportService.deleteBfd(filesId,prbidReportId,bfdTypeId);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 根据prjId获取prbidReportId
	 * @param prjId
	 * @return
	 */
	@RequestMapping("getPrbidReportIdByPrjId")
	public @ResponseBody String getPrbidReportIdByPrjId(String prjId){
		return this.projectReportService.getPrbidReportIdByPrjId(prjId);
	}
	/**
	 * 判断是否已报审
	 * @param prjId
	 * @return
	 */
	@RequestMapping("isOrNotReport")
	public @ResponseBody OperResult<PpmPrebidReport> isOrNotReport(
			String prjId) {
		List<PpmPrebidReport> list = this.projectReportService.isOrNotReport(prjId);
		if(list.size()>0) {
			Integer status = list.get(0).getPretenderReportStatus();
			if(status==20) {
				return OperResult.succeed("succeed", null,null);
			}
		}
		return OperResult.fail(null, null);
	}
	/**
	 * 检查所选项目是否可以启动报审
	 * @param prjId 项目id
	 * @return 检查结果
	 */
	@RequestMapping("/checkStatus")
	@ResponseBody
	public boolean checkPrjStatus(String prjId) {
		return projectReportService.checkStatus(prjId);
	}
}
