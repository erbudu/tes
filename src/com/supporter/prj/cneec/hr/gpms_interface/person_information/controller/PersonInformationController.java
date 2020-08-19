package com.supporter.prj.cneec.hr.gpms_interface.person_information.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.PersonPhotoInfo;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.service.PersonInformationService;
import com.supporter.prj.eip.emp.entity.Employee;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Controller
 * @Description: 人力的人员信息.
 * @author tiansen
 * @date 2017-9-08
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("eip/hr/person_information")
public class PersonInformationController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private PersonInformationService personInformationService;

	
	/**
	 * 查看人员信息
	 * @param systemInername
	 * @return
	 */
	@RequestMapping("viewPersonInformation")
	public @ResponseBody void viewPersonInformation(String as_personNo) {
		PrintWriter out = null;
		String personNo="";
		if(as_personNo != null && !as_personNo.equals("") ){
			personNo = as_personNo;
		}else{
			if(as_personNo == null || as_personNo.equals("")){
				Employee employee = (Employee)EIPService.getEmpService().getEmp(CommonUtil.trim(this.getUserProfile().getPersonId()));
				personNo =employee.getIdCard();
			}
		}
		
		String rentruJoson = personInformationService.getPersonInfomation(personNo);
		try{
			HttpServletResponse response = this.getResponse();
			  /* 设置格式为text/json    */
            response.setContentType("text/json"); 
            /*设置字符集为'UTF-8'*/
            response.setCharacterEncoding("UTF-8"); 
            out = response.getWriter();
            out.write(rentruJoson);
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	/**
	 * 查看人员照片
	 * @return
	 */
	@RequestMapping("viewPersonPhoto")
	public @ResponseBody void viewPersonPhoto(String as_personNo) {
		ServletOutputStream  sout  = null;
		String personNo="";
		if(as_personNo != null && !as_personNo.equals("")){
			personNo = as_personNo;
		}else{
			if(as_personNo == null || as_personNo.equals("")){
				Employee employee = (Employee)EIPService.getEmpService().getEmp(CommonUtil.trim(this.getUserProfile().getPersonId()));
				personNo =employee.getIdCard();
			}
		}
		PersonPhotoInfo personPhotoInfo = personInformationService.getPersonPhotoInfo(personNo);
		try{
			HttpServletResponse response = this.getResponse();
			  /* 设置格式为text/json    */
            response.setContentType("image/jped"); 
            /*设置字符集为'UTF-8'*/
            response.setCharacterEncoding("UTF-8"); 
            sout = response.getOutputStream();
            if(personPhotoInfo == null){
            	//home/tomcat/tomcat-eip/webapps/eip
            	String fileDir = EIPService.getContextService().getRootDirPath() + "/eip/hr/person_information/img/no_photo.gif";
            	File file = new File(fileDir);
            	InputStream input = new FileInputStream(file);
            	// ServletOutputStream用来传输数据,
            	OutputStream out = response.getOutputStream();
            	int firstChar = input.read();
            	int length = input.available();
            	byte[] byt = new byte[length];
            	byt[0] = (byte)firstChar;
            	int len = 0;
            	while ((len = input.read(byt,1,length-1)) != -1) {
            	  out.write(byt, 0, len);
            	}
            }else{
            	sout.write(personPhotoInfo.getEmpPhoto());
            }
            
            sout.flush();   
            sout.close(); 
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	/**
	 * 查看人员 教育经历
	 * @param systemInername
	 * @return
	 */
	@RequestMapping("viewPersonEducationInfo")
	public @ResponseBody void viewPersonEducationInfo(String as_personNo) {
		PrintWriter out = null;
		String personNo="";
		if(as_personNo != null && !as_personNo.equals("")){
			personNo = as_personNo;
		}else{
			if(as_personNo == null || as_personNo.equals("")){
				Employee employee = (Employee)EIPService.getEmpService().getEmp(CommonUtil.trim(this.getUserProfile().getPersonId()));
				personNo =employee.getIdCard();
			}
		}
		String rentruJoson = personInformationService.getPersonEducationInfo(personNo);
		try{
			HttpServletResponse response = this.getResponse();
			  /* 设置格式为text/json    */
            response.setContentType("text/json"); 
            /*设置字符集为'UTF-8'*/
            response.setCharacterEncoding("UTF-8"); 
            out = response.getWriter();
            out.write(rentruJoson);
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	/**
	 * 查看人员工作简历
	 * @param systemInername
	 * @return
	 */
	@RequestMapping("viewPersonExperienceInfo")
	public @ResponseBody void viewPersonCertificationsInfo(String as_personNo) {
		PrintWriter out = null;
		String personNo="";
		if(as_personNo != null && !as_personNo.equals("")){
			personNo = as_personNo;
		}else{
			if(as_personNo == null || as_personNo.equals("")){
				Employee employee = (Employee)EIPService.getEmpService().getEmp(CommonUtil.trim(this.getUserProfile().getPersonId()));
				personNo =employee.getIdCard();
			}
		}
		String rentruJoson = personInformationService.getPersonExperienceInfo(personNo);
		try{
			HttpServletResponse response = this.getResponse();
			  /* 设置格式为text/json    */
            response.setContentType("text/json"); 
            /*设置字符集为'UTF-8'*/
            response.setCharacterEncoding("UTF-8"); 
            out = response.getWriter();
            out.write(rentruJoson);
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	/**
	 * 查看人员业绩
	 * @param systemInername
	 * @return
	 */
	@RequestMapping("viewPersonPerformanceInfo")
	public @ResponseBody void viewPersonPerformanceInfo(String as_personNo) {
		PrintWriter out = null;
		String personNo="";
		if(as_personNo != null && !as_personNo.equals("")){
			personNo = as_personNo;
		}else{
			if(as_personNo == null || as_personNo.equals("")){
				Employee employee = (Employee)EIPService.getEmpService().getEmp(CommonUtil.trim(this.getUserProfile().getPersonId()));
				personNo =employee.getIdCard();
			}
		}
		String rentruJoson = personInformationService.getPersonPerformanceInfo(personNo);
		try{
			HttpServletResponse response = this.getResponse();
			  /* 设置格式为text/json    */
            response.setContentType("text/json"); 
            /*设置字符集为'UTF-8'*/
            response.setCharacterEncoding("UTF-8"); 
            out = response.getWriter();
            out.write(rentruJoson);
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
}
