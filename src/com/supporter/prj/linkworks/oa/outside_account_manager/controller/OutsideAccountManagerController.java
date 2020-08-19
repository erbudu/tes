package com.supporter.prj.linkworks.oa.outside_account_manager.controller;

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
import com.supporter.prj.linkworks.oa.outside_account_manager.entity.OutsideAccountManager;
import com.supporter.prj.linkworks.oa.outside_account_manager.service.OutsideAccountManagerService;
import com.supporter.prj.log.controller.AbstractController;

@Controller
@RequestMapping("oa/outsideAccountManager")
public class OutsideAccountManagerController extends AbstractController {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private OutsideAccountManagerService outsideAccountManagerService;
	
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, OutsideAccountManager outsideAccountManager) throws Exception{
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.outsideAccountManagerService.getGrid(jqGrid, outsideAccountManager);
		return jqGrid;
	}
	
	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param managerId
	 * @param map
	 */
	@ModelAttribute
	public void getNetin(@RequestParam(value = "managerId", required = false) String managerId, Map<String, Object> map){
		if (StringUtils.isNotBlank(managerId)){
			OutsideAccountManager outsideAccountManager = this.outsideAccountManagerService.get(managerId);
			if (outsideAccountManager != null){
				map.put("outsideAccountManager", outsideAccountManager);
			}
		}
	}
	
	/**
	 * 进入新建、编辑或查看页面时加载的信息
	 * @param managerId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody OutsideAccountManager initEditOrViewPage(String managerId){
		UserProfile user = this.getUserProfile();
		OutsideAccountManager outsideAccountManager = outsideAccountManagerService.initEditOrViewPage(managerId, user);
		return outsideAccountManager;
	}
	
	/**
	 * 保存或更新数据
	 * @param outsideAccountManager
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<OutsideAccountManager> saveOrUpdate(OutsideAccountManager outsideAccountManager){
		UserProfile user = this.getUserProfile();
		OutsideAccountManager entity = this.outsideAccountManagerService.saveOrUpdate(user, outsideAccountManager);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 删除
	 * @param managerId
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String managerId){
		this.outsideAccountManagerService.batchDel(managerId);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 保存提交
	 * @param outsideAccountManager
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody OperResult<OutsideAccountManager> commit(OutsideAccountManager outsideAccountManager){
		UserProfile user = this.getUserProfile();
		OutsideAccountManager entity = this.outsideAccountManagerService.commit(user, outsideAccountManager);
		return OperResult.succeed("saveSuccess", "提交成功", entity);
	}
}
