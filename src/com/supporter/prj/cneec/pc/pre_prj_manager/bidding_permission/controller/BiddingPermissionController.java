package com.supporter.prj.cneec.pc.pre_prj_manager.bidding_permission.controller;

import com.supporter.prj.cneec.pc.pre_prj_manager.bidding_permission.entity.BiddingPermission;
import com.supporter.prj.cneec.pc.pre_prj_manager.bidding_permission.service.BiddingPermissionService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;



/**   
 * @Title: Controller
 * @Description: 投标许可.
 * @author kangh_000
 * @date 2018-12-11 09:56:38
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("pre_prj_manager/bidding_permission/biddingPermission")
public class BiddingPermissionController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private BiddingPermissionService biddingPermissionService;
	
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param biddingPermissionId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody BiddingPermission initEditOrViewPage(String biddingPermissionId){
		UserProfile user = this.getUserProfile();
		BiddingPermission entity = biddingPermissionService.initEditOrViewPage(biddingPermissionId, user);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, BiddingPermission biddingPermission) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.biddingPermissionService.getGrid(user, jqGrid, biddingPermission);
		return jqGrid;
	}
	
	/**
	 * 保存数据.
	 * 
	 * @param biddingPermission 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("save")
	public @ResponseBody OperResult< BiddingPermission > save(BiddingPermission biddingPermission){
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(BiddingPermission.class);
		OperResult< BiddingPermission > op = this.biddingPermissionService.save(user, biddingPermission, valueMap);
		return op;
	}
	
	/**
	 * 更新数据.
	 * 
	 * @param biddingPermission 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("update")
	public @ResponseBody OperResult< BiddingPermission > update(BiddingPermission biddingPermission){
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(BiddingPermission.class);
		OperResult< BiddingPermission > op = this.biddingPermissionService.update(user, biddingPermission, valueMap);
		return op;
	}
	
	/**
	 * 删除前判断是否可以删除.
	 * @return
	 */
	@RequestMapping("checkBatchDelCondition")
	public  @ResponseBody List< OperResult< ? > > checkBatchDelCondition(String biddingPermissionIds){
		return this.biddingPermissionService.checkBatchDelCondition(this.getUserProfile(), biddingPermissionIds);
	}
	
	/**
	 * 删除操作.
	 * 
	 * @param biddingPermissionIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult< ? > batchDel(String biddingPermissionIds) {
		UserProfile user = this.getUserProfile();
		OperResult< ? > op = this.biddingPermissionService.delete(user, biddingPermissionIds);
		return op;
	}
	
	/**
	 * 判断指定字段唯一性
	 * 
	 * @param biddingPermissionId
	 * @param propertyName 属性名称
	 * @param propertyName 属性值
	 * @return
	 */
	@RequestMapping("checkPropertyUniquenes")
	public @ResponseBody Boolean checkPropertyUniquenes(String biddingPermissionId, String propertyName, String propertyValue){
		return this.biddingPermissionService.checkPropertyUniquenes(biddingPermissionId, propertyName, propertyValue);
	}
}
