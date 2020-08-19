package com.supporter.prj.ppm.poa.icc.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.log.controller.AbstractController;
import com.supporter.prj.pm.util.OperResultConstant;
import com.supporter.prj.ppm.poa.icc.entity.Coordination;
import com.supporter.prj.ppm.poa.icc.service.CoordinationExport;
import com.supporter.prj.ppm.poa.icc.service.CoordinationService;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReview;
import com.supporter.prj.ppm.prj_org.base_info.common.PrjInfo;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.util.CommonUtil;


@Controller
@RequestMapping("/ppm/poa/icc")
public class CoordinationController  extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private CoordinationService  coordinationService;
	@Autowired
	private CoordinationExport coordinationExport;
	@Autowired
	private PrjInfo PrjInfo;

	@RequestMapping("/getGrid")
	@ResponseBody
	public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, Coordination coordination,String prjId) throws Exception {
		UserProfile user = getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		List list = this.coordinationService.getGrid(user, jqGrid, coordination,prjId);
		//Map<String, String> map = Coordination.getCorType();
		
		jqGrid.setRows(list);
		return jqGrid;
	}
	@RequestMapping("/getPrjById")
	public @ResponseBody Prj getPrjById(HttpServletRequest request, Prj prj,String prjId)
			throws Exception {
		UserProfile user = this.getUserProfile();
		//String prjName = this.getRequestPara("prjName");
		//JqGrid jqGrid = jqGridReq.initJqGrid(request);
		//this.PrjInfo.getGridToWidgetRank(user, jqGrid, prj, "查询参数");
		Prj prjById=this.PrjInfo.PrjInformation(prjId);
		return prjById;
	}
	/**
	 * 查看页面时初始化数据
	 * 
	 * */
	
	@RequestMapping("/initEditOrViewPage")
	@ResponseBody
	public Coordination initEditOrViewPage(String coordinationId) throws ParseException {
		UserProfile user = this.getUserProfile();
		if(coordinationId.isEmpty()) {
			return null;
		}
		else {
		Coordination coordination = this.coordinationService.getById(coordinationId, user);
		return coordination;}
	}

	@RequestMapping("/initData")
	@ResponseBody
	public Coordination initData(String iccId, String prjId) {
		UserProfile user = this.getUserProfile();
		Coordination entity = coordinationService.initData(iccId, prjId, user);
		return entity;
	}
	/*
	 * 检查是否有流程审批中的记录
	 * */
	@RequestMapping("/checkInProc")
	@ResponseBody
	public Coordination checkInProc( String prjId) {
		UserProfile user = this.getUserProfile();
		Coordination entity = coordinationService.checkInProc( prjId, user);
		return entity;
	}
	/**
	 * 新增数据记录
	 * */
	@RequestMapping("/saveOrUpdate")
	public @ResponseBody
	OperResult<Coordination> saveOrUpdate (Coordination coordination) throws ParseException {
		UserProfile user = this.getUserProfile();
		Coordination entity = this.coordinationService.saveOrUpdate(coordination, user);
	    return OperResult.succeed("saveSuccess", null, entity);
		
		
	}
	@RequestMapping("/edit")
	public @ResponseBody
	OperResult<Coordination> edit (Coordination coordination) throws ParseException {
		UserProfile user = this.getUserProfile();
		Coordination entity = this.coordinationService.edit(coordination, user);
	    return OperResult.succeed("saveSuccess", null, entity);
		
		
	}
	
	/**
	 * 单项删除/多项删除
	 * */
	@RequestMapping("commitCoor")
	public @ResponseBody OperResult<Coordination> commit(
			Coordination coordination) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this
				.getPropValues(PpmPrebidReview.class);
		Coordination entity = this.coordinationService.commit(user,
				coordination, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS,
				"提交成功", entity);
	}
	@RequestMapping("/batchDel")
	public @ResponseBody OperResult<Coordination> batchDel(String CoordinationId) {
		UserProfile user = this.getUserProfile();
		System.out.println(CoordinationId);
		coordinationService.delete(user, CoordinationId);
		return OperResult.succeed("deleteSuccess", null, null);

	}
	@RequestMapping("getNoticePerson")
	public @ResponseBody List<Map<String,String>> getNoticePerson(){
		
		List<Map<String,String>> list = coordinationService.getNoticePerson();
		return list;
	}
	@RequestMapping(value = { "/downExcel" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET,
			org.springframework.web.bind.annotation.RequestMethod.POST })
	public String downExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputStream os = null;
		try {
			String fileName = "�ڲ�Э��.xls";
			String iccIds = CommonUtil.trim(getRequestPara("ids"));

			HSSFWorkbook wb = this.coordinationExport.getCoordinationImp(iccIds);

			response.setContentType("applicationnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
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
	@RequestMapping("/getCorType")
	public @ResponseBody Map<String, String> getCountryCodetable() {
		
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems("CorType");
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;

	}
	 
}
