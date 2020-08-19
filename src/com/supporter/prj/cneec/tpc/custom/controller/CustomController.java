package com.supporter.prj.cneec.tpc.custom.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.custom.entity.Custom;
import com.supporter.prj.cneec.tpc.custom.service.CustomService;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.log.controller.AbstractController;
import com.supporter.util.CommonUtil;

@Controller
@RequestMapping("tpc/custom")
public class CustomController extends AbstractController {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CustomService customService;
	
	/**
	 * 返回列表
	 * 分页表格展示数据
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, Custom custom) throws Exception{
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.customService.getGrid(user, jqGrid, custom);
		return jqGrid;
	}

	//列表中方法结束
	
	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param customId
	 * @param map
	 */
	@ModelAttribute
	public void getCustom(@RequestParam(value = "customId", required = false) String customId, Map<String, Object> map) {
		if (StringUtils.isNotBlank(customId)) {
			Custom custom = this.customService.get(customId);
			if (custom != null) {
				map.put("custom", custom);
			}
		}
	}
	
	/**
	 * 进入新建、编辑或查看页面时加载的信息
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody Custom initEditOrViewPage(String customId){
		UserProfile user = this.getUserProfile();
		Custom entity = customService.initEditOrviewPage(customId, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.customService.getAuthCanExecute(user, entity);
		}
		return entity;
	}
	
	/**
	 * 保存或更新数据
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<Custom> saveOrUpdate(Custom custom){
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.customService.getAuthCanExecute(user, custom);
		Map<String, Object> valueMap = this.getPropValues(Custom.class);
		Custom entity = this.customService.saveOrUpdate(user, custom, valueMap);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 提交数据
	 * @param registerProject
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<Custom> commit(Custom custom) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.customService.getAuthCanExecute(user, custom);
		Map<String, Object> valueMap = this.getPropValues(Custom.class);
		Custom entity = this.customService.commit(user, custom, valueMap);
		return OperResult.succeed("saveSuccess", "保存成功", entity);
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String customId){
		UserProfile user = this.getUserProfile();
		this.customService.delete(user, customId);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 失效操作
	 */
	@RequestMapping("batchLnvalid")
	public @ResponseBody OperResult batchLnvalid(String customIds){
		UserProfile user = this.getUserProfile();
		this.customService.batchLnvalid(user, customIds);
		return OperResult.succeed("lnvalidSuccess");
	}

	/**
	 * 获取客户提交状态
	 */
	@ResponseBody
	@RequestMapping("getStatus")
	public Map<Integer, String> getStatusType(){
		Map<Integer, String> map = Custom.getStatusMap();
		return map;
	}
	
	/**
	 * 获取客户有效状态
	 */
	@ResponseBody
	@RequestMapping("getControlStatus")
	public Map<String,String> getControlStatusType(){
		Map<String,String> map = Custom.getControlStatusCodeMap();
		return map;
	}
	
	/**
	 * 判断名字是否重复
	 */
	@RequestMapping("checkNameIsValid")
	public @ResponseBody Boolean checkNameIsValid(String customId, String customerName) {
		return this.customService.checkNameIsValid(customId, customerName);
	}

	/**
	 * 获取是否国内
	 */
	@RequestMapping("selectIsForeignData")
	public @ResponseBody List<CheckBoxVO> selectIsForeignData(String key) {
		return this.customService.getIsForeignData(CommonUtil.trim(key));
	}
	
	/**
	 * 获取是否上市
	 */
	@RequestMapping("selectIsListedData")
	public @ResponseBody List<CheckBoxVO> selectIsListedData(String key) {
		return this.customService.getIsListedData(CommonUtil.trim(key));
	}
	
	/**
	 * 获取地区
	 */
	@RequestMapping("selectAreaData")
	public @ResponseBody
	Map<String, String> selectAreaData(String value) {
		if (CommonUtil.trim(value).length() > 0) {
			boolean isForeign = String.valueOf(Custom.IS_FOREIGN).equals(value);
			return TpcConstant.getAreaMap(isForeign);
		}
		return null;
	}
	
	/**
	 * 获取国家省份
	 */
	@RequestMapping("selectAreaItemData")
	public @ResponseBody
	Map<String, String> selectAreaItemData(String value) {
		return TpcConstant.getAreaItemMap(value);
	}
	
	/**
	 * 获取币别
	 */
	@RequestMapping("selectCurrencyData")
	public @ResponseBody JSONObject selectCurrencyData(){
		return this.customService.getCurrencyData();
	}
}
