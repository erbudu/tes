package com.supporter.prj.cneec.tpc.register_project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.register_project.util.RegisterProjectConstant;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**
 * @Title: RegisterProjectController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-8-30
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/registerProject")
public class RegisterProjectController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private RegisterProjectService registerProjectService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param registerProject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, RegisterProject registerProject) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.registerProjectService.getGrid(user, jqGrid, registerProject);
		return jqGrid;
	}
	
	/**
	 * 选择项目控件中非草稿状态的分页查询
	 * @param request
	 * @param jqGridReq
	 * @param registerProject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getSelectGrid")
	public @ResponseBody
	JqGrid getSelectGrid(HttpServletRequest request, JqGridReq jqGridReq, String prjName, RegisterProject registerProject) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		registerProject.setKeyword(prjName);
		this.registerProjectService.getSelectGrid(user, jqGrid, registerProject);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param projectId
	 * @param map
	 */
	@ModelAttribute
	public void getRegisterProject(String projectId, Map<String, Object> map) {
		if (!StringUtils.isBlank(projectId)) {
			RegisterProject registerProject = this.registerProjectService.get(projectId);
			if (registerProject != null) {
				map.put("registerProject", registerProject);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param projectId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	RegisterProject get(String projectId) {
		RegisterProject registerProject = this.registerProjectService.get(projectId);
		return registerProject;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param projectId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	RegisterProject initEditOrViewPage(String projectId) {
		UserProfile user = this.getUserProfile();
		RegisterProject registerProject = this.registerProjectService.initEditOrViewPage(projectId, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.registerProjectService.getAuthCanExecute(user, registerProject);
		}
		return registerProject;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param registerProject
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<RegisterProject> saveOrUpdate(RegisterProject registerProject) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(RegisterProject.class);
		RegisterProject entity = this.registerProjectService.saveOrUpdate(user, registerProject, valueMap);
		return OperResult.succeed("saveSuccess", "保存成功", entity);
	}

	/**
	 * 提交数据
	 * @param registerProject
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<RegisterProject> commit(RegisterProject registerProject) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(RegisterProject.class);
		RegisterProject entity = this.registerProjectService.commit(user, registerProject, valueMap);
		return OperResult.succeed("saveSuccess", "保存成功", entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param projectIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String projectIds) {
		UserProfile user = this.getUserProfile();
		this.registerProjectService.delete(user, projectIds);
		return OperResult.succeed("deleteSuccess");
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData() {
		return RegisterProject.getSwfStatusMap();
	}

	/**
	 * 项目所属事业部
	 * 解决 LinkedHashMap 中key值为数字时，前台顺序错乱
	 * @return List<JSONObject>
	 */
	@RequestMapping(value = "/selectProjectDeptJsonData")
	public @ResponseBody
	List<JSONObject> selectProjectDeptJsonData() {
		Map<String, String> map = TpcConstant.getProjectDeptMap();
		List<JSONObject> list = new ArrayList<JSONObject>();
		for (Map.Entry<String, String> e : map.entrySet()) {
			String str = "{\"id\":\"" + e.getKey() + "\",\"text\":\"" + e.getValue() + "\"}";
			JSONObject json = JSONObject.fromObject(str);
			list.add(json);
		}
		return list;
	}

	/**
	 * 部门代码
	 * @return
	 */
	@RequestMapping(value = "/selectProjectDeptData")
	public @ResponseBody
	Map<String, String> selectProjectDeptData() {
		return TpcConstant.getProjectDeptMap();
	}

	/**
	 * 项目类别
	 * @return
	 */
	@RequestMapping(value = "/selectProjectCategoryData")
	public @ResponseBody
	Map<Integer, String> selectProjectCategoryData() {
		return RegisterProjectConstant.getProjectCategoryMap();
	}

	/**
	 * 采购类型
	 * @return
	 */
	@RequestMapping(value = "/selectPurchaseTypeData")
	public @ResponseBody
	Map<Integer, String> selectPurchaseTypeData() {
		return RegisterProjectConstant.getPurchaseTypeMap();
	}

	/**
	 * 项目类型
	 * @return
	 */
	@RequestMapping(value = "/selectProjectClassificationData")
	public @ResponseBody
	Map<String, String> selectProjectClassificationData() {
		return RegisterProjectConstant.getProjectClassificationMap();
	}

	/**
	 * 项目性质
	 * @return
	 */
	@RequestMapping(value = "/selectProjectNatureData")
	public @ResponseBody
	Map<String, String> selectProjectNatureData() {
		return RegisterProjectConstant.getProjectNatureMap();
	}

	/**
	 * 是否有框架协议
	 * @return
	 */
	@RequestMapping(value = "/selectFrameworkAgreementData")
	public @ResponseBody
	List<CheckBoxVO> selectFrameworkAgreementData(String key) {
		return this.registerProjectService.getFrameworkAgreementList(CommonUtil.trim(key));
	}

	/**
	 * 单一类型
	 * @return
	 */
	@RequestMapping(value = "/selectSingleCategoryData")
	public @ResponseBody
	Map<String, String> selectSingleCategoryData() {
		return TpcConstant.getSingleCategoryMap();
	}

	/**
	 * 全部币别
	 * @return
	 */
	@RequestMapping(value = "/selectCurrencyData")
	public @ResponseBody
	Map<String, String> selectCurrencyData() {
		return TpcConstant.getCurrencyMap();
	}

	/**
	 * 常用币别
	 * @return
	 */
	@RequestMapping(value = "/selectCommonCurrencyData")
	public @ResponseBody
	Map<String, String> selectCommonCurrencyData() {
		return TpcConstant.getCommonCurrencyMap();
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectCurrencyRateData")
	public @ResponseBody
	Map<String, String> selectCurrencyRateData() {
		return TpcConstant.getCurrencyRateMap();
	}

	/**
	 * 校验项目名称
	 */
	@RequestMapping("checkNameIsValid")
	public @ResponseBody
	Boolean checkNameIsValid(String projectId, String projectName) {
		return this.registerProjectService.checkNameIsValid(projectId, projectName);
	}

	/**
	 * 校验项目代码
	 */
	@RequestMapping("checkAbbreviationIsValid")
	public @ResponseBody
	Boolean checkAbbreviationIsValid(String projectId, String projectAbbreviation) {
		return this.registerProjectService.checkAbbreviationIsValid(projectId, projectAbbreviation);
	}

}
