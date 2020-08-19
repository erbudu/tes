package com.supporter.prj.cneec.tpc.supplier.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.supplier.entity.Supplier;
import com.supporter.prj.cneec.tpc.supplier.service.SupplierService;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.log.controller.AbstractController;
import com.supporter.util.CommonUtil;

@Controller
@RequestMapping("tpc/supplier")
public class SupplierController extends AbstractController {

	private static final long serialVersionUID = 1L;
	@Autowired
	private SupplierService supplierService;
	
	/**
	 * 返回列表
	 * 分页表格展示数据
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, Supplier supplier) throws Exception{
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.supplierService.getGrid(user, jqGrid, supplier);
		return jqGrid;
	}
	
	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param supplierId
	 * @param map
	 */
	@ModelAttribute
	public void getSupplier(@RequestParam(value = "supplierId", required = false) String supplierId, Map<String, Object> map) {
		if (StringUtils.isNotBlank(supplierId)) {
			Supplier supplier = this.supplierService.get(supplierId);
			if (supplier != null) {
				map.put("supplier", supplier);
			}
		}
	}
	
	/**
	 * 进入新建、编辑或查看页面时加载的信息
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody Supplier initEditOrViewPage(String supplierId){
		UserProfile user = this.getUserProfile();
		Supplier entity = supplierService.initEditOrviewPage(supplierId, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.supplierService.getAuthCanExecute(user, entity);
		}
		return entity;
	}
	
	/**
	 * 保存或更新数据
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<Supplier> saveOrUpdate(Supplier supplier){
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.supplierService.getAuthCanExecute(user, supplier);
		Map<String, Object> valueMap = this.getPropValues(Supplier.class);
		Supplier entity = this.supplierService.saveOrUpdate(user, supplier, valueMap);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String supplierIds){
		UserProfile user = this.getUserProfile();
		this.supplierService.delete(user, supplierIds);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 失效操作
	 */
	@RequestMapping("batchLnvalid")
	public @ResponseBody OperResult batchLnvalid(String supplierIds){
		UserProfile user = this.getUserProfile();
		this.supplierService.batchLnvalid(user, supplierIds);
		return OperResult.succeed("lnvalidSuccess");
	}
	
	/**
	 * 获取供应商有效状态
	 */
	@ResponseBody
	@RequestMapping("getControlStatus")
	public Map<String,String> getControlStatusType(){
		Map<String,String> map = Supplier.getControlStatusCodeMap();
		return map;
	}
	
	/**
	 * 判断名字是否重复
	 */
	@RequestMapping("checkNameIsValid")
	public @ResponseBody Boolean checkNameIsValid(String supplierId, String supplierName) {
		return this.supplierService.checkNameIsValid(supplierId, supplierName);
	}
	
	/**
	 * 获取是否国内
	 */
	@RequestMapping("selectIsForeignData")
	public @ResponseBody Map<Integer, String> selectIsForeignData() {
		return Supplier.getForeignStatusMap();
	}
	
	/**
	 * 获取地区
	 */
	@RequestMapping("selectAreaData")
	public @ResponseBody
	Map<String, String> selectAreaData(String value) {
		if (CommonUtil.trim(value).length() > 0) {
			boolean isForeign = String.valueOf(Supplier.IS_FOREIGN).equals(value);
			return TpcConstant.getAreaMap(isForeign);
		}
		return null;
	}
	
	/**
	 * 获取国家/省份
	 */
	@RequestMapping("selectAreaItemData")
	public @ResponseBody
	Map<String, String> selectAreaItemData(String value) {
		return TpcConstant.getAreaItemMap(value);
	}

}
