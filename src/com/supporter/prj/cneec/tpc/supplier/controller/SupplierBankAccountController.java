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
import com.supporter.prj.cneec.tpc.supplier.entity.SupplierBankAccount;
import com.supporter.prj.cneec.tpc.supplier.service.SupplierBankAccountService;
import com.supporter.prj.cneec.tpc.supplier.service.SupplierService;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.log.controller.AbstractController;
import com.supporter.util.CommonUtil;

@Controller
@RequestMapping("tpc/supplierBankAccount")
public class SupplierBankAccountController extends AbstractController {
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private SupplierBankAccountService supplierBankAccountService;
	
	/**
	 * 返回列表
	 * 分页表格展示数据
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, String supplierId, String pagetype) throws Exception{
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.supplierService.getBankAccountGrid(user, jqGrid, supplierId, pagetype);
		return jqGrid;
	}
	
	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param supplierId
	 * @param map
	 */
	@ModelAttribute
	public void getSupplier(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
		if (StringUtils.isNotBlank(id)) {
			SupplierBankAccount supplierBankAccount = this.supplierBankAccountService.get(id);
			if (supplierBankAccount != null) {
				map.put("supplierBankAccount", supplierBankAccount);
			}
		}
	}
	
	/**
	 * 进入新建、编辑或查看页面时加载的信息
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody SupplierBankAccount initEditOrViewPage(String id,String supplierId){
		SupplierBankAccount entity = supplierBankAccountService.initEditOrViewPage(id, supplierId, this.getUserProfile());
		return entity;
	}
	
	/**
	 * 保存或更新数据
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<SupplierBankAccount> saveOrUpdate(SupplierBankAccount bankAccount){
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(Supplier.class);
		SupplierBankAccount entity = this.supplierBankAccountService.saveOrUpdate(user, bankAccount, valueMap);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping("deleteBanAccount")
	public @ResponseBody OperResult batchDel(String id){
		UserProfile user = this.getUserProfile();
		this.supplierBankAccountService.delete(user, id);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 获取开户行所在省
	 */
	@RequestMapping("getRemitProvinces")
	public @ResponseBody
	Map<String, String> selectRemitProvinces(String value) {
		return TpcConstant.getRemitProvinceMap();
	}

	/**
	 * 获取开户行所在市
	 */
	@RequestMapping("getRemitCitys")
	public @ResponseBody
	Map<String, String> selectRemitCitys(String value) {
		return TpcConstant.getRemitCityMap(CommonUtil.trim(value));
	}
}
