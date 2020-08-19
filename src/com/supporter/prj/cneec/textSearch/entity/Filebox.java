package com.supporter.prj.cneec.textSearch.entity;
 
//import java.util.Date;
//import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.textSearch.entity.base.BaseFilebox;
//import com.supporter.prj.core.spring.SpringContextHolder;
//import com.supporter.prj.eip_service.EIPService;
//import com.supporter.prj.filebox.base.BaseFilebox;
//import com.supporter.prj.filebox.service.FileboxImpl;
//import com.supporter.prj.filebox.service.FolderImpl;
//import com.supporter.security.AccessMode;
//import com.supporter.security.UserProfile;
//import com.supporter.util.CommonUtil;


/**
 * 封装一个文件柜.
 * @author 
 *
 */
@Entity
@Table(name = "fbox_filebox", schema = "")
public class Filebox extends BaseFilebox{
	private static final long serialVersionUID = 1L;
//	public static final String RESOURCE_TYPE_CODE = "FILE_BOX";
//	private UserProfile userProfile = null;
//	private String is_AccessModes = null;
	
//	public static final String TREE_ROOT_ID = "filebox";
	

//	public FolderImpl getFolderService(){
//		return SpringContextHolder.getBean(FolderImpl.class);
//	}
//
//	public FileboxImpl getFileboxService(){
//		return SpringContextHolder.getBean(FileboxImpl.class);
//	}

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Filebox () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Filebox (java.lang.String id) {
		super(id);
	}

/*[CONSTRUCTOR MARKER END]*/
	
//	public String getManagerName(){
//		if(getManagerId() != null && CommonUtil.trim(getManagerId()).length() > 0){
//			return EIPService.getEmpService().getEmp(getManagerId()).getName();
//		}
//		return "";
//	}
     
    /**
     * 获取根目录.
     * @return
     */
//    public Folder getRootFolder() {
//    	Folder folder = this.getFolderService().getRootFolder(this);
//    	folder.setUserProfile(getUserProfile());
//        return folder;
//    }
    
    /**
     * 返回所有文件柜列表.
     * @deprecated
     */
//    public List < Filebox > getAllFilebox(){
//    	return this.getFileboxService().getAllFileboxes();
//    } 
    
    /**
     * 获取文件柜下级文件夹数量.
     * @return
     */
//    public int getSubFolderCount(){
//		return this.getFileboxService().getSubFolderCount(this.getId());
//    	
//    }
    
    /**
     * 获取本文件柜的授权记录列表.
     * @return List实例，每个元素是一个FileboxAcl实例.
     */
//   @SuppressWarnings("unchecked")
//   public List < FileboxAcl > getAclList() {
//	   return this.getFileboxService().getAclList(this);
//
//    }
   
   /**
    * 获取当前用户对本文件夹的有效访问模式.
    * @return 字符串，参照AccessMode的相关常量.
    */
//   public String getAccessModesByCurrUser() {
////	   System.out.println("==取权限字符开始==" + CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
////	   System.out.println("...." + this.getId());
//	   try {
//       if (is_AccessModes == null) {
//           is_AccessModes = this.getFileboxService().getAccessModes(getUserProfile(), this);
//       }
//	   } catch (Exception e){
//		   e.getStackTrace();
//	   }
////	   System.out.println("==取权限字符结束==" + CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//       return is_AccessModes;
//   }
   
   /**
    * 判断是否可以被当前用户读取.
    * @return
    */
//   public boolean getCanBeReadByCurrUser() {
//       String ls_AccessModes = CommonUtil.trim(getAccessModesByCurrUser());
//
//       return containStr(ls_AccessModes, AccessMode.ACC_MODE_READ)
//          || containStr(ls_AccessModes, AccessMode.ACC_MODE_WRITE)
//          || containStr(ls_AccessModes, AccessMode.ACC_MODE_FULL);
//   } 
   
   /**
    * 判断S1是否包含S2.
    * @param as_S1
    * @param as_S2
    * @return
    */
//   private boolean containStr(String as_S1, String as_S2) {
//       return as_S1.indexOf(as_S2) >= 0;
//   }
  
   /**
    * 判断是否可以被当前用户修改.
    * @return
    */
   
//   public boolean getCanBeEditedByCurrUser() {  
//      String ls_AccessModes = CommonUtil.trim(getAccessModesByCurrUser());
//       
//      return containStr(ls_AccessModes, AccessMode.ACC_MODE_WRITE)
//      || containStr(ls_AccessModes, AccessMode.ACC_MODE_FULL);
//   } 
  
   /**
    * 判断是否可以被当前用户完全控制.
    * @return
    */
//   public boolean getCanBeFullAccessedByCurrUser() {
//       String ls_AccessModes = CommonUtil.trim(getAccessModesByCurrUser());
//       
//       return containStr(ls_AccessModes, AccessMode.ACC_MODE_FULL);
//   } 
   
   /**
    * 判断是否可以被当前用户进行授权. 能对文件夹进行授权的条件为：可以对文件夹进行完全控制，且有“FILE_BOX_FOLDER_AUTH”的操作授权.
    * @return
    */
//   public boolean getCanBeAuthorizedByCurrUser() {
//   	return getCanBeFullAccessedByCurrUser();
//   } 
   

//   public void setUserProfile(UserProfile ausrprf_U) {
//       if (ausrprf_U != null) {
//    	    this.userProfile = ausrprf_U;
//	       	this.setModifiedBy(ausrprf_U.getName());
//	       	this.setModifiedById(ausrprf_U.getPersonId());
//	       	this.setModifiedDate(new Date());
//	       	if(this.getId() == null || CommonUtil.trim(this.getId()).length() <= 0){
//	       		this.setCreatedBy(ausrprf_U.getName());
//	       		this.setCreatedById(ausrprf_U.getPersonId());
//	       		this.setCreatedDate(new Date());
//       	}
//       }
//   }
   
//   public UserProfile getUserProfile() {
//		return userProfile;
//	}
//   
   /**
    * 获取有访问权限(可以读取的权限)的文件柜列表.
    * @return List实例，每个实例是一个Filebox实例. 
    * @deprecated @see {@link #getAccessibleFileboxes()}
    */
//   @SuppressWarnings("unchecked")
//   public List < Filebox > getAccessibleFilebox() {
//       return getAccessibleFileboxes();
//   }
//   
   /**
    * 获取有访问权限(可以读取的权限)的文件柜列表.
    * @return List实例，每个实例是一个Filebox实例. 
    */
//   @SuppressWarnings("unchecked")
//   public List < Filebox > getAccessibleFileboxes() {
//       return this.getFileboxService().getAccessibleFileboxes(getUserProfile());
//   }
//   
//   public String createdDateDesc(){
//	   if (getCreatedDate() != null) {
//		   return CommonUtil.format(getCreatedDate(), "yyyy-MM-dd");
//	   }
//	   return "";
//   } 
}
