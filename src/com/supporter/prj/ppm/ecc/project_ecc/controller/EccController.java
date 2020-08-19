package com.supporter.prj.ppm.ecc.project_ecc.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.ecc.project_ecc.service.EccService;
import com.supporter.prj.ppm.ecc.project_ecc.entity.Ecc;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 出口管制.
 * @author YAN
 * @date 2019-08-16 18:23:05
 * @version V1.0
 */
@Controller
@RequestMapping("ecc/project_ecc/ecc")
public class EccController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EccService eccService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param eccId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody Ecc initEditOrViewPage(String eccId) {
		Ecc entity = eccService.initEditOrViewPage(eccId);
		return entity;
	}
	@RequestMapping("initByPrjId")
	public @ResponseBody Ecc initByPrjId(String prjId) {
		Ecc entity = eccService.initByPrjId(prjId);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, Ecc ecc) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.eccService.getGrid(user, jqGrid, ecc);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param ecc 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(Ecc ecc) {
		UserProfile user = this.getUserProfile();
		Ecc entity = this.eccService.saveOrUpdate(user, ecc);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 删除操作
	 * 
	 * @param eccIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String eccIds) {
		UserProfile user = this.getUserProfile();
		this.eccService.delete(user, eccIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param eccId
	 * @param eccName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String eccId,String eccName) {
		return this.eccService.nameIsValid(eccId, eccName);
	}

	/**
	 *  导出表单
	 */
	@RequestMapping("exportExcel")
	public @ResponseBody String exportExcel(HttpServletResponse response, HttpServletRequest request, String prjId, String formType) throws Exception {
		System.out.println("项目ID:" + prjId);
		try {
			eccService.exportExcel(response, request, prjId, formType);
			return "导出成功！！！";
		} catch (Exception e) {
			e.printStackTrace();
			return "导出失败！！！";
		}
	}
}
