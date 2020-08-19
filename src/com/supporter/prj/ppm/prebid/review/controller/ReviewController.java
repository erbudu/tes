package com.supporter.prj.ppm.prebid.review.controller;

import java.util.List;
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
import com.supporter.prj.pm.util.OperResultConstant;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReportBfd;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReview;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReviewCon;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReviewEmp;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReviewEmpForGrid;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReviewEmpRp;
import com.supporter.prj.ppm.prebid.review.service.PpmPrebidReviewEmpService;
import com.supporter.prj.ppm.prebid.review.service.PpmPrebidReviewService;
import com.supporter.prj.ppm.prebid.review.service.PrebidReportBfdService;
import com.supporter.prj.ppm.prebid.review.service.PrebidReportBfd_FService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("ppm/prebid/review")
public class ReviewController extends AbstractController {
	private static final long serialVersionUID = 1L;

	@Autowired
	private PpmPrebidReviewService ppmPrebidReviewService;
	@Autowired
	private PpmPrebidReviewEmpService ppmPrebidReviewEmpService;

	@Autowired
	private PrebidReportBfdService bfdService;
	@Autowired
	private PrebidReportBfd_FService bfd_FService;
	
	
	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param ppmPrebidReview
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, PpmPrebidReview ppmPrebidReview) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		List<PpmPrebidReview> querydata = this.ppmPrebidReviewService.getGrid(user, jqGrid, ppmPrebidReview);
		int startRow=jqGrid.getStartIndex();
		int endrow=startRow+jqGrid.getPageSize();
		if(querydata==null||querydata.size()==0) {
			jqGrid.setRowCount(0);
			return jqGrid;
		}
		if(endrow>querydata.size()){
			 List<PpmPrebidReview> data=querydata.subList(startRow, querydata.size());
			 jqGrid.setRows(data);
			 jqGrid.setRowCount(querydata.size());
			 return jqGrid;
		}
		    List<PpmPrebidReview>data=querydata.subList(startRow, endrow);
			jqGrid.setRows(data);
			jqGrid.setRowCount(querydata.size());
			return jqGrid;
	}
	/**
	 * 返回列表，分页表格展示数据
	 * @param request
	 * @param jqGridReq
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("getGridForEmp")
	public @ResponseBody JqGrid getGridForEmp(HttpServletRequest request,
			JqGridReq jqGridReq,Map<String, Object> map, String prebidReviewId,String reviewRoleId )
			throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		List<PpmPrebidReviewEmpForGrid> querydata=
				this.ppmPrebidReviewService.getGridForEmp(user,jqGrid, map,prebidReviewId,reviewRoleId);
		int startRow=jqGrid.getStartIndex();
		int endrow=startRow+jqGrid.getPageSize();
		if(querydata==null||querydata.size()==0) {
			jqGrid.setRowCount(0);
			return jqGrid;
		}
		if(endrow>querydata.size()){
			 List<PpmPrebidReviewEmpForGrid> data=querydata.subList(startRow, querydata.size());
			 jqGrid.setRows(data);
			 jqGrid.setRowCount(querydata.size());
			 return jqGrid;
		}
		    List<PpmPrebidReviewEmpForGrid>data=querydata.subList(startRow, endrow);
			jqGrid.setRows(data);
			jqGrid.setRowCount(querydata.size());
			return jqGrid;
	}
	
	/**
	 * 根据prebidReviewId获取评审信息
	 * @param prebidReviewId
	 * @return
	 */
	@RequestMapping("getDataByPK")
	public @ResponseBody PpmPrebidReview getDataByPK(String prebidReviewId) {
		PpmPrebidReview ppmPrebidReview = this.ppmPrebidReviewService.getDataByPK(prebidReviewId);
		return ppmPrebidReview;
	}
	/**
	 * 根据prebidReviewId获取评审结论状态
	 * @param prebidReviewId
	 * @return
	 */
	@RequestMapping("getConStatus")
	public @ResponseBody PpmPrebidReviewCon getConStatus(String prebidReviewId) {
		PpmPrebidReviewCon ppmPrebidReviewCon = this.ppmPrebidReviewService.getConStatus(prebidReviewId);
		return ppmPrebidReviewCon;
	}
	/**
	 * 检查所选项目是否可以启动授权书
	 * @param prjId 项目id
	 * @return 检查结果
	 */
	@RequestMapping("/checkPowerAttorney")
	@ResponseBody
	public boolean checkPrjStatus(String prjId) {
		return ppmPrebidReviewService.checkPowerAttorney(prjId);
	}
	 /**
	 * 查看页面加载对象
	 * 
	 * @param prebidReviewId prjId
	 * @return
	 */
	@RequestMapping("initEditOrViewPageReview")
	public @ResponseBody PpmPrebidReview initEditOrViewPageReview(
			String prebidReviewId,String prjId) {
		UserProfile user = this.getUserProfile();
		PpmPrebidReview ppmPrebidReview = this.ppmPrebidReviewService
				.initEditOrViewPage(prebidReviewId,prjId, user);
		return ppmPrebidReview;
	}
	 /**
	 * 查看页面加载对象
	 * 
	 * @param reviewEmpId
	 * @return
	 */
	@RequestMapping("initEditOrViewPageReviewEmp")
	public @ResponseBody PpmPrebidReviewEmpForGrid initEditOrViewPageReviewEmp(
			String reviewEmpId) {
		UserProfile user = this.getUserProfile();
		PpmPrebidReviewEmpForGrid ppmPrebidReviewEmpForGrid = this.ppmPrebidReviewEmpService
				.initEditOrViewPageReviewEmp(reviewEmpId, user);
		return ppmPrebidReviewEmpForGrid;
	}
	 /**
	 * 审核结果页面加载对象
	 * 
	 * @param prebidReviewId
	 * @return
	 */
	@RequestMapping("initEditOrViewConResult")
	public @ResponseBody PpmPrebidReviewCon initEditOrViewConResult(
			String prebidReviewId) {
		UserProfile user = this.getUserProfile();
		PpmPrebidReviewCon ppmPrebidReviewCon = this.ppmPrebidReviewService
				.initEditOrViewConResult(prebidReviewId, user);
		return ppmPrebidReviewCon;
	}
	/**
	 * 保存或更新数据.
	 * 
	 * @param ppmPrebidReport
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdateReview")
	public @ResponseBody OperResult<PpmPrebidReview> saveOrUpdate(
			PpmPrebidReview ppmPrebidReview) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		Map<String, Object> valueMap = this
				.getPropValues(PpmPrebidReview.class);
		PpmPrebidReview entity = this.ppmPrebidReviewService.saveOrUpdate(user,
				ppmPrebidReview, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS,
				"保存成功", entity);
	}
	/**
	 * 保存或更新数据--评审人员.
	 * @param ppmPrebidReviewEmp
	 * @return
	 */
	@RequestMapping("saveOrUpdateForEmp")
	public @ResponseBody OperResult<PpmPrebidReviewEmp> saveOrUpdateForEmp(
			PpmPrebidReviewEmp ppmPrebidReviewEmp) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		Map<String, Object> valueMap = this
				.getPropValues(PpmPrebidReviewEmp.class);
		PpmPrebidReviewEmp entity = this.ppmPrebidReviewEmpService.saveOrUpdateForEmp(user,
				ppmPrebidReviewEmp,valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS,
				"保存成功", entity);
	}
	
	/**
	 * 保存或更新数据--评审人员的评审要点.
	 * @param ppmPrebidReviewEmpRp
	 * @return
	 */
	@RequestMapping("saveOrUpdateForEmpRp")
	public @ResponseBody OperResult<PpmPrebidReviewEmpRp> saveOrUpdateForEmpRp(
			PpmPrebidReviewEmpRp ppmPrebidReviewEmpRp) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		Map<String, Object> valueMap = this
				.getPropValues(PpmPrebidReviewEmpRp.class);
		PpmPrebidReviewEmpRp entity =this.ppmPrebidReviewEmpService.saveOrUpdateForEmpRp(user,
				ppmPrebidReviewEmpRp,valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS,
				"保存成功", entity);
	}
	
	/**
	 * 保存或更新数据--审批结果
	 * @param ppmPrebidReviewEmpRp
	 * @return
	 */
	@RequestMapping("saveCon")
	public @ResponseBody OperResult<PpmPrebidReviewCon> saveOrUpdateReviewCon(
			PpmPrebidReviewCon ppmPrebidReviewCon) {
		// 权限验证
		Map<String, Object> valueMap = this
				.getPropValues(PpmPrebidReviewCon.class);
		PpmPrebidReviewCon entity =this.ppmPrebidReviewService.saveOrUpdateReviewCon(ppmPrebidReviewCon,valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS,
				"保存成功", entity);
	}
	
	/**
	 * 获取评估性质类型数据
	 */
	@RequestMapping("selectReviewTypeData")
	public @ResponseBody
	Map<String, String> selectReviewTypeData() {
		return PpmPrebidReview.getReviewTypeMap();
	}
	/**
	 * 用印文件列表
	 * @param businessUUID
	 * @return
	 */
	@RequestMapping("getUseSealGrid")
	public @ResponseBody List<Map<String,String>> getUseSealGrid(String businessUUID){
		List<Map<String,String>> useSealFileGrid = bfd_FService.getUseSealGrid(businessUUID);
		return useSealFileGrid;
	}
	/**
	 * 标记用印文件
	 * @param chkValueStr
	 * @param bidIngUpId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("markUsePrintFile")
	public @ResponseBody OperResult markUsePrintFile(String chkValueStr,String prbidReportId) {
		System.out.println(chkValueStr);
		if(chkValueStr == null || chkValueStr == "") return OperResult.succeed(null, null, null);
		bfdService.markUsePrintFile(chkValueStr,prbidReportId);
		return OperResult.succeed("success", null, null);
	}
	/**
	 * 获取用印业务
	 * @return
	 */
	@RequestMapping("getUseSealBusiness")
	public @ResponseBody String getUseSealBusiness() {
		return bfdService.getUseSealBusiness();
	}
	/**
	 * 初始化确认用印文件
	 * @param prbidReportId
	 * @return
	 */
	@RequestMapping("initPrintFile")
	public @ResponseBody List<PpmPrebidReportBfd> initPrintFile(String prbidReportId){
		if(prbidReportId == "" || prbidReportId == null) {
			return null;
		}
		List<PpmPrebidReportBfd> list = bfdService.initPrintFile(prbidReportId);
		return list;
	}
	/**
	 * 获取盖章后的文件
	 * @param promaryKey
	 * @param fuSealFileId
	 * @param fuSealFileName
	 * @return
	 */
	@RequestMapping("backtUseSealFile")
	public @ResponseBody String  backtUseSealFile(String promaryKey,String fuSealFileId,String fuSealFileName) {
		String result = bfd_FService.backtUseSealFile(promaryKey,fuSealFileId,fuSealFileName);
		return result;
	}
	/**
	 * 提交数据
	 * 
	 * @param purchaseDemand
	 * @return
	 */
	@RequestMapping("commitReview")
	public @ResponseBody OperResult<PpmPrebidReview> commit(
			PpmPrebidReview ppmPrebidReview) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this
				.getPropValues(PpmPrebidReview.class);
		PpmPrebidReview entity = this.ppmPrebidReviewService.commit(user,
				ppmPrebidReview, valueMap);
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
	@RequestMapping("prebidReviewDel")
	public @ResponseBody OperResult<?> reportingStartDel(String prebidReviewId) {
		UserProfile user = this.getUserProfile();
		this.ppmPrebidReviewService.delete(user, prebidReviewId);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}
	/**
	 * 删除Model
	 * 
	 * @param ids
	 *            主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("modelDel")
	public @ResponseBody OperResult<?> modelDel(String dataId) {
		//UserProfile user = this.getUserProfile();
		this.ppmPrebidReviewService.deleModel(dataId);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}
	/**
	 * 删除操作
	 * 
	 * @return
	 */
	@RequestMapping("reviewEmpDel")
	public @ResponseBody OperResult<?> reviewEmpDel(String reviewEmpId) {
		UserProfile user = this.getUserProfile();
		this.ppmPrebidReviewService.reviewEmpDel(user, reviewEmpId);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}
	
}
