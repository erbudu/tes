package com.supporter.prj.cneec.tpc.use_seal.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.use_seal.entity.UseSealDetail;
import com.supporter.prj.cneec.tpc.use_seal.service.UseSealService;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**
 * @Title: UseSealDetailController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-10-24
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/useSealDetail")
public class UseSealDetailController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UseSealService useSealService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param useSeal
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, String sealId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.useSealService.getDetailGrid(user, jqGrid, sealId);
		return jqGrid;
	}

	/**
	 * 添加ModelAttribute
	 * @param detailId
	 * @param map
	 */
	@ModelAttribute
	public void getUseSeal(String detailId, Map<String, Object> map) {
		if (!StringUtils.isBlank(detailId)) {
			UseSealDetail useSealDetail = this.useSealService.getUseSealDetail(detailId);
			if (useSealDetail != null) {
				map.put("useSealDetail", useSealDetail);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param sealId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	UseSealDetail get(String detailId) {
		UseSealDetail useSealDetail = this.useSealService.getUseSealDetail(detailId);
		return useSealDetail;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param sealId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	UseSealDetail initEditOrViewPage(String detailId) {
		UseSealDetail useSealDetail = this.useSealService.initEditOrViewPageUseSealDetail(detailId);
		return useSealDetail;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param useSeal 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<UseSealDetail> saveOrUpdate(UseSealDetail useSealDetail) {
		Map<String, Object> valueMap = this.getRequestParameters();
		UseSealDetail entity = this.useSealService.saveOrUpdateUseSealDetail(useSealDetail, valueMap);
		return OperResult.succeed("use_seal_detail-saveSuccess", "保存成功", entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param detailIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String detailIds) {
		this.useSealService.deleteUseSealDetail(detailIds);
		return OperResult.succeed("use_seal_detail-deleteSuccess");
	}
	
	/**
	 * 获取用印业务
	 */
	@RequestMapping(value = "/selectUseSealBusinessData")
	public @ResponseBody
	List<CheckBoxVO> selectUseSealBusinessData(String key) {
		return this.useSealService.getUseSealBusinessList(CommonUtil.trim(key));
	}

	/**
	 * 是否已备案
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/selectFilingData")
	public @ResponseBody
	List<CheckBoxVO> selectFilingData(String key) {
		return this.useSealService.getFilingList(CommonUtil.trim(key));
	}

	/**
	 * 生成销售合同/协议编号（为避免编号重复，保存该明细）
	 * @param useSealDetail
	 * @return
	 */
	@RequestMapping("createdBusinessNo")
	public @ResponseBody
	UseSealDetail createdBusinessNo(UseSealDetail useSealDetail) {
		String projectId = CommonUtil.trim(this.getRequestPara("projectId"));
		String businessNo = this.useSealService.createdBusinessNo(projectId, useSealDetail);
		useSealDetail.setBusinessNo(businessNo);
		Map<String, Object> valueMap = this.getPropValues(UseSealDetail.class);
		UseSealDetail entity = this.useSealService.saveOrUpdateUseSealDetail(useSealDetail, valueMap);
		return entity;
	}

	/**
	 * 生成用印单明细
	 * @return
	 */
	@RequestMapping("createUseSealDetailByPre")
	public @ResponseBody
	UseSealDetail createUseSealDetailByPre() {
		Map<String, Object> valueMap = this.getRequestParameters();
		UseSealDetail useSealDetail = this.useSealService.createUseSealDetailByPre(valueMap);
		return useSealDetail;
	}

}