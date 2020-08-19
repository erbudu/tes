package com.supporter.prj.cneec.tpc.custom.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.custom.entity.CustomLinkman;
import com.supporter.prj.cneec.tpc.custom.service.CustomLinkmanService;
import com.supporter.prj.cneec.tpc.custom.service.CustomService;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.log.controller.AbstractController;
import com.supporter.util.CommonUtil;

@Controller
@RequestMapping("tpc/customLinkman")
public class CustomLinkmanController extends AbstractController {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CustomService customService;
	
	@Autowired
	private CustomLinkmanService customLinkmanService;
	
	/**
	 * 返回列表
	 * 分页表格展示数据
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, String customId, String pagetype) throws Exception{
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.customService.getLinkGrid(user, jqGrid, customId, pagetype);
		return jqGrid;
	}
	
	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param linkmanId
	 * @param map
	 */
	@ModelAttribute
	public void getCustom(@RequestParam(value = "linkmanId", required = false) String linkmanId, Map<String, Object> map) {
		if (StringUtils.isNotBlank(linkmanId)) {
			CustomLinkman customLinkman = this.customLinkmanService.get(linkmanId);
			if (customLinkman != null) {
				map.put("customLinkman", customLinkman);
			}
		}
	}
	
	/**
	 * 进入新建、编辑或查看页面时加载的信息
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody CustomLinkman initEditOrViewPage(String linkmanId, String customId){
		CustomLinkman entity = customLinkmanService.initEditOrviewPage(linkmanId, customId, this.getUserProfile());
		return entity;
	}
	
	/**
	 * 保存或更新数据
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<CustomLinkman> saveOrUpdate(CustomLinkman customLinkman){
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(CustomLinkman.class);
		CustomLinkman entity = this.customLinkmanService.saveOrUpdate(user, customLinkman, valueMap);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String linkmanId){
		UserProfile user = this.getUserProfile();
		this.customLinkmanService.delete(user, linkmanId);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 获取是否主联系人
	 */
	@RequestMapping("selectIsMainLinkmanData")
	public @ResponseBody List<CheckBoxVO> selectIsMainLinkmanData(String key) {
		return this.customLinkmanService.getIsMainLinkmanData(CommonUtil.trim(key));
	}
}
