package com.supporter.prj.linkworks.oa.salary_pwd.controller;

import java.io.IOException;
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
import com.supporter.prj.linkworks.oa.salary_pwd.entity.SalaryPwd;
import com.supporter.prj.linkworks.oa.salary_pwd.service.SalaryPwdService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("oa/salary/pwd")
public class SalaryPwdController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private SalaryPwdService salaryPwdService;

	/**
	 * 判断密码是否正确
	 * 
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("inquiry")
	public @ResponseBody
	String getGrid(HttpServletRequest request, JqGridReq jqGridReq,
			SalaryPwd salaryPwd) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);

			//判断输入的查询密码是否正确
			salaryPwd.setUserName(user.getAccountLogin());
			boolean isRight=this.salaryPwdService.getGrid(user, jqGrid, salaryPwd);
			if(isRight){
				return "salary_list";
			}else{
				return "salaryPwd_error";
			}
	
		
	}
	
	/**
	 * 判断该用户是否是第一次查询工资
	 * 
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("isFirst")
	public @ResponseBody
	String isFirst(HttpServletRequest request, JqGridReq jqGridReq,
			SalaryPwd salaryPwd) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		//第一步：查询该用户是不是第一次查询工资（如果是第一次，首先设置查询密码）
		salaryPwd.setUserName(user.getAccountLogin());
		boolean NoFirst=this.salaryPwdService.getBySalaryName(jqGrid,salaryPwd);	
		//第二步：
		if(NoFirst){//不是第一次，（有查询密码，无需设置）
			//跳转到密码输入界面
			return "salaryPwd";

		}else{//是第一次，需要设置查询密码（相当于注册）
			//跳转到设置密码界面
			return "salaryPwd_new";
		}		
		
	}

	/**
	 * 根据主键获取功能模块�?.
	 * 
	 * @param reportId
	 *            主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody
	SalaryPwd get(String roomId) {
		UserProfile user = this.getUserProfile();
		SalaryPwd salaryPwd = salaryPwdService.get(roomId);
		return salaryPwd;

	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param roomId
	 *            主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	SalaryPwd initEditOrViewPage(String roomId) {
		SalaryPwd entity = salaryPwdService.initEditOrViewPage(roomId, this
				.getUserProfile());
		return entity;
	}

	/**
	 * 保存或更新数据
	 * 
	 * @param report
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<SalaryPwd> saveOrUpdate(SalaryPwd salaryPwd) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(SalaryPwd.class);
		SalaryPwd entity = this.salaryPwdService.saveOrUpdate(user,
				salaryPwd, valueMap);
		
//		return OperResult.succeed(SalaryPwdConstant.I18nKey.SAVE_SUCCESS,
//				null, null);
		return OperResult.succeed("saveSuccess", null, entity);
	}

//	/**
//	 * 删除操作
//	 * 
//	 * @param reportIds
//	 *            主键集合，多个以逗号分隔
//	 * @return
//	 */
//	@RequestMapping("batchDel")
//	public @ResponseBody
//	OperResult batchDel(String roomIds) {
//		UserProfile user = this.getUserProfile();
//		this.salaryPwdService.delete(user, roomIds);
////		return OperResult.succeed(SalaryPwdConstant.I18nKey.DELETE_SUCCESS,
////				null, null);
//		return OperResult.succeed("deleteSuccess", null, null);
//	}

}
