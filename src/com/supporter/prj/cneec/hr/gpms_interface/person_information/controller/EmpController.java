package com.supporter.prj.cneec.hr.gpms_interface.person_information.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.hr.gpms_interface.person_information.service.DeptEmpService;
import com.supporter.prj.eip.dept.entity.DeptTreeVO;
import com.supporter.prj.eip.dept.entity.Page;
import com.supporter.prj.eip.dept.service.ICompanyService;
import com.supporter.prj.eip.dept.util.ConvertUtils;
import com.supporter.prj.eip.dept.util.Params;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.linkworks.oa.abroad.constants.AbroadAuthConstant;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: Controller
 * @Description: 系统人员.
 * @author yanbingchao
 * @date 2017-3-27 15:25:34
 * @version V1.0
 * 
 */

@Controller("hrEmpController")
@RequestMapping("eip/hr/person_information")
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
		List<DeptTreeVO> newList = new ArrayList<DeptTreeVO>();
		String authHql = EIPService.getAuthorityService().getHqlFilter(this.getUserProfile(),
				"PERSONINFO",
				"TREEAUTH");
		if(authHql.indexOf("1=1")!=-1){
			newList.addAll(list);
		}else{
			for (DeptTreeVO deptTreeVO : list) {
				if(authHql.indexOf("'"+deptTreeVO.getNodeId()+"'")!=-1){					
					newList.add(deptTreeVO);
				}
			}
		}
		List<DeptTreeVO> list2 = newList;		
			list2 = this.empService.getTreeData(newList,sysParamUtil);
		Page<DeptTreeVO> page = new Page(pageNumber, 2147483647, Long
				.valueOf(list2.size()), "", "");
		ConvertUtils.setParent(list2);
		page.setRows(list2);
		return page;
	}
}
