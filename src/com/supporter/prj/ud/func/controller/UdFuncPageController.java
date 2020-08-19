package com.supporter.prj.ud.func.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ud.func.entity.UdFuncPage;
import com.supporter.prj.ud.func.service.UdFuncPageService;
import com.supporter.prj.ud.func.util.UdFuncPageConstant;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: Controller
 * @Description: 物资信息设置.
 * @author yanbingchao
 * @date 2017-3-27 15:25:34
 * @version V1.0
 * 
 */

@Controller
@RequestMapping("oa/ud_func")
public class UdFuncPageController extends AbstractController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private UdFuncPageService codeService;

	/**
	 * 根据主键获取.
	 * 
	 * @param pageId
	 *            主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody
	UdFuncPage get(String pageId) {
		UdFuncPage code = codeService.get(pageId);
		return code;

	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param cideId
	 *            主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	UdFuncPage initEditOrViewPage(String pageId) {
		UserProfile user = this.getUserProfile();
		UdFuncPage entity = codeService.initEditOrViewPage(
				pageId,user);
		return entity;
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
			UdFuncPage code) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.codeService.getGrid(user, jqGrid, code);
		return jqGrid;
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
	OperResult<UdFuncPage> saveOrUpdate(UdFuncPage code) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this
				.getPropValues(UdFuncPage.class);
		UdFuncPage entity = this.codeService.saveOrUpdate(user, code,
				valueMap);
		return OperResult.succeed(
				UdFuncPageConstant.I18nKey.SAVE_SUCCESS, "保存成功",
				entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param pageIds
	 *            主键集合，多个以逗号分隔
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult batchDel(String pageIds) {
		UserProfile user = this.getUserProfile();
		this.codeService.delete(user, pageIds);
		return OperResult
				.succeed(UdFuncPageConstant.I18nKey.DELETE_SUCCESS,
						null, null);
	}

}
