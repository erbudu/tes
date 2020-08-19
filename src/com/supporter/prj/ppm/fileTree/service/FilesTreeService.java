package com.supporter.prj.ppm.fileTree.service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.fileTree.entity.FilesTree;
import com.supporter.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor 颜丙超
 * @date 2019/11/14
 */
@Service
public class FilesTreeService {
    @Autowired
    private FileUploadService fileUploadService;

    public List<FilesTree> getGrid(UserProfile user, JqGrid jqGrid, String codeTableName, String oneLevelId, String moduleName, String busiType, String fileType) {
        //获取附件类型
        List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems(codeTableName);
        System.out.println(items.size());
        List<FilesTree> list = new ArrayList<FilesTree>();

        for (IComCodeTableItem item : items
        ) {
            FilesTree filesTree = new FilesTree();
            filesTree.setFileType(item.getItemValue());//类型名称
            filesTree.setId(item.getItemId());//id
            filesTree.setParent(item.getExtField1());//父节点id
            String isLeaf = item.getExtField2();//是否是末及
            String level = item.getExtField3();//级别
            if (StringUtils.isNotBlank(isLeaf) && isLeaf.equals("1")) {
                filesTree.setIsLeaf(true);
            }
            //级别
            if (StringUtils.isNotBlank(level)) {
                filesTree.setLevel(Integer.valueOf(level));
            }
            //是否需要附件
            String needFile = item.getExtField4();
            if (StringUtils.isNotBlank(needFile) && needFile.equals("1")) {
                filesTree.setNeedFile(true);
            }
            //是否必须传附件
            String must = item.getExtField5();
            if (StringUtils.isNotBlank(must) && must.equals("1")) {
                filesTree.setMust(true);
            }
            //获取文件
            List<FileUpload> files = fileUploadService.getList(moduleName, busiType, oneLevelId, item.getItemId());
            if (!CommonUtil.isNullOrEmpty(files)) {
                String fileName = "";
                String fileId = "";
                for (FileUpload file : files
                ) {
                    fileId += file.getFileId() + ",";
                    fileName += file.getFileName() + ",";
                }
                if (fileName.length() > 0) {
                    fileName = fileName.substring(0, fileName.length() - 1);
                    fileId = fileId.substring(0, fileId.length() - 1);
                    filesTree.setFileName(fileName);
                    filesTree.setFileId(fileId);
                }
            }
            //附件类别 1类 2类 3类
            if (StringUtils.isNotBlank(fileType)&&StringUtils.isNotBlank(item.getExtField6())){
                for (String fileT: fileType.split(",")
                     ) {
                    if (fileT.equals(item.getExtField6())){
                        list.add(filesTree);
                    }
                }
            }else{
                list.add(filesTree);
            }

        }
        jqGrid.setRows(list);
        jqGrid.setRowCount(list.size());
        return list;
    }
}
