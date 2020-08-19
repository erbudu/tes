package com.supporter.prj.linkworks.oa.use_seal_apply.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.module.constant.ModuleConstant;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.use_seal_apply.entity.UseSealApply;
import com.supporter.prj.linkworks.oa.use_seal_apply.entity.VUseSealApply;
import com.supporter.prj.linkworks.oa.use_seal_apply.service.UseSealApplyService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("oa/use_seal_apply")
public class UseSealApplyController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private UseSealApplyService useSealApplyService; 

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, VUseSealApply vUseSealApply) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.useSealApplyService.getGrid(user, jqGrid, vUseSealApply);
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
	public @ResponseBody UseSealApply get(String applyId) {;
		UseSealApply useSealApply = useSealApplyService.get(applyId);
		return useSealApply;

	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param applyId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody UseSealApply initEditOrViewPage(HttpServletRequest request,JqGridReq jqGridReq,String applyId) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		UseSealApply entity = useSealApplyService.initEditOrViewPage(jqGrid,applyId,this.getUserProfile());
		return entity;
	}
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param applyId 主键
	 * @return
	 */
	@RequestMapping("initSignNo")
	public @ResponseBody UseSealApply initSignNo(HttpServletRequest request,JqGridReq jqGridReq,String applyId) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		UseSealApply entity = useSealApplyService.initSignNo(jqGrid,applyId,this.getUserProfile());
		
		return entity;
	}
	/**
	 * 保存或更新数据
	 * 
	 * @param report 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<UseSealApply> saveOrUpdate(UseSealApply useSealApply) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(UseSealApply.class);		
		UseSealApply entity = this.useSealApplyService.saveOrUpdate(user,useSealApply,valueMap);
//		return OperResult.succeed(SalaryConstant.I18nKey.SAVE_SUCCESS, null, null);
		return OperResult.succeed("saveSuccess", "保存成功", entity);
	}
	
	/**
	 * 流程中的保存
	 * 
	 * @param useSealApply 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveProc")
	public @ResponseBody void saveProc(UseSealApply useSealApply) {
		UserProfile user = this.getUserProfile();	
		String applyId=useSealApply.getApplyId();
		if(applyId!=null){
			UseSealApply useSealApplyZ=this.useSealApplyService.get(applyId);
			useSealApplyZ.setAdd(false);
			if(useSealApply.getUseSealApplyBoardDesc()!=null&&!useSealApply.getUseSealApplyBoardDesc().equals("")){
				useSealApplyZ.setUseSealApplyBoardDesc(useSealApply.getUseSealApplyBoardDesc());
				this.useSealApplyService.saveBoard(user,useSealApplyZ);
			}	
			//要是编号不为空就保存编号
			if(useSealApply.getSignNo()!=null&&!useSealApply.getSignNo().equals("")){
				useSealApplyZ.setSignNo(useSealApply.getSignNo());
				this.useSealApplyService.update(useSealApplyZ);
			}
			
			
		}				
	}
	
	/**
	 * 获取留言板
	 */
	@RequestMapping("getMessageBoard")
	public @ResponseBody String getMessageBoard(String applyId) {
		return this.useSealApplyService.getMessageBoard(applyId);
	}
	
	/**
	 * 获取留言板
	 */
	@RequestMapping("getMessageBoardForIphone")
	public @ResponseBody String getMessageBoardForIphone(String applyId) {
		return this.useSealApplyService.getMessageBoardForIphone(applyId);
	}
	/**
	 * 保存审批中会签人
	 */
	@RequestMapping("saveSigner")
	public @ResponseBody OperResult<UseSealApply> saveSigner(String applyId) {
		UserProfile user = this.getUserProfile();
		this.useSealApplyService.saveSigner(applyId,user);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS,null,null);
	}	
	
	
	/**
	 * 流程中的保存
	 * 
	 * @param maintenance 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveProcOfRelatedNames")
	public @ResponseBody void saveProcOfRelatedNames(UseSealApply useSealApply) {	
		String applyId=useSealApply.getApplyId();
		if(applyId!=null){
			UseSealApply useSealApplyZ=this.useSealApplyService.get(applyId);
            useSealApplyZ.setRelatedIds(useSealApply.getRelatedIds());
            useSealApplyZ.setRelatedNames(useSealApply.getRelatedNames());
			useSealApplyZ.setAdd(false);
			this.useSealApplyService.update(useSealApplyZ);
		}				
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
	OperResult<UseSealApply> commit(UseSealApply useSealApply) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this
				.getPropValues(UseSealApply.class);
		UseSealApply entity = this.useSealApplyService.commit(user,
				useSealApply, valueMap);
//		return OperResult.succeed(SalaryConstant.I18nKey.SAVE_SUCCESS, null, null);
		return OperResult.succeed("saveSuccess", "保存成功", entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param reportIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String applyIds) {
		UserProfile user = this.getUserProfile();
		this.useSealApplyService.delete(user, applyIds);
//		return OperResult.succeed(SalaryConstant.I18nKey.DELETE_SUCCESS, null, null);
		return OperResult.succeed("deleteSuccess", "删除成功", null);
	}
	
	/**
	 * 重置流程状态
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("updateStatus")
	public @ResponseBody
	OperResult updateStatus(String applyId ,String applyStatus) {
		
		if(applyStatus!=null&&!applyStatus.equals("")){
			Integer swfStatusInt = Integer.parseInt(applyStatus);
			this.useSealApplyService.updateStatus(applyId,swfStatusInt);
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
	@RequestMapping(value = "/getUseSealApplyStatusCodetable")
	public Map<Integer, String> getUseSealApplyStatusCodetable()
			throws IOException {
		Map<Integer, String> map = UseSealApply.getStatusCodeTable();
		return map;
	}
	
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("extractFiles")
	public @ResponseBody String extractFiles(String applyId){
		return useSealApplyService.extractFiles(applyId,this.getUserProfile());
	}
	
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("batchExtractFiles")
	public @ResponseBody String batchExtractFiles(){
		return useSealApplyService.batchExtractFiles(this.getUserProfile());
	}
	
	
	
	/**
	 *验证是不是公司领导（如果是返回“no”）
	 * 
	 * @param deptSignerIds 人员id
	 * @return String (yes/no)
	 */
	@RequestMapping("checkIsLeaders")
	public @ResponseBody String checkIsLeaders(String relatedIds) {
		String isSatisfied=this.useSealApplyService.checkIsLeaders(relatedIds);
		return isSatisfied;
	}
	
	
}
