package com.supporter.prj.linkworks.oa.consignment.controller;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.abroadPublicity.entity.AbroadPublicity;
import com.supporter.prj.linkworks.oa.consignment.entity.Consignment;
import com.supporter.prj.linkworks.oa.consignment.service.ConsignmentService;
import com.supporter.spring_mvc.AbstractController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"oa/consignment"})
public class ConsignmentController
  extends AbstractController
{
  private static final long serialVersionUID = 1L;
  public static final int OA_ID = 1;
  public static final int PC_ID = 2;
  public static final int AM_ID = 3;
  public static final int PL_ID = 4;
  public static final int BM_ID = 5;
  public static final int DM_ID = 6;
  public static final int CM_ID = 7;
  public static final int OLD_OA_ID = 8;
  @Autowired
  private ConsignmentService consignmentService;
  
  @RequestMapping({"getGrid"})
  @ResponseBody
  public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, AbroadPublicity abroadPublicity, String attr)
    throws Exception
  {
    UserProfile user = getUserProfile();
    
    JqGrid jqGrid = jqGridReq.initJqGrid(request);
    this.consignmentService.getGrid(user, jqGrid, attr);
    return jqGrid;
  }
  
  @RequestMapping({"initEditOrViewPage"})
  @ResponseBody
  public Consignment initEditOrViewPage(String consignmentId)
  {
    Consignment entity = this.consignmentService.initEditOrViewPage(
      consignmentId, getUserProfile());
    return entity;
  }
  
  @RequestMapping({"saveOrUpdate"})
  @ResponseBody
  public OperResult<Consignment> saveOrUpdate(Consignment consignment)
  {
    UserProfile user = getUserProfile();
    Map<String, Object> valueMap = getPropValues(Consignment.class);
    
    Consignment entity = this.consignmentService.saveOrUpdate(user, 
      consignment, valueMap);
    return OperResult.succeed("saveSuccess", null, entity);
  }
  
  @RequestMapping({"batchDel"})
  @ResponseBody
  public OperResult batchDel(String consignIds)
  {
    UserProfile user = getUserProfile();
    this.consignmentService.delete(user, consignIds);
    return OperResult.succeed("deleteSuccess");
  }
  
  @RequestMapping({"batchStop"})
  @ResponseBody
  public OperResult batchStop(String consignmentIds)
  {
    UserProfile user = getUserProfile();
    this.consignmentService.stop(user, consignmentIds);
    return OperResult.succeed("stopSuccess");
  }
  
  @RequestMapping({"/getCheckBoxList"})
  @ResponseBody
  public List<Map<String, Object>> getCheckBoxList(HttpServletRequest request, JqGridReq jqGridReq, Integer id)
  {
    UserProfile user = getUserProfile();
    jqGridReq.setRows(9999);
    JqGrid jqGrid = jqGridReq.initJqGrid(request);
    Map<String, Object> params = getPropValues(Consignment.class);
    List<Map<String, Object>> list = new ArrayList();
    switch (id.intValue())
    {
    case 1: 
      list = this.consignmentService.getCheckBoxList(
        jqGrid, params, user);
      break;
    case 2: 
      list = this.consignmentService.getCheckBoxListPc(
        jqGrid, params, user);
      break;
    case 3: 
      list = this.consignmentService.getCheckBoxListAm(
        jqGrid, params, user);
      break;
    case 4: 
      list = this.consignmentService.getCheckBoxListPl(
        jqGrid, params, user);
      break;
    case 5: 
      list = this.consignmentService.getCheckBoxListBm(
        jqGrid, params, user);
      break;
    case 6: 
      list = this.consignmentService.getCheckBoxListDm(
        jqGrid, params, user);
      break;
    case 7: 
      list = this.consignmentService.getCheckBoxListCm(
        jqGrid, params, user);
      break;
    case 8: 
      list = this.consignmentService.getCheckBoxListOa(
        jqGrid, params, user);
      break;
    }
    return list;
  }
  
  @RequestMapping({"saveConsignment"})
  @ResponseBody
  public OperResult<Consignment> saveConsignment(Consignment consignment)
  {
    UserProfile user = getUserProfile();
    Consignment entity = this.consignmentService.saveConsignment(
      consignment, user);
    return OperResult.succeed("saveSuccess", null, entity);
  }
  
  @RequestMapping({"initConsignment"})
  @ResponseBody
  public Consignment initConsignment(String consignId)
  {
    Consignment entity = this.consignmentService.initConsignment(consignId);
    return entity;
  }
  
  @RequestMapping({"initConsignation"})
  @ResponseBody
  public Consignment initConsignation(String consignmentId)
  {
    Consignment entity = this.consignmentService.initConsignmentById(consignmentId);
    return entity;
  }


	/**
	 * 获取当前登录人
	 * 
	 * @return
	 */
	@RequestMapping("getLoginId")
	public @ResponseBody
	String getLoginId() {
		UserProfile user = this.getUserProfile();
		String loginId = user.getPersonId();
		return loginId;
	}
	
	
	

	/**
	 * 验证当前登录人是否有权限修改和编辑
	 * 
	 * @return
	 */
	@RequestMapping("checkAuth")
	public @ResponseBody
	String checkAuth(String consignerId) {
		UserProfile user = this.getUserProfile();
		String loginId = user.getPersonId();
		if(loginId.equals(consignerId)||loginId.equals("1")||loginId.equals("10101")){
			return "true";
		}else{
			return "false";
			
		}
	}
}
