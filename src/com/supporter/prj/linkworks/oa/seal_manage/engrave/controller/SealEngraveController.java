package com.supporter.prj.linkworks.oa.seal_manage.engrave.controller;

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
import com.supporter.prj.linkworks.oa.seal_manage.engrave.entity.SealEngrave;
import com.supporter.prj.linkworks.oa.seal_manage.engrave.service.SealEngraveService;
import com.supporter.prj.pm.util.AuthUtils;
import com.supporter.spring_mvc.AbstractController;



/**   
 * @Title: Controller
 * @Description: 印章刻制数据表.
 * @author z
 * @date 2019-12-12 17:52:29
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("eip/engrave/sealEngrave")
public class SealEngraveController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SealEngraveService sealEngraveService;
	
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param applyId 主键resp
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody SealEngrave initEditOrViewPage(String applyId){
		UserProfile user = this.getUserProfile();
		SealEngrave entity = sealEngraveService.initEditOrViewPage(applyId,user);
		return entity;
	}
	/**
	 * 进入审批页面查看
	 * 
	 * @param applyId 主键
	 * @return
	 */
	@RequestMapping("ViewPage")
	@ResponseBody
	public SealEngrave viewPage(String applyId) {
		SealEngrave entity = sealEngraveService.ViewPage(applyId,this.getUserProfile());
		return entity;
	}
	/**
	 * 进入查看页面时加载的信息
	 * 
	 * @param applyId 主键
	 * @return
	 */
	@RequestMapping("ViewSealEngrave")
	public @ResponseBody SealEngrave ViewSealEngrave(String applyId){
		SealEngrave entity = sealEngraveService.ViewSealEngrave(applyId);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, SealEngrave sealEngrave) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.sealEngraveService.getGrid(jqGrid, sealEngrave,user);
		return jqGrid;
	}
	
	/**
	 * 保存或更新数据.resp.body
	 * 
	 * @param sealEngrave 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<SealEngrave> saveOrUpdate(SealEngrave sealEngrave){
		UserProfile user = this.getUserProfile();
		SealEngrave entity = this.sealEngraveService.saveOrUpdate(user, sealEngrave);
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
		this.sealEngraveService.delete(user, applyIds);
		return OperResult.succeed("deleteSuccess", null, null);
	}
	
	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectStatusData")
	public @ResponseBody
	Map<Integer, String> selectStatusData() {
		return SealEngrave.getStatusMap();
	}
	/**
	 * 保存归档备注
	 */
	@RequestMapping("savePrintMessage")
	@ResponseBody
	public SealEngrave savePrintMessage(String applyId, String remarks) {
		SealEngrave sealEngrave = sealEngraveService.savePrintMessage(applyId, remarks);
		return sealEngrave;
	}
	/**
	 * 保存是否送总裁审批
	 */
	@RequestMapping("saveIsPresident")
	@ResponseBody
	public SealEngrave saveIsPresident(String applyId, int isPresident) {
		SealEngrave sealEngrave = sealEngraveService.saveIsPresident(applyId, isPresident);
		return sealEngrave;
	}
	/**
	 * 保存是否送总裁审批字段为已发送审批，值为3
	 */
	@RequestMapping("savePresident")
	@ResponseBody
	public SealEngrave savePresident(String applyId) {
		SealEngrave sealEngrave = sealEngraveService.savePresident(applyId);
		return sealEngrave;
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
		AuthUtils.canExecute(user, SealEngrave.MODULE_ID, authOper,
				entityId, sealEngraveService.get(entityId));
		return OperResult.succeed("success", "已授权", null);
	}
}
