package com.supporter.prj.linkworks.oa.abroadPublicity.controller;

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
import com.supporter.prj.linkworks.oa.abroadPublicity.entity.AbroadPublicity;
import com.supporter.prj.linkworks.oa.abroadPublicity.service.AbroadPublicityService;
import com.supporter.spring_mvc.AbstractController;


/**   
 * @Title: Controller
 * @Description: 出国审批公示.
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("oa/abroadPublicity")
public class AbroadPublicityController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AbroadPublicityService abroadPublicityService;
	
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq,AbroadPublicity abroadPublicity,String attr) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.abroadPublicityService.getGrid(user, jqGrid, attr);
		return jqGrid;
	}
	
	
//	/**
//	 * 获取目录下拉菜单的数据
//	 * @param key  
//	 * @throws IOException
//	 */
//	@RequestMapping(value = "/getTypeCodetable")
//	public @ResponseBody String getRelatedPartiesTypeTable(){
//		String map = abroadPublicityService.getSelectList();
//		return map;
//	}
	
	/**
	 * 保存或更新数据.
	 * 
	 * @param abroad 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("save")
	public @ResponseBody OperResult<AbroadPublicity> save(AbroadPublicity abroadPublicity) {
		Map< String, Object > valueMap = this.getPropValues(AbroadPublicity.class);
		UserProfile user = this.getUserProfile();
		AbroadPublicity entity = this.abroadPublicityService.save(user, abroadPublicity, valueMap);
		return OperResult.succeed("saveSuccess", null, entity);
	}
}
