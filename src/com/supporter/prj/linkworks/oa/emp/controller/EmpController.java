package com.supporter.prj.linkworks.oa.emp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.eip.dept.entity.DeptTreeVO;
import com.supporter.prj.eip.dept.entity.Page;
import com.supporter.prj.eip.dept.service.ICompanyService;
import com.supporter.prj.eip.dept.util.ConvertUtils;
import com.supporter.prj.eip.dept.util.Params;
import com.supporter.prj.linkworks.oa.emp.service.DeptEmpService;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: Controller
 * @Description: 物资信息设置.
 * @author yanbingchao
 * @date 2017-3-27 15:25:34
 * @version V1.0
 * 
 */

@Controller("oaEmpController")
@RequestMapping("oa/emp_dept")
public class EmpController extends AbstractController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ICompanyService eipCompanyService;
	@Autowired
	private DeptEmpService empService;
	@ResponseBody
	@RequestMapping( { "/getTreeData" })
	public Page<DeptTreeVO> getTreeData() throws IOException {
		Map<String, Object> paramMap = getRequestParameters();
		Params sysParamUtil = new Params(paramMap);
		int pageNumber = sysParamUtil.getParaToInt("page", Integer.valueOf(1))
				.intValue();
		List<DeptTreeVO> list = this.eipCompanyService.getTreeData(paramMap);	
		List<DeptTreeVO> list2 = this.empService.getTreeData(list);
		Page<DeptTreeVO> page = new Page(pageNumber, 2147483647, Long
				.valueOf(list2.size()), "", "");
		ConvertUtils.setParent(list2);
		page.setRows(list2);
		return page;
	}
}
