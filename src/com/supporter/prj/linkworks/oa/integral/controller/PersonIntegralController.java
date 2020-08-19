package com.supporter.prj.linkworks.oa.integral.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.integral.constants.IntegralAuthConstant;
import com.supporter.prj.linkworks.oa.integral.entity.PersonIntegral;
import com.supporter.prj.linkworks.oa.integral.service.PersonIntegralService;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: Controller
 * @Description: 报告.
 * @author liyinfeng
 * @date 2016-12-06 15:25:34
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("oa/person_integral")
public class PersonIntegralController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private PersonIntegralService personIntegralService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * 
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@AuthCheck(module = IntegralAuthConstant.MODULE_ID, oper = IntegralAuthConstant.AUTH_OPER_NAME_PAGESHOW, failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq,
			PersonIntegral entity) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.personIntegralService.getGrid(user, jqGrid, entity);
		return jqGrid;
	}

	// 以上为列表中方法

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param PersonIntegralId
	 *            主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	PersonIntegral initEditOrViewPage(String personIntegralId) {
		PersonIntegral entity = personIntegralService.initEditOrViewPage(
				personIntegralId, this.getUserProfile());
		return entity;
	}

	@RequestMapping("getUserInfomation")
	public @ResponseBody
	UserProfile getUserInfomation() {
		return this.getUserProfile();
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param PersonIntegral
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@AuthCheck(module = IntegralAuthConstant.MODULE_ID, oper = IntegralAuthConstant.AUTH_OPER_NAME_PAGESHOW, failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<PersonIntegral> saveOrUpdate(PersonIntegral entity) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(PersonIntegral.class);
		PersonIntegral re = this.personIntegralService.saveOrUpdate(user,
				entity, valueMap);
		return OperResult.succeed("saveSuccess", null, re);
	}

	@RequestMapping("getLastYearIntegral")
	public @ResponseBody
	Integer getLastYearIntegral(String personIntegralId, String personId,
			Integer year) {
		Integer integral = personIntegralService.getLastYearIntegral(
				personIntegralId, personId, year);
		return integral;
	}

	/**
	 * 删除操作
	 * 
	 * @param PersonIntegralIds
	 *            主键集合，多个以逗号分隔
	 * @return
	 */
	@AuthCheck(module = IntegralAuthConstant.MODULE_ID, oper = IntegralAuthConstant.AUTH_OPER_NAME_PAGESHOW, failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult batchDel(String personIntegralIds) {
		UserProfile user = this.getUserProfile();
		this.personIntegralService.delete(user, personIntegralIds);
		return OperResult.succeed("deleteSuccess");
	}

	// 获取年码表
	@RequestMapping("getYearCodeTable")
	public @ResponseBody
	Map<String, String> getYearCodeTable() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		/*
		 * List<IComCodeTableItem> list = EIPService.getComCodeTableService()
		 * .getCodeTableItems("INTEGRAL_YEAR"); for (IComCodeTableItem item :
		 * list) { map.put(item.getItemId(), item.getDisplayName()); }
		 */
		map.put("2016", "2016");
		map.put("2017", "2017");
		map.put("2018", "2018");
		return map;
	}

	@RequestMapping("downExcel")
	public @ResponseBody
	String downExcel(HttpServletRequest request, HttpServletResponse response,
			PersonIntegral pn) throws Exception {
		OutputStream os = null;
		UserProfile user = this.getUserProfile();
		try {
			String fileName = "";
			if (pn != null) {
				String year = pn.getYear();
				if (StringUtils.isNotBlank(year)) {
					fileName = year + "年人员积分.xls";
				} else {
					return null;
				}
			}
			HSSFWorkbook wb = this.personIntegralService.getWorkbook(pn, user);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ URLEncoder.encode(fileName, "utf-8"));
			os = response.getOutputStream();
			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			os.flush();
			os.close();
		}
		return null;
	}
}
