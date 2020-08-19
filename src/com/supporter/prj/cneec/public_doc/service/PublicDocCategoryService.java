package com.supporter.prj.cneec.public_doc.service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.cneec.public_doc.constant.PublicDocConstant;
import com.supporter.prj.cneec.public_doc.dao.PublicDocCategoryDao;
import com.supporter.prj.cneec.public_doc.entity.PublicDocCategory;

/**   
 * @Title: Service
 * @Description: OA_PUBLIC_DOC_CATEGORY.
 * @author Administrator
 * @date 2018-06-04 16:05:02
 * @version V1.0   
 *
 */
@Service
public class PublicDocCategoryService {
	@Autowired
	private PublicDocCategoryDao categoryDao;
	
	public List < PublicDocCategory > getCategoryList() {
		return categoryDao.getAllCategorys();
	}
	
	/**
	 * 从缓存中获取所有文档分类列表.
	 * @return List < PublicDocCategory >
	 */
	@SuppressWarnings("unchecked")
	public List < PublicDocCategory > getCategoryListFromBuffer() {
		String key = PublicDocConstant.KEY_PUBLIC_DOC_CATEGORY;
		if (EIPService.getCacheService().keyExists(key)) {
			return (List < PublicDocCategory >) EIPService.getCacheService().get(key);
		} else {
			List < PublicDocCategory > all = this.getCategoryList();
			ArrayList < PublicDocCategory > newList = new ArrayList <PublicDocCategory>();
			int allSize = 0;
			if (all != null) {
				allSize = all.size();
			}
			
			Deque < PublicDocCategory > nodeDeque = new ArrayDeque < PublicDocCategory >();
			for (int i = 0; i < allSize; i++) {
				PublicDocCategory category = all.get(i);
				if (StringUtils.isBlank(category.getParentCategoryId())) {
					category.setFinalShow(category.isInHome());
					nodeDeque.add(category);
					break;
				}
			}
			//遍历生成子树节点
			while (!nodeDeque.isEmpty()) {
				PublicDocCategory node = nodeDeque.peekFirst();
				newList.add(node);
				
				String id = node.getCategoryId();
				//获得节点的子节点
				for (int i = 0; i < allSize; i++) {
					PublicDocCategory temp = all.get(i);
					String parentId = temp.getParentCategoryId();
					
					if (StringUtils.isBlank(parentId)) {
						continue;
					}
					if (id.equals(parentId)) {
						if (node.getFinalShow()) {
							temp.setFinalShow(temp.isInHome());
						} else {
							temp.setFinalShow(false);
						}
						
						nodeDeque.add(temp);
					}
				}
				
		        nodeDeque.pollFirst();
		    }
			
			
			EIPService.getCacheService().put(key, newList);
			return all;
		}
	}
	
	/**
	 * 从列表中根据ID查找分类
	 * @param categorys 分类列表
	 * @param categoryId 分类ID
	 * @return PublicDocCategory
	 */
	private PublicDocCategory findCategoryByCategoryId(List < PublicDocCategory > categorys, String categoryId) {
		if (categorys == null || categorys.size()  == 0 || categoryId == null) {
			return null;
		}
		int categorySize = categorys.size();
		for (int i = 0; i < categorySize; i++) {
			PublicDocCategory category = categorys.get(i);
			if (categoryId.equals(category.getCategoryId())) {
				return category;
			}
		}
		return null;
	}
	
	/**
	 * 从缓存中获取分类.
	 * @param categoryId 分类ID
	 * @return PublicDocCategory
	 */
	public PublicDocCategory getCategoryFromBuffer(String categoryId) {
		List < PublicDocCategory > categorys = this.getCategoryListFromBuffer();
		if (categorys == null || categorys.size() == 0) {
			return null;
		}
		
		PublicDocCategory returnObj = null;
		
		int size = categorys.size();
		for (int i = 0; i < size; i++) {
			PublicDocCategory category = categorys.get(i);
			if (categoryId.equals(category.getCategoryId())) {
				returnObj = category;
				break;
			}
		}
		
		return returnObj;
	}
	
	/**
	 * 获取指定分类的直接下级分类列表.
	 * @param categoryId 分类ID
	 * @param checkShow  是否检查显示在首页
	 * @return List < PublicDocCategory >
	 */
	public List < PublicDocCategory > getDirectSubCategorys(String categoryId, boolean checkShow) {
		List < PublicDocCategory > categorys = getCategoryListFromBuffer();
		if (categorys == null || categorys.size() == 0) {
			return null;
		}
		
		List < PublicDocCategory > subList = new ArrayList < PublicDocCategory >();
		
		int size = categorys.size();
		
		for (int i = 0; i < size; i++) {
			PublicDocCategory category = categorys.get(i);
			String parentCategoryId = category.getParentCategoryId();
			
			if (StringUtils.isBlank(parentCategoryId)) {
				continue;
			}
			
			if (checkShow) {
				if (!category.getFinalShow()) {
					continue;
				}
			}
			
			if (categoryId.equals(parentCategoryId)) {
				subList.add(category);
			}
		}
		return subList;
	}
	
	/**
	 * 获取指定分类的所有子孙分类列表.
	 * @param categoryId 分类ID
	 * @return List < PublicDocCategory >
	 */
	public List < PublicDocCategory > getSubTreeCategorys(String categoryId, boolean checkShow) {
		List < PublicDocCategory > categorys = getCategoryListFromBuffer();
		if (categorys == null || categorys.size() == 0) {
			return null;
		}
		
		int size = categorys.size();
		
		List < PublicDocCategory > treeNodes = new ArrayList < PublicDocCategory >();
		Deque < PublicDocCategory > nodeDeque = new ArrayDeque < PublicDocCategory >();
		
		//找根节点
		for (int i = 0; i < size; i++) {
			PublicDocCategory category = categorys.get(i);
			if (categoryId.equals(category.getCategoryId())) {
				if (checkShow) {
					if (category.getFinalShow()) {
						nodeDeque.add(category);
					}
				} else {
					nodeDeque.add(category);
				}
				break;
			}
		}
		//遍历生成子树节点
		while (!nodeDeque.isEmpty()) {
			PublicDocCategory node = nodeDeque.peekFirst();
			treeNodes.add(node);
			
			String id = node.getCategoryId();
			//获得节点的子节点
			for (int i = 0; i < size; i++) {
				PublicDocCategory temp = categorys.get(i);
				String parentCategoryId = temp.getParentCategoryId();
				
				if (StringUtils.isBlank(parentCategoryId)) {
					continue;
				}
				if (id.equals(parentCategoryId)) {
					if (checkShow) {
						if (temp.getFinalShow()) {
							nodeDeque.add(temp);
						}
					} else {
						nodeDeque.add(temp);
					}
				}
			}
			
	        nodeDeque.pollFirst();
	    }
		return treeNodes;
	}
	
	/**
	 * 获取或新建分类.
	 * @param parentCategoryId 上级分类ID
	 * @param categoryId 分类ID
	 * @return PublicDocCategory
	 */
	public PublicDocCategory getOrNewCategory(String parentCategoryId, String categoryId) {
		PublicDocCategory category = null;
		if (StringUtils.isNotBlank(categoryId)) {
			category = categoryDao.get(categoryId);
		}
		if (category == null) {
			category = new PublicDocCategory();
			category.setParentCategoryId(parentCategoryId);
			category.setActived(true);
			category.setInHome(true);
		}
		
		if (StringUtils.isNotBlank(category.getParentCategoryId())) {
			PublicDocCategory parent = categoryDao.get(category.getParentCategoryId());
			if (parent != null) {
				category.setParentCategoryName(parent.getCategoryName());
			}
		}
		
		return category;
	}
	
	/**
	 * 删除分类.
	 * @param categoryId 分类ID
	 */
	@Transactional(transactionManager = TransManager.APP, rollbackFor = Exception.class)
	public void deleteCategory(String categoryId) {
		if (StringUtils.isBlank(categoryId)) {
			return;
		} else {
			List < PublicDocCategory > subTreeNodes = this.getSubTreeCategorys(categoryId, false);
			if (subTreeNodes != null && subTreeNodes.size() > 0) {
				categoryDao.delete(subTreeNodes);
				//更新缓存
				refreshCategoryCache();
			}
		}
	}
	
	
	/**
	 * 根据主键获取OA_PUBLIC_DOC_CATEGORY.
	 * 
	 * @param categoryId 主键
	 * @return PublicDocCategory
	 */
	public PublicDocCategory get(String categoryId) {
		return  categoryDao.get(categoryId);
	}
	
	/**
	 * 保存.
	 * @param category 分类对象
	 * @return PublicDocCategory
	 */
	@Transactional(transactionManager = TransManager.APP, rollbackFor = Exception.class)
	public PublicDocCategory saveOrUpdate(PublicDocCategory category) {
		String categoryId = category.getCategoryId();
		
		if (StringUtils.isBlank(categoryId)) {
			categoryDao.save(category);
		} else {
			categoryDao.update(category);
		}
		//更新缓存
		refreshCategoryCache();

		return category;
	}
	
	/**
	 * 判断指定字段唯一性
	 * 
	 * @param categoryId 类别ID
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return boolean
	 */
	public boolean checkPropertyUniquenes(String categoryId, String propertyName, String propertyValue) {
		return this.categoryDao.checkPropertyUniquenes(categoryId, propertyName, propertyValue);
	}
	
	/**
	 * 检查当前用户是否有维护该分类的权限.
	 * 权限逻辑：所见即所得原则，集中控制和灵活控制相结合原则
	 * (1)如果当前节点设置了“权限人”，则按照实际设置的人员进行授权
	 * (2)如果当前节点未设置“权限人”，则需要逐级去查上级的权限，直到找到被授权者或者到根节点
	 * (3)权限控制只控制到各级分类，文件夹和文件的权限从属于自己所在的分类
	 * @param user 用户
	 * @param categoryId 分类ID
	 * @return boolean true:有权限； false:无权限
	 */
	public boolean checkAuth(UserProfile user, String categoryId) {
		if (user == null) {
			return false;
		}
		
		//如果是管理员，则默认有权限
		if (EIPService.getAdminMemberService().isAdminMember(user.getAccountLogin())) {
			return true;
		}
		
		//是否为权限人
		boolean isOwner = false;
		
		String personId = user.getPersonId();
		
		PublicDocCategory category = categoryDao.get(categoryId);
		String ownerIds = category.getOwnerIds();
			
		
		if (StringUtils.isBlank(ownerIds)) {
			//如果未设置被授权者，则需要逐级去查上级的权限，直到找到被授权者或者到根节点
			PublicDocCategory node = category; //从下往上逐级查看的分类节点
			
			boolean findedAuth = false; //是否找到明确的授权
			
			//尚未找到被授权者或者未到根节点就继续循环
			while (!findedAuth) {
				String parentId = node.getParentCategoryId();
				if (StringUtils.isBlank(parentId)) {
					isOwner = false;
					findedAuth = true;
				} else {
					PublicDocCategory parent = this.getCategoryFromBuffer(parentId);
					if (parent == null) {
						isOwner = false;
						findedAuth = true;
					} else {
						if (StringUtils.isBlank(parent.getOwnerIds())) {
							//父节点也没设置人员，则继续往上找
							node = parent;
						} else {
							findedAuth = true;
							
							String[] arrayIds = parent.getOwnerIds().split(",");
							for (int i = 0; i < arrayIds.length; i++) {
								String empId = CommonUtil.trim(arrayIds[i]);
								if (empId.equals(personId)) {
									isOwner = true;
									break;
								}
							}
						}
					}
				}
			} //end while
		} else {
			//如果当前节点设置了“权限人”，则按照实际设置的人员进行授权
			String[] arrayIds = ownerIds.split(",");
			for (int i = 0; i < arrayIds.length; i++) {
				String empId = CommonUtil.trim(arrayIds[i]);
				if (empId.equals(personId)) {
					isOwner = true;
					break;
				}
			}
		}
		
		return isOwner;
	}
	
	/**
	 * 获取从根节点开始的文档分类列表.
	 * @param categoryId 端节点的文档分类ID
	 * @return List < PublicDocCategory >
	 */
	public List < PublicDocCategory > getFullPathCategorys(String categoryId) {
		List < PublicDocCategory > categorys = new ArrayList < PublicDocCategory >();
		
		PublicDocCategory category = categoryDao.get(categoryId);
		while (category != null) {
			categorys.add(0, category);
			if (StringUtils.isNotBlank(category.getParentCategoryId())) {
				category = categoryDao.get(category.getParentCategoryId());
			} else {
				break;
			}
		}
		return categorys;
	}
	
	/**
	 * 更新分类缓存.
	 */
	public void refreshCategoryCache() {
		String key = PublicDocConstant.KEY_PUBLIC_DOC_CATEGORY;
		if (EIPService.getCacheService().keyExists(key)) {
			EIPService.getCacheService().remove(key);
		}
	}
	
	/**
	 * 获取分类树的根节点.
	 * @return PublicDocCategory
	 */
	public PublicDocCategory getRootCategory() {
		return categoryDao.getRootCategory();
	}

}

