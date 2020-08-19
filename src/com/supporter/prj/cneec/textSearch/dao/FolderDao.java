package com.supporter.prj.cneec.textSearch.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.textSearch.entity.DataFile;
import com.supporter.prj.cneec.textSearch.entity.DataFileAcl;
import com.supporter.prj.cneec.textSearch.entity.Filebox;
import com.supporter.prj.cneec.textSearch.entity.Folder;
import com.supporter.prj.cneec.textSearch.entity.FolderAcl;
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
public class FolderDao extends MainDaoSupport<Folder, String> {

	//	public Folder getFolderById(String id) {
	//		StringBuffer hql = new StringBuffer("from "+Folder.class.getName()+" where id = '"+id+"' ");
	//		List<Folder> list = this.find(hql.toString());
	//		if (list == null || list.size() == 0)
	//			return null;
	//		return list.get(0);
	//	}

//	private boolean isFileboxManager(Filebox filebox, UserProfile ausrprf_U) {
//		boolean isManager = false;
//		if ((ausrprf_U != null) && (filebox != null)) {
//			isManager = CommonUtil.trim(filebox.getManagerId()).equals(
//					CommonUtil.trim(ausrprf_U.getPersonId()));
//		}
//		return isManager;
//	}

	public Map<String, String> getAccessModes(UserProfile ausrprf_U, List<Folder> folderList,
			Map<String, Filebox> fileboxMap, Map<String, String> folderAclMap) {
		Map<String, String> accessModesMap = new HashMap<String, String>();
		for(Folder folder : folderList){
			String id = folder.getId();
			Filebox lfbox_B = fileboxMap.get(folder.getFileboxId());
			if ((lfbox_B != null) && (ausrprf_U != null)
					&& (lfbox_B.getManagerId().equals(ausrprf_U.getPersonId()))){
				accessModesMap.put(id, "F");
				continue;
			}else{
				String folderAcl = "";
				if(accessModesMap.containsKey(id)){
					folderAcl = folderAclMap.get(id);
				}
				if (folderAcl.indexOf("F") >= 0){
					accessModesMap.put(id, "F");
					continue;
				}else{
					accessModesMap.put(id, folderAcl);
				}
			}
		}
		return accessModesMap;
	}
	public Map<String, String> getFolderAclMap(UserProfile ausrprf_U){
		String ls_HQL = "from FolderAcl  where 1=1 ";//folderId = '"	+ afolder_F.getId() + "'"
		ls_HQL = ls_HQL + " and (" + getAclFilter(ausrprf_U) + ")";
		List<FolderAcl> llist_FolderAcl = find(ls_HQL);
		String ls_AccessModes = "";
		Map<String, String> accessModesMap = new HashMap<String, String>();
		for (int i = 0; i < llist_FolderAcl.size(); i++) {
			FolderAcl folderAcl = (FolderAcl) llist_FolderAcl.get(i);
			String fileId = folderAcl.getFolderId();
			String accessModes = "";
			if ((folderAcl.isCanRead())) {
				accessModes = "R";
			}else if(folderAcl.isCanNewFile()){
				accessModes = "N";
			}else if ((folderAcl.isCanWrite())) {
				accessModes = "W";
			}else if ((folderAcl.isCanDelete())) {
				accessModes = "X";
			}else if ((folderAcl.isFullAccess())) {
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

	private String getAclFilter(UserProfile ausrprf_U) {
		String ls_AclFilter = "";
		if (ausrprf_U == null) {
			ls_AclFilter = ls_AclFilter
					+ " (authorizeeType = 'GROUP' and authorizeeId = '99999999')";
		} else {
			Role[] larr_Roles = ausrprf_U.getRoles();
			String ls_UserGroupIds = "";
			for (int i = 0; i < larr_Roles.length; i++) {
				if (i > 0)
					ls_UserGroupIds = ls_UserGroupIds + ",";
				ls_UserGroupIds = ls_UserGroupIds + "'"
						+ larr_Roles[i].getRoleId() + "'";
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
					+ CommonUtil.trim(ausrprf_U.getPersonId()) + "'"
					+ "              )";
		}
		return ls_AclFilter;
	}

	public String getEmpDeptIds(String as_EmpId) {
		Person person = EIPService.getEmpService().getEmp(as_EmpId);
		if (person != null) {
			String ls_DeptIds = person.getDeptId();
			return ls_DeptIds;
		}

		return "";
	}

	public static String rewritingIds(String ids) {
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
	public List<Folder> getFolderList(){
		return this.find("from Folder");
	}
}
