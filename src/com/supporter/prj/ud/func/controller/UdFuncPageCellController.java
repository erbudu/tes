package com.supporter.prj.ud.func.controller;

import java.io.IOException;
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
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ud.control.InputControlType;
import com.supporter.prj.ud.func.entity.UdFuncPage;
import com.supporter.prj.ud.func.entity.UdFuncPageCell;
import com.supporter.prj.ud.func.service.UdFuncPageCellService;
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
@RequestMapping("oa/ud_func_cell")
public class UdFuncPageCellController extends AbstractController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private UdFuncPageCellService codeService;

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
	UdFuncPageCell get(String pageId) {
		UdFuncPageCell code = codeService.get(pageId);
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
	List<UdFuncPageCell> initEditOrViewPage(String pageId) {
		UserProfile user = this.getUserProfile();
		List<UdFuncPageCell> entitys = codeService.initEditOrViewPage(pageId,
				user);
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
			UdFuncPageCell code) throws Exception {
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
	OperResult<UdFuncPageCell> saveOrUpdate(UdFuncPage list) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(UdFuncPageCell.class);
		this.codeService.saveOrUpdate(user, list, valueMap);
		return OperResult.succeed(UdFuncPageConstant.I18nKey.SAVE_SUCCESS,
				"保存成功", null);
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
		return OperResult.succeed(UdFuncPageConstant.I18nKey.DELETE_SUCCESS,
				null, null);
	}

	@RequestMapping("getTypeCodeTable")
	public @ResponseBody
	Map<String, String> getTypeCodeTable() {
		
		/*
		 * List <IComCodeTableItem> list =
		 * EIPService.getComCodeTableService().getCodeTableItems("UNIT");
		 * for(IComCodeTableItem item : list){ map.put(item.getItemId(),
		 * item.getDisplayName()); }
		 */
/*		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("普通输入", "普通输入");
		map.put("数字输入", "数字输入");
		map.put("单选", "单选");
		map.put("日期选择", "日期选择");
		map.put("文本框", "文本框");
		map.put("下拉列表", "下拉列表");
		map.put("选择员工", "选择员工");
		map.put("选择部门", "选择部门");*/
		return InputControlType.getMap();
	}
	@RequestMapping("getEmpGroupCodeTable")
	public @ResponseBody
	Map<String, String> getEmpGroupCodeTable() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		/*
		 * List <IComCodeTableItem> list =
		 * EIPService.getComCodeTableService().getCodeTableItems("UNIT");
		 * for(IComCodeTableItem item : list){ map.put(item.getItemId(),
		 * item.getDisplayName()); }
		 */
		map.put("1", "部门一");
		map.put("2", "部门2");
		map.put("3", "部门3");
		map.put("4", "部门4");
		
		return map;
	}
}
