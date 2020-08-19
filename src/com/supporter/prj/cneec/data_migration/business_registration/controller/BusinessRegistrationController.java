package com.supporter.prj.cneec.data_migration.business_registration.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.data_migration.business_registration.entity.BusinessRegistration;
import com.supporter.prj.cneec.data_migration.business_registration.service.BusinessRegistrationService;
import com.supporter.prj.cneec.data_migration.business_registration.util.BusinessRegistrationConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: Controller
 * @Description: 物资信息设置.
 * @author yanbingchao
 * @date 2017-3-27 15:25:34
 * @version V1.0
 * 
 */

@Controller("oa_business_registion")
@RequestMapping("oa/business_registration")
public class BusinessRegistrationController extends AbstractController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private BusinessRegistrationService codeService;

	/**
	 * 根据主键获取.
	 * 
	 * @param businessRegistrationId
	 *            主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody
	BusinessRegistration get(String businessRegistrationId) {
		BusinessRegistration code = codeService.get(businessRegistrationId);
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
	BusinessRegistration initEditOrViewPage(String businessRegistrationId,String docClassify) {
		UserProfile user = this.getUserProfile();
		BusinessRegistration entity = codeService.initEditOrViewPage(businessRegistrationId,docClassify, user);
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
			BusinessRegistration code, String docClassify) throws Exception {
		UserProfile user = this.getUserProfile();
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
	OperResult<BusinessRegistration> saveOrUpdate(BusinessRegistration code) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(BusinessRegistration.class);
		BusinessRegistration entity = this.codeService.saveOrUpdate(user, code, valueMap);
		return OperResult.succeed(BusinessRegistrationConstant.I18nKey.SAVE_SUCCESS, "保存成功",
				entity);
	}
	/**
	 * 删除操作
	 * 
	 * @param businessRegistrationIds
	 *            主键集合，多个以逗号分隔
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult batchDel(String businessRegistrationIds) {
		UserProfile user = this.getUserProfile();
		this.codeService.delete(user, businessRegistrationIds);
		return OperResult.succeed(BusinessRegistrationConstant.I18nKey.DELETE_SUCCESS, null,
				null);
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
	Map<String, String> getDeptCodeTable() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<IComCodeTableItem> list = EIPService.getComCodeTableService()
				.getCodeTableItems("BusinessRegistrationDept");
		for (IComCodeTableItem item : list) {
			map.put(item.getDisplayName(), item.getDisplayName());
		}
		return map;
	}

}
