package com.supporter.prj.cneec.public_doc.controller;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.cneec.public_doc.service.PublicDocCategoryService;
import com.supporter.prj.cneec.public_doc.service.PublicDocService;
import com.supporter.prj.cneec.public_doc.entity.PublicDoc;
import com.supporter.prj.cneec.public_doc.entity.PublicDocCategory;
import com.supporter.prj.cneec.public_doc.entity.PublicDocTreeNode;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**   
 * @Title: Controller
 * @Description: OA_PUBLIC_DOC.
 * @author Administrator
 * @date 2018-06-04 16:05:00
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("cneec/public_doc")
public class PublicDocController extends AbstractController {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PublicDocService publicDocService;
	@Autowired
	private PublicDocCategoryService categoryService;
	
	/**
	 * 获取分类列表.
	 * @return List < PublicDocCategory >
	 */
	@RequestMapping("getCategoryList")
	@ResponseBody
	public List < PublicDocCategory > getCategoryList() {
		return categoryService.getCategoryList();
	}
	
	/**
	 * 获取或新建分类.
	 * @param parentCategoryId 上级分类ID
	 * @param categoryId 分类ID
	 * @return PublicDocCategory
	 */
	@RequestMapping("getOrNewCategory")
	@ResponseBody
	public PublicDocCategory getOrNewCategory(String parentCategoryId, String categoryId) {
		return categoryService.getOrNewCategory(parentCategoryId, categoryId);
	}
	
	/**
	 * 删除分类.
	 * @param categoryId 分类ID
	 * @return String
	 */
	@RequestMapping("deleteCategory")
	@ResponseBody
	public String deleteCategory(String categoryId) {
		categoryService.deleteCategory(categoryId);
		return "";
	}
	
	/**
	 * 保存文档分类.
	 * @param category 分类对象
	 * @return PublicDocCategory
	 */
	@RequestMapping("saveOrUpdateCategory")
	@ResponseBody
	public PublicDocCategory saveOrUpdateCategory(PublicDocCategory category) {
		PublicDocCategory objToSave = categoryService.getOrNewCategory(category.getParentCategoryId(),
				category.getCategoryId());
		this.setPropValues(objToSave);
		categoryService.saveOrUpdate(objToSave);
		return objToSave;
	}
	
	/**
	 * 判断指定字段唯一性
	 * 
	 * @param categoryId 类别ID
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return boolean
	 */
	@RequestMapping("checkCategoryUniquenes")
	@ResponseBody
	public boolean checkCategoryUniquenes(String categoryId, String propertyName, String propertyValue) {
		return categoryService.checkPropertyUniquenes(categoryId, propertyName, propertyValue);
	}
	
	/**
	 * 检查当前用户是否有维护该分类的权限.
	 * @param categoryId 分类ID
	 * @return boolean
	 */
	@RequestMapping("checkAuth")
	@ResponseBody
	public boolean checkAuth(String categoryId) {
		return categoryService.checkAuth(this.getUserProfile(), categoryId);
	}
	
	/**
	 * 获取文档列表.
	 * @param request 请求对象
	 * @param jqGridReq 表格参数
	 * @param publicDoc 对象参数
	 * @return JqGrid
	 */
	@RequestMapping("getDocsGrid")
	@ResponseBody
	public JqGrid getDocsGrid(HttpServletRequest request, JqGridReq jqGridReq, PublicDoc publicDoc) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.publicDocService.getGrid(jqGrid, publicDoc);
		return jqGrid;
	}
	
	/**
	 * 获取文件列表.
	 * @param request 请求对象
	 * @param jqGridReq 表格参数
	 * @param publicDoc 对象参数
	 * @return JqGrid
	 */
	@RequestMapping("getFilesGrid")
	@ResponseBody
	public JqGrid getFilesGrid(HttpServletRequest request, JqGridReq jqGridReq, PublicDoc publicDoc) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.publicDocService.getFileGrid(jqGrid, publicDoc);
		return jqGrid;
	}
	
	/**
	 * 获取文档全路径对象列表.
	 * @param categoryId 分类ID
	 * @param docId 文档ID
	 * @return Map < String, Object >
	 */
	@RequestMapping("getFullPath")
	@ResponseBody
	public Map < String, Object > getFullPath(String categoryId, String docId) {
		Map < String, Object > map = new HashMap < String, Object >();
		
		if (StringUtils.isBlank(categoryId)) {
			PublicDoc doc = publicDocService.get(docId);
			if (doc != null) {
				categoryId = doc.getCategoryId();
			}
		}
		
		List < PublicDocCategory > categorys = categoryService.getFullPathCategorys(categoryId);
		List < PublicDoc > docs = publicDocService.getFullPathDocs(docId);
		
		map.put("categorys", categorys);
		map.put("docs", docs);
		return map;
	}
	
	/**
	 * 检查目录名是否重复.
	 * @param categoryId 分类ID
	 * @param parentDocId 父文档ID
	 * @param docNames 文档名 docName1,docName2,docName3...
	 * @return 如果检查通过返回"",否则返回alert字符串
	 */
	@RequestMapping("checkFolders")
	@ResponseBody
	public String checkFolders(String categoryId, String parentDocId, String docNames) {
		boolean isFile = false;
		return publicDocService.checkFolders(categoryId, parentDocId, docNames, isFile);
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
	@RequestMapping("checkDocUniquenes")
	@ResponseBody
	public boolean checkDocUniquenes(String docId, String categoryId, String parentDocId,
			String propertyName, String propertyValue) {
		return publicDocService.checkPropertyUniquenes(docId, categoryId, parentDocId,
				propertyName, propertyValue);
	}
	
	/**
	 * 批量新增子目录.
	 * @param categoryId 分类ID
	 * @param parentDocId 父目录ID
	 * @param docNames 多个子目录名
	 * @param displayOrders 和docNames对应的显示顺序
	 * @return String 如果为空，则表示成功保存，不为空表示异常，返回异常信息字符串
	 */
	@RequestMapping("batchAddFolders")
	@ResponseBody
	public String batchAddFolders(String categoryId, String parentDocId, String docNames, String displayOrders) {
		return publicDocService.batchAddFolders(this.getUserProfile(), categoryId, parentDocId,
				docNames, displayOrders);
	}
	
	/**
	 * 批量新增文档文件.
	 * @param categoryId 分类ID
	 * @param parentDocId 父目录ID
	 * @param fileIds 多个文件ID
	 * @return String 如果为空，则表示成功保存，不为空表示异常，返回异常信息字符串
	 */
	@RequestMapping("batchAddFiles")
	@ResponseBody
	public String batchAddFiles(String categoryId, String parentDocId, String fileIds) {
		return publicDocService.batchAddFiles(this.getUserProfile(), categoryId, parentDocId, fileIds);
	}
	
	/**
	 * 增加链接
	 * @param categoryId 分类ID
	 * @param parentDocId 父目录ID
	 * @param url 链接
	 * @return
	 */
	@RequestMapping("batchAddUrl")
	@ResponseBody
	public String batchAddUrl(PublicDoc doc) {
		return publicDocService.batchAddUrl(this.getUserProfile(), doc);
	}
	
	/**
	 * 移动文档.
	 * @param docIds 文档IDS
	 * @param categoryId 分类ID
	 * @param parentDocId 父文件夹ID
	 * @return String
	 */
	@RequestMapping("batchMoveDocs")
	@ResponseBody
	public String batchMoveDocs(String docIds, String categoryId, String parentDocId) {
		return publicDocService.batchMoveDocs(this.getUserProfile(), docIds, categoryId, parentDocId);
	}
	
	/**
	 * 获取或新建文档.
	 * @param categoryId 分类ID
	 * @param parentDocId 上级目录ID
	 * @param docId 文档ID
	 * @return PublicDoc
	 */
	@RequestMapping("getOrNewDoc")
	@ResponseBody
	public PublicDoc getOrNewDoc(String categoryId, String parentDocId, String docId) {
		return publicDocService.getOrNewDoc(categoryId, parentDocId, docId);
	}
	
	/**
	 * 保存文档.
	 * @param doc 文档对象
	 * @return PublicDoc
	 */
	@RequestMapping("saveOrUpdateDoc")
	@ResponseBody
	public PublicDoc saveOrUpdateDoc(PublicDoc doc) {
		PublicDoc objToSave = publicDocService.getOrNewDoc(doc.getCategoryId(),
				doc.getParentDocId(), doc.getDocId());
		this.setPropValues(objToSave);
		publicDocService.saveOrUpdateDoc(this.getUserProfile(), objToSave);
		return objToSave;
	}
	
	
	
	/**
	 * 删除操作.
	 * 
	 * @param docIds 主键集合，多个以逗号分隔
	 * @return String
	 */
	@RequestMapping("deleteDocs")
	@ResponseBody
	public String deleteDocs(String docIds) {
		this.publicDocService.delete(docIds);
		return "";
	}
	
	
	/**
	 * **************************页面展示******************************
	 */
	
	/**
	 * 获取分类树的根节点.
	 * @return PublicDocCategory
	 */
	@RequestMapping("getRootCategory")
	@ResponseBody
	public PublicDocCategory getRootCategory() {
		return categoryService.getRootCategory();
	}
	
	/**
	 * 获取分类树的根节点下的直属子节点.
	 * @param checkShow  是否显示在首页
	 * @return List < PublicDocCategory >
	 */
	@RequestMapping("getSubCategorysOfRoot")
	@ResponseBody
	public List < PublicDocCategory > getSubCategorysOfRoot(boolean checkShow) {
		PublicDocCategory root = categoryService.getRootCategory();
		if (root == null) {
			return null;
		} else {
			return categoryService.getDirectSubCategorys(root.getCategoryId(), checkShow);
		}
	}
	
	/**
	 * 获取指定分类的直接下级分类列表.
	 * @param categoryId 分类ID
	 * @param checkShow  是否显示在首页
	 * @return List < PublicDocCategory >
	 */
	@RequestMapping("getDirectSubCategorys")
	@ResponseBody
	public List < PublicDocCategory > getDirectSubCategorys(String categoryId, boolean checkShow) {
		return categoryService.getDirectSubCategorys(categoryId, checkShow);
	}
	
	/**
	 * 获取指定分类的直接下级文件夹列表.
	 * @param categoryId 分类ID
	 * @return List < PublicDoc >
	 */
	@RequestMapping("getDirectSubFolders")
	@ResponseBody
	public List < PublicDoc > getDirectSubFolders(String categoryId) {
		return publicDocService.getDirectFoldersByCategoryId(categoryId);
	}
	
	/**
	 * 获取指定分类的直接下级文件列表.
	 * @param categoryId 分类ID
	 * @return List < PublicDoc >
	 */
	@RequestMapping("getDirectSubFiles")
	@ResponseBody
	public List < PublicDoc > getDirectSubFiles(String categoryId) {
		return publicDocService.getDirectFilesByCategoryId(categoryId);
	}
	
	/**
	 * 获取分类下的所有文件（包括子分类文件）.
	 * @param categoryId 分类ID
	 * @param checkShow 检查显示
	 * @return List < PublicDoc >
	 */
	@RequestMapping("getSubTreeFilesByCategoryId")
	@ResponseBody
	public List < PublicDoc > getSubTreeFilesByCategoryId(String categoryId, boolean checkShow) {
		List < PublicDoc > files = publicDocService.getSubTreeFilesByCategoryId(categoryId, checkShow);
		return files;
	}
	
	/**
	 * 获取文档树节点.
	 * @param categoryId 根节点ID
	 * @param checkShow 是否进行显示控制
	 * @return List < PublicDocTreeNode >
	 */
	@RequestMapping("getDocTreeNode")
	@ResponseBody
	public List < PublicDocTreeNode > getDocTreeNode(String categoryId, boolean checkShow) {
		List < PublicDocCategory > categorys = categoryService.getSubTreeCategorys(categoryId, checkShow);
		if (categorys == null || categorys.size() == 0) {
			return null;
		}
		List < PublicDoc > docs = publicDocService.getSubTreeDocsByCategoryId(categoryId, checkShow);
		
		List < PublicDocTreeNode > nodes = new ArrayList < PublicDocTreeNode >();
		
		int categorySize = categorys.size();
		for (int i = 0; i < categorySize; i++) {
			PublicDocCategory c = categorys.get(i);
			PublicDocTreeNode node = new PublicDocTreeNode(c.getCategoryId(), c.getInnerName(),
					c.getParentCategoryId(), c.getCategoryName(),null,
					false, true, true, false, PublicDocTreeNode.NodeType.CATEGORY);
			node.setIcon(c.getImgUrl());
			node.setExpanded(c.getExpanded());
			nodes.add(node);
		}
		
		if (docs != null && docs.size() > 0) {
			int docSize = docs.size();
			for (int i = 0; i < docSize; i++) {
				PublicDoc doc = docs.get(i);
				String parentId = doc.getParentDocId();
				if (StringUtils.isBlank(parentId)) {
					parentId = doc.getCategoryId();
				}
				int nodeType = PublicDocTreeNode.NodeType.FILE;
				if (!doc.isFile()) {
					nodeType = PublicDocTreeNode.NodeType.FOLDER;
				}
				if (PublicDoc.URL.equals(doc.getType())) {
					nodeType = PublicDocTreeNode.NodeType.URL;
				}
				PublicDocTreeNode node = new PublicDocTreeNode(doc.getDocId(), doc.getUploadFileId(),
						parentId, doc.getDocName(), doc.getUrl(), false, true, true, false, nodeType);
				node.setUploadFileId(doc.getUploadFileId());
				node.setExpanded(false);
				nodes.add(node);
			}
		}
		
		return nodes;
	}
	
	/**
	 * 获取文档树节点.
	 * @param categoryId 根节点ID
	 * @return List < PublicDocTreeNode >
	 */
	@RequestMapping("getFolderTreeNode")
	@ResponseBody
	public List < PublicDocTreeNode > getFolderTreeNode(String categoryId, boolean checkShow) {
		if (StringUtils.isBlank(categoryId)) {
			PublicDocCategory root = categoryService.getRootCategory();
			if (root == null) {
				return null;
			}
			categoryId = root.getCategoryId();
		}
		
		List < PublicDocCategory > categorys = categoryService.getSubTreeCategorys(categoryId, checkShow);
		if (categorys == null || categorys.size() == 0) {
			return null;
		}
		List < PublicDoc > docs = publicDocService.getSubTreeDocsByCategoryId(categoryId, checkShow);
		
		List < PublicDocTreeNode > nodes = new ArrayList < PublicDocTreeNode >();
		
		int categorySize = categorys.size();
		for (int i = 0; i < categorySize; i++) {
			PublicDocCategory c = categorys.get(i);
			PublicDocTreeNode node = new PublicDocTreeNode(c.getCategoryId(), c.getInnerName(),
					c.getParentCategoryId(), c.getCategoryName(),null,
					false, false, true, false, PublicDocTreeNode.NodeType.CATEGORY);
			//node.setIcon(c.getImgUrl());
			node.setExpanded(c.getExpanded());
			nodes.add(node);
		}
		
		if (docs != null && docs.size() > 0) {
			int docSize = docs.size();
			for (int i = 0; i < docSize; i++) {
				PublicDoc doc = docs.get(i);
				if (doc.isFile()) {
					continue;
				}
				
				String parentId = doc.getParentDocId();
				if (StringUtils.isBlank(parentId)) {
					parentId = doc.getCategoryId();
				}
				int nodeType = PublicDocTreeNode.NodeType.FOLDER;
				
				if (PublicDoc.URL.equals(doc.getType())) {
					nodeType = PublicDocTreeNode.NodeType.URL;
				}
				
				PublicDocTreeNode node = new PublicDocTreeNode(doc.getDocId(), doc.getCategoryId(),
						parentId, doc.getDocName(), doc.getUrl(), false, false, true, false, nodeType);
				node.setExpanded(false);
				nodes.add(node);
			}
		}
		
		return nodes;
	}
	
	/**
	 * 获取文档树节点(带权限).
	 * @param categoryId 根节点ID
	 * @param 是否检查显示
	 * @return List < PublicDocTreeNode >
	 */
	@RequestMapping("getFolderTreeNodeWithAuth")
	@ResponseBody
	public List < PublicDocTreeNode > getFolderTreeNodeWithAuth(String categoryId, boolean checkShow) {
		List < PublicDocTreeNode > nodes = this.getFolderTreeNode(categoryId, checkShow);
		if (nodes == null || nodes.size() == 0) {
			return null;
		}
		
		Map < String, PublicDocTreeNode > map = new HashMap < String, PublicDocTreeNode >();
		UserProfile user = this.getUserProfile();
		
		int size = nodes.size();
		for (int i = 0; i < size; i++) {
			PublicDocTreeNode node = nodes.get(i);
			if (node.getNodeType() != PublicDocTreeNode.NodeType.CATEGORY) {
				continue;
			}
			
			String cId = node.getId();
			if (map.containsKey(cId)) {
				continue;
			}
			
			boolean auth = categoryService.checkAuth(user, cId);
			if (!auth) {
				continue;
			}
			
			node.setAuth(true);
			map.put(cId, node);
			String pId = node.getPid();
			if (map.containsKey(pId)) {
				continue;
			} else {
				PublicDocTreeNode parentNode = this.getTreeNode(nodes, pId);
				while (parentNode != null) {
					parentNode.setAuth(categoryService.checkAuth(user, parentNode.getId()));
					map.put(parentNode.getId(), parentNode);
					
					parentNode = this.getTreeNode(nodes, parentNode.getPid());
				}
			}
		}
		
		List < PublicDocTreeNode > authList = new ArrayList < PublicDocTreeNode >();
		for (int i = 0; i < size; i++) {
			PublicDocTreeNode node = nodes.get(i);
			if (map.containsKey(node.getId())) {
				authList.add(map.get(node.getId()));
			} else { //加入有权限的文件夹
				String cId = node.getKey();
				if (StringUtils.isNotBlank(cId) && map.containsKey(cId)) {
					if (map.get(cId).getAuth()) {
						node.setAuth(true);
						authList.add(node);
					}
				}
			}
		}
		
		return authList;
	}
	
	/**
	 * 根据ID查找节点
	 * @param nodes 节点列表
	 * @param id 节点ID
	 * @return PublicDocTreeNode
	 */
	private PublicDocTreeNode getTreeNode(List < PublicDocTreeNode > nodes, String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		
		int size = nodes.size();
		for (int i = 0; i < size; i++) {
			PublicDocTreeNode node = nodes.get(i);
			if (id.equals(node.getId())) {
				return node;
			}
		}
		return null;
	}
	
	/**
	 * 发布老系统的文件柜文件到常用文件. 
	 * @param categoryId 分类ID
	 * @param parentDocId 文件夹ID
	 * @param filePath 旧文件路径
	 * @param fileName 旧文件名
	 * @return String 为空代表成功，否则返回错误信息
	 */
	@RequestMapping("publishOldFile")
	@ResponseBody
	public String publishOldFile(String categoryId, String parentDocId, String filePath, String fileName) {
		return publicDocService.publishOldFile(this.getRequest(), this.getUserProfile(),
				categoryId, parentDocId, filePath, fileName);
	}
	
	/**
	 * 获取文档展现模板码表
	 * @return Map < String, String >
	 */
	@RequestMapping("getTemplateInnerNameMap")
	@ResponseBody
	public Map < String, String > getTemplateInnerNameMap() {
		return PublicDocCategory.TemplateInnerName.getMap();
	}
	
}
