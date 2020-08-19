package com.supporter.prj.linkworks.oa.doc_out.workflow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.doc_out.entity.OaDocOut;
import com.supporter.prj.linkworks.oa.doc_out.service.OaDocOutService;
import com.supporter.util.CommonUtil;

public class AcceptEditingHandler extends AbstractExecHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private HttpServletRequest request;
	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		OaDocOutService docService = (OaDocOutService) SpringContextHolder
				.getBean(OaDocOutService.class);
		String docId = (String) execContext.getProcVar("docId");
		OaDocOut doc = docService.get(docId);
		
		String ls_FilePath = getServicePath() + "/oa/doc_out/revised/" + docId + ".doc";
		if (!fileExists(ls_FilePath)) {
			String ls_TemplateFile = getServicePath()
					+ "/oa/doc_out/docs/"+docId+ ".doc";
			File lfile_Source = new File(ls_TemplateFile);
			copy(lfile_Source, ls_FilePath);
		}
		doc.setFileNames(ls_FilePath);
		docService.update(doc);
		return null;
	}

	public void executeProcVars(ExecContext execContext) {
	}
	
	boolean copy(File af_File, String as_TargetFile) {
		// 判断是否已经存在该目标文件
		if (fileExists(as_TargetFile)) {
			System.out.println("file already exists: " + as_TargetFile);
			return false;
		}

		File lfile_Target = new File(as_TargetFile);

		// 开始拷贝
		try {
			lfile_Target.createNewFile();
			FileInputStream lfis_Source = new FileInputStream(af_File);
			FileOutputStream lfos_Target = new FileOutputStream(lfile_Target);
			byte[] larrbyte_Buffer = new byte[1024];
			int li_Bytes = 0;
			while ((li_Bytes = lfis_Source.read(larrbyte_Buffer)) != -1) {
				lfos_Target.write(larrbyte_Buffer, 0, li_Bytes);
			}
			lfis_Source.close();
			lfos_Target.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean fileExists(String as_FileName) {
		File lfile_F = new File(as_FileName);
		return lfile_F.exists();
	}

	public String getSubDirName(String as_FileId) {
		String ls_Id = CommonUtil.format(as_FileId, "000000");
		String ls_Dir = ls_Id.substring(ls_Id.length() - 6).substring(0, 3);
		int li_DirId = CommonUtil.parseInt(ls_Dir, 1);
		return Integer.toString(li_DirId);
	}

	public String getServicePath() {
		/*
		 * String servicePath = this.request.getScheme() + "://" +
		 * this.request.getServerName() + ":" + this.request.getServerPort() +
		 * this.request.getContextPath();
		 */
		String servicePath = this.request.getSession().getServletContext().getRealPath(File.separator);
		return servicePath;
	}

}
