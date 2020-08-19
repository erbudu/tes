package com.supporter.prj.cneec.tpc.tariff_vat_payment.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.tariff_vat_payment.entity.TariffVatPayment;
import com.supporter.prj.cneec.tpc.tariff_vat_payment.service.TariffVatPaymentService;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;
import com.supporter.util.HttpUtil;

/**
 * @Title: TariffVatPaymentController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-12-20
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/tariffVatPayment")
public class TariffVatPaymentController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private TariffVatPaymentService tariffVatPaymentService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param tariffVatPayment
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, TariffVatPayment tariffVatPayment) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.tariffVatPaymentService.getGrid(user, jqGrid, tariffVatPayment);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param paymentId
	 * @param map
	 */
	@ModelAttribute
	public void getTariffVatPayment(String paymentId, Map<String, Object> map) {
		if (!StringUtils.isBlank(paymentId)) {
			TariffVatPayment tariffVatPayment = this.tariffVatPaymentService.get(paymentId);
			if (tariffVatPayment != null) {
				map.put("tariffVatPayment", tariffVatPayment);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param paymentId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	TariffVatPayment get(String paymentId) {
		TariffVatPayment tariffVatPayment = this.tariffVatPaymentService.get(paymentId);
		return tariffVatPayment;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param paymentId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	TariffVatPayment initEditOrViewPage(String paymentId) {
		UserProfile user = this.getUserProfile();
		TariffVatPayment tariffVatPayment = this.tariffVatPaymentService.initEditOrViewPage(paymentId, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.tariffVatPaymentService.getAuthCanExecute(user, tariffVatPayment);
		}
		return tariffVatPayment;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param tariffVatPayment 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<TariffVatPayment> saveOrUpdate(TariffVatPayment tariffVatPayment) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(TariffVatPayment.class);
		TariffVatPayment entity = this.tariffVatPaymentService.saveOrUpdate(user, tariffVatPayment, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}

	/**
	 * 提交数据
	 * @param tariffVatPayment
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<TariffVatPayment> commit(TariffVatPayment tariffVatPayment) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(TariffVatPayment.class);
		TariffVatPayment entity = this.tariffVatPaymentService.commit(user, tariffVatPayment, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS, "提交成功", entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param paymentIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String paymentIds) {
		UserProfile user = this.getUserProfile();
		this.tariffVatPaymentService.delete(user, paymentIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData() {
		return TariffVatPayment.getSwfStatusMap();
	}

	/**
	 * 付款类型
	 * @return
	 */
	@RequestMapping(value = "/selectPaymentTypeData")
	public @ResponseBody
	List<CheckBoxVO> selectPaymentTypeData(String key) {
		return this.tariffVatPaymentService.getPaymentTypeList(CommonUtil.trim(key));
	}

	/**
	 * 开户行所在省
	 * @return
	 */
	@RequestMapping(value = "/selectRemitProvinceData")
	public @ResponseBody
	Map<String, String> selectRemitProvinceData() {
		return TpcConstant.getRemitProvinceMap();
	}

	/**
	 * 开户行所在市
	 * @param value 级联选择时上级select传递过来的参数名必须为value
	 * @return
	 */
	@RequestMapping(value = "/selectRemitCityData")
	public @ResponseBody
	Map<String, String> selectRemitCityData(String value) {
		return TpcConstant.getRemitCityMap(CommonUtil.trim(value));
	}

	/**
	 * 汇款用途
	 * @return
	 */
	@RequestMapping("getRemittancePurposeData")
	@ResponseBody
	public Map<String, String> getRemittancePurposeData() {
		Map<String, String> map = this.tariffVatPaymentService.getRemittancePurposeData();
		return map;
	}
	
	/**
	 * 设置打印次数
	 * @param response
	 */
	@RequestMapping("setPrintCount")
	public void setPrintCount(HttpServletResponse response, String paymentId) {
		TariffVatPayment payment = this.tariffVatPaymentService.setPrintCount(paymentId);
		HttpUtil.write(response, payment.getPrintCount()+"");
	}

}