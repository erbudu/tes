package com.supporter.prj.cneec.textSearch.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.textSearch.entity.base.BaseDataFileAcl;


/**
 * 封装一个针对某文件的授权记录.
 * @author
 *
 */
@Entity
@Table(name = "fbox_file_acl", schema = "")
public class DataFileAcl extends BaseDataFileAcl {
	private static final long serialVersionUID = 1L;
    
//    private static final int UNINITIALIZED = -100;
//    private int ii_OverrideParentAcl = UNINITIALIZED; //未初始化时为-100
//    
//    public static final String PERSON = "PERSON";
//    public static final String GROUP =  "GROUP";
//    public static final String DEPT = "DEPT";
//    private DataFileImpl dataFileService = null;
//    private DataFileAclImpl dataFileAclService = null;

/*[CONSTRUCTOR MARKER BEGIN]*/
    /**
     * 构造方法.
     */
	public DataFileAcl(){
		super();
	}

	/**
	 * Constructor for primary key.
	 */
	public DataFileAcl(java.lang.String id){
		super(id);
	}

/*[CONSTRUCTOR MARKER END]*/
	
//	public DataFileImpl getDataFileImpl(){
//		if (dataFileService == null)
//		dataFileService = SpringContextHolder.getBean(DataFileImpl.class);
//		return dataFileService;
//	}
//	public DataFileAclImpl getDataFileAclImpl(){
//		if (dataFileAclService == null)
//			dataFileAclService = SpringContextHolder.getBean(DataFileAclImpl.class);
//		return dataFileAclService;
//	} 
//	
//	 /**
//     * 获取权限设置的描述性文字.
//     * @return
//     */
//    public String getAclDesc() {
//        if (this.isFullAccess()) return "全部权限";
//        
//        String ls_Desc = "";
//        if (this.isCanRead()) ls_Desc += "可读取";
//        if (this.isCanWrite()) {
//            if (ls_Desc.length() > 0) ls_Desc += ",";
//            ls_Desc += "可修改";
//        }
//        if (this.isCanDelete()) {
//            if (ls_Desc.length() > 0) ls_Desc += ",";
//            ls_Desc += "可删除";
//        }
//        
//        return ls_Desc;
//    }
//    
//    public static CodeTable getAuthorizeeTypeCodeTable() {
//        CodeTable lcdtbl_Return = new CodeTable();
//        
//        lcdtbl_Return.insertItem(DEPT,"部门",0);
//        lcdtbl_Return.insertItem(GROUP,"用户组",0);
//        lcdtbl_Return.insertItem(PERSON,"人员",0);
//        
//        return lcdtbl_Return;
//    }
//    
//    public void setOverrideParentAcl(boolean abool_OverrideParentAcl) {
//        if (abool_OverrideParentAcl) {
//            ii_OverrideParentAcl = 1;
//        } else {
//            ii_OverrideParentAcl = 0;
//        } 
//    }
//
//    public boolean getOverrideParentAcl() {
//        //如果尚未初始化，那么需要获取一下
//        if (ii_OverrideParentAcl == UNINITIALIZED) {
//            DataFile ldfile_F = this.getDataFileImpl().getDataFile(getFileId());
//            if (ldfile_F == null) {
//                setOverrideParentAcl(false);
//            } else {
//                setOverrideParentAcl(ldfile_F.isOverrideParentAcl());
//            }
//        }
//        
//        return ii_OverrideParentAcl == 1;
//    }
//    
//    public void update() {
//    	this.getDataFileAclImpl().update(this);
//        
//        //刷新所属文件的“overrideParentAcl”属性
//        DataFile ldfile_F = this.getDataFileImpl().getDataFile(getFileId());
//        if (ldfile_F != null) {
//            ldfile_F.setOverrideParentAcl(this.getOverrideParentAcl());
//            this.getDataFileImpl().updateInstance(ldfile_F);
//        } 
//    }
//    
//    /**
//     * 获取被授权者的名称(姓名).
//     * @return
//     */
//   public String getAuthorizeeName() {
//	   String ls_AuthorizeeId = CommonUtil.trim(getAuthorizeeId());
//	   String ls_AuthorizzType = CommonUtil.trim(getAuthorizeeType());
//        if (DEPT.equals(ls_AuthorizzType)) {
//            return EIPService.getDeptService().getDept(ls_AuthorizeeId).getName();
//        } else if (GROUP.equals(ls_AuthorizzType)) {
//        	int groupId = CommonUtil.parseInt(ls_AuthorizeeId);
//            Role lgr_Group = EIPService.getRoleService().getRole(groupId);
//            EIPService.getRoleService().getRole(groupId);
//            if (lgr_Group != null) {
//                return lgr_Group.getName();
//            } else {
//                return null;
//            }
//        } else if (PERSON.equals(ls_AuthorizzType)) {
//            return EIPService.getEmpService().getEmp(ls_AuthorizeeId).getName();
//        } 
//        
//        return null;
//    }

}
