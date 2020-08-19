package com.supporter.prj.linkworks.oa.emp_meal_apply.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
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
import com.supporter.prj.linkworks.oa.dept_meal_non_emps.dao.DeptMealNonEmpsDao;
import com.supporter.prj.linkworks.oa.dept_meal_non_emps.dao.DeptMealNonEmpsRecDao;
import com.supporter.prj.linkworks.oa.dept_meal_non_emps.entity.DeptMealNonEmps;
import com.supporter.prj.linkworks.oa.dept_meal_non_emps.entity.DeptMealNonEmpsRec;
import com.supporter.prj.linkworks.oa.doc_in.constants.AuthConstant;
import com.supporter.prj.linkworks.oa.doc_in.util.AuthUtil;
import com.supporter.prj.linkworks.oa.doc_out.entity.OaDocOut;
import com.supporter.prj.linkworks.oa.emp_meal_apply.constants.EmpMealAuthConstant;
import com.supporter.prj.linkworks.oa.emp_meal_apply.entity.EmpCustormerMealRec;
import com.supporter.prj.linkworks.oa.emp_meal_apply.entity.EmpMealApply;
import com.supporter.prj.linkworks.oa.emp_meal_apply.entity.NonEmpMealRec;
import com.supporter.prj.linkworks.oa.emp_meal_apply.service.EmpMealApplyService;
import com.supporter.prj.linkworks.oa.invitation_f.util.InvitationConstant;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: Controller
 * @Description: 物资信息设置.
 * @author yanbingchao
 * @date 2017-3-27 15:25:34
 * @version V1.0
 * 
 */

@Controller
@RequestMapping("oa/empMealApply")
public class EmpMealApplyController extends AbstractController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private EmpMealApplyService codeService;
	@Autowired
	private DeptMealNonEmpsDao empDao;
	@Autowired
	private DeptMealNonEmpsRecDao empRecDao;

	/**
	 * 根据主键获取.
	 * 
	 * @param applyId
	 *            主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody
	EmpMealApply get(String applyId) {
		EmpMealApply code = codeService.get(applyId);
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
	EmpMealApply initEditOrViewPage(String applyId, String docClassify) {
		UserProfile user = this.getUserProfile();
		EmpMealApply entity = codeService.initEditOrViewPage(applyId,
				docClassify, user);
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
			EmpMealApply code) throws Exception {
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
	OperResult<EmpMealApply> saveOrUpdate(EmpMealApply code) {
		UserProfile user = this.getUserProfile();
		//权限验证
		AuthUtil.canExecute(user, EmpMealAuthConstant.AUTH_OPER_NAME_SETVALPPLY, code.getApplyId(), code);
		Map<String, Object> valueMap = this.getPropValues(EmpMealApply.class);
		EmpMealApply entity = this.codeService.saveOrUpdate(user, code,
				valueMap);
		return OperResult.succeed(InvitationConstant.I18nKey.SAVE_SUCCESS,
				"保存成功", entity);
	}
	@RequestMapping("viewPage")
	@ResponseBody
	public EmpMealApply viewPage(String applyId) {
		EmpMealApply entity = codeService.viewPage(applyId,this.getUserProfile());
		return entity;
	}
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<EmpMealApply> commit(EmpMealApply code) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(EmpMealApply.class);
		code.setStatus(1);
		EmpMealApply entity = this.codeService.saveOrUpdate(user, code,
				valueMap);
		return OperResult.succeed(InvitationConstant.I18nKey.SAVE_SUCCESS,
				"保存成功", entity);
	}

	@RequestMapping("saveFromHistory")
	public @ResponseBody
	OperResult<EmpMealApply> saveFromHistory(EmpMealApply code) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(EmpMealApply.class);
		EmpMealApply entity = this.codeService.saveFromHistory(user, code,
				valueMap);
		return OperResult.succeed(InvitationConstant.I18nKey.SAVE_SUCCESS,
				entity.getApplyId(), entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param applyIds
	 *            主键集合，多个以逗号分隔
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult batchDel(String applyIds) {
		UserProfile user = this.getUserProfile();
		this.codeService.delete(user, applyIds);
		return OperResult.succeed(InvitationConstant.I18nKey.DELETE_SUCCESS,
				null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param applyId
	 * @param materialName
	 * @return
	 */
	@RequestMapping("checkNameIsValid")
	public @ResponseBody
	Boolean checkNameIsValid(EmpMealApply entity) {
		return this.codeService.checkNameIsValid(entity);
	}

	@RequestMapping("getStatus")
	public @ResponseBody
	Map<String, String> getOutDocStatus() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		/*
		 * List <IComCodeTableItem> list =
		 * EIPService.getComCodeTableService().getCodeTableItems("UNIT");
		 * for(IComCodeTableItem item : list){ map.put(item.getItemId(),
		 * item.getDisplayName()); }
		 */
		map.put("0", "未提交");
		map.put("1", "审批进行中");
		map.put("2", "审批完成");
		return map;
	}

	@RequestMapping("getYear")
	public @ResponseBody
	Map<String, String> getYear() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		/*
		 * List <IComCodeTableItem> list =
		 * EIPService.getComCodeTableService().getCodeTableItems("UNIT");
		 * for(IComCodeTableItem item : list){ map.put(item.getItemId(),
		 * item.getDisplayName()); }
		 */
		map.put("2016", "2016年");
		map.put("2017", "2017年");
		map.put("2018", "2018年");
		map.put("2019", "2019年");
		map.put("2020", "2020年");
		return map;
	}

	@RequestMapping("getMonth")
	public @ResponseBody
	Map<String, String> getMonth() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		/*
		 * List <IComCodeTableItem> list =
		 * EIPService.getComCodeTableService().getCodeTableItems("UNIT");
		 * for(IComCodeTableItem item : list){ map.put(item.getItemId(),
		 * item.getDisplayName()); }
		 */
		String month = "";
		for (int i = 0; i < 12; i++) {
			month = String.valueOf(i + 1);
			map.put(month, month + "月");
		}
		return map;
	}

	@RequestMapping("getCustormer")
	public @ResponseBody
	Map<String, String> getCustormer(String deptId) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		UserProfile user = this.getUserProfile();
		if (deptId == null) {
			deptId = "1";
		}
		List<DeptMealNonEmps> l = empDao.findByDeptId(deptId);
		String hql = " from "
				+ DeptMealNonEmpsRec.class.getName()
				+ " where nonEmpIds = :nonEmpIds and nonEmpType != 1 and isAvailable = 1";
		for (DeptMealNonEmps deptMealNonEmps : l) {
			List<DeptMealNonEmpsRec> recs = empRecDao.findByNamedParam(hql,
					"nonEmpIds", deptMealNonEmps.getNonEmpIds());
			for (DeptMealNonEmpsRec deptMealNonEmpsRec : recs) {
				String name = deptMealNonEmpsRec.getNonEmpName();
				map.put(name, name);
			}

		}
		return map;
	}

	@RequestMapping("getNonMelEmp")
	public @ResponseBody
	Map<String, String> getNonMelEmp(String deptId) {
		UserProfile user = this.getUserProfile();
		if (deptId == null) {
			deptId = "1";
		}
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<DeptMealNonEmps> l = empDao.findByDeptId(deptId);
		String hql = " from "
				+ DeptMealNonEmpsRec.class.getName()
				+ " where nonEmpIds = :nonEmpIds and nonEmpType = 1 and isAvailable = 1";
		for (DeptMealNonEmps deptMealNonEmps : l) {
			List<DeptMealNonEmpsRec> recs = empRecDao.findByNamedParam(hql,
					"nonEmpIds", deptMealNonEmps.getNonEmpIds());
			for (DeptMealNonEmpsRec deptMealNonEmpsRec : recs) {
				String name = deptMealNonEmpsRec.getNonEmpName();
				map.put(name, name);
			}

		}

		/*
		 * List <IComCodeTableItem> list =
		 * EIPService.getComCodeTableService().getCodeTableItems("UNIT");
		 * for(IComCodeTableItem item : list){ map.put(item.getItemId(),
		 * item.getDisplayName()); }
		 */
		return map;
	}
}
