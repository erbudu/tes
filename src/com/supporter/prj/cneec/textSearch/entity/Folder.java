package com.supporter.prj.cneec.textSearch.entity;

//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;


import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.textSearch.entity.base.BaseFolder;
//import com.supporter.prj.core.spring.SpringContextHolder;

//import com.supporter.prj.filebox.service.FileboxImpl;
//import com.supporter.prj.filebox.service.FolderImpl;
//import com.supporter.security.AccessMode;
//import com.supporter.security.UserProfile;
//import com.supporter.util.CommonUtil;


/**
 * 封装一个文件夹.
 * @author hsh
 *
 */
@Entity
@Table(name = "fbox_folder", schema = "")
public class Folder extends BaseFolder {
	private static final long serialVersionUID = 1L;
//	private UserProfile userProfile = null;
//	private String is_AccessModes = null;
	
	

/*[CONSTRUCTOR MARKER BEGIN]*/
	/**
	 * 
	 */
	public Folder() {
		super();
	}

	/**
	 * Constructor for primary key.
	 * @param id ID.
	 */
	public Folder(java.lang.String id){
		super(id);
	}

/*[CONSTRUCTOR MARKER END]*/
	
    //private boolean ibool_ReadAllGranted = false;
	
//    public FolderImpl getFolderService(){
//		return SpringContextHolder.getBean(FolderImpl.class);
//	}
//	
//	public FileboxImpl getFileboxService(){
//		return SpringContextHolder.getBean(FileboxImpl.class);
//	}
    /**
     * 设置当前用户是否可以读取全部文件和子目录.
     * @param abool_Granted
     */
//    public void setReadAllGranted(boolean abool_Granted) {
//        //ibool_ReadAllGranted = abool_Granted;
//    }
//    
//    //public boolean getReadAllGranted() {return ibool_ReadAllGranted;}
//    public boolean getReadAllGranted() {return true;}
    
    /**
     * 获取本目录的路径. 
     * @return 格式为： \[root]\folder1\folder11之类的
     */
//    public String getFolderPath() {
//        String ls_Path = CommonUtil.trim(getFolderName());
//        Folder lfolder_Parent = this.getParentFolder();
//        while (lfolder_Parent != null) {
//            ls_Path = CommonUtil.trim(lfolder_Parent.getFolderName()) + "\\" + ls_Path;
//            String ls_ParentFolderId = lfolder_Parent.getId();
//            lfolder_Parent = lfolder_Parent.getParentFolder();    
//            if (lfolder_Parent != null && ls_ParentFolderId == lfolder_Parent.getId()) break; 
//        }
//        return ls_Path;
//    }
//    
//    public Folder getParentFolder() {
//        return this.getFolderService().getParentFolder(this);
//    }
    
    
    /**
     * 获取下级目录的数量.
     * @return
     */
//    public int getSubFolderCount() {
//        return this.getFolderService().getSubFolderCount(this);
//    }
    
    /**
     * 获取子目录列表.
     * @return List实例，每个实例是一个Folder实例.
     */
//    @SuppressWarnings("unchecked")
//	public List < Folder > getSubFolders() {
//        return this.getFolderService().getSubFolders(this);
//    } 
    
    /**
     * 获取有访问权限(可以读取的权限)的文件列表.
     * @return List实例，每个实例是一个Folder实例.
     */
//    @SuppressWarnings("unchecked")
//	public List < Folder > getAccessibleSubFolders() {
//        List < Folder > llist_SubFolders = this.getSubFolders();
//        List < Folder > llist_SubFolder = new ArrayList();
//        int size = llist_SubFolders.size();
//        for (int i = 0; i < size; i++) {
//            Folder lfolder_F = (Folder)llist_SubFolders.get(i);
//            lfolder_F.setUserProfile(userProfile);
//            if (lfolder_F.getCanBeReadByCurrUser()) {
//            	llist_SubFolder.add(lfolder_F);
//            }
//        }
//        return llist_SubFolder;
//    }
    
    /**
     * 获取文件的数量(不包括下级目录的文件).
     * @return
     */
//    public int getDataFileCount() {
//        return this.getFolderService().getDataFileCount(this);
//    }
    
    /**
     * 是否为空目录：没有下级文件夹和文件.
     */
//    public boolean isEmpty() {return getDataFileCount() <= 0 && getSubFolderCount() <= 0;}
    
    /**
     * 是否为空目录. 等同于@see #isEmpty()
     * @return
     */
//    public boolean getIsEmpty() {
//        return isEmpty();
//    }
    
    /**
     * 获取文件列表.
     * @return List实例，每个实例是一个DataFile实例.
     */
//    @SuppressWarnings("unchecked")
//	public List < DataFile > getDataFiles() {
//		return getFolderService().getDataFiles(this);
//	}
    
    /**
     * 获取有访问权限(可以读取的权限)的文件列表.
     * @return List实例，每个实例是一个DataFile实例.
     */
//    @SuppressWarnings("unchecked")
//	public List < DataFile > getAccessibleDataFiles() {
//        List < DataFile > llist_Return = new ArrayList();
//        List < DataFile > llist_Files = this.getDataFiles();
//        int size = llist_Files.size();
//        for (int i = 0; i < size; i++) {
//            DataFile ldfile_F = (DataFile)llist_Files.get(i);
//            ldfile_F.setUserProfile(getUserProfile());
//            if (getReadAllGranted() || ldfile_F.getCanBeReadByCurrUser()) llist_Return.add(ldfile_F);
//        }
//        return llist_Return;
//    }
    
    /**
     * 获取本文件夹的授权记录列表.
     * @return List实例，每个元素是一个FolderAcl实例.
     */
//   @SuppressWarnings("unchecked")
//   public List < FolderAcl > getAclList() {	   
//	   return this.getFolderService().getAclList(this);
//
//    }
    
    /**
     * 获取继承而得的授权记录列表.
     * @return
     */
//    @SuppressWarnings("unchecked")
//    public List < FolderAcl > getInheritedAclList() {
//        List < FolderAcl > llist_Return = new ArrayList();
//        
//        List < FolderAcl > llist_Acl = this.getAclList();
//        for (int i = 0; i < llist_Acl.size(); i++) {
//            FolderAcl lfdacl_Acl = (FolderAcl)llist_Acl.get(i);
//            if (lfdacl_Acl.isInherited()) llist_Return.add(lfdacl_Acl);
//        }
//        
//        return llist_Return;
//    }
    
    /**
     * 获取额外的(即非继承而得)的授权记录列表.
     * @return
     */
//    @SuppressWarnings("unchecked")
//	public List < FolderAcl > getExtraAclList() {
//        List < FolderAcl > llist_Return = new ArrayList();
//        List < FolderAcl > llist_Acl = this.getAclList();
//        for (int i = 0; i < llist_Acl.size(); i++) {
//            FolderAcl lfdacl_Acl = (FolderAcl)llist_Acl.get(i);
//            if (!lfdacl_Acl.isInherited()) llist_Return.add(lfdacl_Acl);
//        }
//        
//        return llist_Return;
//    }
    
    /**
     * 获取所属的文件柜.
     * @return
     */
//    public Filebox getFilebox() {
//        Filebox lfbox_B = this.getFileboxService().getFilebox(getFileboxId());
//        return lfbox_B;
//    }
    
  /*  *//**
     * 获取当前用户对本文件夹的有效访问模式.
     * @return 字符串，参照AccessMode的相关常量.
     */
//    public String getAccessModesByCurrUser() {        
//        if (is_AccessModes == null) {
//            is_AccessModes = this.getFolderService().getAccessModes(getUserProfile(), this);
//        }
//        return is_AccessModes;
//    }
    
    /**
     * 判断是否可以被当前用户修改.
     * @return
     */
//    public boolean getCanBeReadByCurrUser() {
//        String ls_AccessModes = CommonUtil.trim(getAccessModesByCurrUser());
//        
//        return containStr(ls_AccessModes, AccessMode.ACC_MODE_READ)
//           || containStr(ls_AccessModes, AccessMode.ACC_MODE_WRITE)
//           || containStr(ls_AccessModes, AccessMode.ACC_MODE_DELETE)
//           || containStr(ls_AccessModes, AccessMode.ACC_MODE_FULL);
//    } 
    
    /**
     * 判断S1是否包含S2.
     * @param as_S1
     * @param as_S2
     * @return
     */
//    private boolean containStr(String as_S1, String as_S2) {
//        return as_S1.indexOf(as_S2) >= 0;
//    }
    
    /**
     * 判断当前用户是否可以新建文件.
     * @return
     */
//    public boolean getCurrUserCanNewFile() {  
//       String ls_AccessModes = CommonUtil.trim(getAccessModesByCurrUser());
//
//       return containStr(ls_AccessModes, AccessMode.ACC_MODE_NEW) 
//       			|| containStr(ls_AccessModes, AccessMode.ACC_MODE_FULL); 
//    } 
    
    /**
     * 判断是否可以被当前用户修改.
     * @return
     */
    
//    public boolean getCanBeEditedByCurrUser() {  
//       String ls_AccessModes = CommonUtil.trim(getAccessModesByCurrUser());
//        
//       return containStr(ls_AccessModes, AccessMode.ACC_MODE_WRITE)
//       || containStr(ls_AccessModes, AccessMode.ACC_MODE_FULL);
//    } 
    
    /**
     * 判断是否可以被当前用户删除.
     * @return
     */
//    public boolean getCanBeDeletedByCurrUser() { 
//    	 String ls_AccessModes = CommonUtil.trim(getAccessModesByCurrUser());
//         
//         return containStr(ls_AccessModes, AccessMode.ACC_MODE_DELETE)
//            || containStr(ls_AccessModes, AccessMode.ACC_MODE_FULL);
//         
//        
//    }
    
    /**
     * 判断是否可以被当前用户完全控制.
     * @return
     */
//    public boolean getCanBeFullAccessedByCurrUser() {
//        String ls_AccessModes = CommonUtil.trim(getAccessModesByCurrUser());
//        
//        return containStr(ls_AccessModes, AccessMode.ACC_MODE_FULL);
//    } 
    
    /**
     * 判断是否可以被当前用户进行授权. 能对文件夹进行授权的条件为：可以对文件夹进行完全控制，且有“FILE_BOX_FOLDER_AUTH”的操作授权.
     * @return
     */
//    public boolean getCanBeAuthorizedByCurrUser() {
//    	return getCanBeFullAccessedByCurrUser();
//    } 
//    
//    
//    public void setUserProfile(UserProfile ausrprf_U) {
//        if (ausrprf_U != null) {
//        	userProfile = ausrprf_U;
//        	this.setModifiedBy(ausrprf_U.getName());
//        	this.setModifiedById(ausrprf_U.getPersonId());
//        	this.setModifiedDate(new Date());
//        	if(this.getId() == null || CommonUtil.trim(this.getId()).length() <= 0){
//        		this.setCreatedBy(ausrprf_U.getName());
//        		this.setCreatedById(ausrprf_U.getPersonId());
//        		this.setCreatedDate(new Date());
//        	}
//        }
//    }
//    
//    public boolean isNew() {
//    	return CommonUtil.trim(getId()).length() <= 0;
//    }

//	public UserProfile getUserProfile() {
//		return userProfile;
//	}
//	
//	public boolean hasSon(){
//    	return this.getFileboxService().hasSon(this);
//    }
}
