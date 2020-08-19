/**
 * 
 */
package com.supporter.prj.ppm.poa.use_seal.service;

import java.util.Date;					  
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;														
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.todo.entity.ITodo;													   
import com.supporter.prj.ppm.poa.use_seal.constant.UseSealConstant;																   
import com.supporter.prj.ppm.poa.use_seal.dao.UseSealFileDao;
import com.supporter.prj.ppm.poa.use_seal.entity.UseSealFileEntity;

import net.sf.json.JSONArray;

/**
 *<p>Title: 用印文件列表</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月18日
 */
@Service
public class UseSealFileService {

	@Autowired
	private UseSealFileDao useSealFileDao;

	/**
	 * <pre>save 'jqGrid' information</pre>
	 * @param useSealId 启动用印主键
	 * @param entityId 调用用印业务表单主键
	 * @param prjId 项目主键
	 * @param useSealFileGrid 用印列表数据
	 */
	public void save(String useSealId, String businessUUID, String prjId, String useSealFileGrid) {
		
		JSONArray objectArray = JSONArray.fromObject(useSealFileGrid);//将String转json数组
		UseSealFileEntity[] array = (UseSealFileEntity[])JSONArray.toArray(objectArray, UseSealFileEntity.class);//json数组转对象数组
		for(int i=0;i<array.length;i++) {
			UseSealFileEntity entity = array[i];
			entity.setUseSealId(useSealId);
			entity.setBusinessUUID(businessUUID);
			entity.setPrjId(prjId);
			useSealFileDao.save(entity);
		}

	}

	/**
	 * <pre>update 'jqGrid' information</pre>
	 * @param useSealId 启动用印主键
	 * @param entityId 调用用印业务表单主键
	 * @param prjId 项目主键
	 * @param useSealFileGrid 用印列表数据
	 */
	public void update(String useSealId, String entityId, String prjId, String useSealFileGrid) {
		useSealFileDao.deleteByProperty("useSealId", useSealId);
		this.save(useSealId,entityId,prjId,useSealFileGrid);
	}

	/**
	 * <p>获取jgGrid数据</p>
	 * @param jqGrid
	 * @return
	 */
	public List<UseSealFileEntity> getGridData(String entityId) {
		if(entityId == null || entityId == "") {return null;}
		List<UseSealFileEntity> list = this.useSealFileDao.findBy("entityId", entityId);
		return list;
	}
	
	/**
	 * <p>根据外键查数据</p>
	 * @param jqGrid 
	 * @param 外键
	 * @return
	 */
	public List<UseSealFileEntity> getByUseSealId(String useSealId, JqGrid jqGrid){
		if(useSealId == null || useSealId == "") {return null;}
		return this.useSealFileDao.findPage(useSealId,jqGrid);
		
	}

	/**
	 * <pre>更新盖章文件信息</pre>
	 * @param sealFileId 用印文件主键
	 * @param fuSealFileId 盖章文件id
	 * @param fuSealFileName 盖章文件名称
	 * @return 用印盖章文件信息
	 */
	public UseSealFileEntity updateUseSealFuFile(String sealFileId, String fuSealFileId, String fuSealFileName) {
		
		if(sealFileId == null || sealFileId == "") {
			return null;
		}
		
		UseSealFileEntity useSealFileEntity = useSealFileDao.get(sealFileId);
		useSealFileEntity.setFuSealFileId(fuSealFileId);
		useSealFileEntity.setFuSealFileName(fuSealFileName);
		useSealFileDao.update(useSealFileEntity);
		return useSealFileEntity;
	}

	/**
	 * 根据业务单编号查找用印文件列表
	 * @param businessUUId 业务单编号
	 * @param jqGrid
	 * @return
	 */
	public  List<UseSealFileEntity> getByBusinessId(String businessUUId, JqGrid jqGrid) {
		if(businessUUId == null || businessUUId == "") {return null;}
		return this.useSealFileDao.findPageByBusinessId(businessUUId,jqGrid);
		
	}
	
	public void deleteUseSealFuFile(String sealFileId, String moduleName, String basiType, String entityId, String twolevelid) {
		List<IFile> fileList = EIPService.getFileUploadService().getFileList(moduleName, basiType, entityId, twolevelid);
		if(fileList.size() == 0 || fileList == null) {
			UseSealFileEntity useSealFileEntity = useSealFileDao.get(sealFileId);
			useSealFileEntity.setFuSealFileId("");
			useSealFileEntity.setFuSealFileName("");
			useSealFileDao.update(useSealFileEntity);
		}
}

	/**
	 * <pre>
	 * @Title : 删除用印文件信息
	 * @param useSealId	用印文件的外键
	 * </pre>
	 */
	public void deleteFile(String useSealId) {
		
		if(!useSealId.isEmpty()) {
			
			useSealFileDao.deleteByProperty("useSealId", useSealId);
		}
	}
	
	public boolean isSendNotice(String useSealId) {
		
		if(useSealId.isEmpty()) {
			return false;
		}
			
		List<UseSealFileEntity> list = useSealFileDao.findBy("useSealId", useSealId);
		
		for(UseSealFileEntity entity : list) {
			
			if(UseSealConstant.USE_SEAL_FILE_KIND.equals(entity.getUseSealKind())) {
				
				return true;
			}
		}
		return false;
	}
}
