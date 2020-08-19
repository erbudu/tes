package com.supporter.prj.linkworks.oa.doc_in.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.doc_in.constants.AuthConstant;
import com.supporter.prj.linkworks.oa.doc_in.entity.DocIn;
import com.supporter.prj.linkworks.oa.doc_in.service.DocInService;
import com.supporter.prj.linkworks.oa.doc_in.util.AuthUtil;
import com.supporter.prj.linkworks.oa.doc_in.util.DocInConstant;
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
@RequestMapping("oa/doc_in")
public class DocInController extends AbstractController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private DocInService codeService;

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
	DocIn get(String docId) {
		DocIn code = codeService.get(docId);
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
	DocIn initEditOrViewPage(String docId, String docClassify) {
		UserProfile user = this.getUserProfile();
		DocIn entity = codeService.initEditOrViewPage(docId, docClassify, user);
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
			DocIn code, String docClassify) throws Exception {
		UserProfile user = this.getUserProfile();
		if (docClassify != null) {
			code.setDocClassify(Long.valueOf(docClassify));
		}
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.codeService.getGrid(user, jqGrid, code);
		return jqGrid;
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
	OperResult<DocIn> saveOrUpdate(DocIn code) {
		UserProfile user = this.getUserProfile();
		//权限验证
		AuthUtil.canExecute(user, AuthConstant.AUTH_OPER_NAME_SETVALDOCIN, code.getDocId(), code);
		Map<String, Object> valueMap = this.getPropValues(DocIn.class);
		DocIn entity = this.codeService.saveOrUpdate(user, code, valueMap);
		return OperResult.succeed(DocInConstant.I18nKey.SAVE_SUCCESS, "保存成功",
				entity);
	}

	@RequestMapping("commit")
	public @ResponseBody
	OperResult<DocIn> commit(DocIn code) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(DocIn.class);
		DocIn entity = get(code.getDocId());
		// 审批人
		String handlerIds = code.getHandlerIds();
		if (StringUtils.isNotBlank(handlerIds)) {
			entity.setHandlerIds(handlerIds);
			entity.setHandlerNames(code.getHandlerNames());
		}
		/*
		 * String extendReader = code.getExtendReaderIds(); if
		 * (StringUtils.isNotBlank(extendReader)) {
		 * entity.setExtendReaderIds(extendReader);
		 * entity.setExtendReaderNames(code.getExtendReaderNames()); String
		 * readers = entity.getReaderIds(); if (readers == null) {
		 * entity.setReaderIds(extendReader);
		 * entity.setReaderNames(code.getExtendReaderNames()); } else { if
		 * (!readers.equals(extendReader)) { entity.setReaderIds(readers + "," +
		 * extendReader); entity.setReaderNames(entity.getReaderNames() + "," +
		 * code.getExtendReaderNames()); } } }
		 */
		this.codeService.update(entity);
		return OperResult.succeed(DocInConstant.I18nKey.SAVE_SUCCESS, "保存成功",
				entity);
	}

	// 添加传阅人
	@RequestMapping("addReader")
	public @ResponseBody
	DocIn addReader(DocIn code) {
		UserProfile user = this.getUserProfile();
		DocIn entity = get(code.getDocId());
		String readers = entity.getReaderIds();
		if (readers == null) {
			entity.setReaderIds(user.getPersonId());
			entity.setReaderNames(user.getName());
		} else {
			entity.setReaderIds(readers + "," + user.getPersonId());
			entity.setReaderNames(entity.getReaderNames() + ","
					+ user.getName());

		}
		this.codeService.update(entity);
		return entity;
	}
	// 添加传阅人
	@RequestMapping("addShenPiRen")
	public @ResponseBody
	DocIn addShenPiRen(DocIn code) {
		UserProfile user = this.getUserProfile();
		DocIn entity = get(code.getDocId());
		String shenPiRen = entity.getHandlerIds();
		if (shenPiRen == null) {
			entity.setHandlerIds(user.getPersonId());
			entity.setHandlerNames(user.getName());
		} else {
			entity.setHandlerIds(shenPiRen + "," + user.getPersonId());
			entity.setHandlerNames(entity.getHandlerNames() + ","
					+ user.getName());

		}
		this.codeService.update(entity);
		return entity;
	}
	// 保存发布人
	@RequestMapping("setPublisher")
	public @ResponseBody
	DocIn setPublisher(DocIn code) {
		UserProfile user = this.getUserProfile();
		DocIn entity = get(code.getDocId());
		entity.setPublisherId(user.getPersonId());
		entity.setPublisherName(user.getName());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		entity.setPublishDate(sdf.format(new Date()));
		this.codeService.update(entity);
		return entity;
	}
	
	@RequestMapping("viewPage")
	@ResponseBody
	public DocIn viewPage(String docId) {
		DocIn entity = codeService.viewPage(docId, this.getUserProfile());
		return entity;
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
		return OperResult.succeed(DocInConstant.I18nKey.DELETE_SUCCESS, null,
				null);
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

	@RequestMapping("getDocStatusCodeTable")
	public @ResponseBody
	Map<String, String> getDocStatusCodeTable() {
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
		map.put("0", "普通");
		map.put("1", "急件");
		return map;
	}
	@RequestMapping("getDeptCodeTable")
	public @ResponseBody
	JqGrid getDeptCodeTable(HttpServletRequest request, JqGridReq jqGridReq) {
		
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		codeService.getDeptCodeTable(jqGrid);
/*		Map<String, String> map = new LinkedHashMap<String, String>();
		if (units != null) {
			for (DocInSrcUnit docInSrcUnit : units) {
				map.put(docInSrcUnit.getUnitName(), docInSrcUnit.getUnitName());
			}
		}*/
		/*
		 * List<IComCodeTableItem> list = EIPService.getComCodeTableService()
		 * .getCodeTableItems("docInDept"); for (IComCodeTableItem item : list) {
		 * map.put(item.getDisplayName(), item.getDisplayName()); }
		 */
		return jqGrid;
	}
}
