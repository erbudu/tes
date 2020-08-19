package com.supporter.prj.ppm.template_register.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.dept.util.Params;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.template_register.entity.Page;
import com.supporter.prj.ppm.template_register.entity.TemplateKeyPointsExam;
import com.supporter.prj.ppm.template_register.entity.TemplateRegister;
import com.supporter.prj.ppm.template_register.entity.TemplateRegisterDetail;
import com.supporter.prj.ppm.template_register.entity.TemplateRegisterTable;
import com.supporter.prj.ppm.template_register.entity.TreeVO;
import com.supporter.prj.ppm.template_register.service.TemplateRegisterService;
import com.supporter.prj.ppm.template_register.util.ConvertUtils;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.spring_mvc.model.MvcResponse;
import com.supporter.util.CommonUtil;


/**   
 * @Title: Controller
 * @Description: 模板注册.
 * @author liyinfeng
 * @date 2016-12-06 15:25:34
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("ppm/templateRegister")
public class TemplateRegisterController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private TemplateRegisterService templateRegisterService;

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, TemplateRegister templateRegister) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.templateRegisterService.getGrid(user, jqGrid, templateRegister);
		return jqGrid;
	}
	
	/**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getDetailGrid")
	public @ResponseBody JqGrid getDetailGrid(HttpServletRequest request, JqGridReq jqGridReq, String templateId, String parentId, TemplateRegisterDetail templateRegisterDetail) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.templateRegisterService.getDetailGrid(user, jqGrid, templateId, parentId, templateRegisterDetail);
		return jqGrid;
	}
	
	/**
	 * This method is run by tree Ajax request everytime 
	 * when we are getting data for Tree.<p>
	 * 获取树状json数据
	 * 用于显示段落
	 * @param writer the writer for output
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getTreeData")
	public Page<TreeVO> getTreeData() throws IOException {
		Map<String, Object> paramMap = getRequestParameters();
		Params sysParamUtil = new Params(paramMap);
		int pageNumber = sysParamUtil.getParaToInt(Page.PAGE, Integer.valueOf(1)).intValue();
		List<TreeVO> list = templateRegisterService.getTreeData(paramMap);
		Page<TreeVO> page = new Page<TreeVO>(pageNumber, Integer.MAX_VALUE, (long)list.size(), "", "");
		ConvertUtils.setParent(list);
		page.setRows(list);
		return page;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDetailList")
	public MvcResponse<List<TemplateRegisterDetail>> getDetailList(String templateId, String paragraphNo)
	    throws UnsupportedEncodingException{
	    List<TemplateRegisterDetail> detailList = this.templateRegisterService.getDetailList(templateId, paragraphNo);
	    return MvcResponse.success(detailList);
	}

	/**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getTableGrid")
	public @ResponseBody JqGrid getTableGrid(HttpServletRequest request, JqGridReq jqGridReq, String detailId, TemplateRegisterTable templateRegisterTable) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.templateRegisterService.getTableGrid(user, jqGrid, detailId, templateRegisterTable);
		return jqGrid;
	}
	
	/**
     * 返回列表.
     * 生成列表样式
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getTableEditGrid")
	public @ResponseBody String getTableEditGrid(HttpServletRequest request, JqGridReq jqGridReq, String detailId, TemplateRegisterTable templateRegisterTable) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		String json = this.templateRegisterService.getTableEditGrid(user, jqGrid, detailId, templateRegisterTable);
		//this.templateRegisterService.getTableEditGrid(user, jqGrid, detailId, templateRegisterTable);
		return json;
	}
	
	/**
	 * 获取评审要点数据，及审批意见
	 * 用于评估审批页面右下角审批意见
	 * @param templateId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDetailListByReviewKeyPoint")
	public MvcResponse<List<TemplateRegisterDetail>> getDetailListByReviewKeyPoint(String templateId, String procId){
		UserProfile user = this.getUserProfile();
	    List<TemplateRegisterDetail> detailList = this.templateRegisterService.getDetailListByReviewKeyPoint(templateId, procId, user);
	    return MvcResponse.success(detailList);
	}
	
	/**
	 * 码表点选
	 * @return
	 */
	@RequestMapping(value = "/getReviewKeyPointCodeTables")
	public @ResponseBody
	Map<String, String> getReviewKeyPointCodeTables(String templateId) {
		List<TemplateRegisterDetail> items = this.templateRegisterService.getDetailListByReviewKeyPoint(templateId);
		Map<String, String> map = new TreeMap<String, String>();//HashMap无序，改为TreeMap
		if(items != null && !items.isEmpty()) {
			for(TemplateRegisterDetail item : items) {
				map.put(item.getDetailId(), item.getTextDisplay());
			}
		}
		return map;
	}
	
	/**
	 * 获取树状json数据
	 * 目前用于投标前评审
	 * 用于显示评审要点
	 * @param writer the writer for output
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getTreeDataByReviewKeyPoint")
	public Page<TreeVO> getTreeDataByReviewKeyPoint() throws IOException {
		Map<String, Object> paramMap = getRequestParameters();
		Params sysParamUtil = new Params(paramMap);
		int pageNumber = sysParamUtil.getParaToInt(Page.PAGE, Integer.valueOf(1)).intValue();
		//判断当前登录人可以看的评审要点内容
		String dataId = CommonUtil.trim(sysParamUtil.getPara("dataId"));
		UserProfile user = this.getUserProfile();
		
		List<TreeVO> list = templateRegisterService.getTreeDataByReviewKeyPoint(paramMap);
		Page<TreeVO> page = new Page<TreeVO>(pageNumber, Integer.MAX_VALUE, (long)list.size(), "", "");
		ConvertUtils.setParent(list);
		page.setRows(list);
		return page;
	}
	/**
	 * 获取评审段落数据
	 * @param templateId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDetailListByParagraphNo")
	public MvcResponse<List<TemplateRegisterDetail>> getDetailListByParagraphNo(String templateId){
	    List<TemplateRegisterDetail> detailList = this.templateRegisterService.getDetailListByParagraphNo(templateId);
	    return MvcResponse.success(detailList);
	}
	
	/**
     * 返回审批意见列表.
	 * 分页表格展示数据.
	 * 根据审批人ID
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getExamGridByEmpId")
	public @ResponseBody JqGrid getExamGridByEmpId(HttpServletRequest request, JqGridReq jqGridReq, String empId, String templateId, String procId) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.templateRegisterService.getExamGridByEmpId(user, jqGrid, empId, templateId, procId);
		return jqGrid;
	}
	
	/**
     * 返回审批意见列表.
	 * 分页表格展示数据.
	 * 根据审批人ID
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getExamGridByProcId")
	public @ResponseBody JqGrid getExamGridByProcId(HttpServletRequest request, JqGridReq jqGridReq, String procId) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.templateRegisterService.getExamGridByProcId(user, jqGrid, procId);
		return jqGrid;
	}	
	/**
     * 返回审批意见列表.
	 * 分页表格展示数据.
	 * 根据审批人ID
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("getPointsExamGridByProcId")
	public @ResponseBody JqGrid getPointsExamGridByProcId(HttpServletRequest request, JqGridReq jqGridReq, String procId) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		List<TemplateKeyPointsExam> recs = this.templateRegisterService.getPointsExamGridByProcId(user, jqGrid, procId);
		
		int startRow=jqGrid.getStartIndex();
		int endrow=startRow+jqGrid.getPageSize();
		if(recs==null||recs.size()==0) {
			jqGrid.setRowCount(0);
			return jqGrid;
		}
		if(endrow>recs.size()){
			 List<TemplateKeyPointsExam> data=recs.subList(startRow, recs.size());
			 jqGrid.setRows(data);
			 jqGrid.setRowCount(recs.size());
			 return jqGrid;
		}
		    List<TemplateKeyPointsExam>data=recs.subList(startRow, endrow);
			jqGrid.setRows(data);
			jqGrid.setRowCount(recs.size());
			return jqGrid;
	}
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param templateId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	@ResponseBody
	public TemplateRegister initEditOrViewPage(String templateId) {
		TemplateRegister entity = templateRegisterService.initEditOrViewPage(templateId,this.getUserProfile());
		return entity;
	}
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param templateId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPageDetail")
	@ResponseBody
	public TemplateRegisterDetail initEditOrViewPageDetail(String detailId, String templateId) {
		TemplateRegisterDetail entity = templateRegisterService.initEditOrViewPageDetail(detailId, 
				templateId,this.getUserProfile());
		return entity;
	}
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param templateId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPageTable")
	@ResponseBody
	public TemplateRegisterDetail initEditOrViewPageTable(String detailId, String templateId) {
		TemplateRegisterDetail entity = templateRegisterService.initEditOrViewPageTable(detailId, 
				templateId,this.getUserProfile());
		return entity;
	}
	
	/**
	 * 编辑或查看页面加载对象
	 * 数据显示
	 * @param contractId
	 * @return
	 */
	@RequestMapping("initEditOrViewPageByDetailAndAuto")
//	@AuthCheck(module = ContractExam.MODULE_ID, oper = AuthConstant.AUTH_OPER_NAME_SETVAL,
//	failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody
	String initEditOrViewPageByDetailAndAuto(HttpServletRequest request, String templateId, String paragraphNo) {
		UserProfile user = this.getUserProfile();
		String str = request.getParameter("paramId");
		EIPService.getLogService(TemplateRegister.LOG_TYPE).info(
				user, "获取数据读入","接收前端参数，paramId：" + str,
				null, null);
		//System.out.println("paramId="+str);
		String text = "";
		if(str != null && !str.equals("[]")){
			Map<String, String> paramId = (Map<String, String>)JSON.parse(str);
			text = this.templateRegisterService.getDataByAuto(paramId, templateId, paragraphNo, user);
		}
		return text;
	}
	
	/**
	 * 编辑或查看页面加载对象
	 * 数据显示
	 * @param contractId
	 * @return
	 */
	@RequestMapping("initEditOrViewPageByDetailAndData")
//	@AuthCheck(module = ContractExam.MODULE_ID, oper = AuthConstant.AUTH_OPER_NAME_SETVAL,
//	failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody
	String initEditOrViewPageByDetailAndData(String dataId, String templateId, String paragraphNo) {
		String sales = this.templateRegisterService.loadByDetail(dataId, templateId, paragraphNo);
		return sales;
	}
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param templateRegisterId 主键
	 * @return
	 */
	@RequestMapping("viewPage")
	@ResponseBody
	public TemplateRegister viewPage(String templateRegisterId) {
		TemplateRegister entity = templateRegisterService.viewPage(templateRegisterId,this.getUserProfile());
		return entity;
	}
	
	@RequestMapping("getDataForReviewVer")
	public @ResponseBody OperResult<TemplateRegister> getDataForReviewVer(String dataId1, String dataId2) {
		UserProfile user = this.getUserProfile();
		//权限验证
		//canExecute(user, AuthConstant.AUTH_OPER_NAME_SETVALREPORT, templateRegister.getTemplateRegisterId(), templateRegister);
		return this.templateRegisterService.getDataForReviewVer(user, dataId1, dataId2);
	}
	/**
	 * 保存或更新数据.
	 * 
	 * @param templateRegister 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
//	@AuthCheck(module = TemplateRegisterConstant.MODULE_ID, 
//     oper = AuthConstant.AUTH_OPER_NAME_SETVALREPORT, 
//     failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody OperResult<TemplateRegister> saveOrUpdate(TemplateRegister templateRegister) {
		UserProfile user = this.getUserProfile();
		//权限验证
		//canExecute(user, AuthConstant.AUTH_OPER_NAME_SETVALREPORT, templateRegister.getTemplateRegisterId(), templateRegister);
		Map< String, Object > valueMap = this.getPropValues(TemplateRegister.class);
		return this.templateRegisterService.saveOrUpdate(user, templateRegister, valueMap);
	}
	
	/**
	 * 保存或更新数据.
	 * 
	 * @param templateRegister 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdateDetail")
//	@AuthCheck(module = TemplateRegisterConstant.MODULE_ID, 
//     oper = AuthConstant.AUTH_OPER_NAME_SETVALREPORT, 
//     failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody OperResult<TemplateRegisterDetail> saveOrUpdateDetail(TemplateRegisterDetail templateRegisterDetail) {
		UserProfile user = this.getUserProfile();
		//权限验证
		//canExecute(user, AuthConstant.AUTH_OPER_NAME_SETVALREPORT, templateRegister.getTemplateRegisterId(), templateRegister);
		Map< String, Object > valueMap = this.getPropValues(TemplateRegister.class);
		return this.templateRegisterService.saveOrUpdateDetail(user, templateRegisterDetail, valueMap);
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param templateRegister 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdateTable")
//	@AuthCheck(module = TemplateRegisterConstant.MODULE_ID, 
//     oper = AuthConstant.AUTH_OPER_NAME_SETVALREPORT, 
//     failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody OperResult<TemplateRegisterDetail> saveOrUpdateTable(TemplateRegisterDetail templateRegisterDetail) {
		UserProfile user = this.getUserProfile();
		return this.templateRegisterService.saveOrUpdateTable(user, templateRegisterDetail);
	}
	
	/**
	 * 保存或更新数据.
	 * 
	 * @param Sales 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdateByDetailAndData")
//	@AuthCheck(module = ContractExam.MODULE_ID, oper = AuthConstant.AUTH_OPER_NAME_SETVAL, 
//	failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody
	OperResult<String> saveOrUpdateByDetailAndData(HttpServletRequest request) {
		UserProfile user = this.getUserProfile();
		Map<String, String[]> map = request.getParameterMap();
		this.templateRegisterService.saveOrUpdateByDetailAndData(user, map);
		return OperResult.succeed("saveSuccess");
	}
	
	/**
	 * 保存或更新数据.
	 * 保存评审要点意见
	 * @param Sales 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdateByKeyPointsExam")
//	@AuthCheck(module = ContractExam.MODULE_ID, oper = AuthConstant.AUTH_OPER_NAME_SETVAL, 
//	failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody
	OperResult<String> saveOrUpdateByKeyPointsExam(HttpServletRequest request) {
		UserProfile user = this.getUserProfile();
		Map<String, String[]> map = request.getParameterMap();
		this.templateRegisterService.saveOrUpdateByKeyPointsExam(user, map);
		return OperResult.succeed("saveSuccess");
	}
	
	/**
	 * 删除操作
	 * 
	 * @param templateRegisterIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult<?> batchDel(String ids) {
		UserProfile user = this.getUserProfile();
		this.templateRegisterService.delete(user, ids);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 删除操作
	 * 
	 * @param templateRegisterIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("detailDel")
	public @ResponseBody OperResult<?> detailDel(String ids) {
		UserProfile user = this.getUserProfile();
		this.templateRegisterService.detailDel(user, ids);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 获取字典数据
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getTemplateRegisterStatusCodetable")
	public Map<Integer, String> getRelatedPartiesTypeTable() throws IOException {
		Map<Integer, String> map = TemplateRegister.getStatusCodeTable();
		return map;
	}

	/**
	 * 获取目录级别
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getDirectoryStructureCodetable")
	public Map<Integer, String> getDirectoryStructureCodetable() throws IOException {
		return TemplateRegisterDetail.getDirectoryStructureCodeTable();
	}
	
	/**
	 * 内容类型
	 * @return
	 */
	@RequestMapping(value = "/getContentTypeCodeTables")
	public @ResponseBody
	Map<String, String> getContentTypeCodeTables() {
		return TemplateRegisterDetail.getContentTypeCodeTable();
	}
	
	/**
	 * 内容类型
	 * 用于表格中选择
	 * @return
	 */
	@RequestMapping(value = "/getContentTypeByTableCodeTable")
	public @ResponseBody
	Map<String, String> getContentTypeByTableCodeTable() {
		return TemplateRegisterDetail.getContentTypeByTableCodeTable();
	}
	
	/**
	 * 输入类型
	 * @return
	 */
	@RequestMapping(value = "/getInputTypeCodeTables")
	public @ResponseBody
	Map<String, String> getInputTypeCodeTables() {
		/*List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PPM_INPUT_TYPE");
		Map<String, String> map = new HashMap<String, String>();
		if(items != null && !items.isEmpty()) {
			for(IComCodeTableItem item : items) {
				map.put(item.getExtField1(), item.getDisplayName());
			}
		}*/
		//文本、文本域、文本编辑器
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("TEXT", "文本");
		map.put("TEXTAREA", "文本域");
		//map.put("TEXTEDIT", "文本编辑器");
		return map;
	}
	
	/**
	 * 列表点选
	 * @return
	 */
	@RequestMapping(value = "/getListSelectCodeTables")
	public @ResponseBody
	Map<String, String> getListSelectCodeTables() {
		List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PPM_LIST_SELECT");
		Map<String, String> map = new HashMap<String, String>();
		if(items != null && !items.isEmpty()) {
			for(IComCodeTableItem item : items) {
				//获取一级码表内容
				if(item.getParentItemId() == null){
					map.put(item.getItemId(), item.getDisplayName());
				}
			}
		}
		return map;
	}
	
	/**
	 * 样式点选
	 * @return
	 */
	@RequestMapping(value = "/getStyleTypeCodeTables")
	public @ResponseBody
	Map<String, String> getStyleTypeCodeTables() {
		List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PPM_STYLE");
		Map<String, String> map = new HashMap<String, String>();
		if(items != null && !items.isEmpty()) {
			for(IComCodeTableItem item : items) {
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;
	}
	
	/**
	 * 码表点选
	 * @return
	 */
	@RequestMapping(value = "/getCodeTableSelectCodeTables")
	public @ResponseBody
	Map<String, String> getCodeTableSelectCodeTables() {
		List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PPM_CODETABLE_SELECT");
		Map<String, String> map = new HashMap<String, String>();
		if(items != null && !items.isEmpty()) {
			for(IComCodeTableItem item : items) {
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;
	}
	
	/**
	 * 码表公共方法
	 * 通过码表分类编码获取对应的码表信息
	 * @return
	 */
	@RequestMapping(value = "/getCommonCodeTableSelectCodeTables")
	public @ResponseBody
	Map<String, String> getCommonCodeTableSelectCodeTables(String param) {
		//System.out.println("param="+param);
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isNotBlank(param)){
			List<IComCodeTableItem> items = EIPService.getComCodeTableService().getSubItems(param);
			if(items != null && !items.isEmpty()) {
				for(IComCodeTableItem item : items) {
					//map.put(item.getExtField1(), item.getDisplayName());
					map.put(item.getDisplayName(), item.getDisplayName());
				}
			}
		}
		return map;
	}
	
	/**
	 * 判断模板编码是否重复
	 * 
	 * @param templateId
	 * @param templateNo
	 * @return
	 */
	@RequestMapping("checkTemplateNoIsValid")
	public @ResponseBody Boolean checkTemplateNoIsValid(String templateId, String templateNo) {
		return this.templateRegisterService.checkTemplateNoIsValid(templateId, templateNo);
	}
	
	/**
	 * 判断信息项编码是否重复
	 * 
	 * @param detailId
	 * @param contentNo
	 * @return
	 */
	@RequestMapping("checkContentNoIsValid")
	public @ResponseBody Boolean checkContentNoIsValid(String detailId, String contentNo) {
		return this.templateRegisterService.checkContentNoIsValid(detailId, contentNo);
	}
	/**
	 * 判断分段显示编码是否重复
	 * 
	 * @param detailId
	 * @param paragraphNo
	 * @param templateId
	 * @return
	 */
	@RequestMapping("checkParagraphNoIsValid")
	public @ResponseBody Boolean checkParagraphNoIsValid(String detailId, String paragraphNo, String templateId) {
		return this.templateRegisterService.checkParagraphNoIsValid(detailId, paragraphNo, templateId);
	}
}
