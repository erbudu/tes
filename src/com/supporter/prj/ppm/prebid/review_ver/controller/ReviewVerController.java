package com.supporter.prj.ppm.prebid.review_ver.controller;

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
import com.supporter.prj.ppm.prebid.review_ver.entity.PpmPrebidReviewVer;
import com.supporter.prj.ppm.prebid.review_ver.entity.PpmPrebidReviewVerBfd;
import com.supporter.prj.ppm.prebid.review_ver.entity.PpmPrebidReviewVerInfo;
import com.supporter.prj.ppm.prebid.review_ver.service.PpmPrebidReviewVerFService;
import com.supporter.prj.ppm.prebid.review_ver.service.PpmPrebidReviewVerService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("ppm/prebid/reviewVer")
public class ReviewVerController extends AbstractController {
	private static final long serialVersionUID = 1L;

	@Autowired
	private PpmPrebidReviewVerService ppmPrebidReviewVerService;
	@Autowired
	private PpmPrebidReviewVerFService prebidReviewVerFService;
	/**
	 * 返回列表. 分页表格展示数据.
	 * 
	 * @param request
	 * @param jqGridReq
	 * @param ppmPrebidReview
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("getVerGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request,
			JqGridReq jqGridReq, PpmPrebidReviewVer ppmPrebidReviewVer)
			throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		List<PpmPrebidReviewVer> querydata = this.ppmPrebidReviewVerService.getGrid(user, jqGrid,
				ppmPrebidReviewVer);
		int startRow=jqGrid.getStartIndex();
		int endrow=startRow+jqGrid.getPageSize();
		if(querydata==null||querydata.size()==0) {
			jqGrid.setRowCount(0);
			return jqGrid;
		}
		if(endrow>querydata.size()){
			 List<PpmPrebidReviewVer> data=querydata.subList(startRow, querydata.size());
			 jqGrid.setRows(data);
			 jqGrid.setRowCount(querydata.size());
			 return jqGrid;
		}
		    List<PpmPrebidReviewVer>data=querydata.subList(startRow, endrow);
			jqGrid.setRows(data);
			jqGrid.setRowCount(querydata.size());
			return jqGrid;
	}

	/**
	 * 查看页面加载对象
	 * 
	 * @param prbidReportId
	 * @return
	 */
	@RequestMapping("initEditOrViewPageReviewVer")
	public @ResponseBody PpmPrebidReviewVer initEditOrViewPageReviewVer(
			String prjId, String reviewVerId, String prebidReviewId) {
		UserProfile user = this.getUserProfile();
		PpmPrebidReviewVer ppmPrebidReviewVer = this.ppmPrebidReviewVerService
				.initEditOrViewPage(prjId, reviewVerId, prebidReviewId, user);
		return ppmPrebidReviewVer;
	}
	/**
	 * 保存或更新数据.
	 * 
	 * @param ppmPrebidReport
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdateReviewVer")
	public @ResponseBody OperResult<PpmPrebidReviewVer> saveOrUpdate(
			PpmPrebidReviewVer ppmPrebidReviewVer) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		Map<String, Object> valueMap = this
				.getPropValues(PpmPrebidReviewVer.class);
		PpmPrebidReviewVer entity = this.ppmPrebidReviewVerService
				.saveOrUpdate(user, ppmPrebidReviewVer, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS,
				"保存成功", entity);
	}

	/**
	 * 获取需评审验证但未评审验证的评审单号
	 * @param prjId 项目id
	 * @return map(评审id,评审单号)
	 */
	@RequestMapping("/getVerReview")
	@ResponseBody
	public Map<String, String> getVerReview(String prjId) {
		return ppmPrebidReviewVerService.getVerReview(prjId);
	}
	/**
	 * 获取评审验证主表信息
	 * @param reviewVerId
	 * @return
	 */
	@RequestMapping("/getVerEntity")
	@ResponseBody
	public PpmPrebidReviewVer getVerEntity(String reviewVerId) {
		
		return ppmPrebidReviewVerService.getVerEntity(reviewVerId);
	}
	/**
	 * 评审验证编辑操作下,获取该条评审验证当前关联的评审单
	 * @return map(评审id,评审单号)
	 */
	@RequestMapping("/getVerReviewPlus")
	@ResponseBody
	public Map<String, String> getVerReviewPlus(String prebidReviewId) {
		return ppmPrebidReviewVerService.getVerReviewPlus(prebidReviewId);
	}
	/**
	 * 获取评审验证对象状态
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("getStutas")
	public @ResponseBody Integer getStutas(String reviewVerId) {
		Integer stutas = this.ppmPrebidReviewVerService.getStutas(reviewVerId);
		return stutas;
	}

	/**
	 * 获取评审验证中用到的评审表中的信息
	 * 
	 * @param prebidReviewId
	 * @return
	 */
	@RequestMapping("getReviewVerInfo")
	public @ResponseBody PpmPrebidReviewVerInfo getReviewVerInfo(String prebidReviewId) {
		PpmPrebidReviewVerInfo ppmPrebidReviewVerInfo = this.ppmPrebidReviewVerService
				.getReviewVerInfo(prebidReviewId);
		return ppmPrebidReviewVerInfo;
	}
	@RequestMapping("copyFile")
	public @ResponseBody OperResult<PpmPrebidReviewVerBfd> copyFile(
			String prjId , String reviewVerId) {
		UserProfile user = this.getUserProfile();
		this.ppmPrebidReviewVerService
				.copyFile(user,prjId,reviewVerId);
		return null;
	}
	
	/**
	 * <pre>
	 * 保存资料清单
	 * </pre>
	 * 
	 * @param ppmPrebidReportBfd
	 *            资料清单业务实体类
	 * @return
	 */
	@RequestMapping("saveBfdForVer")
	public @ResponseBody OperResult<PpmPrebidReviewVerBfd> saveBFD(
			PpmPrebidReviewVerBfd ppmPrebidReviewVerBfd) {
		if (ppmPrebidReviewVerBfd == null) {
			return null;
		}
		PpmPrebidReviewVerBfd backEntity = ppmPrebidReviewVerService
				.saveBfd(ppmPrebidReviewVerBfd);
		if (backEntity == null) {
			return OperResult.fail("保存失败", null);
		}
		return OperResult.succeed("保存成功", null, backEntity);
	}

	/**
	 * 提交数据
	 * 
	 * @param purchaseDemand
	 * @return
	 */
	@RequestMapping("commitReviewVer")
	public @ResponseBody OperResult<PpmPrebidReviewVer> commit(
			PpmPrebidReviewVer ppmPrebidReviewVer) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this
				.getPropValues(PpmPrebidReviewVer.class);
		PpmPrebidReviewVer entity = this.ppmPrebidReviewVerService.commit(user,
				ppmPrebidReviewVer, valueMap);
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
	@RequestMapping("reviewVerDel")
	public @ResponseBody OperResult<?> reviewVerDel(String reviewVerId) {
		UserProfile user = this.getUserProfile();
		this.ppmPrebidReviewVerService.delete(user, reviewVerId);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}
	/**
	 * 资料清单删除 回调方法
	 * 
	 * @param moduleName
	 * @param basiType
	 * @param entityId
	 * @param twolevelid
	 * @return
	 */
	@RequestMapping("deleteBfdForVer")
	public @ResponseBody OperResult<?> delteBFD(String  filesId,String prbidReportId,String bfdTypeId) {
		ppmPrebidReviewVerService.deleteBfd(filesId,prbidReportId,bfdTypeId);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 用印文件列表
	 * @param businessUUID
	 * @return
	 */
	@RequestMapping("getUseSealGrid")
	public @ResponseBody List<Map<String,String>> getUseSealGrid(String businessUUID){
		List<Map<String,String>> useSealFileGrid = prebidReviewVerFService.getUseSealGrid(businessUUID);
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
	public @ResponseBody OperResult markUsePrintFile(String chkValueStr,String reviewVerId) {
		if(chkValueStr == null || chkValueStr == "") return OperResult.succeed(null, null, null);
		prebidReviewVerFService.markUsePrintFile(chkValueStr,reviewVerId);
		return OperResult.succeed("success", null, null);
	}
	/**
	 * 获取用印业务
	 * @return
	 */
	@RequestMapping("getUseSealBusiness")
	public @ResponseBody String getUseSealBusiness() {
		return prebidReviewVerFService.getUseSealBusiness();
	}
	/**
	 * 初始化确认用印文件
	 * @param prbidReportId
	 * @return
	 */
	@RequestMapping("initPrintFile")
	public @ResponseBody List<PpmPrebidReviewVerBfd> initPrintFile(String reviewVerId){
		if(reviewVerId == "" || reviewVerId == null) {
			return null;
		}
		List<PpmPrebidReviewVerBfd> list = prebidReviewVerFService.initPrintFile(reviewVerId);
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
		String result = prebidReviewVerFService.backtUseSealFile(promaryKey,fuSealFileId,fuSealFileName);
		return result;
	}
	
	/**
	 * 检查所选项目是否可以启动评审验证
	 * @param prjId 项目id
	 * @return 检查结果
	 */
	@RequestMapping("/checkReviewVer")
	@ResponseBody
	public boolean checkPrjStatus(String prjId) {
		return ppmPrebidReviewVerService.checkReviewVer(prjId);
	}

}
