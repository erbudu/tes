package com.supporter.prj.linkworks.oa.user_defined.controller;

import java.io.IOException;
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
import com.supporter.prj.linkworks.oa.doc_in.entity.DocIn;
import com.supporter.prj.linkworks.oa.user_defined.entity.UNetin;
import com.supporter.prj.linkworks.oa.user_defined.service.UNetinService;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Controller
 * @Description: 报告.
 * @author liyinfeng
 * @date 2016-12-06 15:25:34
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("oa/netin")
public class UNetinController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private UNetinService uNetinService;
	//以下为列表中方法
    private String is_PrevPageURL = ""; 

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, UNetin entity) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.uNetinService.getGrid(user, jqGrid, entity);
		return jqGrid;
	}
    
    public String getPrevPageURL(){
        if (is_PrevPageURL.length() > 0) return is_PrevPageURL;
        
        String ls_PrevPageURL = CommonUtil.trim(this.getRequestPara("prevPageURL"));
        if (ls_PrevPageURL.length() == 0)
           ls_PrevPageURL = CommonUtil.trim(this.getRequestPara("CURRENT_PAGE_URL"));
        
        is_PrevPageURL = ls_PrevPageURL;
        return is_PrevPageURL;
    }
	
	//以上为列表中方法
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param UNetinId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody UNetin initEditOrViewPage(String netinId) {
		UNetin entity = uNetinService.initEditOrViewPage(netinId,this.getUserProfile());
		return entity;
	}
	@RequestMapping("viewPage")
	@ResponseBody
	public UNetin viewPage(String netinId) {
		UNetin entity = uNetinService.viewPage(netinId, this.getUserProfile());
		return entity;
	}
	/**
	 * 保存或更新数据.
	 * 
	 * @param UNetin 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<UNetin> saveOrUpdate(UNetin entity) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(UNetin.class);
		UNetin re = this.uNetinService.saveOrUpdate(user, entity, valueMap);
		return OperResult.succeed("saveSuccess", null, re);
	}
	@RequestMapping("commit")
	public @ResponseBody OperResult<UNetin> commit(String netinId) {
		UserProfile user = this.getUserProfile();
		UNetin re = this.uNetinService.commit(user, netinId);
		return OperResult.succeed("saveSuccess", null, re);
	}
	/**
	 * 删除操作
	 * 
	 * @param UNetinIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String netinIds) {
		UserProfile user = this.getUserProfile();
		this.uNetinService.delete(user, netinIds);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 获取字典数据
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getUNetinStatusCodetable")
	public Map<Integer, String> getRelatedPartiesTypeTable() throws IOException {
		Map<Integer, String> map = UNetin.getStatusCodeTable();
		return map;
	}

	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("extractFiles")
	public @ResponseBody String extractFiles(String id){
		return uNetinService.extractFiles(id,this.getUserProfile());
	}
	
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("batchExtractFiles")
	public @ResponseBody String batchExtractFiles(){
		return uNetinService.batchExtractFiles(this.getUserProfile());
	}	
}
