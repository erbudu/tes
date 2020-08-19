package com.supporter.prj.eip.code_share.code.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.eip.code_share.code.service.EntitySalesContractService;
import com.supporter.prj.eip.code_share.code.entity.EntitySalesContract;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.common.entity.OperResult;

/**   
 * @Title: Controller
 * @Description: CS_ENTITY_SALES_CONTRACT.
 * @author Administrator
 * @date 2019-07-17 16:46:39
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("code_share/code/entitySalesContract")
public class EntitySalesContractController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EntitySalesContractService entitySalesContractService;
	
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param contractRecId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody EntitySalesContract initEditOrViewPage(String contractRecId){
		EntitySalesContract entity = entitySalesContractService.initEditOrViewPage(contractRecId);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * @param request 页面请求对象
	 * @param jqGridReq 表格
	 * @param entitySalesContract 对象参数
	 * @return JqGrid
	 */
	@RequestMapping("getGrid")
	@ResponseBody
	public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, EntitySalesContract entitySalesContract) {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.entitySalesContractService.getGrid(user, jqGrid, entitySalesContract);
		return jqGrid;
	}
	
	/**
	 * 保存数据.
	 * 
	 * @param entitySalesContract 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("save")
	public @ResponseBody OperResult< EntitySalesContract > save(EntitySalesContract entitySalesContract){
		UserProfile user = this.getUserProfile();
		OperResult< EntitySalesContract > op = this.entitySalesContractService.save(user, entitySalesContract);
		return op;
	}

	/**
	 * 删除操作.
	 * 
	 * @param contractRecIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult< ? > batchDel(String contractRecIds) {
		UserProfile user = this.getUserProfile();
		OperResult< ? > op = this.entitySalesContractService.delete(user, contractRecIds);
		return op;
	}
	
	/**
	 * 根据项目库和销售合同ID获取销售合同对象
	 * @param prjLib 项目库
	 * @param contractId 销售合同ID
	 * @return EntitySalesContract
	 */
	@RequestMapping("getContract")
	@ResponseBody
	public EntitySalesContract getContract(String prjLib, String contractId) {
		return entitySalesContractService.getContractInLibById(prjLib, contractId);
	}
	
}
