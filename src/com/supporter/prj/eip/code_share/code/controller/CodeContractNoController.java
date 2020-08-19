package com.supporter.prj.eip.code_share.code.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.eip.code_share.code.service.CodeContractNoService;
import com.supporter.prj.eip.code_share.code.entity.CodeContractNo;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.common.entity.OperResult;

/**   
 * @Title: Controller
 * @Description: CS_CODE_CONTRACT_NO.
 * @author Administrator
 * @date 2019-07-17 16:46:44
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("code_share/code/codeContractNo")
public class CodeContractNoController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CodeContractNoService codeContractNoService;
	
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param codeId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody CodeContractNo initEditOrViewPage(String codeId){
		CodeContractNo entity = codeContractNoService.initEditOrViewPage(codeId);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * @param request 页面请求对象
	 * @param jqGridReq 表格
	 * @param codeContractNo 合同编号记录对象
	 * @return JqGrid
	 */
	@RequestMapping("getGrid")
	@ResponseBody
	public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, CodeContractNo codeContractNo) {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.codeContractNoService.getGrid(user, jqGrid, codeContractNo);
		return jqGrid;
	}
	
	/**
	 * 保存数据.
	 * 
	 * @param codeContractNo 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("save")
	public @ResponseBody OperResult< CodeContractNo > save(CodeContractNo codeContractNo){
		UserProfile user = this.getUserProfile();
		OperResult< CodeContractNo > op = this.codeContractNoService.saveOrUpdate(user, codeContractNo);
		return op;
	}

	/**
	 * 删除操作.
	 * 
	 * @param codeIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult< ? > batchDel(String codeIds) {
		UserProfile user = this.getUserProfile();
		OperResult< ? > op = this.codeContractNoService.delete(user, codeIds);
		return op;
	}
	
	/**
	 * 获取合同编码类型码表
	 * @return Map <Integer, String>
	 */
	@RequestMapping("getCodeTypeMap")
	@ResponseBody
	public Map <Integer, String> getCodeTypeMap() {
		Map <Integer, String> map = CodeContractNo.CodeType.getMap();
		map.put(0, "全部");
		return map;
	}
	
	/**
	 * 强制锁定
	 * @param contractRecId 编码记录ID
	 * @return OperResult<?>
	 */
	@RequestMapping("forceLock")
	@ResponseBody
	public OperResult<?> forceLock(String contractRecId) {
		codeContractNoService.forceLock(contractRecId);
		return OperResult.succeed("success", null, null);
	}
	
	/**
	 * 强制解锁
	 * @param contractRecId 编码记录ID
	 * @return OperResult<?>
	 */
	@RequestMapping("fireLock")
	@ResponseBody
	public OperResult<?> fireLock(String contractRecId) {
		codeContractNoService.fireLock(contractRecId);
		return OperResult.succeed("success", null, null);
	}
	
}
