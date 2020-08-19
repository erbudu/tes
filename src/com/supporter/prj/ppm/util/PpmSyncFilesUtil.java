package com.supporter.prj.ppm.util;

import com.mysql.jdbc.StringUtils;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReview;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVer;
import com.supporter.prj.ppm.contract.pact.review_ver.service.ContractPactRevVerService;
import com.supporter.util.CommonUtil;
import org.springframework.stereotype.Service;
import sun.applet.Main;

import java.io.File;
import java.util.List;

/**
 * @autor 颜丙超
 * @date 2019/11/27
 */
public class PpmSyncFilesUtil {
    public static FileUploadService getFileUploadService(){
        FileUploadService fileUploadService = (FileUploadService) SpringContextHolder.getBean(FileUploadService.class);
        return fileUploadService;
    }
    public static String syncFiles(String oldM, String oldB, String oldOneId, String oldTowId, String newM, String newB, String newOneId,
                                   String newTowId, UserProfile userProfile) {
        String newfileId = "";

        //获取附件对象
        List<FileUpload> files = getFileUploadService().getList(oldM, oldB, oldOneId, oldTowId);
        System.out.println(files.size());
        //循环复制
        for (FileUpload fileUpload : files
        ) {
            String[] levelId = {newOneId, newTowId};
            //获取文件对象
            File file = getFileUploadService().getFile(fileUpload.getFileId());
            //保存新文件 并保存附件对象
            newfileId = getFileUploadService().saveFile(file, newM, newB,
                    fileUpload.getFileName(), userProfile, levelId);
        }
        System.out.println("newfileId"+newfileId);
        return newfileId;
    }

    public static String syncFileByFileId(String oldFileId,String newM, String newB, String newOneId,
                                          String newTowId, UserProfile userProfile) {
        String newfileId = "";

        String[] levelId = {newOneId, newTowId};
        FileUpload fileUpload = getFileUploadService().get(oldFileId);
        //获取文件对象
        File file = getFileUploadService().getFile(oldFileId);
        //保存新文件 并保存附件对象
        newfileId = getFileUploadService().saveFile(file, newM, newB,
                fileUpload.getFileName(), userProfile, levelId);

        return newfileId;
    }

    public static void main(String[] args) {
        EIPService.getTodoService().getProcTodos("ff8080816711d0e1016714dab7770007");
        System.out.println(EIPService.getTodoService().getProcTodos("ff8080816711d0e1016714dab7770007").size());
    }
}
