package com.supporter.prj.ud.udf.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.supporter.spring_mvc.AbstractController;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.doc_in.entity.DocIn;
import com.supporter.prj.ud.func.entity.UdFuncPage;
import com.supporter.prj.ud.func.entity.UdFuncPageCell;
import com.supporter.prj.ud.func.service.UdFuncPageCellService;
import com.supporter.prj.ud.func.util.UdFuncPageConstant;
import com.supporter.prj.ud.udf.entity.CommonType;
import com.supporter.prj.ud.udf.entity.Lable;
import com.supporter.prj.ud.udf.service.UdfService;

@Controller
@RequestMapping("oa/udf")
public class UdfController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private UdfService codeService;

	@RequestMapping("getLable")
	public @ResponseBody
	List<Lable> getLable(String pageId) {
		UserProfile user = this.getUserProfile();
		List<Lable> entitys = codeService.getLable(pageId, user);
		return entitys;
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getTreeGrid(HttpServletRequest request, JqGridReq jqGridReq,
			String pageId,String keyWord) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.codeService.getGrid(user, jqGrid, pageId,keyWord);
		return jqGrid;
	}

	@RequestMapping("createPage")
	public @ResponseBody
	String createPage(String pageId) {
		String page = "";
		page = this.codeService.createPage(pageId);
		return page;
	}

	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	Map<String, Object> initEditOrViewPage(String pageId, String recId) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> map = codeService.initEditOrViewPage(pageId, recId,
				user);
		return map;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param code
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<UdFuncPageCell> saveOrUpdate(HttpServletRequest request,
			CommonType coo) {
		UserProfile user = this.getUserProfile();
		this.codeService.saveOrUpdate(request, user, coo);
		return OperResult.succeed(UdFuncPageConstant.I18nKey.SAVE_SUCCESS,
				"保存成功", null);
	}

	@RequestMapping("createViewPage")
	public @ResponseBody
	List<UdFuncPageCell> createViewPage(String pageId) {
		UserProfile user = this.getUserProfile();
		List<UdFuncPageCell> entitys = codeService.createViewPage(pageId, user);
		return entitys;
	}
//删除
	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult batchDel(String recIds,String pageId) {
		UserProfile user = this.getUserProfile();
		this.codeService.delete(user, recIds,pageId);
		return OperResult.succeed(UdFuncPageConstant.I18nKey.DELETE_SUCCESS,
				null, null);
	}
}
