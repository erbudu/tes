package com.supporter.prj.linkworks.oa.seal_manage.destruction.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.module.constant.ModuleConstant;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.seal_manage.destruction.entity.SealDestruction;
import com.supporter.prj.linkworks.oa.seal_manage.destruction.service.SealDestructionService;
import com.supporter.prj.pm.util.AuthUtils;
import com.supporter.spring_mvc.AbstractController;



/**   
 * @Title: Controller
 * @Description: 印章销毁数据表.
 * @author z
 * @date 2019-12-12 16:59:13
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("eip/destruction/sealDestruction")
public class SealDestructionController extends AbstractController {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SealDestructionService sealDestructionService;
	
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param applyId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody SealDestruction initEditOrViewPage(String applyId){
		UserProfile user = this.getUserProfile();
		SealDestruction entity = sealDestructionService.initEditOrViewPage(applyId,user);
		return entity;
	}
	
	/**
	 * 进入查看页面时加载的信息
	 * 
	 * @param applyId 主键
	 * @return
	 */
	@RequestMapping("ViewSealDestruction")
	public @ResponseBody SealDestruction ViewSealDestruction(String applyId){
		SealDestruction entity = sealDestructionService.ViewSealDestruction(applyId);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, SealDestruction sealDestruction) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.sealDestructionService.getGrid(jqGrid, sealDestruction,user);
		return jqGrid;
	}
	
	/**
	 * 保存或更新数据.
	 * 
	 * @param sealDestruction 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult< SealDestruction > saveOrUpdate(SealDestruction sealDestruction){
		UserProfile user = this.getUserProfile();
		SealDestruction entity = this.sealDestructionService.saveOrUpdate(user, sealDestruction);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS, null, entity);
	}
	
	/**
	 * 删除操作
	 * 
	 * @param applyIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String applyIds) {
		UserProfile user = this.getUserProfile();
		this.sealDestructionService.delete(user, applyIds);
		return OperResult.succeed("deleteSuccess", null, null);
	}
	
	/**
	 * 获取状态数据
	 */
	@RequestMapping(value = "/selectStatusData")
	public @ResponseBody
	Map<Integer, String> selectStatusData() {
		return SealDestruction.getStatusMap();
	}
	/**
	 * 保存归档备注
	 */
	@RequestMapping("savePrintMessage")
	@ResponseBody
	public SealDestruction savePrintMessage(String applyId, String remarks) {
		SealDestruction sealDestruction = sealDestructionService.savePrintMessage(applyId, remarks);
		return sealDestruction;
	}
	/**
	 * 验证权限
	 *
	 * @param entityId 主键
	 * @param authOper 权限
	 * @return OperResult<?>
	 */
	@RequestMapping("validAuth")
	public @ResponseBody OperResult<?> validAuth(String entityId, String authOper) {
		UserProfile user = this.getUserProfile();
		//参数分别对应的是 当前登录用户，应用编号，权限项编号，主键，根据主键获取的验证对象
		AuthUtils.canExecute(user, SealDestruction.MODULE_ID, authOper,
				entityId, sealDestructionService.get(entityId));
		return OperResult.succeed("success", "已授权", null);
	}
}
