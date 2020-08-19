package com.supporter.prj.pm.enginee_negotiate.controller;

import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.supporter.prj.pm.enginee_negotiate.service.ExportEngineeVisaService;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;
@Controller
@RequestMapping("pm/enginee_negotiate/export")
public class ExportEngineeVisaController  extends AbstractController {

	@Autowired
    private ExportEngineeVisaService exportService;
	
	@RequestMapping(value={"downExcel"}, method={RequestMethod.GET, RequestMethod.POST})
	  public String downExcel(HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	  {
	    OutputStream os = null;
	    try
	    {
	      String fileName = "工程签证.xls";
	      String nodeId = CommonUtil.trim(getRequestPara("nodeId"));
	      HSSFWorkbook wb = exportService.getWorkbook(nodeId, getUserProfile());

	      response.setContentType("application/vnd.ms-excel");
	      response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
	      os = response.getOutputStream();
	      wb.write(os);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    finally {
	      os.flush();
	      os.close();
	    }
	    return null;
	  }
	
}
