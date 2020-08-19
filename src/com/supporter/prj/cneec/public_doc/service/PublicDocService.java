package com.supporter.prj.cneec.public_doc.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.cneec.public_doc.constant.PublicDocConstant;
import com.supporter.prj.cneec.public_doc.dao.PublicDocCategoryDao;
import com.supporter.prj.cneec.public_doc.dao.PublicDocDao;
import com.supporter.prj.cneec.public_doc.entity.PublicDoc;
import com.supporter.prj.cneec.public_doc.entity.PublicDocCategory;

/**   
 * @Title: Service
 * @Description: OA_PUBLIC_DOC.
 * @author Administrator
 * @date 2018-06-04 16:05:00
 * @version V1.0   
 *
 */
@Service
public class PublicDocService {
	@Autowired
	private PublicDocDao publicDocDao;
	@Autowired
	private PublicDocCategoryDao categoryDao;
	@Autowired
	private PublicDocCategoryService categoryService;
	
	
	/**
	 * 根据主键获取OA_PUBLIC_DOC.
	 * 
	 * @param docId 主键
	 * @return PublicDoc
	 */
	public PublicDoc get(String docId) {
		return  publicDocDao.get(docId);
	}
	
	
	/**
	 * 分页表格展示数据.
	 * @param jqGrid 表格请求参数
	 * @param publicDoc 文档对象
	 * @return List< PublicDoc >
	 */
	public List< PublicDoc > getGrid(JqGrid jqGrid, PublicDoc publicDoc) {
		return publicDocDao.findPage(jqGrid, publicDoc, false);
	}
	
	/**
	 * 分页表格展示数据.
	 * @param jqGrid 表格请求参数
	 * @param publicDoc 文档对象
	 * @return List< PublicDoc >
	 */
	public List< PublicDoc > getFileGrid(JqGrid jqGrid, PublicDoc publicDoc) {
		return publicDocDao.findPage(jqGrid, publicDoc, true);
	}
	
	/**
	 * 获取从根节点开始的文档列表.
	 * @param docId 端节点的文档ID
	 * @return List < PublicDoc >
	 */
	public List < PublicDoc > getFullPathDocs(String docId) {
		List < PublicDoc > docs = new ArrayList < PublicDoc >();
		
		PublicDoc doc = publicDocDao.get(docId);
		while (doc != null) {
			if (!doc.isFile()) {
				docs.add(0, doc);
			}
			
			if (StringUtils.isBlank(doc.getParentDocId())) {
				break;
			}
			
			doc = publicDocDao.get(doc.getParentDocId());
		}
		return docs;
	}
	
	/**
	 * 检查目录名是否重复.
	 * @param categoryId 分类ID
	 * @param parentDocId 父文档ID
	 * @param docNames 文档名 docName1,docName2,docName3...
	 * @param isFile 是否文件
	 * @return 如果检查通过返回"",否则返回alert字符串
	 */
	public String checkFolders(String categoryId, String parentDocId, String docNames, boolean isFile) {
		if (StringUtils.isBlank(docNames)
				|| StringUtils.isBlank(categoryId) && StringUtils.isBlank(parentDocId)) {
			return "条件不足，无法判断目录名是否重复，请联系管理员";
		}
		
		List < PublicDoc > docs = publicDocDao.getPublicDocs(categoryId, parentDocId, docNames, isFile);
		if (docs == null || docs.size() == 0) {
			return "";
		}
		
		String msg = "名称重复：";
		for (int i = 0; i < docs.size(); i++) {
			PublicDoc doc = docs.get(i);
			msg += doc.getDocName() + "； ";
		}
		return msg;
	}
	
	/**
	 * 批量新增子目录.
	 * @param user 用户
	 * @param categoryId 分类ID
	 * @param parentDocId 父目录ID
	 * @param docNames 目录名 docName1,docName2,docName3...
	 * @param displayOrders 和docNames对应的显示顺序
	 * @return String 如果为空，则表示成功保存，不为空表示异常，返回异常信息字符串
	 */
	@Transactional(transactionManager = TransManager.APP, rollbackFor = Exception.class)
	public String batchAddFolders(UserProfile user, String categoryId, String parentDocId, String docNames,
			String displayOrders) {
		if (StringUtils.isBlank(docNames)
				|| StringUtils.isBlank(categoryId) && StringUtils.isBlank(parentDocId)) {
			return "条件不足，无法保存，请联系管理员";
		}
		if (StringUtils.isNotBlank(parentDocId)) {
			PublicDoc doc = publicDocDao.get(parentDocId);
			if (doc == null) {
				return "上级目录指定错误，无法保存，请联系管理员";
			}
			categoryId = doc.getCategoryId();
		}
		if (StringUtils.isBlank(categoryId)) {
			return "条件不足，无法保存，请联系管理员";
		}
		//查重
		String msg = this.checkFolders(categoryId, parentDocId, docNames, false);
		if (msg.length() > 0) {
			return msg;
		}
		
		String[] nameArray = docNames.split(",");
		String[] orderArray = displayOrders.split(",");
		for (int i = 0; i < nameArray.length; i++) {
			if (StringUtils.isBlank(nameArray[i])) {
				continue;
			}
			PublicDoc doc = new PublicDoc();
			doc.setCategoryId(categoryId);
			doc.setDocName(nameArray[i]);
			doc.setFile(false);
			doc.setParentDocId(parentDocId);
			doc.setDisplayOrder(CommonUtil.parseInt(orderArray[i], 0));
			this.saveOrUpdateDoc(user, doc);
		}
		return "";
	}
	
	/**
	 * 批量新增文件.
	 * @param user 用户
	 * @param categoryId 分类ID
	 * @param parentDocId 父目录ID
	 * @param fileIds 文件ID fileId1,fileId2,fileId3...
	 * @return String 如果为空，则表示成功保存，不为空表示异常，返回异常信息字符串
	 */
	@Transactional(transactionManager = TransManager.APP, rollbackFor = Exception.class)
	public String batchAddFiles(UserProfile user, String categoryId, String parentDocId, String fileIds) {
		if (StringUtils.isBlank(fileIds)
				|| StringUtils.isBlank(categoryId) && StringUtils.isBlank(parentDocId)) {
			return "条件不足，无法保存，请联系管理员";
		}
		if (StringUtils.isNotBlank(parentDocId)) {
			PublicDoc doc = publicDocDao.get(parentDocId);
			if (doc == null) {
				return "上级目录指定错误，无法保存，请联系管理员";
			}
			categoryId = doc.getCategoryId();
		}
		if (StringUtils.isBlank(categoryId)) {
			return "条件不足，无法保存，请联系管理员";
		}
		
		String[] fileArray = fileIds.split(",");
		for (int i = 0; i < fileArray.length; i++) {
			if (StringUtils.isBlank(fileArray[i])) {
				continue;
			}
			IFile file = EIPService.getFileUploadService().getFileRec(fileArray[i]);
			PublicDoc doc = new PublicDoc();
			doc.setCategoryId(categoryId);
			doc.setUploadFileId(fileArray[i]);
			doc.setDocName(file.getFileName());
			doc.setDisplayOrder(((FileUpload) file).getDisplayOrder());
			doc.setFile(true);
			doc.setParentDocId(parentDocId);
			doc.setType(PublicDoc.FILE);
			this.saveOrUpdateDoc(user, doc);
		}
		return "";
	}
	
	/**
	 * 新增保存链接.
	 * @param user 用户
	 * @param categoryId 分类ID
	 * @param parentDocId 父目录ID
	 * @param url 链接
	 * @return String 如果为空，则表示成功保存，不为空表示异常，返回异常信息字符串
	 */
	@Transactional(transactionManager = TransManager.APP, rollbackFor = Exception.class)
	public String batchAddUrl(UserProfile user, PublicDoc doc) {
		if (doc == null) {
			return "对象为空，请联系管理员";
		}
		if (StringUtils.isBlank(doc.getDocId())) {//新建保存时设置类型
			doc.setFile(true);//属于文件类
			doc.setType(PublicDoc.URL);
		}
		this.saveOrUpdateDoc(user, doc);
		
		return "";
	}
	
	/**
	 * 移动文档.
	 * @param user 用户
	 * @param docIds 文档IDS
	 * @param categoryId 分类ID
	 * @param parentDocId 父文件夹ID
	 * @return String
	 */
	public String batchMoveDocs(UserProfile user, String docIds, String categoryId, String parentDocId) {
		if (StringUtils.isBlank(docIds)
				|| StringUtils.isBlank(categoryId) && StringUtils.isBlank(parentDocId)) {
			return "请选择需要移动的文档或有效的文档分类";
		}
		if (StringUtils.isNotBlank(parentDocId)) {
			PublicDoc doc = publicDocDao.get(parentDocId);
			if (doc == null) {
				parentDocId = null;
			} else {
				categoryId = doc.getCategoryId();
			}
		}
		if (StringUtils.isBlank(categoryId)) {
			return "请选择有效的文档分类";
		}
		
		String[] docIdArray = docIds.split(",");
		for (int i = 0; i < docIdArray.length; i++) {
			String docId = docIdArray[i];
			if (StringUtils.isBlank(docId)) {
				continue;
			}
			PublicDoc doc = publicDocDao.get(docId);
			if (doc != null) {
				doc.setCategoryId(categoryId);
				doc.setParentDocId(parentDocId);
				this.saveOrUpdateDoc(user, doc);
			}
		}
		return "";
	}
	
	/**
	 * 获取或新建.
	 * @param categoryId 所属分类ID
	 * @param parentDocId 上级文档ID
	 * @param docId 文档ID
	 * @return PublicDoc
	 */
	public PublicDoc getOrNewDoc(String categoryId, String parentDocId, String docId) {
		PublicDoc doc = null;
		if (StringUtils.isNotBlank(docId)) {
			doc = publicDocDao.get(docId);
		}
		if (doc == null) {
			doc = new PublicDoc();
			doc.setCategoryId(categoryId);
			doc.setParentDocId(parentDocId);
		}
		return doc;
	}
	
	/**
	 * 保存文档记录.
	 * @param user 用户
	 * @param doc 文档记录
	 */
	@Transactional(transactionManager = TransManager.APP, rollbackFor = Exception.class)
	public void saveOrUpdateDoc(UserProfile user, PublicDoc doc) {
		if (doc == null) {
			return;
		}
		
		boolean isNew = false;
		if (StringUtils.isBlank(doc.getDocId())) {
			isNew = true;
		}
		
		//分类
		if (StringUtils.isBlank(doc.getCategoryId())) {
			if (StringUtils.isNotBlank(doc.getParentDocId())) {
				PublicDoc parent = publicDocDao.get(doc.getParentDocId());
				if (parent != null) {
					doc.setCategoryId(parent.getCategoryId());
				}
			}
		}
		
		PublicDocCategory category = categoryDao.get(doc.getCategoryId());
		if (category != null) {
			doc.setCategoryName(category.getCategoryName());
		}
		
		//创建修改信息
		if (user != null) {
			if (isNew) {
				doc.setCreatedById(user.getPersonId());
				doc.setCreatedByName(user.getName());
			}
			doc.setModifiedById(user.getPersonId());
			doc.setModifiedByName(user.getName());
		}
		Date today = new Date();
		if (isNew) {
			doc.setCreatedDate(today);
		}
		doc.setModifiedDate(today);
		
		if (isNew) {
			publicDocDao.save(doc);
		} else {
			publicDocDao.update(doc);
		}
	}
	
	
	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param docId
	 * @return
	 */
	public PublicDoc initEditOrViewPage(String docId) {
		// 新建
		if (StringUtils.isBlank(docId)) {
			PublicDoc entity = new PublicDoc();
			return entity;
		} else {
			// 编辑
			PublicDoc entity = publicDocDao.get(docId);
			if(entity != null){
				
			}
			return entity;
		}
	}
	
	/**
	 * 删除.
	 * @param docIds 主键集合，多个以逗号分隔
	 */
	@Transactional(transactionManager = TransManager.APP, rollbackFor = Exception.class)
	public void delete(String docIds) {
		if (StringUtils.isNotBlank(docIds)) {
			String[] idArray = docIds.split(",");
			for (int i = 0; i < idArray.length; i++) {
				if (StringUtils.isBlank(idArray[i])) {
					continue;
				}
				PublicDoc publicDocDb = this.publicDocDao.get(idArray[i]);
				if (publicDocDb == null) {
					continue;
				}
				
				//删除文档及下级树所有节点文档.
				deleteDoc(publicDocDb);
				
			}
		}
	}
	
	/**
	 * 删除文档及下级树所有节点文档.
	 * @param doc 文档对象
	 */
	private void deleteDoc(PublicDoc doc) {
		if (doc == null) {
			return;
		}
		List < PublicDoc > docs = publicDocDao.getDocsByCategoryId(doc.getCategoryId());
		if (docs == null || docs.size() == 0) {
			return;
		}
		
		int size = docs.size();
		
		List < PublicDoc > deleteList = new ArrayList < PublicDoc >();
		
		Deque < PublicDoc > nodeDeque = new ArrayDeque < PublicDoc >();
		nodeDeque.add(doc);
		while (!nodeDeque.isEmpty()) {
			PublicDoc node = nodeDeque.peekFirst();
			deleteList.add(node);
			if (node.isFile()) {
				if (!PublicDoc.URL.equals(node.getType())) {
					EIPService.getFileUploadService().deleteFile(node.getUploadFileId());
				}
			} else {
				String docId = node.getDocId();
				
		        //获得节点的子节点
				for (int i = 0; i < size; i++) {
					PublicDoc tempDoc = docs.get(i);
					String parentDocId = tempDoc.getParentDocId();
					
					if (StringUtils.isBlank(parentDocId)) {
						continue;
					}
					if (docId.equals(parentDocId)) {
						nodeDeque.add(tempDoc);
					}
				}
			}
			
	        nodeDeque.pollFirst();
	    }
		
		if (deleteList.size() > 0) {
			publicDocDao.delete(deleteList);
		}
	}
	
	/**
	 * 判断指定字段唯一性
	 * 
	 * @param docId 文档ID
	 * @param categoryId 分类ID
	 * @param parentDocId 文件夹ID
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return boolean
	 */
	public boolean checkPropertyUniquenes(String docId, String categoryId, String parentDocId,
			String propertyName, String propertyValue) {
		return publicDocDao.checkPropertyUniquenes(docId, categoryId, parentDocId, propertyName, propertyValue);
	}
	
	/**
	 * 获取指定分类的所有文件列表(包括分类下的子分类的文件).
	 * @param categoryId 分类ID
	 * @param checkShow 检查显示
	 * @return List < PublicDoc >
	 */
	public List < PublicDoc > getSubTreeFilesByCategoryId(String categoryId, boolean checkShow) {
		List < PublicDocCategory > categorys = categoryService.getSubTreeCategorys(categoryId, checkShow);
		
		if (categorys == null || categorys.size() == 0) {
			return null;
		}
				
		int size = categorys.size();
		String[] categoryIds = new String[size];
		for (int i = 0; i < size; i++) {
			categoryIds[i] = categorys.get(i).getCategoryId();
		}
		
		return publicDocDao.getFilesByCategoryIds(categoryIds);
	}
	
	/**
	 * 获取指定分类的所有文档列表(包括分类下的子分类的文件夹及文件).
	 * @param categoryId 分类ID
	 * @param checkShow 检查显示
	 * @return List < PublicDoc >
	 */
	public List < PublicDoc > getSubTreeDocsByCategoryId(String categoryId, boolean checkShow) {
		List < PublicDocCategory > categorys = categoryService.getSubTreeCategorys(categoryId, checkShow);
		
		if (categorys == null || categorys.size() == 0) {
			return null;
		}
				
		int size = categorys.size();
		String[] categoryIds = new String[size];
		for (int i = 0; i < size; i++) {
			categoryIds[i] = categorys.get(i).getCategoryId();
		}
		
		return publicDocDao.getDocsByCategoryIds(categoryIds);
	}
	
	
	/**
	 * 获取指定分类的所有文件列表(只包括直接挂接到指定分类的文件).
	 * @param categoryId 分类ID
	 * @return List < PublicDoc >
	 */
	public List < PublicDoc > getDirectFilesByCategoryId(String categoryId) {
		return publicDocDao.getFilesByCategoryId(categoryId);
	}
	
	/**
	 * 获取指定分类的所有文件夹列表(只包括直接挂接到指定分类的文件夹).
	 * @param categoryId 分类ID
	 * @return List < PublicDoc >
	 */
	public List < PublicDoc > getDirectFoldersByCategoryId(String categoryId) {
		return publicDocDao.getFoldersByCategoryId(categoryId);
	}
	
	/**
	 * 发布老系统的文件柜文件到常用文件.
	 * @param request 请求
	 * @param user 用户
	 * @param categoryId 分类ID
	 * @param parentDocId 文件夹ID
	 * @param filePath 旧系统的文件路径
	 * @param fileName 旧系统的文件名
	 * @return String 为空代表成功，否则返回错误信息
	 */
	@Transactional(transactionManager = TransManager.APP, rollbackFor = Exception.class)
	public String publishOldFile(HttpServletRequest request, UserProfile user,
			String categoryId, String parentDocId, String filePath, String fileName) {
		boolean unique = this.checkPropertyUniquenes(null, categoryId, parentDocId, "docName", fileName);
		if (!unique) {
			return "发布的文件名重复";
		}
		
		try {
			String newFilePath = request.getSession().getServletContext().getRealPath("/")
					+ "data/supp/remot_foxbox_data/" + filePath;
			//System.out.println(newFilePath);
			File file = new File(newFilePath);
			String newFileId = EIPService.getFileUploadService().saveFile(file, PublicDocConstant.MODULE_ID,
					PublicDocConstant.FILE_DOC, fileName, user, UUIDHex.getUUIDHex());
			
			PublicDoc doc = this.getOrNewDoc(categoryId, parentDocId, null);
			doc.setDocName(fileName);
			doc.setFile(true);
			doc.setType(PublicDoc.FILE);
			doc.setUploadFileId(newFileId);
			this.saveOrUpdateDoc(user, doc);
		} catch (Exception e) {
			System.out.println("文件发布发生了异常");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return "";
	}

}

