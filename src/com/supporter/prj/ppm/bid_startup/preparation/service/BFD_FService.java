/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.preparation.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.ppm.bid_startup.preparation.dao.BFD_FDao;
import com.supporter.prj.ppm.bid_startup.preparation.entity.BFDEntity;
import com.supporter.prj.ppm.bid_startup.preparation.entity.BFD_FEntity;
import com.supporter.util.UUIDHex;

/**
 *<p>Title: BFD_FService</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年8月27日
 * 
 */
@Service
public class BFD_FService {
	@Autowired
	private BFD_FDao bfd_fDao;

	@Autowired
	private BFDService bfdService; 
	
	public List<BFD_FEntity> getBFD_FEntityByBidIngUpId(String bidIngUpId) {
		if(bidIngUpId == "") { return null;}
		List<BFD_FEntity> entity = bfd_fDao.findBy("bidIngUpId", bidIngUpId);
		return entity;
	}
	
	public void updateWhole(BFD_FEntity entity) {
		bfd_fDao.saveOrUpdate(entity);
	}
	
	/**
	 * <pre>保存清单附件</pre>
	 * @param bfdId 资料清业务实体类主键
	 * @param bidIngUpId 启动申报业务实体类主键
	 * @param fileInfo 附件信息 [附件上传成功后的附件主键   上传附件的名称]
	 */
	public void saveBFD_F(String bfdId, String bidIngUpId, String fileInfo) {
		String[] fileInfoArr = fileInfo.split(",");
		String fileId = "";
		String fileName = "";
		for(int i=0;i<fileInfoArr.length;i++) {
			fileId = fileInfoArr[0];
			fileName = fileInfoArr[1];
		}
		BFD_FEntity bfd_FEntity = new BFD_FEntity();
		bfd_FEntity.setRecordId(UUIDHex.newId());
		bfd_FEntity.setBfdId(bfdId);
		bfd_FEntity.setBidIngUpId(bidIngUpId);
		bfd_FEntity.setFuFileId(fileId);//实际上传附件ID
		bfd_FEntity.setFuFileName(fileName);//实际上传附件的名称(资料清单文件类型对应的文件名称)
		bfd_FEntity.setIsUseSeal(0);//是否用印 码表：0否1是
		bfd_fDao.save(bfd_FEntity);
	}

	/**
	 * <pre>删除资料清单对应的附件信息</pre>
	 * @param bfdId 资料清单主键
	 */
	public void deleteByBfdId(String bfdId) {
		bfd_fDao.deleteByProperty("bfdId", bfdId);
	}
	
	/**
	 * <pre>根据启动申报</pre>
	 * <pre>1.启动申报单项删除的时候调用</pre>
	 * @param bidStartUpId 启动申报业务表单实体类主键
	 */
	public void deleteByBidIngUpId(String bidIngUpId) {
		bfd_fDao.deleteByProperty("bidIngUpId", bidIngUpId);
	}

	/**
	 * <pre>标记用印文件</pre>
	 * @param bfdId 资料清单主键
	 */
	public void markUsePrintFile(String bfdId) {
		List<BFD_FEntity> list = bfd_fDao.findBy("bfdId", bfdId);
		for(int i=0;i<list.size();i++) {
			BFD_FEntity bfd_FEntity = list.get(i);
			bfd_FEntity.setIsUseSeal(1);
			bfd_fDao.update(bfd_FEntity);
		}
	}

	/**
	 * <p>用印文件列表</p>
	 * @param bidIngUpId 业务主键
	 * @return 
	 */
	public List<Map<String, String>> getUseSealGrid(String bidIngUpId) {
		if(bidIngUpId == null || bidIngUpId == "") {
			return null;
		}
		List<Map<String,String>> li = new ArrayList<Map<String,String>>();
		String hql = "from "+BFD_FEntity.class.getName()+" where bidIngUpId = ? and isUseSeal = ?";
		List<BFD_FEntity> list = bfd_fDao.find(hql,bidIngUpId,1);
		for(int i = 0;i<list.size();i++) {
			Map<String,String> map = new HashMap<String, String>();
			BFDEntity bfdEntity = bfdService.get(list.get(i).getBfdId());
			map.put("sealFileId", UUIDHex.newId());//用印文件主键 
			map.put("fileUpBusinessId", list.get(i).getRecordId());//用印文件主键
			map.put("bfdTypeName", bfdEntity.getBfdTypeName());//文件类型
			map.put("fuFileName", list.get(i).getFuFileName());//文件名称
			map.put("fuFileId", list.get(i).getFuFileId());//文件id
			map.put("moduleName", "BIDREPARATIO");//应用编号-申报准备
			map.put("busiType", "bidingFile");//附件上传业务一级分类
			li.add(map);
		}
		return li;
	}

	/**
	 * <pre>set盖章文件id和文件名称，名称字段没设计先不做处理</pre>
	 * @param promaryKey 主键
	 * @param fuSealFileId 盖章文件id
	 * @param fuSealFileName 盖章文件名称
	 * @return 结果
	 */
	public String updateFuFileInfo(String promaryKey, String fuSealFileId, String fuSealFileName) {
		
		if(promaryKey == null || promaryKey == "") {
			return "fail";
		}
		
		BFD_FEntity bfd_FEntity = bfd_fDao.get(promaryKey);
		bfd_FEntity.setFuSealFileId(fuSealFileId);
		bfd_fDao.update(bfd_FEntity);
		return "success";
	}

	public void deleteFile(String hql,Object...values) {
		bfd_fDao.execUpdate(hql, values);
	}
}
