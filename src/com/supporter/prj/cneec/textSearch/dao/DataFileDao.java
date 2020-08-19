package com.supporter.prj.cneec.textSearch.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.textSearch.entity.DataFile;
import com.supporter.prj.cneec.textSearch.entity.DataFileAcl;
import com.supporter.prj.cneec.textSearch.entity.Filebox;
import com.supporter.prj.cneec.textSearch.entity.Folder;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.role.entity.Role;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;

/***
 * 
 * @author JXD
 *
 */
@Repository
public class DataFileDao extends MainDaoSupport<DataFile, String> {

	@Autowired
	private FolderDao folderDao;

	/**
	 * 获取指定用户对指定文件的有效访问模式.
	 * @param ausrprf_U
	 * @param adfile_F
	 * @return
	 */
//	public String getAccessModes(UserProfile ausrprf_U, DataFile dataFile,
//			Map<String, Filebox> fileboxMap, Map<String, Folder> folderMap) {
//		if (dataFile == null) {
//			return "";
//		}
//		//DataFile dataFile = (DataFile) adfile_F;
//
//		String ls_AccessModes = "";
//
//		Filebox lfbox_B = fileboxMap.get(dataFile.getFileboxId());//dataFile.getFilebox();
//		if ((lfbox_B != null) && (ausrprf_U != null)
//				&& (lfbox_B.getManagerId().equals(ausrprf_U.getPersonId())))
//			return "F";
//
//		String ls_HQL = " from DataFileAcl  where fileId = '"
//				+ dataFile.getId() + "'";
//
//		String ls_AclFilter = "";
//		if (ausrprf_U == null) {
//			ls_AclFilter = ls_AclFilter
//					+ " (authorizeeType = 'GROUP' and authorizeeId = '99999999')";
//		} else {
//			String ls_UserGroupIds = "";
//			Role[] roles = ausrprf_U.getRoles();
//			for (int i = 0; i < roles.length; i++) {
//				ls_UserGroupIds = ls_UserGroupIds +"'"+ roles[i].getRoleId()+"'";
//				if (i == roles.length - 1)
//					continue;
//				ls_UserGroupIds = ls_UserGroupIds + ",";
//			}
//			if (ls_UserGroupIds.length() > 0) {
//				if (ls_AclFilter.length() > 0)
//					ls_AclFilter = ls_AclFilter + " or ";
//				ls_AclFilter = ls_AclFilter
//						+ " (authorizeeType = 'GROUP' and (authorizeeId in ("
//						+ ls_UserGroupIds + ") or authorizeeId = '" + 99999999
//						+ "')" + ")";
//			} else {
//				if (ls_AclFilter.length() > 0)
//					ls_AclFilter = ls_AclFilter + " or ";
//				ls_AclFilter = ls_AclFilter
//						+ " (authorizeeType = 'GROUP' and authorizeeId = '99999999')";
//			}
//
//			String ls_DeptIds = CommonUtil.trim(getEmpDeptIds(ausrprf_U
//					.getPersonId()));
//			if (ls_DeptIds.length() > 0) {
//				if (ls_AclFilter.length() > 0)
//					ls_AclFilter = ls_AclFilter + " or ";
//				ls_DeptIds = rewritingIds(ls_DeptIds);
//				ls_AclFilter = ls_AclFilter
//						+ " ( authorizeeType = 'DEPT' and authorizeeId in ("
//						+ ls_DeptIds + ")" + ")";
//			}
//
//			if (ls_AclFilter.length() > 0)
//				ls_AclFilter = ls_AclFilter + " or ";
//			ls_AclFilter = ls_AclFilter
//					+ " ( authorizeeType = 'PERSON' and authorizeeId = '"
//					+ ausrprf_U.getPersonId() + "')";
//		}
//
//		ls_HQL = ls_HQL + " and (" + ls_AclFilter + ")";
//
//		List<DataFileAcl> llist_DataFileAcl = this.find(ls_HQL);
//
//		for (int i = 0; i < llist_DataFileAcl.size(); i++) {
//			DataFileAcl dataFileAcl = (DataFileAcl) llist_DataFileAcl.get(i);
//			if ((dataFileAcl.isCanRead()) && (ls_AccessModes.indexOf("R") < 0)) {
//				ls_AccessModes = ls_AccessModes + "R";
//			}
//
//			if ((dataFileAcl.isCanWrite()) && (ls_AccessModes.indexOf("W") < 0)) {
//				ls_AccessModes = ls_AccessModes + "W";
//			}
//
//			if ((dataFileAcl.isCanDelete())
//					&& (ls_AccessModes.indexOf("X") < 0)) {
//				ls_AccessModes = ls_AccessModes + "X";
//			}
//
//			if ((dataFileAcl.isFullAccess())
//					&& (ls_AccessModes.indexOf("F") < 0)) {
//				ls_AccessModes = ls_AccessModes + "F";
//			}
//		}
//
//		if (ls_AccessModes.indexOf("F") >= 0)
//			return ls_AccessModes;
//
//		if (!dataFile.isOverrideParentAcl()) {
//			Folder lfolder_Parent = folderMap.get(dataFile.getFolderId());//dataFile.getFolder();
//			if (lfolder_Parent != null) {
//				String ls_FolderAccModes = folderDao.getAccessModes(ausrprf_U,
//						lfolder_Parent, fileboxMap);
//
//				if (ls_FolderAccModes.indexOf("F") >= 0) {
//					return "F";
//				}
//
//				for (int i = 0; i < ls_FolderAccModes.length(); i++) {
//					String ls_AccMode = ls_FolderAccModes.substring(i, i + 1);
//					if (ls_AccessModes.indexOf(ls_AccMode) < 0) {
//						ls_AccessModes = ls_AccessModes + ls_AccMode;
//					}
//				}
//			}
//
//		}
//
//		return ls_AccessModes;
//	}
	public Map<String, String> getAccessModes(UserProfile ausrprf_U, List<DataFile> dataFileList,
			Map<String, Filebox> fileboxMap, Map<String, Folder> folderMap, Map<String, String> dataFileAclMap, 
			Map<String, String> folderAclMap, List<Folder> folderList) {
		String ls_AccessModes = "";
		Map<String, String> accessModesMap = new HashMap<String, String>();
		Map<String, String> folderAccessModesMap = folderDao.getAccessModes(ausrprf_U,
				folderList, fileboxMap, folderAclMap);
		for(DataFile dataFile : dataFileList){
			String id = dataFile.getId();
			Filebox lfbox_B = fileboxMap.get(dataFile.getFileboxId());
			if ((lfbox_B != null) && (ausrprf_U != null)
					&& (lfbox_B.getManagerId().equals(ausrprf_U.getPersonId()))){
				accessModesMap.put(id, "F");
				continue;
			}else{
				ls_AccessModes = "";
				if(dataFileAclMap.containsKey(id)){
					ls_AccessModes = dataFileAclMap.get(id);
				}
				if (ls_AccessModes.indexOf("F") >= 0){
					accessModesMap.put(id, "F");
					continue;
				}else{
					if (!dataFile.isOverrideParentAcl()) {
						Folder lfolder_Parent = folderMap.get(dataFile.getFolderId());//dataFile.getFolder();
						String folderId = dataFile.getFolderId();
						if (lfolder_Parent != null) {
							String ls_FolderAccModes = folderAccessModesMap.get(folderId);
							if (ls_FolderAccModes.indexOf("F") >= 0) {
								accessModesMap.put(id, "F");
								continue;
							}else{
								for (int i = 0; i < ls_FolderAccModes.length(); i++) {
									String ls_AccMode = ls_FolderAccModes.substring(i, i + 1);
									if (ls_AccessModes.indexOf(ls_AccMode) < 0) {
										ls_AccessModes = ls_AccessModes + ls_AccMode;
									}
								}
							}
						}

					}
					accessModesMap.put(id, ls_AccessModes);
				}
			}
		}
		return accessModesMap;
	}
	public Map<String, String> getDataFileAclMap(UserProfile ausrprf_U){
		String ls_HQL = " from DataFileAcl  where 1=1 ";
			//fileId = '" + dataFile.getId() + "'"

		String ls_AclFilter = "";
		if (ausrprf_U == null) {
			ls_AclFilter = ls_AclFilter
					+ " (authorizeeType = 'GROUP' and authorizeeId = '99999999')";
		} else {
			String ls_UserGroupIds = "";
			Role[] roles = ausrprf_U.getRoles();
			for (int i = 0; i < roles.length; i++) {
				ls_UserGroupIds = ls_UserGroupIds +"'"+ roles[i].getRoleId()+"'";
				if (i == roles.length - 1)
					continue;
				ls_UserGroupIds = ls_UserGroupIds + ",";
			}
			if (ls_UserGroupIds.length() > 0) {
				if (ls_AclFilter.length() > 0)
					ls_AclFilter = ls_AclFilter + " or ";
				ls_AclFilter = ls_AclFilter
						+ " (authorizeeType = 'GROUP' and (authorizeeId in ("
						+ ls_UserGroupIds + ") or authorizeeId = '" + 99999999
						+ "')" + ")";
			} else {
				if (ls_AclFilter.length() > 0)
					ls_AclFilter = ls_AclFilter + " or ";
				ls_AclFilter = ls_AclFilter
						+ " (authorizeeType = 'GROUP' and authorizeeId = '99999999')";
			}
	
			String ls_DeptIds = CommonUtil.trim(getEmpDeptIds(ausrprf_U
					.getPersonId()));
			if (ls_DeptIds.length() > 0) {
				if (ls_AclFilter.length() > 0)
					ls_AclFilter = ls_AclFilter + " or ";
				ls_DeptIds = rewritingIds(ls_DeptIds);
				ls_AclFilter = ls_AclFilter
						+ " ( authorizeeType = 'DEPT' and authorizeeId in ("
						+ ls_DeptIds + ")" + ")";
			}
	
			if (ls_AclFilter.length() > 0)
				ls_AclFilter = ls_AclFilter + " or ";
			ls_AclFilter = ls_AclFilter
					+ " ( authorizeeType = 'PERSON' and authorizeeId = '"
					+ ausrprf_U.getPersonId() + "')";
		}
	
		ls_HQL = ls_HQL + " and (" + ls_AclFilter + ")";
	
		List<DataFileAcl> llist_DataFileAcl = this.find(ls_HQL);
		String ls_AccessModes = "";
		Map<String, String> accessModesMap = new HashMap<String, String>();
		for (int i = 0; i < llist_DataFileAcl.size(); i++) {
			DataFileAcl dataFileAcl = (DataFileAcl) llist_DataFileAcl.get(i);
			String fileId = dataFileAcl.getFileId();
			String accessModes = "";
			if ((dataFileAcl.isCanRead())) {
				accessModes = "R";
			}else if ((dataFileAcl.isCanWrite())) {
				accessModes = "W";
			}else if ((dataFileAcl.isCanDelete())) {
				accessModes = "X";
			}else if ((dataFileAcl.isFullAccess())) {
				accessModes = "F";
			}
			if (!accessModesMap.containsKey(fileId)) {
				accessModesMap.put(fileId, accessModes);
			}else{
				ls_AccessModes = accessModesMap.get(fileId);
				if(ls_AccessModes.indexOf(accessModes) < 0){
					ls_AccessModes = ls_AccessModes + accessModes;
					accessModesMap.put(fileId, ls_AccessModes);
				}
			}
		}
		return accessModesMap;
	}
	private String getEmpDeptIds(String as_EmpId) {
		Person lemp_E = EIPService.getEmpService().getEmp(as_EmpId);
		if (lemp_E != null) {
			String ls_DeptIds = lemp_E.getDeptId();
			return ls_DeptIds;
		}

		return "";
	}

	public String rewritingIds(String ids) {
		if (CommonUtil.trim(ids).length() <= 0)
			return "";
		String rewritingIds = "";
		String[] arrIds = ids.split(",");
		for (int i = 0; i < arrIds.length; i++) {
			rewritingIds = rewritingIds + "'" + CommonUtil.trim(arrIds[i])
					+ "'";
			if (i >= arrIds.length - 1)
				continue;
			rewritingIds = rewritingIds + ",";
		}

		return rewritingIds;
	}
	
	public List<DataFile> getDataFileList(){
    	return this.find("from DataFile");
	}
}
