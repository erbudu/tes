package com.supporter.prj.linkworks.oa.netin.controller;

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
import com.supporter.prj.linkworks.oa.netin.entity.OaNetinPerson;
import com.supporter.prj.linkworks.oa.netin.service.OaNetinPersonService;
import com.supporter.prj.log.controller.AbstractController;

@Controller
@RequestMapping("oa/netinPerson")
public class OaNetinPersonControll extends AbstractController {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private OaNetinPersonService netinPersonService;
	
	/**
	 * 根据入网申请ID获取人员列表
	 * @param request
	 * @param jqGridReq
	 * @param netinId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, String netinId) throws  Exception{
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.netinPersonService.getGrid(jqGrid, netinId);
		return jqGrid;
	}
	
	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param netinId
	 * @param map
	 */
	@ModelAttribute
	public void getNetinPerson(@RequestParam(value = "id", required = false) String id, Map<String, Object> map){
		if (StringUtils.isNotBlank(id)){
			OaNetinPerson netinPerson = this.netinPersonService.get(id);
			if (netinPerson != null){
				map.put("netinPerson", netinPerson);
			}
		}
	}
	
	/**
	 * 进入新建、编辑或查看页面时加载的信息
	 * @param netinId
	 * @param id
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody OaNetinPerson initEditOrViewPage(String netinId, String id){
		UserProfile user = this.getUserProfile();
		OaNetinPerson netinPerson = this.netinPersonService.initEditOrViewPage(netinId, id, user);
		return netinPerson;
	}
	
	/**
	 * 保存或更新数据
	 * @param netinPerson
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<OaNetinPerson> saveOrUpdate(OaNetinPerson netinPerson){
		UserProfile user = this.getUserProfile();
		OaNetinPerson entity = this.netinPersonService.saveOrUpdate(netinPerson);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String id){
		this.netinPersonService.deleteNetinPerson(id);
		return OperResult.succeed("deleteSuccess");
	}
	
}
