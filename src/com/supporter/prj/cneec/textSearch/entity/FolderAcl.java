package com.supporter.prj.cneec.textSearch.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.textSearch.entity.base.BaseFolderAcl;


/**
 * 封装一个部门资源的授权记录.
 * @author hsh
 *
 */
@Entity
@Table(name = "fbox_folder_acl", schema = "")
public class FolderAcl extends BaseFolderAcl {
	private static final long serialVersionUID = 1L;
    
//    private static final int UNINITIALIZED = -100;
//    private int ii_OverrideParentAcl = UNINITIALIZED; //未初始化时为-100
//    
//    public static final String PERSON = "PERSON";
//    public static final String GROUP = "GROUP";
//    public static final String DEPT = "DEPT";

   
    
    
/*[CONSTRUCTOR MARKER BEGIN]*/
    /**
     * 构造方法.
     */
	public FolderAcl(){
		super();
	}

	/**
	 * Constructor for primary key.
	 */
	public FolderAcl(java.lang.String id){
		super(id);
	}

/*[CONSTRUCTOR MARKER END]*/
	
//	public FolderImpl getFolderService(){
//    	return SpringContextHolder.getBean(FolderImpl.class);
//    }
//    
//    public FolderAclImpl getFolderAclService(){
//    	return SpringContextHolder.getBean(FolderAclImpl.class);
//    }
//	 public String getFolderName() {
//	        Folder lfolder_F = this.getFolderService().getFolder(getFolderId());
//	        if (lfolder_F != null) {
//	            return lfolder_F.getFolderName();
//	        } else {
//	            return null;
//	        }
//	    }
//	    
//	    public Folder getFolder() {
//	        return this.getFolderService().getFolder(getFolderId()); 
//	    }
//	    
//	    /**
//	     * 获取被授权者的名称(姓名).
//	     * @return
//	     */
//	   public String getAuthorizeeName() {
//		   String ls_AuthorizeeId = CommonUtil.trim(getAuthorizeeId());
//		   String ls_AuthorizzType = CommonUtil.trim(getAuthorizeeType());
//	        if (DEPT.equals(ls_AuthorizzType)) {
//	            return EIPService.getDeptService().getDept(ls_AuthorizeeId).getName();
//	        } else if (GROUP.equals(ls_AuthorizzType)) {
//	        	int groupId = CommonUtil.parseInt(ls_AuthorizeeId);
//	            Role lgr_Group = EIPService.getRoleService().getRole(groupId);
//	            if (lgr_Group != null) {
//	                return lgr_Group.getName();
//	            } else {
//	                return null;
//	            }
//	        } else if (PERSON.equals(ls_AuthorizzType)) {
//	            return EIPService.getEmpService().getEmp(ls_AuthorizeeId).getName();
//	        } 
//	        
//	        return null;
//	    }
//	    
//	  
//	    
//	    /**
//	     * 获取权限设置的描述性文字.
//	     * @return
//	     */
//	    public String getAclDesc() {
//	        if (isFullAccess()) return "全部权限";
//	        
//	        String ls_Desc = "";
//	        if (isCanRead()) ls_Desc += "可读取";
//	        if (isCanNewFile()) {
//	            if (ls_Desc.length() > 0) ls_Desc += ",";
//	            ls_Desc += "可上载文件";
//	        }
//	        if (isCanWrite()) {
//	            if (ls_Desc.length() > 0) ls_Desc += ",";
//	            ls_Desc += "可修改";
//	        }
//	        if (isCanDelete()) {
//	            if (ls_Desc.length() > 0) ls_Desc += ",";
//	            ls_Desc += "可删除";
//	        }
//	        
//	        return ls_Desc;
//	    }
//	    
//	    public static CodeTable getAuthorizeeTypeCodeTable() {
//	        CodeTable lcdtbl_Return = new CodeTable();
//	        
//	   /*     lcdtbl_Return.insertItem(DEPT,"部门",0);
//	        lcdtbl_Return.insertItem(GROUP,"用户组",0);
//	        lcdtbl_Return.insertItem(PERSON,"人员",0);*/
//	        
//	        return lcdtbl_Return;
//	    }
//
//	    public void setOverrideParentAcl(boolean abool_OverrideParentAcl) {
//	        if (abool_OverrideParentAcl) {
//	            ii_OverrideParentAcl = 1;
//	        } else {
//	            ii_OverrideParentAcl = 0;
//	        } 
//	    }
//
//	    public boolean getOverrideParentAcl() {
//	        //如果尚未初始化，那么需要获取一下
//	        if (ii_OverrideParentAcl == UNINITIALIZED) {
//	            Folder lfolder_F = getFolder();
//	            if (lfolder_F == null) {
//	                setOverrideParentAcl(false);
//	            } else {
//	                setOverrideParentAcl(lfolder_F.isOverrideParentAcl());
//	            }
//	        }
//	        
//	        return ii_OverrideParentAcl == 1;
//	    }
//	    
//	    public void update() {
//	    	this.getFolderAclService().update(this);
//	        
//	        //刷新所属目录的“overrideParentAcl”属性
//	        Folder lfolder_F = this.getFolderService().getFolder(getFolderId());
//	        if (lfolder_F != null) {
//	            lfolder_F.setOverrideParentAcl(this.getOverrideParentAcl());
//	            this.getFolderService().update(lfolder_F,null);
//	        } 
//	    }

}
