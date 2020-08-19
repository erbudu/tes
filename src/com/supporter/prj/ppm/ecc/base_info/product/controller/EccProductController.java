package com.supporter.prj.ppm.ecc.base_info.product.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.ecc.base_info.product.service.EccProductService;
import com.supporter.prj.ppm.ecc.base_info.product.entity.EccProduct;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 出口管制产品.
 * @author YAN
 * @date 2019-08-19 15:22:21
 * @version V1.0
 */
@Controller
@RequestMapping("ecc/product/eccProduct")
public class EccProductController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EccProductService eccProductService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param productId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody EccProduct initEditOrViewPage(String productId) {
		EccProduct entity = eccProductService.initEditOrViewPage(productId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, EccProduct eccProduct) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.eccProductService.getGrid(user, jqGrid, eccProduct);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param eccProduct 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(EccProduct eccProduct) {
		UserProfile user = this.getUserProfile();
		EccProduct entity = this.eccProductService.saveOrUpdate(user, eccProduct);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param productIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String productIds) {
		UserProfile user = this.getUserProfile();
		this.eccProductService.delete(user, productIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param productId
	 * @param productName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String productId,String productName) {
		return this.eccProductService.nameIsValid(productId, productName);
	}

}
