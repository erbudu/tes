package com.supporter.prj.cneec.tpc.benefit_note.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.benefit_note.entity.BenefitNote;
import com.supporter.prj.cneec.tpc.benefit_note.service.BenefitNoteService;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.HttpUtil;

/**
 * @Title: BenefitNoteController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2018-06-01
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/benefitNote")
public class BenefitNoteController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private BenefitNoteService benefitNoteService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param benefitNote
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.benefitNoteService.getGrid(user, jqGrid, this.getRequestParameters());
		return jqGrid;
	}

	/**
	 * 
	 * @param response
	 * @param noteId
	 * @throws Exception
	 */
	@RequestMapping("getOtherBenefitNoteMap")
	public @ResponseBody
	Map<String, String> getOtherBenefitNoteMap(HttpServletResponse response, String noteId, String projectId) throws Exception {
		Map<String, Object> valueMap = this.getRequestParameters();
		return this.benefitNoteService.getOtherBenefitNoteMap(noteId, projectId, valueMap);
	}

	/**
	 * 获取汇总效益币别表
	 * @param request
	 * @param jqGridReq
	 * @param noteId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getNoteCurrencyGrid")
	public @ResponseBody
	JqGrid getNoteCurrencyGrid(HttpServletRequest request, JqGridReq jqGridReq, String noteId) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.benefitNoteService.getNoteCurrencyGrid(jqGrid, noteId, this.getRequestParameters());
		return jqGrid;
	}

	/**
	 * 获取汇总效益测算表
	 * @param request
	 * @param jqGridReq
	 * @param noteId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getNoteBudgetGrid")
	public @ResponseBody
	JqGrid getNoteBudgetGrid(HttpServletRequest request, JqGridReq jqGridReq, String noteId) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.benefitNoteService.getNoteBudgetGrid(jqGrid, noteId, this.getRequestParameters());
		return jqGrid;
	}

	/**
	 * 获取效益预算集合表头
	 * @param response
	 * @param noteId
	 * @throws Exception
	 */
	@RequestMapping("getBenefitAssembleTitleData")
	public void getBenefitAssembleTitleData(HttpServletResponse response, String noteId) throws Exception {
		Map<String, Object> valueMap = this.getRequestParameters();
		String json = this.benefitNoteService.getBenefitAssembleTitleData(noteId, valueMap);
		HttpUtil.write(response, json);
	}

	/**
	 * 获取效益预算集合数据
	 * @param response
	 * @param noteId
	 * @throws Exception
	 */
	@RequestMapping("getBenefitAssembleGrid")
	public void getBenefitAssembleGrid(HttpServletResponse response, String noteId) throws Exception {
		Map<String, Object> valueMap = this.getRequestParameters();
		String json = this.benefitNoteService.getBenefitAssembleGrid(noteId, valueMap);
		HttpUtil.write(response, json);
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param noteId
	 * @param map
	 */
	@ModelAttribute
	public void getBenefitNote(String noteId, Map<String, Object> map) {
		if (!StringUtils.isBlank(noteId)) {
			BenefitNote benefitNote = this.benefitNoteService.get(noteId);
			if (benefitNote != null) {
				map.put("benefitNote", benefitNote);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param noteId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	BenefitNote get(String noteId) {
		BenefitNote benefitNote = this.benefitNoteService.get(noteId);
		return benefitNote;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param noteId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	BenefitNote initEditOrViewPage(String noteId) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(BenefitNote.class);
		BenefitNote benefitNote = this.benefitNoteService.initEditOrViewPage(noteId, valueMap, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.benefitNoteService.getAuthCanExecute(user, benefitNote);
		}
		return benefitNote;
	}

	/**
	 * 事业部评审初始生成/编辑效益测算表
	 * @param noteId
	 * @return
	 */
	@RequestMapping("initByDeptReviewInforId")
	public @ResponseBody
	BenefitNote initByDeptReviewInforId(String inforId) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		BenefitNote benefitNote = this.benefitNoteService.initByDeptReviewInforId(inforId, valueMap, user);
		return benefitNote;
	}

	/**
	 * 公司评审初始生成/编辑效益测算表
	 * @param noteId
	 * @return
	 */
	@RequestMapping("initByCompanyReviewInforId")
	public @ResponseBody
	BenefitNote initByCompanyReviewInforId(String inforId) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		BenefitNote benefitNote = this.benefitNoteService.initByCompanyReviewInforId(inforId, valueMap, user);
		return benefitNote;
	}

	/**
	 * 合同事业部/公司评审初始化效益测算表
	 * @param noteId
	 * @return
	 */
	@RequestMapping("initByReview")
	public @ResponseBody
	BenefitNote initByReview(String noteId, String versionsType) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		BenefitNote benefitNote = this.benefitNoteService.initByReview(noteId, versionsType, valueMap, user);
		return benefitNote;
	}

	/**
	 * 合同事业部评审完成初始化效益测算表(批量)
	 * @param signId
	 * @return
	 */
	@RequestMapping("initAllByDeptReview")
	public void initAllByDeptReview(HttpServletResponse response, String signId) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		String json = this.benefitNoteService.createBenefitNoteListByDeptReview(signId, valueMap, user);
		HttpUtil.write(response, json);
	}

	/**
	 * 合同公司评审完成初始化效益测算表(批量)
	 * @param signId
	 * @return
	 */
	@RequestMapping("initAllByCompanyReview")
	public void initAllByCompanyReview(HttpServletResponse response, String signId) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		String json = this.benefitNoteService.createBenefitNoteListByCommpanyReview(signId, valueMap, user);
		HttpUtil.write(response, json);
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param benefitNote 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<BenefitNote> saveOrUpdate(BenefitNote benefitNote) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.benefitNoteService.getAuthCanExecute(user, benefitNote);
		//Map<String, Object> valueMap = this.getPropValues(BenefitNote.class);
		Map<String, Object> valueMap = this.getRequestParameters();
		BenefitNote entity = this.benefitNoteService.saveOrUpdate(user, benefitNote, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}

	/**
	 * 提交数据
	 * @param benefitNote
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<BenefitNote> commit(BenefitNote benefitNote) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.benefitNoteService.getAuthCanExecute(user, benefitNote);
		Map<String, Object> valueMap = this.getRequestParameters();
		BenefitNote entity = this.benefitNoteService.commit(user, benefitNote, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS, "提交成功", entity);
	}

	/**
	 * 评审结束时若修改效益测算表提交流程
	 * @param benefitNote
	 * @return
	 */
	@RequestMapping("submit")
	public @ResponseBody
	OperResult<BenefitNote> submit(BenefitNote benefitNote) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.benefitNoteService.getAuthCanExecute(user, benefitNote);
		Map<String, Object> valueMap = this.getRequestParameters();
		BenefitNote entity = this.benefitNoteService.submit(user, benefitNote, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS, "提交成功", entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param noteIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String noteIds) {
		UserProfile user = this.getUserProfile();
		this.benefitNoteService.delete(user, noteIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 导出效益测算表
	 * @param request
	 * @param response
	 * @param salary
	 * @param empName
	 * @throws Exception
	 */
	@RequestMapping("downExcel")
	public @ResponseBody
	void downExcel(HttpServletResponse response, String noteId) throws Exception {
		String downFileName = new String(StringUtils.trimToEmpty(this.getRequestPara("downFileName")).getBytes("ISO8859-1"), "UTF-8");
		OutputStream os = null;
		UserProfile user = this.getUserProfile();
		try {
			Workbook wb = this.benefitNoteService.getWorkbook(noteId, downFileName, user);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(downFileName, "utf-8"));
			os = response.getOutputStream();
			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			os.flush();
			os.close();
		}
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData(Boolean hasProc) {
		return BenefitNote.getSwfStatusMap(hasProc);
	}

	/**
	 * 常用币别
	 * @return
	 */
	@RequestMapping(value = "/selectCommonCurrencyData")
	public @ResponseBody
	Map<String, String> selectCommonCurrencyData() {
		Map<String, String> currencyMap = TpcConstant.getCommonCurrencyMap();
		currencyMap.put("", "--请选择--");
		return currencyMap;
	}

	/**
	 * 校验合同名称
	 */
	@RequestMapping("checkNameIsValid")
	public @ResponseBody
	Boolean checkNameIsValid(String noteId, String contractName) {
		return this.benefitNoteService.checkNameIsValid(noteId, contractName);
	}

}