package com.supporter.prj.linkworks.oa.netin.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.netin.entity.OaNetin;
import com.supporter.prj.linkworks.oa.netin.service.OaNetinService;
import com.supporter.prj.log.controller.AbstractController;

@Controller
@RequestMapping("oa/netinApply")
public class OaNetinControll extends AbstractController {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private OaNetinService netinService;
	
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, OaNetin netin) throws Exception{
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.netinService.getGrid(jqGrid, netin);
		return jqGrid;
	}
	
	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param netinId
	 * @param map
	 */
	@ModelAttribute
	public void getNetin(@RequestParam(value = "netinId", required = false) String netinId, Map<String, Object> map){
		if (StringUtils.isNotBlank(netinId)){
			OaNetin netin = this.netinService.get(netinId);
			if (netin != null){
				map.put("netin", netin);
			}
		}
	}
	
	/**
	 * 进入新建、编辑或查看页面时加载的信息
	 * @param netinId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody OaNetin initEditOrViewPage(String netinId){
		UserProfile user = this.getUserProfile();
		OaNetin netin = netinService.initEditOrViewPage(netinId, user);
		return netin;
	}
	
	/**
	 * 保存或更新数据
	 * @param netin
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<OaNetin> saveOrUpdate(OaNetin netin){
		UserProfile user = this.getUserProfile();
		OaNetin entity = this.netinService.saveOrUpdate(user, netin);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 删除
	 * @param netinId
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String netinId){
		this.netinService.batchDel(netinId);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 保存提交
	 * @param netin
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody OperResult<OaNetin> commit(OaNetin netin){
		UserProfile user = this.getUserProfile();
		OaNetin entity = this.netinService.commit(user, netin);
		return OperResult.succeed("saveSuccess", "提交成功", entity);
	}
	
	/**
	 * 获取入网申请状态
	 * @return
	 */
	@RequestMapping("getNetinStatus")
	public @ResponseBody Map<Integer, String> getNetinStatus() {
		return OaNetin.getStatusMap();
	}
	
	/**
	 * 重置流程状态
	 * @param netinId
	 * @param status
	 * @return
	 */
	@RequestMapping("procReset")
	public @ResponseBody OperResult<OaNetin> procReset(String netinId, Integer status) {
		OaNetin entity = this.netinService.procReset(netinId, status);
		return OperResult.succeed("saveSuccess", null, entity);
	}
}
