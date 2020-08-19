package com.supporter.prj.ppm.fileTree.controller;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReport;
import com.supporter.prj.ppm.fileTree.entity.FilesTree;
import com.supporter.prj.ppm.fileTree.service.FilesTreeService;
import com.supporter.spring_mvc.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @autor 颜丙超
 * @date 2019/11/14
 */
@Controller
@RequestMapping("ppm/fileTree")
public class FilesTreeController extends AbstractController {
    @Autowired
    private FilesTreeService filesTreeService;

    @RequestMapping("getGrid")
    public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, String codeTableName,String oneLevelId) throws Exception {
        UserProfile user = this.getUserProfile();
        JqGrid jqGrid = jqGridReq.initJqGrid(request);
        String moduleName = request.getParameter("moduleName");
        String busiType = request.getParameter("busiType");
        //文件类型，用来区分资料清单
        String fileType = request.getParameter("fileType");
        filesTreeService.getGrid(user,jqGrid,codeTableName,oneLevelId,moduleName,busiType,fileType);
        return jqGrid;
    }
}
