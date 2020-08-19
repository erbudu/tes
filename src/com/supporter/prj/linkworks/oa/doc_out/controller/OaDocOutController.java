package com.supporter.prj.linkworks.oa.doc_out.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.com_codetable.constant.AuthConstants;
import com.supporter.prj.eip.module.constant.ModuleConstant;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.doc_in.entity.DocIn;
import com.supporter.prj.linkworks.oa.doc_in.util.AuthUtil;
import com.supporter.prj.linkworks.oa.doc_out.constants.AuthConstant;
import com.supporter.prj.linkworks.oa.doc_out.entity.OaDocOut;
import com.supporter.prj.linkworks.oa.doc_out.service.OaDocOutService;
import com.supporter.prj.linkworks.oa.doc_out.util.OaDocOutConstant;
import com.supporter.prj.linkworks.oa.report.entity.Report;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReport;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: Controller
 * @Description: 物资信息设置.
 * @author yanbingchao
 * @date 2017-3-27 15:25:34
 * @version V1.0
 * 
 */

@Controller
@RequestMapping("oa/doc_out")
public class OaDocOutController extends AbstractController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private OaDocOutService codeService;

	/**
	 * 根据主键获取.
	 * 
	 * @param docId
	 *            主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody
	OaDocOut get(String docId) {
		OaDocOut code = codeService.get(docId);
		return code;

	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param cideId
	 *            主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	OaDocOut initEditOrViewPage(String docId) {
		UserProfile user = this.getUserProfile();
		OaDocOut entity = codeService.initEditOrViewPage(docId, user);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getTreeGrid(HttpServletRequest request, JqGridReq jqGridReq,
			OaDocOut code, String classifyId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.codeService.getGrid(user, jqGrid, code);
		return jqGrid;
	}
	@RequestMapping("viewPage")
	@ResponseBody
	public OaDocOut viewPage(String docId) {
		OaDocOut entity = codeService.viewPage(docId,this.getUserProfile());
		return entity;
	}
	/**
	 * 保存或更新数据.
	 * 
	 * @param code
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<OaDocOut> saveOrUpdate(OaDocOut code) {
		UserProfile user = this.getUserProfile();
		AuthUtil.canExecute(user, AuthConstant.AUTH_OPER_NAME_SETVALDOCOUT, code.getDocId(), code);
		Map<String, Object> valueMap = this.getPropValues(OaDocOut.class);
		OaDocOut entity = this.codeService.saveOrUpdate(user, code, valueMap);
		return OperResult.succeed(OaDocOutConstant.I18nKey.SAVE_SUCCESS,
				"保存成功", entity);
	}
	
	@RequestMapping("saveQianShou")
	public @ResponseBody OaDocOut saveQianShou(OaDocOut code) {
		UserProfile user = this.getUserProfile();
		OaDocOut entity = get(code.getDocId());
		String receiverIds = entity.getReaderIds();
		if(StringUtils.isBlank(receiverIds)){
			entity.setReceiverIds(user.getPersonId());
			entity.setReceiverNames(user.getName());
		}else{
			entity.setReceiverIds(receiverIds+","+user.getPersonId());
			entity.setReceiverNames(entity.getReceiverNames()+","+user.getName());		
		}
		this.codeService.update(entity);
		return entity;
	}
	@RequestMapping("extendsHander")
	public @ResponseBody
	OperResult<OaDocOut> extendsHander(OaDocOut code) {

		OaDocOut entity = get(code.getDocId());
	  
	  	// 增加部门领导会签
		String extendDeptLeaderIds = code.getExtendDeptLeaderIds();
		String extendDeptLeaderNames = code.getExtendDeptLeaderNames();
		//if (StringUtils.isNotBlank(extendDeptLeaderIds)) {
			entity.setExtendDeptLeaderIds(extendDeptLeaderIds);
			entity.setExtendDeptLeaderNames(extendDeptLeaderNames);
		//}
		//增加公司领导审阅信息
		String extendTopLeaderIds = code.getExtendTopLeaderIds();		
		String extendTopLeaderNames = code.getExtendTopLeaderNames();
		//if (StringUtils.isNotBlank(extendTopLeaderIds)) {
			entity.setExtendTopLeaderIds(extendTopLeaderIds);
			entity.setExtendTopLeaderNames(extendTopLeaderNames);
		//}
		
		/*// 部门领导会签
		String deptLeaderIds = code.getDeptLeaderIds();
		String deptLeaderNames = code.getDeptLeaderNames();
		// 公司领导审阅信息
		String topLeaderIds = code.getTopLeaderIds();
		String topLeaderNames = code.getTopLeaderNames();
		// 添加部门领导会签
		String extendDeptLeaderIds = code.getExtendDeptLeaderIds();
		String extendDeptLeaderNames = code.getExtendDeptLeaderNames();
		if (StringUtils.isNotBlank(extendDeptLeaderIds)) {
			
			if (StringUtils.isNotBlank(deptLeaderIds)) {
				String[] addIds = extendDeptLeaderIds.split(",");
				String[] addNames = extendDeptLeaderNames.split(",");
				String[] ids = deptLeaderIds.split(",");
				String[] names =deptLeaderNames.split(",");
				List<String> c_addIds = Arrays.asList(addIds);
				List<String> c_addNames =Arrays.asList(addNames);
				List<String> c_ids = Arrays.asList(ids);
				List<String> c_names = Arrays.asList(names);
				List<String> c_addIds = new ArrayList<String>();
				List<String> c_addNames =new ArrayList<String>();
				List<String> c_ids = new ArrayList<String>();
				List<String> c_names = new ArrayList<String>();		
				for (int i = 0; i < addIds.length; i++) {
					c_addIds.add(addIds[i]);
					c_addNames.add(addNames[i]);
				}
				for (int i = 0; i < ids.length; i++) {
					c_ids.add(ids[i]);
					c_names.add(names[i]);
				}
				c_addIds.removeAll(c_ids);
				c_addNames.removeAll(c_names);
				String newAddIds = StringUtils.join(c_addIds,",");
				String newAddNames =StringUtils.join(c_addNames,",");
				entity.setExtendDeptLeaderIds(newAddIds);
				entity.setExtendDeptLeaderNames(newAddNames);
				entity.setDeptLeaderIds(deptLeaderIds+","+newAddIds);
				entity.setDeptLeaderNames(deptLeaderNames+","+newAddNames);
			}else{
				entity.setExtendDeptLeaderIds(extendDeptLeaderIds);
				entity.setExtendDeptLeaderNames(extendDeptLeaderNames);
				entity.setDeptLeaderIds(extendDeptLeaderIds);
				entity.setDeptLeaderNames(extendDeptLeaderNames);
			}

		}
		// 添加公司领导审阅信息
		String extendTopLeaderIds = code.getExtendTopLeaderIds();
		String extendTopLeaderNames = code.getExtendTopLeaderNames();
		if (StringUtils.isNotBlank(extendTopLeaderIds)) {
			entity.setExtendTopLeaderIds(extendTopLeaderIds);
			entity.setExtendTopLeaderNames(extendTopLeaderNames);
			if (StringUtils.isNotBlank(topLeaderIds)) {
				entity.setTopLeaderIds(topLeaderIds+","+extendTopLeaderIds);
				entity.setTopLeaderNames(topLeaderNames+","+extendTopLeaderNames);
			}else{
				entity.setTopLeaderIds(extendTopLeaderIds);
				entity.setTopLeaderNames(extendTopLeaderNames);
			}
		}*/
		this.codeService.update(entity);

		return null;
	}
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<OaDocOut> commit(OaDocOut code) {
		OaDocOut entity = get(code.getDocId());
/*		// 部门领导会签
		String deptLeaderIds = code.getDeptLeaderIds();
		String deptLeaderNames = code.getDeptLeaderNames();
		if (StringUtils.isNotBlank(deptLeaderIds)) {
			entity.setDeptLeaderIds(deptLeaderIds);
			entity.setDeptLeaderNames(deptLeaderNames);
		}
		// 公司领导审阅信息
		String topLeaderIds = code.getTopLeaderIds();
		String topLeaderNames = code.getTopLeaderNames();
		if (StringUtils.isNotBlank(topLeaderIds)) {
			entity.setTopLeaderIds(topLeaderIds);
			entity.setTopLeaderNames(topLeaderNames);
		}*/
		
		
		// 部门领导会签
		String extendDeptLeaderIds = code.getExtendDeptLeaderIds();
		String extendDeptLeaderNames = code.getExtendDeptLeaderNames();
		//if (StringUtils.isNotBlank(extendDeptLeaderIds)) {
			entity.setExtendDeptLeaderIds(extendDeptLeaderIds);
			entity.setExtendDeptLeaderNames(extendDeptLeaderNames);
		//}
		// 公司领导审阅信息
		String extendTopLeaderIds = code.getExtendTopLeaderIds();
		String extendTopLeaderNames = code.getExtendTopLeaderNames();
		//if (StringUtils.isNotBlank(extendTopLeaderIds)) {
			entity.setExtendTopLeaderIds(extendTopLeaderIds);
			entity.setExtendTopLeaderNames(extendTopLeaderNames);
		//}
		
		
		
		// 签发领导
		String presidentId = code.getPresidentId();
		String presidentName = code.getPresidentName();
//		if (StringUtils.isNotBlank(presidentId)) {
			entity.setPresidentId(presidentId);
			entity.setPresidentName(presidentName);
//		}
		// 是否需要签收
		String needReceivers = code.getNeedReceivers();
		if (StringUtils.isNotBlank(needReceivers)) {
			entity.setNeedReceivers(needReceivers);
		}else{
			entity.setNeedReceivers("0");
		}
		// 待签收人员
		String receiverIds = code.getReaderIds();
		String receiverNames = code.getReaderNames();
		if (StringUtils.isNotBlank(receiverIds)) {
			entity.setReceiverIds(receiverIds);
			entity.setReceiverNames(receiverNames);
		}
		//归档更改编号
		String docNo = code.getDocNo();
		if (StringUtils.isNotBlank(docNo)) {
			entity.setDocNo(docNo);
		}
		Integer docStatus = code.getDocStatus();
		if(docStatus!=null){
			entity.setDocStatus(docStatus);
		}
		this.codeService.update(entity);
		return OperResult.succeed(OaDocOutConstant.I18nKey.SAVE_SUCCESS,
				"保存成功", entity);
	}
	
	//保存发布人
	// 保存发布人
	@RequestMapping("setPublisher")
	public @ResponseBody
	OaDocOut setPublisher(OaDocOut code) {
		UserProfile user = this.getUserProfile();
		OaDocOut entity = get(code.getDocId());
		entity.setPublisherId(user.getPersonId());
		entity.setPublisherName(user.getName());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		entity.setPublishDate(sdf.format(new Date()));
		this.codeService.update(entity);
		return entity;
	}
	@RequestMapping("createdBase")
	@ResponseBody
	public String createdBase(String docId) {
		codeService.createdBase(docId);
		return docId;
	}
	/**
	 * 删除操作
	 * 
	 * @param docIds
	 *            主键集合，多个以逗号分隔
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult batchDel(String docIds) {
		UserProfile user = this.getUserProfile();
		this.codeService.delete(user, docIds);
		return OperResult.succeed(OaDocOutConstant.I18nKey.DELETE_SUCCESS,
				null, null);
	}
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("extractFiles")
	public @ResponseBody String extractFiles(String docId){
		return codeService.extractFiles(docId,this.getUserProfile());
	}
	
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("batchExtractFiles")
	public @ResponseBody String batchExtractFiles(){
		return codeService.batchExtractFiles(this.getUserProfile());
	}
	/**
	 * 判断名字是否重复
	 * 
	 * @param docId
	 * @param materialName
	 * @return
	 */
	@RequestMapping("checkNameIsValid")
	public @ResponseBody
	Boolean checkNameIsValid(String docId, String materialName) {
		return this.codeService.checkNameIsValid(docId, materialName);
	}

	@RequestMapping("getOutDocStatus")
	public @ResponseBody
	Map<String, String> getOutDocStatus() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		/*
		 * List <IComCodeTableItem> list =
		 * EIPService.getComCodeTableService().getCodeTableItems("UNIT");
		 * for(IComCodeTableItem item : list){ map.put(item.getItemId(),
		 * item.getDisplayName()); }
		 */
		map.put("0", "草稿");
		map.put("1", "审核中");
		map.put("2", "已发布");
		map.put("3", "已归档");
		map.put("4", "流程终止");
		return map;
	}

	@RequestMapping("getConfidentialRankCodeTable")
	public @ResponseBody
	Map<String, String> getConfidentialRankCodeTable() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		/*
		 * List <IComCodeTableItem> list =
		 * EIPService.getComCodeTableService().getCodeTableItems("UNIT");
		 * for(IComCodeTableItem item : list){ map.put(item.getItemId(),
		 * item.getDisplayName()); }
		 */
		map.put("0", "");
		map.put("1", "秘密");
		map.put("2", "机密");
		map.put("3", "绝密");
		return map;
	}

	@RequestMapping("getTemCodeTable")
	public @ResponseBody
	Map<String, String> getTemCodeTable() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		/*
		 * List <IComCodeTableItem> list =
		 * EIPService.getComCodeTableService().getCodeTableItems("UNIT");
		 * for(IComCodeTableItem item : list){ map.put(item.getItemId(),
		 * item.getDisplayName()); }
		 */
		map.put("1", "行政发文");
		map.put("2", "党委发文");
		map.put("3", "工会发文");
		map.put("4", "纪检发文");
		map.put("5", "院发文");
		map.put("6", "便函");
		map.put("7", "会议纪要");
		map.put("8", "哈成套所发文");
		map.put("9", "监理公司发文");
		map.put("10", "兴桥公司发文");
		map.put("11", "董事会红头文件");
		map.put("12", "团委文件模板");
		map.put("13", "党委会议纪要");
		return map;
	}

	@RequestMapping("getDocTypeCodeTable")
	public @ResponseBody
	Map<String, String> getDocTypeCodeTable() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		/*
		 * List <IComCodeTableItem> list =
		 * EIPService.getComCodeTableService().getCodeTableItems("UNIT");
		 * for(IComCodeTableItem item : list){ map.put(item.getItemId(),
		 * item.getDisplayName()); }
		 */
		map.put("文件", "文件");
		map.put("命令", "命令");
		map.put("决定", "决定");
		map.put("公告", "公告");
		map.put("通告", "通告");
		map.put("通知", "通知");
		map.put("通报", "通报");
		map.put("议案", "议案");
		map.put("报告", "报告");
		map.put("请示", "请示");
		map.put("批复", "批复");
		map.put("意见", "意见");
		map.put("函", "函");
		map.put("会议纪要", "会议纪要");
		map.put("其它", "其它");
		return map;
	}
	
	
	
	/**
	 * 保存审批中会签人
	 */
	@RequestMapping("saveSigner")
	public @ResponseBody OperResult<OaDocOut> saveSigner(String docId,String signedType) {
		UserProfile user = this.getUserProfile();
		this.codeService.saveSigner(docId,signedType,user);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS,null,null);
	}
	
	/**
	 * 重置流程状态
	 * @param docId
	 * @param status
	 * @return
	 */
	@RequestMapping("procReset")
	public @ResponseBody OperResult<OaDocOut> procReset(String docId, Integer status) {
		OaDocOut docOut = this.codeService.get(docId);
		docOut.setDocStatus(status);
		this.codeService.update(docOut);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS,"保存成功",null);
	}

}
