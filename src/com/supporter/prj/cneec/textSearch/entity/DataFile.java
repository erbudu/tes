package com.supporter.prj.cneec.textSearch.entity;

//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;


import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.textSearch.entity.base.BaseDataFile;
//import com.supporter.prj.core.spring.SpringContextHolder;
//import com.supporter.prj.filebox.service.DataFileImpl;
//import com.supporter.prj.filebox.service.FileboxImpl;
//import com.supporter.prj.filebox.service.FolderImpl;
//import com.supporter.security.AccessMode;
//import com.supporter.security.UserProfile;


/**
 * DataFile.
 * @author
 * 
 */
@Entity
@Table(name = "fbox_file", schema = "")
public class DataFile extends BaseDataFile  {
	
	private static final long serialVersionUID = 1L;

	

/*[CONSTRUCTOR MARKER BEGIN]*/
	/**
	 * 构造方法. 
	 */
	public DataFile() {
		super();
	}

	/**
	 * 构造方法.
	 * @param id id值
	 */
	public DataFile(java.lang.String id) {
		super(id);
	}

/*[CONSTRUCTOR MARKER END]*/
	
//	private UserProfile userProfile = null;
//	private String is_AccessModes = null;
	
//	@Transient
//	public FileboxImpl getFileboxService(){
//		return SpringContextHolder.getBean(FileboxImpl.class);
//	}
//	
//	public FolderImpl getFolderService(){
//		return SpringContextHolder.getBean(FolderImpl.class);
//	}
//	
//	private DataFileImpl getDataFileService(){
//		return SpringContextHolder.getBean(DataFileImpl.class);
//	}
//
//	public Filebox getFilebox() {
//		return this.getFileboxService().getFilebox(getFileboxId()); 
//	}
//	
//	 public Folder getFolder() {
//	        return this.getFolderService().getFolder(getFolderId(),getUserProfile());
//	 }
	 
	 /**
	     * 获取所在目录的路径.
	     * @return
	     */
//	    public String getFolderPath() {
//	        Folder lfolder_F = this.getFolder();
//	        if (lfolder_F == null) {
//	            return null;
//	        } else {
//	            return lfolder_F.getFolderPath();
//	        } 
//	    }
	    
	    /**
	     * 获取文件的图标名. 用于在文件列表显示时使用
	     * @return 例如doc类型的返回"doc"，"xls"的返回"xls"；如果出现尚未在本中注册的，那么返回"file".
	     */
//	    public String getFileIcon() {
//	        String[] larrs_KnownFileTypes = new String[] {
//	                "bmp","class","dll","doc","exe",
//	                "gif","htm","html",
//	                "jar","java",
//	                "jpg","mht","pdf","ppt","rar",
//	                "txt","vsd","xml","xls"
//	        };
//	        
//	        String ls_FileExt = CommonUtil.trim(this.getFileExt()).toLowerCase();
//	        
//	        for (int i = 0; i < larrs_KnownFileTypes.length; i++) {
//	            if (larrs_KnownFileTypes[i].equals(ls_FileExt)) return ls_FileExt;
//	        }
//	        
//	        return "file";
//	    }
//	    
	    /**
	     * 获取本文件的授权记录列表.
	     * @return List实例，每个元素是一个DataFileAcl实例.
	     */
//	    public List < DataFileAcl > getAclList() {
//	        return this.getDataFileService().getAclList(this.getId());
//	    }
	    
	    /**
	     * 获取继承而得的授权记录列表.
	     * @return
	     */
//	    @SuppressWarnings("unchecked")
//	    public List < DataFileAcl > getInheritedAclList() {
//	        List < DataFileAcl > llist_Return = new ArrayList();
//	        
//	        List < DataFileAcl > llist_Acl = this.getAclList();
//	        for (int i = 0; i < llist_Acl.size(); i++) {
//	            DataFileAcl lacl_A = (DataFileAcl)llist_Acl.get(i);
//	            if (lacl_A.isInherited()) llist_Return.add(lacl_A);
//	        }
//	        
//	        return llist_Return;
//	    }
	    
	    /**
	     * 获取额外的(即非继承而得)的授权记录列表.
	     * @return
	     */
//	    @SuppressWarnings("unchecked")
//		public List < DataFileAcl > getExtraAclList() {
//	        List < DataFileAcl > llist_Return = new ArrayList();
//	        List < DataFileAcl > llist_Acl = getAclList();
//	        for (int i = 0; i < llist_Acl.size(); i++) {
//	            DataFileAcl lacl_A = (DataFileAcl)llist_Acl.get(i);
//	            if (!lacl_A.isInherited()) llist_Return.add(lacl_A);
//	        }
//	        return llist_Return;
//	    }
	    
	    /**
	     * 获取当前用户对本文件的有效访问模式.
	     * @return 字符串，参照AccessMode的相关常量.
	     */
//	    public String getAccessModesByCurrUser() {  
//	        if (is_AccessModes == null) {
//	            is_AccessModes = this.getDataFileService().getAccessModes(getUserProfile(), this);
//	        }
//	        return is_AccessModes;
//	    }
	    
	    /**
	     * 判断是否可以被当前用户修改.
	     * @return
	     */
//	    public boolean getCanBeReadByCurrUser() {
//	        String ls_AccessModes = CommonUtil.trim(getAccessModesByCurrUser());
//	        
//	        return containStr(ls_AccessModes, AccessMode.ACC_MODE_READ)
//	           || containStr(ls_AccessModes, AccessMode.ACC_MODE_WRITE)
//	           || containStr(ls_AccessModes, AccessMode.ACC_MODE_DELETE)
//	           || containStr(ls_AccessModes, AccessMode.ACC_MODE_FULL);
//	    } 
	    
	    /**
	     * 判断S1是否包含S2.
	     * @param as_S1
	     * @param as_S2
	     * @return
	     */
//	    private boolean containStr(String as_S1, String as_S2) {
//	        return as_S1.indexOf(as_S2) >= 0;
//	    }
//	    
	    /**
	     * 判断是否可以被当前用户修改.
	     * @return
	     */
//	    public boolean getCanBeEditedByCurrUser() { 
//	        //如果已经被锁住，且加锁人不是当前人，那么直接返回false
//	        if (isLocked() && !CommonUtil.trim(getLockedById()).equals(getUserProfile().getPersonId())){
//	        	return false;
//	        }
////	        String ls_AccessModes = CommonUtil.trim(getAccessModesByCurrUser());
//	        return true;
//	    }
	    
	    /**
	     * 判断是否可以被当前用户在线修改.
	     * @return
	     */
//	    public boolean getCanBeOnlineEditedByCurrUser() { 
//	        String ls_FileExt = CommonUtil.trim(this.getFileExt());
//	        if (!ls_FileExt.equalsIgnoreCase("doc") 
//	                && !ls_FileExt.equalsIgnoreCase("ppt") 
//	                && !ls_FileExt.equalsIgnoreCase("xls")) {
//	            return false;
//	        }
//	        return getCanBeEditedByCurrUser();
//	    }
	    
	    /**
	     * 判断是否可以被当前用户删除.
	     * @return
	     */
//	    public boolean getCanBeDeletedByCurrUser() { 
//	        //如果已经被锁住，且加锁人不是当前人，那么直接返回false
//	        if (isLocked() && !CommonUtil.trim(getLockedById()).equals(getUserProfile().getPersonId())) {
//	        	return false;
//	        }
//	        
//	        //自己建的单子，自己可删.
//	        if (getUserProfile() != null 
//	        		&& CommonUtil.trim(getUserProfile().getPersonId()).equals(getCreatedById())) {
//	        	return true;
//	        }
//	        
//	        String ls_AccessModes = CommonUtil.trim(getAccessModesByCurrUser());
//	        return containStr(ls_AccessModes, AccessMode.ACC_MODE_DELETE)
//	           || containStr(ls_AccessModes, AccessMode.ACC_MODE_FULL);
//	    }
	    
	    /**
	     * 判断是否可以被当前用户完全控制.
	     * @return
	     */
//	    public boolean getCanBeFullAccessedByCurrUser() { 
//	        String ls_AccessModes = CommonUtil.trim(getAccessModesByCurrUser()); 
//	        return containStr(ls_AccessModes, AccessMode.ACC_MODE_FULL);
//	    }
	    
	    /**
	     * 判断是否可以被当前用户加锁/解锁.
	     * 文件柜的管理员可以对任何人加的锁进行解锁
	     * @return
	     */
//	    public boolean getCanBeLockedByCurrUser() { 
//	        if (getUserProfile() == null) return false;
//	        //如果是文件柜的管理员，那么可以进行加锁/解锁
//	        String ls_CurrPersonId = CommonUtil.trim(getUserProfile().getPersonId());
//	        Filebox lfbox_B = this.getFilebox();
//	        if (lfbox_B != null && CommonUtil.trim(lfbox_B.getManagerId()).equals(ls_CurrPersonId)) return true;
//	        //如果已经被锁住，且加锁人不是当前人，那么直接返回false        
//	        if (isLocked() && !CommonUtil.trim(getLockedById()).equals(ls_CurrPersonId)) return false;
//	        return getCanBeFullAccessedByCurrUser();
//	    }
//	    
//	    public void setUserProfile(UserProfile ausrprf_U) {
//	    	if(ausrprf_U != null){
//		    	userProfile = ausrprf_U;
//		        this.setModifiedBy(ausrprf_U.getName());
//		        this.setModifiedById(ausrprf_U.getPersonId());
//		        this.setModifiedDate(new Date());
//		        if(this.getId() == null || CommonUtil.trim(this.getId()).length() <= 0){
//		        	this.setCreatedBy(ausrprf_U.getName());
//		        	this.setCreatedById(ausrprf_U.getPersonId());
//		        	this.setCreatedDate(new Date());
//		        }
//	    	}
//	    }
	    
	    /**
	     * 判断是否可以被当前用户进行授权. 能对文件进行授权的条件为：可以对文件进行完全控制，且有“FILE_BOX_FILE_AUTH”的操作授权.
	     * @return
	     */
//	    public boolean getCanBeAuthorizedByCurrUser() {
//	        return getCanBeFullAccessedByCurrUser();
//	    }
//
//		public UserProfile getUserProfile() {
//			return userProfile;
//		}

	
}
