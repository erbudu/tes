package com.supporter.prj.ppm.pay_apply.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.module.constant.ModuleConstant;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.pay_apply.entity.PayApply;
import com.supporter.prj.ppm.pay_apply.service.PayApplyService;
import com.supporter.spring_mvc.AbstractController;


/**
 * @Title: payApplyController
 * @Description: 控制器类
 * @author: 
 * @date: 2018-07-13
 * @version: V1.0
 */
@Controller
@RequestMapping("ppm/payApply")
public class PayApplyController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PayApplyService payApplyService;

	/**
	 * 获取支付申请列表
	 * @param jqGrid 列表
	 * @param PayApply 实体对象
	 * @param user 当前登录用户
	 * @return 支付申请列表
	 */
	@RequestMapping("getGrid")
	@ResponseBody
	public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, PayApply payApply) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		payApplyService.getGrid(jqGrid, payApply, user);
		return jqGrid;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param id
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	PayApply initEditOrViewPage(String id,String prjId ) {
		UserProfile user = this.getUserProfile();
		PayApply payApply = this.payApplyService.initEditOrViewPage(id, user,prjId);
		return payApply;
	}

	/**
	 * 保存或更新
	 * @param PayApply 实体对象
	 * @return 操作结果
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public OperResult<PayApply> saveOrUpdate(PayApply payApply) {
		UserProfile user = this.getUserProfile();
		PayApply entity = payApplyService.saveOrUpdate(payApply, user);
		return OperResult.succeed("保存成功", "保存成功", entity);
	}
	
	/**
	 * 删除
	 * @param id 主键
	 * @return	操作结果
	 */
	@RequestMapping("/batchDel")
	@ResponseBody
	public OperResult<?> batchDel(String id) {
		this.payApplyService.delete(id);
		return OperResult.succeed(ModuleConstant.I18nKey.DELETE_SUCCESS, null, null);
	}
	

	/**
	 * 获取币别码表
	 */
	@RequestMapping("getCurrencyTable")
	@ResponseBody
	public Map<String, String> getCurrencyTable() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems("PPM_CURRENCY");
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			if (item == null) {
				continue;
			}
			map.put(item.getItemId(), item.getItemValue());
		}
		return map;
	}

	/**
	 * 获取资金用途码表
	 */
	@RequestMapping("getFundUseTable")
	@ResponseBody
	public Map<String, String> getFundUseTable() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems("PPM_FUNDUSE");
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			if (item == null) {
				continue;
			}
			map.put(item.getItemId(), item.getItemValue());
		}
		return map;
	}

	/**
	 * 设置提交人等信息
	 * @param id 支付申请主键
	 */
	@RequestMapping("/valid")
	@ResponseBody
	public void vaild(String id) {
		UserProfile user = this.getUserProfile();
		payApplyService.valid(id, user);
	}

	/**
	 * 项目是否满足款项支付条件
	 * @param prjId 项目id
	 * @return 检查结果
	 */
	@RequestMapping("/checkPrjStatus")
	@ResponseBody
	public boolean checkPrjStatus(String prjId) {
		boolean result = payApplyService.checkPrjStatus(prjId);
		return result;
	}

}