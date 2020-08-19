package com.supporter.prj.cneec.tpc.deliver.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.deliver.entity.Deliver;
import com.supporter.prj.cneec.tpc.deliver.entity.DeliverInformation;
import com.supporter.prj.cneec.tpc.deliver.service.DeliverService;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: DeliverController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-12-20
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/deliver")
public class DeliverController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private DeliverService deliverService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param deliver
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, Deliver deliver) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.deliverService.getGrid(user, jqGrid, deliver);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param deliverId
	 * @param map
	 */
	@ModelAttribute
	public void getDeliver(String deliverId, Map<String, Object> map) {
		if (!StringUtils.isBlank(deliverId)) {
			Deliver deliver = this.deliverService.get(deliverId);
			if (deliver != null) {
				map.put("deliver", deliver);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param deliverId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	Deliver get(String deliverId) {
		Deliver deliver = this.deliverService.get(deliverId);
		return deliver;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param deliverId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	Deliver initEditOrViewPage(String deliverId) {
		UserProfile user = this.getUserProfile();
		Deliver deliver = this.deliverService.initEditOrViewPage(deliverId, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.deliverService.getAuthCanExecute(user, deliver);
		}
		return deliver;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param deliver 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<Deliver> saveOrUpdate(Deliver deliver) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(Deliver.class);
		Deliver entity = this.deliverService.saveOrUpdate(user, deliver, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}

	/**
	 * 提交数据
	 * @param deliver
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<Deliver> commit(Deliver deliver) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(Deliver.class);
		Deliver entity = this.deliverService.commit(user, deliver, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS, "提交成功", entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param deliverIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String deliverIds) {
		UserProfile user = this.getUserProfile();
		this.deliverService.delete(user, deliverIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData() {
		return Deliver.getSwfStatusMap();
	}

	/**
	 * 获取支付明细信息
	 * @param request
	 * @param jqGridReq
	 * @param deliverId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getInfoGrid")
	public @ResponseBody
	JqGrid getInfoGrid(HttpServletRequest request, JqGridReq jqGridReq, String deliverId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.deliverService.getInfoGrid(user, jqGrid, deliverId);
		return jqGrid;
	}

	/**
	 * 获取支付明细并添加一个空的明细
	 * @param deliverId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getInputInfoMap")
	public @ResponseBody
	Map<String, Object> getInputInfoMap(String deliverId) throws Exception {
		return this.deliverService.getInfoMap(deliverId, true);
	}

	/**
	 * 获取支付明细并添加一个空的明细
	 * @param deliverId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getInfoMap")
	public @ResponseBody
	Map<String, Object> getInfoMap(String deliverId) throws Exception {
		return this.deliverService.getInfoMap(deliverId, false);
	}

	@RequestMapping("initEditOrViewInfoPage")
	public @ResponseBody
	DeliverInformation initEditOrViewInfoPage(String informationId) {
		DeliverInformation deliverInformation = this.deliverService.initEditOrViewInfoPage(informationId);
		return deliverInformation;
	}

	@RequestMapping("saveOrUpdateInfo")
	public @ResponseBody
	OperResult<DeliverInformation> saveOrUpdateInfo(DeliverInformation deliverInformation) {
		DeliverInformation entity = this.deliverService.saveOrUpdateInfo(deliverInformation, null);
		return OperResult.succeed("deliver_info-saveSuccess", "保存成功", entity);
	}

	@RequestMapping("batchDelInfo")
	public @ResponseBody
	OperResult<?> batchDelInfo(String informationIds) {
		this.deliverService.deleteInfo(informationIds);
		return OperResult.succeed("deliver_info-saveSuccess");
	}

}