package com.supporter.prj.linkworks.oa.user_defined.controller;

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
import com.supporter.prj.linkworks.oa.user_defined.entity.NetMaintain;
import com.supporter.prj.linkworks.oa.user_defined.service.NetMaintainService;
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
@RequestMapping("oa/netMaintain")
public class NetMaintainController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private NetMaintainService netMaintainService;
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
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, NetMaintain entity) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.netMaintainService.getGrid(user, jqGrid, entity);
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
	 * @param NetMaintainId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody NetMaintain initEditOrViewPage(String netMaintainId) {
		NetMaintain entity = netMaintainService.initEditOrViewPage(netMaintainId,this.getUserProfile());
		return entity;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param NetMaintain 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<NetMaintain> saveOrUpdate(NetMaintain entity) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(NetMaintain.class);
		NetMaintain re = this.netMaintainService.saveOrUpdate(user, entity, valueMap);
		return OperResult.succeed("saveSuccess", null, re);
	}

	/**
	 * 删除操作
	 * 
	 * @param NetMaintainIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String netMaintainIds) {
		UserProfile user = this.getUserProfile();
		this.netMaintainService.delete(user, netMaintainIds);
		return OperResult.succeed("deleteSuccess");
	}
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("extractFiles")
	public @ResponseBody String extractFiles(String id){
		return netMaintainService.extractFiles(id,this.getUserProfile());
	}
	
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("batchExtractFiles")
	public @ResponseBody String batchExtractFiles(){
		return netMaintainService.batchExtractFiles(this.getUserProfile());
	}		
	
}
