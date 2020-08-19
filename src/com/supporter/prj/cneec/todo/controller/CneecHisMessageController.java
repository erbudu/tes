package com.supporter.prj.cneec.todo.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.emp_mapping.service.EmpMappingService;
import com.supporter.prj.cneec.todo.service.CneecHistTodoService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**
 * 已办事宜.
 * @author liug
 *
 */
@Controller
@RequestMapping({"/cneec/hisTodo"})
public class CneecHisMessageController extends AbstractController {
	  private static final long serialVersionUID = 1L;
	  
	  @Autowired
	  private CneecHistTodoService histTodoService;
	  @Autowired
	  private EmpMappingService empMappingService;

	  @SuppressWarnings("unchecked")
	  @RequestMapping({"/getHisTodos"})
	  @ResponseBody
	  public JqGrid getHisTodos(JqGridReq jqGridReq,String moduleId) throws Exception {
		  JqGrid jqGrid = jqGridReq.initJqGrid(this.getRequest());
		  
		  Map < String, Object > params = new HashMap < String, Object >();
		  String personId = empMappingService.getEmpId(CommonUtil.trim(this.getUserProfile().getPersonId()));
		  //EIP6.0的人员ID
		  params.put("Eip6PersonId",this.getUserProfile().getPersonId());
		  //老OA的personID
		  params.put("OldpersonId", personId);
		  params.put("moduleId", moduleId);
		  params.put("msgTitle", this.getRequestPara("msgTitle"));
		  //params.put("webappName", this.getRequestPara("webappName"));
		  params.put("completeDateFrom", this.getRequestPara("completeDateFrom"));
		  params.put("completeDateTo", this.getRequestPara("completeDateTo"));
		  
		  histTodoService.getHistTodos(jqGrid, params);
		  return jqGrid;
	  }
}
