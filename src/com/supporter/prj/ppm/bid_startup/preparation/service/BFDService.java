/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.preparation.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.ppm.bid_startup.preparation.constant.StartContant;
import com.supporter.prj.ppm.bid_startup.preparation.dao.BFDDao;
import com.supporter.prj.ppm.bid_startup.preparation.dao.BFD_FDao;
import com.supporter.prj.ppm.bid_startup.preparation.entity.BFDEntity;
import com.supporter.prj.ppm.bid_startup.preparation.entity.BFD_FEntity;

/**
 *<p>Title: BFDService</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年8月26日
 * 
 */
@Service
public class BFDService {
	
	@Autowired
	private BFDDao bfdDao;
	
	@Autowired
	private BFD_FDao bfd_fDao;
	
	@Autowired
	private BFD_FService bfd_fService;

	/**
	 * <pre>保存资料清单</pre>
	 * @param bfdEntity 资料清单业务表单实体类
	 * @return 保存成功后的业务表单实体类数据
	 */
	public BFDEntity saveBFD(BFDEntity bfdEntity) {
		System.out.println(bfdEntity);
		if(bfdEntity.getBfdId() == null || bfdEntity.getBfdId() == "" ) return null;
		System.out.println(123);
		BFDEntity entity = bfdDao.findUniqueResult("bfdId", bfdEntity.getBfdId());
		if(entity==null) {
			bfdEntity.setBfdTypeName(StartContant.getBfdTypeName(bfdEntity.getBfdTypeId()));
			bfdDao.save(bfdEntity);
		}
		saveBFD_F(bfdEntity.getBfdId(),bfdEntity.getBidIngUpId(),bfdEntity.getFileInfo());
		return bfdEntity;
	}

	/**
	 * <pre>保存资料清单文件</pre>
	 * @param bfdId 资料清单主键
	 * @param bidIngUpId 启动申报准备主键
	 * @param fileInfo 附件信息
	 */
	private void saveBFD_F(String bfdId, String bidIngUpId, String fileInfo) {
		bfd_fService.saveBFD_F(bfdId,bidIngUpId,fileInfo);
	}

	/**
	 * <pre>删除资料清单</pre>
	 * @param moduleName 应用编号 
	 * @param basiType 业务编号
	 * @param entityId 业务实体类
	 * @param twolevelid 二级业务编号
	 */
	public void deleteBFD(String filesId,String bidIngUpId,String bfdTypeId ) {//String moduleName, String basiType, String entityId, String twolevelid
		
		String[] split = filesId.split(",");
		for (int i = 0;i<split.length;i++) {
			bfd_fDao.deleteByProperty("fuFileId", split[i]);
		}
		String hql = "delete " + BFDEntity.class.getName() + " where bidIngUpId = ? and bfdTypeId = ?";
		bfd_fDao.execUpdate(hql, bidIngUpId,bfdTypeId);
//			List<IFile> fileList = EIPService.getFileUploadService().getFileList(moduleName, basiType, entityId, twolevelid);
//			
//			if(fileList.size() == 0 || fileList == null) {
//				bfd_fService.deleteByBfdId(entityId);
//				bfdDao.delete(entityId);
//			}else {
//				String hql = "delete " + BFD_FEntity.class.getName() + " where bfdId = ? and fuFileId not in (" ;
//				Object[] obj = new Object[fileList.size()+1] ;
//				obj[0] = entityId;
//				for(int i = 0; i < fileList.size(); i ++) {
//					if(i == 0) {
//						hql = hql + "?";
//					}else {
//						hql = hql + ",?";
//					}
//					obj[i+1] = fileList.get(i).getFileId();
//				}
//				hql = hql + ")";
//				bfd_fService.deleteFile(hql, obj);
//			}
	}

	/**
	 * <pre>功能:根据启动申报主键和码表中资料清单的ID获取资料清单数据</pre>
	 * <pre>描述:根据启动申报主键和码表资料清单项的id可以确定到对应的而且唯一的，已经保存过的资料清单项</pre>
	 * <pre>调用:1.启动申报编辑页面初始化数据</pre>
	 * @param bidIngUpId 资料清单业务表单实体类主键
	 * @param itemId 资料清单id
	 * @return 资料清单业务表单实体类数据
	 */
	public List<BFDEntity> getDataListEdit(String bidIngUpId, String bfdTypeId) {
		if(bidIngUpId == null || bidIngUpId == "" && bfdTypeId == null || bfdTypeId == "") {
			return null;
		}
		String hql = "from "+BFDEntity.class.getName()+" where bidIngUpId = ? and bfdTypeId = ?";
		List<BFDEntity> list = bfdDao.find(hql,bidIngUpId,bfdTypeId);
		return list;
	}
	
	/**
	 * <pre>根据启动申报业务表单实体类主键删除资料清单信息</pre>
	 * <pre>1.启动申报单项删除的时候调用</pre>
	 * @param bidStartUpId 启动申报业务表单实体类主键
	 */
	public void deleteByBidIngUpId(String bidIngUpId) {
		bfdDao.deleteByProperty("bidIngUpId", bidIngUpId);
	}

	/**
	 * <pre>初始化确认用印文件</pre>
	 * @return
	 */
	public List<BFDEntity> initPrintFile(String bidIngUpId) {
		List<BFDEntity> list = bfdDao.findBy("bidIngUpId", bidIngUpId);
		return list;
	}

	/**
	 * <pre>标记用印文件</pre>
	 * <pre>逻辑：标记前先将是否用印文件设为否，再根据前台选择的需要用印的文件将需要用印的设为是</pre>
	 * @param bfdIds 资料清单主键
	 */
	public void markUsePrintFile(String bfdIds,String bidIngUpId) {//bfdIds：改树形后这个参数是文件类型id
		if(bfdIds == null || bfdIds == "") {};
		List<BFDEntity> listBFDEntity = bfdDao.findBy("bidIngUpId", bidIngUpId);
		
		for (BFDEntity bfdEntity : listBFDEntity) {//这条业务单中的文件类型是否用印设为 否0
			bfdEntity.setIsUseSeal(0);
			bfdDao.update(bfdEntity);
		}
		List<BFD_FEntity> entityList = bfd_fService.getBFD_FEntityByBidIngUpId(bidIngUpId);
		for (BFD_FEntity bfd_FEntity : entityList) {//这条业务单中的文件类型下的文件，是否用印设为 否0
			bfd_FEntity.setIsUseSeal(0);
			bfd_fService.updateWhole(bfd_FEntity);
		}
		String[] bfdIdArr = bfdIds.split(",");
		for(int i=0;i<bfdIdArr.length;i++) {
			//用主键和清单文件类型确定数据的唯一性
			String hql = "from " + BFDEntity.class.getName() + " where bfdTypeId = ? and bidIngUpId = ?";
			BFDEntity bfdEntity = bfdDao.findUniqueResult(hql, (bfdIdArr[i]),bidIngUpId);
			//BFDEntity bfdEntity = bfdDao.get(bfdIdArr[i]);
			if(bfdEntity != null) {
				bfdEntity.setIsUseSeal(1);
				bfdDao.update(bfdEntity);
				bfd_fService.markUsePrintFile(bfdEntity.getBfdId());
			}
		}
	}
	
	public BFDEntity get(String bfdId) {
		return bfdDao.get(bfdId);
	}
}
