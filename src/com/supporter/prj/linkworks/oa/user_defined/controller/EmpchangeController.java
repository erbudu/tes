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
import com.supporter.prj.linkworks.oa.user_defined.entity.Empchange;
import com.supporter.prj.linkworks.oa.user_defined.entity.UNetin;
import com.supporter.prj.linkworks.oa.user_defined.service.EmpchangeService;
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
@RequestMapping("oa/empChange")
public class EmpchangeController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private EmpchangeService service;
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
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, Empchange entity) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.service.getGrid(user, jqGrid, entity);
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
	 * @param EmpchangeId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody Empchange initEditOrViewPage(String empChangeId) {
		Empchange entity = service.initEditOrViewPage(empChangeId,this.getUserProfile());
		return entity;
	}
	@RequestMapping("viewPage")
	@ResponseBody
	public Empchange viewPage(String empChangeId) {
		Empchange entity = service.viewPage(empChangeId, this.getUserProfile());
		return entity;
	}
	/**
	 * 保存或更新数据.
	 * 
	 * @param Empchange 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<Empchange> saveOrUpdate(Empchange entity) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(Empchange.class);
		Empchange re = this.service.saveOrUpdate(user, entity, valueMap);
		return OperResult.succeed("saveSuccess", null, re);
	}
	@RequestMapping("commit")
	public @ResponseBody OperResult<Empchange> commit(String empChangeId) {
		UserProfile user = this.getUserProfile();
		Empchange re = this.service.commit(user, empChangeId);
		return OperResult.succeed("saveSuccess", null, re);
	}
	/**
	 * 删除操作
	 * 
	 * @param EmpchangeIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String empChangeIds) {
		UserProfile user = this.getUserProfile();
		this.service.delete(user, empChangeIds);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 获取字典数据
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getEmpchangeStatusCodetable")
	public Map<Integer, String> getRelatedPartiesTypeTable() throws IOException {
		Map<Integer, String> map = Empchange.getStatusCodeTable();
		return map;
	}
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("extractFiles")
	public @ResponseBody String extractFiles(String id){
		return service.extractFiles(id,this.getUserProfile());
	}
	
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("batchExtractFiles")
	public @ResponseBody String batchExtractFiles(){
		return service.batchExtractFiles(this.getUserProfile());
	}		
}
