package com.supporter.prj.eip.file_upload.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.file_upload.dao.FileUploadDao;
import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.util.Log;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.binding.JsonUtil;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**   
 * @Title: Service
 * @Description: EIP_FILE_UPLOAD
 * @author lhy
 * @date 2016-11-14 14:13:35
 * @version V1.0   
 *
 */
@Service
public class FileUploadService implements IFileUploadService {
    @Autowired
    private FileUploadDao fileUploadDao;

    @Autowired
    FileManageService fileManageService;

    /**
     * 根据主键获取EIP_FILE_UPLOAD
     * @param fileId 主键
     * @return FileUpload
     */
    public FileUpload get(String fileId){
        return  fileUploadDao.get(fileId);
    }
    
    /**
     * 保存附件信息
     * @param fileId 主键
     * @return FileUpload
     */
    @Transactional(TransManager.APP)
    public void save(FileUpload fileUpload){
    	if(StringUtils.isBlank(fileUpload.getFileId())){
    		fileUpload.setFileId(UUIDHex.newId());
    	}
        fileUploadDao.save(fileUpload);
    }

    /**
     * 分页表格展示数据
     * @param user 用户信息
     * @param jqGridReq jqgrid请求对象
     * @return JqGrid
     */
    public List<FileUpload> getGrid(JqGrid jqGrid) {
        return  fileUploadDao.retrievePage(jqGrid);
    }


    /**
     * 保存附件信息
     * @param fileId 主键
     * @return FileUpload
     */
    @Transactional(TransManager.APP)
    public void update(FileUpload fileUpload){
        fileUploadDao.saveOrUpdate(fileUpload);
    }

    /**
     * 附件上传列表
     * @param busiType 业务类型
     * @param keyValues 业务对象id
     * @return list
     */
    public List<FileUpload> getList(String moduleName, String busiType, String oneLevelId, String twoLevelId) {
        List<FileUpload> list = fileUploadDao.getList(moduleName, busiType, oneLevelId, twoLevelId);
        List<FileUpload> newList = new ArrayList<FileUpload>();
        for (FileUpload fileUpload : list) {
            fileUpload.setShowImageUrl(fileManageService.getServicePath() + "/service/eip/FileUpload/showImage?fileId=" + fileUpload.getFileId());
            fileUpload.setFilePictureUrl(fileManageService.getServicePath() + "/eip/file_upload/img/" + fileUpload.getFilePicture());
            newList.add(fileUpload);
        }
        return  newList;
    }

    /**
     * 附件上传图片文件
     * @return list
     */
    public List<FileUpload> getListImg(String moduleName, String busiType, String oneLevelId, String twoLevelId) {
        return  fileUploadDao.getListImg(moduleName, busiType, oneLevelId, twoLevelId);
    }

    /**
     * 从表中删除指定实例. 
     * @param aupload_ToBeDel 被删除的实例
     */
    @Transactional(TransManager.APP)
    public void delete(FileUpload fileUpload, String savePath, UserProfile user) {
        if (fileUpload == null) {

            return;
        }

        //删除硬盘中的文件
        String fileName = savePath + File.separator + fileUpload.getFileId();
        File file = new File(fileName);
        if(file.exists()){
            file.delete();  
        }
        fileUploadDao.delete(fileUpload);
        EIPService.getLogService(Log.EIP_LOG).info(user, "附件-删除", "删除附件：“" + fileUpload.getModuleName() + "”，应用：“"+ fileUpload.getModuleName() 
                +"”，业务记录：" + fileUpload.getFileId(), null, null);

    }

    /**
     * 获取最大序号.
     * @param busiType 业务类型
     * @param keyValues 业务对象id
     * @return
     */
    public int maxOrder(String moduleName, String busiType, String oneLevelId, String twoLevelId) {
        return fileUploadDao.maxOrder(moduleName, busiType, oneLevelId, twoLevelId);	
    }

    @Override
    public String getStoragePath() {
        return fileManageService.getStoragePath();
    }

    /**
     * 获取附件总大小
     * @return
     */
    public long getSumFileSize(String teantNo) {
        return fileUploadDao.getSumFileSize(teantNo);
    }

    /**
     * 根据文件id获取File
     * @param fileId
     * @return
     */
    public File getFile(String fileId) {
        FileUpload fileUpload = get(fileId);
        Date createDate = fileUpload.getCreatedDate();
        String path = fileManageService.getStoragePath(fileUpload.getTenantNo(), fileUpload.getModuleName(), fileUpload.getBusiType(), createDate);
        File file = new File(path, fileId);
        return file; 
    }

    /**
     * 根据业务单一级id找集合文件
     * @return
     */
    @Override
    public List < File > getFileListByEntityIds (String oneLevelIds, String moduleId, String innerName) {
        List<FileUpload> fileUploadList = fileUploadDao.getListByEntityIds(oneLevelIds);
        List < File > list = new ArrayList < File > ();
        if(fileUploadList != null && fileUploadList.size() > 0){
            String tempName="";
            if(StringUtils.isNotEmpty(innerName)){
                tempName = innerName;
            }else{
                tempName = UUIDHex.newId();
            }
            File innerfile = new File(EIPService.getModuleService()
                    .getAssembleDirectory(moduleId), "FILEUPLOAD/INNERNAME"+tempName);
            try {
                FileUtils.writeStringToFile(innerfile, JsonUtil.toJson(fileUploadList), "UTF-8");
                list.add(innerfile);
                for (FileUpload fileUpload : fileUploadList) {
                    Date createDate = fileUpload.getCreatedDate();
                    String path = fileManageService.getStoragePath(fileUpload.getTenantNo(), fileUpload.getModuleName(), fileUpload.getBusiType(), createDate);
                    File file = new File(path, fileUpload.getFileId());
                    list.add(file);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        return list;	
    }


    /**
     * 卸载附件
     * @param oneLevelIds
     */
    @Override
    public void uninstallFileByEntityIds (String oneLevelIds) {
        List<FileUpload> fileList = fileUploadDao.getListByEntityIds(oneLevelIds);
        for (FileUpload fileUpload : fileList) {
            String path = fileManageService.getStoragePath(fileUpload.getTenantNo(), fileUpload.getModuleName(), fileUpload.getBusiType(), fileUpload.getCreatedDate());
            //删除硬盘中的文件
            String fileName = path + File.separator + fileUpload.getFileId();
            File file = new File(fileName);
            if(file.exists()){
                file.delete();  
            }
            fileUploadDao.delete(fileUpload);
        }


    }
    
    /**
	 * 获取附件上传列表
	 * @param moduleId 应用id
	 * @param busiType 业务二级分类
	 * @param entityId 业务单一级id
	 * @return
	 */
	@Override
	public List < IFile > getFileList(String moduleId, String busiType, String entityId) {	
		return getFileList(moduleId, busiType, entityId, "");
	}
	
	/**
	 * 获取附件上传列表
	 * @param moduleId 应用id
	 * @param busiType 业务二级分类
	 * @param entityId 业务单一级id
	 * @param entityId2 业务单二级id
	 * @return
	 */
	@Override
	public List < IFile > getFileList(String moduleId, String busiType, String entityId, String entityId2) {
		List < IFile > fileList = new ArrayList < IFile > ();
		 List < FileUpload > list = fileUploadDao.getList(moduleId, busiType, entityId, entityId2);
		 for (FileUpload fileUpload : list) {
			 fileList.add(fileUpload);
		 }
		return fileList;
	}
	
	/**
	 * 根据附件id获取附件记录
	 * @param fileId
	 * @return
	 */
	@Override
	public IFile getFileRec(String fileId) {
		return this.get(fileId);
	}

	@Override
	public String saveFile(File file, String moduleName, String busiType,
			UserProfile user, String... entityId) {
		return saveFile(file, moduleName, busiType, "", user, entityId);
	}
	
	@Override
	public String saveFile(File file, String moduleName, String busiType,
			String newFileName, UserProfile user, String... entityId) {
		return saveFile(file, moduleName, busiType, newFileName, 0, user, entityId);
	}
	
	@Override
	@Transactional(TransManager.APP)
	public String saveFile(File file, String moduleName, String busiType,
			String newFileName, int displayOrder, UserProfile user,
			String... entityId) {
		String entityId1 = "";
		String entityId2 = "";
		int i =0;
		for (String str : entityId) {
			i++;
			if (i==1) {
				entityId1 = str;
			}
			if (i==2) {
				entityId2 = str;
			}
		}
		//判断文件名长度
		String fileName = file.getName();
		
		//文件的扩展名.
		int li_Index = fileName.lastIndexOf(".");
		String fileType = fileName.substring(li_Index + 1,  fileName.length());
		
		//判断文件大小
		Long fileSize = file.length();

		//保存上传后文件的信息至数据库表uploadfile
		FileUpload fileUpload = new FileUpload();
		fileUploadDao.save(fileUpload);	
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			
			//每月创建一个文件夹，将上传文件按月归类放置
			Date dateNow = new Date();
			String path = fileManageService.getStoragePath(user.getTenantNo(), moduleName , busiType, dateNow);
	    	
			File dirFileDate = new File(path);
	    	if(!dirFileDate.isDirectory() || !dirFileDate.exists()){
	    		dirFileDate.mkdirs();
	    	}
			in = new FileInputStream(file);
			out = new FileOutputStream(new File( path + File.separator + fileUpload.getFileId()));
			byte[] b = new byte[1024 * 5]; 
			int len; 
			while ( (len = in.read(b)) != -1) { 
				out.write(b, 0, len); 
			}
			
			fileUpload = get(fileUpload.getFileId());
			fileUpload.setBusiType(busiType);
			fileUpload.setContentType(new MimetypesFileTypeMap().getContentType(file));
			fileUpload.setCreatedBy(user.getPersonId());
			fileUpload.setCreatedByName(user.getName());
			fileUpload.setCreatedDate(dateNow);			
			if (CommonUtil.trim(newFileName).length() > 0) {
				int index = newFileName.lastIndexOf(".");
				fileType = newFileName.substring(index + 1,  newFileName.length());
				fileName = newFileName;
				
			}
			if (displayOrder == 0) {
				int order = maxOrder(moduleName, busiType, entityId1, entityId2);
				fileUpload.setDisplayOrder(order + 10);
			} else {
				fileUpload.setDisplayOrder(displayOrder);
			}
			
			fileUpload.setFileExt(fileType);
			fileUpload.setFileName(fileName);
			fileUpload.setFileSize(fileSize.intValue());
			fileUpload.setModuleName(moduleName);
			fileUpload.setTenantNo(user.getTenantNo());
			fileUpload.setOneLevelId(entityId1);
			fileUpload.setTwoLevelId(entityId2);
			fileUploadDao.update(fileUpload);
			EIPService.getLogService(Log.EIP_LOG).info(user, "附件-上传", "上传附件：“" + fileName + "”，应用：“"+ moduleName 
					+"”，业务记录：" + fileUpload.getFileId(), null, null);
			
			
		} catch (FileNotFoundException ex) {
			//异常删除
			Date createDate = fileUpload.getCreatedDate();
			String path = fileManageService.getStoragePath(user.getTenantNo(), moduleName, busiType, createDate);
			delete(fileUpload, path, user);	
			ex.printStackTrace();
			return "";
		} catch(OutOfMemoryError e){
			//异常删除
			Date createDate = fileUpload.getCreatedDate();
			String path = fileManageService.getStoragePath(user.getTenantNo(), moduleName, busiType, createDate);
			delete(fileUpload, path, user);	
			e.printStackTrace();
			return "";
		} catch (Exception ex) {
			
			//异常删除
			Date createDate = fileUpload.getCreatedDate();
			String path = fileManageService.getStoragePath(user.getTenantNo(), moduleName, busiType, createDate);
			delete(fileUpload, path, user);
			ex.printStackTrace();
			return "";
		} finally {
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return fileUpload.getFileId();
	}
	
	@Override
	public void deleteFile(String fileId) {
		FileUpload fileUpload = get(fileId);
		if(fileUpload != null) {
			 String path = fileManageService.getStoragePath(fileUpload.getTenantNo(), fileUpload.getModuleName(), fileUpload.getBusiType(), fileUpload.getCreatedDate());
	         //删除硬盘中的文件
	         String fileName = path + File.separator + fileUpload.getFileId();
	         File file = new File(fileName);
	         if(file.exists()){
	             file.delete();  
	         }
	         fileUploadDao.delete(fileUpload);
		}
		
	}

	

	
	
	

	


}

