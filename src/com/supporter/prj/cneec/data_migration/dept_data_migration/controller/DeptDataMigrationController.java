package com.supporter.prj.cneec.data_migration.dept_data_migration.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.data_migration.dept_data_migration.entity.DeptDataMigration;
import com.supporter.prj.cneec.data_migration.dept_data_migration.service.DeptDataMigrationService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.invitation_f.util.InvitationConstant;
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
@RequestMapping("oa/dept_data_migration")
public class DeptDataMigrationController extends AbstractController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private DeptDataMigrationService codeService;

	/**
	 * 根据主键获取.
	 * 
	 * @param deptDataMigrationId
	 *            主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody
	DeptDataMigration get(String deptDataMigrationId) {
		DeptDataMigration code = codeService.get(deptDataMigrationId);
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
	DeptDataMigration initEditOrViewPage(String deptDataMigrationId,String docClassify) {
		UserProfile user = this.getUserProfile();
		DeptDataMigration entity = codeService.initEditOrViewPage(deptDataMigrationId,docClassify, user);
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
			DeptDataMigration code) throws Exception {
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
	OperResult<DeptDataMigration> saveOrUpdate(DeptDataMigration code) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(DeptDataMigration.class);
		DeptDataMigration entity = this.codeService.saveOrUpdate(user, code, valueMap);
		return OperResult.succeed(InvitationConstant.I18nKey.SAVE_SUCCESS, "保存成功",
				entity);
	}
	/**
	 * 删除操作
	 * 
	 * @param deptDataMigrationIds
	 *            主键集合，多个以逗号分隔
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult batchDel(String deptDataMigrationIds) {
		UserProfile user = this.getUserProfile();
		this.codeService.delete(user, deptDataMigrationIds);
		return OperResult.succeed(InvitationConstant.I18nKey.DELETE_SUCCESS, null,
				null);
	}
}
