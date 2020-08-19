package com.supporter.prj.ppm.poa.power_attorney.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApply;
import com.supporter.prj.ppm.poa.power_attorney.entity.PowerAttorney;
import com.supporter.prj.ppm.poa.power_attorney.entity.PowerAttorneyPerson;
import com.supporter.prj.ppm.poa.power_attorney.service.PowerAttorneyService;
import com.supporter.prj.ppm.prj_org.base_info.common.PrjInfo;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.spring_mvc.AbstractController;

/**   
 * @Title: Controller
 * @Description: .
 * @author GuoXiansheng
 * @date 2019-09-30 15:25:34
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("ppm/poa/power_attorney")
public class PowerAttorneyController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PowerAttorneyService powerAttorneyService; 

	@Autowired
	private PrjInfo prjInfo;

	/**
	 *  获取业务主键下所有授权书
	 *  @param businessUUID
	 *  @return list
	 *  @throws SQLException
	 */
	@RequestMapping("listAllLaByBusinessUUID")
	public @ResponseBody List<PowerAttorney> listAllLaByBusinessUUID(String businessUUID) throws SQLException {
		List<PowerAttorney> allLaByBusinessUUID = powerAttorneyService.getAllLaByBusinessUUID(businessUUID);
		return allLaByBusinessUUID;
	}
	/**
	 * 保存或更新数据
	 * 
	 * @param powerAttorneyPerson 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdatePerson")
	public @ResponseBody OperResult<PowerAttorneyPerson> saveOrUpdatePerson(PowerAttorneyPerson powerAttorneyPerson) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(PowerAttorneyPerson.class);
		PowerAttorneyPerson entity = this.powerAttorneyService.saveOrUpdatePerson(user, powerAttorneyPerson, valueMap);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	/**
	 * 展示被授权人员列表.
	 * @param request
	 * @param jqGridReq
	 * @param pp
	 * @param laId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping("getPersonGrid")
	public @ResponseBody JqGrid getPersonGrid(HttpServletRequest request, JqGridReq jqGridReq, PowerAttorneyPerson pp,String laId) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		UserProfile user = this.getUserProfile();
		List<PowerAttorneyPerson> list = this.powerAttorneyService.getPersonGrid(user, jqGrid, laId);
		return jqGrid;
	}
	/**
	 * 删除从表操作
	 * 
	 * @param laPersonId 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("deletePerson")
	public @ResponseBody OperResult deletePerson(String laPersonId) {
		UserProfile user = this.getUserProfile();
		this.powerAttorneyService.deletePerson(user, laPersonId);
		return OperResult.succeed("deleteSuccess", null, null);
	}
	/**
	 * 保存或更新数据
	 * @param powerAttorney 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<PowerAttorney> saveOrUpdate(PowerAttorney powerAttorney) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(AuthorityApply.class);
		PowerAttorney entity = this.powerAttorneyService.saveOrUpdate(user, powerAttorney, valueMap);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	/**
	 * 获取授权业务类型
	 * @param 授权业务编号
	 * @return 授权业务名称
	 */
	@RequestMapping("getLaBusinessType")
	public @ResponseBody String getLaBusinessType(String laBusinessTypeId) {
		Map<String, String> laBusinessTypeTable = PowerAttorney.getLaBusinessTypeTable();
		String laBusinessType = laBusinessTypeTable.get(laBusinessTypeId);
		return laBusinessType;
	}
	/**
	 * 进入新建、编辑或查看页面时加载的信息
	 * @param request
	 * @param jqGridReq
	 * @param laId
	 * @param isEnglish
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody PowerAttorney initEditOrViewPage(HttpServletRequest request,JqGridReq jqGridReq,String laId,String isEnglish) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		PowerAttorney entity = powerAttorneyService.initEditOrViewPage(jqGrid, laId, this.getUserProfile(), isEnglish);
		return entity;
	}
	/**
	 * 开始页完成显示所有授权书信息
	 * @param request
	 * @param jqGridReq
	 * @param pa
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping("getPowerAttorneyGrid")
	public @ResponseBody JqGrid getPowerAttorneyGrid(HttpServletRequest request, JqGridReq jqGridReq, PowerAttorney pa, String prjId) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		UserProfile user = this.getUserProfile();
		List<PowerAttorney> list = this.powerAttorneyService.getPowerAttorneyGrid(user, jqGrid, prjId);
		return jqGrid;
	}
	/**
	 * 按项目和业务显示所有授权书信息
	 * @param request
	 * @param jqGridReq
	 * @param laBusinessTypeId
	 * @param prjId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping("getLaGridByProIdAndLaBusinessTypeId")
	public @ResponseBody JqGrid getLaGridByProIdAndLaBusinessTypeId(HttpServletRequest request, JqGridReq jqGridReq,String laBusinessTypeId, String prjId) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		List<PowerAttorney> list = this.powerAttorneyService.getLaGridByProIdAndLaBusinessTypeId(jqGrid, laBusinessTypeId, prjId);	
		return jqGrid;
	}
	/**
	 * 获取授权书对象.
	 * @param jqGrid
	 * @param laId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getFillFormPowerAttorneyData")
	public @ResponseBody PowerAttorney getFillFormData(JqGrid jqGrid, String laId) throws Exception {
		UserProfile user = this.getUserProfile();
		PowerAttorney powerAttorney = this.powerAttorneyService.getFillFormData(user, laId);
		List<PowerAttorneyPerson> list = this.powerAttorneyService.getPersonGrid(user, jqGrid, laId);
		if(list.size() != 0) {
			powerAttorney.setPersonType(list.get(0).getPersonType());
		}
		return powerAttorney;
	}
	/**
	 * 单项删除授权书.
	 * @param laId
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("singleDelete")
	public @ResponseBody OperResult singleDelete(String laId) throws Exception {
		int singleDelete = this.powerAttorneyService.singleDelete(laId);
		if(singleDelete == 1) {
			return OperResult.succeed("deleteSuccess", null, null);
		}else if(singleDelete == 0) {
			return OperResult.fail("deleteFalse", null, null);
		}else {
			return OperResult.fail("deleteFalse", null, null);
		}
	}
	/**
	 * 删除授所有权书.
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("deleteAll")
	public @ResponseBody OperResult deleteAll() throws Exception {
		return OperResult.succeed("deleteSuccess", null, null);
	}
	/**
	 * 获取附件文件列表.
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("getEnclosureList")
	public @ResponseBody List<IFile> getEnclosureList(String moduleName, String busiType, String oneLevelId, String twoLevelId) throws Exception {
		List<IFile> list = this.powerAttorneyService.getEnclosureList(moduleName, busiType, oneLevelId, twoLevelId);
		return list;
	}
	/**
	 * 上传盖章文件.
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("hasSealDocument")
	public @ResponseBody OperResult hasSealDocument(String laId) throws Exception {
		this.powerAttorneyService.hasSealDocument(laId);
		return OperResult.succeed("deleteSuccess", null, null);
	}
	/**
	 * 判断是否上传盖章文件.
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("selectSealDocument")
	public @ResponseBody int selectSealDocument(String laId) throws Exception {
		return this.powerAttorneyService.selectSealDocument(laId);
	}
	/**
	 * 导出全部授权书信息
	 */
	@RequestMapping(value = { "/downExcelData" }, method = {RequestMethod.GET, RequestMethod.POST })
	public String downExcelData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputStream os = null;
		try {
			String fileName = "授权书信息导出.xls";
			HSSFWorkbook wb = this.powerAttorneyService.getAllLaInfoWorkbook();
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
	/**
	 * 按项目编号和业务编码号导出授权书信息
	 */
	@RequestMapping(value = { "/downExcelLessData" }, method = {RequestMethod.GET, RequestMethod.POST })
	public String downExcelLessData(HttpServletRequest request, HttpServletResponse response,String laBusinessTypeId, String prjId) throws Exception {
		Prj prj = prjInfo.PrjInformation(prjId);
		String prjCName = prj.getPrjCName();
		String laBusinessType = getLaBusinessType(laBusinessTypeId);
		OutputStream os = null;
		try {
			String fileName = prjCName + "_" + laBusinessType + "_" + "授权书信息导出.xls";
			HSSFWorkbook wb = this.powerAttorneyService.getLaInfoWorkbook(prjId, laBusinessTypeId);
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
	/**
	 * 按授权书编号获得项目信息.
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("getPrjDataByLaId")
	public @ResponseBody Prj getPrjDataByLaId(String laId) throws Exception {
		PowerAttorney powerAttorney = this.powerAttorneyService.get(laId);
		String prjId = powerAttorney.getPrjId();
		Prj prj = prjInfo.PrjInformation(prjId);

		return prj;
	}
	/**
	 * 获得项目信息.
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("getPrjData")
	public @ResponseBody Prj getPrjData(String prjId) throws Exception {
		Prj prj = prjInfo.PrjInformation(prjId);
		return prj;
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping("abortAndInit")
	public @ResponseBody OperResult abortAndInit(String laId) {
		this.powerAttorneyService.abortAndInit(laId);
		return OperResult.succeed("Success", null, null);
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping("createPdfC")
	public @ResponseBody OperResult createPdfC(String laId) {
		this.powerAttorneyService.createPdfC(laId);
		return OperResult.succeed("Success", null, null);
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping("createPdfE")
	public @ResponseBody OperResult createPdfE(String laId) {
		this.powerAttorneyService.createPdfE(laId);
		return OperResult.succeed("Success", null, null);
	}
	@RequestMapping("filesdownloads")
	public void download(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		String fileName = "授权书.pdf";
		String classPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		String ROOT_PATH = classPath.substring(0, classPath.indexOf("/WEB-INF/classes"));
		String path = ROOT_PATH + "/ppm/poa/power_attorney/rpt/out/demo.pdf";
		response.reset();
		//1.设置文件ContentType类型，这样设置，会自动判断下载文件类型  
		response.setContentType("multipart/form-data");
		//2.设置文件头
		String userAgent = request.getHeader("User-Agent");
		if (StringUtils.isBlank(userAgent)) {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} else {
			if (userAgent.indexOf("MSIE") != -1) {
				// IE使用URLEncoder
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else {
				// FireFox使用ISO-8859-1
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
		}
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
		File file = new File(path);
		OutputStream out = null;
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			out = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, count);
			}
			out.close();
			out.flush();
		} catch (IOException e) {
			System.out.println("文件下载异常:" + e);
			response.reset();
		} finally {
			//response.reset();
			IOUtils.closeQuietly(inputStream);
		}
	}
}




