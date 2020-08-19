package com.supporter.prj.linkworks.oa.news_exam.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.codetable.entity.ICodeTable;
import com.supporter.prj.eip_service.exception.ExceptionCode;
import com.supporter.prj.linkworks.oa.news_exam.entity.NewsExamRec;
import com.supporter.prj.linkworks.oa.news_exam.service.NewsExamRecService;
import com.supporter.prj.linkworks.oa.news_exam.service.NewsGenerateHtmlService;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**
 * 主题新闻审批.
 * @author linda
 *
 */
@Controller
@RequestMapping("/oa/news_exam")
public class NewsExamRecController extends AbstractController{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private NewsExamRecService newsExamRecService;
	@Autowired
	private NewsGenerateHtmlService newsGenerateHtmlService;
	
	/**
	 * 根据主键获取对象.
	 * 
	 * @param recId 主键
	 * @return
	 */
	@RequestMapping("/getOrNew")
	@ResponseBody
	public NewsExamRec getOrNew(String recId){
		NewsExamRec rec = null;
		if (CommonUtil.trim(recId).length() > 0){
			rec = newsExamRecService.getNewsExamRec(recId);
		}
		if (rec == null){
			rec = newsExamRecService.newNewsExamRec(this.getUserProfile());
		}

		return rec;
	}
	
	/**
	 * 保存.
	 * @param rec
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	@AuthCheck(module = NewsExamRecService.APP_NAME, oper = NewsExamRecService.Auth.MANAGE, failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public NewsExamRec saveOrUpdate(NewsExamRec rec){
		if (rec == null)return rec;
		//获取DB中对象
		NewsExamRec targetObj = this.getOrNew(rec.getRecId());
		//根据页面传过来的值进行赋值
		this.setPropValues(targetObj);
		return newsExamRecService.saveOrUpdate(targetObj);
	}
	
	/**
	 * 保存会签人.
	 * @param rec
	 * @return
	 */
	@RequestMapping("/saveSigner")
	@ResponseBody
	public NewsExamRec saveSigner(NewsExamRec rec){
		if (rec == null)return rec;
		//获取DB中对象
		NewsExamRec targetObj = this.getOrNew(rec.getRecId());
		//根据页面传过来的值进行赋值
		this.setPropValues(targetObj);
		return newsExamRecService.saveOrUpdate(targetObj);
	}
	
	/**
	 * 删除.
	 * @param recId
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@AuthCheck(module = NewsExamRecService.APP_NAME, oper = NewsExamRecService.Auth.MANAGE, failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public String delete(String recIds){
		newsExamRecService.delete(recIds);
		return "";
	}
	
	/**
	 * 获取公司发布码表.
	 * @return
	 */
	@RequestMapping("/getPublishToChk")
	@ResponseBody
	public List < Map < String, Object > > getPublishToChk(String recId){
		NewsExamRec rec = this.getOrNew(recId);
		String[] datas = CommonUtil.trim(rec.getPublishTo()).split(",");
		
		ICodeTable ctbl = NewsExamRec.PublishTo.getCodeTable();
		String[] ids = ctbl.getDataValues();
		
		List < Map < String, Object > > list = new ArrayList < Map < String, Object > >(); 
		for (int i = 0; i < ids.length; i++){
			String id = ids[i];
			Map < String, Object > map = new HashMap < String, Object >();
			map.put("id", id);
			map.put("describe", ctbl.getDisplay(id));
			map.put("value", id);
			map.put("name", "publishToChk");
			if (datas != null && datas.length > 0){
				for (int j = 0; j < datas.length; j++){
					String publishTo = CommonUtil.trim(datas[j]);
					if (id.equals(publishTo)){
						map.put("checked", true);
						break;
					}
				}
			}
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 获取公司内部发布码表.
	 * @return
	 */
	@RequestMapping("/getInnerPublishToChk")
	@ResponseBody
	public List < Map < String, Object > > getInnerPublishToChk(String recId){
		NewsExamRec rec = this.getOrNew(recId);
		String[] datas = CommonUtil.trim(rec.getInnerPublishTo()).split(",");
		
		ICodeTable ctbl = NewsExamRec.PublishTo.getInnerCodeTable();
		String[] ids = ctbl.getDataValues();
		
		List < Map < String, Object > > list = new ArrayList < Map < String, Object > >(); 
		for (int i = 0; i < ids.length; i++){
			String id = ids[i];
			Map < String, Object > map = new HashMap < String, Object >();
			map.put("id", id);
			map.put("describe", ctbl.getDisplay(id));
			map.put("value", id);
			map.put("name", "innerPublishToChk");
			if (datas != null && datas.length > 0){
				for (int j = 0; j < datas.length; j++){
					String innerTo = CommonUtil.trim(datas[j]);
					if (id.equals(innerTo)){
						map.put("checked", true);
						break;
					}
				}
			}
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 获取报送码表.
	 * @return
	 */
	@RequestMapping("/getSubmitToChk")
	@ResponseBody
	public List < Map < String, Object > > getSubmitToChk(String recId){
		NewsExamRec rec = this.getOrNew(recId);
		String[] datas = CommonUtil.trim(rec.getSubmitTo()).split(",");
		
		ICodeTable ctbl = NewsExamRec.SubmitTo.getCodeTable();
		String[] ids = ctbl.getDataValues();
		
		List < Map < String, Object > > list = new ArrayList < Map < String, Object > >(); 
		for (int i = 0; i < ids.length; i++){
			String id = ids[i];
			Map < String, Object > map = new HashMap < String, Object >();
			map.put("id", id);
			map.put("describe", ctbl.getDisplay(id));
			map.put("value", id);
			map.put("name", "submitToChk");
			if (datas != null && datas.length > 0){
				for (int j = 0; j < datas.length; j++){
					String submitTo = CommonUtil.trim(datas[j]);
					if (id.equals(submitTo)){
						map.put("checked", true);
						break;
					}
				}
			}
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 获取编辑情况码表.
	 * @return
	 */
	@RequestMapping("/getEditTypeChk")
	@ResponseBody
	public List < Map < String, Object > > getEditTypeChk(String recId){
		NewsExamRec rec = this.getOrNew(recId);
		int editType = rec.getEditType();
		
		ICodeTable ctbl = NewsExamRec.EditType.getCodeTable();
		String[] ids = ctbl.getDataValues();
		
		List < Map < String, Object > > list = new ArrayList < Map < String, Object > >(); 
		for (int i = 0; i < ids.length; i++){
			int id = CommonUtil.parseInt(ids[i]);
			Map < String, Object > map = new HashMap < String, Object >();
			map.put("id", "editTypeId" + ids[i]);
			map.put("describe", ctbl.getDisplay(id));
			map.put("value", id);
			map.put("name", "editTypeChk");
			if (editType == id)map.put("checked", true);
			list.add(map);
		}
		
		return list;
	}
	
	
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getGrid")
	@ResponseBody
	public JqGrid getGrid(JqGridReq jqGridReq) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(this.getRequest());
		
		newsExamRecService.getGrid(this.getUserProfile(), jqGrid, getRequestParameters());
		return jqGrid;
	}
	
	/**
	 * 创建新闻静态页面.
	 * @param picCount
	 * @param newsCount
	 * @return
	 */
	@RequestMapping("/toMakeHtml")
	@ResponseBody
	public String toMakeHtml(int picCount, int newsCount){
		newsGenerateHtmlService.toMakeHtml(picCount, newsCount);
		return "";
	}
}
