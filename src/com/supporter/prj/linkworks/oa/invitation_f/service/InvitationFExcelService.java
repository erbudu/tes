package com.supporter.prj.linkworks.oa.invitation_f.service;

import java.io.*;
import java.net.URLEncoder;
import java.sql.Connection;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.bcel.util.ClassPath;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runqian.report4.model.ReportDefine;
import com.runqian.report4.model.engine.ExtCellSet;
import com.runqian.report4.usermodel.Context;
import com.runqian.report4.usermodel.Engine;
import com.runqian.report4.usermodel.IReport;
import com.runqian.report4.util.ReportUtils;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.hibernate.HibernateUtil;
import com.supporter.prj.linkworks.oa.invitation_f.entity.InvitationForeignerApply;

@Service
public class InvitationFExcelService {
	
	@Autowired
	private InvitationForeignerApplyService invitationForeignerApplyService;
	
	private String classPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
	private String ROOT_PATH = classPath.substring(0,classPath.indexOf("/WEB-INF/classes"));//获取项目物理根路径
	private String FILE_PATH = "/oa/invitation_f/excel/";
	
	/**
	 * 下載excel文件
	 * @param response
	 * @param invitationId
	 * @param user
	 * @throws Exception
	 */
	public void downloadExcelFile(HttpServletResponse response, String invitationId, UserProfile user) throws Exception{
		if(StringUtils.isNotBlank(invitationId)){
			InvitationForeignerApply entity = this.invitationForeignerApplyService.get(invitationId);
			String filePath = this.ROOT_PATH + this.FILE_PATH + entity.getInvitationId() + ".xls";
			if (!new File(filePath).exists()) {//如果文件不存在，则重新生成
				createExcel(invitationId, user);
			}
			String downloadFileName = "《免于被核实主体邀请函》审批表_拟" + entity.getPlanInboundDate() + "入境（" + entity.getDeptName() + "_" + entity.getCreatedBy() + "）";
			downloadFile(downloadFileName, filePath, response);
		}
	}
	
	/**
	 * 重新生成excel
	 * @param invitationId
	 * @param user
	 * @return
	 */
	public boolean reCreateExcelFile(String invitationId, UserProfile user){
		if(StringUtils.isNotBlank(invitationId)){
			InvitationForeignerApply entity = this.invitationForeignerApplyService.get(invitationId);
			if (entity != null){
				String filePath = createExcel(invitationId, user);
				if (new File(filePath).exists()) return true;
			}
		}
		return false;
	}
	
	/**
	 * 生成excel文件
	 * @param invitationId
	 * @param user
	 * @return
	 */
	public String createExcel(final String invitationId, final UserProfile user){
		String rootPagh = EIPService.getContextService().getRootDirPath();
//		String license = rootPagh + "\\WEB-INF\\classes\\runqianWindowServer.lic";//windows授权
		String license = rootPagh + "/WEB-INF/classes/runqianLinuxServer.lic";//linux授权
		ExtCellSet.setLicenseFileName(license);
		final Context cxt = new Context();
//		final String defPath = rootPagh + "\\oa\\invitation_f\\template\\invitation_f.raq";//windows路径
		final String defPath = rootPagh + "/oa/invitation_f/template/invitation_f.raq";//Linux路径
		final String filePath = rootPagh + this.FILE_PATH +invitationId+".xls";
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) {
				try {
					ReportDefine reportDef = (ReportDefine)ReportUtils.read(defPath);
					cxt.setParamValue("invitationId", invitationId);
					cxt.setParamValue("jingbanren", user.getName());
					cxt.setConnection("supp_app", connection);
					Engine engine = new Engine(reportDef,cxt);
					IReport ireport = engine.calc();
					ReportUtils.exportToExcel(filePath, ireport, true);//导出excel
				}catch(Exception e) {
					e.printStackTrace();
				}catch(Throwable e) {
					e.printStackTrace();
				}
			}

		});
		return filePath;
	}
	
	 /**
     * 下载文件
     * @param name 目标文件名
     * @param filePath 源文件地址，带文件名
     * @param response
     * @return
     * @throws Exception
     */
    public static boolean downloadFile(String name, String filePath, HttpServletResponse response) throws Exception {
        String fileName = name;
        String fileType = filePath.substring(filePath.lastIndexOf("."));
        File file = new File(filePath);  // 根据文件路径获得File文件
        // 判断文件类型
        String contentType = "application/vnd.ms-excel";
        // 设置导出文件表头信息
        response.setContentType(contentType + ";charset=UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8") + fileType);
        //response.setHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName.getBytes(), "ISO8859-1") + fileType + "\"");
        response.setContentLength((int) file.length());
        byte[] buffer = new byte[4096];// 缓冲区
        BufferedOutputStream output = null;
        BufferedInputStream input = null;
        try {
            output = new BufferedOutputStream(response.getOutputStream());
            input = new BufferedInputStream(new FileInputStream(file));
            int n = -1;
            //遍历，开始下载
            while ((n = input.read(buffer, 0, 4096)) > -1) {
                output.write(buffer, 0, n);
            }
            output.flush();   //不可少
            response.flushBuffer();//不可少
        } catch (Exception e) {
            // 异常捕捉
            System.err.println("InvitationFExcelService.downLoadFile error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 关闭流，不可少
            if (input != null)
                input.close();
            if (output != null)
                output.close();
        }
        return false;
    }
}
