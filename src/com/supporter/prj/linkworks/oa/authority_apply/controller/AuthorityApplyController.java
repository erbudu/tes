package com.supporter.prj.linkworks.oa.authority_apply.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApply;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApplyPerson;
import com.supporter.prj.linkworks.oa.authority_apply.service.AuthorityApplyService;
import com.supporter.prj.eip.module.constant.ModuleConstant;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Controller
 * @Description: 功能模块�?.
 * @author gongjiwen
 * @date 2016-12-06 15:25:34
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("oa/authority_apply")
public class AuthorityApplyController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private AuthorityApplyService authorityApplyService; 

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, AuthorityApply authorityApply) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.authorityApplyService.getGrid(user, jqGrid, authorityApply);
		return jqGrid;
	}
    
	/**
	 * 展示公司内部人员列表.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getPersonInnerGrid")
	public @ResponseBody JqGrid getPersonInnerGrid(HttpServletRequest request, JqGridReq jqGridReq, AuthorityApplyPerson ap,String applyId) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		UserProfile user = this.getUserProfile();
		List<AuthorityApplyPerson> list = this.authorityApplyService.getPersonInnerGrid(user,jqGrid,applyId);
		return jqGrid;
	}
	
	
	/**
	 * 展示公司外部人员列表.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getPersonOuterGrid")
	public @ResponseBody JqGrid getPersonOuterGrid(HttpServletRequest request, JqGridReq jqGridReq, AuthorityApplyPerson ap,String applyId) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		UserProfile user = this.getUserProfile();
		List<AuthorityApplyPerson> list = this.authorityApplyService.getPersonOuterGrid(user,jqGrid,applyId);
		return jqGrid;
	}


	/**
	 * 根据主键获取功能模块�?.
	 * 
	 * @param reportId 主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody AuthorityApply get(String applyId) {
		UserProfile user = this.getUserProfile();
		AuthorityApply authorityApply = authorityApplyService.get(applyId);
		return authorityApply;

	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param applyId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody AuthorityApply initEditOrViewPage(HttpServletRequest request,JqGridReq jqGridReq,String applyId,String isEnglish) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
			AuthorityApply entity = authorityApplyService.initEditOrViewPage(jqGrid,applyId,this.getUserProfile(),isEnglish);
			return entity;
	}
	
	
	/**
	 * 初始化授权书编号
	 * 
	 * @param applyId 主键
	 * @return
	 */
	@RequestMapping("initSignNo")
	public @ResponseBody AuthorityApply initSignNo(HttpServletRequest request,JqGridReq jqGridReq,String applyId) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		AuthorityApply entity = authorityApplyService.initSignNo(jqGrid,applyId,this.getUserProfile());		
		return entity;
	}
	
	/**
	 * 保存授权书编号
	 * @param applyId
	 * @param signNo
	 * @return
	 */
	@RequestMapping("saveSignNo")
	public @ResponseBody AuthorityApply saveSignNo(String applyId, String signNo) {
		AuthorityApply entity = authorityApplyService.saveSignNo(CommonUtil.trim(applyId), CommonUtil.trim(signNo));		
		return entity;
	}
	
	
	/**
	 * 保存或更新数据
	 * 
	 * @param report 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<AuthorityApply> saveOrUpdate(AuthorityApply authorityApply) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(AuthorityApply.class);
		AuthorityApply entity = this.authorityApplyService.saveOrUpdate(user, authorityApply, valueMap);
//		return OperResult.succeed(AuthorityApplyConstant.I18nKey.SAVE_SUCCESS, null, null);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 保存或更新数据
	 * 
	 * @param report 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdatePerson")
	public @ResponseBody OperResult<AuthorityApplyPerson> saveOrUpdatePerson(AuthorityApplyPerson authorityApplyPerson) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(AuthorityApplyPerson.class);
		AuthorityApplyPerson entity = this.authorityApplyService.saveOrUpdatePerson(user, authorityApplyPerson, valueMap);
//		return OperResult.succeed(AuthorityApplyConstant.I18nKey.SAVE_SUCCESS, null, null);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	
	
	
	
	/**
	 * 保存提交.
	 * 
	 * @param apply
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<AuthorityApply> commit(AuthorityApply authorityApply) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this
				.getPropValues(AuthorityApply.class);
		AuthorityApply entity = this.authorityApplyService.commit(user,
				authorityApply, valueMap);
//		return OperResult.succeed(AuthorityApplyConstant.I18nKey.SAVE_SUCCESS, null, null);
		return OperResult.succeed("saveSuccess", null, entity);
	}

	/**
	 * 流程中的保存
	 * 
	 * @param useSealApply 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveProc")
	public @ResponseBody void saveProc(AuthorityApply authorityApply) {
		UserProfile user = this.getUserProfile();	
		String applyId=authorityApply.getApplyId();
		if(applyId!=null){
			AuthorityApply authorityApplyZ=this.authorityApplyService.get(applyId);
			    authorityApplyZ.setAdd(false);
				//如果留言板已经不等于空就保存留言板意见
				if(authorityApply.getAuthorityApplyBoardDesc()!=null&&!authorityApply.getAuthorityApplyBoardDesc().equals("")){
					//System.out.println("前台传过来的留言板意见为="+authorityApply.getAuthorityApplyBoardDesc());
					authorityApplyZ.setAuthorityApplyBoardDesc(authorityApply.getAuthorityApplyBoardDesc());
					this.authorityApplyService.saveBoard(user,authorityApplyZ);
				}
				//如果选择的相关会签人不为null就保存相关会签人的id和name				
				if(authorityApply.getRelatedIds()!=null){
					if(authorityApply.getRelatedNames()!=null){
						authorityApplyZ.setRelatedIds(authorityApply.getRelatedIds());
						authorityApplyZ.setRelatedNames(authorityApply.getRelatedNames());
						this.authorityApplyService.update(authorityApplyZ);
					}
				}
			
			
			
			
		}				
	}	
	
	/**
	 * 保存审批中会签人
	 */
	@RequestMapping("saveSigner")
	public @ResponseBody OperResult<AuthorityApply> saveSigner(String applyId) {
		UserProfile user = this.getUserProfile();
		this.authorityApplyService.saveSigner(applyId,user);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS,null,null);
	}	
	
	
	/**
	 * 获取留言板
	 */
	@RequestMapping("getMessageBoard")
	public @ResponseBody String getMessageBoard(String applyId) {
		return this.authorityApplyService.getMessageBoard(applyId);
	}
	
	/**
	 * 获取留言板
	 */
	@RequestMapping("getMessageBoardForIphone")
	public @ResponseBody String getMessageBoardForIphone(String applyId) {
		return this.authorityApplyService.getMessageBoardForIphone(applyId);
	}

	
	
	/**
	 * 删除操作
	 * 
	 * @param reportIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String applyIds) {
		UserProfile user = this.getUserProfile();
		this.authorityApplyService.delete(user, applyIds);
//		return OperResult.succeed(AuthorityApplyConstant.I18nKey.DELETE_SUCCESS, null, null);
		return OperResult.succeed("deleteSuccess", null, null);
	}
	
	/**
	 * 删除从表操作
	 * 
	 * @param authorityApplyPersonId 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("deletePerson")
	public @ResponseBody OperResult deletePerson(String authorityApplyPersonId) {
		UserProfile user = this.getUserProfile();
		this.authorityApplyService.deletePerson(user, authorityApplyPersonId);
//		return OperResult.succeed(AuthorityApplyConstant.I18nKey.DELETE_SUCCESS, null, null);
		return OperResult.succeed("deleteSuccess", null, null);
	}
	
	
	
	/**
	 * 重置流程状态
	 */
	@RequestMapping("updateStatus")
	public @ResponseBody
	OperResult updateStatus(String applyId ,String applyStatus) {
		if(applyStatus!=null&&!applyStatus.equals("")){
			Integer swfStatusInt = Integer.parseInt(applyStatus);
			this.authorityApplyService.updateStatus(applyId,swfStatusInt);			
		}
		return OperResult.succeed("saveSuccess", null, null);
	}
	/**
	 * 获取字典数据-用于高级查询页面的下拉显示
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getAuthorityApplyStatusCodetable")
	public Map<Integer, String> getAuthorityApplyStatusCodetable()
			throws IOException {
		Map<Integer, String> map = AuthorityApply
				.getStatusCodeTable();
		return map;
	}
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("extractFiles")
	public @ResponseBody String extractFiles(String applyId){
		return authorityApplyService.extractFiles(applyId,this.getUserProfile());
	}
	
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("batchExtractFiles")
	public @ResponseBody String batchExtractFiles(){
		return authorityApplyService.batchExtractFiles(this.getUserProfile());
	}
}




