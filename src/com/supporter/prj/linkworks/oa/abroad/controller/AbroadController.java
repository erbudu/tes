package com.supporter.prj.linkworks.oa.abroad.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.abroad.constants.AbroadAuthConstant;
import com.supporter.prj.linkworks.oa.abroad.entity.Abroad;
import com.supporter.prj.linkworks.oa.abroad.entity.AbroadPerson;
import com.supporter.prj.linkworks.oa.abroad.service.AbroadService;
import com.supporter.prj.linkworks.oa.abroad.util.AbroadAuthUtil;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;


/**   
 * @Title: Controller
 * @Description: 出国审批.
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("oa/abroad")
public class AbroadController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AbroadService abroadService;
	
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq,Abroad abroad,String recordId,String attr) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.abroadService.getGrid(user, jqGrid, attr);
		return jqGrid;
	}
	
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param reportId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody Abroad initEditOrViewPage(String recordId) {
		Abroad entity = abroadService.initEditOrViewPage(recordId,this.getUserProfile());
		return entity;
	}
	
	/**
	 * 分页表格展示全体出国人员数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getRecGrid")
	public @ResponseBody JqGrid getRecGrid(HttpServletRequest request, JqGridReq jqGridReq,String recordId, boolean isPrint) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		UserProfile user = this.getUserProfile();
		if (isPrint){
			jqGrid.setPageSize(99);
		}
		this.abroadService.getRecGrid(user,jqGrid,recordId);
		return jqGrid;
	}
	
	/**
	 * 分页表格展示外部出国人员数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getForeignGrid")
	public @ResponseBody JqGrid getForeignGrid(HttpServletRequest request, JqGridReq jqGridReq, AbroadPerson ap,String recordId) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		UserProfile user = this.getUserProfile();
		this.abroadService.getForeignGrid(user,jqGrid, ap,recordId);
		return jqGrid;
	}
	
	/**
	 * 保存或更新数据.
	 * 
	 * @param abroad 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<Abroad> saveOrUpdate(Abroad abroad) {
		Map< String, Object > valueMap = this.getPropValues(Abroad.class);
		UserProfile user = this.getUserProfile();
		AbroadAuthUtil.canExecute(user, AbroadAuthConstant.AUTH_OPER_NAME_SETVALABROAD, abroad.getRecordId(), abroad);
		Abroad entity = this.abroadService.saveOrUpdate(user, abroad, valueMap);
		if(abroad.getRecordStatus() == Abroad.PROCESSING){
			return OperResult.succeed("submitSuccess", null, entity);
		}else{
			return OperResult.succeed("saveSuccess", null, entity);			
		}
	}
	
	/**
	 * 获取当前用户对当前目录的角色
	 * @param id
	 * @return
	 */
	@RequestMapping("/checkAcl")
	public @ResponseBody Abroad checkAcl(String recordId) {
		Abroad abroad = this.abroadService.getById(recordId);
		return abroad ;
	}
	
	
	/**
	 * 删除操作
	 * 
	 * @param ordersIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String recordId) {
		this.abroadService.batchDel(this.getUserProfile(),recordId);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 发布公告
	 * 
	 * @return
	 */
	@RequestMapping("submitPub")
	public @ResponseBody OperResult submitPub(String recordId,String publicityDay,String ifPublicity) {
		this.abroadService.submitPub(recordId,publicityDay,ifPublicity);
		return OperResult.succeed("submitSuccess");
	}
	
	/**
	 * 撤回操作
	 * 
	 * @param ordersIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("returnBack")
	public @ResponseBody OperResult batchBack(String recordId) {
		this.abroadService.returnBack(recordId);
		return OperResult.succeed("backSuccess");
	}
	
	/**
	 * 通过出国审批id获取出国公示id
	 * @param id
	 * @return
	 */
	@RequestMapping("/getPubByAbroadId")
	public @ResponseBody String getPubByAbroadId(String recordId) {
		String pubId = this.abroadService.getPubByAbroadId(recordId);
		return pubId ;
	}
	
	/**
	 * 进入审批页面时加载的信息
	 * 
	 * @param reportId 主键
	 * @return
	 */
	@RequestMapping("initAbroad")
	public @ResponseBody Abroad initAbroad(String backId) {
		Abroad entity = abroadService.initAbroad(backId,this.getUserProfile());
		return entity;
	}
	
	

	/**
	  * 导出
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value={"/downExcel"},
	 method={org.springframework.web.bind.annotation.RequestMethod.GET,
	 org.springframework.web.bind.annotation.RequestMethod.POST})
	 public String downExcel(HttpServletRequest request,HttpServletResponse
	 response) throws Exception{
	 OutputStream os = null;
	 try
	 {
	 String fileName = "出国审批列表.xls";
	 String codeIds = CommonUtil.trim(getRequestPara("ids"));
		      
    HSSFWorkbook wb = this.abroadService.getWorkbook(codeIds);
		      
	 response.setContentType("applicationnd.ms-excel");
	 response.setHeader("Content-disposition", "attachment;filename=" +
	 URLEncoder.encode(fileName, "utf-8"));
	 os = response.getOutputStream();
	 wb.write(os);
	 }
	 catch (Exception e)
	 {
	 e.printStackTrace();
	 }
	 finally
	 {
	 os.flush();
	 os.close();
	 }
	 return null;
	 }

		/**
		 * 提取历史数据文件.
		 * @param report
		 * @return
		 */
		@RequestMapping("extractFiles")
		public @ResponseBody String extractFiles(String recordId){
			return abroadService.extractFiles(recordId,this.getUserProfile());
		}
		
		/**
		 * 提取历史数据文件.
		 * @param report
		 * @return
		 */
		@RequestMapping("batchExtractFiles")
		public @ResponseBody String batchExtractFiles(){
			return abroadService.batchExtractFiles(this.getUserProfile());
		}
}
